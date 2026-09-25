package mx.imss.ctirss.promocion.regularizacion.model;

import java.math.BigDecimal;
import java.net.Proxy.Type;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.imss.ctirss.framework.base.model.AbstractModel;

/**
 * CrtRegulapagos
 */
@Entity
@Table(name = "CRT_REGULAPAGOS")
public class CrtRegulapagos extends AbstractModel {

	private long cveRegulapagos;
	private Long cvePromocion;
	private BigDecimal porRegularizado;
	private BigDecimal porAvance;
	private Date fecPeridodini;
	private Date fecPeriodofin;
	private BigDecimal numTrabomisos;
	private BigDecimal numTrabsubdclara;
	private BigDecimal numTrabrevisados;
	private Date fecFechaReg;
	private String cveUsuario;
	private String numConvenio;
	private Integer numParcialidades;	
	
	private String fecPerIni;	
	private String fecPerFin;
	private String fecPeriodo;
	private String regPatron;
	private String tipoProm;
	public String fechaAtencionPro;	
	public String fechaPAI;

	public CrtRegulapagos() {
	}

	public CrtRegulapagos(long cveRegulapagos) {
		this.cveRegulapagos = cveRegulapagos;
	}

	public CrtRegulapagos(long cveRegulapagos, Long cvePromocion,
			BigDecimal porRegularizado, BigDecimal porAvance,
			Date fecPeridodini, Date fecPeriodofin, BigDecimal numTrabomisos,
			BigDecimal numTrabsubdclara, Set crtRegulapagosdets) {
		this.cveRegulapagos = cveRegulapagos;
		this.cvePromocion = cvePromocion;
		this.porRegularizado = porRegularizado;
		this.porAvance = porAvance;
		this.fecPeridodini = fecPeridodini;
		this.fecPeriodofin = fecPeriodofin;
		this.numTrabomisos = numTrabomisos;
		this.numTrabsubdclara = numTrabsubdclara;
	}

	@Id	
	@SequenceGenerator(name="CVE_REGULAPAGOS_GENERATOR", sequenceName="CRS_CVE_REGULAPAGOS")
	@GeneratedValue(generator="CVE_REGULAPAGOS_GENERATOR")
	@Column(name = "CVE_REGULAPAGOS", unique = true, nullable = false, precision = 22, scale = 0)
	public long getCveRegulapagos() {
		return this.cveRegulapagos;
	}

	public void setCveRegulapagos(long cveRegulapagos) {
		this.cveRegulapagos = cveRegulapagos;
	}

	@Column(name = "CVE_PROMOCION", precision = 22, scale = 0)
	public Long getCvePromocion() {
		return this.cvePromocion;
	}

	public void setCvePromocion(Long cvePromocion) {
		this.cvePromocion = cvePromocion;
	}

	@Column(name = "POR_REGULARIZADO", precision = 5)
	public BigDecimal getPorRegularizado() {
		return this.porRegularizado;
	}

	public void setPorRegularizado(BigDecimal porRegularizado) {
		this.porRegularizado = porRegularizado;
	}

	@Column(name = "POR_AVANCE", precision = 5)
	public BigDecimal getPorAvance() {
		return this.porAvance;
	}

	public void setPorAvance(BigDecimal porAvance) {
		this.porAvance = porAvance;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_PERIDODINI", length = 7)
	public Date getFecPeridodini() {
		return this.fecPeridodini;
	}

	public void setFecPeridodini(Date fecPeridodini) {
		this.fecPeridodini = fecPeridodini;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_PERIODOFIN", length = 7)
	public Date getFecPeriodofin() {
		return this.fecPeriodofin;
	}

	public void setFecPeriodofin(Date fecPeriodofin) {
		this.fecPeriodofin = fecPeriodofin;
	}

	@Column(name = "NUM_TRABOMISOS", precision = 22, scale = 0)
	public BigDecimal getNumTrabomisos() {
		return this.numTrabomisos;
	}

	public void setNumTrabomisos(BigDecimal numTrabomisos) {
		this.numTrabomisos = numTrabomisos;
	}

	@Column(name = "NUM_TRABSUBDCLARA", precision = 22, scale = 0)
	public BigDecimal getNumTrabsubdclara() {
		return this.numTrabsubdclara;
	}

	public void setNumTrabsubdclara(BigDecimal numTrabsubdclara) {
		this.numTrabsubdclara = numTrabsubdclara;
	}

	@Column(name = "NUM_TRABREVISADOS", precision = 22, scale = 0)
	public BigDecimal getNumTrabrevisados() {
		return numTrabrevisados;
	}

	public void setNumTrabrevisados(BigDecimal numTrabrevisados) {
		this.numTrabrevisados = numTrabrevisados;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_FECHAREGISTRO")
	public Date getFecFechaReg() {
		return fecFechaReg;
	}

	public void setFecFechaReg(Date fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}

	@Column(name = "CVE_USUARIO",length = 20)
	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	@Column(name = "NUM_CONVENIO",length = 50)
	public String getNumConvenio() {
		return numConvenio;
	}

	public void setNumConvenio(String numConvenio) {
		this.numConvenio = numConvenio;
	}

	@Column(name = "NUM_PARCIALIDADES",precision = 22,scale = 0)
	public Integer getNumParcialidades() {
		return numParcialidades;
	}

	public void setNumParcialidades(Integer numParcialidades) {
		this.numParcialidades = numParcialidades;
	}

	@Transient
	public String getFecPerIni() {
		return fecPerIni;
	}

	public void setFecPerIni(String fecPerIni) {
		this.fecPerIni = fecPerIni;
	}

	@Transient
	public String getFecPerFin() {
		return fecPerFin;
	}

	public void setFecPerFin(String fecPerFin) {
		this.fecPerFin = fecPerFin;
	}

	@Transient
	public String getFecPeriodo() {
		return fecPeriodo;
	}

	public void setFecPeriodo(String fecPeriodo) {
		this.fecPeriodo = fecPeriodo;
	}

	@Transient
	public String getRegPatron() {
		return regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	@Transient
	public String getTipoProm() {
		return tipoProm;
	}

	public void setTipoProm(String tipoProm) {
		this.tipoProm = tipoProm;
	}

	@Transient
	public String getFechaAtencionPro() {
		return fechaAtencionPro;
	}

	public void setFechaAtencionPro(String fechaAtencionPro) {
		this.fechaAtencionPro = fechaAtencionPro;
	}

	@Transient
	public String getFechaPAI() {
		return fechaPAI;
	}

	public void setFechaPAI(String fechaPAI) {
		this.fechaPAI = fechaPAI;
	}
	
	
}
