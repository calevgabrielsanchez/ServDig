package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CONTROLADIMSS database table.
 * 
 */
@Entity
@NamedQuery(name="Controladimss.findAll", query="SELECT c FROM Controladimss c")
public class Controladimss implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ControladimssPK id;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="CVE_CALIDAD_ASEG")
	private BigDecimal cveCalidadAseg;

	@Column(name="CVE_FOLIO_EXPEDICION")
	private BigDecimal cveFolioExpedicion;

	private String enrolcurp;

	@Temporal(TemporalType.DATE)
	@Column(name="enrolendtime")
	private Date enrolendtime;

	private String enrolfirstname;

	private String enrollastname1;

	private String enrollastname2;

	@Temporal(TemporalType.DATE)
	@Column(name="enrolstarttime")
	private Date enrolstarttime;

	private BigDecimal idoperator;

	private String idstatuscarga;

	@Column(name="NUM_FOLIO_PERSONA")
	private BigDecimal numFolioPersona;

	@Column(name="NUM_NSS_ASEGURADO")
	private BigDecimal numNssAsegurado;

	private BigDecimal updatedby;

	@Temporal(TemporalType.DATE)
	private Date updatedon;

	//bi-directional many-to-one association to Enrollmentstate
	@ManyToOne
	@JoinColumn(name="IDENROLSTATE")
	private Enrollmentstate enrollmentstate;

	
	@OneToMany
	@JoinColumns({
		@JoinColumn(name="IDENROL", referencedColumnName="IDENROL"),
		@JoinColumn(name="IDENROLLMENTSTATION", referencedColumnName="IDENROLLMENTSTATION")
	})
	private List<Documentuminterface> documentumInterfaces;
	
	@ManyToOne
	@JoinColumn(name="IDENROLLMENTSTATION")
	private Enrollmentstation enrollmentstation;
	
	public Controladimss() {
	}

	public ControladimssPK getId() {
		return this.id;
	}

	public void setId(ControladimssPK id) {
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

	public BigDecimal getCveCalidadAseg() {
		return this.cveCalidadAseg;
	}

	public void setCveCalidadAseg(BigDecimal cveCalidadAseg) {
		this.cveCalidadAseg = cveCalidadAseg;
	}

	public BigDecimal getCveFolioExpedicion() {
		return this.cveFolioExpedicion;
	}

	public void setCveFolioExpedicion(BigDecimal cveFolioExpedicion) {
		this.cveFolioExpedicion = cveFolioExpedicion;
	}

	public String getEnrolcurp() {
		return this.enrolcurp;
	}

	public void setEnrolcurp(String enrolcurp) {
		this.enrolcurp = enrolcurp;
	}

	public Date getEnrolendtime() {
		return this.enrolendtime;
	}

	public void setEnrolendtime(Date enrolendtime) {
		this.enrolendtime = enrolendtime;
	}

	public String getEnrolfirstname() {
		return this.enrolfirstname;
	}

	public void setEnrolfirstname(String enrolfirstname) {
		this.enrolfirstname = enrolfirstname;
	}

	public String getEnrollastname1() {
		return this.enrollastname1;
	}

	public void setEnrollastname1(String enrollastname1) {
		this.enrollastname1 = enrollastname1;
	}

	public String getEnrollastname2() {
		return this.enrollastname2;
	}

	public void setEnrollastname2(String enrollastname2) {
		this.enrollastname2 = enrollastname2;
	}

	public Date getEnrolstarttime() {
		return this.enrolstarttime;
	}

	public void setEnrolstarttime(Date enrolstarttime) {
		this.enrolstarttime = enrolstarttime;
	}

	public BigDecimal getIdoperator() {
		return this.idoperator;
	}

	public void setIdoperator(BigDecimal idoperator) {
		this.idoperator = idoperator;
	}

	public String getIdstatuscarga() {
		return this.idstatuscarga;
	}

	public void setIdstatuscarga(String idstatuscarga) {
		this.idstatuscarga = idstatuscarga;
	}

	public BigDecimal getNumFolioPersona() {
		return this.numFolioPersona;
	}

	public void setNumFolioPersona(BigDecimal numFolioPersona) {
		this.numFolioPersona = numFolioPersona;
	}

	public BigDecimal getNumNssAsegurado() {
		return this.numNssAsegurado;
	}

	public void setNumNssAsegurado(BigDecimal numNssAsegurado) {
		this.numNssAsegurado = numNssAsegurado;
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

	public Enrollmentstate getEnrollmentstate() {
		return this.enrollmentstate;
	}

	public void setEnrollmentstate(Enrollmentstate enrollmentstate) {
		this.enrollmentstate = enrollmentstate;
	}

	public List<Documentuminterface> getDocumentumInterfaces() {
		return documentumInterfaces;
	}

	public void setDocumentumInterfaces(
			List<Documentuminterface> documentumInterfaces) {
		this.documentumInterfaces = documentumInterfaces;
	}

	public Enrollmentstation getEnrollmentstation() {
		return enrollmentstation;
	}

	public void setEnrollmentstation(Enrollmentstation enrollmentstation) {
		this.enrollmentstation = enrollmentstation;
	}
	
	
	
	

}