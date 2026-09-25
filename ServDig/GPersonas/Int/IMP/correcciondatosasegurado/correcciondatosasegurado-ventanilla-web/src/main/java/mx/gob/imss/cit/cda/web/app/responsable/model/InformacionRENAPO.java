/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.cit.cda.web.app.responsable.model;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

/**
 *
 * @author antonio
 */
public class InformacionRENAPO extends BaseModel {

	private String folio;
	private String curp;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private String lugarNacimiento;
	private String nacionalidad;
	private String datosDocumentoProbatorio;
	private String sexo;
	private String fechaNacimiento;
	private String telefonoFijo;
	private String telefonoMovil;
	private String correoElectronico;
	private String origen;
	private String idOrigen;
	private String curpsHistoricas;
	private boolean errorSINDO;

	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp
	 *            the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno
	 *            the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno
	 *            the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the lugarNacimiento
	 */
	public String getLugarNacimiento() {
		return lugarNacimiento;
	}

	/**
	 * @param lugarNacimiento
	 *            the lugarNacimiento to set
	 */
	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}

	/**
	 * @return the nacionalidad
	 */
	public String getNacionalidad() {
		return nacionalidad;
	}

	/**
	 * @param nacionalidad
	 *            the nacionalidad to set
	 */
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	/**
	 * @return the datosDocumentoProbatorio
	 */
	public String getDatosDocumentoProbatorio() {
		return datosDocumentoProbatorio;
	}

	/**
	 * @param datosDocumentoProbatorio
	 *            the datosDocumentoProbatorio to set
	 */
	public void setDatosDocumentoProbatorio(String datosDocumentoProbatorio) {
		this.datosDocumentoProbatorio = datosDocumentoProbatorio;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	@Override
	public String toString() {
		return "InformacionRENAPO [curp=" + curp + ", apellidoPaterno="
				+ apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno
				+ ", nombre=" + nombre + ", lugarNacimiento=" + lugarNacimiento
				+ ", nacionalidad=" + nacionalidad
				+ ", datosDocumentoProbatorio=" + datosDocumentoProbatorio
				+ ", sexo=" + sexo + ", fechaNacimiento=" + fechaNacimiento
				+ ", telefonoFijo=" + telefonoFijo + ", telefonoMovil="
				+ telefonoMovil + ", correoElectronico=" + correoElectronico
				+ "]";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCurpsHistoricas() {
		return curpsHistoricas;
	}

	public void setCurpsHistoricas(String curpsHistoricas) {
		this.curpsHistoricas = curpsHistoricas;
	}

	public boolean isErrorSINDO() {
		return errorSINDO;
	}

	public void setErrorSINDO(boolean errorSINDO) {
		this.errorSINDO = errorSINDO;
	}
	
	public String getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(String idOrigen) {
		this.idOrigen = idOrigen;
	}
	

}
