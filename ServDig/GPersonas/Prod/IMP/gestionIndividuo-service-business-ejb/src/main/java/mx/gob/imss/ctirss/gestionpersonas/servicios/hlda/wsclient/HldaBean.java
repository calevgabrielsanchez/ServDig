
package mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HldaBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HldaBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Mensaje" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Nss" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Pantalla" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HldaBean", namespace = "java:mx.gob.imss.ws.hlda.business.bean", propOrder = {
    "mensaje",
    "nss",
    "pantalla"
})
public class HldaBean {

    @XmlElement(name = "Mensaje", required = true, nillable = true)
    protected String mensaje;
    @XmlElement(name = "Nss", required = true, nillable = true)
    protected String nss;
    @XmlElement(name = "Pantalla", required = true, nillable = true)
    protected String pantalla;

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensaje(String value) {
        this.mensaje = value;
    }

    /**
     * Gets the value of the nss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNss() {
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
    public void setNss(String value) {
        this.nss = value;
    }

    /**
     * Gets the value of the pantalla property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPantalla() {
        return pantalla;
    }

    /**
     * Sets the value of the pantalla property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPantalla(String value) {
        this.pantalla = value;
    }

}
