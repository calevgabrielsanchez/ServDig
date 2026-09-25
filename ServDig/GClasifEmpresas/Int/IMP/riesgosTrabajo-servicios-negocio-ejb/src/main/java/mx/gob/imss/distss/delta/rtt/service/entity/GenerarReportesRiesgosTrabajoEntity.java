package mx.gob.imss.distss.delta.rtt.service.entity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.Deflater;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgosTrabajoXML;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultalRiesgoTrabajoServiceRemote;
import mx.gob.imss.distss.delta.rtt.service.util.UtilRTT;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

@Stateless(name = "generarReportesRiesgosTrabajoEntity", mappedName = "generarReportesRiesgosTrabajoEntity")
public class GenerarReportesRiesgosTrabajoEntity implements GenerarReportesRiesgosTrabajoLocal {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(GenerarReportesRiesgosTrabajoEntity.class);
    /**
     * Ubicacion de los reportes
     */
    public static final String REPORTE_URL = "reportes/RepRiesgosPorRegistroPatronal.jasper";
    
    public static final String TYC_URL = "reportes/terminosCondiciones.jasper";

    public static final String REPORTE_URL_EXCEL = "reportes/reporteExcel.xls";

    public static final String REPORTE_URL_RFC = "reportes/RepRiesgosPorRFC.jasper";

    public static final String SUB_REPORTE_RFC = "reportes/subreportRFC.jasper";

    public static final String REPORTE_URL_EXCEL_RFC = "reportes/reporteExcelRfc.xlsx";

    public static final String REPORTE_URL_EXCEL_NSS = "reportes/reporteExcelNSS.xlsx";

    public static final String SUB_REPORTE = "reportes/subreport.jasper";

    public static final String AED_URL = "reportes/acuseEscritoDesacuerdo.jasper";

    public static final String REPORTE_URL_SINRTT_RP = "reportes/sinRiesgosTrabajoRP.jasper";

    public static final String REPORTE_URL_SINRTT_RFC = "reportes/sinRiesgosTrabajoRFC.jasper";

    public static final int NSS = 0;

    public static final int DV = 1;

    public static final int CURP = 2;

    public static final int NOMBRE_ASEGURADO = 3;

    public static final int RECAIDA = 4;

    public static final int ANO_FECH_ACCIDENTE = 5;

    public static final int MES_FECH_ACCIDENTE = 6;

    public static final int DIA_FECH_ACCIDENTE = 7;

    public static final int TIPO_RIESGO = 8;

    public static final int DIAS_SUBSIDIADOS = 9;

    public static final int PORCENTAJE_INCAPACIDAD = 10;

    public static final int DEFUNCION = 11;

    public static final int ANO_FECH_ALTA = 12;

    public static final int MES_FECH_ALTA = 13;

    public static final int DIA_FECH_ALTA = 14;

    public static final int RP = 15;

    public static final int MODALIDAD = 16;

    public static final int DV_RP = 17;

    @EJB(name = "firmaDigitalBusiness", mappedName = "firmaDigitalBusiness")
    private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;

    @EJB
    private GenerararSolicitudRiesgosTrabajoLocal generararSolicitudRiesgosTrabajo;

    @EJB
    private ConsultalRiesgoTrabajoServiceRemote consultaHistRTTService;

