package mx.gob.imss.ctirss.delta.gestion.patronal.test.mac;


import java.io.FileOutputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;


public class RTTTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(RTTTest.class);
	}
	
	
//	@Test
//	public void escritoDesacuerdo(){
//
//		Long idSol = new Long(74971460);
//		Solicitud solicitud = new Solicitud();
//		
//		try {
//			solicitud = EJBLocator.getServiceBusiness().consultarSolicitudPorId(idSol);			
//			System.out.println("IdSolicitud: " + solicitud.getSolicitudId() + "-" + " : "
//					+ solicitud.getTipoSolicitud().getDescripcion() + ", "
//					+ solicitud.getEstadoSolicitud().getDescripcion() + ", " + solicitud.getFechaActualizacion());
//			System.out.println("::: Obteniendo EJB de escrito desacuerdo");
////			EscritoDesacuerdoBusinessRemote ejb = EJBLocator.getSEscritoDesacuerdoBusiness();
////			ejb.finalizarSolicitudEscrito(solicitud);
//		} catch (SolicitudNoValidaException e) {
//			e.printStackTrace();
//		} catch (SolicitudNoEncontradaException e) {
//			e.printStackTrace();
//		}
//	
//	}	
	
	
	
//	@Test
	public void generaComprobante(){

		FileOutputStream fos = null;
		String folio = "1653593725748706211568";
		Long idSol = new Long("706211568");

		try {
			Solicitud solicitud = new Solicitud();		
			solicitud.setNoFolioSolicitud(folio);
//			solicitud = EJBLocator.getSolicitudBusinessRemote().consultarFolio(solicitud);
			solicitud = EJBLocator.getServiceBusiness().consultarSolicitudPorId(idSol);			


			System.out.println("IdSolicitud: " + solicitud.getSolicitudId() + "-" + " : "
					+ solicitud.getTipoSolicitud().getDescripcion() + ", "
					+ solicitud.getEstadoSolicitud().getDescripcion() + ", " + solicitud.getFechaActualizacion());
			
			System.out.println(solicitud.toString());
		
//			EscritoDesacuerdoBusinessRemote ejb = EJBLocator.getSEscritoDesacuerdoBusiness();
//			System.out.println(":: Obteniendo EJB de RTT");
//			byte[] byteArray = ejb.generarAcuseEscritoDesacuerdoPDF(solicitud);
//			if(byteArray == null){
//				log.debug("::::: El documento viene vacio");
//			}
//			fos = new FileOutputStream("c:\\salidasLogs\\RTT-"+folio+".pdf");
//			fos.write(byteArray);
			
			System.out.println("Termine");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			fos = null;
		}
		
	}


	
}
