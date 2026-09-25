/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:ReporteConcentradoServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.concentrado
 *  @Fecha:20/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.concentrado;

import java.util.ArrayList;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ExportarReporteException;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SumarizadoConcentrado;

@Remote
public interface ReporteConcentradoServiceBusinessRemote {
	
	/**
	 * Obtiene parametros necesarios para generar reporte de concentrado
	 * @param model
	 * @param idRol
	 * @param usuario
	 * @return
	 * @throws ExportarReporteException
	 */
	ArrayList<SumarizadoConcentrado> obtieneElementosReporteExcel(FiltrosConcentrado model, int idRol, String usuario) throws ExportarReporteException, Exception;
	
	/**
	 * Genera Archivo Excel para Concentrado 
	 * @param listaResultados
	 * @param fechIni
	 * @param fechFin
	 * @return
	 * @throws Exception
	 */
	byte[] obtieneReporteExcel(ArrayList<SumarizadoConcentrado> listaResultados, String fechIni, String fechFin) throws Exception;	
	

}
