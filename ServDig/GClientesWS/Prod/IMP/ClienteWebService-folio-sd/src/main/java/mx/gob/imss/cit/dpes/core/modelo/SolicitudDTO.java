
package mx.gob.imss.cit.dpes.core.modelo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SolicitudDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SolicitudDTO">
 *   &lt;complexContent>
 *     &lt;extension base="{java:mx.gob.imss.cit.dpes.core.modelo.externo}AbstractResponseExterno">
 *       &lt;sequence>
 *         &lt;element name="CveIdSolicitud" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FecCita" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecRegistroActualizado" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecRegistroAlta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecRegistroBaja" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecSolicitud" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="RefFolio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CveIdUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CveIdPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RefObservacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FecConclusion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="FecPresentacion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="TipoSolicitudDTO" type="{java:mx.gob.imss.cit.dpes.core.modelo.dto}TipoSolicitudDTO"/>
 *         &lt;element name="EstadoSolicitudDTO" type="{java:mx.gob.imss.cit.dpes.core.modelo.dto}EstadoSolicitudDTO"/>
 *         &lt;element name="RazonCancelacionDTO" type="{java:mx.gob.imss.cit.dpes.core.modelo.dto}RazonCancelacionDTO"/>
 *         &lt;element name="CveIdUmf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CveIdTurno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CveIdSubdelegacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CveIdOrigenSolicitud" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ListTramiteDTOs" type="{java:mx.gob.imss.cit.dpes.core.modelo.dto}TramiteDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SolicitudDTO", propOrder = {
    "cveIdSolicitud",
    "fecCita",
    "fecRegistroActualizado",
    "fecRegistroAlta",
    "fecRegistroBaja",
    "fecSolicitud",
    "refFolio",
    "cveIdUsuario",
    "cveIdPersona",
    "refObservacion",
    "fecConclusion",
    "fecPresentacion",
    "tipoSolicitudDTO",
    "estadoSolicitudDTO",
    "razonCancelacionDTO",
    "cveIdUmf",
    "cveIdTurno",
    "cveIdSubdelegacion",
    "cveIdOrigenSolicitud",
    "listTramiteDTOs"
})
public class SolicitudDTO
    extends AbstractResponseExterno
{

    @XmlElement(name = "CveIdSolicitud", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdSolicitud;
    @XmlElement(name = "FecCita", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecCita;
    @XmlElement(name = "FecRegistroActualizado", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroActualizado;
    @XmlElement(name = "FecRegistroAlta", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroAlta;
    @XmlElement(name = "FecRegistroBaja", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroBaja;
    @XmlElement(name = "FecSolicitud", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecSolicitud;
    @XmlElement(name = "RefFolio", required = true, nillable = true)
    protected String refFolio;
    @XmlElement(name = "CveIdUsuario", required = true, nillable = true)
    protected String cveIdUsuario;
    @XmlElement(name = "CveIdPersona", required = true, nillable = true)
    protected String cveIdPersona;
    @XmlElement(name = "RefObservacion", required = true, nillable = true)
    protected String refObservacion;
    @XmlElement(name = "FecConclusion", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecConclusion;
    @XmlElement(name = "FecPresentacion", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecPresentacion;
    @XmlElement(name = "TipoSolicitudDTO", required = true, nillable = true)
    protected TipoSolicitudDTO tipoSolicitudDTO;
    @XmlElement(name = "EstadoSolicitudDTO", required = true, nillable = true)
    protected EstadoSolicitudDTO estadoSolicitudDTO;
    @XmlElement(name = "RazonCancelacionDTO", required = true, nillable = true)
    protected RazonCancelacionDTO razonCancelacionDTO;
    @XmlElement(name = "CveIdUmf", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdUmf;
    @XmlElement(name = "CveIdTurno", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdTurno;
    @XmlElement(name = "CveIdSubdelegacion", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdSubdelegacion;
    @XmlElement(name = "CveIdOrigenSolicitud", required = true, type = Integer.class, nillable = true)
    protected Integer cveIdOrigenSolicitud;
    @XmlElement(name = "ListTramiteDTOs", nillable = true)
    protected List<TramiteDTO> listTramiteDTOs;

    /**
     * Gets the value of the cveIdSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdSolicitud() {
        return cveIdSolicitud;
    }

    /**
     * Sets the value of the cveIdSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdSolicitud(Integer value) {
        this.cveIdSolicitud = value;
    }

    /**
     * Gets the value of the fecCita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecCita() {
        return fecCita;
    }

    /**
     * Sets the value of the fecCita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecCita(XMLGregorianCalendar value) {
        this.fecCita = value;
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
     * Gets the value of the fecSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecSolicitud() {
        return fecSolicitud;
    }

    /**
     * Sets the value of the fecSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecSolicitud(XMLGregorianCalendar value) {
        this.fecSolicitud = value;
    }

    /**
     * Gets the value of the refFolio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefFolio() {
        return refFolio;
    }

    /**
     * Sets the value of the refFolio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefFolio(String value) {
        this.refFolio = value;
    }

    /**
     * Gets the value of the cveIdUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveIdUsuario() {
        return cveIdUsuario;
    }

    /**
     * Sets the value of the cveIdUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveIdUsuario(String value) {
        this.cveIdUsuario = value;
    }

    /**
     * Gets the value of the cveIdPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveIdPersona() {
        return cveIdPersona;
    }

    /**
     * Sets the value of the cveIdPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveIdPersona(String value) {
        this.cveIdPersona = value;
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
     * Gets the value of the tipoSolicitudDTO property.
     * 
     * @return
     *     possible object is
     *     {@link TipoSolicitudDTO }
     *     
     */
    public TipoSolicitudDTO getTipoSolicitudDTO() {
        return tipoSolicitudDTO;
    }

    /**
     * Sets the value of the tipoSolicitudDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoSolicitudDTO }
     *     
     */
    public void setTipoSolicitudDTO(TipoSolicitudDTO value) {
        this.tipoSolicitudDTO = value;
    }

    /**
     * Gets the value of the estadoSolicitudDTO property.
     * 
     * @return
     *     possible object is
     *     {@link EstadoSolicitudDTO }
     *     
     */
    public EstadoSolicitudDTO getEstadoSolicitudDTO() {
        return estadoSolicitudDTO;
    }

    /**
     * Sets the value of the estadoSolicitudDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoSolicitudDTO }
     *     
     */
    public void setEstadoSolicitudDTO(EstadoSolicitudDTO value) {
        this.estadoSolicitudDTO = value;
    }

    /**
     * Gets the value of the razonCancelacionDTO property.
     * 
     * @return
     *     possible object is
     *     {@link RazonCancelacionDTO }
     *     
     */
    public RazonCancelacionDTO getRazonCancelacionDTO() {
        return razonCancelacionDTO;
    }

    /**
     * Sets the value of the razonCancelacionDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link RazonCancelacionDTO }
     *     
     */
    public void setRazonCancelacionDTO(RazonCancelacionDTO value) {
        this.razonCancelacionDTO = value;
    }

    /**
     * Gets the value of the cveIdUmf property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdUmf() {
        return cveIdUmf;
    }

    /**
     * Sets the value of the cveIdUmf property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdUmf(Integer value) {
        this.cveIdUmf = value;
    }

    /**
     * Gets the value of the cveIdTurno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdTurno() {
        return cveIdTurno;
    }

    /**
     * Sets the value of the cveIdTurno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdTurno(Integer value) {
        this.cveIdTurno = value;
    }

    /**
     * Gets the value of the cveIdSubdelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdSubdelegacion() {
        return cveIdSubdelegacion;
    }

    /**
     * Sets the value of the cveIdSubdelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdSubdelegacion(Integer value) {
        this.cveIdSubdelegacion = value;
    }

    /**
     * Gets the value of the cveIdOrigenSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdOrigenSolicitud() {
        return cveIdOrigenSolicitud;
    }

    /**
     * Sets the value of the cveIdOrigenSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdOrigenSolicitud(Integer value) {
        this.cveIdOrigenSolicitud = value;
    }

    /**
     * Gets the value of the listTramiteDTOs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listTramiteDTOs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListTramiteDTOs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TramiteDTO }
     * 
     * 
     */
    public List<TramiteDTO> getListTramiteDTOs() {
        if (listTramiteDTOs == null) {
            listTramiteDTOs = new ArrayList<TramiteDTO>();
        }
        return this.listTramiteDTOs;
    }

}
