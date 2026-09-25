/**
 * 
 */
package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.CorreccionDatosAseguradoException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

/**
 * @author STK
 *
 */
@Remote
public interface RegistroSolicitudCorreccionDatosAseguradoRemote {

    /**
     * Obtiene las solicitudes asociadas a un CURP.
     * @param curp, del asegurado.
     * @return Solicitudes, que est&aacute;n asociadas a la curp proporcionada.
     */
    List<Solicitud> obtenerSolicitudesPorCurp(String curp) throws CorreccionDatosAseguradoException;
        
    Solicitud obtenerUltimaSolicitudRegistradaPorCurp(List<String> curps, List<Integer> estados);
    
    Solicitud crearTramiteCorreccionCurp(Fisica solicitante, OrigenSolicitudEnum origenSolicitud, Usuario usuario) throws SolicitudNoValidaException, SolicitudNoEncontradaException, TramiteNoEncontradoException;
 
    Solicitud cancelarSolicitud(Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException;
    
	FirmaElectronica selloDigital(Fisica personaCorrecion, Solicitud solicitud) throws CorreccionDatosAseguradoException;
    
    Solicitud obtenerSolicitudPorEstado(Long idPersona, EstadoSolicitudEnum estadoSolicitud) throws SolicitudException;
    
	Fisica validarAsociacionCURPCorreo(Fisica fisica) throws CorreccionDatosAseguradoException;

    void guardarSolicitudActualizacionDatos(Solicitud solicitud, TramiteCorreccionCurp tramite,List<DocumentoProbatorio> documentos,  boolean documentosProbatorios, Long origen) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, RegistrarDocumentoProbatorioException, Exception;
    
    /**
	 * Metodo encargado de realizar las acciones correspondientes al finalizar el regisro de una solicitud
	 * asocia la solicitud a un responsable y cambia el estado de la solcitud en proceso
	 * @param solicitud
         * @param listaInicioTramite
         * @param usuario
         * @param origen
	 * @throws CorreccionDatosAseguradoException
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
    void finalizaRegistroCorreccionDatosAsegurados(Solicitud solicitud, List<InicioTramite> listaInicioTramite, String usuario, Long origen) throws Exception;
    /**
     * Metodo encargado de ralizar las acciones correspondientes al finalisar el tr�mite de correccion de datos asegurado
     * envia el movimiento a sindo, actualiza los datos estadisticos del asegurado
     * finaliza la solicitud e impacta los datos del tramite
     * @param tramiteActualizacion
     * @return
     * @throws IllegalArgumentException
     * @throws SolicitudNoValidaException
     * @throws SolicitudNoEncontradaException
     * @throws TramiteNoEncontradoException
     * @throws Exception
     */

	Solicitud finalizaTramiteCorreccionDatosBasicosAsegurado(Solicitud solicitudCorreccion,String idTarea, String usuario)
			throws IllegalArgumentException, SolicitudNoValidaException, SolicitudNoEncontradaException,
			TramiteNoEncontradoException, Exception;
	
	
	
	/**
     * Metodo encargado de buscar las solicitudes iniciadas, en proceso y atendidas de CDA
     * @param curp
     * @param origen
     * @return la ultima solicitud de la persona en alguno de los estados
     */
	Solicitud obtenerUltimaSolicitudSeguimientoCDA(List<String> curps,String origen);
	
	/**
	 * M&eacute;todo encargado de validar que la persona a la que pertenece la CURP cuenta 
	 * &uacute;nicamente con la caracter&iacute;stica de Asegurado y/o beneficiario  
	 * @param curp
	 * @return
	 */
	Map<String, Object> personaAutorizadaRegistroCDA(String curp,List<String>curpsHitoricos);
	/**
	 * M&eacute;todo encargado de enviar la solicitud a SINDO
	 * @param Solicitud
	 * @param idTarea
	 * @param usuario
	 * @return 
	 */
	void enviaCertificacionSINDO(Solicitud solicitud, String idTarea,String usuario) throws IllegalArgumentException,
	SolicitudNoValidaException, SolicitudNoEncontradaException, TramiteNoEncontradoException, Exception;
	
	/**
	 * M&eacute;todo para generar la firma electronica
	 * @param Fisica, Solicitud
	 * @return 
	 */
	FirmaElectronica selloDigitalCertificacion(Fisica personaCorrecion, Solicitud solicitud, Set<List<PeriodoMovimientoAfiliatorio>> parametrosCuentas) throws CorreccionDatosAseguradoException;
	
	/**
	 * M&eacute;todo para consultar el estado de un tr&aacute;mite a trav&eacute;s de su identificador.
	 * @param idTramite
	 * @return
	 */
	EstadoTramite consultarEstadoTramiteById(Long idTramite);
	
	/**
	 * M&eacute;todo para consultar si un NSS se encuentra bloqueado.
	 * @param nss que se requiere consultar.
	 * @return
	 */
	boolean isNSSBloqueado(String nss);
	
	/**
	 * M&eacute;todo para agregar el bloqueo de un NSS.
	 * @param nss que se requiere bloquear.
	 * @param IdTramite identificador del tr&aacute;mite al cual se asociar&aacute; el nss para su bloqueo.
	 * @throws CorreccionDatosAseguradoException en caso de ocurrir alg&uacute;n error al bloquear el NSS.
	 */
	void bloquearNSS(String nss, Long IdTramite)throws CorreccionDatosAseguradoException;
	
	/**
	 * M&eacute;todo para eliminar (baja lógica) el bloqueo de un NSS.
	 * @param nss que se requiere desbloquear.
	 * @param idTramite identificador del tr&aacute;mite al cual esta asociado el nss. 
	 * @throws CorreccionDatosAseguradoException en caso de ocurrir alg&uacute;n problema al eliminar el bloqueo del NSS.
	 */
	void desbloquearNSS(String nss, Long idTramite)throws CorreccionDatosAseguradoException;
	
	/**
	 * M&eacute;todo para agregar una observacion de la subdelegacion
	 * @param idTramite a la que se quiere agregar la subdelegacion  
	 * @param usuario que realiza la accion
	 * @param idTarea tarea a actualizar
	 * @throws IllegalArgumentException 
	 * @throws TramiteNoEncontradoException 
	 * @throws SolicitudNoEncontradaException 
	 * @throws CorreccionDatosAseguradoException en caso de ocurrir alg&uacute;n problema al eliminar el bloqueo del NSS.
	 */
	void agregarObservacionesSubdelegacion(Long idTramite,String usuario, String idTarea) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, IllegalArgumentException ;

	void actualizarSubdelegacionSolicitud(Solicitud solicitud);

}