    /**
     * Genera el PDF
     *
     * @param solicitud
     * @param patron
     * @param listaRiesgosT
     * @throws RiesgosTrabajoException
     * @return
     */
    @Override
    public byte[] generarDocumentoPDF(Solicitud solicitud, PatronRiesgosTrabajo patron, List<RiesgoTrabajo> listaRiesgosT) throws RiesgosTrabajoException {

        LOGGER.debug("Creamos PDF");

        List<JasperPrint> prints = new ArrayList<JasperPrint>();
        Map<String, Object> parametros = new HashMap<String, Object>();
        ByteArrayOutputStream byteArraySalida = new ByteArrayOutputStream();
        JasperReport report;
        FirmaElectronica firma;
        byte[] archivo = null;
        int peri = 0;
        String patronGeneral = "";

        //Se genera la cadena original
        firma = generararSolicitudRiesgosTrabajo.obtenerDatosSellado(listaRiesgosT, solicitud, patron, "0");

        try{
            patronGeneral = consultaHistRTTService.buscarPatronGral(patron.getNrp().substring(0,8));
        }catch (Exception e){
            e.printStackTrace();
        }

        if (firma != null) {
            //Firmamos la solicitud
            firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud,
                    firma);
            try {
                //Seteamos los datoa al reporte
                parametros.put("nombre", patron.getRazonSocial());
                String nrp = patron.getNrp().substring(0,8)+"-"+patron.getNrp().substring(8,10)+"-"+patronGeneral;
                parametros.put("rp", nrp) ;
                parametros.put("fecha", new Date());
                parametros.put("folio", solicitud.getNoFolioSolicitud());
                parametros.put("serie", firma.getSerialCertificado());
                parametros.put("sello", firma.getRecibo());
                parametros.put("secuencia", firma.getSecuenciaNotaria());
                parametros.put("cadenaOriginal", firma.getCadenaOriginal());
                parametros.put("fecSiniestra", UtilRTT.obtenerFechaSiniestralidad());

                if(listaRiesgosT.isEmpty() || listaRiesgosT == null){
                    parametros.put("periodo", UtilRTT.periodoSinRTT(patron.getFinPeriodo(), patron.getInicioPeriodo()));
                    parametros.put("periodotexto", UtilRTT.obetenerPeriodo(patron.getFinPeriodo(), patron.getInicioPeriodo()));

                    //Cargamos el reporte
                    report = (JasperReport) JRLoader
                            .loadObject(new ClassPathResource(REPORTE_URL_SINRTT_RP)
                                    .getInputStream());
                    peri = 1;
                }else{
                    //Cargamos subReporte
                    JasperReport subReporte = (JasperReport) JRLoader
                                .loadObject(new ClassPathResource(SUB_REPORTE)
                                         .getInputStream());

                    parametros.put("periodo", UtilRTT.obetenerPeriodo(patron.getFinPeriodo(), patron.getInicioPeriodo()));
                    parametros.put("listaRegistros", listaRiesgosT);
                    parametros.put("subReport", subReporte);

                    //Cargamos el reporte
                    report = (JasperReport) JRLoader.loadObject(new ClassPathResource(REPORTE_URL).getInputStream());
                }

                //Le enviamos los datos al reporte				
                JasperPrint print = JasperFillManager.fillReport(report,
                        parametros, new JREmptyDataSource());
                prints.add(print);
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
                        prints);
                exporter.setParameter(
                        JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
                        Boolean.TRUE);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                        byteArraySalida);
                exporter.exportReport();

                archivo = byteArraySalida.toByteArray();
                byteArraySalida.close();

