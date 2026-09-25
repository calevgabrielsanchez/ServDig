/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: BitacoraServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstatusAnalisis;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;

@Local
public interface BitacoraServiceUtilityLocal {
	/**
	 * 
	 * @param model
	 * @return
	 */
	DitHistEstatusAnalisis convertirModelToEntity(EstatusAnalisisModel model) throws Exception;
	
	EstatusAnalisisModel armaBitacora(AnalisisClasificacionEmpresas model, String comentario) throws Exception;
	
	EstatusAnalisisModel convertirEntityToModel(DitHistEstatusAnalisis entity) throws Exception;
	
	String obtenerFraccion(DicFraccion dicFraccion) ;
	
	EstatusAnalisisModel armaBitacoraCambioClem(String idAnalisis, Fraccion fraccion, String del, String subDel, String usuario)
			throws Exception;

}
