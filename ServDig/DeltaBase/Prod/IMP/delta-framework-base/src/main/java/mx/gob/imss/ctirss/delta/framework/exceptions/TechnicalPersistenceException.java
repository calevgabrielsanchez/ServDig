/**
 * @author Juan Manuel Lopez Lozano
 * @since 12/10/2011
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

public class TechnicalPersistenceException extends Exception {
	private static final long serialVersionUID = -8840607056295489681L;
	public TechnicalPersistenceException() {}
	public TechnicalPersistenceException(String msg) {super(msg);}
	public TechnicalPersistenceException(String msg, Throwable cause) {super(msg, cause);}
	public TechnicalPersistenceException(Throwable cause) {super("Error de base de datos:", cause);}
}