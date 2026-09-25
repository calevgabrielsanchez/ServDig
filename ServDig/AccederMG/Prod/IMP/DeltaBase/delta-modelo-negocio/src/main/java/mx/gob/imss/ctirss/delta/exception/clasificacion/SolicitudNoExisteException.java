/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: smod-web
 *  @Archivo:SolicitudNoExisteException.java
 *  @Paquete:mx.gob.imss.ctirss.smod.solicitud.exception
 *  @Fecha:14/12/2011
 */
package mx.gob.imss.ctirss.delta.exception.clasificacion;

import javax.ejb.ApplicationException;

import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;

/**
 * @author Lucio Duran Silva
 *
 */
@ApplicationException(rollback=true)
public class SolicitudNoExisteException extends AbstractException {
	
	
	
	private static final String situacion ="La solicitud no existe";
	
	
	
	private static final Integer codigo = new Integer(400);
	
	/**
	 * 
	 */
	public SolicitudNoExisteException(){
		super(situacion , codigo);
	}

}
