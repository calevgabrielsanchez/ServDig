package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller.validator.AsignacionNSSValidator;

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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wizard/comprobante/vigencia")
public class WizardComprobanteVigenciaController extends AbstractController {
	
	private static final String KEY_ASIGNACION_SESSION = "keyAsignacionNSS";
	private static final String KEY_VIEW_INICIAL = "initReporteVigencia";
	
	@Autowired
	private TramiteDocumentosServiceRemote tramiteDocumentosServiceRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusiness;
	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	@Autowired
	private DocumentosServiceRemote documentosService; 
	@Autowired
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	
	/**
	 * Metodo que muestra la pantalla de captura de nss en el portal ciuda
	 * @param asignacionNss
	 * @param model
	 * @param session
	 * @return
	 */
	//removiendo modal para obtener la vigencia
	/*@RequestMapping(value = "/init", method = RequestMethod.POST)
	public String initReporteVigencia(@ModelAttribute AsignacionNSS asignacionNss,  Model model,HttpSession session) {
		
		session.setAttribute(KEY_ASIGNACION_SESSION, asignacionNss);
		model.addAttribute("asignacionNSS", new AsignacionNSS());
		
		return KEY_VIEW_INICIAL;
	}*/
	
	/**
	 * Este metodo lo manda a llamar tambien desde portal ciudadano cuando se ejecuta la consulta
	 * @param asignacionNss
	 * @param result
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	//removiendo modal para obtener la vigencia
	/*@RequestMapping(value = "/imprimir", method = RequestMethod.POST) 
	public String imprimeReporteVigencia(@ModelAttribute AsignacionNSS asignacionNss, BindingResult result, 
			HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		new AsignacionNSSValidator().validate(asignacionNss, result);
		
		if(result.hasErrors()) {
			return KEY_VIEW_INICIAL;
		}
		
		AsignacionNSS nss = (AsignacionNSS) session.getAttribute(KEY_ASIGNACION_SESSION);
		
		log.debug("El nss que viene del formulario es: " + asignacionNss.getNss());
		log.debug("El nss que se tiene en sesion para comparar es: " + nss.getNss());
		if(!nss.getNss().equals(asignacionNss.getNss())) {
			FieldError fieldError = new FieldError("asignacionNSS", "nss", "El NSS proporcionado no es el mismo que el relacionado a la CURP.");
			result.addError(fieldError);
			
			return KEY_VIEW_INICIAL;
		}
		
		return this.getReporte(nss, idOrigenSolicitud,response, request,null);
	}*/

	@RequestMapping(value ="/pdf/{idAsignacionNss}/{idPersona}/{nss}/{usuario}")
	public String getReporteVigencia(Model model, @PathVariable Long idAsignacionNss, @PathVariable Long idPersona, @PathVariable String nss,
			@PathVariable String usuario,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		Long idOrigenSolicitud = new Long(session.getServletContext().getInitParameter("ID_ORIGEN_APP"));
		AsignacionNSS asignacion = new AsignacionNSS();
		asignacion.setIdAsignacionNSS(idAsignacionNss);
		asignacion.setIdPersona(idPersona);
		asignacion.setNss(nss);
		asignacion.setNssStr(nss);
		
		return this.getReporte(asignacion, idOrigenSolicitud, response, request, usuario);
	
	}
	
	@RequestMapping( value = "/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {

		session.removeAttribute(KEY_ASIGNACION_SESSION);
		
		return null;
	}
	
	/**
	 * Metodo interno que manda a llamar al metodo que genera la solicitud, tramite y el 
	 * comprobante de vigencia de derechos
	 * @param asignacion
	 * @param idOrigenSolicitud
	 * @param response
	 * @param request
	 * @return
	 */
	private String getReporte(AsignacionNSS asignacion, Long idOrigenSolicitud,HttpServletResponse response, HttpServletRequest request, String usuarioPeticion) {
		
		byte[] res = null;
		Boolean portalCiudadano = idOrigenSolicitud.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO.getId());
		
