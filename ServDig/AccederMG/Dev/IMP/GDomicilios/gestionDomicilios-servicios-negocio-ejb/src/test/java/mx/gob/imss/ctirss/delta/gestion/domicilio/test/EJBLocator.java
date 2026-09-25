package mx.gob.imss.ctirss.delta.gestion.domicilio.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

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
			env.put(Context.PROVIDER_URL, "t3://localhost:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			ic = new InitialContext(env);
			System.out.println(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ic;
	}
	
	
	public static DomicilioServiceBusinessRemote getDomicilioService() {
		DomicilioServiceBusinessRemote ejb = null;
		
		try {
			LOG.debug("Se obtiene la referencia al EJB del umf");
			ejb = (DomicilioServiceBusinessRemote) getContextoLocal()
					.lookup("domicilioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In UMFService()", e);
		}
		
		return ejb;
	}
	
	public static void main(String args[]){
		
		
		try {
			Domicilio domicilio = new Domicilio();
			domicilio.setClave(381);
			DomicilioServiceBusinessRemote ejb = (DomicilioServiceBusinessRemote) EJBLocator.getContextoLocal().lookup("domicilioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote");
			domicilio = ejb.consultarDomicilio(domicilio);
			
			System.out.println("Domicilio encontrado :" + domicilio);
			
			domicilio.setClave(null);
			try {
				ejb.registrarDomicilio(domicilio);
			} catch (DomicilioNoValidoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DomicilioNoLocalizadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
