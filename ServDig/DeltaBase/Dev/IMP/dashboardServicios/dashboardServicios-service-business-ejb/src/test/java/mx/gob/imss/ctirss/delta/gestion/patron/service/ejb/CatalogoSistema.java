package mx.gob.imss.ctirss.delta.gestion.patron.service.ejb;

import java.util.List;
import javax.naming.Context;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Sistema;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote;

public class CatalogoSistema {


	public static void test (Context ctx) throws Exception {

		System.out.println("************ Se inicia proceso de Catalogo de Sistemas");
		ConsultaBusinessRemote consulta = (ConsultaBusinessRemote) ctx.lookup("java:global/classes/ConsultaBusiness!mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote");

		System.out.println("+++++++++++++++++++ Operacion getSistemas");
		
		System.out.println("+++++++++++++++++++ con variable en nulo");
		Sistema filtroBusqueda = null;
		List<Sistema> listaSistemas = consulta.getSistemas(filtroBusqueda);
		for (Sistema sistema : listaSistemas) {
			System.out.println("Sistema: " + sistema.getCveSistema());
		}			
		System.out.println();
		
		System.out.println("+++++++++++++++++++ con variable vacia");
		filtroBusqueda = new Sistema();
		listaSistemas = consulta.getSistemas(filtroBusqueda);
		for (Sistema sistema : listaSistemas) {
			System.out.println("Sistema: " + sistema.getCveSistema());
		}			
		System.out.println();
		
		System.out.println("+++++++++++++++++++ con clave");
		filtroBusqueda = new Sistema();
		filtroBusqueda.setCveSistema("Nomina");
		listaSistemas = consulta.getSistemas(filtroBusqueda);
		for (Sistema sistema : listaSistemas) {
			System.out.println("Sistema: " + sistema.getCveSistema());
		}			
		System.out.println();
		
		System.out.println("+++++++++++++++++++ con descripcion");
		filtroBusqueda = new Sistema();
		filtroBusqueda.setDesSistema("ead");
		listaSistemas = consulta.getSistemas(filtroBusqueda);
		for (Sistema sistema : listaSistemas) {
			System.out.println("Sistema: " + sistema.getCveSistema());
		}			
		System.out.println();
		
		System.out.println("+++++++++++++++++++ Operacion getSistema");
		
		System.out.println("+++++++++++++++++++ con variable en nulo");
		Sistema sistema = consulta.getSistema(null);
		if (sistema != null){
			System.out.println("Sistema: " + sistema.getCveSistema());
		}
		System.out.println();


		System.out.println("+++++++++++++++++++ con variable vacia");
		sistema = consulta.getSistema("");
		if (sistema != null){
			System.out.println("Sistema: " + sistema.getCveSistema());
		}
		System.out.println();

		System.out.println("+++++++++++++++++++ con clave correcta");
		sistema = consulta.getSistema("Nomina");
		if (sistema != null){
			System.out.println("Sistema: " + sistema.getCveSistema());
		}
		System.out.println();

		System.out.println("+++++++++++++++++++ OPERACION ALTA");
		sistema = new Sistema();
		sistema.setCveSistema("SistemaPrueba");
		sistema.setDesSistema("Sistema Prueba de EJB");
		boolean bExito = consulta.altaSistema(sistema);
		System.out.println("Alta sistema: " + bExito);
		System.out.println();
		
		System.out.println("+++++++++++++++++++ OPERACION ALTA");
		sistema = new Sistema();
		sistema.setCveSistema("SistemaPrueba");
		sistema.setDesSistema("Modificacion a la descripcion");
		bExito = consulta.actualizaSistema(sistema);
		System.out.println("Alta sistema: " + bExito);
		System.out.println();
		
		System.out.println("+++++++++++++++++++ OPERACION BAJA");
		bExito = consulta.eliminaSistema("SistemaPrueba");
		System.out.println("Baja sistema: " + bExito);
		System.out.println();
		
	}
}
