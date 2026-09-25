/**
 * delta-gestionPatronal-web26/02/2012
 * mx.gob.imss.ctirss.delta.gestion.patronal.web.controller26/02/2012
 * DomicilioController.java
 * 26/02/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lucio Duran Silva
 * Instituto Mexicano del Seguro Social
 */



@Controller
@RequestMapping(value="/domicilio/registro")
public class RegistroDomicilioController extends AbstractController {
	
		
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String  inicio(Model model){
		return "domicilio.registro";
	}
	
	
	@RequestMapping(value="/embebed" ,method=RequestMethod.GET)
	public String  inicioEmbebed(Model model){
		return "domicilio.registroEmbebido";
	}
	
	
	
	@RequestMapping(value="/codigopostal/buscar", method=RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object>  localizarDomicilioAproximado(@RequestParam String codigoPostal, HttpServletResponse response ){
//		this.log.debug("localizarDomicilioAproximado" + codigoPostal);
//		GeocoderDomicilio geocoderDomicilio = new GeocoderDomicilio();
//		geocoderDomicilio.setCodigoPostal(codigoPostal);
//		
//		Map result = new HashMap<String, Object>();
//		List<GeocoderDomicilio> domicilios;
//		try {
//			domicilios = domicilioServiceBusinessRemote.localizaDomicilioAproximadoPorCodigoPostal(geocoderDomicilio);
//			//domicilios = this.getDomicilios();
//			result.put("domicilios", domicilios);
//		} catch (DomicilioNoLocalizadoException e) {
//			this.procesarErrorDeNegocio(e, result, response);
//			this.log.error(e.getMessage(), e);
//		}
//		
//		
//		
//		
		return null;
		
	}
	
	
	
	
	
//	public List<GeocoderDomicilio> getDomicilios() throws DomicilioNoLocalizadoException{
//		
//		List<GeocoderDomicilio> domicilios = new ArrayList<GeocoderDomicilio>();
//		GeocoderDomicilio domicilio = null;
//		for ( int i = 0 ; i < 5 ; i++){
//			domicilio = new GeocoderDomicilio("55714" , "1" , "SAN LORENZO TETLIXTAC" , "2" , "SAN FRANCISCO COACALCO" , "3", "COACALCO DE BERRIOZABAL", "4", "MEXICO");
//			domicilios.add(domicilio);
//			
//		}
//		
//		return domicilios;
//	}

}
