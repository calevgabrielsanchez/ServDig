package mx.gob.imss.ctirss.delta.derechohabientes.service.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechoabientePensionesRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosDerechohabientesExternoRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ConsultaDatosAseguradoServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.IdeeServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ProrrogaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitesDerechohabientesMovilRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitesDerechohabientesTSPIRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;

public class EjbLocator {
 
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(EjbLocator.class);
	}

	private static Context getContexto() {
		Context iCtx = null; // NOPMD
		try {
			final Hashtable<String, String> env = new Hashtable<String, String>(); // NOPMD
			env.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
			/** Loop T3 Contexto de desarrollo	
			env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			**/
			
			/**Contexto de produccion**/
			env.put(Context.SECURITY_PRINCIPAL, "wloperator");
			env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");
			env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001");
			
			
			/**contexto qa se cambia en el host**/
			//env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			//env.put(Context.SECURITY_CREDENTIALS, "w1sd0m&53rv1d1r&wl&2014$.");
			//env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001");
			
			iCtx = new InitialContext(env);

		} catch (NamingException e) {
			LOG.error("In getContexto()", e);
		}catch (Exception e) {
			System.out.println("ocurrio un error al generar el context" + e.getMessage());
		}
		return iCtx;
	}
	
	public static CatalogosDerechohabientesExternoRemote getCatalogosDere()  {
		CatalogosDerechohabientesExternoRemote ejb = null;
		
		try {
			LOG.debug("Se obtiene la referencia al EJB del umf");
			ejb = (CatalogosDerechohabientesExternoRemote) getContexto()
					.lookup("catalogosDerechohabientesExterno#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosDerechohabientesExternoRemote");
		} catch (NamingException e) {
			LOG.error("In UMFService()", e);
		}
		
		return ejb;
	}
	
	public static TramitesDerechohabientesMovilRemote getTramitesDerechohabientes() {
		TramitesDerechohabientesMovilRemote ejb = null;
		
		try {
			LOG.debug("Se obtiene la referencia al EJB del umf");
			ejb = (TramitesDerechohabientesMovilRemote) getContexto()
					.lookup("tramitesDerechohabientesMovil#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitesDerechohabientesMovilRemote");
		} catch (NamingException e) {
			LOG.error("In UMFService()", e);
		}
		
		return ejb;
	}
	
	public static EmailServiceRemote getEmailService() {
		EmailServiceRemote ejb = null;
		
		try {
			LOG.debug("Se obtiene la referencia al EJB del umf");
			ejb = (EmailServiceRemote) getContexto()
					.lookup("eMailService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.EmailServiceRemote");
		} catch (NamingException e) {
			LOG.error("In UMFService()", e);
		}
		
		return ejb;
	}
	
	public static UmfServiceRemote getUmfService() {
		
		UmfServiceRemote ejb = null;
		
		try {
			LOG.debug("Se obtiene la referencia al EJB del umf");
			ejb = (UmfServiceRemote) getContexto()
					.lookup("umfService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote");
		} catch (NamingException e) {
			LOG.error("In UMFService()", e);
		}
		
		return ejb;
	}
	
	public static IdeeServiceRemote getIdeeService() {
		IdeeServiceRemote ejb = null; // NOPMD
		try {
			LOG.debug("Se obtiene la referencia al EJB del idee");
			ejb = (IdeeServiceRemote) getContexto()
					.lookup("ideeService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.IdeeServiceRemote");
		} catch (NamingException e) {
			LOG.error("In ideeService()", e);
		}
		return ejb;
	}
	
	public static SujetoObligadoServiceBusinessRemote getSujetoObligatoService() {
		SujetoObligadoServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (SujetoObligadoServiceBusinessRemote) getContexto()
					.lookup("sujetoObligadoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In sujetoObligadoServiceBusiness()", e);
		}
		return ejb;
	}

	public static GrupoFamiliarServiceRemote getGrupoFamiliarService() {
		GrupoFamiliarServiceRemote ejb = null; // NOPMD
		try {
			ejb = (GrupoFamiliarServiceRemote) getContexto()
					.lookup("grupoFamiliarService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote");
		} catch (NamingException e) {
			LOG.error("In GrupoFamiliarServiceRemote()", e);
		}
		return ejb;
	}

	public static CorreccionDerechohabienteServiceRemote getCorreccionDerechohabienteService() {
		
		CorreccionDerechohabienteServiceRemote ejb = null;
		try {
			ejb = (CorreccionDerechohabienteServiceRemote) getContexto()
					.lookup("correccionDerechohabienteService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote");
		} catch (NamingException e) {
			LOG.error("In CorreciconDerechohabienteService()", e);
		}
		return ejb;
	}
	
	public static BajaDerechohabienteServiceRemote getBajaDerechohabienteService() {
		BajaDerechohabienteServiceRemote ejb = null;
		try {
			ejb = (BajaDerechohabienteServiceRemote) getContexto()
					.lookup("bajaDerechohabienteService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechohabienteServiceRemote");
		} catch (NamingException e) {
			LOG.error("In BajaDerechohabienteService()", e);
		}
		return ejb;
	}
	
	public static ProrrogaServiceRemote getProrrogaService() {
		ProrrogaServiceRemote ejb = null;
		try {
			ejb = (ProrrogaServiceRemote) getContexto()
					.lookup("prorrogaService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ProrrogaServiceRemote");
		} catch (NamingException e) {
			LOG.error("In BajaDerechohabienteService()", e);
		}
		return ejb;
	}
	
	 public static PersonaFisicaServiceBusinessRemote getPersonaFisicaBusiness() {
    	 PersonaFisicaServiceBusinessRemote ejb = null; // NOPMD
         try {
             ejb = (PersonaFisicaServiceBusinessRemote) getContexto().lookup("personaFisicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote");

         } catch (NamingException e) {
             LOG.error("In getPersonaBusiness()", e);
         }
         return ejb;
    }
	 
	 public static SolicitudBusinessRemote getSolicitudBusiness() {
		 SolicitudBusinessRemote solicitudBusinessRemote = null; // NOPMD

		 try {
			 solicitudBusinessRemote = (SolicitudBusinessRemote) getContexto()
					 .lookup("solicitudBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote");
		 } catch (NamingException e) {
			 LOG.error("In getSolicitudSesion()", e);

		 }

		 return solicitudBusinessRemote;
	 }
	 
	 public static DocumentosServiceRemote getDocumentosService() {
		 DocumentosServiceRemote solicitudBusinessRemote = null; // NOPMD

		 try {
			 solicitudBusinessRemote = (DocumentosServiceRemote) getContexto()
					 .lookup("documentosService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote");
		 } catch (NamingException e) {
			 LOG.error("In getSolicitudSesion()", e);

		 }

		 return solicitudBusinessRemote;
	 }
	 
	 public static BajaDerechoabientePensionesRemote getBajaDerechohabientePensionesService() {
		 BajaDerechoabientePensionesRemote ejb = null;
			try {
				ejb = (BajaDerechoabientePensionesRemote) getContexto()
						.lookup("bajaDerechoabientePensiones#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechoabientePensionesRemote");
			} catch (NamingException e) {
				LOG.error("In BajaDerechohabienteService()", e);
			}
			return ejb;
		}
	 
	 public static TramitesDerechohabientesTSPIRemote getTramitesDerechohabientesTSPIService() {
		 TramitesDerechohabientesTSPIRemote ejb = null;
			try {
				LOG.debug("Se obtiene la referencia al EJB del umf");
				ejb = (TramitesDerechohabientesTSPIRemote) getContexto()
						.lookup("tramitesDerechohabientesTSPIService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitesDerechohabientesTSPIRemote");
			} catch (NamingException e) {
				LOG.error("In getTramitesDerechohabientesTSPIService()", e);
			}
			return ejb;
		}
	 
	 public static DomicilioServiceBusinessRemote getDomicilioServiceBusinessRemote() {
		 DomicilioServiceBusinessRemote ejb = null;
			try {
				ejb = (DomicilioServiceBusinessRemote) getContexto()
						.lookup("domicilioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote");
			} catch (NamingException e) {
				LOG.error("In getTramitesDerechohabientesTSPIService()", e);
			}
			return ejb;
		}
	 
	 public static ConsultaDatosAseguradoServiceRemote getConsultaDatosAseguradoServiceRemote() {
		 ConsultaDatosAseguradoServiceRemote ejb = null;
			try {
				ejb = (ConsultaDatosAseguradoServiceRemote) getContexto()
						.lookup("consultaDatosAseguradoService#mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ConsultaDatosAseguradoServiceRemote");
			} catch (NamingException e) {
				LOG.error("In consultaDatosAseguradoService()", e);
			}
			return ejb;
		}
	 
}
