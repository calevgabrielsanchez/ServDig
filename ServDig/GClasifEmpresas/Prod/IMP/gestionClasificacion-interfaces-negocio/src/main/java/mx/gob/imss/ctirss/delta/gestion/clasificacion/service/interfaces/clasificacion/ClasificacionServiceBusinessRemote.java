/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:ClasificacionServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion
 *  @Fecha:11/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;

@Remote
public interface ClasificacionServiceBusinessRemote{
	/**
	 * Obtiene Fraccion para asignar a una Clasificacion
	 * @param clasificacion
	 * @return
	 * @throws Exception
	 */
	Clasificacion obtenerDetalleFraccion(Clasificacion clasificacion) throws Exception;
	
	/**
	 * Consulta la Clasificacion inicial de un Patron
	 * @param cveIdAnalisis
	 * @return
	 * @throws Exception
	 */
	EstatusAnalisisModel buscaClasificacionInicial(Long cveIdAnalisis) throws Exception;
	
	/**
	 * Actualiza la Fraccion a una Clasificacion
	 * @param clasificacion
	 * @param fraccion
	 * @throws Exception
	 */
	void actualizaFraccion(Clasificacion clasificacion, Fraccion fraccion) throws Exception;

	/**
	 * Actualiza la Fraccion y prima a una Clasificacion
	 * @param clasificacion
	 * @param fraccion
	 * @throws Exception
	 */
	void actualizaFraccionyPrima(Clasificacion clasificacion, Fraccion fraccion) throws Exception;

	
	/**
	 * Elimina una Clasificacion
	 * @param idClasificacion
	 * @throws Exception
	 */
	void elimina(long idClasificacion) throws Exception;
	
	//void actualizaFraccionAnterior(Long cveIdSolicitud, Fraccion fraccion, TipoPersonaFiscal tipoPersonaFiscal) throws Exception;
	
	/**
	 * 
	 * Consulta Clasificacion
	 * @param clasificacion
	 */
	Clasificacion obtenerClasificacionEquivalente(Clasificacion clasificacion);
	
	/**
	 * Obtiene Fraccion para asignar a una Clasificacion
	 * @param clasificacion
	 * @return
	 * @throws Exception
	 */
	Clasificacion obtenerDetalleFraccionPorId(Clasificacion clasificacion) throws Exception;
	
	/**
	 * 
	 * Consulta Clasificacion
	 * @param clasificacion
	 */
	Clasificacion consultarFracEqPorNumero(Clasificacion clasificacion);

	/**
	 * Actualiza la solicitud y tramite
	 * @param cveIdSolicitud
	 * @param cveIdEstadoSol
	 * @param cveIdEstadoTram
	 * @throws Exception
	 */
	void actualizaSolicitudyTramite(String cveIdSolicitud, Long cveIdEstadoSol, Long cveIdEstadoTram) throws Exception;

	
}
