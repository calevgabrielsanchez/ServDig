import gob.imss.webservice.imss.riss.implementacion.ClienteWebserviceValidarRiss;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceImssRissException;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.junit.Test;


public class ValidarRissTest {

	@Test
	public void validarRiss() {
		
		Fisica fisica = new Fisica();
		fisica.setNss("12345678901");
		
		Beneficio beneficio = new Beneficio();
		beneficio.setRfc("SAEM860110GQ7");
		beneficio.setFisica(fisica);
		beneficio.setTipoApartado("A");		
		
		ClienteWebserviceValidarRiss validarRissWS = new ClienteWebserviceValidarRiss();
		
		try {
			validarRissWS.validarRiss(beneficio);
		} catch (ClienteWebserviceImssRissException e) {
			e.printStackTrace();
		};
		
	}
}
