
package mx.gob.imss.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtieneInfoCabGpoFam complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad cveidasignacionnss.
     * 
     */
    public int getCVEIDASIGNACIONNSS() {
        return cveidasignacionnss;
    }

    /**
     * Define el valor de la propiedad cveidasignacionnss.
     * 
     */
    public void setCVEIDASIGNACIONNSS(int value) {
        this.cveidasignacionnss = value;
    }

}
