package mx.gob.imss.cit.ws.externo.boveda.documental;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import mx.gob.imss.cit.ws.externo.boveda.documental.rest.BovedaRestClient;
import mx.gob.imss.cit.ws.externo.boveda.documental.schema.Atributo;
import mx.gob.imss.cit.ws.externo.boveda.documental.schema.EntradaConsulta;
import mx.gob.imss.cit.ws.externo.boveda.documental.schema.SalidaConsulta;
import mx.gob.imss.cit.ws.externo.boveda.documental.utils.AtributoBovedaEnum;
import mx.gob.imss.cit.ws.externo.boveda.documental.utils.IdentificadorBovedaEnum;
import mx.gob.imss.cit.ws.externo.boveda.documental.utils.RutaBovedaEnum;
import mx.gob.imss.cit.ws.externo.boveda.documental.utils.TipoDocumentalBovedaEnum;

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

    public void testApp()
    {
        assertTrue( true );
    }

    /*
    public void testConsultaBoveda(){
    	System.out.println("Se utiliza el enum " + RutaBovedaEnum.RUTA_TSPI.getDescripcion());
    	System.out.println("Se utiliza el enum " + IdentificadorBovedaEnum.IDENTIFICADOR_TSPI.getDescripcion());
    	System.out.println("Se utiliza el enum " + TipoDocumentalBovedaEnum.TIPO_DOCUMENTAL_TSPI.getDescripcion());
    	
    	BovedaServicio service = new BovedaServicio();
    	BovedaServicioSoap cliente = service.getBovedaServicioSoap();
    	
    	
        EntradaConsulta consulta = new EntradaConsulta();
        Atributo atributo = new Atributo();
        atributo.setNombre(AtributoBovedaEnum.ATRIBUTO_ID.getDescripcion());
        atributo.setValor("2fd1265a-bef7-4e00-a69c-ef649bd726a3;v1.0");
        
        consulta.getAtributo().add(atributo);
        consulta.setIdentificador(IdentificadorBovedaEnum.IDENTIFICADOR_TSPI.getDescripcion());
        consulta.setTipoDocumental(TipoDocumentalBovedaEnum.TIPO_DOCUMENTAL_TSPI.getDescripcion());
        
        SalidaConsulta salidaAlta = cliente.consultaDocumento(consulta);
        System.out.println("la respuesta de la salida es {}" +salidaAlta.getDescripcion());
    	
        
    }*/
    
    public void testConsultaBovedaRest(){
    	System.out.println("Se utiliza el enum REST " + RutaBovedaEnum.RUTA_TSPI.getDescripcion());
    	System.out.println("Se utiliza el enum REST " + IdentificadorBovedaEnum.IDENTIFICADOR_TSPI.getDescripcion());
    	System.out.println("Se utiliza el enum REST " + TipoDocumentalBovedaEnum.TIPO_DOCUMENTAL_TSPI.getDescripcion());
    	
    	
        EntradaConsulta consulta = new EntradaConsulta();
        Atributo atributo = new Atributo();
        atributo.setNombre(AtributoBovedaEnum.ATRIBUTO_ID.getDescripcion());
        atributo.setValor("0a24ea1b-3610-457c-a3ff-6efb7ef69773");
        
        consulta.getAtributo().add(atributo);
        consulta.setIdentificador(IdentificadorBovedaEnum.IDENTIFICADOR_TSPI.getDescripcion());
        consulta.setTipoDocumental(TipoDocumentalBovedaEnum.TIPO_DOCUMENTAL_TSPI.getDescripcion());
        
        SalidaConsulta salidaAlta = BovedaRestClient.consultaDocumento(consulta);
        System.out.println("la respuesta de la salida REST es {}" +salidaAlta.getDescripcion());
    	
        
    }

}