
package mx.gob.imss.consulta.tramites.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for actualizaMovimientosRecientesBDTUResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="actualizaMovimientosRecientesBDTUResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Respuesta" type="{http://service.tramites.consulta.imss.gob.mx/}respuestaWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "actualizaMovimientosRecientesBDTUResponse", propOrder = {
    "respuesta"
})
public class ActualizaMovimientosRecientesBDTUResponse {

    @XmlElement(name = "Respuesta")
    protected RespuestaWS respuesta;

    /**
     * Gets the value of the respuesta property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaWS }
     *     
     */
    public RespuestaWS getRespuesta() {
        return respuesta;
    }

    /**
     * Sets the value of the respuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaWS }
     *     
     */
    public void setRespuesta(RespuestaWS value) {
        this.respuesta = value;
    }

}
