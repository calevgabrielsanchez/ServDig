
package mx.gob.imss.service.vigenciaderechohab;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para InfoConsVigXDHab complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="InfoConsVigXDHab">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cveIdPersona" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveEstadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveSubestadoDerechohabiente" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cveIdCalidadParentesco" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="fecInicioVigencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecFinVigencia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agregadoMedico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoConsVigXDHab", propOrder = {
    "cveIdPersona",
    "cveEstadoDerechohabiente",
    "cveSubestadoDerechohabiente",
    "cveIdCalidadParentesco",
    "fecInicioVigencia",
    "fecFinVigencia",
    "agregadoMedico"
})
public class InfoConsVigXDHab {

    @XmlElementRef(name = "cveIdPersona", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdPersona;
    @XmlElementRef(name = "cveEstadoDerechohabiente", type = JAXBElement.class)
    protected JAXBElement<Integer> cveEstadoDerechohabiente;
    @XmlElementRef(name = "cveSubestadoDerechohabiente", type = JAXBElement.class)
    protected JAXBElement<Integer> cveSubestadoDerechohabiente;
    @XmlElementRef(name = "cveIdCalidadParentesco", type = JAXBElement.class)
    protected JAXBElement<Integer> cveIdCalidadParentesco;
    @XmlElementRef(name = "fecInicioVigencia", type = JAXBElement.class)
    protected JAXBElement<String> fecInicioVigencia;
    @XmlElementRef(name = "fecFinVigencia", type = JAXBElement.class)
    protected JAXBElement<String> fecFinVigencia;
    @XmlElementRef(name = "agregadoMedico", type = JAXBElement.class)
    protected JAXBElement<String> agregadoMedico;

    /**
     * Obtiene el valor de la propiedad cveIdPersona.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getCveIdPersona() {
        return cveIdPersona;
    }

    /**
     * Define el valor de la propiedad cveIdPersona.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setCveIdPersona(JAXBElement<Integer> value) {
        this.cveIdPersona = value;
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
     * Obtiene el valor de la propiedad agregadoMedico.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAgregadoMedico() {
        return agregadoMedico;
    }

    /**
     * Define el valor de la propiedad agregadoMedico.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAgregadoMedico(JAXBElement<String> value) {
        this.agregadoMedico = value;
    }

}
