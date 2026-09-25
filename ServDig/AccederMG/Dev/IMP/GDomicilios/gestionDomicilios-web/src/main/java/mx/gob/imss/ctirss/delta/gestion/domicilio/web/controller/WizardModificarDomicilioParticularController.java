package mx.gob.imss.ctirss.delta.gestion.domicilio.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoBusquedaVialidadEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.web.validator.DomicilioConcluirValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Marco Sanchez
 * 
 */

@Controller
@RequestMapping(value = "/wizard/tramite/modificar/domicilio/particular")
public class WizardModificarDomicilioParticularController extends AbstractController {
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";

	private static final String DESC_TIPO_SOLICITUD = "ACTUALIZACION DE DATOS GENERALES";
	
	private static final String KEY_TIPO_NUEVA_VIALIDAD = "tipoNuevaVialidad";
	private static final String KEY_TIPO_BUSQUEDA = "tipoBusquedaDomicilio";
	
	@Autowired
	PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@Autowired
	SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	@Autowired
	DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @param request
	 * @param idTipoTramite
	 * @param idTipoPersona
	 * @param idPersona
	 * @return
	 */
	@RequestMapping(value = "/{idTipoPersona}/{idPersona}/{idDomicilio}", method = RequestMethod.GET)
	public String initModificarDomicilioParticular(Model model, HttpSession session,
			HttpServletRequest request, @PathVariable Integer idTipoPersona, @PathVariable Long idPersona,
			@PathVariable Long idDomicilio) {
		session.removeAttribute(KEY_TIPO_NUEVA_VIALIDAD);
		session.removeAttribute(KEY_TIPO_BUSQUEDA);
		
		String view = null;
		
		
		UsuarioSSO sso = super.procesarUsuarioSSO(request);
		Usuario usuario = new Usuario();
		usuario.setUsuario(sso.getNombre());
		
		session.setAttribute(SSO_USER_KEY, usuario);

		// Objeto para la forma auxiliar para invocar al servicio de modificacion manual
		MDMDatosEntrada mdmDatosEntrada = new MDMDatosEntrada();
		if(idTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()) {
			Fisica fisica = new Fisica();
			fisica.setIdPersona(idPersona);
			
			mdmDatosEntrada.setPersonaFisica(fisica);
			
			mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.TRUE);
			mdmDatosEntrada.setIndCapturaDomicilioParticular(Boolean.TRUE);
			
			
			view = "wizardModifDomParticularFisicaInit";
			
		} else {
			Moral moral = new Moral();
			moral.setIdPersona(idPersona);
			mdmDatosEntrada.setPersonaMoral(moral);
			
			view = "wizardModifDomParticularMoralInit";
		}
		
		// Se busca si existen solicitudes en proceso pendientes
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		Solicitud solicitudActiva = null;
		
