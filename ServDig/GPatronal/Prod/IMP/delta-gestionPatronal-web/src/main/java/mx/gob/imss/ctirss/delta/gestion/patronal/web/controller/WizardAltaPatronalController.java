package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.math.BigDecimal;
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

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.RifException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.PatronExistenteMunicipioFraccionModalidadException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CommonValidator;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.InstanceofPredicate;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.FraccionEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.web.validator.PersonaAutorizadaValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jfree.base.ClassPathDebugger;
import org.jfree.util.Log;
import org.springframework.aop.interceptor.DebugInterceptor;
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
@RequestMapping(value = "/wizard/tramite/registro/patronal/")
public class WizardAltaPatronalController extends AbstractController {


	private static final String KEY_SUJETO_TRAMITE = "sujetoTramite";
	private static final String KEY_ID_SOLICITUD = "idSolicitud";
	private static final String KEY_FOLIO_SOLICITUD = "folioSolicitud";
	private static final String KEY_ES_OPERADOR = "esOperador";
	private static final String KEY_SUJETO_OBLIGADO = "sujetoObligado";
	private static final String KEY_SOLICITUD = "solicitudTramite";
	private static final String ICA_SESSION_KEY = "icaDatosAux";
	private static final String KEY_APLICA_RISS_ALTA_PAT = "aplicaRissAltaPat";
	private static final String KEY_ID_PERSONA = "idPersonaSujeto";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";
	private static final String DATOS_PERSONA_SESION_KEY = "datosPersonaSesionRep";
	private static final String DESC_TIPO_SOLICITUD = "ALTA PATRONAL";
	private static final String KEY_MENSAJE_EXITO = "mensajeExito";
	private static final String KEY_MENSAJE_ERROR = "mensajeError";
	private static final String KEY_MSG_ERROR = "msgError";
	private static final String KEY_ALTA_POR_RL = "isAltaPorRL";
	private static final String KEY_RESPUESTA = "respuesta";
	private static final String KEY_RETOMAR = "isRetomar";
	private static final String KEY_PERSONA_FORMA = "personaForm";
	private static final String KEY_EXISTE_SOL_REGISTRADA = "existeSolRegistrada";
	private static final String KEY_EXISTE_SOL_PROCESO = "existeSolProceso";
	private static final String KEY_SOLICITUD_MISMO_ORIGEN = "solicitudMismoOrigen";
	private static final String KEY_IND_RCP_INVALIDO = "indRPCInvalido";
	private static final String KEY_IND_REINTENTOS_RCP = "indReintento";
	private static final String KEY_LISTA_RLS = "listaRLs";
	private static final String KEY_RL_REQUERIDO = "rlRequerido";
	private static final String KEY_DESC_ORIGEN_SOLICITUD = "descripcionOrigenSolicitud";
	private static final String KEY_TRAMITE_MOSTRAR = "tramiteMostrar";
	private static final String KEY_REPRESENTADO_SESSION 	= "_RepresentadoSession";

	private static final String KEY_MENSAJE_SOLICITUD_ACTUALIZADA = "Se ha actualizado la solicitud.";
	private static final int PASO_CAPTURA_DOMICILIO = 1;
	private static final int PASO_CAPTURA_CLASIFICACION = 2;
	private static final int PASO_CAPTURA_COMPLEMENTO_CLASIFICACION = 3;
	private static final int PASO_MOSTRAR_RL_VENTANILLA = 4;	//Aplica para Alta PM Ventanilla
	private static final String NUM_MODALIDAD_CIUDAD = "10";
	private static final String NUM_MODALIDAD_CAMPO = "13";
	private static final Long ID_DIVISION_CAMPO = 3L;
	private static final Long ID_DIVISION_CIUDAD = 1L;
	private static final String DIVISION_CAMPO = "0";
	private static final Long ID_DIVISION_CAMPO_CANERO = 12L;
	private static final String NUM_MODALIDAD_CAMPO_CANERO = "30";


	private static final String MOSTRAR_AP_INIT = "wizardAltaPatronalInit";
	private static final String MOSTRAR_AP_ICA = "wizardAltaPatronalICA";
	private static final String MOSTRAR_AP_ICA_MORAL = "wizardAltaPatronalICAMoral";
	private static final String MOSTRAR_AP_DOMICILIO_CT = "wizardAltaPatronalDomicilioCT";
	private static final String MOSTRAR_AP_CLASIFICACION = "wizardAltaPatronalClasificacion";
	private static final String MOSTRAR_AP_COMPLEMENTO_CLASIFICACION = "wizardAltaPatronalComplementoClasif";
	private static final String MOSTRAR_RL_VENTANILLA = "wizardRepresentantesVentanilla";
	
	private static final String SIT_CONT_SESSION_KEY = "sitCont";
	private static final String SIT_CONT_SESSION_COD_KEY = "sitContCod";


	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	@Autowired
	private WizardClasificacionController wizardClasificacionController;
	@Autowired
	private AfiliacionServiceBusinessRemote afiliacionService;
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@Autowired
	private PersonaMoralBusinessRemote personaMoralServiceBusiness;
	@Autowired
	private RuleServiceBusinessRemote ruleServiceBusiness;
	@Autowired
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@Autowired
	private ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaFisicaServiceBusinessRemote;
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	@Autowired
	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusiness;
	
	
	

	@RequestMapping(value = "/{cveIdPersona}/{cveTipoPersona}", method = RequestMethod.GET)
	public String initAltaPatronal(Model model, HttpSession session, HttpServletRequest request,
			@PathVariable Long cveIdPersona, @PathVariable Integer cveTipoPersona) {

		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		String view = MOSTRAR_AP_INIT;
		try {
			if(new CommonValidator().esSolicitudInternet(idOrigen)){
				UsuarioSSO sso = this.procesarUsuarioSSO(request);
				wizardClasificacionController.getUsuarioSesion(sso, session);
				Fisica personaSesion = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
				session.setAttribute(DATOS_PERSONA_SESION_KEY, personaSesion);
			}
			if(cveTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()){
				return obtenerDatosPersonaFisica(cveIdPersona, cveTipoPersona.longValue(),session, request, model, idOrigen);
			}else{
				return obtenerDatosPersonaMoral(cveIdPersona, cveTipoPersona.longValue(), session, request, model, idOrigen);
			}
		} catch (PersonaFisicaNoEncontradaException e) {
			this.log.error("ocurrio un error al consultar las solicitudes abiertas para la persona "
					+ "[" + cveIdPersona + "]", e);
		}
		return view;
	}

