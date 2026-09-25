/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: TramiteServiceEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.tramite
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.tramite;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Local
public interface TramiteServiceEntityLocal {
	List<Tramite> consultaTramitePorSolicitud(Long cveIdSolicitud)throws Exception;
	
	List<TipoTramite> consultaTipoTramitePorModulo(Long cveIdModulo)throws Exception;
	
	List<TipoTramite> consultaTipoTramitePorGrupoAnalisis(Long cveIdGrupo) throws Exception;
	
	Long consultaGrupoAnalisisPorTipoTramite(Long cveIdTipoTramite);
}
