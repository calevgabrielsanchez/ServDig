package mx.gob.imss.cit.ws.externo.boveda.documental;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.schema.Atributo;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.schema.EntradaConsulta;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.schema.IESEndpointService;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.schema.IESServicioSoap;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.schema.SalidaAlta;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.schema.SalidaConsulta;

/**
 * Unit test for simple App.
 */
public class BovedaTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BovedaTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BovedaTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    
    public void testConsultaBoveda(){
    	IESEndpointService service = new IESEndpointService();
    	IESServicioSoap cliente = service.getIESEndpointPort();
    	EntradaConsulta entrada = new EntradaConsulta();
    	
    	
        EntradaConsulta entradaConsultaGuarderia = new EntradaConsulta();
        String tipoDocumentalTspi = "D:cda:imss";
        //String tipoDocumentalHistoricoTspi = "cmis:document";
        Atributo atributo = new Atributo();
       
          atributo.setNombre("objectId");
          atributo.setValor("996458ed-20b7-4425-85e9-01f81ae52ace;1.0");
           entradaConsultaGuarderia.setTipoDocumental(tipoDocumentalTspi);
        entradaConsultaGuarderia.getAtributo().add(atributo);
        
        SalidaConsulta salidaAlta = cliente.consultaDocumento(entrada);
        System.out.println("la respuesta de la salida es {}" +salidaAlta.getDescripcion());
        
    }
    	
    	
		

 

}
