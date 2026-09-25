package mx.gob.imss.distss.derechohabientes.adimss;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes.RespuestaPatronesVigentes;
import mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes.WSPatronesVigentesService;
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
        assertTrue( true );
    }
    
    
    public void testPatronesVigentes(){
    	try {
    		RespuestaPatronesVigentes resp = WSPatronesVigentesService.getService().getConsultaPatronesVigentes("37843113");
    		System.out.println(resp.getPatronesActivos().get(0));
		} catch(Exception e) {
			System.out.println(e);
		} 
    }
}
