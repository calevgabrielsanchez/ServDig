/**
 *  Excepcion de negocio que puede ocurrir durante el registro
 *  de un medio de contacto.
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:RegistrarMedioContactoException.java
 *  @Paquete:mx.gob.imss.ctirss.delta.exception.medio.contacto
 *  @Fecha:10/05/2012
 */
package mx.gob.imss.ctirss.delta.exception.medio.contacto;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 * 
 */
public class RegistrarMedioContactoException extends AbstractException {

	private static final long serialVersionUID = -2471975396453124671L;

	private static final Integer codigoError = new Integer(1099);

	private static final String mensaje = new String(
			"Error al registrar el medio de contacto.");

	public RegistrarMedioContactoException() {

		super(mensaje, codigoError);
	}

	public RegistrarMedioContactoException(String string) {
		super(string, codigoError);
	}

}
