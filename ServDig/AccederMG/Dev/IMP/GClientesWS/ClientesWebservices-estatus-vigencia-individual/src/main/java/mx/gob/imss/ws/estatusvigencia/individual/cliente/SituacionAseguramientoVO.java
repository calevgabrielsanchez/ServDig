
package mx.gob.imss.ws.estatusvigencia.individual.cliente;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SituacionAseguramientoVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SituacionAseguramientoVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indicadorVigente" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="modalidadesFechaVigente" type="{http://situacionAseguramiento.imss.gob.mx/}modalidadFecha" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="modalidadesFechaBaja" type="{http://situacionAseguramiento.imss.gob.mx/}modalidadFecha" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tipoAseguradoBaja" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nrpBaja" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroSemanaAseguramientoBaja" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SituacionAseguramientoVO", propOrder = {
    "indicadorVigente",
    "modalidadesFechaVigente",
    "modalidadesFechaBaja",
    "tipoAseguradoBaja",
    "nrpBaja",
    "numeroSemanaAseguramientoBaja"
})
public class SituacionAseguramientoVO {

    protected boolean indicadorVigente;
    @XmlElement(nillable = true)
    protected List<ModalidadFecha> modalidadesFechaVigente;
    @XmlElement(nillable = true)
    protected List<ModalidadFecha> modalidadesFechaBaja;
    @XmlElementRef(name = "tipoAseguradoBaja", type = JAXBElement.class)
    protected JAXBElement<Integer> tipoAseguradoBaja;
    @XmlElementRef(name = "nrpBaja", type = JAXBElement.class)
    protected JAXBElement<String> nrpBaja;
    @XmlElementRef(name = "numeroSemanaAseguramientoBaja", type = JAXBElement.class)
    protected JAXBElement<Integer> numeroSemanaAseguramientoBaja;

    /**
     * Gets the value of the indicadorVigente property.
     * 
     */
    public boolean isIndicadorVigente() {
        return indicadorVigente;
    }

    /**
     * Sets the value of the indicadorVigente property.
     * 
     */
    public void setIndicadorVigente(boolean value) {
        this.indicadorVigente = value;
    }

    /**
     * Gets the value of the modalidadesFechaVigente property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modalidadesFechaVigente property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModalidadesFechaVigente().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModalidadFecha }
     * 
     * 
     */
    public List<ModalidadFecha> getModalidadesFechaVigente() {
        if (modalidadesFechaVigente == null) {
            modalidadesFechaVigente = new ArrayList<ModalidadFecha>();
        }
        return this.modalidadesFechaVigente;
    }

    /**
     * Gets the value of the modalidadesFechaBaja property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modalidadesFechaBaja property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModalidadesFechaBaja().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModalidadFecha }
     * 
     * 
     */
    public List<ModalidadFecha> getModalidadesFechaBaja() {
        if (modalidadesFechaBaja == null) {
            modalidadesFechaBaja = new ArrayList<ModalidadFecha>();
        }
        return this.modalidadesFechaBaja;
    }

    /**
     * Gets the value of the tipoAseguradoBaja property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getTipoAseguradoBaja() {
        return tipoAseguradoBaja;
    }

    /**
     * Sets the value of the tipoAseguradoBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setTipoAseguradoBaja(JAXBElement<Integer> value) {
        this.tipoAseguradoBaja = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the nrpBaja property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNrpBaja() {
        return nrpBaja;
    }

    /**
     * Sets the value of the nrpBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNrpBaja(JAXBElement<String> value) {
        this.nrpBaja = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the numeroSemanaAseguramientoBaja property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getNumeroSemanaAseguramientoBaja() {
        return numeroSemanaAseguramientoBaja;
    }

    /**
     * Sets the value of the numeroSemanaAseguramientoBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setNumeroSemanaAseguramientoBaja(JAXBElement<Integer> value) {
        this.numeroSemanaAseguramientoBaja = ((JAXBElement<Integer> ) value);
    }

}
