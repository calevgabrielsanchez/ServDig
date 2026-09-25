package mx.gob.imss.ctirss.delta.model.clasificacion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class SubdelegacionesConcentrado extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	private Integer idDel;
	private Integer idSubDel;
	private String del;
	private String subDel;
	
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
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	public String getSubDel() {
		return subDel;
	}
	public void setSubDel(String subDel) {
		this.subDel = subDel;
	}	

}
