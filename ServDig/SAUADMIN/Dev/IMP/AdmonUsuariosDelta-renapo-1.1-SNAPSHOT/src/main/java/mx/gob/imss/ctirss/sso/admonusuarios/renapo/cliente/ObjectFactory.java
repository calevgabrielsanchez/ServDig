
package mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente package. 
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

    private final static QName _CurpKioscosBean_QNAME = new QName("http://www.openuri.org/", "CurpKioscosBean");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.ctirss.sso.admonusuarios.renapo.cliente
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultaDatosCURP }
     * 
     */
    public ConsultaDatosCURP createConsultaDatosCURP() {
        return new ConsultaDatosCURP();
    }

    /**
     * Create an instance of {@link ConsultaCURP }
     * 
     */
    public ConsultaCURP createConsultaCURP() {
        return new ConsultaCURP();
    }

    /**
     * Create an instance of {@link ParametrosConsulta }
     * 
     */
    public ParametrosConsulta createParametrosConsulta() {
        return new ParametrosConsulta();
    }

    /**
     * Create an instance of {@link ConsultaCURPResponse }
     * 
     */
    public ConsultaCURPResponse createConsultaCURPResponse() {
        return new ConsultaCURPResponse();
    }

    /**
     * Create an instance of {@link CurpKioscosBean }
     * 
     */
    public CurpKioscosBean createCurpKioscosBean() {
        return new CurpKioscosBean();
    }

    /**
     * Create an instance of {@link ConsultaDatosCURPResponse }
     * 
     */
    public ConsultaDatosCURPResponse createConsultaDatosCURPResponse() {
        return new ConsultaDatosCURPResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurpKioscosBean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.openuri.org/", name = "CurpKioscosBean")
    public JAXBElement<CurpKioscosBean> createCurpKioscosBean(CurpKioscosBean value) {
        return new JAXBElement<CurpKioscosBean>(_CurpKioscosBean_QNAME, CurpKioscosBean.class, null, value);
    }

}
