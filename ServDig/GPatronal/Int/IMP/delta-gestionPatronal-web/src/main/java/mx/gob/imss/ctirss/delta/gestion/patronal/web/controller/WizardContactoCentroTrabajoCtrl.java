package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CodigoRolTemporal;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.InstanceofPredicate;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/wizard/tramite/centroTrabajo/medios")
public class WizardContactoCentroTrabajoCtrl extends AbstractController {
	
	private static final String KEY_FIRMA_ELECTRONICA 		= "datosFirmaElectronica";
	private static final String KEY_DESCRIPCION_TIPO_TRAMITE = "descripcionTipoTramite";
	private static final String KEY_TIPO_SOLICITUD 			= "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD 	= "descripcionTipoSolicitud";
	private static final String KEY_CADENA_ORIGINAL 		= "contenidoFirmar";	
	private static final String INICIO_WIZARD_CONTACTO 		= "wizardContactoCentroTrabajoInit";
	private static final String CONTENIDO_WIZARD_CONTACTO 	= "wizardContactoCentroTrabajoContenido";
	private static final String KEY_ID_SOLICITUD 			= "idSolicitud";
	private static final String KEY_FOLIO_SOLICITUD 		= "folioSolicitud";
	private static final String KEY_ID_TIPO_TRAMITE 		= "idTipoTramite";
	private static final String KEY_ATRIBUTE_MENSAJE 		= "mensaje";
	private static final String KEY_ATRIBUTE_ERROR 			= "error";	
	private static final String SOLICITUD_KEY 				= "solicitudToSession";
	private static final String SUJETO_OBLIGADO_KEY 		= "sujetoObligadoToSession";	
	private static final String DESC_TIPO_SOLICITUD 		= "MODIFICACIÓN DEL CENTRO DE TRABAJO";
	private static final String DESC_TIPO_TRAMITE			= "ACTUALIZACIÓN DE MEDIOS DE CONTACTO";
	private static final String KEY_ATRIBUTE_SOLICITUD 		= "solicitud";
	
