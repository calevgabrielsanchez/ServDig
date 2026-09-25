package mx.gob.imss.ctirss.idse.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.idse.service.interfaces.RPNPServiceRemote;
import mx.gob.imss.ctirss.idse.service.interfaces.RegistroPatronalIdseServiceBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EJBLocator {
	private static final Logger LOG;

	
	static {
		LOG = LoggerFactory.getLogger(EJBLocator.class);
	}

	static Context getContextoLocal(){
		Context ic = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			
			
//			env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001,vzapma07.imss.gob.mx:8003,vzapma12.imss.gob.mx:8001,vzapma12.imss.gob.mx:8003,vzapmb09.imss.gob.mx:8001,vzapmb09.imss.gob.mx:8003");
//			env.put(Context.SECURITY_PRINCIPAL, "wloperator");
//			env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");
			
			
			env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			
//			env.put(Context.PROVIDER_URL, "t3://127.0.0.1:8001");
//			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
//			env.put(Context.SECURITY_CREDENTIALS, "weblogic123");
			
			ic = new InitialContext(env);
			System.out.println(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ic;
	}

	
	
	public static RegistroPatronalIdseServiceBusinessRemote getRegistroPatronalIdseServiceBusiness() {
		RegistroPatronalIdseServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (RegistroPatronalIdseServiceBusinessRemote) getContextoLocal()
					.lookup("registroPatronalIdseServiceBusiness#mx.gob.imss.ctirss.idse.service.interfaces.RegistroPatronalIdseServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("EJBLocator - getRegistroPatronalIdseServiceBusiness()", e);
		}
		return ejb;
	}
	
	
	 public static RPNPServiceRemote getRPNPServiceBusiness() {
			RPNPServiceRemote ejb = null; // NOPMD
			try {
				ejb = (RPNPServiceRemote) getContextoLocal()
						.lookup("rpnpService#mx.gob.imss.ctirss.idse.service.interfaces.RPNPServiceRemote");
			} catch (NamingException e) {
				LOG.error("In getrpnpsERVICE()", e);
			}
			return ejb;
	 }
	 
}
