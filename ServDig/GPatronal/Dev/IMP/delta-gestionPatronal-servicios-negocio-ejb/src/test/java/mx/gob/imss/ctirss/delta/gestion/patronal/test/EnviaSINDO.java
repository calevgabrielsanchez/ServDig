package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AltaPatronalHelperRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

public class EnviaSINDO {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(EnviaSINDO.class);
	}	

	
	@Test
	public void generaArchivoSindo(){


		String rp = "Z3930364107";
		Long idSol = new Long("1333690494");

		try {
			log.info("::: Obteniendo EJB y enviando movimiento");
			EjbLocator.getConcluirAltaBusiness().concluirAltaPatronal(rp, idSol);
			log.info("::: Se envió el patrón");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
