package mx.gob.imss.ctirss.delta.gestion.solicitud.service.test;

import static org.junit.Assert.fail;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducerRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.global.interfaces.DeltaSolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AsignacionPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FolioCertificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.IDSEQueueProducerRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoAsignacionSIMEBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovtoPatSujetoObligadoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ReingresoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ReporteRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudQueueProducerRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaGlobalBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLocator {
 
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(EjbLocator.class);
	}

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
			env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			iCtx = new InitialContext(env);

		} catch (NamingException e) {
			LOG.error("In getContexto()", e);
		}
		return iCtx;
	}

	public static SolicitudBusinessRemote getSolicitudBusiness() {
		SolicitudBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (SolicitudBusinessRemote) getContexto()
					.lookup("solicitudBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudPersonaBusiness()", e);
		}
		return ejb;
	}

	public static DeltaSolicitudServiceRemote getDeltaSolicitudBusiness() {
		DeltaSolicitudServiceRemote ejb = null; // NOPMD
		try {
			ejb = (DeltaSolicitudServiceRemote) getContexto()
					.lookup("deltaSolicitudService#mx.gob.imss.ctirss.delta.gestion.solicitud.service.global.interfaces.DeltaSolicitudServiceRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudPersonaBusiness()", e);
		}
		return ejb;
	}

	public static ServiceBusinessRemote getServiceBusiness() {
		ServiceBusinessRemote ejb = null;
		try {
			ejb = (ServiceBusinessRemote) getContexto()
					.lookup("serviceBusiness#mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudPersonaBusiness()", e);
		}
		return ejb;
	}

	public static EMailProducerRemote getEmailProducerBusiness() {
		EMailProducerRemote ejb = null;
//        EMailProducerRemoteBusiness
		try {
			ejb = (EMailProducerRemote) getContexto()
					.lookup("eMailProducerRemoteBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducerRemote");
		} catch (NamingException e) {
			LOG.error("In getEmailProducerBusiness()", e);
		}
		return ejb;
	}

	public static MovtoPatSujetoObligadoBusinessRemote getMovtoPatSujetoObligadoBusiness()
			throws NamingException {
		return (MovtoPatSujetoObligadoBusinessRemote) getContexto()
				.lookup("movtoPatSujetoObligadoBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovtoPatSujetoObligadoBusinessRemote");
	}

	public static MovimientoPatronalBusinessRemote getMovimientoPatronalBusiness()
			throws NamingException {
		return (MovimientoPatronalBusinessRemote) getContexto()
				.lookup("movimientoPatronalBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoPatronalBusinessRemote");
	}

	public static AsignacionPatronalBusinessRemote getAsignacionPatronalBusiness() {
		AsignacionPatronalBusinessRemote asignacionPatronalBusinessRemote = null;
		try {
			asignacionPatronalBusinessRemote = (AsignacionPatronalBusinessRemote) getContexto()
					.lookup("asignacionPatronalBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AsignacionPatronalBusinessRemote");
		} catch (NamingException namingException) {
			namingException.printStackTrace();
			fail("Problemas para encontrar el EJB "
					+ AsignacionPatronalBusinessRemote.class.getSimpleName());
		}
		return asignacionPatronalBusinessRemote;
	}

	public static ReingresoServiceBusinessRemote getReingresoServiceBusiness() {
		ReingresoServiceBusinessRemote reingresoServiceBusinessRemote = null;
		try {
			reingresoServiceBusinessRemote = (ReingresoServiceBusinessRemote) getContexto()
					.lookup("reingresoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ReingresoServiceBusinessRemote");
		} catch (NamingException namingException) {
			namingException.printStackTrace();
			fail("Problemas para encontrar el EJB "
					+ ReingresoServiceBusinessRemote.class.getSimpleName());
		}
		return reingresoServiceBusinessRemote;
	}

	public static ConcluirAltaPatronalBusinessRemote getConcluirAltaPatronalBusiness() {
		ConcluirAltaPatronalBusinessRemote concluirAltaPatronalBusinessRemote = null;
		try {
			concluirAltaPatronalBusinessRemote = (ConcluirAltaPatronalBusinessRemote) getContexto()
					.lookup("concluirAltaPatronalBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote");
		} catch (NamingException exception) {
			exception.printStackTrace();
			fail("Problemas para encontrar el EJB "
					+ ConcluirAltaPatronalBusinessRemote.class.getSimpleName());
		}
		return concluirAltaPatronalBusinessRemote;
	}

	public static MovimientoAsignacionSIMEBusinessRemote getMovimientoAsignacionSIMEBusiness() {
		LOG.debug("getting MovimientoAsignacionSIMEBusinessRemote");
		MovimientoAsignacionSIMEBusinessRemote ejb = null;
		try {
			ejb = (MovimientoAsignacionSIMEBusinessRemote) getContexto()
					.lookup("movimientoAsignacionSIMEBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoAsignacionSIMEBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
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

	public static PersonaGlobalBusinessRemote getPersonaGlobalHandler() {
		LOG.debug("getting PersonaGlobalRemote");
		PersonaGlobalBusinessRemote ejb = null;
		try {
			ejb = (PersonaGlobalBusinessRemote) getContexto()
					.lookup("personaGlobalBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaGlobalBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}
		return ejb;
	}

	public static IDSEQueueProducerRemote getIDSEProducer() {
		LOG.debug("getting IDSEQueueProducerRemote");
		IDSEQueueProducerRemote ejb = null;
		try {
			ejb = (IDSEQueueProducerRemote) getContexto()
					.lookup("idseQProducerBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.IDSEQueueProducerRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}
		return ejb;
	}

	public static ActividadEcServiceRemote getClasificacionActividadEconomicaServiceBusinessRemote() {
		LOG.debug("getting clasificacionActividadEconomicaServiceBusiness");
		ActividadEcServiceRemote ejb = null;
		try {
			ejb = (ActividadEcServiceRemote) getContexto()
					.lookup("clasificacionActividadEconomicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}
		return ejb;
	}

	public static IDSEQueueProducerRemote getIDSEQueueProducerRemote() {
		IDSEQueueProducerRemote ejb = null;
		try {
			ejb = (IDSEQueueProducerRemote) getContexto()
					.lookup("idseQProducerBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.IDSEQueueProducerRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}
		return ejb;
	}

	public static ReporteRissServiceBusinessRemote getReporteRissService() {
		LOG.debug("getting ReporteRissServiceBusinessRemote");
		ReporteRissServiceBusinessRemote ejb = null;

		try {
			ejb = (ReporteRissServiceBusinessRemote) getContexto()
					.lookup("reporteRissServiceBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ReporteRissServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static FolioCertificacionServiceBusinessRemote getFolioCertificacionServiceBusiness() {
		LOG.debug("getting FolioCertificacionServiceBusinessRemote");
		FolioCertificacionServiceBusinessRemote ejb = null;

		try {
			ejb = (FolioCertificacionServiceBusinessRemote) getContexto()
					.lookup("folioCertificacionServiceBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FolioCertificacionServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static FirmaDigitalBusinessRemote getFirmaDigitalBusiness() {
		LOG.debug("getting FolioCertificacionServiceBusinessRemote");
		FirmaDigitalBusinessRemote ejb = null;

		try {
			ejb = (FirmaDigitalBusinessRemote) getContexto()
					.lookup("firmaDigitalBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	public static SolicitudQueueProducerRemote getSolicitudProducerService() {
		LOG.debug("getting ReporteRissServiceBusinessRemote");
		SolicitudQueueProducerRemote ejb = null;

		try {
			ejb = (SolicitudQueueProducerRemote) getContexto()
					.lookup("solicitudQProducerBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudQueueProducerRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}

		return ejb;
	}

	
}
