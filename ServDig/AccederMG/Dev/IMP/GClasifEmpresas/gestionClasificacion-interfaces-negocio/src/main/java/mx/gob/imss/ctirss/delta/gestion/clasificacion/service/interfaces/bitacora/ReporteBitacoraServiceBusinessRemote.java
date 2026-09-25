/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:ReporteBitacoraServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.bitacora
 *  @Fecha:20/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.bitacora;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ExportarReporteException;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacora;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosBitacoras;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;

@Remote
public interface ReporteBitacoraServiceBusinessRemote {
	
	/**
	 * Genera Archivo Excel para Bitacora 
	 * @param listaResultados
	 * @param filtrosBitacoras
	 * @return
	 * @throws Exception
	 */
	byte[] obtieneReporteExcel(List<ElementoBitacora> listaResultados, FiltrosBitacoras filtrosBitacoras) throws Exception;	
	
	/**
	 * Obtiene parametros necesarios para generar una bitacor
	 * @param model
	 * @return
	 * @throws ExportarReporteException
	 */
	List<ElementoBitacora> obtieneElementosReporteExcel(FiltrosBitacoras model, int iRol) throws ExportarReporteException ;
	
	/**
	 * Almacena Archivo Excel en la BD
	 * @param estatusAnalisisModel
	 * @throws Exception
	 */
	void guardaBitacora(EstatusAnalisisModel estatusAnalisisModel) throws Exception;
	
	/**
	 * Consulta Clasificacion Declarada a partir del Historico
	 * @param idAnalisis
	 * @return
	 * @throws Exception
	 */
	Clasificacion consultaClasificacionActualPorHistorico(Long idAnalisis) throws Exception;
	
}
