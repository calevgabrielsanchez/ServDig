package mx.gob.imss.cit.cda.web.app.reportes.helper;

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
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.web.app.reportes.constants.ReporteConstantes;
import mx.gob.imss.cit.cda.web.app.reportes.dto.ReporteDTO;
import mx.gob.imss.cit.cda.web.app.reportes.dto.VariableDTO;
import mx.gob.imss.cit.cda.web.app.reportes.enums.FormatoReporteEnum;
import mx.gob.imss.cit.cda.web.app.reportes.enums.ReportesEncabezadoEnum;
import mx.gob.imss.cit.cda.web.app.reportes.model.VariablesReportes;
import mx.gob.imss.cit.cda.web.app.reportes.model.TramitesReportes;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Clase que contiene diversos metodos para la generacion de reportes en 
 * formato PDF y Excel
 */
@Component
public class GenerarReporteHelper extends BaseReporteHelper {
	
	private static final int CERO = 0;
	private static final int FILA_UNO = 1; 
	private static final int FILA_DOS = 2;
	private static final String EXCEL = "xls";
	private static final String PDF = "pdf";
	private static final String NOMBRE_REPORTE = "reporte";
	
	
	/**Mascara para el formato de moneda*/
	private DecimalFormat moneda = new DecimalFormat("###0.00");
	/**Mascara para el formato de fecha de la Base de datos*/
	private SimpleDateFormat fechaBD = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	/**Mascara para el formato de fecha de la vista*/
	private SimpleDateFormat fechaCorta = new SimpleDateFormat("dd-MM-yyyy");
	
    private static final long serialVersionUID = -6948731311776085036L;
    
    /**
     * Metodo para crear el nombre de los reportes en formato PDF y Excel.
     * @param extension
     * @return
     */
    public String creaNombreReporte(String extension){
    	StringBuilder nombre = new StringBuilder();
    	String fecha = formatoFecha(new Date());

    	nombre.append(NOMBRE_REPORTE).append(fecha).append(".").append(extension);
    	
    	return nombre.toString();
    }
    
    public String creaNombreReporteExcel(){
    	return creaNombreReporte(EXCEL);
    }
    
    public String creaNombreReportePDF(){
    	return creaNombreReporte(PDF);
    }
    
    private String formatoFecha(Date fecha) {
        if (fecha != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
            return formatter.format(fecha);
        } else {
            return "";
        }
    }
    
    /**
	 * Metodo para la generacion del reporte de CDA
	 * 
	 * @return ReporteDTO
	 */
	public ReporteDTO obtenerReporteVariablesPDF(String tipoVariable) {
		getLogger().debug("******Generando reporte [{}]", "obtenerReporteRemuneracionesPDF");
		
		ReporteDTO reporteDTO = new ReporteDTO();
		List<TramitesReportes> list = new ArrayList<TramitesReportes>();
		//TramitesReportes tramitesAsignados = new TramitesReportes();
		List<VariableDTO> variables = new ArrayList<VariableDTO>();
		VariableDTO variable = new VariableDTO();
		String delegacion ="delegacionOPENam";
		String subdelegacion="subdelegacionOPENam";
		
//		se debe recuperar las estadisticas de sesion 
		for(int i=0; i< 10; i++){
			variable = new VariableDTO();
			variable.setDescripcion("tramitesAsignado"+(i+1));
			variable.setCantidad(i+1);
			variables.add(variable);
				}		
		
		Map<String, Object> parametros = new HashMap<String, Object>();

		try{
			
			InputStream inputSub = new ClassPathResource(ReporteConstantes.SUBREPORTE_CDA_JASPER).getInputStream();
		  
		  getLogger().debug("******RUTA subreporte [{}]"+"aaaa");
			
          parametros.put("SUBREPORT_DIR", inputSub);
      	  parametros.put("estadisticas", variables);
      	  parametros.put("delegacion", delegacion);
      	  parametros.put("subdelegacion", subdelegacion);
      	  parametros.put("variable", tipoVariable);
      	  
		} catch (Exception e) {
			getLogger().debug("EXCEPCION...");
			e.printStackTrace();
			
		}
		
		reporteDTO
				.setContenido(generarReportePDF(ReporteConstantes.REPORTE_CDA_JASPER, parametros));
		reporteDTO.setNombre(creaNombreReportePDF());
		reporteDTO.setContentType(FormatoReporteEnum.PDF.getContentType());
		return reporteDTO;
	}
    
