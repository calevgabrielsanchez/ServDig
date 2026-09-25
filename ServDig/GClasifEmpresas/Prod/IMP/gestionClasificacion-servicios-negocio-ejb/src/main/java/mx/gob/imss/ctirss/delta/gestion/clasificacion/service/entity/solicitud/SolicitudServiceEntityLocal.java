/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: SolicitudServiceEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosAnalisisConsulta;
import mx.gob.imss.ctirss.delta.model.clasificacion.SolicitudConcluida;

@Local
public interface SolicitudServiceEntityLocal{
	
	/**
	 * 
	 * @param parametrosPaginador
	 * @return DatosSalidaPaginador<SolicitudConcluida>
	 */
	DatosSalidaPaginador<SolicitudConcluida> consultarSolicitudesConcluidas(
			DatosEntradaPaginador<FiltrosAnalisisConsulta> parametrosPaginador);
		
	/**
	 * Consulta lista de Análisis a partir de un Registro Patronal en
	 * DivSolicitudConcluida Posteriormente cancela los Análisis relacionados a
	 * partir de un Registro Patronal seleccionado anteriormente
	 * 
	 * @param regPatronal
	 * @param estadoCancelacion
	 * @param solicitud
	 * @throws ClasificacionException 
	 * @throws PersistenceException 
	 */
	void cancelarAnalisisPorRegistroPatronal(
			final String regPatronal,
			final int estadoCancelacion,
			final mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) throws PersistenceException, ClasificacionException;
	
	void crearAnalisisPorRegistroPatronalDictamen(
			final String regPatronal,
			final mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) throws PersistenceException, ClasificacionException;
	
	/**
	 * Verifica si la solicitud tiene varias clasificaciones
	 * @param cveIdSolicitud
	 * @return boolean
	 */
	boolean consultaReintentoRPC(Long cveIdSolicitud);
	
	/**
	 * Obtiene Registro Patronal Completo,
	 * el orden a enviar es: RP, Modalidad y Dígito Verificador
	 */
	String obtenerRegistroPatronalCompleto(String... regPatron);
}