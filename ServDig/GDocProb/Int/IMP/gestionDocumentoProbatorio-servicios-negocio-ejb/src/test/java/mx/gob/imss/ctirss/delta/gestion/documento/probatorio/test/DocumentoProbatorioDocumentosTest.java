package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.test;


import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.RegistrarDocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.test.utils.displayUtils;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;


import org.junit.Test;





public class DocumentoProbatorioDocumentosTest extends DeltaTestCaseBase {

	private static final String OUTPUT_FILE_IMAGE_URI = "C:/test/testOutput.pdf";
	private static final Long TRAMITE =new Long("204");
	private static final String DOCUMENTO_PROBATORIO_SERVICE_BUSSINESS_LOOKUP="documentoProbatorioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote";
	DocumentoProbatorioServiceBusinessRemote remote ;
	
	
	public DocumentoProbatorioServiceBusinessRemote getRemote() throws NamingException {
		this.remote=(DocumentoProbatorioServiceBusinessRemote) this
		.lookUp(DOCUMENTO_PROBATORIO_SERVICE_BUSSINESS_LOOKUP);
		return remote;
	}
	
	@Test
	public void testGuardaYMuestroaDocumentoDigitalizado() throws NamingException, IOException{
		DocumentoProbatorio dP=new DocumentoProbatorio();
		dP.setDigitalizacion(DocumentoProbatorioHardData.getLocalImagen());
		dP.setCifrado("GIF");// tipo de imagen original	
		try {
			dP=this.getRemote().registraDocumento(dP);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("idDocumentoProbatorio: " + dP.getIdDocumentoProbatorio() );
		dP=this.getRemote().getDocumentoProbatorioBytes(dP.getIdDocumentoProbatorio().longValue());
		byte[] dig=dP.getDigitalizacion();
		FileOutputStream fileOuputStream= new FileOutputStream(OUTPUT_FILE_IMAGE_URI);
		fileOuputStream.write(dig);
		fileOuputStream.close();
		System.out.println("file succes wirite in:" + OUTPUT_FILE_IMAGE_URI);
	}


	@Test
	public void testActasGeneral() throws RegistrarDocumentoProbatorioException, NamingException{
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getActa(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getActa(i++)));
		//...
		//guarda
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	@Test
	public void testAcuerdo() throws RegistrarDocumentoProbatorioException, NamingException{
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getAcuerdo(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getAcuerdo(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testComprobanteDomicilio() throws RegistrarDocumentoProbatorioException, NamingException{
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getComprobanteDomicilio(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getComprobanteDomicilio(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//CartillaMilitarParser.java
	@Test
	public void testCartillaMilitar() throws RegistrarDocumentoProbatorioException, NamingException{
		
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getCartillaMilitar(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getCartillaMilitar(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//CedulaProfecionalParser.java
	@Test
	public void testCedulaProf() throws RegistrarDocumentoProbatorioException, NamingException{
		
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getCedulaProf(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getCedulaProf(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//CertificadoNacimientoParser.java
	@Test
	public void testCertificadoNacimiento() throws RegistrarDocumentoProbatorioException, NamingException{
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getCertificadoNacimiento(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getCertificadoNacimiento(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//CertificadoSituacionCriticaParser.java
	@Test
	public void testCitCrit() throws RegistrarDocumentoProbatorioException, NamingException{
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getCertCitCrit(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getCertCitCrit(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//ConstanciaEstudioParser.java
	@Test
	public void testConstEst() throws RegistrarDocumentoProbatorioException, NamingException{
		
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getConstanciaEstudios(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getConstanciaEstudios(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//CurpParser.java
	@Test
	public void testCurp() throws RegistrarDocumentoProbatorioException, NamingException{
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getCurp(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getCurp(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//DictamenIntegranteIncapacitadoParser.java
	@Test
	public void testDictIntInc() throws RegistrarDocumentoProbatorioException, NamingException{
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getDictIntegranteInc(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getDictIntegranteInc(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	//IfeParser.java
	@Test
	public void testIfe()throws RegistrarDocumentoProbatorioException, NamingException{
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getIfe(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getIfe(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//NacimientoParser.java
	@Test
	public void testNacimiento() throws RegistrarDocumentoProbatorioException, NamingException{
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getNacimiento(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getNacimiento(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//ObstetricoParser.java
	@Test
	public void testObstetrico() throws RegistrarDocumentoProbatorioException, NamingException{
		
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getObstetrico(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getObstetrico(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//PasaporteParser.java
	@Test
	public void testPasaporte() throws RegistrarDocumentoProbatorioException, NamingException{
		
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getPasaporte(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getPasaporte(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//VigenciaTemporalParser.java
	@Test
	public void testVigTemp() throws RegistrarDocumentoProbatorioException, NamingException{
		int i=1;
		List<DocumentoProbatorio> documentoProbatorios=new ArrayList<DocumentoProbatorio>();
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getVigTemp(i++)));
		documentoProbatorios.add(DocumentoProbatorioHardData.getDocumentoProv(DocumentoProbatorioHardData.getVigTemp(i++)));
		//...
		
		try {
			this.getRemote().procesaDocumentos(TRAMITE, documentoProbatorios);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetDocumentosProbatoriosTramite() throws NamingException{
		//trae lo guardado
		
		List<DocumentoProbatorio> documentoProbatorios=this.getRemote().listaDocumentosProbatoriosTramite(1L);
		displayUtils.displayBeanList(documentoProbatorios);
	}
	@Test
	public void testDocumentoProbatorio() throws NamingException{
		try {
			this.getRemote().getDocumentoProbatorio(12151L);
		} catch (DocumentoProbatorioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testDocumentoProbatorioBytes() throws NamingException{
		this.getRemote().getDocumentoProbatorioBytes(12151L);
	}
	
	
}

