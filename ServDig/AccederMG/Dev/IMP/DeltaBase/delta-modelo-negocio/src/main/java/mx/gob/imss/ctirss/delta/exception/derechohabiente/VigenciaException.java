package mx.gob.imss.ctirss.delta.exception.derechohabiente;


import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class VigenciaException extends AbstractException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248553615614427314L;
	
	
	public VigenciaException(String message){
		super(message);
		
	}
	
	public VigenciaException(String message, String situacion){
		super(message);
		this.setSituacion(situacion);
		
	}
	
	public VigenciaException(){
		
	}
	/**
	 * Lanza una excepcion de negocio con el mensage de la Excepcion
	 * @param message
	 * @throws DerechohabientesBusinessException 
	 */
	
	public static void throwException(String message) throws VigenciaException{
		//Por favor no ensierren entre try catch
		throw new VigenciaException(message);
	}
	
	public static void throwException(String message, String situacion) throws VigenciaException{
		//Por favor no ensierren entre try catch
		throw new VigenciaException(message, situacion);
	}
	

}

