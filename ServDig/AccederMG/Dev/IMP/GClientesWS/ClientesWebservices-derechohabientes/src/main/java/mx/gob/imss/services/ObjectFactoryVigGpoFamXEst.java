
package mx.gob.imss.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.services package. 
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
public class ObjectFactoryVigGpoFamXEst {

    private final static QName _ObtieneVigGpoFamXEstSubResponse_QNAME = new QName("http://services.imss.gob.mx/", "obtieneVigGpoFamXEstSubResponse");
    private final static QName _Message_QNAME = new QName("http://services.imss.gob.mx/", "message");
    private final static QName _ObtieneVigGpoFamXEstSub_QNAME = new QName("http://services.imss.gob.mx/", "obtieneVigGpoFamXEstSub");
    private final static QName _InfPersonaGpoFamiliarVoCveIdPersona_QNAME = new QName("", "cveIdPersona");
    private final static QName _InfPersonaGpoFamiliarVoCveIdEstadoDerechohabiente_QNAME = new QName("", "cveIdEstadoDerechohabiente");
    private final static QName _InfPersonaGpoFamiliarVoCveIdSubestadoDerechohabiente_QNAME = new QName("", "cveIdSubestadoDerechohabiente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.services
     * 
     */
    public ObjectFactoryVigGpoFamXEst() {
    }

    /**
     * Create an instance of {@link RespuestaVigGpoFamXEstSub.ListaResultado }
     * 
     */
    public RespuestaVigGpoFamXEstSub.ListaResultado createRespuestaServVigGpoFamXEstSubListaResultado() {
        return new RespuestaVigGpoFamXEstSub.ListaResultado();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public MessageVigGpoFamXEst createMessage() {
        return new MessageVigGpoFamXEst();
    }

    /**
     * Create an instance of {@link InfPersonaGpoFamiliarVo }
     * 
     */
    public InfPersonaGpoFamiliarVo createInfPersonaGpoFamiliarVo() {
        return new InfPersonaGpoFamiliarVo();
    }

    /**
     * Create an instance of {@link ObtieneVigGpoFamXEstSub }
     * 
     */
    public ObtieneVigGpoFamXEstSub createObtieneVigGpoFamXEstSub() {
        return new ObtieneVigGpoFamXEstSub();
    }

    /**
     * Create an instance of {@link RespuestaVigGpoFamXEstSub }
     * 
     */
    public RespuestaVigGpoFamXEstSub createRespuestaServVigGpoFamXEstSub() {
        return new RespuestaVigGpoFamXEstSub();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWSServVigGpoFamXEst createRespuestaWS() {
        return new RespuestaWSServVigGpoFamXEst();
    }

    /**
     * Create an instance of {@link ObtieneVigGpoFamXEstSubResponse }
     * 
     */
    public ObtieneVigGpoFamXEstSubResponse createObtieneVigGpoFamXEstSubResponse() {
        return new ObtieneVigGpoFamXEstSubResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigGpoFamXEstSubResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.imss.gob.mx/", name = "obtieneVigGpoFamXEstSubResponse")
    public JAXBElement<ObtieneVigGpoFamXEstSubResponse> createObtieneVigGpoFamXEstSubResponse(ObtieneVigGpoFamXEstSubResponse value) {
        return new JAXBElement<ObtieneVigGpoFamXEstSubResponse>(_ObtieneVigGpoFamXEstSubResponse_QNAME, ObtieneVigGpoFamXEstSubResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Message }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.imss.gob.mx/", name = "message")
    public JAXBElement<MessageVigGpoFamXEst> createMessage(MessageVigGpoFamXEst value) {
        return new JAXBElement<MessageVigGpoFamXEst>(_Message_QNAME, MessageVigGpoFamXEst.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigGpoFamXEstSub }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.imss.gob.mx/", name = "obtieneVigGpoFamXEstSub")
    public JAXBElement<ObtieneVigGpoFamXEstSub> createObtieneVigGpoFamXEstSub(ObtieneVigGpoFamXEstSub value) {
        return new JAXBElement<ObtieneVigGpoFamXEstSub>(_ObtieneVigGpoFamXEstSub_QNAME, ObtieneVigGpoFamXEstSub.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdPersona", scope = InfPersonaGpoFamiliarVo.class)
    public JAXBElement<Integer> createInfPersonaGpoFamiliarVoCveIdPersona(Integer value) {
        return new JAXBElement<Integer>(_InfPersonaGpoFamiliarVoCveIdPersona_QNAME, Integer.class, InfPersonaGpoFamiliarVo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdEstadoDerechohabiente", scope = InfPersonaGpoFamiliarVo.class)
    public JAXBElement<Integer> createInfPersonaGpoFamiliarVoCveIdEstadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfPersonaGpoFamiliarVoCveIdEstadoDerechohabiente_QNAME, Integer.class, InfPersonaGpoFamiliarVo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdSubestadoDerechohabiente", scope = InfPersonaGpoFamiliarVo.class)
    public JAXBElement<Integer> createInfPersonaGpoFamiliarVoCveIdSubestadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfPersonaGpoFamiliarVoCveIdSubestadoDerechohabiente_QNAME, Integer.class, InfPersonaGpoFamiliarVo.class, value);
    }

}
