package mx.gob.imss.cit.cda.service.business;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.springframework.core.io.ClassPathResource;

import mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

@Stateless(name = "manejadorReportesBusiness", mappedName = "manejadorReportesBusiness")
public class ManejadorReportesBusiness extends AbstractServiceUtility implements ManejadorReportesRemote {
	
	private String IMG_DIR = "reportes/images/";
	private String SUBREPORT_DIR = "reportes/";
	
	private String SUBREPORTE_DIR = "reportes/certificadoCDA/";
	
	private String COMPROBANTE_SOLICITUD = "comprobanteSolicitudCDA.jasper";
	private String FORMATO_SOLICITUD = "SolicitudCorreccionDatosAsegurado.jasper";
	private String CERTIFICACION_SOLICITUD = "certificacionRegularizacionCDA.jasper";
	
	public byte[] ejecutaReporte(Map<String, Object> parametros1, Map<String, Object> parametros2) {
		
		byte[] arreglo = new byte[0];
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			parametros1.put("IMAGENES_DIR", new ClassPathResource(IMG_DIR).getPath());
			parametros1.put("SUBREPORT_DIR", new ClassPathResource(SUBREPORT_DIR).getPath());
			JREmptyDataSource emptyDS = new JREmptyDataSource();
			JasperReport report2 = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/"+COMPROBANTE_SOLICITUD).getInputStream());
			JasperPrint print2 = JasperFillManager.fillReport(report2, parametros1, emptyDS);
			
			parametros2.put("IMAGENES_DIR", new ClassPathResource(IMG_DIR).getPath());
			parametros2.put("SUBREPORT_DIR", new ClassPathResource(SUBREPORT_DIR).getPath());
			JasperReport report1 = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/"+FORMATO_SOLICITUD).getInputStream());
			JasperPrint print1 = JasperFillManager.fillReport(report1, parametros2, new JREmptyDataSource());
			
			
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
            jasperPrintList.add(print1);
            jasperPrintList.add(print2);
                                
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
            exporter.exportReport();
            arreglo = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            
            
		} catch (Exception e) {// Agregar las excepciones personalizadas
			e.printStackTrace();
			log.error("----- error al generar el reporte " + e);
		}
		
		
		return arreglo;
	}
	
	
	public byte[] ejecutaReporteCertificacion(Map<String, Object> parametros) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			parametros.put("IMAGENES_DIR", new ClassPathResource(IMG_DIR).getPath());
			parametros.put("SUBREPORT_DIR", new ClassPathResource(SUBREPORTE_DIR).getPath());
			JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/certificadoCDA/"+CERTIFICACION_SOLICITUD).getInputStream());
			JREmptyDataSource emptyDS = new JREmptyDataSource();
            JasperPrint print = JasperFillManager.fillReport(report, parametros, emptyDS);
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
            exporter.setParameter(JRPdfExporterParameter.FORCE_LINEBREAK_POLICY, Boolean.TRUE);
            exporter.exportReport();

		} catch (Exception e) {// Agregar las excepciones personalizadas
			log.error("----- error al generar el reporte " + CERTIFICACION_SOLICITUD, e);
		}

		return byteArrayOutputStream.toByteArray();
	}
	
	
	
	

}
