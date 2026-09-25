/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.business;

import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.DS_REPORTE;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.LOGO_IMSS;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.REPORTE_FILE;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.SUBREPORTE2_FILE;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.SUBREPORTE_FILE;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.MAIN_REPORT;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.MAIN_REPORT_CUEST;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.PATH_REPORTS;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.RUTA_LOGO_IMSS;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.SUBREPORT2_CUEST;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.keys.IVROPersonalKey.SUBREPORT_CUEST;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.WritePDF.getDocsPdfsBytesToOnePDFBytes;
import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.WritePDF.generaPDFBytes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ComprobanteSeguroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.CuestionarioSeguroRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model.CuestionarioVO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.PropertiesOpciones;
import mx.gob.imss.digital.modelo.cuestionario.Opcion;
import mx.gob.imss.digital.modelo.cuestionario.Respuesta;
import mx.gob.imss.digital.modelo.cuestionario.RespuestasCuestionario;
import mx.gob.imss.digital.modelo.seguros.CuestionarioSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.CuestionariosSeguroReporte;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "cuestionarioSeguroBussines", mappedName = "cuestionarioSeguroBussines")
public class CuestionarioSeguroBussines implements CuestionarioSeguroRemote {
    /** Logger de la clase. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CuestionarioSeguroBussines.class);

    /**
     * Comprobante del seguro
     */
    @EJB
    private ComprobanteSeguroLocal comprobanteSeguroLocal;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * CuestionarioSeguroRemote
     * #generaDatosCuetionario(mx.gob.imss.digital.modelo.seguros.SegurosIvro)
     */
    @Override
    public CuestionariosSeguroReporte generaDatosCuetionario(SegurosIvro seguros) {
        return comprobanteSeguroLocal.generaDatosCuetionario(seguros);
    }

    @Override
    public DocumentoSeguro generaCuestionariosSeguro(SegurosIvro seguros) {
        DocumentoSeguro cuestionarios = new DocumentoSeguro();
        cuestionarios.setNombreArchivo("cuestionario-seguro.pdf");
        if (seguros.getSeguroIvro() != null && seguros.getSeguroIvro().length > 0) {
            cuestionarios.setArchivo(generaCuestionarios(seguros));
        }            
        return cuestionarios;
    }

    /**
     * @author Dj Leo 3/12/2014 Genera cuestionario pdf para ivro domestico.
     * 
     * @param seguros
     *            the seguros
     * @return the byte[]
     */
    public byte[] generaCuestionarios(SegurosIvro seguros) {
        PropertiesOpciones properties = new PropertiesOpciones();
        Map<String, String> opciones = properties.getOpciones();
        byte[] bytesCuest = null;
        String rutaReports = null;
        String mainReport = null;
        String mainReportCuest = null;
        String subReportCuest = null;
        String subReport2Cuest = null;
        String rutaLogoImss = null;
        // Obteniendo configuracion para reportes
        rutaReports = opciones.get(PATH_REPORTS);
        mainReport = opciones.get(MAIN_REPORT);
        mainReportCuest = opciones.get(MAIN_REPORT_CUEST);
        subReportCuest = opciones.get(SUBREPORT_CUEST);
        subReport2Cuest = opciones.get(SUBREPORT2_CUEST);
        rutaLogoImss = opciones.get(RUTA_LOGO_IMSS);
        bytesCuest = generarPDFCuest(seguros, rutaReports, mainReportCuest, subReportCuest,
                subReport2Cuest, mainReport, rutaLogoImss);
        return bytesCuest;
    }

    /**
     * Generar pdf cuest for ivro.
     * 
     * @param seguros
     *            the seguros
     * @param params
     *            the params
     * @return the byte[]
     */
    private byte[] generarPDFCuest(SegurosIvro seguros, String... params) {
        byte[] bytesPdf = null;
        byte[] bytesAllPdf = null;
        // Invocar el servicio para obtener la informacion del cuestionario
        CuestionariosSeguroReporte cuestsReport = serviceGeneraCuestionario(seguros);
        CuestionarioSeguroReporte[] cuestsRespuestas = cuestsReport.getCuestionarios();
        List<byte[]> listaBytesPdf = new ArrayList<byte[]>();
        for (CuestionarioSeguroReporte cuestCurrent : cuestsRespuestas) {
            // Llenar el cuestionarioVO con los datos recibidos del servicio
            CuestionarioVO cuestFully = fillCuestionario(cuestCurrent);

            // Enviar el VO lleno con los datos del cuestionario a la utileria
            // que generara
            // a partir de la informacion en ese VO el pdf del cuestionario con
            // los datos llenados
            bytesPdf = generaCuestionarioPDF(cuestFully, params); // pdf bytes
            // del cuestionario actual
            listaBytesPdf.add(bytesPdf);
        }
        // Concatenar todos los PDFS

        if (listaBytesPdf.size() > 0) {
            try {
                bytesAllPdf = getDocsPdfsBytesToOnePDFBytes(listaBytesPdf);
            } catch (Exception e) {
                LOGGER.error(" Error al crear pdf de todos los pdfs cuestionario generados por cada seguro: "
                        + e.getMessage());
            }
        }

        return bytesAllPdf;
    }

