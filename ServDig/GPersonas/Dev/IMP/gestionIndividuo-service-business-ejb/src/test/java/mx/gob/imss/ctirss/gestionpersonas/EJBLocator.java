package mx.gob.imss.ctirss.gestionpersonas;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaGlobalBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.interfaces.HldaClientServiceRemote;

public class EJBLocator {

    private static final Logger log = LoggerFactory.getLogger(EJBLocator.class);


    static Context getContextoLocal() {
        Context context = null;

        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            env.put(Context.PROVIDER_URL, "t3://desarrollo.imss.gob.mx:7001");
            env.put(Context.SECURITY_PRINCIPAL, "weblogic");
            env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
            context = new InitialContext(env);
            log.info("after initialContext");
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
        return context;
    }

    public static HldaClientServiceRemote getHldaClientService() {
        log.info("getHldaClientService");
        HldaClientServiceRemote ejb = null;
        try {
            ejb = (HldaClientServiceRemote) getContextoLocal()
                .lookup("hldaClientServiceBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.interfaces.HldaClientServiceRemote");
            log.info("{}", ejb);
            log.info("-------------");
        }
        catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ejb;
    }
    
    public static ComponentesExternosBusinessRemote getComponentesExternos() {
        log.info("getHldaClientService");
        ComponentesExternosBusinessRemote ejb = null;
        try {
            ejb = (ComponentesExternosBusinessRemote) getContextoLocal()
                .lookup("componentesExternosBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote");
            log.info("{}", ejb);
            log.info("-------------");
        }
        catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ejb;
    }
    
    public static PersonaGlobalBusinessRemote getPersonaGlobalService() {
        log.info("getPersonaGlobalBusinessRemote");
        PersonaGlobalBusinessRemote ejb = null;
        try {
            ejb = (PersonaGlobalBusinessRemote) getContextoLocal()
                .lookup("personaGlobalBusiness#mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaGlobalBusinessRemote");
            log.info("{}", ejb);
            log.info("-------------");
        }
        catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return ejb;
    }

}
