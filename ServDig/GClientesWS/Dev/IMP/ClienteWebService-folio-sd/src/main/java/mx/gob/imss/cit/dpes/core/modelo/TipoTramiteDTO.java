
package mx.gob.imss.cit.dpes.core.modelo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TipoTramiteDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TipoTramiteDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CveIdTipoTramite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DesTipoTramite" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RefHomoclave" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FecRegistroActualizado" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecRegistroAlta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecRegistroBaja" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="RefGuiaDetallada" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="RefGuiaRapida" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="RefSigla" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IndTipoConclusion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="IndTipoTramite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoTramiteDTO", propOrder = {
    "cveIdTipoTramite",
    "desTipoTramite",
    "refHomoclave",
    "fecRegistroActualizado",
    "fecRegistroAlta",
    "fecRegistroBaja",
    "refGuiaDetallada",
    "refGuiaRapida",
    "refSigla",
    "indTipoConclusion",
    "indTipoTramite"
})
public class TipoTramiteDTO {

    @XmlElement(name = "CveIdTipoTramite", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdTipoTramite;
    @XmlElement(name = "DesTipoTramite", required = true, nillable = true)
    protected String desTipoTramite;
    @XmlElement(name = "RefHomoclave", required = true, nillable = true)
    protected String refHomoclave;
    @XmlElement(name = "FecRegistroActualizado", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroActualizado;
    @XmlElement(name = "FecRegistroAlta", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroAlta;
    @XmlElement(name = "FecRegistroBaja", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroBaja;
    @XmlElement(name = "RefGuiaDetallada")
    protected byte[] refGuiaDetallada;
    @XmlElement(name = "RefGuiaRapida")
    protected byte[] refGuiaRapida;
    @XmlElement(name = "RefSigla", required = true, nillable = true)
    protected String refSigla;
    @XmlElement(name = "IndTipoConclusion", required = true, type = Integer.class, nillable = true)
    protected Integer indTipoConclusion;
    @XmlElement(name = "IndTipoTramite", required = true, type = Integer.class, nillable = true)
    protected Integer indTipoTramite;

    /**
     * Gets the value of the cveIdTipoTramite property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdTipoTramite() {
        return cveIdTipoTramite;
    }

    /**
     * Sets the value of the cveIdTipoTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdTipoTramite(Integer value) {
        this.cveIdTipoTramite = value;
    }

    /**
     * Gets the value of the desTipoTramite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesTipoTramite() {
        return desTipoTramite;
    }

    /**
     * Sets the value of the desTipoTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesTipoTramite(String value) {
        this.desTipoTramite = value;
    }

    /**
     * Gets the value of the refHomoclave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefHomoclave() {
        return refHomoclave;
    }

    /**
     * Sets the value of the refHomoclave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefHomoclave(String value) {
        this.refHomoclave = value;
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

    /**
     * Gets the value of the refGuiaDetallada property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getRefGuiaDetallada() {
        return refGuiaDetallada;
    }

    /**
     * Sets the value of the refGuiaDetallada property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setRefGuiaDetallada(byte[] value) {
        this.refGuiaDetallada = ((byte[]) value);
    }

    /**
     * Gets the value of the refGuiaRapida property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getRefGuiaRapida() {
        return refGuiaRapida;
    }

    /**
     * Sets the value of the refGuiaRapida property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setRefGuiaRapida(byte[] value) {
        this.refGuiaRapida = ((byte[]) value);
    }

    /**
     * Gets the value of the refSigla property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefSigla() {
        return refSigla;
    }

    /**
     * Sets the value of the refSigla property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefSigla(String value) {
        this.refSigla = value;
    }

    /**
     * Gets the value of the indTipoConclusion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndTipoConclusion() {
        return indTipoConclusion;
    }

    /**
     * Sets the value of the indTipoConclusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndTipoConclusion(Integer value) {
        this.indTipoConclusion = value;
    }

    /**
     * Gets the value of the indTipoTramite property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndTipoTramite() {
        return indTipoTramite;
    }

    /**
     * Sets the value of the indTipoTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndTipoTramite(Integer value) {
        this.indTipoTramite = value;
    }

}
