/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ReportesAnalisisServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.analisis
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.analisis;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteAnalisis;
import mx.gob.imss.ctirss.delta.persistence.DivReporteAnalisis;

@Local
public interface ReportesAnalisisServiceUtilityLocal {
	
	/**
	 * Convierte una lista de Entities de tipo DivReporteAnalisis a una lista de Models de tipo ReporteAnalisis
	 * @param origen
	 * @return
	 * @throws Exception 
	 */
	List <ReporteAnalisis> convertListOfEntitiesToListOfModel(List <DivReporteAnalisis> origen) throws Exception;
	
}
