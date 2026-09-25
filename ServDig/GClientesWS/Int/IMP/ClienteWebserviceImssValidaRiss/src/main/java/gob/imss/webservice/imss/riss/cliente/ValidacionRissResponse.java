package gob.imss.webservice.imss.riss.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indicadorDerechoBeneficio" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tipoApartadoBeneficio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaRif" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="motivoDeRechazo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="exito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="claveError" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "indicadorDerechoBeneficio",
		"tipoApartadoBeneficio", "indicadorApartadoC", "fechaRif",
		"motivoDeRechazo", "exito", "claveError", "descripcion" })
@XmlRootElement(name = "validacionRissResponse")
public class ValidacionRissResponse {

	protected boolean indicadorDerechoBeneficio;
	protected String tipoApartadoBeneficio;
	protected boolean indicadorApartadoC;
	protected String fechaRif;
	@XmlElement(required = true)
	protected String motivoDeRechazo;
	protected int exito;
	protected int claveError;
	@XmlElement(required = true)
	protected String descripcion;

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
	 * Gets the value of the tipoApartadoBeneficio property.
	 * 
	 */
	public String getTipoApartadoBeneficio() {
		return tipoApartadoBeneficio;
	}

	/**
	 * Sets the value of the tipoApartadoBeneficio property.
	 * 
	 */
	public void setTipoApartadoBeneficio(String tipoApartadoBeneficio) {
		this.tipoApartadoBeneficio = tipoApartadoBeneficio;
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
	 * Gets the value of the motivoDeRechazo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMotivoDeRechazo() {
		return motivoDeRechazo;
	}

	/**
	 * Sets the value of the motivoDeRechazo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMotivoDeRechazo(String value) {
		this.motivoDeRechazo = value;
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
