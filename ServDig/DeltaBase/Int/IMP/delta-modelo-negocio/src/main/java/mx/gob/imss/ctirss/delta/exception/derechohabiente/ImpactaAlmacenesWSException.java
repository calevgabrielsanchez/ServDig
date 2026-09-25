package mx.gob.imss.ctirss.delta.exception.derechohabiente;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

@ApplicationException(rollback = true)
public class ImpactaAlmacenesWSException extends AbstractException{

	private static final long serialVersionUID = 1L;
	
	public ImpactaAlmacenesWSException(String message){
		super(message);
		
	}
	
	public ImpactaAlmacenesWSException(String message, String situacion){
		super(message);
		this.setSituacion(situacion);
		
	}
	
	public ImpactaAlmacenesWSException(){
		
	}
	/**
	 * Lanza una excepcion de negocio con el mensage de la Excepcion
	 * @param message
	 * @throws ImpactaAlmacenesWSException 
	 */
	
	public static void throwException(String message) throws ImpactaAlmacenesWSException{
		throw new ImpactaAlmacenesWSException(message);
	}
	
	public static void throwException(String message, String situacion) throws ImpactaAlmacenesWSException{
		throw new ImpactaAlmacenesWSException(message, situacion);
	}
	

}
