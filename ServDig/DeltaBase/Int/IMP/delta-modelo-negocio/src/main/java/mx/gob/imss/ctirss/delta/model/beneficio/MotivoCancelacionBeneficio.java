package mx.gob.imss.ctirss.delta.model.beneficio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class MotivoCancelacionBeneficio extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private Integer idMotivoCancelacionBeneficio;
	private String descripcion;

	public Integer getIdMotivoCancelacionBeneficio() {
		return idMotivoCancelacionBeneficio;
	}

	public void setIdMotivoCancelacionBeneficio(
			Integer idMotivoCancelacionBeneficio) {
		this.idMotivoCancelacionBeneficio = idMotivoCancelacionBeneficio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
