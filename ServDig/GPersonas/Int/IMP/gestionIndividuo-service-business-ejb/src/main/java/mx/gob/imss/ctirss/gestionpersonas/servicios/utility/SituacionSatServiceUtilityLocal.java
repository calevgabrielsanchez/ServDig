package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.delta.persistence.DitSituacionSat;

@Local
public interface SituacionSatServiceUtilityLocal {

	/**
	 * Transforma un entity a un objeto de modelo
	 * 
	 * @param entity
	 * @return
	 * @throws TransformacionException
	 */
	SituacionSAT transformarSituacionSat(DitSituacionSat entity)
			throws TransformacionException;

	/**
	 * Transforma un objeto de modelo a un entity
	 * 
	 * @param model
	 * @return
	 * @throws TransformacionException
	 */
	DitSituacionSat transformarSituacionSat(SituacionSAT model)
			throws TransformacionException;

}
