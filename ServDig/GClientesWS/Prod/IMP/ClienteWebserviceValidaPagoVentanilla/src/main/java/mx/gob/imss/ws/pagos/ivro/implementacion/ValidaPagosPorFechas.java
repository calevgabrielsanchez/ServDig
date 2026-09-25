package mx.gob.imss.ws.pagos.ivro.implementacion;

import mx.gob.imss.ws.pagos.ivro.*;
import org.apache.log4j.Logger;

import java.util.Date;

public class ValidaPagosPorFechas extends Thread {

	private Logger log = Logger.getLogger(ValidaPagosPorFechas.class);

	private RespWSPagosIvroSimple response = null;
	private boolean existeErrorServicio = false;
	private MsgWSPagosIvroByFec request;

	@Override
	public void run() {

		try {

			final WSPAGOSIVRO_Service wspagosivro_service = new WSPAGOSIVRO_Service();
			final WSPAGOSIVRO wspagosivro_port = wspagosivro_service.getWSPAGOSIVROPort();

			log.info("WebserviceValidaPagosVentanilla. Thread. Se inicia la validación del NSS: "
					+ request.getNSS() + ". " + new Date()+ " para validar si tiene pagos legados" );
			response = wspagosivro_port.pagoPorFechas(request);

			log.info("WebserviceValidaPagosVentanilla. Thread. Se finaliza satisfactoriamente la validación si tiene pagos legados: "
					+ request.getNSS() + ". " + new Date());
		} catch (Exception e) {
			String message =  e.getMessage();
			if(e.getMessage().contains( "but could not connect over HTTP to server")){
				message= "Servicio no disponible: No se puede establecer conexion con el servicio. Intente más tarde";
			}
			log.error("WebserviceValidaPagosVentanilla. Thread. Se genero un error al validar si tiene pagos legados: "
					+ request.getNSS() + ": " +message);
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

    public MsgWSPagosIvroByFec getRequest() {
        return request;
    }

    public void setRequest(MsgWSPagosIvroByFec request) {
        this.request = request;
    }
}
