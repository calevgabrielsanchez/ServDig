/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Archivo: BuzonClasificacion.java
 * @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 */
public class BuzonClasificacion extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2876338001840976123L;
	
	private Long id;
	private String regPatron;
	private Long idTipoTramite;
	private String refFolio;
	private String mensaje;
	private Date fecPresentacion;
	
	@Override
	public String toString() {
		return "BuzonClasificacion [id=" + id + ", regPatron=" + regPatron + ", idTipoTramite=" + idTipoTramite
				+ ", refFolio=" + refFolio + ", mensaje=" + mensaje + ", fecPresentacion=" + fecPresentacion + "]";
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegPatron() {
		return regPatron;
	}
	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}
	public Long getIdTipoTramite() {
		return idTipoTramite;
	}
	public void setIdTipoTramite(Long idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}
	public String getRefFolio() {
		return refFolio;
	}
	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Date getFecPresentacion() {
		return fecPresentacion;
	}
	public void setFecPresentacion(Date fecPresentacion) {
		this.fecPresentacion = fecPresentacion;
	}
		
}
