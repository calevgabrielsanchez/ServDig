package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaAsegurado;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaAseguradoResponse;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaDerechohabienteResponse;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ws.client.core.WebServiceTemplate;


@Controller
@RequestMapping(value = "/derechohabientesImg/*")
public class DerechohabienteImgController extends AbstractController {
	
	private static final Logger logger = Logger.getLogger(DerechohabienteImgController.class);
	
	@Autowired
	private WebServiceTemplate webServiceImagenAdImss;
	
	
	@RequestMapping( value = "/getFotografiaAsegurado/{calidad}/{nss}", method = {RequestMethod.POST,RequestMethod.GET} )
	public void getFotografiaAsegurado(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response
			,@PathVariable("calidad") int calidad, @PathVariable("nss") String nss){
		
		
		try{
			logger.debug("entre al metodo por el asegurado" + nss );
			
			GetFotografiaAsegurado  getFotografiaAsegurado = new GetFotografiaAsegurado();
			getFotografiaAsegurado.setCalidad(calidad);
			getFotografiaAsegurado.setNss(nss);
			String solicitudXml = JaxbUtil.marshaller(getFotografiaAsegurado);
			
			GetFotografiaAseguradoResponse aseguradoResponse = (GetFotografiaAseguradoResponse) webServiceImagenAdImss.marshalSendAndReceive(getFotografiaAsegurado);
			
			/*
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			
			webServiceImagenAdImss.sendSourceAndReceiveToResult(new StreamSource(new StringReader(solicitudXml)),result);
			String respuestaXml = writer.toString();
			
			//logger.debug("el writeer trae" +  respuestaXml);
			 * 
			 */
			//GetFotografiaAseguradoResponse aseguradoResponse = JaxbUtil.unmarshaller(finalstring, GetFotografiaAseguradoResponse.class); 
			
			/*GetFotografiaAseguradoResponse aseguradoResponse =  realizaConsulta(solicitudXml, 
					GetFotografiaAseguradoResponse.class, webServiceImagenAdImss);
					*/
			
			//logger.debug("pase la respuesta del servicio para asegurado " + aseguradoResponse.getFotografia());
			
			byte[] foto =  aseguradoResponse.getFotografia();
			
			logger.debug("la foto tiene una tamaño de " + foto.length);
	
			if( foto.length == 0 )
				throw new Exception("NO ENCONTRO LA FOTO DE "+nss);

		
			
			response.addHeader("Accept-Ranges","bytes");
			response.addHeader("Cache-Control","public");
			response.addHeader("Cache-Control","must-revalidate");
			response.addHeader("Pragma","public");
			response.setContentType("image/jpeg");
			response.addHeader("expires","0");
			response.addHeader("Content-disposition", "inline;filename=\"reporte.jpeg\""); 
			response.setContentLength(foto.length);
			response.getOutputStream().write(foto);
			response.flushBuffer();
			response.getOutputStream().close();	 
			
			
		    
		}catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		} 
	}
	
	

	@RequestMapping( value = "/getFotografiaDerechohabiente/{calidad}/{nss}/{nombre}" , method = {RequestMethod.POST,RequestMethod.GET})
	public void getFotografiaDerechohabiente(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response
			,@PathVariable("calidad") int calidad, @PathVariable("nss") String nss, @PathVariable("nombre") String nombre){
		
		try{
		
			String[] nombreCompleto = nombre.split("_");
			
			
			GetFotografiaDerechohabiente  getFotografiaDerechohabiente = new GetFotografiaDerechohabiente();
			
			if( nombreCompleto.length > 2 )
			getFotografiaDerechohabiente.setApMaterno(nombreCompleto[2]);
			
			getFotografiaDerechohabiente.setApPaterno(nombreCompleto[1]);
			getFotografiaDerechohabiente.setCalidad(calidad);
			getFotografiaDerechohabiente.setNombre(nombreCompleto[0]);
			getFotografiaDerechohabiente.setNss(nss);
			logger.debug("entre al metodo de beneficiarios con nss "+nss );
		
			String solicitudXml = JaxbUtil.marshaller(getFotografiaDerechohabiente);
			
			/*
			GetFotografiaDerechohabienteResponse derechohabienteResponse = realizaConsulta(solicitudXml, 
					GetFotografiaDerechohabienteResponse.class, webServiceImagenAdImss); 
			
			logger.debug("pase la respuesta del servicio con " + derechohabienteResponse.toString());
			*/
			
			GetFotografiaDerechohabienteResponse derechohabienteResponse = (GetFotografiaDerechohabienteResponse) webServiceImagenAdImss.marshalSendAndReceive(getFotografiaDerechohabiente);
			
			//logger.debug("pase la respuesta del servicio con " + derechohabienteResponse.getFotografia());
			
			byte[] foto =  derechohabienteResponse.getFotografia();
			
			logger.debug("la foto tiene una tamaño de " + foto.length);
			
			if( foto.length == 0 )
				throw new Exception("NO ENCONTRO LA FOTO DE "+nss);
			
			response.addHeader("Accept-Ranges","bytes");
			response.addHeader("Cache-Control","public");
			response.addHeader("Cache-Control","must-revalidate");
			response.addHeader("Pragma","public");
			response.setContentType("image/jpeg");
			response.addHeader("expires","0");
			response.addHeader("Content-disposition", "inline;filename=\"reporte.jpeg\""); 
			response.setContentLength(foto.length);
			response.getOutputStream().write(foto);
			response.flushBuffer();
			response.getOutputStream().close();	 
			
			logger.debug("termina el metodo");
			
		}catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		}
		
	}
	
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

