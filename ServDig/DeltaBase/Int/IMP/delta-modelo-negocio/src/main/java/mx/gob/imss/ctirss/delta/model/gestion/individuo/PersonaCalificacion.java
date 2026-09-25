package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class PersonaCalificacion extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5199188857300643159L;
	private Persona persona;
	private Calificacion calificacion;
	private Date fechaCalificacion;

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}


	public Date getFechaCalificacion() {
		return fechaCalificacion;
	}

	public void setFechaCalificacion(Date fechaCalificacion) {
		this.fechaCalificacion = fechaCalificacion;
	}

	/**
	 * @return the calificacion
	 */
	public Calificacion getCalificacion() {
		return calificacion;
	}

	/**
	 * @param calificacion the calificacion to set
	 */
	public void setCalificacion(Calificacion calificacion) {
		this.calificacion = calificacion;
	}
}
