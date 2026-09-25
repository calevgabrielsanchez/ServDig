/**
 * 
 */
package mx.gob.imss.ctirss.delta.exception.gestion.asegurado;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 *
 */
public class AseguradoConNSSAsignadoException extends AbstractException {

	
	
	private static final long serialVersionUID = -3288570647623273063L;
	private static final Integer codigo = new Integer(0);
	private static final String situacion = new String("El asegurado ya tiene asignado un NSS.");
	
	
	/**
	 * Constructor por omision
	 */
	public AseguradoConNSSAsignadoException(){
		super(situacion , codigo);
	}
	
	public AseguradoConNSSAsignadoException(String nss){
		super("El asegurado ya cuenta con el siguiente NSS:" + nss , codigo);
	} 
}
