package mx.gob.imss.ctirss.delta.exception.beneficio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class BeneficiarioBusinessException extends AbstractException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1248553615614427314L;
	
	
	public BeneficiarioBusinessException(String message){
		super(message);
		
	}
	
	public BeneficiarioBusinessException(String message, String situacion){
		super(message);
		this.setSituacion(situacion);
		
	}
	
	public BeneficiarioBusinessException(){
		
	}
	/**
	 * Lanza una excepcion de negocio con el mensage de la Excepcion
	 * @param message
	 * @throws BeneficiarioBusinessException 
	 */
	
	public static void throwException(String message) throws BeneficiarioBusinessException{
		//Por favor no ensierren entre try catch
		throw new BeneficiarioBusinessException(message);
	}
	
	public static void throwException(String message, String situacion) throws BeneficiarioBusinessException{
		//Por favor no ensierren entre try catch
		throw new BeneficiarioBusinessException(message, situacion);
	}
	

}


