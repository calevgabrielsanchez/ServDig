package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

@Stateless(name = "envioCorreoElectronicoBusiness", mappedName = "envioCorreoElectronicoBusiness")
public class EnvioCorreoElectronicoBusiness extends AbstractServiceBusiness implements EnvioCorreoElectronicoBusinessRemote{
	
	private static final Logger log = LoggerFactory
			.getLogger(EnvioCorreoElectronicoBusiness.class);
	
	private JavaMailSenderImpl getPropertiesMail() throws Exception{
		Properties prop = new Properties();
		JavaMailSenderImpl mail = new JavaMailSenderImpl();
		Properties envio = new Properties();
		
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("mailConfiguration.properties");
			prop.load(input);
			
			log.info("las propiedades seteadas son use ["+ prop.getProperty("USER_CORREO") +
					"] pass [" + prop.getProperty("PASSWORD_CORREO")+ "] autenticacion ["
					+ prop.getProperty("AUTENTICACION") + "] HOST [" +prop.getProperty("HOST_MEXICO")+"] puerto [" 
					 + prop.getProperty("PUERTO") + "] protocolo ["
					 +prop.getProperty("PROTOCOLO")+ "] ");
			
			Boolean autenticacion = new Boolean(prop.getProperty("AUTENTICACION"));
			if(autenticacion){
				mail.setUsername(prop.getProperty("USER_CORREO"));
				mail.setPassword(prop.getProperty("PASSWORD_CORREO"));
				envio.setProperty("mail.smtps.auth", "true");
			}
			else{
				envio.setProperty("mail.smtps.auth", "false");
			}
			mail.setHost(prop.getProperty("HOST_MEXICO"));
			mail.setPort(new Integer(prop.getProperty("PUERTO")).intValue());
			mail.setProtocol(prop.getProperty("PROTOCOLO"));
			envio.setProperty("mail.transport.protocol", prop.getProperty("PROTOCOLO"));
			mail.setJavaMailProperties(envio);

		}catch (Exception e){
			log.error("error al setear las propiedades");
			throw e;
		}
		return mail;

	}
	
	private String getDefaultMailSender(){
		Properties prop = new Properties();
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("mailConfiguration.properties");
			prop.load(input);
			return prop.getProperty("FROMIMSS_CORREO");
		}catch (Exception e){
			log.error("error al setear las propiedades");
		}
		
		return null;
		
	}
	
	@Async
	@Override
	public void enviarCorreo(CorreoElectronicoDTO correoElectronicoDTO) throws Exception{
		try{
			JavaMailSenderImpl mail = this.getPropertiesMail();
			MimeMessage message = mail.createMimeMessage();
			message.setFrom(new InternetAddress(this.getDefaultMailSender()));
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(correoElectronicoDTO.getAsunto());
			helper.setText(correoElectronicoDTO.getCuerpoCorreo(), true);
			helper.setTo(correoElectronicoDTO.getCorreoPara());
			
			if(correoElectronicoDTO.getCorreoCopia()!= null){
				helper.setCc(correoElectronicoDTO.getCorreoCopia());
			}
			
			ByteArrayResource byteAr;
			if(correoElectronicoDTO.getAdjuntos() != null && !correoElectronicoDTO.getAdjuntos().isEmpty()){
		        for (Map.Entry<String, byte[]> adjunto : correoElectronicoDTO.getAdjuntos().entrySet()) {
		        	byteAr = new ByteArrayResource(adjunto.getValue());
					helper.addAttachment(adjunto.getKey(), byteAr);
		        }
			}
			mail.send(message);
			}catch(Exception e){
				log.error("error al enviar el corrreo", e);
		}
	}
	
	@Async
	@Override
	public void enviarCorreo(CorreoElectronicoDTO correoElectronicoDTO, String from) throws Exception{
		try{
			JavaMailSenderImpl mail = this.getPropertiesMail();
			MimeMessage message = mail.createMimeMessage();
			message.setFrom(new InternetAddress(from));
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(correoElectronicoDTO.getAsunto());
			helper.setText(correoElectronicoDTO.getCuerpoCorreo(), true);
			helper.setTo(correoElectronicoDTO.getCorreoPara());
			
			if(correoElectronicoDTO.getCorreoCopia()!= null){
				helper.setCc(correoElectronicoDTO.getCorreoCopia());
			}
			
			ByteArrayResource byteAr;
			if(correoElectronicoDTO.getAdjuntos() != null && !correoElectronicoDTO.getAdjuntos().isEmpty()){
		        for (Map.Entry<String, byte[]> adjunto : correoElectronicoDTO.getAdjuntos().entrySet()) {
		        	byteAr = new ByteArrayResource(adjunto.getValue());
					helper.addAttachment(adjunto.getKey(), byteAr);
		        }
			}
			mail.send(message);
			}catch(Exception e){
				log.error("error al enviar el corrreo", e);
		}
	}

}
