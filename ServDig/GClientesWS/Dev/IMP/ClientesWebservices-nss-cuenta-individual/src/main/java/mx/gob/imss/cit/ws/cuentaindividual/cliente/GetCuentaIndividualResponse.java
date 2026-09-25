
package mx.gob.imss.cit.ws.cuentaindividual.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCuentaIndividualResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCuentaIndividualResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://cuentaIndividual.imss.gob.mx/}respuestaCuentaIndividual" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCuentaIndividualResponse", propOrder = {
    "_return"
})
public class GetCuentaIndividualResponse {

    @XmlElement(name = "return")
    protected RespuestaCuentaIndividual _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaCuentaIndividual }
     *     
     */
    public RespuestaCuentaIndividual getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaCuentaIndividual }
     *     
     */
    public void setReturn(RespuestaCuentaIndividual value) {
        this._return = value;
    }

}
