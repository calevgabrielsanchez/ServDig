package mx.gob.imss.cit.cda.service.business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ClassPathResource;

import mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote;
import mx.gob.imss.cit.cda.service.model.OrigenesReporteDTO;
import mx.gob.imss.cit.cda.service.model.VariableDTO;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TramitesReportes;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
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
        private static final String SUBREPORT_DIR_CONST = "SUBREPORT_DIR";
	
	private static final int CERO = 0;
	private static final int FILA_UNO = 1; 
	private static final int FILA_DOS = 2;
	
	/**Mascara para el formato de moneda*/
	private DecimalFormat moneda = new DecimalFormat("###0.00");
	/**Mascara para el formato de fecha de la vista*/
	private SimpleDateFormat fechaCorta = new SimpleDateFormat("dd-MM-yyyy");
	
	public static final String RUTA_BASE_REPORTES = "/reportes/";
    public static final String REPORTE_CDA_JASPER = RUTA_BASE_REPORTES + "reporteGenerado.jasper";
    public static final String SUBREPORTE_CDA_JASPER = RUTA_BASE_REPORTES + "subReporteGenerado.jasper";
    public static final String SUBREPORTE_CDA_JASPER_ORIGENES = RUTA_BASE_REPORTES + "subReporteOrigenes.jasper";
	
	public byte[] ejecutaReporte(Map<String, Object> parametros1, Map<String, Object> parametros2) {
		
		byte[] arreglo = new byte[0];
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			parametros1.put("IMAGENES_DIR", new ClassPathResource(IMG_DIR).getPath());
			parametros1.put(SUBREPORT_DIR_CONST, new ClassPathResource(SUBREPORT_DIR).getPath());
			JREmptyDataSource emptyDS = new JREmptyDataSource();
			JasperReport report2 = (JasperReport) JRLoader.loadObject(new ClassPathResource(SUBREPORT_DIR+COMPROBANTE_SOLICITUD).getInputStream());
			JasperPrint print2 = JasperFillManager.fillReport(report2, parametros1, emptyDS);
			
			parametros2.put("IMAGENES_DIR", new ClassPathResource(IMG_DIR).getPath());
			parametros2.put(SUBREPORT_DIR_CONST, new ClassPathResource(SUBREPORT_DIR).getPath());
			JasperReport report1 = (JasperReport) JRLoader.loadObject(new ClassPathResource(SUBREPORT_DIR+FORMATO_SOLICITUD).getInputStream());
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
			
			log.error("----- error al generar el reporte {}" + e);
		}
		
		
		return arreglo;
	}
	
	
	public byte[] ejecutaReporteCertificacion(Map<String, Object> parametros) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			parametros.put("IMAGENES_DIR", new ClassPathResource(IMG_DIR).getPath());
			parametros.put(SUBREPORT_DIR_CONST, new ClassPathResource(SUBREPORTE_DIR).getPath());
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
	
	/**
	 * Nuevos reportes CDA
	 */
	
	/**
     * Genera un reporte en PDF
     * @param nombreReporte
     * @param parameters
     * @return
     */
    public byte[] generarReportePDF(String nombreReporte, Map<String, Integer> mapVariables, Map<Integer, Integer> mapOrigenes, String delegacion, String subdelegacion, String tipoVariable) {
    	log.debug("******generarReportePDF [{}]"+ mapVariables.size());
    	Map<String, Object> parametros = new HashMap<String, Object>();
    	
    	List<VariableDTO> variables = new ArrayList<VariableDTO>();
    	List<OrigenesReporteDTO> origenes = new ArrayList<OrigenesReporteDTO>();
    	VariableDTO variable;
    	OrigenesReporteDTO origen;
    	Integer totalEstadistica = 0;
    	
		for (Map.Entry<String, Integer> entry : mapVariables.entrySet()){
			variable = new VariableDTO();
			variable.setDescripcion(entry.getKey());
			variable.setCantidad(entry.getValue());
			variables.add(variable);
		}
		
		for(Map.Entry<Integer, Integer> entry : mapOrigenes.entrySet()){
			origen = new OrigenesReporteDTO();
			origen.setNumeroInternet(entry.getKey());
			origen.setNumeroVentanilla(entry.getValue());
			origenes.add(origen);
			totalEstadistica = origen.getNumeroInternet() + origen.getNumeroVentanilla();
		}

		try{
			
		  InputStream inputSub = new ClassPathResource(SUBREPORTE_CDA_JASPER).getInputStream();
		  InputStream inputOrigenes = new ClassPathResource(SUBREPORTE_CDA_JASPER_ORIGENES).getInputStream();
		  
		  log.debug("******RUTA subreporte [{}]"+"aaaa");
			
          parametros.put(SUBREPORT_DIR_CONST, inputSub);
      	  parametros.put("estadisticas", variables);
      	  parametros.put("SUBREPORT_ORIG", inputOrigenes);
      	  parametros.put("origenes", origenes);
      	  parametros.put("delegacion", delegacion);
      	  parametros.put("subdelegacion", subdelegacion);
      	  parametros.put("variable", tipoVariable.toUpperCase());
      	  parametros.put("totalEstadistica", totalEstadistica);
      	  
		} catch (Exception e) {
			log.error("Error generarReportePDF {}" + e);
			
		}

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            JREmptyDataSource emptyDS = new JREmptyDataSource();
            JasperReport report = (JasperReport) JRLoader
                    .loadObject(new ClassPathResource(REPORTE_CDA_JASPER).getInputStream());
            JasperPrint print = JasperFillManager.fillReport(report, parametros, emptyDS);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
        	log.error("Error al generar el reporte PDF [{}]", e);
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    
    /**
     * Metodo para la generacion del reporte Excel
     * @param titulo
     * @param encabezados
     * @param datos
     * @return
     */
    public byte[] generaReporteXLS(List<TramitesReportes> datos){
    	HSSFWorkbook wb = new HSSFWorkbook();
    	HSSFRow row;
    	HSSFCell cell;
    	HSSFSheet sheet = wb.createSheet("Reporte");
    	row = sheet.createRow(CERO);
    	
    	String titulo = mx.gob.imss.cit.cda.service.model.ReportesEncabezadoEnum.ENCABEZADO_EXCEL.getTitulo();
    	String[] encabezados = mx.gob.imss.cit.cda.service.model.ReportesEncabezadoEnum.ENCABEZADO_EXCEL.getEncabezado();
    	
    	HSSFFont fTitulo = creaTipoLetra(wb, (short) 10, true);
    	HSSFFont fEncabezado = creaTipoLetra(wb, (short)8, false);
    	
    	HSSFCellStyle styleTitulo = creaEstiloCelda(wb, fTitulo, HSSFCellStyle.ALIGN_LEFT, false);
    	HSSFCellStyle styleEncabezado = creaEstiloCelda(wb, fEncabezado, HSSFCellStyle.ALIGN_CENTER, true);
    	HSSFCellStyle styleCadena = creaEstiloCelda(wb, fEncabezado, HSSFCellStyle.ALIGN_LEFT, false);
    	HSSFCellStyle styleNumero = creaEstiloCelda(wb, fEncabezado, HSSFCellStyle.ALIGN_RIGHT, false);

    	cell = row.createCell(CERO);
    	cell.setCellValue(titulo);
    	cell.setCellStyle(styleTitulo);
    	CellRangeAddress cellRango = new CellRangeAddress(CERO, CERO, CERO, encabezados.length-1);
    	sheet.addMergedRegion(cellRango);
    	row = sheet.createRow(FILA_UNO);
    	
    	cell = generaEncabezadoExcel(encabezados, row, cell, sheet, styleEncabezado);
    	
    	if(datos != null && !datos.isEmpty()){
    		int col = 0;
    		for(TramitesReportes obj : datos) {
        		row = sheet.createRow(FILA_DOS + col);
        		crearCelda(0, maskCadena(obj.getFolio()), styleCadena, cell, row);
        		crearCelda(1, maskCadena(obj.getCurp()), styleCadena, cell, row);
        		crearCelda(2, maskCadena(obj.getNssInvolucrados()), styleCadena, cell, row);
        		crearCelda(3, maskCadena(obj.getVencida()), styleNumero, cell, row);
        		crearCelda(4, maskCadena(obj.getDelegacion()), styleCadena, cell, row);
        		crearCelda(5, maskCadena(obj.getSubdelegacion()), styleCadena, cell, row);
        		crearCelda(6, maskCadena(obj.getAutorizo()), styleCadena, cell, row);
        		crearCelda(7, maskCadena(obj.getResponsable()), styleCadena, cell, row);
        		crearCelda(8, maskCadena(obj.getOrigen()), styleCadena, cell, row);
        		crearCelda(9, maskCadena(obj.getTipo()), styleCadena, cell, row);
        		crearCelda(10, maskCadena(obj.getFechaSolicitud()), styleCadena, cell, row);
        		crearCelda(11, maskCadena(obj.getFechaFinalizacion()), styleCadena, cell, row);
        		crearCelda(12, maskCadena(obj.getUltimaActualizacion()), styleCadena, cell, row);
        		crearCelda(13, maskCadena(obj.getEstatus()), styleCadena, cell, row);
        		
        		col++;
        	}
    	}
    	
    	ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
    	
    	try {
			wb.write(byteArray);
		} catch (IOException e) {
			 log.error("Error al generar el reporte XLS [{}]", e);
		}
    	return byteArray.toByteArray();
    }
    
        
	/**
	 * @param encabezados
	 * @param row
	 * @param cell
	 * @param sheet
	 * @param styleEncabezado
	 * @return
	 */
    private HSSFCell generaEncabezadoExcel(String[] encabezados, HSSFRow row, HSSFCell cell, HSSFSheet sheet,
			HSSFCellStyle styleEncabezado) {
		for(int i = 0; i < encabezados.length; i++){
    		cell = row.createCell(i);
    		cell.setCellStyle(styleEncabezado);
    		cell.setCellValue(encabezados[i]);
    		sheet.autoSizeColumn(i);
    	}
		return cell;
	}
    
	/**
	 * Metodo para la creacion de las celdas que forman el excel apliacando los estilos
	 * @param id
	 * @param val
	 * @param stilo
	 * @param cell
	 * @param row
	 */
	private void crearCelda(Integer id, String val, HSSFCellStyle stilo, HSSFCell cell, HSSFRow row) {
    	cell = row.createCell(id);
		cell.setCellValue(val);
		cell.setCellStyle(stilo);
	}

    /**
     * Metodo que genera el estilo de letra para el reporte en excel, el metodo
     * recibe el libro de excel, el tamnio y un booleano para indicar si la letra
     * sera en negrillas o normal.
     * 
     * @param wb
     * @param size
     * @param strong
     * @return
     */
    private HSSFFont creaTipoLetra (HSSFWorkbook wb, short size, boolean strong){
    	HSSFFont font = wb.createFont();
    	font.setFontHeightInPoints(size);
    	font.setFontName("Arial");
    	font.setColor(IndexedColors.BLACK.getIndex());
    	if(strong){
    		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	}
    	font.setItalic(false);
    	return font;
    }
    
    /**
     * Metodo para genera el estilo de celda aplicado al reporte en Excel, el
     * metodo recibe el libro de excel, el tipo de letra, la alineacion del texto
     * y 2 boleanos para determinar si se crearan bordes y color de fondo en las
     * celdas creadas.
     *  
     * @param wb
     * @param font
     * @param cellAlign
     * @param borde
     * @param fondo
     * @return
     */
    private HSSFCellStyle creaEstiloCelda (HSSFWorkbook wb, HSSFFont font, short cellAlign, boolean encabezado){
    	HSSFPalette palete = wb.getCustomPalette();
    	palete.setColorAtIndex((byte) 55, (byte)166, (byte)169, (byte) 169);
	
    	HSSFCellStyle style = wb.createCellStyle();
    	if(encabezado){
    		style.setFillForegroundColor(palete.getColor(55).getIndex());
    		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	}
    	style.setFont(font);
    	style.setAlignment(cellAlign);
    	return style;	
    }
    
    private String maskCadena(Object campo){
		if (campo==null)
			return "";
		return campo.toString();
	}
	
    private String maskMoneda(Object campo){
		if (campo==null)
			return "";
		return moneda.format(new BigDecimal(campo.toString()));
	}
	
	private String maskFecha(Object campo){
		if (campo==null)
			return "";
		return fechaCorta.format((Date)campo);
	}
	

}