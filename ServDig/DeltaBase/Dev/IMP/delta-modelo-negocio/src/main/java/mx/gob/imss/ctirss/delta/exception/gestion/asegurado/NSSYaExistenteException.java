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
public class NSSYaExistenteException extends AbstractException {

	private static final long serialVersionUID = 7397742760576692228L;
	private static final Integer CODIGO = new Integer(0);
	private static final String SITUACION_BASE = new String(
			"El NSS ya existe: ");

	public NSSYaExistenteException(String nss) {
		super(SITUACION_BASE + nss, CODIGO);
	}
}
