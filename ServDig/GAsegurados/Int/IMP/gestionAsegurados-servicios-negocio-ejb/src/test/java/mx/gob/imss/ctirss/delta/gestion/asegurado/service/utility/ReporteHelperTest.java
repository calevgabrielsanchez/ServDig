package mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.junit.Test;

public class ReporteHelperTest {

	@Test
	public void testReporte() throws JRException, IOException {
		final Solicitud solicitud = new Solicitud(23L);
		solicitud.setFechaSolicitud(new Date());
		for (int idTramite = 0; idTramite < 3; idTramite++) {
			agregarTramiteSolicitud(solicitud, idTramite);
		}
		JasperCompileManager
				.compileReportToFile(
						"src/main/resources/reportes/ReporteComprobanteAsignacion.jrxml",
						"src/main/resources/reportes/ReporteComprobanteAsignacion.jasper");
		final OutputStream osReport = new FileOutputStream(
				"ReporteUnitTest.pdf");
		osReport.write(new ReporteHelper().reporte(solicitud));
		osReport.flush();
		osReport.close();
	}

	private static void agregarTramiteSolicitud(final Solicitud solicitud,
			final int index) {
		final TramiteAsegurado tramite2 = new TramiteAsegurado();
		tramite2.setTramiteId(index + 0L);
		tramite2.setFechaTramite(new Date());
		tramite2.setObservacion("Ninguna, por ahora");
		final Sexo sexo = new Sexo();
		sexo.setIdSexo(2);
		sexo.setDescripcion("QUIMERA");

		final AsignacionNSS personaFisica = new AsignacionNSS();
		personaFisica.setNssStr("NSS value");
		personaFisica.setIdPersona(index + 0L);
		personaFisica.setNombre("Joaco");
		personaFisica.setPrimerApellido("Ponte");

		personaFisica.setSexo(sexo);

		personaFisica.setSegundoApellido("Agatha");
		personaFisica.setFechaNacimiento(new Date());
		personaFisica.getLugarNacimiento().setClave("9");
		personaFisica.getLugarNacimiento().setNombre("Distrito Federal");
		// personaFisica.setCurp("GFDR45454HRFDF");
		// personaFisica.setRfc("GFDR45454");
		tramite2.setFisica(personaFisica);
		solicitud.getTramites().add(tramite2);
	}

}
