
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recuperaResponsablesDelegacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recuperaResponsablesDelegacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveDelegacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveSubdelegacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="roles" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modulo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recuperaResponsablesDelegacion", propOrder = {
    "cveDelegacion",
    "cveSubdelegacion",
    "roles",
    "modulo"
})
public class RecuperaResponsablesDelegacion {

    protected int cveDelegacion;
    protected int cveSubdelegacion;
    protected String roles;
    protected int modulo;

    /**
     * Gets the value of the cveDelegacion property.
     * 
     */
    public int getCveDelegacion() {
        return cveDelegacion;
    }

    /**
     * Sets the value of the cveDelegacion property.
     * 
     */
    public void setCveDelegacion(int value) {
        this.cveDelegacion = value;
    }

    /**
     * Gets the value of the cveSubdelegacion property.
     * 
     */
    public int getCveSubdelegacion() {
        return cveSubdelegacion;
    }

    /**
     * Sets the value of the cveSubdelegacion property.
     * 
     */
    public void setCveSubdelegacion(int value) {
        this.cveSubdelegacion = value;
    }

    /**
     * Gets the value of the roles property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoles() {
        return roles;
    }

    /**
     * Sets the value of the roles property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoles(String value) {
        this.roles = value;
    }

    /**
     * Gets the value of the modulo property.
     * 
     */
    public int getModulo() {
        return modulo;
    }

    /**
     * Sets the value of the modulo property.
     * 
     */
    public void setModulo(int value) {
        this.modulo = value;
    }

}
