package mx.gob.imss.ctirss.delta.model.beneficio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class EstadoBeneficio extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private Integer idEstadoBeneficio;
	private String descripcion;

	public Integer getIdEstadoBeneficio() {
		return idEstadoBeneficio;
	}

	public void setIdEstadoBeneficio(Integer idEstadoBeneficio) {
		this.idEstadoBeneficio = idEstadoBeneficio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
