package mx.gob.imss.digital.modelo.satRiss;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validaDerechoRifSatResponse", namespace = "http://mx.gob.imss.delta.global.service/" ) 
@XmlRootElement(name = "validaDerechoRifSatResponse", namespace = "http://mx.gob.imss.delta.global.service/")
public class ValidaDerechoRifSatResponse implements Serializable {

	private static final long serialVersionUID = -4719708288119126210L;
	
	private boolean indicadorDerechoBeneficio;
	private boolean indicadorApartadoC;
	private String fechaRif;
	private int exito;
	private int claveError;
	@XmlElement(required = true)
	private String descripcion;

	/**
	 * Gets the value of the indicadorDerechoBeneficio property.
	 * 
	 */
	public boolean isIndicadorDerechoBeneficio() {
		return indicadorDerechoBeneficio;
	}

	/**
	 * Sets the value of the indicadorDerechoBeneficio property.
	 * 
	 */
	public void setIndicadorDerechoBeneficio(boolean value) {
		this.indicadorDerechoBeneficio = value;
	}

	/**
	 * Gets the value of the indicadorApartadoC property.
	 * 
	 */
	public boolean isIndicadorApartadoC() {
		return indicadorApartadoC;
	}

	/**
	 * Sets the value of the indicadorApartadoC property.
	 * 
	 */
	public void setIndicadorApartadoC(boolean value) {
		this.indicadorApartadoC = value;
	}

	/**
	 * Gets the value of the fechaRif property.
	 * 
	 */
	public String getFechaRif() {
		return fechaRif;
	}

	/**
	 * Sets the value of the fechaRif property.
	 * 
	 */
	public void setFechaRif(String fechaRif) {
		this.fechaRif = fechaRif;
	}

	/**
	 * Gets the value of the exito property.
	 * 
	 */
	public int getExito() {
		return exito;
	}

	/**
	 * Sets the value of the exito property.
	 * 
	 */
	public void setExito(int value) {
		this.exito = value;
	}

	/**
	 * Gets the value of the claveError property.
	 * 
	 */
	public int getClaveError() {
		return claveError;
	}

	/**
	 * Sets the value of the claveError property.
	 * 
	 */
	public void setClaveError(int value) {
		this.claveError = value;
	}

	/**
	 * Gets the value of the descripcion property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the value of the descripcion property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescripcion(String value) {
		this.descripcion = value;
	}
	
	public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE) + "\n";
    }
	
}
