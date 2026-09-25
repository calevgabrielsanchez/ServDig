package mx.gob.imss.ctirss.delta.cobranza.service.test;

import static org.junit.Assert.fail;
import mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.exception.ErrorSelloCFDIException;
import mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.model.SelloCFDI;
import mx.gob.imss.ctirss.delta.cobranza.timbrado.certificado.service.business.SelloCFDIBusinessService;

import org.junit.Before;
import org.junit.Test;

public class SelloCFDIBusinessTest {
	
	
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGenerarSelloCFDI() {
		
		
		SelloCFDIBusinessService service = new SelloCFDIBusinessService();
		
		try {
			SelloCFDI sello = service.generarSelloCFDI("<xml> version </xml>");
			System.out.println("Sello :" + sello.toString());
			
		} catch (ErrorSelloCFDIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			fail(e.getMessage());
			
		}
		
		
		
	}

}
