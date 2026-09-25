/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:SolicitudPersonaEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.gestionpersonas.servicios.entity
 *  @Fecha:22/02/2012
 */
package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;

/**
 * @author Lucio Duran Silva
 *
 */
@Local
public interface SolicitudPersonaEntityLocal { 

	Solicitud alta(Solicitud solicitud);
		
	Solicitud modificar(Solicitud solicitud);
	
	Solicitud getSolicitud(Long idSolicitud) throws SolicitudNoEncontradaException;
	
	List<Solicitud> getSolicitudesRegistradas();	
	
	List<Solicitud> getSolicitudesVencidas();
	
	List<String> consultarNombresPatrones(List<String> registroPatronal);
	
	List<String> consultarNombrePatronPorRPYModalidad(String registroPatronal, String modalidad);
	
}
