
package mx.gob.imss.service.vigenciaderechohab;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtieneVigXDHab complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtieneVigXDHab">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Mensaje" type="{http://vigenciaderechohab.service.imss.gob.mx/}messageWSConsVigXDHab" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtieneVigXDHab", propOrder = {
    "mensaje"
})
public class ObtieneVigXDHab {

    @XmlElement(name = "Mensaje")
    protected MessageWSConsVigXDHab mensaje;

    /**
     * Obtiene el valor de la propiedad mensaje.
     * 
     * @return
     *     possible object is
     *     {@link MessageWSConsVigXDHab }
     *     
     */
    public MessageWSConsVigXDHab getMensaje() {
        return mensaje;
    }

    /**
     * Define el valor de la propiedad mensaje.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageWSConsVigXDHab }
     *     
     */
    public void setMensaje(MessageWSConsVigXDHab value) {
        this.mensaje = value;
    }

}
