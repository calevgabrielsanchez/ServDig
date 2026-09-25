package mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.ws;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;
import mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.respuesta.RespuestaDocumentosRTT;
import mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.utils.WSUtils;
import mx.gob.imss.ctirss.delta.riesgosTrabajo.rttws.soap.vo.DocumentosByteVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.*;

@WebService(name = "WSConsultaRTTNRP", serviceName = "WSConsultaRTTNRP")
public class WSConsultaRTTNRP {
    private static final Logger logger = LoggerFactory.getLogger(WSConsultaRTTNRP.class);

    @Resource
    private WebServiceContext context;

    @WebMethod(operationName = "getConsultaNRP")
    @WebResult(name = "return")
    public RespuestaDocumentosRTT obtenerDocumentosRTT(@WebParam(name = "nrp") String nrp, @WebParam(name = "periodo") int periodo) throws RiesgosTrabajoException {
        logger.info("Comenzando a consultar los rtt relacionados al nrp");
        RespuestaDocumentosRTT respuesta = new RespuestaDocumentosRTT();
        DocumentosByteVO respuestaDocumentos = new DocumentosByteVO();
        respuestaDocumentos.setId(nrp);

        if (!WSUtils.verificaNrp(nrp)) {
            logger.error("Par&aacute;metros de b&uacute;squeda incorrectos: Formato invalido de estructura de nrp.");
            respuesta.crearMensaje(1, "Formato inv&aacute;lido de nrp");
            return respuesta;
        }

        int validaNrp = WSUtils.getBeanDocumentos(context).encuentraNRP(nrp);
        if (validaNrp != 1){
            logger.error("El nrp ingresado no existe en base de datos.");
            respuesta.crearMensaje(4, "El nrp ingresado no existe en base de datos.");
            return respuesta;
        }

        logger.debug("Entrando a consultar patron");
        PatronRiesgosTrabajo patron = null;
        List<byte[]> documentosResponse = null;
        try {

            //Obtenemos los datos del patron
            if (!nrp.equals("sinNRP")) {
                patron = WSUtils.getBeanDocumentos(context).buscarPatron(nrp, periodo, null, null, OrigenSolicitudEnum.INTERNET);
            }

            logger.debug("El periodo de busqueda es de " + patron.getInicioPeriodo() + " a " + patron.getFinPeriodo());
            //obtenemos los riesgos de trabajo
            List<RiesgoTrabajo> riesgosTrabajo = WSUtils.getBeanDocumentos(context).obtenerRiesgosTrabajoPatronalesPeriodo(patron, OrigenSolicitudEnum.INTERNET);

            /*if (riesgosTrabajo.isEmpty()) {
                throw new Exception("Lista vac&iacute;a");
            }*/

            boolean solMes = WSUtils.getBeanDocumentos(context).validarSolicitudMes(patron, "0");
            boolean validTyc = WSUtils.getBeanDocumentos(context).validarTerminosCondiciones(patron);

            if (!riesgosTrabajo.isEmpty()) {
                byte[] arcvivoXLS = generarExcelRTxNRP(patron);
                if (arcvivoXLS != null) {
                    respuestaDocumentos.getListDocumentos().add(WSUtils.documentosByte("Reporte", arcvivoXLS));
                }
				
				if (solMes) {
				    if(!validTyc)
				        WSUtils.getBeanDocumentos(context).aceptarTerminosCondiciones(patron);
                byte[] arcvivoPDF = generarPDFRTxNRP(patron);

                if (arcvivoPDF != null) {
                    respuestaDocumentos.getListDocumentos().add(WSUtils.documentosByte("Constancia", arcvivoPDF));
                }
            }
            }

            if (solMes) {
                if(!validTyc)
                    WSUtils.getBeanDocumentos(context).aceptarTerminosCondiciones(patron);
                byte[] arcvivoPDF = generarPDFRTxNRP(patron);

                if (arcvivoPDF != null) {
                    respuestaDocumentos.getListDocumentos().add(WSUtils.documentosByte("Constancia", arcvivoPDF));
                }
            }

            if (!riesgosTrabajo.isEmpty()) {
                respuesta.crearMensaje(0, "Consulta Exitosa");
            } else {
                respuesta.crearMensaje(3, "Sin Riesgos de Trabajo");
            }
            respuesta.setDocumentos(respuestaDocumentos);
            return respuesta;
        } catch (Exception e) {
            logger.debug("Error al consultar la informaci&oacute;n: No se encontraron resultados para la b&uacute;squeda: " + e.getMessage());
            respuesta.crearMensaje(2, "Error al consultar informaci&oacute;n");
            return respuesta;
        }
    }

    @WebMethod(exclude = true)
    private byte[] generarExcelRTxNRP(PatronRiesgosTrabajo patron) throws RiesgosTrabajoException {
        byte[] reporteXLS = null;
        try {
            //Generamos el excel
            reporteXLS = WSUtils.getBeanDocumentos(context).generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.XLS, OrigenSolicitudEnum.INTERNET);
        } catch (RiesgosTrabajoException e) {
            e.printStackTrace();
            logger.debug("Error al generar xls {}", e);
        }
        return reporteXLS;
    }

    @WebMethod(exclude = true)
    private byte[] generarPDFRTxNRP(PatronRiesgosTrabajo patron) throws RiesgosTrabajoException {
        byte[] reportePDF = null;
        try {
            //Generamos el pdf
            reportePDF = WSUtils.getBeanDocumentos(context).generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.PDF, OrigenSolicitudEnum.INTERNET);
        } catch (RiesgosTrabajoException e) {
            e.printStackTrace();
        }
        return reportePDF;
    }
}
