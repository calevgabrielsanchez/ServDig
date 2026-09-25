package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaEstado;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersona;

/**
 * 181012
 * @author ICCSRG
 *
 */
@Local
public interface EstadosPersonaFisicaServiceUtilityLocal {

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de entidad a uno de modelo
	 * @param ditHistEstadoPersona
	 * @return
	 */
	PersonaEstado transformarAModelo(DitHistEstadoPersona ditHistEstadoPersona);

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de modelo a uno de entidad
	 * @param personaEstado
	 * @param fisica
	 * @return
	 */
	DitHistEstadoPersona transformarAEntidad(PersonaEstado personaEstado, Fisica fisica);
	
}
