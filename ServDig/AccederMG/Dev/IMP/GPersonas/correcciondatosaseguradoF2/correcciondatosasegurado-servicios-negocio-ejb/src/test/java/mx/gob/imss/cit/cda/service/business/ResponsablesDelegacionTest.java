package mx.gob.imss.cit.cda.service.business;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import mx.gob.imss.base.Ambiente;
import mx.gob.imss.base.EjbLocator;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

public class ResponsablesDelegacionTest {
	
	
	@Test
	public void getResponsables(){
		try {
			List<Fisica> lista=new ArrayList<Fisica>();
			
			lista=	EjbLocator.find(ResponsablesDelegacionRemote.class, Ambiente.PRUEBAS).consultarAutorizadoresDelegacion(39, 138);
			
			Assert.assertNotNull(lista);
		} catch (ClienteWebserviceResponsablesSubdelegacionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
