package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.GestionClasifEmpresasTestCaseBase;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.utils.Utilerias;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;

import org.junit.Test;

/**
 * Clase de prueba para ratificar el análisis de una solicitud.
 * @author Eduardo González
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 31/07/2012
 */
public class RechazarRatificacionTest extends GestionClasifEmpresasTestCaseBase {
	
	/**
	 * Ejecuta la prueba unitaria del proceso de ratificación del análisis
	 * de una solicitud.
	 * @throws Exception
	 */
	@Test
	public void testRechazarRatificacion() throws Exception {
		Object object = initialContext.lookup("analisisMovimientoBusiness");
		assertNotNull(object);
		assertTrue(object instanceof AnalisisServiceBusinessRemote);
		final AnalisisServiceBusinessRemote analisisServiceBusinessRemote = (AnalisisServiceBusinessRemote) object;
		assertNotNull(analisisServiceBusinessRemote);
		final ClasificacionDTO dto = new Utilerias().generaClasificacionDTO();
		final AnalisisClasificacionEmpresas analisisClasificacionEmpresas = analisisServiceBusinessRemote.rechazarRatificacionPendAut(dto);
		assertNotNull(analisisClasificacionEmpresas);
		assertEquals(new BigDecimal(7), analisisClasificacionEmpresas.getCveIdEstatus());
	}

}