                //Mandamos el reporte a la notaria
                if (peri == 1){
                    firmaDigitalBusinessRemote.guardarArchivoFirmado(
                            firma.getSecuenciaNotaria(), "SinRiesgosTrabajo_"+patron
                            .getNrp()+".pdf", archivo);
                }else{
                    firmaDigitalBusinessRemote.guardarArchivoFirmado(
                            firma.getSecuenciaNotaria(), listaRiesgosT.get(0)
                            .getRfc() + ".pdf", archivo);
                }

            } catch (JRException e1) {
                LOGGER.error("Error al crear reporte: {}", e1);
                throw new RiesgosTrabajoException(ERROR_SOLICITUD);
            } catch (IOException e1) {
                LOGGER.error("Error al crear reporte: {}", e1);
                throw new RiesgosTrabajoException(ERROR_SOLICITUD);
            }
        }

        LOGGER.debug("Se creo correctamente el PDF");
        return archivo;
    }

    /**
     * Genera el Excel
     *
     * @param listaRiesgosT
     * @throws RiesgosTrabajoException
     * @return
     */
    @Override
    public byte[] generarDocumentoXLS(List<RiesgoTrabajo> listaRiesgosT) throws RiesgosTrabajoException {

        LOGGER.debug("Creamos Excel");

        ByteArrayOutputStream byteArraySalida = new ByteArrayOutputStream();
        byte[] archivo = null;
        
        for (int cont = 0; cont < listaRiesgosT.size(); cont++) {
            listaRiesgosT.get(cont).setNumSegSocial(UtilRTT.obtenerNSS(listaRiesgosT.get(cont).getNumSegSocial()));
        }

        try {
            // Cargamos la plantilla de excel
            HSSFWorkbook libro = new HSSFWorkbook(new ClassPathResource(REPORTE_URL_EXCEL).getInputStream());
            HSSFSheet hoja = libro.getSheetAt(0);
            
            //Iniciamos en la fila 2
            int numFila = 2;
            
            //Recoremos los riesgos de trabajo
            for (RiesgoTrabajo RiesgoTrabajo : listaRiesgosT) {
                
                //Creamos una nueva fila
                HSSFRow fila = hoja.getRow(numFila);
                
                //seteamos los valosres en las celdas
                fila.getCell(NSS).setCellValue(RiesgoTrabajo.getNumSegSocial());
                fila.getCell(DV).setCellValue(RiesgoTrabajo.getDv());
                fila.getCell(CURP).setCellValue(RiesgoTrabajo.getCurp());
                fila.getCell(NOMBRE_ASEGURADO).setCellValue(RiesgoTrabajo.getNombreAsegurado());
                fila.getCell(RECAIDA).setCellValue(RiesgoTrabajo.getRecaidaRevaluacion());
                fila.getCell(ANO_FECH_ACCIDENTE).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAccidente(), 3));
                fila.getCell(MES_FECH_ACCIDENTE).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAccidente(), 2));
                fila.getCell(DIA_FECH_ACCIDENTE).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAccidente(), 1));
                fila.getCell(TIPO_RIESGO).setCellValue(RiesgoTrabajo.getTipoRiesgo());
                fila.getCell(DIAS_SUBSIDIADOS).setCellValue(RiesgoTrabajo.getDiasSubsidiados());
                fila.getCell(PORCENTAJE_INCAPACIDAD).setCellValue(RiesgoTrabajo.getPorcentajeIncapac());
                fila.getCell(DEFUNCION).setCellValue(RiesgoTrabajo.getDefuncion());
                fila.getCell(ANO_FECH_ALTA).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAlta(), 3));
                fila.getCell(MES_FECH_ALTA).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAlta(), 2));
                fila.getCell(DIA_FECH_ALTA).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAlta(), 1));
                numFila++;
            }
           
            //Generamos el areglo de bytes
            libro.write(byteArraySalida);
            archivo = byteArraySalida.toByteArray();
            byteArraySalida.close();
           
        } catch (FileNotFoundException ex) {
            LOGGER.error("Error al crear reporte: {}", ex);
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        } catch (IOException ex) {
            LOGGER.error("Error al crear reporte: {}", ex);
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        } 
        LOGGER.debug("Se creo correctamente el Excel");
        return archivo;
    }

    /**
     * Genera el Excel
     *
     * @param listaRiesgosT
     * @throws RiesgosTrabajoException
     * @return
     */
    @Override
    public byte[] generarDocumentoXLSxRFC(List<RiesgoTrabajo> listaRiesgosT, boolean xNSS) throws RiesgosTrabajoException {
        LOGGER.debug("Creamos Excel");

        String excel;
        if (xNSS) {
            excel = REPORTE_URL_EXCEL_NSS;
        } else {
            excel = REPORTE_URL_EXCEL_RFC;
        }
        LOGGER.info("Generando excel: " + excel);

        ByteArrayOutputStream byteArraySalida = new ByteArrayOutputStream();
        byte[] archivo = null;

        for (int cont = 0; cont < listaRiesgosT.size(); cont++) {
            listaRiesgosT.get(cont).setNumSegSocial(UtilRTT.obtenerNSS(listaRiesgosT.get(cont).getNumSegSocial()));
        }

        try {
            // Cargamos la plantilla de excel
            XSSFWorkbook libro = new XSSFWorkbook(new ClassPathResource(excel).getInputStream());
            XSSFSheet hoja = libro.getSheetAt(0);

            //Iniciamos en la fila 2
            int numFila = 2;

            //Recoremos los riesgos de trabajo
            for (RiesgoTrabajo RiesgoTrabajo : listaRiesgosT) {

                //Creamos una nueva fila
                XSSFRow fila = hoja.createRow(numFila);

                //seteamos los valosres en las celdas
                fila.createCell(NSS).setCellValue(RiesgoTrabajo.getNumSegSocial());
                fila.createCell(DV).setCellValue(Integer.parseInt(RiesgoTrabajo.getDv()));
                fila.createCell(CURP).setCellValue(RiesgoTrabajo.getCurp());
                fila.createCell(NOMBRE_ASEGURADO).setCellValue(RiesgoTrabajo.getNombreAsegurado());
                fila.createCell(RECAIDA).setCellValue(RiesgoTrabajo.getRecaidaRevaluacion());
                fila.createCell(ANO_FECH_ACCIDENTE).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAccidente(), 3));
                fila.createCell(MES_FECH_ACCIDENTE).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAccidente(), 2));
                fila.createCell(DIA_FECH_ACCIDENTE).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAccidente(), 1));
                fila.createCell(TIPO_RIESGO).setCellValue(RiesgoTrabajo.getTipoRiesgo());
                fila.createCell(DIAS_SUBSIDIADOS).setCellValue(RiesgoTrabajo.getDiasSubsidiados());
                fila.createCell(PORCENTAJE_INCAPACIDAD).setCellValue(RiesgoTrabajo.getPorcentajeIncapac());
                fila.createCell(DEFUNCION).setCellValue(RiesgoTrabajo.getDefuncion());
                fila.createCell(ANO_FECH_ALTA).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAlta(), 3));
                fila.createCell(MES_FECH_ALTA).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAlta(), 2));
                fila.createCell(DIA_FECH_ALTA).setCellValue(UtilRTT.obtenerFechaFormateada(RiesgoTrabajo.getFechaAlta(), 1));
                fila.createCell(RP).setCellValue(RiesgoTrabajo.getRpReg());
                fila.createCell(MODALIDAD).setCellValue(RiesgoTrabajo.getModalidad());
                fila.createCell(DV_RP).setCellValue(RiesgoTrabajo.getDvRp());
                numFila++;
            }

            //Generamos el areglo de bytes
            libro.write(byteArraySalida);
            if (xNSS) {
                archivo = byteArraySalida.toByteArray();
            } else {
                archivo = compress(byteArraySalida.toByteArray());
            }
            byteArraySalida.close();

        } catch (FileNotFoundException ex) {
            LOGGER.error("Error al crear reporte: {}", ex);
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        } catch (IOException ex) {
            LOGGER.error("Error al crear reporte: {}", ex);
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        }
        LOGGER.debug("Se creo correctamente el Excel");
        return archivo;
    }

    private byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        LOGGER.debug("Original: " + data.length / 1024 + " Kb");
        LOGGER.debug("Comprimido: " + output.length / 1024 + " Kb");
        return output;
    }

    @Override
    public byte[] generarDocumentoXML(List<RiesgoTrabajo> listaRiesgosT) throws RiesgosTrabajoException {

        LOGGER.debug("Se crea XML");
        StringWriter xml = new StringWriter();
        ByteArrayOutputStream byteArraySalida = new ByteArrayOutputStream();

        try {
            JAXBContext context = JAXBContext.newInstance(RiesgosTrabajoXML.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            RiesgosTrabajoXML riesgos = new RiesgosTrabajoXML();
            riesgos.setRiesgo(listaRiesgosT);
            marshaller.marshal(riesgos, xml);
        } catch (JAXBException e) {
            LOGGER.error("Error al generar la lista JAXB {}" + e);
            throw new RiesgosTrabajoException(ERROR_SOLICITUD);
        }

        LOGGER.debug("Se creo correctamente el XML");
        return xml.toString().getBytes(Charset.forName("UTF-8"));
    }

	@Override
	public byte[] generarTerminosCondicionesPDF(Solicitud solicitud, PatronRiesgosTrabajo patron, String rfc) throws RiesgosTrabajoException {
        List<JasperPrint> prints = new ArrayList<JasperPrint>();
        Map<String, Object> parametros = new HashMap<String, Object>();
        ByteArrayOutputStream byteArraySalida = new ByteArrayOutputStream();
        JasperReport report;
        FirmaElectronica firma;
        byte[] archivo = null;

        //Se genera la cadena original
        StringBuilder sbCadenaOriginal = new StringBuilder();
        Locale locMEX = new Locale("es", "MX");
        Date fechaDelReporte = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);

        //Generamos la Firma electronica
        sbCadenaOriginal.append("||Invocante:portalimssdigital").append("|Tipo de Tramite:").append("Consulta de riesgos de trabajo terminados")
                .append("|Fecha del Tramite:").append(sdf.format(fechaDelReporte)).append("|Folio:").append(solicitud.getNoFolioSolicitud()).append("|Nombre o Razon Social:").append(patron.getRazonSocial());
                //.append("|Numero Registro Patronal:").append(patron.getNrp()).append("||");

        if(rfc.equals("0") || rfc  == "0"){
            sbCadenaOriginal.append("|Numero Registro Patronal:").append(patron.getNrp()).append("||");
        }else {
            sbCadenaOriginal.append("|Registro Federeal de Contribuyentes:").append(rfc).append("||");
            patron.setNrp(rfc);
        }

        firma = generararSolicitudRiesgosTrabajo.obtenerFirmaElectronica(sbCadenaOriginal.toString(), solicitud, patron);

        if (firma != null) {
            //Firmamos la solicitud
            firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud,
                    firma);
            try {
                //Seteamos los datos al reporte
                parametros.put("cadena", firma.getCadenaOriginal());

                //Cargamos el reporte
                report = (JasperReport) JRLoader
                        .loadObject(new ClassPathResource(TYC_URL).getInputStream());

                //Le enviamos los datos al reporte				
                JasperPrint print = JasperFillManager.fillReport(report,
                        parametros, new JREmptyDataSource());
                prints.add(print);
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
                        prints);
                exporter.setParameter(
                        JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
                        Boolean.TRUE);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                        byteArraySalida);
                exporter.exportReport();

                archivo = byteArraySalida.toByteArray();
                byteArraySalida.close();

                //Mandamos el reporte a la notaria
                String nombreArchivo = patron.getNrp() + "TerminosCondiciones" + ".pdf";
                firmaDigitalBusinessRemote.guardarArchivoFirmado(
                        firma.getSecuenciaNotaria(), nombreArchivo, archivo);
                LOGGER.debug("Acuse generado y guardado en notaria: {}",nombreArchivo);
            } catch (JRException e1) {
                LOGGER.error("Error al crear reporte: {}", e1);
                throw new RiesgosTrabajoException(ERROR_SOLICITUD);
            } catch (IOException e1) {
                LOGGER.error("Error al crear reporte: {}", e1);
                throw new RiesgosTrabajoException(ERROR_SOLICITUD);
            }
        }
        LOGGER.debug("Se creo correctamente el PDF TYC");
        return archivo;
	}

    @Override
    public byte[] generarAcuseEscritoDesacuerdoPDF(Solicitud solicitud) throws RiesgosTrabajoException {
        List<JasperPrint> prints = new ArrayList<JasperPrint>();
        Map<String, Object> parametros = new HashMap<String, Object>();
        ByteArrayOutputStream byteArraySalida = new ByteArrayOutputStream();
        JasperReport report;
        byte[] archivo = null;
        TramiteEscritoDesacuerdo tramiteEscritoDesacuerdo = null;

        for (Tramite tramite : solicitud.getTramites()) {
            if (tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.REGISTRO_ESCRITO_DESACUERDO.getCodigo())){
                tramiteEscritoDesacuerdo = (TramiteEscritoDesacuerdo) tramite;
                break;
            }
        }

        if(tramiteEscritoDesacuerdo != null){
            try {
                //Seteamos los datos al reporte

                parametros.put("nombre",tramiteEscritoDesacuerdo.getPatron().getRazonSocial());

                parametros.put("nrp",tramiteEscritoDesacuerdo.getPatron().getNrp());

                parametros.put("folioRecepcion",tramiteEscritoDesacuerdo.getFolioRecepcion());

                parametros.put("fechSolTram",tramiteEscritoDesacuerdo.getFechaConclusion());

                parametros.put("cadenaOriginal",solicitud.getFirmaElectronica().getCadenaOriginal());

                parametros.put("selloDigital",solicitud.getFirmaElectronica().getRecibo());

                parametros.put("secuenciaNotarial",solicitud.getFirmaElectronica().getReciboNotarial());

                //Cargamos el reporte
                report = (JasperReport) JRLoader
                        .loadObject(new ClassPathResource(AED_URL).getInputStream());

                //Le enviamos los datos al reporte
                JasperPrint print = JasperFillManager.fillReport(report,
                        parametros, new JREmptyDataSource());
                prints.add(print);
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
                        prints);
                exporter.setParameter(
                        JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
                        Boolean.TRUE);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                        byteArraySalida);
                exporter.exportReport();

                archivo = byteArraySalida.toByteArray();
                byteArraySalida.close();

                LOGGER.debug("Acuse generado");
            } catch (JRException e1) {
                LOGGER.error("Error al crear reporte: {}", e1);
                throw new RiesgosTrabajoException(ERROR_SOLICITUD);
            } catch (IOException e1) {
                LOGGER.error("Error al crear reporte: {}", e1);
                throw new RiesgosTrabajoException(ERROR_SOLICITUD);
            }

            LOGGER.debug("Se creo correctamente el PDF AcuseEscritoDesacuerdo");
        }

        return archivo;
    }

    @Override
    public byte[] generarDocumentoPDFxRFC(Solicitud solicitud, PatronRiesgosTrabajo patron, List<RiesgoTrabajo> listaRiesgosT, String rfc) throws RiesgosTrabajoException {

        LOGGER.debug("Creamos PDF");

        List<JasperPrint> prints = new ArrayList<JasperPrint>();
        Map<String, Object> parametros = new HashMap<String, Object>();
        ByteArrayOutputStream byteArraySalida = new ByteArrayOutputStream();
        JasperReport report;
        FirmaElectronica firma;
        byte[] archivo = null;

        //Se genera la cadena original
        firma = generararSolicitudRiesgosTrabajo.obtenerDatosSellado(listaRiesgosT, solicitud, patron, rfc);

        if (firma != null) {
            //Firmamos la solicitud
            firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud,
                    firma);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            String anActual = sdf.format(new Date());

            try {
                //Cargamos subReporte
                JasperReport subReporte = (JasperReport) JRLoader
                        .loadObject(new ClassPathResource(SUB_REPORTE_RFC)
                                .getInputStream());
                //Seteamos los datoa al reporte
                parametros.put("nombre", patron.getRazonSocial());
                parametros.put("rfc", rfc);
                parametros.put("fecha", new Date());
                parametros.put("folio", solicitud.getNoFolioSolicitud());
                parametros.put("serie", firma.getSerialCertificado());
                parametros.put("sello", firma.getRecibo());
                parametros.put("secuencia", firma.getSecuenciaNotaria());
                parametros.put("cadenaOriginal", firma.getCadenaOriginal());
                parametros.put("anActual", firma.getCadenaOriginal());
                parametros.put("fecSiniestra", UtilRTT.obtenerFechaSiniestralidad());

                
				//Cargamos el reporte
				if(listaRiesgosT.isEmpty() || listaRiesgosT == null){
				 	parametros.put("periodo", UtilRTT.periodoSinRTT(patron.getFinPeriodo(), patron.getInicioPeriodo()));                    
				 	parametros.put("periodotexto", UtilRTT.obetenerPeriodo(patron.getFinPeriodo(), patron.getInicioPeriodo()));
                    report = (JasperReport) JRLoader.loadObject(new ClassPathResource(REPORTE_URL_SINRTT_RFC).getInputStream());
                }else{
                    parametros.put("periodo", UtilRTT.obetenerPeriodo(patron.getFinPeriodo(), patron.getInicioPeriodo()));
                    parametros.put("listaRegistros", listaRiesgosT);
                    parametros.put("subReport", subReporte);

                    report = (JasperReport) JRLoader
                             .loadObject(new ClassPathResource(REPORTE_URL_RFC)
                                     .getInputStream());
                }

                //Le enviamos los datos al reporte
                JasperPrint print = JasperFillManager.fillReport(report,
                        parametros, new JREmptyDataSource());
                prints.add(print);
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
                        prints);
                exporter.setParameter(
                        JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
                        Boolean.TRUE);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                        byteArraySalida);
                exporter.exportReport();

                archivo = byteArraySalida.toByteArray();
                byteArraySalida.close();
                archivo = compress(archivo);

                //Mandamos el reporte a la notaria
                firmaDigitalBusinessRemote.guardarArchivoFirmado(
                        firma.getSecuenciaNotaria(),
                                rfc + ".pdf", archivo);
            } catch (JRException e1) {
                LOGGER.error("Error al crear reporte: {}", e1);
                throw new RiesgosTrabajoException(ERROR_SOLICITUD);
            } catch (IOException e1) {
                LOGGER.error("Error al crear reporte: {}", e1);
                throw new RiesgosTrabajoException(ERROR_SOLICITUD);
            }
        }

        LOGGER.debug("Se creo correctamente el PDF");
        return archivo;
    }
}
