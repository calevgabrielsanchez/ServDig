
package mx.gob.imss.ws.estatusvigencia.individual.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getSituacionAseguramientoXAsginacionNSS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getSituacionAseguramientoXAsginacionNSS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idAsignacionNSS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getSituacionAseguramientoXAsginacionNSS", propOrder = {
    "idAsignacionNSS"
})
public class GetSituacionAseguramientoXAsginacionNSS {

    protected String idAsignacionNSS;

    /**
     * Gets the value of the idAsignacionNSS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAsignacionNSS() {
        return idAsignacionNSS;
    }

    /**
     * Sets the value of the idAsignacionNSS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAsignacionNSS(String value) {
        this.idAsignacionNSS = value;
    }

}
