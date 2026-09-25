package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyInfoType", propOrder = {
    "keyName",
    "keyValue",
    "x509Data"
})
public class KeyInfoType {

    @XmlElement(name = "KeyName", required = true)
    protected BigDecimal keyName;
    @XmlElement(name = "KeyValue", required = true)
    protected KeyValueType keyValue;
    @XmlElement(name = "X509Data", required = true)
    protected X509DataType x509Data;
    /**
     * Gets the value of the keyName property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getKeyName() {
        return keyName;
    }

    /**
     * Sets the value of the keyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setKeyName(BigDecimal value) {
        this.keyName = value;
    }

    /**
     * Gets the value of the keyValue property.
     * 
     * @return
     *     possible object is
     *     {@link KeyValueType }
     *     
     */
    public KeyValueType getKeyValue() {
        return keyValue;
    }

    /**
     * Sets the value of the keyValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyValueType }
     *     
     */
    public void setKeyValue(KeyValueType value) {
        this.keyValue = value;
    }

    public X509DataType getX509Data() {
        return x509Data;
    }

    public void setX509Data(X509DataType x509Data) {
        this.x509Data = x509Data;
    }
}
