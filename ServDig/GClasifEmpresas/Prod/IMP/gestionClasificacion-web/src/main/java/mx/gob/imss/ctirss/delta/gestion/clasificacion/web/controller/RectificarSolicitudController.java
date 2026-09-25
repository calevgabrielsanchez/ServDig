/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hector Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:RectificarSolicitudController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:30/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import static mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles.getFechaActual;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClemCaracterException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.EstatusMovimientoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.GCESujetoObligadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionPropuestaDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ValidaClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionPropuestaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.rectificacion.RectificacionBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.web.validator.DatosClemValidator;

/**
 * @author Héctor Lara A. Instituto Mexicano del Seguro Social
 */

@Controller
@RequestMapping(value = "/rectificacion/{cveIdAnalisis}")
public class RectificarSolicitudController extends AbstractController {

	@Autowired
	private RectificacionBusinessRemote rectificacionBusiness;

	@Autowired
	private DetalleSolicitudController detalleSolicitudController;
	
//	@Autowired
//	private GCESujetoObligadoServiceBusinessRemote gceSujetoObligadoServiceBusiness;
	
	@Autowired
	private ClasificacionServiceBusinessRemote clasificacionServiceBusiness;
		
	@Autowired
	private ValidaClasificacionServiceBusinessRemote validaClasificacionServiceBusiness;
	
	@Autowired
	private DatosClemServiceBusinessRemote datosClemBusiness;
	
	@Autowired
	private ClasificacionPropuestaServiceBusinessRemote clasificacionPropuestaBusiness;
		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// METODOS PARA RECTIFICAR UN RP
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/****
	 * Método para iniciar el flujo de rectificación
	 * 
	 * @throws Exception
	 ****/
	@RequestMapping(value = "/rectificarMovimiento", method = RequestMethod.POST)
	public ModelAndView rectificarMovimiento(
			@PathVariable String cveIdAnalisis,
			@RequestParam String regPatronal, @RequestParam String rfc,
			@RequestParam String popUp, @RequestParam String cveIdDelegacion,
			@RequestParam String cveIdSubdelegacion,
			@RequestParam String cveIdFraccionAct,
			@RequestParam String cveIdFraccionPro,
			@RequestParam String cveIdFraccionAnt,
			@RequestParam String primaSRTAct, @RequestParam String primaSRTPro,
			@RequestParam String primaSRTAnt,
			@RequestParam Long tipoTramite,
			@RequestParam Long cveIdPatronDictamen, @RequestParam String cveIdClaseAct, HttpSession session) {
		ModelAndView model = new ModelAndView();
		model.addObject("cveIdAnalisis", cveIdAnalisis);
		session.setAttribute("regPatronal", regPatronal);
		session.setAttribute("rfc", rfc);
		session.setAttribute("popUp", 1);
		model.addObject("cveIdDelegacion", cveIdDelegacion);
		model.addObject("cveIdSubdelegacion", cveIdSubdelegacion);
		model.addObject("cveIdFraccionAct", cveIdFraccionAct);
		model.addObject("cveIdFraccionPro", cveIdFraccionPro);
		model.addObject("cveIdFraccionAnt", cveIdFraccionAnt);
		model.addObject("primaSRTAct", primaSRTAct);
		model.addObject("primaSRTPro", primaSRTPro);
		model.addObject("primaSRTAnt", primaSRTAnt);
		model.addObject("tipoTramite", tipoTramite);
		model.addObject("cveIdPatronDictamen", cveIdPatronDictamen);
		model.addObject("cveIdClaseAct",cveIdClaseAct);
		model.setViewName("rectificacionMovimiento");
		return model;
	}

