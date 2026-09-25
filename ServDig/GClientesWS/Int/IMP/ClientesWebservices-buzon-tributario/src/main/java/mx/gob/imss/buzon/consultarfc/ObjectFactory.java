
package mx.gob.imss.buzon.consultarfc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.buzon.consultarfc package. 
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

    private final static QName _GetConsultaRFCResponse_QNAME = new QName("http://consultarfc.buzon.imss.gob.mx/", "getConsultaRFCResponse");
    private final static QName _GetConsultaRFC_QNAME = new QName("http://consultarfc.buzon.imss.gob.mx/", "getConsultaRFC");
    private final static QName _RespuestaWSMensajeError_QNAME = new QName("", "mensajeError");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.buzon.consultarfc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RespuestaBuzonTriburario }
     * 
     */
    public RespuestaBuzonTriburario createRespuestaBuzonTriburario() {
        return new RespuestaBuzonTriburario();
    }

    /**
     * Create an instance of {@link GetConsultaRFC }
     * 
     */
    public GetConsultaRFC createGetConsultaRFC() {
        return new GetConsultaRFC();
    }

    /**
     * Create an instance of {@link GetConsultaRFCResponse }
     * 
     */
    public GetConsultaRFCResponse createGetConsultaRFCResponse() {
        return new GetConsultaRFCResponse();
    }

    /**
     * Create an instance of {@link UsuarioBuzonVO }
     * 
     */
    public UsuarioBuzonVO createUsuarioBuzonVO() {
        return new UsuarioBuzonVO();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConsultaRFCResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://consultarfc.buzon.imss.gob.mx/", name = "getConsultaRFCResponse")
    public JAXBElement<GetConsultaRFCResponse> createGetConsultaRFCResponse(GetConsultaRFCResponse value) {
        return new JAXBElement<GetConsultaRFCResponse>(_GetConsultaRFCResponse_QNAME, GetConsultaRFCResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConsultaRFC }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://consultarfc.buzon.imss.gob.mx/", name = "getConsultaRFC")
    public JAXBElement<GetConsultaRFC> createGetConsultaRFC(GetConsultaRFC value) {
        return new JAXBElement<GetConsultaRFC>(_GetConsultaRFC_QNAME, GetConsultaRFC.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "mensajeError", scope = RespuestaWS.class)
    public JAXBElement<String> createRespuestaWSMensajeError(String value) {
        return new JAXBElement<String>(_RespuestaWSMensajeError_QNAME, String.class, RespuestaWS.class, value);
    }

}
