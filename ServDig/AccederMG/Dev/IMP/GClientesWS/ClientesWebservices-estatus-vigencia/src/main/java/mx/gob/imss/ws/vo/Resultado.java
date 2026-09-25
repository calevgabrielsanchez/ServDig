
package mx.gob.imss.ws.vo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para resultado complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="resultado">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="estadoVigencia" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="listModVigentes" type="{http://vo.ws.imss.gob.mx/}modalidad" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="indPension" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="indTrabajadorIMSS" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fecUltimaBajaObligatorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecUltimaBajaMod33" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="semanasCotizadas" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resultado", propOrder = {
    "estadoVigencia",
    "listModVigentes",
    "indPension",
    "indTrabajadorIMSS",
    "fecUltimaBajaObligatorio",
    "fecUltimaBajaMod33",
    "semanasCotizadas"
})
public class Resultado {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer estadoVigencia;
    @XmlElement(nillable = true)
    protected List<Modalidad> listModVigentes;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer indPension;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer indTrabajadorIMSS;
    @XmlElementRef(name = "fecUltimaBajaObligatorio", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fecUltimaBajaObligatorio;
    @XmlElementRef(name = "fecUltimaBajaMod33", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fecUltimaBajaMod33;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer semanasCotizadas;

    /**
     * Obtiene el valor de la propiedad estadoVigencia.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEstadoVigencia() {
        return estadoVigencia;
    }

    /**
     * Define el valor de la propiedad estadoVigencia.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEstadoVigencia(Integer value) {
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
     * {@link Modalidad }
     * 
     * 
     */
    public List<Modalidad> getListModVigentes() {
        if (listModVigentes == null) {
            listModVigentes = new ArrayList<Modalidad>();
        }
        return this.listModVigentes;
    }

    /**
     * Obtiene el valor de la propiedad indPension.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndPension() {
        return indPension;
    }

    /**
     * Define el valor de la propiedad indPension.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndPension(Integer value) {
        this.indPension = value;
    }

    /**
     * Obtiene el valor de la propiedad indTrabajadorIMSS.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIndTrabajadorIMSS() {
        return indTrabajadorIMSS;
    }

    /**
     * Define el valor de la propiedad indTrabajadorIMSS.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIndTrabajadorIMSS(Integer value) {
        this.indTrabajadorIMSS = value;
    }

    /**
     * Obtiene el valor de la propiedad fecUltimaBajaObligatorio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFecUltimaBajaObligatorio() {
        return fecUltimaBajaObligatorio;
    }

    /**
     * Define el valor de la propiedad fecUltimaBajaObligatorio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFecUltimaBajaObligatorio(JAXBElement<String> value) {
        this.fecUltimaBajaObligatorio = value;
    }

    /**
     * Obtiene el valor de la propiedad fecUltimaBajaMod33.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFecUltimaBajaMod33() {
        return fecUltimaBajaMod33;
    }

    /**
     * Define el valor de la propiedad fecUltimaBajaMod33.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFecUltimaBajaMod33(JAXBElement<String> value) {
        this.fecUltimaBajaMod33 = value;
    }

    /**
     * Obtiene el valor de la propiedad semanasCotizadas.
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
     * Define el valor de la propiedad semanasCotizadas.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSemanasCotizadas(Integer value) {
        this.semanasCotizadas = value;
    }

}
