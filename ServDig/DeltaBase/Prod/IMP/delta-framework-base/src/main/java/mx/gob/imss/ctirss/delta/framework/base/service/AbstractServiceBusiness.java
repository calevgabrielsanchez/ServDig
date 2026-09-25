/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:AbstractServiceBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.framework.base.service
 *  @Fecha:14/02/2012
 */
package mx.gob.imss.ctirss.delta.framework.base.service;

/**
 * @author Lucio Duran Silva
 *
 */
public class AbstractServiceBusiness extends AbstractService {

	
	public String getValue( String value ){
		if( value != null )
			return value;
		return "";
	}
	
	public Long getValue( Long value ){
		if( value != null )
			return value;
		return 0L;
	}
	
}
