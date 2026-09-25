package mx.gob.imss.distss.delta.rtt.service.util;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;


import java.util.Date;
import java.util.Map;

public class ExcelPOIUtils {

    private static final Logger logger = Logger.getLogger(ExcelPOIUtils.class);

    /**
     *
     * @param workbook workbook sobre el cual se va a  trabajar
     * @param data datos de entrada para escribir en la hoja nueva
     * @param nombre nombre de la nueva hoja
     */
    public static void createSheet(XSSFWorkbook workbook, Map<Integer, Object[]> data, String nombre) {

        logger.info("Creando la hoja de Excel " + nombre);
        XSSFSheet sheet = workbook.createSheet(nombre);
        XSSFRow row;
        XSSFCell cell;
        for (int i = 0; i < data.size(); i++) {
            row = sheet.createRow(i);
            for (int r = 0; r < data.get(i).length; r++) {
                Object cellData = data.get(i)[r];
                cell = row.createCell(r);
                setCellStyle(workbook, cell, cellData,i == 0);
                setCellValue(cell, cellData);
                sheet.autoSizeColumn(cell.getColumnIndex());
            }
        }
        logger.info("Termino el proceso");
    }

    /**
     *
     * @param workbook workbook sobre el cual se esta trabajando
     * @param cell celda a la que se aplicara el estilo
     * @param cellData datos contenidos en la celda
     * @param isHeader identificador para validar cuando se trata de una celda de cabecera
     */
    private static void setCellStyle(XSSFWorkbook workbook, XSSFCell cell, Object cellData, boolean isHeader) {

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();

        if(isHeader) {
            font.setBold(Boolean.TRUE);
            cellStyle.setFont(font);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        } else {
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
        }
        if(cellData instanceof Date){
            XSSFDataFormat format = workbook.createDataFormat();
            cellStyle.setDataFormat(format.getFormat(UtilRTT.FORMAT_DATE));
        }
        cell.setCellStyle(cellStyle);
    }

    /**
     *
     * @param cell celda sobre la cual se esta trabajando
     * @param cellData datos que contiene la celda
     */
    private static void setCellValue(XSSFCell cell, Object cellData) {

        if (cellData != null) {
            if (cellData instanceof Date) {
                cell.setCellValue((Date) cellData);
            } else if (cellData instanceof Integer) {
                cell.setCellValue((Integer) cellData);
            } else if (cellData instanceof String) {
                cell.setCellValue((String) cellData);
            } else if (cellData instanceof Long){
                cell.setCellValue((Long) cellData);
            }
        } else {
            cell.setCellValue("");
        }
    }
}
