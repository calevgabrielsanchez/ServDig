/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.usuario;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Eduardo Gonzalez
 *
 */
public class UsuarioNoEncontradoException extends AbstractException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6587919839255328534L;

	private static final Integer codigo = new Integer(0);
	
	private static final String situacion = new String("El usuario no ha sido encontrado");
	
	
	public UsuarioNoEncontradoException(){
		super(situacion , codigo);
	}
	
	/**
	 * Recibe la situacion que origino el problema
	 * @param situacion
	 */
	public UsuarioNoEncontradoException(String situacion){
		super(situacion , codigo);
	}

}
