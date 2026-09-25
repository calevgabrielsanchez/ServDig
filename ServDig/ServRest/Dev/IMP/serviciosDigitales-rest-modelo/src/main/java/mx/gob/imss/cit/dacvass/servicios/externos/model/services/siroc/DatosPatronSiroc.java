package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DatosPatronSiroc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 259620589352526804L;
	
	private String delegacion;
	private String subDelegacion;
	private String nombreRazonSocial;
	private String rfc;
	private String registroPatronal;
	private String numeroRegistroObra;
	private Date fechaRegistro;
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public String getSubDelegacion() {
		return subDelegacion;
	}
	public void setSubDelegacion(String subDelegacion) {
		this.subDelegacion = subDelegacion;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public String getNumeroRegistroObra() {
		return numeroRegistroObra;
	}
	public void setNumeroRegistroObra(String numeroRegistroObra) {
		this.numeroRegistroObra = numeroRegistroObra;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	

}
