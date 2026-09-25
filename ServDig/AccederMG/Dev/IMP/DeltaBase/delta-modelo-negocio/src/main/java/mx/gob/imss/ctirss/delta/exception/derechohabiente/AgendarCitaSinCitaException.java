package mx.gob.imss.ctirss.delta.exception.derechohabiente;

public class AgendarCitaSinCitaException extends
		DerechohabientesBusinessException {

	private static final String MENSAGE = "El UMF no acepta Citas";

	public AgendarCitaSinCitaException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public static void throwException() throws DerechohabientesBusinessException{
		throw new DerechohabientesBusinessException(MENSAGE);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8349786982369415759L;

}
