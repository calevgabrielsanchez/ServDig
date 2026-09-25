package mx.gob.imss.ctirss.delta.gestion.beneficio.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.PersonaNoValidaBeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesModificacionException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceImssRissException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.web.controller.validator.RissValidator;
import mx.gob.imss.ctirss.delta.gestion.beneficio.web.formModel.DatosEntradaRiss;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/altaPublica/riss")
public class AltaBeneficioRissController extends AbstractController {

	@Autowired
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	@Autowired
	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusiness;
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	private static final String KEY_VALIDAR_PATRON 		= "/gestionBeneficio-web-publico/altaPublica/riss/validar/patron";
	
	private static final String KEY_ATRIBUTE_FISICA 	= "fisicaToSession";
	private static final String KEY_ATRIBUTE_PATRONES 	= "sujetosObligadosToSession";
	private static final String EDO_SOLICITUD 			= "EDO_SOLICITUD";	
	private static final String RISS_RFC 				= "rissRfc";
	private static final String RISS_NSS 				= "rissNss";
	private static final String MSG_RPS_PENDIENTES 		= "msgRPSPendientes";
	private static final String KEY_FISICA 				= "fisica";
	private static final String DATOS_ENTRADA_RISS		= "datosEntradaRiss";
	private static final String MSG_ERROR 				= "msgError";
	private static final String TIENE_PATRONES 			= "tienePatrones";
	private static final String KEY_ERROR 				= "error";
	private static final String RFC_OBLIGATORIO_VALUE	= "rfcObligatorioValue";
	private static final String SOLIC_RISS 				= "SOLIC_RISS";
	private static final String BENEF_RISS 				= "BENEF_RISS";
	private static final String MSG_MOTIVO_RECHAZO		= "msgMotivoRechazo";
	private static final String FOLIO_SOLICITUD			= "folioSolicitud";
	private static final String SOLICITUD 				= "solicitud";
	private static final String ID_SOLICITUD 			= "idSolicitud";
	private static final String KEY_MENSAJE 			= "mensaje";
	private static final String SOLICITUD_EN_RPOCESO	= "solicitudEnProceso";
	private static final String SOLICITUD_REGISTRADA	= "solicitudRegistrada";
	private static final String FOLIO_SOLICITUD_REGISTRADA 	= "folioSolicitudRegistrada";
	private static final String ID_SOLICITUD_REGISTRADA = "idSolicitudRegistrada";
	private static final String KEY_USUARIO_EXTERNO		= "USUARIO EXTERNO";
	//MENSAJES
	private static final String MSG_SOLICITUD_CANCELADA		= "La solicitud fue cancelada correctamente.";
	private static final String MSG_CAUSA_CANCELACION		= "SOLICITUD CANCELADA POR ";
	private static final String MSG_SOLICITUD_EN_PROCESO	= "Ya cuenta con una solicitud EN PROCESO.";
	private static final String MSG_SOLICITUD_REGISTRADA	= "Ya cuenta con una solicitud REGISTRADA.";
	private static final String MSG_ERROR_CANCELAR			= "Hubo un error al cancelar la solicitud: ";
	private static final String MSG_ERROR_INESPERADO		= "Ha ocurrido un error inesperado.";
	private static final String MSG_PERSONA_NO_ENCONTRADA	= "Persona no encontrada.";
	private static final String MSG_ERROR_SAT_IMSS			= "Los datos encontrados en el Instituto no coinciden " +
															"con los registrados en el SAT.";
	private static final String MSG_RFC_NO_LOCALIZADO_SAT	= "El RFC capturado no fue localizado en el SAT.";
	private static final String MSG_RFC_SIN_RPS				= "No se encontraron patrones asociados al RFC ";
	//PANTALLAS
	private static final String INICIAR_RISS 						= "iniciarRiss";
	private static final String CONFIRMAR_DATOS_RISS				= "confirmarDatosRiss";
	private static final String CONFIRMAR_DATOS_ADICIONALES_RISS 	= "confirmarDatosAdicionalesRiss";
	private static final String RESULTADO_VALIDACION_RISS 			= "resultadoValidacionRiss";
	private static final String CONCLUIR_RISS 						= "concluirRiss";
	
	
	@RequestMapping(value = "/iniciar", method = RequestMethod.GET)
	public String initAltaRiss(Model model, HttpSession session,
			HttpServletRequest request) {		
		model.addAttribute(DATOS_ENTRADA_RISS, new DatosEntradaRiss());	
		this.setFechaSistema(session);
		return INICIAR_RISS;
	}

