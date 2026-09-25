package mx.gob.imss.ctirss.delta.exception.firma;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Joaquín Guevara
 *
 */
public class RecursoRemotoNoDisponibleException extends AbstractException {

	
	private static final String situacion = "El recurso remoto solicitado no se encuentra disponible.";
	
	private static final Integer codigo = new Integer(120);

	
	private String serviceName;
	
	public RecursoRemotoNoDisponibleException() {
		super(situacion, codigo);
		
	}
	
	public RecursoRemotoNoDisponibleException(String serviceName){
		this.serviceName = serviceName;
	}
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5040200984272881687L;

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	
	
	

}
