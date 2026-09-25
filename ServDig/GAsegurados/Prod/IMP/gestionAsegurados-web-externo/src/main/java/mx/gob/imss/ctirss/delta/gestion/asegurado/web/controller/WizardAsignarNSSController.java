package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

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
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.GenerarNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.nss.ModuloOrigenAsignacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.web.validator.WizardAsignacionNSSValidator;
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

@Controller
@RequestMapping(value = "/wizard/nss")
public class WizardAsignarNSSController extends AbstractController {

	private static final String PERSONA_CON_FIEL_SESSION_KEY = "CON_FIEL";
	private static final String PERSONA_SESSION_KEY = "FISICA_NSS";
	private static final String SOLICITUD_SESSION_KEY = "SOLICITUD_NSS";
	private static final String IS_PREREGISTRO_SESSION_KEY = "IS_PREREGISTRO";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";
	
	private static final String DESC_TIPO_SOLICITUD = "ASIGNACION DE NSS";
	
	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	
	@RequestMapping(value="/initTest", method = RequestMethod.GET)
	public String initTestAsignacionNSS() {
		return "wizardAsignacionNSSInitTest";
	}
	
	@RequestMapping(value = "/{idPersona}/{curp}", method = RequestMethod.GET)
	public String initAsignacionNSS(Model model, @PathVariable Long idPersona,
			@PathVariable String curp, HttpServletRequest request,
			HttpSession session) {
		
		
		this.log.info("ID de la persona para NSS ->" + idPersona);
		this.log.info("CURP de la persona para NSS ->" + curp);
		
		
		if (curp.equals("SIN_CURP")) {
			model.addAttribute("SIN_CURP", true);
		} else {
		
			Fisica fisica = new Fisica();
			fisica.setIdPersona(idPersona);
			fisica.setCurp(curp);
			
			model.addAttribute("fisica", fisica);
			
			session.setAttribute(PERSONA_SESSION_KEY, fisica);
			session.setAttribute(PERSONA_CON_FIEL_SESSION_KEY, true);
	
			session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ASIGNACION_NSS.getValor());
			session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);
			
