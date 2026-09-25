/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:MedioContactoServiceUtility.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.utility
 *  @Fecha:10/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoContacto;
import mx.gob.imss.ctirss.delta.persistence.DicModulo;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitTipoContacto;

/**
 * @author Lucio Duran Silva
 *
 */
@Local
public interface MedioContactoServiceUtilityLocal {

	
	/**
	 * Transforma el medio de contacto a un objecto persistente.
	 * @param medio
	 * @return
	 * @throws TransformacionException
	 */
	List<DitFormaContacto> transformarMedioContacto( List<MedioContacto> models ) throws TransformacionException;
	
	
	
	/**
	 * 
	 * @param medios
	 * @return
	 * @throws TransformacionException
	 */
	List<MedioContacto> transformarMedioContactoEntities ( List<DitFormaContacto> entities ) throws TransformacionException;
	
	/**
	 * 
	 * @param entities
	 * @return
	 * @throws TransformacionException
	 */
	List<Modulo> transformarModuloEntities ( List<DicModulo> entities ) throws TransformacionException;
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	TipoContacto transformarTipoContactoEntity(DitTipoContacto entity);
	
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	List<TipoContacto> transformarTipoContactoEntities(List<DitTipoContacto> entities);
	
	/**
	 * 
	 * @param entities
	 * @return
	 */
	List<MedioContacto> transformarEntitiesToModel(List<DitFormaContacto> entities);
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	MedioContacto transformarEntityToModel(DitFormaContacto entity);
	
	/**
	 * 
	 * @param mediosDelta
	 * @return
	 */
	List<mx.gob.imss.digital.modelo.medio.contacto.MedioContacto>
	transformarMediosDeltaAImssDigital(List<MedioContacto> mediosDelta);
}
