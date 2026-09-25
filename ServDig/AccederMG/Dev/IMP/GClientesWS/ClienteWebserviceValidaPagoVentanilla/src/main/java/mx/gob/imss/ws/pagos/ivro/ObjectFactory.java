
package mx.gob.imss.ws.pagos.ivro;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.ws.pagos.ivro package. 
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

    private final static QName _PagoPorPeriodoResponse_QNAME = new QName("http://ivro.pagos.ws.imss.gob.mx/", "pagoPorPeriodoResponse");
    private final static QName _PagoPorPeriodo_QNAME = new QName("http://ivro.pagos.ws.imss.gob.mx/", "pagoPorPeriodo");
    private final static QName _PagoPorFechasResponse_QNAME = new QName("http://ivro.pagos.ws.imss.gob.mx/", "pagoPorFechasResponse");
    private final static QName _PagoPorFechas_QNAME = new QName("http://ivro.pagos.ws.imss.gob.mx/", "pagoPorFechas");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.ws.pagos.ivro
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PagoPorPeriodoResponse }
     * 
     */
    public PagoPorPeriodoResponse createPagoPorPeriodoResponse() {
        return new PagoPorPeriodoResponse();
    }

    /**
     * Create an instance of {@link PagoPorFechasResponse }
     * 
     */
    public PagoPorFechasResponse createPagoPorFechasResponse() {
        return new PagoPorFechasResponse();
    }

    /**
     * Create an instance of {@link PagoPorFechas }
     * 
     */
    public PagoPorFechas createPagoPorFechas() {
        return new PagoPorFechas();
    }

    /**
     * Create an instance of {@link PagoPorPeriodo }
     * 
     */
    public PagoPorPeriodo createPagoPorPeriodo() {
        return new PagoPorPeriodo();
    }

    /**
     * Create an instance of {@link MsgWSPagosIvroByFec }
     * 
     */
    public MsgWSPagosIvroByFec createMsgWSPagosIvroByFec() {
        return new MsgWSPagosIvroByFec();
    }

    /**
     * Create an instance of {@link MsgWSPagosIvroByPeriodo }
     * 
     */
    public MsgWSPagosIvroByPeriodo createMsgWSPagosIvroByPeriodo() {
        return new MsgWSPagosIvroByPeriodo();
    }

    /**
     * Create an instance of {@link RespWSPagosIvroSimple }
     * 
     */
    public RespWSPagosIvroSimple createRespWSPagosIvroSimple() {
        return new RespWSPagosIvroSimple();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PagoPorPeriodoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ivro.pagos.ws.imss.gob.mx/", name = "pagoPorPeriodoResponse")
    public JAXBElement<PagoPorPeriodoResponse> createPagoPorPeriodoResponse(PagoPorPeriodoResponse value) {
        return new JAXBElement<PagoPorPeriodoResponse>(_PagoPorPeriodoResponse_QNAME, PagoPorPeriodoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PagoPorPeriodo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ivro.pagos.ws.imss.gob.mx/", name = "pagoPorPeriodo")
    public JAXBElement<PagoPorPeriodo> createPagoPorPeriodo(PagoPorPeriodo value) {
        return new JAXBElement<PagoPorPeriodo>(_PagoPorPeriodo_QNAME, PagoPorPeriodo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PagoPorFechasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ivro.pagos.ws.imss.gob.mx/", name = "pagoPorFechasResponse")
    public JAXBElement<PagoPorFechasResponse> createPagoPorFechasResponse(PagoPorFechasResponse value) {
        return new JAXBElement<PagoPorFechasResponse>(_PagoPorFechasResponse_QNAME, PagoPorFechasResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PagoPorFechas }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ivro.pagos.ws.imss.gob.mx/", name = "pagoPorFechas")
    public JAXBElement<PagoPorFechas> createPagoPorFechas(PagoPorFechas value) {
        return new JAXBElement<PagoPorFechas>(_PagoPorFechas_QNAME, PagoPorFechas.class, null, value);
    }

}
