package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.PersonaSinDocumentosException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

import org.junit.Test;

public class DocumentoProbatorioTest extends DeltaTestCaseBase {

	@Test
	public void testConsultas() {
		
		DocumentoProbatorioServiceBusinessRemote remote = null;
		try {
			remote = (DocumentoProbatorioServiceBusinessRemote) this
					.lookUp("documentoProbatorioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Long idPersona = remote.getIdPersonaTramite(12L);
		System.out.println("El id de la persona realcionado al tramites es: " + idPersona);
		
		List<DocumentoProbatorio> documentos = remote.listaDocumentosProbatoriosTramite(3598069L);
		System.out.println("los documentos del tramite son: " + documentos);
	}
	@Test
	public void testRegistrarDocumentos() {

		DocumentoProbatorioServiceBusinessRemote remote = null;
		try {
			remote = (DocumentoProbatorioServiceBusinessRemote) this
					.lookUp("documentoProbatorioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<DocumentoProbatorio> documentos = new ArrayList<DocumentoProbatorio>();

		Nacimiento nacimiento = new Nacimiento();
		nacimiento.setAnio(1986);
		nacimiento.setCrip("12345678DAF");
		nacimiento.setNoJuzgado("12345");
		nacimiento.setTomo("986hdi");

		nacimiento.setFechaSuceso(new Date());
		Municipio municipio = new Municipio();
		municipio.setClave("1");
		EntidadFederativa entidadFederativa = new EntidadFederativa();
		entidadFederativa.setClave("1");
		municipio.setEntidadFederativa(entidadFederativa);
		nacimiento.setMunicipio(municipio);
		nacimiento.setNoActa("1243");
		nacimiento.setNoFoja("adfad");
		nacimiento.setNoLibro("93839");

		nacimiento.setFechaExpedicion(new Date());

		// documentos.add(nacimiento);

		nacimiento = new Nacimiento();
		nacimiento.setAnio(1986);
		nacimiento.setCrip("MI_CRIP");
		nacimiento.setNoJuzgado("JUZGADO");
		nacimiento.setTomo("MI_TOMO");

		nacimiento.setFechaSuceso(new Date());
		municipio = new Municipio();
		municipio.setClave("1");
		entidadFederativa = new EntidadFederativa();
		entidadFederativa.setClave("2");
		municipio.setEntidadFederativa(entidadFederativa);
		nacimiento.setMunicipio(municipio);
		nacimiento.setNoActa("1243");
		nacimiento.setNoFoja("456313");
		nacimiento.setNoLibro("78905");

		nacimiento.setFechaExpedicion(new Date());

		// documentos.add(nacimiento);

		CURP curp = new CURP();
		curp.setAnioRegistro(new Long("2012"));
		curp.setCifrado("Cifrado");
		curp.setCrip("Crip");
		curp.setCurp("DUSL821212312312");

		municipio = new Municipio();
		municipio.setClave("1");
		entidadFederativa = new EntidadFederativa();
		entidadFederativa.setClave("2");
		municipio.setEntidadFederativa(entidadFederativa);

		curp.setMunicipio(municipio);
		curp.setFechaExpedicion(new Date());
		curp.setFechaInscripcion(new Date());
		curp.setNumFolioExtranjero("F" + System.currentTimeMillis());
		curp.setNoActa("NoActa");
		curp.setNoFoja("NoFoja");
		curp.setNoLibro("NoLibro");
		curp.setNoTomo("NoTomo");

		documentos.add(curp);

		try {
			List<DocumentoProbatorio> documentosGuardados = remote
					.registrarDocumentos(documentos);

			Iterator<DocumentoProbatorio> it = documentosGuardados.iterator();
			DocumentoProbatorio doc = null;
			while (it.hasNext()) {
				doc = it.next();

				System.out.println(doc.toString());
				System.out.println(doc.getIdDocumentoProbatorio());
			}

		} catch (RegistrarDocumentoProbatorioException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// @Test
	public void testConsultarDocumentosDePersona() throws NamingException {

		DocumentoProbatorioServiceBusinessRemote remote = (DocumentoProbatorioServiceBusinessRemote) this
				.lookUp("documentoProbatorioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote");

		Persona persona = new Persona();
		// persona.setIdPersona(25128697L);
		persona.setIdPersona(25129044L);
		// persona.setIdPersona(1L);

		try {
			List<DocumentoProbatorio> documentos = remote
					.consultarDocumentosDePersona(persona);

			Iterator<DocumentoProbatorio> it = documentos.iterator();
			DocumentoProbatorio doc = null;
			while (it.hasNext()) {
				doc = it.next();

				System.out.println(doc.toString());

				if (doc instanceof Nacimiento) {
					Nacimiento n = (Nacimiento) doc;
					System.out.println(n.getMunicipio());
				}

			}

		} catch (PersonaSinDocumentosException e) {
			e.printStackTrace();
		}
	}

}
