package mx.gob.imss.cit.cda.service.business;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

public class ManejadorReportesTest {

	//private transient ManejadorReportesRemote manejadorReportesBusiness = EjbLocator.getManejadorReportesRemote();

	@Test
	public void ejecutaReporte() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		String pathIMG = "reportes/images/";
		String REPORTE_CAPTURA_DATOS_SOLICITUD = "comprobanteSolicitudCDA.jrxml";
		String REPORTE_PRUEBA = "reportePrueba.jrxml";

//		parametros.put(JRParameter.REPORT_LOCALE, new Locale("es", "MX"));
//		parametros.put("titulo", "prueba");
//		parametros.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
//		parametros.put("folioSolicitud", "01234");
//		parametros.put("fechaSolicitud", "01/01/2016");
//		parametros.put("fechaHoraRecepcion", "01/01/2016");
//		parametros.put("cadenaOriginal", "CADENOTA");
//		parametros.put("secuenciaNotarial", "Sec");
//		parametros.put("selloDigital", "Sello");
//		parametros.put("numeroSerie", "Serie");
		ByteArrayOutputStream repo = ejecutaReporte(parametros,
				REPORTE_CAPTURA_DATOS_SOLICITUD);
		try {
			OutputStream outputStream = new FileOutputStream("C:/test/reporteTest.pdf");
			repo.writeTo(outputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Resultado" + repo.toByteArray());
	}
	
	private ByteArrayOutputStream ejecutaReporte(Map<String, Object> parametros, String reporte) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport("dsdfC:/test/REPORTE_PRUEBA.jrxml", null);
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
			exporter.setParameter(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY, Boolean.TRUE);
			exporter.exportReport();
		} catch (Exception e) {// Agregar las excepciones personalizadas
			// log.error("----- error al generar el reporte " + reporte, e);
		}

		return byteArrayOutputStream;
	}

}
