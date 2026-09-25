
package mx.gob.imss.cit.ws.cuentaindividual.cliente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.cit.ws.cuentaindividual.cliente package. 
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

    private final static QName _GetCuentaIndividualResponse_QNAME = new QName("http://cuentaIndividual.imss.gob.mx/", "getCuentaIndividualResponse");
    private final static QName _GetCuentaIndividual_QNAME = new QName("http://cuentaIndividual.imss.gob.mx/", "getCuentaIndividual");
    private final static QName _RespuestaWSMensaje_QNAME = new QName("", "mensaje");
    private final static QName _RespuestaWSClave_QNAME = new QName("", "clave");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.cit.ws.cuentaindividual.cliente
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCuentaIndividualResponse }
     * 
     */
    public GetCuentaIndividualResponse createGetCuentaIndividualResponse() {
        return new GetCuentaIndividualResponse();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link GetCuentaIndividual }
     * 
     */
    public GetCuentaIndividual createGetCuentaIndividual() {
        return new GetCuentaIndividual();
    }

    /**
     * Create an instance of {@link CuentaIndividualVo }
     * 
     */
    public CuentaIndividualVo createCuentaIndividualVo() {
        return new CuentaIndividualVo();
    }

    /**
     * Create an instance of {@link RespuestaCuentaIndividual }
     * 
     */
    public RespuestaCuentaIndividual createRespuestaCuentaIndividual() {
        return new RespuestaCuentaIndividual();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCuentaIndividualResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cuentaIndividual.imss.gob.mx/", name = "getCuentaIndividualResponse")
    public JAXBElement<GetCuentaIndividualResponse> createGetCuentaIndividualResponse(GetCuentaIndividualResponse value) {
        return new JAXBElement<GetCuentaIndividualResponse>(_GetCuentaIndividualResponse_QNAME, GetCuentaIndividualResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCuentaIndividual }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cuentaIndividual.imss.gob.mx/", name = "getCuentaIndividual")
    public JAXBElement<GetCuentaIndividual> createGetCuentaIndividual(GetCuentaIndividual value) {
        return new JAXBElement<GetCuentaIndividual>(_GetCuentaIndividual_QNAME, GetCuentaIndividual.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "mensaje", scope = RespuestaWS.class)
    public JAXBElement<String> createRespuestaWSMensaje(String value) {
        return new JAXBElement<String>(_RespuestaWSMensaje_QNAME, String.class, RespuestaWS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "clave", scope = RespuestaWS.class)
    public JAXBElement<String> createRespuestaWSClave(String value) {
        return new JAXBElement<String>(_RespuestaWSClave_QNAME, String.class, RespuestaWS.class, value);
    }

}
