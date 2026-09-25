
package mx.gob.imss.cit.dpes.core.modelo;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TramiteDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TramiteDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CveIdTramite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FecRegistroActualizado" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecRegistroAlta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecRegistroBaja" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecTramite" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IndResultado" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RefObservacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TipoTramiteDTO" type="{java:mx.gob.imss.cit.dpes.core.modelo.dto}TipoTramiteDTO"/>
 *         &lt;element name="SolicitudDTO" type="{java:mx.gob.imss.cit.dpes.core.modelo.dto}SolicitudDTO"/>
 *         &lt;element name="FecPresentacion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecEfecto" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecConclusion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IndRatificado" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="NumSecNotaria" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RefSelloDigital" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RefCadenaOriginal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CveIdTspiNmpsSppm" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="RefUrlBovedaDoc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CveIdRazonResultado" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CveIdEstadoTramite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TramiteDTO", propOrder = {
    "cveIdTramite",
    "fecRegistroActualizado",
    "fecRegistroAlta",
    "fecRegistroBaja",
    "fecTramite",
    "indResultado",
    "refObservacion",
    "tipoTramiteDTO",
    "solicitudDTO",
    "fecPresentacion",
    "fecEfecto",
    "fecConclusion",
    "indRatificado",
    "numSecNotaria",
    "refSelloDigital",
    "refCadenaOriginal",
    "cveIdTspiNmpsSppm",
    "refUrlBovedaDoc",
    "cveIdRazonResultado",
    "cveIdEstadoTramite"
})
public class TramiteDTO {

