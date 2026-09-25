package mx.gob.imss.ctirss.delta.gestion.beneficio.service.business.base;

import static org.junit.Assert.fail;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.CancelarBeneficioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.ReportesBeneficiosBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLocator {

	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(EjbLocator.class);
	}

	private static Context getContexto() {
		Context iCtx = null;
		try {
			final Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
			env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			iCtx = new InitialContext(env);

		} catch (NamingException e) {
			LOG.error("In getContexto()", e);
		}
		return iCtx;
	}

	public static BeneficioRissServiceBusinessRemote getBeneficioRissServiceBusiness() {
		LOG.debug("getting BeneficioServiceBusinessRemote");
		BeneficioRissServiceBusinessRemote ejb = null;
		try {
			ejb = (BeneficioRissServiceBusinessRemote) getContexto()
					.lookup("beneficioRissServiceBusiness#mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}
		return ejb;
	}
	
	public static CancelarBeneficioServiceBusinessRemote getCancelarBeneficioService() {
		LOG.debug("getting BeneficioServiceBusinessRemote");
		CancelarBeneficioServiceBusinessRemote ejb = null;
		try {
			ejb = (CancelarBeneficioServiceBusinessRemote) getContexto()
					.lookup("cancelarBeneficioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.CancelarBeneficioServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}
		return ejb;
	}
	
	public static PersonaFisicaServiceBusinessRemote getPersonaFisicaServiceBusiness() {
		LOG.debug("getting BeneficioServiceBusinessRemote");
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
	
	public static ReportesBeneficiosBusinessRemote getReporteBeneficioBusiness() {
		LOG.debug("getting BeneficioServiceBusinessRemote");
		ReportesBeneficiosBusinessRemote ejb = null;
		try {
			ejb = (ReportesBeneficiosBusinessRemote) getContexto()
					.lookup("reportesBeneficiosBusiness#mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.ReportesBeneficiosBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}
		return ejb;
	}

	public static SolicitudBusinessRemote getSolicitudBusinessRemote() {
		LOG.debug("getting BeneficioServiceBusinessRemote");
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
