/**
*
*
**/
package mx.gob.imss.ctirss.delta.tramite.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: TramiteServiceEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.tramite.service.entity
 *  @Fecha: 17:58:12
 */
@Local
public interface TramiteServiceEntityLocal {
	List<TramiteSolicitud> consultarTramites();
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param tramite
	 * @param tipoPersona
	 * void
	 */
	void insertarTramite(TramiteSolicitud tramite, TipoPersonaFiscal tipoPersona);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param idTramite
	 * @param idNuevoEstado
	 * void
	 */
	void actualizarEstadoTramite(Long idSolicitud, Long idPersona, Long idNuevoEstado, TipoPersonaFiscal tipPersona);
	
	/**
	 * Obtiene la lista completa de trámites asociados al patrón sujeto obligado
	 * @param idPatronSujetoObligado
	 * @return List
	 */
	List<TramiteSolicitud> consultarTramitesPorSujetoObligado(Long idPatronSujetoObligado);
	
	/**
	 * @author Hugo Martinez
	 * @param cveIdTramite
	 * @param cveIdPatronSujetoObligado
	 */
	void asociarTramite(Long cveIdTramite, Long cveIdPatronSujetoObligado);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 14/06/2012
	 * @param tipo
	 * @param cveIdPatronSujetoObligado
	 * @return
	 */
	boolean existeTramiteActivoPorSujetoObligadoYTipo(TipoTramiteEnum tipo, Long cveIdPatronSujetoObligado);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 19/06/2012
	 * @param tipos
	 * @param cveIdPatronSujetoObligado
	 * @return
	 */
	boolean existeTramitesActivoPorSujetoObligadoYTipos(
			List<TipoTramiteEnum> tipos, Long cveIdPatronSujetoObligado, List<Long> estadosInvalidos);
	
	
	/**
	 * Obtiene todos los trámites activos del sujeto obligado
	 * @author Hugo Martinez
	 * @Date 20/06/2012
	 * @param cveIdSujetoObligado
	 * @return List<Tramite>
	 */
	List<TramiteSolicitud> consultarTramitesActivosPorSujetoObligado(Long cveIdSujetoObligado);
	
	/**
	 * Obtiene todos los trámites activos
	 * @return
	 */
	List<TramiteSolicitud> consultarTramitesActivos();
	
	/**
	 * Obtiene todos los trámites del patron creados por el usuario proporcionado con estatus distinto a cerrado
	 * 
	 * @author Hugo Martinez
	 * @Date 20/06/2012
	 * @param cveIdSujetoObligado
	 * @param idUsuario
	 * @return
	 */
	List<TramiteSolicitud> consultarTramitesDeSujetoObligadoEnCursoCreadosPorUsuario(Long cveIdSujetoObligado, Long idUsuario);
	
	/**
	 * Obtiene todos los trámites Activos del patron.
	 * 
	 * @author Hugo Martinez
	 * @Date 20/06/2012
	 * @param cveIdSujetoObligado
	 * @return
	 */
	List<TramiteSolicitud> consultarTramitesActivosDeSujetoObligado(Long cveIdSujetoObligado);
	
	/**
	 * Obtiene la información del tipo de tramite con el detalle de pasos a
	 * seguir y la documentación a entregar para finalizar el trámite.
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param id Identificador del tipo de tramite
	 * @return TipoTramite
	 */
	TipoTramite consultarTipoTramite(Integer id);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 29/08/2012
	 * @param idModulo
	 * @return
	 */
	List<TipoTramite> listarTipoTramitesPorModulo(Long idModulo);
	
	/**
	 * 
	 */
	List<DocumentoPorTipo> getDocumentosResultantesPorTipoTramite(Long idTipoTramite);
	
	void actualizarDocumentosTramite(Long idTramite, Long idDocumentoTipo, Object bytes);
	
	Object getDocumentoPorTipoIdTramite(Long idTramite, Long idDocumentoPorTipo);
	
	
	/**
	 * Metodo que consulta los tramites asociados a una lista de modulos si el modulo es null devuevle
	 * el catalogo de tramites sin filtrar
	 * @param modulos
	 * @return
	 */
	List<TipoTramite> getTramitesByModulos(List<Modulo> modulos);
	
	
}
