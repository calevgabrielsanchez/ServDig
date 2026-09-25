/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * @author vanderluk
 * 
 */
public class DomicilioNoLocalizadoException extends AbstractException {

	private static final long serialVersionUID = 3648554008876564136L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("El domicilio no ha sido localizado");
	
	private Fisica fisica;

	public DomicilioNoLocalizadoException() {
		super(situacion, codigo);
	}

	/**
	 * Recibe la situacion que origino el problema
	 * 
	 * @param situacion
	 */
	public DomicilioNoLocalizadoException(String situacion) {
		super(situacion, codigo);
	}

	public Fisica getFisica() {
		return fisica;
	}

	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}

}
