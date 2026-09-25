package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

import java.io.Serializable;

public class AdimssDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String folio;
	private String fechaExpedicion;
	
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getFechaExpedicion() {
		return fechaExpedicion;
	}
	public void setFechaExpedicion(String fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}
	
	
}
