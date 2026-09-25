/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 * 
 */
public class PersonaConVariosNSSException extends AbstractException {

	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer CODIGO = new Integer(0);

	/**
	 * Constructor por omision
	 */
	public PersonaConVariosNSSException(Long idPersona) {
		super("La persona " + idPersona + " cuenta con más de un NSS", CODIGO);
	}
	
	public PersonaConVariosNSSException(String msg) {
		super(msg, CODIGO);
	}
}
