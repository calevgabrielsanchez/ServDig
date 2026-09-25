package mx.gob.imss.ws.pagos.ivro.implementacion;

import mx.gob.imss.ws.pagos.ivro.MsgWSPagosIvroByPeriodo;
import mx.gob.imss.ws.pagos.ivro.RespWSPagosIvroSimple;
import mx.gob.imss.ws.pagos.ivro.WSPAGOSIVRO;
import mx.gob.imss.ws.pagos.ivro.WSPAGOSIVRO_Service;
import org.apache.log4j.Logger;

import java.util.Date;

public class ValidaPagosPorPeriodo extends Thread {

	private Logger log = Logger.getLogger(ValidaPagosPorPeriodo.class);

	private RespWSPagosIvroSimple response = null;
	private boolean existeErrorServicio = false;
	private MsgWSPagosIvroByPeriodo request;

	@Override
	public void run() {

		try {

			final WSPAGOSIVRO_Service wspagosivro_service = new WSPAGOSIVRO_Service();
			final WSPAGOSIVRO wspagosivro_port = wspagosivro_service.getWSPAGOSIVROPort();

			log.info("WebserviceValidaPagosVentanilla. Thread. Se inicia la validación del NSS: "
					+ request.getNSS() + ". " + new Date()+ " para validar si tiene pagos legados" );
			response = wspagosivro_port.pagoPorPeriodo(request);

			log.info("WebserviceValidaPagosVentanilla. Thread. Se finaliza satisfactoriamente la validación si tiene pagos legados: "
					+ request.getNSS() + ". " + new Date());
		} catch (Exception e) {
			log.error("WebserviceValidaPagosVentanilla. Thread. Se genero un error al validar si tiene pagos legados: "
					+ request.getNSS() + ": " + e.getMessage());
			existeErrorServicio = true;
		}

		log.info("WebserviceValidaPagosVentanilla. Thread. Fin del thread. " + new Date());
	}

    public RespWSPagosIvroSimple getResponse() {
        return response;
    }

    public void setResponse(RespWSPagosIvroSimple response) {
        this.response = response;
    }

    public boolean isExisteErrorServicio() {
        return existeErrorServicio;
    }

    public void setExisteErrorServicio(boolean existeErrorServicio) {
        this.existeErrorServicio = existeErrorServicio;
    }

    public MsgWSPagosIvroByPeriodo getRequest() {
        return request;
    }

    public void setRequest(MsgWSPagosIvroByPeriodo request) {
        this.request = request;
    }
}
