/**
*
*
**/
package mx.gob.imss.ctirss.delta.tramite.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TramiteSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicDocumento;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoral;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: TramiteServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.tramite.service.utility
 *  @Fecha: 09:41:14
 */
@Local
public interface TramiteServiceUtilityLocal {
	
	/**
	 * @author Hugo Armando Martínez Chamónica
	 * @param tramite
	 * @return
	 */
	TramiteSolicitud convertirEntityToModel(DitTramite tramite);
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param tramite
	 * @return
	 * DitTramitePersonaFisica
	 */
	DitTramitePersonaFisica convertirModelToEntityTramitePersonaFisica(TramiteSolicitud tramite);
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param tramite
	 * @return
	 * DitTramitePersonaMoral
	 */
	DitTramitePersonaMoral convertirModelToEntityTramitePersonaMoral(TramiteSolicitud tramite);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return
	 * DicEstadoTramite
	 */
	DicEstadoTramite convertirModelToEntityEstadoTramite(EstadoTramite model);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return
	 * DicRazonResultado
	 */
	DicRazonResultado convertirModelToEntityRazonResultado(RazonResultado model);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return DicTipoTramite
	 */
	DicTipoTramite convertirModelToEntityTipoTramite(TipoTramite model);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param persona
	 * @return DitPersona
	 */
	DitPersona convertirEntityToModelPersona(Persona persona);
	
	/**
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return EstadoTramite
	 */
	EstadoTramite convertEntityToModelEstadoTramite(DicEstadoTramite entity);
	
	/**
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return RazonResultado
	 */
	RazonResultado convertirEntityToModelRazonResultado(DicRazonResultado entity);
	
	/**
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return TipoTramite
	 */
	TipoTramite convertirEntityToModelTipoTramite(DicTipoTramite entity);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param entity
	 * @return
	 */
	Documento convertirEntityToModelDocumento(DicDocumento entity);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 30/07/2012
	 * @param entities
	 * @return
	 */
	List<Documento> convertirEntitiesToModel(List<DicDocumento> entities);
}
