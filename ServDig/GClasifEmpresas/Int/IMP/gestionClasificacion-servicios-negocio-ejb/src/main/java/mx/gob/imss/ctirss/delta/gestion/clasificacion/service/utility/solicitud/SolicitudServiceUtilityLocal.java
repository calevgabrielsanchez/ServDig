/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: SolicitudServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.solicitud
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.solicitud;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.SolicitudConcluida;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Solicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DivSolicitudConcluida;

/**
 * @author Jaime Ramirez N.
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 02/01/2012
 */
@Local
public interface SolicitudServiceUtilityLocal {
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	Solicitud convertirEntityToModel(DitSolicitud entity) throws Exception;
		
	/**
	 * Convierte una lista de objetos Entity DivSolicitudConcluida a una lista de objetos modelo SolicitudConcluida.
	 * @param origen Lista de DivSolicitudConcluida
	 * @return solicitudes Lista de SolicitudConcluida
	 */
	List<SolicitudConcluida> convertirListOfVsolicitudconcluidasToSolicitudConcluida(List<DivSolicitudConcluida> origen);
		
	/**
	 * Convierte un bean entity Vsolicitudconcluidas a un bean model SolicitudConcluida
	 * @param v
	 * @return
	 */
	SolicitudConcluida convertirVsolicitudconcluidaToSolicitudConcluida(DivSolicitudConcluida v);
	
}
