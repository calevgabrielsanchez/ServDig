package mx.gob.imss.ctirss.delta.gestion.patron.service.ejb;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class EjbTest {
	private static EJBContainer ejbContainer;
	private static Context ctx;

	@BeforeClass
	public static void setUp() {
		ejbContainer = EJBContainer.createEJBContainer();
		ctx = ejbContainer.getContext();
	}

	@AfterClass
	public static void tearDown() {
		ejbContainer.close();
	}

	@Test
	public void testFindAll() {
		try {
			System.out.println("************ SE INICIA EL PROCESO DE PRUEBA DE EJB'S");
			
			//PRUEBA TRANSACCIONAL
			//OperacionTransaccional.test(ctx);

			
			//CATALOGO DE SISTEMAS
			CatalogoSistema.test(ctx);
			
			//CONSULTAS
			//Consultas.test(ctx);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
