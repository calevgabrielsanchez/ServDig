
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for perfilDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="perfilDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="areaNormDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}areaNormativaDTO" minOccurs="0"/>
 *         &lt;element name="cveSsoperfilessol" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="deptoDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}departamentoDTO" minOccurs="0"/>
 *         &lt;element name="desDefault" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puestoDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}puestoDTO" minOccurs="0"/>
 *         &lt;element name="solicitudDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}solicitudDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "perfilDTO", propOrder = {
    "areaNormDTO",
    "cveSsoperfilessol",
    "deptoDTO",
    "desDefault",
    "puestoDTO",
    "solicitudDTO"
})
public class PerfilDTO {

    protected AreaNormativaDTO areaNormDTO;
    protected long cveSsoperfilessol;
    protected DepartamentoDTO deptoDTO;
    protected String desDefault;
    protected PuestoDTO puestoDTO;
    protected SolicitudDTO solicitudDTO;

    /**
     * Gets the value of the areaNormDTO property.
     * 
     * @return
     *     possible object is
     *     {@link AreaNormativaDTO }
     *     
     */
    public AreaNormativaDTO getAreaNormDTO() {
        return areaNormDTO;
    }

    /**
     * Sets the value of the areaNormDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link AreaNormativaDTO }
     *     
     */
    public void setAreaNormDTO(AreaNormativaDTO value) {
        this.areaNormDTO = value;
    }

    /**
     * Gets the value of the cveSsoperfilessol property.
     * 
     */
    public long getCveSsoperfilessol() {
        return cveSsoperfilessol;
    }

    /**
     * Sets the value of the cveSsoperfilessol property.
     * 
     */
    public void setCveSsoperfilessol(long value) {
        this.cveSsoperfilessol = value;
    }

    /**
     * Gets the value of the deptoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link DepartamentoDTO }
     *     
     */
    public DepartamentoDTO getDeptoDTO() {
        return deptoDTO;
    }

    /**
     * Sets the value of the deptoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartamentoDTO }
     *     
     */
    public void setDeptoDTO(DepartamentoDTO value) {
        this.deptoDTO = value;
    }

    /**
     * Gets the value of the desDefault property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesDefault() {
        return desDefault;
    }

    /**
     * Sets the value of the desDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesDefault(String value) {
        this.desDefault = value;
    }

    /**
     * Gets the value of the puestoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link PuestoDTO }
     *     
     */
    public PuestoDTO getPuestoDTO() {
        return puestoDTO;
    }

    /**
     * Sets the value of the puestoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link PuestoDTO }
     *     
     */
    public void setPuestoDTO(PuestoDTO value) {
        this.puestoDTO = value;
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

}
