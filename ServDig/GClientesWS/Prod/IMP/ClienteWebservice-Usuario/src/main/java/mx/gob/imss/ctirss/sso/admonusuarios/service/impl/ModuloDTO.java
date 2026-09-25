
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for moduloDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="moduloDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="areaNormDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}areaNormativaDTO" minOccurs="0"/>
 *         &lt;element name="cveAccesoModulo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="cveIdModulo" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="desModulo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dptoDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}departamentoDTO" minOccurs="0"/>
 *         &lt;element name="estatusDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}estatusDTO" minOccurs="0"/>
 *         &lt;element name="fecRegistroActualizado" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fecRegistroAlta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fecRegistroBaja" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="nuevoReg" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "moduloDTO", propOrder = {
    "areaNormDTO",
    "cveAccesoModulo",
    "cveIdModulo",
    "desModulo",
    "dptoDTO",
    "estatusDTO",
    "fecRegistroActualizado",
    "fecRegistroAlta",
    "fecRegistroBaja",
    "nuevoReg"
})
public class ModuloDTO {

    protected AreaNormativaDTO areaNormDTO;
    protected long cveAccesoModulo;
    protected long cveIdModulo;
    protected String desModulo;
    protected DepartamentoDTO dptoDTO;
    protected EstatusDTO estatusDTO;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroActualizado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroAlta;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroBaja;
    protected boolean nuevoReg;

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
     * Gets the value of the cveAccesoModulo property.
     * 
     */
    public long getCveAccesoModulo() {
        return cveAccesoModulo;
    }

    /**
     * Sets the value of the cveAccesoModulo property.
     * 
     */
    public void setCveAccesoModulo(long value) {
        this.cveAccesoModulo = value;
    }

    /**
     * Gets the value of the cveIdModulo property.
     * 
     */
    public long getCveIdModulo() {
        return cveIdModulo;
    }

    /**
     * Sets the value of the cveIdModulo property.
     * 
     */
    public void setCveIdModulo(long value) {
        this.cveIdModulo = value;
    }

    /**
     * Gets the value of the desModulo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesModulo() {
        return desModulo;
    }

    /**
     * Sets the value of the desModulo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesModulo(String value) {
        this.desModulo = value;
    }

    /**
     * Gets the value of the dptoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link DepartamentoDTO }
     *     
     */
    public DepartamentoDTO getDptoDTO() {
        return dptoDTO;
    }

    /**
     * Sets the value of the dptoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartamentoDTO }
     *     
     */
    public void setDptoDTO(DepartamentoDTO value) {
        this.dptoDTO = value;
    }

    /**
     * Gets the value of the estatusDTO property.
     * 
     * @return
     *     possible object is
     *     {@link EstatusDTO }
     *     
     */
    public EstatusDTO getEstatusDTO() {
        return estatusDTO;
    }

    /**
     * Sets the value of the estatusDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstatusDTO }
     *     
     */
    public void setEstatusDTO(EstatusDTO value) {
        this.estatusDTO = value;
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
     * Gets the value of the nuevoReg property.
     * 
     */
    public boolean isNuevoReg() {
        return nuevoReg;
    }

    /**
     * Sets the value of the nuevoReg property.
     * 
     */
    public void setNuevoReg(boolean value) {
        this.nuevoReg = value;
    }

}
