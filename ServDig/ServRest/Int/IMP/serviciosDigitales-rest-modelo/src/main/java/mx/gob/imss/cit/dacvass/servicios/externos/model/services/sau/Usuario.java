package mx.gob.imss.cit.dacvass.servicios.externos.model.services.sau;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String apellidoMaterno;
	protected String apellidoPaterno;
	protected String nombres;
	protected Integer claveDelegacion;
	protected Integer claveSubDelegacion;
	protected String roles;
	protected Integer modulo;
	protected boolean activo;

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Integer getModulo() {
		return modulo;
	}

	public void setModulo(Integer modulo) {
		this.modulo = modulo;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	protected String puesto;

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Integer getClaveDelegacion() {
		return claveDelegacion;
	}

	public void setClaveDelegacion(Integer claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}

	public String getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(String perfiles) {
		this.perfiles = perfiles;
	}

	protected String perfiles;
	protected String uid;
	protected String uidAuditorAsignado;

	public String getUidAuditorAsignado() {
		return uidAuditorAsignado;
	}

	public void setUidAuditorAsignado(String uidAuditorAsignado) {
		this.uidAuditorAsignado = uidAuditorAsignado;
	}

	/**
	 * Gets the value of the apellidoMaterno property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * Sets the value of the apellidoMaterno property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setApellidoMaterno(String value) {
		this.apellidoMaterno = value;
	}

	/**
	 * Gets the value of the apellidoPaterno property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * Sets the value of the apellidoPaterno property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setApellidoPaterno(String value) {
		this.apellidoPaterno = value;
	}

	/**
	 * Gets the value of the claveSubDelegacion property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getClaveSubDelegacion() {
		return claveSubDelegacion;
	}

	/**
	 * Sets the value of the claveSubDelegacion property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setClaveSubDelegacion(Integer value) {
		this.claveSubDelegacion = value;
	}

	/**
	 * Gets the value of the nombres property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * Sets the value of the nombres property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNombres(String value) {
		this.nombres = value;
	}

	/**
	 * Gets the value of the uid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Sets the value of the uid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUid(String value) {
		this.uid = value;
	}

}
