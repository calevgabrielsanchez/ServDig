package mx.gob.imss.cit.cda.service.business;

import org.junit.Assert;
import org.junit.Test;

import mx.gob.imss.base.Ambiente;
import mx.gob.imss.base.EjbLocator;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;

public class RegistroSolicitudCorreccionDatosAseguradoTest {

	
	@Test
	public void recuperarResponsables() {
		String str = EjbLocator.find(RegistroSolicitudCorreccionDatosAseguradoRemote.class, Ambiente.PRUEBAS).toString();
		System.out.println(str);
		Assert.assertNotNull(str);
	}

}
