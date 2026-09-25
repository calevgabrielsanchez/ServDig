
package mx.gob.imss.consulta.tramites.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for message complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="message">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificadorFuente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="registroAInsertar" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="identificadorOperacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "message", propOrder = {
    "identificadorFuente",
    "registroAInsertar",
    "identificadorOperacion"
})
public class Message {

    @XmlElement(required = true)
    protected String identificadorFuente;
    @XmlElement(required = true)
    protected String registroAInsertar;
    @XmlElement(required = true)
    protected String identificadorOperacion;

    /**
     * Gets the value of the identificadorFuente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificadorFuente() {
        return identificadorFuente;
    }

    /**
     * Sets the value of the identificadorFuente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificadorFuente(String value) {
        this.identificadorFuente = value;
    }

    /**
     * Gets the value of the registroAInsertar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistroAInsertar() {
        return registroAInsertar;
    }

    /**
     * Sets the value of the registroAInsertar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistroAInsertar(String value) {
        this.registroAInsertar = value;
    }

    /**
     * Gets the value of the identificadorOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificadorOperacion() {
        return identificadorOperacion;
    }

    /**
     * Sets the value of the identificadorOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificadorOperacion(String value) {
        this.identificadorOperacion = value;
    }

}
