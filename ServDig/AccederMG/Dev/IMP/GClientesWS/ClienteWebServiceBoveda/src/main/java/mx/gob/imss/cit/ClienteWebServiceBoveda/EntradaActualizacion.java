
package mx.gob.imss.cit.ClienteWebServiceBoveda;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EntradaActualizacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EntradaActualizacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="documento" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="atributo" type="{http://www.openuri.org/}Atributo" maxOccurs="unbounded"/>
 *         &lt;element name="versionado" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntradaActualizacion", propOrder = {
    "idDocumento",
    "documento",
    "atributo",
    "versionado"
})
public class EntradaActualizacion {

    @XmlElement(required = true)
    protected String idDocumento;
    @XmlElement(required = true)
    protected byte[] documento;
    @XmlElement(required = true)
    protected List<Atributo> atributo;
    protected Boolean versionado;

    /**
     * Gets the value of the idDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDocumento() {
        return idDocumento;
    }

    /**
     * Sets the value of the idDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDocumento(String value) {
        this.idDocumento = value;
    }

    /**
     * Gets the value of the documento property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDocumento() {
        return documento;
    }

    /**
     * Sets the value of the documento property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDocumento(byte[] value) {
        this.documento = value;
    }

    /**
     * Gets the value of the atributo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the atributo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAtributo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Atributo }
     * 
     * 
     */
    public List<Atributo> getAtributo() {
        if (atributo == null) {
            atributo = new ArrayList<Atributo>();
        }
        return this.atributo;
    }

    /**
     * Gets the value of the versionado property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isVersionado() {
        return versionado;
    }

    /**
     * Sets the value of the versionado property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVersionado(Boolean value) {
        this.versionado = value;
    }

}
