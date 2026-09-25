/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author daniel.hernandez
 *
 */
public class GeneraReporte {

	/**
	 *
	 * Metodo para generar reporte en formato PDF
	 * @param rutaPlantilla
	 * @param nombreReporte
	 * @param tipoRep
	 * @param datosReporte
	 * @return String : Ruta donde se genero el reporte. 
	 */
	public byte[] generaReportePDF(String rutaPlantilla, String nombreReporte, String tipoRep, HashMap<String, Object> datosReporte) {
		JasperReport jasperReport;
		JasperPrint jasperPrint = null;
		
		try {
			jasperReport = JasperCompileManager.compileReport(rutaPlantilla.concat("/plantilla/").concat(nombreReporte).concat(ExtensionEnum.JRXML.getExtension()));
			jasperPrint = JasperFillManager.fillReport(jasperReport, datosReporte, new JREmptyDataSource());
			return JasperExportManager.exportReportToPdf(jasperPrint);
		
		} catch (JRException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 *
	 * Metodo para generar reporte en formato PDF ademas de exportarlo en un array de bytes para guardado
	 * @param rutaPlantilla
	 * @param nombreReporte
	 * @param tipoRep
	 * @param datosReporte
	 * @return String : Ruta donde se genero el reporte. 
	 */
	public Map<String, Object> generaReportePDFBytes(String rutaPlantilla, String nombreReporte, String tipoRep, HashMap<String, Object> datosReporte) {
		System.out.println("Datos antes de mandar al reporte........");
		Iterator its = (Iterator) datosReporte.keySet().iterator();
		while(its.hasNext()){
			Object key =  its.next();
		  System.out.println("Clave: " + key + " -> Valor: " + datosReporte.get(key));
		}
		
		Map<String, Object> resultado = new HashMap<String, Object>();
		JasperReport jasperReport;
		JasperPrint jasperPrint = null;
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		String nombreFinal = formatter.format(date);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] doctosBytes = null;
		
		try {
			jasperReport = JasperCompileManager.compileReport(rutaPlantilla.concat("/plantilla/").concat(nombreReporte).concat(ExtensionEnum.JRXML.getExtension()));
			jasperPrint = JasperFillManager.fillReport(jasperReport, datosReporte, new JREmptyDataSource());
		
			//Creamos el exporter para obtener el array de bytes del documento
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,byteArrayOutputStream);
			exporter.exportReport();
			//obtenemos el array de bytes del documento
			doctosBytes = byteArrayOutputStream.toByteArray();
		} catch (JRException e) {
			e.printStackTrace();
		}
		resultado.put("nombreArchivo", nombreFinal.concat(ExtensionEnum.PDF.getExtension()));
		resultado.put("arrayDocto", doctosBytes);
		
		//Pintamos lo que mandamos al reporte
				System.out.println("Datos que mandamos al reporte.......");
				Iterator it = (Iterator) resultado.keySet().iterator();
				while(it.hasNext()){
				  Object key = it.next();
				  System.out.println("Clave: " + key + " -> Valor: " + resultado.get(key));
				}
		
		return resultado;

	}
	
	
	public byte[] generarReporteExcel(String rutaPlantilla, String nombreReporte, HashMap<String, String> datosReporte, Map<String, Object> parametros) {
		
		ByteArrayOutputStream byteArraySalida = new ByteArrayOutputStream();
		List<JasperPrint> prints = new ArrayList<JasperPrint>();
		JasperReport report;
		byte[] archivo = null;
		try {
			
			JasperReport subReporte = (JasperReport) JRLoader.loadObject(rutaPlantilla.concat("/reporteGeneralObra_subreport.jasper"));
			parametros.put("subReport", subReporte);

			report = (JasperReport) JRLoader.loadObject(rutaPlantilla.concat("/reporteGeneralObra.jasper"));

			JasperPrint print = JasperFillManager.fillReport(report,
					parametros, new JREmptyDataSource());
			prints.add(print);

			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, prints);
			exporter.setParameter(
					JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
					Boolean.TRUE);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					byteArraySalida);
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
					Boolean.TRUE);
			exporter.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			exporter.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.FALSE);
			exporter.exportReport();

			archivo = byteArraySalida.toByteArray();
			byteArraySalida.close();
		} catch (JRException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return archivo;
	}
	
	
	public <T> byte[] generarReporteExcel(String rutaPlantilla, String nombreReporte, String nombreSubReporte, 
			HashMap<String, Object> datosReporte, List<T> datosTabla) {
		
        ByteArrayOutputStream byteArraySalida = new ByteArrayOutputStream();
        List<JasperPrint> prints = new ArrayList<JasperPrint>();
		JasperReport reporte = null;
		JasperReport subReporte = null;
            
        try {
        	
            subReporte = (JasperReport) JRLoader.loadObject(rutaPlantilla.concat(nombreSubReporte).concat(ExtensionEnum.JASPER.getExtension()));
            reporte = (JasperReport) JRLoader.loadObject(rutaPlantilla.concat(nombreReporte).concat(ExtensionEnum.JASPER.getExtension()));
            datosReporte.put("rutaPlantilla", rutaPlantilla);
            datosReporte.put("subReport", subReporte);
            datosReporte.put("datosTabla", datosTabla);
            
			//Le enviamos los datos al reporte
            JasperPrint print = JasperFillManager.fillReport(reporte, datosReporte, new JREmptyDataSource());
            prints.add(print);
			
			//Generamos el reporte
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, prints);
            exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArraySalida);
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.exportReport();
			
            byte[] archivo = byteArraySalida.toByteArray();
            byteArraySalida.close();
			
            return archivo;
        } catch (JRException jre) {
        	jre.printStackTrace();
        } catch (IOException ioe) {
        	ioe.printStackTrace();
        }

		return null;
	}

}
