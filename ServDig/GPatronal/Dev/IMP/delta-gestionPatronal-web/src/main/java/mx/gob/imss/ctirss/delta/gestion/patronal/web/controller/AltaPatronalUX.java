package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





import mx.gob.economia.ConfirmarSolicitudRequestDTO;
import mx.gob.economia.ConfirmarSolicitudResponseDTO;
import mx.gob.economia.SolicitudService;
import mx.gob.economia.SolicitudServiceLocator;
import mx.gob.economia.SolicitudServiceType;
import mx.gob.economia.NotificarConclusionResponseDTO;
import mx.gob.economia.NotificarConclusionRequestDTO;

//import mx.gob.economia.ConfirmarSolicitudRequestDTO;
//import mx.gob.economia.ConfirmarSolicitudResponseDTO;
//import mx.gob.economia.EconomiaWS;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.PatronExistenteMunicipioFraccionModalidadException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudQueueProducerRemote;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.soap.encoding.soapenc.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalInvalidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalYaExisteException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoActualizadaRenapoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioRegistradoSSOException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.firma.FirmaElectronicaBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.beans.EstructuraFirmaDTO;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.beans.RepresentanteDTO;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.beans.SolicitudAltaPatronalDTO;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CommonValidator;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.EnviaArchivoServlet;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.ErroresModificacionPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstatusPersona;
import mx.gob.imss.ctirss.delta.model.enums.FraccionEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPoder;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.AcuseVentanilla;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSocios;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.web.validator.PersonaRFCValidator;
import mx.gob.imss.ctirss.delta.web.validator.PersonaValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;






import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

@Controller
@RequestMapping("/alta/*")
public class AltaPatronalUX extends AbstractController {

	// Vista para el login con FIEL
	private final String VIEW_LOGIN_MORAL = "viewLoginAltaMoral";
	// vista donde se muestran los pasos del alta patronal persona moral
	private final String VIEW_PASOS_MORAL = "viewPasosMoral";
	// vista para mostrar la confirmacion de los datos de la empreza
	private final String VIEW_CONFIRMACION = "viewConfirmacionDatos";
	// vista para mostrar la pantalla de captura de domicilio de centro de
	// trabajo
	private final String VIEW_DOMICILIO_CT = "viewDomicilioCentroTrabajo";
	// vista principal
	private final String VIEW_PRINCIPAL = "viewPrincipal";
	// vista error
	private final String VIEW_ERROR = "viewError";
	// vista para mostrar la pantalla de llenado de personas autorizadas
	private final String VIEW_PERSONAS_AUTORIZADAS = "viewPersonasAutorizadas";

	private final String VIEW_CARTA_TERMINOS = "viewCartaTerminos";

	private static final String KEY_OFORM = "oForm";

	private static final String KEY_NEGOCIO = "negocio";

	private static final String DATOS_PERSONA_KEY = "datosPersonales";

	private static final String ESTRUCTURA_FIRMA_EMPRESA_KEY = "estructuraFirmaEmpresa";

	private static final String ESTRUCTURA_FIRMA_REPRESENTANTE_KEY = "estructuraFirmaRepresentante";

	private static final String DESC_TIPO_SOLICITUD = "ALTA DE EMPRESA REPRESENTADA";

	private static final String MSG_FINALIZAR_SOLICITUD = "Su solicitud ha finalizado correctamente";

	private static final String MSG_ACTUALIZADA_SOLICITUD = "Su solicitud fue actualizada correctamente";

	private static final String KEY_MENSAJE = "mensaje";

	private static final String DATOS_PERSONA_SESION_KEY = "datosPersonaSesionRep";

	private static final String SUJETO_OBLIGADO_KEY = "sujetoObligado";

	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";

	private static final String FISICA_SESSION_KEY = "fisicaDatosRegistro";

	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";

	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";

	private static final String MSG_ERROR_RFC_REPRESENTANTE = "El RFC del representante en la firma no corresponde";

	private static final String MSG_ERROR_REPRESENTANTE_INVALIDO = "No es posible registrar el RFC como representante del mismo.";
	private static final String KEY_MORAL_RL = "personaMoralRepresentante";
	private static final String KEY_MORAL_AP = "personaMoralAltaPatronal";



	private static final String ID_GLOBAL_TRAMITE = "idGlobalTramite";
	private static final String RFC_REPRESENTANTE = "rfcRepresentante";
	private static final String RFC_MORAL = "rfcMoral";
	private static final String ALTA_PATRONAL_EXITO="1";
	private static final String ALTA_PATRONAL_ERROR="2";

    private static final String KEY_MENSAJE_EXITO = "mensajeExito";
    private static final String KEY_MENSAJE_ERROR = "mensajeError";

	private static final String NUM_MODALIDAD_CIUDAD = "10";
	private static final String NUM_MODALIDAD_CAMPO = "13";
	private static final Long ID_DIVISION_CAMPO = 3L;
	private static final Long ID_DIVISION_CIUDAD = 1L;
	private static final String DIVISION_CAMPO = "0";
	private static final Long ID_DIVISION_CAMPO_CANERO = 12L;
	private static final String NUM_MODALIDAD_CAMPO_CANERO = "30";
	private static final int SOLICITUD_FINALIZADA=2;

	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired
	private IndividuoServiceBusinessRemote individuoServiceBusiness;
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	@Autowired
	private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusinessRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@Autowired
	private PersonaMoralBusinessRemote personaMoralServiceBusiness;
	@Autowired
	private transient ArpBusinessRemote arpBusiness;
	@Autowired
	private ConsultaPersonaFisicaServiceBusinessRemote consultaPersonaFisicaServiceBusinessRemote;
	@Autowired
	private AfiliacionServiceBusinessRemote afiliacionService;
	@Autowired
	private SolicitudQueueProducerRemote solicitudQueueProducerRemote;
	@Autowired
	private RuleServiceBusinessRemote ruleServiceBusiness;
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@Autowired
	private ComponentesExternosBusinessRemote componentesExternosBusinessRemote;
	@Autowired
	SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	@Autowired
	FirmaDigitalBusinessRemote firmaDigitalBusiness;
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;

	@RequestMapping("/login")
	public String loginAltaPatronalMoral(HttpSession session, HttpServletRequest request) {

		return VIEW_LOGIN_MORAL;
	}

