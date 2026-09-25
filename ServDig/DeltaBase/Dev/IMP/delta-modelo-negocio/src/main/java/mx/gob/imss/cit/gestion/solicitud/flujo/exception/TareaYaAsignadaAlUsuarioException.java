package mx.gob.imss.cit.gestion.solicitud.flujo.exception;

import javax.ejb.ApplicationException;

/**
 * Clase para el manejo de excepcion de una tarea ya asignada a un usuario
 * @author softtek
 *
 */
@ApplicationException(rollback = true)
public class TareaYaAsignadaAlUsuarioException extends BPMException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 4982869063681569421L;

    public TareaYaAsignadaAlUsuarioException() {
	}

	public TareaYaAsignadaAlUsuarioException(String mensaje) {
		super(mensaje);
	}

	public TareaYaAsignadaAlUsuarioException(String message, String situacion) {
		super(message);
		this.setSituacion(situacion);
	}

}
