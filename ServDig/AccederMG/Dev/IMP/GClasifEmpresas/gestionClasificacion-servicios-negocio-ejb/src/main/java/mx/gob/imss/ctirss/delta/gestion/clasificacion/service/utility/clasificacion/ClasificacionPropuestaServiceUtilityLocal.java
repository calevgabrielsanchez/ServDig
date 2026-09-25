/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ClasificacionPropuestaServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clasificacion;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacionPropuesta;

@Local
public interface ClasificacionPropuestaServiceUtilityLocal{
	AnalisisClasificacionEmpresas convertirEntityToModel(DitClasificacionPropuesta entity)throws PersistenceException;
}
 
