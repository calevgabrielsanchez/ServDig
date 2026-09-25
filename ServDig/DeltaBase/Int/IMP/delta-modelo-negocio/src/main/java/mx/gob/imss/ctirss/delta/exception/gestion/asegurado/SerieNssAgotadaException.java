/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 * 
 */
@ApplicationException(rollback=false)
public class SerieNssAgotadaException extends AbstractException {

	private static final long serialVersionUID = 7397742760576692228L;
	private static final Integer CODIGO = new Integer(0);
	private static final String SITUACION_BASE = new String(
			"La serie para NSS {nomSerie} se ha agotado");

	public SerieNssAgotadaException(String nomSerie) {
		super(SITUACION_BASE.replace("{nomSerie}", nomSerie), CODIGO);
	}
}
