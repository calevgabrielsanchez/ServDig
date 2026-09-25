
package mx.gob.imss.service.vigenciagrupofamparen.estado;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtieneVigCabGpoFamXParenEstResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtieneVigCabGpoFamXParenEstResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Respuesta" type="{http://estado.vigenciagrupofamparen.service.imss.gob.mx/}respuestaWSConsVigGpoFamXParenEst" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtieneVigCabGpoFamXParenEstResponse", propOrder = {
    "respuesta"
})
public class ObtieneVigCabGpoFamXParenEstResponse {

    @XmlElement(name = "Respuesta")
    protected RespuestaWSConsVigGpoFamXParenEst respuesta;

    /**
     * Gets the value of the respuesta property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaWSConsVigGpoFamXParenEst }
     *     
     */
    public RespuestaWSConsVigGpoFamXParenEst getRespuesta() {
        return respuesta;
    }

    /**
     * Sets the value of the respuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaWSConsVigGpoFamXParenEst }
     *     
     */
    public void setRespuesta(RespuestaWSConsVigGpoFamXParenEst value) {
        this.respuesta = value;
    }

}
