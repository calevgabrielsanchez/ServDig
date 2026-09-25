package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;

public class EJBLocator {

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
        	e.printStackTrace();
        }
        return iCtx;
    }

    public static DocumentoProbatorioServiceBusinessRemote getServiceBusiness() {
    	DocumentoProbatorioServiceBusinessRemote ejb = null; // NOPMD
        try {
            ejb = (DocumentoProbatorioServiceBusinessRemote) getContextoLocal().lookup("documentoProbatorioServiceBusiness#mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote");
        } catch (NamingException e) {
        	e.printStackTrace();
        }
        return ejb;
    }
}
