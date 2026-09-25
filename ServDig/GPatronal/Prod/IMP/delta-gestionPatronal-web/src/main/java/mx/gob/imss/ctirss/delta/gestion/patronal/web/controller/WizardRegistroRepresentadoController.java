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

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalInvalidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalYaExisteException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.CommonValidator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.EstatusPersona;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPoder;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
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
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRepresentanteLegal;
import mx.gob.imss.ctirss.delta.web.validator.PersonaRFCValidator;
import mx.gob.imss.ctirss.delta.web.validator.PersonaValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wizard/tramite/representado/registro")
public class WizardRegistroRepresentadoController extends AbstractController{

	@Autowired 
	private RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusinessRemote;
	@Autowired 
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired 
	private ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
	@Autowired 
	private PersonaMoralBusinessRemote personaMoralBusiness;
	@Autowired 
	private IndividuoServiceBusinessRemote individuoServiceBusiness;
	@Autowired 
	private PersonaBusinessRemote personaBusiness;
	@Autowired
	private SolicitudPersonaBusinessRemote solicitudPersonaBusiness;
	
	//Variables de sesion
	private static final String DATOS_PERSONA_KEY = "datosPersonales";
	private static final String DATOS_PERSONA_SESION_KEY = "datosPersonaSesionRep";
	private static final String SUJETO_OBLIGADO_KEY = "sujetoObligado";
	private static final String DESC_TIPO_SOLICITUD = "ALTA DE EMPRESA REPRESENTADA";
	private static final String KEY_MENSAJE = "mensaje";
	private static final String KEY_RETOMAR = "isRetomar";
	private static final String KEY_OFORM = "oForm";
	private static final String KEY_NEGOCIO = "negocio";
	private static final String KEY_ERROR = "error";
	private static final String KEY_RFC_SOLICITANTE = "rfcPersona";	
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_FIRMA_ELECTRONICA_REPRESENTADO = "datosFirmaElectronicaRepresentado";
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";	
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String KEY_BUSQUEDA_PERSONA = "busquedaPersona";
	private static final String KEY_ICA_DATOS_ENTRADA = "icaDatosEntrada";
	private static final String KEY_SOLICITUD_FORM = "solicitudForm";
	private static final String KEY_FOLIO_SOLICITUD = "folioSolicitud";
	private static final String KEY_ID_SOLICITUD = "idSolicitud";
	private static final String KEY_SOLICITUD = "solicitud";
	private static final String KEY_TRAMITE = "tramite";
	private static final String KEY_EXISTE_SOL_REGISTRADA = "existeSolRegistrada";
	private static final String KEY_EXISTE_SOL_PROCESO = "existeSolProceso";
	private static final String KEY_SOLICITUD_MISMO_ORIGEN = "solicitudMismoOrigen";
	private static final String KEY_SOLICITUD_CREADA = "solicitudCreada";
	private static final String KEY_USUARIO_SSO = "usuarioSSO";
	private static final String KEY_DESC_ORIGEN_SOLICITUD = "descripcionOrigenSolicitud";
//	private static final String RAZON_SOCIAL_PM_KEY = "nombrePersonaSAT";
//	private static final String TIPO_SOCIEDAD_PM_KEY = "tipoSociedadSAT";
	
