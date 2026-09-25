
package mx.gob.imss.cit.dpes.core.modelo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TipoSolicitudDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TipoSolicitudDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CveIdTipoSolicitud" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DesTipoSolicitud" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FecRegistroActualizado" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecRegistroAlta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecRegistroBaja" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoSolicitudDTO", propOrder = {
    "cveIdTipoSolicitud",
    "desTipoSolicitud",
    "fecRegistroActualizado",
    "fecRegistroAlta",
    "fecRegistroBaja"
})
public class TipoSolicitudDTO {

    @XmlElement(name = "CveIdTipoSolicitud", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdTipoSolicitud;
    @XmlElement(name = "DesTipoSolicitud", required = true, nillable = true)
    protected String desTipoSolicitud;
    @XmlElement(name = "FecRegistroActualizado", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroActualizado;
    @XmlElement(name = "FecRegistroAlta", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroAlta;
    @XmlElement(name = "FecRegistroBaja", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroBaja;

    /**
     * Gets the value of the cveIdTipoSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdTipoSolicitud() {
        return cveIdTipoSolicitud;
    }

    /**
     * Sets the value of the cveIdTipoSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdTipoSolicitud(Integer value) {
        this.cveIdTipoSolicitud = value;
    }

    /**
     * Gets the value of the desTipoSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesTipoSolicitud() {
        return desTipoSolicitud;
    }

    /**
     * Sets the value of the desTipoSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesTipoSolicitud(String value) {
        this.desTipoSolicitud = value;
    }

    /**
     * Gets the value of the fecRegistroActualizado property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecRegistroActualizado() {
        return fecRegistroActualizado;
    }

    /**
     * Sets the value of the fecRegistroActualizado property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecRegistroActualizado(XMLGregorianCalendar value) {
        this.fecRegistroActualizado = value;
    }

    /**
     * Gets the value of the fecRegistroAlta property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecRegistroAlta() {
        return fecRegistroAlta;
    }

    /**
     * Sets the value of the fecRegistroAlta property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecRegistroAlta(XMLGregorianCalendar value) {
        this.fecRegistroAlta = value;
    }

    /**
     * Gets the value of the fecRegistroBaja property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecRegistroBaja() {
        return fecRegistroBaja;
    }

    /**
     * Sets the value of the fecRegistroBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecRegistroBaja(XMLGregorianCalendar value) {
        this.fecRegistroBaja = value;
    }

}
