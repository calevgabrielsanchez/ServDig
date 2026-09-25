
package mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Actor;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.BaseRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Document;


/**
 * <p>Java class for CreateDocumentRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateDocumentRequest">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.bp.cit.imss.gob.mx/bovedaPersonalCommonSchema}BaseRequest">
 *       &lt;sequence>
 *         &lt;element name="Document" type="{http://ws.bp.cit.imss.gob.mx/bovedaPersonalCommonSchema}Document"/>
 *         &lt;element name="Actor" type="{http://ws.bp.cit.imss.gob.mx/bovedaPersonalCommonSchema}Actor"/>
 *         &lt;element name="isEncripted" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateDocumentRequest", propOrder = {
    "document",
    "actor",
    "isEncripted"
})
public class CreateDocumentRequest
    extends BaseRequest
{

    @XmlElement(name = "Document", required = true)
    protected Document document;
    @XmlElement(name = "Actor", required = true)
    protected Actor actor;
    @XmlElement(required = true)
    protected String isEncripted;

    /**
     * Gets the value of the document property.
     * 
     * @return
     *     possible object is
     *     {@link Document }
     *     
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     * 
     * @param value
     *     allowed object is
     *     {@link Document }
     *     
     */
    public void setDocument(Document value) {
        this.document = value;
    }

    /**
     * Gets the value of the actor property.
     * 
     * @return
     *     possible object is
     *     {@link Actor }
     *     
     */
    public Actor getActor() {
        return actor;
    }

    /**
     * Sets the value of the actor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Actor }
     *     
     */
    public void setActor(Actor value) {
        this.actor = value;
    }

    /**
     * Gets the value of the isEncripted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsEncripted() {
        return isEncripted;
    }

    /**
     * Sets the value of the isEncripted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsEncripted(String value) {
        this.isEncripted = value;
    }

}
