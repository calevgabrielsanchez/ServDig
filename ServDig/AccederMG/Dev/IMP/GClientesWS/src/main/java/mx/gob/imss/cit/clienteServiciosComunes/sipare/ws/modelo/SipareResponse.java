package mx.gob.imss.cit.clienteServiciosComunes.sipare.ws.modelo;

import java.io.Serializable;

public class SipareResponse implements Serializable {

	private static final long serialVersionUID = 5967520898307005725L;

	private String[] listExcepciones;
	private String lineaCaptura;
	private byte[] pdf;

	public String[] getListExcepciones() {
		return listExcepciones;
	}

	public void setListExcepciones(String[] listExcepciones) {
		this.listExcepciones = listExcepciones;
	}

	public String getLineaCaptura() {
		return lineaCaptura;
	}

	public void setLineaCaptura(String lineaCaptura) {
		this.lineaCaptura = lineaCaptura;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

}
