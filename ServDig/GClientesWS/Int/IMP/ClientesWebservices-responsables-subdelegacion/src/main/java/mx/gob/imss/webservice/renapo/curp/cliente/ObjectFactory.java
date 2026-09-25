
package mx.gob.imss.webservice.renapo.curp.cliente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.webservice.renapo.curp.cliente package. 
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

    private final static QName _ConsultaDatosUsuarioResponse_QNAME = new QName("http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/", "consultaDatosUsuarioResponse");
    private final static QName _RecuperaResponsablesDelegacion_QNAME = new QName("http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/", "recuperaResponsablesDelegacion");
    private final static QName _RecuperaResponsablesDelegacionResponse_QNAME = new QName("http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/", "recuperaResponsablesDelegacionResponse");
    private final static QName _ConsultaDatosUsuario_QNAME = new QName("http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/", "consultaDatosUsuario");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.webservice.renapo.curp.cliente
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResponsableDTO }
     * 
     */
    public ResponsableDTO createResponsableDTO() {
        return new ResponsableDTO();
    }

    /**
     * Create an instance of {@link ConsultaDatosUsuario }
     * 
     */
    public ConsultaDatosUsuario createConsultaDatosUsuario() {
        return new ConsultaDatosUsuario();
    }

    /**
     * Create an instance of {@link RecuperaResponsablesDelegacionResponse }
     * 
     */
    public RecuperaResponsablesDelegacionResponse createRecuperaResponsablesDelegacionResponse() {
        return new RecuperaResponsablesDelegacionResponse();
    }

    /**
     * Create an instance of {@link ConsultaDatosUsuarioResponse }
     * 
     */
    public ConsultaDatosUsuarioResponse createConsultaDatosUsuarioResponse() {
        return new ConsultaDatosUsuarioResponse();
    }

    /**
     * Create an instance of {@link UsuarioInfoDTO }
     * 
     */
    public UsuarioInfoDTO createUsuarioInfoDTO() {
        return new UsuarioInfoDTO();
    }

    /**
     * Create an instance of {@link ResponsablesDelegacionDTO }
     * 
     */
    public ResponsablesDelegacionDTO createResponsablesDelegacionDTO() {
        return new ResponsablesDelegacionDTO();
    }

    /**
     * Create an instance of {@link RecuperaResponsablesDelegacion }
     * 
     */
    public RecuperaResponsablesDelegacion createRecuperaResponsablesDelegacion() {
        return new RecuperaResponsablesDelegacion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaDatosUsuarioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/", name = "consultaDatosUsuarioResponse")
    public JAXBElement<ConsultaDatosUsuarioResponse> createConsultaDatosUsuarioResponse(ConsultaDatosUsuarioResponse value) {
        return new JAXBElement<ConsultaDatosUsuarioResponse>(_ConsultaDatosUsuarioResponse_QNAME, ConsultaDatosUsuarioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperaResponsablesDelegacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/", name = "recuperaResponsablesDelegacion")
    public JAXBElement<RecuperaResponsablesDelegacion> createRecuperaResponsablesDelegacion(RecuperaResponsablesDelegacion value) {
        return new JAXBElement<RecuperaResponsablesDelegacion>(_RecuperaResponsablesDelegacion_QNAME, RecuperaResponsablesDelegacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecuperaResponsablesDelegacionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/", name = "recuperaResponsablesDelegacionResponse")
    public JAXBElement<RecuperaResponsablesDelegacionResponse> createRecuperaResponsablesDelegacionResponse(RecuperaResponsablesDelegacionResponse value) {
        return new JAXBElement<RecuperaResponsablesDelegacionResponse>(_RecuperaResponsablesDelegacionResponse_QNAME, RecuperaResponsablesDelegacionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultaDatosUsuario }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/", name = "consultaDatosUsuario")
    public JAXBElement<ConsultaDatosUsuario> createConsultaDatosUsuario(ConsultaDatosUsuario value) {
        return new JAXBElement<ConsultaDatosUsuario>(_ConsultaDatosUsuario_QNAME, ConsultaDatosUsuario.class, null, value);
    }

}
