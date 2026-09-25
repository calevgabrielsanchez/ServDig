
package mx.gob.imss.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtieneVigGpoFamXListEstResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtieneVigGpoFamXListEstResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Respuesta" type="{http://services.imss.gob.mx/}respuestaServVigGpoFamXListEst" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtieneVigGpoFamXListEstResponse", propOrder = {
    "respuesta"
})
public class ObtieneVigGpoFamXListEstResponse {

    @XmlElement(name = "Respuesta")
    protected RespuestaVigGpoFamXListEst respuesta;

    /**
     * Gets the value of the respuesta property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaVigGpoFamXListEst }
     *     
     */
    public RespuestaVigGpoFamXListEst getRespuesta() {
        return respuesta;
    }

    /**
     * Sets the value of the respuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaVigGpoFamXListEst }
     *     
     */
    public void setRespuesta(RespuestaVigGpoFamXListEst value) {
        this.respuesta = value;
    }

}
