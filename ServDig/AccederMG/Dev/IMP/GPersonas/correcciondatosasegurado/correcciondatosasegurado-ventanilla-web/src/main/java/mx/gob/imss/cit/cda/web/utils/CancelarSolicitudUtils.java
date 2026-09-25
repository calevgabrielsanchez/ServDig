package mx.gob.imss.cit.cda.web.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.RazonCancelacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Component
public class CancelarSolicitudUtils {

	/**
	 * Metodo para crear la solicitud de cancelacion 
	 * 
	 * @param solicitud
	 * @param sequimientoSolicitud
	 * @param fisica persona que cancelara la solicitud
	 * @return solicitud
	 */
	public Solicitud crearSolicitudCancelacion(Solicitud solicitud, SeguimientoSolicitud seguimientoSolicitud, Fisica fisica) {

		RazonCancelacion razonCancelacion = new RazonCancelacion();
		Fisica responsable = new Fisica();
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		List<Tramite> lstTramite = new ArrayList<Tramite>();
		
		for (Tramite tramitecda: solicitud.getTramites()){
			TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)tramitecda;
			
			solicitud.setSolicitudId(new Long(seguimientoSolicitud.getIdSolicitud()));
			razonCancelacion.setIdRazonCancelacion(RazonCancelacionEnum.INASISTENCIA.getId());
			razonCancelacion.setDescripcion(seguimientoSolicitud.getDetalle());
			solicitud.setRazonCancelacion(razonCancelacion);
			responsable.setIdPersona(fisica.getIdPersona());
			solicitud.setPersonaInteresada(responsable);
			estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
			solicitud.setEstadoSolicitud(estadoSolicitud);
			EstadoTramite estadoTramite = new EstadoTramite();
			estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo()));
			estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo());
			tramite.setEstadoTramite(estadoTramite);
			if(tramite.getObservacionesSubdelegacion()== null){				 
				 tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
			 }		 
		     ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion ();
			 obSubdelegacion.setDetalle(seguimientoSolicitud.getDetalle());
		     obSubdelegacion.setResumen(seguimientoSolicitud.getResumen());
		     obSubdelegacion.setFechaActualizacion(new Date());	     
		     obSubdelegacion.setUsuario(fisica.getCurp());
		     obSubdelegacion.setAsignado(seguimientoSolicitud.getCurpResponsable());
		     obSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
			 tramite.getObservacionesSubdelegacion().add(obSubdelegacion);
			 tramite.setTramiteId(new Long(seguimientoSolicitud.getIdTramite()));
			 
			 lstTramite.add(tramite);
			
		}

		solicitud.setTramites(lstTramite);
		return solicitud;
		
	}

}
