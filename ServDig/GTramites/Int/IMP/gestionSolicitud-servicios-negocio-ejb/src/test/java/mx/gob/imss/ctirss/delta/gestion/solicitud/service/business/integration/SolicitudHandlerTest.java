package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import static org.junit.Assert.fail;

import java.io.IOException;

import mx.gob.imss.ctirss.delta.gestion.solicitud.service.test.EjbLocator;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolicitudHandlerTest {

	private static final Logger log = LoggerFactory
			.getLogger(SolicitudHandlerTest.class);
	private String folio = "13842952440332842";
//	private SolicitudHandlerRemote handler;

	@Before
	public void setUp() {
//		handler = EjbLocator.getSolicitudHandler();
//		log.debug("servicio: {}", handler);
	}
	
	@Test
	public void testPublicarCometSession() throws IOException {
//		handler.publicarFinProcesamientoSolicitud(folio, true, "");
		fail("No llamaste al simplePublish");
	}
}
