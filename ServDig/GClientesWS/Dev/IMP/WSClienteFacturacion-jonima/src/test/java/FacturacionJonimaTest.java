import org.junit.Test;

import mx.gob.imss.cit.clienteswebservices.externo.jonima.Services;
import mx.gob.imss.cit.clienteswebservices.externo.jonima.Services_Service;

public class FacturacionJonimaTest {

	
	
	@Test
	public void getToken() {
		try {
		Services_Service service = new Services_Service();
		Services port = service.getServicesPort();
		String token = port.authenticate("FIBESO_FUN", "FIbeSO_2023!");
		System.out.println("el token es" + token);
		}catch (Exception e) {
			System.out.println("error al cosultar el cliente" + e.getMessage());
		}
	}
}
