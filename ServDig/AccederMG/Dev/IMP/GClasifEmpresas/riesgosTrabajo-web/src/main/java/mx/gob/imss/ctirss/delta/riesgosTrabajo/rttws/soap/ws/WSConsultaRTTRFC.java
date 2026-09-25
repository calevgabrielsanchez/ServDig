package mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.ws;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;
import mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.respuesta.RespuestaDocumentosRTT;
import mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.utils.WSUtils;
import mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.vo.DocumentosByteVO;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.zip.DataFormatException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(name = "WSConsultaRTTRFC", serviceName = "WSConsultaRTTRFC")
public class WSConsultaRTTRFC {

    private static final Logger logger = LoggerFactory.getLogger(WSConsultaRTTRFC.class);

    @Resource
    private WebServiceContext context;

    @WebMethod(operationName = "getConsultaRFC")
    @WebResult(name = "return")
    public RespuestaDocumentosRTT obtenerDocumentosRTTRFC(@WebParam(name = "rfc") String rfc, @WebParam(name = "periodo") int periodo) throws RiesgosTrabajoException {
        //Locale locale = new Locale("es", "MX");
        //Calendar anActual = Calendar.getInstance(locale);
        String rfcUp = rfc.toUpperCase();

        logger.info("Comenzando a consultar los rtt relacionados al rfc");
        RespuestaDocumentosRTT respuesta = new RespuestaDocumentosRTT();
        DocumentosByteVO respuestaDocumentos = new DocumentosByteVO();
        respuestaDocumentos.setId(rfcUp);

        if (!WSUtils.verificaRfc(rfcUp)) {
            logger.error("Par\u00E1metros de b\u00FAsqueda incorrectos: Formato invalido de estructura de rfc.");
            respuesta.crearMensaje(1, "Formato inv&aacute;lido de rfc");
            return respuesta;
        }

        int validacion = WSUtils.getBeanDocumentos(context).encuentraRFC(rfcUp);
        if (validacion != 1){
            logger.error("El rfc ingresado no existe en base de datos.");
            respuesta.crearMensaje(4, "El rfc ingresado no existe en base de datos.");
            return respuesta;
        }

        //Obtenemos los datos de la persona
        PatronRiesgosTrabajo patron = WSUtils.getBeanDocumentos(context).buscarPersona(rfcUp, null, OrigenSolicitudEnum.INTERNET);

        //PatronRiesgosTrabajo patron = null;
        try {
            logger.debug("Se consulta RTT");
            List<RiesgoTrabajo> riesgosTrabajoRFC = WSUtils.getBeanDocumentos(context).buscarRtXLisRp(periodo, rfcUp, null, null, OrigenSolicitudEnum.INTERNET);

            int prubs = Integer.parseInt(riesgosTrabajoRFC.get(0).getDv());
            boolean solMes = WSUtils.getBeanDocumentos(context).validarSolicitudMes(patron, rfcUp);
            boolean validTyc = WSUtils.getBeanDocumentos(context).validarTerminosCondicionesRfc(rfcUp);

            if (prubs == 9999) {
                riesgosTrabajoRFC = null;
                if (solMes) {
                        if(!validTyc)
                            WSUtils.getBeanDocumentos(context).aceptarTerminosCondicionesRfc(patron, rfc);

                    byte[] arcvivoPDF = generarPDFRTxRFC(patron, riesgosTrabajoRFC, rfc.toUpperCase());
                    if (arcvivoPDF != null) {
                        respuestaDocumentos.getListDocumentos().add(WSUtils.documentosByte("Constancia", arcvivoPDF));
                    }
                }
                respuesta.crearMensaje(3, "Sin Riesgos de Trabajo");
            } else {
                byte[] arcvivoXLS = generarExcelRTxRFC(patron, riesgosTrabajoRFC, rfc.toUpperCase());
                if (arcvivoXLS != null) {
                    respuestaDocumentos.getListDocumentos().add(WSUtils.documentosByte("Reporte", arcvivoXLS));
                }

                //Solo se solicita la extracción de PDF en caso de que no cuente con Registros RTT
                /*if (solMes) {
                        if(!validTyc)
                            WSUtils.getBeanDocumentos(context).aceptarTerminosCondicionesRfc(patron, rfc);

                    byte[] arcvivoPDF = generarPDFRTxRFC(patron, riesgosTrabajoRFC, rfc.toUpperCase());
                    if (arcvivoPDF != null) {
                        respuestaDocumentos.getListDocumentos().add(WSUtils.documentosByte("Constancia", arcvivoPDF));
                    }
                }*/
                respuesta.crearMensaje(0, "Consulta Exitosa");
            }

            respuesta.setDocumentos(respuestaDocumentos);
            return respuesta;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error al consultar la informaci\u00F3n: No se encontraron resultados para la b\u00FAsqueda: " + e.getMessage());
            respuesta.crearMensaje(2, "Error al consultar informaci\u00F3n");
            return respuesta;
        }
    }

    @WebMethod(exclude = true)
    private byte[] generarExcelRTxRFC(PatronRiesgosTrabajo patron, List<RiesgoTrabajo> riesgosTrabajo, String rfc) throws RiesgosTrabajoException {
        byte[] reporteXLS = null;
        try {
            //Generamos el excel
            reporteXLS = WSUtils.getBeanDocumentos(context).generarDocumentoRiesgosTrabajoRfc(patron, riesgosTrabajo, rfc, TipoDescargaArchivo.XLS, OrigenSolicitudEnum.INTERNET);
            reporteXLS = WSUtils.decompress(reporteXLS);
        } catch (RiesgosTrabajoException e) {
            e.printStackTrace();
            logger.debug("Error al generar xls {}", e);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        return reporteXLS;
    }

    @WebMethod(exclude = true)
    private byte[] generarPDFRTxRFC(PatronRiesgosTrabajo patron, List<RiesgoTrabajo> riesgosTrabajo, String rfc) throws RiesgosTrabajoException {
        byte[] reportePDF = null;
        try {
            //Generamos el pdf
            reportePDF = WSUtils.getBeanDocumentos(context).generarDocumentoRiesgosTrabajoRfc(patron, riesgosTrabajo, rfc, TipoDescargaArchivo.PDF, OrigenSolicitudEnum.INTERNET);
            reportePDF = WSUtils.decompress(reportePDF);
        } catch (RiesgosTrabajoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
        return reportePDF;
    }
}
