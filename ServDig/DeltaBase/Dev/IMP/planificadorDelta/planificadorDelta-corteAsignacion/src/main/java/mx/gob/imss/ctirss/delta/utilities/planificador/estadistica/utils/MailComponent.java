package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.utils;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public class MailComponent implements Serializable {
	private static final long serialVersionUID = 5693530221717810201L;
	private String host;
	private String user;
	private String password;
	private String fromMail;
	private String fromMailName;
	private String[] mailTo;
	private String[] mailCc;
	private String[] mailBcc;
	private String mailSubject;
	private String mailBody;
	private File[] mailAttachments;
	private File[] mailInLineResources;
	private String mailTemplate;
	private String replyTo;
	private String replyToName;
	private Integer priority;
	private Map<String, String> mailVelAttributes;

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String[] getMailTo() {
		return mailTo;
	}

	public void setMailTo(String[] mailTo) {
		this.mailTo = mailTo != null ? mailTo.clone() : null;
	}

	public String[] getMailCc() {
		return mailCc;
	}

	public void setMailCc(String[] mailCc) {
		this.mailCc = mailCc != null ? mailCc.clone() : null;
	}

	public String[] getMailBcc() {
		return mailBcc;
	}

	public void setMailBcc(String[] mailBcc) {
		this.mailBcc = mailBcc != null ? mailBcc.clone() : null;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public File[] getMailAttachments() {
		return mailAttachments;
	}

	public void setMailAttachments(File[] mailAttachments) {
		this.mailAttachments = mailAttachments != null ? mailAttachments
				.clone() : null;
	}

	public File[] getMailInLineResources() {
		return mailInLineResources;
	}

	public void setMailInLineResources(File[] mailInLineResources) {
		this.mailInLineResources = mailInLineResources != null ? mailInLineResources
				.clone() : null;
	}

	public String getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

	public Map<String, String> getMailVelAttributes() {
		return mailVelAttributes;
	}

	public void setMailVelAttributes(Map<String, String> mailVelAttributes) {
		this.mailVelAttributes = mailVelAttributes;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getFromMailName() {
		return fromMailName;
	}

	public void setFromMailName(String fromMailName) {
		this.fromMailName = fromMailName;
	}

	public String getReplyToName() {
		return replyToName;
	}

	public void setReplyToName(String replyToName) {
		this.replyToName = replyToName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}
