package mx.gob.imss.ctirss.delta.exception.derechohabiente;


import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class PatronImssException extends AbstractException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248553615614427314L;
	
	
	public PatronImssException(String message){
		super(message);
		
	}
	
	public PatronImssException(String message, String situacion){
		super(message);
		this.setSituacion(situacion);
		
	}
	
	public PatronImssException(){
		
	}
	/**
	 * Lanza una excepcion de negocio con el mensage de la Excepcion
	 * @param message
	 * @throws PatronImssException 
	 */
	
	public static void throwException(String message) throws PatronImssException{
		//Por favor no ensierren entre try catch
		throw new PatronImssException(message);
	}
	
	public static void throwException(String message, String situacion) throws PatronImssException{
		//Por favor no ensierren entre try catch
		throw new PatronImssException(message, situacion);
	}
	

}


