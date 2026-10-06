
package mx.gob.imss.consultamod40;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Modalidad40VO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Modalidad40VO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="regPatUltimoMov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modUltimoMov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoUltimoMov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecUltimoMov" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estadoVigencia" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="listModVigentes" type="{http://consultaMod40.imss.gob.mx/}modalidadVigente" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="indPension" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tipoPension" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indTrabajadorIMSS" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="semanasCotizadas" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="regPatUltimoObligatorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modUltimoObligatorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoMovObligatorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecMovObligatorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="salarioObligatorio" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *         &lt;element name="regPatUltimoMod40" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modUltimoMod40" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoMovMod40" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecMovMod40" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="salarioMod40" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Modalidad40VO", propOrder = {
    "regPatUltimoMov",
    "modUltimoMov",
    "tipoUltimoMov",
    "fecUltimoMov",
    "estadoVigencia",
    "listModVigentes",
    "indPension",
    "tipoPension",
    "indTrabajadorIMSS",
    "semanasCotizadas",
    "regPatUltimoObligatorio",
    "modUltimoObligatorio",
    "tipoMovObligatorio",
    "fecMovObligatorio",
    "salarioObligatorio",
    "regPatUltimoMod40",
    "modUltimoMod40",
    "tipoMovMod40",
    "fecMovMod40",
    "salarioMod40"
})
public class Modalidad40VO {

    protected String regPatUltimoMov;
    protected String modUltimoMov;
    protected String tipoUltimoMov;
    protected String fecUltimoMov;
    protected int estadoVigencia;
    @XmlElement(nillable = true)
    protected List<ModalidadVigente> listModVigentes;
    protected int indPension;
    @XmlElementRef(name = "tipoPension", type = JAXBElement.class)
    protected JAXBElement<String> tipoPension;
    protected int indTrabajadorIMSS;
    protected Integer semanasCotizadas;
    @XmlElementRef(name = "regPatUltimoObligatorio", type = JAXBElement.class)
    protected JAXBElement<String> regPatUltimoObligatorio;
    @XmlElementRef(name = "modUltimoObligatorio", type = JAXBElement.class)
    protected JAXBElement<String> modUltimoObligatorio;
    @XmlElementRef(name = "tipoMovObligatorio", type = JAXBElement.class)
    protected JAXBElement<String> tipoMovObligatorio;
    @XmlElementRef(name = "fecMovObligatorio", type = JAXBElement.class)
    protected JAXBElement<String> fecMovObligatorio;
    @XmlElementRef(name = "salarioObligatorio", type = JAXBElement.class)
    protected JAXBElement<Float> salarioObligatorio;
    @XmlElementRef(name = "regPatUltimoMod40", type = JAXBElement.class)
    protected JAXBElement<String> regPatUltimoMod40;
    @XmlElementRef(name = "modUltimoMod40", type = JAXBElement.class)
    protected JAXBElement<String> modUltimoMod40;
    @XmlElementRef(name = "tipoMovMod40", type = JAXBElement.class)
    protected JAXBElement<String> tipoMovMod40;
    @XmlElementRef(name = "fecMovMod40", type = JAXBElement.class)
    protected JAXBElement<String> fecMovMod40;
    @XmlElementRef(name = "salarioMod40", type = JAXBElement.class)
    protected JAXBElement<Float> salarioMod40;

    /**
     * Gets the value of the regPatUltimoMov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegPatUltimoMov() {
        return regPatUltimoMov;
    }

    /**
     * Sets the value of the regPatUltimoMov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegPatUltimoMov(String value) {
        this.regPatUltimoMov = value;
    }

    /**
     * Gets the value of the modUltimoMov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModUltimoMov() {
        return modUltimoMov;
    }

    /**
     * Sets the value of the modUltimoMov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModUltimoMov(String value) {
        this.modUltimoMov = value;
    }

    /**
     * Gets the value of the tipoUltimoMov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoUltimoMov() {
        return tipoUltimoMov;
    }

    /**
     * Sets the value of the tipoUltimoMov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoUltimoMov(String value) {
        this.tipoUltimoMov = value;
    }

    /**
     * Gets the value of the fecUltimoMov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecUltimoMov() {
        return fecUltimoMov;
    }

    /**
     * Sets the value of the fecUltimoMov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecUltimoMov(String value) {
        this.fecUltimoMov = value;
    }

    /**
     * Gets the value of the estadoVigencia property.
     * 
     */
    public int getEstadoVigencia() {
        return estadoVigencia;
    }

    /**
     * Sets the value of the estadoVigencia property.
     * 
     */
    public void setEstadoVigencia(int value) {
        this.estadoVigencia = value;
    }

    /**
     * Gets the value of the listModVigentes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listModVigentes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListModVigentes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModalidadVigente }
     * 
     * 
     */
    public List<ModalidadVigente> getListModVigentes() {
        if (listModVigentes == null) {
            listModVigentes = new ArrayList<ModalidadVigente>();
        }
        return this.listModVigentes;
    }

    /**
     * Gets the value of the indPension property.
     * 
     */
    public int getIndPension() {
        return indPension;
    }

