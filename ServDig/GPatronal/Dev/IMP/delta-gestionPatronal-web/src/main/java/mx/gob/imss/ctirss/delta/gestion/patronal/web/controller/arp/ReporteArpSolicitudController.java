
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.arp;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Jon
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date Julio 2013
 */
@Controller
@RequestMapping(value = "/arp")
public class ReporteArpSolicitudController extends AbstractController {
	
    @Autowired	
	private transient ArpBusinessRemote arpBusiness;
    
    /**
     * Este metodo procesa el reporte de ARP
     * @param folio
     * @param request
     * @return
     */
	@RequestMapping(value = "/comprobante/solicitud", method = {RequestMethod.GET, RequestMethod.POST})
	public void mostrarDocumentoARP(@RequestParam("folioSolicitud") String folioSolicitud, 
			Model model, HttpServletResponse response, HttpSession session){

		this.log.debug("**********Entre al controller para imprmir arp folioSolicitud: " + folioSolicitud);
	    	
    	byte[] archivo = null;
    	
		try {
			archivo = arpBusiness.getArpPersona(folioSolicitud);

			if(archivo != null){
				log.debug("El documento no es nulo length: " + archivo.length);
				
				response.addHeader("Accept-Ranges","bytes");
				response.addHeader("Cache-Control","public");
				response.addHeader("Cache-Control","must-revalidate");
				response.addHeader("Pragma","public");
				response.setContentType("application/pdf");
				response.addHeader("expires","0");
				response.addHeader("Content-disposition", "inline;filename=ARP.pdf"); 
				response.setContentLength(archivo.length);
				response.getOutputStream().write(archivo);
				response.getOutputStream().close();
			}else{
				this.log.debug("No hay documento");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		this.log.debug("Termina generación de ARP");	    	    		    
    }	
	
    /**
     * Este metodo procesa el reporte TIP
     * @param folio
     * @param request
     * @return
     */
	@RequestMapping(value = "/comprobante/solicitud/tip", method = {RequestMethod.GET, RequestMethod.POST})
	public void mostrarDocumentoTip(@RequestParam("folioSolicitud") String folioSolicitud, 
			Model model, HttpServletResponse response, HttpSession session){
	
		this.log.debug("**********Entre al controller para imprmir tip folioSolicitud: " + folioSolicitud);
    	
    	byte[] archivo = null;
    	
		try {
			archivo = arpBusiness.getTipPersona(folioSolicitud);

			if(archivo != null){
				log.debug("El documento no es nulo length: " + archivo.length);
				
				response.addHeader("Accept-Ranges","bytes");
				response.addHeader("Cache-Control","public");
				response.addHeader("Cache-Control","must-revalidate");
				response.addHeader("Pragma","public");
				response.setContentType("application/pdf");
				response.addHeader("expires","0");
				response.addHeader("Content-disposition", "inline;filename=TIP.pdf"); 
				response.setContentLength(archivo.length);
				response.getOutputStream().write(archivo);
				response.getOutputStream().close();
			}else{
				this.log.debug("No hay documento");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		this.log.debug("Termina generación de ARP");				
    }
    
    /**
     * Metodo de prueba para  imprimir ARP de personas morales con una persona fisica
     * @param folio
     * @param request
     * @return
     */    
	@RequestMapping(value = "/comprobante/solicitud/pm", method = {RequestMethod.GET, RequestMethod.POST})
	public void mostrarDocumentoPM(@RequestParam("folioSolicitud") String folioSolicitud, 
			Model model, HttpServletResponse response, HttpSession session){

		this.log.debug("**********Entre al controller para imprmir arp persona moral folioSolicitud: " + folioSolicitud);
	    	
    	byte[] archivo = null;
    	
		try {
			archivo = arpBusiness.getArpPersonaMoralTest(folioSolicitud);

			if(archivo != null){
				log.debug("El documento no es nulo length: " + archivo.length);
				
				response.addHeader("Accept-Ranges","bytes");
				response.addHeader("Cache-Control","public");
				response.addHeader("Cache-Control","must-revalidate");
				response.addHeader("Pragma","public");
				response.setContentType("application/pdf");
				response.addHeader("expires","0");
				response.addHeader("Content-disposition", "inline;filename=ARP.pdf"); 
				response.setContentLength(archivo.length);
				response.getOutputStream().write(archivo);
				response.getOutputStream().close();
			}else{
				this.log.debug("No hay documento");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		this.log.debug("Termina generación de ARP pm");	    	    		    
    }

    
//    /**
//     * Este metodo procesa el reporte de ARP para persona morales
//     * @param folio
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/comprobante/solicitud/pm/{folioSolicitud}", method = RequestMethod.GET)
//    public ModelAndView handleRequestInternalPM(final @PathVariable String folioSolicitud, final HttpServletRequest request) {
//    	String viewName = "arp.comprobante.solicitud.pm"; 
//    	Map<String, Object> model = null; 
//        try {
//        	
//        	this.log.debug("**********Entre al controller para imprmir arp persona moral folioSolicitud: " + folioSolicitud);
//        	
//	    	if(StringUtils.isBlank(folioSolicitud)) {
//	    	    request.setAttribute("msgError", "No se ha pasado el folio de la solicitud!");
//	    	    viewName = "mensajes"; // NOPMD
//	    	}
//	    	
//	    	model = arpBusiness.getArpModelPersona(folioSolicitud);
//			model.put("SUBREPORT_DIR", "/comprobantes/");
//        } 
//        catch (IOException e) { // TODO To load header image
//            log.error("Error al leer la imagen de logo IMSS para el reporte interno.", e);
//	    } catch (Exception e) {
//	    	log.error("Error.", e);
//	        request.setAttribute("mensaje", e.getMessage());
//	        viewName = "mensajes";
//	    }
//        
//		return new ModelAndView(viewName, model);
//    }
//    
//  /**
//  * Este metodo procesa el reporte de ARP
//  * @param folio
//  * @param request
//  * @return
//  */
// @RequestMapping(value = "/comprobante/solicitud/{folioSolicitud}", method = RequestMethod.GET)
// public ModelAndView handleRequestInternalArp(final @PathVariable String folioSolicitud, final HttpServletRequest request) {
// 	String viewName = "arp.comprobante.solicitud."; 
// 	Map<String, Object> model = null; 
//     try {
//     	
//     	this.log.debug("**********Entre al controller para imprmir arp folioSolicitud: " + folioSolicitud);
//     	
//	    	if(StringUtils.isBlank(folioSolicitud)) {
//	    	    request.setAttribute("msgError", "No se ha pasado el folio de la solicitud!");
//	    	    viewName = "mensajes"; // NOPMD
//	    	}
//	    	
//	    	model = arpBusiness.getArpModelPersona(folioSolicitud);
//	    	
//			model.put("SUBREPORT_DIR", "/comprobantes/");
//
//	    	viewName = viewName + model.get("viewName");	    	
//	    	
//     } 
//     catch (IOException e) { // TODO To load header image
//         log.error("Error al leer la imagen de logo IMSS para el reporte interno.", e);
//	    } catch (Exception e) {
//	    	log.error("Error.", e);
//	        request.setAttribute("mensaje", e.getMessage());
//	        viewName = "mensajes";
//	    }
//     
//		return new ModelAndView(viewName, model);
// }    
//    
//    /**
//     * Este metodo procesa el reporte TIP
//     * @param folio
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/comprobante/solicitud/tip{folioSolicitud}", method = RequestMethod.GET)
//    public ModelAndView handleRequestInternalTip(final @PathVariable String folioSolicitud, final HttpServletRequest request) {
//    	String viewName = "arp.comprobante.solicitud.tip"; 
//    	Map<String, Object> model = null; 
//        try {
//        	
//        	this.log.debug("**********Entre al controller para imprmir TIP folioSolicitud: " + folioSolicitud);
//        	
//	    	if(StringUtils.isBlank(folioSolicitud)) {
//	    	    request.setAttribute("msgError", "No se ha pasado el folio de la solicitud!");
//	    	    viewName = "mensajes"; // NOPMD
//	    	}
//	    	
//	    	model = arpBusiness.getTipModelPersona(folioSolicitud);
//        } 
//        catch (IOException e) { // TODO To load header image
//            log.error("Error al leer la imagen de logo IMSS para el reporte interno.", e);
//	    } catch (Exception e) {
//	    	log.error("Error.", e);
//	        request.setAttribute("mensaje", e.getMessage());
//	        viewName = "mensajes";
//	    }
//        
//		return new ModelAndView(viewName, model);
//    }	

}