	@Autowired
	private SujetoObligadoServiceBusinessRemote registroPatronalService;
	@Autowired
	private SolicitudServiceBusinessRemote gpSolicitudService;
	@Autowired
	private AfiliacionServiceBusinessRemote afiliacionBusiness;
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
   

	
	@RequestMapping(value = "/{numeroRegistroPatronal}/{idPersona}/{idTipoPersona}", method = RequestMethod.GET)
	public String initMedios(Model model, HttpSession session, HttpServletRequest request, 
		@PathVariable String numeroRegistroPatronal, @PathVariable Long idPersona, @PathVariable Integer idTipoPersona) {
		limpiarSession(session);
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(numeroRegistroPatronal);
		if(idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		} else {
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		}
		sujetoObligado=registroPatronalService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);				
		// Se busca si existen solicitudes pendientes...
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;		
		//Obtener solicitud REGISTRADA
		 Solicitud solicitudActiva = gpSolicitudService.obtenerSolicitudEnCaptura(
			sujetoObligado, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO);
		if (solicitudActiva != null
				&& solicitudActiva.getSolicitudId() != null) {
			existeSolRegistrada = true;
			model.addAttribute(KEY_ID_SOLICITUD, solicitudActiva.getSolicitudId());
			model.addAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());			
		} else {			
			//Obtener solicitud EN PROCESO (PENDIENTE_AUTORIZACION / EDICION_VENTANILLA / EDICION_BACKOFFICE)
			solicitudActiva = gpSolicitudService.obtenerSolicitudEnProceso(
				sujetoObligado, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO);
			if (solicitudActiva != null
					&& solicitudActiva.getSolicitudId() != null) {
				existeSolProceso = true;
				model.addAttribute(KEY_ID_SOLICITUD, solicitudActiva.getSolicitudId());
				model.addAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());				
			} else {
				solicitudActiva = new Solicitud();
			}
		}
		request.setAttribute("existeSolRegistrada", existeSolRegistrada);
		request.setAttribute("existeSolProceso", existeSolProceso);
		model.addAttribute("sujetoTramite", sujetoObligado);
		return INICIO_WIZARD_CONTACTO;
	}
	
	@RequestMapping(value = "/crear/solicitud", method = RequestMethod.POST)
	public String crearSolicitud(Model model,
			final HttpSession session, HttpServletRequest request,
			@ModelAttribute SujetoObligado sujetoObligado) {
		limpiarSession(session);
		Solicitud solicitud = null;
		try {
			UsuarioSSO sso = this.procesarUsuarioSSO(request);
			Usuario usuario = getUsuarioSesion(sso, session);			
			sujetoObligado=registroPatronalService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
			model.addAttribute("sujetoTramite",sujetoObligado);
			uploadMediosContacto(sujetoObligado.getCntroTrabajo().getMediosContacto(), model);						
			solicitud = gpSolicitudService.generarSolicitud(TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO, 
				EstadoSolicitudEnum.REGISTRADA, usuario, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO,
				EstadoTramiteEnum.CERRADO, sujetoObligado, false, false);			
			model.addAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
			model.addAttribute(KEY_ATRIBUTE_SOLICITUD, solicitud);
			Fisica personaRecuperada = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
			obtenerDatosAcuse(personaRecuperada, session, sujetoObligado.getNumeroRegistroPatronal());
			Persona persona = sujetoObligado.getFisica()!=null ? sujetoObligado.getFisica() : sujetoObligado.getMoral();
			generarCadenaOriginal(persona, session, sujetoObligado.getNumeroRegistroPatronal());	
			//To session
			setAttributeToSessionTramite(session, solicitud, sujetoObligado);
		} catch (AbstractException e) {
			log.error(e);
			e.printStackTrace();
			request.setAttribute(KEY_ATRIBUTE_ERROR, e.getMessage());
		}
		request.setAttribute("isRetomar", false);
		return CONTENIDO_WIZARD_CONTACTO;
	}
		
	@RequestMapping(value = "/retomar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public String retomarSolicitud(@ModelAttribute SujetoObligado sujetoObligado, HttpSession session,
			HttpServletRequest request, @PathVariable Long idSolicitud, final Model model) {
		limpiarSession(session);
		Solicitud solicitud = gpSolicitudService.consultarSolicitudPorId(idSolicitud);
		model.addAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
		model.addAttribute(KEY_ATRIBUTE_SOLICITUD, solicitud);
		// Se verifica si existe un tramite de tipo TramieSujetoObligado
		InstanceofPredicate tramiteSujetoObligadoPredicate = new InstanceofPredicate(TramiteSujetoObligado.class);
		Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramiteSujetoObligadoPredicate);		
		if (tramiteInicial != null) {
			try {
				TramiteSujetoObligado tramiteSO = (TramiteSujetoObligado) tramiteInicial;
				sujetoObligado = tramiteSO.getSujetoObligado();
				if(sujetoObligado!=null && sujetoObligado.getCntroTrabajo()!=null){
					log.debug("Informacion de contacto sin finalizar solicitud");
					//Revisar si se da el caso que se guarde información del contacto sin finalizar la solicitud.
				}else{
					//No se ha guardao información, obtener información existente.
					sujetoObligado=registroPatronalService
						.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
				}
				model.addAttribute("sujetoTramite", sujetoObligado);
				uploadMediosContacto(sujetoObligado.getCntroTrabajo().getMediosContacto(), model);				
				Fisica personaRecuperada = serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(procesarUsuarioSSO(request).getIdPersona().longValue());				
				obtenerDatosAcuse(personaRecuperada, session, sujetoObligado.getNumeroRegistroPatronal());	
				Persona persona = sujetoObligado.getFisica()!=null ? sujetoObligado.getFisica() : sujetoObligado.getMoral();
				generarCadenaOriginal(persona, session, sujetoObligado.getNumeroRegistroPatronal());				
				//To session
				setAttributeToSessionTramite(session, solicitud, sujetoObligado);
			} catch (AbstractException e) {
				log.error(e);
				e.printStackTrace();
				request.setAttribute(KEY_ATRIBUTE_ERROR, e.getMessage());
			}
		}
		request.setAttribute("isRetomar", true);
		return CONTENIDO_WIZARD_CONTACTO;
	}
	
	
	@RequestMapping(value = "/cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitudModificacionDatos(
			@PathVariable Long idSolicitud, HttpServletResponse response,
			HttpServletRequest request,Locale locale) {
		String message = "";
		Map<String, Object> result = new HashMap<String, Object>();
		log.debug("CANCELAR SOLICITUD [ " + idSolicitud + " ]");
		if (idSolicitud != null) {
			try {
				gpSolicitudService.cancelarSolicitud(idSolicitud);
				result.put(KEY_ATRIBUTE_MENSAJE, "La solicitud fue cancelada correctamente");
				result.put("solicitud", idSolicitud);
			}catch(SolicitudException se){ 
				message = messageSource.getMessage(se.getSituacion(), null, locale);
				result.put(KEY_ATRIBUTE_MENSAJE, message);
			}catch (Exception e) {
				e.printStackTrace();
				message = "Ocurrio un error al intentar cancelar la solicitud.";
				result.put(KEY_ATRIBUTE_MENSAJE, message);
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session){
    	
    	session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		log.info("Se almacenan los datos de la firma digital de forma temporal " + firmaElectronica);
		return null;		
    }
	
	@RequestMapping(value = "/finalizarSolicitud", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitud(HttpSession session, 
			@RequestBody CentroTrabajo centroTrabajo,Locale locale) {
		
		Map<String, Object> result = new HashMap<String, Object>();		
		FirmaElectronica firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
		//Recuperar sujetos y solicitud de session. Actualizar datos contacto en el tramite.
		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute(SUJETO_OBLIGADO_KEY);
		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_KEY);
		sujetoObligado.setCntroTrabajo(centroTrabajo);
		InstanceofPredicate tramiteSujetoObligadoPredicate = new InstanceofPredicate(TramiteSujetoObligado.class);
		Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramiteSujetoObligadoPredicate);
		TramiteSujetoObligado tramiteSO = (TramiteSujetoObligado) tramiteInicial;
		tramiteSO.setSujetoObligado(sujetoObligado);		
		try {
			//Actualizar detalle del tramite con datos finales.
			gpSolicitudService.actualizarTramites(solicitud);
			gpSolicitudService.procesarSolicitudContactoCentroTrabajo(solicitud.getSolicitudId(), firma);
			result.put(KEY_ATRIBUTE_MENSAJE, "Su solicitud ha finalizado correctamente");
		} catch (AbstractException e) {
			log.error(e);
			result.put(KEY_ATRIBUTE_MENSAJE, e.getSituacion());
		}				
		return result;
	}
	
	
	
	private void limpiarSession(final HttpSession session) {	
		session.removeAttribute(SOLICITUD_KEY);
		session.removeAttribute(KEY_FOLIO_SOLICITUD);		
		session.removeAttribute(KEY_ID_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);	
		session.removeAttribute(KEY_CADENA_ORIGINAL);		
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);		
		session.removeAttribute(SUJETO_OBLIGADO_KEY);
		session.removeAttribute(KEY_ID_TIPO_TRAMITE);
	}
	
	private void setAttributeToSessionTramite(final HttpSession session, Solicitud solicitud, SujetoObligado sujetoObligado) {	
		session.setAttribute(KEY_ID_SOLICITUD, solicitud.getSolicitudId());
		session.setAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
		session.setAttribute(SOLICITUD_KEY, solicitud);			
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_CENTRO_TRABAJO.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		session.setAttribute(KEY_ID_TIPO_TRAMITE, TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO.getCodigo());
		session.setAttribute(KEY_DESCRIPCION_TIPO_TRAMITE, DESC_TIPO_TRAMITE);		
		session.setAttribute(SUJETO_OBLIGADO_KEY, sujetoObligado);
	}
	
	
	
	private void uploadMediosContacto(List<MedioContacto> medios, Model model){
		boolean existsPrincipal = false;		
		if(CollectionUtils.isEmpty(medios)){
			return;
		}else{
			for(MedioContacto contacto : medios){
				if(contacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO))
					model.addAttribute("ctCorreoElectronico",contacto.getDesFormaContacto());
				if(contacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO) && !existsPrincipal){
					model.addAttribute("ctTelefonoFijo",contacto.getDesFormaContacto());
					existsPrincipal=true;
				}
				if(contacto.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO) && existsPrincipal)
					model.addAttribute("ctTelefonoFijo2",contacto.getDesFormaContacto());							
			}
		}
	}

	private Usuario getUsuarioSesion(UsuarioSSO sso, HttpSession session) {
		Usuario usuario = new Usuario();
		ServletContext context = session.getServletContext();
		String rolRepresentanteLegal = context.getInitParameter("rolRepresentanteLegal");
        String rolSujetoObligado = context.getInitParameter("rolSujetoObligado");
        String rolExterno = context.getInitParameter("rolUsuarioExterno");
        String[] arrayRolRepresentanteLegal = {rolRepresentanteLegal};
        String[] arrayRolSujetoObligado = {rolSujetoObligado, rolExterno};
		usuario.setUsuario(sso.getNombre().toUpperCase());
		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(sso.getPerfil());
		UsuarioFuncionario uf = new UsuarioFuncionario();
		
		if (sso.getDelegacion() != null) {
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(sso.getDelegacion().longValue());
		}
		if (sso.getSubdelegacion() != null) {
			Subdelegacion subdel = afiliacionBusiness.obtenerSubdelegacion(sso.getSubdelegacion().longValue());
			usuario.setCveIdSubdelegacion(sso.getSubdelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			uf.getSubdelegacion().setClave(subdel.getClave());
			uf.getSubdelegacion().setDescripcion(subdel.getDescripcion());
			uf.getSubdelegacion().setDelegacion(new Delegacion());
			uf.getSubdelegacion().getDelegacion().setClave(subdel.getClave());
			uf.getSubdelegacion().getDelegacion().setDescripcion(subdel.getDescripcion());
			uf.getSubdelegacion().getDelegacion().setId(subdel.getId());
		}
		uf.setUsuario(usuario);
		usuario.setUsuarioFuncionario(uf);

		if (this.checkGrantedAuthorities(arrayRolSujetoObligado)) {
			pu.setIdPerfilUsuario( CodigoRolTemporal.PATRON_SUJETO_OBLIGADO.getCodigo().longValue());
			usuario.setPerfilUsuario(pu);
		} else if (this.checkGrantedAuthorities(arrayRolRepresentanteLegal)) {
			usuario.setFisica((Fisica) registroPatronalService.obtenerPersonaPorIdentificador(sso.getIdPersona().longValue()));
			pu.setIdPerfilUsuario(CodigoRolTemporal.REPRESENTANTE_LEGAL.getCodigo().longValue());
			usuario.setPerfilUsuario(pu);
		} else {
			// TODO Considerar Perfil por default
			pu.setIdPerfilUsuario(CodigoRolTemporal.TRAMITADOR.getCodigo().longValue());
			Fisica persona = (Fisica)registroPatronalService.obtenerPersonaPorIdentificador(sso.getIdPersona().longValue());
			usuario.setNomNombre(persona.getNombre());
			usuario.setNomPaterno(persona.getPrimerApellido());
			usuario.setNomMaterno(persona.getSegundoApellido());
			usuario.setFisica(persona);
			usuario.setPerfilUsuario(pu);
		}
		// Subimos a la sesion la informacion del usuario
		session.setAttribute(KEY_USUARIO, usuario);
		this.setFechaSistema(session);
		return usuario;
	}
	
	private void obtenerDatosAcuse(Persona persona, HttpSession session, String numeroRegistroPatronal) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosAcuse = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		datosAcuse.setFechaElectronicaFormateada(strFechaElectronica);
		datosAcuse.setFechaElectronica(Calendar.getInstance().getTime());
		// RFC
		datosAcuse.setRfc(persona.getRfc());
		// Nombre, denominacion o razon social del interesado (y en su caso el de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica)persona).getNombre().trim()).append(" ");
			if(StringUtils.isNotBlank(((Fisica)persona).getPrimerApellido())) {
				sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			}
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral)persona).getRazonSocial());
		}
		datosAcuse.setNombreCompleto(sbnombre.toString());
		// CURP
		if (persona instanceof Fisica) {
			datosAcuse.setCurp(((Fisica)persona).getCurp());
		}
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosAcuse);
	}
		
	private void generarCadenaOriginal(Persona persona, HttpSession session, String numeroRegistroPatronal) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();
		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");
		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(DESC_TIPO_SOLICITUD).append("|");
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(Calendar.getInstance().getTime());
		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(" ").append("|");
		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");
		datosEntradaFirma.setRfc(persona.getRfc());
		// Nombre, denominacion o razon social del interesado (y en su caso el de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica)persona).getNombre().trim()).append(" ");
			if(StringUtils.isNotBlank(((Fisica)persona).getPrimerApellido())) {
				sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
			}
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			sbnombre.append(((Moral)persona).getRazonSocial());
		}
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(sbnombre.toString()).append("|");
		datosEntradaFirma.setNombreCompleto(sbnombre.toString());
		// CURP
		contenidoAFirmar.append("CURP:");
		if (persona instanceof Fisica) {
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		} else {
			contenidoAFirmar.append("|");
		}
		// Registro Patronal
		contenidoAFirmar.append("Registro Patronal:");
		contenidoAFirmar.append(numeroRegistroPatronal).append("|");
		// NSS(No aplica)
		//contenidoAFirmar.append("Numero de Seguridad Social:||");
		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
	}
    
}
