package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovtoPatSujetoObligadoBusinessRemote;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;

public class MovtoPatSujetoObligadoBusinessLookupTest {

    private static final Logger log = LoggerFactory.getLogger(MovtoPatSujetoObligadoBusinessLookupTest.class);
    
    private MovtoPatSujetoObligadoBusinessRemote service = null;

    @Before
    public void setUp() throws NamingException {
        service = EjbLocator.getMovtoPatSujetoObligadoBusiness();
    }

    @Test
    @Ignore
    public void testListMovsCurrentDate() {
        log.info("testListMovsCurrentDate");
        log.info("{}", service.listMovtosCurrentDate());
    }

    @Test
    @Ignore
    public void testStop() {
        log.info("Stoping the timer");
        service.stopTimer();
    }

    @Test
    //@Ignore
    public void testSchedule() {
        log.info("Testing schedule");
        service.schedule(10000L);
    }

    @Test
    @Ignore
    public void testCallQueue() {
        log.info("adding message to queue");
        service.callQueue();
    }
}
