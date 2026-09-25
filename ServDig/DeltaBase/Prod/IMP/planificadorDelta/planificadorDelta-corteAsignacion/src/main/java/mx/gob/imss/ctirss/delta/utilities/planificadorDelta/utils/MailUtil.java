package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.utils;


import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import mx.gob.imss.utilmail.service.MailService;
import mx.gob.imss.utilmail.service.impl.MailServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class MailUtil {
	private final Log log = LogFactory.getLog(getClass());
	private JavaMailSenderImpl mailSender;
	private VelocityEngine velocityEngine;
	private MailService mailService = new MailServiceImpl();

	public void sendSimpleMail(MailComponent mailComponent) {
		try {
			String mailTo = null;
			String mailCc = null;
			String mailBcc = null;

			if (mailComponent.getMailTo() != null) {
				mailTo = mailComponent.getMailTo()[0];
			}
			if (mailComponent.getMailCc() != null) {
				mailCc = mailComponent.getMailCc()[0];
			}
			if (mailComponent.getMailBcc() != null) {
				mailBcc = mailComponent.getMailBcc()[0];
			}

			mailService.sendMail(mailComponent.getHost(),
					mailComponent.getUser(),
					mailComponent.getPassword(),
					mailComponent.getMailSubject(),
					mailComponent.getFromMail(),
					mailTo,
					mailCc,
					mailBcc,
					mailComponent.getMailBody());
		} catch (Exception e) {
			log.error(e);
		}
	}

	private void initJavaMailSender(MailComponent mailComponent) {

		JavaMailSenderImpl sender = new JavaMailSenderImpl();

		String host = mailComponent.getHost();
		String username = mailComponent.getUser();
		String password = mailComponent.getPassword();

		sender.setHost(host);
		sender.setUsername(username);
		sender.setPassword(password);

		setMailSender(sender);
	}

	private void initVelocityEngine() throws VelocityException, IOException {
		VelocityEngineFactoryBean velocityEngineFactory = new VelocityEngineFactoryBean();

		Map<String, Object> velocityPropertiesMap = new HashMap<String, Object>();
		velocityPropertiesMap.put("resource.loader", "class");
		velocityPropertiesMap.put("class.resource.loader.class", ClasspathResourceLoader.class.getName());

		velocityEngineFactory.setVelocityPropertiesMap(velocityPropertiesMap);
		setVelocityEngine(velocityEngineFactory.createVelocityEngine());
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

		if (host == null) {
			throw new MessagingException("No se encuentra definido host del correo");
		} else {
			initJavaMailSender(mailComponent);
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

		helper.setText(mailBody, true);

		this.mailSender.send(message);
	}

	public void sendVelocityMail(MailComponent mailComponent)
			throws MessagingException, VelocityException, IOException {
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

		initVelocityEngine();

		if (host == null) {
			throw new MessagingException("No se encuentra definido host del correo");
		} else {
			initJavaMailSender(mailComponent);
		}

		if (this.velocityEngine == null) {
			throw new MessagingException("No se ha instanciado objeto VelocityEngine.");
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

				Map model = new HashMap();
				if (mailVelAttributes != null) {
					for (String key : mailVelAttributes.keySet()) {
						model.put(key, (String) mailVelAttributes.get(key));
					}
				}

				String text = VelocityEngineUtils.mergeTemplateIntoString(
						MailUtil.this.velocityEngine, mailTemplate, model);
				message.setText(text, true);
			}
		};

		this.mailSender.send(preparator);
	}

	public void sendMail(MailComponent mailComponent, String[] pathAttachments,
			String[] pathInLineResources) throws MessagingException {
		File[] mailAttachments = (File[]) null;
		File[] mailInLineResources = (File[]) null;

		try {
			if (pathAttachments != null) {
				mailAttachments = new File[pathAttachments.length];

				for (int i = 0; i < pathAttachments.length; i++) {
					URL url = MailUtil.class.getResource(pathAttachments[i]);
					File f = new File(url.getFile().substring(url.getFile().lastIndexOf("/") + 1));

					f.deleteOnExit();
					InputStream is = url.openStream();
					writeToFile(is, f);
					mailAttachments[i] = f;

					is.close();
				}
			}

			if (pathInLineResources != null) {
				mailInLineResources = new File[pathInLineResources.length];

				for (int i = 0; i < pathInLineResources.length; i++) {
					URL url = MailUtil.class.getResource(pathInLineResources[i]);
					File f = new File(url.getFile().substring(url.getFile().lastIndexOf("/") + 1));

					f.deleteOnExit();
					InputStream is = url.openStream();
					writeToFile(is, f);
					mailInLineResources[i] = f;

					is.close();
				}
			}
		} catch (IOException e) {
			throw new MessagingException(e.getMessage());
		}

		mailComponent.setMailAttachments(mailAttachments);
		mailComponent.setMailInLineResources(mailInLineResources);

		sendMail(mailComponent);
	}

	public void sendVelocityMail(MailComponent mailComponent,
			String[] pathAttachments, String[] pathInLineResources)
			throws MessagingException, VelocityException, IOException {
		File[] mailAttachments = (File[]) null;
		File[] mailInLineResources = (File[]) null;

		try {
			if (pathAttachments != null) {
				mailAttachments = new File[pathAttachments.length];

				for (int i = 0; i < pathAttachments.length; i++) {
					URL url = MailUtil.class.getResource(pathAttachments[i]);
					File f = new File(url.getFile().substring(url.getFile().lastIndexOf("/") + 1));

					f.deleteOnExit();
					InputStream is = url.openStream();
					writeToFile(is, f);
					mailAttachments[i] = f;

					is.close();
				}
			}

			if (pathInLineResources != null) {
				mailInLineResources = new File[pathInLineResources.length];
				for (int i = 0; i < pathInLineResources.length; i++) {
					URL url = MailUtil.class.getResource(pathInLineResources[i]);
					File f = new File(url.getFile().substring(url.getFile().lastIndexOf("/") + 1));

					f.deleteOnExit();
					InputStream is = url.openStream();
					writeToFile(is, f);
					mailInLineResources[i] = f;

					is.close();
				}
			}
		} catch (IOException e) {
			throw new MessagingException(e.getMessage());
		}

		mailComponent.setMailAttachments(mailAttachments);
		mailComponent.setMailInLineResources(mailInLineResources);

		sendVelocityMail(mailComponent);
	}

	private static void writeToFile(InputStream is, File f) throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
				new FileOutputStream(f)));
		int c;
		while ((c = is.read()) != -1) {
			out.writeByte(c);
		}

		is.close();
		out.close();
	}

	public JavaMailSenderImpl getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
}