	//Pantallas
	private static final String WIZARD_REGISTRO_RL_INICIO = "wizardRegistroRepresentadoLegalInit";
	private static final String WIZARD_REGISTRO_RL_CONTENIDO = "wizardRegistroRepresentadoLegalContenido";
	//Mensajes
	private static final String MSG_ERROR_SOL_PENDIENTES = "Ocurri� un error al consultar las solicitudes pendientes.";
	private static final String MSG_ERROR_LOCALIZAR_PERSONA = "Ocurri� un error al localizar informaci�n de la persona.";
	private static final String MSG_ERROR_RETOMAR_SOLICITUD = "Error al retomar la solicitud.";	
	private static final String MSG_ERROR_RFC_REPRESENTANTE = "El RFC del representante en la firma no corresponde";
	private static final String MSG_ERROR_REPRESENTANTE_INVALIDO = "No es posible registrar el RFC como representante del mismo.";
	private static final String MSG_ERROR_CANCELAR_SOLICITUD = "Hubo un error al cancelar la solicitud: ";
	private static final String MSG_OBSERVACION_CANCELACION ="Solicitud cancelada por el usuario ";
	private static final String MSG_CANCELAR_SOLICITUD = "La solicitud fue cancelada correctamente";	
	private static final String MSG_FINALIZAR_SOLICITUD = "Su solicitud ha finalizado correctamente";
	private static final String MSG_ACTUALIZADA_SOLICITUD = "Su solicitud fue actualizada correctamente";
	
	/**
	 * Metodo para iniciar el registro de representado legal
	 * @param model
	 * @param request
	 * @param session
	 * @param idPersona
	 * @param curp
	 * @param rfc
	 * @return
	 */
	@RequestMapping("/{idPersona}/{curp}/{rfc}")
	public String initWizardRegistroRepresentanteLegal(Model model, HttpServletRequest request, 
			HttpSession session, @PathVariable Long idPersona, @PathVariable String curp, 
			@PathVariable String rfc) {
			
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		String vista = WIZARD_REGISTRO_RL_INICIO;		
		// Objeto para la forma auxiliar para invocar al servicio del ICA
		ICADatosConsulta datosEntrada = new ICADatosConsulta();		
		Fisica fisica = new Fisica();
		fisica.setIdPersona(idPersona);
		fisica.setCveFisica(idPersona);
		fisica.setCurp(curp);
		fisica.setRfc(rfc);			
		session.setAttribute(DATOS_PERSONA_KEY , fisica);
		model.addAttribute(KEY_ICA_DATOS_ENTRADA, datosEntrada);		
		//Se valida si existen solicitudes en proceso pendientes
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		boolean solicitudMismoOrigen = false;
		boolean solicitudCreada = false;
		Solicitud solicitudActiva = null;
		
		try {
			//Para INTERNET se obtiene la persona autenticada en OpenAM.
			//Para Ventanilla, se toma la persona localizada, tal cual.
			Long identificadorPersona = 0L;
			UsuarioSSO sso = this.procesarUsuarioSSO(request);
			PortalController portal = new PortalController();
			portal.getUsuarioSesion(sso);
			if(new CommonValidator().esSolicitudInternet(idOrigen)){
				identificadorPersona = sso.getIdPersona().longValue();
			}else{
				identificadorPersona = idPersona;
			}			
			Fisica personaSesion = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(identificadorPersona);
			session.setAttribute(DATOS_PERSONA_SESION_KEY, personaSesion);
			session.setAttribute(KEY_USUARIO_SSO, sso);
			
			solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudRegistrada(idPersona, 
				TipoPersonaEnum.FISICA.getId(), TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, 
				TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL);

			if (solicitudActiva != null
					&& solicitudActiva.getSolicitudId() != null) {
				existeSolRegistrada = true;
				solicitudMismoOrigen = new CommonValidator().validarSolicitudMismoOrigen(solicitudActiva, idOrigen);
				model.addAttribute(KEY_ID_SOLICITUD, solicitudActiva.getSolicitudId());
				model.addAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
				session.setAttribute(KEY_FOLIO_SOLICITUD, solicitudActiva.getNoFolioSolicitud());
				model.addAttribute(KEY_DESC_ORIGEN_SOLICITUD, solicitudActiva.getOrigenSolicitud().getDescripcion());
				solicitudCreada = true;				
			} else {
				solicitudActiva = this.solicitudPersonaBusiness.obtenerSolicitudEnProceso(idPersona,
					TipoPersonaEnum.FISICA.getId(), TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES, 
					TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL);
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
			model.addAttribute(KEY_ERROR, MSG_ERROR_SOL_PENDIENTES);
			log.error(e);
		} catch (PersonaFisicaNoEncontradaException ee) {
			model.addAttribute(KEY_ERROR, MSG_ERROR_LOCALIZAR_PERSONA);
			log.error(ee);
		}
		
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		listTipoTramite.add(TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL.getCodigo());
		model.addAttribute(KEY_SOLICITUD_FORM, solicitudActiva);
		request.setAttribute(KEY_EXISTE_SOL_REGISTRADA, existeSolRegistrada);
		request.setAttribute(KEY_EXISTE_SOL_PROCESO, existeSolProceso);
		request.setAttribute(KEY_SOLICITUD_MISMO_ORIGEN, solicitudMismoOrigen);
		request.setAttribute(KEY_SOLICITUD_CREADA, solicitudCreada);		
		session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.ACTUALIZACION_DATOS_PATRONALES.getValor());
		session.setAttribute(KEY_DESC_TIPO_SOLICITUD, DESC_TIPO_SOLICITUD);		
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
		
		return vista;
	}
	
