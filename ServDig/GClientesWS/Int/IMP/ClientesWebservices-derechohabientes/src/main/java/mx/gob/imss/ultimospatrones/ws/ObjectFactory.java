
package mx.gob.imss.ultimospatrones.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.ultimospatrones.ws package. 
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

    private final static QName _GetUltimosPatrones_QNAME = new QName("http://ws.ultimospatrones.imss.gob.mx/", "getUltimosPatrones");
    private final static QName _GetUltimosPatronesResponse_QNAME = new QName("http://ws.ultimospatrones.imss.gob.mx/", "getUltimosPatronesResponse");
    private final static QName _RespuestaWSCODIGO_QNAME = new QName("", "CODIGO");
    private final static QName _RespuestaWSMENSAJE_QNAME = new QName("", "MENSAJE");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.ultimospatrones.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UltimosPatronesVO }
     * 
     */
    public UltimosPatronesVO createUltimosPatronesVO() {
        return new UltimosPatronesVO();
    }

    /**
     * Create an instance of {@link GetUltimosPatrones }
     * 
     */
    public GetUltimosPatrones createGetUltimosPatrones() {
        return new GetUltimosPatrones();
    }

    /**
     * Create an instance of {@link GetUltimosPatronesResponse }
     * 
     */
    public GetUltimosPatronesResponse createGetUltimosPatronesResponse() {
        return new GetUltimosPatronesResponse();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link RespuestaUltimosPatrones }
     * 
     */
    public RespuestaUltimosPatrones createRespuestaUltimosPatrones() {
        return new RespuestaUltimosPatrones();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUltimosPatrones }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ultimospatrones.imss.gob.mx/", name = "getUltimosPatrones")
    public JAXBElement<GetUltimosPatrones> createGetUltimosPatrones(GetUltimosPatrones value) {
        return new JAXBElement<GetUltimosPatrones>(_GetUltimosPatrones_QNAME, GetUltimosPatrones.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUltimosPatronesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.ultimospatrones.imss.gob.mx/", name = "getUltimosPatronesResponse")
    public JAXBElement<GetUltimosPatronesResponse> createGetUltimosPatronesResponse(GetUltimosPatronesResponse value) {
        return new JAXBElement<GetUltimosPatronesResponse>(_GetUltimosPatronesResponse_QNAME, GetUltimosPatronesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CODIGO", scope = RespuestaWS.class)
    public JAXBElement<Integer> createRespuestaWSCODIGO(Integer value) {
        return new JAXBElement<Integer>(_RespuestaWSCODIGO_QNAME, Integer.class, RespuestaWS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MENSAJE", scope = RespuestaWS.class)
    public JAXBElement<String> createRespuestaWSMENSAJE(String value) {
        return new JAXBElement<String>(_RespuestaWSMENSAJE_QNAME, String.class, RespuestaWS.class, value);
    }

}
