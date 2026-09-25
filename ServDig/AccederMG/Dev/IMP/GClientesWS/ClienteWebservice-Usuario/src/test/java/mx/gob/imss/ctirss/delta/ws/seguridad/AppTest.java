package mx.gob.imss.ctirss.delta.ws.seguridad;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.AdmonUsuariosException_Exception;
import mx.gob.imss.ctirss.sso.admonusuarios.service.impl.AdmonUsuariosService;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	System.out.println("Iniciando pruena ...");
    	AdmonUsuariosService service = new AdmonUsuariosService();
    	
    	try {
			System.out.println( service.getAdmonUsuariosPort().existeUsuario("DUSL821218HDFRLC09"));
		} catch (AdmonUsuariosException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}
