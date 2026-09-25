
package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyValueType", propOrder = {
    "rsaKeyValue"
})
public class KeyValueType {

    @XmlElement(name = "RSAKeyValue", required = true)
    protected RSAKeyValueType rsaKeyValue;

    /**
     * Gets the value of the rsaKeyValue property.
     * 
     * @return
     *     possible object is
     *     {@link RSAKeyValueType }
     *     
     */
    public RSAKeyValueType getRSAKeyValue() {
        return rsaKeyValue;
    }

    /**
     * Sets the value of the rsaKeyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link RSAKeyValueType }
     *     
     */
    public void setRSAKeyValue(RSAKeyValueType value) {
        this.rsaKeyValue = value;
    }

}
