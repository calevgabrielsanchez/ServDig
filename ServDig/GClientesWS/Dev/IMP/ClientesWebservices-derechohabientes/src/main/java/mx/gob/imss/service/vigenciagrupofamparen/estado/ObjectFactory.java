
package mx.gob.imss.service.vigenciagrupofamparen.estado;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.service.vigenciagrupofamparen.estado package. 
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

    private final static QName _ObtieneVigCabGpoFamXParenEstResponse_QNAME = new QName("http://estado.vigenciagrupofamparen.service.imss.gob.mx/", "obtieneVigCabGpoFamXParenEstResponse");
    private final static QName _ObtieneVigCabGpoFamXParenEst_QNAME = new QName("http://estado.vigenciagrupofamparen.service.imss.gob.mx/", "obtieneVigCabGpoFamXParenEst");
    private final static QName _InfoConsVigGpoFamXParenCveIdPersona_QNAME = new QName("", "cveIdPersona");
    private final static QName _InfoConsVigGpoFamXParenCveIdCalidadParentesco_QNAME = new QName("", "cveIdCalidadParentesco");
    private final static QName _InfoConsVigGpoFamXParenCveEstadoDerechohabiente_QNAME = new QName("", "cveEstadoDerechohabiente");
    private final static QName _InfoConsVigGpoFamXParenCveSubestadoDerechohabiente_QNAME = new QName("", "cveSubestadoDerechohabiente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.service.vigenciagrupofamparen.estado
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RespuestaWSConsVigGpoFamXParen.ListaResultados }
     * 
     */
    public RespuestaWSConsVigGpoFamXParen.ListaResultados createRespuestaWSConsVigGpoFamXParenListaResultados() {
        return new RespuestaWSConsVigGpoFamXParen.ListaResultados();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link RespuestaWSConsVigGpoFamXParenEst }
     * 
     */
    public RespuestaWSConsVigGpoFamXParenEst createRespuestaWSConsVigGpoFamXParenEst() {
        return new RespuestaWSConsVigGpoFamXParenEst();
    }

    /**
     * Create an instance of {@link MessageWSConsVigGpoFamXParenEst }
     * 
     */
    public MessageWSConsVigGpoFamXParenEst createMessageWSConsVigGpoFamXParenEst() {
        return new MessageWSConsVigGpoFamXParenEst();
    }

    /**
     * Create an instance of {@link ObtieneVigCabGpoFamXParenEst }
     * 
     */
    public ObtieneVigCabGpoFamXParenEst createObtieneVigCabGpoFamXParenEst() {
        return new ObtieneVigCabGpoFamXParenEst();
    }

    /**
     * Create an instance of {@link InfoConsVigGpoFamXParen }
     * 
     */
    public InfoConsVigGpoFamXParen createInfoConsVigGpoFamXParen() {
        return new InfoConsVigGpoFamXParen();
    }

    /**
     * Create an instance of {@link ObtieneVigCabGpoFamXParenEstResponse }
     * 
     */
    public ObtieneVigCabGpoFamXParenEstResponse createObtieneVigCabGpoFamXParenEstResponse() {
        return new ObtieneVigCabGpoFamXParenEstResponse();
    }

    /**
     * Create an instance of {@link RespuestaWSConsVigGpoFamXParen }
     * 
     */
    public RespuestaWSConsVigGpoFamXParen createRespuestaWSConsVigGpoFamXParen() {
        return new RespuestaWSConsVigGpoFamXParen();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigCabGpoFamXParenEstResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://estado.vigenciagrupofamparen.service.imss.gob.mx/", name = "obtieneVigCabGpoFamXParenEstResponse")
    public JAXBElement<ObtieneVigCabGpoFamXParenEstResponse> createObtieneVigCabGpoFamXParenEstResponse(ObtieneVigCabGpoFamXParenEstResponse value) {
        return new JAXBElement<ObtieneVigCabGpoFamXParenEstResponse>(_ObtieneVigCabGpoFamXParenEstResponse_QNAME, ObtieneVigCabGpoFamXParenEstResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigCabGpoFamXParenEst }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://estado.vigenciagrupofamparen.service.imss.gob.mx/", name = "obtieneVigCabGpoFamXParenEst")
    public JAXBElement<ObtieneVigCabGpoFamXParenEst> createObtieneVigCabGpoFamXParenEst(ObtieneVigCabGpoFamXParenEst value) {
        return new JAXBElement<ObtieneVigCabGpoFamXParenEst>(_ObtieneVigCabGpoFamXParenEst_QNAME, ObtieneVigCabGpoFamXParenEst.class, null, value);
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
