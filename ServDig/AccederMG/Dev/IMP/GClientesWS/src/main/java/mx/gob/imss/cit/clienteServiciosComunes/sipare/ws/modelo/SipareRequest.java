package mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.modelo;

import java.io.Serializable;

public class SipareRequest implements Serializable {

	private static final long serialVersionUID = 4886064243062694617L;

	private String lineaSua;
	private int tipoPatron;

	public String getLineaSua() {
		return lineaSua;
	}

	public void setLineaSua(String lineaSua) {
		this.lineaSua = lineaSua;
	}

	public int getTipoPatron() {
		return tipoPatron;
	}

	public void setTipoPatron(int tipoPatron) {
		this.tipoPatron = tipoPatron;
	}

}
