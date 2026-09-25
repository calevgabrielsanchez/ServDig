package mx.gob.imss.ctirss.delta.gestion.asegurado.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.VigenciaServiceRemote;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.EnvioCorreoResponse;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VigenciaServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(VigenciaServiceTest.class);
	
	InitialContext initialContextBDTU = null;
	
	protected void initContextBDTU() throws NamingException {
		Hashtable<String, String> hashtable = new Hashtable<String, String>(7);
//		hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		
		//QA
		hashtable.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001,vzapma07.imss.gob.mx:8003,vzapma12.imss.gob.mx:8001,vzapma12.imss.gob.mx:8003,vzapmb09.imss.gob.mx:8001,vzapmb09.imss.gob.mx:8003");
		hashtable.put(Context.SECURITY_PRINCIPAL, "weblogic");
		hashtable.put(Context.SECURITY_CREDENTIALS, "w1sd0m&53rv1d1r&wl&2014$.");
		
		//Servidor Jorge Ventura
//		hashtable.put(Context.PROVIDER_URL, "t3://servidor.jorge.imss.gob.mx:7001");
//		hashtable.put(Context.SECURITY_PRINCIPAL, "weblogic");
//		hashtable.put(Context.SECURITY_CREDENTIALS, "welcome1");
		
		//Servidor Local
//		hashtable.put(Context.PROVIDER_URL, "t3://localhost:7001");
//		hashtable.put(Context.SECURITY_PRINCIPAL, "admin");
//		hashtable.put(Context.SECURITY_CREDENTIALS, "P@ssw0rd");
		
		initialContextBDTU = new InitialContext(hashtable);
	}
	
	protected Object getServiceBDTU(String nombreService) {
		Object object = null;
		try {
			logger.info("Inicializando InitialContext con el servicio: "+ nombreService);
			object = initialContextBDTU.lookup(nombreService);
		} catch(Exception ex) {
			ex.printStackTrace();
			logger.info("ERROR: No se pudo inicializar el InitialContext con el servicio: ." +nombreService, ex);
		}		
		return object;
	}
	
	@Test
	public void consultaVigenciaMovilesEnvioCorreoTest() {
		try {
			initContextBDTU();
			VigenciaServiceRemote vigenciaServiceRemote = (VigenciaServiceRemote) getServiceBDTU("vigenciaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.VigenciaServiceRemote");
			System.out.println("vigenciaServiceRemote: "+vigenciaServiceRemote);
			
			EnvioCorreoResponse envioCorreoResponse = vigenciaServiceRemote.consultaVigenciaMovilesEnvioCorreo("SAAC820312HDFNLS06", "96968200442", "cesar.sanchez@novutek.com");
			System.out.println("codigo: "+envioCorreoResponse.getCodigo());
			System.out.println("mensaje: "+envioCorreoResponse.getMensaje());
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
