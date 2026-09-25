
package mx.gob.imss.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtieneVigenciaGpoFamEstResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtieneVigenciaGpoFamEstResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Respuesta" type="{http://services.imss.gob.mx/}respuestaServVigGpoFamEst" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtieneVigenciaGpoFamEstResponse", propOrder = {
    "respuesta"
})
public class ObtieneVigenciaGpoFamEstResponse {

    @XmlElement(name = "Respuesta")
    protected RespuestaServVigGpoFamEst respuesta;

    /**
     * Gets the value of the respuesta property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaServVigGpoFamEst }
     *     
     */
    public RespuestaServVigGpoFamEst getRespuesta() {
        return respuesta;
    }

    /**
     * Sets the value of the respuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaServVigGpoFamEst }
     *     
     */
    public void setRespuesta(RespuestaServVigGpoFamEst value) {
        this.respuesta = value;
    }

}
