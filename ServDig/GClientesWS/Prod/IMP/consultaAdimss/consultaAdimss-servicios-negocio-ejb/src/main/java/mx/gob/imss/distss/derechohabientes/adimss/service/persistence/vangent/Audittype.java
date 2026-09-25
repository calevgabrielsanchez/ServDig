package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the AUDITTYPES database table.
 * 
 */
@Entity
@Table(name="AUDITTYPES")
@NamedQuery(name="Audittype.findAll", query="SELECT a FROM Audittype a")
public class Audittype implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idaudittypes;

	private String audittypedescription;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Auditlogdetail
	@OneToMany(mappedBy="audittype")
	private List<Auditlogdetail> auditlogdetails;

	public Audittype() {
	}

	public long getIdaudittypes() {
		return this.idaudittypes;
	}

	public void setIdaudittypes(long idaudittypes) {
		this.idaudittypes = idaudittypes;
	}

	public String getAudittypedescription() {
		return this.audittypedescription;
	}

	public void setAudittypedescription(String audittypedescription) {
		this.audittypedescription = audittypedescription;
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

	public List<Auditlogdetail> getAuditlogdetails() {
		return this.auditlogdetails;
	}

	public void setAuditlogdetails(List<Auditlogdetail> auditlogdetails) {
		this.auditlogdetails = auditlogdetails;
	}

	public Auditlogdetail addAuditlogdetail(Auditlogdetail auditlogdetail) {
		getAuditlogdetails().add(auditlogdetail);
		auditlogdetail.setAudittype(this);

		return auditlogdetail;
	}

	public Auditlogdetail removeAuditlogdetail(Auditlogdetail auditlogdetail) {
		getAuditlogdetails().remove(auditlogdetail);
		auditlogdetail.setAudittype(null);

		return auditlogdetail;
	}

}