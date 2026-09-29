package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;

public class ValidaRetroactividadRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 792727765628314422L;
	
	
	private String nss;
	private String usuario;
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	
	

}
