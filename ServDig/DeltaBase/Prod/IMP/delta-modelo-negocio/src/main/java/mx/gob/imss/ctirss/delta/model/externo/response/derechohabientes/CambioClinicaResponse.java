package mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes;

import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class CambioClinicaResponse extends AbstractResponseExterno{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6324668191917190557L;
	private String folio;
	
	public CambioClinicaResponse () {
		super();
	}
	
	public CambioClinicaResponse(String folio) {
		super();
		this.folio = folio;
	}
	
	public CambioClinicaResponse(String folio,String codigo, String mensaje) {
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