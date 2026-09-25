package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTDATATYPES database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTDATATYPES")
@NamedQuery(name="Enrollmentdatatype.findAll", query="SELECT e FROM Enrollmentdatatype e")
public class Enrollmentdatatype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenroldatatype;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String enroldatacheck;

	private BigDecimal enroldatalength;

	private String enroldatamask;

	private String enroldatatype;

	private String enroldatatypedesc;

	private String enroldatatypename;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentdatadetail
	@OneToMany(mappedBy="enrollmentdatatype")
	private List<Enrollmentdatadetail> enrollmentdatadetails;

	public Enrollmentdatatype() {
	}

	public long getIdenroldatatype() {
		return this.idenroldatatype;
	}

	public void setIdenroldatatype(long idenroldatatype) {
		this.idenroldatatype = idenroldatatype;
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

	public String getEnroldatacheck() {
		return this.enroldatacheck;
	}

	public void setEnroldatacheck(String enroldatacheck) {
		this.enroldatacheck = enroldatacheck;
	}

	public BigDecimal getEnroldatalength() {
		return this.enroldatalength;
	}

	public void setEnroldatalength(BigDecimal enroldatalength) {
		this.enroldatalength = enroldatalength;
	}

	public String getEnroldatamask() {
		return this.enroldatamask;
	}

	public void setEnroldatamask(String enroldatamask) {
		this.enroldatamask = enroldatamask;
	}

	public String getEnroldatatype() {
		return this.enroldatatype;
	}

	public void setEnroldatatype(String enroldatatype) {
		this.enroldatatype = enroldatatype;
	}

	public String getEnroldatatypedesc() {
		return this.enroldatatypedesc;
	}

	public void setEnroldatatypedesc(String enroldatatypedesc) {
		this.enroldatatypedesc = enroldatatypedesc;
	}

	public String getEnroldatatypename() {
		return this.enroldatatypename;
	}

	public void setEnroldatatypename(String enroldatatypename) {
		this.enroldatatypename = enroldatatypename;
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

	public List<Enrollmentdatadetail> getEnrollmentdatadetails() {
		return this.enrollmentdatadetails;
	}

	public void setEnrollmentdatadetails(List<Enrollmentdatadetail> enrollmentdatadetails) {
		this.enrollmentdatadetails = enrollmentdatadetails;
	}

	public Enrollmentdatadetail addEnrollmentdatadetail(Enrollmentdatadetail enrollmentdatadetail) {
		getEnrollmentdatadetails().add(enrollmentdatadetail);
		enrollmentdatadetail.setEnrollmentdatatype(this);

		return enrollmentdatadetail;
	}

	public Enrollmentdatadetail removeEnrollmentdatadetail(Enrollmentdatadetail enrollmentdatadetail) {
		getEnrollmentdatadetails().remove(enrollmentdatadetail);
		enrollmentdatadetail.setEnrollmentdatatype(null);

		return enrollmentdatadetail;
	}

}