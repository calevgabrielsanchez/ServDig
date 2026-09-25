package mx.gob.imss.ctirss.delta.framework.base.model;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public class MailComponent implements Serializable {
	private static final long serialVersionUID = -2837578389671286970L;
	private String host;
	private String user;
	private Integer port;
	private String protocol;
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
	private Map<String, byte[]> byteMailAttachments;

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

	public String getFromMail() {
		return fromMail;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}

	public String getFromMailName() {
		return fromMailName;
	}

	public void setFromMailName(String fromMailName) {
		this.fromMailName = fromMailName;
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
		this.mailCc = mailCc;
	}

	public String[] getMailBcc() {
		return mailBcc;
	}

	public void setMailBcc(String[] mailBcc) {
		this.mailBcc = mailBcc;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public File[] getMailAttachments() {
		return mailAttachments;
	}

	public void setMailAttachments(File[] mailAttachments) {
		this.mailAttachments = mailAttachments;
	}

	public File[] getMailInLineResources() {
		return mailInLineResources;
	}

	public void setMailInLineResources(File[] mailInLineResources) {
		this.mailInLineResources = mailInLineResources;
	}

	public String getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
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

	public Map<String, String> getMailVelAttributes() {
		return mailVelAttributes;
	}

	public void setMailVelAttributes(Map<String, String> mailVelAttributes) {
		this.mailVelAttributes = mailVelAttributes;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Map<String, byte[]> getByteMailAttachments() {
		return byteMailAttachments;
	}

	public void setByteMailAttachments(Map<String, byte[]> byteMailAttachments) {
		this.byteMailAttachments = byteMailAttachments;
	}
}
