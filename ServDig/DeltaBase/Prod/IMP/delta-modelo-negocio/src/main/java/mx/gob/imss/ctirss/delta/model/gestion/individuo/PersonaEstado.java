package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class PersonaEstado extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7729225522425057962L;
	private Persona persona;
	private EstadoPersona estadoPersona;
	private Date fechaCalificacion;

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public EstadoPersona getEstadoPersona() {
		return estadoPersona;
	}

	public void setEstadoPersona(EstadoPersona estadoPersona) {
		this.estadoPersona = estadoPersona;
	}

	public Date getFechaCalificacion() {
		return fechaCalificacion;
	}

	public void setFechaCalificacion(Date fechaCalificacion) {
		this.fechaCalificacion = fechaCalificacion;
	}
}
