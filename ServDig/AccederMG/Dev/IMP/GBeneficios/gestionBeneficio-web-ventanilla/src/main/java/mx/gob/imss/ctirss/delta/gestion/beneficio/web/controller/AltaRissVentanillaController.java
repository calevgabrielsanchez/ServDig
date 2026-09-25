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
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
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
@RequestMapping(value = "/alta/riss")
public class AltaRissVentanillaController extends AbstractController {

	@Autowired
	private HomeController homeController;
	@Autowired
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@Autowired
	private PersonaBusinessRemote personaBusiness;
	@Autowired
	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusiness;
	@Autowired
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	private static final String KEY_ATRIBUTE_FISICA 	= "fisicaToSession";
	private static final String KEY_ATRIBUTE_PATRONES 	= "sujetosObligadosToSession";
	private static final String KEY_VALIDAR_PATRON 		= "/gestionBeneficio-web-ventanilla/alta/riss/validar/patron";
	private static final String VISTA_DATOS_ADICIONALES = "confirmarDatosAdicionalesRiss";
	private static final String EDO_SOLICITUD 			= "EDO_SOLICITUD";
	
	@RequestMapping(value = "/iniciar", method = RequestMethod.GET)
	public String initAltaRiss(Model model, HttpSession session,
			HttpServletRequest request) {		
		this.homeController.generarUsuarioSession(model, session, request);		
		model.addAttribute("datosEntradaRiss", new DatosEntradaRiss());		
		return "initRiss";
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
			view = "initRiss";
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
						msgError = "No se encontraron patrones asociados al RFC " + datosEntrada.getRfc()+".";
					}else{		
						//Validar si cuenta con RPs Pendientes para notificar.
						String msgRPSPendientes = beneficioRissServiceBusiness
							.indicarRPsPendientes(fisica, OrigenSolicitudEnum.VENTANILLA);
						request.setAttribute("msgRPSPendientes", msgRPSPendientes);
					}
					request.setAttribute("rissRfc", true);
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
					//se cambia llamada para validar que el NSS este vigente
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
				request.setAttribute("rissNss", true);
				request.setAttribute("fisica", fisica);
			}			
			model.addAttribute("datosEntradaRiss", datosEntrada);						
			view = "confirmarDatosRiss";
		}		
		if (StringUtils.isNotBlank(msgError)) {
			request.setAttribute("msgError", msgError);
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
				respuesta.put("tienePatrones", result);
				session.setAttribute(KEY_ATRIBUTE_PATRONES, sujObligados);
			} catch (AbstractException e) {
				e.printStackTrace();
				error = e.getMessage();
			}
		}
		respuesta.put("error", error);
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
		
		String view = VISTA_DATOS_ADICIONALES;
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
				request.setAttribute("rissNss", true);							
				view = "confirmarDatosRiss";				
			}else{
				sujObligados = (List<SujetoObligado>) session
					.getAttribute(KEY_ATRIBUTE_PATRONES);
			}
			request.setAttribute("rfcObligatorioValue", true);		
		}else{
			request.setAttribute("rfcObligatorioValue", false);	
			sujObligados = (List<SujetoObligado>) session
				.getAttribute(KEY_ATRIBUTE_PATRONES);
		}
		datosEntrada.setPatrones(sujObligados);
		model.addAttribute("datosEntradaRiss", datosEntrada);
		request.setAttribute("fisica", fisica);	
		
		if((!CollectionUtils.isEmpty(datosEntrada.getPatrones()))
			&& rfcNoVacio(fisica.getRfc())){
			//Validar si cuenta con RPs Pendientes para notificar.
			String msgRPSPendientes = beneficioRissServiceBusiness
				.indicarRPsPendientes(fisica, OrigenSolicitudEnum.VENTANILLA);
			request.setAttribute("msgRPSPendientes", msgRPSPendientes);
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
		
		String view = "confirmarDatosRiss";
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
			request.setAttribute("rissNss", true);
			request.setAttribute("fisica", fisica);						
		} else if (fisica != null) {
			Fisica fisicaConsulta = new Fisica();
			fisicaConsulta.setIdPersona(fisica.getIdPersona());
			if (rfcObligatorio != null && rfcObligatorio == true) {
				fisicaConsulta.setRfc(datosEntrada.getRfc());
			}else{
				fisicaConsulta.setRfc(fisica.getRfc());
			}		
			Solicitud solicitudProceso = beneficioRissServiceBusiness.
				validarSolicitudRissEnProceso(fisicaConsulta, OrigenSolicitudEnum.VENTANILLA);
			
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
					Usuario usuario = (Usuario) this.getUsuarioEnSesion(session);	
					Beneficio beneficio = beneficioRissServiceBusiness
						.obtenerPersonaBeneficioVentanilla(fisica, datosEntrada.getPatrones());
					solicitud = beneficioRissServiceBusiness.crearSolicitudRiss(
						beneficio, usuario, OrigenSolicitudEnum.VENTANILLA.getId());
					beneficio = beneficioRissServiceBusiness.validarSolicitudRiss(solicitud, beneficio);
					
					request.setAttribute("SOLIC_RISS", solicitud);
					request.setAttribute("BENEF_RISS", beneficio);									
					view = "resultadoValidacionRiss";
				} catch (ClienteWebserviceSatRfcException e) {
					this.log.error(e);
					datosEntrada.setErrorFormGeneral(e.getMessage());
					request.setAttribute("rissNss", true);
					view = "confirmarDatosRiss";
				} catch (ErrorComparacionDatosSATException e) {
					this.log.error(e);
					datosEntrada.setErrorFormGeneral(e.getMessage());				
					request.setAttribute("rissNss", true);
					view = "confirmarDatosRiss";
				} catch (PersonasNoLocalizadasException e) {
					this.log.warn(e);
					datosEntrada.setErrorFormGeneral(e.getMessage());
					request.setAttribute("rissNss", true);				
					view = "confirmarDatosRiss";
				} catch (SolicitudNoValidaException e) {
					this.log.error(e);
					msgError = e.getMessage();
					request.setAttribute("rissNss", true);
					view = "confirmarDatosRiss";
				} catch (PersonaNoValidaBeneficioRissException e) {
					this.log.warn(e);
					datosEntrada.setErrorFormGeneral(e.getMessage());
					request.setAttribute("rissNss", true);				
					view = "confirmarDatosRiss";				
				} catch (DatosInsuficientesModificacionException e) {
					this.log.error(e);
					msgError = e.getMessage();				
					request.setAttribute("rissNss", true);
					view = "confirmarDatosRiss";	
								
				} catch (BeneficioRissException e) {
					this.log.warn(e);
					String msgMotivoRechazo = null;					
					if (StringUtils.isBlank(e.getMessage())) {
						msgMotivoRechazo = "Ha ocurrido un error inesperado";
					} else {
						msgMotivoRechazo = e.getMessage();
					}					
					try {
						beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
					} catch (AbstractException ae) {
						this.log.error(ae);
						ae.printStackTrace();
					}			
					request.setAttribute("msgMotivoRechazo", msgMotivoRechazo);
					view = "resultadoValidacionRiss";					
				} catch (ClienteWebserviceImssRissException e) {
					this.log.warn(e);
					String msgMotivoRechazo = null;					
					if (StringUtils.isBlank(e.getMessage())) {
						msgMotivoRechazo = "Ha ocurrido un error inesperado";
					} else {
						msgMotivoRechazo = e.getMessage();
					}					
					try {
						beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
					} catch (AbstractException ae) {
						this.log.error(ae);
						ae.printStackTrace();
					}			
					request.setAttribute("msgMotivoRechazo", msgMotivoRechazo);
					view = "resultadoValidacionRiss";					
				}
			}else{
				//Existe solicitud en proceso o registrada
				validarSolicitudEnProceso(request, solicitudProceso);
				request.setAttribute("rissNss", true);				
				view = "confirmarDatosRiss";
			}
		} else {
			msgError = "Persona no encontrada";
			inicializarVariblesSolicitudEnProceso(request);
		}		
		if (StringUtils.isNotBlank(msgError)) {
			request.setAttribute("msgError", msgError);
		}
		
		request.setAttribute("fisica", fisica);		
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
		Usuario usuario = (Usuario) this.getUsuarioEnSesion(session);				
		Fisica fisica = new Fisica();
		fisica.setRfc(datosEntrada.getRfc().toUpperCase().trim());
		
		Solicitud solicitudProceso = beneficioRissServiceBusiness.
			validarSolicitudRissEnProceso(fisica, OrigenSolicitudEnum.VENTANILLA);
		if(solicitudProceso==null){
			//NO Existe solicitud en proceso o registrada
			inicializarVariblesSolicitudEnProceso(request);
			Solicitud solicitud = null;
			try {				
				Beneficio beneficio = beneficioRissServiceBusiness
					.obtenerPersonaBeneficioVentanilla(fisica, sujetosObligados);
				solicitud = beneficioRissServiceBusiness.crearSolicitudRiss(
						beneficio, usuario, OrigenSolicitudEnum.VENTANILLA.getId());				
				beneficio = beneficioRissServiceBusiness.validarSolicitudRiss(solicitud, beneficio);
				
				request.setAttribute("SOLIC_RISS", solicitud);
				request.setAttribute("BENEF_RISS", beneficio);
				view = "resultadoValidacionRiss";				
			} catch (SolicitudNoValidaException e) {
				this.log.error(e);
				msgError = e.getMessage();				
				view = "confirmarDatosRiss";
			} catch (PersonaNoValidaBeneficioRissException e) {
				this.log.error(e);
				msgError = e.getMessage();				
				view = "confirmarDatosRiss";
				
			} catch (BeneficioRissException e) {
				this.log.warn(e);
				String msgMotivoRechazo = null;				
				if (StringUtils.isBlank(e.getMessage())) {
					msgMotivoRechazo = "Ha ocurrido un error inesperado";
				} else {
					msgMotivoRechazo = e.getMessage();
				}				
				try {
					beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
				} catch (AbstractException ae) {
					this.log.error(ae);
					ae.printStackTrace();
				}				
				request.setAttribute("msgMotivoRechazo", msgMotivoRechazo);			
				view = "resultadoValidacionRiss";
			} catch (ClienteWebserviceImssRissException e) {
				this.log.warn(e);
				String msgMotivoRechazo = null;				
				if (StringUtils.isBlank(e.getMessage())) {
					msgMotivoRechazo = "Ha ocurrido un error inesperado";
				} else {
					msgMotivoRechazo = e.getMessage();
				}				
				try {
					beneficioRissServiceBusiness.cancelarRechazarSolicitudRiss(solicitud, msgMotivoRechazo, true);	
				} catch (AbstractException ae) {
					this.log.error(ae);
					ae.printStackTrace();
				}				
				request.setAttribute("msgMotivoRechazo", msgMotivoRechazo);			
				view = "resultadoValidacionRiss";
			}
			
			if (StringUtils.isNotBlank(msgError)) {
				request.setAttribute("msgError", msgError);
			}
		}else{
			//Existe solicitud en proceso o registrada
			validarSolicitudEnProceso(request, solicitudProceso);
			view = "confirmarDatosRiss";
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
			request.setAttribute("folioSolicitud", solicitud.getNoFolioSolicitud());
			request.setAttribute("solicitud", solicitud);
			
		} catch (AbstractException e) {
			this.log.error(e);
		}		
		request.setAttribute("idSolicitud", idSolicitud);		
		return "concluirRiss";
	}
	
	@RequestMapping(value = "/cancelar", method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> cancelarSolicitud(
			@RequestParam String idSolicitud, HttpSession session) {
		
		Usuario usuario = (Usuario) this.getUsuarioEnSesion(session);
		
		String causaCancelacion = "SOLICITUD CANCELADA POR USUARIO DE VETANILLA " + usuario.getUsuario();
		
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Solicitud solicitud = this.beneficioRissServiceBusiness
					.cancelarRechazarSolicitudRiss(Long.valueOf(idSolicitud), causaCancelacion, false);

			result.put("mensaje", "La solicitud fue cancelada correctamente");
			result.put("solicitud", solicitud);

		} catch (SolicitudNoEncontradaException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: "
					+ e.getMessage());
		} catch (TramiteNoEncontradoException e) {
			this.log.error(e);
			result.put("mensaje", "Hubo un error al cancelar la solicitud: "
					+ e.getMessage());
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
			log.warn("Solicitud en proceso " + solicitudProceso.getNoFolioSolicitud());
			request.setAttribute("solicitudEnProceso", true);
			int cveEdoSolicitud = solicitudProceso.getEstadoSolicitud().getIdEstadoSolicitud().intValue();
			if (cveEdoSolicitud == EstadoSolicitudEnum.REGISTRADA.getCodigo().intValue()) {
				request.setAttribute("msgError", "Ya cuenta con una solicitud REGISTRADA.");
				request.setAttribute("solicitudRegistrada", true);
				request.setAttribute("folioSolicitudRegistrada", solicitudProceso.getNoFolioSolicitud());
				request.setAttribute("idSolicitudRegistrada", solicitudProceso.getSolicitudId());
			}else{
				request.setAttribute("msgError", "Ya cuenta con una solicitud EN PROCESO.");
			}
		}else{
			inicializarVariblesSolicitudEnProceso(request);
		}
	}	
	
	private void inicializarVariblesSolicitudEnProceso(HttpServletRequest request){
		request.setAttribute("solicitudEnProceso", false);
		request.setAttribute("solicitudRegistrada", false);
	}
	
	private boolean rfcNoVacio(String rfc){
		if(StringUtils.isNotEmpty(rfc) && StringUtils.isNotBlank(rfc)){
			return true;
		}else{
			return false;
		}
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
				throw new ErrorComparacionDatosSATException("Los datos encontrados en el Instituto no coinciden con los registrados en el SAT.");
			}
		} else {
			throw new PersonasNoLocalizadasException("El RFC capturado no fue localizado en el SAT.");
		}
		this.personaFisicaServiceBusiness.actualizarRFC(fisica);
	}
	
}
