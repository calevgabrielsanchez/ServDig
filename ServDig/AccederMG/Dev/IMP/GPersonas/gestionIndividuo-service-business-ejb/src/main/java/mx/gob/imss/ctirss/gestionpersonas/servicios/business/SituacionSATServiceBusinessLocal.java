package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.situacionSAT.SituacionSATNoValidaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;

/**
 * @author Marco Sánchez
 * @date 20/03/2013
 */

@Local
public interface SituacionSATServiceBusinessLocal {

	/**
	 * Guarda una nueva situación SAT
	 * 
	 * @param situacionSAT
	 * @return
	 * @throws SituacionSATNoValidaException
	 */
	SituacionSAT guardar(SituacionSAT situacionSAT)
			throws SituacionSATNoValidaException;

	/**
	 * Expira una situación SAT
	 * 
	 * @param situacionSAT
	 * @throws SituacionSATNoValidaException
	 */
	void expirar(SituacionSAT situacionSAT)
			throws SituacionSATNoValidaException;

	/**
	 * Obtiene las situaciones SAT relacionadas a una persona
	 * 
	 * @param persona
	 * @param obtenerActivas - true(para obtener sólo las situaciones activas), 
	 * 			false(para obtener todas las situaciones)
	 * @return
	 * @throws SituacionSATNoValidaException
	 */
	List<SituacionSAT> obtenerSituacionesPersona(Persona persona,
			boolean obtenerActivas) throws SituacionSATNoValidaException;

}
