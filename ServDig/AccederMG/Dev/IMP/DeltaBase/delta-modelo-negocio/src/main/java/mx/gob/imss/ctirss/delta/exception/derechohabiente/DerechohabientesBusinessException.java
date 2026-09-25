package mx.gob.imss.ctirss.delta.exception.derechohabiente;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class DerechohabientesBusinessException extends AbstractException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248553615614427314L;
	
	
	public DerechohabientesBusinessException(String message){
		super(message);
		
	}
	
	public DerechohabientesBusinessException(String message, String situacion){
		super(message);
		this.setSituacion(situacion);
		
	}
	
	public DerechohabientesBusinessException(){
		
	}
	/**
	 * Lanza una excepcion de negocio con el mensage de la Excepcion
	 * @param message
	 * @throws DerechohabientesBusinessException 
	 */
	
	public static void throwException(String message) throws DerechohabientesBusinessException{
		//Por favor no ensierren entre try catch
		throw new DerechohabientesBusinessException(message);
	}
	
	public static void throwException(String message, String situacion) throws DerechohabientesBusinessException{
		//Por favor no ensierren entre try catch
		throw new DerechohabientesBusinessException(message, situacion);
	}
	

}
