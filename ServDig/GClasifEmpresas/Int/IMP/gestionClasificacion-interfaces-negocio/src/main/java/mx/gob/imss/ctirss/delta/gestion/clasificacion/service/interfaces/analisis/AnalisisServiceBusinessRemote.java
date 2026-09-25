/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:AnalisisServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis
 *  @Fecha:10/05/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.AnalisisNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.EstatusMovimientoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.PatronNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.model.clasificacion.AdjuntosClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Remote
public interface AnalisisServiceBusinessRemote {

	/**
	 * InDica que la Clasificacion elegida por el Patron es correcta 
	 * @param dto
	 * @return
	 * @throws AnalisisNoEncontradoException
	 * @throws ClasificacionException
	 * @throws EstatusMovimientoException
	 */
	AnalisisClasificacionEmpresas ratificarPendiente(ClasificacionDTO dto)
			throws AnalisisNoEncontradoException, ClasificacionException,
			EstatusMovimientoException;
	
	/**
	 * InDica que la clasificacion elegida por el patron es correcta, esto para Dictamen
	 * @param dto
	 * @return
	 * @throws AnalisisNoEncontradoException
	 * @throws ClasificacionException
	 * @throws EstatusMovimientoException
	 */
	AnalisisClasificacionEmpresas ratificarPendienteDictamen(ClasificacionDTO dto)
			throws AnalisisNoEncontradoException, ClasificacionException, EstatusMovimientoException;
	/**
	 * Autoriza la Clasificacion establecida por el Patron
	 * @param dto
	 * @return
	 * @throws AnalisisNoEncontradoException
	 * @throws EstatusMovimientoException
	 * @throws ClasificacionException 
	 * @throws PersistenceException 
	 */
	AnalisisClasificacionEmpresas autorizarRatificacion(
			ClasificacionDTO dto) throws AnalisisNoEncontradoException,
			EstatusMovimientoException, PersistenceException, ClasificacionException;
	
	/**
	 * Autoriza la clasificacion establecida por el patron de Dictamen
	 * @param dto
	 * @return
	 * @throws AnalisisNoEncontradoException
	 * @throws EstatusMovimientoException
	 * @throws PersistenceException
	 * @throws ClasificacionException
	 */
	AnalisisClasificacionEmpresas autorizarRatificacionDictamen(
			ClasificacionDTO dto) throws AnalisisNoEncontradoException,
			EstatusMovimientoException, PersistenceException, ClasificacionException;
	
	/**
	 * Deshace los cambios al rechazar una Ratificacion
	 * @param dto
	 * @return
	 * @throws AnalisisNoEncontradoException
	 * @throws NumberFormatException
	 * @throws EstatusMovimientoException
	 * @throws ClasificacionException 
	 */
	AnalisisClasificacionEmpresas rechazarRatificacionPendAut(
			ClasificacionDTO dto) throws AnalisisNoEncontradoException,
			NumberFormatException, EstatusMovimientoException,
			ClasificacionException;
	
	/**
	 * Deshace los cambios al rechazar una ratificaci�n de Dictamen
	 * @param dto
	 * @return
	 * @throws AnalisisNoEncontradoException
	 * @throws NumberFormatException
	 * @throws EstatusMovimientoException
	 * @throws ClasificacionException
	 */
	AnalisisClasificacionEmpresas rechazarRatificacionDictamenPendAut(
			ClasificacionDTO dto) throws AnalisisNoEncontradoException,
			NumberFormatException, EstatusMovimientoException,
			ClasificacionException;
	
	/**
	 * Autoriza la rectificacion del la clasificacion del patron.
	 * @param model Objeto con el analisis realizado con el estatus del analisis
	 * @return Objeto AnalisisClasificacionEmpresas ya actualizado.
	 * @throws AnalisisNoEncontradoException
	 * @throws EstatusMovimientoException
	 */
	AnalisisClasificacionEmpresas autorizarRectificarSolicitud(
			AnalisisClasificacionEmpresas model)
			throws AnalisisNoEncontradoException, EstatusMovimientoException;	
	
	/**
	 * Deshace los cambios al rechazar una Rectificacion 
	 * @param dto
	 * @return
	 * @throws AnalisisNoEncontradoException
	 * @throws NumberFormatException
	 * @throws ClasificacionException
	 * @throws EstatusMovimientoException
	 */
	AnalisisClasificacionEmpresas rechazarRectificacionPendAut(
			ClasificacionDTO dto) throws AnalisisNoEncontradoException,
			NumberFormatException, ClasificacionException,
			EstatusMovimientoException;
	
