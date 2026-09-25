package mx.gob.imss.cit.cda.web.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.cit.cda.core.events.UpdateEvent;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.RazonCancelacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.springframework.stereotype.Component;

@Component
public class SolicitudUpdateUtils {

	/**
	 * Metodo para crear la solicitud de rechazo 
	 * 
	 * @param solicitud
	 * @param sequimientoSolicitud
	 * @param fisica persona que cancelara la solicitud
	 * @return solicitud
	 */
	public Solicitud rechazarSolicitud(Solicitud solicitud,SeguimientoSolicitud seguimientoSolicitud, Fisica fisica) {
		
		RazonCancelacion razonCancelacion = new RazonCancelacion();
		Fisica responsable = new Fisica();
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		List<Tramite> lstTramite = new ArrayList<Tramite>();
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);

		solicitud.setSolicitudId(new Long(seguimientoSolicitud.getIdSolicitud()));
		//solicitud.setObservacion(seguimientoSolicitud.getResumen());
		razonCancelacion.setIdRazonCancelacion(RazonCancelacionEnum.INASISTENCIA.getId());
		razonCancelacion.setDescripcion(seguimientoSolicitud.getDetalle());
		solicitud.setRazonCancelacion(razonCancelacion);
		responsable.setIdPersona(fisica.getIdPersona());
		solicitud.setPersonaInteresada(responsable);
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.RECHAZADO.getCodigo()));
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.RECHAZADO.getCodigo());
		tramite.setEstadoTramite(estadoTramite);
		tramite.setTramiteId(new Long(seguimientoSolicitud.getIdTramite()));
		if(tramite.getObservacionesSubdelegacion()== null){
			tramite.setObservacionesSubdelegacion(new  ArrayList<ObservacionesSubdelegacion>());
		}
		ObservacionesSubdelegacion observacionesSubdelegacion= new ObservacionesSubdelegacion();
		observacionesSubdelegacion.setDetalle(seguimientoSolicitud.getDetalle());
		observacionesSubdelegacion.setResumen(seguimientoSolicitud.getResumen());
		observacionesSubdelegacion.setIdTarea(seguimientoSolicitud.getIdTarea());	     
		observacionesSubdelegacion.setFechaActualizacion(new Date());
	    observacionesSubdelegacion.setUsuario(fisica.getCurp());
		observacionesSubdelegacion.setAsignado(seguimientoSolicitud.getResponsable());
		observacionesSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
		tramite.getObservacionesSubdelegacion().add(observacionesSubdelegacion);
		lstTramite.add(tramite);
		solicitud.setTramites(lstTramite);

		return solicitud;
	}
	
	/**
	 * Metodo para crear la solicitud en solicitar datos adicionales 
	 * 
	 * @param solicitud
	 * @param requestUpdateEvent
	 * @return solicitud
	 */
	public Solicitud solicitarInformacion(Solicitud solicitud,UpdateEvent<SeguimientoSolicitud> requestUpdateEvent) {
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		List<Tramite> lstTramite = new ArrayList<Tramite>();
		
		for (Tramite tramitecda: solicitud.getTramites()){
			    TramiteCorreccionCurp tramite = (TramiteCorreccionCurp)tramitecda;
			
				Usuario usuarioSolicitante = new Usuario();
				usuarioSolicitante.setCveIdUsuario(requestUpdateEvent.getData().getCurpResponsable());
				usuarioSolicitante.setUsuario(requestUpdateEvent.getData().getCurpResponsable());
				
				solicitud.setSolicitante(usuarioSolicitante);
				
				estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
				solicitud.setEstadoSolicitud(estadoSolicitud);
				EstadoTramite estadoTramite = new EstadoTramite();
				estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo()));
				estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo());
				tramite.setEstadoTramite(estadoTramite);	
				if(tramite.getObservacionesSubdelegacion()== null){
					tramite.setObservacionesSubdelegacion(new  ArrayList<ObservacionesSubdelegacion>());
				}
				ObservacionesSubdelegacion observacionesSubdelegacion= new ObservacionesSubdelegacion();
				observacionesSubdelegacion.setDetalle(requestUpdateEvent.getData().getDetalle());
				observacionesSubdelegacion.setResumen(requestUpdateEvent.getData().getResumen());
				observacionesSubdelegacion.setIdTarea(requestUpdateEvent.getData().getIdTarea());
				observacionesSubdelegacion.setFechaActualizacion(new Date());
				observacionesSubdelegacion.setUsuario(requestUpdateEvent.getUserProfile().getUsuario());
				observacionesSubdelegacion.setAsignado(requestUpdateEvent.getData().getCurpResponsable());
				observacionesSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
				tramite.getObservacionesSubdelegacion().add(observacionesSubdelegacion);
			
			    lstTramite.add(tramite);
			
		}
		
		solicitud.setTramites(lstTramite);

		return solicitud;
	}
	
	/**
	 * Metodo para agregar correo electronico del asegurado en solicitud de datos adicionales  
	 * 
	 * @param solicitud
	 * @param requestUpdateEvent
	 * @return solicitud
	 */
	public Solicitud modificarCorreoAsegurado(Solicitud solicitud,UpdateEvent<SeguimientoSolicitud> requestUpdateEvent) {
		List<Tramite> tramites = new ArrayList<Tramite>();
		
		for (Tramite tramite: solicitud.getTramites()){
			
			TramiteCorreccionCurp tramiteCda = (TramiteCorreccionCurp)tramite;
			
			/*Tramite principal */
			if (tramiteCda.getPersonaRENAPO()!=null){
				
				List<MedioContacto> medios = tramiteCda.getPersonaRENAPO().getMediosContacto();
				
				/*Si trae medios de contactos previamente*/
				if (medios!=null){
					
					for(int i =0;i<medios.size();i++) {
						
						if(medios.get(i) instanceof CorreoElectronico){
							CorreoElectronico nuevo = new CorreoElectronico();
							nuevo.setCorreo(requestUpdateEvent.getData().getCorreoAsegurado());
							medios.remove(medios.get(i));
							medios.add(nuevo);
						}
						
					}
					
				/*Si no tiene medios de contactos */	
				}else{
					
					medios = new ArrayList<MedioContacto>();
					MedioContacto medioNuevo = new CorreoElectronico(requestUpdateEvent.getData().getCorreoAsegurado());
					medios.add(medioNuevo);
					
				}
				
				tramiteCda.getPersonaRENAPO().setMediosContacto(medios);
				
				/*Correo Electronico de la Persona Renapo (Asegurado)*/
				CorreoElectronico correo = tramiteCda.getPersonaRENAPO().getCorreoElectronico();

				if(correo==null){
					correo = new CorreoElectronico();
				}
				
				correo.setCorreo(requestUpdateEvent.getData().getCorreoAsegurado());
				tramiteCda.getPersonaRENAPO().setCorreoElectronico(correo);
				
		    }
			tramites.add(tramiteCda);
			
		}
		
		solicitud.setTramites(tramites);
		
		return solicitud;
	}

}
