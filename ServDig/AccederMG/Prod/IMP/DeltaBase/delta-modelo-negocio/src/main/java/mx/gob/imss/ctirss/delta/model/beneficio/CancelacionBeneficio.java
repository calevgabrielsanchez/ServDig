package mx.gob.imss.ctirss.delta.model.beneficio;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class CancelacionBeneficio extends AbstractModel {
	
	private static final long serialVersionUID = -243381322192251500L;
	
	private String idRNCancelacion;
	private int motivoCancelacion;
	
	private Integer cveOperacion;
	private String descripcionOperacion;
	
	public String getIdRNCancelacion() {
		return idRNCancelacion;
	}
	public void setIdRNCancelacion(String idRNCancelacion) {
		this.idRNCancelacion = idRNCancelacion;
	}
	public int getMotivoCancelacion() {
		return motivoCancelacion;
	}
	public void setMotivoCancelacion(int motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}
	
	public Integer getCveOperacion() {
		return cveOperacion;
	}
	public void setCveOperacion(Integer cveOperacion) {
		this.cveOperacion = cveOperacion;
	}
	public String getDescripcionOperacion() {
		return descripcionOperacion;
	}
	public void setDescripcionOperacion(String descripcionOperacion) {
		this.descripcionOperacion = descripcionOperacion;
	}
	

}
