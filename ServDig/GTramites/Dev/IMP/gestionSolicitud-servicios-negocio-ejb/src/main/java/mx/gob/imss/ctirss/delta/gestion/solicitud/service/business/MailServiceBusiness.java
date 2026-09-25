package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import mx.gob.imss.ctirss.delta.framework.base.model.MailComponent;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MailServiceBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;


@Stateless(name = "mailServiceBusiness", mappedName = "mailServiceBusiness")
public class MailServiceBusiness implements MailServiceBusinessRemote {
	private final Log log = LogFactory.getLog(getClass());
	private JavaMailSenderImpl mailSender;
	@EJB( mappedName = "envioCorreoElectronicoBusiness")
	private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusiness;

	private void initJavaMailSender(MailComponent mailComponent) throws Exception{
		
		/**
		JavaMailSenderImpl sender = new JavaMailSenderImpl();

		String host = mailComponent.getHost();
		String username = mailComponent.getUser();
		String password = mailComponent.getPassword();
		String protocol = mailComponent.getProtocol();
		Integer port = mailComponent.getPort();

		sender.setHost(host);
		sender.setUsername(username);
		sender.setPassword(password);

		if (port != null && !port.equals(0)) {
			sender.setPort(port);
		}
		if(StringUtils.isNotBlank(protocol)){
			sender.setProtocol(protocol);
		}
		**/

		setMailSender(envioCorreoElectronicoBusiness.getPropertiesMail(mailComponent.getFromMail()));
	}

	public void sendMail(MailComponent mailComponent) throws MessagingException {
		String host =  mailComponent.getHost();
		String[] mailTo = mailComponent.getMailTo();
		String[] mailCc = mailComponent.getMailCc();
		String[] mailBcc = mailComponent.getMailBcc();
		String mailSubject = mailComponent.getMailSubject();
		String mailBody = mailComponent.getMailBody();
		File[] mailAttachments = mailComponent.getMailAttachments();
		File[] mailInLineResources = mailComponent.getMailInLineResources();
		Map<String, byte[]> byteMailAttachments = mailComponent.getByteMailAttachments();

		if (host == null) {
			throw new MessagingException("No se encuentra definido host del correo");
		} else {
			try {
			initJavaMailSender(mailComponent);
			}catch (Exception e) {
				log.error("ocurrio un error al setear los datos del correo" , e);
				throw new MessagingException("ocurrio un error al setear los datos del correo"  + e.getMessage());
			}
		}

		if (this.mailSender == null) {
			throw new MessagingException("No se ha instanciado objeto MailSender.");
		}

		if (mailTo == null) {
			throw new MessagingException("No se encuentra definido ningún destinatario.");
		}

		if (mailSubject == null) {
			throw new MessagingException("No se encuentra definido el asunto del correo.");
		}

		if (mailBody == null) {
			throw new MessagingException("No se encuentra definido el cuerpo del correo.");
		}

		MimeMessage message = this.mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(mailSubject);
		helper.setTo(mailTo);
		if (mailCc != null)
			helper.setCc(mailCc);
		if (mailBcc != null) {
			helper.setBcc(mailBcc);
		}

		if (mailInLineResources != null) {
			for (int i = 0; i < mailInLineResources.length; i++) {
				FileSystemResource res = new FileSystemResource(
						mailInLineResources[i]);
				helper.addInline(res.getFilename(), res);
			}
		}

		if (mailAttachments != null) {
			for (int i = 0; i < mailAttachments.length; i++) {
				FileSystemResource att = new FileSystemResource(mailAttachments[i]);
				helper.addAttachment(att.getFilename(), att);
			}
		}
		
		if (byteMailAttachments != null) {
			if (byteMailAttachments != null) {
				for (Map.Entry<String, byte[]> entry : byteMailAttachments.entrySet()) {
					ByteArrayResource file = new ByteArrayResource(entry.getValue());
					helper.addAttachment(entry.getKey(), file);
				}
			}
		}

		helper.setText(mailBody, true);

		this.mailSender.send(message);
	}
	
