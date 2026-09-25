package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DEMOGRAPHICSUMARY database table.
 * 
 */
@Entity
@NamedQuery(name="Demographicsumary.findAll", query="SELECT d FROM Demographicsumary d")
public class Demographicsumary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long iddemographicsumary;

	private BigDecimal adults;

	private BigDecimal children;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Temporal(TemporalType.DATE)
	private Date demographicsumarydate;

	private BigDecimal elderly;

	@Column(name="\"FOREIGN\"")
	private BigDecimal foreign;

	private BigDecimal livesintheenrollocaliy;

	private BigDecimal married;

	private BigDecimal men;

	private BigDecimal mexicans;

	@Column(name="\"MONTH\"")
	private BigDecimal month;

	private BigDecimal single;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private BigDecimal women;

	@Column(name="\"YEAR\"")
	private BigDecimal year;

	private BigDecimal young;

	//bi-directional many-to-one association to Enrollmentstation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;

	public Demographicsumary() {
	}

	public long getIddemographicsumary() {
		return this.iddemographicsumary;
	}

	public void setIddemographicsumary(long iddemographicsumary) {
		this.iddemographicsumary = iddemographicsumary;
	}

	public BigDecimal getAdults() {
		return this.adults;
	}

	public void setAdults(BigDecimal adults) {
		this.adults = adults;
	}

	public BigDecimal getChildren() {
		return this.children;
	}

	public void setChildren(BigDecimal children) {
		this.children = children;
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

	public Date getDemographicsumarydate() {
		return this.demographicsumarydate;
	}

	public void setDemographicsumarydate(Date demographicsumarydate) {
		this.demographicsumarydate = demographicsumarydate;
	}

	public BigDecimal getElderly() {
		return this.elderly;
	}

	public void setElderly(BigDecimal elderly) {
		this.elderly = elderly;
	}

	public BigDecimal getForeign() {
		return this.foreign;
	}

	public void setForeign(BigDecimal foreign) {
		this.foreign = foreign;
	}

	public BigDecimal getLivesintheenrollocaliy() {
		return this.livesintheenrollocaliy;
	}

	public void setLivesintheenrollocaliy(BigDecimal livesintheenrollocaliy) {
		this.livesintheenrollocaliy = livesintheenrollocaliy;
	}

	public BigDecimal getMarried() {
		return this.married;
	}

	public void setMarried(BigDecimal married) {
		this.married = married;
	}

	public BigDecimal getMen() {
		return this.men;
	}

	public void setMen(BigDecimal men) {
		this.men = men;
	}

	public BigDecimal getMexicans() {
		return this.mexicans;
	}

	public void setMexicans(BigDecimal mexicans) {
		this.mexicans = mexicans;
	}

	public BigDecimal getMonth() {
		return this.month;
	}

	public void setMonth(BigDecimal month) {
		this.month = month;
	}

	public BigDecimal getSingle() {
		return this.single;
	}

	public void setSingle(BigDecimal single) {
		this.single = single;
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

	public BigDecimal getWomen() {
		return this.women;
	}

	public void setWomen(BigDecimal women) {
		this.women = women;
	}

	public BigDecimal getYear() {
		return this.year;
	}

	public void setYear(BigDecimal year) {
		this.year = year;
	}

	public BigDecimal getYoung() {
		return this.young;
	}

	public void setYoung(BigDecimal young) {
		this.young = young;
	}

	public Enrollmentstation getEnrollmentstation() {
		return this.enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}

}