
package mx.gob.imss.services;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaServVigGpoFamXListEst complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaServVigGpoFamXListEst">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="listaResultado" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="listaRespuesta" type="{http://services.imss.gob.mx/}InfoVigGpoFamXListEstVo" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "respuestaServVigGpoFamXListEst", propOrder = {
    "listaResultado"
})
public class RespuestaVigGpoFamXListEst
    extends RespuestaWSServVigGpoFamXListEst
{

    protected RespuestaVigGpoFamXListEst.ListaResultado listaResultado;

    /**
     * Gets the value of the listaResultado property.
     * 
     * @return
     *     possible object is
     *     {@link RespuestaVigGpoFamXListEst.ListaResultado }
     *     
     */
    public RespuestaVigGpoFamXListEst.ListaResultado getListaResultado() {
        return listaResultado;
    }

    /**
     * Sets the value of the listaResultado property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespuestaVigGpoFamXListEst.ListaResultado }
     *     
     */
    public void setListaResultado(RespuestaVigGpoFamXListEst.ListaResultado value) {
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
     *         &lt;element name="listaRespuesta" type="{http://services.imss.gob.mx/}InfoVigGpoFamXListEstVo" maxOccurs="unbounded" minOccurs="0"/>
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
        "listaRespuesta"
    })
    public static class ListaResultado {

        @XmlElement(nillable = true)
        protected List<InfoVigGpoFamXListEstVo> listaRespuesta;

        /**
         * Gets the value of the listaRespuesta property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the listaRespuesta property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getListaRespuesta().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InfoVigGpoFamXListEstVo }
         * 
         * 
         */
        public List<InfoVigGpoFamXListEstVo> getListaRespuesta() {
            if (listaRespuesta == null) {
                listaRespuesta = new ArrayList<InfoVigGpoFamXListEstVo>();
            }
            return this.listaRespuesta;
        }

    }

}
