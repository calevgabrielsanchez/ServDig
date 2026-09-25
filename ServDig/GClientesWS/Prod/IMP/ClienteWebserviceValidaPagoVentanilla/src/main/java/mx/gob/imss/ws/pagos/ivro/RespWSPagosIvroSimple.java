
package mx.gob.imss.ws.pagos.ivro;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RespWSPagosIvroSimple complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RespWSPagosIvroSimple">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CODIGO_ERROR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MENSAJE_ERROR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NSS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PERIODO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MODALIDAD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FECHA_INICIO_ASEGURAMIENTO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FECHA_FIN_ASEGURAMIENTO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FECHA_PAGO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IND_PAGO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IND_PAGOSCOMPLETOS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RespWSPagosIvroSimple", propOrder = {
    "codigoerror",
    "mensajeerror",
    "nss",
    "periodo",
    "modalidad",
    "fechainicioaseguramiento",
    "fechafinaseguramiento",
    "fechapago",
    "indpago",
    "indpagoscompletos"
})
public class RespWSPagosIvroSimple {

    @XmlElement(name = "CODIGO_ERROR", required = true)
    protected String codigoerror;
    @XmlElement(name = "MENSAJE_ERROR", required = true)
    protected String mensajeerror;
    @XmlElement(name = "NSS", required = true, nillable = true)
    protected String nss;
    @XmlElement(name = "PERIODO", required = true, nillable = true)
    protected String periodo;
    @XmlElement(name = "MODALIDAD", required = true, nillable = true)
    protected String modalidad;
    @XmlElement(name = "FECHA_INICIO_ASEGURAMIENTO", required = true, nillable = true)
    protected String fechainicioaseguramiento;
    @XmlElement(name = "FECHA_FIN_ASEGURAMIENTO", required = true, nillable = true)
    protected String fechafinaseguramiento;
    @XmlElement(name = "FECHA_PAGO", required = true, nillable = true)
    protected String fechapago;
    @XmlElement(name = "IND_PAGO", required = true, nillable = true)
    protected String indpago;
    @XmlElement(name = "IND_PAGOSCOMPLETOS", required = true, nillable = true)
    protected String indpagoscompletos;

    /**
     * Gets the value of the codigoerror property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOERROR() {
        return codigoerror;
    }

    /**
     * Sets the value of the codigoerror property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOERROR(String value) {
        this.codigoerror = value;
    }

    /**
     * Gets the value of the mensajeerror property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMENSAJEERROR() {
        return mensajeerror;
    }

    /**
     * Sets the value of the mensajeerror property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMENSAJEERROR(String value) {
        this.mensajeerror = value;
    }

    /**
     * Gets the value of the nss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNSS() {
        return nss;
    }

    /**
     * Sets the value of the nss property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNSS(String value) {
        this.nss = value;
    }

    /**
     * Gets the value of the periodo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPERIODO() {
        return periodo;
    }

    /**
     * Sets the value of the periodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPERIODO(String value) {
        this.periodo = value;
    }

    /**
     * Gets the value of the modalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODALIDAD() {
        return modalidad;
    }

    /**
     * Sets the value of the modalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODALIDAD(String value) {
        this.modalidad = value;
    }

    /**
     * Gets the value of the fechainicioaseguramiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAINICIOASEGURAMIENTO() {
        return fechainicioaseguramiento;
    }

    /**
     * Sets the value of the fechainicioaseguramiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAINICIOASEGURAMIENTO(String value) {
        this.fechainicioaseguramiento = value;
    }

    /**
     * Gets the value of the fechafinaseguramiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAFINASEGURAMIENTO() {
        return fechafinaseguramiento;
    }

    /**
     * Sets the value of the fechafinaseguramiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAFINASEGURAMIENTO(String value) {
        this.fechafinaseguramiento = value;
    }

    /**
     * Gets the value of the fechapago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAPAGO() {
        return fechapago;
    }

    /**
     * Sets the value of the fechapago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAPAGO(String value) {
        this.fechapago = value;
    }

    /**
     * Gets the value of the indpago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDPAGO() {
        return indpago;
    }

    /**
     * Sets the value of the indpago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDPAGO(String value) {
        this.indpago = value;
    }

    /**
     * Gets the value of the indpagoscompletos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDPAGOSCOMPLETOS() {
        return indpagoscompletos;
    }

    /**
     * Sets the value of the indpagoscompletos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDPAGOSCOMPLETOS(String value) {
        this.indpagoscompletos = value;
    }

}
