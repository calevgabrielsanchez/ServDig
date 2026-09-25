
package mx.gob.imss.consulta.tramites.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.consulta.tramites.service package. 
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

    private final static QName _ActualizaMovimientosRecientesBDTU_QNAME = new QName("http://service.tramites.consulta.imss.gob.mx/", "actualizaMovimientosRecientesBDTU");
    private final static QName _ActualizaMovimientosRecientesBDTUResponse_QNAME = new QName("http://service.tramites.consulta.imss.gob.mx/", "actualizaMovimientosRecientesBDTUResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.consulta.tramites.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ActualizaMovimientosRecientesBDTU }
     * 
     */
    public ActualizaMovimientosRecientesBDTU createActualizaMovimientosRecientesBDTU() {
        return new ActualizaMovimientosRecientesBDTU();
    }

    /**
     * Create an instance of {@link Message }
     * 
     */
    public Message createMessage() {
        return new Message();
    }

    /**
     * Create an instance of {@link ActualizaMovimientosRecientesBDTUResponse }
     * 
     */
    public ActualizaMovimientosRecientesBDTUResponse createActualizaMovimientosRecientesBDTUResponse() {
        return new ActualizaMovimientosRecientesBDTUResponse();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActualizaMovimientosRecientesBDTU }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.tramites.consulta.imss.gob.mx/", name = "actualizaMovimientosRecientesBDTU")
    public JAXBElement<ActualizaMovimientosRecientesBDTU> createActualizaMovimientosRecientesBDTU(ActualizaMovimientosRecientesBDTU value) {
        return new JAXBElement<ActualizaMovimientosRecientesBDTU>(_ActualizaMovimientosRecientesBDTU_QNAME, ActualizaMovimientosRecientesBDTU.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActualizaMovimientosRecientesBDTUResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.tramites.consulta.imss.gob.mx/", name = "actualizaMovimientosRecientesBDTUResponse")
    public JAXBElement<ActualizaMovimientosRecientesBDTUResponse> createActualizaMovimientosRecientesBDTUResponse(ActualizaMovimientosRecientesBDTUResponse value) {
        return new JAXBElement<ActualizaMovimientosRecientesBDTUResponse>(_ActualizaMovimientosRecientesBDTUResponse_QNAME, ActualizaMovimientosRecientesBDTUResponse.class, null, value);
    }

}
