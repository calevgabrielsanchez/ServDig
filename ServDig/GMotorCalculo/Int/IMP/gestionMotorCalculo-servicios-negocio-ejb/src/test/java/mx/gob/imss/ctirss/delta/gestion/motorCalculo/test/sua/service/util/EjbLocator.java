package mx.gob.imss.ctirss.delta.gestion.motorCalculo.test.sua.service.util;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PortalCiudadanoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.RecargoServiceRemote;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbLocator {
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(EjbLocator.class);
	}

	private static InitialContext obtContext(Ambiente ambiente) throws NamingException, IOException {
		Context iCtx = null;
		final Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");

		Properties prop = new Properties();
		prop.load(ClassLoader.getSystemResourceAsStream("opcionesTest.properties"));

		String PROVIDER_URL = prop.getProperty(ambiente.name()+"_PROVIDER_URL");
		String SECURITY_PRINCIPAL = prop.getProperty(ambiente.name()+"_SECURITY_PRINCIPAL");
		String SECURITY_CREDENTIALS = prop.getProperty(ambiente.name()+"_SECURITY_CREDENTIALS");

		env.put(Context.PROVIDER_URL, PROVIDER_URL);
		env.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
		env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);

		iCtx = new InitialContext(env);

		return (InitialContext) iCtx;
	}

	private static  <T> T obtResource(Ambiente ambiente, String name) throws NamingException, IOException {
		return (T) obtContext(ambiente).lookup(name);
	}

	public static <T> T find(Class<T> t, Ambiente ambiente) {

		String ejbName;
		if(t.getSimpleName().equals("DomicilioServiceBussinessExternosRemote")){
			ejbName = "domicilioExternosServiceBusiness";
		}else if(t.getSimpleName().equals("EMailProducer")){
			ejbName = "EMailQProducer";
		}else{
			ejbName = t.getSimpleName().replace("Remote","");

			if(ejbName.equals("SolicitudQueueProducer")){
				ejbName = "solicitudQProducerBusiness";
			}else if(ejbName.equals("DerechohabienteService")){

			}else{
				String complement = null;
				if(!ejbName.endsWith(complement = "Business")){
					ejbName += complement;
				}
			}

			ejbName = WordUtils.uncapitalize(ejbName);
		}

		try {
			return obtResource(ambiente,ejbName + "#" + t.getName());
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  null;
	}
}
