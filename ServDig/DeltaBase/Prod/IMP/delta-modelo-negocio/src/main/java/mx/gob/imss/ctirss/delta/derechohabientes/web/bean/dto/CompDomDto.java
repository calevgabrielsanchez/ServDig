package mx.gob.imss.ctirss.delta.derechohabientes.web.bean.dto;

import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ComprobanteDomicilio;

public class CompDomDto extends ComprobanteDomicilio {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1598742047546863896L;
	private String fechaExpedicionString;
	private String folio;
	

	public String getFechaExpedicionString() {
		return fechaExpedicionString;
	}

	public void setFechaExpedicionString(String fechaExpedicionString) {
		this.fechaExpedicionString = fechaExpedicionString;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	
	
}
