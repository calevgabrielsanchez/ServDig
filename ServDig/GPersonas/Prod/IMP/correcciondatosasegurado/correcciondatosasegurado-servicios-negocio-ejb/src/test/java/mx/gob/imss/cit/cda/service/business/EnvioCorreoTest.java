package mx.gob.imss.cit.cda.service.business;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import mx.gob.imss.base.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.model.AttachmentContent;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
//import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.model.derechohabientes.MailProperties;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

public class EnvioCorreoTest {
//	private transient EMailProducer envioCorreoRemote = EjbLocator.getEnvioCorreoRemote();
	
	private EmailServiceRemote emailService = EjbLocator.getEmailServiceRemote();
	@Test
	public void enviarCorreoPersonaFisica() {
		
		Map<String,String> parametrosCorreo = new HashMap<String, String>();
		
		EmailPayloadType mailWrapper = new EmailPayloadType();
		mailWrapper.setSubject("Prueba de correo");
		mailWrapper.setContent("");
		mailWrapper.setTo("neburgh@hotmail.com");		
		mailWrapper.setContentType("text/html");
		mailWrapper.setParameters(parametrosCorreo);
		
		try{
//			envioCorreoRemote.agendarCorreoElectronico(mailWrapper);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testCorreo(){
		EmailPayloadType data = new EmailPayloadType();
		data.setTo("flormaria.ezha02@gmail.com");
		data.setContent("Attachment (Comprobantes)");
		data.setSubject("Portal digital IMSS - Solicitud Atendida");
		data.setContentType("text/html");
		AttachmentContent attachment = new AttachmentContent();
		attachment.setContentDisposition("Test.txt");
		attachment.setContentType("text/plain");
		attachment.setTextBody("dato de prueba");
		data.setAttachment(attachment);
		
		
		MailProperties mailProperties;
		try {
			mailProperties = new MailProperties();
			mailProperties.setHost("11.254.12.85");
			mailProperties.setUser("g.derechohabientes");
			mailProperties.setFromAddress("gestion.derechohabientes@imss.gob.mx");
			mailProperties.setPassword("g3st10n.d3r3c#0#4b");
			mailProperties.setSubject("Correccion de dtos");
			mailProperties.setTo("flormaria.ezha02@gmail.com");
			mailProperties.setBody("te informamos que el ");
			emailService.sendMailConAdjunto(mailProperties, new byte[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
private String contenidoCorreoCDA(Solicitud solicitud) {
		
		StringBuffer body= new StringBuffer();
		String sl="<br>";
		
		body.append("En atenci\u00f3n a tu solicitud de regularizaci\u00f3n y/o correcci\u00f3n de datos personales del Asegurado en el Instituto Mexicano del Seguro Social, ");
		body.append("te informamos que el tr\u00e1mite ha sido registrado para su atenci\u00f3n en la Subdelegaci\u00f3n ");
		body.append(solicitud.getSubdelegacion().getDescripcion());
		body.append(" con fecha ");
		body.append(solicitud.getFechaSolicitudParse());
		body.append(", se env\u00eda adjunto el comprobante de recepci\u00f3n de tu solicitud.");
		body.append(sl);
		body.append("Podr\u00e1s consultar el avance del tr\u00e1mite a trav\u00e9s de IMSS Digital en la siguiente direcci\u00f3n electr\u00f3nica:");
		body.append("&lt;a href=\"https://consultas.curp.gob.mx/CurpSP/gobmx/inicio.jsp\"&gt;Servicio de Correci\u00f3n de Datos&lt;/a&gt;");
		
		return body.toString();
	}
	

}
