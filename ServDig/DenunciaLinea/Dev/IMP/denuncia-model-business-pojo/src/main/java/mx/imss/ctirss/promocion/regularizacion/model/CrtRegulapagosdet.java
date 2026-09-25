package mx.imss.ctirss.promocion.regularizacion.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.imss.ctirss.framework.base.model.AbstractModel;

/**
 * CrtRegulapagosdet 
 */
@Entity
@Table(name = "CRT_REGULAPAGOSDET")
public class CrtRegulapagosdet extends AbstractModel {

	private long cveRegulapagosdet;
	private CrtRegulapagos crtRegulapagos;
	private BigDecimal idConcepto;
	private Integer numFoliosua;
	private String numOrdeningreso;
	private String numCredito;
	private Date fecFechapago;
	private Integer numPeriodoCop;
	private BigDecimal impCopsp;
	private BigDecimal impCopact;
	private BigDecimal impCoprec;
	private BigDecimal impMultasCop;
	private Integer numPeriodoRcv;
	private BigDecimal impRcvsp;
	private BigDecimal impRcvact;
	private BigDecimal impRcvrec;
	private BigDecimal impMultasRcv;
	
	private String regpat;
	private String fechaPago;
	private boolean bandera = false;
	private long cveBusqueda;
	private long idPagoCaratula;

	public CrtRegulapagosdet() {
	}

	public CrtRegulapagosdet(long cveRegulapagosdet) {
		this.cveRegulapagosdet = cveRegulapagosdet;
	}

	public CrtRegulapagosdet(long cveRegulapagosdet,
			CrtRegulapagos crtRegulapagos, BigDecimal idConcepto,
			Integer numFoliosua, String numOrdeningreso, String numCredito,
			Date fecFechapago, BigDecimal impCopsp, BigDecimal impCopact,
			BigDecimal impCoprec, BigDecimal impRcvsp, BigDecimal impRcvact,
			BigDecimal impRcvrec, BigDecimal impMultasRcv) {
		this.cveRegulapagosdet = cveRegulapagosdet;
		this.crtRegulapagos = crtRegulapagos;
		this.idConcepto = idConcepto;
		this.numFoliosua = numFoliosua;
		this.numOrdeningreso = numOrdeningreso;
		this.numCredito = numCredito;
		this.fecFechapago = fecFechapago;
		this.impCopsp = impCopsp;
		this.impCopact = impCopact;
		this.impCoprec = impCoprec;
		this.impRcvsp = impRcvsp;
		this.impRcvact = impRcvact;
		this.impRcvrec = impRcvrec;
		this.impMultasRcv = impMultasRcv;
	}

	@Id
	@SequenceGenerator(name="CVE_REGULAPAGOSDET_GENERATOR", sequenceName="CRS_CVE_REGULAPAGOSDET")
	@GeneratedValue(generator="CVE_REGULAPAGOSDET_GENERATOR")
	@Column(name = "CVE_REGULAPAGOSDET", unique = true, nullable = false, precision = 22, scale = 0)
	public long getCveRegulapagosdet() {
		return this.cveRegulapagosdet;
	}

