/**
 * SeguimientoEstatusObraVO.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.saticb
 * @project correccion-web
 */
package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;

/**
 * @author Oscar Beltran
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeguimientoEstatusObraVO extends ControlTabs{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	
	private String tipoIncidencia;
	private String fechaPresentacion;
	private boolean regularizarObra;
	private String fechaAtencionOficio;
	private String numRegObra;
	private SegEOIncidenciasObraGenericoVO incidenciasModelo;
	/**     
	 * Retorna el valor tipoIncidencia
	 * @return  tipoIncidencia
	 */
	public String getTipoIncidencia() {
		return tipoIncidencia;
	}
	/**
	 * Asigna el valor del tipoIncidencia al atributo tipoIncidencia
	 * @param tipoIncidencia 
	 */
	public void setTipoIncidencia(String tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}
	/**
	 * Retorna el valor fechaPresentacion
	 * @return  fechaPresentacion
	 */
	public String getFechaPresentacion() {
		return fechaPresentacion;
	}
	/**
	 * Asigna el valor del fechaPresentacion al atributo fechaPresentacion
	 * @param fechaPresentacion 
	 */
	public void setFechaPresentacion(String fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
	/**
	 * Retorna el valor regularizarObra
	 * @return  regularizarObra
	 */
	public boolean isRegularizarObra() {
		return regularizarObra;
	}
	/**
	 * Asigna el valor del regularizarObra al atributo regularizarObra
	 * @param regularizarObra 
	 */
	public void setRegularizarObra(boolean regularizarObra) {
		this.regularizarObra = regularizarObra;
	}
	/**
	 * Retorna el valor fechaAtencionOficio
	 * @return  fechaAtencionOficio
	 */
	public String getFechaAtencionOficio() {
		return fechaAtencionOficio;
	}
	/**
	 * Asigna el valor del fechaAtencionOficio al atributo fechaAtencionOficio
	 * @param fechaAtencionOficio 
	 */
	public void setFechaAtencionOficio(String fechaAtencionOficio) {
		this.fechaAtencionOficio = fechaAtencionOficio;
	}
	public String getNumRegObra() {
		return numRegObra;
	}
	public void setNumRegObra(String numRegObra) {
		this.numRegObra = numRegObra;
	}
	public SegEOIncidenciasObraGenericoVO getIncidenciasModelo() {
		return incidenciasModelo;
	}
	public void setIncidenciasModelo(SegEOIncidenciasObraGenericoVO incidenciasModelo) {
		this.incidenciasModelo = incidenciasModelo;
	}
	
	
	

}
