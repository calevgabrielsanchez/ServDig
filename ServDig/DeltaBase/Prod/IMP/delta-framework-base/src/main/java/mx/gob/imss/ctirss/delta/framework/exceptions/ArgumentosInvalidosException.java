/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:ArgumentosInvalidosException.java
 *  @Paquete:mx.gob.imss.ctirss.delta.framework.exceptions
 *  @Fecha:27/04/2012
 */
package mx.gob.imss.ctirss.delta.framework.exceptions;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 *
 */
@ApplicationException(rollback=true)
public class ArgumentosInvalidosException extends AbstractException {

}
