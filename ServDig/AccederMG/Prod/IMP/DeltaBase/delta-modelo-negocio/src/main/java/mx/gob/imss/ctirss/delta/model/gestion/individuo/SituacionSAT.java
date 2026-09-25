package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class SituacionSAT extends AbstractModel {

	private static final long serialVersionUID = -6376249488655839731L;

	// Atributo para settear el id de la base de datos
	private Long idSituacionSAT;
	private Date fechaSituacion;
	// Atributo que representa la clave devuelta por el WS del SAT
	private String cveSituacionSAT;
	private String descripcion;
	private Persona persona;

	/**
	 * @return the idSituacionSAT
	 */
	public Long getIdSituacionSAT() {
		return idSituacionSAT;
	}

	/**
	 * @param idSituacionSAT
	 *            the idSituacionSAT to set
	 */
	public void setIdSituacionSAT(Long idSituacionSAT) {
		this.idSituacionSAT = idSituacionSAT;
	}

	/**
	 * @return the fechaSituacion
	 */
	public Date getFechaSituacion() {
		return fechaSituacion;
	}

	/**
	 * @param fechaSituacion
	 *            the fechaSituacion to set
	 */
	public void setFechaSituacion(Date fechaSituacion) {
		this.fechaSituacion = fechaSituacion;
	}

	/**
	 * @return the cveSituacionSAT
	 */
	public String getCveSituacionSAT() {
		return cveSituacionSAT;
	}

	/**
	 * @param cveSituacionSAT
	 *            the cveSituacionSAT to set
	 */
	public void setCveSituacionSAT(String cveSituacionSAT) {
		this.cveSituacionSAT = cveSituacionSAT;
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
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

}
