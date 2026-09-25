import org.junit.Test;

import mx.gob.imss.cit.clienteswebservices.boveda.legada.Atributo;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.EntradaConsulta;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.LegadoPort;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.LegadoPortService;
import mx.gob.imss.cit.clienteswebservices.boveda.legada.SalidaConsulta;

public class WSClienteBovedaLegadaTest {
	
	
	@Test
	public void getDocumento() {
		try {
			LegadoPortService serice = new LegadoPortService();
			LegadoPort port = serice.getLegadoPortSoap11();
		EntradaConsulta consulta = new EntradaConsulta();
		 String tipoDocumentalTspi = "D:cda:imss";
	        //String tipoDocumentalHistoricoTspi = "cmis:document";
	        Atributo atributo = new Atributo();
	       
	          atributo.setNombre("objectId");
	          atributo.setValor("996458ed-20b7-4425-85e9-01f81ae52ace;1.0");
	          consulta.setTipoDocumental(tipoDocumentalTspi);
	          consulta.getAtributo().add(atributo);
	          
	          SalidaConsulta salidaAlta = port.consultaDocumento(consulta);
	          System.out.println("la respuesta del cliente es :" + salidaAlta.getDescripcion());
		}catch(Exception e) {
			System.out.println("error al cosultar el cliente " + e.getMessage());
		}
		
	}
	


}
