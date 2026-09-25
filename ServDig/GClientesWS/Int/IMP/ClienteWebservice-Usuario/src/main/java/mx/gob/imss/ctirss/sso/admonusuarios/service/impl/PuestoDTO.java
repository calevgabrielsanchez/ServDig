
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for puestoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="puestoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveArea" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveDepartamento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cvePuesto" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="defaultRol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departamento" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}departamentoDTO" minOccurs="0"/>
 *         &lt;element name="nombreArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreDepartamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombrePuesto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nuevoReg" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "puestoDTO", propOrder = {
    "cveArea",
    "cveDepartamento",
    "cvePuesto",
    "defaultRol",
    "departamento",
    "nombreArea",
    "nombreDepartamento",
    "nombrePuesto",
    "nuevoReg"
})
public class PuestoDTO {

    protected Integer cveArea;
    protected Integer cveDepartamento;
    protected long cvePuesto;
    protected String defaultRol;
    protected DepartamentoDTO departamento;
    protected String nombreArea;
    protected String nombreDepartamento;
    protected String nombrePuesto;
    protected boolean nuevoReg;

    /**
     * Gets the value of the cveArea property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveArea() {
        return cveArea;
    }

    /**
     * Sets the value of the cveArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveArea(Integer value) {
        this.cveArea = value;
    }

    /**
     * Gets the value of the cveDepartamento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveDepartamento() {
        return cveDepartamento;
    }

    /**
     * Sets the value of the cveDepartamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveDepartamento(Integer value) {
        this.cveDepartamento = value;
    }

    /**
     * Gets the value of the cvePuesto property.
     * 
     */
    public long getCvePuesto() {
        return cvePuesto;
    }

    /**
     * Sets the value of the cvePuesto property.
     * 
     */
    public void setCvePuesto(long value) {
        this.cvePuesto = value;
    }

    /**
     * Gets the value of the defaultRol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultRol() {
        return defaultRol;
    }

    /**
     * Sets the value of the defaultRol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultRol(String value) {
        this.defaultRol = value;
    }

    /**
     * Gets the value of the departamento property.
     * 
     * @return
     *     possible object is
     *     {@link DepartamentoDTO }
     *     
     */
    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    /**
     * Sets the value of the departamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartamentoDTO }
     *     
     */
    public void setDepartamento(DepartamentoDTO value) {
        this.departamento = value;
    }

    /**
     * Gets the value of the nombreArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreArea() {
        return nombreArea;
    }

    /**
     * Sets the value of the nombreArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreArea(String value) {
        this.nombreArea = value;
    }

    /**
     * Gets the value of the nombreDepartamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    /**
     * Sets the value of the nombreDepartamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreDepartamento(String value) {
        this.nombreDepartamento = value;
    }

    /**
     * Gets the value of the nombrePuesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombrePuesto() {
        return nombrePuesto;
    }

    /**
     * Sets the value of the nombrePuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombrePuesto(String value) {
        this.nombrePuesto = value;
    }

    /**
     * Gets the value of the nuevoReg property.
     * 
     */
    public boolean isNuevoReg() {
        return nuevoReg;
    }

    /**
     * Sets the value of the nuevoReg property.
     * 
     */
    public void setNuevoReg(boolean value) {
        this.nuevoReg = value;
    }

}
