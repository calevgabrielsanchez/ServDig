package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.RegistroAseguradoDatosBasicosValidator;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.Empleado;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.enums.EstadoRegistroSIMEEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSerieEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.ModuloOrigenAsignacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.nss.TipoSerie;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.web.validator.TramiteAseguradoDatosComplementariosValidator;
import mx.gob.imss.ctirss.delta.web.validator.TramiteAseguradoValidator;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lucio Duran Silva 
 * @client Instituto Mexicano del Seguro Social
 */
@Controller
@RequestMapping(value = "/tramite")
public class TramiteNssVentanillaController extends AbstractController {
	
	private static final String KEY_SOLICITUD = "solicitudNssInterno_";
	private static final String KEY_DATOS_PERSONA = "datosPersona_";
		
	private static final String ESTATUS_RENAPO_INVALIDO = "Invalido";
	
	private static final String EXISTE_SOL_REGISTRADA = "SOL_REGISTRADA";
	private static final String EXISTE_SOL_PROCESO = "SOL_PROCESO";
	private static final String FOLIO_PENDIENTE = "FOLIO_PENDIENTE";
	private static final String EDO_SOLICITUD = "EDO_SOLICITUD";
	
	private static final String MOTIVO_CANCELACION_SOLICITUD = "MOTIVO_CANCELACION_SOLICITUD";
	
	private static final String HASH_CODE_DATOS_BASICOS = "hashDatosPersona";
	private static final String SOL_CHECK_STATUS = "SOL_AUX";
	
	private static final String FOLIO_SOLIC_REQ = "folioSolicitud";
	private static final String ID_SOLIC_REQ = "idSolicitud";
	private static final String MSG_SOLIC_PROCESO_KEY = "MSG_SOLIC_PROCESO";
	private static final String MSG_SOLIC_PROCESO = "Se ha redireccionado a esta página debido a que ya se cuenta con la siguente solicitud para la persona que se está buscando";
	
	
	@Autowired
	private ServiciosPersonaBusinessRemote serviciosPersonaBusinessRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private SerieServiceBusinessRemote serieServiceBusiness;
	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	@Autowired
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	
	
	
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	@Autowired
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
	
	@Autowired
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
	@Autowired
	private  AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;
	
	
	
	


	/* Paso 1 */
	@RequestMapping(value = "/iniciar", method = RequestMethod.GET)
	public String iniciar(Model model, HttpSession session) {
		
		model.addAttribute("fisica", new Fisica());
		
		// Atributos del proceso SIME
		session.removeAttribute(ProcesoSIMEController.LISTA_EXTRANJEROS_PROCESADOS_SESSION_KEY);
		session.removeAttribute(ProcesoSIMEController.LISTA_EXTRANJEROS_SESSION_KEY);
		session.removeAttribute(ProcesoSIMEController.EXTRANJERO_SESSION_KEY);
		session.removeAttribute(ProcesoSIMEController.PROCESO_SIME_SESSION_KEY);
		
		return "consultapor.datosbasicos";
	}
	
