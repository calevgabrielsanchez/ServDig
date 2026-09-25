package mx.gob.imss.ctirss.delta.gestion.patronal.test.idse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.IDSEQueueProducerRemote;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal.PersonaIDSE;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal.RegistroIDSE;

public class IntegracionIdseTest {
	
	private static final Logger log = LoggerFactory.getLogger(IntegracionIdseTest.class);

	//Este es el bueno para enviar NRP a IDSE ;)
	@Test
	public void encolarMensajeIDSE(){
		IDSEQueueProducerRemote idseService;
		log.debug("::: Recuperando servicio");
		idseService = EJBLocator.getIDSEProducer();
		log.debug("::: Se recupero EJB servicio: {}", idseService);
		log.debug("::: Encolando solicitud a IDSE");
		List<String> folios = new ArrayList<String>();

		folios.add("17418908992811344853323");


		for(String folio:folios){
			log.debug("Encolando folio: " + folio);
			idseService.encolarMensajeIDSE(folio);			
		}
		log.debug("::: Termine");
	}
	    
  //  @Test
    public void generarRegistroIdse() {
    	
      	AltaPatronalIDSEIntegrador service;
    	log.debug("::: Recuperando servicio");
        service = EJBLocator.getAltaIdseServiceBusiness();
        log.debug("::: Se recupero EJB servicio: {}", service);
        
		List<String> folios = new ArrayList<String>();

		folios.add("17283154825801237065427");
		folios.add("17273047564521229227686");
		folios.add("17283572254991237685989");
		folios.add("17283354509381237417893");
		folios.add("17284067585551238052735");
		folios.add("17284045714191238010766");
		folios.add("17284130750681238170941");
		folios.add("17284147742191238199215");
		folios.add("17284157316761238215194");
		folios.add("17260005352691220308424");
		folios.add("17285741485621239747178");
		folios.add("17286104007981240267283");
		folios.add("17284271429991238387363");
		folios.add("17286617534981240562733");		

		try {
			for(String folio:folios){
				log.debug("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::Consulando folio: " + folio);
				RegistroIDSE  idse = service.prepararDatosMovimientosIdse(folio);
				System.out.println(":: Se se llama metodo");
				System.out.println("NRP|" + idse.getNrp());
				System.out.println("Rfc|" + idse.getPatron().getRfc());
				System.out.println("ClaveSerial|" + idse.getPatron().getCertificado().getClaveSerial());
				System.out.println("RazonSocial|" + idse.getRazonSocial());
				System.out.println("DomicilioCentroTrabajo|" + idse.getDomicilioCentroTrabajo());
				System.out.println("CveDelegacion|" + idse.getCveDelegacion());
				System.out.println("CveSubdelegacion|" + idse.getCveSubdelegacion());
				System.out.println("CveSector|" + idse.getCveSector());
				System.out.println("CveMunicipio|" + idse.getCveMunicipio());
				System.out.println("Fraccion|" + idse.getFraccion());
				System.out.println("Clase|" + idse.getClase());
				System.out.println("Correo|" + idse.getCorreo());
				System.out.println("::RL::|RL");
				if(idse.getRepresentantes() != null && idse.getRepresentantes().length > 0) {
					for (int i = 0; i < idse.getRepresentantes().length; i++) {
						PersonaIDSE pi = idse.getRepresentantes()[i];
						System.out.println("Rfc|" + pi.getRfc());
						System.out.println("NombreUsuario|" + pi.getNombreUsuario());
						System.out.println("NombreRazonSocial|" + pi.getNombreRazonSocial());
						System.out.println("DomicilioFiscal|" + pi.getDomicilioFiscal());
						System.out.println("CorreoElectronico|" + pi.getCorreoElectronico());
						System.out.println("ClaveSerial|" + pi.getCertificado().getClaveSerial());
						System.out.println("Curp|" + pi.getCurp());
					}				
				}else {
					System.out.println("::NO SE OBTUVO RL::");
				}
				log.debug(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

			}
			
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}   	
    } 
    
  //  @Test
    public void generarRegistroIdse_() {
    	
      	AltaPatronalIDSEIntegrador service;
    	log.debug("::: Recuperando servicio");
        service = EJBLocator.getAltaIdseServiceBusiness();
        log.debug("::: Se recupero EJB servicio: {}", service);
        
		List<String> folios = new ArrayList<String>();

		folios.add("17256520891551217716730");

		try {
			for(String folio:folios){
				log.debug("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::Consulando folio: " + folio);
				RegistroIDSE  idse = service.prepararDatosMovimientosIdse(folio);
				System.out.println(":: Se se llama metodo");
				System.out.println("NR: " + idse.getNrp());
				System.out.println("Rfc: " + idse.getPatron().getRfc());
				System.out.println("ClaveSerial: " + idse.getPatron().getCertificado().getClaveSerial());
				System.out.println("RazonSocial: " + idse.getRazonSocial());
				System.out.println("DomicilioCentroTrabajo: " + idse.getDomicilioCentroTrabajo());
				System.out.println("CveDelegacion: " + idse.getCveDelegacion());
				System.out.println("CveSubdelegacion: " + idse.getCveSubdelegacion());
				System.out.println("CveSector: " + idse.getCveSector());
				System.out.println("CveMunicipio: " + idse.getCveMunicipio());
				System.out.println("Fraccion: " + idse.getFraccion());
				System.out.println("Clase: " + idse.getClase());
				System.out.println("Correo: " + idse.getCorreo());
				System.out.println("::RL::");
				if(idse.getRepresentantes() != null && idse.getRepresentantes().length > 0) {
					for (int i = 0; i < idse.getRepresentantes().length; i++) {
						PersonaIDSE pi = idse.getRepresentantes()[i];
						System.out.println("Rfc: " + pi.getRfc());
						System.out.println("NombreUsuario: " + pi.getNombreUsuario());
						System.out.println("NombreRazonSocial: " + pi.getNombreRazonSocial());
						System.out.println("DomicilioFiscal: " + pi.getDomicilioFiscal());
						System.out.println("CorreoElectronico: " + pi.getCorreoElectronico());
						System.out.println("ClaveSerial: " + pi.getCertificado().getClaveSerial());
						System.out.println("Curp: " + pi.getCurp());
					}				
				}else {
					System.out.println("::NO SE OBTUVO RL::");
				}
				log.debug(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

			}
			
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}   	
    } 
    
}
