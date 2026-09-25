package mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes;

import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class EnvioCorreoResponse extends AbstractResponseExterno {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2452275875209460774L;

	public EnvioCorreoResponse() {
		super();
	}

	public EnvioCorreoResponse(String codigo, String mensaje) {
		super(codigo, mensaje);
	}
	
}
