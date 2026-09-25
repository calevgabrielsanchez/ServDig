package mx.gob.imss.ctirss.delta.model.externo.asegurado;

import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class CambioCurpResponse extends AbstractResponseExterno {

	private String folio;

	public CambioCurpResponse() {
		super();
	}
	
	public CambioCurpResponse(String codigo, String mensaje) {
		super(codigo,mensaje);
		this.folio = null;
	}
	
	public CambioCurpResponse(String codigo, String mensaje, String folio) {
		super(codigo, mensaje);
		this.folio = folio;
	}
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	
}
