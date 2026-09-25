/**
 * 
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author SRG
 *
 */
public class FormularioVacioException extends AbstractException {
	
	private static final long serialVersionUID = 1L;
	private static final String mensaje ="No fue posible realizar la b\u00fasqueda. Al menos debe elegir un filtro de b\u00fasqueda";
	private static final Integer codigo = new Integer(101);
	
	public FormularioVacioException(){
		super(mensaje, codigo);
	}
	
}
