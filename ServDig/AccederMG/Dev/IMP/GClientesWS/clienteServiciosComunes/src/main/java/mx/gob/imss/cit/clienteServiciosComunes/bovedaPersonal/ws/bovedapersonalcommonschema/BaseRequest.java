
package mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddDocumentActorRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddFolderActorRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsByDocRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsMetadataByDocRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllMetadataByMetadataRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateDocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateFolderRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DeleteDocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentsByMetadataRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDescendantsRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDocumentsRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderObjectsRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.MetadataByDocRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.UserFolderRequest;
import mx.gob.imss.cit.clienteServiciosComunes.ws.commonschema.GovernanceHeaderRequest;


/**
 * <p>Java class for BaseRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="governanceHeaderRequest" type="{http://cit.imss.gob.mx/ws/commonSchema}governanceHeaderRequest"/>
 *         &lt;element name="Tramite" type="{http://ws.bp.cit.imss.gob.mx/bovedaPersonalCommonSchema}Tramite"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseRequest", propOrder = {
    "governanceHeaderRequest",
    "tramite"
})
@XmlSeeAlso({
    FolderDocumentsRequest.class,
    AddDocumentActorRequest.class,
    AddFolderActorRequest.class,
    FolderDescendantsRequest.class,
    UserFolderRequest.class,
    CreateDocumentRequest.class,
    DocumentsByMetadataRequest.class,
    CreateFolderRequest.class,
    DeleteDocumentRequest.class,
    AllMetadataByMetadataRequest.class,
    MetadataByDocRequest.class,
    DocumentRequest.class,
    FolderObjectsRequest.class,
    AllDocumentVersionsByDocRequest.class,
    AllDocumentVersionsMetadataByDocRequest.class
})
public class BaseRequest {

    @XmlElement(required = true)
    protected GovernanceHeaderRequest governanceHeaderRequest;
    @XmlElement(name = "Tramite", required = true)
    protected Tramite tramite;

    /**
     * Gets the value of the governanceHeaderRequest property.
     * 
     * @return
     *     possible object is
     *     {@link GovernanceHeaderRequest }
     *     
     */
    public GovernanceHeaderRequest getGovernanceHeaderRequest() {
        return governanceHeaderRequest;
    }

    /**
     * Sets the value of the governanceHeaderRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link GovernanceHeaderRequest }
     *     
     */
    public void setGovernanceHeaderRequest(GovernanceHeaderRequest value) {
        this.governanceHeaderRequest = value;
    }

    /**
     * Gets the value of the tramite property.
     * 
     * @return
     *     possible object is
     *     {@link Tramite }
     *     
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * Sets the value of the tramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tramite }
     *     
     */
    public void setTramite(Tramite value) {
        this.tramite = value;
    }

}
