package mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaRifSat;
import mx.gob.imss.digital.modelo.satRiss.ValidaDerechoRifSatRequest;
import mx.gob.imss.digital.modelo.satRiss.ValidaDerechoRifSatResponse;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

public abstract class BeneficioWSUtil {

    private static final Logger log = LoggerFactory.getLogger(BeneficioWSUtil.class);
    
	public static RespuestaRifSat webServicesValidaDerechoRifSat(String rfc){
		RespuestaRifSat respuestaRifSat=null;
		try {
			String uriWsValidaSatRiff = getUriWS(BeneficiosConstants.URI_WS_VALIDA_RIF_SAT);
			ValidaDerechoRifSatRequest request = new ValidaDerechoRifSatRequest();
			request.setRfc(rfc);
			
			ValidaDerechoRifSatResponse response = callWebService(
				getWebServiceTemplate(uriWsValidaSatRiff), request, ValidaDerechoRifSatResponse.class);
			respuestaRifSat = tranformarValidaDerechoRifSatResponse(response);
		} catch (Exception e) {
			//log.error("Error - webServicesValidaDerechoRifSat" ,e);
			respuestaRifSat = getRespuestaRifSatError();
		}
		return respuestaRifSat;
	}

	public static boolean esCadenaNoVacia(String cadena){
		if(StringUtils.isNotEmpty(cadena) && StringUtils.isNotBlank(cadena)){
			return true;
		}else{
			return false;
		}
	}
    
    private static <T> T callWebService(WebServiceTemplate webService, Object source, Class<T> resultClass, Class<?>[] classes) throws Exception {
		T result = null;
		try {
			StringWriter writer = new StringWriter();
			StreamResult streamResult = new StreamResult(writer);
			//log.info("********** CADENA DE ENTRADA DEL WEB-SERVICE: " + JaxbUtil.marshaller(source, classes));
			webService.sendSourceAndReceiveToResult(new StreamSource(new StringReader(JaxbUtil.marshaller(source, classes))), streamResult);
			//log.info("********** CADENA DE SALIDA DEL WEB-SERVICE: " + writer.toString());
			result = resultClass.newInstance();
			result = JaxbUtil.unmarshaller(writer.toString(), resultClass);
		} catch (InstantiationException e) {
			throw new Exception("No se ha podido crear una instancia de la clase resultado.", e);
		} catch (IllegalAccessException e) {
			throw new Exception("No se ha podido crear una instancia de la clase resultado.", e);
		}
		return result;
	}
	
	private static <T> T callWebService(WebServiceTemplate webService, Object source, Class<T> resultClass) throws Exception {
		return callWebService(webService, source, resultClass, new Class<?>[] {source.getClass()});
	}
	
	private static WebServiceTemplate getWebServiceTemplate(String uri) throws Exception {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		MessageFactory msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SaajSoapMessageFactory newSoapMessageFactory = new SaajSoapMessageFactory(msgFactory);
		
		webServiceTemplate.setMessageFactory(newSoapMessageFactory);
		webServiceTemplate.setDefaultUri(uri);
	    
	    return webServiceTemplate;
	}
	
	private static String getUriWS(String key){
		Properties properties = new Properties();
		String valor = null;
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(BeneficiosConstants.ARCHIVO_PROPERTIES);
			properties.load(inputStream);			
			valor = properties.getProperty(key);
		}catch (Exception e){
			log.error("Error al cargar las propiedades " ,e);
		}
		return valor;
	}
	
	private static RespuestaRifSat tranformarValidaDerechoRifSatResponse(ValidaDerechoRifSatResponse response){
		RespuestaRifSat respuestaRifSat = new RespuestaRifSat();		
		respuestaRifSat.setIndicadorDerecho(response.isIndicadorDerechoBeneficio());
		respuestaRifSat.setIndicadorC(response.isIndicadorApartadoC());
		if(esCadenaNoVacia(response.getFechaRif())){
			respuestaRifSat.setFechaAltaRif(DateUtils.dateToDateConFormato(
				response.getFechaRif(), BeneficiosConstants.FORMAT_DATE_GUINMEDIO_dd_MM_yyyy));
		}
		respuestaRifSat.setMotivoDeRechazo(response.getDescripcion());
		respuestaRifSat.setExito(response.getExito());
		respuestaRifSat.setClaveError(response.getClaveError());
		respuestaRifSat.setDescripcion(response.getDescripcion());		
		return respuestaRifSat;
	}
	
	private static RespuestaRifSat getRespuestaRifSatError(){
		RespuestaRifSat respuestaRifSat = new RespuestaRifSat();		
		respuestaRifSat.setMotivoDeRechazo("Error de comunicación con el servicio externo del SAT");
		respuestaRifSat.setDescripcion("Error de comunicación con el servicio externo del SAT");		
		respuestaRifSat.setExito(1);
		respuestaRifSat.setClaveError(001);
		return respuestaRifSat;
	}
	

}
