package mx.gob.imss.ctirss.delta.model.gestion.cobranza;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Entity DicEstadoPago
 * 
 * @author IMSS
 *
 */
public class EstadoPago extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	private Long cveIdEstadoPago;
	private String descripcion;
	
	
	public EstadoPago(){
		super();
	}
	
	
	public Long getCveIdEstadoPago() {
		return cveIdEstadoPago;
	}
	public void setCveIdEstadoPago(Long cveIdEstadoPago) {
		this.cveIdEstadoPago = cveIdEstadoPago;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
		
}
