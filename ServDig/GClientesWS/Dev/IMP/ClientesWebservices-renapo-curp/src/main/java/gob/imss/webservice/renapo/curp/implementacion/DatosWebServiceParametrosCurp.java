package gob.imss.webservice.renapo.curp.implementacion;

import java.util.Date;

import org.apache.log4j.Logger;

import gob.imss.webservice.renapo.curp.cliente.CurpKioscosBean;
import gob.imss.webservice.renapo.curp.cliente.ParametrosConsulta;
import gob.imss.webservice.renapo.curp.cliente.Wsimsscurp;
import gob.imss.webservice.renapo.curp.cliente.WsimsscurpSoap;

public class DatosWebServiceParametrosCurp extends Thread {
	private Logger log = Logger.getLogger(DatosWebServiceParametrosCurp.class);
	private CurpKioscosBean respuesta = null;
	private boolean existeErrorServicio = false;
	private ParametrosConsulta parametrosEntrada;

	@Override
	public void run() {
		final Wsimsscurp service = new Wsimsscurp();
		final WsimsscurpSoap port = service.getWsimsscurpSoap();

		try {
			log.error("WebserviceCurp. Thread. El proceso inicia consulta a RENAPO. " + new Date());
			respuesta = port.consultaCURP(parametrosEntrada);
			log.error("WebserviceCurp. Thread. Se finaliza la consulta satisfactoriamente a RENAPO. " + new Date());
		} catch (Exception e) {
			log.error("WebserviceCurp. Thread. Se genero un error al accesar el webservice");
			existeErrorServicio = true;
		}

		log.error("WebserviceCurp. Thread. Fin del thread. " + new Date());
	}

	public CurpKioscosBean getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(CurpKioscosBean respuesta) {
		this.respuesta = respuesta;
	}

	public boolean isExisteErrorServicio() {
		return existeErrorServicio;
	}

	public void setExisteErrorServicio(boolean existeErrorServicio) {
		this.existeErrorServicio = existeErrorServicio;
	}

	public ParametrosConsulta getParametrosEntrada() {
		return parametrosEntrada;
	}

	public void setParametrosEntrada(ParametrosConsulta parametrosEntrada) {
		this.parametrosEntrada = parametrosEntrada;
	}
}
