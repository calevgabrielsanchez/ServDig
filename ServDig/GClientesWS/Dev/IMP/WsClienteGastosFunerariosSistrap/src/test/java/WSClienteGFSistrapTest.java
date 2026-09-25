import org.junit.Test;

import _26._116._24._172.spes.services.AutorizaGF.AutorizaGastosFunerarios;
import _26._116._24._172.spes.services.AutorizaGF.AutorizaGastosFunerariosService;
import _26._116._24._172.spes.services.AutorizaGF.AutorizaGastosFunerariosServiceLocator;


public class WSClienteGFSistrapTest {

	
	
	@Test
	public void getTest() {
		try {
		
			AutorizaGastosFunerariosService serviceLocatr = new AutorizaGastosFunerariosServiceLocator();
			AutorizaGastosFunerarios serviceClient = serviceLocatr.getAutorizaGF();
			String response = serviceClient.consultaGastosFunerarios("hola mundo");
				
		System.out.println("la respuesta del servicio es : " + response);
		}catch (Exception e) {
			System.out.println("error al cosultar el cliente" + e.getMessage());
		}
	}
}
