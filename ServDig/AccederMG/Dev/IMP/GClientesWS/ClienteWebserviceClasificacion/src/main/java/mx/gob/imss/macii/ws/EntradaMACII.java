
package mx.gob.imss.macii.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for entradaMACII complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="entradaMACII">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NRP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fec_Ini_Registro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Fec_Fin_Registro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Cve_Tipo_Persona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Clase_Rectificada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipo_Registro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cve_Estatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cve_Delegacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cve_Subdelegacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipo_Movimiento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Tipo_Tramite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entradaMACII", propOrder = {
    "nrp",
    "fecIniRegistro",
    "fecFinRegistro",
    "cveTipoPersona",
    "claseRectificada",
    "tipoRegistro",
    "cveEstatus",
    "cveDelegacion",
    "cveSubdelegacion",
    "tipoMovimiento",
    "tipoTramite"
})
public class EntradaMACII {

    @XmlElement(name = "NRP")
    protected String nrp;
    @XmlElement(name = "Fec_Ini_Registro", required = true)
    protected String fecIniRegistro;
    @XmlElement(name = "Fec_Fin_Registro", required = true)
    protected String fecFinRegistro;
    @XmlElement(name = "Cve_Tipo_Persona")
    protected String cveTipoPersona;
    @XmlElement(name = "Clase_Rectificada")
    protected String claseRectificada;
    @XmlElement(name = "Tipo_Registro")
    protected String tipoRegistro;
    @XmlElement(name = "Cve_Estatus")
    protected String cveEstatus;
    @XmlElement(name = "Cve_Delegacion")
    protected String cveDelegacion;
    @XmlElement(name = "Cve_Subdelegacion")
    protected String cveSubdelegacion;
    @XmlElement(name = "Tipo_Movimiento", required = true)
    protected String tipoMovimiento;
    @XmlElement(name = "Tipo_Tramite")
    protected String tipoTramite;

    /**
     * Gets the value of the nrp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNRP() {
        return nrp;
    }

    /**
     * Sets the value of the nrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNRP(String value) {
        this.nrp = value;
    }

    /**
     * Gets the value of the fecIniRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecIniRegistro() {
        return fecIniRegistro;
    }

    /**
     * Sets the value of the fecIniRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecIniRegistro(String value) {
        this.fecIniRegistro = value;
    }

    /**
     * Gets the value of the fecFinRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecFinRegistro() {
        return fecFinRegistro;
    }

    /**
     * Sets the value of the fecFinRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecFinRegistro(String value) {
        this.fecFinRegistro = value;
    }

    /**
     * Gets the value of the cveTipoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveTipoPersona() {
        return cveTipoPersona;
    }

    /**
     * Sets the value of the cveTipoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveTipoPersona(String value) {
        this.cveTipoPersona = value;
    }

    /**
     * Gets the value of the claseRectificada property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaseRectificada() {
        return claseRectificada;
    }

    /**
     * Sets the value of the claseRectificada property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaseRectificada(String value) {
        this.claseRectificada = value;
    }

    /**
     * Gets the value of the tipoRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * Sets the value of the tipoRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRegistro(String value) {
        this.tipoRegistro = value;
    }

    /**
     * Gets the value of the cveEstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveEstatus() {
        return cveEstatus;
    }

    /**
     * Sets the value of the cveEstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveEstatus(String value) {
        this.cveEstatus = value;
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
     * Gets the value of the cveSubdelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveSubdelegacion() {
        return cveSubdelegacion;
    }

    /**
     * Sets the value of the cveSubdelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveSubdelegacion(String value) {
        this.cveSubdelegacion = value;
    }

    /**
     * Gets the value of the tipoMovimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * Sets the value of the tipoMovimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoMovimiento(String value) {
        this.tipoMovimiento = value;
    }

    /**
     * Gets the value of the tipoTramite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoTramite() {
        return tipoTramite;
    }

    /**
     * Sets the value of the tipoTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoTramite(String value) {
        this.tipoTramite = value;
    }

}