    /**
     * Genera cuestionario.
     * 
     * @param seguros
     *            the seguros
     * @return the cuestionarios seguro reporte
     */
    public CuestionariosSeguroReporte serviceGeneraCuestionario(SegurosIvro seguros) {
        CuestionariosSeguroReporte cuestionariosReport = new CuestionariosSeguroReporte();
        try {
            cuestionariosReport = generaDatosCuetionario(seguros);
        } catch (Exception e) {
            LOGGER.error("Error no controlado ", e);
        }
        return cuestionariosReport;
    }

    /**
     * Fill cuestionario.
     * 
     * @author Dj Leo 3/12/2014
     * @param cuestIVROReceived
     *            the cuest ivro received
     * @return the cuestionario vo
     */
    private CuestionarioVO fillCuestionario(CuestionarioSeguroReporte cuestIVROReceived) {
        LOGGER.info("cuestionario  filling.. :");
        // Obtenemos las respuestas principales del cuestionario datos del
        // titular
        RespuestasCuestionario respCuest = cuestIVROReceived.getRespuestasCuestionario();
        // Obtenemos las respuestas a el cuestionario seccion uno y seccion dos
        Respuesta[] respuestas = respCuest.getRespuestas();
        LOGGER.info("cuestionario  :" + respCuest.getSumatoriaRespuestas());
        for (Respuesta resp : respuestas) {
            LOGGER.info(" respuesta valores idRep: " + resp.getNumPregunta() + " , seccion:"
                    + resp.getNumSeccion() + ", respuesta:" + resp.getValores());
            for (Opcion opcionP : resp.getValores()) {
                LOGGER.info(" respuesta: " + opcionP.getDescripcion() + " , value:"
                        + opcionP.getValor() + ", clave:" + opcionP.getClave());
            }
        }
        CuestionarioVO ctVO = new CuestionarioVO();
        // Llenado el vo Cuestionarios para plantarle los valores al PDF
        ctVO = ctVO.fill(cuestIVROReceived);
        return ctVO;
    }

    /**
     * Genera cuestionario pdf.
     * 
     * @author Dj Leo 3/12/2014
     * @param cuestVOFully
     *            the cuest vo fully
     * @param params
     *            the params
     * @return the byte[]
     */
    public byte[] generaCuestionarioPDF(CuestionarioVO cuestVOFully, String... params) {
        CuestionarioVO cuestVO;
        byte[] bytesPdf;

        cuestVO = cuestVOFully;
        String pathReports = params[0];
        String nombreReportMainJasper = params[1];
        String nombreSubReportJasper = params[2];
        String nombreSubReport2Jasper = params[3];
        String nombreReportAll = params[4]; // Reporte principal que jala los
                                            // tres
        String rutaLogo = params[5]; // Reporte principal que jala los tres
        Map<String, Object> mapParams = new HashMap<String, Object>();
        LOGGER.info("Generando pdf Cuestionario");
        try {
            mapParams.put(DS_REPORTE, cuestVO);
            mapParams.put(
                    REPORTE_FILE,
                    CuestionarioSeguroBussines.class.getResourceAsStream(pathReports
                            + nombreReportMainJasper));
            mapParams.put(
                    SUBREPORTE_FILE,
                    CuestionarioSeguroBussines.class.getResourceAsStream(pathReports
                            + nombreSubReportJasper));
            mapParams.put(
                    SUBREPORTE2_FILE,
                    CuestionarioSeguroBussines.class.getResourceAsStream(pathReports
                            + nombreSubReport2Jasper));
            mapParams
                    .put(LOGO_IMSS, CuestionarioSeguroBussines.class.getResourceAsStream(rutaLogo));
            bytesPdf = generaPDFBytes(nombreReportAll, mapParams, pathReports);
            LOGGER.info("Generacion correcta PDF CUESTIONARIO");
        } catch (Exception e) {
            LOGGER.error("El pdf del cuestionario no ha podido ser generado exitosamente, causa:"
                    + e.getMessage());
            bytesPdf = null;
        }
        return bytesPdf;
    }

}
