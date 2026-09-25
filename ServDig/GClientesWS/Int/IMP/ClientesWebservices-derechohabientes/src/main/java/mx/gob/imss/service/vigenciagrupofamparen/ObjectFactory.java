
package mx.gob.imss.service.vigenciagrupofamparen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.service.vigenciagrupofamparen package. 
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

    private final static QName _ObtieneVigCabGpoFamXParenResponse_QNAME = new QName("http://vigenciagrupofamparen.service.imss.gob.mx/", "obtieneVigCabGpoFamXParenResponse");
    private final static QName _ObtieneVigCabGpoFamXParen_QNAME = new QName("http://vigenciagrupofamparen.service.imss.gob.mx/", "obtieneVigCabGpoFamXParen");
    private final static QName _InfoConsVigGpoFamXParenCveIdPersona_QNAME = new QName("", "cveIdPersona");
    private final static QName _InfoConsVigGpoFamXParenCveIdCalidadParentesco_QNAME = new QName("", "cveIdCalidadParentesco");
    private final static QName _InfoConsVigGpoFamXParenCveEstadoDerechohabiente_QNAME = new QName("", "cveEstadoDerechohabiente");
    private final static QName _InfoConsVigGpoFamXParenCveSubestadoDerechohabiente_QNAME = new QName("", "cveSubestadoDerechohabiente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.service.vigenciagrupofamparen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MessageWSConsVigGpoFamXParen }
     * 
     */
    public MessageWSConsVigGpoFamXParen createMessageWSConsVigGpoFamXParen() {
        return new MessageWSConsVigGpoFamXParen();
    }

    /**
     * Create an instance of {@link RespuestaWSConsVigGpoFamXParen }
     * 
     */
    public RespuestaWSConsVigGpoFamXParen createRespuestaWSConsVigGpoFamXParen() {
        return new RespuestaWSConsVigGpoFamXParen();
    }

    /**
     * Create an instance of {@link RespuestaWSConsVigGpoFamXParen.ListaResultados }
     * 
     */
    public RespuestaWSConsVigGpoFamXParen.ListaResultados createRespuestaWSConsVigGpoFamXParenListaResultados() {
        return new RespuestaWSConsVigGpoFamXParen.ListaResultados();
    }

    /**
     * Create an instance of {@link InfoConsVigGpoFamXParen }
     * 
     */
    public InfoConsVigGpoFamXParen createInfoConsVigGpoFamXParen() {
        return new InfoConsVigGpoFamXParen();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link ObtieneVigCabGpoFamXParenResponse }
     * 
     */
    public ObtieneVigCabGpoFamXParenResponse createObtieneVigCabGpoFamXParenResponse() {
        return new ObtieneVigCabGpoFamXParenResponse();
    }

    /**
     * Create an instance of {@link ObtieneVigCabGpoFamXParen }
     * 
     */
    public ObtieneVigCabGpoFamXParen createObtieneVigCabGpoFamXParen() {
        return new ObtieneVigCabGpoFamXParen();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigCabGpoFamXParenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://vigenciagrupofamparen.service.imss.gob.mx/", name = "obtieneVigCabGpoFamXParenResponse")
    public JAXBElement<ObtieneVigCabGpoFamXParenResponse> createObtieneVigCabGpoFamXParenResponse(ObtieneVigCabGpoFamXParenResponse value) {
        return new JAXBElement<ObtieneVigCabGpoFamXParenResponse>(_ObtieneVigCabGpoFamXParenResponse_QNAME, ObtieneVigCabGpoFamXParenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigCabGpoFamXParen }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://vigenciagrupofamparen.service.imss.gob.mx/", name = "obtieneVigCabGpoFamXParen")
    public JAXBElement<ObtieneVigCabGpoFamXParen> createObtieneVigCabGpoFamXParen(ObtieneVigCabGpoFamXParen value) {
        return new JAXBElement<ObtieneVigCabGpoFamXParen>(_ObtieneVigCabGpoFamXParen_QNAME, ObtieneVigCabGpoFamXParen.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdPersona", scope = InfoConsVigGpoFamXParen.class)
    public JAXBElement<Integer> createInfoConsVigGpoFamXParenCveIdPersona(Integer value) {
        return new JAXBElement<Integer>(_InfoConsVigGpoFamXParenCveIdPersona_QNAME, Integer.class, InfoConsVigGpoFamXParen.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdCalidadParentesco", scope = InfoConsVigGpoFamXParen.class)
    public JAXBElement<Integer> createInfoConsVigGpoFamXParenCveIdCalidadParentesco(Integer value) {
        return new JAXBElement<Integer>(_InfoConsVigGpoFamXParenCveIdCalidadParentesco_QNAME, Integer.class, InfoConsVigGpoFamXParen.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveEstadoDerechohabiente", scope = InfoConsVigGpoFamXParen.class)
    public JAXBElement<Integer> createInfoConsVigGpoFamXParenCveEstadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfoConsVigGpoFamXParenCveEstadoDerechohabiente_QNAME, Integer.class, InfoConsVigGpoFamXParen.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveSubestadoDerechohabiente", scope = InfoConsVigGpoFamXParen.class)
    public JAXBElement<Integer> createInfoConsVigGpoFamXParenCveSubestadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfoConsVigGpoFamXParenCveSubestadoDerechohabiente_QNAME, Integer.class, InfoConsVigGpoFamXParen.class, value);
    }

}
