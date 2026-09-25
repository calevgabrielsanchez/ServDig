/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.SeguroIndividualServices;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeguroIvroRenovacionUtil{
    
    private static final Logger log = LoggerFactory.getLogger(SeguroIvroRenovacionUtil.class);
    /**
     * Constante que indica que la aplicacion corre en el ambiente de internet
     */
    public static final String KEY_ORIGEN_ID_CONTEXT = "ORIGEN_APP_ID";
    private static final Integer NUM_VIGENCIA_SEGURO = 12;
    
    /** The Constant VIEW_DETALLE_SEGURO. */
    public static final String VIEW_DETALLE_SEGURO = "wizardDetalleSeguroContenido";
    private static final String MSG_EXCEPTION_EXTEMPORANEA = "El seguro de tu trabajador doméstico venció, puede acudir a tu Subdelegación para realizar el trámite de renovación extemporánea o continua tu solicitud como una Incorporación Inicial, en este caso elije la opción Siguiente. Ver mayor información";
	private static final String MSG_EXCEPTION_NSS_NO_VIGENTE = "El número de seguridad social (NSS) no está vigente o no se localiza, favor de acudir a la Subdelegación";

	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	
	@Autowired
    private SeguroIndividualServices seguroIndividualServices;
    
    /** El sistema valida el periodo de renovación (Ver RN-020-00-13).
     * @param result
     * @param seguros
     * @param idSerguroRenovacion */
	public void validaPeriodoRenovacion(Map<String, Object> result, SegurosIvro seguros, Long idSerguroRenovacion){
		if(seguros != null && seguros.getSeguroIvro().length > 0){
			for(SeguroIvro seguroIvo : seguros.getSeguroIvro()){
				if(seguroIvo.getCveIdSeguroIvro()!= null && seguroIvo.getCveIdSeguroIvro().equals(idSerguroRenovacion)){
					if(seguroIvo.getCompra().getPagos() == null){
						log.error("No tiene pagos asociados para calcular el periodo de renovacion");
						result.put("error", Boolean.TRUE);
						result.put("msgError", MSG_EXCEPTION_EXTEMPORANEA);
						return;
					}
					boolean isRenovacionATiempo = isRenovacionATiempo(seguroIvo);
					if (!isRenovacionATiempo) {
						log.error(MSG_EXCEPTION_EXTEMPORANEA);
						result.put("error", Boolean.TRUE);
						result.put("msgError", MSG_EXCEPTION_EXTEMPORANEA);
					}else{
						log.debug("Renovacion en tiempo.");
					}
				}
			}
		}
	}
	
	public boolean isRenovacionATiempo(SeguroIvro seguro){
		return validarTiempoRenovacion(obtenerFechaInicioMora(seguro.getCompra().getPagos()));
	}
	
	public boolean validarTiempoRenovacion(Date fechaInicioMora){
		Calendar tiempoRenovacion=Calendar.getInstance();
		tiempoRenovacion.setTime(fechaInicioMora);
		tiempoRenovacion.add(Calendar.MONTH, NUM_VIGENCIA_SEGURO);
		return fechaInicioMora.compareTo(tiempoRenovacion.getTime()) < 0;
	}
	
public Date obtenerFechaInicioMora(Pago[] listPagos){
		
		
		List<Pago> pagos = new ArrayList<Pago>();
		for(Pago pago: listPagos){
			pagos.add(pago);
		}
		Collections.sort(pagos, new OrdenarPago());
		Pago pagoReferencia = null;
		Date fechainicioBaja= null;
		for(int cont=0;cont<pagos.size()-1;cont++){
			pagoReferencia=pagos.get(cont);
			if(pagoReferencia.getEstadoPago() != null){
				if (pagoReferencia.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.VENCIDO.getId()){
					Pago pagoPosterior=pagos.get(cont+1);
					if (pagoPosterior.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.VENCIDO.getId()){
						fechainicioBaja=pagoReferencia.getFechaInicioPeriodo();
						cont=pagos.size();
					}
				}
				if(fechainicioBaja != null){
					return fechainicioBaja;
					}
			}
		}
		return pagoReferencia.getFechaInicioPeriodo();
	}
	
    public void validarBeneficiariosNss(Map<String, Object> result, Long idAmbiente, SeguroIvro seguroSeleccionado) {
        if (seguroSeleccionado != null && seguroSeleccionado.getTramite() != null && seguroSeleccionado.getTramite().getBeneficiarios() != null) {
            for (Fisica beneficiario : seguroSeleccionado.getTramite().getBeneficiarios()) {
                if (beneficiario != null && beneficiario.getNss() != null) {
                    try {
                        GrupoFamiliar grupoFam = grupoFamiliarService.getGrupoFamiliar(beneficiario.getNss(), false);
                        if (grupoFam != null && grupoFam.getAsignacionNSS() != null) {
                        OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(idAmbiente);
                        Persona persona = new Persona();
                        persona.setIdPersona(grupoFam.getAsignacionNSS().getIdPersona());
                        persona.setRfc(grupoFam.getAsignacionNSS().getRfc());
                        persona.setTipoPersona(new TipoPersona());
                        persona.getTipoPersona().setIdTipoPersona(TipoPersonaEnum.FISICA.getId());

                        DatosCalculoCuota dcc = seguroIndividualServices.obtenDatosCotizacion(persona, origen, null);
                        if(!validaVigenciaNss(dcc)){
                            
			result.put("error", Boolean.TRUE);
			result.put("msgError", MSG_EXCEPTION_NSS_NO_VIGENTE);
                        }
                    } else {
			result.put("error", Boolean.TRUE);
			result.put("msgError", MSG_EXCEPTION_NSS_NO_VIGENTE);
                    }
                    } catch (DerechohabientesBusinessException e) {
                        log.error("Error derechhabiente al consultar nss.",e);
                    } catch (Exception ex) {
                        log.error("Exception general atrapada.",ex);
                    }
                    

                }
            }
        }
    }
	
	
	public boolean validaVigenciaNss(DatosCalculoCuota dcc ){
		if(dcc.getModalidad() == 0) {
			if(dcc.getErrorFormGeneral() != null){
				log.info(" dcc.getErrorFormGeneral():" + dcc.getErrorFormGeneral());
				
				if(dcc.getErrorFormGeneral().contains("No se localiza el registro de NSS asociado a tu  CURP, si no cuentas con NSS ingrese")
					&& dcc.getErrorFormGeneral().contains("Si ya cuentas con NSS, acude a tu subdelegaci\u00F3n para actualizar los datos de tu registro.")){
					log.info(" se encontro error al validar la vigencia del nss");
					return false;
				}
			}
		}
                return true;
	}
	
	/**
	 * TODO util
	 * Agrega la lista de seguros a un mapa
	 * @param seguros
	 * @return
	 */
	public Map<Long, SeguroIvro> mapaSeguros(SegurosIvro seguros){
		Map<Long, SeguroIvro> mapaSeguros = new HashMap<Long, SeguroIvro>();
		if(seguros != null && seguros.getSeguroIvro() != null && seguros.getSeguroIvro().length > 0){
			for(SeguroIvro seguroIvro : seguros.getSeguroIvro()){
				mapaSeguros.put(seguroIvro.getCveIdSeguroIvro(), seguroIvro);
			}
		}
		return mapaSeguros;
	}
	
	/*
	 * Util
	 */
	public void generaHashMapsFechas(Collection<SeguroIvro> seguros, Map<Long, String> mapaAseguamiento, Map<Long, String> mapaRenovacon){
		for(SeguroIvro seguroIvro : seguros){
			if(seguroIvro != null && seguroIvro.getCompra() != null && seguroIvro.getCompra().getPagos() != null){
				obtieneFechasFromPagos(seguroIvro.getCompra().getPagos(), seguroIvro.getCveIdSeguroIvro(), mapaAseguamiento, mapaRenovacon);
			}
		}
	}
	
	/*
	 * Util
	 */
	public void obtieneFechasFromPagos(Pago[] pagos, Long idSeguro, Map<Long, String> mapaAseguamiento, Map<Long, String> mapaRenovacon){
		if(pagos.length == 0){
			return;
		}
		String patronFechaCorta = "dd/MM/yyyy";
		SimpleDateFormat formatoFechaCorta = new SimpleDateFormat(patronFechaCorta);
		
		Map<Long, Pago> mapPagos = new HashMap<Long, Pago>();
		for(Pago pago : pagos){
			mapPagos.put(pago.getIdPago(), pago);
		}
		List<Long> listaIdPagos = new ArrayList<Long>(mapPagos.keySet());
		Collections.sort(listaIdPagos);
		Long primerElemento = listaIdPagos.get(0);
		Long ultimoElemento = listaIdPagos.get(listaIdPagos.size() - 1);
		
		Pago primerPago = mapPagos.get(primerElemento);
		Pago ultimoPago = mapPagos.get(ultimoElemento);
		Date fechaInicioPagos = null, fechaFinPagos = null;
		
		if(primerPago != null){
			fechaInicioPagos = primerPago.getFechaInicioPeriodo();
		}
		if(ultimoPago != null){
			fechaFinPagos = ultimoPago.getFechaFinPeriodo();
		}
		
		if(fechaInicioPagos != null && fechaFinPagos != null){
			String strFechaAseguramiento = formatoFechaCorta.format(fechaInicioPagos) + " a " + formatoFechaCorta.format(fechaFinPagos);
			//restar a la fecha fin un mes
			Calendar cal = Calendar.getInstance();
		    cal.setTime(fechaFinPagos);
		    cal.add(Calendar.DATE, -30);
		    Date dateEndBefore1Month = cal.getTime();
		    String strFechaRenovacion = formatoFechaCorta.format(dateEndBefore1Month) + " a " + formatoFechaCorta.format(fechaFinPagos);
		    mapaAseguamiento.put(idSeguro, strFechaAseguramiento);
		    mapaRenovacon.put(idSeguro, strFechaRenovacion);
		}
	}

	public GrupoFamiliarServiceRemote getGrupoFamiliarService() {
		return grupoFamiliarService;
	}

	public void setGrupoFamiliarService(
			GrupoFamiliarServiceRemote grupoFamiliarService) {
		this.grupoFamiliarService = grupoFamiliarService;
	}

	public SeguroIndividualServices getSeguroIndividualServices() {
		return seguroIndividualServices;
	}

	public void setSeguroIndividualServices(
			SeguroIndividualServices seguroIndividualServices) {
		this.seguroIndividualServices = seguroIndividualServices;
	}
   
}
