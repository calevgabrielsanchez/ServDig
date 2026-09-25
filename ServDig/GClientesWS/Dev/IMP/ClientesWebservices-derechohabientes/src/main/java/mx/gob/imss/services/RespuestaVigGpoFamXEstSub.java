
package mx.gob.imss.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaServVigGpoFamXEstSub complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaServVigGpoFamXEstSub">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="listaResultado" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="lstInfoPersonas" type="{http://services.imss.gob.mx/}InfPersonaGpoFamiliarVo" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "respuestaServVigGpoFamXEstSub", propOrder = {
    "listaResultado"
})
public class RespuestaVigGpoFamXEstSub
    extends RespuestaWSServVigGpoFamXEst
{

    protected RespuestaVigGpoFamXEstSub.ListaResultado listaResultado;

    /**
     * Gets the value of the listaResultado property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaVigGpoFamXEstSub.ListaResultado }
     *     
     */
    public RespuestaVigGpoFamXEstSub.ListaResultado getListaResultado() {
        return listaResultado;
    }

    /**
     * Sets the value of the listaResultado property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaVigGpoFamXEstSub.ListaResultado }
     *     
     */
    public void setListaResultado(RespuestaVigGpoFamXEstSub.ListaResultado value) {
        this.listaResultado = value;
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
     *         &lt;element name="lstInfoPersonas" type="{http://services.imss.gob.mx/}InfPersonaGpoFamiliarVo" maxOccurs="unbounded" minOccurs="0"/>
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
        "lstInfoPersonas"
    })
    public static class ListaResultado {

        @XmlElement(nillable = true)
        protected List<InfPersonaGpoFamiliarVo> lstInfoPersonas;

        /**
         * Gets the value of the lstInfoPersonas property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the lstInfoPersonas property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getLstInfoPersonas().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InfPersonaGpoFamiliarVo }
         * 
         * 
         */
        public List<InfPersonaGpoFamiliarVo> getLstInfoPersonas() {
            if (lstInfoPersonas == null) {
                lstInfoPersonas = new ArrayList<InfPersonaGpoFamiliarVo>();
            }
            return this.lstInfoPersonas;
        }

    }

}
