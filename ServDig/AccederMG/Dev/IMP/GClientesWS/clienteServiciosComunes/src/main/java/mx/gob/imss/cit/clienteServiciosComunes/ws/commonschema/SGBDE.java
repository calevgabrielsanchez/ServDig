
package mx.gob.imss.cit.clienteServiciosComunes.ws.commonschema;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SGBDE complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SGBDE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idOrganizacion" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="idZona" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="nombreTramite" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="folio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoIdEntidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idEntidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="contextoEntidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaPeticion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="idSistema" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="idCanal" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="idOperacion" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="idServicio" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SGBDE", propOrder = {
    "idOrganizacion",
    "idZona",
    "nombreTramite",
    "folio",
    "tipoIdEntidad",
    "idEntidad",
    "contextoEntidad",
    "fechaPeticion",
    "idSistema",
    "idCanal",
    "idOperacion",
    "idServicio"
})
public class SGBDE {

    @XmlElement(required = true)
    protected BigInteger idOrganizacion;
    @XmlElement(required = true)
    protected BigInteger idZona;
    @XmlElement(required = true)
    protected String nombreTramite;
    @XmlElement(required = true)
    protected String folio;
    @XmlElement(required = true)
    protected String tipoIdEntidad;
    @XmlElement(required = true)
    protected String idEntidad;
    @XmlElement(required = true)
    protected String contextoEntidad;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaPeticion;
    @XmlElement(required = true)
    protected BigInteger idSistema;
    @XmlElement(required = true)
    protected BigInteger idCanal;
    @XmlElement(required = true)
    protected BigInteger idOperacion;
    @XmlElement(required = true)
    protected BigInteger idServicio;

    /**
     * Gets the value of the idOrganizacion property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdOrganizacion() {
        return idOrganizacion;
    }

    /**
     * Sets the value of the idOrganizacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdOrganizacion(BigInteger value) {
        this.idOrganizacion = value;
    }

    /**
     * Gets the value of the idZona property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdZona() {
        return idZona;
    }

    /**
     * Sets the value of the idZona property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdZona(BigInteger value) {
        this.idZona = value;
    }

    /**
     * Gets the value of the nombreTramite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreTramite() {
        return nombreTramite;
    }

    /**
     * Sets the value of the nombreTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreTramite(String value) {
        this.nombreTramite = value;
    }

    /**
     * Gets the value of the folio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Sets the value of the folio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolio(String value) {
        this.folio = value;
    }

    /**
     * Gets the value of the tipoIdEntidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoIdEntidad() {
        return tipoIdEntidad;
    }

    /**
     * Sets the value of the tipoIdEntidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoIdEntidad(String value) {
        this.tipoIdEntidad = value;
    }

    /**
     * Gets the value of the idEntidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdEntidad() {
        return idEntidad;
    }

    /**
     * Sets the value of the idEntidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdEntidad(String value) {
        this.idEntidad = value;
    }

    /**
     * Gets the value of the contextoEntidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContextoEntidad() {
        return contextoEntidad;
    }

    /**
     * Sets the value of the contextoEntidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContextoEntidad(String value) {
        this.contextoEntidad = value;
    }

    /**
     * Gets the value of the fechaPeticion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaPeticion() {
        return fechaPeticion;
    }

    /**
     * Sets the value of the fechaPeticion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaPeticion(XMLGregorianCalendar value) {
        this.fechaPeticion = value;
    }

    /**
     * Gets the value of the idSistema property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdSistema() {
        return idSistema;
    }

    /**
     * Sets the value of the idSistema property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdSistema(BigInteger value) {
        this.idSistema = value;
    }

    /**
     * Gets the value of the idCanal property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdCanal() {
        return idCanal;
    }

    /**
     * Sets the value of the idCanal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdCanal(BigInteger value) {
        this.idCanal = value;
    }

    /**
     * Gets the value of the idOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdOperacion() {
        return idOperacion;
    }

    /**
     * Sets the value of the idOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdOperacion(BigInteger value) {
        this.idOperacion = value;
    }

    /**
     * Gets the value of the idServicio property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdServicio() {
        return idServicio;
    }

    /**
     * Sets the value of the idServicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdServicio(BigInteger value) {
        this.idServicio = value;
    }

}
