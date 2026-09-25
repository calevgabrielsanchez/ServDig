package mx.gob.imss.ctirss.delta.gestion.patron.service.ejb;

import java.util.List;
import javax.naming.Context;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Sistema;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote;

public class OperacionTransaccional {


	public static void test (Context ctx) throws Exception {

		System.out.println("************ Se inicia proceso de Catalogo de Sistemas");
		ConsultaBusinessRemote consulta = (ConsultaBusinessRemote) ctx.lookup("java:global/classes/ConsultaBusiness!mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote");

		System.out.println("+++++++++++++++++++ Operacion transaccional");
		consulta.pruebaTransaccional();
		
	}
}
