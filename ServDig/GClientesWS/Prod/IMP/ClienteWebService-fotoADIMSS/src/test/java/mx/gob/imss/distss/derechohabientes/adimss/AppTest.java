package mx.gob.imss.distss.derechohabientes.adimss;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import mx.gob.imss.distss.derechohabientes.adimss.schema.WSConsultaAdimssService;
import mx.gob.imss.distss.derechohabientes.adimss.schema.WSConsultaAdimssService_Service;
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
    
    
    public void testFotoAsegurado(){
    	WSConsultaAdimssService_Service service = new WSConsultaAdimssService_Service();
    	WSConsultaAdimssService cliente = service.getWSConsultaAdimssServiceSOAP();
    	byte[] foto = cliente.getFotografiaAsegurado("11967000552", 1);
    	
    	System.out.println("la foto es [" +foto+ "]");
    	
    	
				
		try {
			File file = new File("D:/foto.jpg");
			if(!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			
		//	FileOutputStream fos = new FileOutputStream("D:\foto.jpg");
			 fos.write(foto);
			 fos.close();
			 BufferedImage imgFoto = null;
			 InputStream in = new ByteArrayInputStream(foto);
			 System.out.println("ya parsr [" +foto+ "]");
		    	imgFoto = ImageIO.read(in);
		    	System.out.println("read [" +imgFoto+ "]");
		    	if(imgFoto != null){
		    		System.out.println("el tamanio de la imagen es [" + imgFoto.getHeight() +"]");
		    	}
			 System.out.println("ya salio [" +foto+ "]");
		} catch(Exception e) {
			System.out.println(e);
		} 
    }
}
