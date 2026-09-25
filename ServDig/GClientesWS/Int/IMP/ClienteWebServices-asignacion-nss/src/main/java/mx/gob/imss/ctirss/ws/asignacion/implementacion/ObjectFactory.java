
package mx.gob.imss.ctirss.ws.asignacion.implementacion;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.ctirss.ws.asignacion.implementacion package. 
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

    private final static QName _EjecutarAlta_QNAME = new QName("http://implementacion.asignacion.ws.ctirss.imss.gob.mx/", "ejecutarAlta");
    private final static QName _EjecutarAltaResponse_QNAME = new QName("http://implementacion.asignacion.ws.ctirss.imss.gob.mx/", "ejecutarAltaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.ctirss.ws.asignacion.implementacion
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EjecutarAltaResponse }
     * 
     */
    public EjecutarAltaResponse createEjecutarAltaResponse() {
        return new EjecutarAltaResponse();
    }

    /**
     * Create an instance of {@link EjecutarAlta }
     * 
     */
    public EjecutarAlta createEjecutarAlta() {
        return new EjecutarAlta();
    }

    /**
     * Create an instance of {@link AsignacionNSSBean }
     * 
     */
    public AsignacionNSSBean createAsignacionNSSBean() {
        return new AsignacionNSSBean();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EjecutarAlta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://implementacion.asignacion.ws.ctirss.imss.gob.mx/", name = "ejecutarAlta")
    public JAXBElement<EjecutarAlta> createEjecutarAlta(EjecutarAlta value) {
        return new JAXBElement<EjecutarAlta>(_EjecutarAlta_QNAME, EjecutarAlta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EjecutarAltaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://implementacion.asignacion.ws.ctirss.imss.gob.mx/", name = "ejecutarAltaResponse")
    public JAXBElement<EjecutarAltaResponse> createEjecutarAltaResponse(EjecutarAltaResponse value) {
        return new JAXBElement<EjecutarAltaResponse>(_EjecutarAltaResponse_QNAME, EjecutarAltaResponse.class, null, value);
    }

}
