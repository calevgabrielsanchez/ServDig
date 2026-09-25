package mx.gob.imss.ctirss.delta.model.externo.asegurado;

import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class ValidaRequisitosCambioCurpResponse extends AbstractResponseExterno {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6411266260062276133L;
	private TramiteCambioCurpDTO tramite;
	
	public ValidaRequisitosCambioCurpResponse() {
		super();
		this.tramite = null;
	}
	
	public ValidaRequisitosCambioCurpResponse(String codigo, String mensaje) {
		super(codigo,mensaje);
		this.tramite = null;
	}
	
	public ValidaRequisitosCambioCurpResponse(String codigo, String mensaje, TramiteCambioCurpDTO tramite) {
		super(codigo,mensaje);
		this.tramite = tramite;
	}

	public TramiteCambioCurpDTO getTramite() {
		return tramite;
	}

	public void setTramite(TramiteCambioCurpDTO tramite) {
		this.tramite = tramite;
	}

	
}