package mx.gob.imss.service.pagoscfdi.implementacion;

import java.util.Date;

import mx.gob.imss.service.pagoscfdi.MessageWSConsPagosCFDIRegPatron;
import mx.gob.imss.service.pagoscfdi.Response;
import mx.gob.imss.service.pagoscfdi.WSConsPagosCFDIRegPatron;
import mx.gob.imss.service.pagoscfdi.WSConsPagosCFDIRegPatron_Service;

import org.apache.log4j.Logger;

public class DatosWebserviceComprobanteFiscal extends Thread {
	private Logger log = Logger.getLogger(DatosWebserviceComprobanteFiscal.class);

	private Response response;
	private boolean existeErrorServicio = false;
	private MessageWSConsPagosCFDIRegPatron request;

	@Override
	public void run() {
		WSConsPagosCFDIRegPatron_Service pagoService = new WSConsPagosCFDIRegPatron_Service();
		WSConsPagosCFDIRegPatron servicesPort = pagoService.getWSConsPagosCFDIRegPatronPort();

		try {
			log.info("WebserviceComprobanteFiscal. Thread. Se inicia la obtencion del CFDI para el Patron: "
					+ request.getRegistroPatronal() + ". " + new Date());
			response = servicesPort.obtienePagoCFDI(request);
			log.info("WebserviceComprobanteFiscal. Thread. Se finaliza satisfactoriamente la obtencion del CFDI para el Patron: "
					+ request.getRegistroPatronal() + ". " + new Date());
		} catch (Exception e) {
			log.error("WebserviceComprobanteFiscal. Thread. Se genero un error al obtener el Comprobante Fiscal: "
					+ request.getRegistroPatronal() + ": " + e.getMessage());
			existeErrorServicio = true;
		}

		log.info("WebserviceComprobanteFiscal. Thread. Fin del thread. " + new Date());
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public boolean isExisteErrorServicio() {
		return existeErrorServicio;
	}

	public void setExisteErrorServicio(boolean existeErrorServicio) {
		this.existeErrorServicio = existeErrorServicio;
	}

	public MessageWSConsPagosCFDIRegPatron getRequest() {
		return request;
	}

	public void setRequest(MessageWSConsPagosCFDIRegPatron request) {
		this.request = request;
	}
}
