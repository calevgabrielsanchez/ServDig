package mx.gob.imss.ctirss.delta.exception.derechohabiente;


import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class ModalidadesException extends AbstractException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248553615614427314L;
	
	
	public ModalidadesException(String message){
		super(message);
		
	}
	
	public ModalidadesException(String message, String situacion){
		super(message);
		this.setSituacion(situacion);
		
	}
	
	public ModalidadesException(){
		
	}
	/**
	 * Lanza una excepcion de negocio con el mensage de la Excepcion
	 * @param message
	 * @throws DerechohabientesBusinessException 
	 */
	
	public static void throwException(String message) throws ModalidadesException{
		//Por favor no ensierren entre try catch
		throw new ModalidadesException(message);
	}
	
	public static void throwException(String message, String situacion) throws ModalidadesException{
		//Por favor no ensierren entre try catch
		throw new ModalidadesException(message, situacion);
	}
	

}
