package mx.gob.imss.cit.dacvass.servicios.rest.util;

import java.util.Hashtable;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IAseguradoServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ICatalogosServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaCreditosSiscobServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaPersonaServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaServiciosExternosServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IDomicilioServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IPatronServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISAUServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IServiciosBackEndOriginalServiciosDIigiatlesRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IServiciosDigitalesUtilServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISirocServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISisecServiciosDigitalesServiceRemote;


@Resource
public class EjbLocator {
 

	private static final ResourceBundle recursoProperties =ResourceBundleConfiguration.getResourceServiciosDigitales();
	private static  final String STR_PROVIDER_URL =recursoProperties.getString("service.provider.url");
	private static  final String STR_SECURITY_PRINCIPAL = recursoProperties.getString("service.security.principal");
	private static  final String STR_SECURITY_CREDENTIALS = recursoProperties.getString("service.security.credentials");

	private static  final String STR_PROVIDER_URL_ORIGINAL =recursoProperties.getString("service.provider.url.original");
	private static  final String STR_SECURITY_PRINCIPAL_ORIGINAL = recursoProperties.getString("service.security.principal.original");
	private static  final String STR_SECURITY_CREDENTIALS_ORIGINAL = recursoProperties.getString("service.security.credentials.original");
	
	
	private static final Logger log = LoggerFactory.getLogger(EjbLocator.class); 

	private static Context getContexto() throws Exception{
		log.debug("llegue a generar la conexion");
		Context iCtx = null; // NOPMD
		try {
			final Hashtable<String, String> env = new Hashtable<String, String>(); // NOPMD
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			/**contexto de desarrollo**/
		
			env.put(Context.PROVIDER_URL, STR_PROVIDER_URL);
			env.put(Context.SECURITY_PRINCIPAL, STR_SECURITY_PRINCIPAL);
			env.put(Context.SECURITY_CREDENTIALS, STR_SECURITY_CREDENTIALS);
		
			/**Contexto de produccion **/
		//	env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001");
		//	env.put(Context.SECURITY_PRINCIPAL, "wloperator");
		//	env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");
		
			/**Contexto de qa todos los servers se llaman igual**/
		/*
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "w1sd0m&53rv1d1r&wl&2014$."); 
			env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001");
		*/
			iCtx = new InitialContext(env);

		} catch (NamingException e) {
			log.error("error de nombre del servicio In getContexto()", e);
			throw e;
		}catch (Exception e) {
			log.error("error no localizadoIn getContexto()", e);
			throw e;
		}
		
		return iCtx;
	}
	
	/**
	 * Serivicio temporal para conectarse al backend original por problemas con el JMS en el backend clon
	 * @return
	 * @throws Exception
	 */
	private static Context getContextoOriginal() throws Exception{
		log.debug("llegue a generar la conexion orginal");
		Context iCtx = null;
		try {
			final Hashtable<String, String> env = new Hashtable<String, String>(); // NOPMD
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			env.put(Context.PROVIDER_URL, STR_PROVIDER_URL_ORIGINAL);
			env.put(Context.SECURITY_PRINCIPAL, STR_SECURITY_PRINCIPAL_ORIGINAL);
			env.put(Context.SECURITY_CREDENTIALS, STR_SECURITY_CREDENTIALS_ORIGINAL);	
			iCtx = new InitialContext(env);

		} catch (NamingException e) {
			log.error("error de nombre del servicio In getContexto()", e);
			throw e;
		}catch (Exception e) {
			log.error("error no localizadoIn getContexto()", e);
			throw e;
		}
		
		return iCtx;
	}
	
	
	@EJB(name = "consultaPersonaService", mappedName = "consultaPersonaService") 
	public static IConsultaPersonaServiceRemote getConsltaInfoPersonaRemote() throws Exception{
		IConsultaPersonaServiceRemote ejb = null; 
		try {
			ejb = (IConsultaPersonaServiceRemote) getContexto()
					.lookup("consultaPersonaService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaPersonaServiceRemote");
		} catch (NamingException e) {
			log.error("In getPersonaBusinessRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn getPersonaBusinessRemote()", e);
			throw e;
		}
		return ejb;
	}
	
