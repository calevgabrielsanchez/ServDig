package personas;

import mx.gob.imss.ctirss.delta.exception.individuo.PortalCiudadanoException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;

import org.junit.Test;

import test.EjbLocator;

public class PortalCiudadanoServiceTest {

	@Test
	public void testActualizacionCurpCorre() {
		String correo = "marte876@gmail.com";
		String curp = "TEBM871029HPLRLR09";
		
		PortalCiudadanoServiceBusinessRemote ejb = EjbLocator.getPortalCiudadanoService();
		try {
			ejb.actualizarCurpACorreo(correo, curp);
		} catch (PortalCiudadanoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
