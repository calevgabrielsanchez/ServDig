package mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoPatronAsociadoSalida complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoPatronAsociadoSalida">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoPostal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="correo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="curp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveDelegacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveMunicipio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveSubDelegacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveTipoMov" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="digitoVerificador" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="domicilio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="localidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modalidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="razonSocial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="registroPatronal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rfc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoRP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoPatronAsociadoSalida", propOrder = {
    "codigoPostal",
    "correo",
    "curp",
    "cveDelegacion",
    "cveMunicipio",
    "cveSubDelegacion",
    "cveTipoMov",
    "digitoVerificador",
    "domicilio",
    "localidad",
    "modalidad",
    "razonSocial",
    "registroPatronal",
    "rfc",
    "tipoRP"
})
public class InfoPatronAsociadoSalida {

    @XmlElement(required = true, nillable = true)
    protected String codigoPostal;
    @XmlElement(required = true, nillable = true)
    protected String correo;
    @XmlElement(required = true, nillable = true)
    protected String curp;
    @XmlElement(required = true, nillable = true)
    protected String cveDelegacion;
    @XmlElement(required = true, nillable = true)
    protected String cveMunicipio;
    @XmlElement(required = true, nillable = true)
    protected String cveSubDelegacion;
    @XmlElement(required = true, nillable = true)
    protected String cveTipoMov;
    @XmlElement(required = true, nillable = true)
    protected String digitoVerificador;
    @XmlElement(required = true, nillable = true)
    protected String domicilio;
    @XmlElement(required = true, nillable = true)
    protected String localidad;
    @XmlElement(required = true, nillable = true)
    protected String modalidad;
    @XmlElement(required = true, nillable = true)
    protected String razonSocial;
    @XmlElement(required = true, nillable = true)
    protected String registroPatronal;
    @XmlElement(required = true, nillable = true)
    protected String rfc;
    @XmlElement(required = true, nillable = true)
    protected String tipoRP;

    /**
     * Gets the value of the codigoPostal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Sets the value of the codigoPostal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPostal(String value) {
        this.codigoPostal = value;
    }

    /**
     * Gets the value of the correo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Sets the value of the correo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorreo(String value) {
        this.correo = value;
    }

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
     * Gets the value of the cveDelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveDelegacion() {
        return cveDelegacion;
    }

    /**
     * Sets the value of the cveDelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveDelegacion(String value) {
        this.cveDelegacion = value;
    }

    /**
     * Gets the value of the cveMunicipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveMunicipio() {
        return cveMunicipio;
    }

    /**
     * Sets the value of the cveMunicipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveMunicipio(String value) {
        this.cveMunicipio = value;
    }

    /**
     * Gets the value of the cveSubDelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveSubDelegacion() {
        return cveSubDelegacion;
    }

    /**
     * Sets the value of the cveSubDelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveSubDelegacion(String value) {
        this.cveSubDelegacion = value;
    }

    /**
     * Gets the value of the cveTipoMov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveTipoMov() {
        return cveTipoMov;
    }

    /**
     * Sets the value of the cveTipoMov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveTipoMov(String value) {
        this.cveTipoMov = value;
    }

    /**
     * Gets the value of the digitoVerificador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    /**
     * Sets the value of the digitoVerificador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigitoVerificador(String value) {
        this.digitoVerificador = value;
    }

    /**
     * Gets the value of the domicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     * Sets the value of the domicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicilio(String value) {
        this.domicilio = value;
    }

    /**
     * Gets the value of the localidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Sets the value of the localidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalidad(String value) {
        this.localidad = value;
    }

    /**
     * Gets the value of the modalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * Sets the value of the modalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModalidad(String value) {
        this.modalidad = value;
    }

    /**
     * Gets the value of the razonSocial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Sets the value of the razonSocial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazonSocial(String value) {
        this.razonSocial = value;
    }

    /**
     * Gets the value of the registroPatronal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistroPatronal() {
        return registroPatronal;
    }

    /**
     * Sets the value of the registroPatronal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistroPatronal(String value) {
        this.registroPatronal = value;
    }

    /**
     * Gets the value of the rfc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * Sets the value of the rfc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfc(String value) {
        this.rfc = value;
    }

    /**
     * Gets the value of the tipoRP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRP() {
        return tipoRP;
    }

    /**
     * Sets the value of the tipoRP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRP(String value) {
        this.tipoRP = value;
    }

}