	/**
	 * Separaci�n de la l�gica de persona f�sica y moral
	 * @param cveIdPersona
	 * @param cveTipoPersona
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	private String obtenerDatosPersonaFisica(Long cveIdPersona, Long cveTipoPersona,HttpSession session,
			HttpServletRequest request, Model model, Long origenSolicitud){

		String view = MOSTRAR_AP_INIT;
		Fisica fisica = new Fisica();
		ICADatosConsulta datosEntrada = new ICADatosConsulta();
		fisica.setIdPersona(cveIdPersona);
		fisica.setTipoPersona(new TipoPersona());
		fisica.getTipoPersona().setIdTipoPersona(cveTipoPersona);
		datosEntrada.setPersonaFisica(fisica);
		datosEntrada.setIndicadorConsultaRENAPO(Boolean.TRUE);
		datosEntrada.setIndicadorConsultaSAT(Boolean.TRUE);
		datosEntrada.setIndicadorMostrarPantalla(Boolean.TRUE);
		view = MOSTRAR_AP_INIT;
		// Se busca si existen solicitudes en proceso pendientes
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		boolean solicitudMismoOrigen = false;
		Solicitud solicitudActiva = null;

		try {
			solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudRegistrada(cveIdPersona,
					cveTipoPersona, TipoSolicitudEnum.ALTA_PATRONAL, TipoTramiteEnum.ALTA_SRT);

			if (solicitudActiva != null
					&& solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitudActiva, origenSolicitud);

				model.addAttribute(KEY_ID_SOLICITUD, solicitudActiva.getSolicitudId());
				model.addAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
				session.setAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
				model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(cveIdPersona,
						cveTipoPersona.longValue(), TipoSolicitudEnum.ALTA_PATRONAL, TipoTramiteEnum.ALTA_SRT);
				if (solicitudActiva != null
						&& solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;

					model.addAttribute(KEY_ID_SOLICITUD, solicitudActiva.getSolicitudId());
					model.addAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
					session.setAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
					model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
				} else {
					solicitudActiva = new Solicitud();
				}
			}
		} catch (SolicitudException e) {
			this.log.error("ocurrio un error al consultar las solicitudes abiertas para la persona [" + cveIdPersona + "]", e);
		}
		model.addAttribute(KEY_PERSONA_FORMA, fisica);
		request.setAttribute(KEY_EXISTE_SOL_REGISTRADA, existeSolRegistrada);
		request.setAttribute(KEY_EXISTE_SOL_PROCESO, existeSolProceso);
		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ALTA_PATRONAL.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);

		view = MOSTRAR_AP_INIT;
		return view;
	}


	/**
	 * Separaci�n de la l�gica de persona f�sica y moral
	 * @param cveIdPersona
	 * @param cveTipoPersona
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	private String obtenerDatosPersonaMoral(Long cveIdPersona, Long cveTipoPersona,HttpSession session,
			HttpServletRequest request, Model model, Long origenSolicitud){

		String view = MOSTRAR_AP_INIT;
		Moral moral = new Moral();
		ICADatosConsulta datosEntrada = new ICADatosConsulta();
		moral.setIdPersona(cveIdPersona);
		moral.setTipoPersona(new TipoPersona());
		moral.getTipoPersona().setIdTipoPersona(cveTipoPersona);
		datosEntrada.setPersonaMoral(moral);
		datosEntrada.setIndicadorConsultaSAT(Boolean.TRUE);
		datosEntrada.setIndicadorMostrarPantalla(Boolean.TRUE);
		view = MOSTRAR_AP_INIT;
		// Se busca si existen solicitudes en proceso pendientes
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		boolean solicitudMismoOrigen = false;
		Solicitud solicitudActiva = null;

		try {
			solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudRegistrada(cveIdPersona,
					cveTipoPersona, TipoSolicitudEnum.ALTA_PATRONAL, TipoTramiteEnum.ALTA_SRT_PM);

			if (solicitudActiva != null
					&& solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitudActiva, origenSolicitud);

				model.addAttribute(KEY_ID_SOLICITUD, solicitudActiva.getSolicitudId());
				model.addAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
				session.setAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
				model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(cveIdPersona,
						cveTipoPersona.longValue(), TipoSolicitudEnum.ALTA_PATRONAL, TipoTramiteEnum.ALTA_SRT_PM);
				if (solicitudActiva != null
						&& solicitudActiva.getSolicitudId() != null) {
					existeSolProceso = true;
					model.addAttribute(KEY_ID_SOLICITUD, solicitudActiva.getSolicitudId());
					model.addAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
					session.setAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
					model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
				} else {
					solicitudActiva = new Solicitud();
				}
			}
		}catch (SolicitudException e) {
			this.log.error("ocurrio un error al consultar las solicitudes abiertas para la persona [" + cveIdPersona + "]", e);
		}
		model.addAttribute(KEY_PERSONA_FORMA, moral);
		request.setAttribute(KEY_EXISTE_SOL_REGISTRADA, existeSolRegistrada);
		request.setAttribute(KEY_EXISTE_SOL_PROCESO, existeSolProceso);
		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ALTA_PATRONAL.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);

		view = MOSTRAR_AP_INIT;
		return view;
	}

	@RequestMapping(value = "/validarIniciarRetomarAltaFisica/{cveIdPersona}", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> validarIniciarRetomarAltaFisica(HttpSession session,
			HttpServletRequest request, @PathVariable Long cveIdPersona) {

		Map<String, Object> result = new HashMap<String, Object>();
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		Usuario usuario = new CommonValidator().getUsuarioSession(session);
		boolean aplicaRIF = true;
		Fisica fisica = personaBusiness.getPersonaFisica(cveIdPersona);
		if(idOrigen.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())){
			//Valida si ya cuenta con Usuario FIEL
			if(solicitudPersonaBusiness.existeTramiteRegistroUsuario(fisica.getCurp())){
				result.put(KEY_MENSAJE_ERROR, "Este tr\u00e1mite debe realizarlo en el Portal con Fiel.");
				return result;
			}
		}

		try {
			beneficioRissServiceBusiness.validaIncorporacionBeneficioRissAltaPatronal(
				fisica, idOrigen, (usuario != null ? usuario.getUsuario() : null));
			result.put(KEY_MENSAJE_EXITO, "RISS");
		} catch (RifException re) {
			aplicaRIF = false;
			//Solo para CIUDADANO impedir Alta Patronal, para el resto permitir continuar.
			if(idOrigen.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())){
				result.put(KEY_MENSAJE_ERROR, re.getMessage());
			}else{
				result.put(KEY_MENSAJE_EXITO, "RISS");
			}
		} catch (BeneficioRissException nre) {
			//Solo para CIUDADANO impedir Alta Patronal, para el resto permitir continuar.
			if(idOrigen.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId())){
				result.put(KEY_MENSAJE_ERROR, nre.getMessage());
			}else{
				result.put(KEY_MENSAJE_EXITO, "RISS");
			}
		}
		session.setAttribute(KEY_APLICA_RISS_ALTA_PAT, aplicaRIF);

		return 	result;
	}

	@RequestMapping(value = "/crear/solicitud", method = RequestMethod.POST)
	public String crearSolicitudAltaPatronal(Model model,
			final HttpSession session, HttpServletRequest request,
			@ModelAttribute Persona persona) {
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		ICADatosRespuesta icaDatosRespuesta = null;

		
		String view = MOSTRAR_AP_ICA;		
		if(!persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())){
			view = MOSTRAR_AP_ICA_MORAL;
		}

		this.log.debug("entre a buscar la info con el id persona "
				+ persona.getIdPersona());
		session.setAttribute(KEY_ID_PERSONA, persona.getIdPersona());

		Solicitud solicitud = null;
		Tramite tramiteDatosGenerales = null;
		Tramite tramitePersona = null;
		session.setAttribute(KEY_ES_OPERADOR, true);
		// Se inicializa objeto de Sujeto Obligado de Tramite
		SujetoObligado sujetoTramite = inicializaInformacionActividadEconomica(new SujetoObligado());
		// Objeto para la forma auxiliar para invocar al servicio del ICA
		ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
		boolean isFisica = true;
		Fisica objfisicaRecuperado = null;
		Moral objMoralRecuperado = null;
		boolean RFCpermitido = true;

		try {
			
			///VALIDACION PARA INHABILITAR TRAMITE DE ALTAS PF - WO458169 
			// Se comenta para habilitar de nuevo tramite por Mm WO465028 - Implementaci󮠤e reglas y funcionalidad para la situaci󮠤el contribuyente en el SAT
//			if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())){
//				log.debug(":::Error: provocado por inhabilitacion de altas PF");
//				throw new ErrorComparacionDatosSATException();
//			}
			
			try {
			// se setean los datos requeridos para hacer la consulta de
						// informaci�n completa
			icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
			icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.TRUE);

			session.setAttribute(SIT_CONT_SESSION_KEY, false); //variable para mensaje de error en situacion del contribuyente
			
			if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())){
				log.debug("::: Voy a buscar a la PF :" + persona.getIdPersona());
				objfisicaRecuperado = this.serviciosPersonaBusiness
						.buscarPersonaFisicayDPyDyMCEnIMSS(persona.getIdPersona());
				persona.setRfc(objfisicaRecuperado.getRfc());
				
				// mm Alta Comprobar que RFC no este en tabla de rfc bloqueados
				RFCpermitido = sujetoObligadoServiceBusiness.esRfcPermitido(persona.getRfc());

				if(!RFCpermitido){
					icaDatosRespuesta = new ICADatosRespuesta();
					this.log.debug("ENTRO A MENSAJE DE ERROR:: " );
					icaDatosRespuesta.setErrorFormGeneral("mensaje de error");
					session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
					return "wizardAltaPatronalRFCBloqueado";
				}

				
				//Mm WO465028 - Implementaci󮠤e reglas y funcionalidad para la situaci󮠤el contribuyente en el SAT
				this.log.debug("::: Revisando situacion del contribuyente para PF, persona.getIdPersona(): " + persona.getIdPersona());
				personaFisicaServiceBusiness.revisaSituacionContribuyente(objfisicaRecuperado);
				
				this.log.debug("::: Identificando cambios ICA");
				icaDatosConsulta.setPersonaFisica(objfisicaRecuperado);
				icaDatosConsulta.setIndicadorConsultaRENAPO(Boolean.TRUE);
				icaDatosRespuesta = personaFisicaServiceBusiness.identificarCambios(icaDatosConsulta);				
				
			}else{
				//Cambio para obtener los datos de la PM - INC110489
				//objMoralRecuperado = this.serviciosPersonaBusiness
					//	.buscarPersonaMoralyDPyDyMCEnIMSS(persona.getIdPersona());
				log.debug("::: Voy a buscar a la PM :" + persona.getIdPersona());
				objMoralRecuperado = serviciosPersonaBusiness.buscarPMyDPyDyMCEnIMSS(persona.getIdPersona());
				
				//Se omite validar situacion del contribuyente para PM a solicitud del usuario INC363844 
				log.debug("::: Se omite validar situacion del contribuyente para PM");
				//Mm WO465028 - Implementaci󮠤e reglas y funcionalidad para la situaci󮠤el contribuyente en el SAT
				//this.log.debug("::: Revisando situacion del contribuyente para PM, persona.getIdPersona(): " + persona.getIdPersona());
				//personaMoralBusiness.revisaSituacionContribuyente(objMoralRecuperado);
				
				isFisica = false;
				persona.setRfc(objMoralRecuperado.getRfc());
				icaDatosConsulta.setPersonaMoral(objMoralRecuperado);
				//Cambio para obtener los datos de la PM - INC110489
				//icaDatosRespuesta = personaMoralBusiness.identificarCambios(icaDatosConsulta);
				
				// mm Alta Comprobar que RFC no este en tabla de rfc bloqueados
				RFCpermitido = sujetoObligadoServiceBusiness.esRfcPermitido(persona.getRfc());

				if(!RFCpermitido){
					icaDatosRespuesta = new ICADatosRespuesta();
					this.log.debug("ENTRO A MENSAJE DE ERROR:: " );
					icaDatosRespuesta.setErrorFormGeneral("mensaje de error");
					session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
					return "wizardAltaPatronalRFCBloqueado";
				}

				icaDatosRespuesta = personaMoralBusiness.identificarCambios_AP(icaDatosConsulta);
			}
			// Se detectan cambios en ICA
//			icaDatosRespuesta = personaFisicaServiceBusiness.integrarCambios(icaDatosRespuesta);
			} catch (ComparacionSinDiferenciasException e1) {
				log.error(e1);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e1.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(
						ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
								.getCodigo(), e1.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			}
			// Se obtienen os tramites de cambio de datos generales en caso de
			// existir cambios
			boolean existeDiferenciasIca = solicitudPersonaBusiness.existenDiferencias(icaDatosRespuesta.getCambios());

			if (existeDiferenciasIca) {
				/*TSe cambia la validacion para diferencias solo valida que no sea de nombre o razon social para persona moral
				 * se ser asi no continua el flujo y arroja una excepcion**/
				if(!isFisica ){

					if(icaDatosRespuesta.getTraza().get("MSG_DIF_RFC") != null){
						icaDatosRespuesta.setErrorFormGeneral("La informaci󮠤e la Persona Moral presenta diferencias entre el IMSS y la entidades externa SAT en el RFC, acuda a la subdelegaci󮠣orrespondiente para regularizar esta informaci󮢩");
						session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
						return view;
					}
				}
				tramiteDatosGenerales = solicitudPersonaBusiness.getTramiteAsociadoByTipoPersona(isFisica, icaDatosRespuesta);
				tramiteDatosGenerales.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ACTUALIZACION_DATOS_USUARIO.getCodigo());
			} else {
				icaDatosRespuesta = new ICADatosRespuesta();
				ComparacionSinDiferenciasException e1 = new ComparacionSinDiferenciasException();
				icaDatosRespuesta.setErrorFormGeneral(e1.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
								.getCodigo(), e1.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			}
			request.setAttribute(KEY_RFC_SOLICITANTE, persona.getRfc());
			sujetoTramite.setDatosICA(icaDatosRespuesta);
			// Se ingresa la persona obtenida en la coprobacion
			if (isFisica) {
				sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);

				if (existeDiferenciasIca) {
					sujetoTramite.setFisica(icaDatosRespuesta.getPersonaFisicaEE());
				} else {
					sujetoTramite.setFisica(objfisicaRecuperado);
				}
			} else {
				sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);

				if (existeDiferenciasIca) {
					Moral objMoralComplementado=icaDatosRespuesta.getPersonaMoralEE();
					//Se agrega la info de datos de sindicato y escritura pues los requerira el arp
					objMoralComplementado.setEscrituraConstitutiva(icaDatosRespuesta.getPersonaMoralIMSS().getEscrituraConstitutiva());
					objMoralComplementado.setRegistroSindicato(icaDatosRespuesta.getPersonaMoralIMSS().getRegistroSindicato());
					sujetoTramite.setMoral(objMoralComplementado);//TODO se adecua pues la entidad externa es la que tiene los datos correctos
				} else {
					sujetoTramite.setMoral(objMoralRecuperado);
				}
			}

			// Se crea y se guarda la solicitud
			Usuario usuario = new Usuario();
			if (idOrigen == OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()) {
				if (isFisica) {
					Fisica fisica = sujetoTramite.getFisica();
					usuario.setUsuario(fisica.getCurp());
				}
			} else {
				UsuarioSSO sso = this.procesarUsuarioSSO(request);
            	String strUsuario = sso.getCurp();
				usuario.setUsuario(strUsuario);
			}

			solicitud = afiliacionService.crearSolicitudDeAltaPatronal(sujetoTramite, usuario,
				OrigenSolicitudEnum.getById(new CommonValidator().getOrigenContext(request)));
			// Se agrega relacion (tipo de tramite) al usuario asociado
			for (Tramite tramiteActual : solicitud.getTramites()) {
				if (isFisica) {
					tramitePersona = new TramiteFisica();
					if (existeDiferenciasIca) {
						((TramiteFisica) tramitePersona).setFisica(icaDatosRespuesta.getPersonaFisicaIMSS());
					} else {
						((TramiteFisica) tramitePersona).setFisica(objfisicaRecuperado);
					}
				} else {
					tramitePersona = new TramiteMoral();

					if (existeDiferenciasIca) {
						((TramiteMoral) tramitePersona).setMoral(icaDatosRespuesta.getPersonaMoralIMSS());
					} else {
						((TramiteMoral) tramitePersona).setMoral(objMoralRecuperado);
					}
				}
				tramitePersona.setTramiteId(tramiteActual.getTramiteId());
				tramitePersona.setTipoTramite(tramiteActual.getTipoTramite());
				tramitePersona.getTipoTramite().setIdTipoTramite(tramiteActual.getTipoTramite().getIdTipoTramite());
				tramitePersona.setFechaTramite(tramiteActual.getFechaTramite());
				tramitePersona.setEstadoTramite(tramiteActual.getEstadoTramite());
				tramitePersona.setFechaPresentacion(Calendar.getInstance().getTime());
				tramitePersona.getEstadoTramite().setIdEstadoTramitePersona(
						tramiteActual.getEstadoTramite().getIdEstadoTramitePersona());
				tramitePersona.setAcuseVentanilla(tramiteActual.getAcuseVentanilla());
				solicitudServiceBusiness.agregarTramiteASolicitud(solicitud.getSolicitudId(), tramitePersona);
			}
			if (tramiteDatosGenerales != null) {
				solicitud.getTramites().add(tramiteDatosGenerales);
				solicitudServiceBusiness.agregarTramiteASolicitud(solicitud.getSolicitudId(), tramiteDatosGenerales);
			}
			// Datos del acuse
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				Fisica personaRecuperada=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				session.removeAttribute(DATOS_PERSONA_SESION_KEY);
				obtenerDatosAcuse(solicitud, personaRecuperada, session);
				// Datos para la firma digital
				if (isFisica)
					generarCadenaOriginal(solicitud, objfisicaRecuperado, session);
				else
					generarCadenaOriginal(solicitud, objMoralRecuperado, session);

			}
			session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			model.addAttribute(KEY_SUJETO_OBLIGADO, sujetoTramite);
			session.setAttribute(KEY_SUJETO_OBLIGADO, sujetoTramite);
			// Se suben a sesion los datos de la solicitud
			session.setAttribute(KEY_IND_RCP_INVALIDO, false);
			session.setAttribute(KEY_IND_REINTENTOS_RCP, false);
			session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			session.setAttribute(KEY_SUJETO_OBLIGADO, sujetoTramite);
			session.setAttribute(KEY_RETOMAR, false);
			if (isFisica)
				session.setAttribute(KEY_RFC_SOLICITANTE, objfisicaRecuperado.getRfc());
			else
				session.setAttribute(KEY_RFC_SOLICITANTE, objMoralRecuperado.getRfc());

			session.setAttribute(KEY_ID_SOLICITUD, solicitud.getSolicitudId());
			session.setAttribute(KEY_FOLIO_SOLICITUD,
					solicitud.getNoFolioSolicitud());
		} catch (PersonaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.CURP_NO_LOCALIZADO
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_VALIDAR_DATOS_ENTIDAD_EXTERNA
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.RFC_NO_LOCALIZADO
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_COMPARACION_DATOS_RENAPO
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (DatosInsuficientesICAException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DATOS_INSUFICIENTES_ICA
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (DiferenciasRENAPOContraSAT e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.DIFERENCIAS_RENAPO_SAT
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.PERSONA_FISICA_NO_ENCONTRADA
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_RENAPO
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ClienteWebserviceSatRfcException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_SAT
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(e.getCode().toString(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		} catch (ErrorComparacionDatosSATException e) {
			log.error(e);
			log.error(":::Error en situacion contribuyente: codigo: " + e.getCodigo() + " - " + e.getMessage());
			session.setAttribute(SIT_CONT_SESSION_KEY, true);
			session.setAttribute(SIT_CONT_SESSION_COD_KEY, e.getCodigo());
			icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_SAT.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
		}

		Boolean aplicaRissAltaPat = (Boolean) session.getAttribute(KEY_APLICA_RISS_ALTA_PAT);
		session.setAttribute(KEY_APLICA_RISS_ALTA_PAT, aplicaRissAltaPat);
		session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
		log.debug("::: Regresando a : " + view);
		return view;
	}

	@RequestMapping(value = "/guardar/solicitud/datosIca/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> guardarSolicitudActualizacionDatosIca(
			@PathVariable Long idSolicitud, HttpServletResponse response,
			HttpServletRequest request, HttpSession session) {

		Map<String, Object> result = new HashMap<String, Object>();
		ICADatosRespuesta datosRespuesta = (ICADatosRespuesta) session.getAttribute(ICA_SESSION_KEY);
		try {
			this.solicitudPersonaBusiness.guardarSolicitudActualizacionDatos(
					idSolicitud, datosRespuesta);
			result.put(KEY_MENSAJE_EXITO,
					"La solicitud se ha guardado exitosamente.");
		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/captura/centroTrabajo/domicilio", method = RequestMethod.POST)
	public String capturarDomcilioCentroTrabajo(final HttpSession session,
			HttpServletRequest request, Model model, Locale locale) {

		String view = MOSTRAR_AP_DOMICILIO_CT;
		Integer pasoTramite = PASO_CAPTURA_DOMICILIO;
		// Se obtienen los datos de sesion
		Solicitud solicitud = new Solicitud();
		SujetoObligado sujetoTramite = (SujetoObligado) session
			.getAttribute(KEY_SUJETO_TRAMITE);
		Long idSolicitud = (Long) session.getAttribute(KEY_ID_SOLICITUD);
		Solicitud solicitudTramite = solicitudServiceBusiness
			.consultarSolicitudPorId(idSolicitud);
		convertirTramiteSujetoObligado(solicitudTramite, sujetoTramite,	pasoTramite);
		actualizarFechaPresentacion(solicitudTramite, sujetoTramite);
		// Se actualiza solicitud
		try {
			solicitud = afiliacionService.actualizarSolicitudDeAltaPatronal(
					solicitudTramite, sujetoTramite);
			session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			model.addAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			solicitud = new Solicitud();
			String message = messageSource.getMessage(e.getMessage(),
				null, locale);
			solicitud.setErrorFormGeneral(message);
		}
		session.setAttribute(KEY_SOLICITUD, solicitud);
		return view;
	}

	@RequestMapping(value = "/guardar/solicitud/domicilioCt/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> guardarSolicitudActualizacionDomCT(
			@PathVariable Long idSolicitud, HttpServletResponse response,
			HttpServletRequest request, HttpSession session,
			@RequestBody SujetoObligado sujetoObligado, Locale locale) {

		Map<String, Object> result = new HashMap<String, Object>();
		// Se obtienen los datos de sesion
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute(KEY_SUJETO_TRAMITE);
		Solicitud solicitudTramite = solicitudServiceBusiness
				.consultarSolicitudPorId(idSolicitud);
		sujetoTramite.setCntroTrabajo(sujetoObligado.getCntroTrabajo());
		actualizarFechaPresentacion(solicitudTramite, sujetoTramite);
		// Se actualiza solicitud
		try {
			afiliacionService.actualizarSolicitudDeAltaPatronal(
					solicitudTramite, sujetoTramite);
			session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			result.put(KEY_MENSAJE_EXITO, KEY_MENSAJE_SOLICITUD_ACTUALIZADA);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			String message = messageSource.getMessage(e.getMessage(), null, locale);
			result.put(KEY_MENSAJE_ERROR, message);
		}
		return result;
	}

	@RequestMapping(value = "/captura/clasificacion", method = RequestMethod.POST)
	public String capturarActividadEconomica(final HttpSession session,
			HttpServletRequest request,
			@ModelAttribute SujetoObligado sujetoObligado, Model model,
			Locale locale) {
		Integer pasoTramite = PASO_CAPTURA_CLASIFICACION;
		String view = MOSTRAR_AP_CLASIFICACION;
		Solicitud solicitud = new Solicitud();
		// Se obtienen los datos de sesion
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute(KEY_SUJETO_TRAMITE);
		Long idSolicitud = (Long) session.getAttribute(KEY_ID_SOLICITUD);
		Solicitud solicitudTramite = solicitudServiceBusiness
			.consultarSolicitudPorId(idSolicitud);
		convertirTramiteSujetoObligado(solicitudTramite, sujetoTramite, pasoTramite);
		SujetoObligado sujetoObligadoSesion = (SujetoObligado) session
			.getAttribute(KEY_SUJETO_OBLIGADO);
		// Se agregan los datos del Centro de Trabajo
		sujetoTramite.setCntroTrabajo(sujetoObligado.getCntroTrabajo());
		actualizarFechaPresentacion(solicitudTramite, sujetoTramite);
		// Se actualiza solicitud
		try {
			solicitud = afiliacionService.actualizarSolicitudDeAltaPatronal(
					solicitudTramite, sujetoTramite);
			session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			model.addAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			model.addAttribute(KEY_SUJETO_OBLIGADO, sujetoObligadoSesion);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			solicitud = new Solicitud();
			String message = messageSource.getMessage(e.getMessage(),
				null, locale);
			solicitud.setErrorFormGeneral(message);
		}
		session.setAttribute(KEY_SOLICITUD, solicitud);
		colocarIndicadorRpc(request, session, model, solicitudTramite, sujetoTramite);
		return view;
	}

	@RequestMapping(value = "/actualizar/solicitud/clasificacion", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> actualizarSolicitudClasificacion(Model model,
			HttpServletResponse response, HttpServletRequest request, HttpSession session,
			@RequestBody Clasificacion inputObject, Locale locale) {

		Long idOrigen =  new CommonValidator().getOrigenContext(request);

		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solicitud = new Solicitud();
		try {
			validaFechaEfecto(inputObject.getFecEfecto(), idOrigen);
		} catch (GestionPatronalBusinessException e1) {
			e1.printStackTrace();
			result.put(KEY_MENSAJE_ERROR, e1.getMessage());
			return result;
		}
		// Se obtienen los datos de sesion
		SujetoObligado sujetoTramite = (SujetoObligado) session
			.getAttribute(KEY_SUJETO_TRAMITE);
		Long idSolicitud = (Long) session.getAttribute(KEY_ID_SOLICITUD);
		Solicitud solicitudTramite = solicitudServiceBusiness
			.consultarSolicitudPorId(idSolicitud);
		// Se vacian los datos capturados
		try {
			Proceso proceso = inputObject.getSujetoObligado().getProceso();
			inputObject.getSujetoObligado().setProceso(null);
			SujetoObligado soProceso = new SujetoObligado();
			soProceso.setCveIdSujetoObligado(inputObject.getSujetoObligado()
					.getCveIdSujetoObligado());
			proceso.setSujetoObligado(soProceso);
			sujetoTramite.setProceso(proceso);
			sujetoTramite.setDesAfectacion(inputObject.getSujetoObligado()
					.getDesAfectacion());
			sujetoTramite.setDesUsosBienes(inputObject.getSujetoObligado()
					.getDesUsosBienes());
			sujetoTramite.setCveIdSujetoObligado(inputObject.getSujetoObligado()
					.getCveIdSujetoObligado());
			sujetoTramite.setTipoPersonaFiscal(inputObject.getSujetoObligado()
					.getTipoPersonaFiscal());
			sujetoTramite.setFisica(inputObject.getSujetoObligado().getFisica());
			sujetoTramite.setMoral(inputObject.getSujetoObligado().getMoral());
			if (sujetoTramite.getFisica() != null) {
				Long idPersona = (Long) session.getAttribute(KEY_ID_PERSONA);
				Fisica objfisicaRecuperado = this.serviciosPersonaBusiness
					.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
				sujetoTramite.setFisica(objfisicaRecuperado);
			}
			if (sujetoTramite.getMoral() != null) {
				Long idPersona = (Long) session.getAttribute(KEY_ID_PERSONA);
				//Cambio para obtener los datos de la PM - INC110489
				//Moral objMoralRecuperado = this.serviciosPersonaBusiness
					//.buscarPersonaMoralyDPyDyMCEnIMSS(idPersona);
				log.debug("::: Voy a buscar a la PM :" + idPersona);
				Moral objMoralRecuperado = serviciosPersonaBusiness.buscarPMyDPyDyMCEnIMSS(idPersona);				
				sujetoTramite.setMoral(objMoralRecuperado);
			}
			sujetoTramite.setCveIdSujetoObligado(inputObject.getSujetoObligado()
				.getCveIdSujetoObligado());
			sujetoTramite.setCuentaConTransporte(inputObject.getSujetoObligado()
				.getCuentaConTransporte());
			inputObject.setSujetoObligado(soProceso);
			sujetoTramite.setProceso(proceso);
			Modalidad modalidad = new Modalidad();
			String numDivision = StringUtils.deleteWhitespace(
					inputObject.getFraccion().getGrupo().getDivision().getNumDivision());
			if (numDivision.equals(DIVISION_CAMPO)) {
				String numGrupo = StringUtils.deleteWhitespace(
						inputObject.getFraccion().getGrupo().getNumGrupo());
				String numFraccion = StringUtils.deleteWhitespace(
						inputObject.getFraccion().getNumFraccion());
				boolean esProductorCanero=false;
				StringBuffer numFraccionCompleta = new StringBuffer();
				numFraccionCompleta.append(numDivision).append(numGrupo).append(numFraccion);
				if(numFraccionCompleta.toString().equals(FraccionEnum.AGRICULTURA.getCodigo()))
					if(inputObject.getIndProductorCana()!=null && inputObject.getIndProductorCana()==1)
						esProductorCanero=true;

				if(esProductorCanero){
					modalidad.setIdModalidad(ModalidadEnum.TREINTA.getId());
					modalidad.setNumModalidad(NUM_MODALIDAD_CAMPO_CANERO);
				}else{
					modalidad.setIdModalidad(ID_DIVISION_CAMPO);
					modalidad.setNumModalidad(NUM_MODALIDAD_CAMPO);
				}

			} else {
				modalidad.setIdModalidad(ID_DIVISION_CIUDAD);
				modalidad.setNumModalidad(NUM_MODALIDAD_CIUDAD);
			}
			sujetoTramite.setModalidad(modalidad);
			inputObject.setFecPresentacion(Calendar.getInstance().getTime());
			if (inputObject.getIndPrestaServicioPersonal() != null
					&& inputObject.getIndPrestaServicioPersonal().intValue()==1) {
				Clasificacion clasificacionTramite = sujetoTramite.getClasificacion();
				if (clasificacionTramite != null) {
					inputObject.setNumCentrosTraba(clasificacionTramite.getNumCentrosTraba());
				}
			} else {
				Clasificacion clasificacionTramite = sujetoTramite.getClasificacion();
				if (clasificacionTramite != null) {
					inputObject.setNumCentrosTraba(null);
					inputObject.setIndRegPatClase(null);
				}
			}

			sujetoTramite.setClasificacion(inputObject);
			actualizarFechaPresentacion(solicitudTramite, sujetoTramite);
			solicitud = afiliacionService.actualizarSolicitudDeAltaPatronal(
					solicitudTramite, sujetoTramite);
			session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			session.setAttribute(KEY_SOLICITUD, solicitud);
			result.put("solicitud", solicitud);
			result.put(KEY_MENSAJE_EXITO, KEY_MENSAJE_SOLICITUD_ACTUALIZADA);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			solicitud = new Solicitud();
			String message = messageSource.getMessage(e.getMessage(),
				null, locale);
			solicitud.setErrorFormGeneral(message);
			result.put(KEY_MENSAJE_ERROR, message);
		} catch (PersonaFisicaNoEncontradaException e) {
			log.error(e);
			solicitud.setErrorFormGeneral( e.getMessage());
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		}

		return result;
	}


	private void validaFechaEfecto(Date fechaEfecto, Long idOrigen) throws GestionPatronalBusinessException{

		Calendar calendario = Calendar.getInstance();
		calendario.set(Calendar.HOUR, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		Date fechaActual = calendario.getTime();
		Calendar calendarioEfecto = Calendar.getInstance();
		calendarioEfecto.setTime(fechaEfecto);
		calendarioEfecto.set(Calendar.HOUR, 0);
		calendarioEfecto.set(Calendar.MINUTE, 0);
		calendarioEfecto.set(Calendar.SECOND, 0);
		calendarioEfecto.set(Calendar.MILLISECOND, 0);
		calendarioEfecto.set(Calendar.HOUR_OF_DAY, 0);
		Date fechaEfectoEvaluar=calendarioEfecto.getTime();
		Calendar calendarioAnioAnt = Calendar.getInstance();
		calendarioAnioAnt.setTime(fechaActual);
		calendarioAnioAnt.add(Calendar.DATE, -364);
		calendarioAnioAnt.set(Calendar.HOUR, 0);
		calendarioAnioAnt.set(Calendar.MINUTE, 0);
		calendarioAnioAnt.set(Calendar.SECOND, 0);
		calendarioAnioAnt.set(Calendar.MILLISECOND, 0);
		calendarioAnioAnt.set(Calendar.HOUR_OF_DAY, 0);
		Date fechaAunAnioAnterior = calendarioAnioAnt.getTime();
		if(fechaEfectoEvaluar.after(fechaActual)){
			String message = "La fecha de efecto no puede ser mayor a la fecha actual";
			throw new GestionPatronalBusinessException(message);
		}else if(fechaEfectoEvaluar.before(fechaAunAnioAnterior)
				&& !idOrigen.equals(OrigenSolicitudEnum.VENTANILLA.getId())){
			String message = "La fecha de efecto no puede ser menor a 364 d? de la fecha actual";
			throw new GestionPatronalBusinessException(message);
		}
	}

	@RequestMapping(value = "/captura/clasificacion/complemento", method = RequestMethod.POST)
	public String capturarComplementoClasificacion(final HttpSession session,
			HttpServletRequest request, Model model, Locale locale) {

		String view = MOSTRAR_AP_COMPLEMENTO_CLASIFICACION;
		Solicitud solicitud = new Solicitud();
		Integer pasoTramite = PASO_CAPTURA_COMPLEMENTO_CLASIFICACION;
		// Se obtienen los datos de sesion
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute(KEY_SUJETO_TRAMITE);
		Long idSolicitud = (Long) session.getAttribute(KEY_ID_SOLICITUD);
		Solicitud solicitudTramite = solicitudServiceBusiness
				.consultarSolicitudPorId(idSolicitud);
		convertirTramiteSujetoObligado(solicitudTramite, sujetoTramite,
				pasoTramite);
		List<Integer> listIdTipoTramite = new ArrayList<Integer>();
		for(Tramite tramite : solicitudTramite.getTramites()){
			Integer idTipoTramite = tramite.getTipoTramite().getIdTipoTramite();
			listIdTipoTramite.add(idTipoTramite);
		}
		session.setAttribute(KEY_TIPO_TRAMITE, listIdTipoTramite);
		if (new CommonValidator().esSolicitudInternet(solicitudTramite
				.getOrigenSolicitud().getIdTipoSolicitud())) {
			obtenerRepresentanteLegalFirmado(request, sujetoTramite, null, true);
			// Se sube el indicador de si es rl o no
		}
		colocarIndicadorRpc(request, session, model, solicitudTramite, sujetoTramite);

		try {
			solicitud = afiliacionService.actualizarSolicitudDeAltaPatronal(
					solicitudTramite, sujetoTramite);
			cargarDomicilioFiscaldeICAsoloRPC(sujetoTramite);
			session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			model.addAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			solicitud = new Solicitud();
			String message = messageSource.getMessage(e.getMessage(), null,
					locale);
			solicitud.setErrorFormGeneral(message);
		}

		return view;
	}

	@RequestMapping(value = "/guardar/solicitud/complementoClasif/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> guardarSolicitudComplementoClasificacion(
			@PathVariable Long idSolicitud, HttpServletResponse response,
			HttpServletRequest request, HttpSession session,
			@RequestBody SujetoObligado sujetoObligado, Locale locale) {

		Map<String, Object> result = new HashMap<String, Object>();
		// Se obtienen los datos de sesion
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute(KEY_SUJETO_TRAMITE);
		Solicitud solicitudTramite = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
		// Se obtienen los datos de la subdelegacion, RPC y Num de centros de trabajo
		if (StringUtils.isNotBlank(sujetoObligado.getNombreComercial())) {
			sujetoTramite.setNombreComercial(sujetoObligado.getNombreComercial().toUpperCase());
		}
		if (StringUtils.isNotBlank(sujetoObligado.getTipoPoder())) {
			sujetoTramite.setTipoPoder(sujetoObligado.getTipoPoder());
		}
		if (StringUtils.isNotBlank(sujetoObligado.getOtroPoder())) {
			sujetoTramite.setOtroPoder(sujetoObligado.getOtroPoder().toUpperCase());
		}
		if (sujetoObligado.getClasificacion().getNumCentrosTraba() != null) {
			sujetoTramite.getClasificacion().setNumCentrosTraba(sujetoObligado.getClasificacion().getNumCentrosTraba());
		}
		sujetoTramite.setMunicipioIMSS(sujetoObligado.getMunicipioIMSS());
		if (sujetoObligado.getMunicipioIMSS() != null) {
			sujetoTramite.setSubdelegacion(sujetoObligado.getMunicipioIMSS().getSubdelegacion());
			log.error("Se asigna la subdelegacion a la solicitud: "+sujetoTramite.getSubdelegacion());
			solicitudTramite.setSubdelegacion(sujetoTramite.getSubdelegacion());
		}
		sujetoTramite.setPersonasAutorizadas(sujetoObligado.getPersonasAutorizadas());
		actualizarFechaPresentacion(solicitudTramite, sujetoTramite);
		// Se actualiza solicitud
		try {
			afiliacionService.actualizarSolicitudDeAltaPatronal(solicitudTramite, sujetoTramite);
			session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			result.put(KEY_MENSAJE_EXITO, KEY_MENSAJE_SOLICITUD_ACTUALIZADA);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			String message = messageSource.getMessage(e.getMessage(), null, locale);
			result.put(KEY_MENSAJE_ERROR, message);
		}
		return result;
	}

	/**
	 * @param sujetoTramite
	 * @param idSolicitud
	 * @param response
	 * @param session
	 * @param locale
	 * @return
	 */
	@RequestMapping(value = "/cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> cancelarSolicitudAltaPatronal(
			@PathVariable Long idSolicitud, HttpServletResponse response,
			Locale locale) {

		String message = "";
		Map<String, Object> result = new HashMap<String, Object>();
		log.debug("CANCELAR SOLICITUD [ " + idSolicitud + " ]");
		if (idSolicitud != null) {
			try {
				log.debug("Se cancela la solicitud [ " + idSolicitud + " ]");
				solicitudServiceBusiness.cancelarSolicitud(idSolicitud);
				result.put("mensaje", "La solicitud fue cancelada correctamente");
				result.put("solicitud", idSolicitud);
			}catch(SolicitudException se){
				message = messageSource.getMessage(se.getSituacion(), null, locale);
				result.put("mensaje", message);
			}catch (Exception e) {
				e.printStackTrace();
				message = "Ocurrio un error al intentar cancelar la solicitud.";
				result.put("mensaje", message);
			}
		}
		return result;
	}

	@RequestMapping(value = "/validar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> validarSolicitudAltaPatronal(@PathVariable Long idSolicitud, HttpServletResponse response,
			HttpServletRequest request, HttpSession session,
			@RequestBody SujetoObligado sujetoObligado, Locale locale) {

		Long idOrigen =  new CommonValidator().getOrigenContext(request);

		Map<String, Object> result = new HashMap<String, Object>();
		// Se obtienen los datos de sesion
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute(KEY_SUJETO_TRAMITE);
		sujetoTramite.setMunicipioIMSS(sujetoObligado.getMunicipioIMSS());
		if (sujetoObligado.getMunicipioIMSS() != null) {
			sujetoTramite.setSubdelegacion(sujetoObligado.getMunicipioIMSS().getSubdelegacion());
		}
		try {
			ruleServiceBusiness.validaPatronExistentePorMunicipioFraccionModalidad(sujetoTramite);
		} catch (PatronExistenteMunicipioFraccionModalidadException em) {
			log.error(em);
			result.put(KEY_MENSAJE_ERROR, em.getMessage());
			return result;
		}
		try {
			validaFechaEfecto(sujetoTramite.getClasificacion().getFecEfecto(), idOrigen);
		} catch (GestionPatronalBusinessException e1) {
			Log.error(e1);
			result.put(KEY_MENSAJE_ERROR, e1.getMessage());
			return result;
		}
		try {
			ruleServiceBusiness.validarTipoAmbitoPorMunicipio(sujetoTramite.getMunicipioIMSS().getCvecMunicipioSINDO(), sujetoTramite.getModalidad().getNumModalidad(), sujetoTramite.getClasificacion().getFecEfecto());
		} catch (GestionPatronalBusinessException e1) {
			Log.error(e1);
			String message = messageSource.getMessage(e1.getMessage(), null, locale);
			result.put(KEY_MENSAJE_ERROR, message);
			return result;
		}
		try {
			if(sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
				//se genera un objeto clasif con sujeto obligado dentro pues asi lo requiere la validacion
				Clasificacion clasifValidar=sujetoTramite.getClasificacion();
				if(clasifValidar.getSujetoObligado()==null){
					clasifValidar.setSujetoObligado(new SujetoObligado());
					clasifValidar.getSujetoObligado().setMoral(new Moral());
					clasifValidar.getSujetoObligado().setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
					clasifValidar.getSujetoObligado().getMoral().setRfc(sujetoTramite.getMoral().getRfc());
				}else if(clasifValidar.getSujetoObligado().getMoral()==null){
					clasifValidar.getSujetoObligado().setMoral(new Moral());
					clasifValidar.getSujetoObligado().setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
					clasifValidar.getSujetoObligado().getMoral().setRfc(sujetoTramite.getMoral().getRfc());
				}else if(clasifValidar.getSujetoObligado().getMoral().getRfc()==null
						|| clasifValidar.getSujetoObligado().getTipoPersonaFiscal()==null){
					clasifValidar.getSujetoObligado().setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
					clasifValidar.getSujetoObligado().getMoral().setRfc(sujetoTramite.getMoral().getRfc());
				}

				// Se modifica por INC398780
//				ruleServiceBusiness
//				.validarRPPrevios(
//						sujetoTramite.getMoral().getRfc(),
//						sujetoTramite.getMunicipioIMSS().getCvecMunicipioSINDO(),
//						sujetoTramite.getClasificacion().getFraccion());

				log.debug("::: PM, se validaran registros patronales previos");
				ruleServiceBusiness.validarRPPrevios(sujetoTramite);

				ruleServiceBusiness.validarSociosRequeridos(sujetoTramite.getMoral().getIdPersona());
			}else{
				// Se modifica por INC398780
//				ruleServiceBusiness
//				.validarRPPreviosFisica(
//						sujetoTramite.getFisica().getRfc(),
//						sujetoTramite.getMunicipioIMSS().getCvecMunicipioSINDO());

				log.debug("::: PF, se validaran registros patronales previos");
				ruleServiceBusiness.validarRPPrevios(sujetoTramite);
			}
			
			
			
		} catch (GestionPatronalBusinessException e1) {
			Log.debug("El mensaje de error: " + e1.getMessage());
			Log.error("El mensaje de error: " + e1.getMessage());
			log.error(e1);
			result.put(KEY_MENSAJE_ERROR, e1.getMessage());
			return result;
			
//			Log.error("El mensaje de error: " + e1.getMessage());
//			Log.error(e1);
//			if(e1.getCodigo().equals(801)){
//				Log.debug("::: El error es 801");
//				result.put(KEY_MENSAJE_ERROR, e1.getMessage());
//				return result;
//			}else{
//				Log.debug("::: El error no es 801");
//			}
//			String message = messageSource.getMessage(e1.getMessage(), null, locale);
//			result.put(KEY_MENSAJE_ERROR, message);
//			Log.error("::: Imprimiendo Map de errores: ");
//			for (Map.Entry<String, Object> entry : result.entrySet()) {
//			    Log.error("clave=" + entry.getKey() + ", valor=" + entry.getValue());
//			}
//			return result;
			
		}

		String rfc = "";
		if (sujetoTramite.getFisica() != null) {
			rfc = sujetoTramite.getFisica().getRfc();
			boolean aplicarRIF=ruleServiceBusiness.estaParametroRIFHabilitado();
			System.err.println("Parametro RIF: "+aplicarRIF);
			if(aplicarRIF){
				Fisica persona = sujetoTramite.getFisica();
				boolean indRIF = ruleServiceBusiness.personaConRegimenRIF(persona);
				System.err.println("Persona despues de evaluar RIF: "+persona);
				sujetoTramite.setFisica(persona);//Se agregan los r�gimenes
				sujetoTramite.getFisica().setIndRIF(indRIF);
				result.put("isRIF", indRIF);
			}else{//Se asegura que no aplique tipo de pago 1 para sindo cuando no se tiene configurado RIF
				sujetoTramite.getFisica().setIndRIF(false);
			}
		}
		if (sujetoTramite.getMoral() != null) {
			rfc = sujetoTramite.getMoral().getRfc();
		}
		//Se actualiza el objeto de la solicitud a guardar con la informaci�n de regimen y RIF
		session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
		//Clasificacion clasificacionCapturada = sujetoObligado.getClasificacion();
		if (sujetoTramite.getClasificacion().getIndPrestaServicioPersonal() != null
				&& sujetoTramite.getClasificacion().getIndPrestaServicioPersonal().intValue()==1
				&& sujetoTramite.getClasificacion().getIndRegPatClase() != null
				&& sujetoTramite.getClasificacion().getIndRegPatClase().intValue()==1) {
			try {
				ruleServiceBusiness.validarRPC_AP_MOD_MAC(rfc,
						sujetoTramite.getClasificacion().getFraccion().getClase().getClave());
				String message = "Se ha validado la solicitud.";

				result.put(KEY_MENSAJE_EXITO, message);
			} catch (GestionPatronalBusinessException e) {
				log.error(e);
				String message = messageSource.getMessage(e.getMessage(), null, locale);
				result.put(KEY_MENSAJE_ERROR, message);
			}
		} else {
			String message = "Se ha validado la solicitud.";
			result.put(KEY_MENSAJE_EXITO, message);
		}
		return result;
	}

	@RequestMapping(value = "representantesLegales/{idSolicitud}", method = {RequestMethod.GET, RequestMethod.POST })
	public String obtenerRepresenantesLegales(final Model model, @PathVariable Long idSolicitud,
			HttpSession session, HttpServletRequest request) {
		Solicitud solicitud = solicitudServiceBusiness
				.consultarSolicitudPorId(idSolicitud);
		InstanceofPredicate tramiteSujetoObligadoPredicate = new InstanceofPredicate(TramiteSujetoObligado.class);
		Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramiteSujetoObligadoPredicate);
		TramiteSujetoObligado tramiteAlta = (TramiteSujetoObligado) tramiteInicial;
		SujetoObligado sujetoTramite = tramiteAlta.getSujetoObligado();
		List<RepresentanteLegal> lista = obtenerRepresentantesLegales(session, request, sujetoTramite);
		representanteLegalSelected(sujetoTramite, lista);
		model.addAttribute(KEY_LISTA_RLS, lista);

		return MOSTRAR_RL_VENTANILLA;
	}

	@RequestMapping(value = "/finalizar/solicitud/{idSolicitud}/{cveIdPersona}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> finalizarSolicitudAltaPatronal(@PathVariable Long idSolicitud, @PathVariable Long cveIdPersona,
			HttpServletResponse response, HttpServletRequest request, HttpSession session, Locale locale) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute(KEY_SUJETO_TRAMITE);
			Solicitud solicitudTramite = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
			sujetoTramite.setRepresentantesLegales(obtenerRepresentanteLegalFirmado(request, sujetoTramite, cveIdPersona, false));
			//Finalizar Alta Patronal PM y encolar solicitud.
			afiliacionService.finalizarSolicitudDeAltaPatronal(solicitudTramite, sujetoTramite, null, true);
			result.put(KEY_MENSAJE_EXITO, KEY_MENSAJE_SOLICITUD_ACTUALIZADA);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			String message = messageSource.getMessage(e.getMessage(), null, locale);
			result.put(KEY_MENSAJE_ERROR, message);
		} catch (SolicitudNoEncontradaException e) {
			String message = "No se encontr󠥬 folio de la solicitud proporcionado";
			result.put(KEY_MENSAJE_ERROR, message);
		} catch (TramiteNoEncontradoException e) {
			String message = "No se encontr󠥬 tramite proporcionado";
			result.put(KEY_MENSAJE_ERROR, message);
		} catch (SolicitudException se){
			String message = se.getSituacion();
			result.put(KEY_MENSAJE_ERROR, message);
		}
		return result;
	}

	@RequestMapping(value = "/finalizar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> finalizarSolicitudAltaPatronal(@PathVariable Long idSolicitud, HttpServletResponse response,
			HttpServletRequest request, HttpSession session,
			@RequestBody SujetoObligado sujetoObligado, Locale locale) {

		Map<String, Object> result = new HashMap<String, Object>();
		FirmaElectronica datosFirma = null;
		// Se obtienen los datos de sesion
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute(KEY_SUJETO_TRAMITE);
		Solicitud solicitudTramite = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
		// Se obtienen los datos de la subdelegacion, RPC y Num de centros de trabajo
		String nombreComercial = sujetoObligado.getNombreComercial()!=null ? sujetoObligado.getNombreComercial().toUpperCase() : "";
		sujetoTramite.setNombreComercial(nombreComercial);
		if (sujetoObligado.getClasificacion().getNumCentrosTraba() != null) {
			sujetoTramite.getClasificacion().setNumCentrosTraba(sujetoObligado.getClasificacion().getNumCentrosTraba());
		}
		sujetoTramite.setMunicipioIMSS(sujetoObligado.getMunicipioIMSS());
		if (sujetoObligado.getMunicipioIMSS() != null) {
			sujetoTramite.setSubdelegacion(sujetoObligado.getMunicipioIMSS().getSubdelegacion());
			solicitudTramite.setSubdelegacion(sujetoTramite.getSubdelegacion());
		}
		if(StringUtils.isNotBlank(sujetoObligado.getTipoPoder()) )
			sujetoTramite.setTipoPoder(sujetoObligado.getTipoPoder());
		if(StringUtils.isNotBlank(sujetoObligado.getOtroPoder()) )
			sujetoTramite.setOtroPoder(sujetoObligado.getOtroPoder().toUpperCase());

		sujetoTramite.setPersonasAutorizadas(sujetoObligado.getPersonasAutorizadas());

		// Se actualiza solicitud
		try {
			boolean esAPVentanilla = false;
			esAltaPatronalVentanilla(request, solicitudTramite, sujetoTramite);
			actualizarFechaPresentacion(solicitudTramite, sujetoTramite);
			Long idOrigen = new CommonValidator().getOrigenContext(request);
			if(idOrigen.equals(OrigenSolicitudEnum.INTERNET.getId())){
				sujetoTramite.setRepresentantesLegales(obtenerRepresentanteLegalFirmado(request,sujetoTramite, null, true));
				datosFirma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);
			}else if(idOrigen.equals(OrigenSolicitudEnum.VENTANILLA.getId())){
				esAPVentanilla = true;
				convertirTramiteSujetoObligado(solicitudTramite, sujetoTramite, PASO_MOSTRAR_RL_VENTANILLA);
			}

			//No encolar solicitud de Alta Patronal Ventanilla, se requiere actualizar informaci�n
			//pero hasta despues de mostrar Representantes Legales se encolara (para PM es requerido el RL).
			afiliacionService.finalizarSolicitudDeAltaPatronal(solicitudTramite, sujetoTramite, datosFirma, !esAPVentanilla);
			session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			result.put(KEY_MENSAJE_EXITO, KEY_MENSAJE_SOLICITUD_ACTUALIZADA);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			String message = messageSource.getMessage(e.getMessage(), null,	locale);
			result.put(KEY_MENSAJE_ERROR, message);
		} catch (SolicitudNoEncontradaException e) {
			String message = "No se encontr󠥬 folio de la solicitud proporcionado";
			result.put(KEY_MENSAJE_ERROR, message);
		} catch (TramiteNoEncontradoException e) {
			String message = "No se encontr󠥬 tramite proporcionado";
			result.put(KEY_MENSAJE_ERROR, message);
		} catch (SolicitudException se){
			String message = se.getSituacion();
			result.put(KEY_MENSAJE_ERROR, message);
		}
		return result;
	}

	private List<RepresentanteLegal> obtenerRepresentanteLegalFirmado(HttpServletRequest request,
			SujetoObligado sujetoTramite, Long cveIdPersona, boolean esInternet){
		Fisica representante=null;
		Long idRepresentante = 0L;
		try {
			if(esInternet){
				UsuarioSSO sso = this.procesarUsuarioSSO(request);
				if(sujetoTramite.getFisica()!=null && sujetoTramite.getFisica()
						.getIdPersona().equals(sso.getIdPersona().longValue())){
					request.getSession().setAttribute(KEY_ALTA_POR_RL, false);
					return new ArrayList<RepresentanteLegal>();//Si la persona del tramite y la firmada es la misma no se incluye como RL
				}
				idRepresentante = sso.getIdPersona().longValue();
			}else{
				//Si no se selecciono RL (Es Alta PF) o si el RL es el mismo que genera la alta, no integrar RL.
				if(cveIdPersona.equals(0L) || sujetoTramite.getFisica()!=null && sujetoTramite.getFisica()
						.getIdPersona().equals(cveIdPersona)){
					request.getSession().setAttribute(KEY_ALTA_POR_RL, false);
					return new ArrayList<RepresentanteLegal>();
				}
				idRepresentante = cveIdPersona;
			}
			representante = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idRepresentante);
			if(representante!=null){
				RepresentanteLegal rl = new RepresentanteLegal();
				rl.setPersonaFisica(representante);
				rl.setIndActAdmonDominio(BigDecimal.ONE);
				List<RepresentanteLegal> listaRL = new ArrayList<RepresentanteLegal>();
				listaRL.add(rl);
				request.getSession().setAttribute(KEY_ALTA_POR_RL, true);
				return listaRL;
			}
		} catch (PersonaFisicaNoEncontradaException e) {
			e.printStackTrace();
		}
		return new ArrayList<RepresentanteLegal>();
	}

	@RequestMapping(value = "/retomar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public String retomarSolicitudAltaPatronal(HttpSession session,
			HttpServletRequest request, @PathVariable Long idSolicitud,
			final Model model, @ModelAttribute Fisica fisica) {

		
		///VALIDACION PARA INHABILITAR TRAMITE DE ALTAS PF - WO458169
		// Se comenta para habilitar de nuevo tramite por Mm WO465028 - Implementaci󮠤e reglas y funcionalidad para la situaci󮠤el contribuyente en el SAT
//		if(fisica.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
//			Persona p = new Persona();
//			p.setTipoPersona(fisica.getTipoPersona());
//			p.setIdPersona(fisica.getIdPersona());
//			log.debug("::: Regresando en crearSolicitudAltaPatronal: ");
//			return	crearSolicitudAltaPatronal(model, session, request, p);
//		}
		
		String view = MOSTRAR_AP_DOMICILIO_CT;
		Solicitud solicitud = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
		session.setAttribute(KEY_FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
		session.setAttribute(KEY_ID_SOLICITUD, solicitud.getSolicitudId());
		session.setAttribute(KEY_ES_OPERADOR, true);
		session.setAttribute(KEY_IND_RCP_INVALIDO, solicitud.isIndRpcInvalido());
		session.setAttribute(KEY_IND_REINTENTOS_RCP, solicitud.isIndReintentoRpc());
		session.setAttribute(KEY_ID_PERSONA, fisica.getIdPersona());
		boolean isFisica = fisica.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA);
		Fisica objfisica = null;
		Moral objMoral = null;
		try {
			// Datos del acuse
			Persona personaEnTramite = null;
			if(isFisica){
				objfisica = this.serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(fisica.getIdPersona());
				personaEnTramite = objfisica;
			}else{
				//Cambio para obtener los datos de la PM - INC110489
				//objMoral = this.serviciosPersonaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(fisica.getIdPersona());
				log.debug("::: Voy a buscar a la PM :" + fisica.getIdPersona());
				objMoral = serviciosPersonaBusiness.buscarPMyDPyDyMCEnIMSS(fisica.getIdPersona());				
				personaEnTramite = objMoral;
			}
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				Fisica personaRecuperada=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				session.removeAttribute(DATOS_PERSONA_SESION_KEY);
				obtenerDatosAcuse(solicitud, personaRecuperada, session);
				generarCadenaOriginal(solicitud, personaEnTramite, session);
			}
			session.setAttribute(KEY_RFC_SOLICITANTE, personaEnTramite.getRfc());
		} catch (PersonaFisicaNoEncontradaException e2) {
			e2.printStackTrace();
		}
		
		//Mm WO465028 - Implementaci󮠤e reglas y funcionalidad para la situaci󮠤el contribuyente en el SAT
		String viewReturn = MOSTRAR_AP_ICA;
		try {
			session.setAttribute(SIT_CONT_SESSION_KEY, false);
			if(isFisica) {
				this.log.debug("::: Revisando situacion del contribuyente para PF, persona.getIdPersona(): " + fisica.getIdPersona());
					personaFisicaServiceBusiness.revisaSituacionContribuyente(objfisica);				
			}else {
				//Se omite validar situacion del contribuyente para PM a solicitud del usuario INC363844
				log.debug("::: Se omite validar situacion del contribuyente para PM");
				//viewReturn = MOSTRAR_AP_ICA_MORAL;
				//this.log.debug("::: Revisando situacion del contribuyente para PM, persona.getIdPersona(): " + fisica.getIdPersona());
				//personaMoralBusiness.revisaSituacionContribuyente(objMoral);				
			}
		} catch (ErrorComparacionDatosSATException e) {
			log.error(e);
			log.error(":::Error en situacion contribuyente: codigo: " + e.getCodigo() + " - " + e.getMessage());
			session.setAttribute(SIT_CONT_SESSION_KEY, true);
			session.setAttribute(SIT_CONT_SESSION_COD_KEY, e.getCodigo());
			ICADatosRespuesta icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_SAT.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);	
			session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
			return viewReturn;
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			ICADatosRespuesta icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.RFC_NO_LOCALIZADO
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
			return viewReturn;
		} catch (ClienteWebserviceSatRfcException e) {
			log.error(e);
			ICADatosRespuesta icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_SAT
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
			return viewReturn;
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			log.error(e);
			ICADatosRespuesta icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_VALIDAR_DATOS_ENTIDAD_EXTERNA
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
			return viewReturn;
		} catch(CURPNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			ICADatosRespuesta icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.CURP_NO_LOCALIZADO
					.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);
			session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
			return viewReturn;			
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error(e);
			ICADatosRespuesta icaDatosRespuesta = new ICADatosRespuesta();
			icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
			Map<String, String> mensajes = new HashMap<String, String>();
			mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_RENAPO
				.getCodigo(), e.getMessage());
			icaDatosRespuesta.setTraza(mensajes);			
			session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
			return viewReturn;			
		}
		this.log.debug("::: Termine de revisar situacon del contribuyente");
		
		session.setAttribute(KEY_RETOMAR, true);
		// Se verifica si existe un tramite de tipo TramieSujetoObligado
		InstanceofPredicate tramiteSujetoObligadoPredicate = new InstanceofPredicate(TramiteSujetoObligado.class);
		Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramiteSujetoObligadoPredicate);
		if (tramiteInicial != null) {
			TramiteSujetoObligado tramiteAlta = (TramiteSujetoObligado) tramiteInicial;
			SujetoObligado sujetoTramite = tramiteAlta.getSujetoObligado();
			if(tramiteAlta.getPasoTramite().intValue()==PASO_CAPTURA_COMPLEMENTO_CLASIFICACION){
				cargarDomicilioFiscaldeICAsoloRPC(sujetoTramite);
			}
			session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			model.addAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			session.setAttribute(KEY_SUJETO_OBLIGADO, sujetoTramite);
			
			switch (tramiteAlta.getPasoTramite()) {
			case PASO_CAPTURA_DOMICILIO: {
				view = MOSTRAR_AP_DOMICILIO_CT;
				String telefonoFijo="";
				String telefonoFijo2="";
				String correoElectronico="";
				if(sujetoTramite.getCntroTrabajo()!=null && sujetoTramite.getCntroTrabajo().getMediosContacto()!=null){
					for(MedioContacto medio :sujetoTramite.getCntroTrabajo().getMediosContacto()){
						if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
							if(medio.getIdVista()==1)
								telefonoFijo = medio.getDesFormaContacto();
							else if(medio.getIdVista()==2)
								telefonoFijo2 = medio.getDesFormaContacto();
						}
						if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
							correoElectronico=medio.getDesFormaContacto();
						}
					}
				}
				model.addAttribute("ctTelefonoFijo",telefonoFijo);
				model.addAttribute("ctTelefonoFijo2",telefonoFijo2);
				model.addAttribute("ctCorreoElectronico",correoElectronico);
				break;
			}
			case PASO_CAPTURA_CLASIFICACION: {
				view = MOSTRAR_AP_CLASIFICACION;
				colocarIndicadorRpc(request, session, model, solicitud, sujetoTramite);
				break;
			}
			case PASO_CAPTURA_COMPLEMENTO_CLASIFICACION: {
				List<Integer> listIdTipoTramite = new ArrayList<Integer>();
				for(Tramite tramite : solicitud.getTramites()){
					Integer idTipoTramite = tramite.getTipoTramite().getIdTipoTramite();
					listIdTipoTramite.add(idTipoTramite);
				}
				session.setAttribute(KEY_TIPO_TRAMITE, listIdTipoTramite);
				view = MOSTRAR_AP_COMPLEMENTO_CLASIFICACION;
				if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
					obtenerRepresentanteLegalFirmado(request, sujetoTramite, null, true);//Se sube el indicador de si es rl o no
				}else{
					esAltaPatronalVentanilla(request, solicitud, sujetoTramite);
				}
				break;
			}
			case PASO_MOSTRAR_RL_VENTANILLA: {
				List<RepresentanteLegal> lista = obtenerRepresentantesLegales(session, request, sujetoTramite);
				representanteLegalSelected(sujetoTramite, lista);
				model.addAttribute(KEY_LISTA_RLS, lista);
				view = MOSTRAR_RL_VENTANILLA;
			}
			default: {
				break;
			}
			}
		} else {
			view = MOSTRAR_AP_ICA;
			if(!isFisica)
				view = MOSTRAR_AP_ICA_MORAL;
			ICADatosRespuesta icaDatosRespuesta = null;
			ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
			try {
				Fisica objfisicaRecuperado = null;
				Moral objMoralRecuperado = null;
				if(isFisica){
					objfisicaRecuperado = this.serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(fisica.getIdPersona());
					// se setean los datos requeridos para hacer la consulta de
					// informaci�n completa
					icaDatosConsulta.setPersonaFisica(objfisicaRecuperado);
					icaDatosConsulta.setIndicadorConsultaRENAPO(Boolean.TRUE);
					icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
					icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.TRUE);
				}else{
					//Cambio para obtener los datos de la PM - INC110489
					//objMoralRecuperado = this.serviciosPersonaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(fisica.getIdPersona());
					log.debug("::: Voy a buscar a la PM :" + fisica.getIdPersona());
					objMoralRecuperado = serviciosPersonaBusiness.buscarPMyDPyDyMCEnIMSS(fisica.getIdPersona());				

					// se setean los datos requeridos para hacer la consulta de
					// informaci�n completa
					icaDatosConsulta.setPersonaMoral(objMoralRecuperado);
					icaDatosConsulta.setIndicadorConsultaRENAPO(Boolean.FALSE);
					icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
					icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.TRUE);
				}
				
				SujetoObligado sujetoTramite = inicializaInformacionActividadEconomica(new SujetoObligado());
				// Se detectan cambios en ICA
				try {
					if(isFisica){
						icaDatosRespuesta = personaFisicaServiceBusiness.identificarCambios(icaDatosConsulta);
					}else{
						//Cambio para obtener los datos de la PM - INC110489
						//icaDatosRespuesta = personaMoralServiceBusiness.identificarCambios(icaDatosConsulta);
						icaDatosRespuesta = personaMoralServiceBusiness.identificarCambios_AP(icaDatosConsulta);
					}
//					icaDatosRespuesta = personaFisicaServiceBusiness.integrarCambios(icaDatosRespuesta);
				} catch (ComparacionSinDiferenciasException e1) {
					log.error(e1);
					icaDatosRespuesta = new ICADatosRespuesta();
					icaDatosRespuesta.setErrorFormGeneral(e1.getMessage());
					Map<String, String> mensajes = new HashMap<String, String>();
					mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
									.getCodigo(), e1.getMessage());
					icaDatosRespuesta.setTraza(mensajes);
				}
				
				sujetoTramite.setDatosICA(icaDatosRespuesta);
