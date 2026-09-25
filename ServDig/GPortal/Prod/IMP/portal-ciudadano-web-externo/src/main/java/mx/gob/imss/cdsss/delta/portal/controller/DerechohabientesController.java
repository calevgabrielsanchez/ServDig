/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: portalExpediente
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.portal.expediente.web.controller
 *  @Fecha:15/02/2012
 */
package mx.gob.imss.cdsss.delta.portal.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import mx.gob.imss.cdsss.delta.portal.controller.validator.HomeValidator;
import mx.gob.imss.cdsss.delta.portal.utils.Constants;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RequisitosMinimosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.individuo.PortalCiudadanoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Ciudadano;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/derechohabientes/tramite")
public class DerechohabientesController extends AbstractController {

	private static final String DERECHOHABIENTES = "derechohabientes";
	private static final String TRAMITE = "tramite";
	private static final String ERROR_MENSAJE ="Usted cuenta con m\u00E1s de un NSS o su informaci\u00F3n esta desactualizada, por favor acuda a su Subdelegaci\u00F3n m\u00E1s cercana para aclarar su situaci\u00F3n.";
	private static final String ERROR_SIN_NSS = "La CURP capturada no cuenta con un Número de Seguridad Social.";
	private static final String ERROR_SIN_VIGENCIA = "El asegurado/pensionado se encuentra en baja, su situaci\u00F3n de vigencia no permite realizar la solicitud";
	private static final String ERROR_MODALIDADES_PATRON = "No fue posible obtener las modalidades del asegurado/pensionado.";
	private static final String ERROR_ASEGURADO_REGISTRADO = "Ya se encuentra registrado el NSS en alguna cl\u00EDnica.";
	private static final String ERROR_ASEGURADO_NO_REGISTRADO = "Para poder realizar el cambio de cl\u00EDnica es necesario que el NSS cuenta con una cl\u00EDnica asignada.";
    private static final String ERROR_ASEGURADO_SIN_CAMBIOS_CLINICA = "Usted ya realiz\u00F3 un cambio de cl\u00EDnica hace menos de 6 meses, si actualmente requiere este tr\u00E1mite, podr\u00E1 acudir a la cl\u00EDnica a la cual desea realizar el cambio.";
	private static final String ERROR_CAPTCHA = "La informaci\u00F3n del captcha no coincide, favor de intentar nuevamente.";
	private static final String ERROR_REQUERIDO = "Este campo es obligatorio.";
	private static final String ERROR_ESTUDIANTE = "No es posible realizar el tr\u00E1mite por internet para estudiantes.";
	private static final String ERROR_CONSULTA_CABEZA = "Ocurri\u00F3 un error al consultar la cabeza de grupo familiar.";
	private static final String ERROR_SIN_UMF_ANTERIOR = "No es posible realizar el tr\u00E1mite por este medio ya que no se cuenta con la cl\u00EDnica anterior, si actualmente requiere este tr\u00E1mite, podr\u00E1 acudir a la cl\u00EDnica a la cual desea realizar el cambio.";
	private static final String ERROR_MODALIDAD_NO_PERMITE_REGISTRO = "No cuenta con una relaci\u00F3n laboral que le otorgue servicio m\u00E9dico. Con lo cual no tiene acceso al registro en cl\u00EDnica.";
	private static final String ERROR_MODALIDAD_HIJOS = "La modalidad de aseguramiento con la que cuenta el asegurado no permite el registro de beneficiarios hijos.";
	private static final String ERROR_ASEGURADO_NO_REGISTRADO_HIJOS = "Para registrar un beneficiario es necesario realizar el registro del asegurado. Acude a la UMF correspondiente para realizar el tr\u00E1mite o real\u00EDzalo desde internet.";

