package mx.gob.imss.ctirss.delta.exception.derechohabiente;

public class AgendarCitaSinCapacidadException extends
		DerechohabientesBusinessException {

	

	public AgendarCitaSinCapacidadException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	public static void throwException() throws AgendarCitaSinCapacidadException{
		throw new AgendarCitaSinCapacidadException(ExceptionMessages.AGENDARCITA_SIN_CAPASIDAD);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8349786982369415759L;

}
