/**
 * @author Juan Manuel Lopez Lozano
 * @since 12/10/2011
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

public class CatalogoException extends Exception {
	private static final long serialVersionUID = -8840607056295489681L;
	private Integer codigo = new Integer(100);
	
	public static final Integer DATOS_ENTRADA_INVALIDOS = new Integer (102);
	public static final Integer ERROR_DE_SISTEMA = new Integer (108);
	
	public CatalogoException() {
		
	}
	
	public CatalogoException(String msg) {
		super(msg);
	}
	
	public CatalogoException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public CatalogoException(Throwable cause) {
		super("Error de base de datos:", cause);
	}
	
	public CatalogoException(String msg, Integer codigo) {
		super(msg);
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	
}