    /**
     * Sets the value of the indPension property.
     * 
     */
    public void setIndPension(int value) {
        this.indPension = value;
    }

    /**
     * Gets the value of the tipoPension property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoPension() {
        return tipoPension;
    }

    /**
     * Sets the value of the tipoPension property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoPension(JAXBElement<String> value) {
        this.tipoPension = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the indTrabajadorIMSS property.
     * 
     */
    public int getIndTrabajadorIMSS() {
        return indTrabajadorIMSS;
    }

    /**
     * Sets the value of the indTrabajadorIMSS property.
     * 
     */
    public void setIndTrabajadorIMSS(int value) {
        this.indTrabajadorIMSS = value;
    }

    /**
     * Gets the value of the semanasCotizadas property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSemanasCotizadas() {
        return semanasCotizadas;
    }

    /**
     * Sets the value of the semanasCotizadas property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSemanasCotizadas(Integer value) {
        this.semanasCotizadas = value;
    }

    /**
     * Gets the value of the regPatUltimoObligatorio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRegPatUltimoObligatorio() {
        return regPatUltimoObligatorio;
    }

    /**
     * Sets the value of the regPatUltimoObligatorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRegPatUltimoObligatorio(JAXBElement<String> value) {
        this.regPatUltimoObligatorio = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the modUltimoObligatorio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getModUltimoObligatorio() {
        return modUltimoObligatorio;
    }

    /**
     * Sets the value of the modUltimoObligatorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setModUltimoObligatorio(JAXBElement<String> value) {
        this.modUltimoObligatorio = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the tipoMovObligatorio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoMovObligatorio() {
        return tipoMovObligatorio;
    }

    /**
     * Sets the value of the tipoMovObligatorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoMovObligatorio(JAXBElement<String> value) {
        this.tipoMovObligatorio = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the fecMovObligatorio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFecMovObligatorio() {
        return fecMovObligatorio;
    }

    /**
     * Sets the value of the fecMovObligatorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFecMovObligatorio(JAXBElement<String> value) {
        this.fecMovObligatorio = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the salarioObligatorio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Float }{@code >}
     *     
     */
    public JAXBElement<Float> getSalarioObligatorio() {
        return salarioObligatorio;
    }

    /**
     * Sets the value of the salarioObligatorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Float }{@code >}
     *     
     */
    public void setSalarioObligatorio(JAXBElement<Float> value) {
        this.salarioObligatorio = ((JAXBElement<Float> ) value);
    }

    /**
     * Gets the value of the regPatUltimoMod40 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRegPatUltimoMod40() {
        return regPatUltimoMod40;
    }

    /**
     * Sets the value of the regPatUltimoMod40 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRegPatUltimoMod40(JAXBElement<String> value) {
        this.regPatUltimoMod40 = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the modUltimoMod40 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getModUltimoMod40() {
        return modUltimoMod40;
    }

    /**
     * Sets the value of the modUltimoMod40 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setModUltimoMod40(JAXBElement<String> value) {
        this.modUltimoMod40 = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the tipoMovMod40 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoMovMod40() {
        return tipoMovMod40;
    }

    /**
     * Sets the value of the tipoMovMod40 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoMovMod40(JAXBElement<String> value) {
        this.tipoMovMod40 = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the fecMovMod40 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFecMovMod40() {
        return fecMovMod40;
    }

    /**
     * Sets the value of the fecMovMod40 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFecMovMod40(JAXBElement<String> value) {
        this.fecMovMod40 = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the salarioMod40 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Float }{@code >}
     *     
     */
    public JAXBElement<Float> getSalarioMod40() {
        return salarioMod40;
    }

    /**
     * Sets the value of the salarioMod40 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Float }{@code >}
     *     
     */
    public void setSalarioMod40(JAXBElement<Float> value) {
        this.salarioMod40 = ((JAXBElement<Float> ) value);
    }

	@Override
	public String toString() {
		return "Modalidad40VO [regPatUltimoMov=" + regPatUltimoMov + ", modUltimoMov=" + modUltimoMov
				+ ", tipoUltimoMov=" + tipoUltimoMov + ", fecUltimoMov=" + fecUltimoMov + ", estadoVigencia="
				+ estadoVigencia + ", listModVigentes=" + listModVigentes + ", indPension=" + indPension
				+ ", tipoPension=" + tipoPension + ", indTrabajadorIMSS=" + indTrabajadorIMSS + ", semanasCotizadas="
				+ semanasCotizadas + ", regPatUltimoObligatorio=" + regPatUltimoObligatorio + ", modUltimoObligatorio="
				+ modUltimoObligatorio + ", tipoMovObligatorio=" + tipoMovObligatorio + ", fecMovObligatorio="
				+ fecMovObligatorio + ", salarioObligatorio=" + salarioObligatorio + ", regPatUltimoMod40="
				+ regPatUltimoMod40 + ", modUltimoMod40=" + modUltimoMod40 + ", tipoMovMod40=" + tipoMovMod40
				+ ", fecMovMod40=" + fecMovMod40 + ", salarioMod40=" + salarioMod40 + "]";
	}

}
