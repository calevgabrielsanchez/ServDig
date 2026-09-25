/**
*
*
**/
package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicRazonCancelacion;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitUsuario;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: SolicitudUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.solicitud
 *  @Fecha: 10:35:11
 */
@Local
public interface SolicitudUtilityLocal {
	
	/**
	 * Popula la información detallada de la solicitud en el
	 * objeto de modelo.
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity 
	 * @return Solicitud
	 */
	Solicitud convertEntityToModelSolicitud(DitSolicitud entity);
	
	/**
	 * Popula la información de la entidad DIT_ESTADO_SOLICITUD 
	 * en el objeto de modelo Estado Solicitud
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return EstadoSolicitud
	 */
	EstadoSolicitud convertEntityToModelEstadoSolicitud(DicEstadoSolicitud entity );
	
	/**
	 * Popula la información de la entidad DIT_RAZON_CANCELACION
	 * en el objeto de modelo RazonCancelacion
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * RazonCancelacion
	 */
	RazonCancelacion convertirEntityToModelRazonCancelacion(DicRazonCancelacion entity);
	
	/**
	 * Popula la información de la entidad DIT_TIPO_SOLICITUD
	 * en el objeto de modelo TipoSolicitud
	 * @author Hugo Armando Martínez Chamónica
	 * @param tipoSolicitud
	 * @return
	 * TipoSolicitud
	 */
	TipoSolicitud convertirEntityToModelTipoSolicitud(DicTipoSolicitud tipoSolicitud);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * Tramite
	 */
	TramiteSolicitud convertirEntityToModelTramite(DitTramitePersonaFisica entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * Tramite
	 */
	TramiteSolicitud convertirEntityToModelTramite(DitTramitePersonaMoral entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * TipoTramite
	 */
	TipoTramite convertirEntityToModelTipoTramite(DicTipoTramite entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param estadoTramite
	 * @return
	 * EstadoTramite
	 */
	EstadoTramite convertirEntityToModelEstadoTramite(DicEstadoTramite estadoTramite);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * Persona
	 */
	Persona convertirEntityToModelPersonaTramite(DitPersona entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * RazonResultado
	 */
	RazonResultado convertirEntityToModelRazonResultado(DicRazonResultado entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return DitSolicitud
	 */
	DitSolicitud convertirModelToEntitySolicitud(Solicitud model);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return DitSolicitud
	 */
	DicTipoSolicitud convertirModelToEntityTipoSolicitud(TipoSolicitud model);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return DitSolicitud
	 */
	DicEstadoSolicitud convertirModelToEntityEstadoSolicitud(EstadoSolicitud model);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return DicRazonCancelacion
	 */
	DicRazonCancelacion convertirModelToEntityRazonCancelacion(RazonCancelacion model);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return DitUsuario
	 */
	DitUsuario convertirModelToEntityUsuario(Usuario model);
	
	/**
	 * Obtiene la información requerida del rfc o registro patronal
	 * asociado a la solicitud
	 * @author Hugo Martinez
	 * @Date 31/08/2012
	 * @return SujetoObligado
	 */
	SujetoObligado convertirEntityToModelSujetoObligadoSolicitud(DitSolicitud entity);
}
