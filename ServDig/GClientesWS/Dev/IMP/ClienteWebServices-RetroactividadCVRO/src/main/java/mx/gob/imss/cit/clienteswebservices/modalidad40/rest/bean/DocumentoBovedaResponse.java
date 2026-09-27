package mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean;

import java.io.Serializable;

public class DocumentoBovedaResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6126020047146167885L;
	private String uuid;
	private String nombre;
	private String creado;
	private String folio;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCreado() {
		return creado;
	}
	public void setCreado(String creado) {
		this.creado = creado;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	

}
