package mx.gob.imss.dashboardServicios.infraestructura.utilerias;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote;

public class EjbLocator {

	public static ConsultaBusinessRemote getEjbRemote() {
		ConsultaBusinessRemote ejb = null;
		try {
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			env.put(Context.PROVIDER_URL, "t3://localhost:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			Context ic = new InitialContext(env);			
			ejb = (ConsultaBusinessRemote) ic.lookup("ejb/ConsultaBusiness#mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ejb;
	}	
}
