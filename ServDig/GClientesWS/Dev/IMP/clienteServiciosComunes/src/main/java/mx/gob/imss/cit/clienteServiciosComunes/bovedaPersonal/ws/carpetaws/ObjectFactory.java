
package mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.carpetaws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddFolderActorRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddFolderActorResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateFolderRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateFolderResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDescendantsRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDescendantsResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDocumentsRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDocumentsResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderObjectsRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderObjectsResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.UserFolderRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.UserFolderResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.cit.bp.ws.carpetaws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetFolderDocumentsResponse_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "getFolderDocumentsResponse");
    private final static QName _CreateFolderResponse_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "createFolderResponse");
    private final static QName _GetFolderObjectsRequest_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "getFolderObjectsRequest");
    private final static QName _CreateFolderRequest_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "createFolderRequest");
    private final static QName _AddFolderActorRequest_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "addFolderActorRequest");
    private final static QName _GetUserFolderRequest_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "getUserFolderRequest");
    private final static QName _GetFolderDescendantsResponse_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "getFolderDescendantsResponse");
    private final static QName _GetFolderObjectsResponse_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "getFolderObjectsResponse");
    private final static QName _AddFolderActorResponse_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "addFolderActorResponse");
    private final static QName _GetFolderDocumentsRequest_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "getFolderDocumentsRequest");
    private final static QName _GetFolderDescendantsRequest_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "getFolderDescendantsRequest");
    private final static QName _GetUserFolderResponse_QNAME = new QName("http://carpetaws.ws.bp.cit.imss.gob.mx/", "getUserFolderResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.cit.bp.ws.carpetaws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FolderDocumentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "getFolderDocumentsResponse")
    public JAXBElement<FolderDocumentsResponse> createGetFolderDocumentsResponse(FolderDocumentsResponse value) {
        return new JAXBElement<FolderDocumentsResponse>(_GetFolderDocumentsResponse_QNAME, FolderDocumentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateFolderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "createFolderResponse")
    public JAXBElement<CreateFolderResponse> createCreateFolderResponse(CreateFolderResponse value) {
        return new JAXBElement<CreateFolderResponse>(_CreateFolderResponse_QNAME, CreateFolderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FolderObjectsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "getFolderObjectsRequest")
    public JAXBElement<FolderObjectsRequest> createGetFolderObjectsRequest(FolderObjectsRequest value) {
        return new JAXBElement<FolderObjectsRequest>(_GetFolderObjectsRequest_QNAME, FolderObjectsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateFolderRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "createFolderRequest")
    public JAXBElement<CreateFolderRequest> createCreateFolderRequest(CreateFolderRequest value) {
        return new JAXBElement<CreateFolderRequest>(_CreateFolderRequest_QNAME, CreateFolderRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddFolderActorRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "addFolderActorRequest")
    public JAXBElement<AddFolderActorRequest> createAddFolderActorRequest(AddFolderActorRequest value) {
        return new JAXBElement<AddFolderActorRequest>(_AddFolderActorRequest_QNAME, AddFolderActorRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserFolderRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "getUserFolderRequest")
    public JAXBElement<UserFolderRequest> createGetUserFolderRequest(UserFolderRequest value) {
        return new JAXBElement<UserFolderRequest>(_GetUserFolderRequest_QNAME, UserFolderRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FolderDescendantsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "getFolderDescendantsResponse")
    public JAXBElement<FolderDescendantsResponse> createGetFolderDescendantsResponse(FolderDescendantsResponse value) {
        return new JAXBElement<FolderDescendantsResponse>(_GetFolderDescendantsResponse_QNAME, FolderDescendantsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FolderObjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "getFolderObjectsResponse")
    public JAXBElement<FolderObjectsResponse> createGetFolderObjectsResponse(FolderObjectsResponse value) {
        return new JAXBElement<FolderObjectsResponse>(_GetFolderObjectsResponse_QNAME, FolderObjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddFolderActorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "addFolderActorResponse")
    public JAXBElement<AddFolderActorResponse> createAddFolderActorResponse(AddFolderActorResponse value) {
        return new JAXBElement<AddFolderActorResponse>(_AddFolderActorResponse_QNAME, AddFolderActorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FolderDocumentsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "getFolderDocumentsRequest")
    public JAXBElement<FolderDocumentsRequest> createGetFolderDocumentsRequest(FolderDocumentsRequest value) {
        return new JAXBElement<FolderDocumentsRequest>(_GetFolderDocumentsRequest_QNAME, FolderDocumentsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FolderDescendantsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "getFolderDescendantsRequest")
    public JAXBElement<FolderDescendantsRequest> createGetFolderDescendantsRequest(FolderDescendantsRequest value) {
        return new JAXBElement<FolderDescendantsRequest>(_GetFolderDescendantsRequest_QNAME, FolderDescendantsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserFolderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://carpetaws.ws.bp.cit.imss.gob.mx/", name = "getUserFolderResponse")
    public JAXBElement<UserFolderResponse> createGetUserFolderResponse(UserFolderResponse value) {
        return new JAXBElement<UserFolderResponse>(_GetUserFolderResponse_QNAME, UserFolderResponse.class, null, value);
    }

}
