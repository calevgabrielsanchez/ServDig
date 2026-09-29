package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpSession;

import mx.gob.imss.consultamod40.Modalidad40VO;
import mx.gob.imss.consultamod40.RespuestaModalidad40;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum;
import mx.gob.imss.ctirss.delta.model.gestion.seguro.PatronPlataformasDigitales;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.digital.modelo.cobranza.EstadoPago;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.solicitud.FirmaElectronica;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SeguroCvroUtil {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SeguroCvroUtil.class);
	private static final Integer NUM_SALARIOS = 25;
	private static final String KEY_NSS_CIFRADO_FINAL = "_nssCifradoFinal";
	private static final String KEY_NSS_MOD40 = "nss";
	private static final String KEY_NSS_CIFRADO_MOD40 = "nssCifrado";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String DESC_ALTA_TRAMITE_SOLICITUD = "ALTA DE SEGURO CVRO";
	private static final Integer NUM_VIGENCIA_SEGURO = 12;
    /**
     * MOdalidades de una persona que lo hacen no valido para dquirir un seguro
     */
    private static final String[] MODALIDADES_NO_VALIDAS = new String[] {"10", "13", "14", "17","30"};
	private static final String BAJA_RO = "02";
	private static final DecimalFormat format = new DecimalFormat("#####0.00", new DecimalFormatSymbols(new Locale("es-MX")));
	
	public String formatCurrency(BigDecimal amount) {
		String amountFormat = "0.00";
		amountFormat = format.format(amount);
		return amountFormat;
	}

	public BigDecimal calcularSalaraioMaximo(BigDecimal sdiMax) {
		sdiMax = sdiMax.multiply(new BigDecimal(NUM_SALARIOS));
		return sdiMax;
	}

	public Map<String, Object> descifrarNss(HttpSession session,
			String valorRetornoErrorNssCifrado, String nssCifrado) {
		Map<String, Object> result = new HashMap<String, Object>();
		String nss = null;
		try {
			nss = Base64Cipher.descrifrar(nssCifrado);
		} catch (InvalidKeyException e1) {
			nssCifrado = valorRetornoErrorNssCifrado;
			e1.printStackTrace();
		} catch (IllegalBlockSizeException e1) {
			nssCifrado = (String) session.getAttribute(KEY_NSS_CIFRADO_FINAL);
			if (StringUtils.isNotEmpty(nssCifrado)
					&& StringUtils.isNotBlank(nssCifrado)) {
				try {
					nss = Base64Cipher.descrifrar(nssCifrado);
				} catch (Exception e) {
					nssCifrado = valorRetornoErrorNssCifrado;
					e.printStackTrace();
				}
			} else {
				nssCifrado = valorRetornoErrorNssCifrado;
				e1.printStackTrace();
			}
		} catch (BadPaddingException e1) {
			nssCifrado = valorRetornoErrorNssCifrado;
			e1.printStackTrace();
		} catch (IOException e1) {
			nssCifrado = valorRetornoErrorNssCifrado;
			e1.printStackTrace();
		}

		if (StringUtils.isNotEmpty(nssCifrado)
				&& StringUtils.isNotBlank(nssCifrado)
				&& !nssCifrado.equals("-1")) {
			session.setAttribute(KEY_NSS_CIFRADO_FINAL, nssCifrado);
		} else {
			session.removeAttribute(KEY_NSS_CIFRADO_FINAL);
		}

		result.put(KEY_NSS_MOD40, nss);
		result.put(KEY_NSS_CIFRADO_MOD40, nssCifrado);

		return result;
	}

	public boolean esSolicitudInternet(Long origenSolicitud) {
		boolean esInternet = false;
		OrigenSolicitudEnum origen = OrigenSolicitudEnum
				.getById(origenSolicitud);
		if (origen.equals(OrigenSolicitudEnum.INTERNET)) {
			esInternet = true;
		}
		return esInternet;
	}

	public String generarXMLValidacionCorreoElectronico(Long idPersona) {
		StringBuffer sbXmlValidacion = new StringBuffer();
		sbXmlValidacion
				.append("<mx:idPersona xmlns:mx=\"http://mx.gob.imss.digital.modelo.seguros\">")
				.append(idPersona).append("</mx:idPersona>");
		return sbXmlValidacion.toString();
	}
	
	public void generarCadenaOriginalyFirma(Solicitud solicitud,
			Persona persona, HttpSession session) {

		Locale locMEX = new Locale("es", "MX");
		DateFormat dateFormat = new SimpleDateFormat(
				"dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		Date fecha = Calendar.getInstance().getTime();
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		StringBuffer contenidoAFirmar = new StringBuffer();

		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");

		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(DESC_ALTA_TRAMITE_SOLICITUD).append("|");

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(fecha);
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(fecha);

		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNumSolicitud()).append("|");

		// Checar si tiene que ir el RFC en la cadena original
		// contenidoAFirmar.append("RFC:");
		// contenidoAFirmar.append(persona.getRfc()).append("|");
		// datosEntradaFirma.setRfc(persona.getRfc());

		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
	
	
	public boolean validarTiempoRenovacion(Date fechaInicioMora){
		Calendar fechaTramiteActual = Calendar.getInstance();
		Calendar fechaFinRenovacion = Calendar.getInstance();
		fechaFinRenovacion.setTime(fechaInicioMora);
		//Se le agrega 12 meses, para saber fecha máxima de renovación
		fechaFinRenovacion.add(Calendar.MONTH, NUM_VIGENCIA_SEGURO);
		return fechaTramiteActual.before(fechaFinRenovacion);
	}
	
	public Date obtenerFechaInicioMora(Pago[] listPagos){
		List<Pago> pagos = new ArrayList<Pago>(Arrays.asList(listPagos));

		Collections.sort(pagos, new Comparator<Pago>() {
			@Override
			public int compare(Pago o1, Pago o2) {
				int diffFecLim = o1.getFechaLimitePago().compareTo(
						o2.getFechaLimitePago());
				
				if (diffFecLim != 0) {
					return diffFecLim;
				}
				
				int diffFechaInicio = o1.getFechaInicioPeriodo().compareTo(
						o2.getFechaInicioPeriodo());
				
				if (diffFechaInicio != 0) {
					return diffFechaInicio;
				}
				
				int diffIdPago = o1.getIdPago().compareTo(o2.getIdPago());
				
				return diffIdPago;
			}
		});
		Date fechainicioBaja= null;

        Pago ultimoPagoVencido = null;
        
		for(int cont=0;cont<pagos.size()-1;cont++){
			Pago pagoReferencia=pagos.get(cont);
			if (pagoReferencia.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.VENCIDO.getId()){

				ultimoPagoVencido = pagoReferencia;
				
				LOGGER.info("Existe un pago vencido");
			    Pago pagoPosterior=pagos.get(cont+1);
                
				if (pagoPosterior.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.VENCIDO.getId()){
					
					LOGGER.info("Existe un pago posterior vencido: "+pagoPosterior);
					fechainicioBaja=pagoReferencia.getFechaInicioPeriodo();
					cont=pagos.size();
				}
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		//Si la fecha de inicio de baja es null
		if(fechainicioBaja==null){
			
			LOGGER.info("No se tienen dos pagos vencidos, se calcula la fecha de baja por mora.");
			
			//se evalua si hay al menos un periodo en baja
			if(ultimoPagoVencido != null) {
				
				LOGGER.info("Existe un pago vencido, se toma la fecha de inicio");
				//Si existe un periodo vencido se toma su fecha de inicio como inicio de baja
				fechainicioBaja = ultimoPagoVencido.getFechaInicioPeriodo();
				LOGGER.info("fecha inicio baja1: "+sdf.format(fechainicioBaja));
			}else {
				LOGGER.info("No existe ningun periodo vencido, se toma como fecha inicial un dia posterior a la fecha final del ultimo pago.");
				
				Pago ultimoPago = pagos.get(pagos.size()-1);
				Date fechaFinUltPago = ultimoPago.getFechaFinPeriodo();
				
				LOGGER.info("fecha fin ultimo pago: "+sdf.format(fechaFinUltPago));
				
				//Se suma un dia a la fecha fin
				Calendar calendar = Calendar.getInstance();
			    calendar.setTime(fechaFinUltPago); 
			    calendar.add(Calendar.DAY_OF_YEAR, 1);  
			    fechainicioBaja = calendar.getTime(); 
				
			    LOGGER.info("fecha inicio baja2: "+sdf.format(fechainicioBaja));
			    
			}
			
			
        }else {
        	LOGGER.info("fecha inicio baja: "+sdf.format(fechainicioBaja));
        }

		return fechainicioBaja;
	}
	
	public boolean isRenovacionATiempo(SeguroIvro seguro){
		return validarTiempoRenovacion(obtenerFechaInicioMora(seguro.getCompra().getPagos()));
	}
	
	/**
	 * metodo que indica si la modalidad es una de las consideradas de Regimen Obligatorio
	 * @param modalidad que se va a validar
	 * @return True si es R.O.   False si no es R.O.
	 */
	private boolean esModalidadRegimenObligatorio(String modalidad){
		boolean esRegimenObligatorio = false;
		for(String modalidadRO: MODALIDADES_NO_VALIDAS){
			if(modalidadRO.equals(modalidad)){esRegimenObligatorio = true;}
		}
		return esRegimenObligatorio;
	}
	
	
	/**
	 * Metodo para validar si se tiene que hacer una compra sin importar que se tenga seguro para renovar
	 * @param response del WS wSConsultaMod40 con los datos de los ultimos movimientos
	 * @return 
	 */
	public boolean esCompraDirecta(RespuestaModalidad40 response, SeguroIvro seguro){
		boolean esCompraDirecta = false;
		Modalidad40VO modalidad40VO = response.getModalidad40();
		if(seguro != null && (seguro.getEstadoSeguro().getIdEstadoSeguro() == EstadoSeguroIvroEnum.VENCIDO.getId()) && !seguro.getTramite().getRenovacion()){
			esCompraDirecta = true;
		}

		if(!esCompraDirecta && esModalidadRegimenObligatorio(modalidad40VO.getModUltimoMov())){
			if(modalidad40VO.getTipoUltimoMov().equals(BAJA_RO)){
				 esCompraDirecta = true;
			}
		}
		return esCompraDirecta;
	}
	
	/**
	 * Metodo para validar si se tiene que hacer una compra sin importar que se tenga seguro para renovar
	 * @param response del WS wSConsultaMod40 con los datos de los ultimos movimientos
	 * @return 
	 */
	public boolean esCompraDirectaPlataformasDigitales(RespuestaModalidad40 response, SeguroIvro seguro, List<PatronPlataformasDigitales> patronPlataformas ){
		boolean esCompraDirecta = false;
		Modalidad40VO modalidad40VO = response.getModalidad40();
		if(seguro != null && (seguro.getEstadoSeguro().getIdEstadoSeguro() == EstadoSeguroIvroEnum.VENCIDO.getId()) && !seguro.getTramite().getRenovacion()){
			esCompraDirecta = true;
		}

		if(!esCompraDirecta && esModalidadRegimenObligatorio(modalidad40VO.getModUltimoMov())){
			if(modalidad40VO.getTipoUltimoMov().equals(BAJA_RO)){
				 esCompraDirecta = true;
			}
		}
		
		
		if ((modalidad40VO.getModUltimoMov().equals("10")) &&( modalidad40VO.getTipoUltimoMov().equals(BAJA_RO)) && esCompraDirecta == true ) {
			for(PatronPlataformasDigitales temp:patronPlataformas){
				if(temp.getCveRegPatron().equals(modalidad40VO.getRegPatUltimoMov())){
					esCompraDirecta = false;
				}
			}
		}
		
		return esCompraDirecta;
	}
	
	
	public void eliminarCaracteresNoPermitidosDomicilio(Domicilio domicilioSeguro){
		if(domicilioSeguro == null){
			return;
		}
		if(domicilioSeguro.getCalle() != null){
			domicilioSeguro.setCalle(StringEscapeUtils.escapeXml(domicilioSeguro.getCalle()));
        }
        if(domicilioSeguro.getNumExteriorAlf() != null){
        	domicilioSeguro.setNumExteriorAlf(StringEscapeUtils.escapeXml(domicilioSeguro.getNumExteriorAlf()));
        }
        if(domicilioSeguro.getNumInteriorAlf() != null){
        	domicilioSeguro.setNumInteriorAlf(StringEscapeUtils.escapeXml(domicilioSeguro.getNumInteriorAlf()));
        }
        if(domicilioSeguro.getVialidadPrimaria() != null && domicilioSeguro.getVialidadPrimaria().getNombre() != null){
        	domicilioSeguro.getVialidadPrimaria().setNombre(StringEscapeUtils.escapeXml(domicilioSeguro.getVialidadPrimaria().getNombre()));
        }
	}
		

    public static mx.gob.imss.digital.modelo.persona.Fisica convertirFisica(
    		mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica origen) {
    		 
    		if (origen == null) {
    		return null;
    		}
    		 
    		mx.gob.imss.digital.modelo.persona.Fisica destino =
    		new mx.gob.imss.digital.modelo.persona.Fisica();
    		 
    		// Datos básicos
    		destino.setCveFisica(origen.getCveFisica());
    		destino.setNombre(origen.getNombre());
    		destino.setPrimerApellido(origen.getPrimerApellido());
    		destino.setSegundoApellido(origen.getSegundoApellido());
    		 
    		destino.setCurp(origen.getCurp());
    		destino.setCurpRenapo(origen.getCurpRenapo());
    		 
    		destino.setNss(origen.getNss());
    		destino.setNssCifrado(origen.getNssCifrado());
    		 
    		destino.setFechaNacimiento(origen.getFechaNacimiento());
    		destino.setFechaDefuncion(origen.getFechaDefuncion());
    		 
    		destino.setFechaRegistro(origen.getFechaRegistro());
    		destino.setFechaBaja(origen.getFechaBaja());
    		destino.setFechaModificacion(origen.getFechaModificacion());
    		 
    		destino.setFechaNacimientoFormateada(
    		origen.getFechaNacimientoFormateada());
    		 
    		destino.setAltaEnImss(origen.getAltaEnImss());
    		destino.setNumeroLineaArchivo(origen.getNumeroLineaArchivo());
    		 
    		destino.setBusqAprox(origen.getBusqAprox());
    		 
    		destino.setEstadosFormateados(
    		origen.getEstadosFormateados());
    		 
    		destino.setSubEstadosFormateados(
    		origen.getSubEstadosFormateados());
    		 
    		destino.setMesRegistroNac(origen.getMesRegistroNac());
    		destino.setAnioRegistroNac(origen.getAnioRegistroNac());
    		 
    		destino.setEstatusRenapo(origen.getEstatusRenapo());
    		destino.setCveEstatusRenapo(origen.getCveEstatusRenapo());
    		 
    		// Datos heredados de Persona
    		destino.setIdPersona(origen.getIdPersona());
    		destino.setRfc(origen.getRfc());
    		 
    		if (origen.getTipoPersona() != null) {
    		 
    		mx.gob.imss.digital.modelo.persona.TipoPersona tipoPersona =
    		new mx.gob.imss.digital.modelo.persona.TipoPersona();
    		 
    		tipoPersona.setIdTipoPersona(
    		origen.getTipoPersona().getIdTipoPersona());
    		 
    		destino.setTipoPersona(tipoPersona);
    		}
    		 
    		// Sexo
    		if (origen.getSexo() != null) {
    		 
    		mx.gob.imss.digital.modelo.persona.Sexo sexo =
    		new mx.gob.imss.digital.modelo.persona.Sexo();
    		 
    		sexo.setIdSexo(origen.getSexo().getIdSexo());
    		 
    		destino.setSexo(sexo);
    		}
    		 
    		// Estado civil
    		if (origen.getEstadoCivil() != null) {
    		 
    		mx.gob.imss.digital.modelo.persona.EstadoCivil estadoCivil =
    		new mx.gob.imss.digital.modelo.persona.EstadoCivil();
    		 
    		estadoCivil.setIdEstadoCivil(
    		origen.getEstadoCivil().getIdEstadoCivil());
    		 
    		destino.setEstadoCivil(estadoCivil);
    		}
    		 
    		// País
    		if (origen.getPais() != null) {
    		 
    		mx.gob.imss.digital.modelo.domicilio.Pais pais =
    		new mx.gob.imss.digital.modelo.domicilio.Pais();
    		 
    		pais.setIdPais(origen.getPais().getIdPais());
    		 
    		destino.setPais(pais);
    		}
    		 
    		// Entidad federativa
    		if (origen.getLugarNacimiento() != null) {
    		 
    		mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidad =
    		new mx.gob.imss.digital.modelo.domicilio.EntidadFederativa();
    		 
    		entidad.setClave(
    		origen.getLugarNacimiento().getClave());
    		 
    		destino.setLugarNacimiento(entidad);
    		}
    		 
	return destino;
	}

}
