package mx.gob.imss.ctirss.delta.gestion.patronal.test.idse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.IDSEQueueProducerRemote;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal.RegistroIDSE;
import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.service.interfaces.RegistroPatronalIdseServiceBusinessRemote;


public class EnviaIDSETest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(EnviaIDSETest.class);
	}
	
	
	@Test
	public void enviapatronIDSE(){

		IDSEQueueProducerRemote idseService;
		log.debug("::: Recuperando servicio");
		idseService = EJBLocator.getIDSEProducer();
		log.debug("::: Se recupero EJB servicio: {}", idseService);
		log.debug("::: Encolando solicitud a IDSE");
		List<String> folios = new ArrayList<String>();
		folios.add("17230876531481198433912");
		for(String folio:folios){
			idseService.encolarMensajeIDSE(folio);			
		}
		log.debug("::: Termine");
		
//		try {
//			AltaPatronalIDSEIntegrador ejb = EjbLocator.obtenerIDSEService();
//			System.out.println("::: Ejb recuperado");
//			//Buscamos por folio de solicitud el tramite de alta para recuperar el patron
//			RegistroIDSE  regIdse = ejb.prepararDatosMovimientosIdse("17175358692841155599251");
//			System.out.println("::: Patron recuperado : " + regIdse.getNrp() + " - " + regIdse.getRazonSocial());
//			RegistroPatronalIdseServiceBusinessRemote ejbIdse = EjbLocator.getRegistroPatronalIdseServiceBusiness();
//			System.out.println("::: Ejb recuperado");
//			//seteamos el patron recuperado a el VO de IDSE
//			RegistroPatronal regPat = new RegistroPatronal();
//			regPat.setActividad(regIdse.getActividad());
//			//Ejecutamos servicio para enviar a IDSE el patron recuperado
//			ejbIdse.altaRegistroPatronal(regPat);
//			System.out.println("::: Patron enviado verificar en BD de IDSE");
//		}catch(Exception e){
//			e.printStackTrace();
//		}

	
	}
	

}