	@RequestMapping(value = "/verificar/datos", method = RequestMethod.POST)
	public String verificarDatos(
			@ModelAttribute DatosEntradaRiss datosEntrada,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {
		
		Fisica fisica = null;
		String msgError = null;
		String view = null;		
		RissValidator validator = new RissValidator();
		validator.setTipoValidacion(1);
		validator.validate(datosEntrada, result);
		
		if (result.hasErrors()) {
			view = INICIAR_RISS;
		} else {
			if (datosEntrada.getOpcRISS().equals(DatosEntradaRiss.opcRissRfc)) {
				datosEntrada.setRfc(datosEntrada.getRfc().toUpperCase().trim());				
				datosEntrada.setNss(null);
				datosEntrada.setAccion(KEY_VALIDAR_PATRON);
				// Buscar patrones relacionados al RFC
				List<SujetoObligado> sujObligados = null;								
				try {
					sujObligados = this.beneficioRissServiceBusiness
						.obtenerSujetosObligadosParaBeneficio(datosEntrada.getRfc());	
					fisica = new Fisica();
					fisica.setRfc(datosEntrada.getRfc());
					
					if (CollectionUtils.isEmpty(sujObligados)) {
						msgError = MSG_RFC_SIN_RPS + datosEntrada.getRfc()+".";
					}else{		
						//Validar si cuenta con RPs Pendientes para notificar.
						String msgRPSPendientes = beneficioRissServiceBusiness
							.indicarRPsPendientes(fisica, OrigenSolicitudEnum.VENTANILLA_UNICA);
						request.setAttribute(MSG_RPS_PENDIENTES, msgRPSPendientes);
					}
					request.setAttribute(RISS_RFC, true);
					datosEntrada.setPatrones(sujObligados);					
				} catch (PersonaNoValidaBeneficioRissException e) {
					this.log.error(e);
					msgError = e.getMessage();
				}
			} else {
				datosEntrada.setNss(datosEntrada.getNss().toUpperCase().trim());
				datosEntrada.setRfc(null);
				// Obtener datos persona				
				try {
					fisica = this.personaFisicaServiceBusiness
							.localizarPersonaFisicaPorNss(datosEntrada.getNss());
					fisica.setNss(personaBusiness.obtenerNssVigentePersona(fisica.getIdPersona()));
					session.setAttribute(KEY_ATRIBUTE_FISICA, fisica);
				} catch (PersonasNoLocalizadasException e) {
					this.log.error(e);
					msgError = e.getMessage();
				} catch (NssRelacionadoVariasPersonasException e) {
					this.log.error(e);
					msgError = e.getMessage();
				} catch (PersonaConVariosNSSException e) {
					this.log.error(e);
					msgError = "La persona cuenta con m\u00E1s de un NSS";
				} catch (PersonaSinNSSException e) {
					this.log.error(e);
					msgError = e.getMessage();
				}								
				request.setAttribute(RISS_NSS, true);
				request.setAttribute(KEY_FISICA, fisica);
			}			
			model.addAttribute(DATOS_ENTRADA_RISS, datosEntrada);						
			view = CONFIRMAR_DATOS_RISS;
		}		
		if (StringUtils.isNotBlank(msgError)) {
			request.setAttribute(MSG_ERROR, msgError);
		}
		
		return view;
	}
	
	@RequestMapping(value = "/validarNRPsAsociados/{rfc}/{validaRfc}", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> validarNRPsAsociados(@PathVariable String rfc, @PathVariable String validaRfc,
			DefaultMessageContext messageContext, HttpSession session,
			HttpServletRequest request) {
		Map<String, Object> respuesta = new HashMap<String, Object>();		
		boolean result = false;
		String error = "";
		//Validar si tiene RFC y si tiene Registros Patronales asociados a dicho RFC
		if(rfcNoVacio(rfc)){
			try {
				//Valida que el RFC capturado corresponde con el de la persona.
				if(validaRfc.equals("true")){
					Fisica fisica = (Fisica) session.getAttribute(KEY_ATRIBUTE_FISICA);
					validarRfcEnSat(fisica, rfc);
				}
				
				List<SujetoObligado> sujObligados = this.beneficioRissServiceBusiness
					.obtenerSujetosObligadosParaBeneficio(rfc.toUpperCase().trim());				
				if (!CollectionUtils.isEmpty(sujObligados)) {
					result = true;
				}
				respuesta.put(TIENE_PATRONES, result);
				session.setAttribute(KEY_ATRIBUTE_PATRONES, sujObligados);
			} catch (AbstractException e) {
				e.printStackTrace();
				error = e.getMessage();
			}
		}
		respuesta.put(KEY_ERROR, error);
		return respuesta;
	}
	   
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/datosAdicionales/fisica", method = RequestMethod.POST)
	public String validarDatosAdicionalesPersonaFisica(
			@ModelAttribute DatosEntradaRiss datosEntrada,
			BindingResult result, Model model,
			@RequestParam("iPer") Long idPersona,
			@RequestParam(required = false) Boolean rfcObligatorio,
			HttpSession session,
			HttpServletRequest request) {
		
		String view = CONFIRMAR_DATOS_ADICIONALES_RISS;
		Fisica fisica = (Fisica) session.getAttribute(KEY_ATRIBUTE_FISICA);
		List<SujetoObligado> sujObligados=null;
		if (rfcObligatorio != null && rfcObligatorio == true) {
			if (rfcNoVacio(datosEntrada.getRfc())) {
				datosEntrada.setRfc(datosEntrada.getRfc().toUpperCase().trim());
			}					
			RissValidator validator = new RissValidator();
			validator.setTipoValidacion(2);
			validator.validate(datosEntrada, result);
			if (result.hasErrors()) {
				fisica.setRfc(datosEntrada.getRfc());				
				request.setAttribute(RISS_NSS, true);							
				view = CONFIRMAR_DATOS_RISS;				
			}else{
				sujObligados = (List<SujetoObligado>) session
					.getAttribute(KEY_ATRIBUTE_PATRONES);
			}
			request.setAttribute(RFC_OBLIGATORIO_VALUE, true);		
		}else{
			request.setAttribute(RFC_OBLIGATORIO_VALUE, false);	
			sujObligados = (List<SujetoObligado>) session
				.getAttribute(KEY_ATRIBUTE_PATRONES);
		}
		datosEntrada.setPatrones(sujObligados);
		model.addAttribute(DATOS_ENTRADA_RISS, datosEntrada);
		request.setAttribute(KEY_FISICA, fisica);	
		
		if((!CollectionUtils.isEmpty(datosEntrada.getPatrones()))
			&& rfcNoVacio(fisica.getRfc())){
			//Validar si cuenta con RPs Pendientes para notificar.
			String msgRPSPendientes = beneficioRissServiceBusiness
				.indicarRPsPendientes(fisica, OrigenSolicitudEnum.VENTANILLA_UNICA);
			request.setAttribute(MSG_RPS_PENDIENTES, msgRPSPendientes);
		}		
		
		return view;
	}
	
	@RequestMapping(value = "/validar/fisica", method = RequestMethod.POST)
	public String validarAltaRissPersonaFisica(
			@ModelAttribute DatosEntradaRiss datosEntrada,
			BindingResult result, Model model,
			@RequestParam("iPer") Long idPersona,
			@RequestParam(required = false) Boolean rfcObligatorio,
			HttpSession session,
			HttpServletRequest request) {
		
		String view = CONFIRMAR_DATOS_RISS;
		String msgError = null;		
		Fisica fisica = null;		
		if (rfcObligatorio != null && rfcObligatorio == true) {
			if (rfcNoVacio(datosEntrada.getRfc())) {
				datosEntrada.setRfc(datosEntrada.getRfc().toUpperCase().trim());
			}
			RissValidator validator = new RissValidator();
			validator.setTipoValidacion(2);
			validator.validate(datosEntrada, result);
		}
		// Obtener persona por ID
		fisica = this.personaBusiness.getPersonaFisica(idPersona);		
		if (result.hasErrors()) {						
			request.setAttribute(RISS_NSS, true);
			request.setAttribute(KEY_FISICA, fisica);						
		} else if (fisica != null) {
			Fisica fisicaConsulta = new Fisica();
			fisicaConsulta.setIdPersona(fisica.getIdPersona());
			if (rfcObligatorio != null && rfcObligatorio == true) {
				fisicaConsulta.setRfc(datosEntrada.getRfc());
			}else{
				fisicaConsulta.setRfc(fisica.getRfc());
			}		
			Solicitud solicitudProceso = beneficioRissServiceBusiness.
				validarSolicitudRissEnProceso(fisicaConsulta, OrigenSolicitudEnum.VENTANILLA_UNICA);
			
			if(solicitudProceso==null){
				//NO Existe solicitud en proceso o registrada
				inicializarVariblesSolicitudEnProceso(request);
				Solicitud solicitud = null;
				try {
					if (rfcObligatorio != null && rfcObligatorio == true) {
						validarRfcEnSat(fisica, datosEntrada.getRfc());
					}
					String rfcPersona = ((fisica.getRfc() != null && StringUtils.isNotBlank(fisica.getRfc())) 
						? fisica.getRfc() : datosEntrada.getRfc());
					personaFisicaServiceBusiness.complementarDatosPersonas(rfcPersona, fisica.getIdPersona());										
					Usuario usuario = getUsuarioExterno();	
					Beneficio beneficio = beneficioRissServiceBusiness
						.obtenerPersonaBeneficioVentanilla(fisica, datosEntrada.getPatrones());
					solicitud = beneficioRissServiceBusiness.crearSolicitudRiss(
						beneficio, usuario, OrigenSolicitudEnum.VENTANILLA_UNICA.getId());
					beneficio = beneficioRissServiceBusiness.validarSolicitudRiss(solicitud, beneficio);
					
					request.setAttribute(SOLIC_RISS, solicitud);
					request.setAttribute(BENEF_RISS, beneficio);									
					view = RESULTADO_VALIDACION_RISS;
				} catch (ClienteWebserviceSatRfcException e) {
					this.log.error(e);
					datosEntrada.setErrorFormGeneral(e.getMessage());
					request.setAttribute(RISS_NSS, true);
					view = CONFIRMAR_DATOS_RISS;
				} catch (ErrorComparacionDatosSATException e) {
					this.log.error(e);
					datosEntrada.setErrorFormGeneral(e.getMessage());				
					request.setAttribute(RISS_NSS, true);
					view = CONFIRMAR_DATOS_RISS;
				} catch (PersonasNoLocalizadasException e) {
					this.log.warn(e);
					datosEntrada.setErrorFormGeneral(e.getMessage());
					request.setAttribute(RISS_NSS, true);				
					view = CONFIRMAR_DATOS_RISS;
				} catch (SolicitudNoValidaException e) {
					this.log.error(e);
					msgError = e.getMessage();
					request.setAttribute(RISS_NSS, true);
					view = CONFIRMAR_DATOS_RISS;
				} catch (PersonaNoValidaBeneficioRissException e) {
					this.log.warn(e);
					datosEntrada.setErrorFormGeneral(e.getMessage());
					request.setAttribute(RISS_NSS, true);				
					view = CONFIRMAR_DATOS_RISS;				
				} catch (DatosInsuficientesModificacionException e) {
					this.log.error(e);
					msgError = e.getMessage();				
					request.setAttribute(RISS_NSS, true);
					view = CONFIRMAR_DATOS_RISS;	
								
				} catch (BeneficioRissException e) {
					this.log.warn(e);
					String msgMotivoRechazo = null;					
					if (StringUtils.isBlank(e.getMessage())) {
						msgMotivoRechazo = MSG_ERROR_INESPERADO;
					} else {
						msgMotivoRechazo = e.getMessage();
					}					
					try {
						beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
					} catch (AbstractException ae) {
						this.log.error(ae);
						ae.printStackTrace();
					}			
					request.setAttribute(MSG_MOTIVO_RECHAZO, msgMotivoRechazo);
					view = RESULTADO_VALIDACION_RISS;					
				} catch (ClienteWebserviceImssRissException e) {
					this.log.warn(e);
					String msgMotivoRechazo = null;					
					if (StringUtils.isBlank(e.getMessage())) {
						msgMotivoRechazo = MSG_ERROR_INESPERADO;
					} else {
						msgMotivoRechazo = e.getMessage();
					}					
					try {
						beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
					} catch (AbstractException ae) {
						this.log.error(ae);
						ae.printStackTrace();
					}			
					request.setAttribute(MSG_MOTIVO_RECHAZO, msgMotivoRechazo);
					view = RESULTADO_VALIDACION_RISS;					
				}	
			}else{
				//Existe solicitud en proceso o registrada
				validarSolicitudEnProceso(request, solicitudProceso);
				request.setAttribute(RISS_NSS, true);				
				view = CONFIRMAR_DATOS_RISS;
			}
		} else {
			msgError = MSG_PERSONA_NO_ENCONTRADA;
			inicializarVariblesSolicitudEnProceso(request);
		}		
		if (StringUtils.isNotBlank(msgError)) {
			request.setAttribute(MSG_ERROR, msgError);
		}
		
		request.setAttribute(KEY_FISICA, fisica);		
		return view;
	}
	
	@RequestMapping(value = "/validar/patron", method = RequestMethod.POST)
	public String validarAltaRissPatron(
			@ModelAttribute DatosEntradaRiss datosEntrada,
			BindingResult result, Model model, HttpSession session,
			HttpServletRequest request) {
		
		List<SujetoObligado> sujetosObligados = datosEntrada.getPatrones();				
		String view = null;
		String msgError = null;
		Usuario usuario = getUsuarioExterno();				
		Fisica fisica = new Fisica();
		fisica.setRfc(datosEntrada.getRfc().toUpperCase().trim());
		
		Solicitud solicitudProceso = beneficioRissServiceBusiness.
			validarSolicitudRissEnProceso(fisica, OrigenSolicitudEnum.VENTANILLA_UNICA);
		if(solicitudProceso==null){
			//NO Existe solicitud en proceso o registrada
			inicializarVariblesSolicitudEnProceso(request);
			Solicitud solicitud = null;
			try {				
				Beneficio beneficio = beneficioRissServiceBusiness
					.obtenerPersonaBeneficioVentanilla(fisica, sujetosObligados);
				solicitud = beneficioRissServiceBusiness.crearSolicitudRiss(
						beneficio, usuario, OrigenSolicitudEnum.VENTANILLA_UNICA.getId());				
				beneficio = beneficioRissServiceBusiness.validarSolicitudRiss(solicitud, beneficio);
				
				request.setAttribute(SOLIC_RISS, solicitud);
				request.setAttribute(BENEF_RISS, beneficio);
				view = RESULTADO_VALIDACION_RISS;				
			} catch (SolicitudNoValidaException e) {
				this.log.error(e);
				msgError = e.getMessage();				
				view = CONFIRMAR_DATOS_RISS;
			} catch (PersonaNoValidaBeneficioRissException e) {
				this.log.error(e);
				msgError = e.getMessage();				
				view = CONFIRMAR_DATOS_RISS;
				
			} catch (BeneficioRissException e) {
				this.log.warn(e);
				String msgMotivoRechazo = null;				
				if (StringUtils.isBlank(e.getMessage())) {
					msgMotivoRechazo = MSG_ERROR_INESPERADO;
				} else {
					msgMotivoRechazo = e.getMessage();
				}				
				try {
					beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
				} catch (AbstractException ae) {
					this.log.error(ae);
					ae.printStackTrace();
				}				
				request.setAttribute(MSG_MOTIVO_RECHAZO, msgMotivoRechazo);			
				view = RESULTADO_VALIDACION_RISS;
			} catch (ClienteWebserviceImssRissException e) {
				this.log.warn(e);
				String msgMotivoRechazo = null;				
				if (StringUtils.isBlank(e.getMessage())) {
					msgMotivoRechazo = MSG_ERROR_INESPERADO;
				} else {
					msgMotivoRechazo = e.getMessage();
				}				
				try {
					beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
				} catch (AbstractException ae) {
					this.log.error(ae);
					ae.printStackTrace();
				}				
				request.setAttribute(MSG_MOTIVO_RECHAZO, msgMotivoRechazo);			
				view = RESULTADO_VALIDACION_RISS;
			}
			
			if (StringUtils.isNotBlank(msgError)) {
				request.setAttribute(MSG_ERROR, msgError);
			}
		}else{
			//Existe solicitud en proceso o registrada
			validarSolicitudEnProceso(request, solicitudProceso);
			view = CONFIRMAR_DATOS_RISS;
		}
		
		return view;
	}
	
	@RequestMapping(value = "/concluir", method = {RequestMethod.GET, RequestMethod.POST })
	public String concluirSolicitud(@RequestParam Long idSolicitud,
			HttpSession session, HttpServletRequest request) {		
		try {
			Solicitud solicitud = this.solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);
			if(EstadoSolicitudEnum.REGISTRADA.getCodigo().intValue()==solicitud
					.getEstadoSolicitud().getIdEstadoSolicitud().intValue()){
				beneficioRissServiceBusiness.encolarSolicitudRiss(idSolicitud,null);
				solicitud = this.solicitudServiceBusiness.consultarSolicitudPorId(idSolicitud);				
			}
			getDocumentoPorTipo(request, solicitud);
			getClaveEstadoSolicitud(request, solicitud);
			request.setAttribute(FOLIO_SOLICITUD, solicitud.getNoFolioSolicitud());
			request.setAttribute(SOLICITUD, solicitud);
			
		} catch (AbstractException e) {
			this.log.error(e);
		}		
		request.setAttribute(ID_SOLICITUD, idSolicitud);		
		return CONCLUIR_RISS;
	}
	
	@RequestMapping(value = "/cancelar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitud(
			@RequestParam String idSolicitud, HttpSession session) {
		
		Usuario usuario = getUsuarioExterno();		
		String causaCancelacion = MSG_CAUSA_CANCELACION + usuario.getUsuario();		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Solicitud solicitud = this.beneficioRissServiceBusiness
				.cancelarRechazarSolicitudRiss(Long.valueOf(idSolicitud), causaCancelacion, false);
			result.put(KEY_MENSAJE, MSG_SOLICITUD_CANCELADA);
			result.put(SOLICITUD, solicitud);
		} catch (AbstractException e) {
			this.log.error(e);
			result.put(KEY_MENSAJE, MSG_ERROR_CANCELAR + e.getMessage());
		}
		return result;
	}
	
