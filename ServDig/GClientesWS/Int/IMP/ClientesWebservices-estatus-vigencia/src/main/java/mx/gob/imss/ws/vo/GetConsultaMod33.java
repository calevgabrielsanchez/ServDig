
package mx.gob.imss.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para getConsultaMod33 complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="getConsultaMod33">
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
@XmlType(name = "getConsultaMod33", propOrder = {
    "idAsignacionNSS"
})
public class GetConsultaMod33 {

    protected String idAsignacionNSS;

    /**
     * Obtiene el valor de la propiedad idAsignacionNSS.
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
     * Define el valor de la propiedad idAsignacionNSS.
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
