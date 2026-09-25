package mx.gob.imss.cit.gestion.solicitud.flujo.service.business;

import javax.ejb.Stateless;

import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoDummyRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;

@Stateless(name = "flujoTrabajoDummyBusiness", mappedName = "flujoTrabajoDummyBusiness")
public class FlujoTrabajoDummyBusiness extends AbstractServiceUtility implements FlujoTrabajoDummyRemote {
	
	@Override
	public String testMethod(String nombre){
	    return "Hello " + nombre;
	}

}