	@RequestMapping(value = "/moral",method = RequestMethod.GET)
	public String inicioAltaPatronalPersonaMotal(HttpSession session, HttpServletRequest request, @RequestParam(value="idGlobalTramite", required=false) String idGlobalTramite) {
		Fisica datosPersonales = null;


		try {
			log.error("Se inicia el proceso para obtener los parametros con tramite: " + idGlobalTramite);
			ConfirmarSolicitudResponseDTO respuesta=recuperaParametros(idGlobalTramite);
			//Implementacion del WS de economia
			log.debug("RFC header economia"+request.getHeader("rfc"));
			log.debug("RFC WS economia"+respuesta!=null?respuesta.getRfcRepLegal():"");

			if(respuesta==null || !respuesta.getRfcRepLegal().equals(request.getHeader("rfc"))){ //Caso cuando este integrado headers
				log.debug("Tramite Invalido economia");
				request.setAttribute("msgError", "Tramite inválido");
				return "tramiteInvalido";
			}else{
				log.debug("Tramite Valido economia");
				session.setAttribute(ID_GLOBAL_TRAMITE, idGlobalTramite);
				session.setAttribute(RFC_REPRESENTANTE, respuesta.getRfcRepLegal());
				session.setAttribute(RFC_MORAL, respuesta.getRfcSAS());
				log.debug("idGlobalTramite "+idGlobalTramite);
				log.debug("rfcRepresentante "+idGlobalTramite);
				log.debug("rfcMoral "+idGlobalTramite);
			}
			//Implementacion del WS de economia
			datosPersonales = obtenerDatosPersonaFirmada(request, session);
		} catch (Exception ex) {
			ex.printStackTrace();
			request.setAttribute("msgError", ex.getMessage());
			return "tramiteInvalido";
		}

		session.setAttribute(DATOS_PERSONA_KEY, datosPersonales);
		session.setAttribute(DATOS_PERSONA_SESION_KEY, datosPersonales);
		datosPersonales = (Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
		return VIEW_PRINCIPAL;
	}

	// Paso 3
	@RequestMapping("/patron/confirmar")
	public String confirmacionDatosEmpresa(HttpSession session, HttpServletRequest request) {
		Long idPersonaMoral = 3827466L;
		Moral personaMoral = this.serviciosPersonaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(idPersonaMoral);
		log.debug("La persona moral es: " + personaMoral);
		request.setAttribute("moral", personaMoral);
		return VIEW_CONFIRMACION;
	}

	@RequestMapping("/patron/domicilio")
	public String capturaDomicilioCentroTrabajo(HttpSession session, HttpServletRequest request) {

		return VIEW_DOMICILIO_CT;
	}

	@RequestMapping("/patron/personasAutorizadas")
	public String capturaPersonasAutorizadas(HttpSession session, HttpServletRequest request) {

		return VIEW_PERSONAS_AUTORIZADAS;
	}

	@RequestMapping(value = "/patron/recuperaEstructuraAltaPatronal", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody SolicitudAltaPatronalDTO recuperaEstructura(HttpSession session, HttpServletRequest request) {
		System.out.println("DevolviendoAA estructura generica para patrones");
		SolicitudAltaPatronalDTO solicitudAltaPatronal = new SolicitudAltaPatronalDTO();

		return solicitudAltaPatronal;
	}


	@RequestMapping(value = "/patron/guardarSolicitudPrincipal", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody SolicitudAltaPatronalDTO guardarSolicitudPrincipal(
			@RequestBody SolicitudAltaPatronalDTO solicidtudDTO, HttpSession session, HttpServletRequest request) {
		System.out.println("Generando Solicitud ");
		System.out.println(solicidtudDTO.toString());

		Moral personaMoral = (Moral)session.getAttribute(KEY_MORAL_AP);
		TramiteSujetoObligado tramiteSO = solicidtudDTO.getTramiteSujetoObligado();
		tramiteSO.getSujetoObligado().setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		tramiteSO.getSujetoObligado().setMoral(personaMoral);

		return solicidtudDTO;
	}

	@RequestMapping(value = "/finalizarAltaPatronal", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> finalizaAltaPatronal(
			@RequestBody SolicitudAltaPatronalDTO solicidtudDTO, HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		FirmaElectronica firma = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA);

		procesarEscrituraSocios(solicidtudDTO,session);
		TramiteSujetoObligado tramiteSO = solicidtudDTO.getTramiteSujetoObligado();
		TramiteSocios tramiteSocios = crearTramiteSocios(tramiteSO.getSujetoObligado());
		TramiteMoral tramiteEsc = crearTramiteEscrituraSindicato(tramiteSO.getSujetoObligado());

		try {
			Solicitud solicitud = afiliacionService.crearSolicitudDeAltaPatronalConEstado(tramiteSO.getSujetoObligado(), EstadoSolicitudEnum.PENDIENTE_AUTORIZACION, null,
				OrigenSolicitudEnum.ECONOMIA);

			//si los datos de firma de alta patronal existen los relacionamos antes de crear los datos de la solicitud
			if(firma != null) {
				log.debug("Existen los datos de la firma en session por lo que se guardan");
				//insertamos la firma digital
				firmaDigitalBusiness.insertarSolicitudFirmaDigital(solicitud, firma);
			}

			if(tramiteSocios != null) {
				solicitudServiceBusiness.agregarTramiteASolicitud(solicitud.getSolicitudId(), tramiteSocios);
				log.debug("Cree el tramite de alta de socios");
			}

			if(tramiteEsc != null) {
				solicitudServiceBusiness.agregarTramiteASolicitud(solicitud.getSolicitudId(), tramiteEsc);
				log.debug("Cree el tramite de alta escritura sindicato");
			}
			Solicitud temp = new Solicitud();
			temp.setSolicitudId(solicitud.getSolicitudId());
			temp.setNoFolioSolicitud(solicitud.getNoFolioSolicitud());
			result.put("solicitud", temp);
			solicitudQueueProducerRemote.encolarSolicitudAConcluir(solicitud.getNoFolioSolicitud());
		} catch(Exception e) {
			e.printStackTrace();
			result.put("error", "Ocurrio un error al procesar la solicitud");
		}

		return result;
	}

	@RequestMapping(value = "/verificaEstadoSolicitud", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> verificaEstadoSolicitud(@RequestBody Solicitud solicitud, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();

		String idGlobalTramite = (String) session.getAttribute(ID_GLOBAL_TRAMITE);
		solicitud = solicitudBusinessRemote.obtenerEstados(solicitud);
		Integer estadoSolicitud = solicitud.getEstadoSolicitud().getIdEstadoSolicitud();
		if(estadoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo())) {
			try{
			solicitud = solicitudBusinessRemote.consultarFolio(solicitud);

			for(Tramite tramite: solicitud.getTramites()) {
				if(tramite instanceof TramiteSujetoObligado) {
					result.put("tramiteSO", (TramiteSujetoObligado) tramite);
					String registroPatronalCreado = ((TramiteSujetoObligado) tramite).getSujetoObligado().getNumeroRegistroPatronal();
					log.debug("Se hace notificacion a economia con el id: " + idGlobalTramite + " con el NRP: " +registroPatronalCreado);
					notificaTramite(idGlobalTramite, registroPatronalCreado, ALTA_PATRONAL_EXITO);

				}
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(!estadoSolicitud.equals(EstadoSolicitudEnum.ATENDIDA.getCodigo()) &&  !estadoSolicitud.equals(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo())) {
			log.debug("Se hace notificacion a economia de error con el id: " + idGlobalTramite);
			notificaTramite(idGlobalTramite, "", ALTA_PATRONAL_ERROR);
		}


		result.put("solicitud", solicitud);

		return result;

	}

	/**
	 * Metodo que valida el formulario de captura de persona fisica (combos,
	 * CURP y RFC unicamente)
	 *
	 * @param oForm
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/buscaRFC", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody Fisica oForm,
			final HttpServletResponse response, final HttpSession session) {
		log.trace("entramos a RegistroPersonaFisicaCapturaValidadorController para validar el objeto de formulario --> "
                + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
		Fisica datosPersonales = (Fisica) session.getAttribute(DATOS_PERSONA_KEY);
		final Map<String, Object> result = new HashMap<String, Object>();
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
		oForm.setTipoPersona(tipoPersona);
		final Errors errors = new BindException(oForm, "model");
		new PersonaValidator().validate(oForm, errors);
		if (!errors.hasErrors()) {
			new PersonaRFCValidator().validate(oForm, errors);
		}
		if (errors.hasErrors()) {
			procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		Persona per = this.getErroresNegocio(session, oForm);
		if (per instanceof Moral) {
			session.setAttribute(KEY_MORAL_RL, (Moral)per);
			session.setAttribute(KEY_MORAL_AP, (Moral)per);
			result.put(ESTRUCTURA_FIRMA_EMPRESA_KEY, armaEstructuraEmpresa((Moral) per, datosPersonales));
			result.put(ESTRUCTURA_FIRMA_REPRESENTANTE_KEY, armaEstructuraRepresentante((Moral) per,datosPersonales));
		}
		result.put(KEY_NEGOCIO, per);
		result.put(KEY_OFORM, oForm);
		return result;

	}

	private Persona getErroresNegocio(HttpSession session, Fisica busquedaPersona) {
		EstatusPersona estatusPersonaARepresentar = EstatusPersona.Existe_en_bdtu;


		Fisica datosPersonales = (Fisica) session.getAttribute(DATOS_PERSONA_KEY);

		Persona resultado = new Persona();
		Persona personaEncontrada = null;
		// Se verifica que el rfc de la empresa a representar no coincida con el
		// rfc que es el representante legal
		if (busquedaPersona.getRfc().equals(datosPersonales.getRfc())) {
			resultado.setErrorFormGeneral("No es posible registrar el RFC como representante del mismo");
			return resultado;
		}

		try {
			if (busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)) {
				personaEncontrada = individuoServiceBusiness.consultarPersonaFisicaIMSSPorRFC(busquedaPersona);
				personaEncontrada.setTipoPersona(new TipoPersona());
				personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			} else if (busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)) {
				personaEncontrada = individuoServiceBusiness.consultarPersonaMoralIMSSPorRFC(busquedaPersona);
				personaEncontrada.setTipoPersona(new TipoPersona());
				personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			}
		} catch (PersonasNoLocalizadasException e1) {
			estatusPersonaARepresentar = EstatusPersona.Inexistente;
			log.error("El RFC no se encuentra registrado dn BDTU, se buscarï¿½ en el SAT");
			try {
				if (busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)) {
					personaEncontrada = personaBusiness.buscarPersonaFisicaPorRfcEnSat(busquedaPersona.getRfc());
					if (personaEncontrada == null) {
						resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, "
								+ "por favor verifique la informaci\u00F3n y vuelva a intentarlo");
						return resultado;
					}
					personaEncontrada.setTipoPersona(new TipoPersona());
					personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				} else if (busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)) {
					personaEncontrada = personaBusiness.buscarPersonaMoralPorRfcEnSat(busquedaPersona.getRfc());
					if (personaEncontrada == null) {
						resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, "
								+ "por favor verifique la informaci\u00F3n y vuelva a intentarlo");
						return resultado;
					}
					personaEncontrada.setTipoPersona(new TipoPersona());
					personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				}
			} catch (ClienteWebserviceSatRfcException cwsException) {
				cwsException.printStackTrace();
				resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, "
						+ "por favor verifique la informaci\u00F3n y vuelva a intentarlo");
				return resultado;
			}
		} catch (ClienteWebserviceRenapoCurpException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (ClienteWebserviceSatRfcException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (ErrorComparacionDatosRENAPOException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (ErrorComparacionDatosSATException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (DiferenciasRENAPOContraSAT e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		} catch (PersonaSinCalificacionesException e) {
			resultado.setErrorFormGeneral(e.getMessage());
			e.printStackTrace();
			return resultado;
		}

		// Verificamos que se haya encontrado algun patron sujeto obligado en
		// caso contrario se muestra mensaje de error
		if (personaEncontrada != null) {
			// SE crea un objeto representante legal para saber si ya existe la
			// relacion
			RepresentanteLegal representante = new RepresentanteLegal();
			representante.setCveIdPersona(personaEncontrada.getIdPersona());
			representante.setPersonaFisica(datosPersonales);
			representante.setTipoPersonaRepresentada(personaEncontrada.getTipoPersona());
			representante.setAccion(TipoAccionAfectacionEnum.AGREGAR);
			representante.setIndActAdmonDominio(BigDecimal.ONE);// Por defecto se agregan actos de administraciï¿½n y dominio
			// Verificamos que no exista la relacion como epresentante
			try {
				if (!estatusPersonaARepresentar.equals(EstatusPersona.Inexistente))// Si la persona no existe la validacion no se aplica
					representanteLegalServiceBusinessRemote.validaExisteRepresentanteLegal(representante);
			} catch (RepresentanteLegalInvalidoException e) {
				resultado.setErrorFormGeneral("Ocurrio un error inesperado");
				return resultado;
			} catch (RepresentanteLegalYaExisteException e) {
				resultado.setErrorFormGeneral("Ya existe la relacion como representante legal");
				//return resultado;
			}
			if (personaEncontrada.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)) {
				Fisica perFisica = new Fisica();
				Fisica fisicaEncontrada = (Fisica) personaEncontrada;
				perFisica.setCurp(fisicaEncontrada.getCurp());
				perFisica.setRfc(fisicaEncontrada.getRfc());
				perFisica.setNombre(fisicaEncontrada.getNombre().trim() + " "
						+ (fisicaEncontrada.getPrimerApellido() != null ? fisicaEncontrada.getPrimerApellido() : "")
						+ " "
						+ (fisicaEncontrada.getSegundoApellido() != null ? fisicaEncontrada.getSegundoApellido() : ""));

				return perFisica;
			} else {
				Moral perMoral = (Moral) personaEncontrada;
				perMoral.setRazonSocial(perMoral.getRazonSocial().replace("\"", "\\\""));
				perMoral.setTipoSociedad(perMoral.getTipoSociedad());
				perMoral.setRfc(perMoral.getRfc());
				if(resultado.getErrorFormGeneral()!=null){
					perMoral.setErrorFormGeneral("existeRelacion");
				}
				return perMoral;
			}
		}
		resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS");
		return resultado;
	}

	private EstructuraFirmaDTO armaEstructuraEmpresa(Moral perMoral, Fisica datosPersonales) {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo());

		EstructuraFirmaDTO estructuraFirmaDTO = new EstructuraFirmaDTO();
		estructuraFirmaDTO.setIdTipoSolicitud(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo().toString());
		estructuraFirmaDTO.setDescripcionTipoSolicitud(DESC_TIPO_SOLICITUD);
		estructuraFirmaDTO.setIdTipoTramite(listTipoTramite);
		estructuraFirmaDTO.setFolioSolicitud("");
		estructuraFirmaDTO.setCurp("");
		estructuraFirmaDTO.setRfc(perMoral.getRfc());
		estructuraFirmaDTO.setValidarRFC(true);
		estructuraFirmaDTO.setRegistroPatronal("");
		estructuraFirmaDTO.setNombreCompleto(perMoral.getRazonSocial());
		estructuraFirmaDTO.setFechaElectronica(formatter.format(Calendar.getInstance().getTime()));
		estructuraFirmaDTO.setCad_original(generarCadenaOriginal(perMoral, null, null));
		estructuraFirmaDTO.setTipo_operacion("firmaCMS");
		estructuraFirmaDTO.setFirma_archivo(false);
		estructuraFirmaDTO.setMin_archivos("0");
		estructuraFirmaDTO.setMax_archivos("0");
		estructuraFirmaDTO.setMostrarCartaTerminos(false);
		estructuraFirmaDTO.getAfectado()[0] = (new RepresentanteDTO(perMoral.getRfc(), perMoral.getRazonSocial(), ""));
		estructuraFirmaDTO.getRepresentados()[0] = (
                new RepresentanteDTO(datosPersonales.getRfc(), datosPersonales.getNombreCompleto(), ""));
		estructuraFirmaDTO.setTipoAcuse("1");
		estructuraFirmaDTO.setAcuse("ARL");

		return estructuraFirmaDTO;
	}

	private String generarCadenaOriginal(Persona persona, Solicitud solicitud, String tipoTramite) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		StringBuffer contenidoAFirmar = new StringBuffer();
		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");
		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		contenidoAFirmar.append(tipoTramite == null ? DESC_TIPO_SOLICITUD : tipoTramite).append("|");
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(Calendar.getInstance().getTime());
		// Folio
		if(solicitud != null && solicitud.getNoFolioSolicitud() != null) {
			contenidoAFirmar.append("Folio:");
			contenidoAFirmar.append(solicitud.getNoFolioSolicitud() != null
			? solicitud.getNoFolioSolicitud() : "").append("|");
		}
		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");
		datosEntradaFirma.setRfc(persona.getRfc());
		// Nombre, denominacion o razon social del interesado (y en su caso el
		// de su representante o persona autorizada)
		StringBuffer sbnombre = new StringBuffer();
		if (persona instanceof Fisica) {
			if(((Fisica) persona).getNombre() != null)
				sbnombre.append(((Fisica) persona).getNombre().trim()).append(" ");
			if (StringUtils.isNotBlank(((Fisica) persona).getPrimerApellido())) {
				sbnombre.append(((Fisica) persona).getPrimerApellido()).append(" ");
			}
			if (StringUtils.isNotBlank(((Fisica) persona).getSegundoApellido())) {
				sbnombre.append(((Fisica) persona).getSegundoApellido());
			}
		} else {
			Moral moral = (Moral) persona;
			sbnombre.append(moral.getRazonSocial());

			if(moral.getTipoSociedad() != null && moral.getTipoSociedad().getDescripcionAbreviada() != null) {
				sbnombre.append(" " + moral.getTipoSociedad().getDescripcionAbreviada());
			}
		}
		contenidoAFirmar.append("Nombre o Razon Social:");
		contenidoAFirmar.append(sbnombre.toString().toUpperCase()).append("|");
		datosEntradaFirma.setNombreCompleto(sbnombre.toString());
		// CURP
		if (persona instanceof Fisica) {
			Fisica pFisica = (Fisica) persona;
			if (!StringUtils.isEmpty(pFisica.getCurp())) {
				contenidoAFirmar.append("CURP:");
				contenidoAFirmar.append(pFisica.getCurp()).append("|");
			}
			datosEntradaFirma.setCurp(pFisica.getCurp());
		}
		contenidoAFirmar.append("|");
		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());

		return contenidoAFirmar.toString();
	}

	private EstructuraFirmaDTO armaEstructuraRepresentante(Moral perMoral,Fisica datosPersonales) {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo());

		EstructuraFirmaDTO estructuraFirmaDTO = new EstructuraFirmaDTO();
		estructuraFirmaDTO.setIdTipoSolicitud(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo().toString());
		estructuraFirmaDTO.setDescripcionTipoSolicitud(DESC_TIPO_SOLICITUD);
		estructuraFirmaDTO.setIdTipoTramite(listTipoTramite);
		estructuraFirmaDTO.setFolioSolicitud("");
		estructuraFirmaDTO.setCurp("");
		estructuraFirmaDTO.setRfc(datosPersonales.getRfc());
		estructuraFirmaDTO.setValidarRFC(true);
		estructuraFirmaDTO.setRegistroPatronal("");
		estructuraFirmaDTO.setNombreCompleto(datosPersonales.getNombreCompleto());
		estructuraFirmaDTO.setFechaElectronica(formatter.format(Calendar.getInstance().getTime()));
		estructuraFirmaDTO.setCad_original(generarCadenaOriginal(datosPersonales, null, null));
		estructuraFirmaDTO.setTipo_operacion("firmaCMS");
		estructuraFirmaDTO.setFirma_archivo(false);
		estructuraFirmaDTO.setMin_archivos("0");
		estructuraFirmaDTO.setMax_archivos("0");
		estructuraFirmaDTO.setMostrarCartaTerminos(false);
		estructuraFirmaDTO.setTipoAcuse("1");
		estructuraFirmaDTO.setAcuse("ARL");
		estructuraFirmaDTO.getAfectado()[0] = (new RepresentanteDTO(perMoral.getRfc(), perMoral.getRazonSocial(), ""));
		estructuraFirmaDTO.getRepresentados()[0] = (
                new RepresentanteDTO(datosPersonales.getRfc(), datosPersonales.getNombreCompleto(), ""));

		return estructuraFirmaDTO;
	}

	/**
	 * Metodo para crear la solicitud de registro de representado legal
	 * @param model
	 * @param busquedaPersona
	 * @param result
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/crear/solicitud", method = { RequestMethod.GET,RequestMethod.POST })
	@ResponseBody SolicitudAltaPatronalDTO validarPersona(@RequestBody SolicitudAltaPatronalDTO solicitudAltaPatronalDTOReq, HttpServletRequest request, HttpSession session) {
		//Se crean los objetos necesarios
		SolicitudAltaPatronalDTO solicitudAltaPatronalDTO = new SolicitudAltaPatronalDTO();
		Solicitud solicitud = new Solicitud();
		Fisica busquedaPersona = solicitudAltaPatronalDTOReq.getBusquedaPersona();
		Fisica datosPersonales = (Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
		SujetoObligado sujetoEncontrado = null;

		//Se verifica que el rfc del Representado y Representante no sea el mismo.
		if(busquedaPersona.getRfc().equals(datosPersonales.getRfc())) {
			solicitud.setErrorFormGeneral(MSG_ERROR_REPRESENTANTE_INVALIDO);
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		}
		//Se verifica que el RFC del representante correponda con el RFC que viene en la firma
//		String rfc = busquedaPersona.getRepresentantesLegales().get(0).getPersonaFisica().getRfc();
//		if(rfc != null && !rfc.isEmpty() && !rfc.equals(datosPersonales.getRfc())) {
//			solicitud.setErrorFormGeneral(MSG_ERROR_RFC_REPRESENTANTE);
//			solicitudAltaPatronalDTO.setSolicitud(solicitud);
//			return solicitudAltaPatronalDTO;
//		}
		Persona personaEncontrada = null;
		EstatusPersona estatusPersonaARepresentar = EstatusPersona.Existe_en_bdtu;

		try {
			if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
				personaEncontrada = individuoServiceBusiness.consultarPersonaFisicaIMSSPorRFC(busquedaPersona);
				personaEncontrada.setTipoPersona(new TipoPersona());
				personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				sujetoEncontrado = new SujetoObligado();
				sujetoEncontrado.setFisica((Fisica)personaEncontrada);
				sujetoEncontrado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
			}else if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
				personaEncontrada = individuoServiceBusiness.consultarPersonaMoralIMSSPorRFC(busquedaPersona);
				personaEncontrada.setTipoPersona(new TipoPersona());
				personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				sujetoEncontrado = new SujetoObligado();
				sujetoEncontrado.setMoral((Moral)personaEncontrada);
				sujetoEncontrado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
			}
		} catch (PersonasNoLocalizadasException e1) {
			estatusPersonaARepresentar = EstatusPersona.Inexistente;
			log.error("El RFC no se encuentra registrado dn BDTU, se buscara en el SAT");
			//TODO
			try{
				if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
					personaEncontrada = personaBusiness.buscarPersonaFisicaPorRfcEnSat(busquedaPersona.getRfc());
					personaEncontrada.setTipoPersona(new TipoPersona());
					personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
					sujetoEncontrado = new SujetoObligado();
					sujetoEncontrado.setFisica((Fisica)personaEncontrada);
					sujetoEncontrado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
				}else if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
					personaEncontrada = personaBusiness.buscarPersonaMoralPorRfcEnSat(busquedaPersona.getRfc());
					personaEncontrada.setTipoPersona(new TipoPersona());
					personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
					sujetoEncontrado = new SujetoObligado();
					sujetoEncontrado.setMoral((Moral)personaEncontrada);
					sujetoEncontrado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
				}
			}catch(ClienteWebserviceSatRfcException cwsException){
				cwsException.printStackTrace();
				solicitud.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, " +
					"por favor verifique la informaci\u00F3n y vuelva a intentarlo");
				solicitudAltaPatronalDTO.setSolicitud(solicitud);
				return solicitudAltaPatronalDTO;
			}
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		} catch (ErrorComparacionDatosRENAPOException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		} catch (ErrorComparacionDatosSATException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		} catch (DiferenciasRENAPOContraSAT e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		} catch (PersonaSinCalificacionesException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		}
		//SE crea un objeto representante legal para saber si ya existe la relacion
		RepresentanteLegal representante = new RepresentanteLegal();
		representante.setCveIdPersona(busquedaPersona.getIdPersona());//Representado
		representante.setPersonaFisica(datosPersonales);//Representante Legal
		representante.setTipoPersonaRepresentada(new TipoPersona());
		representante.getTipoPersonaRepresentada().setIdTipoPersona(busquedaPersona.getTipoPersona().getIdTipoPersona());
		representante.setAccion(TipoAccionAfectacionEnum.AGREGAR);
		representante.setIndActAdmonDominio(BigDecimal.ONE);//Por defecto se agregan actos de administraciï¿½n y dominio
		//Verificamos que no exista la relacion como epresentante
		try {
			if(!estatusPersonaARepresentar.equals(EstatusPersona.Inexistente) )
				representanteLegalServiceBusinessRemote.validaExisteRepresentanteLegal(representante);
		} catch (RepresentanteLegalInvalidoException e) {
			solicitud.setErrorFormGeneral("Ocurri\u00F3 un error inesperado");
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		} catch (RepresentanteLegalYaExisteException e) {
			solicitud.setErrorFormGeneral("Ya existe la relaci\u00F3n como representante legal");
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		}
		//Si no existe la relacion agregamos a la persona actual como representante
		//Se crea la solicitud de registro de representado legal y se retorna en el model
		try {
			Persona personaAcuse = null;
			List<RepresentanteLegal> representantes = null;
			if(personaEncontrada.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
				representantes = personaEncontrada.getRepresentantesLegales();
				representantes = representantes == null ? new ArrayList<RepresentanteLegal>() : representantes;
				representante.setCveIdPersona(personaEncontrada.getIdPersona());
				representantes.add(representante);
				personaEncontrada.setRepresentantesLegales(representantes);
				Fisica representadoFisica = null;
				if(!estatusPersonaARepresentar.equals(EstatusPersona.Inexistente) )
					representadoFisica = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(datosPersonales.getIdPersona());
				else
					representadoFisica = (Fisica)personaEncontrada;//Como no  existe en bdtu se manda la info del SAT

				personaAcuse = representadoFisica;
			}else {
				representantes = personaEncontrada.getRepresentantesLegales();
				representantes = representantes == null ? new ArrayList<RepresentanteLegal>() : representantes;
				representante.setCveIdPersona(personaEncontrada.getIdPersona());
				representantes.add(representante);
				personaEncontrada.setRepresentantesLegales(representantes);
				Moral representadaMoral = null;
				if(!estatusPersonaARepresentar.equals(EstatusPersona.Inexistente) )
					representadaMoral = personaMoralBusiness.getPersonaMoral(personaEncontrada.getIdPersona());
				else
					representadaMoral = (Moral)personaEncontrada;//Como no  existe en bdtu se manda la info del SAT

				personaAcuse = representadaMoral;
			}
			Fisica personaRepresentante = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(datosPersonales.getIdPersona());
			solicitud = this.crearSolicitud(personaEncontrada, datosPersonales, session, request, solicitudAltaPatronalDTOReq.getFirmaEmpresa());

			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				generarCadenaOriginal(datosPersonales, solicitud, null);
				Fisica personaSesion=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				obtenerDatosAcuse(solicitud, personaSesion, session);
			}

			solicitudAltaPatronalDTO.setTramite(solicitud.getTramites().get(0));
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			solicitudAltaPatronalDTO.setSujetoObligado(sujetoEncontrado);

			session.setAttribute(SUJETO_OBLIGADO_KEY, sujetoEncontrado);
			session.setAttribute(KEY_RFC_SOLICITANTE, personaRepresentante.getRfc());
		} catch (Exception e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral("No fue posible crear la solicitud");
			solicitudAltaPatronalDTO.setSolicitud(solicitud);
			return solicitudAltaPatronalDTO;
		}
		return solicitudAltaPatronalDTO;
	}

	/**
	 * Metodo para crear la solicitud con su tramite de representante legal
	 * @param sujetoObligado
	 * @param fisica
	 * @param firmaElectronica
	 * @return
	 * @throws SolicitudNoValidaException
	 */
	private Solicitud crearSolicitud(Persona personaRepresentada, Fisica fisica, HttpSession session,
			HttpServletRequest request, FirmaElectronica firmaElectronica) throws SolicitudNoValidaException {

		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		OrigenSolicitudEnum origenSolicitud = OrigenSolicitudEnum.ECONOMIA;
		Date fechaActual = new Date();
		Usuario usuario = new CommonValidator().getUsuarioSession(session);
		Solicitud solicitud = new Solicitud();
		solicitud.setOrigenSolicitud(new OrigenSolicitud());
		solicitud.getOrigenSolicitud().setIdTipoSolicitud(origenSolicitud.getId());
		solicitud.setFechaSolicitud(fechaActual);
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor().longValue());
		solicitud.setEstadoSolicitud(new EstadoSolicitud());
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		solicitud.setTramites(new ArrayList<Tramite>());
		TramiteRepresentanteLegal tramiteRP = new TramiteRepresentanteLegal();
		tramiteRP.setFisica(fisica);

		if(personaRepresentada instanceof Fisica)
			tramiteRP.setFisicaRepresentada((Fisica)personaRepresentada);
		else if(personaRepresentada instanceof Moral)
			tramiteRP.setMoralRepresentada((Moral)personaRepresentada);

		tramiteRP.setEstadoTramite(new EstadoTramite());
		tramiteRP.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		tramiteRP.getEstadoTramite().setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
		tramiteRP.setTipoTramite(new TipoTramite());
		tramiteRP.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo());
		tramiteRP.setFechaTramite(fechaActual);
		tramiteRP.setFechaPresentacion(fechaActual);
		tramiteRP.setIndRatificado(false);
		tramiteRP.setFirmaElectronica(firmaElectronica);
		tramiteRP.setAcuseVentanilla(new AcuseVentanilla());
		if(usuario != null){
			tramiteRP.getAcuseVentanilla().setUsuarioVentanilla(usuario.getUsuario());
			if(usuario.getUsuarioFuncionario()!=null
					&& usuario.getUsuarioFuncionario().getSubdelegacion()!=null){
				tramiteRP.getAcuseVentanilla().setIdSubdelegacion(usuario
					.getUsuarioFuncionario().getSubdelegacion().getId());
			}
		}
		solicitud.getTramites().add(tramiteRP);
		solicitud.setSolicitante(usuario);

		solicitud = solicitudBusinessRemote.crear(solicitud);
		return solicitud;
	}

	/**
	 * Metodo para finalizar la solicitud
	 * @param solicitud
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/finalizar/solicitud/registroRepresentado", method = { RequestMethod.GET,RequestMethod.POST })
	public @ResponseBody Map<String, ? extends Object> finalizarSolicitudRegistroRepresentado(@RequestBody SolicitudAltaPatronalDTO solicitudAltaPatronal,
			HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		FirmaElectronica firmaElectronica = null;
		try {
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(solicitudAltaPatronal.getSolicitud().getSolicitudId());
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			firmaElectronica = solicitudAltaPatronal.getFirmaRepresentante();
			FirmaElectronica firmaElectronicaPatron = solicitudAltaPatronal.getFirmaEmpresa();
			solicitud.setFirmaElectronica(firmaElectronicaPatron);
			actualizarTipoPoderRepresentante(solicitud, solicitudAltaPatronal.getBusquedaPersona().getTipoPoder().getIdTipoPoder());
			solicitudBusinessRemote.finalizarSolicitud(solicitud, firmaElectronica);

			Moral moral = (Moral) session.getAttribute(KEY_MORAL_RL);
			try{
				Persona busqueda = new Persona();
				busqueda.setRfc(moral.getRfc());
				Moral personaEncontrada = individuoServiceBusiness.consultarPersonaMoralIMSSPorRFC(moral);
				session.setAttribute(KEY_MORAL_RL, personaEncontrada);
				session.setAttribute(KEY_MORAL_AP, personaEncontrada);
			} catch (PersonasNoLocalizadasException e) {
				e.printStackTrace();
				result.put(KEY_MENSAJE, e.getMessage());
				result.put("error",true);
				return result;
			}
			result.put(KEY_MENSAJE, MSG_FINALIZAR_SOLICITUD);
		} catch(AbstractException e) {
			log.error(e);
			result.put(KEY_MENSAJE, e.getMessage());
			result.put("error",true);
		}
		return result;
	}

	private Solicitud actualizarTipoPoderRepresentante(Solicitud solicitud, Integer idTipoPoder){
		TramiteRepresentanteLegal tramiteRL =(TramiteRepresentanteLegal)solicitud.getTramites().get(0);
		tramiteRL.getFisica().setTipoPoder(new TipoPoder());
		tramiteRL.getFisica().getTipoPoder().setIdTipoPoder(idTipoPoder);
		solicitud.getTramites().set(0,tramiteRL);
		return solicitud;
	}

	private void obtenerDatosAcuse(Solicitud solicitud, Persona persona, HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(Calendar.getInstance().getTime());
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(Calendar.getInstance().getTime());
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
		datosEntradaFirma.setNombreCompleto(sbnombre.toString().toUpperCase());
		// CURP
		if (persona instanceof Fisica) {
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		}
		//session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}

	@RequestMapping(value = "/visualizacionPrevia", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> visualizacionPrevia(final @RequestBody SolicitudAltaPatronalDTO solicitudAltaPatronalDTO,
    		 HttpServletResponse response,HttpServletRequest request,HttpSession session) {
	 System.out.println("Se genera visualizacion Previa "+arpBusiness);

	 try {
		 procesarEscrituraSocios(solicitudAltaPatronalDTO,session);

		 byte[] archivo = arpBusiness.visualizacionPreviaAltaPatron(solicitudAltaPatronalDTO.getTramiteSujetoObligado());

		System.out.println("Archivo recuperado "+archivo);
		final String acusePdf = Base64.encode(archivo);
		request.getSession().setAttribute(EnviaArchivoServlet.DOCUMENTO_PDF, acusePdf);
		request.getSession().setAttribute(EnviaArchivoServlet.NOMBRE_ARCHIVO_PDF, "acuseAltaPatronal.pdf");
		request.getSession().setAttribute(EnviaArchivoServlet.TIPO_DESCARGA_PDF, EnviaArchivoServlet.MUESTRA_PDF);
		System.out.println("Se suben archivos a sesion");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return null;

 }

	 @RequestMapping(value = "/get/personaAutorizada", method = RequestMethod.POST)
	 public @ResponseBody Map<String, ? extends Object> obtenerDatosPersonaAutorizada( @RequestBody Fisica persona, HttpServletResponse response, HttpServletRequest request,
			 HttpSession session) {
		 Map<String, Object> result = new HashMap<String, Object>();

		 try {
			 persona = consultaPersonaFisicaServiceBusinessRemote.getPersonaByCurpImssEntidadesExternas(persona);
			 Fisica personaFirmada= (Fisica) session.getAttribute(DATOS_PERSONA_KEY);
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
	  * Metodo para obtener los datos de la persona que ingresa al sistema
	  * @param request
	  * @return
	  */
	 private Fisica obtenerDatosPersonaFirmada(HttpServletRequest request, HttpSession session) throws Exception {
			//UsuarioSSO sso = this.procesarUsuarioSSO(request);

		 Long idPersona = null;
		 Fisica fisica = new Fisica();
		 Usuario usuario = null;
		 String rfcRepresentante = null;
		 String correoRepresentante = null;
		 String numeroSerieRep = null;
		 String curpUsuario = null;
		 String nombreRepresentante = null;
		 String role = null;
		 String via = null;

		 try{

			 rfcRepresentante = request.getHeader("rfc");
			 correoRepresentante = request.getHeader("correo");
			 String hex=request.getHeader("numeroSerie");
			 System.out.println("Serie Hexa "+hex);
			 StringBuilder output = new StringBuilder();
			    for (int i = 0; i < hex.length(); i+=2) {
			        String str = hex.substring(i, i+2);
			        output.append((char)Integer.parseInt(str, 16));
			 }
			 numeroSerieRep = output.toString();
			 System.out.println("Ascci "+numeroSerieRep);
			 nombreRepresentante = request.getHeader("nombreCompleto");
			 curpUsuario = request.getHeader("curp");
			 role = request.getHeader("Role");
			 via = request.getHeader("Via");

			 FirmaElectronica firmaElectronica = new FirmaElectronica();
			 firmaElectronica.setCurp(curpUsuario);
			 firmaElectronica.setSerialCertificado(numeroSerieRep);
			 session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);

			 fisica.setRfc(rfcRepresentante);
			 fisica.setCurp(curpUsuario);

			 log.error("Recuperando los headers [rfc - "+rfcRepresentante + "]\n[correo - "+correoRepresentante+ "]\n[numeroSerie - "+numeroSerieRep+
					 "]\n[curp - "+curpUsuario+"]\n[nombreCompleto - "+nombreRepresentante + "]\n[Role "+role+"]\n[Via - "+via + "]");

		 }catch(Exception e){
			 e.printStackTrace();
		 }

		 //si traemos la curp, buscamos si ya existe la cuenta
		 if(curpUsuario != null) {
			 try {
				usuario =  componentesExternosBusinessRemote.recuperaUsuarioEsquemaSeguridadByCURP(curpUsuario);
			 } catch(EsquemaSegurdiadException e) {
				 e.printStackTrace();
				 usuario = registraPersona(fisica,session,curpUsuario, numeroSerieRep);
			 } catch(UsuarioNoEncontradoException e) {
				 e.printStackTrace();
				 usuario = registraPersona(fisica,session,curpUsuario, numeroSerieRep);
			 }
			 //si encontramos al usuario obtenemos el id de la persona que vamos a utilizar
			 if(usuario != null) {
				 log.debug("El usuario con curp " + curpUsuario + " tiene el id de persona " + usuario.getFisica().getIdPersona());
				 idPersona = usuario.getFisica().getIdPersona();
			 }
		 }

		 try {
			 Fisica personaSesion = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(idPersona);
			 return personaSesion;
		 } catch (PersonaFisicaNoEncontradaException e) {
			 //this.log.error("ocurrio un error al consultar las solicitudes abiertas para la persona "+ "[" + sso.getIdPersona() + "]", e);
		 }
		 return null;
	 }

	 public Usuario registraPersona(Fisica fisica,HttpSession session, String curp, String serial)throws Exception{
		 Usuario usuario  = null;

		 try {

			 log.error("El usuario a registrar tiene la curp " + curp + " y el serial " + serial);
			 fisica = validaPersonaRegistroUsuario(fisica, session);

			 fisica = registrarPersonaFisica(fisica, session, curp, serial);

			 usuario = new Usuario();
			 usuario.setFisica(fisica);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		}

		return usuario;
	 }



		/**
		 * Metodo para finalizar la solicitud
		 * @param solicitud
		 * @param request
		 * @param session
		 * @return
		 */
		@RequestMapping(value = "/finalizar/solicitud/", method = RequestMethod.POST)
		public @ResponseBody Map<String, ? extends Object> finalizarSolicitudRegistroRepresentado(
				@PathVariable Integer idTipoPoder, @PathVariable Long idSolicitud,
				HttpServletRequest request, HttpSession session, HttpServletResponse response) {
			Map<String, Object> result = new HashMap<String, Object>();
			FirmaElectronica firmaElectronica = null;
			try {
				Solicitud solicitud = new Solicitud();
				solicitud.setSolicitudId(idSolicitud);
				solicitud = solicitudBusinessRemote.consultar(solicitud);
				solicitud.setSolicitante(new CommonValidator().getUsuarioSession(session));
				if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
					firmaElectronica = (FirmaElectronica)session.getAttribute("");
					FirmaElectronica firmaElectronicaPatron = (FirmaElectronica) session.getAttribute("");
					solicitud.setFirmaElectronica(firmaElectronicaPatron);
					actualizarTipoPoderRepresentante(solicitud, idTipoPoder);
					solicitudBusinessRemote.finalizarCapturaSolicitud(solicitud, firmaElectronica);
					result.put(KEY_MENSAJE, MSG_FINALIZAR_SOLICITUD);
				}else{
					//No se finalizan solicitudes de Ventanilla ya que falta indicar el RL,
					//posterior a ello se manda a encolar la consilicitud para concluir.
					actualizarTipoPoderRepresentante(solicitud, idTipoPoder);
					solicitudBusinessRemote.actualizarTramites(solicitud);
					result.put(KEY_MENSAJE, MSG_ACTUALIZADA_SOLICITUD);
				}
			} catch(AbstractException e) {
				log.error(e);
				result.put(KEY_MENSAJE, e.getMessage());
			}
			return result;
		}

    @RequestMapping(value = "/patron/actualizar/clasificacion", method = RequestMethod.POST)
    public @ResponseBody
    Clasificacion actualizarClasificacion(@RequestBody Clasificacion inputObject) {
        SujetoObligado sujetoTramite = inputObject.getSujetoObligado();

        try {
            Proceso proceso = inputObject.getSujetoObligado().getProceso();
            inputObject.getSujetoObligado().setProceso(null);
            SujetoObligado soProceso = new SujetoObligado();
            soProceso.setCveIdSujetoObligado(inputObject.getSujetoObligado()
                    .getCveIdSujetoObligado());
            proceso.setSujetoObligado(soProceso);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputObject;
    }

    @RequestMapping("/muestraCarta")
	public String muestraCarta(HttpSession session, HttpServletRequest request) {
		return VIEW_CARTA_TERMINOS;
	}

    @RequestMapping(value = "/descargaDocumentoResultante", method = {RequestMethod.GET, RequestMethod.POST })
    public void descargarDocumento(
			@RequestParam("idSolicitud") Long idSolicitudHashed,
			@RequestParam("idTramite") Long idTramiteHashed,
			@RequestParam("tipoDocumento") Integer tipoDocumentoHashed,
			Model model, HttpServletResponse response, HttpSession session) {

		byte[] archivo = null;

		String nomArh = tipoDocumentoHashed.equals(55) ? "TIP" : "ARP";
		String nombreArchivo = nomArh + "_" + idTramiteHashed + ".pdf";
		archivo = solicitudBusinessRemote.obtenerDocumentoResultante(idSolicitudHashed,
				idTramiteHashed, tipoDocumentoHashed);

		try {
			if (archivo != null) {
				log.debug("El documento no es nulo");

				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition", "attachment;filename="
						+ nombreArchivo);
				response.setContentLength(archivo.length);
				response.getOutputStream().write(archivo);
				response.getOutputStream().close();
			} else {
				this.log.debug("No hay documento");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.log.debug("Termina generaciï¿½n de documentos de solicitud");
	}

	@RequestMapping(value = "/mostrarDocumentoResultante", method = {RequestMethod.GET, RequestMethod.POST })
	public void mostrarDocumentoResultante(
			@RequestParam("idSolicitud") Long idSolicitudHashed,
			@RequestParam("idTramite") Long idTramiteHashed,
			@RequestParam("tipoDocumento") Integer tipoDocumentoHashed,
			Model model, HttpServletResponse response, HttpSession session) {

		byte[] archivo = null;


		String nombreArchivo = "Solicitud_" + idTramiteHashed + ".pdf";
		archivo = solicitudBusinessRemote.obtenerDocumentoResultante(idSolicitudHashed,
				idTramiteHashed, tipoDocumentoHashed);

		try {
			if (archivo != null) {
				log.debug("El documento no es nulo");

				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition", "inline;filename="
						+ nombreArchivo);
				response.setContentLength(archivo.length);
				response.getOutputStream().write(archivo);
				response.getOutputStream().close();
			} else {
				this.log.debug("No hay documento");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.log.debug("Termina generaciï¿½n de documentos de solicitud");
	}

	/**
	 * Metodo super chingon que hace las validaciones del inicio del alta patronal
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validarPersonaMoral", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> validaPersonaParaAltaREST(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Moral patronPersonaMoral = (Moral) session.getAttribute(KEY_MORAL_RL);
		Moral empresaMoral = null;
		boolean tieneEscritura = false;
		Integer validaMoralActa;
		Integer validaMoralSindicato;

		//Datos respuesta ica
		ICADatosRespuesta icaDatosRespuesta = null;
		final Integer VALIDACIONES_INCORRECTAS = 0; //si existe algun problema que no permita el alta patronal
		final Integer VALIDACIONES_CORRECTAS = 1; //Si no existe ningun problema y cuenta ccon acta y socios o con sindicato
		final Integer REQUIERE_ESCRITURA_SINDICATO = 2;//si no cuenta con acta o sindicato
		final Integer REQUIERE_SOCIOS = 3;//si cuenta con acta constitutiva y no cuenta con socios
		Integer codigoRespuesta = VALIDACIONES_CORRECTAS;
		String error = "OK";

		//si alguno de estos metodos manda excepcion, quiere decir que el tramite no es posible realizarlo
		/*try {
			SE COMENTA DE ACUERDO A LA NUEVA LEY APLICABLE
			sujetoObligadoServiceBusiness.validaCveIdPersonaPorRegistroPatronalClaseActivo(patronPersonaMoral.getRfc(), patronPersonaMoral.getIdPersona().intValue());
			//Modificacion por MM 4121544 / WO1504931 - Actualizar validación de fracción en nuevos Registros Patronales
			//ruleServiceBusiness.validarFraccionesConsistentesPorPatron(patronPersonaMoral.getRfc());
		} catch(GestionPatronalBusinessException e) {
			e.printStackTrace();
			error = e.getMessage();
			codigoRespuesta = VALIDACIONES_INCORRECTAS;
		}*/

		//si no existe limitante para continuar con el tramite
		if(codigoRespuesta != 0) {
			//se valida si tiene acta constitutiva
			validaMoralActa = personaMoralServiceBusiness.consultaActaConstitutivaPersonaMoral(patronPersonaMoral.getIdPersona());
			//si no tiene acta constitutiva
			if (validaMoralActa == 0) {
				//validamos
				log.debug("El RFC " + patronPersonaMoral.getRfc() + " no tiene acta constitutiva, se valida si tiene sindicato");
				validaMoralSindicato= personaMoralServiceBusiness.consultaSindicatoPersonaMoral(patronPersonaMoral.getIdPersona());
				if (validaMoralSindicato > 0) {
					log.debug("El RFC " + patronPersonaMoral.getRfc() + " si tiene sindicato");
					//Regresa registro y se manda parametro 0 para seguir con Alta
					codigoRespuesta = VALIDACIONES_CORRECTAS;
				} else {
					log.debug("El RFC " + patronPersonaMoral.getRfc() + " no tiene ni escritura ni sindicato");
					codigoRespuesta = REQUIERE_ESCRITURA_SINDICATO;
				}
			} else {
				log.debug("El RFC " + patronPersonaMoral.getRfc() + " tiene escritura por lo que no se valida si tiene sindicato");
				//solo si tiene escritura se valida si tiene socios
				try {
					log.debug("Se valida si el RFC " + patronPersonaMoral.getRfc() + " tiene socios ya que si tiene escritura");
					ruleServiceBusiness.validarSociosRequeridos(patronPersonaMoral.getIdPersona());
				} catch (GestionPatronalBusinessException e) {
					log.debug("El RFC " + patronPersonaMoral.getRfc() + " no tiene socios registrados");
					e.printStackTrace();
					//Se manda este codigo ya que cuenta con escritura pero no con socios
					codigoRespuesta = REQUIERE_SOCIOS;
				}
			}

			// Objeto para la forma auxiliar para invocar al servicio del ICA
			ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
			icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
			icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.TRUE);
			//buscamos a la persona moral
			empresaMoral = this.serviciosPersonaBusiness.buscarPersonaMoralyDPyDyMCEnIMSS(patronPersonaMoral.getIdPersona());
			session.setAttribute(KEY_MORAL_AP, empresaMoral);
			icaDatosConsulta.setPersonaMoral(empresaMoral);

			try {
				icaDatosRespuesta = personaMoralServiceBusiness.identificarCambios(icaDatosConsulta);
			} catch (PersonaNoEncontradaException e) {
				e.printStackTrace();
				error = e.getMessage();
			} catch (RFCNoLocalizadoEnEntidadExternaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error = e.getMessage();
			} catch (ClienteWebserviceSatRfcException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error = e.getMessage();
			} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error = e.getMessage();
			} catch (ComparacionSinDiferenciasException e) {
				// TODO Auto-generated catch block
				log.error(e);
				icaDatosRespuesta = new ICADatosRespuesta();
				icaDatosRespuesta.setErrorFormGeneral(e.getMessage());
				Map<String, String> mensajes = new HashMap<String, String>();
				mensajes.put(ErroresModificacionPersonaEnum.COMPARACION_SIN_DIFERENCIAS.getCodigo(), e.getMessage());
				icaDatosRespuesta.setTraza(mensajes);
			} catch (DatosInsuficientesICAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				error = e.getMessage();
			}
			//si no existen errores validamos si existen diferencias
			if(error.equals("OK")) {
				// Se obtienen os tramites de cambio de datos generales en caso de
				// existir cambios
				boolean existeDiferenciasIca = solicitudPersonaBusiness.existenDiferencias(icaDatosRespuesta.getCambios());
				//si existen diferencias lo manamos por las cocas
				if(existeDiferenciasIca) {
					error = "La informaci&oacute;n del patr&oacute;n presenta diferencias entre el IMSS y las entidades externas, acuda a la subdelegaci&oacute;n correspondiente para regularizar esta informaci&oacute;n";
					codigoRespuesta = VALIDACIONES_INCORRECTAS;
				}
			} else {
				codigoRespuesta = VALIDACIONES_INCORRECTAS;
			}
		}
		//Regresa registro y se manda parametro 0 para seguir con Alta
		result.put("codigoRespuesta", codigoRespuesta);
		result.put("mensaje", error);
		result.put("personaMoral", empresaMoral);
		result.put("datosICA", icaDatosRespuesta);
		return result;
	}

    @RequestMapping(value = "/patron/valida/clasificacion", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> validaClasificacion(@RequestBody SujetoObligado sujetoTramite, HttpSession session, Locale locale) {

        Map<String, Object> result = new HashMap<String, Object>();


        //Se valida la clasificaciÃ³n seleccionada

        Moral personaMoral = (Moral)session.getAttribute(KEY_MORAL_AP);

        sujetoTramite.setMoral(personaMoral);

        try {
            ruleServiceBusiness.validaPatronExistentePorMunicipioFraccionModalidad(sujetoTramite);
        } catch (PatronExistenteMunicipioFraccionModalidadException em) {
            log.error(em);
            result.put(KEY_MENSAJE_ERROR, em.getMessage());
            return result;
        }
        try {
            ruleServiceBusiness.validarTipoAmbitoPorMunicipio(sujetoTramite.getMunicipioIMSS().getCvecMunicipioSINDO(), sujetoTramite.getModalidad().getNumModalidad(), sujetoTramite.getClasificacion().getFecEfecto());
        } catch (GestionPatronalBusinessException e1) {
            log.error(e1);
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
              //Modificacion por MM 4121544 / WO1504931 - Actualizar validación de fracción en nuevos Registros Patronales
                if(sujetoObligadoServiceBusiness.consultarMarcaRPC(sujetoTramite.getMoral().getRfc(), TipoPersonaFiscal.MORAL.getCodigo()) == 0){
					ruleServiceBusiness
							.validarFraccionesConsistentesPorPatronV2(
									sujetoTramite.getMoral().getRfc(),
									sujetoTramite.getMunicipioIMSS().getCvecMunicipioSINDO(),
									sujetoTramite.getClasificacion().getFraccion());
                }else{
                	 //clasifValidar.setSujetoObligado(sujetoTramite);
                    ruleServiceBusiness.validarFraccionesConsistentesPorPatron(sujetoTramite.getMoral().getRfc());
                    ruleServiceBusiness.validarFraccionObligatoria(clasifValidar);
                }
				ruleServiceBusiness.validarSociosRequeridos(sujetoTramite.getMoral().getIdPersona());
            }
        } catch (GestionPatronalBusinessException e1) {
            log.error(e1);
            if(e1.getCodigo().equals(801)){
                result.put(KEY_MENSAJE_ERROR, e1.getMessage());
                return result;
            }
            result.put(KEY_MENSAJE_ERROR, e1.getMessage());
            return result;
        }

        String rfc = "";
        if (sujetoTramite.getFisica() != null) {
            rfc = sujetoTramite.getFisica().getRfc();
            boolean aplicarRIF=ruleServiceBusiness.estaParametroRIFHabilitado();
            System.err.println("Parametro RIF: "+aplicarRIF);
            if(aplicarRIF){
                Fisica persona = sujetoTramite.getFisica();
                boolean indRIF = ruleServiceBusiness.personaConRegimenRIF(persona);
                System.err.println("Persona despuï¿½s de evaluar RIF: "+persona);
                sujetoTramite.setFisica(persona);//Se agregan los rï¿½gimenes
                sujetoTramite.getFisica().setIndRIF(indRIF);
                result.put("isRIF", indRIF);
            }else{//Se asegura que no aplique tipo de pago 1 para sindo cuando no se tiene configurado RIF
                sujetoTramite.getFisica().setIndRIF(false);
            }
        }
        if (sujetoTramite.getMoral() != null) {
            rfc = sujetoTramite.getMoral().getRfc();
        }
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

    public Fisica validaPersonaRegistroUsuario(Fisica fisica, HttpSession session) throws Exception {
    	Fisica fisicaValidado = null;
    	String msgError = null;

    	this.log.error("Usuario con RFC ["+ fisica.getRfc()+ "] y CURP ["+ fisica.getCurp()+ "]");
    	System.out.println("Usuario con RFC ["+ fisica.getRfc()+ "] y CURP ["+ fisica.getCurp()+ "]");

		try {

			fisicaValidado = this.consultaPersonaFisicaServiceBusinessRemote.validaPersonaRegistroUsuario(fisica);

		} catch (ErrorComparacionDatosSATException e) {
			log.error(e);
			msgError = e.getMessage();
		} catch (EsquemaSegurdiadException e) {
			log.error(e);
			msgError = "Ocurri&oacute; un error al consultar el CURP : " +fisica.getCurp() +" en el esquema de seguridad";
		} catch (UsuarioRegistradoSSOException e) {
			log.error(e);
			msgError = "Ya existe un usuario registrado con la CURP " +fisica.getCurp();
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			msgError = e.getMessage();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			log.error(e);
			msgError = e.getMessage();
		} catch (ErrorComparacionDatosRENAPOException e) {
			log.error(e);
			msgError = e.getMessage();
		} catch (DiferenciasRENAPOContraSAT e) {
			log.error(e);
			msgError = e.getMessage();
		} catch (ClienteWebserviceRenapoCurpException e) {
			log.error(e);
			msgError = e.getMessage();
		} catch (ClienteWebserviceSatRfcException e) {
			log.error(e);
			msgError = e.getMessage();
		} catch (CURPNoActualizadaRenapoException e) {
			log.error(e);
			msgError = e.getMessage();
		} catch (Exception e) {
			log.error("ocurrio por exception " ,e);
			msgError = "Ocurri&oacute; un error inesperado al recuperar los datos del CURP " +e.getCause();
		}

		if (fisicaValidado != null) {
			generarCadenaOriginalRegistroFisica(fisicaValidado, session);
			session.setAttribute(KEY_RFC_SOLICITANTE, fisicaValidado.getRfc());
			session.setAttribute(FISICA_SESSION_KEY, fisicaValidado);
		} else {
			msgError = "Ocurri&oacute; un error inesperado al recuperar los datos del CURP.";
		}

		if (msgError != null) {
			throw new Exception(msgError);
		}

		return fisicaValidado;
    }

    private void generarCadenaOriginalRegistroFisica(Persona persona, HttpSession session) {
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

		// Folio (No aplica)
		//contenidoAFirmar.append("Folio:|");

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

		if (persona instanceof Fisica) {
			// CURP
			contenidoAFirmar.append("CURP:");
			contenidoAFirmar.append(((Fisica)persona).getCurp()).append("|");
			datosEntradaFirma.setCurp(((Fisica)persona).getCurp());
		}

		// Registro Patronal(No aplica)
		//contenidoAFirmar.append("Registro Patronal:|");

		// NSS(No aplica)
		//contenidoAFirmar.append("Numero de Seguridad Social:||");
		contenidoAFirmar.append("|");

		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}

    public Fisica registrarPersonaFisica(Fisica fisica, HttpSession session, String curp, String serial) throws Exception {
    	String strMsg = null;
    	Fisica fisicaRegistrada = null;

		try {
			FirmaElectronica firmaElectronicaSession = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
			Solicitud objSolicitudSSO = this.llenaSolicitudRegistroUsuario(fisica);

			String cadenaOriginal = (String) session.getAttribute(KEY_CADENA_ORIGINAL);

			RespuestaFirmadoSimple firmadoSimple = firmaDigitalBusiness.getSelloDigital(cadenaOriginal, null, null);

			this.log.debug("Tramite: " + firmadoSimple.getTramite());

			FirmaElectronica firmaElectronica = firmaDigitalBusiness.convertirRespuestaFirmadoSimple(cadenaOriginal, firmadoSimple);

			firmaElectronica.setSerialCertificado(serial);
			firmaElectronica.setNombreCompleto(firmaElectronicaSession.getNombreCompleto());
			firmaElectronica.setCurp(curp);
			firmaElectronica.setRfc(firmaElectronicaSession.getRfc());
			firmaElectronica.setFechaElectronicaFormateada(firmaElectronicaSession.getFechaElectronicaFormateada());
			firmaElectronica.setFechaElectronica(firmaElectronicaSession.getFechaElectronica());

			objSolicitudSSO.setFirmadaDigitalmente(true);
			objSolicitudSSO.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			objSolicitudSSO.setSecuenciaDeNotaria(firmaElectronica.getReciboNotarial());
			objSolicitudSSO.setSelloDigital(firmaElectronica.getRecibo());
			objSolicitudSSO.setUrlAcuseFirma(firmaElectronica.getUrlAcuseFirma());

			this.log.error("Se creara el usuario " + curp + " con el serial directo " + serial + " y del objeto firma " + firmaElectronica.getSerialCertificado());
			fisicaRegistrada = this.solicitudPersonaBusiness.creaCuentaUsuararioSSO(objSolicitudSSO, firmaElectronica);

			this.log.error("se crea el usuario en SSO para la curp " + curp);
			strMsg = "El registro de usuario concluy&oacute; exitosamente";
		} catch (IllegalArgumentException e) {
			this.log.error("error la solicitud esta vacia" ,e);
			strMsg = e.getMessage();
			throw new Exception(strMsg);
		} catch(Exception e) {
			this.log.error("otro error no cachado al intentar generar el usuario con curp " + curp, e);
			strMsg = "Ocurri&oacute; un error y no se pudo generar el usuario";
			throw new Exception(strMsg);
		}

		return fisicaRegistrada;
    }

    private Solicitud llenaSolicitudRegistroUsuario(Fisica fisica){
		Date fechaRegistro = new Date();
		Solicitud objSolicitudUsuario = new Solicitud();

		//llenado de las propiedades del objeto tramiteFisica
		TramiteFisica tramite = new TramiteFisica();
		EstadoTramiteEnum.INICIADO.getCodigo();
		EstadoTramite objEstadoTramite = new EstadoTramite();
		TipoTramite tipoTramite = new TipoTramite();

		tipoTramite.setIdTipoTramite(TipoTramiteEnum.ALTA_USUARIOS_SSO.getCodigo());
		objEstadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());

		tramite.setEstadoTramite(objEstadoTramite);
		tramite.setFechaPresentacion(fechaRegistro);
		tramite.setFechaTramite(fechaRegistro);
		tramite.setFisica(fisica);
		tramite.setTipoTramite(tipoTramite);

		List<Tramite> lstTramiteReg = new ArrayList();
		lstTramiteReg.add(tramite);

		//llenado de la solicitud
		TipoSolicitud objTipoSol = new TipoSolicitud();
		objTipoSol.setIdTipoSolicitud(mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum.REGISTRO_USUARIOS_SSO.getValor().longValue());

		EstadoSolicitud objEstadoSol =new EstadoSolicitud();
		objEstadoSol.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getCodigo());

		objSolicitudUsuario.setEstadoSolicitud(objEstadoSol);
		objSolicitudUsuario.setFechaSolicitud(fechaRegistro);
		objSolicitudUsuario.setFirmadaDigitalmente(true);
		objSolicitudUsuario.setTipoSolicitud(objTipoSol);
		objSolicitudUsuario.setSelloDigital("");
		objSolicitudUsuario.setTramites(lstTramiteReg);

		objSolicitudUsuario.setOrigenSolicitud(new OrigenSolicitud());
		objSolicitudUsuario.getOrigenSolicitud().setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		return objSolicitudUsuario;
	}

    private void procesarEscrituraSocios(SolicitudAltaPatronalDTO solicitudAltaPatronalDTO, HttpSession session) {
    	 Moral personaMoral = (Moral)session.getAttribute(KEY_MORAL_AP);
    	 Fisica representante = (Fisica) session.getAttribute(DATOS_PERSONA_KEY);

		 TramiteSujetoObligado tramiteSujetoObligado = solicitudAltaPatronalDTO.getTramiteSujetoObligado();
		 tramiteSujetoObligado.getSujetoObligado().setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		 if(tramiteSujetoObligado.getSujetoObligado().getEscrituraConstitutiva() != null) {
			 personaMoral.setEscrituraConstitutiva(tramiteSujetoObligado.getSujetoObligado().getEscrituraConstitutiva());
			 personaMoral.setRegistroSindicato(null);
		 } else if(tramiteSujetoObligado.getSujetoObligado().getRegistroSindicato() != null) {
			 personaMoral.setRegistroSindicato(tramiteSujetoObligado.getSujetoObligado().getRegistroSindicato());
			 personaMoral.setEscrituraConstitutiva(null);
			 tramiteSujetoObligado.getSujetoObligado().setSocios(null);
		 }

		 //se setea el representante legal
		 tramiteSujetoObligado.getSujetoObligado().setRepresentantesLegales(new ArrayList<RepresentanteLegal>());
		 RepresentanteLegal repLegal = new RepresentanteLegal();
		 repLegal.setIndActAdmonDominio(BigDecimal.ONE);
		 repLegal.setPersonaFisica(representante);
		 tramiteSujetoObligado.getSujetoObligado().getRepresentantesLegales().add(repLegal);

		 //Se procesan los socios
		 List<Socio> socios = tramiteSujetoObligado.getSujetoObligado().getSocios();
		 List<Socio> sociosAux = null;
		 if(socios != null) {
			 sociosAux = new ArrayList<Socio>();
			 for(Socio socio: socios) {
				 Socio socioAux = socio;
				 if(socioAux.getPersonaFisica() != null) {
					 socioAux.setPersona(socioAux.getPersonaFisica());
				 } else {
					 socioAux.setPersona(socioAux.getPersonaMoral());
				 }
				 sociosAux.add(socioAux);
			 }

			 tramiteSujetoObligado.getSujetoObligado().setSocios(sociosAux);

		 }
		 tramiteSujetoObligado.getSujetoObligado().setMoral(personaMoral);
		 solicitudAltaPatronalDTO.setTramiteSujetoObligado(tramiteSujetoObligado);
    }

    /**
     * Metodo para crear tramite de alta de socios
     * @param sujeto
     * @return
     */
    private TramiteSocios crearTramiteSocios(SujetoObligado sujeto) {
    	TramiteSocios tramiteSocios = null;
    	if(sujeto.getSocios() != null && !sujeto.getSocios().isEmpty()) {
    		Socio socio = sujeto.getSocios().get(0);

	    	tramiteSocios = new TramiteSocios();
			tramiteSocios.setPatron(new Moral());
			tramiteSocios.getPatron().setIdPersona(socio.getIdPersonaMoralPatron());
			tramiteSocios.setListaSocios(null);

			if(socio.getPersonaFisica() != null){
				tramiteSocios.setSociosFisico(socio.getPersonaFisica());
			}else{
				tramiteSocios.setSocioMoral(socio.getPersonaMoral());
			}
			tramiteSocios.setEstadoTramite(new EstadoTramite());
			tramiteSocios.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
			tramiteSocios.getEstadoTramite().setDescripcion(EstadoTramiteEnum.INICIADO.getDescripcion());
			tramiteSocios.setTipoTramite(new TipoTramite());
			tramiteSocios.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo());
			tramiteSocios.setFechaTramite(new Date());
			tramiteSocios.setFechaPresentacion(new Date());
			tramiteSocios.setAcuseVentanilla(new AcuseVentanilla());
    	}

    	return tramiteSocios;
    }

