package mx.gob.imss.ctirss.correccion.promocion.base.model;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public class DataTableCriterioSeleccion extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private String registroPatronal;
	private String razonSocial;
	private Long cveSelector;
	private Long subDelegacion;
	private Long idCriterioSeleccion;
	
	public Long getSubDelegacion() {
		return subDelegacion;
	}
	public void setSubDelegacion(Long subDelegacion) {
		this.subDelegacion = subDelegacion;
	}
	public Long getIdCriterioSeleccion() {
		return idCriterioSeleccion;
	}
	public void setIdCriterioSeleccion(Long idCriterioSeleccion) {
		this.idCriterioSeleccion = idCriterioSeleccion;
	}
	public Long getCveSelector() {
		return cveSelector;
	}
	public void setCveSelector(Long cveSelector) {
		this.cveSelector = cveSelector;
	}
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
