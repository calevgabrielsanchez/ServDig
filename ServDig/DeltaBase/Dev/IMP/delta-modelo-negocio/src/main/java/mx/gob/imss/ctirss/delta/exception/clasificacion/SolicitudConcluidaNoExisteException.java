/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author NOVUTEK
 *
 */
@ApplicationException(rollback=true)
public class SolicitudConcluidaNoExisteException extends AbstractException {

	
	/**
	 * serial.
	 */
	private static final long serialVersionUID = -4955542321323574679L;

	private static final String situacion = "El equipo o transporte es inv\u00E1lido";

	private static final Integer codigo = new Integer(123);

	/**
	 * Contructor de la clase. 
	 */
	public SolicitudConcluidaNoExisteException( ) {
		super(situacion, codigo);
	}
	
	
}
