/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:PersonaSinMedioDeContactoException.java
 *  @Paquete:mx.gob.imss.ctirss.delta.exception.medio.contacto
 *  @Fecha:10/05/2012
 */
package mx.gob.imss.ctirss.delta.exception.medio.contacto;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 *
 */
public class PersonaSinMedioDeContactoException extends AbstractException {
	
	
	/**
	 * serialVersionUID
	 * long
	 */
	private static final long serialVersionUID = 1L;

	private static final Integer codigo = new Integer(9987);
	
	private static final String mensaje = new String("La persona no cuenta con medios de contacto.");
	
	
	public PersonaSinMedioDeContactoException(){
		
		super(mensaje, codigo);
		
	}

}
