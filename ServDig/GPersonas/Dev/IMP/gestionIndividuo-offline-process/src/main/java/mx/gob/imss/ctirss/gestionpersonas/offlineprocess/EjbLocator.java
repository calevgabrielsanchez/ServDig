package mx.gob.imss.ctirss.gestionpersonas.offlineprocess;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;


public class EjbLocator {

	static SolicitudPersonaBusinessRemote getSolicitudPersonaBusiness() {
		SolicitudPersonaBusinessRemote ejb = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			env.put(Context.PROVIDER_URL, "t3://localhost:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			Context ic = new InitialContext(env);
			ejb = (SolicitudPersonaBusinessRemote) ic.lookup("solicitudPersonaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ejb;
	}	
}
