package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;

public class UsuarioIdentidadDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1942819662408601702L;
	
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Integer claveDelegacion;
	private Integer claveSubDelegacion;
	private Integer claveAreaNormativa;
	private Integer claveDepartamento;
	private String descripcionArea;
	private String descripcionDelegacion;
	private String descripcionSubDelegacion;
	private String descripcionDepartamento;
	
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public Integer getClaveDelegacion() {
		return claveDelegacion;
	}
	public void setClaveDelegacion(Integer claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}
	public Integer getClaveSubDelegacion() {
		return claveSubDelegacion;
	}
	public void setClaveSubDelegacion(Integer claveSubDelegacion) {
		this.claveSubDelegacion = claveSubDelegacion;
	}
	public Integer getClaveAreaNormativa() {
		return claveAreaNormativa;
	}
	public void setClaveAreaNormativa(Integer claveAreaNormativa) {
		this.claveAreaNormativa = claveAreaNormativa;
	}
	public Integer getClaveDepartamento() {
		return claveDepartamento;
	}
	public void setClaveDepartamento(Integer claveDepartamento) {
		this.claveDepartamento = claveDepartamento;
	}
	public String getDescripcionArea() {
		return descripcionArea;
	}
	public void setDescripcionArea(String descripcionArea) {
		this.descripcionArea = descripcionArea;
	}
	public String getDescripcionDelegacion() {
		return descripcionDelegacion;
	}
	public void setDescripcionDelegacion(String descripcionDelegacion) {
		this.descripcionDelegacion = descripcionDelegacion;
	}
	public String getDescripcionSubDelegacion() {
		return descripcionSubDelegacion;
	}
	public void setDescripcionSubDelegacion(String descripcionSubDelegacion) {
		this.descripcionSubDelegacion = descripcionSubDelegacion;
	}
	public String getDescripcionDepartamento() {
		return descripcionDepartamento;
	}
	public void setDescripcionDepartamento(String descripcionDepartamento) {
		this.descripcionDepartamento = descripcionDepartamento;
	}

	
}
