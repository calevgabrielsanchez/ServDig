
package mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.BaseResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Document;


/**
 * <p>Java class for FolderDocumentsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FolderDocumentsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.bp.cit.imss.gob.mx/bovedaPersonalCommonSchema}BaseResponse">
 *       &lt;sequence>
 *         &lt;element name="Response" type="{http://ws.bp.cit.imss.gob.mx/bovedaPersonalCommonSchema}Document" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FolderDocumentsResponse", propOrder = {
    "response"
})
public class FolderDocumentsResponse
    extends BaseResponse
{

    @XmlElement(name = "Response", required = true)
    protected List<Document> response;

    /**
     * Gets the value of the response property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the response property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Document }
     * 
     * 
     */
    public List<Document> getResponse() {
        if (response == null) {
            response = new ArrayList<Document>();
        }
        return this.response;
    }

}
