     
package mx.gob.imss.service.pagoscfdi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.service.pagoscfdi package. 
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

    private final static QName _ObtienePagoCFDIResponse_QNAME = new QName("http://pagoscfdi.service.imss.gob.mx/", "obtienePagoCFDIResponse");
    private final static QName _ObtienePagoCFDI_QNAME = new QName("http://pagoscfdi.service.imss.gob.mx/", "obtienePagoCFDI");
    private final static QName _ResponseListaInfoPagosCFDIRegPatronVO_QNAME = new QName("", "listaInfoPagosCFDIRegPatronVO");
    private final static QName _PagosCFDIRegitroPatronalActRCV_QNAME = new QName("", "actRCV");
    private final static QName _PagosCFDIRegitroPatronalSubTotRCV_QNAME = new QName("", "subTotRCV");
    private final static QName _PagosCFDIRegitroPatronalRfc_QNAME = new QName("", "rfc");
    private final static QName _PagosCFDIRegitroPatronalCfdiXml_QNAME = new QName("", "cfdiXml");
    private final static QName _PagosCFDIRegitroPatronalNombre_QNAME = new QName("", "nombre");
    private final static QName _PagosCFDIRegitroPatronalActIMSS_QNAME = new QName("", "actIMSS");
    private final static QName _PagosCFDIRegitroPatronalNrp_QNAME = new QName("", "nrp");
    private final static QName _PagosCFDIRegitroPatronalRecIMSS_QNAME = new QName("", "recIMSS");
    private final static QName _PagosCFDIRegitroPatronalFechaProceso_QNAME = new QName("", "fechaProceso");
    private final static QName _PagosCFDIRegitroPatronalEntidadRecaudadora_QNAME = new QName("", "entidadRecaudadora");
    private final static QName _PagosCFDIRegitroPatronalFolSua_QNAME = new QName("", "folSua");
    private final static QName _PagosCFDIRegitroPatronalRecRCV_QNAME = new QName("", "recRCV");
    private final static QName _PagosCFDIRegitroPatronalUuid_QNAME = new QName("", "uuid");
    private final static QName _PagosCFDIRegitroPatronalPeriodo_QNAME = new QName("", "periodo");
    private final static QName _PagosCFDIRegitroPatronalEstatus_QNAME = new QName("", "estatus");
    private final static QName _PagosCFDIRegitroPatronalFechaPago_QNAME = new QName("", "fechaPago");
    private final static QName _PagosCFDIRegitroPatronalSubTotIMSS_QNAME = new QName("", "subTotIMSS");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.service.pagoscfdi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MessageWSConsPagosCFDIRegPatron }
     * 
     */
    public MessageWSConsPagosCFDIRegPatron createMessageWSConsPagosCFDIRegPatron() {
        return new MessageWSConsPagosCFDIRegPatron();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link ObtienePagoCFDIResponse }
     * 
     */
    public ObtienePagoCFDIResponse createObtienePagoCFDIResponse() {
        return new ObtienePagoCFDIResponse();
    }

    /**
     * Create an instance of {@link PagosCFDIRegitroPatronal }
     * 
     */
    public PagosCFDIRegitroPatronal createPagosCFDIRegitroPatronal() {
        return new PagosCFDIRegitroPatronal();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link ObtienePagoCFDI }
     * 
     */
    public ObtienePagoCFDI createObtienePagoCFDI() {
        return new ObtienePagoCFDI();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtienePagoCFDIResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pagoscfdi.service.imss.gob.mx/", name = "obtienePagoCFDIResponse")
    public JAXBElement<ObtienePagoCFDIResponse> createObtienePagoCFDIResponse(ObtienePagoCFDIResponse value) {
        return new JAXBElement<ObtienePagoCFDIResponse>(_ObtienePagoCFDIResponse_QNAME, ObtienePagoCFDIResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtienePagoCFDI }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://pagoscfdi.service.imss.gob.mx/", name = "obtienePagoCFDI")
    public JAXBElement<ObtienePagoCFDI> createObtienePagoCFDI(ObtienePagoCFDI value) {
        return new JAXBElement<ObtienePagoCFDI>(_ObtienePagoCFDI_QNAME, ObtienePagoCFDI.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaPagosCFDIRegitroPatronal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "listaInfoPagosCFDIRegPatronVO", scope = Response.class)
    public JAXBElement<ListaPagosCFDIRegitroPatronal> createResponseListaInfoPagosCFDIRegPatronVO(ListaPagosCFDIRegitroPatronal value) {
        return new JAXBElement<ListaPagosCFDIRegitroPatronal>(_ResponseListaInfoPagosCFDIRegPatronVO_QNAME, ListaPagosCFDIRegitroPatronal.class, Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "actRCV", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalActRCV(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalActRCV_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "subTotRCV", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalSubTotRCV(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalSubTotRCV_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "rfc", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalRfc(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalRfc_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cfdiXml", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalCfdiXml(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalCfdiXml_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nombre", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalNombre(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalNombre_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "actIMSS", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalActIMSS(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalActIMSS_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nrp", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalNrp(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalNrp_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "recIMSS", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalRecIMSS(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalRecIMSS_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fechaProceso", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalFechaProceso(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalFechaProceso_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "entidadRecaudadora", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<Integer> createPagosCFDIRegitroPatronalEntidadRecaudadora(Integer value) {
        return new JAXBElement<Integer>(_PagosCFDIRegitroPatronalEntidadRecaudadora_QNAME, Integer.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "folSua", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<Integer> createPagosCFDIRegitroPatronalFolSua(Integer value) {
        return new JAXBElement<Integer>(_PagosCFDIRegitroPatronalFolSua_QNAME, Integer.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "recRCV", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalRecRCV(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalRecRCV_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "uuid", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalUuid(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalUuid_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "periodo", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<Integer> createPagosCFDIRegitroPatronalPeriodo(Integer value) {
        return new JAXBElement<Integer>(_PagosCFDIRegitroPatronalPeriodo_QNAME, Integer.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "estatus", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalEstatus(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalEstatus_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fechaPago", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalFechaPago(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalFechaPago_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "subTotIMSS", scope = PagosCFDIRegitroPatronal.class)
    public JAXBElement<String> createPagosCFDIRegitroPatronalSubTotIMSS(String value) {
        return new JAXBElement<String>(_PagosCFDIRegitroPatronalSubTotIMSS_QNAME, String.class, PagosCFDIRegitroPatronal.class, value);
    }

}
