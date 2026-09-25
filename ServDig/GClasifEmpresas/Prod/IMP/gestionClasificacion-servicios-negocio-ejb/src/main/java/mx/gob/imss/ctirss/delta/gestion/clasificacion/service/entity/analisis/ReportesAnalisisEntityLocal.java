/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:ReportesAnalisisEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosReportes;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteAnalisis;

@Local
public interface ReportesAnalisisEntityLocal{
	
	/**
	 * Método para consultar los analisis a las solicitudes 
	 * @param filtro
	 * @return
	 */
	List<ReporteAnalisis> consultarReportesAnalisis(FiltrosReportes filtro) throws Exception;
	
}