    @XmlElement(name = "CveIdTramite", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdTramite;
    @XmlElement(name = "FecRegistroActualizado", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroActualizado;
    @XmlElement(name = "FecRegistroAlta", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroAlta;
    @XmlElement(name = "FecRegistroBaja", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroBaja;
    @XmlElement(name = "FecTramite", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecTramite;
    @XmlElement(name = "IndResultado", required = true, nillable = true)
    protected BigDecimal indResultado;
    @XmlElement(name = "RefObservacion", required = true, nillable = true)
    protected String refObservacion;
    @XmlElement(name = "TipoTramiteDTO", required = true, nillable = true)
    protected TipoTramiteDTO tipoTramiteDTO;
    @XmlElement(name = "SolicitudDTO", required = true, nillable = true)
    protected SolicitudDTO solicitudDTO;
    @XmlElement(name = "FecPresentacion", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecPresentacion;
    @XmlElement(name = "FecEfecto", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecEfecto;
    @XmlElement(name = "FecConclusion", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecConclusion;
    @XmlElement(name = "IndRatificado", required = true, type = Boolean.class, nillable = true)
    protected Boolean indRatificado;
    @XmlElement(name = "NumSecNotaria", required = true, nillable = true)
    protected String numSecNotaria;
    @XmlElement(name = "RefSelloDigital", required = true, nillable = true)
    protected String refSelloDigital;
    @XmlElement(name = "RefCadenaOriginal", required = true, nillable = true)
    protected String refCadenaOriginal;
    @XmlElement(name = "CveIdTspiNmpsSppm", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdTspiNmpsSppm;
    @XmlElement(name = "RefUrlBovedaDoc", required = true, nillable = true)
    protected String refUrlBovedaDoc;
    @XmlElement(name = "CveIdRazonResultado", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdRazonResultado;
    @XmlElement(name = "CveIdEstadoTramite", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdEstadoTramite;

    /**
     * Gets the value of the cveIdTramite property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdTramite() {
        return cveIdTramite;
    }

    /**
     * Sets the value of the cveIdTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdTramite(Integer value) {
        this.cveIdTramite = value;
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
     * Gets the value of the fecTramite property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecTramite() {
        return fecTramite;
    }

    /**
     * Sets the value of the fecTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecTramite(XMLGregorianCalendar value) {
        this.fecTramite = value;
    }

    /**
     * Gets the value of the indResultado property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIndResultado() {
        return indResultado;
    }

    /**
     * Sets the value of the indResultado property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIndResultado(BigDecimal value) {
        this.indResultado = value;
    }

    /**
     * Gets the value of the refObservacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefObservacion() {
        return refObservacion;
    }

    /**
     * Sets the value of the refObservacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefObservacion(String value) {
        this.refObservacion = value;
    }

    /**
     * Gets the value of the tipoTramiteDTO property.
     * 
     * @return
     *     possible object is
     *     {@link TipoTramiteDTO }
     *     
     */
    public TipoTramiteDTO getTipoTramiteDTO() {
        return tipoTramiteDTO;
    }

    /**
     * Sets the value of the tipoTramiteDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoTramiteDTO }
     *     
     */
    public void setTipoTramiteDTO(TipoTramiteDTO value) {
        this.tipoTramiteDTO = value;
    }

    /**
     * Gets the value of the solicitudDTO property.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudDTO }
     *     
     */
    public SolicitudDTO getSolicitudDTO() {
        return solicitudDTO;
    }

    /**
     * Sets the value of the solicitudDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudDTO }
     *     
     */
    public void setSolicitudDTO(SolicitudDTO value) {
        this.solicitudDTO = value;
    }

    /**
     * Gets the value of the fecPresentacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecPresentacion() {
        return fecPresentacion;
    }

    /**
     * Sets the value of the fecPresentacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecPresentacion(XMLGregorianCalendar value) {
        this.fecPresentacion = value;
    }

    /**
     * Gets the value of the fecEfecto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecEfecto() {
        return fecEfecto;
    }

    /**
     * Sets the value of the fecEfecto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecEfecto(XMLGregorianCalendar value) {
        this.fecEfecto = value;
    }

    /**
     * Gets the value of the fecConclusion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecConclusion() {
        return fecConclusion;
    }

    /**
     * Sets the value of the fecConclusion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecConclusion(XMLGregorianCalendar value) {
        this.fecConclusion = value;
    }

    /**
     * Gets the value of the indRatificado property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIndRatificado() {
        return indRatificado;
    }

    /**
     * Sets the value of the indRatificado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIndRatificado(Boolean value) {
        this.indRatificado = value;
    }

    /**
     * Gets the value of the numSecNotaria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumSecNotaria() {
        return numSecNotaria;
    }

    /**
     * Sets the value of the numSecNotaria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumSecNotaria(String value) {
        this.numSecNotaria = value;
    }

    /**
     * Gets the value of the refSelloDigital property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefSelloDigital() {
        return refSelloDigital;
    }

    /**
     * Sets the value of the refSelloDigital property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefSelloDigital(String value) {
        this.refSelloDigital = value;
    }

    /**
     * Gets the value of the refCadenaOriginal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefCadenaOriginal() {
        return refCadenaOriginal;
    }

    /**
     * Sets the value of the refCadenaOriginal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefCadenaOriginal(String value) {
        this.refCadenaOriginal = value;
    }

    /**
     * Gets the value of the cveIdTspiNmpsSppm property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdTspiNmpsSppm() {
        return cveIdTspiNmpsSppm;
    }

    /**
     * Sets the value of the cveIdTspiNmpsSppm property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdTspiNmpsSppm(Integer value) {
        this.cveIdTspiNmpsSppm = value;
    }

    /**
     * Gets the value of the refUrlBovedaDoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefUrlBovedaDoc() {
        return refUrlBovedaDoc;
    }

    /**
     * Sets the value of the refUrlBovedaDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefUrlBovedaDoc(String value) {
        this.refUrlBovedaDoc = value;
    }

    /**
     * Gets the value of the cveIdRazonResultado property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdRazonResultado() {
        return cveIdRazonResultado;
    }

    /**
     * Sets the value of the cveIdRazonResultado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdRazonResultado(Integer value) {
        this.cveIdRazonResultado = value;
    }

    /**
     * Gets the value of the cveIdEstadoTramite property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdEstadoTramite() {
        return cveIdEstadoTramite;
    }

    /**
     * Sets the value of the cveIdEstadoTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdEstadoTramite(Integer value) {
        this.cveIdEstadoTramite = value;
    }

}
