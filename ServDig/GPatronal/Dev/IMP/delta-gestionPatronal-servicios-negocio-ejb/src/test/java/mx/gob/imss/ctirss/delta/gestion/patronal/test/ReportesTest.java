package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoTramiteEnum;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReportesTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(ReportesTest.class);
	}
	
	
	@Test
	public void obtieneDoctos(){
		List<Long> tiposTramite = new ArrayList<Long>();
		List<Map<String, Object>> documentos = null;
		String idTipoTramite = "175";
		String[] tiposTramites = idTipoTramite.split(",");
		for(String cveTipoT: tiposTramites) {
			tiposTramite.add(new Long(cveTipoT));
		}
		
		List<Long> tiposPersona = new ArrayList<Long>();
		tiposPersona.add(new Long(0)); // tipo persona default para documentos que son para PF y PM
		tiposPersona.add(new Long(2)); //tipo de persona del tramite
		
		try {
			log.info("::: Obteniendo EJB y consultando docs");
//			documentos = EjbLocator.getDoctoProbService().getDocumentosClasificadosPorTipoTramite(tiposTramite, tiposPersona);
			log.info("::: Se envi� el patr�n");
			for (Iterator iterator = documentos.iterator(); iterator.hasNext();) {
				Map<String, Object> map = (Map<String, Object>) iterator.next();
				System.out.println(map.toString());
			}
			log.info("::: Termine");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	
	@Test
	public void generaReporteSolicitud(){
//		Long idSolicitud = 6458L;//5364l se puede usar esta tambien
//		TipoDocumentoTramiteEnum tipoDocumento = TipoDocumentoTramiteEnum.COMPROBANTE;
//		EJBLocator.getSolicitudBusinessRemote().obtenerDocumento(idSolicitud, tipoDocumento.getCodigo());
//		log.error("Finaliza generacion de docto");
		
		Integer x = null;
		
		System.out.println(x != null ? "NO es null" : "Es nukll");
		
	}
	
	
	//@Test
	public void generaReporteArp(){
		String folio="145772369564340178616";
		
		try {
			byte[] byteArray = EJBLocator.getARPBusiness().getArpPersona(folio);
		
	
	log.error("Finaliza generacion de docto");
	
	
	FileOutputStream fos;
	
		fos = new FileOutputStream("d:\\pruebasReportes\\salidaARP_PM.pdf");
		fos.write(byteArray);
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		log.error("Finaliza generacion de docto");
	}
	
	@Test
	public void generaReporteTIP(){
		String folio="147278662683440199520";
		
		try {
			byte[] byteArray = EJBLocator.getARPBusiness().getTipPersona(folio);
		
	
	log.error("Finaliza generacion de docto");
	
	
	FileOutputStream fos;
	
		fos = new FileOutputStream("d:\\pruebasReportes\\salidaTIP_PM.pdf");
		fos.write(byteArray);
	} catch (FileNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		log.error("Finaliza generacion de docto");
	}
	
	 @Test
		public void generAvisoModificacion(){
			Long idSolicitud = 3599256L;//5364l se puede usar esta tambien
			TipoDocumentoTramiteEnum tipoDocumento = TipoDocumentoTramiteEnum.COMPROBANTE;
			//EJBLocator.getSolicitudBusinessRemote().obtenerDocumento(idSolicitud, tipoDocumento.getCodigo());
			
			byte[] byteArray = EJBLocator.getSolicitudBusinessRemote().obtenerDocumento(idSolicitud, tipoDocumento.getCodigo());
			log.error("Finaliza generacion de docto");
			
			
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("C:\\AvisoModi.pdf");
				fos.write(byteArray);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
