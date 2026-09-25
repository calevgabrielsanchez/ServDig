package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;


public class CentroTrabajoTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(CentroTrabajoTest.class);
	}
	
	
	@Test
	public void generaReporteSolicitud(){		
		Long idSO = 403L;		
		System.out.println("Invocando EJB");
		SujetoObligadoServiceBusinessRemote so = EJBLocator.getSujetoServiceBusiness() ;
		System.out.println("Obtuve EJB consultandpo CT");
		CentroTrabajo ct = so.getCentroTrabajo(idSO);
		System.out.println("CT:");
		System.out.println(ct);
	}
	
	

}
