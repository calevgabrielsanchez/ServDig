
package mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddFolderActorResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsByDocResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsMetadataByDocResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllMetadataByMetadataResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateFolderResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentsByMetadataResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDescendantsResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDocumentsResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderObjectsResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.MetadataByDocResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.UserFolderResponse;
import mx.gob.imss.cit.clienteServiciosComunes.ws.commonschema.GovernanceHeaderResponse;


/**
 * <p>Java class for BaseResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="governanceHeaderResponse" type="{http://cit.imss.gob.mx/ws/commonSchema}governanceHeaderResponse"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseResponse", propOrder = {
    "governanceHeaderResponse"
})
@XmlSeeAlso({
    DocumentResponse.class,
    CreateFolderResponse.class,
    DocumentsByMetadataResponse.class,
    MetadataByDocResponse.class,
    AddFolderActorResponse.class,
    FolderDescendantsResponse.class,
    AllDocumentVersionsMetadataByDocResponse.class,
    AllDocumentVersionsByDocResponse.class,
    UserFolderResponse.class,
    FolderObjectsResponse.class,
    FolderDocumentsResponse.class,
    AllMetadataByMetadataResponse.class
})
public class BaseResponse {

    @XmlElement(required = true)
    protected GovernanceHeaderResponse governanceHeaderResponse;

    /**
     * Gets the value of the governanceHeaderResponse property.
     * 
     * @return
     *     possible object is
     *     {@link GovernanceHeaderResponse }
     *     
     */
    public GovernanceHeaderResponse getGovernanceHeaderResponse() {
        return governanceHeaderResponse;
    }

    /**
     * Sets the value of the governanceHeaderResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link GovernanceHeaderResponse }
     *     
     */
    public void setGovernanceHeaderResponse(GovernanceHeaderResponse value) {
        this.governanceHeaderResponse = value;
    }

}
