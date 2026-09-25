/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:SolicitudDocumentoTest.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios
 *  @Fecha:26/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.servicios;
import javax.naming.NamingException;

import org.junit.Test;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudDocumentoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.GestionClasifEmpresasTestCaseBase;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;

public class SolicitudDocumentoTest extends GestionClasifEmpresasTestCaseBase{
	@Test
	public void testConsultaDocumento() throws NamingException{
		Object object = initialContext.lookup("solicitudDocumentoBusiness");
		assertNotNull(object);
		assertTrue(object instanceof SolicitudDocumentoBusinessRemote);
		final SolicitudDocumentoBusinessRemote solDocBusinessRemote=
			(SolicitudDocumentoBusinessRemote)object;
		assertNotNull(solDocBusinessRemote);
		
		//solDocBusinessRemote.pruebaInsertarDocumentoProbatorio();
		DocumentoProbatorio documentoProbatorio=new DocumentoProbatorio();
		documentoProbatorio=solDocBusinessRemote.pruebaaObtenerDocumentoProbatorio();
		assertNotNull(documentoProbatorio);
		//byte[] doc=solDocBusinessRemote.obtenerDocumento(1596L);
		//assertNotNull(doc);
	}
}