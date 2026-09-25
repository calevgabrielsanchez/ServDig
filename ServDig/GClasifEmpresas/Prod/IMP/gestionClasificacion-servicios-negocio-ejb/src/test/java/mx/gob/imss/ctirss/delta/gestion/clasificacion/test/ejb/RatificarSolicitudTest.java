package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;

import org.junit.Test;

/**
 * Clase de prueba para el proceso de ratificación de análisis
 * @author Leticia E. Torres R.
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 12/07/2012
 */
public class RatificarSolicitudTest extends GestionClasifEmpresasTestCaseBase {
	
	/**
	 * Ejecuta la prueba unitaria del proceso de ratificación de análisis
	 * @throws Exception
	 */
	@Test
	public void testRatificarAnalisis() throws Exception {
		final Object object = initialContext.lookup("analisisMovimientoBusiness");
		assertNotNull(object);
		assertTrue(object instanceof AnalisisServiceBusinessRemote);
		final AnalisisServiceBusinessRemote analisisServiceBusinessRemote = (AnalisisServiceBusinessRemote) object;
		assertNotNull(analisisServiceBusinessRemote);
		/*final AnalisisClasificacionEmpresas analisisClasificacionEmpresas = analisisServiceBusinessRemote.ratificarSolicitud((new Utilerias()).generaModelo());
		assertEquals(3, analisisClasificacionEmpresas.getCveIdEstatus().longValue());*/
	}

}
