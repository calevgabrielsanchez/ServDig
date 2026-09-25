
package mx.gob.imss.service.vigenciagrupofamparen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtieneVigCabGpoFamXParen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtieneVigCabGpoFamXParen">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Mensaje" type="{http://vigenciagrupofamparen.service.imss.gob.mx/}messageWSConsVigGpoFamXParen" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtieneVigCabGpoFamXParen", propOrder = {
    "mensaje"
})
public class ObtieneVigCabGpoFamXParen {

    @XmlElement(name = "Mensaje")
    protected MessageWSConsVigGpoFamXParen mensaje;

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link MessageWSConsVigGpoFamXParen }
     *     
     */
    public MessageWSConsVigGpoFamXParen getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageWSConsVigGpoFamXParen }
     *     
     */
    public void setMensaje(MessageWSConsVigGpoFamXParen value) {
        this.mensaje = value;
    }

}
