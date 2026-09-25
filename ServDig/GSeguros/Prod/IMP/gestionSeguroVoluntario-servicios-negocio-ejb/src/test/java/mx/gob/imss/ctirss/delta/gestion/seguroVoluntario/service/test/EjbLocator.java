package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLocator {
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(EjbLocator.class);
	}

	private static InitialContext obtContext(Ambiente ambiente) throws NamingException, IOException {

		Context iCtx = null;
		final Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

		Properties prop = new Properties();
		prop.load(ClassLoader.getSystemResourceAsStream("opcionesTest.properties"));

		String PROVIDER_URL = prop.getProperty(ambiente.name() + "_PROVIDER_URL");
		String SECURITY_PRINCIPAL = prop.getProperty(ambiente.name() + "_SECURITY_PRINCIPAL");
		String SECURITY_CREDENTIALS = prop.getProperty(ambiente.name() + "_SECURITY_CREDENTIALS");

		env.put(Context.PROVIDER_URL, PROVIDER_URL);
		env.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
		env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);

		iCtx = new InitialContext(env);

		return (InitialContext) iCtx;
	}

	private static <T> T obtResource(Ambiente ambiente, String name) throws NamingException, IOException {
		return (T) obtContext(ambiente).lookup(name);
	}

	public static <T> T find(Class<T> t, Ambiente ambiente) {

		String ejbName;
		if (t.getSimpleName().equals("DomicilioServiceBussinessExternosRemote")) {
			ejbName = "domicilioExternosServiceBusiness";
		} else if (t.getSimpleName().equals("EMailProducer")) {
			ejbName = "EMailQProducer";
		}else if(t.getSimpleName().equals("ValidaVigenciaRemote")){
			ejbName = "validaVigenciaBusinesss";
		} else {
			ejbName = t.getSimpleName().replace("Remote", "");

			if (ejbName.equals("SolicitudQueueProducer")) {
				ejbName = "solicitudQProducerBusiness";
			} else if (ejbName.equals("DerechohabienteService")) {

			}
			else {
				String complement = null;
				if (!ejbName.endsWith(complement = "Business")) {
					ejbName += complement;
				}
			}

			ejbName = WordUtils.uncapitalize(ejbName);
		}

		try {
			return obtResource(ambiente, ejbName + "#" + t.getName());
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Context getContexto() {
		Context iCtx = null;
		try {
			final Hashtable<String, String> env = new Hashtable<String, String>();

			env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");

			iCtx = new InitialContext(env);
		} catch (NamingException e) {
			LOG.error("In getContexto()", e);
		}
		return iCtx;
	}

	public static SeguroIvroServiceRemote getSeguroIvroServiceRemote() {
		LOG.debug("getting SeguroIvroServiceRemote");
		SeguroIvroServiceRemote ejb = null;

		try {
			ejb = (SeguroIvroServiceRemote) getContexto()
					.lookup("seguroIvroServiceBusiness#mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static CompraServiceRemote getCompraServiceRemote() {
		LOG.debug("getting CompraServiceRemote");
		CompraServiceRemote ejb = null;

		try {
			ejb = (CompraServiceRemote) getContexto()
					.lookup("compraServiceBusiness#mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static ConsultaSeguroIvroServiceRemote getConsultaSeguroIvroServiceRemote() {
		LOG.debug("getting ConsultaSeguroIvroServiceRemote");
		ConsultaSeguroIvroServiceRemote ejb = null;

		try {
			ejb = (ConsultaSeguroIvroServiceRemote) getContexto()
					.lookup("consultaSeguroIvroServiceBusiness#mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static PersonaFisicaServiceBusinessRemote getPersonaFisicaServiceBusinessRemote() {
		LOG.debug("getting PersonaFisicaServiceBusinessRemote");
		PersonaFisicaServiceBusinessRemote ejb = null;

		try {
			ejb = (PersonaFisicaServiceBusinessRemote) getContexto()
					.lookup("personaFisicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static CotizacionServiceRemote getCotizacionServiceRemote() {
		LOG.debug("getting CotizacionServiceRemote");
		CotizacionServiceRemote ejb = null;

		try {
			ejb = (CotizacionServiceRemote) getContexto()
					.lookup("cotizacionServiceBusiness#mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static DomicilioServiceBusinessRemote getDomicilioServiceBusinessRemote() {
		LOG.debug("getting DomicilioServiceBusinessRemote");
		DomicilioServiceBusinessRemote ejb = null;

		try {
			ejb = (DomicilioServiceBusinessRemote) getContexto()
					.lookup("domicilioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static SujetoObligadoServiceBusinessRemote getSujetoObligadoServiceBusinessRemote() {
		LOG.debug("getting SujetoObligadoServiceBusinessRemote");
		SujetoObligadoServiceBusinessRemote ejb = null;

		try {
			ejb = (SujetoObligadoServiceBusinessRemote) getContexto()
					.lookup("sujetoObligadoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static PersonaBusinessRemote getPersonaBusinessRemote() {
		LOG.debug("getting PersonaBusinessRemote");
		PersonaBusinessRemote ejb = null;

		try {
			ejb = (PersonaBusinessRemote) getContexto()
					.lookup("personaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static SolicitudBusinessRemote getSolicitudBusinessRemote() {
		LOG.debug("getting SolicitudBusinessRemote");
		SolicitudBusinessRemote ejb = null;

		try {
			ejb = (SolicitudBusinessRemote) getContexto()
					.lookup("solicitudBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}
}
