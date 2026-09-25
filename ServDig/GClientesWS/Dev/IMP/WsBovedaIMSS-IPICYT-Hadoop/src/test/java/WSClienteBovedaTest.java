import org.junit.Test;

import mx.gob.imss.cit.clienteswebservices.boveda.Atributo;
import mx.gob.imss.cit.clienteswebservices.boveda.EntradaConsulta;
import mx.gob.imss.cit.clienteswebservices.boveda.SalidaConsulta;
import mx.gob.imss.cit.clienteswebservices.boveda.TspiPort;
import mx.gob.imss.cit.clienteswebservices.boveda.TspiPortService;
import mx.gob.imss.cit.clienteswebservices.boveda.rest.BovedaTspiRestServiceImp;
import mx.gob.imss.cit.clienteswebservices.boveda.rest.IBovedaTspiRestLocal;
import mx.gob.imss.cit.clienteswebservices.boveda.rest.bean.BovedaResponseException;
import mx.gob.imss.cit.clienteswebservices.boveda.rest.bean.ConsultaEstatusDocBovedaRequest;
import mx.gob.imss.cit.clienteswebservices.boveda.rest.bean.ConsultaEstatusDocBovedaResponse;

public class WSClienteBovedaTest {
	
	
	@Test
	public void getDocumento() {
		try {
		TspiPortService serice = new TspiPortService();
		TspiPort port = serice.getTspiPortSoap11();
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
	
	@Test
	public void consultaDocumentoRest() {
		try {
			ConsultaEstatusDocBovedaRequest consulta = new ConsultaEstatusDocBovedaRequest();
			consulta.setFolio("5093655");
			consulta.setTipoDocumental("tspi");
			consulta.setDocId("");
			
			IBovedaTspiRestLocal service = new BovedaTspiRestServiceImp();
			ConsultaEstatusDocBovedaResponse response= service.consultaEstatusDoc(consulta);
			System.out.println("la respuesta es:  " + response.getCodigo());
		}catch (BovedaResponseException e) {
			System.out.println("error de negocio " + e.getMessage());
		}catch (Exception e) {
			System.out.println("error no cachado " + e.getMessage());
		}
		
	}

}
