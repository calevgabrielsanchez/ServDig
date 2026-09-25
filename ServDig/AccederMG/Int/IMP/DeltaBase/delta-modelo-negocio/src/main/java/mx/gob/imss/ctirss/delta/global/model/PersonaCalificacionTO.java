package mx.gob.imss.ctirss.delta.global.model;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class PersonaCalificacionTO extends AbstractModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -476797378128082749L;
	
	private CalificacionTO[] calificacion;

	public CalificacionTO[] getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(CalificacionTO[] calificacion) {
		this.calificacion = calificacion != null ? calificacion.clone() : null;
	}
	
	
	
}
