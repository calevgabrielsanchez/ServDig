package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CHGREEXPEDITIONS database table.
 * 
 */
@Entity
@Table(name="CHGREEXPEDITIONS")
@NamedQuery(name="Chgreexpedition.findAll", query="SELECT c FROM Chgreexpedition c")
public class Chgreexpedition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long idchgreexpeditions;

	@Temporal(TemporalType.DATE)
	private Date chgdate;

	private BigDecimal cosmo;

	@Column(name="CVE_CALIDAD_ASEG")
	private BigDecimal cveCalidadAseg;

	private BigDecimal dh;

	private String enrolcurp;

	@Temporal(TemporalType.DATE)
	private Date enroldate;

	private String enroname;

	private BigDecimal idchgreexprango;

	private BigDecimal idenrol;

	private BigDecimal idenrollmentstation;

	private BigDecimal idenrolstate;

	private BigDecimal idenrolsubtype;

	private BigDecimal imss;

	@Column(name="NUM_NSS_ASEGURADO")
	private BigDecimal numNssAsegurado;

	private BigDecimal reproceso;

	private String stationstatus;

	private BigDecimal vangent;

	public Chgreexpedition() {
	}

	public long getIdchgreexpeditions() {
		return this.idchgreexpeditions;
	}

	public void setIdchgreexpeditions(long idchgreexpeditions) {
		this.idchgreexpeditions = idchgreexpeditions;
	}

	public Date getChgdate() {
		return this.chgdate;
	}

	public void setChgdate(Date chgdate) {
		this.chgdate = chgdate;
	}

	public BigDecimal getCosmo() {
		return this.cosmo;
	}

	public void setCosmo(BigDecimal cosmo) {
		this.cosmo = cosmo;
	}

	public BigDecimal getCveCalidadAseg() {
		return this.cveCalidadAseg;
	}

	public void setCveCalidadAseg(BigDecimal cveCalidadAseg) {
		this.cveCalidadAseg = cveCalidadAseg;
	}

	public BigDecimal getDh() {
		return this.dh;
	}

	public void setDh(BigDecimal dh) {
		this.dh = dh;
	}

	public String getEnrolcurp() {
		return this.enrolcurp;
	}

	public void setEnrolcurp(String enrolcurp) {
		this.enrolcurp = enrolcurp;
	}

	public Date getEnroldate() {
		return this.enroldate;
	}

	public void setEnroldate(Date enroldate) {
		this.enroldate = enroldate;
	}

	public String getEnroname() {
		return this.enroname;
	}

	public void setEnroname(String enroname) {
		this.enroname = enroname;
	}

	public BigDecimal getIdchgreexprango() {
		return this.idchgreexprango;
	}

	public void setIdchgreexprango(BigDecimal idchgreexprango) {
		this.idchgreexprango = idchgreexprango;
	}

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

	public BigDecimal getIdenrolsubtype() {
		return this.idenrolsubtype;
	}

	public void setIdenrolsubtype(BigDecimal idenrolsubtype) {
		this.idenrolsubtype = idenrolsubtype;
	}

	public BigDecimal getImss() {
		return this.imss;
	}

	public void setImss(BigDecimal imss) {
		this.imss = imss;
	}

	public BigDecimal getNumNssAsegurado() {
		return this.numNssAsegurado;
	}

	public void setNumNssAsegurado(BigDecimal numNssAsegurado) {
		this.numNssAsegurado = numNssAsegurado;
	}

	public BigDecimal getReproceso() {
		return this.reproceso;
	}

	public void setReproceso(BigDecimal reproceso) {
		this.reproceso = reproceso;
	}

	public String getStationstatus() {
		return this.stationstatus;
	}

	public void setStationstatus(String stationstatus) {
		this.stationstatus = stationstatus;
	}

	public BigDecimal getVangent() {
		return this.vangent;
	}

	public void setVangent(BigDecimal vangent) {
		this.vangent = vangent;
	}

}