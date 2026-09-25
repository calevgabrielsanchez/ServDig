
package mx.gob.imss.macii.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.macii.ws package. 
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

    private final static QName _GetInfoMACII_QNAME = new QName("http://ws.macii.imss.gob.mx/", "getInfoMACII");
    private final static QName _GetInfoMACIIResponse_QNAME = new QName("http://ws.macii.imss.gob.mx/", "getInfoMACIIResponse");
    private final static QName _EntradaMACII_QNAME = new QName("http://ws.macii.imss.gob.mx/", "EntradaMACII");
    private final static QName _RespuestaWSCODIGOERROR_QNAME = new QName("", "CODIGO_ERROR");
    private final static QName _RespuestaWSMENSAJEERROR_QNAME = new QName("", "MENSAJE_ERROR");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.macii.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EntradaMACII }
     * 
     */
    public EntradaMACII createEntradaMACII() {
        return new EntradaMACII();
    }

    /**
     * Create an instance of {@link GetInfoMACIIResponse }
     * 
     */
    public GetInfoMACIIResponse createGetInfoMACIIResponse() {
        return new GetInfoMACIIResponse();
    }

    /**
     * Create an instance of {@link GetInfoMACII }
     * 
     */
    public GetInfoMACII createGetInfoMACII() {
        return new GetInfoMACII();
    }

    /**
     * Create an instance of {@link RespuestaMACII }
     * 
     */
    public RespuestaMACII createRespuestaMACII() {
        return new RespuestaMACII();
    }

    /**
     * Create an instance of {@link InfoMACIIVO }
     * 
     */
    public InfoMACIIVO createInfoMACIIVO() {
        return new InfoMACIIVO();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoMACII }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.macii.imss.gob.mx/", name = "getInfoMACII")
    public JAXBElement<GetInfoMACII> createGetInfoMACII(GetInfoMACII value) {
        return new JAXBElement<GetInfoMACII>(_GetInfoMACII_QNAME, GetInfoMACII.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInfoMACIIResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.macii.imss.gob.mx/", name = "getInfoMACIIResponse")
    public JAXBElement<GetInfoMACIIResponse> createGetInfoMACIIResponse(GetInfoMACIIResponse value) {
        return new JAXBElement<GetInfoMACIIResponse>(_GetInfoMACIIResponse_QNAME, GetInfoMACIIResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EntradaMACII }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.macii.imss.gob.mx/", name = "EntradaMACII")
    public JAXBElement<EntradaMACII> createEntradaMACII(EntradaMACII value) {
        return new JAXBElement<EntradaMACII>(_EntradaMACII_QNAME, EntradaMACII.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CODIGO_ERROR", scope = RespuestaWS.class)
    public JAXBElement<Integer> createRespuestaWSCODIGOERROR(Integer value) {
        return new JAXBElement<Integer>(_RespuestaWSCODIGOERROR_QNAME, Integer.class, RespuestaWS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MENSAJE_ERROR", scope = RespuestaWS.class)
    public JAXBElement<String> createRespuestaWSMENSAJEERROR(String value) {
        return new JAXBElement<String>(_RespuestaWSMENSAJEERROR_QNAME, String.class, RespuestaWS.class, value);
    }

}
