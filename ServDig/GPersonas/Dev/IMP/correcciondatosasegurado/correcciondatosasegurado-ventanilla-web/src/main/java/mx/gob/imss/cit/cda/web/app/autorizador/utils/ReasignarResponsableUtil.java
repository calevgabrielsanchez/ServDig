package mx.gob.imss.cit.cda.web.app.autorizador.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.web.app.responsable.model.ReasignacionSolicitud;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Component
public class ReasignarResponsableUtil<I> {
	
	private static String REASIGNADA = "Reasignada";
	private final Logger log = LoggerFactory.getLogger(ReasignarResponsableUtil.class);
	
	 public Solicitud crearSolicitudReasignar(Solicitud solicitud, UpdateEvent<ReasignacionSolicitud> requestUpdateEvent){
		 
		 log.debug("---CDA--- Responsbale {}",requestUpdateEvent.getData().getCurp());
		 
		 EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		 List<Tramite> lstTramite = new ArrayList<Tramite>();
		 TramiteCorreccionCurp  tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
		 Usuario usuario = new Usuario();
		 usuario.setCveIdUsuario(requestUpdateEvent.getData().getCurp());
		 usuario.setUsuario(requestUpdateEvent.getData().getCurp());
		 solicitud.setSolicitante(usuario);
		 solicitud.setSolicitudId(Long.parseLong(requestUpdateEvent.getData().getIdSolicitud()));
		 estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		 solicitud.setEstadoSolicitud(estadoSolicitud);
		 EstadoTramite estadoTramite = new EstadoTramite();
		 estadoTramite.setDescripcion(REASIGNADA.toUpperCase());
		 estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo());
		 tramite.setEstadoTramite(estadoTramite);
		 tramite.setTramiteId(Long.parseLong(requestUpdateEvent.getData().getIdTramite()));
		 tramite.setDetalleReasignacion(requestUpdateEvent.getData().getDetalle());
		 if(tramite.getObservacionesSubdelegacion()== null){				 
			 tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
		 }
	     ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion ();
		 obSubdelegacion.setDetalle(requestUpdateEvent.getData().getDetalle());
		 obSubdelegacion.setFechaActualizacion(new Date());
	     obSubdelegacion.setUsuario(requestUpdateEvent.getUserProfile().getUsuario());
	     obSubdelegacion.setAsignado(requestUpdateEvent.getData().getCurp());
	     obSubdelegacion.setCveEstado(EstadoNegocioEnum.REASIGNADA.getCodigo());
		 tramite.getObservacionesSubdelegacion().add(obSubdelegacion);
		 //tramite.setFechaRegistroActualizacion(solicitud.getFechaActualizacion());
		 lstTramite.add(tramite);
		 solicitud.setTramites(lstTramite);
		 log.debug("---CDA--- Id Solicitud{}  Id Tramite {} ",solicitud.getSolicitudId(), solicitud.getTramites().get(0).getTramiteId());
		 return solicitud;		 
	 }

}
