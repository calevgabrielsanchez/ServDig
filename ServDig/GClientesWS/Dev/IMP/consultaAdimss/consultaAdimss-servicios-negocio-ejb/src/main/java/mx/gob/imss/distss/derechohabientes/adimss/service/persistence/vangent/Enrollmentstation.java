package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ENROLLMENTSTATIONS database table.
 * 
 */
@Entity
@Table(name="ENROLLMENTSTATIONS")
@NamedQuery(name="Enrollmentstation.findAll", query="SELECT e FROM Enrollmentstation e")
public class Enrollmentstation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idenrollmentstation;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	private String domainstation;

	private String enrollmentstationdescription;

	private String enrollmentstationipaddress;

	private String enrollmentstationmacaddress;

	private String enrollmentstationname;

	private BigDecimal folio;

	@Column(name="FOLIO_ERR")
	private BigDecimal folioErr;

	private BigDecimal idhyperic;

	private BigDecimal idnaspath;

	private BigDecimal idstage;

	private BigDecimal idsupervisor;

	private String omobileuser;

	private String replistorlicense;

	private String stationstatus;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	private String userstation;
	
	@ManyToOne
	@JoinColumn(name="IDNASPATH")
	private Naspath naspath;
	
	//bi-directional many-to-one association to Adimssevent
	@OneToMany(mappedBy="enrollmentstation")
	private List<Adimssevent> adimssevents;

	//bi-directional many-to-one association to Card
	@OneToMany(mappedBy="enrollmentstation")
	private List<Card> cards;

	//bi-directional many-to-one association to Companycelife
	@OneToMany(mappedBy="enrollmentstation")
	private List<Companycelife> companycelifes;

	//bi-directional many-to-one association to Demographicsumary
	@OneToMany(mappedBy="enrollmentstation")
	private List<Demographicsumary> demographicsumaries;

	//bi-directional many-to-one association to Enrollmentctracking
	@OneToMany(mappedBy="enrollmentstation")
	private List<Enrollmentctracking> enrollmentctrackings;

	//bi-directional many-to-one association to Enrollmentnotification
	@OneToMany(mappedBy="enrollmentstation")
	private List<Enrollmentnotification> enrollmentnotifications;

	//bi-directional many-to-one association to Enrollment
	@OneToMany(mappedBy="enrollmentstation")
	private List<Enrollment> enrollments;

	//bi-directional many-to-one association to Enrollmentsperhourday
	@OneToMany(mappedBy="enrollmentstation")
	private List<Enrollmentsperhourday> enrollmentsperhourdays;

	//bi-directional one-to-one association to Enrollmentstationmig
	@OneToOne(mappedBy="enrollmentstation")
	private Enrollmentstationmig enrollmentstationmig;

	//bi-directional many-to-one association to Enrollmentlocation
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTLOCATION")
	private Enrollmentlocation enrollmentlocation;

	//bi-directional many-to-one association to Fingerprintsummary
	@OneToMany(mappedBy="enrollmentstation")
	private List<Fingerprintsummary> fingerprintsummaries;

	//bi-directional many-to-one association to Hoursoperation
	@OneToMany(mappedBy="enrollmentstation")
	private List<Hoursoperation> hoursoperations;

	//bi-directional many-to-one association to Icaosummary
	@OneToMany(mappedBy="enrollmentstation")
	private List<Icaosummary> icaosummaries;

	//bi-directional many-to-one association to Operatordetail
	@OneToMany(mappedBy="enrollmentstation")
	private List<Operatordetail> operatordetails;

	//bi-directional many-to-one association to Production
	@OneToMany(mappedBy="enrollmentstation")
	private List<Production> productions;

	//bi-directional many-to-one association to Productionbehaviour
	@OneToMany(mappedBy="enrollmentstation")
	private List<Productionbehaviour> productionbehaviours;

	//bi-directional many-to-one association to Timedetail
	@OneToMany(mappedBy="enrollmentstation")
	private List<Timedetail> timedetails;

	//bi-directional many-to-one association to Timesummary
	@OneToMany(mappedBy="enrollmentstation")
	private List<Timesummary> timesummaries;

	//bi-directional many-to-one association to Userattention
	@OneToMany(mappedBy="enrollmentstation")
	private List<Userattention> userattentions;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="enrollmentstation")
	private List<User> users;

	public Enrollmentstation() {
	}

	public long getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(long idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
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

	public String getDomainstation() {
		return this.domainstation;
	}

	public void setDomainstation(String domainstation) {
		this.domainstation = domainstation;
	}

	public String getEnrollmentstationdescription() {
		return this.enrollmentstationdescription;
	}

	public void setEnrollmentstationdescription(String enrollmentstationdescription) {
		this.enrollmentstationdescription = enrollmentstationdescription;
	}

	public String getEnrollmentstationipaddress() {
		return this.enrollmentstationipaddress;
	}

	public void setEnrollmentstationipaddress(String enrollmentstationipaddress) {
		this.enrollmentstationipaddress = enrollmentstationipaddress;
	}

	public String getEnrollmentstationmacaddress() {
		return this.enrollmentstationmacaddress;
	}

	public void setEnrollmentstationmacaddress(String enrollmentstationmacaddress) {
		this.enrollmentstationmacaddress = enrollmentstationmacaddress;
	}

	public String getEnrollmentstationname() {
		return this.enrollmentstationname;
	}

	public void setEnrollmentstationname(String enrollmentstationname) {
		this.enrollmentstationname = enrollmentstationname;
	}

	public BigDecimal getFolio() {
		return this.folio;
	}

	public void setFolio(BigDecimal folio) {
		this.folio = folio;
	}

	public BigDecimal getFolioErr() {
		return this.folioErr;
	}

	public void setFolioErr(BigDecimal folioErr) {
		this.folioErr = folioErr;
	}

	public BigDecimal getIdhyperic() {
		return this.idhyperic;
	}

	public void setIdhyperic(BigDecimal idhyperic) {
		this.idhyperic = idhyperic;
	}

	public BigDecimal getIdnaspath() {
		return this.idnaspath;
	}

	public void setIdnaspath(BigDecimal idnaspath) {
		this.idnaspath = idnaspath;
	}

	public BigDecimal getIdstage() {
		return this.idstage;
	}

	public void setIdstage(BigDecimal idstage) {
		this.idstage = idstage;
	}

	public BigDecimal getIdsupervisor() {
		return this.idsupervisor;
	}

	public void setIdsupervisor(BigDecimal idsupervisor) {
		this.idsupervisor = idsupervisor;
	}

	public String getOmobileuser() {
		return this.omobileuser;
	}

	public void setOmobileuser(String omobileuser) {
		this.omobileuser = omobileuser;
	}

	public String getReplistorlicense() {
		return this.replistorlicense;
	}

	public void setReplistorlicense(String replistorlicense) {
		this.replistorlicense = replistorlicense;
	}

	public String getStationstatus() {
		return this.stationstatus;
	}

	public void setStationstatus(String stationstatus) {
		this.stationstatus = stationstatus;
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

	public String getUserstation() {
		return this.userstation;
	}

	public void setUserstation(String userstation) {
		this.userstation = userstation;
	}

	public List<Adimssevent> getAdimssevents() {
		return this.adimssevents;
	}

	public void setAdimssevents(List<Adimssevent> adimssevents) {
		this.adimssevents = adimssevents;
	}

	public Adimssevent addAdimssevent(Adimssevent adimssevent) {
		getAdimssevents().add(adimssevent);
		adimssevent.setEnrollmentstation(this);

		return adimssevent;
	}

	public Adimssevent removeAdimssevent(Adimssevent adimssevent) {
		getAdimssevents().remove(adimssevent);
		adimssevent.setEnrollmentstation(null);

		return adimssevent;
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Card addCard(Card card) {
		getCards().add(card);
		card.setEnrollmentstation(this);

		return card;
	}

	public Card removeCard(Card card) {
		getCards().remove(card);
		card.setEnrollmentstation(null);

		return card;
	}

	public List<Companycelife> getCompanycelifes() {
		return this.companycelifes;
	}

	public void setCompanycelifes(List<Companycelife> companycelifes) {
		this.companycelifes = companycelifes;
	}

	public Companycelife addCompanycelife(Companycelife companycelife) {
		getCompanycelifes().add(companycelife);
		companycelife.setEnrollmentstation(this);

		return companycelife;
	}

	public Companycelife removeCompanycelife(Companycelife companycelife) {
		getCompanycelifes().remove(companycelife);
		companycelife.setEnrollmentstation(null);

		return companycelife;
	}

	public List<Demographicsumary> getDemographicsumaries() {
		return this.demographicsumaries;
	}

	public void setDemographicsumaries(List<Demographicsumary> demographicsumaries) {
		this.demographicsumaries = demographicsumaries;
	}

	public Demographicsumary addDemographicsumary(Demographicsumary demographicsumary) {
		getDemographicsumaries().add(demographicsumary);
		demographicsumary.setEnrollmentstation(this);

		return demographicsumary;
	}

	public Demographicsumary removeDemographicsumary(Demographicsumary demographicsumary) {
		getDemographicsumaries().remove(demographicsumary);
		demographicsumary.setEnrollmentstation(null);

		return demographicsumary;
	}

	public List<Enrollmentctracking> getEnrollmentctrackings() {
		return this.enrollmentctrackings;
	}

	public void setEnrollmentctrackings(List<Enrollmentctracking> enrollmentctrackings) {
		this.enrollmentctrackings = enrollmentctrackings;
	}

	public Enrollmentctracking addEnrollmentctracking(Enrollmentctracking enrollmentctracking) {
		getEnrollmentctrackings().add(enrollmentctracking);
		enrollmentctracking.setEnrollmentstation(this);

		return enrollmentctracking;
	}

	public Enrollmentctracking removeEnrollmentctracking(Enrollmentctracking enrollmentctracking) {
		getEnrollmentctrackings().remove(enrollmentctracking);
		enrollmentctracking.setEnrollmentstation(null);

		return enrollmentctracking;
	}

	public List<Enrollmentnotification> getEnrollmentnotifications() {
		return this.enrollmentnotifications;
	}

	public void setEnrollmentnotifications(List<Enrollmentnotification> enrollmentnotifications) {
		this.enrollmentnotifications = enrollmentnotifications;
	}

	public Enrollmentnotification addEnrollmentnotification(Enrollmentnotification enrollmentnotification) {
		getEnrollmentnotifications().add(enrollmentnotification);
		enrollmentnotification.setEnrollmentstation(this);

		return enrollmentnotification;
	}

	public Enrollmentnotification removeEnrollmentnotification(Enrollmentnotification enrollmentnotification) {
		getEnrollmentnotifications().remove(enrollmentnotification);
		enrollmentnotification.setEnrollmentstation(null);

		return enrollmentnotification;
	}

	public List<Enrollment> getEnrollments() {
		return this.enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	public Enrollment addEnrollment(Enrollment enrollment) {
		getEnrollments().add(enrollment);
		enrollment.setEnrollmentstation(this);

		return enrollment;
	}

	public Enrollment removeEnrollment(Enrollment enrollment) {
		getEnrollments().remove(enrollment);
		enrollment.setEnrollmentstation(null);

		return enrollment;
	}

	public List<Enrollmentsperhourday> getEnrollmentsperhourdays() {
		return this.enrollmentsperhourdays;
	}

	public void setEnrollmentsperhourdays(List<Enrollmentsperhourday> enrollmentsperhourdays) {
		this.enrollmentsperhourdays = enrollmentsperhourdays;
	}

	public Enrollmentsperhourday addEnrollmentsperhourday(Enrollmentsperhourday enrollmentsperhourday) {
		getEnrollmentsperhourdays().add(enrollmentsperhourday);
		enrollmentsperhourday.setEnrollmentstation(this);

		return enrollmentsperhourday;
	}

	public Enrollmentsperhourday removeEnrollmentsperhourday(Enrollmentsperhourday enrollmentsperhourday) {
		getEnrollmentsperhourdays().remove(enrollmentsperhourday);
		enrollmentsperhourday.setEnrollmentstation(null);

		return enrollmentsperhourday;
	}

	public Enrollmentstationmig getEnrollmentstationmig() {
		return this.enrollmentstationmig;
	}

	public void setEnrollmentstationmig(Enrollmentstationmig enrollmentstationmig) {
		this.enrollmentstationmig = enrollmentstationmig;
	}

	public Enrollmentlocation getEnrollmentlocation() {
		return this.enrollmentlocation;
	}

	public void setEnrollmentlocation(Enrollmentlocation enrollmentlocation) {
		this.enrollmentlocation = enrollmentlocation;
	}

	public List<Fingerprintsummary> getFingerprintsummaries() {
		return this.fingerprintsummaries;
	}

	public void setFingerprintsummaries(List<Fingerprintsummary> fingerprintsummaries) {
		this.fingerprintsummaries = fingerprintsummaries;
	}

	public Fingerprintsummary addFingerprintsummary(Fingerprintsummary fingerprintsummary) {
		getFingerprintsummaries().add(fingerprintsummary);
		fingerprintsummary.setEnrollmentstation(this);

		return fingerprintsummary;
	}

	public Fingerprintsummary removeFingerprintsummary(Fingerprintsummary fingerprintsummary) {
		getFingerprintsummaries().remove(fingerprintsummary);
		fingerprintsummary.setEnrollmentstation(null);

		return fingerprintsummary;
	}

	public List<Hoursoperation> getHoursoperations() {
		return this.hoursoperations;
	}

	public void setHoursoperations(List<Hoursoperation> hoursoperations) {
		this.hoursoperations = hoursoperations;
	}

	public Hoursoperation addHoursoperation(Hoursoperation hoursoperation) {
		getHoursoperations().add(hoursoperation);
		hoursoperation.setEnrollmentstation(this);

		return hoursoperation;
	}

	public Hoursoperation removeHoursoperation(Hoursoperation hoursoperation) {
		getHoursoperations().remove(hoursoperation);
		hoursoperation.setEnrollmentstation(null);

		return hoursoperation;
	}

	public List<Icaosummary> getIcaosummaries() {
		return this.icaosummaries;
	}

	public void setIcaosummaries(List<Icaosummary> icaosummaries) {
		this.icaosummaries = icaosummaries;
	}

	public Icaosummary addIcaosummary(Icaosummary icaosummary) {
		getIcaosummaries().add(icaosummary);
		icaosummary.setEnrollmentstation(this);

		return icaosummary;
	}

	public Icaosummary removeIcaosummary(Icaosummary icaosummary) {
		getIcaosummaries().remove(icaosummary);
		icaosummary.setEnrollmentstation(null);

		return icaosummary;
	}

	public List<Operatordetail> getOperatordetails() {
		return this.operatordetails;
	}

	public void setOperatordetails(List<Operatordetail> operatordetails) {
		this.operatordetails = operatordetails;
	}

	public Operatordetail addOperatordetail(Operatordetail operatordetail) {
		getOperatordetails().add(operatordetail);
		operatordetail.setEnrollmentstation(this);

		return operatordetail;
	}

	public Operatordetail removeOperatordetail(Operatordetail operatordetail) {
		getOperatordetails().remove(operatordetail);
		operatordetail.setEnrollmentstation(null);

		return operatordetail;
	}

	public List<Production> getProductions() {
		return this.productions;
	}

	public void setProductions(List<Production> productions) {
		this.productions = productions;
	}

	public Production addProduction(Production production) {
		getProductions().add(production);
		production.setEnrollmentstation(this);

		return production;
	}

	public Production removeProduction(Production production) {
		getProductions().remove(production);
		production.setEnrollmentstation(null);

		return production;
	}

	public List<Productionbehaviour> getProductionbehaviours() {
		return this.productionbehaviours;
	}

	public void setProductionbehaviours(List<Productionbehaviour> productionbehaviours) {
		this.productionbehaviours = productionbehaviours;
	}

	public Productionbehaviour addProductionbehaviour(Productionbehaviour productionbehaviour) {
		getProductionbehaviours().add(productionbehaviour);
		productionbehaviour.setEnrollmentstation(this);

		return productionbehaviour;
	}

	public Productionbehaviour removeProductionbehaviour(Productionbehaviour productionbehaviour) {
		getProductionbehaviours().remove(productionbehaviour);
		productionbehaviour.setEnrollmentstation(null);

		return productionbehaviour;
	}

	public List<Timedetail> getTimedetails() {
		return this.timedetails;
	}

	public void setTimedetails(List<Timedetail> timedetails) {
		this.timedetails = timedetails;
	}

	public Timedetail addTimedetail(Timedetail timedetail) {
		getTimedetails().add(timedetail);
		timedetail.setEnrollmentstation(this);

		return timedetail;
	}

	public Timedetail removeTimedetail(Timedetail timedetail) {
		getTimedetails().remove(timedetail);
		timedetail.setEnrollmentstation(null);

		return timedetail;
	}

	public List<Timesummary> getTimesummaries() {
		return this.timesummaries;
	}

	public void setTimesummaries(List<Timesummary> timesummaries) {
		this.timesummaries = timesummaries;
	}

	public Timesummary addTimesummary(Timesummary timesummary) {
		getTimesummaries().add(timesummary);
		timesummary.setEnrollmentstation(this);

		return timesummary;
	}

	public Timesummary removeTimesummary(Timesummary timesummary) {
		getTimesummaries().remove(timesummary);
		timesummary.setEnrollmentstation(null);

		return timesummary;
	}

	public List<Userattention> getUserattentions() {
		return this.userattentions;
	}

	public void setUserattentions(List<Userattention> userattentions) {
		this.userattentions = userattentions;
	}

	public Userattention addUserattention(Userattention userattention) {
		getUserattentions().add(userattention);
		userattention.setEnrollmentstation(this);

		return userattention;
	}

	public Userattention removeUserattention(Userattention userattention) {
		getUserattentions().remove(userattention);
		userattention.setEnrollmentstation(null);

		return userattention;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setEnrollmentstation(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setEnrollmentstation(null);

		return user;
	}

	public Naspath getNaspath() {
		return naspath;
	}

	public void setNaspath(Naspath naspath) {
		this.naspath = naspath;
	}
	
	

}