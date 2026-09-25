/**
 * @author Gerardo Salazar Vega
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 24/05/2012
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.satica;

import org.codehaus.jackson.annotate.JsonProperty;

import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegCancelacionGenericoTabVO;
import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagos;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;

/**
 * Objeto SaticASeguimientoPromocionVO que encapsula los objetos manejados en capa de presentación
 * del seguimiento de la promoción para SaticA
 *
 */   
public class SeguimientoSaticATabVO extends ControlTabs {

	private String fecNotificacionOficio;
	private String fecCancelacionOficio;
	private String fecDerivaSubDel;
	private String fecDerivaFiscalizacion;
	private String fecAtencionOficio;
	private String numRegistroObra;
	private boolean regularizaObra;
	private String fecIniPeriodo;
	private String fecFinPeriodo;
	private String observaciones;
	/**
	 * @return the fecNotificacionOficio
	 */
	public String getFecNotificacionOficio() {
		return fecNotificacionOficio;
	}
	/**
	 * @param fecNotificacionOficio the fecNotificacionOficio to set
	 */
	public void setFecNotificacionOficio(String fecNotificacionOficio) {
		this.fecNotificacionOficio = fecNotificacionOficio;
	}
	/**
	 * @return the fecCancelacionOficio
	 */
	public String getFecCancelacionOficio() {
		return fecCancelacionOficio;
	}
	/**
	 * @param fecCancelacionOficio the fecCancelacionOficio to set
	 */
	public void setFecCancelacionOficio(String fecCancelacionOficio) {
		this.fecCancelacionOficio = fecCancelacionOficio;
	}
	/**
	 * @return the fecDerivaSubDel
	 */
	public String getFecDerivaSubDel() {
		return fecDerivaSubDel;
	}
	/**
	 * @param fecDerivaSubDel the fecDerivaSubDel to set
	 */
	public void setFecDerivaSubDel(String fecDerivaSubDel) {
		this.fecDerivaSubDel = fecDerivaSubDel;
	}
	/**
	 * @return the fecDerivaFiscalizacion
	 */
	public String getFecDerivaFiscalizacion() {
		return fecDerivaFiscalizacion;
	}
	/**
	 * @param fecDerivaFiscalizacion the fecDerivaFiscalizacion to set
	 */
	public void setFecDerivaFiscalizacion(String fecDerivaFiscalizacion) {
		this.fecDerivaFiscalizacion = fecDerivaFiscalizacion;
	}
	/**
	 * @return the fecAtencionOficio
	 */
	public String getFecAtencionOficio() {
		return fecAtencionOficio;
	}
	/**
	 * @param fecAtencionOficio the fecAtencionOficio to set
	 */
	public void setFecAtencionOficio(String fecAtencionOficio) {
		this.fecAtencionOficio = fecAtencionOficio;
	}
	/**
	 * @return the numRegistroObra
	 */
	public String getNumRegistroObra() {
		return numRegistroObra;
	}
	/**
	 * @param numRegistroObra the numRegistroObra to set
	 */
	public void setNumRegistroObra(String numRegistroObra) {
		this.numRegistroObra = numRegistroObra;
	}

	/**
	 * @return the regularizaObra
	 */
	public boolean isRegularizaObra() {
		return regularizaObra;
	}
	
	/**
	 * @param regularizaObra the regularizaObra to set
	 */
	public void setRegularizaObra(boolean regularizaObra) {
		this.regularizaObra = regularizaObra;
	}
	/**
	 * @return the fecIniPeriodo
	 */
	public String getFecIniPeriodo() {
		return fecIniPeriodo;
	}
	
	/**
	 * @param fecIniPeriodo the fecIniPeriodo to set
	 */
	public void setFecIniPeriodo(String fecIniPeriodo) {
		this.fecIniPeriodo = fecIniPeriodo;
	}
	/**
	 * @return the fecFinPeriodo
	 */
	public String getFecFinPeriodo() {
		return fecFinPeriodo;
	}
	/**
	 * @param fecFinPeriodo the fecFinPeriodo to set
	 */
	public void setFecFinPeriodo(String fecFinPeriodo) {
		this.fecFinPeriodo = fecFinPeriodo;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	


}