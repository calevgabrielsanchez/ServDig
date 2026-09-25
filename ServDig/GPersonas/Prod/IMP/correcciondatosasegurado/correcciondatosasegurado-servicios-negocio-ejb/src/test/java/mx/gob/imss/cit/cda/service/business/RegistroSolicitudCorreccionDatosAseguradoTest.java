package mx.gob.imss.cit.cda.service.business;

import org.junit.Assert;
import org.junit.Test;

import mx.gob.imss.base.EjbLocator;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;

public class RegistroSolicitudCorreccionDatosAseguradoTest {

	private transient RegistroSolicitudCorreccionDatosAseguradoRemote registroSolicitudCorreccionDatosAsegurado = EjbLocator.getRegistroSolicitudCorreccionDatosAseguradoRemote();
	
	@Test
	public void recuperarResponsables() {
		String str = registroSolicitudCorreccionDatosAsegurado.toString();
		System.out.println(str);
		Assert.assertNotNull(str);
	}

}