		try {
			solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudRegistrada(
					idPersona, idTipoPersona.longValue(),TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
					TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR);
		
			if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(
						idPersona, idTipoPersona.longValue(),TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES,
						TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR);
				
				if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;
				} else {
					solicitudActiva = new Solicitud();
				}
			}
		} catch (SolicitudException e) {
			this.log.error(e);
		}
		
		model.addAttribute("solicitudForm", solicitudActiva);
		request.setAttribute("existeSolRegistrada", existeSolRegistrada);
		request.setAttribute("existeSolProceso", existeSolProceso);
		
		model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
		
		request.setAttribute("idDomicilio", idDomicilio);
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES.getValor());
		
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR.getCodigo());
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);

		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
		
		return view;
	}

	@RequestMapping(value = "/crear/solicitud/{idDomicilio}", method = RequestMethod.POST)
	public String crearSolicitudModificacionDomicilio(@ModelAttribute MDMDatosEntrada mdmDatosEntrada,
			final HttpSession session, HttpServletRequest request, final Model model,
			@PathVariable Long idDomicilio) {
        
    	Solicitud solicitud = null;
    	Domicilio domicilio = null;
    	session.removeAttribute(KEY_TIPO_NUEVA_VIALIDAD);
		session.removeAttribute(KEY_TIPO_BUSQUEDA);
		
    	log.debug("ID PERSONA -> " + mdmDatosEntrada.getPersonaFisica().getIdPersona());
    	
    	// Se agregan las banderas que requiere el servicio de la modificacion manual
		mdmDatosEntrada.setIndCapturaDatosRENAPO(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaNombre(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaCURP(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaSexo(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaFechaNacimiento(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaLugarNacimiento(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDocumentoProbatorio(Boolean.FALSE);
		
		mdmDatosEntrada.setIndCapturaDatosSAT(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaRFC(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaDomicilioFiscal(Boolean.FALSE);
		mdmDatosEntrada.setIndCapturaMediosContactoFiscales(Boolean.FALSE);
		
		mdmDatosEntrada.setIndCapturaDatosComplementarios(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaDomicilioParticular(Boolean.TRUE);
		mdmDatosEntrada.setIndCapturaMediosContactoParticular(Boolean.FALSE);
		
		mdmDatosEntrada.setIndAutorizacion(Boolean.FALSE);

		try {
			
			// Obtener domicilio de persona
			domicilio = new Domicilio();
			domicilio.setClave(idDomicilio.intValue());
			domicilio = this.domicilioServiceBusiness.consultarDomicilio(domicilio);
			
			// Se settea el estado de administracion para que se tome en cuenta en la afectacion
			domicilio.setEstadoAdministracionDomicilio(EstadoAdministracionEnum.MODIFICADO);
			
			/*
			 * Se le settea el tipo de dicTipoDomicilio, ya que como no tiene la
			 * relacion directa en la base de datos, se le debe poner, aqui sabemos
			 * que es un domicilio particular
			 */
			TipoDomicilio tipoDomicilio = new TipoDomicilio();
			tipoDomicilio.setClave(TipoDomicilioEnum.PARTICULAR.getCodigo().intValue());
			domicilio.setDicTipoDomicilio(tipoDomicilio);
						
			if (mdmDatosEntrada.getPersonaFisica().getDomicilios() == null
					|| !mdmDatosEntrada.getPersonaFisica().getDomicilios()
							.isEmpty()) {
				mdmDatosEntrada.getPersonaFisica().setDomicilios(new ArrayList<Domicilio>());
			}
			
			mdmDatosEntrada.getPersonaFisica().getDomicilios().add(domicilio);
			
			
			UsuarioSSO usuarioSSO = this.procesarUsuarioSSO(request);
			Usuario usuario = new Usuario();
			usuario.setUsuario(usuarioSSO.getNombre());
			solicitud = this.solicitudPersonaBusiness.crearTramiteModificacionDatosPersona(mdmDatosEntrada, usuario);
			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			request.setAttribute("isRetomar", false);
			
			// Datos para la firma digital
			Fisica objfisicaRecuperado = serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(mdmDatosEntrada.getPersonaFisica().getIdPersona());
			obtenerDatosAcuse(solicitud, objfisicaRecuperado, session);
			generarCadenaOriginal(solicitud, objfisicaRecuperado, session);
			request.setAttribute(KEY_RFC_SOLICITANTE, mdmDatosEntrada.getPersonaFisica().getRfc());

		} catch (PersonaFisicaNoEncontradaException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
			
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_FISICA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			mdmDatosEntrada.setTraza(mensajes);
		} catch (SolicitudNoValidaException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
		} catch (DomicilioNoLocalizadoException e) {
			log.error(e);
			mdmDatosEntrada.setErrorFormGeneral(e.getMessage());
		}

		model.addAttribute("mdmDatosEntrada", mdmDatosEntrada);
		model.addAttribute("domicilio", domicilio);
		request.setAttribute("idDomicilio", idDomicilio);
    	
    	return "wizardModifDomParticularFisicaContenido";
    }
	
	@RequestMapping(value = "/retomar/solicitud", method = RequestMethod.POST)
	public String retomarSolicitudModificacionDomicilio(@ModelAttribute Solicitud solicitud,
			HttpSession session, HttpServletRequest request, final Model model) { 
	
		MDMDatosEntrada datosModif = null;
		
		try {
			Map<String, Object> resultado = this.solicitudPersonaBusiness
					.retomarSolicitudModificacionDatosPersona(solicitud.getSolicitudId());
			
			solicitud = (Solicitud) resultado.get("solicitud");
			
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("idSolicitud", solicitud.getSolicitudId());
			request.setAttribute("isRetomar", true);
			
			datosModif = (MDMDatosEntrada) resultado.get("datosModif");
			
			// Datos para la firma digital
			Fisica objfisicaRecuperado = serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(datosModif.getPersonaFisica().getIdPersona());

			obtenerDatosAcuse(solicitud, objfisicaRecuperado, session);
			generarCadenaOriginal(solicitud, objfisicaRecuperado, session);
			request.setAttribute(KEY_RFC_SOLICITANTE, datosModif.getPersonaFisica().getRfc());
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			datosModif = new MDMDatosEntrada();
			datosModif.setErrorFormGeneral(e.getMessage());
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			datosModif = new MDMDatosEntrada();
			datosModif.setErrorFormGeneral(e.getMessage());
		} catch (PersonaFisicaNoEncontradaException e) {
			this.log.error(e);
			datosModif = new MDMDatosEntrada();
			datosModif.setErrorFormGeneral(e.getMessage());
		}
		
		model.addAttribute("mdmDatosEntrada", datosModif);
		model.addAttribute("domicilio", datosModif.getPersonaFisica().getDomicilios().get(0));
    	
    	return "wizardModifDomParticularFisicaContenido";
	} 
	
	@RequestMapping(value = "/finalizar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> finalizarSolicitudModificacionDomicilio(@PathVariable Long idSolicitud,
			@RequestBody MDMDatosEntrada mdmDatosEntrada, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) { 
		Map<String, Object> result = new HashMap<String, Object>();
		
		Solicitud solicitudReq = new Solicitud();
		solicitudReq.setSolicitudId(idSolicitud);
		
		try {
			// Se encola la solicitud para su procesamiento
			FirmaElectronica firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
			
			Solicitud solicitud = solicitudBusinessRemote.consultar(solicitudReq);
			mdmDatosEntrada = this.comprobarCambiosDomicilio(mdmDatosEntrada);

			solicitudPersonaBusiness.finalizarSolicitudModificacionDatosPersona(solicitud, mdmDatosEntrada, firmaElectronica);
			result.put("mensaje", "Su solicitud esta siendo procesada. Por favor espere");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (SolicitudException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		}
		
		return result;
	}
	
	private MDMDatosEntrada comprobarCambiosDomicilio(MDMDatosEntrada datos) {
		
		if(datos.getPersonaFisica() != null) {
			Domicilio dom = datos.getPersonaFisica().getDomicilios().get(0);
			
			if (dom.getTipoBusquedaVialidad() != null) {
				if (dom.getTipoBusquedaVialidad() != null
						&& dom.getTipoBusquedaVialidad().equals(
								TipoBusquedaVialidadEnum.CAMINO.getCodigo())) {
					dom.setDomicilioCarretera(null);
					dom.setVialidadPrimaria(null);
				} else if(dom.getTipoBusquedaVialidad().equals(TipoBusquedaVialidadEnum.CARRETERA.getCodigo())) {
					dom.setDomicilioCamino(null);
					dom.setVialidadPrimaria(null);
				} else {
					dom.setDomicilioCarretera(null);
					dom.setDomicilioCamino(null);
				}
			} else {
				dom.setDomicilioCarretera(null);
				dom.setDomicilioCamino(null);
			}
			
			datos.getPersonaFisica().setDomicilios(new ArrayList<Domicilio>());
			datos.getPersonaFisica().getDomicilios().add(dom);
		} else {
			Domicilio dom = datos.getPersonaMoral().getDomicilios().get(0);
			
			if (dom.getTipoBusquedaVialidad() != null) {
				if (dom.getTipoBusquedaVialidad() != null
						&& dom.getTipoBusquedaVialidad().equals(
								TipoBusquedaVialidadEnum.CAMINO.getCodigo())) {
					dom.setDomicilioCarretera(null);
					dom.setVialidadPrimaria(null);
				} else if(dom.getTipoBusquedaVialidad().equals(TipoBusquedaVialidadEnum.CARRETERA.getCodigo())) {
					dom.setDomicilioCamino(null);
					dom.setVialidadPrimaria(null);
				} else {
					dom.setDomicilioCarretera(null);
					dom.setDomicilioCamino(null);
				}
			} else {
				dom.setDomicilioCarretera(null);
				dom.setDomicilioCamino(null);
			}
			
			datos.getPersonaMoral().setDomicilios(new ArrayList<Domicilio>());
			datos.getPersonaMoral().getDomicilios().add(dom);
		}
			
		return datos;
	}
	
	@RequestMapping(value = "/guardar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> guardarSolicitudModificacionDomicilio(@PathVariable Long idSolicitud,
			@RequestBody MDMDatosEntrada datosModif, HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {
		Solicitud solicitudReq = new Solicitud();
		solicitudReq.setSolicitudId(idSolicitud);
	
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Solicitud solicitud = solicitudBusinessRemote.consultar(solicitudReq);
			this.solicitudPersonaBusiness.guardarSolicitudModificacionDatosPersona(solicitud, datosModif);
			result.put("mensaje", "La solicitud se ha guardado exitosamente.");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", e.getMessage());
		}
		
		return result;
		
	}
	
	@RequestMapping(value = "/cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudModificacionDomicilio(@PathVariable Long idSolicitud,
			HttpServletResponse response, HttpServletRequest request) { 
	
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Solicitud solicitud = this.solicitudPersonaBusiness.cancelarSolicitud(idSolicitud);
			
			result.put("mensaje", "La solicitud fue cancelada correctamente");
			result.put("solicitud", solicitud);
			
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: " + e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value = "/validar", method = RequestMethod.POST)
	public  @ResponseBody Map<String, ? extends Object> validarDomicilio(
			@RequestBody MDMDatosEntrada mdmDatosEntrada, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) { 
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Domicilio domicilio = mdmDatosEntrada.getPersonaFisica().getDomicilios().get(0);
		final Errors errors = new BindException(domicilio, "model");
		new DomicilioConcluirValidator().validate(domicilio,errors);
		
		if(errors.hasErrors()){
			procesaErroresDeCaptura(errors, result, response);
		}
		
		return result;
	}

	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody Domicilio limpiarSessionWizard(
			final HttpSession session) {
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_RFC_SOLICITANTE);
		session.removeAttribute(KEY_TIPO_NUEVA_VIALIDAD);
		session.removeAttribute(KEY_TIPO_BUSQUEDA);
		
		return null;
	}

	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}

	private void generarCadenaOriginal(Solicitud solicitud, Persona persona, HttpSession session) {
		Date fechaSistema = Calendar.getInstance().getTime();
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
		String strFechaElectronica = dateFormat.format(fechaSistema);
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(fechaSistema);

		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");

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

		// Registro Patronal(No aplica)
		contenidoAFirmar.append("Registro Patronal:|");

		// NSS(No aplica)
		contenidoAFirmar.append("Numero de Seguridad Social:||");

		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
	}

	private void obtenerDatosAcuse(Solicitud solicitud, Persona persona, HttpSession session) {
		Date fechaSistema = Calendar.getInstance().getTime();
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);

		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(fechaSistema);
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(fechaSistema);

		// RFC
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
		datosEntradaFirma.setNombreCompleto(sbnombre.toString());

		// CURP
		if (persona instanceof Fisica) {
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		}

		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
}