	private static final String ERROR_NSS = "El Instituto (IMSS) no ha podido localizar tu N&uacute;mero de Seguridad Social (NSS), por favor<br> <a href=\"/gestionAsegurados-web-externo/asignacionNSS\" target=\"_blank\">cons&uacute;ltalo aqu&iacute;</a>.";
	private static final String ERROR_BAJA_LOGICA = "Estimado asegurado(a) o pensionado(a), el N&uacute;mero de seguridad social asociado a la CURP que ingres&oacute;, requiere realizar su aclaraci&oacute;n, por lo que le agradeceremos acudir a la Subdelegaci&oacute;n m&aacute;s cercana.";
	private static final String ERROR_MSG_304 = "Se encontraron inconsistencias en tus datos, por este motivo no es posible realizar el tr&aacute;mite por Internet, favor de acudir a la Unidad de Medicina Familiar que te corresponde.";
	private static final String ERROR_MSG_232 = "No se encontr&oacute; informaci&oacute;n en el IMSS con la CURP capturada";
	private static final String ERROR_MSG_102 = "No se localiz&oacute; informaci&oacute;n en RENAPO con la CURP capturada";

	@Autowired
	private PortalCiudadanoServiceBusinessRemote portalCiudadanoService;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	@Autowired
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusiness;
    @Autowired
    private SolicitudTramiteBusinessRemote solicitudTramiteBusiness;
    @Autowired
    private RequisitosMinimosServiceRemote requisitosMinimosServiceRemote;
    @Autowired
	private PersonaBusinessRemote personaBusinessRemote;

	@RequestMapping(value = "/{tramite}",method = RequestMethod.GET)
	public String login(Model model, HttpSession session,@PathVariable String tramite){
		limpiarElementosSession(session);
		return loginCommon(model, session, tramite);
	}

	private String loginCommon(Model model, HttpSession session, String tramite){
		log.debug("El tipo de tramite en el metodo loginCommon es " + tramite);
		session.setAttribute(TRAMITE, tramite);
		model.addAttribute("tramite", tramite);
		Boolean isRegistroAsegurado = tramite.equals("registro") || tramite.equals("registroD");
		Boolean isCambioClinica = tramite.equals("cambioClinica") || tramite.equals("cambioClinicaD");
		Long cveTipoTramite = isCambioClinica ? TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue() : (isRegistroAsegurado ? TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue()
				: TipoTramiteEnum.REGISTRO_HIJOS.getCodigo().longValue());
		

		session.setAttribute(Constants.KEY_TIPO_TRAMITE, cveTipoTramite);
		
        Fisica fisica = new Fisica();

		model.addAttribute("fisica", fisica);
		return DERECHOHABIENTES;
	}

	@RequestMapping(value = "/validar", method = RequestMethod.POST)
	public String consultaDatosBasicos(@ModelAttribute Fisica fisica, BindingResult result, @RequestParam("captcha") String captcha,
			@RequestParam("tramite") String tramite, boolean terminos, HttpSession session, Model model) {

		log.debug("el tipo de tramites es: " + tramite);
		String correoCapturado = fisica.getCorreoElectronico().getCorreo().toLowerCase();
		fisica.getCorreoElectronico().setCorreo(correoCapturado);
		String correoConfirmacion = fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase();
		fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);

		new HomeValidator().validateConNSS(fisica, true, result);
		
		if (StringUtils.isBlank(captcha)) {
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral", ERROR_REQUERIDO);
			result.addError(fieldError);
		}
		if (result.hasErrors()) {
			this.log.warn("Errores de captura");
			return DERECHOHABIENTES;
		}
		if (!captcha.equals(session.getAttribute("captcha"))) {
			this.log.error("Captcha no v?lido!!!");
			FieldError fieldError = new FieldError("fisica", "errorFormGeneral",ERROR_CAPTCHA);
			result.addError(fieldError);
			return DERECHOHABIENTES;
		}

