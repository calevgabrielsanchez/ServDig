/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.firma;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Joaquín Guevara
 *
 */
public class ErrorEnInvocacionRecursoRemotoException extends AbstractException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2355189683050610343L;

	private static final String situacion = "Ha ocurrido un error al invocar al recurso remoto.";
	
	private static final Integer codigo = new Integer(130);
	
	public ErrorEnInvocacionRecursoRemotoException() {
		super(situacion, codigo);
	}
	
	public ErrorEnInvocacionRecursoRemotoException(Throwable t) {
		super(situacion, codigo);
	}
	
	
	public ErrorEnInvocacionRecursoRemotoException( String situacion ) {
		super(situacion, codigo);
	}

}
