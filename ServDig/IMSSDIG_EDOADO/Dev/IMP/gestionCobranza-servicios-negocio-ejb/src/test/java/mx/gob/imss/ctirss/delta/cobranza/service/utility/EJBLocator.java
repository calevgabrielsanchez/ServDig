package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CobranzaServiceRemote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EJBLocator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EJBLocator.class);
	
	static Context getContextoLocal(){
		Context ic = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			
			env.put(Context.PROVIDER_URL, "t3://localhost:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "WEBLOGIC1");
			
			ic = new InitialContext(env);
			System.out.println(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ic;
	}
	
	
	public static CobranzaServiceRemote getCobranzaService(){
		CobranzaServiceRemote ejb = null;
		try {
			ejb = (CobranzaServiceRemote) getContextoLocal()
					.lookup("cobranzaService#mx.gob.imss.ctirss.delta.cobranza.service.interfaces.CobranzaServiceRemote");
		} catch (NamingException e) {
			LOGGER.error("En getCobranzaService(): ", e);
		}
		return ejb;
	}
}
