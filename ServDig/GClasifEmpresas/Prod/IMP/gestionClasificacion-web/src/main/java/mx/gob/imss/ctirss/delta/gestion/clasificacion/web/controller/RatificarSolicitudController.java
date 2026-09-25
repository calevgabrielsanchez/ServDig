/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:RatificarSolicitudController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:15/05/2012
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
import mx.gob.imss.ctirss.delta.exception.clasificacion.EstatusMovimientoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionPropuestaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Controller
@RequestMapping(value = "/analisis/ratificarSolicitud")
public class RatificarSolicitudController extends AbstractController {
	
	@Autowired
	DetalleSolicitudController detalleSolicitudController;

	@Autowired
	AnalisisServiceBusinessRemote analisisBusiness;
		
	@Autowired
	ClasificacionPropuestaServiceBusinessRemote clasificacionPropuestaBusiness;
	
	@RequestMapping(method = RequestMethod.GET)		
	public String ratificarAnalisis(@RequestParam String cveIdAnalisis,
			@RequestParam String cveIdSolicitud, @RequestParam String regPatronal, 
			@RequestParam String tipoPersona, @RequestParam String cveIdDelegacion, 
			@RequestParam String cveIdSubdelegacion, @RequestParam String cveIdFraccionAct, 
			@RequestParam String cveIdFraccionPro, @RequestParam String cveIdFraccionAnt, 
			@RequestParam String primaSRTAct, @RequestParam String primaSRTPro, 
			@RequestParam String primaSRTAnt, @RequestParam String comentarios, 
			@RequestParam Long cveIdPatronDictamen,
			HttpSession session, Model model, DefaultMessageContext messageContext) {
		
		SujetoObligado sujetoObligado = null;
	 	try {
			messageContext.setMessageSource(messageSource);
			//Se recupera el usuario logeado
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

	 		analisisBusiness.ratificarPendiente(dto);
	 		messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
	 	} catch (final EstatusMovimientoException e) {
	 		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final AnalisisNoEncontradoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar"));
			log.error(e.getMessage(), e);
		} catch (final ClasificacionException e) {
			if( e.getCodigo() != null && e.getCodigo().intValue() == Constantes.CODIGO_ERROR_LONGITUD)
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.comentarios"));
			else
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar"));
			log.error(e.getMessage(), e);
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar"));
			log.error(e.getMessage(), e);
		}
		model.addAttribute("messageContext", messageContext);
		
		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		return detalleSolicitudController.detalle(cveIdSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/dictamen")		
	public String ratificarAnalisisDictamen(@RequestParam String cveIdAnalisis,@RequestParam String cveIdSolicitud, @RequestParam String regPatronal, 
			@RequestParam String tipoPersona, @RequestParam String cveIdDelegacion, @RequestParam String cveIdSubdelegacion, @RequestParam String cveIdFraccionAct, @RequestParam String cveIdFraccionPro, @RequestParam String cveIdFraccionAnt, @RequestParam String primaSRTAct, @RequestParam String primaSRTPro, @RequestParam String primaSRTAnt, @RequestParam String comentarios, 
			@RequestParam Long cveIdPatronDictamen,
			HttpSession session, Model model, DefaultMessageContext messageContext) {
		SujetoObligado sujetoObligado = null;
		try {
			messageContext.setMessageSource(messageSource);
			//Se recupera el usuario logeado
			Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
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
			analisisBusiness.ratificarPendienteDictamen(dto);
			messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
		} catch (final EstatusMovimientoException e) {
	 		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final AnalisisNoEncontradoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar"));
			log.error(e.getMessage(), e);
		} catch (final ClasificacionException e) {
			if( e.getCodigo() != null && e.getCodigo().intValue() == Constantes.CODIGO_ERROR_LONGITUD)
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.comentarios"));
			else
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar"));
			log.error(e.getMessage(), e);
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar"));
			log.error(e.getMessage(), e);
		}
		model.addAttribute("messageContext", messageContext);
		
		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		return detalleSolicitudController.detalle(cveIdSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
		
	}

	@RequestMapping(value = "/autorizar/dictamen", method = RequestMethod.GET)
	public String autorizarRatificacionDictamen(@RequestParam String cveIdAnalisis,
			@RequestParam String cveIdSolicitud, @RequestParam String regPatronal, 
			@RequestParam String tipoPersona, @RequestParam String cveIdDelegacion, 
			@RequestParam String cveIdSubdelegacion, @RequestParam String cveIdFraccionAct, 
			@RequestParam String cveIdFraccionPro, @RequestParam String cveIdFraccionAnt, 
			@RequestParam String primaSRTAct, @RequestParam String primaSRTPro, 
			@RequestParam String primaSRTAnt,
			@RequestParam Long cveIdPatronDictamen, HttpSession session, Model model,
			DefaultMessageContext messageContext) {
		
		
		SujetoObligado sujetoObligado = null;
		
		try {
			messageContext.setMessageSource(messageSource);
			
//			Se recupera el usuario logeado
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			
//			Se valida si el usuario cuentra con permisos
			int idRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
			if(!perfilUsuarioValido(idRol)) {
				log.error("::: El usuario "+usuario.getUsuario()+" no tiene un perfil valido para ejecutar esta acción, rol "+idRol);
				return "internalError";
			}
			
			ClasificacionDTO dto = new ClasificacionDTO();
			dto.setRegPatronal(regPatronal);
			dto.setTipoPersona(tipoPersona);
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
		 	dto.setCveIdSolicitud(cveIdSolicitud);
		 	analisisBusiness.autorizarRatificacion(dto);
		 	messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
		}catch (final EstatusMovimientoException e) {
	 		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final AnalisisNoEncontradoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.autorizacion"));
			log.error(e.getMessage(), e);
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.autorizacion"));
			log.error(e.getMessage(), e);
		}
		
		model.addAttribute("messageContext", messageContext);
		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		return detalleSolicitudController.detalle(cveIdSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
	}
	
	@RequestMapping(value = "/autorizar", method = RequestMethod.GET)
	public String autorizarRatificacion(@RequestParam String cveIdAnalisis,
			@RequestParam String cveIdSolicitud, @RequestParam String regPatronal, 
			@RequestParam String tipoPersona, @RequestParam String cveIdDelegacion, 
			@RequestParam String cveIdSubdelegacion, @RequestParam String cveIdFraccionAct, 
			@RequestParam String cveIdFraccionPro, @RequestParam String cveIdFraccionAnt, 
			@RequestParam String primaSRTAct, @RequestParam String primaSRTPro, 
			@RequestParam String primaSRTAnt,
			@RequestParam Long cveIdPatronDictamen, HttpSession session, Model model,
			DefaultMessageContext messageContext) {
		
		SujetoObligado sujetoObligado = null;
	 	try {
			messageContext.setMessageSource(messageSource);
			//Se recupera el usuario logeado
		 	Usuario usuario = (Usuario)session.getAttribute("usuario");
		 	//S valida Usuario
		 	int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		 	if (!perfilUsuarioValido(iRol)) {
				log.error("::: El usuario "+usuario.getUsuario()+" no tiene un perfil valido para ejecutar esta acción, rol: " + iRol);
				return "internalError";
			}
			ClasificacionDTO dto = new ClasificacionDTO();
			dto.setRegPatronal(regPatronal);
			dto.setTipoPersona(tipoPersona);
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
		 	dto.setCveIdSolicitud(cveIdSolicitud);

	 		analisisBusiness.autorizarRatificacion(dto);
	 		messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
	 	} catch (final EstatusMovimientoException e) {
	 		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final AnalisisNoEncontradoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.autorizacion"));
			log.error(e.getMessage(), e);
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.ratificar.autorizacion"));
			log.error(e.getMessage(), e);
		}
	 	
		model.addAttribute("messageContext", messageContext);
		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		
		return detalleSolicitudController.detalle(cveIdSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
	}
	
	private boolean perfilUsuarioValido(int iRol){
		if (iRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue()
				) {
			return true;
		}
		return false;
	}
	
	
}
