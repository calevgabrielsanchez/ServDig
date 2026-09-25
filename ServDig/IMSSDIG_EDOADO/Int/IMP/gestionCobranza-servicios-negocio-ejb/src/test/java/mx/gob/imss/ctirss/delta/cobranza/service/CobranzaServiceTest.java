package mx.gob.imss.ctirss.delta.cobranza.service;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.cobranza.modelo.ResumenEdoAdeudo;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CobranzaServiceRemote;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.EJBLocator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prueba métodos de CobranzaService
 * @author User
 *
 */
public class CobranzaServiceTest {
	
	/**
	 * Logger
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(CobranzaServiceTest.class);
	
	/**
	 * Realiza un test al método getAdeudo de CobranzaService
	 */
	@Test
	public void getAdeudoTest(){
		LOGGER.info("Comienza getAdeudo...");
		
		CobranzaServiceRemote ejb = EJBLocator.getCobranzaService();
		try {
			ResumenEdoAdeudo resumenEdoAdeudo = ejb.getAdeudo("A0110004106");
			
			LOGGER.info("resumenEdoAdeudo: "+resumenEdoAdeudo);
			LOGGER.info("resumenEdoAdeudo.getExisteAdeudo(): "+resumenEdoAdeudo.getExisteAdeudo());
			LOGGER.info("resumenEdoAdeudo.getAdeudoImss(): "+resumenEdoAdeudo.getAdeudoImss());
		} catch (EstadoAdeudoException eaEx) {
			LOGGER.error("Error: "+eaEx.getLocalizedMessage());
		}
	}
}