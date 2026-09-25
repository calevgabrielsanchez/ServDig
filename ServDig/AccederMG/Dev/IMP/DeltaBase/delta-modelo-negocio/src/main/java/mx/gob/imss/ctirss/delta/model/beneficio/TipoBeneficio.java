package mx.gob.imss.ctirss.delta.model.beneficio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoBeneficio extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private Integer idTipoBeneficio;
	private String descripcion;

	public Integer getIdTipoBeneficio() {
		return idTipoBeneficio;
	}

	public void setIdTipoBeneficio(Integer idTipoBeneficio) {
		this.idTipoBeneficio = idTipoBeneficio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
