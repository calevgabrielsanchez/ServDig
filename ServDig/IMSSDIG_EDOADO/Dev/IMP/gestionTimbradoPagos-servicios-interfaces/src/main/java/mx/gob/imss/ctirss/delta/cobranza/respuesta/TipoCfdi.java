package mx.gob.imss.ctirss.delta.cobranza.respuesta;

import java.io.Serializable;

public class TipoCfdi implements Serializable {

	private static final long serialVersionUID = -4462755792558787799L;

	private String uuid;
	private String estatusUuid;
	private String respuesta;
	private String rfcEmisor;
	private String rfcReceptor;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getEstatusUuid() {
		return estatusUuid;
	}

	public void setEstatusUuid(String estatusUuid) {
		this.estatusUuid = estatusUuid;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	public String getRfcReceptor() {
		return rfcReceptor;
	}

	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}

}
