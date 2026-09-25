/**
 * 
 */
package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegAnexoPagosVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegRegularizarObraGenericoTabVO;

/**
 * @author CesarAgustin
 * @version 1.0.0
 * @since 28/06/2012
 *
 */
public class RecepcionSeguimientoVO extends EstudioSolicitudCorreccionVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8847265439181152191L;
	private Integer idPresentaCorreccion;
	private Integer cveRevRecepcion;
	
	private String calle;
	private String colonia;
	private String numExt;
	private String numInt;
	private String codPostal;
	private String observaciones;
	
	private Integer tipoSolicitud;
	private BigDecimal claveObra;
	private BigDecimal pago;
	private BigDecimal afilia;
	private BigDecimal docto;
	
	//Propiedades de auditori<
	private Date fechaRegistro;
	private String cveUsuario;
	private Integer idPresentaCorr;
	
	private boolean documentacionSustenta;  
	private boolean comprobPagoConvenio;
	private boolean comprobPresAvisosAfil;
	
	//consolidacion importes
	private boolean comprobanteConvenio;
	private String fechaPresentacionCorr;
	private String estatusPresentacionCorr;
	
	//estatus
	private Integer cveStatus;
	
	private SegRegularizarObraGenericoTabVO consolidaImporteVo;
	private SegAnexoPagosVO pagosAutodeterminacionVo;
	
	private Integer cveTipoCorreccion;
	
	private boolean preGuardado;
	private String numeroFolio;
	
	
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getNumExt() {
		return numExt;
	}
	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}
	public String getNumInt() {
		return numInt;
	}
	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}
	public String getCodPostal() {
		return codPostal;
	}
	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getCveUsuario() {
		return cveUsuario;
	}
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	public Integer getTipoSolicitud() {
		return tipoSolicitud;
	}
	public void setTipoSolicitud(Integer tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	public BigDecimal getClaveObra() {
		return claveObra;
	}
	public void setClaveObra(BigDecimal claveObra) {
		this.claveObra = claveObra;
	}
	public BigDecimal getPago() {
		return pago;
	}
	public void setPago(BigDecimal pago) {
		this.pago = pago;
	}
	public BigDecimal getAfilia() {
		return afilia;
	}
	public void setAfilia(BigDecimal afilia) {
		this.afilia = afilia;
	}
	public BigDecimal getDocto() {
		return docto;
	}
	public void setDocto(BigDecimal docto) {
		this.docto = docto;
	}
	public Integer getIdPresentaCorr() {
		return idPresentaCorr;
	}
	public void setIdPresentaCorr(Integer idPresentaCorr) {
		this.idPresentaCorr = idPresentaCorr;
	}
	/**
	 * Retorna el valor consolidaImporteVo
	 * @return  consolidaImporteVo
	 */
	public SegRegularizarObraGenericoTabVO getConsolidaImporteVo() {
		return consolidaImporteVo;
	}
	/**
	 * Asigna el valor del consolidaImporteVo al atributo consolidaImporteVo
	 * @param consolidaImporteVo 
	 */
	public void setConsolidaImporteVo(
			SegRegularizarObraGenericoTabVO consolidaImporteVo) {
		this.consolidaImporteVo = consolidaImporteVo;
	}
	/**
	 * Retorna el valor pagosAutodeterminacionVo
	 * @return  pagosAutodeterminacionVo
	 */
	public SegAnexoPagosVO getPagosAutodeterminacionVo() {
		return pagosAutodeterminacionVo;
	}
	/**
	 * Asigna el valor del pagosAutodeterminacionVo al atributo pagosAutodeterminacionVo
	 * @param pagosAutodeterminacionVo 
	 */
	public void setPagosAutodeterminacionVo(SegAnexoPagosVO pagosAutodeterminacionVo) {
		this.pagosAutodeterminacionVo = pagosAutodeterminacionVo;
	}
	/**
	 * Retorna el valor documentacionSustenta
	 * @return  documentacionSustenta
	 */
	public boolean isDocumentacionSustenta() {
		return documentacionSustenta;
	}
	/**
	 * Asigna el valor del documentacionSustenta al atributo documentacionSustenta
	 * @param documentacionSustenta 
	 */
	public void setDocumentacionSustenta(boolean documentacionSustenta) {
		this.documentacionSustenta = documentacionSustenta;
	}
	/**
	 * Retorna el valor comprobPagoConvenio
	 * @return  comprobPagoConvenio
	 */
	public boolean isComprobPagoConvenio() {
		return comprobPagoConvenio;
	}
	/**
	 * Asigna el valor del comprobPagoConvenio al atributo comprobPagoConvenio
	 * @param comprobPagoConvenio 
	 */
	public void setComprobPagoConvenio(boolean comprobPagoConvenio) {
		this.comprobPagoConvenio = comprobPagoConvenio;
	}
	/**
	 * Retorna el valor comprobPresAvisosAfil
	 * @return  comprobPresAvisosAfil
	 */
	public boolean isComprobPresAvisosAfil() {
		return comprobPresAvisosAfil;
	}
	/**
	 * Asigna el valor del comprobPresAvisosAfil al atributo comprobPresAvisosAfil
	 * @param comprobPresAvisosAfil 
	 */
	public void setComprobPresAvisosAfil(boolean comprobPresAvisosAfil) {
		this.comprobPresAvisosAfil = comprobPresAvisosAfil;
	}
	/**
	 * Retorna el valor comprobanteConvenio
	 * @return  comprobanteConvenio
	 */
	public boolean isComprobanteConvenio() {
		return comprobanteConvenio;
	}
	/**
	 * Asigna el valor del comprobanteConvenio al atributo comprobanteConvenio
	 * @param comprobanteConvenio 
	 */
	public void setComprobanteConvenio(boolean comprobanteConvenio) {
		this.comprobanteConvenio = comprobanteConvenio;
	}
	/**
	 * Retorna el valor fechaPresentacionCorr
	 * @return  fechaPresentacionCorr
	 */
	public String getFechaPresentacionCorr() {
		return fechaPresentacionCorr;
	}
	/**
	 * Asigna el valor del fechaPresentacionCorr al atributo fechaPresentacionCorr
	 * @param fechaPresentacionCorr 
	 */
	public void setFechaPresentacionCorr(String fechaPresentacionCorr) {
		this.fechaPresentacionCorr = fechaPresentacionCorr;
	}
	/**
	 * Retorna el valor estatusPresentacionCorr
	 * @return  estatusPresentacionCorr
	 */
	public String getEstatusPresentacionCorr() {
		return estatusPresentacionCorr;
	}
	/**
	 * Asigna el valor del estatusPresentacionCorr al atributo estatusPresentacionCorr
	 * @param estatusPresentacionCorr 
	 */
	public void setEstatusPresentacionCorr(String estatusPresentacionCorr) {
		this.estatusPresentacionCorr = estatusPresentacionCorr;
	}
	/**
	 * Retorna el valor idPresentaCorreccion
	 * @return  idPresentaCorreccion
	 */
	public Integer getIdPresentaCorreccion() {
		return idPresentaCorreccion;
	}
	/**
	 * Asigna el valor del idPresentaCorreccion al atributo idPresentaCorreccion
	 * @param idPresentaCorreccion 
	 */
	public void setIdPresentaCorreccion(Integer idPresentaCorreccion) {
		this.idPresentaCorreccion = idPresentaCorreccion;
	}
	/**
	 * Retorna el valor cveTipoCorreccion
	 * @return  cveTipoCorreccion
	 */
	public Integer getCveTipoCorreccion() {
		return cveTipoCorreccion;
	}
	/**
	 * Asigna el valor del cveTipoCorreccion al atributo cveTipoCorreccion
	 * @param cveTipoCorreccion 
	 */
	public void setCveTipoCorreccion(Integer cveTipoCorreccion) {
		this.cveTipoCorreccion = cveTipoCorreccion;
	}
	/**
	 * Retorna el valor cveRevRecepcion
	 * @return  cveRevRecepcion
	 */
	public Integer getCveRevRecepcion() {
		return cveRevRecepcion;
	}
	/**
	 * Asigna el valor del cveRevRecepcion al atributo cveRevRecepcion
	 * @param cveRevRecepcion 
	 */
	public void setCveRevRecepcion(Integer cveRevRecepcion) {
		this.cveRevRecepcion = cveRevRecepcion;
	}
	/**
	 * Retorna el valor preGuardado
	 * @return  preGuardado
	 */
	public boolean isPreGuardado() {
		return preGuardado;
	}
	/**
	 * Asigna el valor del preGuardado al atributo preGuardado
	 * @param preGuardado 
	 */
	public void setPreGuardado(boolean preGuardado) {
		this.preGuardado = preGuardado;
	}
	/**
	 * Retorna el valor numeroFolio
	 * @return  numeroFolio
	 */
	public String getNumeroFolio() {
		return numeroFolio;
	}
	/**
	 * Asigna el valor del numeroFolio al atributo numeroFolio
	 * @param numeroFolio 
	 */
	public void setNumeroFolio(String numeroFolio) {
		this.numeroFolio = numeroFolio;
	}
	/**
	 * Retorna el valor cveStatus
	 * @return  cveStatus
	 */
	public Integer getCveStatus() {
		return cveStatus;
	}
	/**
	 * Asigna el valor del cveStatus al atributo cveStatus
	 * @param cveStatus 
	 */
	public void setCveStatus(Integer cveStatus) {
		this.cveStatus = cveStatus;
	}
	
	
	

}
