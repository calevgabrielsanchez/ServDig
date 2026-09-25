
package mx.gob.imss.cit.clienteServiciosComunes.ws.commonschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for governanceHeaderRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="governanceHeaderRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sgbde" type="{http://cit.imss.gob.mx/ws/commonSchema}SGBDE"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "governanceHeaderRequest", propOrder = {
    "sgbde"
})
public class GovernanceHeaderRequest {

    @XmlElement(required = true)
    protected SGBDE sgbde;

    /**
     * Gets the value of the sgbde property.
     * 
     * @return
     *     possible object is
     *     {@link SGBDE }
     *     
     */
    public SGBDE getSgbde() {
        return sgbde;
    }

    /**
     * Sets the value of the sgbde property.
     * 
     * @param value
     *     allowed object is
     *     {@link SGBDE }
     *     
     */
    public void setSgbde(SGBDE value) {
        this.sgbde = value;
    }

}
