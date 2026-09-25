package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CONTROLADIMSSHIST database table.
 * 
 */
@Entity
@NamedQuery(name="Controladimsshist.findAll", query="SELECT c FROM Controladimsshist c")
public class Controladimsshist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idctlhist;

	private BigDecimal createdby;

	@Temporal(TemporalType.DATE)
	private Date createdon;

	@Column(name="CVE_CALIDAD_ASEG")
	private BigDecimal cveCalidadAseg;

	@Column(name="CVE_FOLIO_EXPEDICION")
	private BigDecimal cveFolioExpedicion;

	private String enrolcurp;

//	private Object enrolendtime;

	private String enrolfirstname;

	private String enrollastname1;

	private String enrollastname2;

//	private Object enrolstarttime;

	private BigDecimal idenrol;

	private BigDecimal idenrollmentstation;

	private BigDecimal idenrolstate;

	private BigDecimal idoperator;

	private String idstatuscarga;

	@Column(name="NUM_FOLIO_PERSONA")
	private BigDecimal numFolioPersona;

	@Column(name="NUM_NSS_ASEGURADO")
	private BigDecimal numNssAsegurado;

	public Controladimsshist() {
	}

	public long getIdctlhist() {
		return this.idctlhist;
	}

	public void setIdctlhist(long idctlhist) {
		this.idctlhist = idctlhist;
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

//	public Object getEnrolendtime() {
//		return this.enrolendtime;
//	}
//
//	public void setEnrolendtime(Object enrolendtime) {
//		this.enrolendtime = enrolendtime;
//	}

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

//	public Object getEnrolstarttime() {
//		return this.enrolstarttime;
//	}
//
//	public void setEnrolstarttime(Object enrolstarttime) {
//		this.enrolstarttime = enrolstarttime;
//	}

	public BigDecimal getIdenrol() {
		return this.idenrol;
	}

	public void setIdenrol(BigDecimal idenrol) {
		this.idenrol = idenrol;
	}

	public BigDecimal getIdenrollmentstation() {
		return this.idenrollmentstation;
	}

	public void setIdenrollmentstation(BigDecimal idenrollmentstation) {
		this.idenrollmentstation = idenrollmentstation;
	}

	public BigDecimal getIdenrolstate() {
		return this.idenrolstate;
	}

	public void setIdenrolstate(BigDecimal idenrolstate) {
		this.idenrolstate = idenrolstate;
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

}