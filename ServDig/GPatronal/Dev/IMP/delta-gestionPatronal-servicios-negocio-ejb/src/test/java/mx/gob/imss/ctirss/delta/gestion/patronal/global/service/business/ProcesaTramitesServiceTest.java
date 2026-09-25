package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.business;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
//import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.ProcesaTramitesServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;

public class ProcesaTramitesServiceTest {
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(ProcesaTramitesServiceTest.class);
	}

	@Test
	public void testProcesaTramitesEnProceso() {

		LOG.debug(":: Obteniendo EJB");
		
//		ProcesaTramitesServiceRemote ejb = EJBLocator.getProcesaTramitesService();
//
//		try {
//			LOG.debug(":: Ejecutando servicio para procesar tramites");
//			ejb.procesaSolicitudes_AP_EnProceso_SinDocumentos();
//		} catch (GestionPatronalBusinessException e) {
//			e.printStackTrace();
//		}
		
		LOG.debug(":: FIN");
	}
	
	
	
	
}
