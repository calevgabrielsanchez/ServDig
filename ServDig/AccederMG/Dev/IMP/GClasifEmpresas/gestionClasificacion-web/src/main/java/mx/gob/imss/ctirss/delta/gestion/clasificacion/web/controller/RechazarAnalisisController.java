/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonz�lez
 *  @Proyecto: delta
 *  @Archivo:RechazarAnalisisController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:30/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mx.gob.imss.ctirss.delta.exception.clasificacion.AnalisisNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.EstatusMovimientoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionPropuestaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 * @author Jonathan Sanchez Montiel
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 17/01/2012
 */
@Controller
@RequestMapping(value = "/analisis/rechazarAnalisis")
public class RechazarAnalisisController extends AbstractController {
	
	@Autowired
	DetalleSolicitudController detalleSolicitudController;

	@Autowired
	AnalisisServiceBusinessRemote analisisBusiness;
	
	@Autowired
	ClasificacionPropuestaServiceBusinessRemote clasificacionPropuestaBusiness;
	
	@Autowired
	ClasificacionServiceBusinessRemote clasificacionServiceBusiness;
	
	@Autowired
	SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	@Autowired
	DatosClemServiceBusinessRemote datosClemBusiness;
	@RequestMapping(value = "/dictamen/rechazarRatificacionPendAut", method = RequestMethod.GET)
	public String rechazaRatificacionDictamenPendiente(@RequestParam String cveIdSolicitud,
			@RequestParam String cveIdAnalisis, @RequestParam String comentarios,
			@RequestParam String regPatronal, @RequestParam String tipoPersona,
			@RequestParam String cveIdDelegacion, @RequestParam String cveIdSubdelegacion,
			@RequestParam String cveIdFraccionAct, @RequestParam String cveIdFraccionPro,
			@RequestParam String cveIdFraccionAnt, @RequestParam String primaSRTAct,
			@RequestParam String primaSRTPro, @RequestParam String primaSRTAnt,
			@RequestParam Long cveIdPatronDictamen,
			HttpSession session, Model model, DefaultMessageContext messageContext) {
		
		SujetoObligado sujetoObligado = null;
		
		try {
			messageContext.setMessageSource(messageSource);
			//Obtener el usuario en la sesión
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			ClasificacionDTO dto = new ClasificacionDTO();
			dto.setUsuario(usuario);
			dto.setCveIdAnalisis(cveIdAnalisis);
		 	dto.setCveIdDelegacion(cveIdDelegacion);
		 	dto.setCveIdSubdelegacion(cveIdSubdelegacion);
		 	dto.setCveIdFraccionAct(cveIdFraccionAct);
		 	dto.setCveIdFraccionPro(cveIdFraccionPro);
		 	dto.setCveIdFraccionAnt(cveIdFraccionAnt);
		 	dto.setPrimaSRTAct(primaSRTAct);
		 	dto.setPrimaSRTPro(primaSRTPro);
		 	dto.setPrimaSRTAnt(primaSRTAnt);
		 	dto.setComentarios(comentarios);
		 	dto.setCveIdSolicitud(cveIdSolicitud);
			
		 	analisisBusiness.rechazarRatificacionDictamenPendAut(dto);
			messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
		}
		catch (final EstatusMovimientoException e) {
    		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final AnalisisNoEncontradoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo"));
			log.error(e.getMessage(), e);
		} catch (final NumberFormatException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo"));
			log.error(e.getMessage(), e);
		} catch (final ClasificacionException e) {
			
			if(e.getCodigo() != null && e.getCodigo().intValue() == Constantes.CODIGO_ERROR_LONGITUD)
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.comentarios"));
			else
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar"));
			
			log.error(e.getMessage(), e);

		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo"));
			log.error(e.getMessage(), e);
		}		
		model.addAttribute("messageContext", messageContext);
		
		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		
		return detalleSolicitudController.detalle(cveIdSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
	}
	
	
	@RequestMapping(value = "/rechazarRatificacionPendAut", method = RequestMethod.GET)
	public String rechazaRatificacionPendiente(@RequestParam String cveIdSolicitud,
			@RequestParam String cveIdAnalisis, @RequestParam String comentarios,
			@RequestParam String regPatronal, @RequestParam String tipoPersona,
			@RequestParam String cveIdDelegacion, @RequestParam String cveIdSubdelegacion,
			@RequestParam String cveIdFraccionAct, @RequestParam String cveIdFraccionPro,
			@RequestParam String cveIdFraccionAnt, @RequestParam String primaSRTAct,
			@RequestParam String primaSRTPro, @RequestParam String primaSRTAnt,
			@RequestParam Long cveIdPatronDictamen,
			HttpSession session, Model model, DefaultMessageContext messageContext) {

		SujetoObligado sujetoObligado = null;
	    try {
			messageContext.setMessageSource(messageSource);
			/*obtener los objetos de la sesion*/
	    	Usuario usuario = (Usuario)session.getAttribute("usuario");
	    	
			ClasificacionDTO dto = new ClasificacionDTO();
			dto.setUsuario(usuario);
		 	dto.setCveIdAnalisis(cveIdAnalisis);
		 	dto.setCveIdDelegacion(cveIdDelegacion);
		 	dto.setCveIdSubdelegacion(cveIdSubdelegacion);
		 	dto.setCveIdFraccionAct(cveIdFraccionAct);
		 	dto.setCveIdFraccionPro(cveIdFraccionPro);
		 	dto.setCveIdFraccionAnt(cveIdFraccionAnt);
		 	dto.setPrimaSRTAct(primaSRTAct);
		 	dto.setPrimaSRTPro(primaSRTPro);
		 	dto.setPrimaSRTAnt(primaSRTAnt);
		 	dto.setComentarios(comentarios);
		 	dto.setCveIdSolicitud(cveIdSolicitud);

	    	analisisBusiness.rechazarRatificacionPendAut(dto);
	    	messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
    	} catch (final EstatusMovimientoException e) {
    		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final AnalisisNoEncontradoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo"));
			log.error(e.getMessage(), e);
		} catch (final NumberFormatException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo"));
			log.error(e.getMessage(), e);
		} catch (final ClasificacionException e) {
			
			if(e.getCodigo() != null && e.getCodigo().intValue() == Constantes.CODIGO_ERROR_LONGITUD)
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.comentarios"));
			else
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar"));
			
			log.error(e.getMessage(), e);

		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo"));
			log.error(e.getMessage(), e);
		}		
		model.addAttribute("messageContext", messageContext);
		
		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		return detalleSolicitudController.detalle(cveIdSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
		//return detalleSolicitudController.detalleSolicitud(cveIdSolicitud, regPatronal, tipoPersona, session, model);
	}

	@RequestMapping(value = "/rechazarRectificacionPendAut", method = RequestMethod.GET)
	public String rechazarRectificacionPendAut(@RequestParam String cveIdSolicitud,
		   @RequestParam String cveIdAnalisis, @RequestParam String comentarios, 
		   @RequestParam String regPatronal, @RequestParam String tipoPersona, 
		   @RequestParam String cveIdDelegacion, @RequestParam String cveIdSubdelegacion,
		   @RequestParam String cveIdFraccionAct, @RequestParam String cveIdFraccionPro,
		   @RequestParam String cveIdFraccionAnt, @RequestParam String primaSRTAct,
		   @RequestParam String primaSRTPro, @RequestParam String primaSRTAnt,
		   @RequestParam Long cveIdPatronDictamen,
		   HttpSession session, Model model, DefaultMessageContext messageContext) {
		SujetoObligado sujetoObligado = null;
		
    	try {
    		messageContext.setMessageSource(messageSource);
    		/*obtener los objetos de la sesion*/
        	Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);
        	
        	ClasificacionDTO dto = new ClasificacionDTO();
    		dto.setUsuario(usuario);
    	 	dto.setCveIdAnalisis(cveIdAnalisis);
    	 	dto.setCveIdDelegacion(cveIdDelegacion);
    	 	dto.setCveIdSubdelegacion(cveIdSubdelegacion);
    	 	dto.setCveIdFraccionAct(cveIdFraccionAct);
    	 	dto.setCveIdFraccionPro(cveIdFraccionPro);
    	 	dto.setCveIdFraccionAnt(cveIdFraccionAnt);
    	 	dto.setPrimaSRTAct(primaSRTAct);
    	 	dto.setPrimaSRTPro(primaSRTPro);
    	 	dto.setPrimaSRTAnt(primaSRTAnt);
    	 	dto.setComentarios(comentarios);
    	 	dto.setCveIdSolicitud(cveIdSolicitud);	 	
    	 	dto.setTipoPersona(tipoPersona);
    	 	dto.setRegPatronal(regPatronal);

    		analisisBusiness.rechazarRectificacionPendAut(dto);
			messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
		} catch (final EstatusMovimientoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final AnalisisNoEncontradoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificacion.rechazo"));
			log.error(e.getMessage(), e);
		} catch (final NumberFormatException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificacion.rechazo"));
			log.error(e.getMessage(), e);
		} catch (final ClasificacionException e) {
			if( e.getCodigo() != null && e.getCodigo().intValue() == Constantes.CODIGO_ERROR_LONGITUD)
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.comentarios"));
			else
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificacion.rechazo"));

			log.error(e.getMessage(), e);
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificacion.rechazo"));
			log.error(e.getMessage(), e);
		}
		model.addAttribute("messageContext", messageContext);
		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		return detalleSolicitudController.detalle(cveIdSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
		//return detalleSolicitudController.detalleSolicitud(cveIdSolicitud, regPatronal, tipoPersona, session, model);
	}	

	@RequestMapping(value = "/rechazarRectificacionDictamenPendAut", method = RequestMethod.GET)
	public String rechazarRectificacionDictamenPendAut(@RequestParam String cveIdSolicitud,
		   @RequestParam String cveIdAnalisis, @RequestParam String comentarios, 
		   @RequestParam String regPatronal, @RequestParam String tipoPersona, 
		   @RequestParam String cveIdDelegacion, @RequestParam String cveIdSubdelegacion,
		   @RequestParam String cveIdFraccionAct, @RequestParam String cveIdFraccionPro,
		   @RequestParam String cveIdFraccionAnt, @RequestParam String primaSRTAct,
		   @RequestParam String primaSRTPro, @RequestParam String primaSRTAnt,
		   @RequestParam Long cveIdPatronDictamen,
		   HttpSession session, Model model, DefaultMessageContext messageContext) {
		SujetoObligado sujetoObligado = null;
		
    	try {
    		messageContext.setMessageSource(messageSource);
    		/*obtener los objetos de la sesion*/
        	Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);
        	
        	ClasificacionDTO dto = new ClasificacionDTO();
    		dto.setUsuario(usuario);
    	 	dto.setCveIdAnalisis(cveIdAnalisis);
    	 	dto.setCveIdDelegacion(cveIdDelegacion);
    	 	dto.setCveIdSubdelegacion(cveIdSubdelegacion);
    	 	dto.setCveIdFraccionAct(cveIdFraccionAct);
    	 	dto.setCveIdFraccionPro(cveIdFraccionPro);
    	 	dto.setCveIdFraccionAnt(cveIdFraccionAnt);
    	 	dto.setPrimaSRTAct(primaSRTAct);
    	 	dto.setPrimaSRTPro(primaSRTPro);
    	 	dto.setPrimaSRTAnt(primaSRTAnt);
    	 	dto.setComentarios(comentarios);
    	 	dto.setCveIdSolicitud(cveIdSolicitud);	 	
    	 	dto.setTipoPersona(tipoPersona);
    	 	dto.setRegPatronal(regPatronal);

    		analisisBusiness.rechazarRectificacionPendAut(dto);
			messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
		} catch (final EstatusMovimientoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final AnalisisNoEncontradoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificacion.rechazo"));
			log.error(e.getMessage(), e);
		} catch (final NumberFormatException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificacion.rechazo"));
			log.error(e.getMessage(), e);
		} catch (final ClasificacionException e) {
			if( e.getCodigo() != null && e.getCodigo().intValue() == Constantes.CODIGO_ERROR_LONGITUD)
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.comentarios"));
			else
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificacion.rechazo"));

			log.error(e.getMessage(), e);
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificacion.rechazo"));
			log.error(e.getMessage(), e);
		}
		model.addAttribute("messageContext", messageContext);
		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		return detalleSolicitudController.detalle(cveIdSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
		//return detalleSolicitudController.detalleSolicitud(cveIdSolicitud, regPatronal, tipoPersona, session, model);
	}	
	@RequestMapping(value = "/dictamen/rechazarAutorizacion", method = RequestMethod.GET)
	public String rechazaAutorizacionDictamen(@RequestParam String cveIdSolicitud,
			@RequestParam String cveIdAnalisis, @RequestParam String comentarios,
			@RequestParam String idEstatus, @RequestParam String regPatronal,
			@RequestParam String tipoPersona, @RequestParam String cveIdDelegacion,
			@RequestParam String cveIdSubdelegacion, @RequestParam String cveIdFraccionAct,
			@RequestParam String cveIdFraccionPro, @RequestParam String cveIdFraccionAnt,
			@RequestParam String primaSRTAct, @RequestParam String primaSRTPro,
			@RequestParam String primaSRTAnt, @RequestParam String tTramite,
			@RequestParam Long cveIdPatronDictamen,
			HttpSession session, Model model, DefaultMessageContext messageContext) {
		
		log.debug("::: ESTOY RECHAZANDO LA AUTORIZACION DE DicTAMEN");
		SujetoObligado sujetoObligado = null;
		
		try {
			messageContext.setMessageSource(messageSource);
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
			ClasificacionDTO dto = new ClasificacionDTO();
			dto.setUsuario(usuario);
		 	dto.setCveIdAnalisis(cveIdAnalisis);
		 	dto.setCveIdDelegacion(cveIdDelegacion);
		 	dto.setCveIdSubdelegacion(cveIdSubdelegacion);
		 	dto.setCveIdFraccionAct(cveIdFraccionAct);
		 	dto.setCveIdFraccionPro(cveIdFraccionPro);
		 	dto.setCveIdFraccionAnt(cveIdFraccionAnt);
		 	dto.setPrimaSRTAct(primaSRTAct);
		 	dto.setPrimaSRTPro(primaSRTPro);
		 	dto.setPrimaSRTAnt(primaSRTAnt);
		 	dto.setComentarios(comentarios);
		 	dto.setIdEstatus(idEstatus);
		 	dto.setTipoPersona(tipoPersona);
		 	dto.setRegPatronal(regPatronal);
		 	dto.setCveIdSolicitud(cveIdSolicitud);
		 	dto.setTTramite(tTramite);
		 	
		 
		 	analisisBusiness.rechazarAutorizacion(dto, cveIdPatronDictamen, true);
			messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
		}catch (final EstatusMovimientoException e) {
	    	messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final DatosClemException e) {
	    	messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo.autorizacion"));
			log.error(e.getMessage(), e);
		} catch (final AnalisisNoEncontradoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo.autorizacion"));
			log.error(e.getMessage(), e);
		}  catch (final NumberFormatException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo.autorizacion"));
			log.error(e.getMessage(), e);
		} catch (final ClasificacionException e) {
			if( e.getCodigo() != null && e.getCodigo().intValue() == Constantes.CODIGO_ERROR_LONGITUD)
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.comentarios"));
			else
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo.autorizacion"));

			log.error(e.getMessage(), e);
		}catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo.autorizacion"));
			log.error(e.getMessage(), e);
		}
		model.addAttribute("messageContext", messageContext);

		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		
		
		return detalleSolicitudController.detalle(cveIdSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
	}
	
	@RequestMapping(value = "/rechazarAutorizacion", method = RequestMethod.GET)
	public String rechazaAutorizacion(@RequestParam String cveIdSolicitud,
			@RequestParam String cveIdAnalisis, @RequestParam String comentarios,
			@RequestParam String idEstatus, @RequestParam String regPatronal,
			@RequestParam String tipoPersona, @RequestParam String cveIdDelegacion,
			@RequestParam String cveIdSubdelegacion, @RequestParam String cveIdFraccionAct,
			@RequestParam String cveIdFraccionPro, @RequestParam String cveIdFraccionAnt,
			@RequestParam String primaSRTAct, @RequestParam String primaSRTPro,
			@RequestParam String primaSRTAnt, @RequestParam String tTramite,
			@RequestParam Long cveIdPatronDictamen,
			HttpSession session, Model model, DefaultMessageContext messageContext) {

		log.debug("::: Estoy en rechazarAutorizacion");
		SujetoObligado sujetoObligado = null;
		
		try {
			messageContext.setMessageSource(messageSource);
			/*obtener los objetos de la sesion*/
	    	Usuario usuario = (Usuario)session.getAttribute("usuario");
	    	
	    	ClasificacionDTO dto = new ClasificacionDTO();
			dto.setUsuario(usuario);
		 	dto.setCveIdAnalisis(cveIdAnalisis);
		 	dto.setCveIdDelegacion(cveIdDelegacion);
		 	dto.setCveIdSubdelegacion(cveIdSubdelegacion);
		 	dto.setCveIdFraccionAct(cveIdFraccionAct);
		 	dto.setCveIdFraccionPro(cveIdFraccionPro);
		 	dto.setCveIdFraccionAnt(cveIdFraccionAnt);
		 	dto.setPrimaSRTAct(primaSRTAct);
		 	dto.setPrimaSRTPro(primaSRTPro);
		 	dto.setPrimaSRTAnt(primaSRTAnt);
		 	dto.setComentarios(comentarios);
		 	dto.setIdEstatus(idEstatus);
		 	dto.setTipoPersona(tipoPersona);
		 	dto.setRegPatronal(regPatronal);
		 	dto.setCveIdSolicitud(cveIdSolicitud);
		 	dto.setTTramite(tTramite);

			analisisBusiness.rechazarAutorizacion(dto, cveIdPatronDictamen, false);
			messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
	    } catch (final EstatusMovimientoException e) {
	    	messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final DatosClemException e) {
	    	messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo.autorizacion"));
			log.error(e.getMessage(), e);
		} catch (final AnalisisNoEncontradoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo.autorizacion"));
			log.error(e.getMessage(), e);
		}  catch (final NumberFormatException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo.autorizacion"));
			log.error(e.getMessage(), e);
		} catch (final ClasificacionException e) {
			if( e.getCodigo() != null && e.getCodigo().intValue() == Constantes.CODIGO_ERROR_LONGITUD)
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.comentarios"));
			else
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo.autorizacion"));

			log.error(e.getMessage(), e);
		}catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.rechazo.autorizacion"));
			log.error(e.getMessage(), e);
		}
		model.addAttribute("messageContext", messageContext);

		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		return detalleSolicitudController.detalle(cveIdSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
		
		//return detalleSolicitudController.detalleSolicitud(cveIdSolicitud, regPatronal, tipoPersona, session, model);
	}	

}
