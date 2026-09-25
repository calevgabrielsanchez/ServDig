package mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Impuesto extends AbstractModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Double importe;
	private String descripcion;
		
	
	public Impuesto() {
		super();
	}	
	
	public Impuesto(Double importe, String descripcion) {
		super();
		this.importe = importe;
		this.descripcion = descripcion;
	}

	
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
