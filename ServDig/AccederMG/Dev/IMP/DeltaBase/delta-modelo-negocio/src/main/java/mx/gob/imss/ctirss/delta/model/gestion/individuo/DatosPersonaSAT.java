package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DatosPersonaSAT extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private Long cveDatosSAT;
	private Persona persona;
	private Date fechaConstitucion;
	private Date fechaInicioOperaciones;

	/**
	 * @return the cveDatosSAT
	 */
	public Long getCveDatosSAT() {
		return cveDatosSAT;
	}

	/**
	 * @param cveDatosSAT the cveDatosSAT to set
	 */
	public void setCveDatosSAT(Long cveDatosSAT) {
		this.cveDatosSAT = cveDatosSAT;
	}

	/**
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * @return the fechaConstitucion
	 */
	public Date getFechaConstitucion() {
		return fechaConstitucion;
	}

	/**
	 * @param fechaConstitucion the fechaConstitucion to set
	 */
	public void setFechaConstitucion(Date fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}

	/**
	 * @return the fechaInicioOperaciones
	 */
	public Date getFechaInicioOperaciones() {
		return fechaInicioOperaciones;
	}

	/**
	 * @param fechaInicioOperaciones the fechaInicioOperaciones to set
	 */
	public void setFechaInicioOperaciones(Date fechaInicioOperaciones) {
		this.fechaInicioOperaciones = fechaInicioOperaciones;
	}

}
