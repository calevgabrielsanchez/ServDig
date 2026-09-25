package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EJBLocator {
	private static final Logger log = LoggerFactory.getLogger(EJBLocator.class);
	
	public static Object getContextoLocal(String jndi) {
		log.info("########## Obteniendo el Contexto de Local ##########");
		Context context = null;
		Object ejb = null;
		Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
        env.put(Context.SECURITY_PRINCIPAL, "admin");
        env.put(Context.SECURITY_CREDENTIALS, "P@ssw0rd");
        
        try {
        	context = new InitialContext(env);
			ejb = context.lookup(jndi);
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
        return ejb;
	}
	
	public static Object getContextoStage(String jndi) {
		log.info("########## Obteniendo el Contexto de Stage ##########");
		Context context = null;
		Object ejb = null;
		Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://vzapmb17.imss.gob.mx:8001");
        env.put(Context.SECURITY_PRINCIPAL, "perifericos");
        env.put(Context.SECURITY_CREDENTIALS, "w3bAp.Perifericos");
        
        try {
        	context = new InitialContext(env);
			ejb = context.lookup(jndi);
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
        return ejb;
	}
	
}
