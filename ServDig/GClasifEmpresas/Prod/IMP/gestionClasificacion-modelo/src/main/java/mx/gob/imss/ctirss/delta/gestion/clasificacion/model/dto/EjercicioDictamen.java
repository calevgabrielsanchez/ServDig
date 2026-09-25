package mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class EjercicioDictamen extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2497076743174643908L;
	private Long idEjercicio;
	private String descripcion;
	
	public EjercicioDictamen(Long idEjercicio, String descripcion) {
		super();
		this.idEjercicio = idEjercicio;
		this.descripcion = descripcion;
	}

	public Long getIdEjercicio() {
		return idEjercicio;
	}
	
	public void setIdEjercicio(Long idEjercicio) {
		this.idEjercicio = idEjercicio;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
