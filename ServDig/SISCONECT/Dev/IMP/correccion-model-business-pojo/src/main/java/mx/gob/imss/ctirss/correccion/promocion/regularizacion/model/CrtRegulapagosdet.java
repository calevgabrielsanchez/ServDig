package mx.gob.imss.ctirss.correccion.promocion.regularizacion.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * CrtRegulapagosdet 
 */
@Entity
@Table(name = "CRT_REGULAPAGOSDET")
public class CrtRegulapagosdet extends AbstractModel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	private Integer idTipoDocto;
	private Integer numTrabajadoresRegulariza;
	private Integer numAltas;
	private Integer numBajas;
	private Integer numModifSalario;
	private Date fecFechaReg;
	private Long cveUsuario;
	
	@Transient
	private BigDecimal impTotalCop;
	
	@Transient
	private BigDecimal impTotalRcv;
	
	@Transient
	private int contador;
	

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
	
	@Column(name = "NUM_COPPERIODO", precision = 22, scale = 0)
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

	@Column(name = "IMP_COPMULTAS", precision = 10, scale = 2)
	public BigDecimal getImpMultasCop() {
		return impMultasCop;
	}

	public void setImpMultasCop(BigDecimal impMultasCop) {
		this.impMultasCop = impMultasCop;
	}
					
	@Column(name = "NUM_RCVPERIODO", precision = 22, scale = 0)
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
	
	@Column(name = "IMP_RCVMULTAS", precision = 10, scale = 2)
	public BigDecimal getImpMultasRcv() {
		return impMultasRcv;
	}

	public void setImpMultasRcv(BigDecimal impMultasRcv) {
		this.impMultasRcv = impMultasRcv;
	}
	
	
	@Column(name = "NUM_TRABREGULARIZADOS", precision = 10, scale = 0)
	public Integer getNumTrabajadoresRegulariza() {
		return numTrabajadoresRegulariza;
	}

	/**
	 * Asigna el valor del numTrabajadoresRegulariza al atributo numTrabajadoresRegulariza
	 * @param numTrabajadoresRegulariza 
	 */
	public void setNumTrabajadoresRegulariza(Integer numTrabajadoresRegulariza) {
		this.numTrabajadoresRegulariza = numTrabajadoresRegulariza;
	}

	
	@Column(name = "ID_TIPODOCTO", precision = 10, scale = 0)
	public Integer getIdTipoDocto() {
		return idTipoDocto;
	}

	/**
	 * Asigna el valor del idTipoDocto al atributo idTipoDocto
	 * @param idTipoDocto 
	 */
	public void setIdTipoDocto(Integer idTipoDocto) {
		this.idTipoDocto = idTipoDocto;
	}

	
	@Column(name = "NUM_ALTAS", precision = 10, scale = 0)
	public Integer getNumAltas() {
		return numAltas;
	}

	/**
	 * Asigna el valor del numAltas al atributo numAltas
	 * @param numAltas 
	 */
	public void setNumAltas(Integer numAltas) {
		this.numAltas = numAltas;
	}
	
	@Column(name = "NUM_BAJAS", precision = 10, scale = 0)
	public Integer getNumBajas() {
		return numBajas;
	}
	
	
	
	@Column(name = "NUM_MODIFSALARIO", precision = 10, scale = 0)
	public Integer getNumModifSalario() {
		return numModifSalario;
	}

	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_FECHAREG", length = 7)
	public Date getFecFechaReg() {
		return fecFechaReg;
	}
	
	

	
	@Column(name = "CVE_USUARIO", precision = 10, scale = 0)
	public Long getCveUsuario() {
		return cveUsuario;
	}

	/**
	 * Asigna el valor del cveUsuario al atributo cveUsuario
	 * @param cveUsuario 
	 */
	public void setCveUsuario(Long cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	/**
	 * Asigna el valor del fecFechaReg al atributo fecFechaReg
	 * @param fecFechaReg 
	 */
	public void setFecFechaReg(Date fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}

	/**
	 * Asigna el valor del numModifSalario al atributo numModifSalario
	 * @param numModifSalario 
	 */
	public void setNumModifSalario(Integer numModifSalario) {
		this.numModifSalario = numModifSalario;
	}

	/**
	 * Asigna el valor del numBajas al atributo numBajas
	 * @param numBajas 
	 */
	public void setNumBajas(Integer numBajas) {
		this.numBajas = numBajas;
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

	/**
	 * Retorna el valor impTotalCop
	 * @return  impTotalCop
	 */
	@Transient
	public BigDecimal getImpTotalCop() {
		return impTotalCop;
	}

	/**
	 * Asigna el valor del impTotalCop al atributo impTotalCop
	 * @param impTotalCop 
	 */
	public void setImpTotalCop(BigDecimal impTotalCop) {
		this.impTotalCop = impTotalCop;
	}

	/**
	 * Retorna el valor impTotalRcv
	 * @return  impTotalRcv
	 */
	@Transient
	public BigDecimal getImpTotalRcv() {
		return impTotalRcv;
	}

	/**
	 * Asigna el valor del impTotalRcv al atributo impTotalRcv
	 * @param impTotalRcv 
	 */
	public void setImpTotalRcv(BigDecimal impTotalRcv) {
		this.impTotalRcv = impTotalRcv;
	}

	/**
	 * Retorna el valor contador
	 * @return  contador
	 */
	@Transient
	public int getContador() {
		return contador;
	}

	/**
	 * Asigna el valor del contador al atributo contador
	 * @param contador 
	 */
	public void setContador(int contador) {
		this.contador = contador;
	}

	
	
}
