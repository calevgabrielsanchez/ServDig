package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCUMENTDEPENDENCIES database table.
 * 
 */
@Embeddable
@Table(name="DOCUMENTDEPENDENCIES")
@NamedQuery(name="Documentdependency.findAll", query="SELECT d FROM Documentdependency d")
public class Documentdependency implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal enrolsubtype;

	private BigDecimal enroltype;

	private BigDecimal iddocdependencies;

	private BigDecimal imgtype;

	private String reqdoc;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Documentdependency() {
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

	public BigDecimal getEnrolsubtype() {
		return this.enrolsubtype;
	}

	public void setEnrolsubtype(BigDecimal enrolsubtype) {
		this.enrolsubtype = enrolsubtype;
	}

	public BigDecimal getEnroltype() {
		return this.enroltype;
	}

	public void setEnroltype(BigDecimal enroltype) {
		this.enroltype = enroltype;
	}

	public BigDecimal getIddocdependencies() {
		return this.iddocdependencies;
	}

	public void setIddocdependencies(BigDecimal iddocdependencies) {
		this.iddocdependencies = iddocdependencies;
	}

	public BigDecimal getImgtype() {
		return this.imgtype;
	}

	public void setImgtype(BigDecimal imgtype) {
		this.imgtype = imgtype;
	}

	public String getReqdoc() {
		return this.reqdoc;
	}

	public void setReqdoc(String reqdoc) {
		this.reqdoc = reqdoc;
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