	public void sendTemplateMail(MailComponent mailComponent)
			throws MessagingException {
		final String host =  mailComponent.getHost();
		final String fromMail = mailComponent.getFromMail();
		final String fromMailName = mailComponent.getFromMailName();
		final String[] mailTo = mailComponent.getMailTo();
		final String[] mailCc = mailComponent.getMailCc();
		final String[] mailBcc = mailComponent.getMailBcc();
		final String mailSubject = mailComponent.getMailSubject();
		final File[] mailAttachments = mailComponent.getMailAttachments();
		final File[] mailInLineResources = mailComponent.getMailInLineResources();
		final String replyTo = mailComponent.getReplyTo();
		final String replyToName = mailComponent.getReplyToName();
		final String mailTemplate = mailComponent.getMailTemplate();
		final Integer priority = mailComponent.getPriority();
		final Map<String, String> mailVelAttributes = mailComponent.getMailVelAttributes();
		final Map<String, byte[]> byteMailAttachments = mailComponent.getByteMailAttachments();

		if (host == null) {
			throw new MessagingException("No se encuentra definido host del correo");
		} else {
			try {
				initJavaMailSender(mailComponent);
				}catch (Exception e) {
					log.error("ocurrio un error al setear los datos del correo" , e);
					throw new MessagingException("ocurrio un error al setear los datos del correo"  + e.getMessage());
				}
		}

		if (this.mailSender == null) {
			throw new MessagingException("No se ha instanciado objeto MailSender.");
		}

		if (mailTo == null) {
			throw new MessagingException("No se encuentra definido ningún destinatario.");
		}

		if (mailSubject == null) {
			throw new MessagingException("No se encuentra definido el asunto del correo.");
		}

		if (mailTemplate == null) {
			throw new MessagingException("No se encuentra definido el template del correo.");
		}

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

				if (StringUtils.isNotBlank(fromMail)) {
					if (StringUtils.isNotBlank(fromMailName)) {
						message.setFrom(fromMail, fromMailName);
					} else {
						message.setFrom(fromMail);
					}
				}
				if (priority != null && !priority.equals(0)) {
					message.setPriority(priority);
				}

				message.setTo(mailTo);
				message.setSubject(mailSubject);
				if (mailCc != null)
					message.setCc(mailCc);
				if (mailBcc != null) {
					message.setBcc(mailBcc);
				}
				if (replyTo != null) {
					if (replyToName != null) {
						message.setReplyTo(replyTo, replyToName);
					} else {
						message.setReplyTo(replyTo);
					}
				}

				if (mailInLineResources != null) {
					for (int i = 0; i < mailInLineResources.length; i++) {
						FileSystemResource res = new FileSystemResource(mailInLineResources[i]);
						message.addInline(res.getFilename(), res);
					}
				}

				if (mailAttachments != null) {
					for (int i = 0; i < mailAttachments.length; i++) {
						FileSystemResource att = new FileSystemResource(mailAttachments[i]);
						message.addAttachment(att.getFilename(), att);
					}
				}

				if (byteMailAttachments != null) {
					if (byteMailAttachments != null) {
						for (Map.Entry<String, byte[]> entry : byteMailAttachments.entrySet()) {
							ByteArrayResource file = new ByteArrayResource(entry.getValue());
							message.addAttachment(entry.getKey(), file);
						}
					}
				}

				Map model = new HashMap();
				if (mailVelAttributes != null) {
					for (String key : mailVelAttributes.keySet()) {
						model.put(key, (String) mailVelAttributes.get(key));
					}
				}

				/*String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, mailTemplate, model);*/
				String text = mailTemplate;
				log.info("Contenido del correo a enviar: " + text);
				message.setText(text, true);
			}
		};

		this.mailSender.send(preparator);
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
}
