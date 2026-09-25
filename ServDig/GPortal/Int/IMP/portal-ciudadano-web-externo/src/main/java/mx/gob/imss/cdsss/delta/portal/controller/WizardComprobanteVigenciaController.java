package mx.gob.imss.cdsss.delta.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cdsss.delta.portal.controller.validator.AsignacionNSSValidator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SolicitudNssCorreoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;

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
	private TramiteDocumentosServiceRemote tramiteDocumentosService;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private SolicitudNssCorreoServiceBusinessRemote solicitudNssCorreoServiceBusiness;
	@Autowired
	private ServiceBusinessRemote serviceBusiness;
	
	
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	public String initReporteVigencia(@ModelAttribute AsignacionNSS asignacionNss,  Model model,HttpSession session) {
		
		session.setAttribute(KEY_ASIGNACION_SESSION, asignacionNss);
		model.addAttribute("asignacionNSS", new AsignacionNSS());
		
		return KEY_VIEW_INICIAL;
	}
	
	@RequestMapping(value = "/imprimir", method = RequestMethod.POST) 
	public String imprimeReporteVigencia(@ModelAttribute AsignacionNSS asignacionNss, BindingResult result, 
			HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		
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
		
		return this.getReporte(nss, response, request);
		
	}
	
	@RequestMapping( value = "/limpiar-session")
	public @ResponseBody Map<String, Object> limpiarSession(HttpSession session) {

		session.removeAttribute(KEY_ASIGNACION_SESSION);
		
		return null;
	}
	@RequestMapping(value="/getPdf", method = RequestMethod.POST)
	public String getReporteVigencia(@ModelAttribute AsignacionNSS asignacionNss, HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		return this.getReporte(asignacionNss, response, request);
		
	}
	@RequestMapping(value ="/pdf/{idAsignacionNss}/{idPersona}/{nss}/{correo}/{curp}")
	public String getReporteVigencia(Model model, @PathVariable Long idAsignacionNss, @PathVariable Long idPersona, @PathVariable String nss,
			@PathVariable String correo, @PathVariable String curp, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		
		AsignacionNSS asignacion = new AsignacionNSS();
		asignacion.setIdAsignacionNSS(idAsignacionNss);
		asignacion.setCurp(curp);
		asignacion.setIdPersona(idPersona);
		asignacion.setNss(nss);
		asignacion.setNssStr(nss);
		asignacion.setCorreoElectronico(new CorreoElectronico());
		asignacion.getCorreoElectronico().setCorreo(correo);
		
		return this.getReporte(asignacion, response, request);
	}
	
	public String getReporte(AsignacionNSS nss, HttpServletResponse response, HttpServletRequest request) {
		Integer tipoSolicitud =  TipoSolicitudEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOS.getValor();
		byte[] res = null;
		
		if(StringUtils.isBlank(nss.getNombre())) {
			try {
				nss = grupoFamiliarServiceRemote.getAsignacionNssByIdAsignacion(nss.getIdAsignacionNSS());
			} catch (DerechohabientesBusinessException e1) {
				log.error("Ocurrio un error al consultar el nss", e1);
			}
		}
		
		SolicitudNssCorreo nssCorreo = new SolicitudNssCorreo();
		log.debug("El nss para el comprobante es: " + nss.getNss());
		log.debug("El correo en el portal ciudadano es: " + nss.getCorreoElectronico().getCorreo());
		nssCorreo.setCorreo(new CorreoElectronico());
		nssCorreo.getCorreo().setCorreo(nss.getCorreoElectronico().getCorreo().toLowerCase());
		nssCorreo.setCurp(nss.getCurp());
		nssCorreo.setCveIdTipoSolicitud(tipoSolicitud.longValue());
		try{
			
			int codigoRespuesta = this.solicitudNssCorreoServiceBusiness.isConsultaRegistroNSSValid(nssCorreo, true);
			nssCorreo.setOperacionEjecutar(codigoRespuesta);
			
			//asignacion = grupoFamiliarServiceRemote.getAsignacionNssByIdAsignacion(nss.getIdAsignacionNSS());
			//Identificadores para el tipo de tramite, solicitud
			Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
			identificadoresMap.put("tramite", TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo());
			identificadoresMap.put("solicitud", tipoSolicitud); 
			identificadoresMap.put("origenSolicitud", OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().intValue());
			
			Map<String, Object> resultado = tramiteDocumentosService.generaTramiteDocumentoReporteConstanciaVigencia(nss, null, identificadoresMap, false);
			res = (byte[])resultado.get("documento");
			//res = (byte[])tramiteDocumentosServiceRemote.generaDocumentoConSelloDigital(asignacion, null, null, identificadorReporte, null, identificadoresMap);
			
			//Se actualiza el numero de intentos de consulta
			this.serviceBusiness.ejecutarOperacionValidacionCorreoCurp(nssCorreo);
		} catch (DocumentoException e) {
			log.error("Error al generar el comprobante de vigencia", e);
			tramiteDocumentosService.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), nss.getCurp(),e.getMessage());
			request.setAttribute("error", e.getMessage());
			return "errorReporteVigencia";
		} catch(SolicitudNssCorreoException e) {
			log.error("Error al generar el comprobante de vigencia", e);
			tramiteDocumentosService.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), nss.getCurp(),e.getMessage());
			request.setAttribute("error", e.getMessage());
			return "errorReporteVigencia";
		} catch (Exception e) {
			log.error("Error al generar el comprobante de vigencia", e);
			tramiteDocumentosService.generarSolicitudRechazo(OrigenSolicitudEnum.INTERNET.getId(), nss.getCurp(),e.getMessage());
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
				response.addHeader("Content-disposition", "inline;filename=\"comprobanteVigenceDerechos" +nss.getNss() + ".pdf\""); 
				response.setContentLength(res.length);
				response.getOutputStream().write(res);
				response.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
