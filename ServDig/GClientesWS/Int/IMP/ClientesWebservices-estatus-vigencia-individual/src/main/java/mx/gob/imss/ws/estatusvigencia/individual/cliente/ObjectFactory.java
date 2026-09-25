
package mx.gob.imss.ws.estatusvigencia.individual.cliente;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.situacionaseguramiento package. 
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

    private final static QName _GetSituacionAseguramientoXAsginacionNSS_QNAME = new QName("http://situacionAseguramiento.imss.gob.mx/", "getSituacionAseguramientoXAsginacionNSS");
    private final static QName _GetSituacionAseguramientoXAsginacionNSSResponse_QNAME = new QName("http://situacionAseguramiento.imss.gob.mx/", "getSituacionAseguramientoXAsginacionNSSResponse");
    private final static QName _SituacionAseguramientoVONumeroSemanaAseguramientoBaja_QNAME = new QName("", "numeroSemanaAseguramientoBaja");
    private final static QName _SituacionAseguramientoVOTipoAseguradoBaja_QNAME = new QName("", "tipoAseguradoBaja");
    private final static QName _SituacionAseguramientoVONrpBaja_QNAME = new QName("", "nrpBaja");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.situacionaseguramiento
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SituacionAseguramientoVO }
     * 
     */
    public SituacionAseguramientoVO createSituacionAseguramientoVO() {
        return new SituacionAseguramientoVO();
    }

    /**
     * Create an instance of {@link ModalidadFecha }
     * 
     */
    public ModalidadFecha createModalidadFecha() {
        return new ModalidadFecha();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link GetSituacionAseguramientoXAsginacionNSSResponse }
     * 
     */
    public GetSituacionAseguramientoXAsginacionNSSResponse createGetSituacionAseguramientoXAsginacionNSSResponse() {
        return new GetSituacionAseguramientoXAsginacionNSSResponse();
    }

    /**
     * Create an instance of {@link GetSituacionAseguramientoXAsginacionNSS }
     * 
     */
    public GetSituacionAseguramientoXAsginacionNSS createGetSituacionAseguramientoXAsginacionNSS() {
        return new GetSituacionAseguramientoXAsginacionNSS();
    }

    /**
     * Create an instance of {@link RespuestaSituacionAseguramiento }
     * 
     */
    public RespuestaSituacionAseguramiento createRespuestaSituacionAseguramiento() {
        return new RespuestaSituacionAseguramiento();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSituacionAseguramientoXAsginacionNSS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://situacionAseguramiento.imss.gob.mx/", name = "getSituacionAseguramientoXAsginacionNSS")
    public JAXBElement<GetSituacionAseguramientoXAsginacionNSS> createGetSituacionAseguramientoXAsginacionNSS(GetSituacionAseguramientoXAsginacionNSS value) {
        return new JAXBElement<GetSituacionAseguramientoXAsginacionNSS>(_GetSituacionAseguramientoXAsginacionNSS_QNAME, GetSituacionAseguramientoXAsginacionNSS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSituacionAseguramientoXAsginacionNSSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://situacionAseguramiento.imss.gob.mx/", name = "getSituacionAseguramientoXAsginacionNSSResponse")
    public JAXBElement<GetSituacionAseguramientoXAsginacionNSSResponse> createGetSituacionAseguramientoXAsginacionNSSResponse(GetSituacionAseguramientoXAsginacionNSSResponse value) {
        return new JAXBElement<GetSituacionAseguramientoXAsginacionNSSResponse>(_GetSituacionAseguramientoXAsginacionNSSResponse_QNAME, GetSituacionAseguramientoXAsginacionNSSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "numeroSemanaAseguramientoBaja", scope = SituacionAseguramientoVO.class)
    public JAXBElement<Integer> createSituacionAseguramientoVONumeroSemanaAseguramientoBaja(Integer value) {
        return new JAXBElement<Integer>(_SituacionAseguramientoVONumeroSemanaAseguramientoBaja_QNAME, Integer.class, SituacionAseguramientoVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipoAseguradoBaja", scope = SituacionAseguramientoVO.class)
    public JAXBElement<Integer> createSituacionAseguramientoVOTipoAseguradoBaja(Integer value) {
        return new JAXBElement<Integer>(_SituacionAseguramientoVOTipoAseguradoBaja_QNAME, Integer.class, SituacionAseguramientoVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "nrpBaja", scope = SituacionAseguramientoVO.class)
    public JAXBElement<String> createSituacionAseguramientoVONrpBaja(String value) {
        return new JAXBElement<String>(_SituacionAseguramientoVONrpBaja_QNAME, String.class, SituacionAseguramientoVO.class, value);
    }

}
