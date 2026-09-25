package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APPLICATIONCOMPONENTS database table.
 * 
 */
@Entity
@Table(name="APPLICATIONCOMPONENTS")
@NamedQuery(name="Applicationcomponent.findAll", query="SELECT a FROM Applicationcomponent a")
public class Applicationcomponent implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ApplicationcomponentPK id;

	private String appcomponentdesc;

	private String appcomponentname;

	private String appcomponentpath;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Application
	@ManyToOne
	@JoinColumn(name="IDAPPLICATION")
	private Application application;

	//bi-directional many-to-one association to Enrollmentstep
	@ManyToOne
	@JoinColumn(name="IDENROLSTEP")
	private Enrollmentstep enrollmentstep;

	public Applicationcomponent() {
	}

	public ApplicationcomponentPK getId() {
		return this.id;
	}

	public void setId(ApplicationcomponentPK id) {
		this.id = id;
	}

	public String getAppcomponentdesc() {
		return this.appcomponentdesc;
	}

	public void setAppcomponentdesc(String appcomponentdesc) {
		this.appcomponentdesc = appcomponentdesc;
	}

	public String getAppcomponentname() {
		return this.appcomponentname;
	}

	public void setAppcomponentname(String appcomponentname) {
		this.appcomponentname = appcomponentname;
	}

	public String getAppcomponentpath() {
		return this.appcomponentpath;
	}

	public void setAppcomponentpath(String appcomponentpath) {
		this.appcomponentpath = appcomponentpath;
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

	public Enrollmentstep getEnrollmentstep() {
		return this.enrollmentstep;
	}

	public void setEnrollmentstep(Enrollmentstep enrollmentstep) {
		this.enrollmentstep = enrollmentstep;
	}

}