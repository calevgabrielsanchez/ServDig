
package mx.gob.imss.service.vigenciagrupofamparen.estado;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageWSConsVigGpoFamXParenEst complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messageWSConsVigGpoFamXParenEst">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdAsignacionNss" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveIdCalidadParentesco" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveIdEstadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageWSConsVigGpoFamXParenEst", propOrder = {
    "cveIdAsignacionNss",
    "cveIdCalidadParentesco",
    "cveIdEstadoDerechohabiente"
})
public class MessageWSConsVigGpoFamXParenEst {

    protected int cveIdAsignacionNss;
    protected int cveIdCalidadParentesco;
    protected int cveIdEstadoDerechohabiente;

    /**
     * Gets the value of the cveIdAsignacionNss property.
     * 
     */
    public int getCveIdAsignacionNss() {
        return cveIdAsignacionNss;
    }

    /**
     * Sets the value of the cveIdAsignacionNss property.
     * 
     */
    public void setCveIdAsignacionNss(int value) {
        this.cveIdAsignacionNss = value;
    }

    /**
     * Gets the value of the cveIdCalidadParentesco property.
     * 
     */
    public int getCveIdCalidadParentesco() {
        return cveIdCalidadParentesco;
    }

    /**
     * Sets the value of the cveIdCalidadParentesco property.
     * 
     */
    public void setCveIdCalidadParentesco(int value) {
        this.cveIdCalidadParentesco = value;
    }

    /**
     * Gets the value of the cveIdEstadoDerechohabiente property.
     * 
     */
    public int getCveIdEstadoDerechohabiente() {
        return cveIdEstadoDerechohabiente;
    }

    /**
     * Sets the value of the cveIdEstadoDerechohabiente property.
     * 
     */
    public void setCveIdEstadoDerechohabiente(int value) {
        this.cveIdEstadoDerechohabiente = value;
    }

}