	private void getDocumentoPorTipo(HttpServletRequest request, Solicitud solicitud){
		for(Tramite tramite : solicitud.getTramites()){
			if(beneficioRissServiceBusiness.esTramiteActivo(((TramiteRiss)tramite))){
				DocumentoPorTipo tipoDocumento = new DocumentoPorTipo();
				tipoDocumento.setIdDocumentoPorTipo(DocumentoPorTipoEnum
					.COMPROBANTE_ALTA_BENEFICIO_RISS.getId());				
				request.setAttribute("idSolicitudHashed", solicitud.getSolicitudIdHashed());
				request.setAttribute("idTramiteHashed", tramite.getTramiteIdHashed());
				request.setAttribute("tipoDocumentoHashed", tipoDocumento.getIdDocumentoPorTipoHashed());
			}
		}
	}
	
	private void getClaveEstadoSolicitud(HttpServletRequest request,Solicitud solicitud) {		
		int cveEdoSolicitud = solicitud.getEstadoSolicitud().getIdEstadoSolicitud().intValue();
		String edoSolicitud = "";						
		if (cveEdoSolicitud == EstadoSolicitudEnum.ATENDIDA
				.getCodigo().intValue()) {
			edoSolicitud = "ATENDIDA";						
		} else if (cveEdoSolicitud == EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
				.getCodigo().intValue()) {			
			edoSolicitud = "EN_PROCESO";
		} else if (cveEdoSolicitud == EstadoSolicitudEnum.CANCELADA
				.getCodigo().intValue()) {			
			edoSolicitud = "CANCELADA";
		}
		request.setAttribute(EDO_SOLICITUD, edoSolicitud);		
	}
	
