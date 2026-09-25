
package mx.gob.imss.service.vigenciaderechohab;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtieneVigXDHabResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtieneVigXDHabResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Respuesta" type="{http://vigenciaderechohab.service.imss.gob.mx/}respuestaWSConsVigXDHab" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtieneVigXDHabResponse", propOrder = {
    "respuesta"
})
public class ObtieneVigXDHabResponse {

    @XmlElement(name = "Respuesta")
    protected RespuestaWSConsVigXDHab respuesta;

    /**
     * Obtiene el valor de la propiedad respuesta.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaWSConsVigXDHab }
     *     
     */
    public RespuestaWSConsVigXDHab getRespuesta() {
        return respuesta;
    }

    /**
     * Define el valor de la propiedad respuesta.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaWSConsVigXDHab }
     *     
     */
    public void setRespuesta(RespuestaWSConsVigXDHab value) {
        this.respuesta = value;
    }

}
