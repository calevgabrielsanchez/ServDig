package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ADIMSSUSERS database table.
 * 
 */
@Entity
@Table(name="ADIMSSUSERS")
@NamedQuery(name="Adimssuser.findAll", query="SELECT a FROM Adimssuser a")
public class Adimssuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idadimssuser;

	private String adimssusername;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String email;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Adimssevent
	@OneToMany(mappedBy="adimssuser1")
	private List<Adimssevent> adimssevents1;

	//bi-directional many-to-one association to Adimssevent
	@OneToMany(mappedBy="adimssuser2")
	private List<Adimssevent> adimssevents2;

	//bi-directional many-to-one association to Adimssevent
	@OneToMany(mappedBy="adimssuser3")
	private List<Adimssevent> adimssevents3;

	//bi-directional many-to-one association to Company
	@ManyToOne
	@JoinColumn(name="IDCOMPANY")
	private Company company;

	public Adimssuser() {
	}

	public long getIdadimssuser() {
		return this.idadimssuser;
	}

	public void setIdadimssuser(long idadimssuser) {
		this.idadimssuser = idadimssuser;
	}

	public String getAdimssusername() {
		return this.adimssusername;
	}

	public void setAdimssusername(String adimssusername) {
		this.adimssusername = adimssusername;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<Adimssevent> getAdimssevents1() {
		return this.adimssevents1;
	}

	public void setAdimssevents1(List<Adimssevent> adimssevents1) {
		this.adimssevents1 = adimssevents1;
	}

	public Adimssevent addAdimssevents1(Adimssevent adimssevents1) {
		getAdimssevents1().add(adimssevents1);
		adimssevents1.setAdimssuser1(this);

		return adimssevents1;
	}

	public Adimssevent removeAdimssevents1(Adimssevent adimssevents1) {
		getAdimssevents1().remove(adimssevents1);
		adimssevents1.setAdimssuser1(null);

		return adimssevents1;
	}

	public List<Adimssevent> getAdimssevents2() {
		return this.adimssevents2;
	}

	public void setAdimssevents2(List<Adimssevent> adimssevents2) {
		this.adimssevents2 = adimssevents2;
	}

	public Adimssevent addAdimssevents2(Adimssevent adimssevents2) {
		getAdimssevents2().add(adimssevents2);
		adimssevents2.setAdimssuser2(this);

		return adimssevents2;
	}

	public Adimssevent removeAdimssevents2(Adimssevent adimssevents2) {
		getAdimssevents2().remove(adimssevents2);
		adimssevents2.setAdimssuser2(null);

		return adimssevents2;
	}

	public List<Adimssevent> getAdimssevents3() {
		return this.adimssevents3;
	}

	public void setAdimssevents3(List<Adimssevent> adimssevents3) {
		this.adimssevents3 = adimssevents3;
	}

	public Adimssevent addAdimssevents3(Adimssevent adimssevents3) {
		getAdimssevents3().add(adimssevents3);
		adimssevents3.setAdimssuser3(this);

		return adimssevents3;
	}

	public Adimssevent removeAdimssevents3(Adimssevent adimssevents3) {
		getAdimssevents3().remove(adimssevents3);
		adimssevents3.setAdimssuser3(null);

		return adimssevents3;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}