	/**
	 * Deshace los cambios al rechazar una Rectificacion 
	 * @param dto
	 * @return
	 * @throws AnalisisNoEncontradoException
	 * @throws NumberFormatException
	 * @throws ClasificacionException
	 * @throws EstatusMovimientoException
	 */
	AnalisisClasificacionEmpresas rechazarRectificacionDictamenPendAut(
			ClasificacionDTO dto) throws AnalisisNoEncontradoException,
			NumberFormatException, ClasificacionException,
			EstatusMovimientoException;
	
	
	/**
	 * Deshace los cambios de cambio de clasificacion al Modificar la Autorizacion
	 * @param dto
	 * @return
	 * @throws DatosClemException
	 * @throws NumberFormatException
	 * @throws ClasificacionException
	 * @throws EstatusMovimientoException
	 * @throws AnalisisNoEncontradoException 
	 */
	AnalisisClasificacionEmpresas rechazarAutorizacion(
			ClasificacionDTO dto, Long cveIdPatronDictamen, boolean esDictamen) throws DatosClemException,
			NumberFormatException,
			ClasificacionException, EstatusMovimientoException,
			AnalisisNoEncontradoException;

	/**
	 * Valida que el Estatus de un analisis no sea diferente al esperado
	 * @param cveIdAnalisis
	 * @param cveIdEstatus
	 * @throws EstatusMovimientoException
	 */
	void validaEstatusMovimiento(long cveIdAnalisis, String cveIdEstatus) 
		throws EstatusMovimientoException;
	
	/**
	 * Se obtiene el sujeto obligado y el patron en funcion a la clave del analisis
	 * Agregado JJGV 
	 * @param model
	 * @return
	 * @throws PatronNoEncontradoException 
	 */
	SujetoObligado consultarSujetoObligadoPorAnalisis(AnalisisClasificacionEmpresas model, int tipoPersona) 
			throws PatronNoEncontradoException;
	
	/**
	 * Consulta un analisis
	 * @param model
	 * @return
	 * @throws AnalisisNoEncontradoException
	 */
	AnalisisClasificacionEmpresas consultarAnalisisPorId(AnalisisClasificacionEmpresas model) 
		throws AnalisisNoEncontradoException;	

	/**
	 * Se obtiene el detalle del analisis en funcion a la clave proporcionada.
	 * Agregado JJGV 18/01/2011 
	 * @param model
	 * @return
	 * @throws AnalisisNoEncontradoException
	 */
	AnalisisClasificacionEmpresas obtenerDetalleAnalisis(BigDecimal idSolicitud) 
			throws AnalisisNoEncontradoException ;
	
	List<EstatusAnalisisModel> consultaEstatusAnalisisPorGrupoAnalisis(Long cveIdGrupo) throws Exception;
	
	
	/**
	 * Obtiene lista de documentos adjuntos en FS
	 * @param folio
	 * @return
	 * @throws Exception 
	 */
	
	List<AdjuntosClasificacion> consultarArchivoAdjunto(String folio) throws Exception;

	/**
	 * Crea folio para movimiento 06
	 * @param claveSubdel
	 * @return
	 * @throws Exception 
	 */
	String buildNumeroFolio(String claveSubdel);

	AnalisisClasificacionEmpresas obtenerIdAnalisis(BigDecimal idSolicitud) throws AnalisisNoEncontradoException;
	
	
	
	/**
	 * Pasa el anaisis a estado cancelado por baja del NRP
	 * @param AnalisisClasificacionEmpresas
	 * @return
	 * @throws AnalisisNoEncontradoException
	 * @throws ClasificacionException
	 * @throws EstatusMovimientoException
	 */
	AnalisisClasificacionEmpresas cancelaAnalisisPorBajaNRP(AnalisisClasificacionEmpresas analisis)
			throws AnalisisNoEncontradoException, ClasificacionException,
			EstatusMovimientoException;
	
	
//	/**
//	 * Asigna una solicitud al analista especificado.
//	 * 
//	 * @param model
//	 *            Objeto de tipo Solicitud con los datos a manejar.
//	 * @return Solicitud
//	 * @throws PersistenceException
//	 *             En caso de Error lanza exception.
//	 */ 
//	AnalisisClasificacionEmpresas asignarSolicitudAnalista(AnalisisClasificacionEmpresas model) throws PersistenceException;
	
//	/**
//	 * Se libera una solicitud asignada
//	 * Agregado JJGV 27/01/2011 
//	 * @param model
//	 * @return
//	 * @throws PersistenceException
//	 */
//	AnalisisClasificacionEmpresas liberarAnalisis(AnalisisClasificacionEmpresas model) 
//		throws AnalisisNoEncontradoException;

}
