package gob.imss.webservice.imss.riss.implementacion;

import gob.imss.webservice.imss.riss.cliente.*;

import java.util.Date;

import org.apache.log4j.Logger;

public class ValidacionRissServiceImpl extends Thread {

	private Logger log = Logger.getLogger(ValidacionRissServiceImpl.class);

	private ValidacionRissResponse response = null;
	private boolean existeErrorServicio = false;
	private ValidacionRissRequest request;
	private boolean esRenovacion = false;

	@Override
	public void run() {

	    if(esRenovacion){
            final ValidacionRissRenovaService_Service service = new ValidacionRissRenovaService_Service();
            final ValidacionRissService port = service
                    .getValidacionRissServicePort();

            try {
                log.info("WebserviceValidarRiss. Thread. Se inicia la validación de la Renovacion RISS para el RFC: "
                        + request.getRfc() + ". " + new Date());
                response = port.validar(request);
                log.info("WebserviceValidarRiss. Thread. Se finaliza satisfactoriamente la renovacion del RISS para el RFC: "
                        + request.getRfc() + ". " + new Date());
            } catch (Exception e) {
                log.error("WebserviceValidarRiss. Thread. Se genero un error al validar la renovacion del RISS para el RFC: "
                        + request.getRfc() + ": " + e.getMessage());
                existeErrorServicio = true;
            }

            log.info("WebserviceValidarRenovaRiss. Thread. Fin del thread. " + new Date());
        }else{
            final ValidacionRissService_Service service = new ValidacionRissService_Service();
            final ValidacionRissService port = service
                    .getValidacionRissServicePort();

            try {
                log.info("WebserviceValidarRiss. Thread. Se inicia la validación del RISS para el RFC: "
                        + request.getRfc() + ". " + new Date());
                response = port.validar(request);
                log.info("WebserviceValidarRiss. Thread. Se finaliza satisfactoriamente la validación del RISS para el RFC: "
                        + request.getRfc() + ". " + new Date());
            } catch (Exception e) {
                log.error("WebserviceValidarRiss. Thread. Se genero un error al validar del RISS para el RFC: "
                        + request.getRfc() + ": " + e.getMessage());
                existeErrorServicio = true;
            }

            log.info("WebserviceValidarRiss. Thread. Fin del thread. " + new Date());
        }

	}

	public ValidacionRissResponse getResponse() {
		return response;
	}

	public void setResponse(ValidacionRissResponse response) {
		this.response = response;
	}

	public boolean isExisteErrorServicio() {
		return existeErrorServicio;
	}

	public void setExisteErrorServicio(boolean existeErrorServicio) {
		this.existeErrorServicio = existeErrorServicio;
	}

	public ValidacionRissRequest getRequest() {
		return request;
	}

	public void setRequest(ValidacionRissRequest request) {
		this.request = request;
	}

    public boolean isEsRenovacion() { return esRenovacion; }

    public void setEsRenovacion(boolean esRenovacion) { this.esRenovacion = esRenovacion; }
}
