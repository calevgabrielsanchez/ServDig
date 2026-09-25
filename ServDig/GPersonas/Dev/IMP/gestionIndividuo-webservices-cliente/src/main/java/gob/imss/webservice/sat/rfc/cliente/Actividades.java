
package gob.imss.webservice.sat.rfc.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Actividades complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Actividades">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Orden" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Porcentaje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="c_Actividad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="d_Actividad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Alta_Act" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Baja_Act" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Actividades", propOrder = {
    "orden",
    "porcentaje",
    "cActividad",
    "dActividad",
    "fAltaAct",
    "fBajaAct"
})
public class Actividades {

    @XmlElement(name = "Orden", required = true, nillable = true)
    protected String orden;
    @XmlElement(name = "Porcentaje", required = true, nillable = true)
    protected String porcentaje;
    @XmlElement(name = "c_Actividad", required = true, nillable = true)
    protected String cActividad;
    @XmlElement(name = "d_Actividad", required = true, nillable = true)
    protected String dActividad;
    @XmlElement(name = "f_Alta_Act", required = true, nillable = true)
    protected String fAltaAct;
    @XmlElement(name = "f_Baja_Act", required = true, nillable = true)
    protected String fBajaAct;

    /**
     * Gets the value of the orden property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrden() {
        return orden;
    }

    /**
     * Sets the value of the orden property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrden(String value) {
        this.orden = value;
    }

    /**
     * Gets the value of the porcentaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPorcentaje() {
        return porcentaje;
    }

    /**
     * Sets the value of the porcentaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPorcentaje(String value) {
        this.porcentaje = value;
    }

    /**
     * Gets the value of the cActividad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCActividad() {
        return cActividad;
    }

    /**
     * Sets the value of the cActividad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCActividad(String value) {
        this.cActividad = value;
    }

    /**
     * Gets the value of the dActividad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDActividad() {
        return dActividad;
    }

    /**
     * Sets the value of the dActividad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDActividad(String value) {
        this.dActividad = value;
    }

    /**
     * Gets the value of the fAltaAct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFAltaAct() {
        return fAltaAct;
    }

    /**
     * Sets the value of the fAltaAct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFAltaAct(String value) {
        this.fAltaAct = value;
    }

    /**
     * Gets the value of the fBajaAct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFBajaAct() {
        return fBajaAct;
    }

    /**
     * Sets the value of the fBajaAct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFBajaAct(String value) {
        this.fBajaAct = value;
    }

}
