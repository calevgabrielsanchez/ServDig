/**
 * seguimientoSaticbTabVO.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb
 * @project correccion-web
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;


/**
 * Objeto visual el cual nos apoya al momento de encapsular la informacion de la pestaña de Seguimiento
 * manejada en el flujo de Seguimiento de promocion SATIC B
 * 
 * @author Oscar Beltran Ortega
 * @version 1.0.1
 *
 */
public class SeguimientoSaticbTabVO extends ControlTabs {
	
	private String fechaNotificacion;
	private String fechaCancelacion;
	private String fechaDerivaSubdel;
	private String fechaAutAvisoDictamen;
	private String fechaDerivaFiscaliza;
	private String fechaAtencionOfi;
	private String fechaSolCorreccion;
	private String fechaOficioInvitacion;
	private String periodoCorrecIni;
	private String periodoCorrecFin;
	private boolean estatusObra;
	private boolean regularizaObra;
	private String observaciones;
	

	/**
	 * Retorna el valor fechaNotificacion
	 * @return  fechaNotificacion
	 */
	public String getFechaNotificacion() {
		return fechaNotificacion;
	}

	/**
	 * Asigna el valor del fechaNotificacion al atributo fechaNotificacion
	 * @param fechaNotificacion 
	 */
	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	/**
	 * Retorna el valor fechaCancelacion
	 * @return  fechaCancelacion
	 */
	public String getFechaCancelacion() {
		return fechaCancelacion;
	}

	/**
	 * Asigna el valor del fechaCancelacion al atributo fechaCancelacion
	 * @param fechaCancelacion 
	 */
	public void setFechaCancelacion(String fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	/**
	 * Retorna el valor fechaDerivaSubdel
	 * @return  fechaDerivaSubdel
	 */
	public String getFechaDerivaSubdel() {
		return fechaDerivaSubdel;
	}

	/**
	 * Asigna el valor del fechaDerivaSubdel al atributo fechaDerivaSubdel
	 * @param fechaDerivaSubdel 
	 */
	public void setFechaDerivaSubdel(String fechaDerivaSubdel) {
		this.fechaDerivaSubdel = fechaDerivaSubdel;
	}

	/**
	 * Retorna el valor fechaAutAvisoDictamen
	 * @return  fechaAutAvisoDictamen
	 */
	public String getFechaAutAvisoDictamen() {
		return fechaAutAvisoDictamen;
	}

	/**
	 * Asigna el valor del fechaAutAvisoDictamen al atributo fechaAutAvisoDictamen
	 * @param fechaAutAvisoDictamen 
	 */
	public void setFechaAutAvisoDictamen(String fechaAutAvisoDictamen) {
		this.fechaAutAvisoDictamen = fechaAutAvisoDictamen;
	}

	/**
	 * Retorna el valor fechaDerivaFiscaliza
	 * @return  fechaDerivaFiscaliza
	 */
	public String getFechaDerivaFiscaliza() {
		return fechaDerivaFiscaliza;
	}

	/**
	 * Asigna el valor del fechaDerivaFiscaliza al atributo fechaDerivaFiscaliza
	 * @param fechaDerivaFiscaliza 
	 */
	public void setFechaDerivaFiscaliza(String fechaDerivaFiscaliza) {
		this.fechaDerivaFiscaliza = fechaDerivaFiscaliza;
	}

	/**
	 * Retorna el valor fechaAtencionOfi
	 * @return  fechaAtencionOfi
	 */
	public String getFechaAtencionOfi() {
		return fechaAtencionOfi;
	}

	/**
	 * Asigna el valor del fechaAtencionOfi al atributo fechaAtencionOfi
	 * @param fechaAtencionOfi 
	 */
	public void setFechaAtencionOfi(String fechaAtencionOfi) {
		this.fechaAtencionOfi = fechaAtencionOfi;
	}

	/**
	 * Retorna el valor fechaSolCorreccion
	 * @return  fechaSolCorreccion
	 */
	public String getFechaSolCorreccion() {
		return fechaSolCorreccion;
	}

	/**
	 * Asigna el valor del fechaSolCorreccion al atributo fechaSolCorreccion
	 * @param fechaSolCorreccion 
	 */
	public void setFechaSolCorreccion(String fechaSolCorreccion) {
		this.fechaSolCorreccion = fechaSolCorreccion;
	}

	/**
	 * Retorna el valor fechaOficioInvitacion
	 * @return  fechaOficioInvitacion
	 */
	public String getFechaOficioInvitacion() {
		return fechaOficioInvitacion;
	}

	/**
	 * Asigna el valor del fechaOficioInvitacion al atributo fechaOficioInvitacion
	 * @param fechaOficioInvitacion 
	 */
	public void setFechaOficioInvitacion(String fechaOficioInvitacion) {
		this.fechaOficioInvitacion = fechaOficioInvitacion;
	}

	/**
	 * Retorna el valor periodoCorrecIni
	 * @return  periodoCorrecIni
	 */
	public String getPeriodoCorrecIni() {
		return periodoCorrecIni;
	}

	/**
	 * Asigna el valor del periodoCorrecIni al atributo periodoCorrecIni
	 * @param periodoCorrecIni 
	 */
	public void setPeriodoCorrecIni(String periodoCorrecIni) {
		this.periodoCorrecIni = periodoCorrecIni;
	}

	/**
	 * Retorna el valor periodoCorrecFin
	 * @return  periodoCorrecFin
	 */
	public String getPeriodoCorrecFin() {
		return periodoCorrecFin;
	}

	/**
	 * Asigna el valor del periodoCorrecFin al atributo periodoCorrecFin
	 * @param periodoCorrecFin 
	 */
	public void setPeriodoCorrecFin(String periodoCorrecFin) {
		this.periodoCorrecFin = periodoCorrecFin;
	}

	/**
	 * Retorna el valor regularizaObra
	 * @return  regularizaObra
	 */
	public boolean getRegularizaObra() {
		return regularizaObra;
	}

	/**
	 * Asigna el valor del regularizaObra al atributo regularizaObra
	 * @param regularizaObra 
	 */
	public void setRegularizaObra(boolean regularizaObra) {
		this.regularizaObra = regularizaObra;
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
	 * Retorna el valor estatusObra
	 * @return  estatusObra
	 */
	public boolean isEstatusObra() {
		return estatusObra;
	}

	/**
	 * Asigna el valor del estatusObra al atributo estatusObra
	 * @param estatusObra 
	 */
	public void setEstatusObra(boolean estatusObra) {
		this.estatusObra = estatusObra;
	}


	
	
}
