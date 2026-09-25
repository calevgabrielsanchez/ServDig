
package mx.gob.imss.vigenciagrupofamte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoCabezaGrupoFamiliarVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoCabezaGrupoFamiliarVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdAsignacionNss" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveEstadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveSubestadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveIdCalidadParentesco" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fecUltimoMovto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fecInicioVigencia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fecFinVigencia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indPatronImss" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveIdPatronGeneral" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveIdTipoMovimiento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fecValidezConstancia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indEstudiante" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="regPatron" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveModal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="conDerechoSm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="articulo82" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="articulo83" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="articulo84" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="articulo85" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tiemposEspera" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoCabezaGrupoFamiliarVO", propOrder = {
    "cveIdAsignacionNss",
    "cveEstadoDerechohabiente",
    "cveSubestadoDerechohabiente",
    "cveIdCalidadParentesco",
    "fecUltimoMovto",
    "fecInicioVigencia",
    "fecFinVigencia",
    "indPatronImss",
    "cveIdPatronGeneral",
    "cveIdTipoMovimiento",
    "fecValidezConstancia",
    "indEstudiante",
    "regPatron",
    "cveModal",
    "conDerechoSm",
    "articulo82",
    "articulo83",
    "articulo84",
    "articulo85",
    "tiemposEspera"
})
public class InfoCabezaGrupoFamiliarVO {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer cveIdAsignacionNss;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer cveEstadoDerechohabiente;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer cveSubestadoDerechohabiente;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer cveIdCalidadParentesco;
    @XmlElement(required = true, nillable = true)
    protected String fecUltimoMovto;
    @XmlElement(required = true, nillable = true)
    protected String fecInicioVigencia;
    @XmlElement(required = true, nillable = true)
    protected String fecFinVigencia;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer indPatronImss;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer cveIdPatronGeneral;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer cveIdTipoMovimiento;
    @XmlElement(required = true, nillable = true)
    protected String fecValidezConstancia;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer indEstudiante;
    @XmlElement(required = true, nillable = true)
    protected String regPatron;
    @XmlElement(required = true, nillable = true)
    protected String cveModal;
    @XmlElement(required = true, nillable = true)
    protected String conDerechoSm;
    @XmlElement(required = true, nillable = true)
    protected String articulo82;
    @XmlElement(required = true, nillable = true)
    protected String articulo83;
    @XmlElement(required = true, nillable = true)
    protected String articulo84;
    @XmlElement(required = true, nillable = true)
    protected String articulo85;
    @XmlElement(required = true, nillable = true)
    protected String tiemposEspera;

    /**
     * Gets the value of the cveIdAsignacionNss property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdAsignacionNss() {
        return cveIdAsignacionNss;
    }

    /**
     * Sets the value of the cveIdAsignacionNss property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdAsignacionNss(Integer value) {
        this.cveIdAsignacionNss = value;
    }

    /**
     * Gets the value of the cveEstadoDerechohabiente property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveEstadoDerechohabiente() {
        return cveEstadoDerechohabiente;
    }

    /**
     * Sets the value of the cveEstadoDerechohabiente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveEstadoDerechohabiente(Integer value) {
        this.cveEstadoDerechohabiente = value;
    }

    /**
     * Gets the value of the cveSubestadoDerechohabiente property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveSubestadoDerechohabiente() {
        return cveSubestadoDerechohabiente;
    }

    /**
     * Sets the value of the cveSubestadoDerechohabiente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveSubestadoDerechohabiente(Integer value) {
        this.cveSubestadoDerechohabiente = value;
    }

    /**
     * Gets the value of the cveIdCalidadParentesco property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdCalidadParentesco() {
        return cveIdCalidadParentesco;
    }

    /**
     * Sets the value of the cveIdCalidadParentesco property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdCalidadParentesco(Integer value) {
        this.cveIdCalidadParentesco = value;
    }

    /**
     * Gets the value of the fecUltimoMovto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecUltimoMovto() {
        return fecUltimoMovto;
    }

    /**
     * Sets the value of the fecUltimoMovto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecUltimoMovto(String value) {
        this.fecUltimoMovto = value;
    }

    /**
     * Gets the value of the fecInicioVigencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecInicioVigencia() {
        return fecInicioVigencia;
    }

    /**
     * Sets the value of the fecInicioVigencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecInicioVigencia(String value) {
        this.fecInicioVigencia = value;
    }

    /**
     * Gets the value of the fecFinVigencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecFinVigencia() {
        return fecFinVigencia;
    }

    /**
     * Sets the value of the fecFinVigencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecFinVigencia(String value) {
        this.fecFinVigencia = value;
    }

    /**
     * Gets the value of the indPatronImss property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndPatronImss() {
        return indPatronImss;
    }

    /**
     * Sets the value of the indPatronImss property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndPatronImss(Integer value) {
        this.indPatronImss = value;
    }

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
     * Gets the value of the cveIdTipoMovimiento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdTipoMovimiento() {
        return cveIdTipoMovimiento;
    }

    /**
     * Sets the value of the cveIdTipoMovimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdTipoMovimiento(Integer value) {
        this.cveIdTipoMovimiento = value;
    }

    /**
     * Gets the value of the fecValidezConstancia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecValidezConstancia() {
        return fecValidezConstancia;
    }

    /**
     * Sets the value of the fecValidezConstancia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecValidezConstancia(String value) {
        this.fecValidezConstancia = value;
    }

    /**
     * Gets the value of the indEstudiante property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndEstudiante() {
        return indEstudiante;
    }

    /**
     * Sets the value of the indEstudiante property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndEstudiante(Integer value) {
        this.indEstudiante = value;
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
     * Gets the value of the conDerechoSm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConDerechoSm() {
        return conDerechoSm;
    }

    /**
     * Sets the value of the conDerechoSm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConDerechoSm(String value) {
        this.conDerechoSm = value;
    }

    /**
     * Gets the value of the articulo82 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticulo82() {
        return articulo82;
    }

    /**
     * Sets the value of the articulo82 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticulo82(String value) {
        this.articulo82 = value;
    }

    /**
     * Gets the value of the articulo83 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticulo83() {
        return articulo83;
    }

    /**
     * Sets the value of the articulo83 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticulo83(String value) {
        this.articulo83 = value;
    }

    /**
     * Gets the value of the articulo84 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticulo84() {
        return articulo84;
    }

    /**
     * Sets the value of the articulo84 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticulo84(String value) {
        this.articulo84 = value;
    }

    /**
     * Gets the value of the articulo85 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticulo85() {
        return articulo85;
    }

    /**
     * Sets the value of the articulo85 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticulo85(String value) {
        this.articulo85 = value;
    }

    /**
     * Gets the value of the tiemposEspera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTiemposEspera() {
        return tiemposEspera;
    }

    /**
     * Sets the value of the tiemposEspera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTiemposEspera(String value) {
        this.tiemposEspera = value;
    }

}
