package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCUMENTPROFILES database table.
 * 
 */
@Embeddable
@Table(name="DOCUMENTPROFILES")
@NamedQuery(name="Documentprofile.findAll", query="SELECT d FROM Documentprofile d")
public class Documentprofile implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal iddocprofiles;

	private BigDecimal imgtype;

	private String profileparam;

	private String profileparamvalue;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public Documentprofile() {
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

	public BigDecimal getIddocprofiles() {
		return this.iddocprofiles;
	}

	public void setIddocprofiles(BigDecimal iddocprofiles) {
		this.iddocprofiles = iddocprofiles;
	}

	public BigDecimal getImgtype() {
		return this.imgtype;
	}

	public void setImgtype(BigDecimal imgtype) {
		this.imgtype = imgtype;
	}

	public String getProfileparam() {
		return this.profileparam;
	}

	public void setProfileparam(String profileparam) {
		this.profileparam = profileparam;
	}

	public String getProfileparamvalue() {
		return this.profileparamvalue;
	}

	public void setProfileparamvalue(String profileparamvalue) {
		this.profileparamvalue = profileparamvalue;
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