package mx.gob.imss.cit.gestion.solicitud.flujo.exception;

import javax.ejb.ApplicationException;

/**
 * Clase para manejar la excepcion de tarea de usuario no existente
 * @author softtek
 *
 */
@ApplicationException(rollback = true)
public class NoExisteTareaUsuarioException extends BPMException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 4982869063681569421L;

    public NoExisteTareaUsuarioException() {
	}

	public NoExisteTareaUsuarioException(String mensaje) {
		super(mensaje);
	}

	public NoExisteTareaUsuarioException(String message, String situacion) {
		super(message);
		this.setSituacion(situacion);
	}

}
