package mx.gob.imss.ctirss.delta.gestion.patronal.service;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.RegistrosPatronales34ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.arp.ArpBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ConcluirAltaPatronalBusinessRemote;

public class EjbLocator {
    private static final Logger log = LoggerFactory.getLogger(EjbLocator.class);

    private static Context getContexto() {
        Context iCtx = null; // NOPMD
        try {
        	
            final Hashtable<String, String> env = new Hashtable<String, String>(); // NOPMD
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            
            env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
            env.put(Context.SECURITY_PRINCIPAL, "weblogic");
            env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
            
//            env.put(Context.PROVIDER_URL, "t3://vzapma07.imss.gob.mx:8001,vzapma07.imss.gob.mx:8003,vzapma12.imss.gob.mx:8001,vzapma12.imss.gob.mx:8003,vzapmb09.imss.gob.mx:8001,vzapmb09.imss.gob.mx:8003");
//            env.put(Context.SECURITY_PRINCIPAL, "wloperator");
//            env.put(Context.SECURITY_CREDENTIALS, "53rv4p%wl%1p1r1t1r$.");
            
            
            
            
//            env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
//            env.put(Context.SECURITY_PRINCIPAL, "weblogic");
//            env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
            iCtx = new InitialContext(env);

        } catch (NamingException e) {
            log.error("In getContexto()", e);
        }
        return iCtx;
    }

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
}

