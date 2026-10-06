
package mx.gob.imss.consultamod40;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.imss.cliente package. 
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

    private final static QName _GetConsultaMod40Response_QNAME = new QName("http://consultaMod40.imss.gob.mx/", "getConsultaMod40Response");
    private final static QName _GetConsultaMod40_QNAME = new QName("http://consultaMod40.imss.gob.mx/", "getConsultaMod40");
    private final static QName _Modalidad40VOModUltimoMod40_QNAME = new QName("", "modUltimoMod40");
    private final static QName _Modalidad40VOSalarioObligatorio_QNAME = new QName("", "salarioObligatorio");
    private final static QName _Modalidad40VOTipoMovObligatorio_QNAME = new QName("", "tipoMovObligatorio");
    private final static QName _Modalidad40VORegPatUltimoMod40_QNAME = new QName("", "regPatUltimoMod40");
    private final static QName _Modalidad40VOModUltimoObligatorio_QNAME = new QName("", "modUltimoObligatorio");
    private final static QName _Modalidad40VOTipoPension_QNAME = new QName("", "tipoPension");
    private final static QName _Modalidad40VOFecMovMod40_QNAME = new QName("", "fecMovMod40");
    private final static QName _Modalidad40VOSalarioMod40_QNAME = new QName("", "salarioMod40");
    private final static QName _Modalidad40VORegPatUltimoObligatorio_QNAME = new QName("", "regPatUltimoObligatorio");
    private final static QName _Modalidad40VOTipoMovMod40_QNAME = new QName("", "tipoMovMod40");
    private final static QName _Modalidad40VOFecMovObligatorio_QNAME = new QName("", "fecMovObligatorio");
    private final static QName _RespuestaWSMensajeError_QNAME = new QName("", "mensajeError");
    private final static QName _ModalidadVigenteModalidad_QNAME = new QName("", "modalidad");
    private final static QName _ModalidadVigenteRegistroPatronal_QNAME = new QName("", "registroPatronal");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.imss.cliente
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetConsultaMod40Response }
     * 
     */
    public GetConsultaMod40Response createGetConsultaMod40Response() {
        return new GetConsultaMod40Response();
    }

    /**
     * Create an instance of {@link GetConsultaMod40 }
     * 
     */
    public GetConsultaMod40 createGetConsultaMod40() {
        return new GetConsultaMod40();
    }

    /**
     * Create an instance of {@link Modalidad40VO }
     * 
     */
    public Modalidad40VO createModalidad40VO() {
        return new Modalidad40VO();
    }

    /**
     * Create an instance of {@link RespuestaModalidad40 }
     * 
     */
    public RespuestaModalidad40 createRespuestaModalidad40() {
        return new RespuestaModalidad40();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link ModalidadVigente }
     * 
     */
    public ModalidadVigente createModalidadVigente() {
        return new ModalidadVigente();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConsultaMod40Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://consultaMod40.imss.gob.mx/", name = "getConsultaMod40Response")
    public JAXBElement<GetConsultaMod40Response> createGetConsultaMod40Response(GetConsultaMod40Response value) {
        return new JAXBElement<GetConsultaMod40Response>(_GetConsultaMod40Response_QNAME, GetConsultaMod40Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConsultaMod40 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://consultaMod40.imss.gob.mx/", name = "getConsultaMod40")
    public JAXBElement<GetConsultaMod40> createGetConsultaMod40(GetConsultaMod40 value) {
        return new JAXBElement<GetConsultaMod40>(_GetConsultaMod40_QNAME, GetConsultaMod40 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "modUltimoMod40", scope = Modalidad40VO.class)
    public JAXBElement<String> createModalidad40VOModUltimoMod40(String value) {
        return new JAXBElement<String>(_Modalidad40VOModUltimoMod40_QNAME, String.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "salarioObligatorio", scope = Modalidad40VO.class)
    public JAXBElement<Float> createModalidad40VOSalarioObligatorio(Float value) {
        return new JAXBElement<Float>(_Modalidad40VOSalarioObligatorio_QNAME, Float.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipoMovObligatorio", scope = Modalidad40VO.class)
    public JAXBElement<String> createModalidad40VOTipoMovObligatorio(String value) {
        return new JAXBElement<String>(_Modalidad40VOTipoMovObligatorio_QNAME, String.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "regPatUltimoMod40", scope = Modalidad40VO.class)
    public JAXBElement<String> createModalidad40VORegPatUltimoMod40(String value) {
        return new JAXBElement<String>(_Modalidad40VORegPatUltimoMod40_QNAME, String.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "modUltimoObligatorio", scope = Modalidad40VO.class)
    public JAXBElement<String> createModalidad40VOModUltimoObligatorio(String value) {
        return new JAXBElement<String>(_Modalidad40VOModUltimoObligatorio_QNAME, String.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipoPension", scope = Modalidad40VO.class)
    public JAXBElement<String> createModalidad40VOTipoPension(String value) {
        return new JAXBElement<String>(_Modalidad40VOTipoPension_QNAME, String.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecMovMod40", scope = Modalidad40VO.class)
    public JAXBElement<String> createModalidad40VOFecMovMod40(String value) {
        return new JAXBElement<String>(_Modalidad40VOFecMovMod40_QNAME, String.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "salarioMod40", scope = Modalidad40VO.class)
    public JAXBElement<Float> createModalidad40VOSalarioMod40(Float value) {
        return new JAXBElement<Float>(_Modalidad40VOSalarioMod40_QNAME, Float.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "regPatUltimoObligatorio", scope = Modalidad40VO.class)
    public JAXBElement<String> createModalidad40VORegPatUltimoObligatorio(String value) {
        return new JAXBElement<String>(_Modalidad40VORegPatUltimoObligatorio_QNAME, String.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tipoMovMod40", scope = Modalidad40VO.class)
    public JAXBElement<String> createModalidad40VOTipoMovMod40(String value) {
        return new JAXBElement<String>(_Modalidad40VOTipoMovMod40_QNAME, String.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecMovObligatorio", scope = Modalidad40VO.class)
    public JAXBElement<String> createModalidad40VOFecMovObligatorio(String value) {
        return new JAXBElement<String>(_Modalidad40VOFecMovObligatorio_QNAME, String.class, Modalidad40VO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "mensajeError", scope = RespuestaWS.class)
    public JAXBElement<String> createRespuestaWSMensajeError(String value) {
        return new JAXBElement<String>(_RespuestaWSMensajeError_QNAME, String.class, RespuestaWS.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "modalidad", scope = ModalidadVigente.class)
    public JAXBElement<String> createModalidadVigenteModalidad(String value) {
        return new JAXBElement<String>(_ModalidadVigenteModalidad_QNAME, String.class, ModalidadVigente.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "registroPatronal", scope = ModalidadVigente.class)
    public JAXBElement<String> createModalidadVigenteRegistroPatronal(String value) {
        return new JAXBElement<String>(_ModalidadVigenteRegistroPatronal_QNAME, String.class, ModalidadVigente.class, value);
    }

}
