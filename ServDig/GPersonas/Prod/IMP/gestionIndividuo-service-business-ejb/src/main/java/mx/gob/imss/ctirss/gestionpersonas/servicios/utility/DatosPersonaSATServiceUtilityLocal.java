package mx.gob.imss.ctirss.gestionpersonas.servicios.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.DatosPersonaSAT;
import mx.gob.imss.ctirss.delta.persistence.DitDatosPersonaSat;

@Local
public interface DatosPersonaSATServiceUtilityLocal {

	/**
	 * Transforma un entity a un objeto de modelo
	 * 
	 * @param entity
	 * @return
	 * @throws TransformacionException
	 */
	DatosPersonaSAT transformarDatosPersonaSAT(DitDatosPersonaSat entity)
			throws TransformacionException;

	/**
	 * Transforma un objeto de modelo a un entity
	 * 
	 * @param model
	 * @return
	 * @throws TransformacionException
	 */
	DitDatosPersonaSat transformarDatosPersonaSAT(DatosPersonaSAT model)
			throws TransformacionException;

}
