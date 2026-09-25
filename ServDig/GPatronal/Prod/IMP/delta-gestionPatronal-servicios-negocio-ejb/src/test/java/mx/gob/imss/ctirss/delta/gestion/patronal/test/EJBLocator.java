package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.ConsultaPersonaMoralServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.AfiliacionGlobalServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.global.service.interfaces.ClasificacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.reporte.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration.EMailProducer;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.interfaces.HldaClientServiceRemote;

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
			
			
//			env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001,vzapma07.imss.gob.mx:8003,vzapma12.imss.gob.mx:8001,vzapma12.imss.gob.mx:8003,vzapmb09.imss.gob.mx:8001,vzapmb09.imss.gob.mx:8003");
//			env.put(Context.SECURITY_PRINCIPAL, "wloperator");
//			env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");
			
			
			env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");

			
//			env.put(Context.PROVIDER_URL, "t3://172.24.116.37:13051");
//			env.put(Context.SECURITY_PRINCIPAL, "admin_wlgeptqa");
//			env.put(Context.SECURITY_CREDENTIALS, "wlgeptqa_admin");

			
			ic = new InitialContext(env);
			System.out.println(ic);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return ic;
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
}
 