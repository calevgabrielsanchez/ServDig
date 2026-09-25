package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.utils.Utilerias;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;

import org.junit.Test;

///**
// * Clase de prueba para el proceso de liberación de solicitud
// * @author Leticia E. Torres R.
// * @company IMSS (Instituto Mexicano del Seguro Social)
// * @date 31/07/2012
// */
//public class LiberarSolicitudTest extends GestionClasifEmpresasTestCaseBase {
//	
//	/**
//	 * Ejecuta la prueba unitaria del proceso de liberación de solicitud
//	 * @throws Exception
//	 */
//	@Test
//	public void testAsignarSolicitud() throws Exception {
//		final Object object = initialContext.lookup("solicitudServiceBusiness");
//		assertNotNull(object);
//		assertTrue(object instanceof SolicitudServiceBusinessRemote);
//		final SolicitudServiceBusinessRemote solicitudServiceBusinessRemote = (SolicitudServiceBusinessRemote) object;
//		assertNotNull(solicitudServiceBusinessRemote);
//		final AnalisisClasificacionEmpresas analisisClasificacionEmpresas = solicitudServiceBusinessRemote.liberar(Utilerias.generaClasificacionDTO());
//		assertNotNull(analisisClasificacionEmpresas);
//		assertEquals(1, analisisClasificacionEmpresas.getCveIdEstatus().intValue());
//	}
//
//}