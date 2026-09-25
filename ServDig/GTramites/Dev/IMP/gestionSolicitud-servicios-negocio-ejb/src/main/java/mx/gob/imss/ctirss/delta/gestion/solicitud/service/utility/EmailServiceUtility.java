package mx.gob.imss.ctirss.delta.gestion.solicitud.service.utility;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.CollectionUtils;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.common.EmailDataWrapper;
import mx.gob.imss.utilmail.service.MailService;
import mx.gob.imss.utilmail.service.impl.MailServiceImpl;

@Stateless(mappedName = "emailServiceUtility")
public class EmailServiceUtility extends AbstractServiceUtility implements
		EmailServiceUtilityLocal {
	
	@EJB( mappedName = "envioCorreoElectronicoBusiness")
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusiness; 
	
	@Override
	public void enviarCorreoSimple(EmailDataWrapper emailData) {
		
		validarDatosEntrada(emailData, false);
		
		MailService mailService = new MailServiceImpl();
		
		try {
		getDefaultMailProperties(emailData);
		
		mailService.sendMail(emailData.getMailServer(),
				emailData.getUsername(), emailData.getPassword(),
				emailData.getAsunto(), emailData.getFromAddress(),
				emailData.getToAddress(), emailData.getCcAddress(),
				emailData.getBccAddress(), emailData.getMensaje());
		}catch(Exception e){
			log.error("error al mandar el correo no arroja excepción", e);
		}
	}
	
	@Override
	public void enviarCorreoAdjuntos(EmailDataWrapper emailData) {
	
		validarDatosEntrada(emailData, true);
		
		MailService mailService = new MailServiceImpl();
		try {
		getDefaultMailProperties(emailData);
		
		mailService.sendMailWithAttaches(emailData.getMailServer(),
				emailData.getUsername(), emailData.getPassword(),
				emailData.getAsunto(), emailData.getFromAddress(),
				emailData.getToAddress(), emailData.getCcAddress(),
				emailData.getBccAddress(), emailData.getMensaje(),
				emailData.getNombreArchivosAdjuntos(),
				emailData.getArchivosAdjuntos(),
				emailData.getContentTypeAchivosAdjuntos());
		}catch(Exception e){
			log.error("error al mandar el correo no arroja excepción", e);
		}
	}
	
	private void validarDatosEntrada(EmailDataWrapper emailData,
			boolean validarAdjuntos) {
		
		if (StringUtils.isBlank(emailData.getToAddress())) {
			throw new IllegalArgumentException(
					"El destinatario es requerido para poder enviar el correo");
		}
		
		if (StringUtils.isBlank(emailData.getAsunto())) {
			throw new IllegalArgumentException(
					"El asunto del correo es requerido para poder enviar el correo");
		}
		
		if (StringUtils.isBlank(emailData.getMensaje())) {
			throw new IllegalArgumentException(
					"El mensaje del correo es requerido para poder enviar el correo");
		}
		
		if (validarAdjuntos) {
			if (CollectionUtils.isEmpty(emailData.getArchivosAdjuntos())
					|| CollectionUtils.isEmpty(emailData.getContentTypeAchivosAdjuntos())
					|| CollectionUtils.isEmpty(emailData.getNombreArchivosAdjuntos())) {
				throw new IllegalArgumentException(
						"La información necesaria para adjuntar los archivo no está completa");
			}
		}
	}
	
	/**
	private void getDefaultMailProperties(EmailDataWrapper emailData) {
		Properties prop = new Properties();
		
		try {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			InputStream input = classLoader
					.getResourceAsStream("mailConfiguration.properties");
			prop.load(input);
			
			if (StringUtils.isBlank(emailData.getMailServer())) {
				emailData.setMailServer(prop.getProperty("HOST_MEXICO"));
			}
			
			if (StringUtils.isBlank(emailData.getUsername())) {
				emailData.setUsername(prop.getProperty("USER_CORREO"));
			}
			
			if (StringUtils.isBlank(emailData.getPassword())) {
				emailData.setPassword(prop.getProperty("PASSWORD_CORREO"));
			}
			
			if (StringUtils.isBlank(emailData.getFromAddress())) {
				emailData.setFromAddress(prop.getProperty("FROMIMSS_CORREO"));
			}
			
		} catch (IOException e) {
			this.log.error(e);
		}

	}
	**/
	
	
	private void getDefaultMailProperties(EmailDataWrapper emailData) throws Exception {
		
		
		try {
			JavaMailSenderImpl mail = 	envioCorreoElectronicoBusiness.getPropertiesMail(emailData.getFromAddress());
			
			emailData.setMailServer(mail.getHost());
			emailData.setUsername(mail.getUsername());
			emailData.setPassword(mail.getPassword());
			if(StringUtils.isEmpty(emailData.getFromAddress()))
					emailData.setFromAddress(envioCorreoElectronicoBusiness.getDefaultMailSender());
			
			
		} catch (Exception e) {
			this.log.error("Error al setear las propiedades del correo desde base de datos" , e);
			throw e;
		}

	}
	
}