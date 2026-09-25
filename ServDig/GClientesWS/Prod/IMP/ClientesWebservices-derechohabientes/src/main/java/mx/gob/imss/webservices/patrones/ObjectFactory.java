
package mx.gob.imss.webservices.patrones;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.webservices.patrones package. 
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

    private final static QName _Salida_QNAME = new QName("http://patrones.webservices.imss.gob.mx", "salida");
    private final static QName _Message_QNAME = new QName("http://patrones.webservices.imss.gob.mx", "Message");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.webservices.patrones
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Respuesta }
     * 
     */
    public Respuesta createRespuesta() {
        return new Respuesta();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link PatronActivoVo }
     * 
     */
    public PatronActivoVo createPatronActivoVo() {
        return new PatronActivoVo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Respuesta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://patrones.webservices.imss.gob.mx", name = "salida")
    public JAXBElement<Respuesta> createSalida(Respuesta value) {
        return new JAXBElement<Respuesta>(_Salida_QNAME, Respuesta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Message }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://patrones.webservices.imss.gob.mx", name = "Message")
    public JAXBElement<Message> createMessage(Message value) {
        return new JAXBElement<Message>(_Message_QNAME, Message.class, null, value);
    }

}
