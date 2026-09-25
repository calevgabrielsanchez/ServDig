/**
*
*
**/
package mx.gob.imss.ctirss.delta.tramite.service.business;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: TramiteServiceBusinessLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.tramite.service.business
 *  @Fecha: 11:18:44
 */
@Local
public interface TramiteServiceBusinessLocal {
	/**
	 * Obtiene todos los trámites disponibles de la tabla DIC_TRAMITE
	 * @return Lista de trámites
	 */
	List<TramiteSolicitud> obtenerTramitesPorTipoPersonaFiscal(/*TipoPersonaFiscal tpf*/);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param tramite
	 * void
	 */
	void crearNuevotramite(TramiteSolicitud tramite, TipoPersonaFiscal tipoPersona);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param idTramite
	 * @param idNuevoEstado
	 * 
	 */
	void actualizarEstadoDelTramite(Long idSolicitud, Long idPersona, Long idNuevoEstado, TipoPersonaFiscal tipoPersona);

	/**
	 * Obtiene los trámites asociadas a un sujeto obligado
	 * @return DatosSalidaPaginador
	 */
	DatosSalidaPaginador<TramiteSolicitud> obtenerTramitesPorSujetoObligado(Long idPatronSujetoObligado);
	
	/**
	 * Obtiene los tramites con estatus activo que están relacionados al sujeto obligado proporcionado y que no fueron creados 
	 * por el usuario firmado, además obtiene los tramites con estatus distinto a cerrado que fueron creados
	 * por el usuario firmado relacionados con el patron proporcionado
	 * @author Hugo Martinez
	 * @Date 20/06/2012
	 * @param idPatronSujetoObligado
	 * @param idUsuario
	 * @return DatosSalidaPaginador<TramiteSolicitud>
	 */
	DatosSalidaPaginador<TramiteSolicitud> obtenerTramitesDeSujetoObligadoPorUsuario(Long idPatronSujetoObligado, Long idUsuario);
	
	/**
	 * Obtiene todos los tramites activos del tipo patron sujetoObligado
	 * @author Hugo Martinez
	 * @Date 21/06/2012
	 * @param cveIdPatronSujetoObligado
	 * @return List<TramiteSolicitud>
	 */
	List<TramiteSolicitud> obtenerTramitesActivosPorSujetoObligado(Long cveIdPatronSujetoObligado);
	
	DatosSalidaPaginador<TramiteSolicitud> consultarTramitesActivos();
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 29/08/2012
	 * @param idModulo
	 * @return
	 */
	List<TipoTramite> listarTipoTramitesPorModulo(Long idModulo);
	
	/**
	 * Metodo que consulta los tramites asociados a una lista de modulos si el modulo es null devuevle
	 * el catalogo de tramites sin filtrar
	 * @param modulos
	 * @return
	 */
	 List<TipoTramite> getTramitesByModulos(List<Modulo> modulos);
}
