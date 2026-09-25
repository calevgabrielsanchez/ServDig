package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the AUDITLOGDETAILS database table.
 * 
 */
@Entity
@Table(name="AUDITLOGDETAILS")
@NamedQuery(name="Auditlogdetail.findAll", query="SELECT a FROM Auditlogdetail a")
public class Auditlogdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AuditlogdetailPK id;

//	private Object auditlogdate;

	private String auditlogoperation;

	private String auditlogresult;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Application
	@ManyToOne
	@JoinColumn(name="IDAPPLICATIONS")
	private Application application;

	//bi-directional many-to-one association to Audittype
	@ManyToOne
	@JoinColumn(name="IDAUDITTYPES")
	private Audittype audittype;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION"),
		@JoinColumn(name="IDUSERS", referencedColumnName="IDUSERS")
		})
	private User user;

	public Auditlogdetail() {
	}

	public AuditlogdetailPK getId() {
		return this.id;
	}

	public void setId(AuditlogdetailPK id) {
		this.id = id;
	}

//	public Object getAuditlogdate() {
//		return this.auditlogdate;
//	}
//
//	public void setAuditlogdate(Object auditlogdate) {
//		this.auditlogdate = auditlogdate;
//	}

	public String getAuditlogoperation() {
		return this.auditlogoperation;
	}

	public void setAuditlogoperation(String auditlogoperation) {
		this.auditlogoperation = auditlogoperation;
	}

	public String getAuditlogresult() {
		return this.auditlogresult;
	}

	public void setAuditlogresult(String auditlogresult) {
		this.auditlogresult = auditlogresult;
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

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Audittype getAudittype() {
		return this.audittype;
	}

	public void setAudittype(Audittype audittype) {
		this.audittype = audittype;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}