package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.test;

import java.util.List;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.PersonaSinDocumentosException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

import org.junit.Test;

public class AdmonDocsProbatoriosTest extends DeltaOpenEJBTestCase {

	@Test
	public void testModificarDoctoProbatorios() {

		Object object = null;

		try {
			object = initialContext
					.lookup("documentoProbatorioServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		assertNotNull(object);
		assertTrue(object instanceof DocumentoProbatorioServiceBusinessRemote);

		final DocumentoProbatorioServiceBusinessRemote ejb = (DocumentoProbatorioServiceBusinessRemote) object;
		assertNotNull(ejb);

		Persona persona = new Persona();
		persona.setIdPersona(3676007L);

		List<DocumentoProbatorio> docs;
		try {
			docs = ejb.consultarDocumentosDePersona(persona);

			for (DocumentoProbatorio doc : docs) {
				if (doc instanceof Nacimiento) {
					Nacimiento nacimiento = (Nacimiento) doc;

					nacimiento.setAnio(5678);

					ejb.modificarDocumentoProbatorio(nacimiento);
				}
			}
		} catch (PersonaSinDocumentosException e) {
			e.printStackTrace();
		} catch (DocumentoProbatorioException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void xtestEliminarDocProbatorio(){
		
		Object object = null;

		try {
			object = initialContext
					.lookup("documentoProbatorioServiceBusiness");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		assertNotNull(object);
		assertTrue(object instanceof DocumentoProbatorioServiceBusinessRemote);

		final DocumentoProbatorioServiceBusinessRemote ejb = (DocumentoProbatorioServiceBusinessRemote) object;
		assertNotNull(ejb);

		Long cvePersona = 7250163L;
		
		DocumentoProbatorio documentoProbatorio = new DocumentoProbatorio();
		documentoProbatorio.setIdDocumentoProbatorio(550101);
		
		ejb.eliminarDesasociarDocumentoProbatorioPersona(documentoProbatorio, cvePersona);
		
		System.out.println("Eliminado exitosamente!!");
	}

}
