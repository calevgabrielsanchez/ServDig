                                       
package mx.gob.imss.service.pagoscfdi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PagosCFDIRegitroPatronal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PagosCFDIRegitroPatronal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nrp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rfc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="periodo" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="folSua" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="subTotIMSS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recIMSS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actIMSS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subTotRCV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recRCV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actRCV" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaPago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entidadRecuadadora" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="estatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cfdiXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaProceso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PagosCFDIRegitroPatronal", propOrder = {
    "nrp",
    "rfc",
    "nombre",
    "periodo",
    "folSua",
    "subTotIMSS",
    "recIMSS",
    "actIMSS",
    "subTotRCV",
    "recRCV",
    "actRCV",
    "fechaPago",
    "entidadRecaudadora",
    "estatus",
    "cfdiXml",
    "uuid",
    "fechaProceso"
})
public class PagosCFDIRegitroPatronal {

    @XmlElementRef(name = "nrp", type = JAXBElement.class)
    protected JAXBElement<String> nrp;
    @XmlElementRef(name = "rfc", type = JAXBElement.class)
    protected JAXBElement<String> rfc;
    @XmlElementRef(name = "nombre", type = JAXBElement.class)
    protected JAXBElement<String> nombre;
    @XmlElementRef(name = "periodo", type = JAXBElement.class)
    protected JAXBElement<Integer> periodo;
    @XmlElementRef(name = "folSua", type = JAXBElement.class)
    protected JAXBElement<Integer> folSua;
    @XmlElementRef(name = "subTotIMSS", type = JAXBElement.class)
    protected JAXBElement<String> subTotIMSS;
    @XmlElementRef(name = "recIMSS", type = JAXBElement.class)
    protected JAXBElement<String> recIMSS;
    @XmlElementRef(name = "actIMSS", type = JAXBElement.class)
    protected JAXBElement<String> actIMSS;
    @XmlElementRef(name = "subTotRCV", type = JAXBElement.class)
    protected JAXBElement<String> subTotRCV;
    @XmlElementRef(name = "recRCV", type = JAXBElement.class)
    protected JAXBElement<String> recRCV;
    @XmlElementRef(name = "actRCV", type = JAXBElement.class)
    protected JAXBElement<String> actRCV;
    @XmlElementRef(name = "fechaPago", type = JAXBElement.class)
    protected JAXBElement<String> fechaPago;
    @XmlElementRef(name = "entidadRecaudadora", type = JAXBElement.class)
    protected JAXBElement<Integer> entidadRecaudadora;
    @XmlElementRef(name = "estatus", type = JAXBElement.class)
    protected JAXBElement<String> estatus;
    @XmlElementRef(name = "cfdiXml", type = JAXBElement.class)
    protected JAXBElement<String> cfdiXml;
    @XmlElementRef(name = "uuid", type = JAXBElement.class)
    protected JAXBElement<String> uuid;
    @XmlElementRef(name = "fechaProceso", type = JAXBElement.class)
    protected JAXBElement<String> fechaProceso;

    /**
     * Gets the value of the nrp property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNrp() {
        return nrp;
    }

    /**
     * Sets the value of the nrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNrp(JAXBElement<String> value) {
        this.nrp = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the rfc property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRfc() {
        return rfc;
    }

    /**
     * Sets the value of the rfc property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRfc(JAXBElement<String> value) {
        this.rfc = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombre(JAXBElement<String> value) {
        this.nombre = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the periodo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getPeriodo() {
        return periodo;
    }

    /**
     * Sets the value of the periodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setPeriodo(JAXBElement<Integer> value) {
        this.periodo = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the folSua property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getFolSua() {
        return folSua;
    }

    /**
     * Sets the value of the folSua property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setFolSua(JAXBElement<Integer> value) {
        this.folSua = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the subTotIMSS property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSubTotIMSS() {
        return subTotIMSS;
    }

    /**
     * Sets the value of the subTotIMSS property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSubTotIMSS(JAXBElement<String> value) {
        this.subTotIMSS = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the recIMSS property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRecIMSS() {
        return recIMSS;
    }

    /**
     * Sets the value of the recIMSS property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRecIMSS(JAXBElement<String> value) {
        this.recIMSS = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the actIMSS property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getActIMSS() {
        return actIMSS;
    }

    /**
     * Sets the value of the actIMSS property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setActIMSS(JAXBElement<String> value) {
        this.actIMSS = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the subTotRCV property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSubTotRCV() {
        return subTotRCV;
    }

    /**
     * Sets the value of the subTotRCV property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSubTotRCV(JAXBElement<String> value) {
        this.subTotRCV = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the recRCV property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRecRCV() {
        return recRCV;
    }

    /**
     * Sets the value of the recRCV property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRecRCV(JAXBElement<String> value) {
        this.recRCV = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the actRCV property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getActRCV() {
        return actRCV;
    }

    /**
     * Sets the value of the actRCV property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setActRCV(JAXBElement<String> value) {
        this.actRCV = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the fechaPago property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFechaPago() {
        return fechaPago;
    }

    /**
     * Sets the value of the fechaPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFechaPago(JAXBElement<String> value) {
        this.fechaPago = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the entidadRecuadadora property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getEntidadRecaudadora() {
        return entidadRecaudadora;
    }

    /**
     * Sets the value of the entidadRecuadadora property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setEntidadRecaudadora(JAXBElement<Integer> value) {
        this.entidadRecaudadora = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the estatus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEstatus() {
        return estatus;
    }

    /**
     * Sets the value of the estatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEstatus(JAXBElement<String> value) {
        this.estatus = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the cfdiXml property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCfdiXml() {
        return cfdiXml;
    }

    /**
     * Sets the value of the cfdiXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCfdiXml(JAXBElement<String> value) {
        this.cfdiXml = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUuid(JAXBElement<String> value) {
        this.uuid = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the fechaProceso property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFechaProceso() {
        return fechaProceso;
    }

    /**
     * Sets the value of the fechaProceso property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFechaProceso(JAXBElement<String> value) {
        this.fechaProceso = ((JAXBElement<String> ) value);
    }

}
