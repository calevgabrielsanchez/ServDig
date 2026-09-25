package mx.gob.imss.ctirss.correccion.web.controller.visor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrtTramitePresentado;
import mx.gob.imss.ctirss.correccion.firma.service.interfaces.Archivo;
import mx.gob.imss.ctirss.correccion.firma.service.interfaces.FirmaElectronicaService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.generico.SeguimientoPromocionGenericoController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value="/visor")
public class VisorController extends AbstractController{
	
	
	private final static Logger logger = Logger.getLogger(VisorController.class);
	
	@Autowired
	private FirmaElectronicaService firmaElectronicaService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		return determinaURL(request, "visor/buscaTramite", "visor/buscaTramite/patron");
	}

	@RequestMapping(value="/buscaTramitePresentado", method=RequestMethod.POST)
	public @ResponseBody CrtTramitePresentado buscaTramite(@RequestBody CrtTramitePresentado datosBusqueda, HttpServletResponse response,HttpServletRequest request){
		UserSession usrSession = super.getUsuarioFirmado(request);
		datosBusqueda = firmaElectronicaService.buscaTramitePresentado(datosBusqueda, usrSession);
		return datosBusqueda;
	}
	
	
	@RequestMapping(value="/adjuntarArchivos", method=RequestMethod.POST)
	public String adjuntarArchivos(HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses){
		
		//Se inicia la recuperacion del archivo adjunto
		logger.info("Se envia el archivo adjunto ");
		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("Filedata");
		logger.info("Nombre archivo "+file.getName());
		logger.info("Archivos "+file);
		byte[] bytes;
		try {
			bytes = file.getBytes();
			String acusePdf = org.apache.soap.encoding.soapenc.Base64.encode(bytes);
			Archivo archivo=new Archivo();
			archivo.setNombre(file.getOriginalFilename());
			archivo.setBuffer(acusePdf);							
			firmaElectronicaService.guardarArchivoFirmado(request.getParameter("idTramite"), archivo);			
			ses.setAttribute("archivoAdjunto", "Envio Exitoso");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ses.setAttribute("archivoAdjunto", "ERROR: No se pudo adjuntar el archivo, favor de reintentar");
			e.printStackTrace();
		}		
		return "refArchivo";
	}
	
}
