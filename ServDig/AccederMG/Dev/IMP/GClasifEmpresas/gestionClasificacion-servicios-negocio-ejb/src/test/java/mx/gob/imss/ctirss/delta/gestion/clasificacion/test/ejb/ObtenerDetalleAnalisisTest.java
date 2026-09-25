package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.utils.Utilerias;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;

import org.junit.Test;

/**
 * Clase de prueba para obtener el detalle del análisis
 * @author Leticia E. Torres R.
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 16/07/2012
 */
public class ObtenerDetalleAnalisisTest extends GestionClasifEmpresasTestCaseBase {
	
	/**
	 * Ejecuta la prueba unitaria del proceso de asignación de solicitud
	 * @throws Exception
	 */
	@Test
	public void testObtenerDetalleAnalisis() throws Exception {
		final Object object = initialContext.lookup("analisisMovimientoBusiness");
		assertNotNull(object);
		assertTrue(object instanceof AnalisisServiceBusinessRemote);
		final AnalisisServiceBusinessRemote analisisServiceBusinessRemote = (AnalisisServiceBusinessRemote) object;
		assertNotNull(analisisServiceBusinessRemote);
		final AnalisisClasificacionEmpresas analisisClasificacionEmpresasParam = (new Utilerias()).generaModelo();
		final AnalisisClasificacionEmpresas analisisClasificacionEmpresas = analisisServiceBusinessRemote.obtenerDetalleAnalisis(BigDecimal.valueOf(analisisClasificacionEmpresasParam.getSolicitud().getId()));
		assertNotNull(analisisClasificacionEmpresas);
	}

}
