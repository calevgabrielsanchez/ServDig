package mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssConfirmacion;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteActualizacionCorreo;

@Remote
public interface SolicitudNssCorreoServiceBusinessRemote {

	void actualizarCurpACorreo(String correo, String curp) throws SolicitudNssCorreoException;
	
	void actualizarBajaCorreoPorCurp(String curp) throws SolicitudNssCorreoException;
	
	void actualizarPorCodigo(SolicitudNssCorreo nssCorreo, int codigoActualizacion)throws SolicitudNssCorreoException;
	
	List<String> consultarUMF (ArrayList<Integer> numerosGenerados) throws SolicitudNssCorreoException;

	String consultarSubdelegacion (Integer cveIdSubdelegacion) throws SolicitudNssCorreoException;

	Map<String, String> consultarDatosUsuario(String curp) throws SolicitudNssCorreoException;
	
	List<String> getListaRfcPatronesFisica(List<Long> ids) throws SolicitudNssCorreoException;
	
	List<String> getListaRfcPatronesMoral(List<Long> ids) throws SolicitudNssCorreoException;
	
	List<String> getListaRegistroPatronal(List<Long> ids) throws SolicitudNssCorreoException;
	
	/**
	 * Servicio que obtiene el registro del correo-curp a traves de la llave
	 * primaria, es decir, del correo electronico
	 * 
	 * @param correo
	 * @return
	 */
	SolicitudNssCorreo obtenerPorCorreo(String correo, Long idSolicitud);

	/**
	 * Servicio que guarda el registro correo-curp
	 * 
	 * @param model
	 * @throws SolicitudNssCorreoException
	 */
	void guardar(SolicitudNssCorreo nssCorreo)
			throws SolicitudNssCorreoException;

	/**
	 * Servicio que registra la consulta/generacion de un NSS a traves del
	 * correo recibido
	 * 
	 * @param correo
	 */
	void registrarConsultaCorreoNSS(String correo, Long idSolicitud);

	/**
	 * Servicio para reiniciar el contador del correo, el contador queda en un
	 * valor inicial de 1
	 * 
	 * @param correo
	 */
	void reiniciarConsultaCorreoNSS(String correo, Long idSolicitud);

	/**
	 * Servicio que valida si la consulta o registro del NSS es valida, se basa
	 * en el correo y curp recibidos y las consultas permitidas por periodo.
	 * Devuelve el numero de operacion a realizar en caso de exito: <br>
	 * 1 = guardar registro nuevo <br>
	 * 2 = reinicio de contador <br>
	 * 3 = registro de consulta
	 * 
	 * @param nssCorreo
	 * @return codigo_operacion
	 * @throws SolicitudNssCorreoException
	 */
	int validaCorreosRegistrados(SolicitudNssCorreo nssCorreo) throws SolicitudNssCorreoException;
	
	int isConsultaRegistroNSSValid(SolicitudNssCorreo nssCorreo,
			boolean validaContadores) throws SolicitudNssCorreoException;

	void guardarConfirmacion(String correo, String curp, String token) throws SolicitudNssCorreoException;

	void guardarConfirmacion(String correo, String curp, String token, Long tipoSolicitud) throws SolicitudNssCorreoException;

	SolicitudNssConfirmacion recuperarConfirmacion(String curp, Long tipoSolicitud) throws TransformacionException;

	SolicitudNssConfirmacion recuperarConfirmacion(String curp, String correo, Long tipoSolicitud) throws TransformacionException;

	void actualizaConfirmacion(String correo, String curp, String token);

	void actualizaConfirmacion(String correo, String curp, String token, Long tipoSolicitud);

	void actualizaConfirmacionVigencia(SolicitudNssConfirmacion model);

	SolicitudNssCorreo obtenerPorCurp(String curp, Long idSolicitud);

	String obtieneMedioContactoCorreoPersona(String idPersona);
	
	/**
	 * Servicio que obtiene las curp asociadas a traves de la llave
	 * del correo electronico
	 * 
	 * @param correo
	 * @return
	 */
	List<SolicitudNssCorreo> obtenerPorCorreo(String correo);

	/**
	 * Servicio que obtiene los correos electronicos asociados a traves 
	 * de la curp del asegurado
	 * 
	 * @param correo
	 * @return
	 */
	List<SolicitudNssCorreo> obtenerPorCurp(String curp);
	
	List<SolicitudNssCorreo> obtenerPorCurpCorreosActivos(String curp);

	boolean esDominioPermitido(String correo);
	
	boolean esIgualAlAnterior(String correo, String curp);
	
	String getToken() throws SolicitudNssCorreoException;
	
	void saveDitActualizacionCorreo(Long tramiteId,TramiteActualizacionCorreo xml) throws SolicitudNssCorreoException;
}
