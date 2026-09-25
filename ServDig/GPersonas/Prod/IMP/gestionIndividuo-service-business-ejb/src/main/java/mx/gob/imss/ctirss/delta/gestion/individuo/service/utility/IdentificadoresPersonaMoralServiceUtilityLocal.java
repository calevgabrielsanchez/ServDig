package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificadorMoral;

@Local
public interface IdentificadoresPersonaMoralServiceUtilityLocal {

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de entidad a
	 * uno de modelo
	 * 
	 * @param ditIdentificador
	 * @return
	 */
	Identificador transformarAModelo(
			DitIdentificadorMoral ditIdentificadorMoral);

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de modelo a
	 * uno de entidad
	 * 
	 * @param identificador
	 * @return
	 */
	DitIdentificadorMoral transformarAEntidad(
			Identificador identificador, Moral moral);

}
