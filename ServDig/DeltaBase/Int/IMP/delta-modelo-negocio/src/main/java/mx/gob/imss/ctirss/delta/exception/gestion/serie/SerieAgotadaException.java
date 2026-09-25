/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.serie;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 * 
 */
public class SerieAgotadaException extends AbstractException {

	private static final long serialVersionUID = 7397742760576692228L;
	private static final Integer CODIGO = new Integer(0);
	private static final String SITUACION_BASE = new String(
			"La serie {nomSerie} se ha agotado");

	public SerieAgotadaException(String nomSerie) {
		super(SITUACION_BASE.replace("{nomSerie}", nomSerie), CODIGO);
	}
}
