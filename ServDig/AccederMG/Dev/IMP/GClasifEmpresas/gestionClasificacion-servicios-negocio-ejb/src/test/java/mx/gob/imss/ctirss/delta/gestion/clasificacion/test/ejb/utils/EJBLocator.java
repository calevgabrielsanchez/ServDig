package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.utils;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.concentrado.ReporteConcentradoServiceBusinessRemote;

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

			
//			env.put(Context.PROVIDER_URL, "t3://172.24.116.37:13051");
//			env.put(Context.SECURITY_PRINCIPAL, "admin_wlgeptqa");
//			env.put(Context.SECURITY_CREDENTIALS, "wlgeptqa_admin");
			
			ic = new InitialContext(env);
			System.out.println(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ic;
	}


	public static ReporteConcentradoServiceBusinessRemote getConcentradoServiceBusiness() {
		ReporteConcentradoServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (ReporteConcentradoServiceBusinessRemote) getContextoLocal()
					.lookup("reporteConcentradoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.concentrado.ReporteConcentradoServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getConcentradoServiceBusiness()", e);
		}
		return ejb;
	}
	
}
 