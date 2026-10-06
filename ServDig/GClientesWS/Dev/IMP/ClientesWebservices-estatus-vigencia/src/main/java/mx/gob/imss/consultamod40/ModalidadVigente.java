
package mx.gob.imss.consultamod40;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modalidadVigente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modalidadVigente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="modalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="registroPatronal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modalidadVigente", propOrder = {
    "modalidad",
    "registroPatronal"
})
public class ModalidadVigente {

    @XmlElementRef(name = "modalidad", type = JAXBElement.class)
    protected JAXBElement<String> modalidad;
    @XmlElementRef(name = "registroPatronal", type = JAXBElement.class)
    protected JAXBElement<String> registroPatronal;

    /**
     * Gets the value of the modalidad property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getModalidad() {
        return modalidad;
    }

    /**
     * Sets the value of the modalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setModalidad(JAXBElement<String> value) {
        this.modalidad = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the registroPatronal property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRegistroPatronal() {
        return registroPatronal;
    }

    /**
     * Sets the value of the registroPatronal property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRegistroPatronal(JAXBElement<String> value) {
        this.registroPatronal = ((JAXBElement<String> ) value);
    }

}
