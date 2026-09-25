package mx.imss.estrados.dto;

import java.io.Serializable;

public class TipoAdjuntoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2633495929190694503L;
	/**
	 * 
	 */
	

	private Integer cveTipoAdjunto;
	private String desTipoAdjunto;

	public Integer getCveTipoAdjunto() {
		return cveTipoAdjunto;
	}

	public void setCveTipoAdjunto(Integer cveTipoAdjunto) {
		this.cveTipoAdjunto = cveTipoAdjunto;
	}

	public String getDesTipoAdjunto() {
		return desTipoAdjunto;
	}

	public void setDesTipoAdjunto(String desTipoAdjunto) {
		this.desTipoAdjunto = desTipoAdjunto;
	}

}
