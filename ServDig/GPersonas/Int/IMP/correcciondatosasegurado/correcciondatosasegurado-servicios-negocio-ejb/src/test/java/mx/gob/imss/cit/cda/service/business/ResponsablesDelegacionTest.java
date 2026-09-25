package mx.gob.imss.cit.cda.service.business;

import org.junit.Assert;
import org.junit.Test;

import mx.gob.imss.base.EjbLocator;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;

public class ResponsablesDelegacionTest {
	
	private transient ResponsablesDelegacionRemote responsablesDelegacionRemote= EjbLocator.getResponsablesDelegacionRemote();
	
	@Test
	public void getResponsables(){
		try {
			Assert.assertNotNull(responsablesDelegacionRemote.consultarAutorizadoresDelegacion(39, 138));
		} catch (ClienteWebserviceResponsablesSubdelegacionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
