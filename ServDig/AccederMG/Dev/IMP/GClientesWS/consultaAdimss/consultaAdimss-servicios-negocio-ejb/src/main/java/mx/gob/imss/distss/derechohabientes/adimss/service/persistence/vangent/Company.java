package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the COMPANIES database table.
 * 
 */
@Entity
@Table(name="COMPANIES")
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idcompany;

	private String companyname;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Adimssuser
	@OneToMany(mappedBy="company")
	private List<Adimssuser> adimssusers;

	public Company() {
	}

	public long getIdcompany() {
		return this.idcompany;
	}

	public void setIdcompany(long idcompany) {
		this.idcompany = idcompany;
	}

	public String getCompanyname() {
		return this.companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
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

	public List<Adimssuser> getAdimssusers() {
		return this.adimssusers;
	}

	public void setAdimssusers(List<Adimssuser> adimssusers) {
		this.adimssusers = adimssusers;
	}

	public Adimssuser addAdimssuser(Adimssuser adimssuser) {
		getAdimssusers().add(adimssuser);
		adimssuser.setCompany(this);

		return adimssuser;
	}

	public Adimssuser removeAdimssuser(Adimssuser adimssuser) {
		getAdimssusers().remove(adimssuser);
		adimssuser.setCompany(null);

		return adimssuser;
	}

}