
package mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.documentows;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddDocumentActorRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddDocumentActorResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsByDocRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsByDocResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsMetadataByDocRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsMetadataByDocResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllMetadataByMetadataRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllMetadataByMetadataResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateDocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateDocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DeleteDocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DeleteDocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentsByMetadataRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentsByMetadataResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.MetadataByDocRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.MetadataByDocResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.cit.bp.ws.documentows package. 
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

    private final static QName _DeleteDocumentRequest_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "deleteDocumentRequest");
    private final static QName _GetAllMetadataByMetadataResponse_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "getAllMetadataByMetadataResponse");
    private final static QName _GetMetadataByDocRequest_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "getMetadataByDocRequest");
    private final static QName _DeleteDocumentResponse_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "deleteDocumentResponse");
    private final static QName _GetAllDocumentVersionsByDocRequest_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "getAllDocumentVersionsByDocRequest");
    private final static QName _AddDocumentActorResponse_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "addDocumentActorResponse");
    private final static QName _CreateDocumentRequest_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "createDocumentRequest");
    private final static QName _GetDocumentRequest_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "getDocumentRequest");
    private final static QName _GetAllDocumentVersionsMetadataByDocRequest_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "getAllDocumentVersionsMetadataByDocRequest");
    private final static QName _GetAllDocumentVersionsMetadataByDocResponse_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "getAllDocumentVersionsMetadataByDocResponse");
    private final static QName _FindDocumentsByMetadataResponse_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "findDocumentsByMetadataResponse");
    private final static QName _GetAllMetadataByMetadataRequest_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "getAllMetadataByMetadataRequest");
    private final static QName _GetMetadataByDocResponse_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "getMetadataByDocResponse");
    private final static QName _CreateDocumentResponse_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "createDocumentResponse");
    private final static QName _AddDocumentActorRequest_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "addDocumentActorRequest");
    private final static QName _GetAllDocumentVersionsByDocResponse_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "getAllDocumentVersionsByDocResponse");
    private final static QName _GetDocumentResponse_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "getDocumentResponse");
    private final static QName _FindDocumentsByMetadataRequest_QNAME = new QName("http://documentows.ws.bp.cit.imss.gob.mx/", "findDocumentsByMetadataRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.cit.bp.ws.documentows
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteDocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "deleteDocumentRequest")
    public JAXBElement<DeleteDocumentRequest> createDeleteDocumentRequest(DeleteDocumentRequest value) {
        return new JAXBElement<DeleteDocumentRequest>(_DeleteDocumentRequest_QNAME, DeleteDocumentRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllMetadataByMetadataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "getAllMetadataByMetadataResponse")
    public JAXBElement<AllMetadataByMetadataResponse> createGetAllMetadataByMetadataResponse(AllMetadataByMetadataResponse value) {
        return new JAXBElement<AllMetadataByMetadataResponse>(_GetAllMetadataByMetadataResponse_QNAME, AllMetadataByMetadataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetadataByDocRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "getMetadataByDocRequest")
    public JAXBElement<MetadataByDocRequest> createGetMetadataByDocRequest(MetadataByDocRequest value) {
        return new JAXBElement<MetadataByDocRequest>(_GetMetadataByDocRequest_QNAME, MetadataByDocRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "deleteDocumentResponse")
    public JAXBElement<DeleteDocumentResponse> createDeleteDocumentResponse(DeleteDocumentResponse value) {
        return new JAXBElement<DeleteDocumentResponse>(_DeleteDocumentResponse_QNAME, DeleteDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllDocumentVersionsByDocRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "getAllDocumentVersionsByDocRequest")
    public JAXBElement<AllDocumentVersionsByDocRequest> createGetAllDocumentVersionsByDocRequest(AllDocumentVersionsByDocRequest value) {
        return new JAXBElement<AllDocumentVersionsByDocRequest>(_GetAllDocumentVersionsByDocRequest_QNAME, AllDocumentVersionsByDocRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDocumentActorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "addDocumentActorResponse")
    public JAXBElement<AddDocumentActorResponse> createAddDocumentActorResponse(AddDocumentActorResponse value) {
        return new JAXBElement<AddDocumentActorResponse>(_AddDocumentActorResponse_QNAME, AddDocumentActorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "createDocumentRequest")
    public JAXBElement<CreateDocumentRequest> createCreateDocumentRequest(CreateDocumentRequest value) {
        return new JAXBElement<CreateDocumentRequest>(_CreateDocumentRequest_QNAME, CreateDocumentRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "getDocumentRequest")
    public JAXBElement<DocumentRequest> createGetDocumentRequest(DocumentRequest value) {
        return new JAXBElement<DocumentRequest>(_GetDocumentRequest_QNAME, DocumentRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllDocumentVersionsMetadataByDocRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "getAllDocumentVersionsMetadataByDocRequest")
    public JAXBElement<AllDocumentVersionsMetadataByDocRequest> createGetAllDocumentVersionsMetadataByDocRequest(AllDocumentVersionsMetadataByDocRequest value) {
        return new JAXBElement<AllDocumentVersionsMetadataByDocRequest>(_GetAllDocumentVersionsMetadataByDocRequest_QNAME, AllDocumentVersionsMetadataByDocRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllDocumentVersionsMetadataByDocResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "getAllDocumentVersionsMetadataByDocResponse")
    public JAXBElement<AllDocumentVersionsMetadataByDocResponse> createGetAllDocumentVersionsMetadataByDocResponse(AllDocumentVersionsMetadataByDocResponse value) {
        return new JAXBElement<AllDocumentVersionsMetadataByDocResponse>(_GetAllDocumentVersionsMetadataByDocResponse_QNAME, AllDocumentVersionsMetadataByDocResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentsByMetadataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "findDocumentsByMetadataResponse")
    public JAXBElement<DocumentsByMetadataResponse> createFindDocumentsByMetadataResponse(DocumentsByMetadataResponse value) {
        return new JAXBElement<DocumentsByMetadataResponse>(_FindDocumentsByMetadataResponse_QNAME, DocumentsByMetadataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllMetadataByMetadataRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "getAllMetadataByMetadataRequest")
    public JAXBElement<AllMetadataByMetadataRequest> createGetAllMetadataByMetadataRequest(AllMetadataByMetadataRequest value) {
        return new JAXBElement<AllMetadataByMetadataRequest>(_GetAllMetadataByMetadataRequest_QNAME, AllMetadataByMetadataRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MetadataByDocResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "getMetadataByDocResponse")
    public JAXBElement<MetadataByDocResponse> createGetMetadataByDocResponse(MetadataByDocResponse value) {
        return new JAXBElement<MetadataByDocResponse>(_GetMetadataByDocResponse_QNAME, MetadataByDocResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "createDocumentResponse")
    public JAXBElement<CreateDocumentResponse> createCreateDocumentResponse(CreateDocumentResponse value) {
        return new JAXBElement<CreateDocumentResponse>(_CreateDocumentResponse_QNAME, CreateDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDocumentActorRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "addDocumentActorRequest")
    public JAXBElement<AddDocumentActorRequest> createAddDocumentActorRequest(AddDocumentActorRequest value) {
        return new JAXBElement<AddDocumentActorRequest>(_AddDocumentActorRequest_QNAME, AddDocumentActorRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllDocumentVersionsByDocResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "getAllDocumentVersionsByDocResponse")
    public JAXBElement<AllDocumentVersionsByDocResponse> createGetAllDocumentVersionsByDocResponse(AllDocumentVersionsByDocResponse value) {
        return new JAXBElement<AllDocumentVersionsByDocResponse>(_GetAllDocumentVersionsByDocResponse_QNAME, AllDocumentVersionsByDocResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "getDocumentResponse")
    public JAXBElement<DocumentResponse> createGetDocumentResponse(DocumentResponse value) {
        return new JAXBElement<DocumentResponse>(_GetDocumentResponse_QNAME, DocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentsByMetadataRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://documentows.ws.bp.cit.imss.gob.mx/", name = "findDocumentsByMetadataRequest")
    public JAXBElement<DocumentsByMetadataRequest> createFindDocumentsByMetadataRequest(DocumentsByMetadataRequest value) {
        return new JAXBElement<DocumentsByMetadataRequest>(_FindDocumentsByMetadataRequest_QNAME, DocumentsByMetadataRequest.class, null, value);
    }

}
