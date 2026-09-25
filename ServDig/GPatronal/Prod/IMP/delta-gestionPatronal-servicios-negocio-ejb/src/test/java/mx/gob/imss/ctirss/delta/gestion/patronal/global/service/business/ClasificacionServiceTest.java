package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.model.AttachmentContent;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.ClasificacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.solicitud.SolicitudServiceEntityTest;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaVO;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasificacionServiceTest {
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(SolicitudServiceEntityTest.class);
	}

	@Test
	public void testFinalizarModificacionSRT() {
		Object object = EJBLocator.getClasificacionServiceBusiness();

		Assert.assertNotNull(object);
		Assert.assertTrue(object instanceof ClasificacionServiceRemote);

		final ClasificacionServiceRemote ejb = (ClasificacionServiceRemote) object;
		Assert.assertNotNull(ejb);
		
		try {//137850567042726004003 
			ejb.finalizarModificacionSRT(3381L);
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void testGetCalasificacionActividadEconomica(){
		Object object = EJBLocator.getSujetoServiceBusiness();

		Assert.assertNotNull(object);
		Assert.assertTrue(object instanceof SujetoObligadoServiceBusinessRemote);

		final SujetoObligadoServiceBusinessRemote ejb = (SujetoObligadoServiceBusinessRemote) object;
		Assert.assertNotNull(ejb);

		
			SujetoObligado sujeto = new SujetoObligado();
			sujeto.setNumeroRegistroPatronal("Y5649389101");
			sujeto = ejb.obtenerDetalleSujetoObligadoActividadEconomica(sujeto);
		System.err.println("sujeto: "+sujeto);

	}
	
	@Test
	public void generaEMailMessage(){
		
//		Long idSolicitud = 5892L;//5364l se puede usar esta tambien
//		TipoDocumentoTramiteEnum tipoDocumento = TipoDocumentoTramiteEnum.COMPROBANTE;
//		byte[] amsrt = EJBLocator.getSolicitudBusinessRemote().obtenerDocumento(idSolicitud, tipoDocumento.getCodigo());
		EMailProducer service = EJBLocator.getEMailQProducer();
		EmailPayloadType data = new EmailPayloadType();
		data.setTo("ignacio.espinosav@imss.gob.mx");
		data.setContent("Attachment (Comprobantes)");
		data.setSubject("Portal digital IMSS - Solicitud Atendida");
		data.setContentType("text/plain");
		AttachmentContent attachment = new AttachmentContent();
		attachment.setContentDisposition("Test.txt");
		attachment.setContentType("text/plain");
		attachment.setTextBody("dato de prueba");
		data.setAttachment(attachment);
		
		
		Map<String,String> parametros = new HashMap<String, String>();
		parametros.put("nombre", "HUGO");
		parametros.put("primerApellido", "MARTINEZ");
		parametros.put("segundoApellido", "CHAMONICA");
		parametros.put("idTipoTramite", "43");
		data.setParameters(parametros);
		
		
		service.agendarCorreoElectronico(data);
		
	}
	
	@Test
	public void padd(){
		
		StringBuffer subdelDestino= new StringBuffer();
		String delegacion = String.format("%02d", Integer.parseInt("3"));
    	String subdelegacion = String.format("%02d", Integer.parseInt("1"));
    	subdelDestino.append(delegacion).append(subdelegacion);
    	
    	System.err.println("Padding: "+subdelDestino.toString());
	}
	
	@Test 
	public void generaDoctosTest(){
		String folio="13884457149891850";
		try {
		EJBLocator.getSolicitudServiceBusiness().generarDocumentos(folio);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void generaDoctoSemanasCotizadas(){
		HldaVO datos=EJBLocator.getHLDAService().getHldaVO("43048011613");
		byte[] butes = EJBLocator.getManejadroReportes().generaReporteSemanasCotizadas(datos, new FirmaElectronica());
		
		File pdfFile = new File("outputPDF.pdf");
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(pdfFile);
			fos.write(butes);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void generaSolicitudDocumentoSemanasCotizadas(){
//		HldaVO datos=EJBLocator.getHLDAService().getHldaVO("43048011613");
		byte[] butes = EJBLocator.getComponentesExternosService().obtenerReporteDeHistoriaLaboral("92068610507", new Usuario());
		
		File pdfFile = new File("outputPDF.pdf");
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(pdfFile);
			fos.write(butes);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