		Ciudadano ciudadano = new Ciudadano();
		Fisica fisicaIMSS;
		String mensajeError = null;
		boolean curpCoincide = false;
		GrupoFamiliar aseguradoPensionado = null;
		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;
		AsignacionNSS asignacionNSS;
		boolean isRegistroAsegurado = tramite.equals("registro") || tramite.equals("registroD");
		boolean isCambioClinica = tramite.equals("cambioClinica") || tramite.equals("cambioClinicaD");
		Long cveTipoTramite = isCambioClinica ? TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue() : (isRegistroAsegurado ?
						TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue() : TipoTramiteEnum.REGISTRO_HIJOS.getCodigo().longValue());

		try {
			GrupoFamiliar grupoFamiliar = grupoFamiliarService.getGrupoFamiliar(fisica.getNss(), false);
			asignacionNSS = grupoFamiliar.getAsignacionNSS();
		}catch (Exception e) {
			if (e instanceof DerechohabientesBusinessException) {
				DerechohabientesBusinessException dex = (DerechohabientesBusinessException) e;
				log.error("Mensaje: " + dex.getMessage() + " Situacion: " + dex.getSituacion());
				return this.procesarError(result, ERROR_NSS, model);
			} else if (e instanceof AsignacionNSSNoLocalizadoException) {
				AsignacionNSSNoLocalizadoException aex = (AsignacionNSSNoLocalizadoException) e;
				if (aex.getCodigo() == 1) { // Si el codigo de excepcion es igual a 1 el NSS se encuentra en baja
					log.error("El NSS: " + fisica.getNss() + " presenta fecha de baja");
					return this.procesarError(result, ERROR_BAJA_LOGICA, model);
				} else {
					log.error("Ocurrio un error al consultar el asegurado por NSS: " + fisica.getNss() + " { Mensaje: " + aex.getMessage() + " Situacion: " + aex.getSituacion() + " }");
					return this.procesarError(result, ERROR_NSS, model);
				}
			} else {
				log.error("Ocurrio un error inesperado, existe una inconsistencia de datos no fue posible consultar el NSS: " + fisica.getNss(), e);
				return this.procesarError(result, ERROR_NSS, model);
			}
		}

		//Se compara la CURP capturada contra la CURP de BDTU
		if (fisica.getCurp().equals(asignacionNSS.getCurp())) {
			log.info("La CURP capturada coincide con la CURP encontrada en BDTU asociada al NSS: " + fisica.getNss());
			curpCoincide = true;
			fisicaIMSS = asignacionNSS;
		} else {
			//Si las CURP no coinciden se consutla en RENAPO
			log.info("La CURP capturada es diferente a la CURP encontrada en BDTU, se procede a consultar RENAPO por la CURP capturada");
			Fisica fisicaRenapo;
			try {
				fisicaRenapo = personaBusinessRemote.buscarPersonaFisicaPorCurpEnRenapo(fisica.getCurp());
			} catch (ClienteWebserviceRenapoCurpException e){
				log.error(e.getMessage(), e);
				return this.procesarError(result, e.getMessage(), model);
			}

			//Se valida que la CURP vigente o alguna de las CURPs historicas devueltas por RENAPO coincida con la CURP capturada
			if (fisicaRenapo != null){
				log.info("Se valida que la CURP vigente o alguna de las CURPs historicas devueltas por RENAPO coincida con la CURP capturada");
				if (fisica.getCurp().equals(fisicaRenapo.getCurp())) {
					curpCoincide = true;
				} else {
					for (String curp : fisicaRenapo.getCurpsHistoricas()) {
						if (fisica.getCurp().equals(curp)) {
							curpCoincide = true;
							break;
						}
					}
				}
			} else {
				return this.procesarError(result, ERROR_MSG_102, model);
			}
			fisicaIMSS = fisicaRenapo;
		}

		log.info("CURP: " + fisica.getCurp() + " Coincide: " + curpCoincide);