	public void setCveRegulapagosdet(long cveRegulapagosdet) {
		this.cveRegulapagosdet = cveRegulapagosdet;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CVE_REGULAPAGOS")
	public CrtRegulapagos getCrtRegulapagos() {
		return this.crtRegulapagos;
	}

	public void setCrtRegulapagos(CrtRegulapagos crtRegulapagos) {
		this.crtRegulapagos = crtRegulapagos;
	}

	@Column(name = "ID_CONCEPTO", precision = 22, scale = 0)
	public BigDecimal getIdConcepto() {
		return this.idConcepto;
	}

	public void setIdConcepto(BigDecimal idConcepto) {
		this.idConcepto = idConcepto;
	}

	@Column(name = "NUM_FOLIOSUA", precision = 6, scale = 0)
	public Integer getNumFoliosua() {
		return this.numFoliosua;
	}

	public void setNumFoliosua(Integer numFoliosua) {
		this.numFoliosua = numFoliosua;
	}

	@Column(name = "NUM_ORDENINGRESO", length = 10)
	public String getNumOrdeningreso() {
		return this.numOrdeningreso;
	}

	public void setNumOrdeningreso(String numOrdeningreso) {
		this.numOrdeningreso = numOrdeningreso;
	}

	@Column(name = "NUM_CREDITO", length = 18)
	public String getNumCredito() {
		return this.numCredito;
	}

	public void setNumCredito(String numCredito) {
		this.numCredito = numCredito;
	}

	@Column(name = "NUM_PERIODOCOP", precision = 22, scale = 0)
	public Integer getNumPeriodoCop() {
		return numPeriodoCop;
	}

	public void setNumPeriodoCop(Integer numPeriodoCop) {
		this.numPeriodoCop = numPeriodoCop;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_FECHAPAGO", length = 7)
	public Date getFecFechapago() {
		return this.fecFechapago;
	}

	public void setFecFechapago(Date fecFechapago) {
		this.fecFechapago = fecFechapago;
	}

	@Column(name = "IMP_COPSP", precision = 10)
	public BigDecimal getImpCopsp() {
		return this.impCopsp;
	}

	public void setImpCopsp(BigDecimal impCopsp) {
		this.impCopsp = impCopsp;
	}

	@Column(name = "IMP_COPACT", precision = 10)
	public BigDecimal getImpCopact() {
		return this.impCopact;
	}

	public void setImpCopact(BigDecimal impCopact) {
		this.impCopact = impCopact;
	}

	@Column(name = "IMP_COPREC", precision = 10)
	public BigDecimal getImpCoprec() {
		return this.impCoprec;
	}

	public void setImpCoprec(BigDecimal impCoprec) {
		this.impCoprec = impCoprec;
	}

	@Column(name = "IMP_MULTASCOP", precision = 10, scale = 2)
	public BigDecimal getImpMultasCop() {
		return impMultasCop;
	}

	public void setImpMultasCop(BigDecimal impMultasCop) {
		this.impMultasCop = impMultasCop;
	}

	@Column(name = "NUM_PERIODORCV", precision = 22, scale = 0)
	public Integer getNumPeriodoRcv() {
		return numPeriodoRcv;
	}

	public void setNumPeriodoRcv(Integer numPeriodoRcv) {
		this.numPeriodoRcv = numPeriodoRcv;
	}

	@Column(name = "IMP_RCVSP", precision = 10)
	public BigDecimal getImpRcvsp() {
		return this.impRcvsp;
	}

	public void setImpRcvsp(BigDecimal impRcvsp) {
		this.impRcvsp = impRcvsp;
	}

	@Column(name = "IMP_RCVACT", precision = 10)
	public BigDecimal getImpRcvact() {
		return this.impRcvact;
	}

	public void setImpRcvact(BigDecimal impRcvact) {
		this.impRcvact = impRcvact;
	}

	@Column(name = "IMP_RCVREC", precision = 10)
	public BigDecimal getImpRcvrec() {
		return this.impRcvrec;
	}

	public void setImpRcvrec(BigDecimal impRcvrec) {
		this.impRcvrec = impRcvrec;
	}
	
	@Column(name = "IMP_MULTASRCV", precision = 10, scale = 2)
	public BigDecimal getImpMultasRcv() {
		return impMultasRcv;
	}

	public void setImpMultasRcv(BigDecimal impMultasRcv) {
		this.impMultasRcv = impMultasRcv;
	}

	@Transient
	public String getRegpat() {
		return regpat;
	}

	public void setRegpat(String regpat) {
		this.regpat = regpat;
	}

	@Transient
	public String getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}

	@Transient
	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}

	@Transient
	public long getCveBusqueda() {
		return cveBusqueda;
	}

	public void setCveBusqueda(long cveBusqueda) {
		this.cveBusqueda = cveBusqueda;
	}

	@Transient
	public long getIdPagoCaratula() {
		return idPagoCaratula;
	}

	public void setIdPagoCaratula(long idPagoCaratula) {
		this.idPagoCaratula = idPagoCaratula;
	}

}
