/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DomicilioNoValidoException.java
 *  @Paquete:mx.gob.imss.ctirss.delta.exception.domicilio
 *  @Fecha:27/04/2012
 */
package mx.gob.imss.ctirss.delta.exception.domicilio;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 *
 */
public class DomicilioNoValidoException extends AbstractException {
	
	
	
	private static final Integer codigo = new Integer(0);
	
	private static final String situacion = new String("Domicilio invalido");
	
	
	
	
	
	public DomicilioNoValidoException(){
		super(situacion , codigo);
	}
	
	
	/**
	 * 
	 * @param situacion
	 */
	public DomicilioNoValidoException(String situacion){
		super(situacion , codigo);
	}

}