	@EJB(name = "serviciosDigitalesUtilService", mappedName = "serviciosDigitalesUtilService") 
	public static IServiciosDigitalesUtilServiceRemote getUtilService() throws Exception{
		IServiciosDigitalesUtilServiceRemote ejb = null; 
		try {
			ejb = (IServiciosDigitalesUtilServiceRemote) getContexto()
					.lookup("serviciosDigitalesUtilService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IServiciosDigitalesUtilServiceRemote");
		} catch (NamingException e) {
			log.error("In getUtilServicesRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn getUtilServicesRemote()", e);
			throw e;
		}
		return ejb;
	}
	
	@EJB(name = "catalogosServiciosDigitalesService", mappedName = "catalogosServiciosDigitalesService") 
	public static ICatalogosServiciosDigitalesServiceRemote getCatalogoService() throws Exception{
		ICatalogosServiciosDigitalesServiceRemote ejb = null; 
		try {
			ejb = (ICatalogosServiciosDigitalesServiceRemote) getContexto()
					.lookup("catalogosServiciosDigitalesService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ICatalogosServiciosDigitalesServiceRemote");
		} catch (NamingException e) {
			log.error("In ICatalogosServiciosDigitalesServiceRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn ICatalogosServiciosDigitalesServiceRemote()", e);
			throw e;
		}
		return ejb;
	}
	
	
	@EJB(name = "domicilioServiciosDigitalesService", mappedName = "domicilioServiciosDigitalesService") 
	public static IDomicilioServiciosDigitalesServiceRemote getDomicilioDigitalService() throws Exception{
		log.debug("inicia la instancia");
		IDomicilioServiciosDigitalesServiceRemote ejb = null; 
		try {
			ejb = (IDomicilioServiciosDigitalesServiceRemote) getContexto()
					.lookup("domicilioServiciosDigitalesService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IDomicilioServiciosDigitalesServiceRemote");
		} catch (NamingException e) {
			log.error("In IDomicilioServiciosDigitalesServiceRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn IDomicilioServiciosDigitalesServiceRemote()", e);
			throw e;
		}
		return ejb;
	}
	
	@EJB(name = "patronServiciosDigitalesService", mappedName = "patronServiciosDigitalesService") 
	public static IPatronServiciosDigitalesServiceRemote getPatronDigitalService() throws Exception{
		IPatronServiciosDigitalesServiceRemote ejb = null; 
		try {
			ejb = (IPatronServiciosDigitalesServiceRemote) getContexto()
					.lookup("patronServiciosDigitalesService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IPatronServiciosDigitalesServiceRemote");
		} catch (NamingException e) {
			log.error("In IPatronServiciosDigitalesServiceRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn IPatronServiciosDigitalesServiceRemote()", e);
			throw e;
		}
		return ejb;
	}
	
	@EJB(name = "consultaCreditosSiscobService", mappedName = "consultaCreditosSiscobService") 
	public static IConsultaCreditosSiscobServiceRemote getCreditosPatronService() throws Exception{
		IConsultaCreditosSiscobServiceRemote ejb = null; 
		try {
			ejb = (IConsultaCreditosSiscobServiceRemote) getContexto()
					.lookup("consultaCreditosSiscobService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaCreditosSiscobServiceRemote");
		} catch (NamingException e) {
			log.error("In IConsultaCreditosSiscobServiceRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn IConsultaCreditosSiscobServiceRemote()", e);
			throw e;
		}
		return ejb;
	}
	
	@EJB(name = "sirocServiciosDigitalesService", mappedName = "sirocServiciosDigitalesService") 
	public static ISirocServiciosDigitalesServiceRemote getServiciosSirocService() throws Exception{
		ISirocServiciosDigitalesServiceRemote ejb = null; 
		try {
			ejb = (ISirocServiciosDigitalesServiceRemote) getContexto()
					.lookup("sirocServiciosDigitalesService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISirocServiciosDigitalesServiceRemote");
		} catch (NamingException e) {
			log.error("In ISirocServiciosDigitalesServiceRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn ISirocServiciosDigitalesServiceRemote()", e);
			throw e;
		}
		return ejb;
	}
	
