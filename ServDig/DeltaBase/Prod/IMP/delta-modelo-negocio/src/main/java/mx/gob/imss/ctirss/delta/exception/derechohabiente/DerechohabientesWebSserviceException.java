package mx.gob.imss.ctirss.delta.exception.derechohabiente;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;


public class DerechohabientesWebSserviceException extends AbstractException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248553615614427314L;
	
	
	public DerechohabientesWebSserviceException(String message){
		super(message);
		
	}
	
	public DerechohabientesWebSserviceException(String message, String situacion){
		super(message);
		this.setSituacion(situacion);
		
	}
	
	public DerechohabientesWebSserviceException(){
		
	}
	/**
	 * Lanza una excepcion de negocio con el mensage de la Excepcion
	 * @param message
	 * @throws DerechohabientesWebSserviceException 
	 */
	
	public static void throwException(String message) throws DerechohabientesWebSserviceException{
		//Por favor no ensierren entre try catch
		throw new DerechohabientesWebSserviceException(message);
	}
	
	public static void throwException(String message, String situacion) throws DerechohabientesWebSserviceException{
		//Por favor no ensierren entre try catch
		throw new DerechohabientesWebSserviceException(message, situacion);
	}
	

}
