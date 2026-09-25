
package mx.gob.imss.service.vigenciaderechohab;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para messageWSConsVigXDHab complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="messageWSConsVigXDHab">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdAsignacionNss" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveIdPersonaIntegrante" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageWSConsVigXDHab", propOrder = {
    "cveIdAsignacionNss",
    "cveIdPersonaIntegrante"
})
public class MessageWSConsVigXDHab {

    protected int cveIdAsignacionNss;
    protected int cveIdPersonaIntegrante;

    /**
     * Obtiene el valor de la propiedad cveIdAsignacionNss.
     * 
     */
    public int getCveIdAsignacionNss() {
        return cveIdAsignacionNss;
    }

    /**
     * Define el valor de la propiedad cveIdAsignacionNss.
     * 
     */
    public void setCveIdAsignacionNss(int value) {
        this.cveIdAsignacionNss = value;
    }

    /**
     * Obtiene el valor de la propiedad cveIdPersonaIntegrante.
     * 
     */
    public int getCveIdPersonaIntegrante() {
        return cveIdPersonaIntegrante;
    }

    /**
     * Define el valor de la propiedad cveIdPersonaIntegrante.
     * 
     */
    public void setCveIdPersonaIntegrante(int value) {
        this.cveIdPersonaIntegrante = value;
    }

}
