package mx.gob.imss.ctirss.delta.global.model;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class CalificacionTO extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6225380832806853110L;
	
	private Long idCalificacion;
	private String descripcion;
	private Date fechaCalificacion;
	
	public Long getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(Long idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getFechaCalificacion() {
		return fechaCalificacion;
	}
	
	/**
	 * 
	 * @param fechaCalificacion
	 */
	public void setFechaCalificacion(Date fechaCalificacion) {
		this.fechaCalificacion = fechaCalificacion;
	}
	
	
}
