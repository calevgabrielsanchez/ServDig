
package mx.gob.imss.ctirss.ws.asignacion.externo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.ctirss.ws.asignacion.externo package. 
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

    private final static QName _GetInfoByNss_QNAME = new QName("http://soap.resource.ado.imss.gob.mx/", "getInfoByNss");
    private final static QName _GetInfoByNssResponse_QNAME = new QName("http://soap.resource.ado.imss.gob.mx/", "getInfoByNssResponse");
    private final static QName _InfoNssPasoacambioNombre_QNAME = new QName("", "nombre");
    private final static QName _InfoNssPasoacambioMaterno_QNAME = new QName("", "materno");
    private final static QName _InfoNssPasoacambioNss_QNAME = new QName("", "nss");
    private final static QName _InfoNssPasoacambioPaterno_QNAME = new QName("", "paterno");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.ado.resource.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetInfoByNssResponse }
     * 
     */
    public GetInfoByNssResponse createGetInfoByNssResponse() {
        return new GetInfoByNssResponse();
    }

    /**
     * Create an instance of {@link GetInfoByNss }
     * 
     */
    public GetInfoByNss createGetInfoByNss() {
        return new GetInfoByNss();
    }

    /**
     * Create an instance of {@link ResponseNssPasoacambioResource }
     * 
     */
    public ResponseNssPasoacambioResource createResponseNssPasoacambioResource() {
        return new ResponseNssPasoacambioResource();
    }

    /**
     * Create an instance of {@link ResponseResource }
     * 
     */
    public ResponseResource createResponseResource() {
        return new ResponseResource();
    }

    /**
     * Create an instance of {@link InfoNssPasoacambio }
     * 
     */
    public InfoNssPasoacambio createInfoNssPasoacambio() {
        return new InfoNssPasoacambio();
    }

    /**
     * Create an instance of {@link ResponseNssPasoacambioResource.ListInfoNssPasoacambio }
     * 
     */
    public ResponseNssPasoacambioResource.ListInfoNssPasoacambio createResponseNssPasoacambioResourceListInfoNssPasoacambio() {
        return new ResponseNssPasoacambioResource.ListInfoNssPasoacambio();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoByNss }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.resource.ado.imss.gob.mx/", name = "getInfoByNss")
    public JAXBElement<GetInfoByNss> createGetInfoByNss(GetInfoByNss value) {
        return new JAXBElement<GetInfoByNss>(_GetInfoByNss_QNAME, GetInfoByNss.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoByNssResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.resource.ado.imss.gob.mx/", name = "getInfoByNssResponse")
    public JAXBElement<GetInfoByNssResponse> createGetInfoByNssResponse(GetInfoByNssResponse value) {
        return new JAXBElement<GetInfoByNssResponse>(_GetInfoByNssResponse_QNAME, GetInfoByNssResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nombre", scope = InfoNssPasoacambio.class)
    public JAXBElement<String> createInfoNssPasoacambioNombre(String value) {
        return new JAXBElement<String>(_InfoNssPasoacambioNombre_QNAME, String.class, InfoNssPasoacambio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "materno", scope = InfoNssPasoacambio.class)
    public JAXBElement<String> createInfoNssPasoacambioMaterno(String value) {
        return new JAXBElement<String>(_InfoNssPasoacambioMaterno_QNAME, String.class, InfoNssPasoacambio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nss", scope = InfoNssPasoacambio.class)
    public JAXBElement<String> createInfoNssPasoacambioNss(String value) {
        return new JAXBElement<String>(_InfoNssPasoacambioNss_QNAME, String.class, InfoNssPasoacambio.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "paterno", scope = InfoNssPasoacambio.class)
    public JAXBElement<String> createInfoNssPasoacambioPaterno(String value) {
        return new JAXBElement<String>(_InfoNssPasoacambioPaterno_QNAME, String.class, InfoNssPasoacambio.class, value);
    }

}
