package mx.gob.imss.ws.pagos.ivro.implementacion;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceValidaPagosException;
import mx.gob.imss.ctirss.delta.model.legado.asegurado.RespuestaPagosVentanilla;
import mx.gob.imss.ws.pagos.ivro.MsgWSPagosIvroByFec;
import mx.gob.imss.ws.pagos.ivro.MsgWSPagosIvroByPeriodo;
import mx.gob.imss.ws.pagos.ivro.RespWSPagosIvroSimple;
import org.apache.log4j.Logger;

import java.util.Date;

public class ClienteWebserviceValidaPagosVentanilla {

    private static int serviceTimeOut = 180000;
    Logger log = Logger.getLogger(ClienteWebserviceValidaPagosVentanilla.class);

    public RespuestaPagosVentanilla validaPagosPorFechas(
            String nss, String fechaInicioUltimoPago, String fechaFinUltimoPago)
            throws ClienteWebserviceValidaPagosException {

        this.log.debug("Entrada para validar pagos -> " + nss);
        MsgWSPagosIvroByFec mensajePorFec = new MsgWSPagosIvroByFec();

        log.info("Datos de entrada del WS: ");
        log.info("NSS: " + nss);
        log.info("fechaInicioUltimoPago: " + fechaInicioUltimoPago);
        log.info("fechaFinUltimoPago: " + fechaFinUltimoPago);

        mensajePorFec.setFECHAFIN(fechaFinUltimoPago);
        mensajePorFec.setFECHAINICIO(fechaInicioUltimoPago);
        mensajePorFec.setNSS(nss);

        final RespWSPagosIvroSimple response = llamarValidaPagosPorFecha(mensajePorFec);

        RespuestaPagosVentanilla respuesta = new RespuestaPagosVentanilla();

        if (response == null) {
            log.info("La respuesta viene vacía");
            respuesta.setCodigoError("-1");
            respuesta.setMensajeError("SERVICIO NO DISPONIBLE");
        } else {
            log.info("RespuestaWS: " + response.toString());
            respuesta = convierteRespuesta(response);
        }

        log.info("+++Response: " + respuesta.toString());
        log.info("+++Response indicador codigo: " + respuesta.getCodigoError());
        log.info("+++Response indicador pagos: " + respuesta.getIndPago());
        log.info("+++Response indicador pagos completos: " + response.getINDPAGOSCOMPLETOS());

        return respuesta;
    }


    private RespWSPagosIvroSimple llamarValidaPagosPorFecha(
            final MsgWSPagosIvroByFec request) throws ClienteWebserviceValidaPagosException {
        RespWSPagosIvroSimple response = new RespWSPagosIvroSimple();

        try {
            this.log.debug("WebserviceValidaPagoFecha. Entrada -> " + request);
            this.log.debug("WebserviceValidaPagoFecha. Se ejecuta el thread del Cliente. "
                    + new Date());
            ValidaPagosPorFechas thread = new ValidaPagosPorFechas();
            thread.setRequest(request);
            thread.start();

            this.log.debug("WebserviceValidaPagoFecha. Se establece el tiempo del timeout del thread = "
                    + serviceTimeOut);
            thread.join(serviceTimeOut);
            this.log.debug("WebserviceValidaPagoFecha. Se recupera el control del proceso desde el thread. "
                    + new Date());

            // VERIFICA SI SE GENERO UN ERROR EN EL THREAD
            if (thread.isExisteErrorServicio()) {
                response.setCODIGOERROR("-1");
                throw new ClienteWebserviceValidaPagosException();
            }

            // VERIFICAMOS EN QUE CONDICIONES SE HA FINALIZADO EL THREAD
            if (thread.isAlive()) {
                this.log.debug("WebserviceValidaPagoFecha. El thread sigue esperando la respuesa");
                thread.interrupt();
                this.log.debug("WebserviceValidaPagoFecha. El thread se ha interrumpido y se generara un ClienteWebserviceValidaPagosException");
                response.setCODIGOERROR("-1");
                throw new ClienteWebserviceValidaPagosException(10002);
            } else {
                this.log.debug("WebserviceValidaPagoFecha. El thread termino satisfactoriamente las validaciones del pago dentro del timeout especificado");
                response = thread.getResponse();

            }

            thread = null;
        } catch (ClienteWebserviceValidaPagosException e) {
            response.setCODIGOERROR("-1");
            log.error("ClienteWebserviceValidaPagosException: ", e);
            throw e;
        } catch (Exception e) {
            response.setCODIGOERROR("-1");
            log.error("WebserviceValidaPagoFecha. Se genero un error al accesar el webservice: ", e);
            throw new ClienteWebserviceValidaPagosException();
        }

        this.log.debug("La respuesta de las validaciones del pago en legado -> "
                + "\n codigoError: " + response.getCODIGOERROR()
                + "\n mensaje: " + response.getMENSAJEERROR()
                + "\n nss: " + response.getNSS()
                + "\n periodo: " + response.getPERIODO()
                + "\n modalidad: " + response.getMODALIDAD()
                + "\n fechaInicioAseguramiento: " + response.getFECHAINICIOASEGURAMIENTO()
                + "\n fechaFinAseguramiento: " + response.getFECHAFINASEGURAMIENTO()
                + "\n fechaPago: " + response.getFECHAPAGO()
                + "\n indPago: " + response.getINDPAGO()
                + "\n indPagosCompletos: " + response.getINDPAGOSCOMPLETOS()
        );

        return response;
    }

