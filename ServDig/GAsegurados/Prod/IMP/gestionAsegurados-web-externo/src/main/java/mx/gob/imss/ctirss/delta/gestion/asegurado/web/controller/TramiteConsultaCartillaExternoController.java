package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.web.validator.ConsultaCartillaValidator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;

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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/consultaCartilla")
public class TramiteConsultaCartillaExternoController extends
		AbstractController {

	
	private String MSG_ERROR_COMPARACION = "No se puede realizar el tr�mite Consulta de Cartilla Nacional de Salud " +
			"por internet ya que los datos estad�sticos localizados en el Instituto no coinciden " + 
					 "con los datos encontrados en RENAPO, para poder realizar el tr�mite deber� presentarse en la " +
					 "subdelegaci�n del Instituto m�s cercana para actualizar su informaci�n.";
	
	
	private String MSG_ERROR_NSS = "Estimado Asegurado(a) o Pensionado(a), "
			+ "el n�mero de seguridad social asociado a la CURP que ingres�, "
			+ "requiere de su aclaraci�n, por lo que le agradeceremos acudir "
			+ "a la Subdelegaci�n m�s cercana a realizarla.";
	
	private final String VIEW_LOGIN_GOBMX="loginCartilla";
	private final String VIEW_LOGIN="consultaCartilla.datosbasicos";

	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	@Autowired
	private SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusiness;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private TramiteDocumentosServiceRemote tramiteDocumentosServiceRemote;
	@Autowired
	private DocumentosServiceRemote documentosServiceRemote; 
	@Autowired
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	
	/**
	 * Metodo para iniciar el tramite con gobmx
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="")
	public String homeGobMX(Model model, HttpSession session) {
		model.addAttribute("fisica", new Fisica());
		return VIEW_LOGIN_GOBMX;
	}

	/* Home */
	@RequestMapping(value = "/homeCartilla", method = RequestMethod.GET)
	public Object home(Model model, HttpSession session) {
		session.removeAttribute("comprobanteCartillaSession");
		return new RedirectView("/cartilla", true);
		//return "consultaCartilla.home";
	}

	@RequestMapping(value = "/homeCartilla-interno", method = RequestMethod.GET)
	public Object homeInterno(Model model, HttpSession session) {
		session.removeAttribute("comprobanteCartillaSession");
		return new RedirectView("/cartilla", true);
		//return "consultaCartilla.home";
	}
	
	/* Paso 1 */
	@RequestMapping(value = "/iniciar", method = RequestMethod.GET)
	public Object iniciar(Model model, HttpSession session) {
		model.addAttribute("fisica", new Fisica());
		session.removeAttribute("comprobanteCartillaSession");
		this.setFechaSistema(session);
		return new RedirectView("/cartilla", true);
		//return VIEW_LOGIN;
	}

	/* Del paso 1 hacia el paso 2 */
	@RequestMapping(value = "/consultar", method = RequestMethod.POST)
	public String consultaDatosBasicos(@ModelAttribute Fisica fisica,
			BindingResult result, Model model,
			@RequestParam("captcha") String captcha, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		return this.validacionesCommon(false, fisica, result, model, captcha, session, request, response);
	}
	
	@RequestMapping(value = "/consultarCartilla", method = RequestMethod.POST)
	public String consultaDatosBasicosGobMX(@ModelAttribute Fisica fisica,
			BindingResult result, Model model,
			@RequestParam("captcha") String captcha, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {

		return this.validacionesCommon(true, fisica, result, model, captcha, session, request, response);
	}
	
	private String validacionesCommon(boolean gobMX,Fisica fisica,
			BindingResult result, Model model, String captcha, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		session.removeAttribute("comprobanteCartillaSession");
		int codigoRespuesta = 0;
		String mensajeErrror = "";
		//el error lo dejamos en true para que solo en caso de que todo este ok se ponga en false
		boolean error = true;
		boolean backButoon = false;
		String vista = gobMX ? VIEW_LOGIN_GOBMX : VIEW_LOGIN; 
		
		request.setAttribute("BACK_HOME", null);
		TramiteAsegurado tramiteAsegurado = new TramiteAsegurado();

		// Se pasa a min�sculas el correo capturado y el de confirmaci�n
		String correoCapturado = fisica.getCorreoElectronico().getCorreo().toLowerCase();
		fisica.getCorreoElectronico().setCorreo(correoCapturado);
		String correoConfirmacion = fisica.getCorreoElectronicoFiscal().getCorreo().toLowerCase();
		fisica.getCorreoElectronicoFiscal().setCorreo(correoConfirmacion);

		
		// Validaciones de obligatoriedad, longitud y formato
		new ConsultaCartillaValidator().validate(fisica, result);

		
		if (StringUtils.isBlank(captcha)) {
			FieldError fieldError = new FieldError("fisica","errorFormGeneral", "Campo requerido");
			result.addError(fieldError);
		}

		if (result.hasErrors()) {
			this.log.warn("Errores de captura");
			return vista;
		}

			
		// Se valida el captcha
		if (!captcha.equals(session.getAttribute("captcha"))) {
			this.log.error("Captcha no v�lido!!!");
			FieldError fieldError = new FieldError("fisica","errorFormGeneral","El captcha no fue v�lido, favor de intentar nuevamente");
			result.addError(fieldError);

			return vista;
		}

		/*
		 * Si las validaciones de datos requeridos y del captcha fueron
		 * exitosas, se elimina el correoFiscal, que en este caso representa la
		 * confirmaci�n del correo electr�nico. Se quita para que no se guarde
		 * en la solicitud y se tenga duplicado el correo.
		 */
		fisica.setCorreoElectronicoFiscal(null);
		SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
		nssCorreo.setCorreo(fisica.getCorreoElectronico());
		nssCorreo.setCurp(fisica.getCurp());
		nssCorreo.setCveIdTipoSolicitud( TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor().longValue() );
		Fisica fisicaEncontrada = null;
		
		try {
			// Valida la relacion Correo => CURP, si existe esta relacion
			// se valida que la consulta no haya excedido los intentos maximos
			// del d�a por tipo de servicio
			codigoRespuesta = this.solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, true);
			nssCorreo.setOperacionEjecutar(codigoRespuesta);
			// Se busca el curp capturado en RENAPO
			fisicaEncontrada = this.serviceBusiness.validacionesNSSConsultaVigencia(fisica);
			fisicaEncontrada.setCorreoElectronico(fisica.getCorreoElectronico());
			
			AsignacionNSS nss = null;
			try {
				nss = grupoFamiliarServiceRemote.getAsignacionNssSinPersona(fisica.getNss(), false);
				nss.setCorreoElectronico(fisica.getCorreoElectronico() );
				tramiteAsegurado.setFisica(nss);
				//Identificadores para el tipo de tramite, solicitud
				Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
				identificadoresMap.put("tramite", TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo());
				identificadoresMap.put("solicitud", TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor()); 
				
				Map<String, Object> resultado = this.generaConstancia(nss, null, identificadoresMap, true,false);
				byte[] res = (byte[])resultado.get("documento");
				session.setAttribute("comprobanteCartillaSession", res);
				long tramiteId = (Long) resultado.get("tramiteId");
				request.setAttribute("tramiteId", tramiteId);
				
				//Env�o de correo
				try{
					//tramiteDocumentosService.enviaCorreo(nss, res, null);
					//String subject = "Reporte de cartilla nacional de salud";
					Map<String,String> paramAdicionales = new HashMap<String, String>();
					
					paramAdicionales.put("folio", (String)resultado.get("folio"));
					paramAdicionales.put("idSolicitud",(String) resultado.get("idSolicitud"));
					paramAdicionales.put("folioCifrado", Base64Cipher.cifrar((String) resultado.get("folio")));
					
					
					tramiteDocumentosServiceRemote.enviaCorreo(nss, res, null);
					/*
					serviceBusiness.enviarCorreoPersonaFisica(fisicaEncontrada, 
							TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB, 
							paramAdicionales, subject);
							*/
				}
				catch(Exception exception){
					exception.printStackTrace();
				}

				response.setHeader("Pragma", "No-cache");
			    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			    response.setDateHeader("Expires", 0);
				request.setAttribute("NSS_RECUPERADO", true);
				
				log.debug("se genero la constancia sin ningun error");
				//Se actualiza el numero de intentos de consulta
				this.serviceBusiness.ejecutarOperacionValidacionCorreoCurp(nssCorreo);
				error = false;
				
			} catch (DerechohabientesBusinessException e2) {
				e2.printStackTrace();
				mensajeErrror = e2.getMessage();
				tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri� un error al generar la cartilla nacional de salud.");
				backButoon = true;
			} catch(DocumentoException ex){
				this.log.error(ex);
				mensajeErrror = ex.getMessage();
				tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri� un error al generar la cartilla nacional de salud.");
				backButoon = true;
			} catch (Exception e2) {
				e2.printStackTrace();
				mensajeErrror = e2.getMessage();
				tramiteAsegurado.setErrorFormGeneral("Mensaje: Ocurri� un error al generar la cartilla nacional de salud.");
				backButoon = true;
			}
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			this.log.error(e);
			tramiteAsegurado.setErrorFormGeneral("Mensaje: No se localiz� informaci�n en RENAPO con la CURP capturada.");
			mensajeErrror = tramiteAsegurado.getErrorFormGeneral();
		} catch (ClienteWebserviceRenapoCurpException e) {
			this.log.error(e);
			tramiteAsegurado.setErrorFormGeneral("Mensaje: No se localiz� informaci�n en RENAPO con la CURP capturada.");
			mensajeErrror = tramiteAsegurado.getErrorFormGeneral();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			this.log.error(e);
			mensajeErrror = e.getMessage();
			tramiteAsegurado.setErrorFormGeneral(mensajeErrror);
		} catch (ErrorComparacionDatosRENAPOException e) {
			this.log.error(e);
			tramiteAsegurado.setErrorFormGeneral(MSG_ERROR_COMPARACION);
			mensajeErrror = e.getMessage();
		} catch (SolicitudNssCorreoException e) {
			//Esta exception debe mandar al home (a traves de la pantalla de salida)
			this.log.error(e);
			mensajeErrror = e.getMessage();
			tramiteAsegurado.setErrorFormGeneral(mensajeErrror);
			backButoon = true;
		}catch (AsignacionNSSNoLocalizadoException e){
			tramiteAsegurado.setErrorFormGeneral(MSG_ERROR_NSS);
			mensajeErrror = e.getMessage();
		}
		
		if(error) {
			request.setAttribute("ERROR", true);
			if(backButoon) {
				request.setAttribute("BACK_HOME", true);
			}
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), fisica.getCurp(),mensajeErrror);
			log.error("Ocurrio un error al generar la cartilla nacional de salud para el curp " + fisica.getCurp());
		}
		
		log.debug("existe error? " +  error + " y viene de gobmx? " + gobMX);
		if(error && gobMX) {
			fisica = new Fisica();
			fisica.setErrorFormGeneral(tramiteAsegurado.getErrorFormGeneral());
			model.addAttribute("fisica", fisica);
			return VIEW_LOGIN_GOBMX;
		}
		// Subimos al request el objeto de Modelo
		model.addAttribute("tramiteAsegurado", tramiteAsegurado);

		return "consultaCartilla.salida";
	}
	
	@RequestMapping(value = "/reporteCartilla/{idTramite}", method = RequestMethod.POST)
	public void mostrarPDF(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response, @PathVariable Long idTramite) {
		//byte[] documento = (byte[])tramiteDocumentosServiceRemote.getDocumentoPorTipoIdTramite(idTramite, DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId());
		byte[] documento = (byte[])session.getAttribute("comprobanteCartillaSession");
		construirPdf(response, documento);
	}

	private void construirPdf(HttpServletResponse response, byte[] res) {
		try {
			response.addHeader("Accept-Ranges","bytes");
			response.addHeader("Cache-Control","public");
			response.addHeader("Cache-Control","must-revalidate");
			response.addHeader("Pragma","public");
			response.addHeader("expires","0");
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename = sav");
			response.getOutputStream().write(res);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private Map<String, Object> generaConstancia(AsignacionNSS asignacionNSS, Usuario usuario,
			Map<String, Integer> identificadoresMap, boolean enviaMail, boolean infoWS) throws DocumentoException {

		byte[] documentByteArray = null;
		Map<String, Object> resultado = new HashMap<String, Object>();
		String nss = asignacionNSS.getNssStr();
		asignacionNSS.setNss(nss);

		try {

			// 1. Se debe crear un tramite y solicitud.
			Integer origenSolicitud = identificadoresMap.get("origenSolicitud");
			if(origenSolicitud==null) {
				origenSolicitud = OrigenSolicitudEnum.INTERNET.getId().intValue();
			}

			log.debug("voy a crear la solicitud");
			// asignacionNSS es de tipo AsignacionNSS que extiende de Fisica por lo que puede pasarse como parametro para crear la solicitud
			Solicitud solicitud = tramiteDocumentosServiceRemote.crearTramiteSolicitud(asignacionNSS,
					origenSolicitud.longValue(), usuario, identificadoresMap);

			log.debug("Termino de generar la solicitud");
			// 2. Obtener sello digital
			// Se obtiene el sello digital
			// Se genera cadena original
			asignacionNSS.setNss(nss);
			log.debug("voy a guardar en notaria");
			FirmaElectronica firmaElectronica = tramiteDocumentosServiceRemote.generaFirmaElectronica(asignacionNSS, solicitud, "COMPROBANTE DE VIGENCIA DE DERECHOHABIENTES");
			log.debug("Voy a generar el pdf");

			if(infoWS) {
				// 3. Generacion del reporte con informacion del sello digital
				documentByteArray = (byte[]) documentosServiceRemote.getConstanciaVigenciaWS(asignacionNSS, firmaElectronica, usuario);
			} else {
				// 3. Generacion del reporte con informacion del sello digital
				documentByteArray = (byte[]) documentosServiceRemote.getConstanciaVigenciaInternetRecortado(asignacionNSS,firmaElectronica, usuario);
			}
			
			// 4. Se guarda el reporte
			firmaDigitalBusinessRemote.guardarArchivoFirmado(firmaElectronica.getReciboNotarial(),"cartilla"+nss+".pdf", documentByteArray);
			log.debug("Termino de guardar en notaria en portal y con WS " + infoWS);

			Long tramiteId = solicitud.getTramites().get(0).getTramiteId();

			resultado.put("documento", documentByteArray);
			resultado.put("tramiteId", tramiteId);
			resultado.put("folio", solicitud.getNoFolioSolicitud());
			resultado.put("idSolicitud", solicitud.getSolicitudId().toString());
			resultado.put("secuenciaNotarial",firmaElectronica.getSecuenciaNotaria());
		} catch (SolicitudNoValidaException e) {
			throw new DocumentoException(e.getMessage());
		} catch (SolicitudException e) {
			throw new DocumentoException(e.getMessage());
		} catch (DerechohabientesBusinessException e) {
			throw new DocumentoException(e.getMessage());
		} catch (Exception e) {
			throw new DocumentoException(e.getMessage());
		}
		return resultado;
	}
}
