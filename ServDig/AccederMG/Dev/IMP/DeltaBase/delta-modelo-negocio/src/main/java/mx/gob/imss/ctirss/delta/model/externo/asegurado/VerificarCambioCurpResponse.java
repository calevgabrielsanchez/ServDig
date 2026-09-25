package mx.gob.imss.ctirss.delta.model.externo.asegurado;

import mx.gob.imss.ctirss.delta.model.externo.AbstractResponseExterno;

public class VerificarCambioCurpResponse extends AbstractResponseExterno  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String estatus;
	private String fechaActualizacion;
	
	public VerificarCambioCurpResponse() {
		super();
	}
	
	public VerificarCambioCurpResponse(String codigo, String mensaje) {
		super(codigo, mensaje);
		this.estatus=null;
		this.fechaActualizacion=null;
	}
	
	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
}