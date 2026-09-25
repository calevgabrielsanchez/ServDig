/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ClasificacionServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacionPropuesta;

@Local
public interface ClasificacionServiceUtilityLocal {
	DitClasificacion convertirModelToEntity(Clasificacion model) throws Exception;
	
	Clasificacion convertirEntityToModel(DitClasificacion entity) throws Exception;
	
	DitClasificacionPropuesta convertirModelToEntity(AnalisisClasificacionEmpresas model) throws Exception;
}
 
