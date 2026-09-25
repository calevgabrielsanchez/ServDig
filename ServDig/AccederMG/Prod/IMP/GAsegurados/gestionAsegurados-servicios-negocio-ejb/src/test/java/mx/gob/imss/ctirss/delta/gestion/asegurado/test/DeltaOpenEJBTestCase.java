/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.test;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

/**
 * @author vanderluk
 * 
 */
public class DeltaOpenEJBTestCase extends TestCase {
	protected InitialContext initialContext;
	protected InitialContext localInitialContext;

	/**
	 * Inicialización del contenedor de EJBs.
	 * 
	 * @throws NamingException
	 */
	public void setUp() throws NamingException {
		final Properties properties = new Properties();
		properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
		// properties.put("openejb.configuration",
		// "C:/workspace/gce/04IMP/gestionClasificacion-servicios-negocio-ejb/src/test/java/META-INF/gestionClasifEmpresasTest.conf");
		properties.put("DefaultDS", "new://Resource?type=DataSource");
		properties.put("DefaultDS.JdbcUrl", "jdbc:oracle:thin:@11.254.14.158:1521:DELTADS");
		properties.put("DefaultDS.username", "BDTU_DIDT");
		properties.put("DefaultDS.password", "BDTU_DIDT");

		initialContext = new InitialContext(properties);
	}
}
