
package mx.gob.imss.vigenciagrupofamte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtieneInfoCabGpoFamResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtieneInfoCabGpoFamResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Respuesta" type="{http://service.imss.gob.mx/}respuestaWSConsInfoCabGpoFam" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtieneInfoCabGpoFamResponse", propOrder = {
    "respuesta"
})
public class ObtieneInfoCabGpoFamResponse {

    @XmlElement(name = "Respuesta")
    protected RespuestaWSConsInfoCabGpoFam respuesta;

    /**
     * Gets the value of the respuesta property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaWSConsInfoCabGpoFam }
     *     
     */
    public RespuestaWSConsInfoCabGpoFam getRespuesta() {
        return respuesta;
    }

    /**
     * Sets the value of the respuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaWSConsInfoCabGpoFam }
     *     
     */
    public void setRespuesta(RespuestaWSConsInfoCabGpoFam value) {
        this.respuesta = value;
    }

}
