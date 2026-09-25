
package mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParametrosConsulta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParametrosConsulta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Curp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApellidoPaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApellidoMaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sexo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechNac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CveEntidadNac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametrosConsulta", propOrder = {
    "curp",
    "apellidoPaterno",
    "apellidoMaterno",
    "nombres",
    "sexo",
    "fechNac",
    "cveEntidadNac"
})
public class ParametrosConsulta {

    @XmlElement(name = "Curp")
    protected String curp;
    @XmlElement(name = "ApellidoPaterno")
    protected String apellidoPaterno;
    @XmlElement(name = "ApellidoMaterno")
    protected String apellidoMaterno;
    @XmlElement(name = "Nombres")
    protected String nombres;
    @XmlElement(name = "Sexo")
    protected String sexo;
    @XmlElement(name = "FechNac")
    protected String fechNac;
    @XmlElement(name = "CveEntidadNac")
    protected String cveEntidadNac;

    /**
     * Gets the value of the curp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurp() {
        return curp;
    }

    /**
     * Sets the value of the curp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurp(String value) {
        this.curp = value;
    }

    /**
     * Gets the value of the apellidoPaterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Sets the value of the apellidoPaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoPaterno(String value) {
        this.apellidoPaterno = value;
    }

    /**
     * Gets the value of the apellidoMaterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Sets the value of the apellidoMaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoMaterno(String value) {
        this.apellidoMaterno = value;
    }

    /**
     * Gets the value of the nombres property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Sets the value of the nombres property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombres(String value) {
        this.nombres = value;
    }

    /**
     * Gets the value of the sexo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Sets the value of the sexo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSexo(String value) {
        this.sexo = value;
    }

    /**
     * Gets the value of the fechNac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechNac() {
        return fechNac;
    }

    /**
     * Sets the value of the fechNac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechNac(String value) {
        this.fechNac = value;
    }

    /**
     * Gets the value of the cveEntidadNac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveEntidadNac() {
        return cveEntidadNac;
    }

    /**
     * Sets the value of the cveEntidadNac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveEntidadNac(String value) {
        this.cveEntidadNac = value;
    }

}
