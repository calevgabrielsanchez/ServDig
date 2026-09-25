import org.junit.Test;

import mx.gob.imss.cit.clienteswebservices.vigencia.grupoFamilarNss.Return;
import mx.gob.imss.cit.clienteswebservices.vigencia.grupoFamilarNss.WSConsVigGpoFamComXNss;
import mx.gob.imss.cit.clienteswebservices.vigencia.grupoFamilarNss.WSConsVigGpoFamComXNss_Service;

public class WsVigenciaAlmacenesTest {

	
	@Test
	public void consultaVIgenciaGpoFamNSSTest() {
		try {
			WSConsVigGpoFamComXNss_Service service = new WSConsVigGpoFamComXNss_Service();
			WSConsVigGpoFamComXNss port = service.getWSConsVigGpoFamComXNssPort();
			Return respuesa =port.getInfo("4502790759", "2");
			System.out.println("la respuesta del es " + respuesa.getMensajeError());
		}catch (Exception e) {
			System.out.println("error al cosultar el cliente " + e.getMessage());
		}
		
	}
}
