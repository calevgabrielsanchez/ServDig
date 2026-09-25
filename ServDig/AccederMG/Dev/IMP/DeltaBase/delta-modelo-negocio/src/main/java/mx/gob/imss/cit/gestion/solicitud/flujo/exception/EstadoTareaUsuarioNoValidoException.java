package mx.gob.imss.cit.gestion.solicitud.flujo.exception;

import javax.ejb.ApplicationException;

/**
 * Clase para el manejo de excepciones de estados no validos de las tareas de usuario
 * @author softtek
 *
 */
@ApplicationException(rollback = true)
public class EstadoTareaUsuarioNoValidoException extends BPMException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 4982869063681569421L;

    public EstadoTareaUsuarioNoValidoException() {
	}

	public EstadoTareaUsuarioNoValidoException(String mensaje) {
		super(mensaje);
	}

	public EstadoTareaUsuarioNoValidoException(String message, String situacion) {
		super(message);
		this.setSituacion(situacion);
	}

}
