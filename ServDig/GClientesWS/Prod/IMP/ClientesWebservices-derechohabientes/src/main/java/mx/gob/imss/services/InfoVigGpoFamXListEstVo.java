
package mx.gob.imss.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoVigGpoFamXListEstVo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoVigGpoFamXListEstVo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdPersona" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveIdEstadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveIdSubestadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveIdCalidadParentesco" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoVigGpoFamXListEstVo", propOrder = {
    "cveIdPersona",
    "cveIdEstadoDerechohabiente",
    "cveIdSubestadoDerechohabiente",
    "cveIdCalidadParentesco"
})
public class InfoVigGpoFamXListEstVo {

    @XmlElementRef(name = "cveIdPersona", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdPersona;
    @XmlElementRef(name = "cveIdEstadoDerechohabiente", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdEstadoDerechohabiente;
    @XmlElementRef(name = "cveIdSubestadoDerechohabiente", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdSubestadoDerechohabiente;
    @XmlElementRef(name = "cveIdCalidadParentesco", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdCalidadParentesco;

    /**
     * Gets the value of the cveIdPersona property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveIdPersona() {
        return cveIdPersona;
    }

    /**
     * Sets the value of the cveIdPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveIdPersona(JAXBElement<Integer> value) {
        this.cveIdPersona = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the cveIdEstadoDerechohabiente property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveIdEstadoDerechohabiente() {
        return cveIdEstadoDerechohabiente;
    }

    /**
     * Sets the value of the cveIdEstadoDerechohabiente property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveIdEstadoDerechohabiente(JAXBElement<Integer> value) {
        this.cveIdEstadoDerechohabiente = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the cveIdSubestadoDerechohabiente property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveIdSubestadoDerechohabiente() {
        return cveIdSubestadoDerechohabiente;
    }

    /**
     * Sets the value of the cveIdSubestadoDerechohabiente property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveIdSubestadoDerechohabiente(JAXBElement<Integer> value) {
        this.cveIdSubestadoDerechohabiente = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the cveIdCalidadParentesco property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveIdCalidadParentesco() {
        return cveIdCalidadParentesco;
    }

    /**
     * Sets the value of the cveIdCalidadParentesco property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveIdCalidadParentesco(JAXBElement<Integer> value) {
        this.cveIdCalidadParentesco = ((JAXBElement<Integer> ) value);
    }

}
