package mx.gob.imss.cit.clienteServiciosComunes.service;

import mx.gob.imss.cit.clienteServiciosComunes.helper.CodecHelper;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.Documento;
import mx.gob.imss.cit.clienteServiciosComunes.model.Tramite;
import mx.gob.imss.cit.clienteServiciosComunes.model.Usuario;
import mx.gob.imss.cit.clienteServiciosComunes.services.BovedaPersonalService;
import mx.gob.imss.cit.test.BaseTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BovedaPersonalServiceTest extends BaseTest {

	private Tramite tramite;
	private Usuario usuario;
	private Documento documento;

	@Autowired
	private CodecHelper codecHelper;

	@Autowired
	private BovedaPersonalService bovedaPersonalService;

	@Before
	public void init() {
		tramite = new Tramite();
		tramite.setFolioTramite("45455IR");
		documento = new Documento();
		documento.setNombreArchivo("curpTest.txt");
		documento.setExtencion("txt");
		documento.setMimeType("text/plain");
		documento.setEncriptado(Boolean.FALSE);
		documento.setFolder(Boolean.FALSE);
		usuario = new Usuario();
		usuario.setIdUsr("GUHR830217H39");
		usuario.setOwner(false);
		usuario.setTipoIdUsr("RFC");
	}

	@Test
	public void crearDocumento() {
		CreateDocumentReq createDocumentReq = new CreateDocumentReq();
		String txt = buildStringWithRandomCharacters(15);
		System.out.println("texto documento >> " + tramite.getFolioTramite());
		System.out.println("texto documento >> " + documento.getNombreArchivo());
		documento.setArchivo(txt.getBytes());
		createDocumentReq.setDocumento(documento);
		createDocumentReq.setTramite(tramite);
		createDocumentReq.setUsuario(usuario);

		CreateDocumentRes createDocumentRes = bovedaPersonalService
				.createDocument(createDocumentReq);

		if (createDocumentRes.getRespuestaBoveda().isExito()) {
			System.out.println("insertado");

		} else {
			System.out.println("Error en createDocument :::");
			StringBuilder sb = new StringBuilder();
			sb.append(createDocumentRes.getRespuestaBoveda().getCodigoError());
			sb.append("-");
			sb.append(createDocumentRes.getRespuestaBoveda()
					.getDescripcionError());
			System.out.println("boveda.error.StringBuilder=" + sb.toString());
		}
		
		createDocumentRes = bovedaPersonalService
				.createDocument(createDocumentReq);
		
		createDocumentRes = bovedaPersonalService
				.createDocument(createDocumentReq);
		
		createDocumentRes = bovedaPersonalService
				.createDocument(createDocumentReq);
		
		createDocumentRes = bovedaPersonalService
				.createDocument(createDocumentReq);
		
		createDocumentRes = bovedaPersonalService
				.createDocument(createDocumentReq);
		
		
		documento = new Documento();
		documento.setNombreArchivo("curpTestRuben.txt");
		documento.setExtencion("txt");
		documento.setMimeType("text/plain");
		documento.setEncriptado(Boolean.FALSE);
		documento.setFolder(Boolean.FALSE);
		createDocumentReq = new CreateDocumentReq();
		txt = buildStringWithRandomCharacters(15);
		System.out.println("texto documento >> " + tramite.getFolioTramite());
		System.out.println("texto documento >> " + documento.getNombreArchivo());
		documento.setArchivo(txt.getBytes());
		createDocumentReq.setDocumento(documento);
		createDocumentReq.setTramite(tramite);
		createDocumentReq.setUsuario(usuario);

		createDocumentRes = bovedaPersonalService
				.createDocument(createDocumentReq);

		if (createDocumentRes.getRespuestaBoveda().isExito()) {
			System.out.println("insertado");

		}
		
		
	}

	@Test
	public void obtenerDocumento() {
		DocumentReq documentReq = new DocumentReq();
		documentReq.setDocumento(documento);
		documentReq.setTramite(tramite);
		documentReq.setUsuario(usuario);
		DocumentRes documentRes = bovedaPersonalService
				.getDocument(documentReq);

		if (documentRes.getRespuestaBoveda().isExito()) {
			System.out.println("encontrado");
			System.out.println("contenido documento encontrado >> "
					+ new String(documentRes.getDocumento().getArchivo()));
		} else {
			System.out.println("Error en getDocumentById :::");
			StringBuilder sb = new StringBuilder();
			sb.append(documentRes.getRespuestaBoveda().getCodigoError());
			sb.append("-");
			sb.append(documentRes.getRespuestaBoveda().getDescripcionError());
			System.out.println("boveda.error.StringBuilder=" + sb.toString());
		}
	}

	@Test
	public void eliminarDocumento() {
		DeleteDocumentReq deleteDocumentReq = new DeleteDocumentReq();
		deleteDocumentReq.setDocumento(documento);
		deleteDocumentReq.setTramite(tramite);
		deleteDocumentReq.setUsuario(usuario);
		DeleteDocumentRes deleteDocumentRes = bovedaPersonalService
				.deleteDocument(deleteDocumentReq);

		if (deleteDocumentRes.getRespuestaBoveda().isExito()) {
			System.out.println("eliminado");
		} else {
			System.out.println("Error en getDocumentById :::");
			StringBuilder sb = new StringBuilder();
			sb.append(deleteDocumentRes.getRespuestaBoveda().getCodigoError());
			sb.append("-");
			sb.append(deleteDocumentRes.getRespuestaBoveda()
					.getDescripcionError());
			System.out.println("boveda.error.StringBuilder=" + sb.toString());
		}
	}

}
