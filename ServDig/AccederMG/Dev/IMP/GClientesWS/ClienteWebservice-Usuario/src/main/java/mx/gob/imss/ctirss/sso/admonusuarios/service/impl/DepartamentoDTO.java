
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for departamentoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="departamentoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="areaNormativa" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}areaNormativaDTO" minOccurs="0"/>
 *         &lt;element name="cveSsodepto" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="departamentoPadre" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}departamentoDTO" minOccurs="0"/>
 *         &lt;element name="deptoGeneralDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}departamentoDTO" minOccurs="0"/>
 *         &lt;element name="desDepartamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "departamentoDTO", propOrder = {
    "areaNormativa",
    "cveSsodepto",
    "departamentoPadre",
    "deptoGeneralDTO",
    "desDepartamento"
})
public class DepartamentoDTO {

    protected AreaNormativaDTO areaNormativa;
    protected long cveSsodepto;
    protected DepartamentoDTO departamentoPadre;
    protected DepartamentoDTO deptoGeneralDTO;
    protected String desDepartamento;

    /**
     * Gets the value of the areaNormativa property.
     * 
     * @return
     *     possible object is
     *     {@link AreaNormativaDTO }
     *     
     */
    public AreaNormativaDTO getAreaNormativa() {
        return areaNormativa;
    }

    /**
     * Sets the value of the areaNormativa property.
     * 
     * @param value
     *     allowed object is
     *     {@link AreaNormativaDTO }
     *     
     */
    public void setAreaNormativa(AreaNormativaDTO value) {
        this.areaNormativa = value;
    }

    /**
     * Gets the value of the cveSsodepto property.
     * 
     */
    public long getCveSsodepto() {
        return cveSsodepto;
    }

    /**
     * Sets the value of the cveSsodepto property.
     * 
     */
    public void setCveSsodepto(long value) {
        this.cveSsodepto = value;
    }

    /**
     * Gets the value of the departamentoPadre property.
     * 
     * @return
     *     possible object is
     *     {@link DepartamentoDTO }
     *     
     */
    public DepartamentoDTO getDepartamentoPadre() {
        return departamentoPadre;
    }

    /**
     * Sets the value of the departamentoPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartamentoDTO }
     *     
     */
    public void setDepartamentoPadre(DepartamentoDTO value) {
        this.departamentoPadre = value;
    }

    /**
     * Gets the value of the deptoGeneralDTO property.
     * 
     * @return
     *     possible object is
     *     {@link DepartamentoDTO }
     *     
     */
    public DepartamentoDTO getDeptoGeneralDTO() {
        return deptoGeneralDTO;
    }

    /**
     * Sets the value of the deptoGeneralDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartamentoDTO }
     *     
     */
    public void setDeptoGeneralDTO(DepartamentoDTO value) {
        this.deptoGeneralDTO = value;
    }

    /**
     * Gets the value of the desDepartamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesDepartamento() {
        return desDepartamento;
    }

    /**
     * Sets the value of the desDepartamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesDepartamento(String value) {
        this.desDepartamento = value;
    }

}
