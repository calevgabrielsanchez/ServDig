/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DomicilioNoLocalizadoException.java
 *  @Paquete:mx.gob.imss.ctirss.delta.portal.exception
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.portal.exception;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 *
 */
public class DomicilioNoLocalizadoException extends AbstractException {
	
	
	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;

	private static final String situacion ="El domicilio no puede ser localizado.";
	
	private static final Integer codigo = new Integer (9999);
	
	
	public DomicilioNoLocalizadoException(){
		super(situacion, codigo);
	}
	
	

}
