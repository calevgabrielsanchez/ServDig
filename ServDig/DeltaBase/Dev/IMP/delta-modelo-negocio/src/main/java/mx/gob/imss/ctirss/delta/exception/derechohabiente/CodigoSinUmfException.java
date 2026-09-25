package mx.gob.imss.ctirss.delta.exception.derechohabiente;

public class CodigoSinUmfException extends DerechohabientesBusinessException {

	public CodigoSinUmfException(String message) {
		super(message);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8642841350244741801L;
	
	public static void throwException() throws CodigoSinUmfException{
		throw new CodigoSinUmfException(ExceptionMessages.CODIGO_POSTAL_SIN_UMF);
	}
	

}