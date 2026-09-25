package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.BuzonClasificacion;

public class BuzonClasTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(BuzonClasTest.class);
	}	

	@Test
	public void recuperaMensaje(){
		try {
			log.info("::: Obteniendo EJB ");
			BuzonClasificacion buzon = EjbLocator.getActividadEcServiceRemote()
					.consultaMensajeBuzon("A0562149102");
			log.info("::: Se recupero el mensaje");
			log.info(buzon.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void insertaMensaje(){
		try {
			log.info("::: Obteniendo EJB ");
			EjbLocator.getActividadEcServiceRemote().insertaMensajeBuzon("C2237336104", "175", "12323456789", "Mensaje actualizado");
			log.info("::: Se inserto el mensaje");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
