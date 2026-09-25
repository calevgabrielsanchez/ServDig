
package mx.com.metatrust.doctrust.wsfirmaelectronicaseg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FirmaSegRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FirmaSegRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="jsonParms" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FirmaSegRequestType", propOrder = {
    "jsonParms"
})
public class FirmaSegRequestType {

    @XmlElement(required = true)
    protected String jsonParms;

    /**
     * Gets the value of the jsonParms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJsonParms() {
        return jsonParms;
    }

    /**
     * Sets the value of the jsonParms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJsonParms(String value) {
        this.jsonParms = value;
    }

}