    /**
     * Metodo para crear el tramite d escritura constitutiva o sindicato
     * @param sujetoObligado
     * @return
     */
    private TramiteMoral crearTramiteEscrituraSindicato(SujetoObligado sujetoObligado) {

    	TramiteMoral tramiteEscSin = null;
    	ICADatosRespuesta icaResp = sujetoObligado.getDatosICA();

    	if(sujetoObligado.getEscrituraConstitutiva() != null || sujetoObligado.getRegistroSindicato() != null) {
    		tramiteEscSin = new TramiteMoral();
    		tramiteEscSin.setMoral(sujetoObligado.getMoral());
    		tramiteEscSin.setEstadoTramite(new EstadoTramite());
    		tramiteEscSin.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
    		tramiteEscSin.setTipoTramite(new TipoTramite(TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES.getCodigo()));
    		tramiteEscSin.setFechaTramite(new Date());
    		if(icaResp == null) {
    			icaResp = new ICADatosRespuesta();
    		}

    		if(icaResp.getCambios() == null) {
    			icaResp.setCambios(new HashMap<String, CambioComparacionEnum>());
    		}

    		icaResp.setPersonaMoralEE(sujetoObligado.getMoral());
    		icaResp.setPersonaMoralIMSS(sujetoObligado.getMoral());
    		icaResp.getCambios().put("datosComplementarios", CambioComparacionEnum.CAMBIO);
			if(sujetoObligado.getEscrituraConstitutiva()!=null){
				icaResp.getPersonaMoralEE().setEscrituraConstitutiva(sujetoObligado.getEscrituraConstitutiva());
				icaResp.getPersonaMoralIMSS().setEscrituraConstitutiva(sujetoObligado.getEscrituraConstitutiva());
				icaResp.getPersonaMoralIMSS().setRegistroSindicato(null);
				icaResp.getPersonaMoralEE().setRegistroSindicato(null);
				icaResp.getCambios().put("actaConstitutiva", CambioComparacionEnum.CAMBIO);
				icaResp.getCambios().remove("registroSindicato");
			}else if(sujetoObligado.getRegistroSindicato()!=null){
				icaResp.getPersonaMoralIMSS().setRegistroSindicato(sujetoObligado.getRegistroSindicato());
				icaResp.getPersonaMoralEE().setRegistroSindicato(sujetoObligado.getRegistroSindicato());
				icaResp.getPersonaMoralEE().setEscrituraConstitutiva(null);
				icaResp.getPersonaMoralIMSS().setEscrituraConstitutiva(null);
				icaResp.getCambios().put("registroSindicato", CambioComparacionEnum.CAMBIO);
				icaResp.getCambios().remove("actaConstitutiva");
			}

			tramiteEscSin.setDatosICA(icaResp);
    	}

    	return tramiteEscSin;

    }



private ConfirmarSolicitudResponseDTO recuperaParametros(String idTramite){


		if(idTramite==null ||idTramite.equals("")){
			System.out.println("Sin numero de tramite");
			return  null;
		}
    	ConfirmarSolicitudResponseDTO respuesta= null;
    	try {
    		SolicitudService servicio=new SolicitudServiceLocator();
    		SolicitudServiceType port=servicio.getSolicitudService_Port();
	    	respuesta = port.confirmarSolicitudRegistroPatronal(new ConfirmarSolicitudRequestDTO(idTramite));
			System.out.println("Respuesta "+respuesta.getRfcSAS());
		}catch(RemoteException re){
			re.printStackTrace();
		}catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return respuesta;

    }

