package mx.imss.ctirss.denuncia.vo;

import java.io.Serializable;

public class MotivoDenVO implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int cveMotivoDenuncia;
	private String fechaLabDelIngreso;
	private String fechaLabDejoLab;
	private String impoSalarioReal;
	private String impoSalarioReg;
	public int getCveMotivoDenuncia() {
		return cveMotivoDenuncia;
	}
	public void setCveMotivoDenuncia(int cveMotivoDenuncia) {
		this.cveMotivoDenuncia = cveMotivoDenuncia;
	}
	public String getFechaLabDelIngreso() {
		return fechaLabDelIngreso;
	}
	public void setFechaLabDelIngreso(String fechaLabDelIngreso) {
		this.fechaLabDelIngreso = fechaLabDelIngreso;
	}
	public String getFechaLabDejoLab() {
		return fechaLabDejoLab;
	}
	public void setFechaLabDejoLab(String fechaLabDejoLab) {
		this.fechaLabDejoLab = fechaLabDejoLab;
	}
	public String getImpoSalarioReal() {
		return impoSalarioReal;
	}
	public void setImpoSalarioReal(String impoSalarioReal) {
		this.impoSalarioReal = impoSalarioReal;
	}
	public String getImpoSalarioReg() {
		return impoSalarioReg;
	}
	public void setImpoSalarioReg(String impoSalarioReg) {
		this.impoSalarioReg = impoSalarioReg;
	}
	


}
