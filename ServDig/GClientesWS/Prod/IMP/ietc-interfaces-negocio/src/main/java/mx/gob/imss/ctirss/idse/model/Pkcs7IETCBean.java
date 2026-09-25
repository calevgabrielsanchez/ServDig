package mx.gob.imss.ctirss.idse.model;

import java.util.Date;

public class Pkcs7IETCBean implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 7992016066512951496L;

	private String claveSerial;

	private String nombreCompleto;

	private String estatusFiel;

	private String correoElectronico;

	private String curpFiel;

	private String nombreUsuario;

	private String rfcAsociado;

	private Date fechaValidaInicio;

	private Date fechaValidaFin;

	private String telefono;

	private int idRol;

	public Pkcs7IETCBean() {
	}

	public Pkcs7IETCBean(final String claveSerial, final String nombreCompleto, final String estatusFiel,
			final String correoElectronico, final String curpFiel, final String nombreUsuario,
			final String rfcAsociado, final Date fechaValidaInicio,
			final Date fechaValidaFin, final String telefono, final int idRol) {
		this.claveSerial = claveSerial;
		this.nombreCompleto = nombreCompleto;
		this.estatusFiel = estatusFiel;
		this.correoElectronico = correoElectronico;
		this.curpFiel = curpFiel;
		this.nombreUsuario = nombreUsuario;
		this.rfcAsociado = rfcAsociado;
		this.fechaValidaInicio = fechaValidaInicio;
		this.fechaValidaFin = fechaValidaFin;
		this.telefono = telefono;
		this.idRol = idRol;
	}

	/**
	 * Gets the claveSerial value for this Pkcs7IETCBean.
	 * 
	 * @return claveSerial
	 */
	public String getClaveSerial() {
		return claveSerial;
	}

	/**
	 * Sets the claveSerial value for this Pkcs7IETCBean.
	 * 
	 * @param claveSerial
	 */
	public void setClaveSerial(final String claveSerial) {
		this.claveSerial = claveSerial;
	}

	/**
	 * Gets the nombreCompleto value for this Pkcs7IETCBean.
	 * 
	 * @return nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * Sets the nombreCompleto value for this Pkcs7IETCBean.
	 * 
	 * @param nombreCompleto
	 */
	public void setNombreCompleto(final String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * Gets the estatusFiel value for this Pkcs7IETCBean.
	 * 
	 * @return estatusFiel
	 */
	public String getEstatusFiel() {
		return estatusFiel;
	}

	/**
	 * Sets the estatusFiel value for this Pkcs7IETCBean.
	 * 
	 * @param estatusFiel
	 */
	public void setEstatusFiel(final String estatusFiel) {
		this.estatusFiel = estatusFiel;
	}

	/**
	 * Gets the correoElectronico value for this Pkcs7IETCBean.
	 * 
	 * @return correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	/**
	 * Sets the correoElectronico value for this Pkcs7IETCBean.
	 * 
	 * @param correoElectronico
	 */
	public void setCorreoElectronico(final String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	/**
	 * Gets the curpFiel value for this Pkcs7IETCBean.
	 * 
	 * @return curpFiel
	 */
	public String getCurpFiel() {
		return curpFiel;
	}

	/**
	 * Sets the curpFiel value for this Pkcs7IETCBean.
	 * 
	 * @param curpFiel
	 */
	public void setCurpFiel(final String curpFiel) {
		this.curpFiel = curpFiel;
	}

	/**
	 * Gets the nombreUsuario value for this Pkcs7IETCBean.
	 * 
	 * @return nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Sets the nombreUsuario value for this Pkcs7IETCBean.
	 * 
	 * @param nombreUsuario
	 */
	public void setNombreUsuario(final String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Gets the rfcAsociado value for this Pkcs7IETCBean.
	 * 
	 * @return rfcAsociado
	 */
	public String getRfcAsociado() {
		return rfcAsociado;
	}

	/**
	 * Sets the rfcAsociado value for this Pkcs7IETCBean.
	 * 
	 * @param rfcAsociado
	 */
	public void setRfcAsociado(final String rfcAsociado) {
		this.rfcAsociado = rfcAsociado;
	}

	/**
	 * Gets the fechaValidaInicio value for this Pkcs7IETCBean.
	 * 
	 * @return fechaValidaInicio
	 */
	public Date getFechaValidaInicio() {
		return fechaValidaInicio;
	}

	/**
	 * Sets the fechaValidaInicio value for this Pkcs7IETCBean.
	 * 
	 * @param fechaValidaInicio
	 */
	public void setFechaValidaInicio(final Date fechaValidaInicio) {
		this.fechaValidaInicio = fechaValidaInicio;
	}

	/**
	 * Gets the fechaValidaFin value for this Pkcs7IETCBean.
	 * 
	 * @return fechaValidaFin
	 */
	public Date getFechaValidaFin() {
		return fechaValidaFin;
	}

	/**
	 * Sets the fechaValidaFin value for this Pkcs7IETCBean.
	 * 
	 * @param fechaValidaFin
	 */
	public void setFechaValidaFin(final Date fechaValidaFin) {
		this.fechaValidaFin = fechaValidaFin;
	}

	/**
	 * Gets the telefono value for this Pkcs7IETCBean.
	 * 
	 * @return telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Sets the telefono value for this Pkcs7IETCBean.
	 * 
	 * @param telefono
	 */
	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Gets the idRol value for this Pkcs7IETCBean.
	 * 
	 * @return idRol
	 */
	public int getIdRol() {
		return idRol;
	}

	/**
	 * Sets the idRol value for this Pkcs7IETCBean.
	 * 
	 * @param idRol
	 */
	public void setIdRol(final int idRol) {
		this.idRol = idRol;
	}

}