			List<Integer> listTipoTramite = new ArrayList<Integer>();
			listTipoTramite.add(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo());
			session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
		}

		return "wizardAsignacionNSSInit";
	}
	
	@RequestMapping(value = "/captura", method = RequestMethod.GET)
	public String initCapturaAsignacionNSS(Model model, HttpSession session,
			HttpServletRequest request) {
		
		Fisica fisica = new Fisica();
		
		model.addAttribute("fisica", fisica);
		
		return "wizardAsignacionNSSContenido";
	}
	
	@RequestMapping(value = "/captura/validar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validarCapturaAsignacionNSS(Model model,
			@RequestBody Fisica fisica, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		 
		final Errors errors = new BindException(fisica, "model");
		new WizardAsignacionNSSValidator().validate(fisica, errors);
		
		if(errors.hasErrors()){
			procesaErroresDeCaptura(errors, result, response);
		} else {
			session.setAttribute(PERSONA_SESSION_KEY, fisica);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/captura/procesar", method = RequestMethod.POST)
	public String procesarCapturaAsignacionNSS(Model model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {

		String view = "wizardAsignacionNSSConfirmacion";
		Fisica fisica = (Fisica) session.getAttribute(PERSONA_SESSION_KEY);
		Solicitud solicitud = null; 
		boolean isPreregistro = false;
		
		try {
			Map<String, Object> respuesta = this.serviceBusiness
					.generarSolicitudAsignacionNSS(fisica,
							OrigenSolicitudEnum.INTERNET,
							ModuloOrigenAsignacionEnum.INTERNET);
		
			solicitud = (Solicitud) respuesta.get("SOLICITUD");
			
			fisica = solicitud.getSujetoObligado().getFisica();

			Fisica objfisicaRecuperado = this.serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(fisica.getIdPersona());

			// Datos para la firma digital
			generarCadenaOriginal(solicitud, objfisicaRecuperado, session);
			session.setAttribute(KEY_RFC_SOLICITANTE, objfisicaRecuperado.getRfc());
			
			model.addAttribute("asentamiento", fisica.getDomicilios().get(0).getAsentamiento());
			
			session.setAttribute(IS_PREREGISTRO_SESSION_KEY, isPreregistro);
			session.setAttribute(SOLICITUD_SESSION_KEY, solicitud);
		} catch (GenerarNSSException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral("Su datos no pudieron ser validados dentro del Instituto, para poder realizar el trámite solicitado, será necesario acudir a una subdelegación");
		} catch (PersonaConNSSException e) {
			request.setAttribute("NSS_RECUPERADO", true);
			this.log.info(e);
			fisica.setErrorFormGeneral(e.getFisica().getNss());
		} catch (ClienteWebserviceRenapoCurpException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
		} catch (ErrorComparacionDatosRENAPOException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
		} catch (PersonaFisicaNoEncontradaException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
		} catch (SolicitudException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral(e.getMessage());
		} catch (DomicilioNoLocalizadoException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral("Usted no cuenta con un domicilio particular, para poder continuar es necesario que de de alta un domicilio particular a través de este mismo portal.");
		} catch (UmfNoLocalizadaException e) {
			this.log.error(e);
			fisica.setErrorFormGeneral("No se encontraron UMF asociadas a su domicilio particular, para continuar con el trámite es necesario que acuda a una Subdelegación");
		} 
		
		model.addAttribute("fisica", fisica);
		
		return view;
	}
	
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public String finalizarAsignacionNSS(Model model,
			@ModelAttribute UnidadMedicaFamiliar umf, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		
		Solicitud solicitud = (Solicitud) session.getAttribute(SOLICITUD_SESSION_KEY);
		
		try {
			FirmaElectronica firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);

			solicitud.setFirmadaDigitalmente(true);
			/*
			 * Se manda a guardar en la base datos la solicitud que está en
			 * sesión
			 */
			
			
			//se setea la persona que esta realizando el tramite con su CURP
			Usuario usuario = new Usuario();
			for (Tramite tramite : solicitud.getTramites()) {
				if (tramite instanceof TramiteAsegurado) {
					TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
					usuario.setUsuario(tramiteAsegurado.getFisica().getCurp());
				}
			}
			solicitud.setSolicitante(usuario);

			
			solicitud = this.serviceBusiness.guardarSolicitudAsignacionNSS(solicitud, umf, firmaElectronica);
			
			// Se manda a procesar la solicitud, es decir, a calcular el NSS
			this.serviceBusiness.procesarSolicitudAsignacionNSS(solicitud.getSolicitudId());
			
			for (Tramite tramite : solicitud.getTramites()) {
				if (tramite instanceof TramiteAsegurado) {
					TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
					
					String nss = tramiteAsegurado.getFisica().getNss();
					
					model.addAttribute("NSS_ASIGNADO", nss);
				}
			}
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (AfectacionDatosPersonaException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (PersonaNoEncontradaException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (RegistroPersonaFisicaException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (NivelDeAsignacionSerieIndefinidoException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (SeriesNoLocalizadasException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (ErrorAlActivarSerieException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (DomicilioNoValidoException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (ClienteWebserviceRenapoCurpException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		} catch (PersonaSinCalificacionesException e) {
			this.log.error(e);
			solicitud.setErrorFormGeneral(e.getMessage());
			model.addAttribute("HUBO_ERROR", true);
			this.serviceBusiness.cancelar(solicitud);
		}
		
		model.addAttribute("solicitud", solicitud);
		
		return "wizardAsignacionNSSFin";
	}
	
	@RequestMapping(value = "/finalizarOSBTmp/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Fisica concluirTmpOSBAsignacionNSS(@PathVariable Long idSolicitud) {
		
		try {
			this.serviceBusiness.procesarSolicitudAsignacionNSS(idSolicitud);
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
		} catch (AfectacionDatosPersonaException e) {
			this.log.error(e);
		} catch (PersonaNoEncontradaException e) {
			this.log.error(e);		
		} catch (RegistroPersonaFisicaException e) {
			this.log.error(e);
		} catch (NivelDeAsignacionSerieIndefinidoException e) {
			this.log.error(e);
		} catch (SeriesNoLocalizadasException e) {
			this.log.error(e);
		} catch (ErrorAlActivarSerieException e) {
			this.log.error(e);
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
		} catch (DomicilioNoValidoException e) {
			this.log.error(e);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			this.log.error(e);
		} catch (ClienteWebserviceRenapoCurpException e) {
			this.log.error(e);
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			this.log.error(e);
		} catch (PersonaSinCalificacionesException e) {
			this.log.error(e);
		} catch (SolicitudNoValidaException e) {
			this.log.error(e);
		}
		
		return null;
	}
	
	@RequestMapping(value = "/limpiar", method = RequestMethod.POST)
	public @ResponseBody Fisica limiarSesionAsignacionNSS(Model model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		
		session.removeAttribute(PERSONA_SESSION_KEY);
		session.removeAttribute(SOLICITUD_SESSION_KEY);
		session.removeAttribute(PERSONA_CON_FIEL_SESSION_KEY);
		session.removeAttribute(IS_PREREGISTRO_SESSION_KEY);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		
		return null;
	}

	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		log.info("Se almacenan los datos de la firma digital de forma temporal" + firmaElectronica);
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

		// TODO Folio (Validar si aplica)
		contenidoAFirmar.append("Folio:|");
		//contenidoAFirmar.append(solicitud.getNoFolioSolicitud()).append("|");

		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");
		datosEntradaFirma.setRfc(persona.getRfc());

		// Nombre, denominacion o razon social del interesado (y en su caso el de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			sbnombre.append(((Fisica)persona).getNombre()).append(" ");
			sbnombre.append(((Fisica)persona).getPrimerApellido()).append(" ");
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
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
}
