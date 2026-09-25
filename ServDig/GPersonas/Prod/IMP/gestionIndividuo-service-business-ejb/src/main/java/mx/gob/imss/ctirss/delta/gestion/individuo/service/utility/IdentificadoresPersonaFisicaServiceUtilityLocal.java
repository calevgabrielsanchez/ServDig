package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificador;

/**
 * 111012
 * @author ICCSRG
 *
 */
@Local
public interface IdentificadoresPersonaFisicaServiceUtilityLocal {

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de entidad a uno de modelo
	 * @param ditIdentificador
	 * @return
	 */
	Identificador transformarAModelo(DitIdentificador ditIdentificador);

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de modelo a uno de entidad
	 * @param identificador
	 * @return
	 */
	DitIdentificador transformarAEntidad(Identificador identificador, Fisica fisica);
	
}