    /**
     * Genera un reporte en PDF
     * @param nombreReporte
     * @param parameters
     * @return
     */
    public byte[] generarReportePDF(String nombreReporte, Map<String, Object> parameters) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            JREmptyDataSource emptyDS = new JREmptyDataSource();
            JasperReport report = (JasperReport) JRLoader
                    .loadObject(new ClassPathResource(nombreReporte).getInputStream());
            JasperPrint print = JasperFillManager.fillReport(report, parameters, emptyDS);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception e) {
            getLogger().error("Error al generar el reporte PDF [{}]", e.getMessage(), e);
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    public ReporteDTO obtenerReporteVariablesXLS(List<Object[]> datos){
    	ReporteDTO reporteDTO = new ReporteDTO();
    	
    	reporteDTO
				.setContenido(generaReporteXLS(datos));
		reporteDTO.setNombre(creaNombreReportePDF());
		reporteDTO.setContentType(FormatoReporteEnum.PDF.getContentType());
		return reporteDTO;
    }
    
    /**
     * Metodo para la generacion del reporte Excel
     * @param titulo
     * @param encabezados
     * @param datos
     * @return
     */
    public byte[] generaReporteXLS(List<Object[]> datos){
    	HSSFWorkbook wb = new HSSFWorkbook();
    	HSSFRow row;
    	HSSFCell cell;
    	HSSFSheet sheet = wb.createSheet("Reporte");
    	row = sheet.createRow(CERO);
    	
    	String titulo = ReportesEncabezadoEnum.ENCABEZADO_EXCEL.getTitulo();
    	String[] encabezados = ReportesEncabezadoEnum.ENCABEZADO_EXCEL.getEncabezado();
    	
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
    		for(Object[] obj : datos) {
        		row = sheet.createRow(FILA_DOS + col);
        		crearCelda(0, maskCadena(obj[0]), styleCadena, cell, row);
        		crearCelda(1, maskCadena(obj[1]), styleCadena, cell, row);
        		crearCelda(2, maskCadena(obj[2]), styleCadena, cell, row);
        		crearCelda(3, maskCadena(obj[3]), styleNumero, cell, row);
        		crearCelda(4, maskCadena(obj[4]), styleCadena, cell, row);
        		crearCelda(5, maskCadena(obj[5]), styleCadena, cell, row);
        		crearCelda(6, maskCadena(obj[6]), styleCadena, cell, row);
        		crearCelda(7, maskCadena(obj[7]), styleCadena, cell, row);
        		crearCelda(8, maskCadena(obj[8]), styleCadena, cell, row);
        		crearCelda(9, maskCadena(obj[9]), styleCadena, cell, row);
        		crearCelda(10, maskFecha(obj[10]), styleCadena, cell, row);
        		crearCelda(11, maskFecha(obj[11]), styleCadena, cell, row);
        		crearCelda(12, maskFecha(obj[12]), styleCadena, cell, row);
        		crearCelda(13, maskCadena(obj[13]), styleCadena, cell, row);
        		
        		col++;
        	}
    	}
    	
    	ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
    	
    	try {
			wb.write(byteArray);
		} catch (IOException e) {
			 getLogger().error("Error al generar el reporte XLS [{}]", e.getMessage(), e);
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
	public HSSFCell generaEncabezadoExcel(String[] encabezados, HSSFRow row, HSSFCell cell, HSSFSheet sheet,
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
    public void crearCelda(Integer id, String val, HSSFCellStyle stilo, HSSFCell cell, HSSFRow row) {
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
    public HSSFFont creaTipoLetra (HSSFWorkbook wb, short size, boolean strong){
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
    public HSSFCellStyle creaEstiloCelda (HSSFWorkbook wb, HSSFFont font, short cellAlign, boolean encabezado){
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
    
    public String maskCadena(Object campo){
		if (campo==null)
			return "";
		return campo.toString();
	}
    
    public String maskMoneda(Object campo){
		if (campo==null)
			return "";
		return moneda.format(new BigDecimal(campo.toString()));
	}
	
	public String maskFecha(Object campo){
		if (campo==null)
			return "";
		return fechaCorta.format((Date)campo);
	}
    
}
