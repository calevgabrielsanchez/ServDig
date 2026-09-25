package mx.gob.imss.cdsss.delta.portal.controller;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLocator {
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(EjbLocator.class);
	}

	private static Context getContexto() {
		Context iCtx = null; // NOPMD
		System.out.println("se cambia url");
		try {
			final Hashtable<String, String> env = new Hashtable<String, String>(); // NOPMD
			env.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
			// env.put(Context.PROVIDER_URL,
			// "t3://172.24.116.37:15051,172.24.116.37:15052");
			// env.put(Context.SECURITY_PRINCIPAL, "admin_wlgeceqa");
			// env.put(Context.SECURITY_CREDENTIALS, "wlgeceqa_admin");
			env.put(Context.PROVIDER_URL, "t3://172.16.5.176:8001,172.16.5.176:8003");
			env.put(Context.SECURITY_PRINCIPAL, "wloperator");
			env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");
			iCtx = new InitialContext(env);

		} catch (NamingException e) {
			LOG.error("In getContexto()", e);
		}
		return iCtx;
	}
	
	public static EmailServiceRemote getEmailService() {
		EmailServiceRemote ejb = null;
		
		try {
			LOG.debug("Se obtiene la referencia al EJB del umf");
			ejb = (EmailServiceRemote) getContexto()
					.lookup("eMailService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote");
		} catch (NamingException e) {
			LOG.error("In UMFService()", e);
		}
		
		return ejb;
	}
}
