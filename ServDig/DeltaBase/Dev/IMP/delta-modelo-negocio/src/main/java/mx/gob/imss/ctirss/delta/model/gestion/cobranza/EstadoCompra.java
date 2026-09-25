package mx.gob.imss.ctirss.delta.model.gestion.cobranza;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Entity DicEstadoCompra
 * 
 * @author IMSS
 *
 */
public class EstadoCompra extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	private Long idEstadoCompra;
	private String descripcion;
	
	
	public EstadoCompra(){
		super();
	}
	
	
	public Long getIdEstadoCompra() {
		return idEstadoCompra;
	}
	public void setIdEstadoCompra(Long idEstadoCompra) {
		this.idEstadoCompra = idEstadoCompra;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	 
}