	/**
	 * Metodo para la busqueda de una persona fisica o moral registrado como patron
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/iniciarTramite")
	public String busquedaPersona(Model model, HttpServletRequest request, HttpSession session) {
		Persona persona = new Persona();
		persona.setRepresentantesLegales(new ArrayList<RepresentanteLegal>());
		persona.getRepresentantesLegales().add(new RepresentanteLegal());		
		model.addAttribute(KEY_BUSQUEDA_PERSONA, persona);
		model.addAttribute(KEY_SOLICITUD_CREADA, false);		
		return WIZARD_REGISTRO_RL_CONTENIDO;
	}
	
	/**
	 * Metodo para retomar una solicitud
	 * @param model
	 * @param solicitud
	 * @param session
	 * @return
	 */
	@RequestMapping("/retomar/solicitud")
	public String retomarSolicitud(Model model,@ModelAttribute(value=KEY_SOLICITUD_FORM) Solicitud solicitud, HttpSession session) {
		model.addAttribute(KEY_SOLICITUD_CREADA, true);
		model.addAttribute(KEY_RETOMAR, true);
		Fisica fisica = (Fisica) session.getAttribute(DATOS_PERSONA_KEY);		
		try {
			Fisica	personaRecuperada = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(fisica.getIdPersona());
			//Se consulta la solicitud
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			Fisica personaSesion=null;
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				// Datos del acuse
				personaSesion=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				obtenerDatosAcuse(solicitud, personaSesion, session);			
			}			
			session.setAttribute(KEY_RFC_SOLICITANTE, personaRecuperada.getRfc());			
			//Se verifica que la solicitud contenga algun tramite
			if(solicitud.getTramites().isEmpty()) {
				solicitud = new Solicitud();
				solicitud.setErrorFormGeneral("La solicitud no cuenta con un tramite de registro de representado legal");
				model.addAttribute(KEY_SOLICITUD, solicitud);
			} else {
				TramiteRepresentanteLegal tramiteRP = (TramiteRepresentanteLegal) solicitud.getTramites().get(0);
				session.setAttribute(SUJETO_OBLIGADO_KEY, tramiteRP.getSujetoObligado());
				if(tramiteRP!=null && tramiteRP.getFisica()!=null  
					&& tramiteRP.getFisica().getTipoPoder()!=null){
					model.addAttribute("fisica", tramiteRP.getFisica());
				}
				if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
					session.setAttribute(KEY_FIRMA_ELECTRONICA_REPRESENTADO, tramiteRP.getFirmaElectronica());
					generarCadenaOriginal(solicitud, personaSesion, session);
				}
			}		
			model.addAttribute(KEY_SOLICITUD, solicitud);
			model.addAttribute(KEY_TRAMITE, solicitud.getTramites() != null ? solicitud.getTramites().get(0) : new Tramite());
		} catch(AbstractException e) {
			e.printStackTrace();
			solicitud = new Solicitud();
			solicitud.setErrorFormGeneral(MSG_ERROR_RETOMAR_SOLICITUD);
		}		
		return WIZARD_REGISTRO_RL_CONTENIDO;
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
	@RequestMapping("/crear/solicitud")
	public String validarPersona(Model model,@ModelAttribute(value=KEY_BUSQUEDA_PERSONA) Persona busquedaPersona, 
			BindingResult result,HttpServletRequest request, HttpSession session) {		
		//Se crean los objetos necesarios
		Solicitud solicitud = new Solicitud();
		Fisica datosPersonales = (Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
		SujetoObligado sujetoEncontrado = null;
		String vista = WIZARD_REGISTRO_RL_CONTENIDO;
		model.addAttribute(KEY_SOLICITUD_CREADA, true);
		model.addAttribute(KEY_RETOMAR, false);		
		//Se verifica que el rfc del Representado y Representante no sea el mismo.
		if(busquedaPersona.getRfc().equals(datosPersonales.getRfc())) {
			solicitud.setErrorFormGeneral(MSG_ERROR_REPRESENTANTE_INVALIDO);
			model.addAttribute(KEY_SOLICITUD, solicitud);
			return vista;
		}
		//Se verifica que el RFC del representante correponda con el RFC que viene en la firma
		String rfc = busquedaPersona.getRepresentantesLegales().get(0).getPersonaFisica().getRfc();
		if(rfc != null && !rfc.isEmpty() && !rfc.equals(datosPersonales.getRfc())) {
			solicitud.setErrorFormGeneral(MSG_ERROR_RFC_REPRESENTANTE);
			model.addAttribute(KEY_SOLICITUD, solicitud);
			return vista;
		}	
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
				personaEncontrada = individuoServiceBusiness.consultarPersonaMoralIMSSPorRFC_AP(busquedaPersona);
				personaEncontrada.setTipoPersona(new TipoPersona());
				personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				sujetoEncontrado = new SujetoObligado();
				sujetoEncontrado.setMoral((Moral)personaEncontrada);
				sujetoEncontrado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
			}
		} catch (PersonasNoLocalizadasException e1) {			
			estatusPersonaARepresentar = EstatusPersona.Inexistente;
			log.error("El RFC no se encuentra registrado dn BDTU, se buscar� en el SAT");
			//TODO
			try{
				if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
					personaEncontrada = personaBusiness.buscarPersonaFisicaPorRfcEnSat(busquedaPersona.getRfc());
					personaEncontrada.setTipoPersona(new TipoPersona());
					personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				}else if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
					personaEncontrada = personaBusiness.buscarPersonaMoralPorRfcEnSat(busquedaPersona.getRfc());
					personaEncontrada.setTipoPersona(new TipoPersona());
					personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				}
			}catch(ClienteWebserviceSatRfcException cwsException){
				cwsException.printStackTrace();
				solicitud.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, " +
					"por favor verifique la informaci�n y vuelva a intentarlo");
				return vista;
			}
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			return vista;
		} catch (ClienteWebserviceSatRfcException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			return vista;
		} catch (ErrorComparacionDatosRENAPOException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			return vista;
		} catch (ErrorComparacionDatosSATException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			return vista;
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			return vista;
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			return vista;
		} catch (DiferenciasRENAPOContraSAT e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			return vista;
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			return vista;
		} catch (PersonaSinCalificacionesException e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral(e.getMessage());
			return vista;
		}
		//SE crea un objeto representante legal para saber si ya existe la relacion
		RepresentanteLegal representante = new RepresentanteLegal();
		representante.setCveIdPersona(busquedaPersona.getIdPersona());//Representado
		representante.setPersonaFisica(datosPersonales);//Representante Legal
		representante.setTipoPersonaRepresentada(new TipoPersona());
		representante.getTipoPersonaRepresentada().setIdTipoPersona(busquedaPersona.getTipoPersona().getIdTipoPersona());
		representante.setAccion(TipoAccionAfectacionEnum.AGREGAR);
		representante.setIndActAdmonDominio(BigDecimal.ONE);//Por defecto se agregan actos de administraci�n y dominio
		//Verificamos que no exista la relacion como epresentante
		try {
			if(!estatusPersonaARepresentar.equals(EstatusPersona.Inexistente) )
				representanteLegalServiceBusinessRemote.validaExisteRepresentanteLegal(representante);
		} catch (RepresentanteLegalInvalidoException e) {
			solicitud.setErrorFormGeneral("Ocurrio un error inesperado");
			model.addAttribute(KEY_SOLICITUD, solicitud);
			return vista;
		} catch (RepresentanteLegalYaExisteException e) {
			solicitud.setErrorFormGeneral("Ya existe la relacion como representante legal");
			model.addAttribute(KEY_SOLICITUD, solicitud);
			return vista;
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
					representadaMoral = personaMoralBusiness.getPersonaMoral_AP(personaEncontrada.getIdPersona());
				else
					representadaMoral = (Moral)personaEncontrada;//Como no  existe en bdtu se manda la info del SAT
				
				personaAcuse = representadaMoral; 
			}
			Fisica personaRepresentante = serviciosPersonaBusiness.buscarPersonaFisicayDPyDyMCEnIMSS(datosPersonales.getIdPersona());
			solicitud = this.crearSolicitud(personaEncontrada, datosPersonales, session, request);
			
			if(new CommonValidator().esSolicitudInternet(solicitud.getOrigenSolicitud().getIdTipoSolicitud())){
				generarCadenaOriginal(solicitud, datosPersonales, session);
				Fisica personaSesion=(Fisica) session.getAttribute(DATOS_PERSONA_SESION_KEY);
				obtenerDatosAcuse(solicitud, personaSesion, session);
			}
			
			session.setAttribute(SUJETO_OBLIGADO_KEY, sujetoEncontrado);
			model.addAttribute(KEY_SOLICITUD, solicitud);
			model.addAttribute(KEY_TRAMITE, solicitud.getTramites().get(0));
			session.setAttribute(KEY_RFC_SOLICITANTE, personaRepresentante.getRfc());
		} catch (Exception e) {
			e.printStackTrace();
			solicitud.setErrorFormGeneral("No fue posible crear la solicitud");
			model.addAttribute(KEY_SOLICITUD, solicitud);
		}		
		return vista;
	}
	
	/**
	 * Metodo para cancelar la solicitud
	 * @param solicitud
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancelar/solicitud/{idSolicitud}", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitudActualizacionDatos(
			@PathVariable Long idSolicitud, HttpServletResponse response, 
			HttpServletRequest request, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();		
		try {
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(idSolicitud);
			solicitud.setEstadoSolicitud(new EstadoSolicitud());
			solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getCodigo());
			solicitud.setSolicitante(new CommonValidator().getUsuarioSession(session));
			solicitud.setObservacion(MSG_OBSERVACION_CANCELACION + ((solicitud.getSolicitante() != null 
				&& solicitud.getSolicitante().getUsuario() != null) ? solicitud.getSolicitante().getUsuario() : ""));
			solicitud = solicitudBusinessRemote.actualizarEstados(solicitud);
			result.put(KEY_MENSAJE, MSG_CANCELAR_SOLICITUD);
			result.put(KEY_SOLICITUD, solicitud);			
		} catch (AbstractException e) {
			this.log.error(e);
			result.put(KEY_MENSAJE, MSG_ERROR_CANCELAR_SOLICITUD + e.getMessage());
		}	
		return result;
	}
	
	/**
	 * Metodo para finalizar la solicitud
	 * @param solicitud
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/finalizar/solicitud/{idTipoPoder}/{idSolicitud}", method = RequestMethod.POST)
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
				firmaElectronica = (FirmaElectronica)session.getAttribute(KEY_FIRMA_ELECTRONICA);
				FirmaElectronica firmaElectronicaPatron = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA_REPRESENTADO);
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
	
	private Solicitud actualizarTipoPoderRepresentante(Solicitud solicitud, Integer idTipoPoder){
		TramiteRepresentanteLegal tramiteRL =(TramiteRepresentanteLegal)solicitud.getTramites().get(0);
		tramiteRL.getFisica().setTipoPoder(new TipoPoder());
		tramiteRL.getFisica().getTipoPoder().setIdTipoPoder(idTipoPoder);
		solicitud.getTramites().set(0,tramiteRL);
		return solicitud;
	}
	
	/**
	 * Metodo para limpiar la sesion
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody Solicitud limpiarICA(final HttpSession session) {				
		session.removeAttribute(DATOS_PERSONA_SESION_KEY);
		session.removeAttribute(DATOS_PERSONA_KEY);
		session.removeAttribute(SUJETO_OBLIGADO_KEY);
		session.removeAttribute(KEY_TIPO_SOLICITUD);
		session.removeAttribute(KEY_DESC_TIPO_SOLICITUD);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA);
		session.removeAttribute(KEY_FIRMA_ELECTRONICA_REPRESENTADO);
		session.removeAttribute(KEY_RFC_SOLICITANTE);
		session.removeAttribute(KEY_CADENA_ORIGINAL);
		session.removeAttribute(KEY_TIPO_TRAMITE);		
		session.removeAttribute(KEY_FOLIO_SOLICITUD);
		session.removeAttribute(KEY_USUARIO_SSO);		
		return null;
	}
	
	/**
     * Metodo que valida el formulario de captura de persona fisica (combos, CURP y RFC unicamente)
     * @param oForm
     * @param response
     * @return
     */
    @RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody Fisica oForm, 
    		final HttpServletResponse response, final HttpSession session) {
        log.trace("entramos a RegistroPersonaFisicaCapturaValidadorController para validar el objeto de formulario --> " 
        	+ ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));        
        final Map<String, Object> result = new HashMap<String, Object>();
        final Errors errors = new BindException(oForm, "model");
        new PersonaValidator().validate(oForm, errors);
        if(!errors.hasErrors()){
        	new PersonaRFCValidator().validate(oForm, errors);
        }        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put(KEY_OFORM, oForm);
        Persona resultado;
        try {
            resultado = this.getErroresNegocio(session, oForm);
        } catch (Exception e) {
        	log.debug("Ocurrio un error al registrar el RFC: " + oForm.getRfc() + " [ " +e.getMessage() + " ]");
        	e.printStackTrace();
            resultado = new Persona();
            resultado.setErrorFormGeneral("Ocurrio un error inesperado");
        }
        result.put(KEY_NEGOCIO, resultado);
        return result;

    }
	
	/**
	 * Metodo para crear la solicitud con su tramite de representante legal
	 * @param sujetoObligado
	 * @param fisica
	 * @return
	 * @throws SolicitudNoValidaException
	 */
	private Solicitud crearSolicitud(Persona personaRepresentada, Fisica fisica, HttpSession session,
			HttpServletRequest request) throws SolicitudNoValidaException {
		
		Long idOrigen =  new CommonValidator().getOrigenContext(request);
		OrigenSolicitudEnum origenSolicitud = OrigenSolicitudEnum.getById(idOrigen);
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
		FirmaElectronica firmaRepresentado = (FirmaElectronica) session.getAttribute(KEY_FIRMA_ELECTRONICA_REPRESENTADO);
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
		tramiteRP.setFirmaElectronica(firmaRepresentado);
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

	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirma(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
		log.info("Se almacenan los datos de la firma digital de forma temporal " + firmaElectronica);
		return null;
	}
	
	@RequestMapping(value = "/procesarDatosFirmaRepresentado", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> almacenarTemporalmenteDatosFirmaRepresentado(@RequestBody FirmaElectronica firmaElectronica,
			HttpServletResponse response, HttpSession session) {
		session.setAttribute(KEY_FIRMA_ELECTRONICA_REPRESENTADO, firmaElectronica);
		log.info("Se almacenan los datos de la firma digital del representado de forma temporal " + firmaElectronica);
		return null;
	}
	
	private Persona getErroresNegocio(HttpSession session, Fisica busquedaPersona) {
		EstatusPersona estatusPersonaARepresentar = EstatusPersona.Existe_en_bdtu;
		Fisica datosPersonales = (Fisica) session.getAttribute(DATOS_PERSONA_KEY);
		Persona resultado = new Persona();
		Persona personaEncontrada = null;
		//Se verifica que el rfc de la empresa a representar no coincida con el rfc que es el representante legal
		if(busquedaPersona.getRfc().equals(datosPersonales.getRfc())) {
			resultado.setErrorFormGeneral("No es posible registrar el RFC como representante del mismo");
			return resultado;
		}		
		
		try {			
			if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
				personaEncontrada = individuoServiceBusiness.consultarPersonaFisicaIMSSPorRFC(busquedaPersona);
				personaEncontrada.setTipoPersona(new TipoPersona());
				personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			}else if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
				personaEncontrada = individuoServiceBusiness.consultarPersonaMoralIMSSPorRFC_AP(busquedaPersona);
				personaEncontrada.setTipoPersona(new TipoPersona());
				personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			}
		} catch (PersonasNoLocalizadasException e1) {
			estatusPersonaARepresentar = EstatusPersona.Inexistente;
			log.error("El RFC no se encuentra registrado dn BDTU, se buscar� en el SAT");
			try{
				if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
					personaEncontrada = personaBusiness.buscarPersonaFisicaPorRfcEnSat(busquedaPersona.getRfc());
					if(personaEncontrada==null){
						resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, " +
							"por favor verifique la informaci�n y vuelva a intentarlo");
						return resultado;
					}
					personaEncontrada.setTipoPersona(new TipoPersona());
					personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				}else if(busquedaPersona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
					personaEncontrada = personaBusiness.buscarPersonaMoralPorRfcEnSat(busquedaPersona.getRfc());
					if(personaEncontrada==null){
						resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, " +
							"por favor verifique la informaci�n y vuelva a intentarlo");
						return resultado;
					}
					personaEncontrada.setTipoPersona(new TipoPersona());
					personaEncontrada.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				}
			}catch(ClienteWebserviceSatRfcException cwsException){
				cwsException.printStackTrace();
				resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS ni ante el SAT, " +
					"por favor verifique la informaci�n y vuelva a intentarlo");
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
		
		//Verificamos que se haya encontrado algun patron sujeto obligado en caso contrario se muestra mensaje de error
		if(personaEncontrada != null) {
				//SE crea un objeto representante legal para saber si ya existe la relacion
				RepresentanteLegal representante = new RepresentanteLegal();
				representante.setCveIdPersona(personaEncontrada.getIdPersona());
				representante.setPersonaFisica(datosPersonales);
				representante.setTipoPersonaRepresentada(personaEncontrada.getTipoPersona());
				representante.setAccion(TipoAccionAfectacionEnum.AGREGAR);
				representante.setIndActAdmonDominio(BigDecimal.ONE);//Por defecto se agregan actos de administraci�n y dominio
				//Verificamos que no exista la relacion como epresentante
				try {
					if(!estatusPersonaARepresentar.equals(EstatusPersona.Inexistente))//Si la persona no existe la validaci�n no se aplica
						representanteLegalServiceBusinessRemote.validaExisteRepresentanteLegal(representante);
				} catch (RepresentanteLegalInvalidoException e) {
					resultado.setErrorFormGeneral("Ocurrio un error inesperado");
					return resultado;
				} catch (RepresentanteLegalYaExisteException e) {
					resultado.setErrorFormGeneral("Ya existe la relacion como representante legal");
					return resultado;
				}				
				if(personaEncontrada.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)) {
					Fisica perFisica = new Fisica();
					Fisica fisicaEncontrada = (Fisica)personaEncontrada;
					perFisica.setCurp(fisicaEncontrada.getCurp());					
					perFisica.setRfc(fisicaEncontrada.getRfc());
					perFisica.setNombre(fisicaEncontrada.getNombre().trim() + " " +
							(fisicaEncontrada.getPrimerApellido() != null ? fisicaEncontrada.getPrimerApellido() : "")+ " " + 
							(fisicaEncontrada.getSegundoApellido() != null ? fisicaEncontrada.getSegundoApellido() : "") );
					
					return perFisica;
				} else {
					Moral perMoral = new Moral();
					Moral moralEncontrada = (Moral)personaEncontrada;					
					//perMoral.setRazonSocial(moralEncontrada.getRazonSocial().replace("\"", "\\\""));
					//Modificacion para que tome la razon social del SAT en caso de que venga vacia INC98049					
					if(moralEncontrada.getTipoSociedad() != null) {
						perMoral.setTipoSociedad(moralEncontrada.getTipoSociedad());
					}
					if(moralEncontrada.getRazonSocial() != null) {
						perMoral.setRazonSocial(moralEncontrada.getRazonSocial().replace("\"", "\\\""));
					}else{
						log.debug("::: La razon social esta vacia se buscara en SAT, RFC: " + busquedaPersona.getRfc());
						Moral personaEncontradaSat = null;
						Moral moralEncontradaSat = null;	
						try {
							personaEncontradaSat = personaBusiness.buscarPersonaMoralPorRfcEnSat(busquedaPersona.getRfc());
						} catch (ClienteWebserviceSatRfcException e) {
							e.printStackTrace();
							resultado.setErrorFormGeneral("Error al obtener el RFC ante el SAT, " +
								"por favor verifique la información y vuelva a intentarlo");
							return resultado;
						}
						moralEncontradaSat = (Moral)personaEncontradaSat;	
						//actualiza en la tabla de personas morales la razon social y tipo de sociedad obtenido del SAT
						personaMoralBusiness.actualizaRazonSocialTipoSociedad(personaEncontrada.getIdPersona(),
								moralEncontradaSat.getRazonSocial(), moralEncontradaSat.getTipoSociedad());						
						log.debug("::: Guardamos valores obtenidos del SAT, moralEncontradaSat.getRazonSocial(): " + moralEncontradaSat.getRazonSocial());
						perMoral.setRazonSocial(moralEncontradaSat.getRazonSocial().replace("\"", "\\\""));
						perMoral.setTipoSociedad(moralEncontradaSat.getTipoSociedad());
					}					
					
					perMoral.setRfc(moralEncontrada.getRfc());					
					return perMoral;
				}
		}
		resultado.setErrorFormGeneral("El RFC no se encuentra registrado ante el IMSS");
		return resultado;
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
		if(solicitud.getNoFolioSolicitud() != null) {			
			contenidoAFirmar.append("Folio:");
			contenidoAFirmar.append(solicitud.getNoFolioSolicitud() != null 
				? solicitud.getNoFolioSolicitud() : "").append("|");
		}
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
		contenidoAFirmar.append(sbnombre.toString().toUpperCase()).append("|");
		datosEntradaFirma.setNombreCompleto(sbnombre.toString());
		// CURP
		if (persona instanceof Fisica) {			
			Fisica pFisica = (Fisica)persona;
			if( !StringUtils.isEmpty(pFisica.getCurp()) ){
				contenidoAFirmar.append("CURP:");
				contenidoAFirmar.append(pFisica.getCurp()).append("|");
			}
			datosEntradaFirma.setCurp(pFisica.getCurp());
		}
		contenidoAFirmar.append("|");
		this.log.debug("Contenido a firmar -> " + contenidoAFirmar.toString());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
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
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}
	
}
