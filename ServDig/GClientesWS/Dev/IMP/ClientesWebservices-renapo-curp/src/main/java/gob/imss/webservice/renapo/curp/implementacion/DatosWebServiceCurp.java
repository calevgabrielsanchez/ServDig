package gob.imss.webservice.renapo.curp.implementacion;

import java.util.Date;

import org.apache.log4j.Logger;

import gob.imss.webservice.renapo.curp.cliente.CurpKioscosBean;
import gob.imss.webservice.renapo.curp.cliente.Wsimsscurp;
import gob.imss.webservice.renapo.curp.cliente.WsimsscurpSoap;

public class DatosWebServiceCurp extends Thread {
	private Logger log = Logger.getLogger(DatosWebServiceCurp.class);
	private CurpKioscosBean respuesta = null;
	private boolean existeErrorServicio = false;
	private String curp;

	@Override
	public void run() {
		try {
			final Wsimsscurp service = new Wsimsscurp();
			final WsimsscurpSoap port = service.getWsimsscurpSoap();
			/**
			log.error("WebserviceCurp. Thread. El proceso inicia consulta a RENAPO. CURP " + curp + " timestamp "  + new Date()
					+ "thread name "  + Thread.currentThread().getName() + " thread id "+ Thread.currentThread().getId());
					**/
			respuesta = port.consultaDatosCURP(curp);
			// log.error("WebserviceCurp. Thread. Se finaliza la consulta satisfactoriamente a RENAPO. CURP " + curp + " timestamp "  + new Date());
		} catch (Exception e) {
			log.error("WebserviceCurp. Thread. Se genero un error al accesar el webservice CURP " + curp);
			existeErrorServicio = true;
			e.printStackTrace();
		}

		// log.error("WebserviceCurp. Thread. Fin del thread. " + new Date());
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

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

}
