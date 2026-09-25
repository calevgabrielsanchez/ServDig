package mx.gob.imss.ctirss.delta.gestion.patron.service.ejb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import javax.naming.Context;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Servicio;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Operacion;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.BitacoraServicios;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote;


public class Consultas {
	
	public static void test (Context ctx) throws Exception {
		System.out.println("************ Se inicia proceso de prueba");
		ConsultaBusinessRemote consulta = (ConsultaBusinessRemote) ctx.lookup("java:global/classes/ConsultaBusiness!mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote");

		System.out.println("+++++++++++++++++++ SERVICIOS");
		List<Servicio> listaServicios = consulta.getServicios("SistemaModificacionesPatronales");
		for (Servicio servicio : listaServicios) {
			System.out.println("servicio: " + servicio.getCveServicio());
		}
		System.out.println();

		System.out.println("+++++++++++++++++++ OPERACIONES");
		List<Operacion> listaOperaciones = consulta.getOperaciones("mx.gob.imss.ctirss.proyectotransaccional.servicios.dao.EjbEstatusSolicitudDao");
		for (Operacion operacion : listaOperaciones) {
			System.out.println("operacion: " + operacion.getCveOperacion());
		}
		System.out.println();

		System.out.println("+++++++++++++++++++ BIACORA");
		List<BitacoraServicios> listaBitacora = consulta.getBitacora(new BitacoraServicios(), consulta.TIPO_ORDENAMIENTO_ID );
		for (BitacoraServicios bitacora : listaBitacora) {
			System.out.println("bitacora: " + bitacora.getIdBitacora());
		}
		System.out.println();
		
		System.out.println("+++++++++++++++++++ BIACORA ENTRADA");
		String sEntrada = consulta.getBitacoraEntrada(95L);
		System.out.println("bitacora entrada: " + sEntrada);
		System.out.println();

		System.out.println("+++++++++++++++++++ BIACORA SALIDA");
		String sSalida = consulta.getBitacoraSalida(94L);
		System.out.println("bitacora salida: " + sSalida);
		System.out.println();
	}
}
