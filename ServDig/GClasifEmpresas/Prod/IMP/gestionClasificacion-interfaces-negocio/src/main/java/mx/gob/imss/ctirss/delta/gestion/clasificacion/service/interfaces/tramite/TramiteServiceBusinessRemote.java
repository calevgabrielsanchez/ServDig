/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:TramiteServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.tramite
 *  @Fecha:30/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.tramite;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

@Remote
public interface TramiteServiceBusinessRemote {
	
	/**
	 * Obtiene Tramite a partir de la clave de Solicitud
	 * @param cveIdSolicitud
	 * @return
	 * @throws Exception
	 */
	List<Tramite> consultaTramitePorSolicitud(Long cveIdSolicitud) throws Exception;
	
	/**
	 * Obtiene Tipo de Tramite a partir de la clave de modulo
	 * @param cveIdModulo
	 * @return
	 * @throws Exception
	 */
	List<TipoTramite> consultaTipoTramitePorModulo(Long cveIdModulo) throws Exception;
	
	/**
	 * Obtiene Tipo de Tramite a partir de la clave de grupo
	 * @param cveIdGrupo
	 * @return
	 * @throws Exception
	 */
	List<TipoTramite> consultaTipoTramitePorGrupoAnalisis(Long cveIdGrupo) throws Exception;
	
	
	TramiteSujetoObligado obtenerTramiteSujetoObligado(List<Tramite> tramites, Long tipoSolicitud);
	
	Tramite obtenerTramite(List<Tramite> tramites, Long tipoSolicitud);
}
