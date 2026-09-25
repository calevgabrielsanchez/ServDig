/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:ReportesAnalisisBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis
 *  @Fecha:12/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ConsultaReporteException;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosReportes;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteAnalisis;


@Remote
public interface ReportesAnalisisBusinessRemote {
	
	/**
	 * Obtiene el reporte de analisis segun los criterios de busqueda
	 * @param filtro Objeto con los criterios de busqueda.
	 * @return Lista de analisis encontrados.
	 * @throws Exception En caso de error.
	 */
	List<ReporteAnalisis> consultarReporteAnalisis(FiltrosReportes filtro, int iRol) throws ConsultaReporteException;
	
	List<ReporteAnalisis> consultarReporteAnalisisAlmacenes(FiltrosReportes filtro, int iRol) throws ConsultaReporteException;
	
	byte[] crearReporteAnalisis( List<ReporteAnalisis> listaRetVal, int grupoAnalisis) throws Exception;
	
	byte[] crearReporteAnalisisAlmacenes( List<ReporteAnalisis> listaRetVal, int grupoAnalisis) throws Exception;
		
}
