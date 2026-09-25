
package mx.gob.imss.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para InfoCabezaGrupoFamiliarVO complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="InfoCabezaGrupoFamiliarVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdAsignacionNss" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveEstadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveSubestadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveIdCalidadParentesco" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="fecUltimoMovto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecInicioVigencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecFinVigencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indPatronImss" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveIdPatronGeneral" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveIdTipoMovimiento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="fecValidezConstancia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indEstudiante" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="regPatron" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cveModal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="conDerechoSm" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "conDerechoSm"
})
public class InfoCabezaGrupoFamiliarVO {

    @XmlElementRef(name = "cveIdAsignacionNss", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdAsignacionNss;
    @XmlElementRef(name = "cveEstadoDerechohabiente", type = JAXBElement.class)
    protected JAXBElement<Integer> cveEstadoDerechohabiente;
    @XmlElementRef(name = "cveSubestadoDerechohabiente", type = JAXBElement.class)
    protected JAXBElement<Integer> cveSubestadoDerechohabiente;
    @XmlElementRef(name = "cveIdCalidadParentesco", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdCalidadParentesco;
    @XmlElementRef(name = "fecUltimoMovto", type = JAXBElement.class)
    protected JAXBElement<String> fecUltimoMovto;
    @XmlElementRef(name = "fecInicioVigencia", type = JAXBElement.class)
    protected JAXBElement<String> fecInicioVigencia;
    @XmlElementRef(name = "fecFinVigencia", type = JAXBElement.class)
    protected JAXBElement<String> fecFinVigencia;
    @XmlElementRef(name = "indPatronImss", type = JAXBElement.class)
    protected JAXBElement<Integer> indPatronImss;
    @XmlElementRef(name = "cveIdPatronGeneral", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdPatronGeneral;
    @XmlElementRef(name = "cveIdTipoMovimiento", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdTipoMovimiento;
    @XmlElementRef(name = "fecValidezConstancia", type = JAXBElement.class)
    protected JAXBElement<String> fecValidezConstancia;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer indEstudiante;
    @XmlElement(required = true, type = String.class,nillable = true)
    protected String regPatron;
    @XmlElement(required = true, type = String.class,nillable = true)
    protected String cveModal;
    @XmlElementRef(name = "conDerechoSm", type = JAXBElement.class)
    protected JAXBElement<String> conDerechoSm;

    /**
     * Obtiene el valor de la propiedad cveIdAsignacionNss.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveIdAsignacionNss() {
        return cveIdAsignacionNss;
    }

    /**
     * Define el valor de la propiedad cveIdAsignacionNss.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveIdAsignacionNss(JAXBElement<Integer> value) {
        this.cveIdAsignacionNss = value;
    }

    /**
     * Obtiene el valor de la propiedad cveEstadoDerechohabiente.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveEstadoDerechohabiente() {
        return cveEstadoDerechohabiente;
    }

    /**
     * Define el valor de la propiedad cveEstadoDerechohabiente.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveEstadoDerechohabiente(JAXBElement<Integer> value) {
        this.cveEstadoDerechohabiente = value;
    }

    /**
     * Obtiene el valor de la propiedad cveSubestadoDerechohabiente.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveSubestadoDerechohabiente() {
        return cveSubestadoDerechohabiente;
    }

    /**
     * Define el valor de la propiedad cveSubestadoDerechohabiente.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveSubestadoDerechohabiente(JAXBElement<Integer> value) {
        this.cveSubestadoDerechohabiente = value;
    }

    /**
     * Obtiene el valor de la propiedad cveIdCalidadParentesco.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveIdCalidadParentesco() {
        return cveIdCalidadParentesco;
    }

    /**
     * Define el valor de la propiedad cveIdCalidadParentesco.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveIdCalidadParentesco(JAXBElement<Integer> value) {
        this.cveIdCalidadParentesco = value;
    }

    /**
     * Obtiene el valor de la propiedad fecUltimoMovto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFecUltimoMovto() {
        return fecUltimoMovto;
    }

    /**
     * Define el valor de la propiedad fecUltimoMovto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFecUltimoMovto(JAXBElement<String> value) {
        this.fecUltimoMovto = value;
    }

    /**
     * Obtiene el valor de la propiedad fecInicioVigencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFecInicioVigencia() {
        return fecInicioVigencia;
    }

    /**
     * Define el valor de la propiedad fecInicioVigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFecInicioVigencia(JAXBElement<String> value) {
        this.fecInicioVigencia = value;
    }

    /**
     * Obtiene el valor de la propiedad fecFinVigencia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFecFinVigencia() {
        return fecFinVigencia;
    }

    /**
     * Define el valor de la propiedad fecFinVigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFecFinVigencia(JAXBElement<String> value) {
        this.fecFinVigencia = value;
    }

    /**
     * Obtiene el valor de la propiedad indPatronImss.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIndPatronImss() {
        return indPatronImss;
    }

    /**
     * Define el valor de la propiedad indPatronImss.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIndPatronImss(JAXBElement<Integer> value) {
        this.indPatronImss = value;
    }

    /**
     * Obtiene el valor de la propiedad cveIdPatronGeneral.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveIdPatronGeneral() {
        return cveIdPatronGeneral;
    }

    /**
     * Define el valor de la propiedad cveIdPatronGeneral.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveIdPatronGeneral(JAXBElement<Integer> value) {
        this.cveIdPatronGeneral = value;
    }

    /**
     * Obtiene el valor de la propiedad cveIdTipoMovimiento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveIdTipoMovimiento() {
        return cveIdTipoMovimiento;
    }

    /**
     * Define el valor de la propiedad cveIdTipoMovimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveIdTipoMovimiento(JAXBElement<Integer> value) {
        this.cveIdTipoMovimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad fecValidezConstancia.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFecValidezConstancia() {
        return fecValidezConstancia;
    }

    /**
     * Define el valor de la propiedad fecValidezConstancia.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFecValidezConstancia(JAXBElement<String> value) {
        this.fecValidezConstancia = value;
    }

    /**
     * Obtiene el valor de la propiedad indEstudiante.
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
     * Define el valor de la propiedad indEstudiante.
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
     * Obtiene el valor de la propiedad regPatron.
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
     * Define el valor de la propiedad regPatron.
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
     * Obtiene el valor de la propiedad cveModal.
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
     * Define el valor de la propiedad cveModal.
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
     * Obtiene el valor de la propiedad conDerechoSm.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getConDerechoSm() {
        return conDerechoSm;
    }

    /**
     * Define el valor de la propiedad conDerechoSm.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setConDerechoSm(JAXBElement<String> value) {
        this.conDerechoSm = value;
    }

}
