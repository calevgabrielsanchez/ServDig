package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;
import org.junit.Test;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote;

public class ConcluirAltaPatronalBusinessTestIt {

    private static final Logger log = LoggerFactory.getLogger(ConcluirAltaPatronalBusinessTestIt.class);

    private ConcluirAltaPatronalBusinessRemote concluirAltaPatronalBusiness;

    @Before
    public void setUp(){
        log.info("obteniendo referencia a ConcluirAltaPatronalBusinessRemote");
        concluirAltaPatronalBusiness = EjbLocator.getConcluirAltaPatronalBusiness();
        log.info("OK, referencia obtenida");
    }

    @Test
    public void testConcuirAltaPatronal() {
        log.info("Realizando la prueba de conculir alta patronal");
        concluirAltaPatronalBusiness.concluirAltaPatronal("Z3020011105", 1079L);
        //concluirAltaPatronalBusiness.concluirAltaPatronal("Y5838205101", 26003325L);
    }

}

