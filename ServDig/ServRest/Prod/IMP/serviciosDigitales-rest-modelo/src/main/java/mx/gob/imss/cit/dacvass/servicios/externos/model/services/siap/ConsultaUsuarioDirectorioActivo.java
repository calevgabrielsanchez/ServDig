package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap;

import java.io.Serializable;

public class ConsultaUsuarioDirectorioActivo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7441220179655803766L;
	private String claveUsusaio;
	private String dominio;
	private String password;
	
	public String getClaveUsusaio() {
		return claveUsusaio;
	}
	public void setClaveUsusaio(String claveUsusaio) {
		this.claveUsusaio = claveUsusaio;
	}
	public String getDominio() {
		return dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String toString() {
		return "claveUsuario " + claveUsusaio + "\n"
				+ "dominio " + dominio + "\n"
				+ "contrasenia" + password;
	}
	
	

}
