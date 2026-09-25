package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;

/**
 * 
 * Project: gestionIndividuo-service-business-ejb
 * IndividuoServiceUtilityLocal.java
 * @author Hugo Armando Martinez Chamonica
 * 19/07/2012 11:29:29
 */
@Local
public interface IndividuoServiceUtilityLocal {
	
	/**
	 * Obtiene los datos de la persona fisica
	 * @author Hugo Armando Martinez Chamonica
	 * 19/07/2012 11:31:08
	 * @param entity
	 * @return Fisica 
	 *
	 */
	Fisica convertirEntityToModelFisica(DitPersonaFisica entity);
	
	/**
	 * Obtiene los datos de la persona moral
	 * @author Hugo Armando Martinez Chamonica
	 * 19/07/2012 11:31:34
	 * @param entity
	 * @return Moral
	 *
	 */
	Moral convertirEntityToModelMoral(DitPersonaMoral entity);
	
	/**
	 * 
	 * @author Hugo Armando Martinez Chamonica
	 * 19/07/2012 12:22:03
	 * @param entity
	 * @return Sexo
	 *
	 */
	Sexo convertirEntityToModelSexo(DicSexo entity);
	
	/**
	 * 
	 * @author Hugo Armando Martinez Chamonica
	 * 19/07/2012 12:29:35
	 * @param entity
	 * @return Tipo Sociedad
	 *
	 */
	TipoSociedad convertirEntityToModelTipoSociedad(DicTipoSociedad entity);
}
