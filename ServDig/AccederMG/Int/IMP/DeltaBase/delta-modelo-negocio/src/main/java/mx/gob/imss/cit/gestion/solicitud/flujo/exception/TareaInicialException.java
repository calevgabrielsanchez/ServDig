package mx.gob.imss.cit.gestion.solicitud.flujo.exception;

import javax.ejb.ApplicationException;

/**
 * Clase para el manejo de excepciones de tarea inicial
 * @author softtek
 *
 */
@ApplicationException(rollback = true)
public class TareaInicialException extends BPMException {

    /**
     * Numero de version
     */
    private static final long serialVersionUID = 4982869063681569421L;

    public TareaInicialException() {
	}

	public TareaInicialException(String mensaje) {
		super(mensaje);
	}

	public TareaInicialException(String message, String situacion) {
		super(message);
		this.setSituacion(situacion);
	}

}