//				boolean isFisica = true;
//				if (icaDatosRespuesta.getPersonaMoralIMSS() != null) {
//					isFisica = false;
//				}
				boolean existeDiferenciasIca = solicitudPersonaBusiness.existenDiferencias(icaDatosRespuesta.getCambios());
				if (!existeDiferenciasIca) {
					icaDatosRespuesta = new ICADatosRespuesta();
					ComparacionSinDiferenciasException e1 = new ComparacionSinDiferenciasException();
					icaDatosRespuesta.setErrorFormGeneral(e1.getMessage());
					Map<String, String> mensajes = new HashMap<String, String>();
					mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS
									.getCodigo(), e1.getMessage());
					icaDatosRespuesta.setTraza(mensajes);
				}
				// Se ingresa la persona obtenida en la coprobacion
				if (isFisica) {
					sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
					if (existeDiferenciasIca) {
						sujetoTramite.setFisica(icaDatosRespuesta.getPersonaFisicaIMSS());
					} else {
						sujetoTramite.setFisica(objfisicaRecuperado);
					}
				} else {
					sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);

					if (existeDiferenciasIca) {
						sujetoTramite.setMoral(icaDatosRespuesta.getPersonaMoralIMSS());
					} else {
						Moral personaFound = personaMoralBusiness.getPersonaMoral(fisica.getIdPersona());
						sujetoTramite.setMoral(personaFound);
					}
				}
				session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
				model.addAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
				session.setAttribute(KEY_SUJETO_OBLIGADO, sujetoTramite);
			} catch (PersonaNoEncontradaException e) {
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.PERSONA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} catch (CURPNoLocalizadoEnEntidadExternaException e) {
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.CURP_NO_LOCALIZADO
						.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.ERROR_VALIDAR_DATOS_ENTIDAD_EXTERNA
					.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} catch (RFCNoLocalizadoEnEntidadExternaException e) {
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.RFC_NO_LOCALIZADO
						.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} catch (ErrorComparacionDatosRENAPOException e) {
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.ERROR_COMPARACION_DATOS_RENAPO
					.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} catch (DatosInsuficientesICAException e) {
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.DATOS_INSUFICIENTES_ICA
					.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} catch (DiferenciasRENAPOContraSAT e) {
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.DIFERENCIAS_RENAPO_SAT
					.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} catch (PersonaFisicaNoEncontradaException e) {
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.PERSONA_FISICA_NO_ENCONTRADA
					.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} catch (ClienteWebserviceRenapoCurpException e) {
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_RENAPO
					.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} catch (ClienteWebserviceSatRfcException e) {
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.ERROR_CONSULTA_SAT
					.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} 

			Boolean aplicaRissAltaPat = (Boolean) session.getAttribute(KEY_APLICA_RISS_ALTA_PAT);
			session.setAttribute(KEY_APLICA_RISS_ALTA_PAT, aplicaRissAltaPat);
			session.setAttribute(ICA_SESSION_KEY, icaDatosRespuesta);
		}
		for (Tramite tramiteActual : solicitud.getTramites()) {
			tramiteActual.setFechaPresentacion(Calendar.getInstance().getTime());
		}
		session.setAttribute(KEY_SOLICITUD, solicitud);
		log.debug("::: Regesando en retomar a : " + view);
		return view;
	}

	@RequestMapping(value = "/pasoPrevio/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public String pasoPrevioSolicitudAltaPatronal(HttpSession session,
			HttpServletRequest request, @PathVariable Long idSolicitud,
			final Model model, @ModelAttribute SujetoObligado sujetoObligado, Locale locale) {
		String view = MOSTRAR_AP_DOMICILIO_CT;
		Solicitud solicitud = new Solicitud();
		// Se obtienen los datos de sesion
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute(KEY_SUJETO_TRAMITE);
		Solicitud solicitudTramite = solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
		SujetoObligado sujetoObligadoSesion = (SujetoObligado) session.getAttribute(KEY_SUJETO_OBLIGADO);
		// Se actualiza solicitud
		try {
			for (Tramite tramite : solicitudTramite.getTramites()) {
				if (tramite instanceof TramiteSujetoObligado) {
					TramiteSujetoObligado tramiteAlta = (TramiteSujetoObligado) tramite;
					switch (tramiteAlta.getPasoTramite()) {
					case PASO_CAPTURA_CLASIFICACION: {
						view = MOSTRAR_AP_DOMICILIO_CT;
						String telefonoFijo="";
						String telefonoFijo2="";
						String correoElectronico="";
						if(sujetoTramite.getCntroTrabajo()!=null && sujetoTramite.getCntroTrabajo().getMediosContacto()!=null){
							for(MedioContacto medio :sujetoTramite.getCntroTrabajo().getMediosContacto()){
								if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
									if(medio.getIdVista()==1)
										telefonoFijo = medio.getDesFormaContacto();
									else if(medio.getIdVista()==2)
										telefonoFijo2 = medio.getDesFormaContacto();
								}
								if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
									correoElectronico=medio.getDesFormaContacto();
								}
							}
						}
						model.addAttribute("ctTelefonoFijo",telefonoFijo);
						model.addAttribute("ctTelefonoFijo2",telefonoFijo2);
						model.addAttribute("ctCorreoElectronico",correoElectronico);
						convertirTramiteSujetoObligado(solicitudTramite,
							sujetoTramite, PASO_CAPTURA_DOMICILIO);
						solicitud = afiliacionService.actualizarSolicitudDeAltaPatronal(solicitudTramite, sujetoTramite);
						session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
						break;
					}
					case PASO_CAPTURA_COMPLEMENTO_CLASIFICACION: {
						view = MOSTRAR_AP_CLASIFICACION;
						// Se obtienen los datos de la subdelegacion, RPC y Num de centros de trabajo
						if (StringUtils.isNotBlank(sujetoObligado.getNombreComercial())) {
							sujetoTramite.setNombreComercial(sujetoObligado.getNombreComercial().toUpperCase());
						}
						if (sujetoObligado.getClasificacion().getNumCentrosTraba() != null) {
							sujetoTramite.getClasificacion().setNumCentrosTraba(sujetoObligado.getClasificacion().getNumCentrosTraba());
						}
						sujetoTramite.setMunicipioIMSS(sujetoObligado.getMunicipioIMSS());
						if (sujetoObligado.getMunicipioIMSS() != null) {
							sujetoTramite.setSubdelegacion(sujetoObligado.getMunicipioIMSS().getSubdelegacion());
						}
						convertirTramiteSujetoObligado(solicitudTramite,
							sujetoTramite, PASO_CAPTURA_CLASIFICACION);
						solicitud = afiliacionService.actualizarSolicitudDeAltaPatronal(solicitudTramite, sujetoTramite);
						session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
						colocarIndicadorRpc(request, session, model, solicitudTramite, sujetoTramite);
						break;
					}
					case PASO_MOSTRAR_RL_VENTANILLA: {
						view = MOSTRAR_AP_COMPLEMENTO_CLASIFICACION; //Pantalla previa
						convertirTramiteSujetoObligado(solicitudTramite, sujetoTramite,
								PASO_CAPTURA_COMPLEMENTO_CLASIFICACION);
						solicitud = afiliacionService.actualizarSolicitudDeAltaPatronal(solicitudTramite, sujetoTramite);
						session.setAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
						esAltaPatronalVentanilla(request, solicitudTramite, sujetoTramite);
					}
					default: {
						break;
					}
					}
				}
			}
			model.addAttribute(KEY_FOLIO_SOLICITUD, solicitudTramite.getNoFolioSolicitud());
			session.setAttribute(KEY_FOLIO_SOLICITUD, solicitudTramite.getNoFolioSolicitud());
			model.addAttribute(KEY_ID_SOLICITUD, solicitudTramite.getSolicitudId());
			session.setAttribute(KEY_ID_SOLICITUD, solicitudTramite.getSolicitudId());
			model.addAttribute(KEY_SUJETO_TRAMITE, sujetoTramite);
			model.addAttribute(KEY_SUJETO_OBLIGADO, sujetoObligadoSesion);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			solicitud = new Solicitud();
			String message = messageSource.getMessage(e.getMessage(), null,
				locale);
			solicitud.setErrorFormGeneral(message);
		}
		session.setAttribute(KEY_SOLICITUD, solicitud);
		return view;
	}

	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody
	Solicitud limpiarSesion(final HttpSession session) {
		session.removeAttribute(KEY_SUJETO_TRAMITE);
		session.removeAttribute(KEY_SUJETO_OBLIGADO);
		session.removeAttribute(KEY_ES_OPERADOR);
		session.removeAttribute(KEY_IND_RCP_INVALIDO);
		session.removeAttribute(KEY_IND_REINTENTOS_RCP);
		session.removeAttribute(KEY_ID_SOLICITUD);
		session.removeAttribute(KEY_FOLIO_SOLICITUD);
		session.removeAttribute(KEY_SOLICITUD);
		session.removeAttribute(KEY_ID_PERSONA);
		session.removeAttribute(ICA_SESSION_KEY);
		session.removeAttribute(KEY_APLICA_RISS_ALTA_PAT);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_TIPO_TRAMITE);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_RFC_SOLICITANTE);
		session.removeAttribute(KEY_CADENA_ORIGINAL);

		return null;
	}

	private void convertirTramiteSujetoObligado(Solicitud solicitud,
			SujetoObligado sujetoTramite, Integer pasoTramite) {

		List<Tramite> listTramite = new ArrayList<Tramite>();
		// Se verifica si existe un tramite de tipo TramieSujetoObligado
		InstanceofPredicate tramiteSujetoObligadoPredicate = new InstanceofPredicate(TramiteSujetoObligado.class);
		Object tramiteInicial = CollectionUtils.find(solicitud.getTramites(), tramiteSujetoObligadoPredicate);
		if (tramiteInicial == null) {
			for (Tramite tramiteActual : solicitud.getTramites()) {
				if (tramiteActual.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT.getCodigo()) ||
					tramiteActual.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo())){

					if(sujetoTramite.getClasificacion()==null){
						sujetoTramite.setClasificacion(new Clasificacion());
					}
					if(sujetoTramite.getClasificacion().getFecPresentacion()==null)
						sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());

					TramiteSujetoObligado tramiteNuevo = new TramiteSujetoObligado();
					tramiteNuevo.setTramiteId(tramiteActual.getTramiteId());
					tramiteNuevo.setSujetoObligado(sujetoTramite);
					tramiteNuevo.setTipoTramite(tramiteActual.getTipoTramite());
					tramiteNuevo.getTipoTramite().setIdTipoTramite(
							tramiteActual.getTipoTramite().getIdTipoTramite());
					tramiteNuevo.setFechaTramite(tramiteActual.getFechaTramite());
					tramiteNuevo.setEstadoTramite(tramiteActual.getEstadoTramite());
					tramiteNuevo.getEstadoTramite().setIdEstadoTramitePersona(
							tramiteActual.getEstadoTramite().getIdEstadoTramitePersona());
					tramiteNuevo.setPasoTramite(pasoTramite);
					tramiteNuevo.setFechaPresentacion(sujetoTramite.getClasificacion().getFecPresentacion());
					tramiteNuevo.setAcuseVentanilla(tramiteActual.getAcuseVentanilla());
					listTramite.add(tramiteNuevo);
				}
			}
			for (Tramite tramiteActual : solicitud.getTramites()) {
				if(!(tramiteActual.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT.getCodigo()) ||
						tramiteActual.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT_PM.getCodigo()))){
					tramiteActual.setFechaPresentacion(Calendar.getInstance().getTime());
					listTramite.add(tramiteActual);
				}
			}
			solicitud.setTramites(listTramite);
			solicitudServiceBusiness.actualizarTramites(solicitud);
		} else {
			((TramiteSujetoObligado) tramiteInicial).setPasoTramite(pasoTramite);
			solicitudServiceBusiness.actualizarTramites(solicitud);
		}
	}

	private SujetoObligado inicializaInformacionActividadEconomica(
			SujetoObligado sujetoTramite) {
		sujetoTramite.setBienes(new ArrayList<Bien>());
		sujetoTramite.setEquipos(new ArrayList<MaquinariaEquipo>());
		sujetoTramite.setEquiposTransporte(new ArrayList<EquipoTransporte>());
		sujetoTramite.setMateriaPrimaMateriales(new ArrayList<MateriaPrima>());
		sujetoTramite.setPersonal(new ArrayList<Personal>());
		sujetoTramite.setProductos(new ArrayList<Producto>());
		sujetoTramite.setCntroTrabajo(crearCentroTrabajoVacio());

		return sujetoTramite;
	}

	private CentroTrabajo crearCentroTrabajoVacio() {
		CentroTrabajo ct = new CentroTrabajo();
		ct.setAsentamiento(crearAsentamientoVacio());
		ct.setCodigoPostal(new CodigoPostal());
		ct.setVialidadPrimaria(crearVialidadVacia());
		ct.setVialidadReferenciaPrimaria(crearVialidadVacia());
		ct.setVialidadReferenciaSecundaria(crearVialidadVacia());
		ct.setVialidadReferenciaPosterior(crearVialidadVacia());
		return ct;
	}

	private Asentamiento crearAsentamientoVacio() {
		Asentamiento asent = new Asentamiento();
		asent.setLocalidad(new Localidad());
		asent.getLocalidad().setMunicipio(new Municipio());
		asent.getLocalidad().getMunicipio()
			.setEntidadFederativa(new EntidadFederativa());
		return asent;
	}

	private Vialidad crearVialidadVacia() {
		Vialidad vialidad = new Vialidad();
		vialidad.setTipoVialidad(new TipoVialidad());
		return vialidad;
	}

	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}

	private void generarCadenaOriginal(Solicitud solicitud, Persona persona, HttpSession session) {
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

		String nombreRazonSocial = sbnombre.toString();

		contenidoAFirmar.append("Nombre o Razon Social:");
		/*
		 * Se sustituyen las comillas con el caracter especial HTML &quot; para
		 * que el valor en el input hidden sea correcto y no se despu�s el
		 * applet de la firma funcione correctamente
		 */
		contenidoAFirmar.append(nombreRazonSocial.replace("\"", "&quot;")).append("|");

		datosEntradaFirma.setNombreCompleto(nombreRazonSocial.toString());

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

	/**
	 * Se actualizan con paso por referencia las fechas de presentaci�n de solicitud y objeto de tr�mite
	 * @param solicitud
	 * @param sujetoTramite
	 */
	private void actualizarFechaPresentacion(Solicitud solicitud, SujetoObligado sujetoTramite){
		sujetoTramite.getClasificacion().setFecPresentacion(Calendar.getInstance().getTime());
		solicitud.setFechaPresentacion(sujetoTramite.getClasificacion().getFecPresentacion());
	}

	@RequestMapping(value = "/consultar/personaAutorizada/{indicePersonaAut}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> obtenerDatosPersonaAutorizada(
			@RequestBody Fisica persona, @PathVariable Integer indicePersonaAut,
			HttpServletResponse response, HttpServletRequest request,
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		Errors errors = new BindException(persona, "model");
		result=new PersonaAutorizadaValidator().validate(persona, errors, indicePersonaAut, response, messageSource);
		if(result.containsKey(KEY_MENSAJE_ERROR))
			return result;

		try {
			persona = consultaPersonaFisicaServiceBusinessRemote.getPersonaByCurpImssEntidadesExternas(persona);
			Fisica personaFirmada=obtenerDatosPersonaFirmada(request);
			if(persona.getIdPersona()!=null && persona.getIdPersona().equals(personaFirmada.getIdPersona())){
				result.put(KEY_MENSAJE_ERROR, "No se puede agregar usted mismo como persona autorizada");
				return result;
			}else if(persona.getRfc().equalsIgnoreCase(personaFirmada.getRfc())
					&& persona.getCurp().equalsIgnoreCase(personaFirmada.getCurp()) ){
				result.put(KEY_MENSAJE_ERROR, "No se puede agregar usted mismo como persona autorizada");
				return result;
			}
			//agregarMediosFiscalesSAT(persona);
			result.put("personaEncontrada", persona);
		} catch (ClienteWebserviceRenapoCurpException e) {
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		} catch (ClienteWebserviceSatRfcException e) {
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		} catch (ErrorComparacionDatosRENAPOException e) {
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		} catch (ErrorComparacionDatosSATException e) {
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		} catch (DiferenciasRENAPOContraSAT e) {
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		} catch (PersonaSinCalificacionesException e) {
			result.put(KEY_MENSAJE_ERROR, e.getMessage());
		}

		return result;
	}

	/**
	 *
	 * @param cveIdPersona
	 * @param cveTipoPersona
	 * @return
	 */
	@RequestMapping(value = "/validarPersonaParaAlta/{cveIdPersona}/{cveTipoPersona}", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> validaPersonaParaAlta(HttpSession session, HttpServletRequest request,
			@PathVariable Long cveIdPersona, @PathVariable Integer cveTipoPersona) {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer validaMoralActa;
		Integer validaMoralSindicato;
		OrigenSolicitudEnum origen = OrigenSolicitudEnum
			.getById(new CommonValidator().getOrigenContext(request));

		log.debug("Cve Id persona ::" + cveIdPersona);

		try{
			if(cveTipoPersona.longValue() == TipoPersonaEnum.FISICA.getId()){
				Fisica patronPersonaFisica = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(cveIdPersona);
				log.debug("patronPersonaFisica::: " + patronPersonaFisica);
				log.debug("origen:" + origen);
/*				if(origen.equals(OrigenSolicitudEnum.INTERNET) || origen.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO)) {
					sujetoObligadoServiceBusiness.validaCveIdPersonaPorRegistroPatronalClaseActivo(patronPersonaFisica.getRfc(), cveTipoPersona);
				}*/

			}else{
				//Para Internet primero se selecciona a quien se representara, es decir,
				//a nombre de que patron persona moral se realizara el tramite.
				if(!origen.equals(OrigenSolicitudEnum.VENTANILLA)){
					sujetoObligadoServiceBusiness.validaRepresentanteLegalExistente(cveIdPersona, cveTipoPersona.longValue());
				}

				//Cambio para obtener los datos de la PM - INC110489
				//Moral patronPersonaMoral = serviciosPersonaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(cveIdPersona);
				log.debug("::: Voy a buscar a la PM :" + cveIdPersona);
				Moral patronPersonaMoral = serviciosPersonaBusiness.buscarPMyDPyDyMCEnIMSS(cveIdPersona);				
				
/*				if(origen.equals(OrigenSolicitudEnum.INTERNET)) {
					sujetoObligadoServiceBusiness.validaCveIdPersonaPorRegistroPatronalClaseActivo(patronPersonaMoral.getRfc(), cveTipoPersona);
				}*/

				validaMoralActa = personaMoralServiceBusiness.consultaActaConstitutivaPersonaMoral(cveIdPersona);
				if (validaMoralActa == 0) {
					validaMoralSindicato= personaMoralServiceBusiness.consultaSindicatoPersonaMoral(cveIdPersona);
					if (validaMoralSindicato > 0) {
						//ruleServiceBusiness.validarFraccionesConsistentesPorPatron(patronPersonaMoral.getRfc());
						//Regresa registro y se manda parametro 0 para seguir con Alta
						result.put(KEY_RESPUESTA, 0);
					} else {
						//No regresa registro y se manda parametro 1 para mostrar mensaje de error
						result.put(KEY_RESPUESTA, 1);
						//TramiteMostrar, faltan Datos Generales (TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo())
						result.put(KEY_TRAMITE_MOSTRAR, TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo());
						result.put(KEY_MSG_ERROR, "Usted no cuenta con la informaci&oacute;n requerida para realizar un alta patronal, por favor realice el tr&aacute;mite de actualizaci&oacute;n de datos particulares para completar la informaci&oacute;n referente a la constituci&oacute;n de la empresa.");
						return result;
					}
				} else {
					//Regresa registro y se manda parametro 0 para seguir con Alta
					//ruleServiceBusiness.validarFraccionesConsistentesPorPatron(patronPersonaMoral.getRfc());
					result.put(KEY_RESPUESTA, 0);
				}
				ruleServiceBusiness.validarSociosRequeridos(patronPersonaMoral.getIdPersona());
			}
		}catch(GestionPatronalBusinessException gpbe){
			result.put(KEY_RESPUESTA, 1);
			result.put(KEY_MSG_ERROR, gpbe.getMessage());
			//Se agrega key tramiteMostrar para evaluar el valor en la vista
			result.put(KEY_TRAMITE_MOSTRAR, gpbe.getCodigo());
		} catch (PersonaFisicaNoEncontradaException e) {
			result.put(KEY_RESPUESTA, 1);
			result.put(KEY_MSG_ERROR, e.getSituacion());
		}
		return 	result;
	}

	private Fisica obtenerDatosPersonaFirmada(HttpServletRequest request){
		UsuarioSSO sso = this.procesarUsuarioSSO(request);
		String view = MOSTRAR_AP_INIT;
		try {
			Fisica personaSesion = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(sso.getIdPersona().longValue());
			return personaSesion;
		} catch (PersonaFisicaNoEncontradaException e) {
			this.log.error("ocurrio un error al consultar las solicitudes abiertas para la persona "
					+ "[" + sso.getIdPersona() + "]", e);
		}
		return null;
	}

	private void agregarMediosFiscalesSAT(Persona persona) throws ClienteWebserviceSatRfcException{
		Fisica pf=personaBusiness.buscarPersonaFisicaPorRfcEnSat(persona.getRfc());
		//persona.setMediosContactoFiscales( pf.getMediosContactoFiscales() );
		for(MedioContacto mc:pf.getMediosContactoFiscales()){
			if(mc.getTipoMedioContacto().getIdTipoMedioContacto().equals(
					TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
				CorreoElectronico correo = new CorreoElectronico(mc.getDesFormaContacto());
				persona.setCorreoElectronicoFiscal(correo);
			}else if(mc.getTipoMedioContacto().getIdTipoMedioContacto().equals(
					TipoMedioContacto.TIPO_TELEFONO_FIJO)){
				TelefonoFijo tf = new TelefonoFijo(mc.getDesFormaContacto(),null,null);
				persona.setTelefonoFijoFiscal(tf);
			}else if(mc.getTipoMedioContacto().getIdTipoMedioContacto().equals(
					TipoMedioContacto.TIPO_TELEFONO_MOVIL)){
				TelefonoMovil tm = new TelefonoMovil(mc.getDesFormaContacto());
				persona.setTelefonoMovilFiscal(tm);
			}

		}
	}

	private boolean esAltaPatronalVentanilla(HttpServletRequest request, Solicitud solicitud, SujetoObligado sujetoObligado){
		OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(solicitud.getOrigenSolicitud().getIdTipoSolicitud());
		boolean esAPVentanilla = false;
		boolean altaPorRL = false;
		if(origen.equals(OrigenSolicitudEnum.VENTANILLA)){
			esAPVentanilla = true;
			altaPorRL = true;
		}
		//Marca para indicar que es Alta por RL y si es AltaPatronalVentanilla
		request.getSession().setAttribute(KEY_ALTA_POR_RL, altaPorRL);
		return esAPVentanilla;
	}

	private void representanteLegalSelected(SujetoObligado sujetoObligado, List<RepresentanteLegal> representantes){
		if(!CollectionUtils.isEmpty(representantes)){
			for(RepresentanteLegal representanteLegal : representantes){
				if(sujetoObligado!=null && !CollectionUtils.isEmpty(sujetoObligado.getRepresentantesLegales())){
					//Obtener RL almacenado en XML y compararlo contra los rl asociados al patron.
					RepresentanteLegal rl = sujetoObligado.getRepresentantesLegales().get(0);
					if(rl.getPersonaFisica().getIdPersona().equals(representanteLegal.getPersonaFisica().getIdPersona()))
						representanteLegal.setChecked(new Boolean(true));
				}
			}
		}
	}

	private List<RepresentanteLegal> obtenerRepresentantesLegales(
			HttpSession session, HttpServletRequest request, SujetoObligado sujetoObligado){
		Long idPersonaFM = 0L;	//CveMoral o CveFisica del Representado
		Long idPersona = 0L;	//IdPersona del Representado
		Long idTipoPersona = null;
		List<RepresentanteLegal> listaRLs = new ArrayList<RepresentanteLegal>();
		Persona persona = null;	//RL que se empleara en el dilogo de Agregar RL

		if(sujetoObligado.getMoral()!= null && sujetoObligado.getMoral().getCveMoral()!=null){
			idPersonaFM = sujetoObligado.getMoral().getCveMoral();
			idPersona = idPersonaFM;
			idTipoPersona = TipoPersona.TIPO_PERSONA_MORAL.longValue();
			request.getSession().setAttribute(KEY_RL_REQUERIDO, true);
			request.getSession().setAttribute(KEY_ALTA_POR_RL, true);
			persona = sujetoObligado.getMoral();
		}else{
			idPersonaFM = sujetoObligado.getFisica().getCveFisica();
			idPersona = sujetoObligado.getFisica().getIdPersona();
			idTipoPersona = TipoPersona.TIPO_PERSONA_FISICA.longValue();
			request.getSession().setAttribute(KEY_RL_REQUERIDO, false);
			request.getSession().setAttribute(KEY_ALTA_POR_RL, true);
			persona = sujetoObligado.getFisica();
		}
		idPersonaFM = idPersonaFM != null ? idPersonaFM : 0L;
		idPersona = idPersona != null ? idPersona : 0L;

		List<RepresentanteLegal> representantes = sujetoObligadoServiceBusiness
			.obtenerRepresentantesLegales(idPersonaFM, idTipoPersona);
		//Eliminar PF o PM de la lista de RLs, si es que llegara a existir.
		if(!CollectionUtils.isEmpty(representantes)){
			for(RepresentanteLegal rp : representantes){
				rp.setChecked(new Boolean(false));
				if(!(rp.getPersonaFisica().getIdPersona().equals(idPersona))){
					listaRLs.add(rp);
				}
			}
		}
		persona.setIdPersona(idPersona);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(idTipoPersona);
		if(persona instanceof Fisica){
			((Fisica)persona).setCveFisica(idPersonaFM);
		}
		session.setAttribute(KEY_REPRESENTADO_SESSION, persona);
		return listaRLs;
	}

	private void cargarDomicilioFiscaldeICAsoloRPC(SujetoObligado sujetoObligado){
		if(sujetoObligado != null && sujetoObligado.getClasificacion() != null
				&& sujetoObligado.getClasificacion().getIndRegPatClase() != null
				&& sujetoObligado.getClasificacion().getIndRegPatClase().intValue() == 1){
			if(sujetoObligado.getFisica() != null && sujetoObligado.getFisica().getDomicilioFiscal() == null){
				sujetoObligado.getFisica()
					.setDomicilioFiscal(sujetoObligado.getDatosICA().getPersonaFisicaEE().getDomicilioFiscal());
			}else if(sujetoObligado.getMoral() != null && sujetoObligado.getMoral().getDomicilioFiscal()==null){
				sujetoObligado.getMoral()
					.setDomicilioFiscal(sujetoObligado.getDatosICA().getPersonaMoralEE().getDomicilioFiscal());
			}
		}
	}

	private void colocarIndicadorRpc(HttpServletRequest request,
			HttpSession session, Model model, Solicitud solicitudTramite,
			SujetoObligado sujetoTramite) {
		OrigenSolicitudEnum origen = OrigenSolicitudEnum
			.getById(new CommonValidator().getOrigenContext(request));
		boolean esVentanilla = (origen.equals(OrigenSolicitudEnum.VENTANILLA));
		boolean esPatronRPC = false;

		if (esVentanilla) {
			TipoPersonaFiscal tipoPersona = sujetoTramite.getTipoPersonaFiscal();
			String rfc = null;

			if (tipoPersona.equals(TipoPersonaFiscal.FISICA))
				rfc = sujetoTramite.getFisica().getRfc();
			else
				rfc = sujetoTramite.getMoral().getRfc();
			/*try {
				sujetoObligadoServiceBusiness.validaCveIdPersonaPorRegistroPatronalClaseActivo(rfc, tipoPersona.getCodigo());
			} catch (GestionPatronalBusinessException e) {
				log.error("El patron " + rfc + " tiene al menos un RPC");
				esPatronRPC = true;
			}*/

			if (esPatronRPC) {
				sujetoTramite.getClasificacion().setIndPrestaServicioPersonal(1);
				sujetoTramite.getClasificacion().setIndRegPatClase(1);
			}
		}
		model.addAttribute("esPatronRPC", esPatronRPC);
		session.setAttribute("esPatronRPC", esPatronRPC);
	}
}
