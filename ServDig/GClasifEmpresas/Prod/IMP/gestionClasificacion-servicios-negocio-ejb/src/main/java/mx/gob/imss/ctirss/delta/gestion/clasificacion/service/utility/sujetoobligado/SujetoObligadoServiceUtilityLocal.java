/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: SujetoObligadoServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

@Local
public interface SujetoObligadoServiceUtilityLocal {
	
	
	SujetoObligado convertEntityToModel(DitPatronSujetoObligado entity,
			int idTipoPersona) throws IllegalAccessException,
			InvocationTargetException;
	
	
	TramiteSujetoObligado obtenerTramiteSujetoObligado(List<Tramite> tramites, Long tipoSolicitud);
	
	Tramite obtenerTramite(List<Tramite> tramites, Long tipoSolicitud);
	
}
