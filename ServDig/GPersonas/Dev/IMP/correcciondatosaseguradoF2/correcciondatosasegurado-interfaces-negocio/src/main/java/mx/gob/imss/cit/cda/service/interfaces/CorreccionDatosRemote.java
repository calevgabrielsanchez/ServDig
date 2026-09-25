package mx.gob.imss.cit.cda.service.interfaces;


import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CorreccionDatos;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ResumenCorrecion;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import java.util.Map;

@Remote
public interface CorreccionDatosRemote {

	/**
	 * Obtener fuentes de informacion de los nss incolucrados en un solicitud
	 * 
	 * @param solicitud
	 * @return CorreccionDatos
	 */
	CorreccionDatos obtenerFuentesDatos(Solicitud solicitud);

	/**
	 * Actualiza el xml de las aclaraciones de una solicitud
	 * 
	 * @param correccion
	 * @return
	 * @throws IllegalArgumentException
	 * @throws TramiteNoEncontradoException
	 */
	boolean guardarXmlCorreccionDatos(CorreccionDatos correccion)
			throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException;

	
	
	

	/**
	 * Metodo que obtiene el detalle con las diferencias, para cada correcion
	 * por nss clasificado
	 * 
	 * @param correccionDatos
	 * @return ResumenCorrecion
	 */

	ResumenCorrecion armarDetalleCoreccion(CorreccionDatos correccionDatos);

	/**
	 * Guardar correcciones de XML a tablas de una solicitud
	 * 
	 * @param folioSolicitud
	 * @param usuario
	 * @return
	 * @throws TereaSinUsuarioAsignadoException
	 * @throws NoExisteTransicionParaTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 * @throws NoExisteTareaUsuarioException
	 * @throws TramiteNoEncontradoException 
	 */
	 boolean guardarCorreccionDatos(String folioSolicitud, String usuario)
			throws SolicitudNoEncontradaException,
			NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException,
			TereaSinUsuarioAsignadoException, TramiteNoEncontradoException ;
	
	
	/**
	 * Metodo que avanza la tarea de una solicitud y cambia el estado del tramite
	 *
	 * @param folio
	 * @throws NoExisteTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 * @throws NoExisteTransicionParaTareaUsuarioException
	 * @throws TereaSinUsuarioAsignadoException
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	 String avanzarTareaTramite(String folio, String usuario )
			throws NoExisteTareaUsuarioException,
			EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException,
			TereaSinUsuarioAsignadoException, SolicitudNoEncontradaException,
			TramiteNoEncontradoException;
		
	boolean validarBlanqueamientoCURP(Solicitud solicitud)
			throws PersonasNoLocalizadasException,
			NssRelacionadoVariasPersonasException;

	boolean validarTipoMovimientos(String folio);

	void validarSeparacionPersonas(String usuario, Solicitud solicitud);

	void registrarNuevaPersona(Long idtramite);

	boolean validarCambioAOperada(Solicitud solicitud, String usuario, Map<String, String> tramitesTareas);
}