    public RespuestaPagosVentanilla validaPagosPorPeriodo(String nss, String periodoAEvaluar)
            throws ClienteWebserviceValidaPagosException {

        this.log.debug("Entrada para validar pagos -> " + nss);
        MsgWSPagosIvroByPeriodo mensajeByPeriodo = new MsgWSPagosIvroByPeriodo();

        mensajeByPeriodo.setPERIODO(periodoAEvaluar);
        mensajeByPeriodo.setNSS(nss);

        final RespWSPagosIvroSimple response = llamarValidaPagosPorPeriodo(mensajeByPeriodo);
        RespuestaPagosVentanilla respuesta = new RespuestaPagosVentanilla();

        if (response == null) {
            log.info("La respuesta viene vacia");
            respuesta.setCodigoError("-1");
            respuesta.setMensajeError("SERVICIO NO DISPONIBLE");
        }

        if (response != null) {
            respuesta = convierteRespuesta(response);
        }
        log.info("+++respuesta: " + respuesta);

        return respuesta;
    }


    private RespWSPagosIvroSimple llamarValidaPagosPorPeriodo(
            final MsgWSPagosIvroByPeriodo request) throws ClienteWebserviceValidaPagosException {
        RespWSPagosIvroSimple response = new RespWSPagosIvroSimple();

        try {
            this.log.debug("WebserviceValidaPagoFecha. Entrada -> " + request);
            this.log.debug("WebserviceValidaPagoFecha. Se ejecuta el thread del Cliente. "
                    + new Date());
            ValidaPagosPorPeriodo thread = new ValidaPagosPorPeriodo();
            thread.setRequest(request);
            thread.start();

            this.log.debug("WebserviceValidaPagoFecha. Se establece el tiempo del timeout del thread = "
                    + serviceTimeOut);
            thread.join(serviceTimeOut);
            this.log.debug("WebserviceValidaPagoFecha. Se recupera el control del proceso desde el thread. "
                    + new Date());

            // VERIFICA SI SE GENERO UN ERROR EN EL THREAD
            if (thread.isExisteErrorServicio()) {
                response.setCODIGOERROR("-1");
                throw new ClienteWebserviceValidaPagosException();
            }

            // VERIFICAMOS EN QUE CONDICIONES SE HA FINALIZADO EL THREAD
            if (thread.isAlive()) {
                this.log.debug("WebserviceValidaPagoFecha. El thread sigue esperando la respuesa");
                thread.interrupt();
                this.log.debug("WebserviceValidaPagoFecha. El thread se ha interrumpido y se generara un ClienteWebserviceValidaPagosException");
                response.setCODIGOERROR("-1");
                throw new ClienteWebserviceValidaPagosException(10002);
            } else {
                this.log.debug("WebserviceValidaPagoFecha. El thread termino satisfactoriamente las validaciones del pago dentro del timeout especificado");
                response = thread.getResponse();

            }

            thread = null;
        } catch (ClienteWebserviceValidaPagosException e) {
            response.setCODIGOERROR("-1");
            log.error("ClienteWebserviceValidaPagosException", e);
            throw e;
        } catch (Exception e) {
            response.setCODIGOERROR("-1");
            log.error("WebserviceValidaPagoFecha. Se genero un error al accesar el webservice", e);
            throw new ClienteWebserviceValidaPagosException();
        }

        this.log.debug("La respuesta de las validaciones del pago en legado -> "
                + response);

        return response;
    }

    private RespuestaPagosVentanilla convierteRespuesta(RespWSPagosIvroSimple response) {
        RespuestaPagosVentanilla respuesta = new RespuestaPagosVentanilla();

        //Respuesta normal
        respuesta.setCodigoError(response.getCODIGOERROR());
        respuesta.setMensajeError(response.getMENSAJEERROR());

        respuesta.setFechaInicioAseguramiento(response.getFECHAINICIOASEGURAMIENTO());
        respuesta.setFechaFinAseguramiento(response.getFECHAFINASEGURAMIENTO());
        respuesta.setFechaPago(response.getFECHAPAGO());
        respuesta.setPeriodo(response.getPERIODO());

        respuesta.setIndPago(response.getINDPAGO());
        respuesta.setIndPagosCompletos(response.getINDPAGOSCOMPLETOS());

        respuesta.setModalidad(response.getMODALIDAD());
        respuesta.setNss(response.getNSS());

        return respuesta;
    }
}
