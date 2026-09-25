package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;


public class DomicilioTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(DomicilioTest.class);
	}
	
	
	@Test
	public void buscaColoniaPorCP(){		
		System.out.println("Invocando EJB");
		DomicilioServiceBusinessRemote ejb = EJBLocator.getDomicilioService() ;
		System.out.println("Obtuve EJB ");
		
		CodigoPostal codigoPostal = new CodigoPostal();
		codigoPostal.setCodigoPostal("24460");
		
		List<Asentamiento> asentamientos;
		try {
			asentamientos = ejb.getAsentamientoPorCodigoPosta(codigoPostal);
			System.out.println("::: Imprimiendo asentamientos");
			for (Iterator<Asentamiento> iterator = asentamientos.iterator(); iterator.hasNext();) {
				Asentamiento asentamiento = iterator.next();
				System.out.println(asentamiento.getNombre());
			}
		
		} catch (DomicilioNoLocalizadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("::: FIN");
	}
	
	

}
