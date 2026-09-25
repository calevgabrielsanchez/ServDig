
package mx.gob.imss.ultimospatrones.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UltimosPatronesVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UltimosPatronesVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdPatronGeneral" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveIdModalidad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="regPatron" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveModal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecAlta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecBaja" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UltimosPatronesVO", propOrder = {
    "cveIdPatronGeneral",
    "cveIdModalidad",
    "regPatron",
    "cveModal",
    "fecAlta",
    "fecBaja"
})
public class UltimosPatronesVO {

    protected Integer cveIdPatronGeneral;
    protected Integer cveIdModalidad;
    protected String regPatron;
    protected String cveModal;
    protected String fecAlta;
    protected String fecBaja;

    /**
     * Gets the value of the cveIdPatronGeneral property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdPatronGeneral() {
        return cveIdPatronGeneral;
    }

    /**
     * Sets the value of the cveIdPatronGeneral property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdPatronGeneral(Integer value) {
        this.cveIdPatronGeneral = value;
    }

    /**
     * Gets the value of the cveIdModalidad property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdModalidad() {
        return cveIdModalidad;
    }

    /**
     * Sets the value of the cveIdModalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdModalidad(Integer value) {
        this.cveIdModalidad = value;
    }

    /**
     * Gets the value of the regPatron property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegPatron() {
        return regPatron;
    }

    /**
     * Sets the value of the regPatron property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegPatron(String value) {
        this.regPatron = value;
    }

    /**
     * Gets the value of the cveModal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveModal() {
        return cveModal;
    }

    /**
     * Sets the value of the cveModal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveModal(String value) {
        this.cveModal = value;
    }

    /**
     * Gets the value of the fecAlta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecAlta() {
        return fecAlta;
    }

    /**
     * Sets the value of the fecAlta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecAlta(String value) {
        this.fecAlta = value;
    }

    /**
     * Gets the value of the fecBaja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecBaja() {
        return fecBaja;
    }

    /**
     * Sets the value of the fecBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecBaja(String value) {
        this.fecBaja = value;
    }
    
    public String toString() { 
    	return "regPatron["+this.regPatron+"] modalidad [" + this.getCveModal() +"] cveIdPatronGeneral[" + this.getCveIdPatronGeneral() +"] cveIdModalidad [" +this.getCveIdModalidad()+"] fecAlta[" 
    			+ this.getFecAlta()+ "] fecBaja["+this.getFecBaja()+""; 
    }

    
}
