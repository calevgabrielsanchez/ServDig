package mx.imss.ctirss.bean;

import mx.imss.ctirss.framework.base.model.AbstractModel;

public class CopPagadasTable extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private String registroPatronal;
	private String razonSocial;
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
}
