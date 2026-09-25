package mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces;



import javax.ejb.Remote;

import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TareaYaAsignadaAlUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;


/**
 * Se crea nuevo flujo de trabajo para CDA
 * 
 * @author Alex Peña Softtek
 * 
 */

@Remote
public interface FlujoTrabajoCDARemote  {

	
	/**
	 * Metodo para obtener id de la tarea del responsable actual
	 * 
	 * @param idInstancia
	 * @param Curp
	 * @return cveTareaUsuario
	 * @throws NoExisteTareaUsuarioException
	 */
	Long obtenerCveTareaUsuarioCDA(Long idInstancia, String Curp)
			throws NoExisteTareaUsuarioException;
	
	/**
	 * Metodo para obtener id de la instancia
	 * 
	 * @param idTramite
	 * @return cveIdInstancia
	 * 
	 */
	Long obtenerCveInstancia(Long idTramite);
	
	
	void reasignarTareaCDA(final String usuario, final Long idTarea, MensajeTarea mensajeTarea) throws NoExisteTareaUsuarioException,
	EstadoTareaUsuarioNoValidoException, TareaYaAsignadaAlUsuarioException;
}
