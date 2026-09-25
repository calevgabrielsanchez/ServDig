/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: H�ctor Lara Andr�s
 *  @Proyecto: delta
 *  @Archivo:RectificacionBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.rectificacion
 *  @Fecha:15/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.rectificacion;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.clasificacion.AnalisisNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionPropuestaDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;

@Remote
public interface RectificacionBusinessRemote{
	/**
	 * Hace uso de un Servicio de Gestion Patronal para evaluar una Clasificacion Propuesta
	 * @param rfc
	 * @param clase
	 * @param registroPatronal
	 * @return
	 * @throws GestionPatronalBusinessException
	 */
	//public Boolean validaReglaRPC(String rfc, String clase, String registroPatronal) throws GestionPatronalBusinessException;
	
	/**
	 * Obtiene Clasificacion Inicial
	 * @param idSolicitud
	 * @return
	 * @throws Exception
	 */
	//AnalisisClasificacionEmpresas consultarClasificacionAnteriorPorIdSolicitud(Long idSolicitud)throws Exception;
	
	/**
	 * Cancela una Rectificacion al evaluar que ha sido rechazado por la Regla RPC
	 * @param analisisClasificacionEmpresas
	 * @return
	 * @throws Exception
	 */
	AnalisisClasificacionEmpresas rechazoPorReglaRPC(AnalisisClasificacionEmpresas analisisClasificacionEmpresas)throws Exception;
	
	/**
	 * Almacena en BD la Clasificacion que se le Propone al Patron
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	Integer guardaRectificacion(ClasificacionPropuestaDTO dto, Boolean esDictamen)throws Exception;

	/**
	 * Almacena en BD la omision
	 * @param dictamenDTO
	 * @return
	 * @throws Exception
	 */
	Integer guardaOmision(String omision, String idAnalisis, String justificacion, String pago, String fechaSurteEfecto) throws Exception;

	void actualizaEstatusRegularizar(String cveIdAnalisis)
			throws AnalisisNoEncontradoException;
	
	/**
	 * Valida existencia de datos en BD con valores recibidos del Clasificador
	 * Se aplica validacion con el servicio de Regla RPC (validarRPC_AP_MOD_MAC)
	 * @param clasificacion
	 * @param rfc
	 * @param clase
	 * @param rp
	 * @return
	 */
	//Clasificacion validaClasificadorReglaRPC(Clasificacion clasificacion, String rfc, Long clase, String rp)throws Exception;
}
