package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatStatus;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgtCatCriterioSeleccion;

/**
 * The persistent class for the CGT_CORRECCION database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractCgtCorreccion extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	public String folio;

	@Transient
	public String folioNum;

	@Transient
	public String folioAnio;

	@Temporal(TemporalType.DATE)
	private Date aacp;

	@Temporal(TemporalType.DATE)
	private Date aapp;

	@Temporal(TemporalType.DATE)
	private Date aasr;

	private BigDecimal acopconvsp;

	private String afil15;

	private String anoconvenio;

	/**
	 * oficio de invitacion
	 */
	private String anooficiooi;

	private BigDecimal anoparcialidades;

	@Temporal(TemporalType.DATE)
	private Date aoi;

	@Temporal(TemporalType.DATE)
	private Date aoin;

	@Temporal(TemporalType.DATE)
	private Date ap;

	private BigDecimal arcvconvsp;

	@Temporal(TemporalType.DATE)
	private Date asa;

	@Temporal(TemporalType.DATE)
	private Date asp;

	@Temporal(TemporalType.DATE)
	private Date asr;

	@Temporal(TemporalType.DATE)
	private Date cc;

	@Temporal(TemporalType.DATE)
	private Date cds;
	/**
	 * Oficio de cancelacion
	 */
	private String cnooficio;

	@Temporal(TemporalType.DATE)
	private Date cpai;

	private BigDecimal cpresentopagos;

	@Temporal(TemporalType.DATE)
	private Date csd;

	@Column(name = "CVE_MODALIDAD")
	private BigDecimal cveModalidad;

	@Column(name = "CVE_PATRON")
	private String cvePatron;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	private BigDecimal dv;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG")
	private Date fecFechareg;

	@Column(name = "ID_MOTICOCANCELACION")
	private BigDecimal idMoticocancelacion;

	@Column(name = "ID_MOTIVORECHAZO")
	private Integer idMotivoRechazo;

	@Column(name = "ID_AUDITOR")
	private Long idAuditor;

	private String nombre;

	@Temporal(TemporalType.DATE)
	private Date periodoal;

	@Temporal(TemporalType.DATE)
	private Date periododel;

	@Temporal(TemporalType.DATE)
	private Date rccpr;

	private BigDecimal rcopconvsp;

	@Temporal(TemporalType.DATE)
	private Date rcr;

	@Temporal(TemporalType.DATE)
	private Date rdc;

	private String rnoconvenio;

	@Temporal(TemporalType.DATE)
	private Date rnod;

	private String rnooficiood;

	private String rnooficiorde;

	private BigDecimal rnoparcialidades;

	@Temporal(TemporalType.DATE)
	private Date rod;

	private BigDecimal rporccr;

	private BigDecimal rrcvconvsp;

	@Temporal(TemporalType.DATE)
	private Date rrde;

	@Temporal(TemporalType.DATE)
	private Date rrdn;

	@Temporal(TemporalType.DATE)
	private Date rvda;

	@Temporal(TemporalType.DATE)
	private Date rvpp;

	@Temporal(TemporalType.DATE)
	private Date rvtc;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_SUBDELEGACION", insertable = true, updatable = true)
	private SacSubdelegacion sacSubdelegacion;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO")
	private CgcCatTipo cgcCatTipo;

	@ManyToOne
	@JoinColumn(name = "ID_CRITERIOSELECCION")
	private CgtCatCriterioSeleccion cgtCatCriterioSeleccion;

	@ManyToOne
	@JoinColumn(name = "ID_ORIGEN")
	private CgcCatOrigen cgcCatOrigen;

	@ManyToOne
	@JoinColumn(name = "ID_STATUS")
	private CgcCatStatus cgcCatStatus;

	public CgcCatStatus getCgcCatStatus() {
		return cgcCatStatus;
	}

	public void setCgcCatStatus(CgcCatStatus cgcCatStatus) {
		this.cgcCatStatus = cgcCatStatus;
	}

	public CgcCatTipo getCgcCatTipo() {
		return cgcCatTipo;
	}

	public void setCgcCatTipo(CgcCatTipo cgcCatTipo) {
		this.cgcCatTipo = cgcCatTipo;
	}

	public CgtCatCriterioSeleccion getCgtCatCriterioSeleccion() {
		return cgtCatCriterioSeleccion;
	}

	public void setCgtCatCriterioSeleccion(
			CgtCatCriterioSeleccion cgtCatCriterioSeleccion) {
		this.cgtCatCriterioSeleccion = cgtCatCriterioSeleccion;
	}

	public CgcCatOrigen getCgcCatOrigen() {
		return cgcCatOrigen;
	}

	public void setCgcCatOrigen(CgcCatOrigen cgcCatOrigen) {
		this.cgcCatOrigen = cgcCatOrigen;
	}

	public AbstractCgtCorreccion() {
	}

	public String getFolio() {
		return this.folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Date getAacp() {
		return this.aacp;
	}

	public void setAacp(Date aacp) {
		this.aacp = aacp;
	}

	public Date getAapp() {
		return this.aapp;
	}

	public void setAapp(Date aapp) {
		this.aapp = aapp;
	}

	public Date getAasr() {
		return this.aasr;
	}

	public void setAasr(Date aasr) {
		this.aasr = aasr;
	}

	public BigDecimal getAcopconvsp() {
		return this.acopconvsp;
	}

	public void setAcopconvsp(BigDecimal acopconvsp) {
		this.acopconvsp = acopconvsp;
	}

	public String getAfil15() {
		return this.afil15;
	}

	public void setAfil15(String afil15) {
		this.afil15 = afil15;
	}

	public String getAnoconvenio() {
		return this.anoconvenio;
	}

	public void setAnoconvenio(String anoconvenio) {
		this.anoconvenio = anoconvenio;
	}

	public String getAnooficiooi() {
		return this.anooficiooi;
	}

	public void setAnooficiooi(String anooficiooi) {
		this.anooficiooi = anooficiooi;
	}

	public BigDecimal getAnoparcialidades() {
		return this.anoparcialidades;
	}

	public void setAnoparcialidades(BigDecimal anoparcialidades) {
		this.anoparcialidades = anoparcialidades;
	}

	public Date getAoi() {
		return this.aoi;
	}

	public void setAoi(Date aoi) {
		this.aoi = aoi;
	}

	public Date getAoin() {
		return this.aoin;
	}

	public void setAoin(Date aoin) {
		this.aoin = aoin;
	}

	public Date getAp() {
		return this.ap;
	}

	public void setAp(Date ap) {
		this.ap = ap;
	}

	public BigDecimal getArcvconvsp() {
		return this.arcvconvsp;
	}

	public void setArcvconvsp(BigDecimal arcvconvsp) {
		this.arcvconvsp = arcvconvsp;
	}

	public Date getAsa() {
		return this.asa;
	}

	public void setAsa(Date asa) {
		this.asa = asa;
	}

	public Date getAsp() {
		return this.asp;
	}

	public void setAsp(Date asp) {
		this.asp = asp;
	}

	public Date getAsr() {
		return this.asr;
	}

	public void setAsr(Date asr) {
		this.asr = asr;
	}

	public Date getCc() {
		return this.cc;
	}

	public void setCc(Date cc) {
		this.cc = cc;
	}

	public Date getCds() {
		return this.cds;
	}

	public void setCds(Date cds) {
		this.cds = cds;
	}

	public String getCnooficio() {
		return this.cnooficio;
	}

	public void setCnooficio(String cnooficio) {
		this.cnooficio = cnooficio;
	}

	public Date getCpai() {
		return this.cpai;
	}

	public void setCpai(Date cpai) {
		this.cpai = cpai;
	}

	public BigDecimal getCpresentopagos() {
		return this.cpresentopagos;
	}

	public void setCpresentopagos(BigDecimal cpresentopagos) {
		this.cpresentopagos = cpresentopagos;
	}

	public Date getCsd() {
		return this.csd;
	}

	public void setCsd(Date csd) {
		this.csd = csd;
	}

	public BigDecimal getCveModalidad() {
		return this.cveModalidad;
	}

	public void setCveModalidad(BigDecimal cveModalidad) {
		this.cveModalidad = cveModalidad;
	}

	public String getCvePatron() {
		return this.cvePatron;
	}

	public void setCvePatron(String cvePatron) {
		this.cvePatron = cvePatron;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public BigDecimal getDv() {
		return this.dv;
	}

	public void setDv(BigDecimal dv) {
		this.dv = dv;
	}

	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}

	public BigDecimal getIdMoticocancelacion() {
		return this.idMoticocancelacion;
	}

	public void setIdMoticocancelacion(BigDecimal idMoticocancelacion) {
		this.idMoticocancelacion = idMoticocancelacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getPeriodoal() {
		return this.periodoal;
	}

	public void setPeriodoal(Date periodoal) {
		this.periodoal = periodoal;
	}

	public Date getPeriododel() {
		return this.periododel;
	}

	public void setPeriododel(Date periododel) {
		this.periododel = periododel;
	}

	public Date getRccpr() {
		return this.rccpr;
	}

	public void setRccpr(Date rccpr) {
		this.rccpr = rccpr;
	}

	public BigDecimal getRcopconvsp() {
		return this.rcopconvsp;
	}

	public void setRcopconvsp(BigDecimal rcopconvsp) {
		this.rcopconvsp = rcopconvsp;
	}

	public Date getRcr() {
		return this.rcr;
	}

	public void setRcr(Date rcr) {
		this.rcr = rcr;
	}

	public Date getRdc() {
		return this.rdc;
	}

	public void setRdc(Date rdc) {
		this.rdc = rdc;
	}

	public String getRnoconvenio() {
		return this.rnoconvenio;
	}

	public void setRnoconvenio(String rnoconvenio) {
		this.rnoconvenio = rnoconvenio;
	}

	public Date getRnod() {
		return this.rnod;
	}

	public void setRnod(Date rnod) {
		this.rnod = rnod;
	}

	public String getRnooficiood() {
		return this.rnooficiood;
	}

	public void setRnooficiood(String rnooficiood) {
		this.rnooficiood = rnooficiood;
	}

	public String getRnooficiorde() {
		return this.rnooficiorde;
	}

	public void setRnooficiorde(String rnooficiorde) {
		this.rnooficiorde = rnooficiorde;
	}

	public BigDecimal getRnoparcialidades() {
		return this.rnoparcialidades;
	}

	public void setRnoparcialidades(BigDecimal rnoparcialidades) {
		this.rnoparcialidades = rnoparcialidades;
	}

	public Date getRod() {
		return this.rod;
	}

	public void setRod(Date rod) {
		this.rod = rod;
	}

	public BigDecimal getRporccr() {
		return this.rporccr;
	}

	public void setRporccr(BigDecimal rporccr) {
		this.rporccr = rporccr;
	}

	public BigDecimal getRrcvconvsp() {
		return this.rrcvconvsp;
	}

	public void setRrcvconvsp(BigDecimal rrcvconvsp) {
		this.rrcvconvsp = rrcvconvsp;
	}

	public Date getRrde() {
		return this.rrde;
	}

	public void setRrde(Date rrde) {
		this.rrde = rrde;
	}

	public Date getRrdn() {
		return this.rrdn;
	}

	public void setRrdn(Date rrdn) {
		this.rrdn = rrdn;
	}

	public Date getRvda() {
		return this.rvda;
	}

	public void setRvda(Date rvda) {
		this.rvda = rvda;
	}

	public Date getRvpp() {
		return this.rvpp;
	}

	public void setRvpp(Date rvpp) {
		this.rvpp = rvpp;
	}

	public Date getRvtc() {
		return this.rvtc;
	}

	public void setRvtc(Date rvtc) {
		this.rvtc = rvtc;
	}

	public Integer getIdMotivoRechazo() {
		return idMotivoRechazo;
	}

	public void setIdMotivoRechazo(Integer idMotivoRechazo) {
		this.idMotivoRechazo = idMotivoRechazo;
	}

	public SacSubdelegacion getSacSubdelegacion() {
		return sacSubdelegacion;
	}

	public void setSacSubdelegacion(SacSubdelegacion sacSubdelegacion) {
		this.sacSubdelegacion = sacSubdelegacion;
	}

	/**
	 * @return the idAuditor
	 */
	public Long getIdAuditor() {
		return idAuditor;
	}

	/**
	 * @param idAuditor
	 *            the idAuditor to set
	 */
	public void setIdAuditor(Long idAuditor) {
		this.idAuditor = idAuditor;
	}

	public String getFolioNum() {
		return folioNum;
	}

	public void setFolioNum(String folioNum) {
		this.folioNum = folioNum;
	}

	public String getFolioAnio() {
		return folioAnio;
	}

	public void setFolioAnio(String folioAnio) {
		this.folioAnio = folioAnio;
	}

}