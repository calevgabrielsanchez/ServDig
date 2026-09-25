/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: GCESujetoObligadoServiceBusinessTest.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios
 *  @Fecha: 10/01/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios;

import mx.gob.imss.ctirss.delta.exception.clasificacion.GCESujetoObligadoException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.GCESujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.GestionClasifEmpresasTestCaseBase;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.junit.Test;

public class GCESujetoObligadoServiceBusinessTest extends GestionClasifEmpresasTestCaseBase {
	
	/**
	 * Ejecuta la prueba unitaria para la validación de estado de baja del registro patronal
	 * @throws Exception
	 */
	@Test
	public void testValidarEstadoRegistroPatronal() throws Exception {
		final Object object = initialContext.lookup("gceSujetoObligadoServiceBusiness");
		assertNotNull(object);
		assertTrue(object instanceof GCESujetoObligadoServiceBusinessRemote);
		final GCESujetoObligadoServiceBusinessRemote gceSujetoObligadoServiceBusiness = (GCESujetoObligadoServiceBusinessRemote) object;
		assertNotNull(gceSujetoObligadoServiceBusiness);
		
		final Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(2990L);
		
		try {
			gceSujetoObligadoServiceBusiness.validarEstadoRegistroPatronal(solicitud);
			System.out.println("El registro patronal no ha sido dado de baja");
		} catch (final GCESujetoObligadoException e) {
			e.printStackTrace();
		}
	}
}
