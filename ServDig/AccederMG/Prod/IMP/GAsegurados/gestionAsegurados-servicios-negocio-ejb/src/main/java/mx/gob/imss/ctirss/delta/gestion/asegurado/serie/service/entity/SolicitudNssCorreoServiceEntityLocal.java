package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssConfirmacion;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;

@Local
public interface SolicitudNssCorreoServiceEntityLocal {

	void actualizarCurpACorreo(String correo, String curp);
	
	void actualizarBajaCorreoPorCurp(String curp);
	
	void actualizarAltaCorreo(String correo);
	
	void bajaCorreoExcepto(String curp, String correo);
	
	List<String> consultarUMF (ArrayList<Integer> numerosGenerados);
	
	Map<String, String> consultarDatosUsuario(String curp);
	
	List<String> getListaRfcPatronesFisica (List<Long> ids);
	
	List<String> getListaRfcPatronesMoral (List<Long> ids);
	
	List<String> getListaRegistroPatronal (List<Long> ids);
	
	/**
	 * Metodo que obtiene el registro del correo-curp a traves de la llave
	 * primaria, es decir, del correo electronico
	 * 
	 * @param correo
	 * @return
	 */
	SolicitudNssCorreo obtenerPorCorreo(String correo, Long idSolicitud);

	/**
	 *
	 * Metodo para obtener las veces solicitadas al dia por CURP
	 * @param curp
	 * @return
	 */
	Object [] obtenerSolicitudPorCurpPeriodo (String curp, int diasPeriodo);

	/**
	 *
	 * M�todo para obtener las veces solicitadas al dia por CURP y tipo de solicitud
	 * @param curp
	 * @param diasPeriodo
	 * @param idTipoSolicitud
	 * @return
	 */
	Object[] obtenerSolicitudPorCurpPeriodo(String curp, int diasPeriodo, Long idTipoSolicitud);

	/**
	 * Metodo que guarda el registro correo-curp
	 * 
	 * @param model
	 * @throws SolicitudNssCorreoException
	 */
	void guardar(SolicitudNssCorreo model) throws SolicitudNssCorreoException;

	/**
	 * Metodo que registra la consulta del Correo-CURP
	 * 
	 * @param correo
	 */
	void registrarConsultaCorreoNSS(String correo, Long idSolicitud);

	/**
	 * Metod que reincia el contador de consultas por correo electronico y CURP
	 * 
	 * @param correo
	 */
	void reiniciarConsultaCorreoNSS(String correo, Long idSolicitud);


	void guardarConfirmacion(SolicitudNssConfirmacion model) throws SolicitudNssCorreoException;

	void actualizarConfirmacionCorreo(SolicitudNssConfirmacion model);

	SolicitudNssConfirmacion buscarConfirmacionCorreo(String curp, String correo, Long tipoSolicitud) throws TransformacionException;

	void actualizarConfirmacionCorreoVigencia(SolicitudNssConfirmacion model);
	
	SolicitudNssCorreo obtenerPorCurp(String curp, Long idSolicitud);

	String obtieneMedioContactoCorreo(String idPersona);
	
	/**
	 * Metodo que obtiene las asociaciones de curp a traves de la llave
	 * del correo electronico
	 * 
	 * @param correo
	 * @return
	 */
	List<SolicitudNssCorreo> obtenerPorCorreo(String correo);
	
	/**
	 * Metodo que obtiene las asociaciones de correo electronico
	 * a traves de la curp de un asegurado
	 * 
	 * @param correo
	 * @return
	 */
	List<SolicitudNssCorreo> obtenerPorCurp(String curp);
	
	List<SolicitudNssCorreo> obtenerPorCurpCorreosActivos(String curp);

}
