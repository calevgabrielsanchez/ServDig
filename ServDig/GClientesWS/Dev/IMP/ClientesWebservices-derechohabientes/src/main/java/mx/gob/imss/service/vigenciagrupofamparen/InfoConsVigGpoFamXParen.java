
package mx.gob.imss.service.vigenciagrupofamparen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoConsVigGpoFamXParen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoConsVigGpoFamXParen">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdPersona" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveEstadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveSubestadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
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
@XmlType(name = "InfoConsVigGpoFamXParen", propOrder = {
    "cveIdPersona",
    "cveEstadoDerechohabiente",
    "cveSubestadoDerechohabiente",
    "cveIdCalidadParentesco"
})
public class InfoConsVigGpoFamXParen {

    @XmlElementRef(name = "cveIdPersona", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdPersona;
    @XmlElementRef(name = "cveEstadoDerechohabiente", type = JAXBElement.class)
    protected JAXBElement<Integer> cveEstadoDerechohabiente;
    @XmlElementRef(name = "cveSubestadoDerechohabiente", type = JAXBElement.class)
    protected JAXBElement<Integer> cveSubestadoDerechohabiente;
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
     * Gets the value of the cveEstadoDerechohabiente property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveEstadoDerechohabiente() {
        return cveEstadoDerechohabiente;
    }

    /**
     * Sets the value of the cveEstadoDerechohabiente property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveEstadoDerechohabiente(JAXBElement<Integer> value) {
        this.cveEstadoDerechohabiente = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the cveSubestadoDerechohabiente property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveSubestadoDerechohabiente() {
        return cveSubestadoDerechohabiente;
    }

    /**
     * Sets the value of the cveSubestadoDerechohabiente property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveSubestadoDerechohabiente(JAXBElement<Integer> value) {
        this.cveSubestadoDerechohabiente = ((JAXBElement<Integer> ) value);
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
