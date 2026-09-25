
package mx.gob.imss.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtieneInfoCabGpoFamResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad respuesta.
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
     * Define el valor de la propiedad respuesta.
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