	private void validarSolicitudEnProceso(HttpServletRequest request,Solicitud solicitudProceso){
		if(solicitudProceso!=null){			
			request.setAttribute(SOLICITUD_EN_RPOCESO, true);
			int cveEdoSolicitud = solicitudProceso.getEstadoSolicitud().getIdEstadoSolicitud().intValue();
			if (cveEdoSolicitud == EstadoSolicitudEnum.REGISTRADA.getCodigo().intValue()) {
				request.setAttribute(MSG_ERROR, MSG_SOLICITUD_REGISTRADA);
				request.setAttribute(SOLICITUD_REGISTRADA, true);
				request.setAttribute(FOLIO_SOLICITUD_REGISTRADA, solicitudProceso.getNoFolioSolicitud());
				request.setAttribute(ID_SOLICITUD_REGISTRADA, solicitudProceso.getSolicitudId());
			}else{
				request.setAttribute(MSG_ERROR, MSG_SOLICITUD_EN_PROCESO);
			}
		}else{
			inicializarVariblesSolicitudEnProceso(request);
		}
	}	
	
	private void inicializarVariblesSolicitudEnProceso(HttpServletRequest request){
		request.setAttribute(SOLICITUD_EN_RPOCESO, false);
		request.setAttribute(SOLICITUD_REGISTRADA, false);
	}
	
