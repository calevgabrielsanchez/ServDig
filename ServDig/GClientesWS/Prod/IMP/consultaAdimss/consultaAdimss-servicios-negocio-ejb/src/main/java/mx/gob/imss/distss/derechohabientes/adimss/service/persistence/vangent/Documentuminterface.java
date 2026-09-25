package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCUMENTUMINTERFACES database table.
 * 
 */
@Entity
@Table(name="DOCUMENTUMINTERFACES")
@NamedQuery(name="Documentuminterface.findAll", query="SELECT d FROM Documentuminterface d")
public class Documentuminterface implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocumentuminterfacePK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String documentumreference;

	@Temporal(TemporalType.DATE)
	private Date interfacedate;

	private String interfacestate;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollment
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROL", referencedColumnName="IDENROL"),
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION")
		})
	private Enrollment enrollment;

	//bi-directional many-to-one association to Imagetype
	@ManyToOne
	@JoinColumn(name="IDIMGTYPE")
	private Imagetype imagetype;

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="IDENROL", referencedColumnName="IDENROL", insertable=false, updatable=false),
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION",insertable=false, updatable=false)
		})
	private Controladimss controladimss;
	
	public Documentuminterface() {
	}

	public DocumentuminterfacePK getId() {
		return this.id;
	}

	public void setId(DocumentuminterfacePK id) {
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

	public String getDocumentumreference() {
		return this.documentumreference;
	}

	public void setDocumentumreference(String documentumreference) {
		this.documentumreference = documentumreference;
	}

	public Date getInterfacedate() {
		return this.interfacedate;
	}

	public void setInterfacedate(Date interfacedate) {
		this.interfacedate = interfacedate;
	}

	public String getInterfacestate() {
		return this.interfacestate;
	}

	public void setInterfacestate(String interfacestate) {
		this.interfacestate = interfacestate;
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

	public Enrollment getEnrollment() {
		return this.enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public Imagetype getImagetype() {
		return this.imagetype;
	}

	public void setImagetype(Imagetype imagetype) {
		this.imagetype = imagetype;
	}

	public Controladimss getControladimss() {
		return controladimss;
	}

	public void setControladimss(Controladimss controladimss) {
		this.controladimss = controladimss;
	}
	
	

}