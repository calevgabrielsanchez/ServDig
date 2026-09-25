package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ReporteBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/tarjetaNSS")
public class ConsultaTarjetaNSSController extends AbstractController{
	
	@Autowired
	private ReporteBusinessRemote reporteBusinessRemote;
	
	@RequestMapping(value = "/obtener/{nss}")
	public void getCredencialNSS(@PathVariable(value = "nss") String nss,HttpServletRequest request, HttpServletResponse response) {
		
		byte[] imagen = reporteBusinessRemote.getCredencialNSS(nss);
		
		if(imagen != null) {
			try{
				response.addHeader("Accept-Ranges","bytes");
				response.addHeader("Cache-Control","public");
				response.addHeader("Cache-Control","must-revalidate");
				response.addHeader("Pragma","public");
				response.setContentType("image/png");
				response.addHeader("expires","0");
				response.addHeader("Content-disposition", "inline;filename=\"tarjetaNSS" +nss + ".png\""); 
				response.setContentLength(imagen.length);
				response.getOutputStream().write(imagen);
				response.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return;
	}
	
	@RequestMapping(value = "/mostrar/{nss}")
	public String testImage(@PathVariable(value = "nss") String nss,HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("nss", nss);
		
		return "mostrarTarjetaNSS";
	}
}
