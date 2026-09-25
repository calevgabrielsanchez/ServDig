package mx.gob.imss.ctirss.sso.admonusuarios.ttds.modelo;

import java.io.Serializable;

public class UsuarioTTD implements Serializable {

	private static final long serialVersionUID = -7784690099841544972L;

	private String nss;
	private String curp;
	private String matricula;
	private String nombre;
	private String puestoDesc;
	private String departamentoDesc;
	private String delegacionCve;
	private String tipoContratacion;
	private String estatus;

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPuestoDesc() {
		return puestoDesc;
	}

	public void setPuestoDesc(String puestoDesc) {
		this.puestoDesc = puestoDesc;
	}

	public String getDepartamentoDesc() {
		return departamentoDesc;
	}

	public void setDepartamentoDesc(String departamentoDesc) {
		this.departamentoDesc = departamentoDesc;
	}

	public String getDelegacionCve() {
		return delegacionCve;
	}

	public void setDelegacionCve(String delegacionCve) {
		this.delegacionCve = delegacionCve;
	}

	public String getTipoContratacion() {
		return tipoContratacion;
	}

	public void setTipoContratacion(String tipoContratacion) {
		this.tipoContratacion = tipoContratacion;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

}
