package mx.gob.imss.webservice.renapo.curp.implementacion;

import java.util.Date;

import mx.gob.imss.webservice.renapo.curp.cliente.RecuperaResponsablesDelegacion;
import mx.gob.imss.webservice.renapo.curp.cliente.ResponsablesDelegacionDTO;
import mx.gob.imss.webservice.renapo.curp.cliente.WSPersonalSubdelegacion;
import mx.gob.imss.webservice.renapo.curp.cliente.WSPersonalSubdelegacionService;

import org.apache.log4j.Logger;

public class DatosWebServiceResponsablesSubdelegacion extends Thread {
	private Logger log = Logger.getLogger(DatosWebServiceResponsablesSubdelegacion.class);
	private ResponsablesDelegacionDTO respuesta = null;
	private boolean existeErrorServicio = false;
	private RecuperaResponsablesDelegacion parametrosEntrada;

	@Override
	public void run() {
		final WSPersonalSubdelegacionService service = new WSPersonalSubdelegacionService();
		final WSPersonalSubdelegacion port = service.getWSPersonalSubdelegacionPort();

		try {
			log.info("WebserviceResponsables. Thread. El proceso inicia consulta. " + new Date());
			respuesta = port.recuperaResponsablesDelegacion(parametrosEntrada.getCveDelegacion(),parametrosEntrada.getCveSubdelegacion(),
					parametrosEntrada.getRoles(), parametrosEntrada.getModulo());
			log.info("WebserviceResponsables. Thread. Se finaliza la consulta satisfactoriamente. " + new Date());
		} catch (Exception e) {
			log.error("WebserviceResponsables. Thread. Se genero un error al accesar el webservice");
			existeErrorServicio = true;
		}

		log.error("WebserviceResponsables. Thread. Fin del thread. " + new Date());
	}

	public ResponsablesDelegacionDTO getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(ResponsablesDelegacionDTO respuesta) {
		this.respuesta = respuesta;
	}

	public boolean isExisteErrorServicio() {
		return existeErrorServicio;
	}

	public void setExisteErrorServicio(boolean existeErrorServicio) {
		this.existeErrorServicio = existeErrorServicio;
	}

	public RecuperaResponsablesDelegacion getParametrosEntrada() {
		return parametrosEntrada;
	}

	public void setParametrosEntrada(
			RecuperaResponsablesDelegacion parametrosEntrada) {
		this.parametrosEntrada = parametrosEntrada;
	}

	
}
