/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DomicilioServiceBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.patronal.service.business.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.domicilio;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.domicilio.DomicilioServiceBusinessRemote;

/**
 * @author Lucio Duran Silva
 *
 */

@Stateless(name="domicilioServiceBusiness" ,mappedName="domicilioServiceBusiness")
public class DomicilioServiceBusiness extends AbstractServiceBusiness implements
		DomicilioServiceBusinessRemote {

	@Override
	public void hello() {
		// TODO Auto-generated method stub
		
	}

	
	
}
