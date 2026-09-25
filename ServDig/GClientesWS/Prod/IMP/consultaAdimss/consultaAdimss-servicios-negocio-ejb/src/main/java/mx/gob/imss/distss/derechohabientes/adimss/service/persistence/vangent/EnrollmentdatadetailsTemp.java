package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ENROLLMENTDATADETAILS_TEMP database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTDATADETAILS_TEMP")
@NamedQuery(name="EnrollmentdatadetailsTemp.findAll", query="SELECT e FROM EnrollmentdatadetailsTemp e")
public class EnrollmentdatadetailsTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EnrollmentdatadetailsTempPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal enroldatalength;

	private BigDecimal enroldataquality;

	private String enroldatavalue;

	private String enrolkeydata;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	public EnrollmentdatadetailsTemp() {
	}

	public EnrollmentdatadetailsTempPK getId() {
		return this.id;
	}

	public void setId(EnrollmentdatadetailsTempPK id) {
		this.id = id;
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

	public BigDecimal getEnroldatalength() {
		return this.enroldatalength;
	}

	public void setEnroldatalength(BigDecimal enroldatalength) {
		this.enroldatalength = enroldatalength;
	}

	public BigDecimal getEnroldataquality() {
		return this.enroldataquality;
	}

	public void setEnroldataquality(BigDecimal enroldataquality) {
		this.enroldataquality = enroldataquality;
	}

	public String getEnroldatavalue() {
		return this.enroldatavalue;
	}

	public void setEnroldatavalue(String enroldatavalue) {
		this.enroldatavalue = enroldatavalue;
	}

	public String getEnrolkeydata() {
		return this.enrolkeydata;
	}

	public void setEnrolkeydata(String enrolkeydata) {
		this.enrolkeydata = enrolkeydata;
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