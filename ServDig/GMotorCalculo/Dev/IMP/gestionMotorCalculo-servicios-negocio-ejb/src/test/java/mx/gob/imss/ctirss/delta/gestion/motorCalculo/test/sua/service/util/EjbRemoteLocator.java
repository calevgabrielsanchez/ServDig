package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.util;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.RecargoServiceRemote;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

public class EjbRemoteLocator {

	private static InitialContext obtContext(AMBIENTE ambiente) throws NamingException, IOException {
		Context iCtx = null;
		final Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

		Properties prop = new Properties();
		prop.load(ClassLoader.getSystemResourceAsStream("opcionesTest.properties"));

		String PROVIDER_URL = prop.getProperty(ambiente.name() + "_PROVIDER_URL");
		String SECURITY_PRINCIPAL = prop.getProperty(ambiente.name() + "_SECURITY_PRINCIPAL");
		String SECURITY_CREDENTIALS = prop.getProperty(ambiente.name() + "_SECURITY_CREDENTIALS");

		env.put(Context.PROVIDER_URL, PROVIDER_URL);
		env.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
		env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);

		iCtx = new InitialContext(env);

		return (InitialContext) iCtx;
	}

	public static <T> T obtainEjb(Class<T> t, AMBIENTE ambiente) {

		String ejbName = t.getSimpleName().replace("Remote","");

		if(ejbName.equals("DerechohabienteService")){

		}else{
			String complement = null;
			if(!ejbName.endsWith(complement = "Business")){
				ejbName += complement;
			}
		}

		ejbName = WordUtils.uncapitalize(ejbName);

		try {
			return obtResource(ambiente,ejbName + "#" + t.getName());
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}

	private static  <T> T obtResource(AMBIENTE ambiente, String name) throws NamingException, IOException {
		return (T) obtContext(ambiente).lookup(name);
	}

	public enum AMBIENTE {
		LOCAL,
		STAGE
	}
}