		try{
			//si es el portal ciudadano
			if(portalCiudadano) {
				//Validaremos si se cuenta con el nombre
				if(StringUtils.isBlank(asignacion.getNombre())) {
					//en caso de no contar con el nombre, se vuelve a consultar el asignacion nss
					asignacion = grupoFamiliarServiceRemote.getAsignacionNssByIdAsignacion(asignacion.getIdAsignacionNSS());
				}
			}else {
				//En caso de estar en optro origen que por el momento al dia 30/06/2015
				//siempre sera desde la consutla de segundo y tercer nivel, que se toma como ventanilla
				if(asignacion.getIdAsignacionNSS() != null && asignacion.getIdAsignacionNSS().intValue() != 0){
					String numNss = asignacion.getNss();
					asignacion = grupoFamiliarServiceRemote.getAsignacionNssByIdAsignacion(asignacion.getIdAsignacionNSS());
					if(asignacion == null) {
						asignacion = grupoFamiliarServiceRemote.getAsignacionNssSinPersonaCL3(numNss, false);
					}
				}
			}
			
			//para todos los origenes
			Integer tipoSolicitud =  null;
			SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
			//Si el origen es el portal ciudadano validaremos el numero de veces que imprime los reportes
			if(portalCiudadano) {
				log.debug("al ser el portal la solicitud es de comprobante de vigencia de derechos");
				tipoSolicitud = TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor();
				
				log.debug("El nss para el comprobante es: " + asignacion.getNss());
				log.debug("El correo en el portal ciudadano es: " + asignacion.getCorreoElectronico().getCorreo());
				nssCorreo.setCorreo(new CorreoElectronico());
				nssCorreo.getCorreo().setCorreo(asignacion.getCorreoElectronico().getCorreo().toLowerCase());
				nssCorreo.setCurp(asignacion.getCurp());
				nssCorreo.setCveIdTipoSolicitud(tipoSolicitud.longValue());
				int codigoRespuesta = solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, true);
				nssCorreo.setOperacionEjecutar(codigoRespuesta);
				//si es desde el portal ciudadano el tipo de solicitud es comprobante de vigencia de derechos
				
			} else {
				log.debug("Como no estamos en el porta la solicitud es de impresion de documentos");
				//si no es desde portal ciudadano el tipo de solicitud sera impresion de documento
				tipoSolicitud = TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE.getValor();
			}
			//sacamos el usuario
			Usuario usuario = new Usuario();
			//verificamos si no es el portal ciudadano para que se obtenga
			if(portalCiudadano) {
				usuarioPeticion = asignacion.getCurp();
			}
			
			usuario.setCveIdUsuario(usuarioPeticion);
			usuario.setUsuario(usuarioPeticion);
			//Se crean los parametros para el reporte
			Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
			identificadoresMap.put("origenSolicitud", idOrigenSolicitud.intValue());
			identificadoresMap.put("solicitud", tipoSolicitud); 
			identificadoresMap.put("tramite", TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo());
			//se genera el comprobante de vigencia de derechos
			Map<String, Object> resultado = null;
			if(portalCiudadano) {
				log.debug("estamos en el portal ciudadno, se genera el reporte con el metodo que no va al ws");
				resultado = this.generaTramiteDocumentoReporteConstanciaVigencia(asignacion, usuario, identificadoresMap, false);
			} else {
				log.debug("No estamos en el portal ciudadano, se genera el reporte con el metodo de ws");
				resultado = this.generaTramiteDocumentoReporteConstanciaVigenciaWS(asignacion, usuario, identificadoresMap, false);
			}
			res = (byte[])resultado.get("documento");
			