	@EJB(name = "sauServiciosDigitalesService", mappedName = "sauServiciosDigitalesService") 
	public static ISAUServiciosDigitalesServiceRemote getSAUService() throws Exception{
		ISAUServiciosDigitalesServiceRemote ejb = null; 
		try {
			ejb = (ISAUServiciosDigitalesServiceRemote) getContexto()
					.lookup("sauServiciosDigitalesService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISAUServiciosDigitalesServiceRemote");
		} catch (NamingException e) {
			log.error("In ISirocServiciosDigitalesServiceRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn ISirocServiciosDigitalesServiceRemote()", e);
			throw e;
		}
		return ejb;
	}
	
	@EJB(name = "sisecServiciosDigitalesService", mappedName = "sisecServiciosDigitalesService") 
	public static ISisecServiciosDigitalesServiceRemote getServiciosSiseccService() throws Exception {
		ISisecServiciosDigitalesServiceRemote ejb = null; 
		try {
			ejb = (ISisecServiciosDigitalesServiceRemote) getContexto()
					.lookup("sisecServiciosDigitalesService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISisecServiciosDigitalesServiceRemote");
		} catch (NamingException e) {
			log.error("In ISisecServiciosDigitalesServiceRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn ISisecServiciosDigitalesServiceRemote()", e);
			throw e;
		}
		return ejb;
	}
	
	@EJB(name = "aseguradoServiciosDigitalesService", mappedName = "aseguradoServiciosDigitalesService") 
	public static IAseguradoServiciosDigitalesServiceRemote getServiciosAsegurdoService() throws Exception {
		IAseguradoServiciosDigitalesServiceRemote ejb = null; 
		try {
			ejb = (IAseguradoServiciosDigitalesServiceRemote) getContexto()
					.lookup("aseguradoServiciosDigitalesService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IAseguradoServiciosDigitalesServiceRemote");
		} catch (NamingException e) {
			log.error("In IAseguradoServiciosDigitalesServiceRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn IAseguradoServiciosDigitalesServiceRemote()", e);
			throw e;
		}
		return ejb;
	}
	
	
	@EJB(name = "serviciosBackEndOriginalService", mappedName = "serviciosBackEndOriginalService") 
	public static IServiciosBackEndOriginalServiciosDIigiatlesRemote getServiciosBackEndOriginalService() throws Exception{
		IServiciosBackEndOriginalServiciosDIigiatlesRemote ejb = null; 
		try {
			ejb = (IServiciosBackEndOriginalServiciosDIigiatlesRemote) getContextoOriginal()
					.lookup("serviciosBackEndOriginalService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IServiciosBackEndOriginalServiciosDIigiatlesRemote");
		} catch (NamingException e) {
			log.error("In IPatronServiciosDigitalesServiceRemote()", e);
		}catch (Exception e) {
			log.error("error no identificado en IPatronServiciosDigitalesServiceRemote() original", e);
			throw e;
		}
		return ejb;
	}
	
	@EJB(name = "consultaServiciosExternosService", mappedName = "consultaServiciosExternosService") 
	public static IConsultaServiciosExternosServiceRemote getServiciosExternosService() throws Exception {
		IConsultaServiciosExternosServiceRemote ejb = null; 
		try {
			ejb = (IConsultaServiciosExternosServiceRemote) getContexto()
					.lookup("consultaServiciosExternosService#mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaServiciosExternosServiceRemote");
		} catch (NamingException e) {
			log.error("In IConsultaServiciosExternosServiceRemote()", e);
		}catch (Exception e) {
			log.error("error no localizadoIn IConsultaServiciosExternosServiceRemote()", e);
			throw e;
		}
		return ejb;
	}
	
}
