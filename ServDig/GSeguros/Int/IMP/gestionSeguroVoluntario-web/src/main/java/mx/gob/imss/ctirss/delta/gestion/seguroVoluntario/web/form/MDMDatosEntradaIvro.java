package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.form;

import mx.gob.imss.digital.modelo.persona.Persona;

/** @author Dj Leo  12/11/2014
 * The Class MDMDatosEntrada.
 */
public class MDMDatosEntradaIvro {
	
	/** The persona. */
	private Persona persona;
	
	/** The error form general. */
	private String errorFormGeneral;

	/**
	 * Gets the persona.
	 *
	 * @return the persona
	 */
	public Persona getPersona() {
		return persona;
	}

	/**
	 * Sets the persona.
	 *
	 * @param persona the new persona
	 */
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	/**
	 * Gets the error form general.
	 *
	 * @return the error form general
	 */
	public String getErrorFormGeneral() {
		return errorFormGeneral;
	}

	/**
	 * Sets the error form general.
	 *
	 * @param errorFormGeneral the new error form general
	 */
	public void setErrorFormGeneral(String errorFormGeneral) {
		this.errorFormGeneral = errorFormGeneral;
	}
	
}
