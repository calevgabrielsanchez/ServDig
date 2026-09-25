package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;

public class GestionDomiciliosTest {
    private static final Logger log = LoggerFactory.getLogger(GestionDomiciliosTest.class);

    private DomicilioServiceBusinessRemote service;

    @Before
    public void before() throws NamingException {
        service = EjbLocator.getDomicilioService();
        log.debug("Obtuve servicio EJB: {}", service);
    }

    @Test
    public void obtenerListaPatronesPorPersona(){
    	log.debug("::: Iniciando, " + new Date());

    	Municipio arg0 = new Municipio();
    	String arg1 = "24464";

    	try {
    		List<MunicipioIMSS> listM = service.getMunicipioIMSSbyEstadoMunCP(arg0, arg1);
			log.debug("::: Obtuve " + listM.size() + " municipios");
			for (Iterator<MunicipioIMSS> iterator = listM.iterator(); iterator.hasNext();) {
				MunicipioIMSS so = iterator.next();
				log.debug(so.getDescMunicipio());
				log.debug(so.getSubdelegacion().getDescripcion());
			}
		} catch (MunicipioImssNoLocalizadoException e) {
			e.printStackTrace();
		}
    	
    	log.debug("::: FIN, " + new Date());
    }
    
}

