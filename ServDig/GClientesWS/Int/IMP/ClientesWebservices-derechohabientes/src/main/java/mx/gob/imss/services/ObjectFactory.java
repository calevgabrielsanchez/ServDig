
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
public class ObjectFactory {

    private final static QName _ObtieneVigenciaGpoFamEstResponse_QNAME = new QName("http://services.imss.gob.mx/", "obtieneVigenciaGpoFamEstResponse");
    private final static QName _ObtieneVigenciaGpoFamEst_QNAME = new QName("http://services.imss.gob.mx/", "obtieneVigenciaGpoFamEst");
    private final static QName _Message_QNAME = new QName("http://services.imss.gob.mx/", "message");
    private final static QName _InfoPersonaGpoFamiliarVoCveIdPersona_QNAME = new QName("", "cveIdPersona");
    private final static QName _InfoPersonaGpoFamiliarVoCveIdCalidadParentesco_QNAME = new QName("", "cveIdCalidadParentesco");
    private final static QName _InfoPersonaGpoFamiliarVoCveIdEstadoDerechohabiente_QNAME = new QName("", "cveIdEstadoDerechohabiente");
    private final static QName _InfoPersonaGpoFamiliarVoCveIdSubestadoDerechohabiente_QNAME = new QName("", "cveIdSubestadoDerechohabiente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RespuestaServVigGpoFamEst.ListaResultado }
     * 
     */
    public RespuestaServVigGpoFamEst.ListaResultado createRespuestaServVigGpoFamEstListaResultado() {
        return new RespuestaServVigGpoFamEst.ListaResultado();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link ObtieneVigenciaGpoFamEstResponse }
     * 
     */
    public ObtieneVigenciaGpoFamEstResponse createObtieneVigenciaGpoFamEstResponse() {
        return new ObtieneVigenciaGpoFamEstResponse();
    }

    /**
     * Create an instance of {@link RespuestaServVigGpoFamEst }
     * 
     */
    public RespuestaServVigGpoFamEst createRespuestaServVigGpoFamEst() {
        return new RespuestaServVigGpoFamEst();
    }

    /**
     * Create an instance of {@link ObtieneVigenciaGpoFamEst }
     * 
     */
    public ObtieneVigenciaGpoFamEst createObtieneVigenciaGpoFamEst() {
        return new ObtieneVigenciaGpoFamEst();
    }

    /**
     * Create an instance of {@link InfoPersonaGpoFamiliarVo }
     * 
     */
    public InfoPersonaGpoFamiliarVo createInfoPersonaGpoFamiliarVo() {
        return new InfoPersonaGpoFamiliarVo();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigenciaGpoFamEstResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.imss.gob.mx/", name = "obtieneVigenciaGpoFamEstResponse")
    public JAXBElement<ObtieneVigenciaGpoFamEstResponse> createObtieneVigenciaGpoFamEstResponse(ObtieneVigenciaGpoFamEstResponse value) {
        return new JAXBElement<ObtieneVigenciaGpoFamEstResponse>(_ObtieneVigenciaGpoFamEstResponse_QNAME, ObtieneVigenciaGpoFamEstResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigenciaGpoFamEst }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.imss.gob.mx/", name = "obtieneVigenciaGpoFamEst")
    public JAXBElement<ObtieneVigenciaGpoFamEst> createObtieneVigenciaGpoFamEst(ObtieneVigenciaGpoFamEst value) {
        return new JAXBElement<ObtieneVigenciaGpoFamEst>(_ObtieneVigenciaGpoFamEst_QNAME, ObtieneVigenciaGpoFamEst.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Message }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.imss.gob.mx/", name = "message")
    public JAXBElement<Message> createMessage(Message value) {
        return new JAXBElement<Message>(_Message_QNAME, Message.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdPersona", scope = InfoPersonaGpoFamiliarVo.class)
    public JAXBElement<Integer> createInfoPersonaGpoFamiliarVoCveIdPersona(Integer value) {
        return new JAXBElement<Integer>(_InfoPersonaGpoFamiliarVoCveIdPersona_QNAME, Integer.class, InfoPersonaGpoFamiliarVo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdCalidadParentesco", scope = InfoPersonaGpoFamiliarVo.class)
    public JAXBElement<Integer> createInfoPersonaGpoFamiliarVoCveIdCalidadParentesco(Integer value) {
        return new JAXBElement<Integer>(_InfoPersonaGpoFamiliarVoCveIdCalidadParentesco_QNAME, Integer.class, InfoPersonaGpoFamiliarVo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdEstadoDerechohabiente", scope = InfoPersonaGpoFamiliarVo.class)
    public JAXBElement<Integer> createInfoPersonaGpoFamiliarVoCveIdEstadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfoPersonaGpoFamiliarVoCveIdEstadoDerechohabiente_QNAME, Integer.class, InfoPersonaGpoFamiliarVo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdSubestadoDerechohabiente", scope = InfoPersonaGpoFamiliarVo.class)
    public JAXBElement<Integer> createInfoPersonaGpoFamiliarVoCveIdSubestadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfoPersonaGpoFamiliarVoCveIdSubestadoDerechohabiente_QNAME, Integer.class, InfoPersonaGpoFamiliarVo.class, value);
    }

}
