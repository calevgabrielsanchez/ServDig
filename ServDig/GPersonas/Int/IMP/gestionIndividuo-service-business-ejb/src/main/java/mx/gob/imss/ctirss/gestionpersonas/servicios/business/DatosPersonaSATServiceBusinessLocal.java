package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.DatosPersonaSATNoValidosException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.DatosPersonaSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

@Local
public interface DatosPersonaSATServiceBusinessLocal {

	/**
	 * Guarda / Actualiza los datos SAT de una persona
	 * 
	 * @param datosPersonaSAT
	 * @throws DatosPersonaSATNoValidosException
	 */
	void guardar(DatosPersonaSAT datosPersonaSAT)
			throws DatosPersonaSATNoValidosException;

	/**
	 * Obtiene los datos SAT de una Persona
	 * 
	 * @param persona
	 * @return
	 * @throws DatosPersonaSATNoValidosException
	 */
	DatosPersonaSAT obtenerDatosSAT(Persona persona)
			throws DatosPersonaSATNoValidosException;
}
