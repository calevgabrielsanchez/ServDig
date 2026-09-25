package mx.gob.imss.cit.dacvass.servicios.externos.model.services.sau;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Puesto implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer cveArea;
	protected Integer cveDepartamento;
	protected long cvePuesto;
	protected String defaultRol;
	protected String nombreArea;
	protected String nombreDepartamento;
	protected String nombrePuesto;
	protected boolean nuevoReg;

	/**
	 * Gets the value of the cveArea property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getCveArea() {
		return cveArea;
	}

	/**
	 * Sets the value of the cveArea property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setCveArea(Integer value) {
		this.cveArea = value;
	}

	/**
	 * Gets the value of the cveDepartamento property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getCveDepartamento() {
		return cveDepartamento;
	}

	/**
	 * Sets the value of the cveDepartamento property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setCveDepartamento(Integer value) {
		this.cveDepartamento = value;
	}

	/**
	 * Gets the value of the cvePuesto property.
	 * 
	 */
	public long getCvePuesto() {
		return cvePuesto;
	}

	/**
	 * Sets the value of the cvePuesto property.
	 * 
	 */
	public void setCvePuesto(long value) {
		this.cvePuesto = value;
	}

	/**
	 * Gets the value of the defaultRol property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDefaultRol() {
		return defaultRol;
	}

	/**
	 * Sets the value of the defaultRol property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDefaultRol(String value) {
		this.defaultRol = value;
	}

	/**
	 * Gets the value of the nombreArea property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNombreArea() {
		return nombreArea;
	}

	/**
	 * Sets the value of the nombreArea property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNombreArea(String value) {
		this.nombreArea = value;
	}

	/**
	 * Gets the value of the nombreDepartamento property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	/**
	 * Sets the value of the nombreDepartamento property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNombreDepartamento(String value) {
		this.nombreDepartamento = value;
	}

	/**
	 * Gets the value of the nombrePuesto property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNombrePuesto() {
		return nombrePuesto;
	}

	/**
	 * Sets the value of the nombrePuesto property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNombrePuesto(String value) {
		this.nombrePuesto = value;
	}

	/**
	 * Gets the value of the nuevoReg property.
	 * 
	 */
	public boolean isNuevoReg() {
		return nuevoReg;
	}

	/**
	 * Sets the value of the nuevoReg property.
	 * 
	 */
	public void setNuevoReg(boolean value) {
		this.nuevoReg = value;
	}

}