	@RequestMapping("/getFirmaAP")
	public @ResponseBody EstructuraFirmaDTO getDatosFirmaAltaPatronal(HttpSession session)  {

		Moral afectadoM = (Moral) session.getAttribute(KEY_MORAL_AP);
		Fisica datosPersonales = (Fisica) session.getAttribute(DATOS_PERSONA_KEY);

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.ALTA_SRT_PM.getCodigo());
		String tipoTramite = "ALTA PATRONAL E INSCRIPCI\u00D3N DE PERSONA MORAL EN EL SEGURO DE RIESGOS DE TRABAJO SRT";

		EstructuraFirmaDTO estructuraFirmaDTO = new EstructuraFirmaDTO();
		estructuraFirmaDTO.setIdTipoSolicitud(TipoTramiteEnum.ALTA_SRT_PM.getCodigo().toString());
		estructuraFirmaDTO.setDescripcionTipoSolicitud(tipoTramite);
		estructuraFirmaDTO.setIdTipoTramite(listTipoTramite);
		estructuraFirmaDTO.setFolioSolicitud("");
		estructuraFirmaDTO.setCurp(datosPersonales.getCurp());
		estructuraFirmaDTO.setRfc(datosPersonales.getRfc());
		estructuraFirmaDTO.setValidarRFC(true);
		estructuraFirmaDTO.setRegistroPatronal("");
		estructuraFirmaDTO.setNombreCompleto(datosPersonales.getNombreCompleto());
		estructuraFirmaDTO.setFechaElectronica(formatter.format(Calendar.getInstance().getTime()));
		estructuraFirmaDTO.setCad_original(generarCadenaOriginal(afectadoM, null,tipoTramite).replace("\"/g", "\\\""));
		estructuraFirmaDTO.setTipo_operacion("firmaCMS");
		estructuraFirmaDTO.setFirma_archivo(false);
		estructuraFirmaDTO.setMin_archivos("0");
		estructuraFirmaDTO.setMax_archivos("0");
		estructuraFirmaDTO.setMostrarCartaTerminos(false);
		estructuraFirmaDTO.setTipoAcuse("1");
		estructuraFirmaDTO.setAcuse("AP");
		estructuraFirmaDTO.getAfectado()[0] = (new RepresentanteDTO(afectadoM.getRfc(), afectadoM.getRazonSocial(), null));

