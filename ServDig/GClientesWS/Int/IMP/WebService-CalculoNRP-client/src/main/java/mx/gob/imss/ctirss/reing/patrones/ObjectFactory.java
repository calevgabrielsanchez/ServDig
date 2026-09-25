
package mx.gob.imss.ctirss.reing.patrones;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.ctirss.reing.patrones package. 
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

    private final static QName _ValidaExistePatron_QNAME = new QName("http://patrones.reing.ctirss.imss.gob.mx/", "validaExistePatron");
    private final static QName _ValidaExistePatronResponse_QNAME = new QName("http://patrones.reing.ctirss.imss.gob.mx/", "validaExistePatronResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.ctirss.reing.patrones
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidaExistePatronResponse }
     * 
     */
    public ValidaExistePatronResponse createValidaExistePatronResponse() {
        return new ValidaExistePatronResponse();
    }

    /**
     * Create an instance of {@link ValidaExistePatron }
     * 
     */
    public ValidaExistePatron createValidaExistePatron() {
        return new ValidaExistePatron();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidaExistePatron }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://patrones.reing.ctirss.imss.gob.mx/", name = "validaExistePatron")
    public JAXBElement<ValidaExistePatron> createValidaExistePatron(ValidaExistePatron value) {
        return new JAXBElement<ValidaExistePatron>(_ValidaExistePatron_QNAME, ValidaExistePatron.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidaExistePatronResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://patrones.reing.ctirss.imss.gob.mx/", name = "validaExistePatronResponse")
    public JAXBElement<ValidaExistePatronResponse> createValidaExistePatronResponse(ValidaExistePatronResponse value) {
        return new JAXBElement<ValidaExistePatronResponse>(_ValidaExistePatronResponse_QNAME, ValidaExistePatronResponse.class, null, value);
    }

}
