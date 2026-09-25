package mx.gob.imss.cit.clienteServiciosComunes.consultaPatron.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoPatronEntrada complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoPatronEntrada">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="digitoVerificador" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modalidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="registroPatronal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoPatronEntrada", propOrder = {
    "digitoVerificador",
    "modalidad",
    "registroPatronal"
})
public class InfoPatronEntrada {

    @XmlElement(required = true, nillable = true)
    protected String digitoVerificador;
    @XmlElement(required = true, nillable = true)
    protected String modalidad;
    @XmlElement(required = true, nillable = true)
    protected String registroPatronal;

    /**
     * Gets the value of the digitoVerificador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    /**
     * Sets the value of the digitoVerificador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigitoVerificador(String value) {
        this.digitoVerificador = value;
    }

    /**
     * Gets the value of the modalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * Sets the value of the modalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModalidad(String value) {
        this.modalidad = value;
    }

    /**
     * Gets the value of the registroPatronal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistroPatronal() {
        return registroPatronal;
    }

    /**
     * Sets the value of the registroPatronal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistroPatronal(String value) {
        this.registroPatronal = value;
    }

}
