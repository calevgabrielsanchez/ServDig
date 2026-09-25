
package mx.gob.imss.vigenciagrupofamte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for obtieneInfoCabGpoFam complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="obtieneInfoCabGpoFam">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CVE_ID_ASIGNACION_NSS" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtieneInfoCabGpoFam", propOrder = {
    "cveidasignacionnss"
})
public class ObtieneInfoCabGpoFam {

    @XmlElement(name = "CVE_ID_ASIGNACION_NSS")
    protected int cveidasignacionnss;

    /**
     * Gets the value of the cveidasignacionnss property.
     * 
     */
    public int getCVEIDASIGNACIONNSS() {
        return cveidasignacionnss;
    }

    /**
     * Sets the value of the cveidasignacionnss property.
     * 
     */
    public void setCVEIDASIGNACIONNSS(int value) {
        this.cveidasignacionnss = value;
    }

}
