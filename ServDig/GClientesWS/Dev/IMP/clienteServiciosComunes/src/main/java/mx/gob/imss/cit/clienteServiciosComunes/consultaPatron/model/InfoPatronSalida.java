package mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoPatronSalida complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoPatronSalida">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codigoPostal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="correo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="curp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveDelegacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveMunicipio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveSubDelegacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveTipoMov" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="domicilio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="exito" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="infoAsociadoSalida" type="{java:vo}InfoPatronAsociadoSalida" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="localidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="razonSocial" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "InfoPatronSalida", propOrder = {
    "codigo",
    "codigoPostal",
    "correo",
    "curp",
    "cveDelegacion",
    "cveMunicipio",
    "cveSubDelegacion",
    "cveTipoMov",
    "descripcion",
    "domicilio",
    "exito",
    "infoAsociadoSalida",
    "localidad",
    "razonSocial",
    "rfc",
    "tipoRP"
})
public class InfoPatronSalida {

    protected int codigo;
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
    protected String descripcion;
    @XmlElement(required = true, nillable = true)
    protected String domicilio;
    protected int exito;
    @XmlElement(nillable = true)
    protected List<InfoPatronAsociadoSalida> infoAsociadoSalida;
    @XmlElement(required = true, nillable = true)
    protected String localidad;
    @XmlElement(required = true, nillable = true)
    protected String razonSocial;
    @XmlElement(required = true, nillable = true)
    protected String rfc;
    @XmlElement(required = true, nillable = true)
    protected String tipoRP;

    /**
     * Gets the value of the codigo property.
     * 
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     */
    public void setCodigo(int value) {
        this.codigo = value;
    }

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
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
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
     * Gets the value of the exito property.
     * 
     */
    public int getExito() {
        return exito;
    }

    /**
     * Sets the value of the exito property.
     * 
     */
    public void setExito(int value) {
        this.exito = value;
    }

    /**
     * Gets the value of the infoAsociadoSalida property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infoAsociadoSalida property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoAsociadoSalida().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfoPatronAsociadoSalida }
     * 
     * 
     */
    public List<InfoPatronAsociadoSalida> getInfoAsociadoSalida() {
        if (infoAsociadoSalida == null) {
            infoAsociadoSalida = new ArrayList<InfoPatronAsociadoSalida>();
        }
        return this.infoAsociadoSalida;
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
