
package mx.gob.imss.ws.pagos.ivro;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pagoPorFechasResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pagoPorFechasResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Respuesta" type="{http://ivro.pagos.ws.imss.gob.mx/}RespWSPagosIvroSimple" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagoPorFechasResponse", propOrder = {
    "respuesta"
})
public class PagoPorFechasResponse {

    @XmlElement(name = "Respuesta")
    protected RespWSPagosIvroSimple respuesta;

    /**
     * Gets the value of the respuesta property.
     * 
     * @return
     *     possible object is
     *     {@link RespWSPagosIvroSimple }
     *     
     */
    public RespWSPagosIvroSimple getRespuesta() {
        return respuesta;
    }

    /**
     * Sets the value of the respuesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespWSPagosIvroSimple }
     *     
     */
    public void setRespuesta(RespWSPagosIvroSimple value) {
        this.respuesta = value;
    }

}
