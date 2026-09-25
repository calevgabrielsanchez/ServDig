package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios;

import static mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.utils.Utilerias.getDatosClem;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.utils.Utilerias.getReporteClemBean;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.GestionClasifEmpresasTestCaseBase;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;

public class ModificarClemTest extends GestionClasifEmpresasTestCaseBase{
	@Test
	public void testModificaClem() throws Exception {
		Object object = initialContext.lookup("datosClemServiceBusiness");
		assertNotNull(object);
		assertTrue(object instanceof DatosClemServiceBusinessRemote);
		final DatosClemServiceBusinessRemote datosClemServiceBussinessRemote = (DatosClemServiceBusinessRemote) object;
		assertNotNull(datosClemServiceBussinessRemote);
		final DatosClem resultado= datosClemServiceBussinessRemote.insertaActualizacionClem(getDatosClem(), getReporteClemBean());
		assertNotNull(resultado);
		assertNotSame(null, resultado);
	}
}