	/*
	 * Paso 1 alterno, este paso es para el proceso SIME (dispositivo magnético
	 * - XML). Ya se recibe a una persona fisica
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/disMag/iniciar", method = RequestMethod.POST)
	public String iniciarFromSIME(@ModelAttribute Fisica fisica,
			BindingResult result, Model model,
			@RequestParam("llaveRegistro") Integer llaveRegistro,
			HttpSession session) {
		
		Map<Integer, Empleado> extranjerosSIME = (Map<Integer, Empleado>) session
				.getAttribute(ProcesoSIMEController.LISTA_EXTRANJEROS_SESSION_KEY);
		
		Empleado empleado = extranjerosSIME.get(llaveRegistro);
		session.setAttribute(ProcesoSIMEController.EXTRANJERO_SESSION_KEY, empleado);
		
		model.addAttribute("fisica", fisica);

		session.setAttribute(ProcesoSIMEController.PROCESO_SIME_SESSION_KEY, true);
		
		return "consultapor.datosbasicos";
	}

	/* Del paso 1 hacia el paso 2 */
	@RequestMapping(value = "/consultaDatosBasicos", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String consultaDatosBasicos(@ModelAttribute Fisica fisica,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {
		
		this.log.info("Consultando persona para NSS -> " + fisica);
						
		String vista = null;
		int hashDatosPersona = 0;
		
		new RegistroAseguradoDatosBasicosValidator().validate(fisica, result);
		if (result.hasErrors()) {
			this.log.warn("Errores de captura");
			vista = "consultapor.datosbasicos";
		} else {
			/*
			 * Obtenemos los datos del usuario en sesion.
			 */
			Usuario usuario = (Usuario) this.getUsuarioEnSesion(session);
			this.log.debug("Usuario : " + usuario);
			PerfilUsuario pu = usuario.getPerfilUsuario();
			
			Fisica pfToSearch = nullearCampos(fisica);
			
			String curpBuscado = null;
			if (StringUtils.isNotBlank(pfToSearch.getCurp())) {
				curpBuscado = pfToSearch.getCurp();
			}

			try {
				List<Fisica> personasFisicas = serviceBusiness
						.localizarPersonaFisica(pfToSearch);
				
				// Si se encontro mas de una persona.
				if (personasFisicas != null && !personasFisicas.isEmpty()) {
					this.log.info("Se encontraron " + personasFisicas.size()
							+ " coincidencias en la busqueda de personas para NSS ventanilla");

					/*
					 * Se agrega el curp buscado originalmente a cada una de
					 * las personas para poder utilizarlo posteriormente
					 */
					for (Fisica fisicaAux : personasFisicas) {
						fisicaAux.setCurpRenapo(curpBuscado);
					}
					
					// Se recupera la primera persona fisica.
					Fisica personaFisica = personasFisicas.get(0);

					if (mostrarListaPersonasEncontradas(personasFisicas)) {
						if (personaFisica.getEstatusRenapo() != null
								&& personaFisica.getEstatusRenapo().equals(ESTATUS_RENAPO_INVALIDO)) {
							personaFisica.setEstatusRenapo(null);
						}
												
						// Lista de personas
						model.addAttribute("personaFisicaLst", personasFisicas);
						// Mensaje con la lista de personas
						model.addAttribute("mensaje", "Se encontraron " + personasFisicas.size()
										+ " registros que coinciden con la informaci\u00F3n proporcionada. ");
						model.addAttribute("fisica", fisica);				

						vista = "consultapor.datosbasicos";
					} else {
						/*
						 * Una vez que se encuentra a la persona candidata para asignarle NSS,
						 * se valida que no tenga solicitudes pendientes
						 */
						log.debug("Persona fisica encontrada " + personaFisica);
						hashDatosPersona = personaFisica.hashCodeDatosBasicos();
						
						Map<String, Object> respuesta = existeSolicitudPendiente(personaFisica);
						boolean existeSolRegistrada = (Boolean) respuesta.get(EXISTE_SOL_REGISTRADA);
						boolean existeSolProceso = (Boolean) respuesta.get(EXISTE_SOL_PROCESO);
						
						if (existeSolRegistrada || existeSolProceso) {
							String folioPendiente = respuesta.get(FOLIO_PENDIENTE).toString();
							Object[] errorArgs = {folioPendiente};
							
							if (existeSolRegistrada) {
								result.rejectValue("errorFormGeneral",
										"msg.error.sol.pendiente.registrada",
										errorArgs, "");
							} else if (existeSolProceso) {
								result.rejectValue("errorFormGeneral",
										"msg.error.sol.pendiente.en.proceso",
										errorArgs, "");
							}
							
							return "consultapor.datosbasicos";
						}
						
						/*
						 * Ahora se checa que no este en sesión otra solicitud
						 * para la misma persona
						 */
						Solicitud solicitudInSessionCheck = (Solicitud) session
								.getAttribute(KEY_SOLICITUD + hashDatosPersona);
						
						if (solicitudInSessionCheck != null 
								&& solicitudInSessionCheck.getSolicitudId() != null) {
							Map<String, Object> res = this.validarSolicitudEnSesion(solicitudInSessionCheck);
							String edoSolicitud = (String) res.get(EDO_SOLICITUD);
							
							request.setAttribute(EDO_SOLICITUD, edoSolicitud);
							
							if (edoSolicitud.equals("CANCELADA")) {
								String motivoCancelacion = this.obtenerMotivoCancelacion(solicitudInSessionCheck);
								if (StringUtils.isNotBlank(motivoCancelacion)) {
									request.setAttribute(MOTIVO_CANCELACION_SOLICITUD, motivoCancelacion);
								}
							}
							
							request.setAttribute(FOLIO_SOLIC_REQ, solicitudInSessionCheck.getNoFolioSolicitud());
							request.setAttribute(ID_SOLIC_REQ, solicitudInSessionCheck.getSolicitudIdHashed());
							request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
							request.setAttribute(MSG_SOLIC_PROCESO_KEY, MSG_SOLIC_PROCESO);
							
							vista = "concluir.solicitud";
						} else  {
							
							if (solicitudInSessionCheck != null) {
								session.removeAttribute(KEY_SOLICITUD + hashDatosPersona);
							}
							
							agregarPersonaToTramite(personaFisica, model, false, session);						
							agregarListTipoSerieToModel(usuario, pu, model, session);
							
							session.setAttribute(KEY_DATOS_PERSONA + hashDatosPersona, personaFisica);
							request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
							
							vista = "captura.libre";
						}
					}
				} else {
					/*
					 * Si la persona no fue encontrada por ninguno de los
					 * parametros ni el IMSS o RENAPO, entonces se toman en 
					 * cuenta como persona nueva
					 */
					
					hashDatosPersona = pfToSearch.hashCodeDatosBasicos();
					
					Solicitud solicitudInSessionCheck = (Solicitud) session
							.getAttribute(KEY_SOLICITUD + hashDatosPersona);
					
					if (solicitudInSessionCheck != null 
							&& solicitudInSessionCheck.getSolicitudId() != null) {
						Map<String, Object> res = this.validarSolicitudEnSesion(solicitudInSessionCheck);
						String edoSolicitud = (String) res.get(EDO_SOLICITUD);
						
						request.setAttribute(EDO_SOLICITUD, edoSolicitud);
						
						if (edoSolicitud.equals("CANCELADA")) {
							String motivoCancelacion = this.obtenerMotivoCancelacion(solicitudInSessionCheck);
							if (StringUtils.isNotBlank(motivoCancelacion)) {
								request.setAttribute(MOTIVO_CANCELACION_SOLICITUD, motivoCancelacion);
							}
						}
						
						request.setAttribute(FOLIO_SOLIC_REQ, solicitudInSessionCheck.getNoFolioSolicitud());
						request.setAttribute(ID_SOLIC_REQ, solicitudInSessionCheck.getSolicitudIdHashed());
						request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
						request.setAttribute(MSG_SOLIC_PROCESO_KEY, MSG_SOLIC_PROCESO);
						
						vista = "concluir.solicitud";
					} else  {
						
						if (solicitudInSessionCheck != null) {
							session.removeAttribute(KEY_SOLICITUD + hashDatosPersona);
						}
						
						pfToSearch.setCurpRenapo(curpBuscado);
						agregarPersonaToTramite(pfToSearch, model, false, session);
						agregarListTipoSerieToModel(usuario, pu, model, session);
	
						session.setAttribute(KEY_DATOS_PERSONA + hashDatosPersona, pfToSearch);
						request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
						
						vista = "captura.libre";
					}
				}
			} catch (ClienteWebserviceRenapoCurpException wscurp) {
				result.rejectValue("errorFormGeneral", "msg.error.personaNoLocalizada.curp");
				vista = "consultapor.datosbasicos";
			} catch (CURPNoLocalizadoEnEntidadExternaException e) {
				result.reject("errorFormGeneral", e.getMessage());
				vista = "consultapor.datosbasicos";
			} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
				result.reject("errorFormGeneral", e.getMessage());
				vista = "consultapor.datosbasicos";
			} catch (ErrorComparacionDatosRENAPOException e) {
				result.rejectValue("errorFormGeneral", "msg.error.personaNoCorresponde.curp");
				vista = "consultapor.datosbasicos";
			} catch (DatosInsuficientesParaConsultaException e) {
				result.reject("errorFormGeneral", e.getMessage());
				vista = "consultapor.datosbasicos";
			}
		}

		return vista;
	}
	
	/* Del paso 2 hacia el paso 3 */
	@RequestMapping(value = "/crear", method = RequestMethod.POST)
	public String crearSolicitud(@RequestParam String hashDatosPersona,
			@ModelAttribute TramiteAsegurado tramiteAsegurado,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {
		
		this.log.debug("Iniciando la creacion (en sesion) de la solicitud");
		String view ;

		/*
		 * Obtenemos los datos del usuario en sesion.
		 */
		Usuario usuario = (Usuario) this.getUsuarioEnSesion(session);
		PerfilUsuario pu = usuario.getPerfilUsuario();
		UsuarioFuncionario uf = usuario.getUsuarioFuncionario();
		tramiteAsegurado.getAsignacionSerieNss().setDelegacion(uf.getDelegacion());
		tramiteAsegurado.getAsignacionSerieNss().setSubdelegacion(uf.getSubdelegacion());

		new TramiteAseguradoValidator().validate(tramiteAsegurado, result);
		
		Serie serie = tramiteAsegurado.getAsignacionSerieNss().getSerie();
		
		// Se checa si se viene del SIME
		boolean fromSIME = session.getAttribute(ProcesoSIMEController.PROCESO_SIME_SESSION_KEY) != null ? true : false;
		
		if (result.hasErrors()) {
			/*
			 * Recuperamos la delegacion y subdelegacion del usuario
			 */
			agregarListTipoSerieToModel(usuario, pu, model, session);

			view = "captura.libre";
		} else {
			Fisica personaCompl = (Fisica) session.getAttribute(KEY_DATOS_PERSONA + hashDatosPersona);
			AsignacionNSS personaAsig = tramiteAsegurado.getFisica();
			
			try {
				
				if (personaCompl != null) {
					ConvertUtils.register(new DateConverter(null), Date.class);
					BeanUtils.copyProperties(personaAsig, personaCompl);
					tramiteAsegurado.setFisica(personaAsig);			
				}

				/*
				 * Validamos en el caso de que exista ya una solicitud en la
				 * sesion para que no se vuelva a generar.
				 */
				Solicitud solicitudInSessionCheck = (Solicitud) session.getAttribute(KEY_SOLICITUD + hashDatosPersona);
				Solicitud solicitud;

				String curpRenapo = personaCompl.getCurpRenapo();
				
				if (solicitudInSessionCheck == null) {
					this.log.debug("La solicitud no existe, generamos una nueva solicitud....");
					
					if (personaAsig.getIdPersona() == null || personaAsig.getIdPersona().equals(0L)) {
						
						// Se tiene una persona nueva						
						personaAsig.setIdPersona(null);

						Calificacion calificacion = new Calificacion();
						if(StringUtils.isBlank(personaAsig.getCurp())){
							this.log.debug("La persona nueva no cuenta con CURP por lo tanto se califica como VALIDADA EN IMSS");
							calificacion.setIdCalificacion(CalificacionPersona.VALIDADO_IMSS.longValue());
						} else {
							this.log.debug("El CURP ["
									+ personaAsig.getCurp()
									+ "] fue localizado en RENAPO, se procede a calificar a la persona nueva como VALIDADA EN RENAPO");
							calificacion.setIdCalificacion(CalificacionPersona.VALIDADO_RENAPO.longValue());
						}

						PersonaCalificacion calificacionIMSS = new PersonaCalificacion();
						calificacionIMSS.setCalificacion(calificacion);

						List<PersonaCalificacion> personaCalificaciones = new ArrayList<PersonaCalificacion>();
						personaCalificaciones.add(calificacionIMSS);

						personaAsig.setPersonaCalificaciones(personaCalificaciones);
					}
					
					ModuloOrigenAsignacionEnum moduloOrigen = null;
					
					if (fromSIME) {
						moduloOrigen = ModuloOrigenAsignacionEnum.SIME;
					} else {
						moduloOrigen = ModuloOrigenAsignacionEnum.VENTANILLA;
					}
					
					// Se crea la solicitud
					solicitud = serviceBusiness.crearSolicitudAsignacionNSS(
							personaAsig, TipoSerieEnum.obtenerEnumById(serie.getTipoSerie().getIdTipoSerie()),
							OrigenSolicitudEnum.VENTANILLA, curpRenapo, null, usuario, moduloOrigen);

				} else {
					this.log.debug("Ya existe una solicitud, nos saltamos el paso de crear...");
					solicitud = solicitudInSessionCheck;
					
					if (solicitud.getSolicitudId() != null) {
						Map<String, Object> res = this.validarSolicitudEnSesion(solicitud);
						String edoSolicitud = (String) res.get(EDO_SOLICITUD);
						
						request.setAttribute(EDO_SOLICITUD, edoSolicitud);
						
						if (edoSolicitud.equals("CANCELADA")) {
							String motivoCancelacion = this.obtenerMotivoCancelacion(solicitud);
							if (StringUtils.isNotBlank(motivoCancelacion)) {
								request.setAttribute(MOTIVO_CANCELACION_SOLICITUD, motivoCancelacion);
							}
						}
						
						request.setAttribute(FOLIO_SOLIC_REQ, solicitud.getNoFolioSolicitud());
						request.setAttribute(ID_SOLIC_REQ, solicitud.getSolicitudIdHashed());
						request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
						request.setAttribute(MSG_SOLIC_PROCESO_KEY, MSG_SOLIC_PROCESO);
						
						return "concluir.solicitud";
					} else if (solicitud.getTramites() != null && !solicitud.getTramites().isEmpty()) {
						for (Tramite tramite : solicitud.getTramites()) {
							if (tramite instanceof TramiteAsegurado) {
								TramiteAsegurado tramiteAseg = (TramiteAsegurado) tramite;
								
								/*
								 * Se ponen la delegación y subdelegación del
								 * usuario firmado, en caso de que la solicitud
								 * no lo tenga
								 */
								if (tramiteAseg.getAsignacionSerieNss().getDelegacion() == null
										&& tramiteAseg.getAsignacionSerieNss().getSubdelegacion() == null
										&& uf != null) {
									tramiteAseg.getAsignacionSerieNss().setDelegacion(uf.getDelegacion());
									tramiteAseg.getAsignacionSerieNss().setSubdelegacion(uf.getSubdelegacion());
								}
								
								/*
								 * Se checa el tipo de serie de la solicitud, si
								 * es diferente se settea de nueva cuenta, esto
								 * sucede cuando el usuario se regresa y cambia
								 * la serie eligida, por lo tanto, es necesario
								 * sobreescribirla para evitar que el NSS se
								 * genere con la serie anteriormente
								 * elegida
								 */
								if (!tramiteAseg.getAsignacionSerieNss().getSerie()
										.getTipoSerie().getIdTipoSerie().equals(serie.getTipoSerie().getIdTipoSerie())){
									this.log.debug("El usuario cambio el tipo de serie ["
											+ tramiteAseg.getAsignacionSerieNss().getSerie().getTipoSerie().getIdTipoSerie()
											+ "] por el tipo [" + serie.getTipoSerie().getIdTipoSerie() + "]");
									tramiteAseg.getAsignacionSerieNss().setSerie(serie);
								}
							}
						}
					}
				}

				session.setAttribute(KEY_SOLICITUD  + hashDatosPersona, solicitud);
				model.addAttribute("folio", solicitud.getNoFolioSolicitud());
								
				view = "captura.complementos";
			} catch (SolicitudNoValidaException e) {
				this.log.error(e);
				result.rejectValue("errorFormGeneral", "", e.getMessage());
			
				agregarListTipoSerieToModel(usuario, pu, model, session);

				view = "captura.libre";
			} catch (SolicitudException e) {
				this.log.error(e);
				result.rejectValue("errorFormGeneral", "", e.getMessage());

				agregarListTipoSerieToModel(usuario, pu, model, session);
					
				view = "captura.libre";
			} catch (IllegalAccessException e) {
				this.log.error(e);
				result.rejectValue("errorFormGeneral", "", e.getMessage());

				agregarListTipoSerieToModel(usuario, pu, model, session);

				view = "captura.libre";
			} catch (InvocationTargetException e) {
				this.log.error(e);
				result.rejectValue("errorFormGeneral", "", e.getMessage());

				agregarListTipoSerieToModel(usuario, pu, model, session);

				view = "captura.libre";
			}
		}

		request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
		
		return view;
	}

	/* Del paso 3 hacia el paso 4 (checar SolicitudController.java) */
	@RequestMapping(value = "/concluir", method = RequestMethod.POST)
	public String concluirSolicitud(@RequestParam String hashDatosPersona,
			@ModelAttribute TramiteAsegurado tramiteAsegurado,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {
		
		String vista = null;
		String mensaje = null;

		Solicitud solicitud = (Solicitud) session.getAttribute(KEY_SOLICITUD + hashDatosPersona);
		
		// Se checa si se viene del SIME
		boolean fromSIME = session.getAttribute(ProcesoSIMEController.PROCESO_SIME_SESSION_KEY) != null ? true: false;

		/*
		 * Se valida si la solicitud esta en sesión, de ser así se procede al
		 * procesamiento, en caso contrario, se manda un mensaje de error
		 */
		if (solicitud != null) {

			/*
			 * Si el usuario refresca la pantalla, se checa que la solicitud
			 * tenga id, en caso de tenerlo se busca el estado en el que esta y
			 * dependiendo del estado de la solicitud se ponen mensajes en la
			 * pantalla
			 */
			if (solicitud.getSolicitudId() != null) {
				Map<String, Object> res = this.validarSolicitudEnSesion(solicitud);
				String edoSolicitud = (String) res.get(EDO_SOLICITUD);
				request.setAttribute(EDO_SOLICITUD, edoSolicitud);
				
				if (edoSolicitud.equals("CANCELADA")) {
					String motivoCancelacion = this.obtenerMotivoCancelacion(solicitud);
					if (StringUtils.isNotBlank(motivoCancelacion)) {
						request.setAttribute(MOTIVO_CANCELACION_SOLICITUD, motivoCancelacion);
					}
				}
				
				request.setAttribute(FOLIO_SOLIC_REQ, solicitud.getNoFolioSolicitud());
				request.setAttribute(ID_SOLIC_REQ, solicitud.getSolicitudIdHashed());
				request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
				
				return "concluir.solicitud";
			}

			TramiteAseguradoDatosComplementariosValidator validator = new TramiteAseguradoDatosComplementariosValidator();
			validator.setFromSIME(fromSIME);
			validator.validate(tramiteAsegurado, result);

			if (result.hasErrors()) {
				model.addAttribute("folio", solicitud.getNoFolioSolicitud());

				vista = "captura.complementos";
			} else {
				this.log.debug("Se concluira la solicitud");

				/*
				 * Se pasan los datos recién capturados al objeto solicitud que
				 * esta en sesión
				 */
				if (solicitud.getTramites() != null
						&& !solicitud.getTramites().isEmpty()) {
					for (Tramite tramite : solicitud.getTramites()) {
						if (tramite instanceof TramiteAsegurado) {
							TramiteAsegurado tramiteAseg = (TramiteAsegurado) tramite;

							tramiteAseg.getFisica().setDomicilios(tramiteAsegurado.getFisica().getDomicilios());
							
							if (tramiteAsegurado.getFisica().getCorreoElectronico() != null
									&& StringUtils.isNotBlank(tramiteAsegurado.getFisica().getCorreoElectronico().getCorreo())) {
								tramiteAseg.getFisica().setMediosContacto(tramiteAsegurado.getFisica().getMediosContacto());
								pasarMedios(tramiteAseg.getFisica());
							}
						} else if (tramite instanceof TramiteFisica) {
							TramiteFisica tramiteFisica = (TramiteFisica) tramite;

							tramiteFisica.getFisica().setDomicilios(tramiteAsegurado.getFisica().getDomicilios());
							if (tramiteAsegurado.getFisica().getCorreoElectronico() != null
									&& StringUtils.isNotBlank(tramiteAsegurado.getFisica().getCorreoElectronico().getCorreo())) {
								tramiteFisica.getFisica().setMediosContacto(tramiteAsegurado.getFisica().getMediosContacto());
								pasarMedios(tramiteFisica.getFisica());
							}
						}
					}
				}

				try {
					UnidadMedicaFamiliar umf = tramiteAsegurado.getFisica().getUmf();

					// Se persiste la solicitud en la base de datos
					solicitud.setFirmadaDigitalmente(false);
					
					//seteo del usuario que realiza la solicitud
					Usuario usr = (Usuario)this.getUsuarioEnSesion(session);
					if(usr != null && usr.getUsuario()!= null);{
						solicitud.setSolicitante(usr);
					}
					
					solicitud = serviceBusiness.guardarSolicitudAsignacionNSS(solicitud, umf, null);

					
					
/***************** TODO se cambia la forma de procesar la solicitud de asignacion *****************/
					
					
					
					// Bandera para indicar si se debe guardar o no el domicilio
					
					AsignacionSerieNSS asignacionSerie = null;
					AsignacionNSS fisica = null;
					List<Tramite> tramites = solicitud.getTramites();
					TramiteFisica tramiteFisica = null;
					
					String msgErrorValidacion = "La solicitud  "
								+ solicitud.getNoFolioSolicitud() + " no será procesada debido a que la persona a dar de alta ya cuenta con NSS";

					// Se obtiene el origen de la solicitud
					OrigenSolicitud origenSolicitud = solicitud.getOrigenSolicitud();
					
					SolicitudNssCorreo nssCorreo = null;
					
					this.log.debug("La solicitud de asignacion con folio "
							+ solicitud.getNoFolioSolicitud()
							+ " esta por ser atendida, su origen es "
							+ origenSolicitud.getDescripcion() + "("
							+ origenSolicitud.getIdTipoSolicitud() + ")");
						
					for (Tramite tramite : tramites) {
						if (tramite instanceof TramiteAsegurado) {
							tramiteAsegurado = (TramiteAsegurado) tramite;
							this.log.info("Se encontro tramiteAsegurado con id -> " + tramiteAsegurado.getTramiteId());
						} else if (tramite instanceof TramiteFisica) {
							tramiteFisica = (TramiteFisica) tramite;
							this.log.info("Se encontro tramiteFisica con id -> " + tramiteFisica.getTramiteId());
						}
					}
					
					/*
					 * Antes de comenzar a procesar la solicitud, es necesario
					 * checar los trámties dentro de la solicitud, si se encuentra
					 * uno de REGISTRO DE PERSONA, se debe checar si la persona ya existe
					 * con un NSS, en caso de cumplirse NO se debe procesar la solicitud
					 * recibida.
					 */
					if (tramiteFisica != null && tramiteFisica.getFisica() != null
							&& tramiteFisica.getTipoTramite() != null
							&& tramiteFisica.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo())) {
						
						this.log.info("Se va a realizar la validación para confirmar el procesamiento de la solicitud de NSS "
								+ solicitud.getNoFolioSolicitud() + " ya que se encontró un trámite de REGISTRO DE PERSONA");
						
						boolean isCalificacionIMSS = false;
						Fisica fisicaNuevaValidar = tramiteFisica.getFisica();
						
						this.log.debug("Persona nueva para realizar validaciones -> " + fisicaNuevaValidar);
						
						List<PersonaCalificacion> calificaciones = fisicaNuevaValidar.getPersonaCalificaciones();
						
						if (calificaciones != null) {
							for (PersonaCalificacion calificacion : calificaciones) {
								if (calificacion.getCalificacion().getIdCalificacion()
										.intValue() == CalificacionPersona.VALIDADO_IMSS.intValue()) {
									isCalificacionIMSS = true;
								}
							}
						}
						
						if (fisicaNuevaValidar.getIdPersona() == null && isCalificacionIMSS) {
							/*
							 * Si la persona NUEVA cuenta con calificación IMSS significa que
							 * se esta dando de alta manualmente, por lo tanto, se debe procesar
							 * la solicitud de manera normal
							 */
							this.log.info("La persona encontrada tiene calificación IMSS, la solicitud "
								+ solicitud.getNoFolioSolicitud() + " va a ser procesada de manera normal");
						} else {
							/*
							 * Ya que se sabe que la persona no fue capturada manualmente, se garantiza que 
							 * la persona a dar de alta trae CURP, por lo tanto, la búsqueda de las
							 * personas con NSS se puede realizar sólo por CURP.
							 */
							List<Fisica> personasEncontradas = null;
							String curp = fisicaNuevaValidar.getCurp();

							this.log.info("La búsqueda para la validación se realiza a través de la CURP " + curp);
							
							personasEncontradas = this.personaBusiness
									.buscarPersonaFisicaPorCurpEnImss(curp);
																
							if (personasEncontradas != null) {
								for (Fisica fisicaAux : personasEncontradas) {
									boolean tieneCalificacionRENAPO = calificacionesPersonaBusinessService
											.tieneCalificacionEspecifica(fisicaAux,
													CalificacionEnum.VALIDADO_RENAPO);
									
									if (StringUtils.isNotBlank(fisicaAux.getNss()) && tieneCalificacionRENAPO) {
										this.log.error(msgErrorValidacion + " -> " + fisicaAux);
										throw new SolicitudNoValidaException(msgErrorValidacion);
									} 
								}
							}
							
						}
					}
					
					if (tramiteAsegurado != null) {
						
						// Tramite para la generación y asignacion de NSS
						String curpRenapo = tramiteAsegurado.getCurpRENAPO();
						
						asignacionSerie = tramiteAsegurado.getAsignacionSerieNss();
						
						if (tramiteAsegurado.getNssCorreo() != null) {
							nssCorreo = tramiteAsegurado.getNssCorreo();
						}
						
						if (tramiteAsegurado.getFisica() != null) {
							fisica = tramiteAsegurado.getFisica();
							umf = fisica.getUmf();
							this.log.debug("****************** inicia modificacion de personas *****************  " + new Date());
							//if (origenSolicitud.getIdTipoSolicitud().equals(OrigenSolicitudEnum.VENTANILLA.getId())) {
								// Se obtiene la persona esta registrada se actualiza la informacion
								if (StringUtils.isNotBlank(curpRenapo) && fisica != null && fisica.getIdPersona() != null) {
									
									Fisica fisicaRENAPO = localizarPersonaFisicaEnRENAPOServiceBusiness.
											localizarPersonaFisicaEnRENAPOxCURP(curpRenapo);
									fisicaRENAPO.setIdPersona(fisica.getIdPersona());
									fisicaRENAPO.setCveFisica(fisica.getCveFisica());

									AfectarDatosPersonaWrapper personaWrapper = new AfectarDatosPersonaWrapper();
									personaWrapper.setFisica(fisicaRENAPO);
									personaWrapper.setModificarCURP(true);
									personaWrapper.setModificarFechaNacimiento(true);
									//se actualizara el anio y mes de nacimiento, el segundo parametro indica que los datos se obtendran a partir de la fecha de nacimiento
									//y no del mes y anio que traigan la persona que en esta caso seria null porque la persona que se manda es la de renapo
									personaWrapper.actualizarAnioMesNacimiento(true, true);

									calificacionesPersonaBusinessService.calificarRENAPO(fisicaRENAPO);
									log.debug("Se actualizara a la persona con la curp: " + fisicaRENAPO.getCurp() + " y id: " + fisicaRENAPO.getIdPersona());
									personaBusiness.afectarDatosPersona(personaWrapper);

									fisica.setCurp(fisicaRENAPO.getCurp());
									fisica.setFechaNacimiento(fisicaRENAPO.getFechaNacimiento());
								}
							//}
							this.log.debug("****************** finaliza modificacion de personas *****************  " + new Date());
						}
						
						if (tramiteAsegurado.getIcaDatosRespuesta() != null) {
							this.log.debug("****************** inicia ICA de personas *****************  " + new Date());
							ICADatosRespuesta datosRespuesta = (ICADatosRespuesta) tramiteAsegurado
									.getIcaDatosRespuesta();
							TramiteCambioInformacionPersona infoPersona = new TramiteCambioInformacionPersona();
							infoPersona.setDatosICA(datosRespuesta);
							infoPersona.setTramiteId(tramiteAsegurado.getTramiteId());

							Modulo mod = new Modulo();
							mod.setIdModulo(ModuloEnum.ASIGNACION_NSS.getCodigo()
									.longValue());
							afectarDatosPersonaBusiness.afectarDatos(infoPersona, mod);
							this.log.debug("****************** finaliza ICA de personas *****************  " + new Date());
						}
						
					}
					this.log.debug("****************** inicia Calculo de NSS *****************  " + new Date());
					fisica.setNss(serieServiceBusiness.calculaNSS(tramiteAsegurado.getAsignacionSerieNss(), fisica));
					this.log.debug("****************** finaliza Calculo de NSS *****************  " + new Date());
					tramiteAsegurado.setFisica(fisica);
					
					this.log.debug("****************** inicia IMPACTO y registro de NSS y concluir Solicitud *****************  " + new Date());
					serviceBusiness.procesarSolicitudAsignacionNSSLigero(solicitud, tramiteFisica, tramiteAsegurado);
					this.log.debug("****************** finaliza IMPACTO y registro de NSS y concluir Solicitud *****************  " + new Date());
					
					
					
					try{
						/*
						 * Se ejecuta la operación del NSS-Correo y se envía el correo con el
						 * NSS asignado sólo cuando el origen es INTERNET
						 */
						if ((origenSolicitud.getIdTipoSolicitud().equals(OrigenSolicitudEnum.INTERNET.getId())
								|| origenSolicitud.getIdTipoSolicitud().equals(OrigenSolicitudEnum.MOVILES.getId()))
								&& nssCorreo != null) {
							serviceBusiness.ejecutarOperacionValidacionCorreoCurp(nssCorreo);	
						}
						
						/*
						 * Se envia el correo con el NSS asignado siempre y cuando se tenga
						 * correo capturado, para el caso de asignacion externa siempre se debe
						 * de cumplir la condicion ya que el correo es requerido, para
						 * ventanilla puede no venir
						 */
						if (fisica.getCorreoElectronico() != null
								&& StringUtils.isNotBlank(fisica.getCorreoElectronico().getCorreo())) {
								serviceBusiness.enviarCorreoNSS(fisica, solicitud.getNoFolioSolicitud(), true, solicitud.getSolicitudId(), null);
						}
						}catch(Exception e){
						log.error("ocurrio un error al generar la bitacora o el envio de correo ", e);
					}

						
/**************************  FIN nueva seccion *********************************/
//Se concluye la solicitud de asignación
//this.serviceBusiness.encolarSolicitudAsignacionNSS(solicitud);
//this.serviceBusiness.procesarSolicitudAsignacionNSS(solicitud.getSolicitudId());
					
					session.setAttribute(KEY_SOLICITUD + hashDatosPersona, solicitud);
				} catch (SolicitudNoValidaException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (SolicitudNoEncontradaException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (TramiteNoEncontradoException e) {
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
			/*	} catch (SolicitudException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);	*/
				} catch (AfectacionDatosPersonaException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (PersonaNoEncontradaException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				/*	
				} catch (RegistroPersonaFisicaException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
					*/
				} catch (NivelDeAsignacionSerieIndefinidoException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (SeriesNoLocalizadasException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (ErrorAlActivarSerieException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (DomicilioNoValidoException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (CURPNoLocalizadoEnEntidadExternaException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (ClienteWebserviceRenapoCurpException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (PersonaSinCalificacionesException e) {
					this.log.error(e);
					mensaje = e.getSituacion();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				} catch (Exception e){
					this.log.error(e);
					mensaje = e.getMessage();
					model.addAttribute("HUBO_ERROR", true);
					this.serviceBusiness.cancelar(solicitud);
				}
				model.addAttribute("mensaje", mensaje);
				
				request.setAttribute(FOLIO_SOLIC_REQ, solicitud.getNoFolioSolicitud());
				request.setAttribute(ID_SOLIC_REQ, solicitud.getSolicitudIdHashed());

				vista = "concluir.solicitud";
			}
		} else {
			mensaje = "Ocurrió un error inesperado, favor de reintentar.";
			this.log.error(mensaje);
			model.addAttribute("HUBO_ERROR", true);
			vista = "concluir.solicitud";
		}
				
		request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
		
		return vista;
	}
	
	/**
	 * URL auxiliar para checar el estado de la solicitud cuando el COMET tarde
	 * más de la cuenta.
	 * 
	 */
	@RequestMapping(value = "/concluir", method = RequestMethod.GET)
	public String checarEstadoSolicitud(Model model, HttpSession session,
			HttpServletRequest request, @RequestParam("iS") String idSolicitud,
			@RequestParam("hsdp") String hashDatosPersona) {
		
		String vista = null;
		String mensaje = null;

		Long solicitudId = null;
		try {
			solicitudId = Long.valueOf(Base64Cipher.descrifrar(idSolicitud));
		} catch (NumberFormatException e) {
			this.log.error(e);
		} catch (InvalidKeyException e) {
			this.log.error(e);
		} catch (IllegalBlockSizeException e) {
			this.log.error(e);
		} catch (BadPaddingException e) {
			this.log.error(e);
		} catch (IOException e) {
			this.log.error(e);
		}
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(solicitudId);
		
		boolean fromSIME = session.getAttribute(ProcesoSIMEController.PROCESO_SIME_SESSION_KEY) != null ? true : false;
		
		this.log.debug("Se va a checar el estado de la solicitud de asignacion -> "
				+ solicitud);
		
		/*
		 * Se valida si la solicitud esta en sesión y que tenga id, de ser así
		 * se procede a checar el estado, en caso contrario, se manda un mensaje
		 * de error ya que significa que el usuario ingreso la URL directo
		 */
		if (solicitud != null && solicitud.getSolicitudId() != null) {
			Map<String, Object> res = this.validarSolicitudEnSesion(solicitud);
			String edoSolicitud = (String) res.get(EDO_SOLICITUD);
			solicitud = (Solicitud) res.get(SOL_CHECK_STATUS);
			
			this.log.debug("Estado de la solicitud -> " + edoSolicitud);
			request.setAttribute(EDO_SOLICITUD, edoSolicitud);
			
			if (edoSolicitud.equals("CANCELADA")) {
				String motivoCancelacion = this.obtenerMotivoCancelacion(solicitud);
				if (StringUtils.isNotBlank(motivoCancelacion)) {
					request.setAttribute(MOTIVO_CANCELACION_SOLICITUD, motivoCancelacion);
				}
			}
			
			if (fromSIME && edoSolicitud.equals("ATENDIDA")) {
				/*
				 * En caso de se venga de SIME y la solicitud ya fue atendida,
				 * es necesario obtener la información nueva para complementar
				 * al extranjero que se está procesando
				 */
				Solicitud solSIME;
				try {
					solSIME = this.solicitudBusinessRemote.consultar(solicitud);

					Fisica asegurado = null;
					
					for (Tramite tramite : solSIME.getTramites()) {
						if (tramite instanceof TramiteAsegurado) {
							TramiteAsegurado tramiteAsegurado = (TramiteAsegurado) tramite;
							asegurado = tramiteAsegurado.getFisica();
						}
					}
					
					if (asegurado != null) {
						Empleado empSession = (Empleado) session
								.getAttribute(ProcesoSIMEController.EXTRANJERO_SESSION_KEY);
						empSession.setNss(asegurado.getNss());
						empSession.setEstadoRegistro(EstadoRegistroSIMEEnum.EXITO);
						
						UnidadMedicaFamiliar umf = new UnidadMedicaFamiliar();
						umf.setIdUMF(asegurado.getUmf().getIdUMF());
						umf.setNoEconomico(asegurado.getUmf().getNoEconomico());
						umf.setSubdelegacion(asegurado.getUmf().getSubdelegacion());
						empSession.setUmf(umf);
					}
				
				} catch (SolicitudNoEncontradaException e) {
					this.log.error("No se encontro la solicitud para complementar la informacion de SIME", e);
				}				
			}
			
			request.setAttribute(FOLIO_SOLIC_REQ, solicitud.getNoFolioSolicitud());
			request.setAttribute(ID_SOLIC_REQ, idSolicitud);
			
			vista = "concluir.solicitud";
		} else {
			this.log.warn("No se encontro solicitud en sesion para checar su estatus, se procede a poner mensaje de error.");
			mensaje = "Ocurrió un error inesperado, favor de reintentar.";
			model.addAttribute("HUBO_ERROR", true);
			vista = "concluir.solicitud";
			request.setAttribute("mensaje", mensaje);
		}
		
		request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
		
		return vista;
	}

	/**
	 * Metodo en donde se selecciona una persona de la lista de las personas que
	 * ya existen y se requiere agregar el NSS
	 * 
	 * @param tramiteAsegurado
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/complementar/persona", method = RequestMethod.POST)
	public String complementarPersona(@ModelAttribute Fisica fisica,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {
		
		String vista = null;
		this.log.debug("Complementando el registro de la persona seleccionada -> " + fisica);

		/*
		 * Una vez que se encuentra a la persona candidata para asignarle NSS,
		 * se valida que no tenga solicitudes pendientes
		 */
		Map<String, Object> respuesta = existeSolicitudPendiente(fisica);
		boolean existeSolRegistrada = (Boolean) respuesta.get(EXISTE_SOL_REGISTRADA);
		boolean existeSolProceso = (Boolean) respuesta.get(EXISTE_SOL_PROCESO);
		
		if (existeSolRegistrada || existeSolProceso) {
			String folioPendiente = respuesta.get(FOLIO_PENDIENTE).toString();
			Object[] errorArgs = {folioPendiente};
			
			if (existeSolRegistrada) {
				result.rejectValue("errorFormGeneral",
						"msg.error.sol.pendiente.registrada",
						errorArgs, "");
			} else if (existeSolProceso) {
				result.rejectValue("errorFormGeneral",
						"msg.error.sol.pendiente.en.proceso",
						errorArgs, "");
			}
			
			return "consultapor.datosbasicos";
		}
		
		try {
			Usuario usuario = (Usuario) this.getUsuarioEnSesion(session);
			PerfilUsuario pu = usuario.getPerfilUsuario();

			if (StringUtils.isNotBlank(fisica.getNss())) {

				fisica = serviciosPersonaBusinessRemote
						.buscarPersonaFisicayDPyDyMCEnIMSSbyNSS(fisica.getNss());
			} else {
				fisica = serviciosPersonaBusinessRemote
						.buscarPersonaFisicayDPyDyMCEnIMSS(fisica
								.getIdPersona());
			}
			pasarMedios(fisica);
			pasarDocumentosProbatorios(fisica);
			
			this.log.debug("Persona existente complementada");
			
			int hashDatosPersona = fisica.hashCodeDatosBasicos();
			
			/*
			 * Ahora se checa que no este en sesión otra solicitud
			 * para la misma persona
			 */
			Solicitud solicitudInSessionCheck = (Solicitud) session
					.getAttribute(KEY_SOLICITUD + hashDatosPersona);
					
			if (solicitudInSessionCheck != null 
					&& solicitudInSessionCheck.getSolicitudId() != null) {

				Map<String, Object> res = this.validarSolicitudEnSesion(solicitudInSessionCheck);
				String edoSolicitud = (String) res.get(EDO_SOLICITUD);
				
				request.setAttribute(EDO_SOLICITUD, edoSolicitud);
				
				if (edoSolicitud.equals("CANCELADA")) {
					String motivoCancelacion = this.obtenerMotivoCancelacion(solicitudInSessionCheck);
					if (StringUtils.isNotBlank(motivoCancelacion)) {
						request.setAttribute(MOTIVO_CANCELACION_SOLICITUD, motivoCancelacion);
					}
				}
				
				request.setAttribute(FOLIO_SOLIC_REQ, solicitudInSessionCheck.getNoFolioSolicitud());
				request.setAttribute(ID_SOLIC_REQ, solicitudInSessionCheck.getSolicitudIdHashed());
				request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
				request.setAttribute(MSG_SOLIC_PROCESO_KEY, MSG_SOLIC_PROCESO);
				
				vista = "concluir.solicitud";
			} else  {
				if (solicitudInSessionCheck != null) {
					session.removeAttribute(KEY_SOLICITUD + hashDatosPersona);
				}
				
				agregarPersonaToTramite(fisica, model, true, session);
				agregarListTipoSerieToModel(usuario, pu, model, session);
				session.setAttribute(KEY_DATOS_PERSONA + hashDatosPersona, fisica);
				request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
				
				vista = "captura.libre";
			}
		} catch (PersonaFisicaNoEncontradaException e) {
			result.reject("errorFormGeneral", e.getMessage());
			vista = "consultapor.datosbasicos";
		} catch (PersonasNoLocalizadasException e) {
			result.reject("errorFormGeneral", e.getMessage());
			vista = "consultapor.datosbasicos";
		} catch (NssRelacionadoVariasPersonasException e) {
			result.reject("errorFormGeneral", e.getMessage());
			vista = "consultapor.datosbasicos";
		} catch (Exception e1) {
			result.reject("", "Ocurri\u00F3 un error inesperado");
			log.error(e1);
			vista = "consultapor.datosbasicos";
		}
					
		return vista;
	}

	@RequestMapping(value = "/complementar/personaNueva", method = RequestMethod.POST)
	public String complementarPersonaNueva(@ModelAttribute Fisica fisica,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {

		this.log.debug("Complementando el registro de la persona nueva -> " + fisica);
		
		String vista;

		try {
			Usuario usuario = (Usuario) this.getUsuarioEnSesion(session);
			log.info("Usuario : " + usuario);
			PerfilUsuario pu = usuario.getPerfilUsuario();

			Fisica pfToSearch = nullearCampos(fisica);
			Fisica personaNueva = serviceBusiness.getDatosBasicosPersonaNueva(pfToSearch);

			int hashDatosPersona = personaNueva.hashCodeDatosBasicos();
			
			Solicitud solicitudInSessionCheck = (Solicitud) session
					.getAttribute(KEY_SOLICITUD + hashDatosPersona);
			
			if (solicitudInSessionCheck != null 
					&& solicitudInSessionCheck.getSolicitudId() != null) {
				Map<String, Object> res = this.validarSolicitudEnSesion(solicitudInSessionCheck);
				String edoSolicitud = (String) res.get(EDO_SOLICITUD);
				
				request.setAttribute(EDO_SOLICITUD, edoSolicitud);
				
				if (edoSolicitud.equals("CANCELADA")) {
					String motivoCancelacion = this.obtenerMotivoCancelacion(solicitudInSessionCheck);
					if (StringUtils.isNotBlank(motivoCancelacion)) {
						request.setAttribute(MOTIVO_CANCELACION_SOLICITUD, motivoCancelacion);
					}
				}
				
				request.setAttribute(FOLIO_SOLIC_REQ, solicitudInSessionCheck.getNoFolioSolicitud());
				request.setAttribute(ID_SOLIC_REQ, solicitudInSessionCheck.getSolicitudIdHashed());
				request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
				request.setAttribute(MSG_SOLIC_PROCESO_KEY, MSG_SOLIC_PROCESO);
				
				vista = "concluir.solicitud";
			} else  {
				
				if (solicitudInSessionCheck != null) {
					session.removeAttribute(KEY_SOLICITUD + hashDatosPersona);
				}
				
				agregarPersonaToTramite(personaNueva, model, true, session);
				agregarListTipoSerieToModel(usuario, pu, model, session);
				
				session.setAttribute(KEY_DATOS_PERSONA + hashDatosPersona, personaNueva);
				request.setAttribute(HASH_CODE_DATOS_BASICOS, hashDatosPersona);
				
				this.log.debug("Persona nueva complementada");

				vista = "captura.libre";
			}
		} catch (ClienteWebserviceRenapoCurpException wscurp) {
			result.rejectValue("errorFormGeneral",
					"msg.error.personaNoLocalizada.curp");
			vista = "consultapor.datosbasicos";
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			result.reject("errorFormGeneral", e.getMessage());
			vista = "consultapor.datosbasicos";
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			result.reject("errorFormGeneral", e.getMessage());
			vista = "consultapor.datosbasicos";
		}

		return vista;
	}
	
	@RequestMapping(value = "/limpiar-sesion", method = RequestMethod.POST)
	public @ResponseBody String limpiarDatosSession(HttpSession session,
			@RequestParam String hashDatosPersona) {
		session.removeAttribute(KEY_SOLICITUD  + hashDatosPersona);
		session.removeAttribute(KEY_DATOS_PERSONA  + hashDatosPersona);
	
		return "";
	}

	/**
	 * Con este metodo evitamos que se pasen al backend propiedades que vengan
	 * como cadenas vacias. Para un funcionamiento correcto de las consultas,
	 * las propiedades deben ser NULAS o NO NULAS (pero NUNCA CADENAS VACIAS)
	 * 
	 * @param oForm
	 * @return
	 */
	private Fisica nullearCampos(Fisica oForm) {
		Fisica personaFisica = new Fisica();

		if (StringUtils.isBlank(oForm.getCurp())) {
			personaFisica.setCurp(null);
		} else {
			personaFisica.setCurp(oForm.getCurp().toUpperCase());
		}

		if (StringUtils.isBlank(oForm.getRfc())) {
			personaFisica.setRfc(null);
		} else {
			personaFisica.setRfc(oForm.getRfc().toUpperCase());
		}

		if (StringUtils.isBlank(oForm.getNombre())) {
			personaFisica.setNombre(null);
		} else {
			personaFisica.setNombre(oForm.getNombre().toUpperCase());
		}

		if (StringUtils.isBlank(oForm.getPrimerApellido())) {
			personaFisica.setPrimerApellido(null);
		} else {
			personaFisica.setPrimerApellido(oForm.getPrimerApellido().toUpperCase());
		}

		if (StringUtils.isBlank(oForm.getSegundoApellido())) {
			personaFisica.setSegundoApellido(null);
		} else {
			personaFisica.setSegundoApellido(oForm.getSegundoApellido().toUpperCase());
		}

		if (oForm.getFechaNacimiento() == null) {
			personaFisica.setFechaNacimiento(null);
		} else {
			personaFisica.setFechaNacimiento(oForm.getFechaNacimiento());
		}

		if (oForm.getLugarNacimiento() != null
				&& (oForm.getLugarNacimiento().getClave() == null
				|| oForm.getLugarNacimiento().getClave().equals("-1")
				|| oForm.getLugarNacimiento().getClave().equals(""))) {
			personaFisica.setLugarNacimiento(null);
		} else {
			personaFisica.setLugarNacimiento(oForm.getLugarNacimiento());
		}

		if (oForm.getSexo() != null && (oForm.getSexo().getIdSexo() == null
				|| oForm.getSexo().getIdSexo() == -1)) {
			personaFisica.setSexo(null);
		} else {
			personaFisica.setSexo(oForm.getSexo());
		}

		return personaFisica;
	}
	
	private boolean mostrarListaPersonasEncontradas(List<Fisica> personasFisicas) {
		boolean mostrarLista = false;

		// Se recupera la primera persona fisica.
		Fisica personaFisica = personasFisicas.get(0);

		if (personasFisicas.size() > 1) {
			mostrarLista = true;
		} else if (personaFisica.getEstatusRenapo() != null
				&& personaFisica.getEstatusRenapo().equals(ESTATUS_RENAPO_INVALIDO)) {
			mostrarLista = true;
		}

		return mostrarLista;
	}

	private void agregarPersonaToTramite(Fisica personaFisica, Model model,
			boolean yaComplementado, HttpSession session) {
		
		this.log.debug("Persona a agregar al tramite NSS ventanilla -> "
				+ personaFisica);

		Usuario usuario = (Usuario) this.getUsuarioEnSesion(session);
		
		AsignacionNSS asignacionNss = new AsignacionNSS();
		TramiteAsegurado tramiteAsegurado = new TramiteAsegurado();

		/*
		 * Se consultan los datos complementarios por separado, ya que la
		 * consulta inicial no los trae, siempre y cuando sea una persona
		 * ya existente
		 */
		if (personaFisica.getIdPersona() != null && !yaComplementado) {
			this.log.debug("Se van a consultar datos complementarios de la persona ["
					+ personaFisica.getIdPersona() + "]");
			
			personaFisica.setDocumentosProbatorios(this.componentesExternosBusiness.getDocumentosProbatoriosPersona(personaFisica));
			pasarDocumentosProbatorios(personaFisica);
							
			personaFisica.setMediosContacto(this.componentesExternosBusiness.getMediosContactoPersona(personaFisica));
			pasarMedios(personaFisica);
			
			personaFisica.setDomicilios(this.componentesExternosBusiness.obtenerDomicilioParticularPersona(personaFisica));
		}
		
		try {
			ConvertUtils.register(new DateConverter(null), Date.class);
			BeanUtils.copyProperties(asignacionNss, personaFisica);
		} catch (IllegalAccessException e) {
			this.log.error(e.getStackTrace());
		} catch (InvocationTargetException e) {
			this.log.error(e.getStackTrace());
		}

		tramiteAsegurado.setFisica(asignacionNss);
		
		if(asignacionNss.getNss() != null) {
			this.log.info("La persona ya cuenta con un NSS, se va a crear la solicitud de recuperacion");
			
			Solicitud solicRecuperacion = null;
			
			try {
				Map<String, Object> resultado = serviceBusiness.crearSolicitudRecuperacionNSS(
						personaFisica, OrigenSolicitudEnum.VENTANILLA, usuario);
				solicRecuperacion = (Solicitud) resultado.get("solicitud");
			} catch (SolicitudNoValidaException e) {
				this.log.error(e);
			} catch (SolicitudException e) {
				this.log.error(e);
			}
			
			model.addAttribute(ID_SOLIC_REQ, solicRecuperacion.getSolicitudIdHashed());
			
		}

		// Subimos al request el objeto de Modelo
		model.addAttribute("tramiteAsegurado", tramiteAsegurado);

	}

	private void agregarListTipoSerieToModel(Usuario usuario, PerfilUsuario pu,
			Model model, HttpSession session) {
		
		// Se checa si se viene del SIME
		boolean fromSIME = session.getAttribute(ProcesoSIMEController.PROCESO_SIME_SESSION_KEY) != null ? true : false;
		
		this.log.debug("Obteniendo series para asignacion (fromSIME: " + fromSIME + ")");
		
		/*
		 * Recuperamos la delegacion y subdelegacion del usuario
		 */
		UsuarioFuncionario uf = usuario.getUsuarioFuncionario();

		model.addAttribute("delegacion", uf.getDelegacion());
		model.addAttribute("subdelegacion", uf.getSubdelegacion());

		Long idDelegacion = null;
		Long idSubdelegacion = null;
		if (uf.getDelegacion() != null
				&& uf.getDelegacion().getId() != null
				&& !uf.getDelegacion().getId().equals(-1)) {
			idDelegacion = Long.valueOf(uf.getDelegacion().getId());

		}
		if (uf.getSubdelegacion() != null
				&& uf.getSubdelegacion().getId() != null
				&& !uf.getSubdelegacion().getId().equals(-1)) {
			idSubdelegacion = Long.valueOf(uf.getSubdelegacion().getId());
		}

		// Obtenemos las series dependiendo de la delegacion y subdelegacion
		AsignacionSerieNSS asignacionSerieNSS = new AsignacionSerieNSS();
		asignacionSerieNSS.setDelegacion(uf.getDelegacion());
		asignacionSerieNSS.setSubdelegacion(uf.getSubdelegacion());

		List<TipoSerie> listTipoSerie = new ArrayList<TipoSerie>();

		try {
			List<AsignacionSerieNSS> listAsignacion = serieServiceBusiness
					.obtenerSeriesActivas(idDelegacion, idSubdelegacion);
			listTipoSerie = getTipoSerie(listAsignacion, fromSIME);
			this.log.debug("Series encontradas" + listTipoSerie);
		} catch (SeriesNoLocalizadasException e) {
			this.log.debug("Series NO encontradas");
		}

		// Subimos las series al request
		model.addAttribute("listTipoSerie", listTipoSerie);
	}

	private List<TipoSerie> getTipoSerie(
			List<AsignacionSerieNSS> listAsignacion, boolean fromSIME) {
		List<TipoSerie> listTipoSerie = new ArrayList<TipoSerie>();

		if (listAsignacion != null && !listAsignacion.isEmpty()) {
			TipoSerie tipoSerieAnt = listAsignacion.get(0).getSerie()
					.getTipoSerie();
			Integer idTipoSerieAnt = tipoSerieAnt.getIdTipoSerie();

			StringBuffer descripcion = new StringBuffer();
			descripcion.append("[").append(tipoSerieAnt.getIdTipoSerie())
					.append("] ").append(tipoSerieAnt.getDescripcion());

			tipoSerieAnt.setDescripcion(descripcion.toString());
			listTipoSerie.add(tipoSerieAnt);

			for (AsignacionSerieNSS asignacion : listAsignacion) {
				TipoSerie tipoSeriePost = asignacion.getSerie().getTipoSerie();
				Integer idTipoSeriePost = tipoSeriePost.getIdTipoSerie();

				if (!idTipoSeriePost.equals(idTipoSerieAnt)) {
					descripcion = new StringBuffer();
					descripcion.append("[")
							.append(tipoSeriePost.getIdTipoSerie()).append("] ")
							.append(tipoSeriePost.getDescripcion());

					tipoSeriePost.setDescripcion(descripcion.toString());
					listTipoSerie.add(tipoSeriePost);

					idTipoSerieAnt = idTipoSeriePost;
				}
			}
		}
		
		/*
		 * Si se viene del SIME la única serie disponible debe ser la de
		 * MEXICANOS EN EL EXTRANJERO
		 */
		if (fromSIME) {
			Iterator<TipoSerie> itSeries = listTipoSerie.iterator();
			TipoSerie serie = null;
			while(itSeries.hasNext()) {
				serie = itSeries.next();
				
				if (serie.getIdTipoSerie().intValue() != TipoSerieEnum.MEXICANOS_EXTRANJERO
						.getClave()) {
					itSeries.remove();
				}
			}
		}

		return listTipoSerie;
	}
	
	private void pasarMedios(Fisica fisica) {

		// Se pasan los medio de contacto a los atributos correspondientes
		if (fisica.getMediosContacto() != null && !fisica.getMediosContacto().isEmpty()) {
			for (MedioContacto medio : fisica.getMediosContacto()) {
				if (medio instanceof CorreoElectronico
						|| (medio.getTipoMedioContacto() != null && medio
								.getTipoMedioContacto()
								.getIdTipoMedioContacto().longValue() == TipoContactoEnum.CORREO_ELECTRONICO
								.getCodigo().longValue())) {
					fisica.setCorreoElectronicoAux((CorreoElectronico) medio);
				} else if (medio instanceof TelefonoFijo
						|| (medio.getTipoMedioContacto() != null && medio
								.getTipoMedioContacto()
								.getIdTipoMedioContacto().longValue() == TipoContactoEnum.TELEFONO_FIJO
								.getCodigo().longValue())) {
					fisica.setTelefonoFijoAux((TelefonoFijo) medio);
				} else if (medio instanceof TelefonoMovil
						|| (medio.getTipoMedioContacto() != null && medio
								.getTipoMedioContacto()
								.getIdTipoMedioContacto().longValue() == TipoContactoEnum.TELEFONO_MOVIL
								.getCodigo().longValue())) {
					fisica.setTelefonoMovilAux((TelefonoMovil) medio);
				} else if (medio instanceof Facebook
						|| (medio.getTipoMedioContacto() != null && medio
								.getTipoMedioContacto()
								.getIdTipoMedioContacto().longValue() == TipoContactoEnum.FACEBOOK
								.getCodigo().longValue())) {
					fisica.setFacebookAux((Facebook) medio);
				} else if (medio instanceof Twitter
						|| (medio.getTipoMedioContacto() != null && medio
								.getTipoMedioContacto()
								.getIdTipoMedioContacto().longValue() == TipoContactoEnum.TWITTER
								.getCodigo().longValue())) {
					fisica.setTwitterAux((Twitter) medio);
				}
			}
		}

	}
	
	/**
	 * Se pasan los documentos probatorios a los atributos
	 * específicos para poder mostrarlos en la vista
	 */
	private void pasarDocumentosProbatorios(Fisica fisica) {
		
		if (fisica.getDocumentosProbatorios() != null && !fisica.getDocumentosProbatorios().isEmpty()) {
			for (DocumentoProbatorio doc : fisica.getDocumentosProbatorios()) {
				Long idTipoDocumento = doc.getDocumentoPorTipo().getIdDocumentoPorTipo();
				
				this.log.debug("Tipo documento probatorio -> " + idTipoDocumento);
				
				if (idTipoDocumento.equals(DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId())) {
					fisica.setActaNacimientoAux((Nacimiento) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId())) {
					fisica.setCartaNaturalizacionAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId())) {
					fisica.setDocumentoMigratorioAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId())) {
					fisica.setNumeroUnicoExtranjeroAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId())) {
					fisica.setCertificadoNacionalidadMexicanaAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId())) {
					fisica.setOficioSolicitanteRefugiadoAux((CURP) doc);
				} else if (idTipoDocumento.equals(DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId())) {
					fisica.setFormaMigratoriaTuristaAux((CURP) doc);
				}
			}
		}
	}
	
	private Map<String, Object> existeSolicitudPendiente(
			Fisica fisicaEncontrada) {
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		boolean existeSolRegistrada = false;
		boolean existeSolProceso = false;
		Solicitud solicitudActiva = null;
		String folioPendiente = null;
		
		if (fisicaEncontrada.getIdPersona() != null) {	
			try {
				solicitudActiva = this.serviceBusiness.obtenerSolicitudRegistrada(fisicaEncontrada
								.getIdPersona());
			
				if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
					existeSolRegistrada = true;
					folioPendiente = solicitudActiva.getNoFolioSolicitud();
					
					this.log.info("La persona [idPersona:"
							+ fisicaEncontrada.getIdPersona()
							+ "] ya cuenta con solicitud REGISTRADA para NSS [folio:"
							+ folioPendiente + "]");
					
				} else {
					solicitudActiva = this.serviceBusiness.obtenerSolicitudEnProceso(fisicaEncontrada
							.getIdPersona());
					
					if (solicitudActiva != null && solicitudActiva.getSolicitudId() != null) {
						existeSolProceso = true;
						folioPendiente = solicitudActiva.getNoFolioSolicitud();
						
						this.log.info("La persona [idPersona:"
								+ fisicaEncontrada.getIdPersona()
								+ "] ya cuenta con solicitud EN_PROCESO para NSS [folio:"
								+ folioPendiente + "]");
					} 
				}
							
			} catch (SolicitudException e) {
				this.log.error(e);
			}
			
		}
		
		respuesta.put(EXISTE_SOL_REGISTRADA, existeSolRegistrada);
		respuesta.put(EXISTE_SOL_PROCESO, existeSolProceso);
		respuesta.put(FOLIO_PENDIENTE, folioPendiente);
		
		return respuesta;
	}
			
	private Map<String, Object> validarSolicitudEnSesion(Solicitud solicitud) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Solicitud solAux = this.solicitudBusinessRemote.obtenerEstados(solicitud);
		int cveEdoSolicitud = solAux.getEstadoSolicitud().getIdEstadoSolicitud().intValue();
		String edoSolicitud = null;
						
		if (cveEdoSolicitud == EstadoSolicitudEnum.ATENDIDA
				.getCodigo().intValue()) {
			
			this.log.warn("La solicitud para NSS con folio "
					+ solicitud.getNoFolioSolicitud() + " ya fue atendida");

			edoSolicitud = "ATENDIDA";
						
		} else if (cveEdoSolicitud == EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
				.getCodigo().intValue()) {
				
			this.log.warn("La solicitud para NSS con folio "
					+ solicitud.getNoFolioSolicitud() + " esta en proceso");

			edoSolicitud = "EN_PROCESO";

		} else if (cveEdoSolicitud == EstadoSolicitudEnum.CANCELADA
				.getCodigo().intValue()) {
	
			this.log.warn("La solicitud para NSS con folio "
					+ solicitud.getNoFolioSolicitud() + " fue cancelada");
			
			edoSolicitud = "CANCELADA";
		}
		
		result.put(SOL_CHECK_STATUS, solAux);
		result.put(EDO_SOLICITUD, edoSolicitud);
		
		return result;
	}
	
	private String obtenerMotivoCancelacion(Solicitud solicitud) {
		
		this.log.debug("Se busca el motivo de cancelación de la solicitud "
				+ solicitud.getNoFolioSolicitud());
		
		String motivoCancelacion = null;
		
//		try {
//			Solicitud solAux = this.solicitudBusinessRemote.consultarFolioSinDatosTramite(solicitud);
//			if (StringUtils.isNotBlank(solAux.getObservacion())) {
//				motivoCancelacion = solAux.getObservacion();
//			}
//		} catch (SolicitudNoEncontradaException e) {
//			this.log.error(e);
//		}
		
		return motivoCancelacion;
	}
}