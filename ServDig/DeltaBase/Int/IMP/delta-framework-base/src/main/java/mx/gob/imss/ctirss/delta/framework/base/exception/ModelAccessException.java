package mx.gob.imss.ctirss.delta.framework.base.exception;

/**
 * @author Lucio Duran Silva
 *
 */
public class ModelAccessException extends AbstractException {

	
	private static final Integer codigo = new Integer(200);
	
	/**
	 * @param situacion
	 * @param codigo
	 */
	public ModelAccessException(String situacion) {
		super(situacion, codigo);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

}
