package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import static org.junit.Assert.fail;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.DictamenServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ValidaClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.FirmaClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaMoralServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.AfiliacionGlobalServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.ClasificacionServiceRemote;
//import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.ProcesaTramitesServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.IDSEQueueProducerRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.interfaces.HldaClientServiceRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

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
			
//PRODUCCION			
//			env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001,vzapma07.imss.gob.mx:8003,vzapma12.imss.gob.mx:8001,vzapma12.imss.gob.mx:8003,vzapmb09.imss.gob.mx:8001,vzapmb09.imss.gob.mx:8003");
//			env.put(Context.SECURITY_PRINCIPAL, "wloperator");
//			env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");

			//LOCAL			
	          env.put(Context.PROVIDER_URL, "t3://localhost:7001");
	          env.put(Context.SECURITY_PRINCIPAL, "weblogic");
	          env.put(Context.SECURITY_CREDENTIALS, "password123");


//Stage
//            env.put(Context.PROVIDER_URL, "t3://172.16.5.176:8001");
//			env.put(Context.SECURITY_PRINCIPAL, "wloperator");
//			env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");

//PRODUCCION			
//			env.put(Context.PROVIDER_URL, "t3://172.16.5.11:8001"); // 172.16.5.11    172.16.5.224 mirror
//			env.put(Context.SECURITY_PRINCIPAL, "wloperator");
//			env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");
			
			
			ic = new InitialContext(env);
			System.out.println("Contexto incial: ");
			System.out.println(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ic;
	}
	
	
	
	public static mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote getServiceBusinessMAC() {
		mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote ejb = null; 
		try {
			ejb = (mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote) getContextoLocal()
					.lookup("solicitudServiceBusiness#mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudPersonaBusiness()", e);
		}
		return ejb;
	}

	public static DictamenServiceBusinessRemote getDictamenServiceBusiness() {
		DictamenServiceBusinessRemote ejb = null; 
		try {
			ejb = (DictamenServiceBusinessRemote) getContextoLocal()
					.lookup("dictamenServiceBusiness#mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.DictamenServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getDictamenServiceBusiness()", e);
		}
		if(ejb != null){
			LOG.debug("Regresando EJB");
		}else{
			LOG.debug("EJB NULLLLLL");
		}
		return ejb;
	}
	
	public static AnalisisServiceBusinessRemote getAnalisisServiceBusiness() {
		AnalisisServiceBusinessRemote ejb = null; 
		try {
			ejb = (AnalisisServiceBusinessRemote) getContextoLocal()
					.lookup("analisisMovimientoBusiness#mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getAnalisisServiceBusiness()", e);
		}
		if(ejb != null){
			LOG.debug("Regresando EJB");
		}else{
			LOG.debug("EJB NULLLLLL");
		}
		return ejb;
	}

	public static ClasificacionServiceBusinessRemote getClasificacionServiceBusinessMAC() {
		ClasificacionServiceBusinessRemote ejb = null; 
		try {
			ejb = (ClasificacionServiceBusinessRemote) getContextoLocal()
					.lookup("clasificacionServiceBusiness#mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getAnalisisServiceBusiness()", e);
		}
		if(ejb != null){
			LOG.debug("Regresando EJB");
		}else{
			LOG.debug("EJB NULLLLLL");
		}
		return ejb;
	}

	public static ValidaClasificacionServiceBusinessRemote getValidaClasificacionServiceBusiness() {
		ValidaClasificacionServiceBusinessRemote ejb = null; 
		try {
			ejb = (ValidaClasificacionServiceBusinessRemote) getContextoLocal()
					.lookup("validaClasificacionServiceBusiness#mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ValidaClasificacionServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getValidaClasificacionServiceBusiness()", e);
		}
		if(ejb != null){
			LOG.debug("Regresando EJB");
		}else{
			LOG.debug("EJB NULLLLLL");
		}
		return ejb;
	}
	
	public static DatosClemServiceBusinessRemote getDatosClemServiceBusiness() {
		DatosClemServiceBusinessRemote ejb = null; 
		try {
			ejb = (DatosClemServiceBusinessRemote) getContextoLocal()
					.lookup("datosClemServiceBusiness#mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getDatosClemServiceBusiness()", e);
		}
		if(ejb != null){
			LOG.debug("Regresando EJB");
		}else{
			LOG.debug("EJB NULLLLLL");
		}
		return ejb;
	}
	
	public static SolicitudServiceBusinessRemote getServiceBusiness() {
		SolicitudServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (SolicitudServiceBusinessRemote) getContextoLocal()
					.lookup("solicitudServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudPersonaBusiness()", e);
		}
		return ejb;
	}

	public static ClasificacionServiceRemote getClasificacionServiceBusiness() {
		ClasificacionServiceRemote ejb = null; // NOPMD
		try {
			ejb = (ClasificacionServiceRemote) getContextoLocal()
					.lookup("clasificacionServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.ClasificacionServiceRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudPersonaBusiness()", e);
		}
		return ejb;
	}
	
	public static ConsultaPersonaMoralServiceBusinessRemote getConsultaPersonaMoralServiceBusinessRemote() {
		ConsultaPersonaMoralServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (ConsultaPersonaMoralServiceBusinessRemote) getContextoLocal()
					.lookup("consultaPersonaMoralServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaMoralServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In consultaPersonaMoralServiceBusiness()", e);
		}
		return ejb;
	}
	
	public static ConsultaPersonaFisicaServiceBusinessRemote getConsultaPersonaFisicaServiceBusinessRemote() {
		ConsultaPersonaFisicaServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (ConsultaPersonaFisicaServiceBusinessRemote) getContextoLocal()
					.lookup("consultaPersonaFisicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In consultaPersonaMoralServiceBusiness()", e);
		}
		return ejb;
	}
	
	public static IndividuoServiceBusinessRemote getIndividuoServiceBusiness() {
		IndividuoServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (IndividuoServiceBusinessRemote) getContextoLocal()
					.lookup("individuoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getIndividuoServiceBusiness()", e);
		}
		return ejb;
	}
	
	public static SujetoObligadoServiceBusinessRemote getSujetoServiceBusiness() {
		SujetoObligadoServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (SujetoObligadoServiceBusinessRemote) getContextoLocal()
					.lookup("sujetoObligadoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSujetoBusiness()", e);
		}
		return ejb;
	}
	
	public static PersonaFisicaServiceBusinessRemote getPersonaServiceBusiness() {
    	PersonaFisicaServiceBusinessRemote service = null;
        try {
            service = (PersonaFisicaServiceBusinessRemote) getContextoLocal()
                .lookup("personaFisicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static AfiliacionServiceBusinessRemote getAfiliacionServiceBusiness() {
		AfiliacionServiceBusinessRemote service = null;
        try {
            service = (AfiliacionServiceBusinessRemote) getContextoLocal()
                .lookup("afiliacionServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	//mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion
	
	public static AfiliacionGlobalServiceRemote getAfiliacionGlobalServiceBusiness() {
		AfiliacionGlobalServiceRemote service = null;
        try {
            service = (AfiliacionGlobalServiceRemote) getContextoLocal()
                .lookup("afiliacionGlobalServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.AfiliacionGlobalServiceRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static SolicitudBusinessRemote getSolicitudBusinessRemote() {
		SolicitudBusinessRemote service = null;
        try {
            service = (SolicitudBusinessRemote) getContextoLocal()
                .lookup("solicitudBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static EMailProducer getEMailQProducer() {
		EMailProducer service = null;
        try {
            service = (EMailProducer) getContextoLocal()
                .lookup("EMailQProducer#mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static SolicitudServiceBusinessRemote getSolicitudServiceBusiness() {
    	SolicitudServiceBusinessRemote service = null;
        try {
            service = (SolicitudServiceBusinessRemote) getContextoLocal()
                .lookup("solicitudServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static ArpBusinessRemote getARPBusiness() {
		ArpBusinessRemote service = null;
        try {
            service = (ArpBusinessRemote) getContextoLocal()
                .lookup("arpBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote");
            System.out.println("Regresando instancia de servicio");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	
	public static ManejadorReportesRemote getManejadroReportes() {
		ManejadorReportesRemote service = null;
        try {
            service = (ManejadorReportesRemote) getContextoLocal()
                .lookup("manejadorReportesBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	
	public static HldaClientServiceRemote getHLDAService() {
		HldaClientServiceRemote service = null;
        try {
            service = (HldaClientServiceRemote) getContextoLocal()
                .lookup("hldaClientServiceBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.interfaces.HldaClientServiceRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	
	public static ComponentesExternosBusinessRemote getComponentesExternosService() {
		ComponentesExternosBusinessRemote service = null;
        try {
            service = (ComponentesExternosBusinessRemote) getContextoLocal().lookup("componentesExternosBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static RuleServiceBusinessRemote getRuleServiceBusiness() {
		RuleServiceBusinessRemote service = null;
        try {
            service = (RuleServiceBusinessRemote) getContextoLocal()
                .lookup("ruleServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static AltaPatronalIDSEIntegrador getAltaIdseServiceBusiness() {
		AltaPatronalIDSEIntegrador service = null;
        try {
            service = (AltaPatronalIDSEIntegrador) getContextoLocal()
                .lookup("altaIdse#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static SolicitudPersonaBusinessRemote getSolicitudPersonaBusinessRemote() {
		
	    SolicitudPersonaBusinessRemote ejb = null;
	    try {
	        ejb = (SolicitudPersonaBusinessRemote) getContextoLocal()
	            .lookup("solicitudPersonaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote");
	    }
	    catch (NamingException e) {
	        e.printStackTrace();
	    }
	    return ejb;
	}
	
	public static RepresentanteLegalServiceBusinessRemote getRepresetanteLegalBusinessRemote() {
		
		RepresentanteLegalServiceBusinessRemote ejb = null;
	    try {
	        ejb = (RepresentanteLegalServiceBusinessRemote) getContextoLocal()
	            .lookup("representanteLegalServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote");
	    }
	    catch (NamingException e) {
	        e.printStackTrace();
	    }
	    return ejb;
	}

	public static SolicitudPersonaBusinessRemote getSolicitudPersonaServiceBusiness() {
		SolicitudPersonaBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (SolicitudPersonaBusinessRemote) getContextoLocal()
					.lookup("solicitudPersonaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSolicitudPersonaBusiness()", e);
		}
		return ejb;
	}
	
	public static RegistroPatronalServiceBusinessRemote getRegistroPatronalService() {
		RegistroPatronalServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (RegistroPatronalServiceBusinessRemote) getContextoLocal()
					.lookup("registroPatronalServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getRegistroPatornalServiceRemote()", e);
		}
		return ejb;
	}

	
	public static DomicilioServiceBussinessExternosRemote getDomicilioServices() {
		DomicilioServiceBussinessExternosRemote ejb = null; // NOPMD
		try {
			ejb = (DomicilioServiceBussinessExternosRemote) getContextoLocal()
					.lookup("domicilioExternosServiceBusiness#mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote");
		} catch (NamingException e) {
			LOG.error("In getDomicilioServices()", e);
		}
		return ejb;
	}
	
	public static DomicilioServiceBusinessRemote getDomicilioService() {
		DomicilioServiceBusinessRemote ejb = null; 
		try {
			ejb = (DomicilioServiceBusinessRemote) getContextoLocal()
					.lookup("domicilioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getDomicilioService()", e);
		}
		return ejb;
	}

	public static MediosContactoServiceBusinessRemote getMediosContactoService() {
		MediosContactoServiceBusinessRemote ejb = null; 
		try {
			ejb = (MediosContactoServiceBusinessRemote) getContextoLocal()
					.lookup("mediosContactoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getMediosContactoService()", e);
		}
		return ejb;
	}

	public static PersonasAutorizadasServiceRemote getPersonasAutorizadasService() {
		PersonasAutorizadasServiceRemote ejb = null; 
		try {
			ejb = (PersonasAutorizadasServiceRemote) getContextoLocal()
					.lookup("personasAutorizadasService#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote");
		} catch (NamingException e) {
			LOG.error("In getPersonasAutorizadasService()", e);
		}
		return ejb;
	}
	
	public static SocioServiceBusinessRemote getSociosService() {
		SocioServiceBusinessRemote ejb = null; 
		try {
			ejb = (SocioServiceBusinessRemote) getContextoLocal()
					.lookup("socioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSociosService()", e);
		}
		return ejb;
	}

	public static RuleServiceBusinessRemote getRulesService() {
		RuleServiceBusinessRemote ejb = null; 
		try {
			ejb = (RuleServiceBusinessRemote) getContextoLocal()
					.lookup("ruleServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSociosService()", e);
		}
		return ejb;
	}

	public static FirmaClemServiceBusinessRemote getFirmaBusiness() {
		FirmaClemServiceBusinessRemote ejb = null; 
		try {
			ejb = (FirmaClemServiceBusinessRemote) getContextoLocal()
					.lookup("firmaClemServiceBusiness#mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.FirmaClemServiceBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getSociosService()", e);
		}
		return ejb;
	}
	
	public static PersonaMoralBusinessRemote getPersonaMoralBusinessRemote() {
		PersonaMoralBusinessRemote ejb = null; 
		try {
			ejb = (PersonaMoralBusinessRemote) getContextoLocal()
					.lookup("personaMoralBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote");
		} catch (NamingException e) {
			LOG.error("In getPersonaMoralBusinessRemote()", e);
		}
		return ejb;
	}	

//	public static ServiciosPersonaBusinessRemote getServiciosPersonaBusinessRemote() {
//		ServiciosPersonaBusinessRemote ejb = null; 
//		try {
//			ejb = (ServiciosPersonaBusinessRemote) getContextoLocal()
//					.lookup("personaMoralBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote");
//		} catch (NamingException e) {
//			LOG.error("In getPersonaMoralBusinessRemote()", e);
//		}
//		return ejb;
//	}	

    public static ServiciosPersonaBusinessRemote getServiciosPersonaBusiness() {
        ServiciosPersonaBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (ServiciosPersonaBusinessRemote) getContextoLocal().lookup("serviciosPersonaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote");

        } catch (NamingException e) {
            LOG.error("In getServiciosPersonaBusiness()", e);
        }
        return ejb;
    }

    public static PersonaMoralBusinessRemote getPersonaMoralBusiness() {
        PersonaMoralBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (PersonaMoralBusinessRemote) getContextoLocal().lookup("personaMoralBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote");

        } catch (NamingException e) {
        	throw new RuntimeException(e);
        }
        return ejb;
    }
    
	public static IDSEQueueProducerRemote getIDSEProducer() {
		LOG.debug("getting IDSEQueueProducerRemote");
		IDSEQueueProducerRemote ejb = null;
		try {
			ejb = (IDSEQueueProducerRemote) getContextoLocal()
					.lookup("idseQProducerBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.IDSEQueueProducerRemote");
		} catch (NamingException e) {
			e.printStackTrace();
			fail("no encontrada");
		}
		return ejb;
	}
	
//    public static ProcesaTramitesServiceRemote getProcesaTramitesService() {
//    	ProcesaTramitesServiceRemote ejb = null; // NOPMD
//        try {
//            ejb = (ProcesaTramitesServiceRemote) getContextoLocal().lookup("procesaTramitesServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.ProcesaTramitesServiceRemote");
//
//        } catch (NamingException e) {
//        	throw new RuntimeException(e);
//        }
//        return ejb;
//    }

    
//    public static EscritoDesacuerdoBusinessRemote getSEscritoDesacuerdoBusiness() {
//    	EscritoDesacuerdoBusinessRemote ejb = null; 
//        try {
//            ejb = (EscritoDesacuerdoBusinessRemote) getContextoLocal().lookup("escritoDesacuerdoBusiness#mx.gob.imss.distss.delta.rtt.service.interfaces.EscritoDesacuerdoBusinessRemote");
//
//        } catch (NamingException e) {
//            LOG.error("In getSEscritoDesacuerdoBusiness()", e);
//        }
//        return ejb;
//    }

    
    
}
 