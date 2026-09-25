package mx.gob.imss.ctirss.delta.exception.derechohabiente;


import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class DerechohabienteNoEsCabezaException extends AbstractException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248553615614427314L;
	
	
	public DerechohabienteNoEsCabezaException(String message){
		super(message);
		
	}
	
	public DerechohabienteNoEsCabezaException(String message, String situacion){
		super(message);
		this.setSituacion(situacion);
		
	}
	
	public DerechohabienteNoEsCabezaException(){
		
	}
	/**
	 * Lanza una excepcion de negocio con el mensage de la Excepcion
	 * @param message
	 * @throws DerechohabientesBusinessException 
	 */
	
	public static void throwException(String message) throws DerechohabienteNoEsCabezaException{
		//Por favor no ensierren entre try catch
		throw new DerechohabienteNoEsCabezaException(message);
	}
	
	public static void throwException(String message, String situacion) throws DerechohabienteNoEsCabezaException{
		//Por favor no ensierren entre try catch
		throw new DerechohabienteNoEsCabezaException(message, situacion);
	}
	

}

