package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.reporte;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.Map;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.ServiceEntity;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.springframework.core.io.ClassPathResource;

@Stateless
public class ReporteEntity extends ServiceEntity  implements ReporteEntityLocal {
	
	
	private Connection conexion;
	private String pathIMG = "reportes/";
	
	
	@SuppressWarnings("deprecation")
	@Override
	public Connection retrieveCMTConnection(){
		return this.getSession().connection();
	}
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public ByteArrayOutputStream ejecutaAvisoDeModificacion(Map<String, Object> parametros) {
		conexion = this.getSession().connection();
		parametros.put("IMAGENES_DIR", new ClassPathResource(pathIMG).getPath());
		parametros.put("SUBREPORT_DIR", new ClassPathResource(pathIMG).getPath());
		ByteArrayOutputStream reporte;
		reporte = ejecutaReporte(parametros, "AvisoDeModificacion.jrxml", true);
		if (reporte != null) {
			this.log.debug("reporte size " + reporte.size());
		} else {
			this.log.debug("reporte size no tiene ");
		}
		return reporte;
	}
	
	
	private ByteArrayOutputStream ejecutaReporte(
			Map<String, Object> parametros, String reporte,
			boolean pasarConexion) {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {

			JasperReport report = JasperCompileManager
					.compileReport(new ClassPathResource("reportes/" + reporte)
							.getInputStream());
			JasperPrint print;
			if (pasarConexion) {
				print = JasperFillManager.fillReport(report, parametros,
						conexion);
			} else {
				JREmptyDataSource emptyDS = new JREmptyDataSource();
				print = JasperFillManager.fillReport(report, parametros, emptyDS);
			}

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArrayOutputStream);

			exporter.exportReport();

			return byteArrayOutputStream;

		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
		}

		return null;
	}
	
}
