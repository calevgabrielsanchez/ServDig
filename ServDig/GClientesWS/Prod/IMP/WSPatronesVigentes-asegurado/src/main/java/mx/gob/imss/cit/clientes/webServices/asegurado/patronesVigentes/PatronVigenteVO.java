
package mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PatronVigenteVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PatronVigenteVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdPatronGeneral" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveIdModalidad" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatronVigenteVO", propOrder = {
    "cveIdPatronGeneral",
    "cveIdModalidad"
})
public class PatronVigenteVO {

    protected int cveIdPatronGeneral;
    protected int cveIdModalidad;

    /**
     * Gets the value of the cveIdPatronGeneral property.
     * 
     */
    public int getCveIdPatronGeneral() {
        return cveIdPatronGeneral;
    }

    /**
     * Sets the value of the cveIdPatronGeneral property.
     * 
     */
    public void setCveIdPatronGeneral(int value) {
        this.cveIdPatronGeneral = value;
    }

    /**
     * Gets the value of the cveIdModalidad property.
     * 
     */
    public int getCveIdModalidad() {
        return cveIdModalidad;
    }

    /**
     * Sets the value of the cveIdModalidad property.
     * 
     */
    public void setCveIdModalidad(int value) {
        this.cveIdModalidad = value;
    }

}
