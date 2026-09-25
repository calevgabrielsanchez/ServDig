package mx.gob.imss.cit.gestion.solicitud.flujo.exception;

import javax.ejb.ApplicationException;

/**
 * Clase que maneja la excepcion de una tarea sin usuario asignado
 * @author softtek
 *
 */
@ApplicationException(rollback = true)
public class TereaSinUsuarioAsignadoException extends BPMException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 4982869063681569421L;

    public TereaSinUsuarioAsignadoException() {
	}

	public TereaSinUsuarioAsignadoException(String mensaje) {
		super(mensaje);
	}

	public TereaSinUsuarioAsignadoException(String message, String situacion) {
		super(message);
		this.setSituacion(situacion);
	}

}
