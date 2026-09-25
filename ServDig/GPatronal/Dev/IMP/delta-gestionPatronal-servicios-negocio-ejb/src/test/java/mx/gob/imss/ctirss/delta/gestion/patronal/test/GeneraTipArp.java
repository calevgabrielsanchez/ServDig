package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

public class GeneraTipArp {

	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(GeneraTipArp.class);
	}

	@Test
	public void getReporeteArpPersona(){
		try {
			log.debug(":::: Inicio");
			SolicitudServiceBusinessRemote solicitudServiceBusiness =  EjbLocator.getSolicitudServiceBusiness();
			ArpBusinessRemote arpBusiness = EjbLocator.getARPBusiness();
			
			String ruta = "C:\\logs\\arp\\";
			int[] idSol = {
					933387167
			};
			Solicitud solicitud = null;
			for (int i = 0; i < idSol.length; i++) {
				log.debug(":::: "+i+" - Voy a consultar solicitud idSol: " + idSol[i]);			
				solicitud = solicitudServiceBusiness.consultarSolicitudPorId(new Long(idSol[i]));
				log.debug(":::: "+i+" - Obteniendo documento de: " + solicitud.getNoFolioSolicitud());

				//Obtener ARP
				byte[] reportaARP = (byte[]) arpBusiness.getReporeteArpPersona(solicitud);
				FileOutputStream fos = new FileOutputStream(ruta + solicitud.getNoFolioSolicitud() + ".pdf");
				fos.write(reportaARP);
				fos.close();				

				//Obtener TIP
//				byte[] reportaTIP = (byte[]) arpBusiness.getReporteTipPersoa(solicitud);
//				FileOutputStream fosT = new FileOutputStream(ruta + solicitud.getNoFolioSolicitud() + "-TIP.pdf");
//				fosT.write(reportaTIP);
//				fosT.close();				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.debug(":::: Termine");
	}	
	
	
//	@Test
	public void generaDocumentoResultante() {
		
		String[] arr = {						
				"74961383|81650688"
				//"74883896|81574996"
		};
		
		for (int i = 0; i < arr.length; i++) {
			String[] val = arr[i].split("\\|");
			log.debug("::: Posicion: " + (i+1) + " de " + arr.length);
			log.debug("Inicia generacion docs: val[0]: " + val[0] + " - val[1]: " + val[1]);

			Long idSol = new Long(val[0]);
			Long idTramite = new Long(val[1]);
			Integer tipoDoc = new Integer("1"); // 56-ARP 55-TIP
			
			for(int x=0; x<2; x++){
				if(x==0){
					tipoDoc = new Integer("55");						
				}else{
					tipoDoc = new Integer("56");
				}
				log.error("Inicia generacion de docto val[0]: " + val[0] + " - val[1]: " + val[1] + ", tipoDoc: " + tipoDoc);
				
				FileOutputStream fos = null;
				
				try {
					log.debug("Obteniendo ejb");
					SolicitudBusinessRemote ejb = EJBLocator.getSolicitudBusinessRemote();
					if(ejb != null){
						log.debug("Recupere ejb");
					}else{
						log.debug("Ejb null");
					}
					
					byte[] byteArray = ejb.obtenerDocumentoResultante(idSol, idTramite, tipoDoc);
		
					if(byteArray == null){
						log.debug("::::: El documento viene vacio val[0]: " + val[0] + " - val[1]: " + val[1]);
					}
					
					String tipo = "";
					if(tipoDoc.intValue() == 56){
						tipo = "ARP";
					}else if(tipoDoc.intValue() == 55){
						tipo = "TIP";
					}			
										
					fos = new FileOutputStream("c:\\salidasLogs\\"+tipo+"-"+idSol+".pdf");
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
				}finally{
					fos = null;
				}
				
				log.error("Finaliza generacion de docto val[0]: " + val[0] + " - val[1]: " + val[1] + ", tipoDoc: " + tipoDoc);
				
			}
		
		}
		
		log.error("::::::: Termino proceso");

	}

	//@Test
	public void escribeARP(){
		try {
			String ruta = "C:\\logs\\arp\\arpTest.pdf";
			byte[] reportaARP=EjbLocator.getARPBusiness().getArpPersona("1589304808010458734970");
			FileOutputStream fos = new FileOutputStream(ruta);
			fos.write(reportaARP);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void getTIP() {
		
		//String idSol = "74877461"; // SUB pendiente
		//String idSol = "74877367"; //SUB terminado
		//String idSol = "74877289"; //Alta	
		
		String idSol = "74877500"; // SUB pendiente
		
		FileOutputStream fos = null;

		try{
			
			log.debug(":::: Voy a consultar solicitud idSol: " + idSol);			
			Solicitud solicitudAlta = EjbLocator.getSolicitudServiceBusiness().consultarSolicitudPorId(new Long(idSol));
			log.debug(":::: Folio: " + solicitudAlta.getNoFolioSolicitud());
			Tramite tr = getTr(solicitudAlta);
			log.debug(":::: Tramite: " + tr.getTramiteId());
			
			
			byte[] byteArray = EjbLocator.getARPBusiness().getTipPersona(solicitudAlta.getNoFolioSolicitud(),tr);

			if(byteArray == null){
				log.debug("::::: El documento viene vacio");
			}else{
				fos = new FileOutputStream("c:\\salidasLogs\\TIP-"+idSol+".pdf");
				fos.write(byteArray);
			}	
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			fos = null;
		}
			
		log.error("::::::: Termino proceso");

	}
	
	
	private Tramite getTr(Solicitud sol){
		Tramite tramite = null;
		
		for(Tramite t : sol.getTramites()){
			if(t.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue() ||
					t.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue() ||
					t.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo().intValue()){
			
				tramite = t;
				log.debug("Id-Tramite - "
						+ sol.getSolicitudId()
						+ "|"
						+ tramite.getTramiteId()
						+ " - "
						+ sol.getEstadoSolicitud().getIdEstadoSolicitud()
						+ " - "
						+ sol.getEstadoSolicitud().getDescripcion()
						+ " - "
						+ tramite.getEstadoTramite()
								.getIdEstadoTramitePersona() + "-"
						+ tramite.getEstadoTramite().getDescripcion());
				break;
			}
		}
		
		return tramite;
	}
	
}