	private boolean rfcNoVacio(String rfc){
		if(StringUtils.isNotEmpty(rfc) && StringUtils.isNotBlank(rfc)){
			return true;
		}else{
			return false;
		}
	}
	
	private Usuario getUsuarioExterno(){
		Usuario usuario = new Usuario();
		usuario.setUsuario(KEY_USUARIO_EXTERNO);		
		return usuario;
	}
	
	
	private void validarRfcEnSat(Fisica fisica, String rfc) throws ClienteWebserviceSatRfcException, 
			ErrorComparacionDatosSATException, PersonasNoLocalizadasException, DatosInsuficientesModificacionException{
		//Obtener persona por RFC en SAT y se comparar con los datos localizados en el IMSS.
		Fisica fisicaSat = this.personaBusiness.buscarPersonaFisicaPorRfcEnSat(rfc);
		if (fisicaSat != null) {
			boolean registrosIguales = this.personaFisicaServiceBusiness.comparaDatosBasicosSAT(fisicaSat, fisica);
			if (registrosIguales) {
				fisica.setRfc(rfc);
			} else {
				throw new ErrorComparacionDatosSATException(MSG_ERROR_SAT_IMSS);
			}
		} else {
			throw new PersonasNoLocalizadasException(MSG_RFC_NO_LOCALIZADO_SAT);
		}
		this.personaFisicaServiceBusiness.actualizarRFC(fisica);
	}
	
}
