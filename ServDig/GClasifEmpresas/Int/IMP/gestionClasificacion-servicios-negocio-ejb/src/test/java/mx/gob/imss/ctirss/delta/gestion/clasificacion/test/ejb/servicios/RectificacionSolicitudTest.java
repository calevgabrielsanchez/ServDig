package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionPropuestaDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.rectificacion.RectificacionBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.GestionClasifEmpresasTestCaseBase;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.utils.Utilerias.getClasificacionPropuestaDTO;

public class RectificacionSolicitudTest extends GestionClasifEmpresasTestCaseBase{
	@Test
	public void testRectificar() throws Exception {
		Object object = initialContext.lookup("rectificacionServiceBusiness");
		assertNotNull(object);
		assertTrue(object instanceof RectificacionBusinessRemote);
		final RectificacionBusinessRemote rectificacionServiceBusiness = (RectificacionBusinessRemote) object;
		assertNotNull(rectificacionServiceBusiness);
		final ClasificacionPropuestaDTO dto = getClasificacionPropuestaDTO();
		final Integer resultado= rectificacionServiceBusiness.guardaRectificacion(dto);
		assertNotNull(resultado);
		assertNotSame(0, resultado);
	}
}