
package mx.gob.imss.service.vigenciagrupofamparen.estado;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtieneVigCabGpoFamXParenEst complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtieneVigCabGpoFamXParenEst">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Mensaje" type="{http://estado.vigenciagrupofamparen.service.imss.gob.mx/}messageWSConsVigGpoFamXParenEst" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtieneVigCabGpoFamXParenEst", propOrder = {
    "mensaje"
})
public class ObtieneVigCabGpoFamXParenEst {

    @XmlElement(name = "Mensaje")
    protected MessageWSConsVigGpoFamXParenEst mensaje;

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link MessageWSConsVigGpoFamXParenEst }
     *     
     */
    public MessageWSConsVigGpoFamXParenEst getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageWSConsVigGpoFamXParenEst }
     *     
     */
    public void setMensaje(MessageWSConsVigGpoFamXParenEst value) {
        this.mensaje = value;
    }

}
