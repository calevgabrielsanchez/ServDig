
package mx.gob.imss.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtieneVigGpoFamXEstSubResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtieneVigGpoFamXEstSubResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Respuesta" type="{http://services.imss.gob.mx/}respuestaServVigGpoFamXEstSub" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtieneVigGpoFamXEstSubResponse", propOrder = {
    "respuesta"
})
public class ObtieneVigGpoFamXEstSubResponse {

    @XmlElement(name = "Respuesta")
    protected RespuestaVigGpoFamXEstSub respuesta;

    /**
     * Gets the value of the respuesta property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaVigGpoFamXEstSub }
     *     
     */
    public RespuestaVigGpoFamXEstSub getRespuesta() {
        return respuesta;
    }

    /**
     * Sets the value of the respuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaVigGpoFamXEstSub }
     *     
     */
    public void setRespuesta(RespuestaVigGpoFamXEstSub value) {
        this.respuesta = value;
    }

}
