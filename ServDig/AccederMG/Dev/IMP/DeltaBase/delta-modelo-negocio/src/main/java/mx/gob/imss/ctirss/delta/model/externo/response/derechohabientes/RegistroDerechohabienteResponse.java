package mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes;

import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class RegistroDerechohabienteResponse extends AbstractResponseExterno{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9105792538959411933L;
	private String folio;

	public RegistroDerechohabienteResponse () {
		super();
	}
	
	public RegistroDerechohabienteResponse(String folio) {
		super();
		this.folio = folio;
	}
	
	public RegistroDerechohabienteResponse(String folio,String codigo, String mensaje) {
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
