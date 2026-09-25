package mx.gob.imss.ctirss.delta.model.gestion.seguro;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * Entity DicEstadoSeguro
 * 
 * @author IMSS
 *
 */
public class EstadoSeguroIvro extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	private long idEstadoSeguro;
	private String descripcion;
	
	
	public EstadoSeguroIvro(){
		super();
	}
	
	
	public long getIdEstadoSeguro() {
		return idEstadoSeguro;
	}
	public void setIdEstadoSeguro(long idEstadoSeguro) {
		this.idEstadoSeguro = idEstadoSeguro;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
