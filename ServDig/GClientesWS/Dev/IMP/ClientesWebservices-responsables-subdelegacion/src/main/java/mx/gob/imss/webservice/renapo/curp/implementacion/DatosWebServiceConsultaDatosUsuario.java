package mx.gob.imss.webservice.renapo.curp.implementacion;

import java.util.Date;

import mx.gob.imss.webservice.renapo.curp.cliente.UsuarioInfoDTO;
import mx.gob.imss.webservice.renapo.curp.cliente.WSPersonalSubdelegacion;
import mx.gob.imss.webservice.renapo.curp.cliente.WSPersonalSubdelegacionService;

import org.apache.log4j.Logger;

public class DatosWebServiceConsultaDatosUsuario extends Thread {
	private Logger log = Logger
			.getLogger(DatosWebServiceResponsablesSubdelegacion.class);
	private UsuarioInfoDTO respuesta = null;
	private boolean existeErrorServicio = false;
	private String parametrosEntrada;

	@Override
	public void run() {
		final WSPersonalSubdelegacionService service = new WSPersonalSubdelegacionService();
		final WSPersonalSubdelegacion port = service
				.getWSPersonalSubdelegacionPort();

		try {
			log.info("WebserviceResponsables. Thread. El proceso inicia consulta. " + new Date());
			respuesta = port.consultaDatosUsuario(parametrosEntrada);
			log.info("WebserviceResponsables. Thread. Se finaliza la consulta satisfactoriamente. " + new Date());
		} catch (Exception e) {
			log.error("WebserviceResponsables. Thread. Se genero un error al accesar el webservice", e);
			existeErrorServicio = true;
		}

		log.error("WebserviceResponsables. Thread. Fin del thread. " + new Date());
	}

	public UsuarioInfoDTO getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(UsuarioInfoDTO respuesta) {
		this.respuesta = respuesta;
	}

	public boolean isExisteErrorServicio() {
		return existeErrorServicio;
	}

	public void setExisteErrorServicio(boolean existeErrorServicio) {
		this.existeErrorServicio = existeErrorServicio;
	}

	public String getParametrosEntrada() {
		return parametrosEntrada;
	}

	public void setParametrosEntrada(String parametrosEntrada) {
		this.parametrosEntrada = parametrosEntrada;
	}

}
