/**
 *  Clase de Servicio para el soporte a la funcionalidad del DataTable de jQuery.
 *  
 *  Paginacion , Filtro , Sort etc.
 */
package mx.gob.imss.ctirss.clasificador.service;

import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableReply;
import mx.gob.imss.ctirss.clasificador.model.controller.AbstractDataTableSend;

/**
 * @author Lucio Duran Silva
 *
 */
public interface DataTableService {

	
	/**
	 * MŽtodo para realizar la consulta especifica para el DataTable.
	 * @param dtParams
	 * @return
	 */
	public AbstractDataTableReply filter(AbstractDataTableSend dtParams);
	
	
	/**
	 * Metodo para realizar las consultas de fracciones por palabra anterior.
	 * @param dtParams
	 * @return
	 */
	public AbstractDataTableReply filterXPalabraAnterior(AbstractDataTableSend dtParams);
	
	/**
	 * MŽtodo para realizar las consultas de frecciones nuevas por numero anterior.
	 * @param dtParams
	 * @return
	 */
	public AbstractDataTableReply filterXNumeroAnterior(AbstractDataTableSend dtParams);
	
	
	/**
	 * Metodo apra realizar la consulta de fracciones nuevas por Numero
	 * @param dtParams
	 * @return
	 */
	public AbstractDataTableReply filterXNumero(
			AbstractDataTableSend dtParams);
	
	/**
	 * MŽtodo para realizar las consultas de fraccion por palabra.
	 * @param dtParams
	 * @return
	 */
	public AbstractDataTableReply filterXPalabra(
			AbstractDataTableSend dtParams); 
	
	
	public AbstractDataTableReply filterXPalabraEnAnterior(AbstractDataTableSend dtParams);
	
	public AbstractDataTableReply filterXNumeroEnAnterior(AbstractDataTableSend dtParams);
	
}
