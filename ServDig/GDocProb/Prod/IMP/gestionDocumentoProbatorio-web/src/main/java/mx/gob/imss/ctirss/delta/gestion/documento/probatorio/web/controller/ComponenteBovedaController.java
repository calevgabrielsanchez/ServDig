package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.dto.DatosBoveda;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DoctoReqTramite;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/boveda")
public class ComponenteBovedaController extends AbstractController{
	
	@Autowired
	DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	
	public static final String KEY_ID_TRAMITE = "idTramiteBoveda";
	public static final String KEY_TIPO_COMPONENTE = "keyTipoComponente";
	public static final String KEY_TIPO_TRAMITE ="keyIdTipoTramite";
	public static final String KEY_DATOS_BOVEDA = "keyDatosBoveda";

	@RequestMapping("/prueba")
	public String indexBoveda() {
		return "pruebaBoveda";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inicio() {
		return "inicioBoveda";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String inicioPOST(@RequestBody DatosBoveda iniciales, HttpServletRequest request, HttpSession session) {
		
		session.setAttribute(KEY_TIPO_TRAMITE,iniciales.getTipoTramite());
		session.setAttribute(KEY_TIPO_COMPONENTE, iniciales.getTipoComponente());
		session.setAttribute(KEY_ID_TRAMITE, iniciales.getIdTramite());
		session.setAttribute(KEY_DATOS_BOVEDA, iniciales);
		
		log.debug("los datos adicionales son nulos " + iniciales.getDatosAdicionales());
		
		return "inicioBoveda";
	}
	
	@RequestMapping(value = "/getDocumentos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDocumentos(@RequestBody Tramite tramite, HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		DatosBoveda datosBoveda = (DatosBoveda) session.getAttribute(KEY_DATOS_BOVEDA);
		List<DoctoReqTramite> doctosRequeridos = new ArrayList<DoctoReqTramite>();
		List<DocumentoProbatorio> doctosAdjuntados = new ArrayList<DocumentoProbatorio>();
		//si el tipo de componente fuera 2 no tiene caso buscar los requeridos ya que solo es de lectura
		if(datosBoveda == null || datosBoveda.getTipoComponente() == 1) {
			doctosRequeridos = documentoProbatorioServiceBusinessRemote.getDocumentosRequeridosPorTipoTramite(datosBoveda.getTipoTramite().longValue());
		}
		doctosAdjuntados = documentoProbatorioServiceBusinessRemote.listaDocumentosProbatoriosTramite(datosBoveda.getIdTramite());
		result.put("doctosRequeridos", doctosRequeridos);
		result.put("doctosCapturados", doctosAdjuntados);
		
		return result;
		
	}
	
	@RequestMapping(value = "/api" , method = RequestMethod.POST)
	public @ResponseBody Object guardarDocumento(
			@RequestParam("documento") MultipartFile documento,
            @RequestParam("tipoDocumento") Long tipoDocumento, 
            @RequestParam("desDocumento") String descripcionDocto,
            @RequestParam("idDocumentoPorTipo") Long idDocumentoPorTipo,
            HttpSession session) {
		
		DocumentoProbatorio docto = new DocumentoProbatorio();
		Map<String, Object> result = new HashMap<String, Object>();
		DatosBoveda datosBoveda = (DatosBoveda) session.getAttribute(KEY_DATOS_BOVEDA);
		boolean respuesta = false;
		String mensaje = "OK";
		
		if(documento.getSize() > 0) {
			log.debug("El nombre del archivo es " + documento.getOriginalFilename());
			log.debug("El tamaño del archivo es " + documento.getSize());
			docto.setNomNombreDocumento(documento.getOriginalFilename());
			docto.setDocumentoPorTipo(new DocumentoPorTipo());
			docto.getDocumentoPorTipo().setIdDocumentoPorTipo(idDocumentoPorTipo);
			docto.getDocumentoPorTipo().setDocumento(new Documento());
			docto.getDocumentoPorTipo().getDocumento().setCveIdDocumento(tipoDocumento);
			docto.getDocumentoPorTipo().getDocumento().setDesDocumento(descripcionDocto);
			
			try {
				docto.setDigitalizacion(documento.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				log.debug("Se asociara el documento al tramite " + datosBoveda.getIdTramite());
				docto = documentoProbatorioServiceBusinessRemote.guardarDocumentoProbatorioBoveda(datosBoveda,docto);
				log.debug("El documento fue guardado con el id " + docto.getIdDocumentoProbatorio() + " \n Y con el id de boveda : " + docto.getBovedaDocId());
				respuesta = true;
			} catch (DocumentoProbatorioException e) {
				mensaje = e.getSituacion();
			}
		}
		
		result.put("resultado", respuesta);
		result.put("documento", docto);
		result.put("mensaje", mensaje);
		return result;
	}
	
	@RequestMapping(value = "/api" , method = RequestMethod.DELETE)
	public @ResponseBody Object borrarDocumento(@RequestBody DocumentoProbatorio documento) {
		
		DocumentoProbatorio docto = new DocumentoProbatorio();
		Map<String, Object> result = new HashMap<String, Object>();
		boolean respuesta = false;
		String mensaje = "OK";
		
		if(documento != null) {
			try {
				
				log.debug("Voy a borrar el documento con el id " + documento.getIdDocumentoProbatorio());
				documentoProbatorioServiceBusinessRemote.eliminarDocumentoProbatorioBoveda(documento);
				respuesta = true;
			} catch (DocumentoProbatorioException e) {
				mensaje = e.getSituacion();
			}
		}
		
		result.put("resultado", respuesta);
		result.put("documento", docto);
		result.put("mensaje", mensaje);
		
		return result;
	}
	
	@RequestMapping(value = "/api" , method = RequestMethod.GET)
	public String getDocumento(@RequestParam String bovedaDocId, HttpServletResponse response, HttpSession session) {
		
		DocumentoProbatorio docto = new DocumentoProbatorio();
		DatosBoveda datosBoveda = (DatosBoveda) session.getAttribute(KEY_DATOS_BOVEDA);
		
		if(bovedaDocId != null) {
			try {
				docto = documentoProbatorioServiceBusinessRemote.getDocumentoBoveda(bovedaDocId, datosBoveda);
			} catch (DocumentoProbatorioException e) {
				e.printStackTrace();
			}
		
		}
		
		if(docto != null) {
			//preparamos los encabezados
			try {
				response.addHeader("Accept-Ranges","bytes");
				response.addHeader("Cache-Control","public");
				response.addHeader("Cache-Control","must-revalidate");
				response.addHeader("Pragma","public");
				response.addHeader("expires","0");
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline;filename = "+docto.getNomNombreDocumento());
				response.getOutputStream().write(docto.getDigitalizacion());
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
