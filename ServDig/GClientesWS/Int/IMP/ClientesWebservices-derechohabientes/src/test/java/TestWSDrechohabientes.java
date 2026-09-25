import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import mx.gob.imss.ultimospatrones.ws.InfoUltimosPatronesAseguradoWSClient;

public class TestWSDrechohabientes extends TestCase{
	
	   /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TestWSDrechohabientes( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TestWSDrechohabientes.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    
    public void testMovimientosAsegurado(){
    	
    	InfoUltimosPatronesAseguradoWSClient cliente = new InfoUltimosPatronesAseguradoWSClient();
    	try{
    	cliente.consultaUltimosPatronesAsegurado("01000000057");
    	}catch (Exception e) {
    		System.out.println("error en el cliente" +e.getMessage());
    		e.printStackTrace();
		}
    	
    }
    

}
