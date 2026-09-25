package mx.gob.imss.ctirss.delta.model.gestion.solicitud;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Certificado extends AbstractModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4937355987505548150L;
	
	
	String claveSerial;
    String nombreCompleto;
    Integer estatusFiel;
    String correoElectronico;
    String curpFiel;
    String nombreUsuario;//RFC
    String rfcAsociado;
    Date fechaValidaInicio;
    Date fechaValidaFin;
    String telefono;
    Integer idRol;
	public String getClaveSerial() {
		return claveSerial;
	}
	public void setClaveSerial(String claveSerial) {
		this.claveSerial = claveSerial;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public Integer getEstatusFiel() {
		return estatusFiel;
	}
	public void setEstatusFiel(Integer estatusFiel) {
		this.estatusFiel = estatusFiel;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getCurpFiel() {
		return curpFiel;
	}
	public void setCurpFiel(String curpFiel) {
		this.curpFiel = curpFiel;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getRfcAsociado() {
		return rfcAsociado;
	}
	public void setRfcAsociado(String rfcAsociado) {
		this.rfcAsociado = rfcAsociado;
	}
	public Date getFechaValidaInicio() {
		return fechaValidaInicio;
	}
	public void setFechaValidaInicio(Date fechaValidaInicio) {
		this.fechaValidaInicio = fechaValidaInicio;
	}
	public Date getFechaValidaFin() {
		return fechaValidaFin;
	}
	public void setFechaValidaFin(Date fechaValidaFin) {
		this.fechaValidaFin = fechaValidaFin;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Integer getIdRol() {
		return idRol;
	}
	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}
	
	
}
