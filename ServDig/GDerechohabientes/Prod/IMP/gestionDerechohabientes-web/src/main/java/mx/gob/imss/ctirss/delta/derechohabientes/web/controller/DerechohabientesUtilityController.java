/**
 * RegistroDerechohabientesController.java
 * @author JUAN MANUEL MARQUEZ
 * @package mx.gob.imss.ctirss.delta.derechohabientes.web.controller
 * @project derechohabientes-web	
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;



import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaAsegurado;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaAseguradoResponse;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaDerechohabienteResponse;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ws.client.core.WebServiceTemplate;


/**
 * @author Juan Manuel Marquez
 * @company Novutek
 * @date 05/03/2012
 */
@Controller
@RequestMapping(value = "/derechohabientesUtil/*")
public class DerechohabientesUtilityController extends AbstractController {
	
	private static final Logger logger = Logger.getLogger(DerechohabientesUtilityController.class);
	
	@Autowired
	LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote personaServicesExternos;

	@Autowired
	private WebServiceTemplate webServiceImagenAdImss;
	
	
	/**
	 * Valida el CURP en RENAPO 
	 * @param fisica
	 * @return
	 */
	@RequestMapping( value = "/validaCurpRenapo", method = RequestMethod.POST, headers="Accept=application/json; charset=ISO-8859-1")
	public @ResponseBody RespuestaJSON<Boolean> validaCurpRenapo(@RequestBody Fisica fisica) {
		RespuestaJSON<Boolean> respuesta = new RespuestaJSON<Boolean>();
		Boolean resultado = null;
		
		try {
			resultado = personaServicesExternos.localizarPersonaFisicaEnRENAPOByCURP(fisica);
			respuesta.setEstado(true);
			if(!resultado)
				respuesta.setMensaje("Los datos personales no coinciden con la curp establecida");
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			resultado = false;
			respuesta.setMensaje("No fue posible validar a la persona en RENAPO por lo cual sera calificado por el IMSS");
			respuesta.setEstado(false);
		}catch (ErrorComparacionDatosRENAPOException e) {
			resultado = false;
			respuesta.setMensaje("No fue posible validar a la persona en RENAPO por lo cual sera calificado por el IMSS");
			respuesta.setEstado(false);
		}catch ( Exception  e){
			logger.error("ocurrio un error inesperado",e);
			resultado = false;
			respuesta.setMensaje("No fue posible validar a la persona en RENAPO por lo cual sera calificado por el IMSS");
			respuesta.setEstado(false);
		}
		
		respuesta.setModelo(resultado);
		
		return respuesta;
	}

	public LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote getPersonaServicesExternos() {
		return personaServicesExternos;
	}

