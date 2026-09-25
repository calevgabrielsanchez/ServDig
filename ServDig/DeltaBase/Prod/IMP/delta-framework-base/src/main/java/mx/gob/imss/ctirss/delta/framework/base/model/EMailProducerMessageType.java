package mx.gob.imss.ctirss.delta.framework.base.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="EMailProducerType", namespace="http://mx.gob.imss.email.model")
@XmlAccessorType(XmlAccessType.FIELD)
public class EMailProducerMessageType {
	
	@XmlElement(name="Subject", namespace="http://mx.gob.imss.email.model", required = true)
	private String subject;
	
	@XmlElement(name="To", namespace="http://mx.gob.imss.email.model", required = true)
	private String to;
	
	@XmlElement(name="FromAccountName", namespace="http://mx.gob.imss.email.model", required = true)
	private String from;
	
	@XmlElement(name="Content", namespace="http://mx.gob.imss.email.model", required = true)
	private String content;
	
	@XmlElement(name="ContentType", namespace="http://mx.gob.imss.email.model", required = true)
	private String contentType;
	
	@XmlElement(name="Cc", namespace="http://mx.gob.imss.email.model", required = true)
	private String cc;
	
	@XmlElement(name="Bcc", namespace="http://mx.gob.imss.email.model", required = true)
	private String bcc;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	
}
