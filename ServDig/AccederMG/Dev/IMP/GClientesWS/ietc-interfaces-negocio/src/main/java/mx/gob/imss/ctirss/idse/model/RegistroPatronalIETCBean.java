package mx.gob.imss.ctirss.idse.model;

import java.util.Date;

public class RegistroPatronalIETCBean implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1236631438396101929L;

	private String registroPatronal;

	private Date fechaRecepcion;

	private Date fechaActivacion;

	private String razonSocial;

	private String rfcRegistroPatronal;

	private int tipoPersona;

	private int estatusRP;

	private String usuarioSubDel;

	public RegistroPatronalIETCBean() {
	}

	public RegistroPatronalIETCBean(final String registroPatronal, final Date fechaRecepcion,
			final Date fechaActivacion, final String razonSocial, final String rfcRegistroPatronal,
			final int tipoPersona, final int estatusRP, final String usuarioSubDel) {
		this.registroPatronal = registroPatronal;
		this.fechaRecepcion = fechaRecepcion;
		this.fechaActivacion = fechaActivacion;
		this.razonSocial = razonSocial;
		this.rfcRegistroPatronal = rfcRegistroPatronal;
		this.tipoPersona = tipoPersona;
		this.estatusRP = estatusRP;
		this.usuarioSubDel = usuarioSubDel;
	}

	/**
	 * Gets the registroPatronal value for this RegistroPatronalIETCBean.
	 * 
	 * @return registroPatronal
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * Sets the registroPatronal value for this RegistroPatronalIETCBean.
	 * 
	 * @param registroPatronal
	 */
	public void setRegistroPatronal(final String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	/**
	 * Gets the fechaRecepcion value for this RegistroPatronalIETCBean.
	 * 
	 * @return fechaRecepcion
	 */
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	/**
	 * Sets the fechaRecepcion value for this RegistroPatronalIETCBean.
	 * 
	 * @param fechaRecepcion
	 */
	public void setFechaRecepcion(final Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	/**
	 * Gets the fechaActivacion value for this RegistroPatronalIETCBean.
	 * 
	 * @return fechaActivacion
	 */
	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	/**
	 * Sets the fechaActivacion value for this RegistroPatronalIETCBean.
	 * 
	 * @param fechaActivacion
	 */
	public void setFechaActivacion(final Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	/**
	 * Gets the razonSocial value for this RegistroPatronalIETCBean.
	 * 
	 * @return razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * Sets the razonSocial value for this RegistroPatronalIETCBean.
	 * 
	 * @param razonSocial
	 */
	public void setRazonSocial(final String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * Gets the rfcRegistroPatronal value for this RegistroPatronalIETCBean.
	 * 
	 * @return rfcRegistroPatronal
	 */
	public String getRfcRegistroPatronal() {
		return rfcRegistroPatronal;
	}

	/**
	 * Sets the rfcRegistroPatronal value for this RegistroPatronalIETCBean.
	 * 
	 * @param rfcRegistroPatronal
	 */
	public void setRfcRegistroPatronal(final String rfcRegistroPatronal) {
		this.rfcRegistroPatronal = rfcRegistroPatronal;
	}

	/**
	 * Gets the tipoPersona value for this RegistroPatronalIETCBean.
	 * 
	 * @return tipoPersona
	 */
	public int getTipoPersona() {
		return tipoPersona;
	}

	/**
	 * Sets the tipoPersona value for this RegistroPatronalIETCBean.
	 * 
	 * @param tipoPersona
	 */
	public void setTipoPersona(final int tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	/**
	 * Gets the estatusRP value for this RegistroPatronalIETCBean.
	 * 
	 * @return estatusRP
	 */
	public int getEstatusRP() {
		return estatusRP;
	}

	/**
	 * Sets the estatusRP value for this RegistroPatronalIETCBean.
	 * 
	 * @param estatusRP
	 */
	public void setEstatusRP(final int estatusRP) {
		this.estatusRP = estatusRP;
	}

	/**
	 * Gets the usuarioSubDel value for this RegistroPatronalIETCBean.
	 * 
	 * @return usuarioSubDel
	 */
	public String getUsuarioSubDel() {
		return usuarioSubDel;
	}

	/**
	 * Sets the usuarioSubDel value for this RegistroPatronalIETCBean.
	 * 
	 * @param usuarioSubDel
	 */
	public void setUsuarioSubDel(final String usuarioSubDel) {
		this.usuarioSubDel = usuarioSubDel;
	}
}
