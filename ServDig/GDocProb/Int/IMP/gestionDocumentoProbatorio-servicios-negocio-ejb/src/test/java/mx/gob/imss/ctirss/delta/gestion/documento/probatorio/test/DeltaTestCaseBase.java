/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.test;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author vanderluk
 * 
 */
public class DeltaTestCaseBase extends TestCase {


	/**
	 * Log
	 */
	protected static final Log log = LogFactory.getLog(DeltaTestCaseBase.class);
	
	/**
	 * Contexto 
	 */
	private static Context _ic;
	
	
	
	
	
	
	

	/**
	 * Inicializacion del contexto de pruebas.
	 */
	
	static  {
		
		log.debug(" Iniciando el contexto de pruebas ...");
		
		try {
			
			
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			env.put(Context.PROVIDER_URL, "t3://localhost:7001");
			env.put(Context.SECURITY_PRINCIPAL, "weblogic");
			env.put(Context.SECURITY_CREDENTIALS, "weblogic1");
			
			_ic = new InitialContext(env);
		
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}

	
	/**
	 * 
	 * @param _strNameEjbService
	 * @return
	 * @throws NamingException
	 */
	public Object lookUp(String _strNameEjbService) throws NamingException{
		log.debug(" Look up for service [" + _strNameEjbService +"]");
		Object ejb = null;
		ejb = _ic.lookup(_strNameEjbService);
		return ejb;
	}

}
