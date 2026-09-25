package mx.gob.imss.ctirss.delta.gestion.asegurado.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business.ServiciosExternosAseguradosBusiness;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.AseguradoServiciosExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ReporteBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.integration.AseguradoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EJBLocator {
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(EJBLocator.class);
	}

	private static Context getContextoLocal() {
		Context iCtx = null; // NOPMD

		try {
			final Hashtable<String, String> env = new Hashtable<String, String>(); // NOPMD
			env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			env.put(Context.PROVIDER_URL, "t3://localhost:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
            env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			iCtx = new InitialContext(env);			
		} catch (Exception e) {
			LOG.error("In getContextoLocal()", e);
		}

		return iCtx;
	}

	public static ServiceBusinessRemote getServiceBusiness() {
		ServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (ServiceBusinessRemote) getContextoLocal()
					.lookup("serviceBusiness#mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudPersonaBusiness()", e);
		}
		return ejb;
	}

	public static SerieServiceBusinessRemote getSerieServiceBusiness() {
		SerieServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (SerieServiceBusinessRemote) getContextoLocal()
					.lookup("serieServiceBusiness#mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudPersonaBusiness()", e);
		}
		return ejb;
	}

	public static PersonaBusinessRemote getPersonaBusiness() {
		PersonaBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (PersonaBusinessRemote) getContextoLocal()
					.lookup("personaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote");

		} catch (NamingException e) {
			LOG.error("In getPersonaBusiness()", e);
		}
		return ejb;
	}

	public static PersonaMoralBusinessRemote getPersonaMoralBusiness() {
		PersonaMoralBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (PersonaMoralBusinessRemote) getContextoLocal()
					.lookup("personaMoralBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote");

		} catch (NamingException e) {
			LOG.error("In getPersonaMoralBusiness()", e);
		}
		return ejb;
	}

	public static ServiciosPersonaBusinessRemote getServiciosPersonaBusiness() {
		ServiciosPersonaBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (ServiciosPersonaBusinessRemote) getContextoLocal()
					.lookup("serviciosPersonaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote");

		} catch (NamingException e) {
			LOG.error("In getServiciosPersonaBusiness()", e);
		}
		return ejb;
	}

	public static SolicitudBusinessRemote getSolicitudBusiness() {
		SolicitudBusinessRemote solicitudBusinessRemote = null; // NOPMD

		try {
			solicitudBusinessRemote = (SolicitudBusinessRemote) getContextoLocal()
					.lookup("solicitudBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudSesion()", e);

		}

		return solicitudBusinessRemote;
	}

	public static SolicitudPersonaBusinessRemote getSolicitudPersonaBusiness() {
		SolicitudPersonaBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (SolicitudPersonaBusinessRemote) getContextoLocal()
					.lookup("solicitudPersonaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudPersonaBusiness()", e);
		}
		return ejb;
	}

	public static ReporteBusinessRemote getReporteBusiness() {
		ReporteBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (ReporteBusinessRemote) getContextoLocal()
					.lookup("reporteAseguradoBusiness#mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ReporteBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getReporteBusiness()", e);
		}
		return ejb;
	}
	
	public static AseguradoServiceBusinessRemote getAseguradoBusiness() {
		AseguradoServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (AseguradoServiceBusinessRemote) getContextoLocal()
					.lookup("aseguradoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.integration.AseguradoServiceBusinessRemote");

		} catch (NamingException e) {
			LOG.error("In getPersonaBusiness()", e);
		}
		return ejb;
	}
	
	
	public static AseguradoServiciosExternosRemote getServiciosExternosAseguradoService() {
		AseguradoServiciosExternosRemote ejb = null; // NOPMD
		try {
			ejb = (AseguradoServiciosExternosRemote) getContextoLocal()
					.lookup("serviciosExternosAseguradosBusiness#mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.AseguradoServiciosExternosRemote");

		} catch (NamingException e) {
			LOG.error("In getServiciosExternosAseguradoService()", e);
		}
		return ejb;
	}
}
