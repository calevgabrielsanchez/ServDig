package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios;

import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisis;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.TipoCausaAnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.GestionClasifEmpresasTestCaseBase;

public class TipoCausaAnalisisTest extends GestionClasifEmpresasTestCaseBase{
	@Test
	public void testRectificar() throws Exception {
		Object object = initialContext.lookup("tipoCausaAnalisisServiceBusiness");
		assertNotNull(object);
		assertTrue(object instanceof TipoCausaAnalisisServiceBusinessRemote);
		final TipoCausaAnalisisServiceBusinessRemote causaAnalisisService= (TipoCausaAnalisisServiceBusinessRemote) object;
		assertNotNull(causaAnalisisService);
		TipoCausaAnalisis resultado= causaAnalisisService.consultaTipoCausa(null, 0);
		assertNotNull(resultado);
		assertNotSame(null, resultado);
		System.out.println(resultado); 
	}
}