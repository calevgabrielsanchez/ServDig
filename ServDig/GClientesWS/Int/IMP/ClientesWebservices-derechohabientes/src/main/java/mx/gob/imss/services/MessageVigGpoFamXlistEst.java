
package mx.gob.imss.services;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="cveIdAsignacionNSS" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="listaEstadosDerechohabientes" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="listaEstados" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "cveIdAsignacionNSS",
    "listaEstadosDerechohabientes"
})
public class MessageVigGpoFamXlistEst {

    protected Integer cveIdAsignacionNSS;
    protected MessageVigGpoFamXlistEst.ListaEstadosDerechohabientes listaEstadosDerechohabientes;

    /**
     * Gets the value of the cveIdAsignacionNSS property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveIdAsignacionNSS() {
        return cveIdAsignacionNSS;
    }

    /**
     * Sets the value of the cveIdAsignacionNSS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveIdAsignacionNSS(Integer value) {
        this.cveIdAsignacionNSS = value;
    }

    /**
     * Gets the value of the listaEstadosDerechohabientes property.
     * 
     * @return
     *     possible object is
     *     {@link Message.ListaEstadosDerechohabientes }
     *     
     */
    public MessageVigGpoFamXlistEst.ListaEstadosDerechohabientes getListaEstadosDerechohabientes() {
        return listaEstadosDerechohabientes;
    }

    /**
     * Sets the value of the listaEstadosDerechohabientes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Message.ListaEstadosDerechohabientes }
     *     
     */
    public void setListaEstadosDerechohabientes(MessageVigGpoFamXlistEst.ListaEstadosDerechohabientes value) {
        this.listaEstadosDerechohabientes = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="listaEstados" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "listaEstados"
    })
    public static class ListaEstadosDerechohabientes {

        @XmlElement(nillable = true)
        protected List<Integer> listaEstados;

        /**
         * Gets the value of the listaEstados property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the listaEstados property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getListaEstados().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Integer }
         * 
         * 
         */
        public List<Integer> getListaEstados() {
            if (listaEstados == null) {
                listaEstados = new ArrayList<Integer>();
            }
            return this.listaEstados;
        }

    }

}
