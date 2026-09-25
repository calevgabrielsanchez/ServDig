
package mx.gob.imss.cit.clienteswebservices.externo.jonima;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.cit.clienteswebservices.externo.jonima package. 
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

    private final static QName _AuthenticateResponse_QNAME = new QName("http://servicios.flujocontrol.com/", "AuthenticateResponse");
    private final static QName _ExecuteProcedure_QNAME = new QName("http://servicios.flujocontrol.com/", "ExecuteProcedure");
    private final static QName _RefreshToken_QNAME = new QName("http://servicios.flujocontrol.com/", "RefreshToken");
    private final static QName _Authenticate_QNAME = new QName("http://servicios.flujocontrol.com/", "Authenticate");
    private final static QName _CreateDocumentResponse_QNAME = new QName("http://servicios.flujocontrol.com/", "CreateDocumentResponse");
    private final static QName _RefreshTokenResponse_QNAME = new QName("http://servicios.flujocontrol.com/", "RefreshTokenResponse");
    private final static QName _CreateDocument_QNAME = new QName("http://servicios.flujocontrol.com/", "CreateDocument");
    private final static QName _CreateUserResponse_QNAME = new QName("http://servicios.flujocontrol.com/", "CreateUserResponse");
    private final static QName _CreateUser_QNAME = new QName("http://servicios.flujocontrol.com/", "CreateUser");
    private final static QName _ExecuteProcedureResponse_QNAME = new QName("http://servicios.flujocontrol.com/", "ExecuteProcedureResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.cit.clienteswebservices.externo.jonima
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RefreshTokenResponse }
     * 
     */
    public RefreshTokenResponse createRefreshTokenResponse() {
        return new RefreshTokenResponse();
    }

    /**
     * Create an instance of {@link CreateUserResponse1 }
     * 
     */
    public CreateUserResponse1 createCreateUserResponse1() {
        return new CreateUserResponse1();
    }

    /**
     * Create an instance of {@link RefreshToken }
     * 
     */
    public RefreshToken createRefreshToken() {
        return new RefreshToken();
    }

    /**
     * Create an instance of {@link ExecuteProcedureResponse1 }
     * 
     */
    public ExecuteProcedureResponse1 createExecuteProcedureResponse1() {
        return new ExecuteProcedureResponse1();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link Authenticate }
     * 
     */
    public Authenticate createAuthenticate() {
        return new Authenticate();
    }

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link CreateDocumentResponse1 }
     * 
     */
    public CreateDocumentResponse1 createCreateDocumentResponse1() {
        return new CreateDocumentResponse1();
    }

    /**
     * Create an instance of {@link AuthenticateResponse }
     * 
     */
    public AuthenticateResponse createAuthenticateResponse() {
        return new AuthenticateResponse();
    }

    /**
     * Create an instance of {@link ExecuteProcedure1 }
     * 
     */
    public ExecuteProcedure1 createExecuteProcedure1() {
        return new ExecuteProcedure1();
    }

    /**
     * Create an instance of {@link CreateDocumentResponse }
     * 
     */
    public CreateDocumentResponse createCreateDocumentResponse() {
        return new CreateDocumentResponse();
    }

    /**
     * Create an instance of {@link CreateDocument }
     * 
     */
    public CreateDocument createCreateDocument() {
        return new CreateDocument();
    }

    /**
     * Create an instance of {@link ExecuteProcedureResponse }
     * 
     */
    public ExecuteProcedureResponse createExecuteProcedureResponse() {
        return new ExecuteProcedureResponse();
    }

    /**
     * Create an instance of {@link ExecuteProcedure }
     * 
     */
    public ExecuteProcedure createExecuteProcedure() {
        return new ExecuteProcedure();
    }

    /**
     * Create an instance of {@link CreateDocument1 }
     * 
     */
    public CreateDocument1 createCreateDocument1() {
        return new CreateDocument1();
    }

    /**
     * Create an instance of {@link DocumentFields }
     * 
     */
    public DocumentFields createDocumentFields() {
        return new DocumentFields();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.flujocontrol.com/", name = "AuthenticateResponse")
    public JAXBElement<AuthenticateResponse> createAuthenticateResponse(AuthenticateResponse value) {
        return new JAXBElement<AuthenticateResponse>(_AuthenticateResponse_QNAME, AuthenticateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteProcedure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.flujocontrol.com/", name = "ExecuteProcedure")
    public JAXBElement<ExecuteProcedure> createExecuteProcedure(ExecuteProcedure value) {
        return new JAXBElement<ExecuteProcedure>(_ExecuteProcedure_QNAME, ExecuteProcedure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshToken }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.flujocontrol.com/", name = "RefreshToken")
    public JAXBElement<RefreshToken> createRefreshToken(RefreshToken value) {
        return new JAXBElement<RefreshToken>(_RefreshToken_QNAME, RefreshToken.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Authenticate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.flujocontrol.com/", name = "Authenticate")
    public JAXBElement<Authenticate> createAuthenticate(Authenticate value) {
        return new JAXBElement<Authenticate>(_Authenticate_QNAME, Authenticate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.flujocontrol.com/", name = "CreateDocumentResponse")
    public JAXBElement<CreateDocumentResponse> createCreateDocumentResponse(CreateDocumentResponse value) {
        return new JAXBElement<CreateDocumentResponse>(_CreateDocumentResponse_QNAME, CreateDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RefreshTokenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.flujocontrol.com/", name = "RefreshTokenResponse")
    public JAXBElement<RefreshTokenResponse> createRefreshTokenResponse(RefreshTokenResponse value) {
        return new JAXBElement<RefreshTokenResponse>(_RefreshTokenResponse_QNAME, RefreshTokenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.flujocontrol.com/", name = "CreateDocument")
    public JAXBElement<CreateDocument> createCreateDocument(CreateDocument value) {
        return new JAXBElement<CreateDocument>(_CreateDocument_QNAME, CreateDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.flujocontrol.com/", name = "CreateUserResponse")
    public JAXBElement<CreateUserResponse> createCreateUserResponse(CreateUserResponse value) {
        return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME, CreateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.flujocontrol.com/", name = "CreateUser")
    public JAXBElement<CreateUser> createCreateUser(CreateUser value) {
        return new JAXBElement<CreateUser>(_CreateUser_QNAME, CreateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicios.flujocontrol.com/", name = "ExecuteProcedureResponse")
    public JAXBElement<ExecuteProcedureResponse> createExecuteProcedureResponse(ExecuteProcedureResponse value) {
        return new JAXBElement<ExecuteProcedureResponse>(_ExecuteProcedureResponse_QNAME, ExecuteProcedureResponse.class, null, value);
    }

}
