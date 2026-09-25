/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.test;


import java.io.File;
import java.util.HashMap;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.persistence.Persistence;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author vanderluk
 *
 */
public class DeltaTestCaseBase extends TestCase {
	
	
	
	

	//Logger log = LoggerFactory.getLogger(getClass());
	static EJBContainer ejbContainer;
	static Context ctx;

	public static Context getCtx() {
		return ctx;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@BeforeClass
	public  void setUp() {
		//Properties props = new Properties();
		//props.put("skip-client-modules", "true" );
		HashMap propiedades = new HashMap();
		//propiedades.put("skip-client-modules", "true" );		
		
		propiedades.put(EJBContainer.MODULES, new File(System.getProperty("basedir") + "/target/classes"));		
		//ejbContainer = EJBContainer.createEJBContainer(propiedades);
      //  System.getProperties().list(System.out);
        System.out.println("basedir: " + System.getProperty("basedir"));

		
		//REPALDO DEL ARCHIVO clases/persitance.xml a persistence.xml.bak
		//ESTE PROCESO ES NECESARIO YA QUE GLASSFISH AGREGA A SU CLASSPATH LAS SIGUIENTES RUTAS:
		//   /target/classes/
		//   /target/test-classes/
		//POR LO QUE INCOVENIENTEMENTE AGREGA 2 ARCHIVOS persistence.xml AL CLASSPATH y el que manda es el que se encuentra en /target/classes/
		
		System.out.println("Inicaliza Glassfish embedded .......................");
		
		@SuppressWarnings("unused")
		File respaldoArchivo = new File(System.getProperty("basedir") + "/target/classes/META-INF/persistence.xml");
		//respaldoArchivo.renameTo(new File(System.getProperty("basedir") + "/target/classes/META-INF/persistence.xml.bak"));
		
		//INICIALIZA EL CONTENEDOR DE EJB'S DE GLASSFISH
		
		ejbContainer = EJBContainer.createEJBContainer(propiedades);
		ctx = ejbContainer.getContext();
		
		Persistence.createEntityManagerFactory("deltaPersistenceUnit");
		
		
		//RESTABLECE EL ARCHIVO clases/persitance.xml
		@SuppressWarnings("unused")
		File archivoAOriginal = new File(System.getProperty("basedir") + "/target/classes/META-INF/persistence.xml.bak");
		//archivoAOriginal.renameTo(new File(System.getProperty("basedir") + "/target/classes/META-INF/persistence.xml"));
		
	}
	
	@AfterClass
	public  void tearDown() {
		ejbContainer.close();		
	}
	

	

}
