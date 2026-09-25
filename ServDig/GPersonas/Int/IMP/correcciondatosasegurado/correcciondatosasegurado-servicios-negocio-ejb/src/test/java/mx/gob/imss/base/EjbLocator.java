package mx.gob.imss.base;

import static org.junit.Assert.fail;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote;
import mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;

public class EjbLocator {
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(EjbLocator.class);
	}

	private static Context getContexto() {
		Context iCtx = null;
		try {
			final Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			// env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001");
			// env.put(Context.SECURITY_PRINCIPAL, "wloperator");
			// env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");

			env.put(Context.PROVIDER_URL, "t3://localhost:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "password123");

			iCtx = new InitialContext(env);
		} catch (NamingException e) {
			LOG.error("In getContexto()", e);
		}
		return iCtx;
	}

	public static RegistroSolicitudCorreccionDatosAseguradoRemote getRegistroSolicitudCorreccionDatosAseguradoRemote() {
		LOG.debug("getting SeguroIvroServiceRemote");
		RegistroSolicitudCorreccionDatosAseguradoRemote ejb = null;

		try {
			ejb = (RegistroSolicitudCorreccionDatosAseguradoRemote) getContexto().lookup(
					"registroSolicitudCorreccionDatosAseguradoBusiness#mx.gob.imss.cit.cda.service.interfaces.RegistroSolicitudCorreccionDatosAseguradoRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static ManejadorReportesRemote getManejadorReportesRemote() {
		LOG.debug("getting ManejadorReportesRemote");
		ManejadorReportesRemote ejb = null;

		try {
			ejb = (ManejadorReportesRemote) getContexto()
					.lookup("manejadorReportesBusiness#mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}
	
	public static ResponsablesDelegacionRemote getResponsablesDelegacionRemote() {
		LOG.debug("getting ResponsablesDelegacionRemote");
		ResponsablesDelegacionRemote ejb = null;

		try {
			ejb = (ResponsablesDelegacionRemote) getContexto()
					.lookup("responsablesDelegacionBusiness#mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}
	
	public static EmailServiceRemote getEmailServiceRemote() {
		LOG.debug("getting EmailServiceRemote");
		EmailServiceRemote ejb = null;

		try {
			ejb = (EmailServiceRemote) getContexto()
					.lookup("eMailService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}
	
	public static ResponsableTareaRemote getResponsableTareaRemote() {
		LOG.debug("getting ResponsableTareaRemote");
		ResponsableTareaRemote ejb = null;

		try {
			ejb = (ResponsableTareaRemote) getContexto()
					.lookup("responsableTareaBusiness#mx.gob.imss.cit.cda.service.interfaces.ResponsableTareaRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}
	
	
	
}
