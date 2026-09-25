package gob.imss.webservice.imss.riss.cliente;

import java.util.ArrayList;
import java.util.List;

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
 *         &lt;element name="rfc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="curp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nss" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveAsignacion" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="registrosPatronales">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="nrp" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="tipoPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rfc", "curp", "nss", "cveAsignacion",
		"registrosPatronales", "tipoPersona" })
@XmlRootElement(name = "validacionRissRequest")
public class ValidacionRissRequest {

	@XmlElement(required = true)
	protected String rfc;
	@XmlElement(required = true, nillable = true)
	protected String curp;
	@XmlElement(required = true, nillable = true)
	protected String nss;
	@XmlElement(required = true, nillable = true)
	protected Long cveAsignacion;
	@XmlElement(required = true)
	protected ValidacionRissRequest.RegistrosPatronales registrosPatronales;
	@XmlElement(required = true)
	protected String tipoPersona;

	/**
	 * Gets the value of the rfc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * Sets the value of the rfc property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRfc(String value) {
		this.rfc = value;
	}

	/**
	 * Gets the value of the curp property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * Sets the value of the curp property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCurp(String value) {
		this.curp = value;
	}

	/**
	 * Gets the value of the nss property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNss() {
		return nss;
	}

	/**
	 * Sets the value of the nss property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNss(String value) {
		this.nss = value;
	}

	public Long getCveAsignacion() {
		return cveAsignacion;
	}

	public void setCveAsignacion(Long cveAsignacion) {
		this.cveAsignacion = cveAsignacion;
	}

	/**
	 * Gets the value of the registrosPatronales property.
	 * 
	 * @return possible object is
	 *         {@link ValidacionRissRequest.RegistrosPatronales }
	 * 
	 */
	public ValidacionRissRequest.RegistrosPatronales getRegistrosPatronales() {
		return registrosPatronales;
	}

	/**
	 * Sets the value of the registrosPatronales property.
	 * 
	 * @param value
	 *            allowed object is
	 *            {@link ValidacionRissRequest.RegistrosPatronales }
	 * 
	 */
	public void setRegistrosPatronales(
			ValidacionRissRequest.RegistrosPatronales value) {
		this.registrosPatronales = value;
	}

	/**
	 * Gets the value of the tipoPersona property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTipoPersona() {
		return tipoPersona;
	}

	/**
	 * Sets the value of the tipoPersona property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTipoPersona(String value) {
		this.tipoPersona = value;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="nrp" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "nrp" })
	public static class RegistrosPatronales {

		@XmlElement(required = true, nillable = true)
		protected List<String> nrp;

		/**
		 * Gets the value of the nrp property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a
		 * snapshot. Therefore any modification you make to the returned list
		 * will be present inside the JAXB object. This is why there is not a
		 * <CODE>set</CODE> method for the nrp property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getNrp().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link String }
		 * 
		 * 
		 */
		public List<String> getNrp() {
			if (nrp == null) {
				nrp = new ArrayList<String>();
			}
			return this.nrp;
		}

	}

	public String toString() {
		return "\n"
				+ ToStringBuilder.reflectionToString(this,
						ToStringStyle.MULTI_LINE_STYLE) + "\n";
	}
}
