
package mx.gob.imss.service.vigenciagrupofamparen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaWSConsVigGpoFamXParen complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaWSConsVigGpoFamXParen">
 *   &lt;complexContent>
 *     &lt;extension base="{http://vigenciagrupofamparen.service.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="listaResultados" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="listInfoConsVigGpoFamXParen" type="{http://vigenciagrupofamparen.service.imss.gob.mx/}InfoConsVigGpoFamXParen" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaWSConsVigGpoFamXParen", propOrder = {
    "listaResultados"
})
public class RespuestaWSConsVigGpoFamXParen
    extends RespuestaWS
{

    protected RespuestaWSConsVigGpoFamXParen.ListaResultados listaResultados;

    /**
     * Gets the value of the listaResultados property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaWSConsVigGpoFamXParen.ListaResultados }
     *     
     */
    public RespuestaWSConsVigGpoFamXParen.ListaResultados getListaResultados() {
        return listaResultados;
    }

    /**
     * Sets the value of the listaResultados property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaWSConsVigGpoFamXParen.ListaResultados }
     *     
     */
    public void setListaResultados(RespuestaWSConsVigGpoFamXParen.ListaResultados value) {
        this.listaResultados = value;
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
     *         &lt;element name="listInfoConsVigGpoFamXParen" type="{http://vigenciagrupofamparen.service.imss.gob.mx/}InfoConsVigGpoFamXParen" maxOccurs="unbounded" minOccurs="0"/>
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
        "listInfoConsVigGpoFamXParen"
    })
    public static class ListaResultados {

        @XmlElement(nillable = true)
        protected List<InfoConsVigGpoFamXParen> listInfoConsVigGpoFamXParen;

        /**
         * Gets the value of the listInfoConsVigGpoFamXParen property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the listInfoConsVigGpoFamXParen property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getListInfoConsVigGpoFamXParen().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InfoConsVigGpoFamXParen }
         * 
         * 
         */
        public List<InfoConsVigGpoFamXParen> getListInfoConsVigGpoFamXParen() {
            if (listInfoConsVigGpoFamXParen == null) {
                listInfoConsVigGpoFamXParen = new ArrayList<InfoConsVigGpoFamXParen>();
            }
            return this.listInfoConsVigGpoFamXParen;
        }

    }

}