	public void setPersonaServicesExternos(
			LocalizarPersonaFisicaEnEntidadesExternasServiceBusinessRemote personaServicesExternos) {
		this.personaServicesExternos = personaServicesExternos;
	}


	
//	@RequestMapping( value = "/getFotografiaAsegurado/{calidad}/{nss}", method = RequestMethod.GET )
//	public void getFotografiaAsegurado(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response
//			,@PathVariable("calidad") int calidad, @PathVariable("nss") String nss){
//		
//		
//		try{
//			log.debug("entre al metodo por el asegurado" + nss );
//			
//			
//			GetFotografiaAsegurado  getFotografiaAsegurado = new GetFotografiaAsegurado();
//			getFotografiaAsegurado.setCalidad(calidad);
//			getFotografiaAsegurado.setNss(nss);
//			String solicitudXml = JaxbUtil.marshaller(getFotografiaAsegurado);
//			
//			GetFotografiaAseguradoResponse aseguradoResponse =  realizaConsulta(solicitudXml, 
//					GetFotografiaAseguradoResponse.class, webServiceImagenAdImss);
//			
//			log.debug("pase la respuesta del servicio con " + aseguradoResponse.toString());
//			
//			byte[] foto =  aseguradoResponse.getFotografia();
//			
//			log.debug("la foto tiene una tamaño de " + foto.length);
//	
//			if( foto.length == 0 )
//				throw new Exception("NO ENCONTRO LA FOTO DE "+nss);
//
//		
//			
//			response.addHeader("Accept-Ranges","bytes");
//			response.addHeader("Cache-Control","public");
//			response.addHeader("Cache-Control","must-revalidate");
//			response.addHeader("Pragma","public");
//			response.setContentType("image/jpeg");
//			response.addHeader("expires","0");
//			response.addHeader("Content-disposition", "inline;filename=\"reporte.jpeg\""); 
//			response.setContentLength(foto.length);
//			response.getOutputStream().write(foto);
//			response.flushBuffer();
//			response.getOutputStream().close();	 
//			
//			log.debug("termina el metodo");
//		    
//		}catch(Exception e){
//			e.printStackTrace();
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
//		} 
//		
//		
//		
//	}
//	
//	
//	@RequestMapping( value = "/getFotografiaDerechohabiente/{calidad}/{nss}/{nombre}")
//	public void getFotografiaDerechohabiente(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response
//			,@PathVariable("calidad") int calidad, @PathVariable("nss") String nss, @PathVariable("nombre") String nombre){
//		
//		try{
//		
//			String[] nombreCompleto = nombre.split("_");
//			
//			
//			GetFotografiaDerechohabiente  getFotografiaDerechohabiente = new GetFotografiaDerechohabiente();
//			
//			if( nombreCompleto.length > 2 )
//			getFotografiaDerechohabiente.setApMaterno(nombreCompleto[2]);
//			
//			getFotografiaDerechohabiente.setApPaterno(nombreCompleto[1]);
//			getFotografiaDerechohabiente.setCalidad(calidad);
//			getFotografiaDerechohabiente.setNombre(nombreCompleto[0]);
//			getFotografiaDerechohabiente.setNss(nss);
//			log.debug("entre al metodo con nss "+nss );
//		
//			String solicitudXml = JaxbUtil.marshaller(getFotografiaDerechohabiente);
//			log.debug("pase la transformacion de objeros");
//			
//			GetFotografiaDerechohabienteResponse derechohabienteResponse = realizaConsulta(solicitudXml, 
//					GetFotografiaDerechohabienteResponse.class, webServiceImagenAdImss); 
//			
//			log.debug("pase la respuesta del servicio con " + derechohabienteResponse.toString());
//			
//			byte[] foto =  derechohabienteResponse.getFotografia();
//			
//			log.debug("la foto tiene una tamaño de " + foto.length);
//			
//			if( foto.length == 0 )
//				throw new Exception("NO ENCONTRO LA FOTO DE "+nss);
//			
//			response.addHeader("Accept-Ranges","bytes");
//			response.addHeader("Cache-Control","public");
//			response.addHeader("Cache-Control","must-revalidate");
//			response.addHeader("Pragma","public");
//			response.setContentType("image/jpeg");
//			response.addHeader("expires","0");
//			response.addHeader("Content-disposition", "inline;filename=\"reporte.jpeg\""); 
//			response.setContentLength(foto.length);
//			response.getOutputStream().write(foto);
//			response.flushBuffer();
//			response.getOutputStream().close();	 
//			
//			log.debug("termina el metodo");
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
//		}
//		
//	}
	
	/**
     * MEtodo utilitario que realiza las consultas de webservices
     * @param entrada xml de entrada para la consulta del web service
     * @param salida objeto resultante del webservice
     * @param template el template del webservice a utilizar
     * @return el objeto regresado por el webservice
     * @throws JAXBException  error  al parsear la respuesta
     */
    private <T> T realizaConsulta(String entrada, Class<T> salida, WebServiceTemplate template) throws JAXBException {
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);        
        template.sendSourceAndReceiveToResult(new StreamSource(new StringReader(entrada)), 
                result);
        
        
        String comprobanesXml = writer.toString();
//        LOGGER.info("Respuesta peticion  {}", comprobanesXml);
        return JaxbUtil.unmarshaller(comprobanesXml, salida);
    }
	
	
}
