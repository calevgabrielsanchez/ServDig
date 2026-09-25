package mx.gob.imss.ctirss.cliente.clasificacion.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseMacII implements Serializable {

	private static final long serialVersionUID = 264087656367710841L;

	private Integer codigoError;
	private String mensajeError;
	private ArrayList<InfoConsultaMacII> infoConsultaMacII;

	public Integer getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(Integer codigoError) {
		this.codigoError = codigoError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public ArrayList<InfoConsultaMacII> getInfoConsultaMacII() {
		return infoConsultaMacII;
	}

	public void setInfoConsultaMacII(ArrayList<InfoConsultaMacII> infoConsultaMacII) {
		this.infoConsultaMacII = infoConsultaMacII;
	}

}
