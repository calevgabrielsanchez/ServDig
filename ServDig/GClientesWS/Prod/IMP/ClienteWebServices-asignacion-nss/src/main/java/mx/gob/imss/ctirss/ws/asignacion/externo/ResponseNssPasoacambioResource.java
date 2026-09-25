
package mx.gob.imss.ctirss.ws.asignacion.externo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para responseNssPasoacambioResource complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="responseNssPasoacambioResource">
 *   &lt;complexContent>
 *     &lt;extension base="{http://soap.resource.ado.imss.gob.mx/}responseResource">
 *       &lt;sequence>
 *         &lt;element name="listInfoNssPasoacambio" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="listInfo" type="{http://soap.resource.ado.imss.gob.mx/}InfoNssPasoacambio" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "responseNssPasoacambioResource", propOrder = {
    "listInfoNssPasoacambio"
})
public class ResponseNssPasoacambioResource
    extends ResponseResource
{

    protected ResponseNssPasoacambioResource.ListInfoNssPasoacambio listInfoNssPasoacambio;

    /**
     * Obtiene el valor de la propiedad listInfoNssPasoacambio.
     * 
     * @return
     *     possible object is
     *     {@link ResponseNssPasoacambioResource.ListInfoNssPasoacambio }
     *     
     */
    public ResponseNssPasoacambioResource.ListInfoNssPasoacambio getListInfoNssPasoacambio() {
        return listInfoNssPasoacambio;
    }

    /**
     * Define el valor de la propiedad listInfoNssPasoacambio.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseNssPasoacambioResource.ListInfoNssPasoacambio }
     *     
     */
    public void setListInfoNssPasoacambio(ResponseNssPasoacambioResource.ListInfoNssPasoacambio value) {
        this.listInfoNssPasoacambio = value;
    }


    /**
     * <p>Clase Java para anonymous complex type.
     * 
     * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="listInfo" type="{http://soap.resource.ado.imss.gob.mx/}InfoNssPasoacambio" maxOccurs="unbounded" minOccurs="0"/>
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
        "listInfo"
    })
    public static class ListInfoNssPasoacambio {

        @XmlElement(nillable = true)
        protected List<InfoNssPasoacambio> listInfo;

        /**
         * Gets the value of the listInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the listInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getListInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InfoNssPasoacambio }
         * 
         * 
         */
        public List<InfoNssPasoacambio> getListInfo() {
            if (listInfo == null) {
                listInfo = new ArrayList<InfoNssPasoacambio>();
            }
            return this.listInfo;
        }

    }

}
