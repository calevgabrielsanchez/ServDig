/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Joaquin Guevara
 *
 */
@ApplicationException(rollback=true)
public class ArticuloNoEncontradoException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1211853458197214972L;
	private final static String situacion ="field.clem.error.articulo155";
	private final static Integer codigo = 650;
	
	public ArticuloNoEncontradoException() {
		super(situacion, codigo);
	}

}
