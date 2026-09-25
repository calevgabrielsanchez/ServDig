package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DELEGATIONS database table.
 * 
 */
@Entity
@Table(name="DELEGATIONS")
@NamedQuery(name="Delegation.findAll", query="SELECT d FROM Delegation d")
public class Delegation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long iddelegation;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String delegationdesc;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentcenter
	@OneToMany(mappedBy="delegation")
	private List<Enrollmentcenter> enrollmentcenters;

	//bi-directional many-to-one association to Enrollmentctracking
	@OneToMany(mappedBy="delegation")
	private List<Enrollmentctracking> enrollmentctrackings;

	//bi-directional many-to-one association to Enrollperformancedel
	@OneToMany(mappedBy="delegation")
	private List<Enrollperformancedel> enrollperformancedels;

	//bi-directional many-to-one association to Filecleansingdelegationwork
	@OneToMany(mappedBy="delegation")
	private List<Filecleansingdelegationwork> filecleansingdelegationworks;

	//bi-directional many-to-one association to Filecleansingwork
	@OneToMany(mappedBy="delegation")
	private List<Filecleansingwork> filecleansingworks;

	public Delegation() {
	}

	public long getIddelegation() {
		return this.iddelegation;
	}

	public void setIddelegation(long iddelegation) {
		this.iddelegation = iddelegation;
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

	public String getDelegationdesc() {
		return this.delegationdesc;
	}

	public void setDelegationdesc(String delegationdesc) {
		this.delegationdesc = delegationdesc;
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

	public List<Enrollmentcenter> getEnrollmentcenters() {
		return this.enrollmentcenters;
	}

	public void setEnrollmentcenters(List<Enrollmentcenter> enrollmentcenters) {
		this.enrollmentcenters = enrollmentcenters;
	}

	public Enrollmentcenter addEnrollmentcenter(Enrollmentcenter enrollmentcenter) {
		getEnrollmentcenters().add(enrollmentcenter);
		enrollmentcenter.setDelegation(this);

		return enrollmentcenter;
	}

	public Enrollmentcenter removeEnrollmentcenter(Enrollmentcenter enrollmentcenter) {
		getEnrollmentcenters().remove(enrollmentcenter);
		enrollmentcenter.setDelegation(null);

		return enrollmentcenter;
	}

	public List<Enrollmentctracking> getEnrollmentctrackings() {
		return this.enrollmentctrackings;
	}

	public void setEnrollmentctrackings(List<Enrollmentctracking> enrollmentctrackings) {
		this.enrollmentctrackings = enrollmentctrackings;
	}

	public Enrollmentctracking addEnrollmentctracking(Enrollmentctracking enrollmentctracking) {
		getEnrollmentctrackings().add(enrollmentctracking);
		enrollmentctracking.setDelegation(this);

		return enrollmentctracking;
	}

	public Enrollmentctracking removeEnrollmentctracking(Enrollmentctracking enrollmentctracking) {
		getEnrollmentctrackings().remove(enrollmentctracking);
		enrollmentctracking.setDelegation(null);

		return enrollmentctracking;
	}

	public List<Enrollperformancedel> getEnrollperformancedels() {
		return this.enrollperformancedels;
	}

	public void setEnrollperformancedels(List<Enrollperformancedel> enrollperformancedels) {
		this.enrollperformancedels = enrollperformancedels;
	}

	public Enrollperformancedel addEnrollperformancedel(Enrollperformancedel enrollperformancedel) {
		getEnrollperformancedels().add(enrollperformancedel);
		enrollperformancedel.setDelegation(this);

		return enrollperformancedel;
	}

	public Enrollperformancedel removeEnrollperformancedel(Enrollperformancedel enrollperformancedel) {
		getEnrollperformancedels().remove(enrollperformancedel);
		enrollperformancedel.setDelegation(null);

		return enrollperformancedel;
	}

	public List<Filecleansingdelegationwork> getFilecleansingdelegationworks() {
		return this.filecleansingdelegationworks;
	}

	public void setFilecleansingdelegationworks(List<Filecleansingdelegationwork> filecleansingdelegationworks) {
		this.filecleansingdelegationworks = filecleansingdelegationworks;
	}

	public Filecleansingdelegationwork addFilecleansingdelegationwork(Filecleansingdelegationwork filecleansingdelegationwork) {
		getFilecleansingdelegationworks().add(filecleansingdelegationwork);
		filecleansingdelegationwork.setDelegation(this);

		return filecleansingdelegationwork;
	}

	public Filecleansingdelegationwork removeFilecleansingdelegationwork(Filecleansingdelegationwork filecleansingdelegationwork) {
		getFilecleansingdelegationworks().remove(filecleansingdelegationwork);
		filecleansingdelegationwork.setDelegation(null);

		return filecleansingdelegationwork;
	}

	public List<Filecleansingwork> getFilecleansingworks() {
		return this.filecleansingworks;
	}

	public void setFilecleansingworks(List<Filecleansingwork> filecleansingworks) {
		this.filecleansingworks = filecleansingworks;
	}

	public Filecleansingwork addFilecleansingwork(Filecleansingwork filecleansingwork) {
		getFilecleansingworks().add(filecleansingwork);
		filecleansingwork.setDelegation(this);

		return filecleansingwork;
	}

	public Filecleansingwork removeFilecleansingwork(Filecleansingwork filecleansingwork) {
		getFilecleansingworks().remove(filecleansingwork);
		filecleansingwork.setDelegation(null);

		return filecleansingwork;
	}

}