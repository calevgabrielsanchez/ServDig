package mx.gob.imss.cit.cda.web.app.responsable.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.web.app.responsable.model.Solicitud;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CertificacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoNSSCorreccion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.springframework.stereotype.Component;
import org.apache.commons.lang.StringEscapeUtils;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Value;

@Component
public class ConfirmarSolicitudUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfirmarSolicitudUtil.class);
	
	@Value("${encabezado}")
	private String ENCABEZADO_CORREOS;
	
	@Value("${pie.pagina}")
	private String PIE_PAGINA;
	
	
	@Value("${contenido.correo.atencion.autorizador}")
	private String CONTENIDO_CORREO_ATENCION_AUTORIZADOR;

	
	public  mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud crearSolicitudConfirmacionDatos(UpdateEvent<Solicitud> requestUpdateEvent){
		  
		  mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitudIMSS = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		  
		  solicitudIMSS.setSolicitudId(Long.parseLong(requestUpdateEvent.getData().getId()));
		  
		  EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		  estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		  solicitudIMSS.setEstadoSolicitud(estadoSolicitud);
		  
		  List <Tramite> lstTramite = new ArrayList<Tramite>();
		  TramiteCorreccionCurp tramite = new TramiteCorreccionCurp();
		  
		//TODO actualizar el detalle de la correccion
		  //Tipo de Regularizacion de la solicitud
		  tramite.setTipoRegularizacion(getTipoRegularizacion(requestUpdateEvent.getData()));
		  
		  //Tipo de cada NSS && Tipo de Correccion del NSS
		  tramite.setCertificacionNSS(getCertificacionNSS(requestUpdateEvent.getData()));
		  
		  EstadoTramite estadoTramite = new EstadoTramite();
		  estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo()));
		  estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo());
		  tramite.setTramiteId(Long.parseLong(requestUpdateEvent.getData().getIdTramite()));
		  tramite.setEstadoTramite(estadoTramite);
		  
		if (tramite.getObservacionesSubdelegacion() == null) {
			tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
		}
		ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
		obSubdelegacion.setFechaActualizacion(new Date());
		obSubdelegacion.setUsuario(requestUpdateEvent.getUserProfile().getUsuario());
		obSubdelegacion.setAsignado(((Solicitud)requestUpdateEvent.getData()).getCurpResponsable());
		obSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
		tramite.getObservacionesSubdelegacion().add(obSubdelegacion);
		  
		  
		  lstTramite.add(tramite);
		  solicitudIMSS.setTramites(lstTramite);
		  
		  return solicitudIMSS;
		  
	  }
	
	//Tipo de regularizacion de cada NSS TipoRegularizacionNSS vs TipoNSS
	private CertificacionNSS getCertificacionNSS(Solicitud solicitud) {
		CertificacionNSS certificacionNSS = new CertificacionNSS();
		
		TipoNSSCorreccion correccionNSS = new TipoNSSCorreccion();
		
		//TODO iterar los datos por NSS en lugar de obtener de manera fija la posicion 0
		List<TipoRegularizacionNSS> tiposRegularizacionNSS = null;
		if (solicitud.getGridNSS().getData().get(0).getGrupoCorreccion().getIdRegularizacionNSS() != null 
				&& !solicitud.getGridNSS().getData().get(0).getGrupoCorreccion().getIdRegularizacionNSS().isEmpty()){
			tiposRegularizacionNSS = new ArrayList<TipoRegularizacionNSS>();
			Set<Long> setTiposRegularizacionNSS = new HashSet<Long>(solicitud.getGridNSS().getData().get(0).getGrupoCorreccion().getIdRegularizacionNSS());
			for (Long idTipoRegularizacionNSS : setTiposRegularizacionNSS) {
				TipoRegularizacionNSS regulariacionNSS = new TipoRegularizacionNSS();
				regulariacionNSS.setIdTipoRegularizacionNSS(idTipoRegularizacionNSS);
				tiposRegularizacionNSS.add(regulariacionNSS);
			}
			
		}
		
		correccionNSS.setDesTipoNSSAclaracion(solicitud.getGridNSS().getData().get(0).getTipoCorreccion());
		
		certificacionNSS.setTipoRegularizacionNSS(tiposRegularizacionNSS);
		certificacionNSS.setTipoNSS(correccionNSS);
		return certificacionNSS;
	}
	
	//Tipo de Regularizacion de la solicitud
	private TipoRegularizacion getTipoRegularizacion(Solicitud solicitud){
		TipoRegularizacion tipo = new TipoRegularizacion();
		Long idTipoRegularizacion = solicitud.getTipoRegularizacion().getTipoRegularizacionId();
		tipo.setIdTipoRegularizacion(idTipoRegularizacion);
		return tipo;
	}
	
	public String contenidoCorreoAtencionAutorizador(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud, String autorizador, String origenMovimiento) {
		
		StringBuffer body= new StringBuffer();
		
		body.append(ENCABEZADO_CORREOS);
		
		logger.debug("---CDA--- correo autorizador atención folio {}",solicitud.getNoFolioSolicitud());
		
		body.append(MessageFormat.format(CONTENIDO_CORREO_ATENCION_AUTORIZADOR,
				new Object[] { solicitud.getNoFolioSolicitud(), 
				autorizador , 
				solicitud.getNoFolioSolicitud(), 
				((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getPersonaRENAPO().getCurp(),
						StringEscapeUtils.escapeHtml(((TramiteCorreccionCurp) solicitud.getTramites().get(0))
								.getPersonaRENAPO().getNombreCompleto()),
								((TramiteCorreccionCurp) solicitud.getTramites().get(0)).getListaNSS().get(0),
								 origenMovimiento }));
			
		body.append(PIE_PAGINA);
		
		return body.toString();
	}	

	
}
