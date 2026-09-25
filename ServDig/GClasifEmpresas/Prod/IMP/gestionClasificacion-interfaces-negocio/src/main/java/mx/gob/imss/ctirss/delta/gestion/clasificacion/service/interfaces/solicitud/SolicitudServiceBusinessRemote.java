/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:SolicitudServiceBusinessRemote.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud
 *  @Fecha:30/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud;

import javax.ejb.Remote;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.model.clasificacion.ConfiguracionCe;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosAnalisisConsulta;
import mx.gob.imss.ctirss.delta.model.clasificacion.SolicitudConcluida;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Remote
public interface SolicitudServiceBusinessRemote {
	
	/**
	 * Obtiene el detalle de la solicitud especï¿½fica.
	 * @param Sujeto Obligado con datos a buscar.
	 * @return Objeto SujetoObligado.
	 * @throws Exception En caso de error.
	 */
	SujetoObligado obtenerDetalleSolicitud(SujetoObligado so) throws Exception;	
	
	/**
	 * Obtiene el detalle de la solicitud espec�fica.
	 * @param Sujeto Obligado con datos a buscar.
	 * @return Objeto SujetoObligado.
	 * @throws Exception En caso de error.
	 */
	SujetoObligado obtenerDetalleSolicitudDictamen(SujetoObligado so) throws Exception;

	/**
	 * Metodo de soporte al paginado de las solicitudes concluidas.
	 * @param parametrosPaginador
	 * @return
	 */
	DatosSalidaPaginador<SolicitudConcluida> consultarSolicitudesConcluidas(DatosEntradaPaginador<FiltrosAnalisisConsulta> parametrosPaginador);

	/**
	 * Obtiene la relación entre roles y operaciones con el fin de mostrar los
	 * botones correspondientes en el detalle de la solicitud.
	 * @param cveIdAnalisis
	 * @param cveIdUsuario
	 * @param cveIdRol
	 * @return ConfiguracionCe
	 * @throws Exception
	 */
	ConfiguracionCe obtenerConfiguracionCe(final Long cveIdAnalisis, final String cveIdUsuario, final Integer cveIdRol) throws Exception;
	
	/**
	 * Cambia el Estatus a CANCELADO de los Análisis encontrados a partir del
	 * Registro Patronal
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
	String obtenerRegistroPatronalCompleto(String regPatron);
	
	Long crearSolicitudDictamen(SujetoObligado sujeto, DictamenDTO dictamen);
	
	Long crearVistaDictamen(SujetoObligado sujeto, DictamenDTO dictamen);
	
	void actualizaEstatusInconsistencia(Long cveIdAnalisis) throws Exception;
}