		return estructuraFirmaDTO;
	};

	@RequestMapping(value = "/procesarDatosFirmaAltaPatronal", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirmaAltaPatronal(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		return null;
	}

private void notificaTramite(String idTramite,String registroPatronal,String estatus){
	try {


		SolicitudService servicio=new SolicitudServiceLocator();
		SolicitudServiceType port=servicio.getSolicitudService_Port();
    	System.out.println("Port WS Notifica "+port);
    	System.out.println("Codigo Estatus "+estatus);
    	System.out.println("IDGlobal tramite "+idTramite);
    	System.out.println("Registro Patronal "+registroPatronal);


    	NotificarConclusionResponseDTO respuesta=port.notificarConclusionRegistroPatronal(
    			new NotificarConclusionRequestDTO(estatus, idTramite, registroPatronal));

    	System.out.println("Notificacion a economia");
    	System.out.println("Mensaje "+respuesta.getMensaje());
    	System.out.println("Detalle "+respuesta.getDetalle());
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (ServiceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


	@RequestMapping(value = "/recuperaRfcSesion", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String recuperaRfcSesion(HttpSession session, HttpServletRequest request) {
		String rfcMoralSesion = (String) session.getAttribute(RFC_MORAL);
		return rfcMoralSesion;
	}



	@RequestMapping(value = "/patron/notificarEconomia", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> notificarEconomia(@RequestBody String registroPatronal,HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		System.out.println("Notificando Economia Exitoso "+registroPatronal);
		notificaTramite((String) session.getAttribute(ID_GLOBAL_TRAMITE), registroPatronal, ALTA_PATRONAL_EXITO);
		return result;

	}


	@RequestMapping(value = "/patron/notificarEconomiaError", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> notificarEconomiaError(HttpSession session, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		System.out.println("Notificando Economia notificarEconomiaError");
		notificaTramite((String) session.getAttribute(ID_GLOBAL_TRAMITE), "", ALTA_PATRONAL_ERROR);
		return result;
	}


}
