/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * @author Lucio Duran Silva
 * 
 */
public class PersonaConNSSException extends AbstractException {

	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer CODIGO = new Integer(0);
	private static final String SITUACION_BASE = new String(
			"Usted ya cuenta con el siguiente NSS: ");
	private Fisica fisica;

	/**
	 * Constructor por omision
	 */
	public PersonaConNSSException() {
		super(SITUACION_BASE, CODIGO);
	}

	public PersonaConNSSException(Fisica fisica) {
		super(SITUACION_BASE + fisica.getNss(), CODIGO);

		this.fisica = fisica;
	}

	/**
	 * @return the fisica
	 */
	public Fisica getFisica() {
		return fisica;
	}
}
