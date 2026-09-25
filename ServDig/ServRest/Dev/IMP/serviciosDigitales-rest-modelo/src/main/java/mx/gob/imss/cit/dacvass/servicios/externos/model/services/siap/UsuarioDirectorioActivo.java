package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsuarioDirectorioActivo implements Serializable {
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -635411123840387167L;
	private String primerApellido;
	private String segundoApellido;
	private String nombre;
	private String correo;
	private String nomCuenta;
	private String descripcion;
	private String infoDirectorioActivo;
	
	
	public String getInfoDirectorioActivo() {
		return infoDirectorioActivo;
	}
	public void setInfoDirectorioActivo(String infoDirectorioActivo) {
		this.infoDirectorioActivo = infoDirectorioActivo;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNomCuenta() {
		return nomCuenta;
	}
	public void setNomCuenta(String nomCuenta) {
		this.nomCuenta = nomCuenta;
	}
	
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

}
