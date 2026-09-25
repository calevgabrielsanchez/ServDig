package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegracionIdseTest {
	
	private static final Logger log = LoggerFactory.getLogger(IntegracionIdseTest.class);
    private AltaPatronalIDSEIntegrador service;

    @Before
    public void before() throws NamingException {
        service = EJBLocator.getAltaIdseServiceBusiness();
        log.debug("servicio: {}", service);
    }
    
    @Test
    public void generarRegistroIdse() {
    	
    	String folio = "1409607410397710041";
    	
		try {
			this.service.prepararDatosMovimientosIdse(folio);
			
		} catch (SolicitudNoEncontradaException e) {
			log.error(e.getMessage());
		}   	
    }
}
