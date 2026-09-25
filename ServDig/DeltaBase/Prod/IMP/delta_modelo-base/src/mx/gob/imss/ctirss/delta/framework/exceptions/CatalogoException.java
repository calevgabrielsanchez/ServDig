/**
 * @author Juan Manuel Lopez Lozano
 * @since 12/10/2011
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

public class CatalogoException extends Exception {
	private static final long serialVersionUID = -8840607056295489681L;
	public CatalogoException() {}
	public CatalogoException(String msg) {super(msg);}
	public CatalogoException(String msg, Throwable cause) {super(msg, cause);}
	public CatalogoException(Throwable cause) {super("Error de base de datos:", cause);}
}