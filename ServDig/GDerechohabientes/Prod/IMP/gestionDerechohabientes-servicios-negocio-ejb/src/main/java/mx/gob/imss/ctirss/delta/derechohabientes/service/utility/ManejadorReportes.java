package mx.gob.imss.ctirss.delta.derechohabientes.service.utility;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.InfoDepuracion;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaDepuracionReportes;
import mx.gob.imss.ctirss.delta.model.util.ReporteEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import mx.gob.imss.ctirss.delta.derechohabientes.util.ConstantesReportes;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaCommon;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaDescargaReporte;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaReporte;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Stateless(name = "manejadorReportes", mappedName = "manejadorReportes")
public class ManejadorReportes implements ManejadorReportesLocal {

    /**
     * Log instance.
     */
    private static final Log log = LogFactory.getLog(ManejadorReportes.class);

    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager em;

    //@Resource(name = "pathIMG")
    private String pathIMG = "reportes/";
    //@Resource(name = "pathReportes")
    private String pathReportes = "reportes/";

    @Override
    public ByteArrayOutputStream ejecutaHolaMundo(String cveSolicitud) {

        Map parametros = new HashMap();
        parametros.put("hola", cveSolicitud);

        return ejecutaReporte(parametros, "hola.jrxml");
    }

    // Ejecuta el reporte Jasper y regresa un ByteArrayOutputStream
    @SuppressWarnings("deprecation")
    private ByteArrayOutputStream ejecutaReporte(Map parametros, String reporte) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {

            Session session = (Session) em.getDelegate();
            Connection conn = session.connection();

            JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("reportes/" + reporte).getInputStream());
            JasperPrint print = JasperFillManager.fillReport(report, parametros, conn);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);

            exporter.exportReport();

            return byteArrayOutputStream;

        } catch (Exception e) {// Agregar las excepciones personalizadas
            e.getMessage();
        }

        return null;
    }

    @Override
    public ByteArrayOutputStream ejecutaReporte(Map parametros, List<? extends Serializable> lista, String reporte) {
        // TODO Auto-generated method stub

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            JRBeanCollectionDataSource dataSource;
            dataSource = new JRBeanCollectionDataSource(lista);

            JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("reportes/" + reporte).getInputStream());

            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);

            exporter.exportReport();

            return byteArrayOutputStream;
        } catch (Exception e) {// Agregar las excepciones personalizadas
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ByteArrayOutputStream ejecutaReportePlantillas(Map parametros, List<? extends Serializable> lista, List<String> reportes) {
        // TODO Auto-generated method stub

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        List<JasperPrint> arreglo = new ArrayList<JasperPrint>();

        try {
            //System.out.println("************* contexto "+ClassLoader.getSystemResource("/reportes/img/imss.jpg"));
            //URL l=ClassLoader.getSystemResource("/reportes/img/imss.jpg");
            JRBeanCollectionDataSource dataSource;
            JasperPrint print = new JasperPrint();

            if (parametros.get("LOGO") == null) {
                ClassPathResource c = new ClassPathResource("reportes/img/imss.jpg");
                parametros.put("LOGO", c.getPath());
            }
            for (String reporte : reportes) {
                JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/" + reporte).getInputStream());
                dataSource = new JRBeanCollectionDataSource(lista);
                print = JasperFillManager.fillReport(report, parametros, dataSource);
                arreglo.add(print);
            }

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, arreglo);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
            exporter.exportReport(); //Exportar al archivo PD

            return byteArrayOutputStream;
        } catch (Exception e) {// Agregar las excepciones personalizadas
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JasperPrint imprimeReporte(Map parametros, List<? extends Serializable> lista, String reporte) {

        JasperPrint print = null;
        JRBeanCollectionDataSource dataSource;
        dataSource = new JRBeanCollectionDataSource(lista);

        try {
            JasperReport report = JasperCompileManager.compileReport(new ClassPathResource("reportes/" + reporte).getInputStream());

            print = JasperFillManager.fillReport(report, parametros, dataSource);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return print;
    }

    @Override
    public ByteArrayOutputStream ejecutaReporteCompilado(Map parametros, List<? extends Serializable> lista, String reporte) {
        // TODO Auto-generated method stub

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            JRBeanCollectionDataSource dataSource;
            dataSource = new JRBeanCollectionDataSource(lista);

            JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/" + reporte).getInputStream());
            @SuppressWarnings("unchecked")
            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);

            exporter.exportReport();

            return byteArrayOutputStream;
        } catch (Exception e) {// Agregar las excepciones personalizadas
            System.out.println("Error al genear el reporte");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public JasperPrint getReporteCompilado(Map parametros, List<? extends Serializable> lista, String reporte) {
        // TODO Auto-generated method stub

        try {
            JRBeanCollectionDataSource dataSource;
            dataSource = new JRBeanCollectionDataSource(lista);

            JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/" + reporte).getInputStream());
            @SuppressWarnings("unchecked")
            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

            return print;
        } catch (Exception e) {// Agregar las excepciones personalizadas
            System.out.println("Error al genear el reporte");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ByteArrayOutputStream mergeReporteCompilado(List<JasperPrint> jasperPrints) {
        // TODO Auto-generated method stub

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrints);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);

            exporter.exportReport();

            return byteArrayOutputStream;
        } catch (Exception e) {// Agregar las excepciones personalizadas
            System.out.println("Error al genear el reporte");
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ByteArrayOutputStream ejecutaReporteSubreporte(Map parametros, List<? extends Serializable> lista, String reporte,
            Map<String, String> plantillas) {

        JasperReport subreport = null;
        Iterator it = plantillas.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry<String, Object> e = (Map.Entry<String, Object>) it.next();
            //Key va a ser la plantilla, value va a ser el parametro
            try {
                subreport = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/" + e.getKey()).getInputStream());
                parametros.put(e.getValue().toString(), subreport);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            JRBeanCollectionDataSource dataSource;
            dataSource = new JRBeanCollectionDataSource(lista);

            if (parametros.get("LOGO") == null) {
                ClassPathResource c = new ClassPathResource("reportes/img/imss.jpg");
                parametros.put("LOGO", c.getPath());
            }
            JasperReport report = (JasperReport) JRLoader.loadObject(new ClassPathResource("reportes/" + reporte).getInputStream());
            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataSource);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);

            exporter.exportReport();

            return byteArrayOutputStream;
        } catch (Exception e) {// Agregar las excepciones personalizadas
            e.printStackTrace();
        }

        return null;

    }

    @SuppressWarnings("unchecked")
    @Override
    public ByteArrayOutputStream concatPDF(List<ByteArrayOutputStream> byteArrayOutputStream, boolean paginate) {

        Document document = new Document();
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            List<ByteArrayOutputStream> pdfs = byteArrayOutputStream;
            List<PdfReader> readers = new ArrayList<PdfReader>();
            int totalPages = 0;
            Iterator<ByteArrayOutputStream> iteratorPDFs = pdfs.iterator();

            while (iteratorPDFs.hasNext()) {
                ByteArrayOutputStream pdf = iteratorPDFs.next();
                PdfReader pdfReader = new PdfReader(pdf.toByteArray());
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }

            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            document.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;
            Iterator<PdfReader> iteratorPDFReader = readers.iterator();

            while (iteratorPDFReader.hasNext()) {
                PdfReader pdfReader = iteratorPDFReader.next();

                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {

                    Rectangle rectangle = pdfReader.getPageSizeWithRotation(1);
                    document.setPageSize(rectangle);
                    document.newPage();

                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                    switch (rectangle.getRotation()) {
                        case 0:
                            cb.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                            break;
                        case 90:
                            cb.addTemplate(page, 0, -1f, 1f, 0, 0, pdfReader.getPageSizeWithRotation(1).getHeight());
                            break;
                        case 180:
                            cb.addTemplate(page, -1f, 0, 0, -1f, 0, 0);
                            break;
                        case 270:
                            cb.addTemplate(page, 0, 1.0F, -1.0F, 0, pdfReader.getPageSizeWithRotation(1).getWidth(), 0);
                            break;
                        default:
                            break;
                    }
                    if (paginate) {
                        cb.beginText();
                        cb.getPdfDocument().getPageSize();
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen())
                document.close();
        }
        return (ByteArrayOutputStream) outputStream;
    }

    public SpRespuestaReporte generaReporte(String curp, Long cveIdDelegacionUser, Integer userNacional, Long cveIdDelegacion,
            Long cveIdSubdelegacion, Date fechaInicial, Date fechaFinal, ReporteEnum reporte) {

        SpRespuestaReporte result;

        Connection connection = ((Session) em.getDelegate()).connection();
        CallableStatement stmt;

        try {

            stmt = connection.prepareCall(reporte.getSpGenera());

            // Input parameters
            stmt.setString(ConstantesReportes.CVE_ID_USUARIO, curp);
            stmt.setLong(ConstantesReportes.CVE_ID_DELEGACION_USER, cveIdDelegacionUser);
            stmt.setInt(ConstantesReportes.USER_NACIONAL, userNacional);
            stmt.setLong(ConstantesReportes.CVE_ID_DELEGACION_SQL, cveIdDelegacion);
            stmt.setLong(ConstantesReportes.CVE_ID_SUBDELEGACION_SQL, cveIdSubdelegacion);
            stmt.setDate(ConstantesReportes.FECHA_INICIAL, new java.sql.Date(fechaInicial.getTime()));
            stmt.setDate(ConstantesReportes.FECHA_FINAL, new java.sql.Date(fechaFinal.getTime()));

            stmt.registerOutParameter(ConstantesReportes.FOLIO, Types.VARCHAR);
            stmt.registerOutParameter(ConstantesReportes.COD_PROCESO, Types.INTEGER);
            stmt.registerOutParameter(ConstantesReportes.DES_PROCESO, Types.VARCHAR);

            // Execute stored procedure
            stmt.execute();

            // Get out parameters
            String folio = stmt.getString(ConstantesReportes.FOLIO);
            Integer codProceso = stmt.getInt(ConstantesReportes.COD_PROCESO);
            String desProceso = stmt.getString(ConstantesReportes.DES_PROCESO);

            result = new SpRespuestaReporte(null, folio, codProceso, desProceso);

            System.out.println("folio " + result.getFolio());
            System.out.println("codProceso " + result.getCodProceso());
            System.out.println("desProceso " + result.getDesProceso());

        } catch (SQLException sqe) {
            throw new RuntimeException(sqe.getMessage(), sqe);
        }

        return result;
    }

    @Override
    public SpRespuestaReporte obtieneEstatusReporte(String folio, ReporteEnum reporte) {

        SpRespuestaReporte result;

        Connection connection = ((Session) em.getDelegate()).connection();
        CallableStatement stmt;

        try {

            stmt = connection.prepareCall(reporte.getSpEstatus());

            // Input parameters
            stmt.setString(ConstantesReportes.FOLIO, folio);

            stmt.registerOutParameter(ConstantesReportes.ESTATUS, Types.VARCHAR);
            stmt.registerOutParameter(ConstantesReportes.COD_PROCESO, Types.INTEGER);
            stmt.registerOutParameter(ConstantesReportes.DES_PROCESO, Types.VARCHAR);

            // Execute stored procedure
            stmt.execute();

            // Get out parameters
            String estatus = stmt.getString(ConstantesReportes.ESTATUS);
            Integer codProceso = stmt.getInt(ConstantesReportes.COD_PROCESO);
            String desProceso = stmt.getString(ConstantesReportes.DES_PROCESO);

            result = new SpRespuestaReporte(estatus, null, codProceso, desProceso);

        } catch (SQLException sqe) {
            throw new RuntimeException(sqe.getMessage(), sqe);
        }
        return result;
    }

    @Transactional
    @Override
    public SpRespuestaDescargaReporte descargaReporte(String folio, ReporteEnum reporte) throws SQLException {

        SpRespuestaDescargaReporte result;

        Connection connection = ((Session) em.getDelegate()).connection();
        CallableStatement stmt;
        Blob blob = null;
        log.debug("*********** FOLIO OBTENIDO PARA EL QUERY " + folio);

        try {

            stmt = connection.prepareCall(reporte.getSpDescarga());

            // Input parameters
            stmt.setString(1, folio);

            stmt.registerOutParameter(2, Types.VARCHAR);
            stmt.registerOutParameter(3, Types.BLOB);
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.registerOutParameter(5, Types.VARCHAR);

            // Execute stored procedure
            stmt.execute();

            // Get out parameters
            String nomArchivo = stmt.getString(2);
            blob = stmt.getBlob(3);
            Integer codProceso = stmt.getInt(4);
            String desProceso = stmt.getString(5);

            log.debug("codProceso " + codProceso);
            log.debug("desProceso " + desProceso);

				/*out.close();
				is.close();*/
            result = new SpRespuestaDescargaReporte(nomArchivo, null, codProceso, desProceso, blob.getBytes(1, (int) blob.length()));

        } catch (Exception ioe) {
            throw new RuntimeException(ioe.getMessage(), ioe);
        } finally {
            if (blob != null) {
                blob.free();
            }
        }

        return result;
    }

    @Override
    public SpRespuestaCommon eliminaReporte(String folio, String curpUsuario, ReporteEnum reporte) {

        SpRespuestaCommon result;

        Connection connection = ((Session) em.getDelegate()).connection();
        CallableStatement stmt;

        try {

            stmt = connection.prepareCall(reporte.getSpElimina());

            // Input parameters
            stmt.setString(ConstantesReportes.FOLIO, folio);
            stmt.setString(ConstantesReportes.CVE_ID_USUARIO, curpUsuario);

            stmt.registerOutParameter(ConstantesReportes.COD_PROCESO, Types.INTEGER);
            stmt.registerOutParameter(ConstantesReportes.DES_PROCESO, Types.VARCHAR);

            // Execute stored procedure
            stmt.execute();

            // Get out parameters
            Integer codProceso = stmt.getInt(ConstantesReportes.COD_PROCESO);
            String desProceso = stmt.getString(ConstantesReportes.DES_PROCESO);

            result = new SpRespuestaCommon(codProceso, desProceso);

        } catch (SQLException sqe) {
            throw new RuntimeException(sqe.getMessage(), sqe);
        }
        return result;
    }

    @Override
    public SpRespuestaDepuracionReportes obtieneEstatusDepuracionReportes(Date parFecha) {

        List<InfoDepuracion> info = new ArrayList<InfoDepuracion>();
        Connection connection = ((Session) em.getDelegate()).connection();
        CallableStatement stmt;

        try {

            stmt = connection.prepareCall(ConstantesReportes.ESTATUS_ELIMINACION_ARCHIVOS);

            // Input parameters
            stmt.setDate(ConstantesReportes.PAR_FECHA, new java.sql.Date(parFecha.getTime()));
            stmt.registerOutParameter(ConstantesReportes.CURSOR_DEPURA, -10); /* Corresponde al tipo de CURSOR */
            stmt.registerOutParameter(ConstantesReportes.PAR_FEC_CONSULTA, Types.INTEGER);

            // Execute stored procedure
            stmt.execute();

            ResultSet cursor = (ResultSet) stmt.getObject(ConstantesReportes.CURSOR_DEPURA);
            Integer codProceso = stmt.getInt(ConstantesReportes.PAR_FEC_CONSULTA);
            while (cursor.next()) {
                log.info(cursor.getDate(1) + "  => " + cursor.getString(2) + "  => " + cursor.getString(3) + "  => " + cursor.getInt(4));
                info.add(new InfoDepuracion(cursor.getDate(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
            }
            return new SpRespuestaDepuracionReportes(info, codProceso);
        } catch (SQLException sqe) {
            sqe.printStackTrace();
            throw new RuntimeException(sqe.getMessage(), sqe);
        }
    }
}
