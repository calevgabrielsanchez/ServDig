package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import java.io.Serializable;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de los tabs
 * genericos usados en el flujo de Seguimiento de Promocion.
 * 
 * @author Gerardo Salazar Vega
 * @version 1.0.0
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PromocionSeguimientoGenericoVO extends ControlTabs implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private SegCancelacionGenericoTabVO cancelacionGenericoTabVO;
	private SegAutAvisoDictGenericoTabVO avisoDictamenGenericoTabVO;
	private SegDerivacionFiscalizaGenericoTabVO segDerivaFiscaTabVo;
	private SegDerivacionSubdelGenericoTabVO segDerivaSubdelTabVo;
	private SegRegularizarObraGenericoTabVO segRegularizaObraVo;
	private SegAnexoPagosVO anexoPagosVO;
	private SeguimientoEstatusObraVO estatusObraVO;
	
	private String cvePromocion;
	private String registroPatronal;
	private String fechaNotificacionOficio;
	private String nombreFuncionarioRegistra;
	private String subdelegacionFuncionarioReg;

	/**
	 * Devuelve el vo de cancelacion generico
	 * 
	 * @return cancelacionGenericoTabVO
	 */
	public SegCancelacionGenericoTabVO getCancelacionGenericoTabVO() {
		return cancelacionGenericoTabVO;
	}

	/**
	 * Asigna el vo de cancelacion generico
	 * 
	 * @param cancelacionGenericoTabVO
	 */
	public void setCancelacionGenericoTabVO(
			SegCancelacionGenericoTabVO cancelacionGenericoTabVO) {
		this.cancelacionGenericoTabVO = cancelacionGenericoTabVO;
	}

	/**
	 * Retorna el valor cvePromocion
	 * @return  cvePromocion
	 */
	public String getCvePromocion() {
		return cvePromocion;
	}

	/**
	 * Asigna el valor del cvePromocion al atributo cvePromocion
	 * @param cvePromocion 
	 */
	public void setCvePromocion(String cvePromocion) {
		this.cvePromocion = cvePromocion;
	}

	/**
	 * Retorna el valor serialversionuid
	 * @return  serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Retorna el valor segDerivaFiscaTabVo
	 * @return  segDerivaFiscaTabVo
	 */
	public SegDerivacionFiscalizaGenericoTabVO getSegDerivaFiscaTabVo() {
		return segDerivaFiscaTabVo;
	}

	/**
	 * Asigna el valor del segDerivaFiscaTabVo al atributo segDerivaFiscaTabVo
	 * @param segDerivaFiscaTabVo 
	 */
	public void setSegDerivaFiscaTabVo(
			SegDerivacionFiscalizaGenericoTabVO segDerivaFiscaTabVo) {
		this.segDerivaFiscaTabVo = segDerivaFiscaTabVo;
	}

	/**
	 * Retorna el valor segRegularizaObraVo
	 * @return  segRegularizaObraVo
	 */
	public SegRegularizarObraGenericoTabVO getSegRegularizaObraVo() {
		return segRegularizaObraVo;
	}

	/**
	 * Asigna el valor del segRegularizaObraVo al atributo segRegularizaObraVo
	 * @param segRegularizaObraVo 
	 */
	public void setSegRegularizaObraVo(
			SegRegularizarObraGenericoTabVO segRegularizaObraVo) {
		this.segRegularizaObraVo = segRegularizaObraVo;
	}

	/**
	 * Retorna el valor nombreFuncionarioRegistra
	 * @return  nombreFuncionarioRegistra
	 */
	public String getNombreFuncionarioRegistra() {
		return nombreFuncionarioRegistra;
	}

	/**
	 * Asigna el valor del nombreFuncionarioRegistra al atributo nombreFuncionarioRegistra
	 * @param nombreFuncionarioRegistra 
	 */
	public void setNombreFuncionarioRegistra(String nombreFuncionarioRegistra) {
		this.nombreFuncionarioRegistra = nombreFuncionarioRegistra;
	}

	/**
	 * Retorna el valor avisoDictamenGenericoTabVO
	 * @return  avisoDictamenGenericoTabVO
	 */
	public SegAutAvisoDictGenericoTabVO getAvisoDictamenGenericoTabVO() {
		return avisoDictamenGenericoTabVO;
	}

	/**
	 * Asigna el valor del avisoDictamenGenericoTabVO al atributo avisoDictamenGenericoTabVO
	 * @param avisoDictamenGenericoTabVO 
	 */
	public void setAvisoDictamenGenericoTabVO(
			SegAutAvisoDictGenericoTabVO avisoDictamenGenericoTabVO) {
		this.avisoDictamenGenericoTabVO = avisoDictamenGenericoTabVO;
	}

	/**
	 * Retorna el valor anexoPagosVO
	 * @return  anexoPagosVO
	 */
	public SegAnexoPagosVO getAnexoPagosVO() {
		return anexoPagosVO;
	}
	
	/**
	 * Asigna el valor del anexoPagosVO al atributo anexoPagosVO
	 * @param anexoPagosVO 
	 */
	public void setAnexoPagosVO(SegAnexoPagosVO anexoPagosVO) {
		this.anexoPagosVO = anexoPagosVO;
	}

	/**
	 * Devuelve el vo de derivar a otra subdelegacion generico
	 * 
	 * @return segDerivaSubdelTabVo
	 */
	public SegDerivacionSubdelGenericoTabVO getSegDerivaSubdelTabVo() {
		return segDerivaSubdelTabVo;
	}

	/**
	 * Asigna el vo de derivar a otra subdelegacion generico
	 * 
	 * @param segDerivaSubdelTabVo
	 */
	public void setSegDerivaSubdelTabVo(
			SegDerivacionSubdelGenericoTabVO segDerivaSubdelTabVo) {
		this.segDerivaSubdelTabVo = segDerivaSubdelTabVo;
	}

	/**
	 * @return subdelegacionFuncionarioReg
	 */
	public String getSubdelegacionFuncionarioReg() {
		return subdelegacionFuncionarioReg;
	}

	/**
	 * @param subdelegacionFuncionarioReg
	 */
	public void setSubdelegacionFuncionarioReg(
			String subdelegacionFuncionarioReg) {
		this.subdelegacionFuncionarioReg = subdelegacionFuncionarioReg;
	}

	/**
	 * Retorna el valor estatusObraVO
	 * @return  estatusObraVO
	 */
	public SeguimientoEstatusObraVO getEstatusObraVO() {
		return estatusObraVO;
	}

	/**
	 * Asigna el valor del estatusObraVO al atributo estatusObraVO
	 * @param estatusObraVO 
	 */
	public void setEstatusObraVO(SeguimientoEstatusObraVO estatusObraVO) {
		this.estatusObraVO = estatusObraVO;
	}

	/**
	 * Devuelve la fecha de notificacion del oficio
	 * 
	 * @return fechaNotificacionOficio
	 */
	public String getFechaNotificacionOficio() {
		return fechaNotificacionOficio;
	}

	/**
	 * Asigna la fecha de notificacion del oficio
	 * 
	 * @param fechaNotificacionOficio
	 */
	public void setFechaNotificacionOficio(String fechaNotificacionOficio) {
		this.fechaNotificacionOficio = fechaNotificacionOficio;
	}

	/**
	 * Retorna el valor registroPatronal
	 * @return  registroPatronal
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * Asigna el valor del registroPatronal al atributo registroPatronal
	 * @param registroPatronal 
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	

		

}
