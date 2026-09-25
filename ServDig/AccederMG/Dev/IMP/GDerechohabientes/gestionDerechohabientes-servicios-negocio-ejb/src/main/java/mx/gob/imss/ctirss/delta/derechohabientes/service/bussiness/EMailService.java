package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.model.AttachmentContent;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.framework.base.model.MailComponent;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MailServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabientes.MailProperties;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.utilmail.service.MailService;
import mx.gob.imss.utilmail.service.impl.MailServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Stateless( name = "eMailService", mappedName = "eMailService")
public class EMailService extends AbstractServiceBusiness implements EMailServiceLocal,EmailServiceRemote  {
	@EJB(mappedName = "EMailQProducer")
	private EMailProducer eMailProducer;
	@EJB
	private MailServiceBusinessRemote mailServiceBusiness;

	private MailService mailService= new MailServiceImpl();

	private final String ASUNTO_VIGENCIA = "IMSS DIGITAL REPORTE DE VIGENCIA DE DERECHOS"; 
	private final String ASUNTO_BAJA_NORMATIVA = "IMSS DIGITAL NOTIFICACION DE BAJA DE DERECHOHABIENTE";
	private static final String REPLY_MAIL = "noreply@imss.gob.mx";
	private static final String REPLY_MAIL_NAME = "NO REPLY";
	private static final String DERECHOHAB_TEMPLATE = "mail/template/templateTramiteDerechohabiente.vm";
	private static final String FROM_DERECHOHAB_NAME = "IMSS Digital";
	private static final String CONTENT_TYPE = "text/html";
	private static final String MIME_PDF = "application/pdf";

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.EMailServiceLocal#sendSimpleMail(mx.gob.imss.ctirss.delta.model.derechohabientes.MailProperties)
	 */
	@Override
	public void sendSimpleMail(MailProperties mailProperties) throws Exception {
		MailService mailServiceL= new MailServiceImpl();

		mailServiceL.sendMail(mailProperties.getHost(),
				mailProperties.getUser(), mailProperties.getPassword(),
				mailProperties.getSubject(), mailProperties.getFromAddress(),
				mailProperties.getTo(), mailProperties.getCc(), mailProperties
				.getBcc(), mailProperties
				.getBody());
	}

	@Override
	public void sendMailConAdjunto(MailProperties mailProperties, byte[]adjunto) throws Exception{
		MailService mailServiceL= new MailServiceImpl();
		this.log.debug("voy a generar el correo" + mailProperties.toString());
		mailServiceL.sendMailWithAttach(mailProperties.getHost(),
				mailProperties.getUser(), mailProperties.getPassword(),
				mailProperties.getSubject(), mailProperties.getFromAddress(),
				mailProperties.getTo(), mailProperties.getCc(), mailProperties
				.getBcc(), mailProperties
				.getBody(), "reporteVigencia.pdf", adjunto, "application/pdf");
		

	}



