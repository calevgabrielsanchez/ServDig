/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: AnalisisServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.analisis
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.analisis;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.persistence.DitAnalisisCe;

@Local
public interface AnalisisServiceUtilityLocal {
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	AnalisisClasificacionEmpresas convertirEntityToModel(DitAnalisisCe entity) throws Exception;
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	AnalisisClasificacionEmpresas convertirEntityToModelDetalle(DitAnalisisCe entity) throws Exception;
		
}
