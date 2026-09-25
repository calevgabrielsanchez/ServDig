package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the MESSAGES database table.
 * 
 */
@Entity
@Table(name="MESSAGES")
@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idmessages;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal idmobilemedia;

	@Temporal(TemporalType.DATE)
	private Date messagedate;

	private String messagedestination;

	private String messagefilename;

	private BigDecimal messagefilesize;

	private BigDecimal messagepriority;

	private String messagesource;

	private String messagestatus;

	private BigDecimal messagetransmissionattempts;

	private BigDecimal messagetype;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Message() {
	}

	public long getIdmessages() {
		return this.idmessages;
	}

	public void setIdmessages(long idmessages) {
		this.idmessages = idmessages;
	}

	public BigDecimal getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(BigDecimal createdby) {
		this.createdby = createdby;
	}

	public Date getCreatedon() {
		return this.createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public BigDecimal getIdmobilemedia() {
		return this.idmobilemedia;
	}

	public void setIdmobilemedia(BigDecimal idmobilemedia) {
		this.idmobilemedia = idmobilemedia;
	}

	public Date getMessagedate() {
		return this.messagedate;
	}

	public void setMessagedate(Date messagedate) {
		this.messagedate = messagedate;
	}

	public String getMessagedestination() {
		return this.messagedestination;
	}

	public void setMessagedestination(String messagedestination) {
		this.messagedestination = messagedestination;
	}

	public String getMessagefilename() {
		return this.messagefilename;
	}

	public void setMessagefilename(String messagefilename) {
		this.messagefilename = messagefilename;
	}

	public BigDecimal getMessagefilesize() {
		return this.messagefilesize;
	}

	public void setMessagefilesize(BigDecimal messagefilesize) {
		this.messagefilesize = messagefilesize;
	}

	public BigDecimal getMessagepriority() {
		return this.messagepriority;
	}

	public void setMessagepriority(BigDecimal messagepriority) {
		this.messagepriority = messagepriority;
	}

	public String getMessagesource() {
		return this.messagesource;
	}

	public void setMessagesource(String messagesource) {
		this.messagesource = messagesource;
	}

	public String getMessagestatus() {
		return this.messagestatus;
	}

	public void setMessagestatus(String messagestatus) {
		this.messagestatus = messagestatus;
	}

	public BigDecimal getMessagetransmissionattempts() {
		return this.messagetransmissionattempts;
	}

	public void setMessagetransmissionattempts(BigDecimal messagetransmissionattempts) {
		this.messagetransmissionattempts = messagetransmissionattempts;
	}

	public BigDecimal getMessagetype() {
		return this.messagetype;
	}

	public void setMessagetype(BigDecimal messagetype) {
		this.messagetype = messagetype;
	}

	public BigDecimal getUpdatedby() {
		return this.updatedby;
	}

	public void setUpdatedby(BigDecimal updatedby) {
		this.updatedby = updatedby;
	}

	public Date getUpdatedon() {
		return this.updatedon;
	}

	public void setUpdatedon(Date updatedon) {
		this.updatedon = updatedon;
	}

}