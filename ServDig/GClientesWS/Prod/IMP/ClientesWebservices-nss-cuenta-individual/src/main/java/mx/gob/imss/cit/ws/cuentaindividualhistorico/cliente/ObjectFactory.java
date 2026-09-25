
package mx.gob.imss.cit.ws.cuentaindividualhistorico.cliente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.cit.ws.cuentaindividualhistorico.cliente package. 
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

    private final static QName _GetHistoricoCentralSISEC_QNAME = new QName("http://cuentaIndividual.sisec.imss.gob.mx/", "getHistoricoCentralSISEC");
    private final static QName _GetHistoricoCentralSISECResponse_QNAME = new QName("http://cuentaIndividual.sisec.imss.gob.mx/", "getHistoricoCentralSISECResponse");
    private final static QName _RespuestaWSClave_QNAME = new QName("", "clave");
    private final static QName _RespuestaWSMensaje_QNAME = new QName("", "mensaje");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.cit.ws.cuentaindividualhistorico.cliente
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetHistoricoCentralSISECResponse }
     * 
     */
    public GetHistoricoCentralSISECResponse createGetHistoricoCentralSISECResponse() {
        return new GetHistoricoCentralSISECResponse();
    }

    /**
     * Create an instance of {@link GetHistoricoCentralSISEC }
     * 
     */
    public GetHistoricoCentralSISEC createGetHistoricoCentralSISEC() {
        return new GetHistoricoCentralSISEC();
    }

    /**
     * Create an instance of {@link HistoricoCuentaIndividualVo }
     * 
     */
    public HistoricoCuentaIndividualVo createHistoricoCuentaIndividualVo() {
        return new HistoricoCuentaIndividualVo();
    }

    /**
     * Create an instance of {@link RespuestaHistoricoCuentaIndividual }
     * 
     */
    public RespuestaHistoricoCuentaIndividual createRespuestaHistoricoCuentaIndividual() {
        return new RespuestaHistoricoCuentaIndividual();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHistoricoCentralSISEC }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cuentaIndividual.sisec.imss.gob.mx/", name = "getHistoricoCentralSISEC")
    public JAXBElement<GetHistoricoCentralSISEC> createGetHistoricoCentralSISEC(GetHistoricoCentralSISEC value) {
        return new JAXBElement<GetHistoricoCentralSISEC>(_GetHistoricoCentralSISEC_QNAME, GetHistoricoCentralSISEC.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHistoricoCentralSISECResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cuentaIndividual.sisec.imss.gob.mx/", name = "getHistoricoCentralSISECResponse")
    public JAXBElement<GetHistoricoCentralSISECResponse> createGetHistoricoCentralSISECResponse(GetHistoricoCentralSISECResponse value) {
        return new JAXBElement<GetHistoricoCentralSISECResponse>(_GetHistoricoCentralSISECResponse_QNAME, GetHistoricoCentralSISECResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "clave", scope = RespuestaWS.class)
    public JAXBElement<String> createRespuestaWSClave(String value) {
        return new JAXBElement<String>(_RespuestaWSClave_QNAME, String.class, RespuestaWS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "mensaje", scope = RespuestaWS.class)
    public JAXBElement<String> createRespuestaWSMensaje(String value) {
        return new JAXBElement<String>(_RespuestaWSMensaje_QNAME, String.class, RespuestaWS.class, value);
    }

}
