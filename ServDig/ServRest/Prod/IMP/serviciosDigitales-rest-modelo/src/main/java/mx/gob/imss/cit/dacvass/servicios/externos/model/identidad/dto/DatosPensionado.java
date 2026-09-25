package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

public class DatosPensionado  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5539846187320394363L;
	String tipoPension;
	String anioPension;
	String cuentaClabe;
	public String getTipoPension() {
		return tipoPension;
	}
	public void setTipoPension(String tipoPension) {
		this.tipoPension = tipoPension;
	}
	public String getAnioPension() {
		return anioPension;
	}
	public void setAnioPension(String anioPension) {
		this.anioPension = anioPension;
	}
	public String getCuentaClabe() {
		return cuentaClabe;
	}
	public void setCuentaClabe(String cuentaClabe) {
		this.cuentaClabe = cuentaClabe;
	}
	
	
	
}
