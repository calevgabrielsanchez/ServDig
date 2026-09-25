package mx.gob.imss.ctirss.delta.exception.individuo;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

public class PortalCiudadanoException extends AbstractException{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("Error en portal Ciudadano");
	
	/**
	 * Constructor por omision
	 */
	public PortalCiudadanoException(){
		super(situacion , codigo);
	}
	
	/**
	 * Constructor que recibe un mensaje de erro
	 * @param situacion
	 */
	public PortalCiudadanoException(String situacion){
		super(situacion , codigo);
	}
}
