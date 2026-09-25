/**
 * CrtRevRecepcion.java
 * @package mx.gob.imss.ctirss.correccion.model
 * @project correccion-model-business-pojo
 */
package mx.gob.imss.ctirss.correccion.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

/**
 * @author Oscar German Beltran Ortega
 * @since 07/08/2012
 * @version 1.0.0
 */
@Entity
@Table(name = "CRT_REVRECEPCION")
public class CrtRevRecepcion extends AbstractModel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator (name = "CVE_REVRECEPCION_CORRECCION_GENERATOR" , sequenceName = "SEQ_CVE_REVRECEPCION")
	@GeneratedValue(generator = "CVE_REVRECEPCION_CORRECCION_GENERATOR")
	@Column(name = "CVE_REVRECEPCION")
	private long cveRevRecepcion;

	@Column(name="CVE_PRESENTACORR")
	private Integer cvePresentacorr;
	
	@Column(name="CVE_STATUS")
	private Integer cveStatus;
	
	@Column(name="NU_COMPROBANTEPAGO")
	private Integer nuComprobantePago;
	
	@Column(name="NU_COMPROMOVAFIL")
	private Integer nuComproMovAfil;
	
	@Column(name="NU_DOCTOSUSTENTO")
	private Integer nuDoctoSustento;
	
	@Column(name="NU_PORCENTAJEAVANCE")
	private BigDecimal porcentajeAvance;
	
	@Column(name="NU_PORCENTAJEREGULA")
	private BigDecimal porcentajeRegula;
	
	@Column(name="NU_NUMPARCIALIDADES")
	private Integer numParcialidades;	
	
	@Column(name ="NU_COMPROBANTECONVENIO")
	private Integer comprobanteConvenio;
	
	@Column(name ="NU_TRABREVISADOS")
	private BigDecimal numTrabrevisados;
	
	
	@Column(name ="NU_TRABOMISOS")
	private BigDecimal numTrabomisos; 
	
	@Column(name ="NU_TRABSUBDECLARADOS")
	private BigDecimal numTrabSubdeclarados;
	
	@Column(name ="NU_REGULARIZADOS")
	private BigDecimal numRegularizados;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_FECHARECEPCION", length = 7)
	private Date fecFechaRecepcion;
	
	@Column(name = "IMP_SPCOPAUTODET", precision = 15, scale = 2)
	private BigDecimal impSPCopAutDet;
	
	@Column(name = "IMP_SPRCVAUTODET", precision = 15, scale = 2)
	private BigDecimal impSPRcvAutDet;
	
	@Column(name ="TX_OBSERVACIONES")
	private String observaciones;
	
	@Column(name ="IND_PRESUNTIVO")
	private Integer indPresuntivo; 
	
	@Column(name ="IND_TIPOPAGO")
	private Integer indTipoPago;
	
	@Column(name ="IND_AUTORIZA_REVISION")
	private Integer indAutorizaRevision;
	
	@Column(name ="IND_AUTORIZA_VALPRIMERA")
	private Integer indAutorizaValPrimera;
	
	@Column(name ="IND_AUTORIZA_VALSEGUNDA")
	private Integer indAutorizaValSegunda;
	
	@Column(name ="FEC_FECHAREG")
	@Temporal( TemporalType.TIMESTAMP)
	private Date fecFechaReg;
	
	@Column(name ="FEC_FECHAPLICREVISION")
	@Temporal( TemporalType.TIMESTAMP)
	private Date fecFechaAplicRevision;
	
	@Transient
	private String fechaRegTxt;
	
	@Column(name ="CVE_USUARIO")
	private String cveUsuario;
	
	@Transient
	private String fechaPresentacionTxt;
	
	@Transient
	private String estatusPresentacionDescripcion;
	
	
	@Transient
	private String rolUsuario;
	
	//constructores
	public CrtRevRecepcion() {

	}
	
	public CrtRevRecepcion(long cveRevRecepcion) {
		this.cveRevRecepcion= cveRevRecepcion;
	}

	/**
	 * Retorna el valor cveRevRecepcion
	 * @return  cveRevRecepcion
	 */
	public long getCveRevRecepcion() {
		return cveRevRecepcion;
	}

	/**
	 * Asigna el valor del cveRevRecepcion al atributo cveRevRecepcion
	 * @param cveRevRecepcion 
	 */
	public void setCveRevRecepcion(long cveRevRecepcion) {
		this.cveRevRecepcion = cveRevRecepcion;
	}

	/**
	 * Retorna el valor cvePresentacorr
	 * @return  cvePresentacorr
	 */
	public Integer getCvePresentacorr() {
		return cvePresentacorr;
	}

	/**
	 * Asigna el valor del cvePresentacorr al atributo cvePresentacorr
	 * @param cvePresentacorr 
	 */
	public void setCvePresentacorr(Integer cvePresentacorr) {
		this.cvePresentacorr = cvePresentacorr;
	}

	/**
	 * Retorna el valor nuComprobantePago
	 * @return  nuComprobantePago
	 */
	public Integer getNuComprobantePago() {
		return nuComprobantePago;
	}

	/**
	 * Asigna el valor del nuComprobantePago al atributo nuComprobantePago
	 * @param nuComprobantePago 
	 */
	public void setNuComprobantePago(Integer nuComprobantePago) {
		this.nuComprobantePago = nuComprobantePago;
	}

	/**
	 * Retorna el valor nuComproMovAfil
	 * @return  nuComproMovAfil
	 */
	public Integer getNuComproMovAfil() {
		return nuComproMovAfil;
	}

	/**
	 * Asigna el valor del nuComproMovAfil al atributo nuComproMovAfil
	 * @param nuComproMovAfil 
	 */
	public void setNuComproMovAfil(Integer nuComproMovAfil) {
		this.nuComproMovAfil = nuComproMovAfil;
	}

	/**
	 * Retorna el valor nuDoctoSustento
	 * @return  nuDoctoSustento
	 */
	public Integer getNuDoctoSustento() {
		return nuDoctoSustento;
	}

	/**
	 * Asigna el valor del nuDoctoSustento al atributo nuDoctoSustento
	 * @param nuDoctoSustento 
	 */
	public void setNuDoctoSustento(Integer nuDoctoSustento) {
		this.nuDoctoSustento = nuDoctoSustento;
	}

	/**
	 * Retorna el valor porcentajeAvance
	 * @return  porcentajeAvance
	 */
	public BigDecimal getPorcentajeAvance() {
		return porcentajeAvance;
	}

	/**
	 * Asigna el valor del porcentajeAvance al atributo porcentajeAvance
	 * @param porcentajeAvance 
	 */
	public void setPorcentajeAvance(BigDecimal porcentajeAvance) {
		this.porcentajeAvance = porcentajeAvance;
	}

	/**
	 * Retorna el valor porcentajeRegula
	 * @return  porcentajeRegula
	 */
	public BigDecimal getPorcentajeRegula() {
		return porcentajeRegula;
	}

	/**
	 * Asigna el valor del porcentajeRegula al atributo porcentajeRegula
	 * @param porcentajeRegula 
	 */
	public void setPorcentajeRegula(BigDecimal porcentajeRegula) {
		this.porcentajeRegula = porcentajeRegula;
	}

	/**
	 * Retorna el valor numParcialidades
	 * @return  numParcialidades
	 */
	public Integer getNumParcialidades() {
		return numParcialidades;
	}

	/**
	 * Asigna el valor del numParcialidades al atributo numParcialidades
	 * @param numParcialidades 
	 */
	public void setNumParcialidades(Integer numParcialidades) {
		this.numParcialidades = numParcialidades;
	}

	/**
	 * Retorna el valor comprobanteConvenio
	 * @return  comprobanteConvenio
	 */
	public Integer getComprobanteConvenio() {
		return comprobanteConvenio;
	}

	/**
	 * Asigna el valor del comprobanteConvenio al atributo comprobanteConvenio
	 * @param comprobanteConvenio 
	 */
	public void setComprobanteConvenio(Integer comprobanteConvenio) {
		this.comprobanteConvenio = comprobanteConvenio;
	}

	/**
	 * Retorna el valor numTrabrevisados
	 * @return  numTrabrevisados
	 */
	public BigDecimal getNumTrabrevisados() {
		return numTrabrevisados;
	}

	/**
	 * Asigna el valor del numTrabrevisados al atributo numTrabrevisados
	 * @param numTrabrevisados 
	 */
	public void setNumTrabrevisados(BigDecimal numTrabrevisados) {
		this.numTrabrevisados = numTrabrevisados;
	}

	/**
	 * Retorna el valor numTrabomisos
	 * @return  numTrabomisos
	 */
	public BigDecimal getNumTrabomisos() {
		return numTrabomisos;
	}

	/**
	 * Asigna el valor del numTrabomisos al atributo numTrabomisos
	 * @param numTrabomisos 
	 */
	public void setNumTrabomisos(BigDecimal numTrabomisos) {
		this.numTrabomisos = numTrabomisos;
	}

	/**
	 * Retorna el valor numRegularizados
	 * @return  numRegularizados
	 */
	public BigDecimal getNumRegularizados() {
		return numRegularizados;
	}

	/**
	 * Asigna el valor del numRegularizados al atributo numRegularizados
	 * @param numRegularizados 
	 */
	public void setNumRegularizados(BigDecimal numRegularizados) {
		this.numRegularizados = numRegularizados;
	}

	/**
	 * Retorna el valor fecFechaRecepcion
	 * @return  fecFechaRecepcion
	 */
	public Date getFecFechaRecepcion() {
		return fecFechaRecepcion;
	}

	/**
	 * Asigna el valor del fecFechaRecepcion al atributo fecFechaRecepcion
	 * @param fecFechaRecepcion 
	 */
	public void setFecFechaRecepcion(Date fecFechaRecepcion) {
		this.fecFechaRecepcion = fecFechaRecepcion;
	}

	/**
	 * Retorna el valor impSPCopAutDet
	 * @return  impSPCopAutDet
	 */
	public BigDecimal getImpSPCopAutDet() {
		return impSPCopAutDet;
	}

	/**
	 * Asigna el valor del impSPCopAutDet al atributo impSPCopAutDet
	 * @param impSPCopAutDet 
	 */
	public void setImpSPCopAutDet(BigDecimal impSPCopAutDet) {
		this.impSPCopAutDet = impSPCopAutDet;
	}



	/**
	 * Retorna el valor observaciones
	 * @return  observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * Asigna el valor del observaciones al atributo observaciones
	 * @param observaciones 
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * Retorna el valor indPresuntivo
	 * @return  indPresuntivo
	 */
	public Integer getIndPresuntivo() {
		return indPresuntivo;
	}

	/**
	 * Asigna el valor del indPresuntivo al atributo indPresuntivo
	 * @param indPresuntivo 
	 */
	public void setIndPresuntivo(Integer indPresuntivo) {
		this.indPresuntivo = indPresuntivo;
	}

	/**
	 * Retorna el valor indTipoPago
	 * @return  indTipoPago
	 */
	public Integer getIndTipoPago() {
		return indTipoPago;
	}

	/**
	 * Asigna el valor del indTipoPago al atributo indTipoPago
	 * @param indTipoPago 
	 */
	public void setIndTipoPago(Integer indTipoPago) {
		this.indTipoPago = indTipoPago;
	}

	/**
	 * Retorna el valor indAutorizaRevision
	 * @return  indAutorizaRevision
	 */
	public Integer getIndAutorizaRevision() {
		return indAutorizaRevision;
	}

	/**
	 * Asigna el valor del indAutorizaRevision al atributo indAutorizaRevision
	 * @param indAutorizaRevision 
	 */
	public void setIndAutorizaRevision(Integer indAutorizaRevision) {
		this.indAutorizaRevision = indAutorizaRevision;
	}

	/**
	 * Retorna el valor indAutorizaValPrimera
	 * @return  indAutorizaValPrimera
	 */
	public Integer getIndAutorizaValPrimera() {
		return indAutorizaValPrimera;
	}

	/**
	 * Asigna el valor del indAutorizaValPrimera al atributo indAutorizaValPrimera
	 * @param indAutorizaValPrimera 
	 */
	public void setIndAutorizaValPrimera(Integer indAutorizaValPrimera) {
		this.indAutorizaValPrimera = indAutorizaValPrimera;
	}

	/**
	 * Retorna el valor indAutorizaValSegunda
	 * @return  indAutorizaValSegunda
	 */
	public Integer getIndAutorizaValSegunda() {
		return indAutorizaValSegunda;
	}

	/**
	 * Asigna el valor del indAutorizaValSegunda al atributo indAutorizaValSegunda
	 * @param indAutorizaValSegunda 
	 */
	public void setIndAutorizaValSegunda(Integer indAutorizaValSegunda) {
		this.indAutorizaValSegunda = indAutorizaValSegunda;
	}

	/**
	 * Retorna el valor fecFechaReg
	 * @return  fecFechaReg
	 */
	public Date getFecFechaReg() {
		return fecFechaReg;
	}

	/**
	 * Asigna el valor del fecFechaReg al atributo fecFechaReg
	 * @param fecFechaReg 
	 */
	public void setFecFechaReg(Date fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}

	/**
	 * Retorna el valor cveUsuario
	 * @return  cveUsuario
	 */
	public String getCveUsuario() {
		return cveUsuario;
	}

	/**
	 * Asigna el valor del cveUsuario al atributo cveUsuario
	 * @param cveUsuario 
	 */
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	/**
	 * Retorna el valor numTrabSubdeclarados
	 * @return  numTrabSubdeclarados
	 */
	public BigDecimal getNumTrabSubdeclarados() {
		return numTrabSubdeclarados;
	}

	/**
	 * Asigna el valor del numTrabSubdeclarados al atributo numTrabSubdeclarados
	 * @param numTrabSubdeclarados 
	 */
	public void setNumTrabSubdeclarados(BigDecimal numTrabSubdeclarados) {
		this.numTrabSubdeclarados = numTrabSubdeclarados;
	}

	/**
	 * Retorna el valor impSPRcvAutDet
	 * @return  impSPRcvAutDet
	 */
	public BigDecimal getImpSPRcvAutDet() {
		return impSPRcvAutDet;
	}

	/**
	 * Asigna el valor del impSPRcvAutDet al atributo impSPRcvAutDet
	 * @param impSPRcvAutDet 
	 */
	public void setImpSPRcvAutDet(BigDecimal impSPRcvAutDet) {
		this.impSPRcvAutDet = impSPRcvAutDet;
	}

	/**
	 * Retorna el valor fechaRegTxt
	 * @return  fechaRegTxt
	 */
	public String getFechaRegTxt() {
		return fechaRegTxt;
	}

	/**
	 * Asigna el valor del fechaRegTxt al atributo fechaRegTxt
	 * @param fechaRegTxt 
	 */
	public void setFechaRegTxt(String fechaRegTxt) {
		this.fechaRegTxt = fechaRegTxt;
	}

	/**
	 * Retorna el valor fechaPresentacionTxt
	 * @return  fechaPresentacionTxt
	 */
	public String getFechaPresentacionTxt() {
		return fechaPresentacionTxt;
	}

	/**
	 * Asigna el valor del fechaPresentacionTxt al atributo fechaPresentacionTxt
	 * @param fechaPresentacionTxt 
	 */
	public void setFechaPresentacionTxt(String fechaPresentacionTxt) {
		this.fechaPresentacionTxt = fechaPresentacionTxt;
	}

	public Date getFecFechaAplicRevision() {
		return fecFechaAplicRevision;
	}

	public void setFecFechaAplicRevision(Date fecFechaAplicRevision) {
		this.fecFechaAplicRevision = fecFechaAplicRevision;
	}

	public Integer getCveStatus() {
		return cveStatus;
	}

	public void setCveStatus(Integer cveStatus) {
		this.cveStatus = cveStatus;
	}

	/**
	 * Retorna el valor estatusPresentacionDescripcion
	 * @return  estatusPresentacionDescripcion
	 */
	public String getEstatusPresentacionDescripcion() {
		return estatusPresentacionDescripcion;
	}

	/**
	 * Asigna el valor del estatusPresentacionDescripcion al atributo estatusPresentacionDescripcion
	 * @param estatusPresentacionDescripcion 
	 */
	public void setEstatusPresentacionDescripcion(
			String estatusPresentacionDescripcion) {
		this.estatusPresentacionDescripcion = estatusPresentacionDescripcion;
	}

	public String getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}

	

	
	
}

