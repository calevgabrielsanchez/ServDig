package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.Util.isEmpty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.NoSuchMessageException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;

/**
 * La Class WritePDF.
 * 
 * @author Dj Leo 28/11/2013 La Class WritePDF. Esta clase contiene diversas
 *         utilerias utilizadas para la impresion y manipulacion de xml y
 *         objectos.
 */
public class WritePDF {

    /** La constante log. */
    private static final Log LOGGER = LogFactory.getLog(WritePDF.class);
    
    /**
     * Constructor privado
     */
    private WritePDF () {
        
    }

    /**
     * Genera pdf bytes.
     * 
     * @param nombreReporteJasper
     *            the nombre reporte jasper
     * @param mapaParametros
     *            the mapa parametros
     * @param pathReports
     *            the path reports
     * @return the byte[]
     * @throws Exception the IVRO exception generico
     */
    public static byte[] generaPDFBytes(String nombreReporteJasper,
            Map<String, Object> mapaParametros, String pathReports) throws IvroException {
        byte[] bytesReporte;
        JREmptyDataSource emptyDS = new JREmptyDataSource();
        try {
            // pathReportes =
            if (isEmpty(pathReports)) {
                pathReports = "/cuestionarios/";
            }
        } catch (NoSuchMessageException e) {
            LOGGER.error("Error al carga ruta de reportes " + e.getMessage());
            pathReports = "/cuestionarios/";
        }
        LOGGER.error("ruta de reportes cargada ..  " + pathReports);
        try {
            bytesReporte = getBytesReporte(
                    WritePDF.class.getResourceAsStream(pathReports + nombreReporteJasper),
                    mapaParametros, emptyDS);
        } catch (JRException e) {
            LOGGER.error(e.getMessage());
            throw new IvroException(e.getMessage());
        }
        return bytesReporte;
    }

    /**
     * Gets the bytes reporte.
     * 
     * @param pathReporteCompilado
     *            the path reporte compilado
     * @param mapaParametros
     *            the mapa parametros
     * @param ds
     *            the ds
     * @return the bytes reporte
     * @throws JRException
     *             the JR exception
     */
    public static byte[] getBytesReporte(InputStream pathReporteCompilado,
            Map<String, Object> mapaParametros, JREmptyDataSource ds) throws JRException {
        return JasperRunManager.runReportToPdf(pathReporteCompilado, mapaParametros, ds);
    }

    /**
     * Gets the docs pdfs bytes to one pdf bytes.
     * 
     * @param listaPDFBytesArreglo
     *            the lista pdf bytes arreglo
     * @return the docs pdfs bytes to one pdf bytes
     * @throws Exception the IVRO exception generico
     */
    public static byte[] getDocsPdfsBytesToOnePDFBytes(List<byte[]> listaPDFBytesArreglo)
            throws IvroException {
        PdfReader pdf = null;
        ByteArrayOutputStream arrayBO = new ByteArrayOutputStream();
        try {
            PdfCopyFields copy = new PdfCopyFields(arrayBO);
            for (int i = 0; i < listaPDFBytesArreglo.size(); i++) {
                if (listaPDFBytesArreglo.get(i) != null) {
                    pdf = new PdfReader(listaPDFBytesArreglo.get(i));
                    copy.addDocument(pdf);
                }
            }
            copy.close();
            return arrayBO.toByteArray();
        } catch (DocumentException e) {
            LOGGER.error(e.getMessage());
            throw new IvroException(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IvroException(e.getMessage());
        }
    }

}
