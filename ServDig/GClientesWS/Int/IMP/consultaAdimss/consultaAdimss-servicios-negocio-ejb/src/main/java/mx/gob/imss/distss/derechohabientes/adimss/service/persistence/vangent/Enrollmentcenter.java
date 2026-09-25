package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTCENTER database table.
 * 
 */
@Entity
@NamedQuery(name="Enrollmentcenter.findAll", query="SELECT e FROM Enrollmentcenter e")
public class Enrollmentcenter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrollmentcenter;

	@Temporal(TemporalType.DATE)
	private Date begdate;

	private String cesubtype;

	private String ceunittype;

	private String city;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String economiczone;

	@Temporal(TemporalType.DATE)
	private Date enddate;

	private String endtime;

	private String enrollmentcentername;

	private String externalnumber;

	private BigDecimal idenrollmentstation;

	private String justification;

	private String locality;

	private BigDecimal postalcode;

	private BigDecimal springdifference;

	private String starttime;

	private String street;

	private String suburb;

	private BigDecimal summerdiff;

	private BigDecimal summerdifference;

	private BigDecimal unitnum;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private BigDecimal winterdiff;

	//bi-directional many-to-one association to Cestatus
	@ManyToOne
	@JoinColumn(name="IDCESTATUS")
	private Cestatus cestatus;

	//bi-directional many-to-one association to Cetype
	@ManyToOne
	@JoinColumn(name="IDCETYPE")
	private Cetype cetype;

	//bi-directional many-to-one association to Companymanager
	@ManyToOne
	@JoinColumn(name="IDCOMPANYMANAGER")
	private Companymanager companymanager;

	//bi-directional many-to-one association to Delegation
	@ManyToOne
	@JoinColumn(name="IDDELEGATION")
	private Delegation delegation;

	//bi-directional many-to-one association to Enrollmentctracking
	@OneToMany(mappedBy="enrollmentcenter")
	private List<Enrollmentctracking> enrollmentctrackings;

	public Enrollmentcenter() {
	}

	public long getIdenrollmentcenter() {
		return this.idenrollmentcenter;
	}

	public void setIdenrollmentcenter(long idenrollmentcenter) {
		this.idenrollmentcenter = idenrollmentcenter;
	}

	public Date getBegdate() {
		return this.begdate;
	}

	public void setBegdate(Date begdate) {
		this.begdate = begdate;
	}

	public String getCesubtype() {
		return this.cesubtype;
	}

	public void setCesubtype(String cesubtype) {
		this.cesubtype = cesubtype;
	}

	public String getCeunittype() {
		return this.ceunittype;
	}

	public void setCeunittype(String ceunittype) {
		this.ceunittype = ceunittype;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getEconomiczone() {
		return this.economiczone;
	}

	public void setEconomiczone(String economiczone) {
		this.economiczone = economiczone;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getEnrollmentcentername() {
		return this.enrollmentcentername;
	}

	public void setEnrollmentcentername(String enrollmentcentername) {
		this.enrollmentcentername = enrollmentcentername;
	}

	public String getExternalnumber() {
		return this.externalnumber;
	}

	public void setExternalnumber(String externalnumber) {
		this.externalnumber = externalnumber;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public String getJustification() {
		return this.justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getLocality() {
		return this.locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public BigDecimal getPostalcode() {
		return this.postalcode;
	}

	public void setPostalcode(BigDecimal postalcode) {
		this.postalcode = postalcode;
	}

	public BigDecimal getSpringdifference() {
		return this.springdifference;
	}

	public void setSpringdifference(BigDecimal springdifference) {
		this.springdifference = springdifference;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSuburb() {
		return this.suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public BigDecimal getSummerdiff() {
		return this.summerdiff;
	}

	public void setSummerdiff(BigDecimal summerdiff) {
		this.summerdiff = summerdiff;
	}

	public BigDecimal getSummerdifference() {
		return this.summerdifference;
	}

	public void setSummerdifference(BigDecimal summerdifference) {
		this.summerdifference = summerdifference;
	}

	public BigDecimal getUnitnum() {
		return this.unitnum;
	}

	public void setUnitnum(BigDecimal unitnum) {
		this.unitnum = unitnum;
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

	public BigDecimal getWinterdiff() {
		return this.winterdiff;
	}

	public void setWinterdiff(BigDecimal winterdiff) {
		this.winterdiff = winterdiff;
	}

	public Cestatus getCestatus() {
		return this.cestatus;
	}

	public void setCestatus(Cestatus cestatus) {
		this.cestatus = cestatus;
	}

	public Cetype getCetype() {
		return this.cetype;
	}

	public void setCetype(Cetype cetype) {
		this.cetype = cetype;
	}

	public Companymanager getCompanymanager() {
		return this.companymanager;
	}

	public void setCompanymanager(Companymanager companymanager) {
		this.companymanager = companymanager;
	}

	public Delegation getDelegation() {
		return this.delegation;
	}

	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}

	public List<Enrollmentctracking> getEnrollmentctrackings() {
		return this.enrollmentctrackings;
	}

	public void setEnrollmentctrackings(List<Enrollmentctracking> enrollmentctrackings) {
		this.enrollmentctrackings = enrollmentctrackings;
	}

	public Enrollmentctracking addEnrollmentctracking(Enrollmentctracking enrollmentctracking) {
		getEnrollmentctrackings().add(enrollmentctracking);
		enrollmentctracking.setEnrollmentcenter(this);

		return enrollmentctracking;
	}

	public Enrollmentctracking removeEnrollmentctracking(Enrollmentctracking enrollmentctracking) {
		getEnrollmentctrackings().remove(enrollmentctracking);
		enrollmentctracking.setEnrollmentcenter(null);

		return enrollmentctracking;
	}

}