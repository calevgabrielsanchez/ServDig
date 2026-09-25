package mx.gob.imss.ctirss.delta.model.clasificacion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ElementoConcentrado extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private Integer idDel;
	private Integer idSubDel;
	private Integer estatus;
	private String ind_marca_Clase;
	private String ind_serv_personal;
	
	@Override
	public String toString() {
		return "ElementoConcentrado [idDel=" + idDel + ", idSubDel=" + idSubDel
				+ ", estatus=" + estatus + ", ind_marca_Clase="
				+ ind_marca_Clase + ", ind_serv_personal=" + ind_serv_personal
				+ "]";
	}
	
	public Integer getIdDel() {
		return idDel;
	}
	public void setIdDel(Integer idDel) {
		this.idDel = idDel;
	}
	public Integer getIdSubDel() {
		return idSubDel;
	}
	public void setIdSubDel(Integer idSubDel) {
		this.idSubDel = idSubDel;
	}
	public Integer getEstatus() {
		return estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	public String getInd_marca_Clase() {
		return ind_marca_Clase;
	}
	public void setInd_marca_Clase(String ind_marca_Clase) {
		this.ind_marca_Clase = ind_marca_Clase;
	}
	public String getInd_serv_personal() {
		return ind_serv_personal;
	}
	public void setInd_serv_personal(String ind_serv_personal) {
		this.ind_serv_personal = ind_serv_personal;
	}
	
}
