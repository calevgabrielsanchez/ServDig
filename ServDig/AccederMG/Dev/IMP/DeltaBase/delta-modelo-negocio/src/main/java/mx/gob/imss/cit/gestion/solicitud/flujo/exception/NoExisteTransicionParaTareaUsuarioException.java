package mx.gob.imss.cit.gestion.solicitud.flujo.exception;

import javax.ejb.ApplicationException;

/**
 * Clase para el manejor de la no existencia de transicion para la tarea del usuario
 * @author softtek
 *
 */
@ApplicationException(rollback = true)
public class NoExisteTransicionParaTareaUsuarioException extends BPMException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 4982869063681569421L;

    public NoExisteTransicionParaTareaUsuarioException() {
	}

	public NoExisteTransicionParaTareaUsuarioException(String mensaje) {
		super(mensaje);
	}

	public NoExisteTransicionParaTareaUsuarioException(String message, String situacion) {
		super(message);
		this.setSituacion(situacion);
	}

}
