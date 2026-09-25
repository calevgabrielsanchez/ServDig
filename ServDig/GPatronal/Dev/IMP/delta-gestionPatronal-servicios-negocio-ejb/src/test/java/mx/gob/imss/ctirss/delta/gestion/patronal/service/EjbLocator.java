package mx.gob.imss.ctirss.delta.gestion.patronal.service;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ReportesAnalisisBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnSATServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaMoralEnSATServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.RegistrosPatronales34ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AltaPatronalHelperRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
//import mx.gob.imss.ctirss.idse.service.interfaces.RegistroPatronalIdseServiceBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;
import mx.gob.imss.ctirss.idse.service.interfaces.RegistroPatronalIdseServiceBusinessRemote;

public class EjbLocator {
    private static final Logger log = LoggerFactory.getLogger(EjbLocator.class);

    private static Context getContexto() {
        Context iCtx = null; // NOPMD
        try {
        	
            final Hashtable<String, String> env = new Hashtable<String, String>(); // NOPMD
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            
          //PRODUCCION
			env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001,vzapma07.imss.gob.mx:8003,vzapma12.imss.gob.mx:8001,vzapma12.imss.gob.mx:8003,vzapmb09.imss.gob.mx:8001,vzapmb09.imss.gob.mx:8003");
			env.put(Context.SECURITY_PRINCIPAL, "wloperator");
			env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");

			//LOCAL			
//	          env.put(Context.PROVIDER_URL, "t3://localhost:7001");
//	          env.put(Context.SECURITY_PRINCIPAL, "weblogic");
//	          env.put(Context.SECURITY_CREDENTIALS, "password123");
            
            //Stage
//            env.put(Context.PROVIDER_URL, "t3://172.16.5.176:8001");
//            env.put(Context.SECURITY_PRINCIPAL, "wloperator");
//            env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");

            
          //PRODUCCION			
//			env.put(Context.PROVIDER_URL, "t3://172.16.5.224:8001");  //172.16.5.11  172.16.5.224 mirror 
//			env.put(Context.SECURITY_PRINCIPAL, "wloperator");
//			env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");
            
            
            iCtx = new InitialContext(env);

        } catch (NamingException e) {
            log.error("In getContexto()", e);
        }
        return iCtx;
    }

//    public static RegistroPatronalIdseServiceBusinessRemote getRegistroPatronalIdseServiceBusiness() {
//    	RegistroPatronalIdseServiceBusinessRemote service = null;
//        try {
//            service = (RegistroPatronalIdseServiceBusinessRemote) getContexto()
//                .lookup("registroPatronalIdseServiceBusiness#mx.gob.imss.ctirss.idse.service.interfaces.RegistroPatronalIdseServiceBusinessRemote");
//            return service;
//        }
//        catch (NamingException e) {
//            throw new RuntimeException(e);
//        }
//    }
    
    
    public static SujetoObligadoServiceBusinessRemote getSujetoObligadoServiceBusiness() {
        SujetoObligadoServiceBusinessRemote service = null;
        try {
            service = (SujetoObligadoServiceBusinessRemote) getContexto()
                .lookup("sujetoObligadoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static RegistroPatronalServiceBusinessRemote getRegistroPatronalServiceBusiness() {
    	RegistroPatronalServiceBusinessRemote service = null;
        try {
            service = (RegistroPatronalServiceBusinessRemote) getContexto()
                .lookup("registroPatronalServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static SolicitudServiceBusinessRemote getSolicitudServiceBusiness() {
    	SolicitudServiceBusinessRemote service = null;
        try {
            service = (SolicitudServiceBusinessRemote) getContexto()
                .lookup("solicitudServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    
	public static SolicitudBusinessRemote getSolicitudBusinessRemote() {
		SolicitudBusinessRemote service = null;
        try {
            service = (SolicitudBusinessRemote) getContexto()
                .lookup("solicitudBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArpBusinessRemote getARPBusiness() {
    	ArpBusinessRemote service = null;
        try {
            service = (ArpBusinessRemote) getContexto()
                .lookup("arpBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static ConcluirAltaPatronalBusinessRemote getConcluirAltaBusiness() {
    	ConcluirAltaPatronalBusinessRemote service = null;
        try {
            service = (ConcluirAltaPatronalBusinessRemote) getContexto()
                .lookup("concluirAltaPatronalBusiness#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static AltaPatronalHelperRemote getAltaPatronalHelperRemote() {
    	AltaPatronalHelperRemote service = null;
        try {
            service = (AltaPatronalHelperRemote) getContexto()
                .lookup("altaPatronalHelper#mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AltaPatronalHelperRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static AltaPatronalIDSEIntegrador obtenerIDSEService(){
		AltaPatronalIDSEIntegrador ejb = null; // NOPMD
		try {
			ejb = (AltaPatronalIDSEIntegrador) getContexto()
					.lookup("altaIdse#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ejb;
	}
    
    public static RegistrosPatronales34ServiceBusinessRemote getRegistrosPatronales34ServiceBusinessRemote(){
    	RegistrosPatronales34ServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (RegistrosPatronales34ServiceBusinessRemote) getContexto()
					.lookup("registrosPatronales34ServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.RegistrosPatronales34ServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ejb;
	}
    
	public static DomicilioServiceBusinessRemote getDomicilioService() {
		DomicilioServiceBusinessRemote ejb = null; 
		try {
			ejb = (DomicilioServiceBusinessRemote) getContexto()
					.lookup("domicilioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ejb;
	}

	public static RuleServiceBusinessRemote getRulesService() {
		RuleServiceBusinessRemote ejb = null; 
		try {
			ejb = (RuleServiceBusinessRemote) getContexto()
					.lookup("ruleServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ejb;
	}

	public static RepresentanteLegalServiceBusinessRemote getRLService() {
		RepresentanteLegalServiceBusinessRemote ejb = null; 
		try {
			ejb = (RepresentanteLegalServiceBusinessRemote) getContexto()
					.lookup("representanteLegalServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ejb;
	}

	public static DocumentoProbatorioServiceBusinessRemote getDoctoProbService() {
		DocumentoProbatorioServiceBusinessRemote ejb = null; 
		try {
			ejb = (DocumentoProbatorioServiceBusinessRemote) getContexto()
					.lookup("documentoProbatorioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ejb;
	}
	
    public static ActividadEcServiceRemote getActividadEcServiceRemote() {
    	ActividadEcServiceRemote service = null;
        try {
            service = (ActividadEcServiceRemote) getContexto()
                .lookup("clasificacionActividadEconomicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ReportesAnalisisBusinessRemote getReportesAnalisisBusinessRemote() {
    	ReportesAnalisisBusinessRemote service = null;
        try {
            service = (ReportesAnalisisBusinessRemote) getContexto()
                .lookup("reporteAnalisisBusiness#mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ReportesAnalisisBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    
	public static RuleServiceBusinessRemote getRuleServiceBusiness() {
		RuleServiceBusinessRemote service = null;
        try {
            service = (RuleServiceBusinessRemote) getContexto()
                .lookup("ruleServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
    public static PersonaMoralBusinessRemote getPersonaMoralBusiness() {
        PersonaMoralBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (PersonaMoralBusinessRemote) getContexto().lookup("personaMoralBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote");

        } catch (NamingException e) {
        	throw new RuntimeException(e);
        }
        return ejb;
    }

    
	public static IndividuoServiceBusinessRemote getIndividuoServiceBusiness() {
		IndividuoServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (IndividuoServiceBusinessRemote) getContexto()
					.lookup("individuoServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		return ejb;
	}
	
	public static PersonaBusinessRemote getPersonaBusinessRemote() {
		PersonaBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (PersonaBusinessRemote) getContexto()
					.lookup("personaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		return ejb;
	}
	
	public static SolicitudPersonaBusinessRemote getSolicitudPersonaBusinessRemote() {
		
	    SolicitudPersonaBusinessRemote ejb = null;
	    try {
	        ejb = (SolicitudPersonaBusinessRemote) getContexto()
	            .lookup("solicitudPersonaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote");
	    }
	    catch (NamingException e) {
	        e.printStackTrace();
	    }
	    return ejb;
	}
	
	public static SocioServiceBusinessRemote getSociosService() {
		SocioServiceBusinessRemote ejb = null; 
		try {
			ejb = (SocioServiceBusinessRemote) getContexto()
					.lookup("socioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ejb;
	}
	
	public static RegistroPatronalIdseServiceBusinessRemote getRegistroPatronalIdseServiceBusiness() {
		RegistroPatronalIdseServiceBusinessRemote ejb = null; // NOPMD
		try {
			ejb = (RegistroPatronalIdseServiceBusinessRemote) getContexto()
					.lookup("registroPatronalIdseServiceBusiness#mx.gob.imss.ctirss.idse.service.interfaces.RegistroPatronalIdseServiceBusinessRemote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ejb;
	}
	
    public static LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote getLocalizarPersonaFisicaEnRENAPOServiceBusiness() {
    	LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote) getContexto()
            		.lookup("localizarPersonaFisicaEnRENAPOServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote");
        } catch (NamingException e) {
        	e.printStackTrace();
        }
        return ejb;
   }
    
    public static LocalizarPersonaFisicaEnSATServiceBusinessRemote getLocalizarPersonaFisicaEnSATServiceBusiness() {
    	LocalizarPersonaFisicaEnSATServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (LocalizarPersonaFisicaEnSATServiceBusinessRemote) getContexto()
            		.lookup("localizarPersonaFisicaEnSATServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnSATServiceBusinessRemote");
        } catch (NamingException e) {
        	e.printStackTrace();
        }
        return ejb;
   }
    
    public static LocalizarPersonaMoralEnSATServiceBusinessRemote getLocalizarPersonaMoralEnSATServiceBusiness() {
    	LocalizarPersonaMoralEnSATServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (LocalizarPersonaMoralEnSATServiceBusinessRemote) getContexto()
            		.lookup("localizarPersonaMoralEnSATServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaMoralEnSATServiceBusinessRemote");
        } catch (NamingException e) {
        	e.printStackTrace();
        }
        return ejb;
   }    
    
	public static PersonaFisicaServiceBusinessRemote getPersonaServiceBusiness() {
    	PersonaFisicaServiceBusinessRemote service = null;
        try {
            service = (PersonaFisicaServiceBusinessRemote) getContexto()
                .lookup("personaFisicaServiceBusiness#mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote");
            return service;
        }
        catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static PersonaMoralBusinessRemote getPersonaMoralBusinessRemote() {
		PersonaMoralBusinessRemote ejb = null; 
		try {
			ejb = (PersonaMoralBusinessRemote) getContexto()
					.lookup("personaMoralBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote");
		} catch (NamingException e) {
            throw new RuntimeException(e);
		}
		return ejb;
	}
	
    public static ServiciosPersonaBusinessRemote getServiciosPersonaBusiness() {
        ServiciosPersonaBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (ServiciosPersonaBusinessRemote) getContexto().lookup("serviciosPersonaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote");

        } catch (NamingException e) {
        	throw new RuntimeException(e);
        }
        return ejb;
    }	
    
	public static PersonasAutorizadasServiceRemote getPersonasAutorizadasService() {
		PersonasAutorizadasServiceRemote ejb = null; 
		try {
			ejb = (PersonasAutorizadasServiceRemote) getContexto()
					.lookup("personasAutorizadasService#mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote");
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		return ejb;
	}
	
    public static PersonaBusinessRemote getPersonaBusiness() {
        PersonaBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (PersonaBusinessRemote) getContexto().lookup("personaBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote");

        } catch (NamingException e) {
        	throw new RuntimeException(e);
        }
        return ejb;
    }

}