	@RequestMapping(value = "/rectificarMovimiento/dictamen", method = RequestMethod.POST)
	public ModelAndView rectificarMovimientoDictamen(
			@PathVariable String cveIdAnalisis,
			@RequestParam String regPatronal, @RequestParam String rfc,
			@RequestParam String popUp, @RequestParam String cveIdDelegacion,
			@RequestParam String cveIdSubdelegacion,
			@RequestParam String cveIdFraccionAct,
			@RequestParam String cveIdFraccionPro,
			@RequestParam String cveIdFraccionAnt,
			@RequestParam String primaSRTAct, @RequestParam String primaSRTPro,
			@RequestParam String primaSRTAnt,
			@RequestParam Long cveIdPatronDictamen, HttpSession session) {
		ModelAndView model = new ModelAndView();
		model.addObject("cveIdAnalisis", cveIdAnalisis);
		session.setAttribute("regPatronal", regPatronal);
		session.setAttribute("rfc", rfc);
		session.setAttribute("popUp", 1);
		model.addObject("cveIdDelegacion", cveIdDelegacion);
		model.addObject("cveIdSubdelegacion", cveIdSubdelegacion);
		model.addObject("cveIdFraccionAct", cveIdFraccionAct);
		model.addObject("cveIdFraccionPro", cveIdFraccionPro);
		model.addObject("cveIdFraccionAnt", cveIdFraccionAnt);
		model.addObject("primaSRTAct", primaSRTAct);
		model.addObject("primaSRTPro", primaSRTPro);
		model.addObject("primaSRTAnt", primaSRTAnt);
		model.addObject("cveIdPatronDictamen", cveIdPatronDictamen);
		model.setViewName("rectificacionMovimientoDictamen");
		return model;
	}
	/****
	 * Obtiene la clasisificacion seleccionada desde el sistema CLASIFICADOR
	 * y continua con el flujo de la rectificacion
	 * 
	 * @throws Exception
	 ****/
	@RequestMapping(value = "/get/clasificacion", method = RequestMethod.GET)
	public @ResponseBody
	Clasificacion clasificacion(@PathVariable String cveIdAnalisis,
			@RequestParam String cveIdDivision,
			@RequestParam String cveIdGrupo,
			@RequestParam String cveIdFraccion,
            @RequestParam String clase,
			HttpSession session) {
		
		log.debug("Obteniendo la clasificacion propuesta [" + cveIdDivision + cveIdGrupo + cveIdFraccion + " ]");
		Clasificacion clasif = new Clasificacion();
		Division division = new Division();
		Grupo grupo = new Grupo();
		Fraccion fraccion = new Fraccion();
        Clase claseObject = new Clase();
		
		try {
	        claseObject.setDescripcion(clase);
			division.setId(Long.parseLong(cveIdDivision));
			grupo.setId(Long.parseLong(cveIdGrupo));
			fraccion.setId(Long.parseLong(cveIdFraccion));
			fraccion.setGrupo(grupo);
			fraccion.getGrupo().setDivision(division);
	        fraccion.setClase(claseObject);
			clasif.setFraccion(fraccion);

			//servicio de gestion patronal para obtener la clasificacion equivalente - JSM
			clasif = clasificacionServiceBusiness.obtenerClasificacionEquivalente(clasif);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clasif;
	}

	/****
	 * Método que actualiza el estatus a pendiente de autorizar por rectificación
	 * 
	 * @throws Exception
	 ****/
	@RequestMapping(value = "/rectificadoPendiente", method = RequestMethod.POST)
	public String actualizarStatus(@PathVariable String cveIdAnalisis,
			@RequestParam String idTipoPersona, @RequestParam String regPatron,
			@RequestParam String indRegPatClase,
			@RequestParam String cveIdDivision,
			@RequestParam String cveIdGrupo,
			@RequestParam String cveIdFraccion, @RequestParam String clase,
			@RequestParam String actividadDetectada,
			@RequestParam String comentarios,
			@RequestParam String cveIdDelegacion,
			@RequestParam String cveIdSubdelegacion,
			@RequestParam String cveIdFraccionAct,
			@RequestParam String cveIdFraccionAnt,
			@RequestParam(required=false) String primaSugerida,
			@RequestParam String primaSRTAct, @RequestParam String primaSRTPro,
			@RequestParam String primaSRTAnt, 
			@RequestParam Long cveIdPatronDictamen,
			HttpSession session, Model model,
			DefaultMessageContext messageContext) throws Exception {
		
		String idSolicitud = session.getAttribute("idSolicitud").toString();
		String regPatronal = session.getAttribute("regPatronal").toString();
		String tipoPersona = session.getAttribute("tipoPersona").toString();
		SujetoObligado sujetoObligado = null;
		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}
		
		try {		
			//Se recupera el usuario logeado
		 	Usuario usuario = (Usuario)session.getAttribute("usuario");
		 	int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		 	if (!perfilUsuarioValido(iRol)) {
				log.error("::: El usuario "+usuario.getUsuario()+" no tiene un perfil valido para ejecutar esta accion, rol: " + iRol);
				return "internalError";
			}
		 	log.info("acutalizando status");
			messageContext.setMessageSource(messageSource);

			//valida el campo de comentarios
			if(comentarios !=null && comentarios.length() > Constantes.COMENTARIO_LONGITUD_DESC){
				log.error("***** Error el campo de comentarios es mayor al paermitido, comentarios.length: " + comentarios.length());
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.comentarios"));
				model.addAttribute("messageContext", messageContext);
				return detalleSolicitudController.detalle(idSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);

			}		
			
	//se omite validacion por cambio en la vista donde no se mostraran rps en estatus de baja		
			//Valida si el registro patronal no se encuentra dado de baja; en caso contrario,
			//no permite continuar con la operacion
//			final mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
//			solicitud.setSolicitudId(Long.valueOf(session.getAttribute("idSolicitud").toString()));
//			try {
//				gceSujetoObligadoServiceBusiness.validarEstadoRegistroPatronal(solicitud);
//			} catch (final GCESujetoObligadoException e) {
//				log.error(e.getMessage(), e);
//				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.regpatronal"));
//				model.addAttribute("messageContext", messageContext);
//				return detalleSolicitudController.detalleSolicitud(session.getAttribute("idSolicitud").toString(),
//						session.getAttribute("regPatronal").toString(), session.getAttribute("tipoPersona").toString(), session, model);
//			}
		
			//Validaciones para autorizar la rectificacion - JSM
			Clasificacion clasificacion = clasificacionServiceBusiness.obtenerDetalleFraccionPorId(getClasificacion(cveIdDivision, cveIdGrupo, cveIdFraccion));
			validaClasificacionServiceBusiness.validaClasificacionGP(getClasificacionNum(clasificacion), 
					regPatron, Long.valueOf(session.getAttribute("idSolicitud").toString()));

			ClasificacionPropuestaDTO dto = new ClasificacionPropuestaDTO();
			dto.setCveIdAnalisis(cveIdAnalisis);
			dto.setTipoPersona(idTipoPersona);
			dto.setRegPatronal(regPatron);
			dto.setIndRegPatClase(indRegPatClase);
			dto.setCveIdDivision(cveIdDivision);
			dto.setCveIdGrupo(cveIdGrupo);
			dto.setCveIdFraccionPro(cveIdFraccion);
			dto.setClase(clase);
			dto.setPrimaSugerida(primaSugerida);
			dto.setActividadDetectada(actividadDetectada.toUpperCase());
			dto.setComentarios(comentarios.toUpperCase());
			dto.setCveIdDelegacion(cveIdDelegacion);
			dto.setCveIdSubdelegacion(cveIdSubdelegacion);
			dto.setCveIdFraccionAct(cveIdFraccionAct);
			dto.setCveIdFraccionAnt(cveIdFraccionAnt);
			dto.setPrimaSRTAct(primaSRTAct);
			dto.setPrimaSRTPro(primaSRTPro);
			dto.setPrimaSRTAnt(primaSRTAnt);
			dto.setUsuario(usuario);
			dto.setIndModAut(session.getAttribute("indModAut").toString());
			dto.setCveUsuarioAsignado(usuario.getCveIdUsuario());
			dto.setRfc(session.getAttribute("rfc").toString());

			if(rectificacionBusiness.guardaRectificacion(dto, false) != 0){
				model.addAttribute("regPatronal", regPatron);
				model.addAttribute("tipoPersona", idTipoPersona);
				model.addAttribute("popUp", 1);
				messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE,
						"label.info.success"));
			}else{
				model.addAttribute("popUp", 0);
				model.addAttribute("cveIdAnalisis", cveIdAnalisis);
				model.addAttribute("idTipoPersona", idTipoPersona);
				model.addAttribute("regPatron", regPatron);
				model.addAttribute("indRegPatClase", indRegPatClase);
				model.addAttribute("cveIdDivision", cveIdDivision);
				model.addAttribute("cveIdGrupo", cveIdGrupo);
				model.addAttribute("cveIdFraccion", cveIdFraccion);
				model.addAttribute("clase", clase);
				model.addAttribute("actividadDetectada", actividadDetectada);
				model.addAttribute("comentarios", comentarios);
				model.addAttribute("cveIdDelegacion", cveIdDelegacion);
				model.addAttribute("cveIdSubdelegacion", cveIdSubdelegacion);
				model.addAttribute("cveIdFraccionAct", cveIdFraccionAct);
				model.addAttribute("cveIdFraccionAnt", cveIdFraccionAnt);
				model.addAttribute("primaSRTAct", primaSRTAct);
				model.addAttribute("primaSRTPro", primaSRTPro);
				model.addAttribute("primaSRTAnt", primaSRTAnt);
				model.addAttribute("primaSugerida", primaSugerida);
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.regpatronal"));
			}
			
		} catch (final GestionPatronalBusinessException e) {
			log.error(e.getMessage(), e);
			if(e != null && e.getCodigo() != null && e.getCodigo() == 800){
				rectificacionBusiness.actualizaEstatusRegularizar(cveIdAnalisis);
				model.addAttribute("errorRectificacionMsg", "El registro patronal "+regPatron+" deber\u00E1 ser regularizado "
						+ "por PAC, ya que el patr\u00F3n cuenta con el RP " + e.getMessage().substring(44, 55)
						+ " con la misma clasificaci\u00F3n seleccionada"
						+ ", por lo que se le asignar\u00E1 el estatus \"Por Regularizar\" ");
				
			}else{
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, e.getMessage()));
				model.addAttribute("messageContext", messageContext);
			}
			
			
			return detalleSolicitudController.detalle(idSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
		} catch (final EstatusMovimientoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificar"));
			log.error(e.getMessage(), e);
		}
		
		model.addAttribute("messageContext", messageContext);
		
		return detalleSolicitudController.detalle(idSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado, null);
	}
 
	/////////////////Pendiente por autorizar de Dictamen////////////////////////////////////
	@RequestMapping(value = "/rectificadoPendiente/dictamen", method = RequestMethod.POST)
	public String actualizarStatusDictamen(@PathVariable String cveIdAnalisis,
			@RequestParam String idTipoPersona, @RequestParam String regPatron,
			@RequestParam String indRegPatClase,
			@RequestParam String cveIdDivision,
			@RequestParam String cveIdGrupo,
			@RequestParam String cveIdFraccion, @RequestParam String clase,
			@RequestParam String actividadDetectada,
			@RequestParam String comentarios,
			@RequestParam String cveIdDelegacion,
			@RequestParam String cveIdSubdelegacion,
			@RequestParam String cveIdFraccionAct,
			@RequestParam String cveIdFraccionAnt,
			@RequestParam String primaSRTAct, @RequestParam String primaSRTPro,
			@RequestParam String primaSRTAnt, 
			@RequestParam Long cveIdPatronDictamen,
			HttpSession session, Model model,
			DefaultMessageContext messageContext) throws Exception {
		
		String idSolicitud = session.getAttribute("idSolicitud").toString();
		String regPatronal = session.getAttribute("regPatronal").toString();
		String tipoPersona = session.getAttribute("tipoPersona").toString();
		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
				
		try {		
			//Se recupera el usuario logeado
		 	Usuario usuario = (Usuario)session.getAttribute("usuario");
		 	int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		 	if (!perfilUsuarioValido(iRol)) {
				log.error("::: El usuario "+usuario.getUsuario()+" no tiene un perfil valido para ejecutar esta accion, rol: " + iRol);
				return "internalError";
			}
		 	log.info("acutalizando status");
			messageContext.setMessageSource(messageSource);

			//valida el campo de comentarios
			if(comentarios !=null && comentarios.length() > Constantes.COMENTARIO_LONGITUD_DESC){
				log.error("***** Error el campo de comentarios es mayor al permitido, comentarios.length: " + comentarios.length());
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.comentarios"));
				model.addAttribute("messageContext", messageContext);
				return detalleSolicitudController.detalle(idSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);

			}		
			
	//se omite validacion por cambio en la vista donde no se mostraran rps en estatus de baja		
			//Valida si el registro patronal no se encuentra dado de baja; en caso contrario,
			//no permite continuar con la operación
//			final mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
//			solicitud.setSolicitudId(Long.valueOf(session.getAttribute("idSolicitud").toString()));
//			try {
//				gceSujetoObligadoServiceBusiness.validarEstadoRegistroPatronal(solicitud);
//			} catch (final GCESujetoObligadoException e) {
//				log.error(e.getMessage(), e);
//				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.regpatronal"));
//				model.addAttribute("messageContext", messageContext);
//				return detalleSolicitudController.detalleSolicitud(session.getAttribute("idSolicitud").toString(),
//						session.getAttribute("regPatronal").toString(), session.getAttribute("tipoPersona").toString(), session, model);
//			}
		
			//Validaciones para autorizar la rectificacion - JSM
			Clasificacion clasificacion = clasificacionServiceBusiness.obtenerDetalleFraccionPorId(getClasificacion(cveIdDivision, cveIdGrupo, cveIdFraccion));
			validaClasificacionServiceBusiness.validaClasificacionGP(getClasificacionNum(clasificacion), 
					regPatron, Long.valueOf(session.getAttribute("idSolicitud").toString()));

			ClasificacionPropuestaDTO dto = new ClasificacionPropuestaDTO();
			dto.setCveIdAnalisis(cveIdAnalisis);
			dto.setTipoPersona(idTipoPersona);
			dto.setRegPatronal(regPatron);
			dto.setIndRegPatClase(indRegPatClase);
			dto.setCveIdDivision(cveIdDivision);
			dto.setCveIdGrupo(cveIdGrupo);
			dto.setCveIdFraccionPro(cveIdFraccion);
			dto.setClase(clase);
			dto.setActividadDetectada(actividadDetectada.toUpperCase());
			dto.setComentarios(comentarios.toUpperCase());
			dto.setCveIdDelegacion(cveIdDelegacion);
			dto.setCveIdSubdelegacion(cveIdSubdelegacion);
			dto.setCveIdFraccionAct(cveIdFraccionAct);
			dto.setCveIdFraccionAnt(cveIdFraccionAnt);
			dto.setPrimaSRTAct(primaSRTAct);
			dto.setPrimaSRTPro(primaSRTPro);
			dto.setPrimaSRTAnt(primaSRTAnt);
			dto.setUsuario(usuario);
			dto.setIndModAut(session.getAttribute("indModAut").toString());
			dto.setCveUsuarioAsignado(usuario.getCveIdUsuario());
			dto.setRfc(session.getAttribute("rfc").toString());

			if(rectificacionBusiness.guardaRectificacion(dto, true) != 0){
				model.addAttribute("regPatronal", regPatron);
				model.addAttribute("tipoPersona", idTipoPersona);
				model.addAttribute("popUp", 1);
				messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE,
						"label.info.success"));
			}else{
				model.addAttribute("popUp", 0);
				model.addAttribute("cveIdAnalisis", cveIdAnalisis);
				model.addAttribute("idTipoPersona", idTipoPersona);
				model.addAttribute("regPatron", regPatron);
				model.addAttribute("indRegPatClase", indRegPatClase);
				model.addAttribute("cveIdDivision", cveIdDivision);
				model.addAttribute("cveIdGrupo", cveIdGrupo);
				model.addAttribute("cveIdFraccion", cveIdFraccion);
				model.addAttribute("clase", clase);
				model.addAttribute("actividadDetectada", actividadDetectada);
				model.addAttribute("comentarios", comentarios);
				model.addAttribute("cveIdDelegacion", cveIdDelegacion);
				model.addAttribute("cveIdSubdelegacion", cveIdSubdelegacion);
				model.addAttribute("cveIdFraccionAct", cveIdFraccionAct);
				model.addAttribute("cveIdFraccionAnt", cveIdFraccionAnt);
				model.addAttribute("primaSRTAct", primaSRTAct);
				model.addAttribute("primaSRTPro", primaSRTPro);
				model.addAttribute("primaSRTAnt", primaSRTAnt);
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.regpatronal"));
			}
			
		} catch (final GestionPatronalBusinessException e) {
			log.error(e.getMessage(), e);
			if(e != null && e.getCodigo() != null && e.getCodigo() == 800){
				rectificacionBusiness.actualizaEstatusRegularizar(cveIdAnalisis);
				model.addAttribute("errorRectificacionMsg", "El registro patronal "+regPatron+" deber\u00E1 ser regularizado "
						+ "por PAC, ya que el patr\u00F3n cuenta con el RP " + e.getMessage().substring(44, 55)
						+ " con la misma clasificaci\u00F3n seleccionada"
						+ ", por lo que se le asignar\u00E1 el estatus \"Por Regularizar\" ");
				
			}else{
				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, e.getMessage()));
				model.addAttribute("messageContext", messageContext);
			}
			
			
			return detalleSolicitudController.detalle(idSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado,null);
		} catch (final EstatusMovimientoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificar"));
			log.error(e.getMessage(), e);
		}
		
		model.addAttribute("messageContext", messageContext);
		
		return detalleSolicitudController.detalle(idSolicitud, regPatronal, tipoPersona, session, model, cveIdPatronDictamen, sujetoObligado, null);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// METODOS PARA AUTORIZAR LA RECTIFICACION
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = "/autorizarRectificacion/capturaDatosClem", method = RequestMethod.POST)
	public String capturaDatosClem(@PathVariable String cveIdAnalisis,@ModelAttribute ReporteClemBean reporteClemBean,
			BindingResult result, SessionStatus status, HttpSession session, Model model, HttpServletResponse sresponse){
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		
		log.debug("::: Estoy en capturaDatosClem");
		return this.capturaDatosClemInterno(cveIdAnalisis, null, reporteClemBean, result, status, session, model, sresponse);
	}
	////DicTAMEN
	@RequestMapping(value = "/autorizarRectificacion/capturaDatosClem/{cveIdPatronDictamen}", method = RequestMethod.POST)
	public String capturaDatosClemDictamen(@PathVariable String cveIdAnalisis, @PathVariable Long cveIdPatronDictamen,@ModelAttribute ReporteClemBean reporteClemBean,
			BindingResult result, SessionStatus status, HttpSession session, Model model, HttpServletResponse sresponse){
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		
		log.debug("::: Estoy en capturaDatosClem - cveIdPatronDictamen: " + cveIdPatronDictamen);
		return this.capturaDatosClemInterno(cveIdAnalisis, cveIdPatronDictamen, reporteClemBean, result, status, session, model, sresponse);
	}

	/**
	 * Metodo para iniciar la autorizacion de la rectificacion
	 * @param reporteClemBean
	 * @param result
	 * @param status
	 * @param session
	 * @param model
	 * @param sresponse
	 * @return
	 */
	private String capturaDatosClemInterno(String cveIdAnalisis,Long cveIdPatronDictamen,ReporteClemBean reporteClemBean,
			BindingResult result, SessionStatus status, HttpSession session, Model model, HttpServletResponse sresponse){
		try {
			reporteClemBean.setIdAnalisis(cveIdAnalisis);
			reporteClemBean=datosClemBusiness.consultaDatosClem(reporteClemBean);
			if(reporteClemBean.getLugarFechaExpedicion()==null){
				reporteClemBean.setLugarFechaExpedicion(getFechaActual());
			}
			if(reporteClemBean.getCveIdClem()!=null){
				reporteClemBean.setBotonClem(Constantes.CLEM_MODIFICAR);
			}else{
				reporteClemBean.setBotonClem(Constantes.CLEM_GENERAR);
			}
			
			if(cveIdPatronDictamen != null) {
				reporteClemBean.setBotonClem("Finalizar");
				if(reporteClemBean.getPuesto() == null){
					reporteClemBean.setPuesto("No aplica");
				}
			}
		} catch (DatosClemException e){
			log.error("Ocurrio un error al Consultar Propiedades del CLEM: " + e.getMessage());
		}
		model.addAttribute("reporteClemBean", reporteClemBean);
		model.addAttribute("cveIdPatronDictamen",cveIdPatronDictamen);
		return "autorizarRectificacion";
	}
	
	
	@RequestMapping(value = "/autorizarRectificacion/generaClem/rectificadoAutorizado", method = RequestMethod.POST)
	public String generacionClem(@ModelAttribute ReporteClemBean reporteClemBean,
			BindingResult result, SessionStatus status, HttpSession session, Model model, HttpServletResponse sresponse,
			HttpServletRequest request, DefaultMessageContext messageContext)throws Exception{
		log.debug(":::: Entro a generacionClemDictamen");
		return this.generacionClemInterno(null,reporteClemBean,result,status, session, model, sresponse,
				request, messageContext);
	
	}
	
	@RequestMapping(value ="/autorizarRectificacion/generaClem/rectificadoAutorizado/{cveIdPatronDictamen}", method = RequestMethod.POST)
	public String generacionClemDictamen(@PathVariable Long cveIdPatronDictamen,@ModelAttribute ReporteClemBean reporteClemBean,
			BindingResult result, SessionStatus status, HttpSession session, Model model, HttpServletResponse sresponse,
			HttpServletRequest request, DefaultMessageContext messageContext)throws Exception{
		log.debug(":::: Entro a generacionClemDictamen - cveIdPatronDictamen: " + cveIdPatronDictamen);
		return this.generacionClemInterno(cveIdPatronDictamen,reporteClemBean,result,status, session, model, sresponse,
				request, messageContext);
	
	}
		
	/**
	 * Metodo para generar la clem y autorizar el analisis de rectificacion
	 * @param reporteClemBean
	 * @param result
	 * @param status
	 * @param session
	 * @param model
	 * @param sresponse
	 * @param request
	 * @param messageContext
	 * @return
	 */	
	private String generacionClemInternoDictamen(Long cveIdPatronDictamen,ReporteClemBean reporteClemBean,
			BindingResult result, SessionStatus status, HttpSession session, Model model, HttpServletResponse sresponse,
			HttpServletRequest request, DefaultMessageContext messageContext)throws Exception{
		SujetoObligado sujetoObligado = null;
		
		try {
			log.debug(":::: Entro a generacionClemInternoDictamen " );
			messageContext.setMessageSource(messageSource);
						

		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificar.autorizacion"));
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		model.addAttribute("messageContext", messageContext);
		
		sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		

		return detalleSolicitudController.detalle(session.getAttribute("idSolicitud").toString(), session.getAttribute("regPatronal").toString(),
				session.getAttribute("tipoPersona").toString(), session, model, cveIdPatronDictamen, sujetoObligado,null);
	}	
	
	
	/**
	 * Metodo para generar la clem y autorizar el analisis de rectificacion
	 * @param reporteClemBean
	 * @param result
	 * @param status
	 * @param session
	 * @param model
	 * @param sresponse
	 * @param request
	 * @param messageContext
	 * @return
	 */	
	private String generacionClemInterno(Long cveIdPatronDictamen,ReporteClemBean reporteClemBean,
			BindingResult result, SessionStatus status, HttpSession session, Model model, HttpServletResponse sresponse,
			HttpServletRequest request, DefaultMessageContext messageContext)throws Exception{
		SujetoObligado sujetoObligado = null;
		
		try {
			log.debug(":::: Entro a generacionClemInterno - cveIdPatronDictamen: " + cveIdPatronDictamen);
			messageContext.setMessageSource(messageSource);
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			reporteClemBean.setCveSolicitud(session.getAttribute("idSolicitud").toString());
			reporteClemBean.setRegPatronal(session.getAttribute("regPatronal").toString());
			reporteClemBean.setTitular(reporteClemBean.getTitular().toUpperCase());
			if(reporteClemBean.getSuplente() != null){
				reporteClemBean.setSuplente(reporteClemBean.getSuplente().toUpperCase());
			}
			reporteClemBean.setPuesto(reporteClemBean.getPuesto().toUpperCase());
			
			new DatosClemValidator().validate(reporteClemBean, result);
			if (result.hasErrors()){
				log.debug("Error al validar la captura de datosclem");
				model.addAttribute("reporteClemBean", reporteClemBean);
				model.addAttribute("cveIdPatronDictamen",cveIdPatronDictamen);
				return "autorizarRectificacion";
			}else{
				log.debug("Sin error al validar la captura de datosclem");
			}

			//Validaciones para autorizar la rectificacion - JSM
			AnalisisClasificacionEmpresas analisisClasEmp = clasificacionPropuestaBusiness.consultaPorIdAnalisis(Long.valueOf(reporteClemBean.getIdAnalisis()));

			validaClasificacionServiceBusiness.validaClasificacionGP(Utiles.getClasificacion(analisisClasEmp), reporteClemBean.getRegPatronal(), 
					Long.valueOf(reporteClemBean.getCveSolicitud()));

			log.debug("Despues de validar clasificacion y a generar clem");
			datosClemBusiness.generacionClem(reporteClemBean, usuario, (new ClassPathResource("reportes/").getPath().toString()) + "asimss-clem.jpg",cveIdPatronDictamen);
			messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));

		} catch (final GestionPatronalBusinessException e) {
			log.debug("GestionPatronalBusinessException: " + e.getMessage());
			log.error(e.getMessage(), e);
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, e.getMessage()));
			model.addAttribute("messageContext", messageContext);
			return detalleSolicitudController.detalle(session.getAttribute("idSolicitud").toString(), session.getAttribute("regPatronal").toString(),
					session.getAttribute("tipoPersona").toString(), session, model, cveIdPatronDictamen, sujetoObligado,null);
		}catch(GCESujetoObligadoException e){
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.regpatronal"));
			log.error(e.getMessage(), e);
	 	} catch (final EstatusMovimientoException e) {
	 		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
	 	} catch (final ClemCaracterException e) {
	 		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.caracter.clem"));
			log.error(e.getMessage(), e);
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificar.autorizacion"));
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		model.addAttribute("messageContext", messageContext);
		
		if(cveIdPatronDictamen != null) {
			sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		}

		return detalleSolicitudController.detalle(session.getAttribute("idSolicitud").toString(), session.getAttribute("regPatronal").toString(),
				session.getAttribute("tipoPersona").toString(), session, model, cveIdPatronDictamen, sujetoObligado,null);
	}			

	//obntiene la clasificacion por los numeros del catalogo ya que el metodo que obtiene la fraccion equivalente asi los necesita
	private Clasificacion getClasificacionNum(final Clasificacion clasificacion) {		
		final Clasificacion clas = new Clasificacion();
		final Division division = new Division();
		division.setId(Long.valueOf(clasificacion.getFraccion().getGrupo().getDivision().getNumDivision()));
		final Grupo grupo = new Grupo();
		grupo.setId(Long.valueOf(clasificacion.getFraccion().getGrupo().getNumGrupo()));
		grupo.setDivision(division);
		final Fraccion fraccion = new Fraccion();
		fraccion.setId(Long.valueOf(clasificacion.getFraccion().getNumFraccion()));
		fraccion.setGrupo(grupo);
		clas.setFraccion(fraccion);
		return clas;
	}

	private Clasificacion getClasificacion(final String cveIdDivision, final String cveIdGrupo, final String cveIdFraccion) {
		final Clasificacion clas = new Clasificacion();
		final Division division = new Division();
		division.setId(Long.valueOf(cveIdDivision));
		final Grupo grupo = new Grupo();
		grupo.setId(Long.valueOf(cveIdGrupo));
		grupo.setDivision(division);
		final Fraccion fraccion = new Fraccion();
		fraccion.setId(Long.valueOf(cveIdFraccion));
		fraccion.setGrupo(grupo);
		clas.setFraccion(fraccion);
		return clas;
	}
	
	private boolean perfilUsuarioValido(int iRol){
		if (iRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue() 
				|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().intValue()
				) {
			return true;
		}
		return false;
	}



}
