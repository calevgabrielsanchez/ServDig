package mx.gob.imss.cit.gestion.solicitud.flujo.exception;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * Clase para manejo de excepciones de BP
 * 
 * @author softtek
 *
 */
@ApplicationException(rollback = true)
public class BPMException extends AbstractException {

	private static final long serialVersionUID = 1L;

	public BPMException() {
	}

	public BPMException(String mensaje) {
		super(mensaje);
	}

	public BPMException(String message, String situacion) {
		super(message);
		this.setSituacion(situacion);
	}

}