	public MailService getMailService() {
		return mailService;
	}



	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * Metodo encargado de setear las propiedades de configuración del envio de correo
	 * Host servidor, remitente
	 * @return MailProperties con la información seteada 
	 * @throws Exception
	 */
	@Override
	public MailProperties getDefaultMailProperties() throws Exception{
		MailProperties mailProperties = new MailProperties();
		Properties prop = new Properties();
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("mailConfiguration.properties");
			prop.load(input);
			mailProperties.setHost(prop.getProperty("HOST_MEXICO"));
			mailProperties.setUser(prop.getProperty("USER_DERECHOHABIENTES"));
			mailProperties.setPassword(prop.getProperty("PASSWORD_DERECHOHABIENTES"));
			mailProperties.setFromAddress(prop.getProperty("FROMIMSS_DERECHOHABIENTES_NOTIFICACION"));

		}catch (Exception e){
			this.log.error("error al cargar las propiedades " ,e);
			throw e;
		}
		return mailProperties;

	}

	/**
	 * Metodo encargado de enviar un correo electronico con el reporte de vigencia
	 * @param asignacionNSS con la info del asegurado incluyendo el correo electronico
	 * @param atachDocto array de bytes con el pdf 
	 * @param url en caso de que se utilice la URL de descarga de documentos
	 * @throws Exception
	 */

	@Override
	public void enviaCorreoReporteVigenciaDerechos(AsignacionNSS asignacionNSS, byte[] atachDocto, String url) throws Exception{
		
		/*
		MailProperties mailProperties = this.getDefaultMailProperties();
		mailProperties.setSubject(this.ASUNTO_VIGENCIA);
		mailProperties.setTo(asignacionNSS.getCorreoElectronico().getCorreo());
		mailProperties.setBody(this.contenidoCorreoVigencia(asignacionNSS));
		this.sendMailConAdjunto(mailProperties, atachDocto);
		*/
		
				
		
		log.debug("llegue a la llamada de enviar correo JC");
		try{
		JavaMailSenderImpl mail = this.getPropertiesMail();
		log.debug("pase los valores default");
		MimeMessage message = mail.createMimeMessage();
		message.setFrom(new InternetAddress(this.getDefaultMailSenderDH()));
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setSubject(this.ASUNTO_VIGENCIA);
		helper.setTo(asignacionNSS.getCorreoElectronico().getCorreo());
		helper.setText(this.contenidoCorreoVigencia(asignacionNSS), true);
		
		ByteArrayResource byteAr = new ByteArrayResource(atachDocto);
		helper.addAttachment("reporteVigenciaDerechos.pdf", byteAr);
		mail.send(message);
		log.error("pase el envio todo OK");
		}catch(Exception e){
			log.error("error al enviar el corrreo", e);
		}
		
		

	}
	
	/**
	 * Metodo encargado de enviar un correo electronico de prueba, para consumir desde un EjbRemoto
	 * @param toEmail a quien se le notifica por correo
	 * @param ccEmail con copia a quien se notifica
	 * @param subject asunto del correo
	 * @param body contenido html del correo
	 * @param adjuntos documentos Adjuntos
	 * @throws Exception
	 */
	
	@Override
	public void enviaCorreoConDocumentoAdjunto(final String toEmail, final String ccEmail, final String subject, final String body, final Map<String,byte[]> adjuntos) throws Exception {
		log.debug(" -- Accediendo al servicio de envio de correo con adjunto");
		try {
			JavaMailSenderImpl mail = this.getPropertiesMail();
			final boolean multipart = adjuntos != null && adjuntos.size() > 0;
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setFrom(new InternetAddress(this.setFromMailSender()));
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, multipart,"UTF-8");
					helper.setSubject(subject);
					helper.setTo(toEmail);
					
					if(ccEmail!=null)
						helper.setCc(ccEmail);
					
					helper.setText(body, true);
		
					if (adjuntos != null) {
						 for (Map.Entry<String, byte[]> entry : adjuntos.entrySet()) {
							 ByteArrayResource file = new ByteArrayResource(entry.getValue());
							 helper.addAttachment(entry.getKey(), file);
							 log.error(" -- Adjuntando fichero: "+entry.getKey());
						 }	 
					}
				}
				private String setFromMailSender(){
					Properties prop = new Properties();
					try{
						ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
						InputStream input = classLoader.getResourceAsStream("mailConfigurationDH.properties");
						prop.load(input);
						return prop.getProperty("FROMIMSS_DERECHOHABIENTES_NOTIFICACION");
					}catch (Exception e){
						log.error("error al setear las propiedades");
					}
					return null;
				}
			};
			
			mail.send(preparator);
			this.log.error(" -- Email enviado correctamente a: " + toEmail);
		} catch (Exception e) {
			log.error("-- Error al enviar el corrreo", e);
		}
	}

	/**
	 * Metodo para enviar correo de baja nbormativa con los documentos resultantes como adjuntos
	 * @param derechohabiente
	 * @param documentos
	 * @throws Exception
	 */
	@Override
	public void enviarCorreoBajaNormativa(Derechohabiente derechohabiente, byte[] documentos) throws Exception {
		String dest = null;
		String cc = null;

		if(derechohabiente.getAsignacionNSS().getCorreoElectronico() != null && derechohabiente.getAsignacionNSS().getCorreoElectronico().getCorreo() != null) {
			dest = derechohabiente.getAsignacionNSS().getCorreoElectronico().getCorreo();
			log.debug("El correo que se encontro para el asegurado es: " + dest);
		}
		cc = derechohabiente.getCorreoElectronico().getCorreo();
		log.debug("El correo que se encontro para el beneficiario es: " + cc);
		if(dest == null) {
			dest = new String(cc);
			cc = null;
			
			log.debug("No se encontro correo para el asegurado por lo que solo se le enviara correo al beneficiario");
		}

		if(dest != null || cc != null) {
			MailProperties mailProperties = this.getDefaultMailProperties();
			mailProperties.setSubject(this.ASUNTO_BAJA_NORMATIVA);
			mailProperties.setTo(dest);
			if(StringUtils.isBlank(cc)) {
				mailProperties.setCc(cc);
			}
			mailProperties.setBody(this.contenidoCorreoBajaNormativa(derechohabiente));
			this.sendMailConAdjunto(mailProperties, documentos);
		} 
	}

	private String contenidoCorreoBajaNormativa(Derechohabiente derechohabiente) {
		StringBuffer body= new StringBuffer();
		String sl="<br>";
		String sp=" ";

		body.append(sl);
		body.append("Estimado Derechohabiente :");		
		body.append(sl);
		body.append(this.getNombreCompleto(derechohabiente.getAsignacionNSS()));
		body.append(sl);
		body.append("con Número de Seguridad social :" +derechohabiente.getAsignacionNSS().getNss());
		body.append(sl);
		body.append("Derivado de las revisiones que se hacen a los tr&aacute;mites realizados mediante IMSS Digital, "
				+ " se ha detectado que el registro del beneficiario " );
		body.append(this.getNombreCompleto(derechohabiente));
		body.append(" no cumple con los requisitos, por lo que ha sido dado(a) de baja.");
		body.append(sl);
		body.append("Se adjuntan los documentos que avalan la baja del derechohabiente.");
		body.append("");	
		body.append(sl);
		body.append(sl);
		body.append("Este correo ha sido generado automáticamente favor de no responder.");

		return body.toString();
	}

	private String getNombreCompleto(Fisica persona) {

		String nombreCompleto = "";

		nombreCompleto += StringUtils.isBlank(persona.getNombre()) ? "" : persona.getNombre();
		nombreCompleto += StringUtils.isBlank(persona.getPrimerApellido()) ? "" : persona.getPrimerApellido();
		nombreCompleto += StringUtils.isBlank(persona.getSegundoApellido()) ? "" : persona.getSegundoApellido();

		return nombreCompleto;

	}
	/**
	 * Metodo utilitario para armar HTML con la información para enviar correo de reporte de vigencia
	 * @param asignacionNSS
	 * @return
	 */
	private String contenidoCorreoVigencia(AsignacionNSS asignacionNSS){
		StringBuffer body= new StringBuffer();
		String sl="<br>";
		String sp=" ";
		body.append(sl);
		body.append("Estimado Derechohabiente :");		
		body.append(sl);
		body.append(asignacionNSS.getNombre());
		body.append(sp);
		body.append(asignacionNSS.getPrimerApellido());
		body.append(sp);
		body.append(asignacionNSS.getSegundoApellido());
		body.append(sl);
		body.append("con Número de Seguridad social :" + asignacionNSS.getNss());
		body.append(sl);
		body.append("Derivado de su solicitud del Reporte de Vigencia de Derechos, "
				+ " el Sistema de IMSS Digital le envía el reporte al correo " );
		body.append(asignacionNSS.getCorreoElectronico().getCorreo()  +" que usted capturó en su solicitud");	
		body.append(sl);
		body.append(sl);
		body.append("Este correo ha sido generado automáticamente favor de no responder.");

		return body.toString();
	}
	
	private JavaMailSenderImpl getPropertiesMail() throws Exception{
		Properties prop = new Properties();
		JavaMailSenderImpl mail = new JavaMailSenderImpl();
		Properties envio = new Properties();
		
		
		
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("mailConfigurationDH.properties");
			prop.load(input);
			
			log.debug("las propiedades seteadas son use ["+ prop.getProperty("USER_DERECHOHABIENTES") +
					"] pass [" + prop.getProperty("PASSWORD_DERECHOHABIENTES")+ "] autenticacion ["
					+ prop.getProperty("AUTENTICACION") + "] HOST [" +prop.getProperty("HOST_MEXICO")+"] puerto [" 
					 + prop.getProperty("PUERTO") + "] protocolo ["
					 +prop.getProperty("PROTOCOLO")+ "] debug [" +prop.getProperty("DEBUG_ENABLE")+"]");
			
			Boolean autenticacion = new Boolean(prop.getProperty("AUTENTICACION"));
			if(autenticacion){
				mail.setUsername(prop.getProperty("USER_DERECHOHABIENTES"));
				mail.setPassword(prop.getProperty("PASSWORD_DERECHOHABIENTES"));
				envio.setProperty("mail.smtps.auth", "true");
			}
			else{
				envio.setProperty("mail.smtps.auth", "false");
			}
			//mail.setHost("relay.imss.gob.mx");
			mail.setHost(prop.getProperty("HOST_MEXICO"));
			//mail.setPort(25);
			mail.setPort(new Integer(prop.getProperty("PUERTO")).intValue());
			
			//mail.setProtocol("smtp");
			mail.setProtocol(prop.getProperty("PROTOCOLO"));
			//envio.setProperty("mail.transport.protocol", prop.getProperty("PROTOCOL"));
			envio.setProperty("mail.debug", prop.getProperty("DEBUG_ENABLE"));
			envio.setProperty("mail.transport.protocol", prop.getProperty("PROTOCOLO"));
			mail.setJavaMailProperties(envio);
			
			

		}catch (Exception e){
			log.error("error al setear las propiedades");
			throw e;
		}
		return mail;

	}
	
	private String getDefaultMailSenderDH(){
		Properties prop = new Properties();
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("mailConfigurationDH.properties");
			prop.load(input);
			return prop.getProperty("FROMIMSS_DERECHOHABIENTES_NOTIFICACION");
		}catch (Exception e){
			log.error("error al setear las propiedades");
		}
		
		return null;
		
	}

	@Override
	public void enviarCorreoCambioRegistroClinica(final String[] toEmail,
			final String ccEmail, final String subject,
			final Map<String, byte[]> adjuntos, Map<String, String> mailAttr) {
		try {
			Properties prop = new Properties();
			Map<String, String> registrCambioDhabTemplateAttr = new HashMap<String, String>();

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream("mailConfigurationDH.properties");
			prop.load(input);

			Boolean autenticacion = new Boolean(prop.getProperty("AUTENTICACION"));
			
			MailComponent mailComponent = new MailComponent();
			if (autenticacion) {
				// Datos del servidor
				mailComponent.setUser(prop.getProperty("USER_DERECHOHABIENTES"));
				mailComponent.setPassword(prop.getProperty("PASSWORD_DERECHOHABIENTES"));
			}

			// Datos del servidor
			mailComponent.setHost(prop.getProperty("HOST_MEXICO"));
			mailComponent.setPort(Integer.valueOf(prop.getProperty("PUERTO")));
			mailComponent.setProtocol(prop.getProperty("PROTOCOLO"));
			
			// Datos de envio
			mailComponent.setMailSubject(subject);
			mailComponent.setMailTemplate(DERECHOHAB_TEMPLATE);
			mailComponent.setMailTo(toEmail);
			mailComponent.setFromMail(prop.getProperty("FROMIMSS_DERECHOHABIENTES_NOTIFICACION"));
			mailComponent.setFromMailName(FROM_DERECHOHAB_NAME);
			mailComponent.setReplyTo(REPLY_MAIL);
			mailComponent.setReplyToName(REPLY_MAIL_NAME);
			mailComponent.setByteMailAttachments(adjuntos);

			// Datos de template
			registrCambioDhabTemplateAttr.put("fechaOperacion", mailAttr.get("fechaOperacion"));
			registrCambioDhabTemplateAttr.put("folio", mailAttr.get("folio"));
			registrCambioDhabTemplateAttr.put("nombreCompleto", mailAttr.get("nombreCompleto"));
			registrCambioDhabTemplateAttr.put("fechaSolicitud", mailAttr.get("fechaSolicitud"));
			registrCambioDhabTemplateAttr.put("clinicaAsignada", mailAttr.get("clinicaAsignada"));
			registrCambioDhabTemplateAttr.put("direccionClinica", mailAttr.get("direccionClinica"));
			registrCambioDhabTemplateAttr.put("turno", mailAttr.get("turno"));
			registrCambioDhabTemplateAttr.put("consultorio", mailAttr.get("consultorio"));
			registrCambioDhabTemplateAttr.put("descripcionTipoTramite", mailAttr.get("descripcionTipoTramite"));
			
			mailComponent.setMailVelAttributes(registrCambioDhabTemplateAttr);

			mailServiceBusiness.sendMail(mailComponent);
		} catch (Exception e) {
			log.error("-- Error al enviar el corrreo", e);
		}
	}

	@Override
	public void enviarCorreoCambioRegistroClinicaByQueue(final String toEmail,
			final String ccEmail, final String subject,
			final Map<String, byte[]> adjuntos, Map<String, String> mailAttr) {
		try {
			EmailPayloadType mailWrapper = new EmailPayloadType();

			mailWrapper.setSubject(subject);
			mailWrapper.setContent("");
			mailWrapper.setTo(toEmail);
			mailWrapper.setCc(ccEmail);
			mailWrapper.setContentType(CONTENT_TYPE);
			mailWrapper.setParameters(mailAttr);

			if (adjuntos != null) {
				if (adjuntos.size() == 1) {
					AttachmentContent attachment = new AttachmentContent();

					for (Map.Entry<String, byte[]> entry : adjuntos.entrySet()) {
						String stringEncoded = Base64Cipher.simpleEncode(entry.getValue());
						attachment.setTextBody(stringEncoded);
						attachment.setContentDisposition(entry.getKey());
						attachment.setContentType(MIME_PDF);
					}

					mailWrapper.setAttachment(attachment);
				} else if (adjuntos.size() > 1) {
					AttachmentContent[] lstAtachments = new AttachmentContent[adjuntos.size()];
					int index = 0;
					for (Map.Entry<String, byte[]> entry : adjuntos.entrySet()) {
						AttachmentContent attachment = new AttachmentContent();
						String stringEncoded = Base64Cipher.simpleEncode(entry.getValue());
						attachment.setTextBody(stringEncoded);
						attachment.setContentDisposition(entry.getKey().replace('/', '-'));
						attachment.setContentType(MIME_PDF);
						lstAtachments[index] = attachment;
						index++;
					}

					mailWrapper.setAttachments(lstAtachments);
				}
			}

			eMailProducer.agendarCorreoElectronico(mailWrapper);
		} catch (Exception e) {
			log.error("-- Error al enviar el corrreo", e);
		}

	}
}