		try {

			portalCiudadanoService.validarInicioCurpCorreo(fisica.getCurp(), fisica.getCorreoElectronico().getCorreo(), terminos);

			if (curpCoincide) {
				ciudadano.setCurp(fisicaIMSS.getCurp());
				ciudadano.setCveIdFisica(fisicaIMSS.getCveFisica());
				ciudadano.setCveIdPersona(fisicaIMSS.getIdPersona());
				ciudadano.setRfc(fisicaIMSS.getRfc());
				ciudadano.setNombreCompleto(fisicaIMSS.getNombreCompleto());
				ciudadano.setCveIdAsingacionNSS(asignacionNSS.getIdAsignacionNSS());
				ciudadano.setStrNss(asignacionNSS.getNss());

				try {
					cabezaGrupoFamiliar = grupoFamiliarService.cabezaGrupoFamiliar(asignacionNSS.getIdAsignacionNSS());

					if (cabezaGrupoFamiliar.getEsEstudiante()) {
						return this.procesarError(result, ERROR_ESTUDIANTE, model);
					}

					//se valida la situacion del asegurado o beneficiario para cambio de clinica o registro de derechohabientes su vigencia
					if (isRegistroAsegurado
							&& cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.FALLECIDO
							.getId()) {
						return this.procesarError(result, ERROR_SIN_VIGENCIA, model);
					} else if (cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA.getId()
							|| cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.FALLECIDO
							.getId()) {
						return this.procesarError(result, ERROR_SIN_VIGENCIA, model);
					}

				} catch (DerechohabientesBusinessException e) {
					e.printStackTrace();
					mensajeError = ERROR_CONSULTA_CABEZA;
				} catch (Exception e) {
					e.printStackTrace();
					mensajeError = ERROR_CONSULTA_CABEZA;
				}

				if (mensajeError != null) {
					return this.procesarError(result, mensajeError, model);
				}

				//obtenemos los datos de vigencias
				try {
					aseguradoPensionado = grupoFamiliarService.getCabezaGrupaFamilarRegistrada(asignacionNSS, cabezaGrupoFamiliar);
				} catch (DerechohabientesBusinessException e) {
					e.printStackTrace();
					mensajeError = Constants.ERROR_CONSULTA_INTEGRANTE_ASEGURADO + e.getSituacion();
				} catch (Exception e) {
					e.printStackTrace();
					mensajeError = Constants.ERROR_CONSULTA_INTEGRANTE_ASEGURADO;
				}

				boolean aseguradoRegistrado = aseguradoPensionado != null && aseguradoPensionado.getIndRegistrado() == 1;
				//checamos si el tramite es de registro de asegurado y si ya esta registrado mandamos un error
				if (isRegistroAsegurado && aseguradoRegistrado) {
					mensajeError = ERROR_ASEGURADO_REGISTRADO;
				} else if (!isRegistroAsegurado && !aseguradoRegistrado) {
					//si es otro tramite deberia de ser de cambio de clinica o registro de hijos y ya deberia estar registrado
					mensajeError = isCambioClinica ? ERROR_ASEGURADO_NO_REGISTRADO : ERROR_ASEGURADO_NO_REGISTRADO_HIJOS;
				} else if (isCambioClinica) {//aqui ya pasamos las validaciones de si esta o no está registrado dependiendo del tramite
					//verificamos que el asegurado tenga una UMF anterior
					if (aseguradoPensionado.getMedicoEnTurno() == null || aseguradoPensionado.getMedicoEnTurno().getUnidadMedicaFamiliar() == null
							|| aseguradoPensionado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF() == null) {
						mensajeError = ERROR_SIN_UMF_ANTERIOR;
					} else {
						// -----------------------------------------------------------------------------
						// No puede realizar cambio de clinica mas de dos veces en el anio en curso
						// -----------------------------------------------------------------------------
						List<Tramite> tramitesCambiosDeClinica = this
								.obtenerTramitesCambioClinica(aseguradoPensionado.getDerechohabiente().getIdPersona());
						if ((tramitesCambiosDeClinica != null) && (tramitesCambiosDeClinica.size() >= 1)) {
							mensajeError = ERROR_ASEGURADO_SIN_CAMBIOS_CLINICA;
						}
					}
				}

				/*
				 * En caso de que no haya error obtendremoa las modalidades del asegurado o pensionado en su caso
				 */
				if (mensajeError == null) {
					List<Long> idsModalidades = null;
					try {
						//se ponen en session los patrones y las modalidades
						idsModalidades = this.getModalidadesActivas(asignacionNSS, cabezaGrupoFamiliar, session);
					} catch (Exception e) {
						e.printStackTrace();
						mensajeError = ERROR_MODALIDADES_PATRON;
					}

					/*
					 * En caso de que sea el registro de asegurado se valida que la modalidad permita el registro
					 * de lo contrario de le mandara un mensaje de error
					 */
					log.debug("es registro asegurado?: " + isRegistroAsegurado);
					if (mensajeError == null && !cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco()
							.equals(ParentescoEnum.PENSIONADO.getId()) && !isCambioClinica && !isCambioClinica && isRegistroAsegurado) {
						Long idTramite = TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo().longValue();
						Map<String, Object> resultado = requisitosMinimosServiceRemote
								.tramitePermitidoParaAseguradoPensionado(cabezaGrupoFamiliar, idTramite, false, idsModalidades);
						Boolean correcto = (Boolean) resultado.get("correcto");

						if (!correcto) {
							mensajeError = ERROR_MODALIDAD_NO_PERMITE_REGISTRO;
						}
					}
				}

				//si existe algun error mandamos a la pantalla de logueo
				if (mensajeError != null) {
					return this.procesarError(result, mensajeError, model);
				}

				try {
					// Asociar correo-persona a Medios de Contacto si no existe la relacion.
					mediosContactoServiceBusiness.asociarCorreoMedioContactoPersona(ciudadano.getCveIdPersona(), correoCapturado);
				} catch (Exception e) {
					log.error("Ocurrio un error al asociar el correo a la persona");
				}
			} else {
				return this.procesarError(result, ERROR_MSG_232, model);
			}

			session.setAttribute(Constants.KEY_ASIGNACION_NSS, asignacionNSS);
			session.setAttribute(Constants.KEY_CABEZA_GRUPO, cabezaGrupoFamiliar);
			session.setAttribute(Constants.KEY_DATOS_ASEGURADO, aseguradoPensionado);
			session.setAttribute(Constants.KEY_CORREO_CIUDADANO, fisica.getCorreoElectronico().getCorreo());
			session.setAttribute(Constants.KEY_CIUDADANO_SESSION, ciudadano);
			session.setAttribute(Constants.KEY_TIPO_TRAMITE, cveTipoTramite);

			tramite = isCambioClinica ? tramite : "registro";
			session.setAttribute(TRAMITE, tramite);

			return "redirect:/derechohabientes/tramite/" + tramite + "/inicio";
		} catch (PortalCiudadanoException e) {
			this.log.error("PortalCiudadanoException" + e.getMessage());
			return this.procesarError(result, e.getMessage(), model);
		} catch (DerechohabientesBusinessException e) {
			this.log.error("DerechohabientesBusinessException" + e.getMessage());
			return this.procesarError(result, ERROR_MSG_304, model);
		}

	}
	
	/**
	 * Obtiene la lista de los ids de modalidades activa
	 * @param session
	 * @return
	 */
	private List<Long> getModalidadesActivas(AsignacionNSS nss, CabezaGrupoFamiliar cabeza,HttpSession session) {
		List<Long> idsModalidades = new ArrayList<Long>();
		List<Modalidad> modalidades = this.obtenerModalidadesPatrones(nss, cabeza,session);
		
		session.setAttribute(Constants.KEY_MODALIDADES_ACTIVAS, modalidades);
		//verificamos si las modalidades vienen distintas de nulas y que no sean vacias
		if(modalidades == null || !modalidades.isEmpty()) {
			//si no son vacias agregamos los ids en la lista
			for(Modalidad mod: modalidades) {
				idsModalidades.add(mod.getIdModalidad());
			}
		}
		
		session.setAttribute(Constants.KEY_IDS_MODALIDADES, idsModalidades);
		
		return idsModalidades;
	}
	
	/**
	 * Obtiene las modalidades activas del patron
	 * @param session
	 * @return
	 */
	private List<Modalidad> obtenerModalidadesPatrones(AsignacionNSS asignacionNSS,CabezaGrupoFamiliar cabeza,HttpSession session) {
		//obtenemos los patrones de la session
		List<SujetoObligado> sujetos = null;
		
		//Se obtiene a los patrones
		try{
			sujetos = grupoFamiliarService.getPatronesAsegurado(asignacionNSS);	
		}catch(DerechohabientesBusinessException e){
			log.error("Ocurrio un error al obtener a los patrones del asegurado", e);
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//creamos la lista de modalidades
		List<Modalidad> modalidades = new ArrayList<Modalidad>();
		//si los patrones no son nulos, añadiremos sus modalidades
		if(sujetos != null && !sujetos.isEmpty()) {
			for(SujetoObligado sujeto: sujetos) {
				modalidades.add(sujeto.getModalidad());
			}
		} else {
			//si los patrones son nulos o vacios verificamos si la cabeza de grupo familiar tiene patron
			if(cabeza.getPatronSujetoObligado() != null) {
				//si tiene patron anadimos la modalidad dle patron del ultimo movimiento
				modalidades.add(cabeza.getPatronSujetoObligado().getModalidad());
			}
		}
		
		return modalidades;
	}
	
	private String procesarError(BindingResult result, String mensaje, Model model) {
		//se envia a la primer pantallas
		Fisica fisica = new Fisica();
		fisica.setErrorFormGeneral(mensaje);
		model.addAttribute("fisica", fisica);
		return DERECHOHABIENTES;
	}
	
	private void limpiarElementosSession(HttpSession session) {
		session.removeAttribute(Constants.KEY_ASIGNACION_NSS);
		session.removeAttribute(Constants.KEY_CABEZA_GRUPO);
		session.removeAttribute(Constants.KEY_CIUDADANO_SESSION);
		session.removeAttribute(Constants.KEY_CORREO_CIUDADANO);
		session.removeAttribute(Constants.KEY_DATOS_ASEGURADO);
		session.removeAttribute(Constants.KEY_DATOS_DOM_UMF);
		session.removeAttribute(Constants.KEY_FOLIO_SOLICITUD);
		session.removeAttribute(Constants.KEY_PATRON_IMSS);
		session.removeAttribute(Constants.KEY_PATRONES_ASEGURADO);
		session.removeAttribute(Constants.KEY_SOLICITUD);
		session.removeAttribute(Constants.KEY_TRAMITE);
		session.removeAttribute(Constants.KEY_ACUSE_TRAMITE);
		session.removeAttribute(Constants.KEY_VIEW_TERMINOS_ACTUALIZA_CURP);
		
	}

    /**
     * Obtiene los tr&aacute;mites
     *
     * @param idPersona Identificador de la persona de la que se recuperaran los trámites
     * @return List<Tramite> Lista de trámites de la persona
     */
	private List<Tramite> obtenerTramitesCambioClinica(Long idPersona){

		try{
			// ---------------------------------------------
			// Inicio dia hace medio año
			// ---------------------------------------------
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -6);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			Long[] origenes = {OrigenSolicitudEnum.PORTAL_CIUDADANO.getId(),OrigenSolicitudEnum.MOVILES.getId()};

			return this.solicitudTramiteBusiness.obtenerTramitesCerrados(
					Arrays.asList(origenes), TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE.getValor(), idPersona, cal.getTime(), TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;

	}
}
