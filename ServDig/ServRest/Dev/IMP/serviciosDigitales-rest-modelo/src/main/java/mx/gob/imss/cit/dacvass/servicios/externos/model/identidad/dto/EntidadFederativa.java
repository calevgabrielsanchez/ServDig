package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EntidadFederativa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3089806929705955180L;
	private String clave;
	private String nombre;
	
    private Long idRenapo;
    private String claveRenapo;
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getIdRenapo() {
		return idRenapo;
	}
	public void setIdRenapo(Long idRenapo) {
		this.idRenapo = idRenapo;
	}
	public String getClaveRenapo() {
		return claveRenapo;
	}
	public void setClaveRenapo(String claveRenapo) {
		this.claveRenapo = claveRenapo;
	}
    
    
}
