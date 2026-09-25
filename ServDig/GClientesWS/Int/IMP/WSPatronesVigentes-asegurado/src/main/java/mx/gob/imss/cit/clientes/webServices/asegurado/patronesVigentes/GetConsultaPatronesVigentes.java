
package mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getConsultaPatronesVigentes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getConsultaPatronesVigentes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveAsignacionNSS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getConsultaPatronesVigentes", propOrder = {
    "cveAsignacionNSS"
})
public class GetConsultaPatronesVigentes {

    protected String cveAsignacionNSS;

    /**
     * Gets the value of the cveAsignacionNSS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveAsignacionNSS() {
        return cveAsignacionNSS;
    }

    /**
     * Sets the value of the cveAsignacionNSS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveAsignacionNSS(String value) {
        this.cveAsignacionNSS = value;
    }

}
