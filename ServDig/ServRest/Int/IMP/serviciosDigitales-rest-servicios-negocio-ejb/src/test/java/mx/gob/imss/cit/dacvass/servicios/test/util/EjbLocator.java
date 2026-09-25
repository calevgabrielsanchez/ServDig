package mx.gob.imss.cit.dacvass.servicios.test.util;


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaPersonaServiceRemote;
//import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
//import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducerRemote;


public class EjbLocator {
 
	//private static final Logger LOG;
	/*
	static {
		LOG = LoggerFactory.getLogger(EjbLocator.class);
	}

	*/
	private static Context getContexto() {
		Context iCtx = null; // NOPMD
		try {
			final Hashtable<String, String> env = new Hashtable<String, String>(); // NOPMD
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			// env.put(Context.PROVIDER_URL,
			// "t3://172.24.116.37:15051,172.24.116.37:15052");
			// env.put(Context.SECURITY_PRINCIPAL, "admin_wlgeceqa");
			// env.put(Context.SECURITY_CREDENTIALS, "wlgeceqa_admin");
			/*
			env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			*/
			/**Contexto de produccion **/
				env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001");
				env.put(Context.SECURITY_PRINCIPAL, "wloperator");
				env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");
			
			iCtx = new InitialContext(env);

		} catch (NamingException e) {
			//LOG.error("In getContexto()", e);
			System.out.println("error al generar el contexto de servicios" + e.getMessage());
		}
		return iCtx;
	}

	public static IConsultaPersonaServiceRemote getIConsultaInfoPersonaServiceExternalRemote() {
		IConsultaPersonaServiceRemote ejb = null; // NOPMD
		try {
			ejb = (IConsultaPersonaServiceRemote) getContexto()
					.lookup("consultaInfoPersonaServiceExternal#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaInfoPersonaServiceExternalRemote");
		} catch (NamingException e) {
			System.out.println("error al instanciar el ejb consultaInfoPersonaServiceExternal " + e.getMessage());
		}
		return ejb;
	}
	
	/*
	public static EMailProducerRemote getIEMailProducerRemote() {
		EMailProducerRemote ejb = null; // NOPMD
		try {
			ejb = (EMailProducerRemote) getContexto()
					.lookup("eMailProducerRemoteBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducerRemote");
		} catch (NamingException e) {
			System.out.println("error al instanciar el ejb eMailProducerRemoteBusiness " + e.getMessage());
		}
		return ejb;
	}
	
	
	public static EMailProducer getEMailQProducer() {
		EMailProducer service = null;
		try {
			service = (EMailProducer) getContexto()
					.lookup("EMailQProducer#mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer");
			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	*/
		
	
}
