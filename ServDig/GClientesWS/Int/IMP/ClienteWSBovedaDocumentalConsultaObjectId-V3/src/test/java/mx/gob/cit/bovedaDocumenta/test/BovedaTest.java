package mx.gob.cit.bovedaDocumenta.test;



import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.v3.consulta.schema.BovedaPort;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.v3.consulta.schema.BovedaPortService;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.v3.consulta.schema.GetEstatusDocRequest;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.v3.consulta.schema.GetEstatusDocResponse;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.v3.consulta.util.IdentificadorBovedaEnum;
import mx.gob.imss.cit.ws.externo.boveda.docuemntal.v3.consulta.util.TipoDocumentalBovedaEnum;

public  class BovedaTest 
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

@org.junit.Test
public void testConsultaBoveda(){
	BovedaPortService service = new BovedaPortService();
	BovedaPort cliente = service.getBovedaPortSoap11();
	GetEstatusDocRequest entrada = new GetEstatusDocRequest();
	entrada.setDocId("f271ec4f-3dbd-46e6-bba8-9afc49c25f5");
	entrada.setTipoDocumental(IdentificadorBovedaEnum.IDENTIFICADOR_TSPI.getDescripcion());
       
    cliente.getEstatusDoc(entrada);
    
    
    GetEstatusDocResponse salidaAlta = cliente.getEstatusDoc(entrada);
    System.out.println("la respuesta de la salida es {}" +salidaAlta.getCodigo()
    );
    
}
	
	
@org.junit.Test
public void testPrueba(){
	int entero;
	long largo;
	double doble;
	String cadena;
	char caracter;
	
	String cadenaArray[];
	cadenaArray = new String[1];
	int arr[] = {3, 1, 2, 5, 4};
	
	String [] cadenaArrayDos;
	cadenaArrayDos  = new String[20];
	cadenaArrayDos[0] = "hola";
	int[] intArray = new int[]{ 1,2,3,4,5,6,7,8,9,10 }; 
	
    System.out.println("la respuesta de la salida es " + cadenaArray );
    
    
}
	



}

