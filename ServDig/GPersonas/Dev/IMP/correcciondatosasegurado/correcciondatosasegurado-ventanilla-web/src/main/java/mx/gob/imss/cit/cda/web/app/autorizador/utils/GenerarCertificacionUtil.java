package mx.gob.imss.cit.cda.web.app.autorizador.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacionNSS;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionNSSEnum;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;

@Component
public class GenerarCertificacionUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerarCertificacionUtil.class);
	
	private static final Long  CORRECCION_NOMBRE= 5L;

	private static final Long CORRECCION_DATOS_ESTADISTICOS = 6L;
	
	@Value("${contenido.asegurado.tramite}")
	private String CORREO_ASEGURADO_TRAMITE;
	
	@Value("${encabezado}")
	private String ENCABEZADO_CORREOS;
	
	@Value("${pie.pagina}")
	private String PIE_PAGINA;
	
	public Map<String, Object> generarParametrosReporte(Solicitud solicitud, Map<String, String> participantes, Set<List<PeriodoMovimientoAfiliatorio>> cuentas){
		
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)solicitud.getTramites().get(0);
		
		
		Locale locMEX = new Locale("es", "MX");
		SimpleDateFormat sdf = new SimpleDateFormat("dd ' / ' MM ' / 'yyyy", locMEX);
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		tramite.setPersona(tramite.getPersonaRENAPO());

		parametros.put("CADENA_ORIGINAL", solicitud.getFirmaElectronica().getCadenaOriginal());
		parametros.put("SELLO_DIGITAL", solicitud.getFirmaElectronica().getRecibo());
		parametros.put("SECUENCIA_NOTARIAL", solicitud.getFirmaElectronica().getSecuenciaNotaria());
		parametros.put("NUMERO_SERIE", solicitud.getFirmaElectronica().getSerialCertificado());
		parametros.put("FECHA_FORMATO_DOF", "31 / 07 /2015");
		parametros.put("FOLIO", solicitud.getNoFolioSolicitud());
		parametros.put("FECHA_EXPEDICION", sdf.format(solicitud.getFechaConclusion() != null ? solicitud.getFechaConclusion() : new Date()));
		parametros.put("NSS", tramite.getPersona().getNss());
		parametros.put("CURP", tramite.getPersona().getCurp());
		parametros.put("NOMBRE", tramite.getPersona().getNombre());
		parametros.put("PRIMER_APELLIDO", tramite.getPersona().getPrimerApellido());
		parametros.put("SEGUNDO_APELLIDO", tramite.getPersona().getSegundoApellido());
		parametros.put("FECHA_NACIMIENTO", tramite.getPersona().getFechaNacimientoFormateada());
		parametros.put("LUGAR_NACIMIENTO", tramite.getPersona().getLugarNacimiento().getNombre());
		
		// medio de contacto
		CorreoElectronico correo = null;
				TelefonoFijo telefono = null;	
				
				if(tramite.getPersona().getMediosContacto() != null && !tramite.getPersona().getMediosContacto().isEmpty()){
					for(MedioContacto med : tramite.getPersona().getMediosContacto()){
						if(med instanceof CorreoElectronico){
							 correo = (CorreoElectronico)med;	
						}
						
						if(med instanceof TelefonoFijo){
							 telefono = (TelefonoFijo)med;	
						}
					}
				}
		
		
		
		parametros.put("TELEFONO_FIJO", telefono != null && telefono.getNumero() != null  ? telefono.getNumero() :"");
		parametros.put("MAIL", correo != null ? correo.getCorreo() :"");
		
        List<TipoRegularizacionNSS> listTipoRegulacion = obtenerClaveNSSReporte(tramite, tramite.getPersona().getNss() );
		
		parametros.put("LISTA_TIPO_NSS",listTipoRegulacion);
	 
		
		parametros.put("SEXO", tramite.getPersona().getSexo().getIdSexo().toString());
		parametros.put("RESPONSABLE", participantes.get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
		parametros.put("AUTORIZADOR", participantes.get(ParticipantesEnum.AUTORIZADOR.getDescripcion()));
		
		Iterator<List<PeriodoMovimientoAfiliatorio>> perIterator = cuentas.iterator();
		if(perIterator.hasNext()){
			parametros.put("LISTA_DATOS_LABORALES", crearMovimientosAfiliatorios(perIterator.next()));
			LOGGER.debug("-------CDA------ cuenta{}", parametros.get("LISTA_DATOS_LABORALES"));
		}
		
		if(perIterator.hasNext()){
			parametros.put("LISTA_MOVIMIENTO_FINAL", perIterator.next());
			LOGGER.debug("-------CDA------ cuenta{}", parametros.get("LISTA_MOVIMIENTO_FINAL"));
		}
		
		List <String> listMotivosAclaracion = new ArrayList<String>();
		for(MotivoAclaracion motivoAclaracion : tramite.getMotivosAclaracion()){
			
				listMotivosAclaracion.add(motivoAclaracion.getDescripcionMotivo());
				
		}
		parametros.put("MOTIVO_ACLARACION",listMotivosAclaracion.toString().toUpperCase());
		
		parametros.put("ORIGEN",solicitud.getOrigenSolicitud().getIdTipoSolicitud().toString());
		
		
		
		return parametros;		
	}
	
	private List<PeriodoMovimientoAfiliatorio> crearMovimientosAfiliatorios(List<PeriodoMovimientoAfiliatorio>periodos){
		List<PeriodoMovimientoAfiliatorio> periodoMovimientos = new ArrayList<PeriodoMovimientoAfiliatorio>();
		if(periodos!=null && !periodos.isEmpty()){
			periodoMovimientos.addAll(periodos);
		}else{
			PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio = new PeriodoMovimientoAfiliatorio();
			periodoMovimientoAfiliatorio.setFechaFinalMovimiento(null);
			periodoMovimientoAfiliatorio.setFechaInicioMovimiento(null);
			periodoMovimientoAfiliatorio.setNrp(null);
			periodoMovimientos.add(periodoMovimientoAfiliatorio);
		}
		
		
		return periodoMovimientos;
	}
	
	private List<TipoRegularizacionNSS> obtenerClaveNSSReporte(TramiteCorreccionCurp tramiteCorreccionCurp, String nss){
		List<TipoRegularizacionNSS> claveNSS = new ArrayList<TipoRegularizacionNSS>();
		
		LOGGER.debug("valor de topo NSS: {}",tramiteCorreccionCurp.getCertificacionNSS().getTipoRegularizacionNSS());
		for (TipoRegularizacionNSS idNSS: tramiteCorreccionCurp.getCertificacionNSS().getTipoRegularizacionNSS()){
			TipoRegularizacionNSS tipoRegNss = new TipoRegularizacionNSS();
			if(idNSS.getIdTipoRegularizacionNSS().equals(TipoRegularizacionNSSEnum.CORRECCION_DATOS_ESTADISTICOS.getId())){
				tipoRegNss.setIdTipoRegularizacionNSS(CORRECCION_DATOS_ESTADISTICOS);
				
			}else{ 
				if(idNSS.getIdTipoRegularizacionNSS().equals(TipoRegularizacionNSSEnum.CORRECCION_NOMBRE.getId())){
					tipoRegNss.setIdTipoRegularizacionNSS(CORRECCION_NOMBRE);
				}
				
			}
			tipoRegNss.setDescripcionRegularizacionNSS(nss);
			claveNSS.add(tipoRegNss);
			
		}
		LOGGER.debug("LA CLAVE ES: {}",claveNSS);
		return claveNSS;
	}
	
	public String contenidoCorreoAtendida(Solicitud solicitud, String link) {

		StringBuffer body = new StringBuffer();
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");

		body.append(ENCABEZADO_CORREOS);
		body.append(MessageFormat.format(
				CORREO_ASEGURADO_TRAMITE,
				new Object[] {
						fecha.format(new Date()),
						solicitud.getNoFolioSolicitud(),
						((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getPersonaRENAPO().getNombre(),
						((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getPersonaRENAPO().getPrimerApellido(),
						((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getPersonaRENAPO().getSegundoApellido(),
						solicitud.getSubdelegacion() != null ? solicitud.getSubdelegacion().getDescripcion() : "", 
						link, 
						((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getPersonaRENAPO().getCurp()}));
		body.append(PIE_PAGINA);

		return body.toString();
		}


}
