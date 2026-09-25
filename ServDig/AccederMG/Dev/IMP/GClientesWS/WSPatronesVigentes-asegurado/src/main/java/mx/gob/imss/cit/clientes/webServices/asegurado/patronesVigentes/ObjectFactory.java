
package mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes package. 
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

    private final static QName _GetConsultaPatronesVigentes_QNAME = new QName("http://ws.patronesvigentes.imss.gob.mx/", "getConsultaPatronesVigentes");
    private final static QName _GetConsultaPatronesVigentesResponse_QNAME = new QName("http://ws.patronesvigentes.imss.gob.mx/", "getConsultaPatronesVigentesResponse");
    private final static QName _RespuestaWSCodigo_QNAME = new QName("", "codigo");
    private final static QName _RespuestaWSMensaje_QNAME = new QName("", "mensaje");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.cit.clientes.webServices.asegurado.patronesVigentes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PatronVigenteVO }
     * 
     */
    public PatronVigenteVO createPatronVigenteVO() {
        return new PatronVigenteVO();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link GetConsultaPatronesVigentes }
     * 
     */
    public GetConsultaPatronesVigentes createGetConsultaPatronesVigentes() {
        return new GetConsultaPatronesVigentes();
    }

    /**
     * Create an instance of {@link GetConsultaPatronesVigentesResponse }
     * 
     */
    public GetConsultaPatronesVigentesResponse createGetConsultaPatronesVigentesResponse() {
        return new GetConsultaPatronesVigentesResponse();
    }

    /**
     * Create an instance of {@link RespuestaPatronesVigentes }
     * 
     */
    public RespuestaPatronesVigentes createRespuestaPatronesVigentes() {
        return new RespuestaPatronesVigentes();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConsultaPatronesVigentes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.patronesvigentes.imss.gob.mx/", name = "getConsultaPatronesVigentes")
    public JAXBElement<GetConsultaPatronesVigentes> createGetConsultaPatronesVigentes(GetConsultaPatronesVigentes value) {
        return new JAXBElement<GetConsultaPatronesVigentes>(_GetConsultaPatronesVigentes_QNAME, GetConsultaPatronesVigentes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConsultaPatronesVigentesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.patronesvigentes.imss.gob.mx/", name = "getConsultaPatronesVigentesResponse")
    public JAXBElement<GetConsultaPatronesVigentesResponse> createGetConsultaPatronesVigentesResponse(GetConsultaPatronesVigentesResponse value) {
        return new JAXBElement<GetConsultaPatronesVigentesResponse>(_GetConsultaPatronesVigentesResponse_QNAME, GetConsultaPatronesVigentesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "codigo", scope = RespuestaWS.class)
    public JAXBElement<Integer> createRespuestaWSCodigo(Integer value) {
        return new JAXBElement<Integer>(_RespuestaWSCodigo_QNAME, Integer.class, RespuestaWS.class, value);
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
