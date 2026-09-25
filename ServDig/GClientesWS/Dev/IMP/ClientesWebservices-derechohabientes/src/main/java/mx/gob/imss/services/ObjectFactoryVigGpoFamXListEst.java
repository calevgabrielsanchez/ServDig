
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
public class ObjectFactoryVigGpoFamXListEst {

    private final static QName _ObtieneVigGpoFamXListEstResponse_QNAME = new QName("http://services.imss.gob.mx/", "obtieneVigGpoFamXListEstResponse");
    private final static QName _ObtieneVigGpoFamXListEst_QNAME = new QName("http://services.imss.gob.mx/", "obtieneVigGpoFamXListEst");
    private final static QName _Message_QNAME = new QName("http://services.imss.gob.mx/", "message");
    private final static QName _InfoVigGpoFamXListEstVoCveIdPersona_QNAME = new QName("", "cveIdPersona");
    private final static QName _InfoVigGpoFamXListEstVoCveIdCalidadParentesco_QNAME = new QName("", "cveIdCalidadParentesco");
    private final static QName _InfoVigGpoFamXListEstVoCveIdEstadoDerechohabiente_QNAME = new QName("", "cveIdEstadoDerechohabiente");
    private final static QName _InfoVigGpoFamXListEstVoCveIdSubestadoDerechohabiente_QNAME = new QName("", "cveIdSubestadoDerechohabiente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.services
     * 
     */
    public ObjectFactoryVigGpoFamXListEst() {
    }

    /**
     * Create an instance of {@link RespuestaVigGpoFamXListEst }
     * 
     */
    public RespuestaVigGpoFamXListEst createRespuestaServVigGpoFamXListEst() {
        return new RespuestaVigGpoFamXListEst();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWSServVigGpoFamXListEst createRespuestaWS() {
        return new RespuestaWSServVigGpoFamXListEst();
    }

    /**
     * Create an instance of {@link RespuestaVigGpoFamXListEst.ListaResultado }
     * 
     */
    public RespuestaVigGpoFamXListEst.ListaResultado createRespuestaServVigGpoFamXListEstListaResultado() {
        return new RespuestaVigGpoFamXListEst.ListaResultado();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public MessageVigGpoFamXlistEst createMessage() {
        return new MessageVigGpoFamXlistEst();
    }

    /**
     * Create an instance of {@link ObtieneVigGpoFamXListEst }
     * 
     */
    public ObtieneVigGpoFamXListEst createObtieneVigGpoFamXListEst() {
        return new ObtieneVigGpoFamXListEst();
    }

    /**
     * Create an instance of {@link InfoVigGpoFamXListEstVo }
     * 
     */
    public InfoVigGpoFamXListEstVo createInfoVigGpoFamXListEstVo() {
        return new InfoVigGpoFamXListEstVo();
    }

    /**
     * Create an instance of {@link Message.ListaEstadosDerechohabientes }
     * 
     */
    public MessageVigGpoFamXlistEst.ListaEstadosDerechohabientes createMessageListaEstadosDerechohabientes() {
        return new MessageVigGpoFamXlistEst.ListaEstadosDerechohabientes();
    }

    /**
     * Create an instance of {@link ObtieneVigGpoFamXListEstResponse }
     * 
     */
    public ObtieneVigGpoFamXListEstResponse createObtieneVigGpoFamXListEstResponse() {
        return new ObtieneVigGpoFamXListEstResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigGpoFamXListEstResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.imss.gob.mx/", name = "obtieneVigGpoFamXListEstResponse")
    public JAXBElement<ObtieneVigGpoFamXListEstResponse> createObtieneVigGpoFamXListEstResponse(ObtieneVigGpoFamXListEstResponse value) {
        return new JAXBElement<ObtieneVigGpoFamXListEstResponse>(_ObtieneVigGpoFamXListEstResponse_QNAME, ObtieneVigGpoFamXListEstResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigGpoFamXListEst }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.imss.gob.mx/", name = "obtieneVigGpoFamXListEst")
    public JAXBElement<ObtieneVigGpoFamXListEst> createObtieneVigGpoFamXListEst(ObtieneVigGpoFamXListEst value) {
        return new JAXBElement<ObtieneVigGpoFamXListEst>(_ObtieneVigGpoFamXListEst_QNAME, ObtieneVigGpoFamXListEst.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Message }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.imss.gob.mx/", name = "message")
    public JAXBElement<MessageVigGpoFamXlistEst> createMessage(MessageVigGpoFamXlistEst value) {
        return new JAXBElement<MessageVigGpoFamXlistEst>(_Message_QNAME, MessageVigGpoFamXlistEst.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdPersona", scope = InfoVigGpoFamXListEstVo.class)
    public JAXBElement<Integer> createInfoVigGpoFamXListEstVoCveIdPersona(Integer value) {
        return new JAXBElement<Integer>(_InfoVigGpoFamXListEstVoCveIdPersona_QNAME, Integer.class, InfoVigGpoFamXListEstVo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdCalidadParentesco", scope = InfoVigGpoFamXListEstVo.class)
    public JAXBElement<Integer> createInfoVigGpoFamXListEstVoCveIdCalidadParentesco(Integer value) {
        return new JAXBElement<Integer>(_InfoVigGpoFamXListEstVoCveIdCalidadParentesco_QNAME, Integer.class, InfoVigGpoFamXListEstVo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdEstadoDerechohabiente", scope = InfoVigGpoFamXListEstVo.class)
    public JAXBElement<Integer> createInfoVigGpoFamXListEstVoCveIdEstadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfoVigGpoFamXListEstVoCveIdEstadoDerechohabiente_QNAME, Integer.class, InfoVigGpoFamXListEstVo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdSubestadoDerechohabiente", scope = InfoVigGpoFamXListEstVo.class)
    public JAXBElement<Integer> createInfoVigGpoFamXListEstVoCveIdSubestadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfoVigGpoFamXListEstVoCveIdSubestadoDerechohabiente_QNAME, Integer.class, InfoVigGpoFamXListEstVo.class, value);
    }

}
