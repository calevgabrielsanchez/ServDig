
package mx.gob.imss.ws.pagos.ivro;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pagoPorPeriodo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pagoPorPeriodo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Mensaje" type="{http://ivro.pagos.ws.imss.gob.mx/}msgWSPagosIvroByPeriodo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagoPorPeriodo", propOrder = {
    "mensaje"
})
public class PagoPorPeriodo {

    @XmlElement(name = "Mensaje")
    protected MsgWSPagosIvroByPeriodo mensaje;

    /**
     * Gets the value of the mensaje property.
     * 
     * @return
     *     possible object is
     *     {@link MsgWSPagosIvroByPeriodo }
     *     
     */
    public MsgWSPagosIvroByPeriodo getMensaje() {
        return mensaje;
    }

    /**
     * Sets the value of the mensaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link MsgWSPagosIvroByPeriodo }
     *     
     */
    public void setMensaje(MsgWSPagosIvroByPeriodo value) {
        this.mensaje = value;
    }

}