			if(portalCiudadano) {
				//Se actualiza el numero de intentos de consulta
				serviceBusiness.ejecutarOperacionValidacionCorreoCurp(nssCorreo);
			}
		} catch (DocumentoException e) {
			log.error("Error al generar el comprobante de vigencia", e);
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(idOrigenSolicitud, asignacion.getCurp(),e.getMessage());
			request.setAttribute("error", e.getMessage());
			return "errorReporteVigencia";
		} catch(SolicitudNssCorreoException e) {
			log.error("Error al generar el comprobante de vigencia", e);
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(idOrigenSolicitud, asignacion.getCurp(),e.getMessage());
			request.setAttribute("error", e.getMessage());
			return "errorReporteVigencia";
		} catch (Exception e) {
			log.error("Error al generar el comprobante de vigencia", e);
			tramiteDocumentosServiceRemote.generarSolicitudRechazo(idOrigenSolicitud, asignacion.getCurp(),e.getMessage());
			request.setAttribute("error", "Ocurri&oacute; un errror al generar el reporte.");
			return "errorReporteVigencia";
		} 

		if(res != null) {
			try{
				response.addHeader("Accept-Ranges","bytes");
				response.addHeader("Cache-Control","public");
				response.addHeader("Cache-Control","must-revalidate");
				response.addHeader("Pragma","public");
				response.setContentType("application/pdf");
				response.addHeader("expires","0");
				response.addHeader("Content-disposition", "inline;filename=\"comprobanteVigenciaDerechos" +asignacion.getNss() + ".pdf\""); 
				response.setContentLength(res.length);
				response.getOutputStream().write(res);
				response.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Usuario getUsuarioSesion(UsuarioSSO usuariosso) {

		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());

		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(usuariosso.getNombre());
		usuario.setPerfilUsuario(pu);

		// Se crean los objetos necesarios para ligar el usuario con la
		// subdelegacion y delegacion.

		if (usuariosso.getDelegacion() != null && usuariosso.getSubdelegacion() != null) {

			// LUDS Se agrego esta validacion para que si es en caso de un
			// usuario EXTERNO no le llega la delegacion.
			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(usuariosso.getDelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.setUsuario(usuario);
			uf.getSubdelegacion().setId(usuariosso.getSubdelegacion().longValue());
			usuario.setUsuarioFuncionario(uf);
		}
		
		if(usuariosso.getUmf() != null) {
			usuario.setIdUmf(usuariosso.getUmf().longValue());
			
			if(usuario.getUsuarioFuncionario() != null) {
				usuario.getUsuarioFuncionario().setUnidadMedicaFamiliar(new UnidadMedicaFamiliar());
				usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().setIdUMF(usuario.getIdUmf());
			}
		}

		usuario.setCveIdUsuario(usuariosso.getCurp());

		return usuario;
	}
	
	private Map<String, Object> generaTramiteDocumentoReporteConstanciaVigencia(AsignacionNSS asignacionNSS, Usuario usuario,
			Map<String, Integer> identificadoresMap, boolean enviaMail)
			throws DocumentoException {
		
		return this.generaConstancia(asignacionNSS, usuario, identificadoresMap, enviaMail,false);

			
	}
	
	private Map<String, Object> generaTramiteDocumentoReporteConstanciaVigenciaWS(AsignacionNSS asignacionNSS, Usuario usuario, Map<String, Integer> identificadoresMap, boolean enviaMail) throws DocumentoException {

		if(asignacionNSS.getIdAsignacionNSS() == null || asignacionNSS.getIdAsignacionNSS().intValue() == 0){
			try {
				GrupoFamiliar grupoFamiliar =  grupoFamiliarServiceRemote.getInfoAsegurado(asignacionNSS.getNss());
				asignacionNSS = grupoFamiliar.getAsignacionNSS();
			} catch (DerechohabientesWebSserviceException e) {
				throw new DocumentoException(e.getMessage());
			}
		}
		
		return this.generaConstancia(asignacionNSS, usuario, identificadoresMap, enviaMail, true);
			
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
		// asignacionNSS es de tipo AsignacionNSS que extiende de Fisica
		// por lo que puede pasarse como parametro para crear la solicitud
		Solicitud solicitud = tramiteDocumentosServiceRemote.crearTramiteSolicitud(asignacionNSS,
				origenSolicitud.longValue(), usuario, identificadoresMap);

		log.debug("Termino de generar la solicitud");
		// 2. Obtener sello digital
		// Se obtiene el sello digital
		// Se genera cadena original
		asignacionNSS.setNss(nss);
		log.debug("voy a guardar en notaria");
		FirmaElectronica firmaElectronica = tramiteDocumentosServiceRemote.generaFirmaElectronica(
				asignacionNSS, solicitud, "COMPROBANTE DE VIGENCIA DE DERECHOHABIENTES");
		log.debug("termino de guardar en notaria");
	
		log.debug("Voy a generar el pdf");
		
		if(infoWS) {
			// 3. Generacion del reporte con informacion del sello digital
			documentByteArray = (byte[]) documentosService.getConstanciaVigenciaWS(asignacionNSS, firmaElectronica, usuario);
		} else {
			// 3. Generacion del reporte con informacion del sello digital
			documentByteArray = (byte[]) documentosService.getConstanciaVigenciaInternetRecortado(asignacionNSS,firmaElectronica, usuario);
		}
		log.debug("Termino de generar el pdf");
		
		log.debug("Voy a giardar en notaria");
		// 4. Se guarda el reporte
		firmaDigitalBusinessRemote.guardarArchivoFirmado(
				firmaElectronica.getReciboNotarial(),
				"reporteVigencia"+nss+".pdf", documentByteArray);
		log.debug("Termino de guardar en notaria en portal y con WS " + infoWS);
		
		Long tramiteId = solicitud.getTramites().get(0).getTramiteId();
		
		/*solicitudEntity.actualizarDocumentosTramite(tramiteId, 
				DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId(), documentByteArray);*/
		
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
