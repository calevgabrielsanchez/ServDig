package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersona;

/**
 * 181012
 * @author ICCSRG
 *
 */
@Local
public interface EstadosPersonaFisicaServiceEntityLocal {

	/**
	 * Metodo encargado de realizar el registro de los estados asociados a una persona fisica en BDU
	 * @param ditHistEstadoPersona
	 */
	void registrar(DitHistEstadoPersona ditHistEstadoPersona);

	/**
	 * Metodo encargado de realizar la actualizacion de los estados asociados a una persona fisica en BDU
	 * @param ditHistEstadoPersona
	 */
	void actualizar(DitHistEstadoPersona ditHistEstadoPersona);
	
}
