
package mx.gob.imss.service.vigenciaderechohab;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.service.vigenciaderechohab package. 
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

    private final static QName _ObtieneVigXDHabResponse_QNAME = new QName("http://vigenciaderechohab.service.imss.gob.mx/", "obtieneVigXDHabResponse");
    private final static QName _ObtieneVigXDHab_QNAME = new QName("http://vigenciaderechohab.service.imss.gob.mx/", "obtieneVigXDHab");
    private final static QName _InfoConsVigXDHabCveIdPersona_QNAME = new QName("", "cveIdPersona");
    private final static QName _InfoConsVigXDHabCveIdCalidadParentesco_QNAME = new QName("", "cveIdCalidadParentesco");
    private final static QName _InfoConsVigXDHabFecInicioVigencia_QNAME = new QName("", "fecInicioVigencia");
    private final static QName _InfoConsVigXDHabFecFinVigencia_QNAME = new QName("", "fecFinVigencia");
    private final static QName _InfoConsVigXDHabCveEstadoDerechohabiente_QNAME = new QName("", "cveEstadoDerechohabiente");
    private final static QName _InfoConsVigXDHabAgregadoMedico_QNAME = new QName("", "agregadoMedico");
    private final static QName _InfoConsVigXDHabCveSubestadoDerechohabiente_QNAME = new QName("", "cveSubestadoDerechohabiente");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.service.vigenciaderechohab
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtieneVigXDHabResponse }
     * 
     */
    public ObtieneVigXDHabResponse createObtieneVigXDHabResponse() {
        return new ObtieneVigXDHabResponse();
    }

    /**
     * Create an instance of {@link ObtieneVigXDHab }
     * 
     */
    public ObtieneVigXDHab createObtieneVigXDHab() {
        return new ObtieneVigXDHab();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link InfoConsVigXDHab }
     * 
     */
    public InfoConsVigXDHab createInfoConsVigXDHab() {
        return new InfoConsVigXDHab();
    }

    /**
     * Create an instance of {@link MessageWSConsVigXDHab }
     * 
     */
    public MessageWSConsVigXDHab createMessageWSConsVigXDHab() {
        return new MessageWSConsVigXDHab();
    }

    /**
     * Create an instance of {@link RespuestaWSConsVigXDHab }
     * 
     */
    public RespuestaWSConsVigXDHab createRespuestaWSConsVigXDHab() {
        return new RespuestaWSConsVigXDHab();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigXDHabResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://vigenciaderechohab.service.imss.gob.mx/", name = "obtieneVigXDHabResponse")
    public JAXBElement<ObtieneVigXDHabResponse> createObtieneVigXDHabResponse(ObtieneVigXDHabResponse value) {
        return new JAXBElement<ObtieneVigXDHabResponse>(_ObtieneVigXDHabResponse_QNAME, ObtieneVigXDHabResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneVigXDHab }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://vigenciaderechohab.service.imss.gob.mx/", name = "obtieneVigXDHab")
    public JAXBElement<ObtieneVigXDHab> createObtieneVigXDHab(ObtieneVigXDHab value) {
        return new JAXBElement<ObtieneVigXDHab>(_ObtieneVigXDHab_QNAME, ObtieneVigXDHab.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdPersona", scope = InfoConsVigXDHab.class)
    public JAXBElement<Integer> createInfoConsVigXDHabCveIdPersona(Integer value) {
        return new JAXBElement<Integer>(_InfoConsVigXDHabCveIdPersona_QNAME, Integer.class, InfoConsVigXDHab.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdCalidadParentesco", scope = InfoConsVigXDHab.class)
    public JAXBElement<Integer> createInfoConsVigXDHabCveIdCalidadParentesco(Integer value) {
        return new JAXBElement<Integer>(_InfoConsVigXDHabCveIdCalidadParentesco_QNAME, Integer.class, InfoConsVigXDHab.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecInicioVigencia", scope = InfoConsVigXDHab.class)
    public JAXBElement<String> createInfoConsVigXDHabFecInicioVigencia(String value) {
        return new JAXBElement<String>(_InfoConsVigXDHabFecInicioVigencia_QNAME, String.class, InfoConsVigXDHab.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecFinVigencia", scope = InfoConsVigXDHab.class)
    public JAXBElement<String> createInfoConsVigXDHabFecFinVigencia(String value) {
        return new JAXBElement<String>(_InfoConsVigXDHabFecFinVigencia_QNAME, String.class, InfoConsVigXDHab.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveEstadoDerechohabiente", scope = InfoConsVigXDHab.class)
    public JAXBElement<Integer> createInfoConsVigXDHabCveEstadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfoConsVigXDHabCveEstadoDerechohabiente_QNAME, Integer.class, InfoConsVigXDHab.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "agregadoMedico", scope = InfoConsVigXDHab.class)
    public JAXBElement<String> createInfoConsVigXDHabAgregadoMedico(String value) {
        return new JAXBElement<String>(_InfoConsVigXDHabAgregadoMedico_QNAME, String.class, InfoConsVigXDHab.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveSubestadoDerechohabiente", scope = InfoConsVigXDHab.class)
    public JAXBElement<Integer> createInfoConsVigXDHabCveSubestadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfoConsVigXDHabCveSubestadoDerechohabiente_QNAME, Integer.class, InfoConsVigXDHab.class, value);
    }

}
