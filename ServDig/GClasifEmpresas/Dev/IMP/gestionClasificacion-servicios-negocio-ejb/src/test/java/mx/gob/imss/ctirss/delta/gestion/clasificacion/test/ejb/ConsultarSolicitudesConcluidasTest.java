package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb;

import java.text.SimpleDateFormat;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud.SolicitudServiceEntityLocal;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosAnalisisConsulta;
import mx.gob.imss.ctirss.delta.model.clasificacion.SolicitudConcluida;

import org.junit.Test;

/**
 * Clase de prueba para la consulta de solicitudes
 * @author Leticia E. Torres R.
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 13/08/2012
 */
public class ConsultarSolicitudesConcluidasTest extends GestionClasifEmpresasTestCaseBase {

	/**
	 * Ejecuta la prueba unitaria del proceso de asignación de solicitud
	 * @throws Exception
	 */
	@Test
	public void testAsignarSolicitud() throws Exception {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		final FiltrosAnalisisConsulta filtrosAnalisisConsulta = new FiltrosAnalisisConsulta();
		filtrosAnalisisConsulta.setDelegacion("39");
		filtrosAnalisisConsulta.setGrupoTramite("0");
		filtrosAnalisisConsulta.setRegistroPatronal("A403927012");
		filtrosAnalisisConsulta.setPeriodoInicio(simpleDateFormat.parse("2012-08-13"));
		filtrosAnalisisConsulta.setPeriodoFin(simpleDateFormat.parse("2012-08-13"));
				
		final DatosEntradaPaginador<FiltrosAnalisisConsulta> parametrosPaginador = new DatosEntradaPaginador<FiltrosAnalisisConsulta>();
		parametrosPaginador.setModelo(filtrosAnalisisConsulta);
		parametrosPaginador.setiDisplayStart(0);
		parametrosPaginador.setiDisplayLength(10);
				
		final Object object = initialContext.lookup("solicitudServiceEntityLocal");
		assertNotNull(object);
		assertTrue(object instanceof SolicitudServiceEntityLocal);
		final SolicitudServiceEntityLocal solicitudServiceEntityLocal = (SolicitudServiceEntityLocal) object;
		assertNotNull(solicitudServiceEntityLocal);
		final DatosSalidaPaginador<SolicitudConcluida> salidaPaginador = solicitudServiceEntityLocal.consultarSolicitudesConcluidas(parametrosPaginador);
		System.out.println("Total de registros a desplegar: " + salidaPaginador.getiTotalDisplayRecords());
		System.out.println("Total de registros: " + salidaPaginador.getiTotalRecords());
		System.out.println("Total de registros en la lista: " + salidaPaginador.getAaData().size());
	    System.out.println("Registro patronal         Solicitud");
	    for (final SolicitudConcluida solicitudConcluida : salidaPaginador.getAaData()) {
			System.out.print(solicitudConcluida.getRegistroPatronal());
			System.out.print("               ");
			System.out.print(solicitudConcluida.getCveIdSolicitud() + "\n");
		}
	}
	
}
