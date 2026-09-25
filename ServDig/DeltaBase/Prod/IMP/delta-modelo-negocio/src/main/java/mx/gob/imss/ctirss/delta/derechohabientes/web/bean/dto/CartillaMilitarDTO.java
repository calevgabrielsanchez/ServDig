package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CartillaMilitar;


public class CartillaMilitarDTO extends CartillaMilitar {


	private static final long serialVersionUID = 7110504307864741819L;
	/**
	 * 
	 */
	
	private String fechaExpedicionString;

	public String getFechaExpedicionString() {
		return fechaExpedicionString;
	}

	public void setFechaExpedicionString(String fechaExpedicionString) {
		this.fechaExpedicionString = fechaExpedicionString;
	}
	
	

}
