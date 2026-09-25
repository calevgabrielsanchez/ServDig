package mx.gob.imss.cit.cda.web.prueba;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.base.Ambiente;
import mx.gob.imss.cit.cda.web.base.EjbLocator;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

public class ResponsableTareaTest {

	private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(ResponsableTareaTest.class);
    }
	
	
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
