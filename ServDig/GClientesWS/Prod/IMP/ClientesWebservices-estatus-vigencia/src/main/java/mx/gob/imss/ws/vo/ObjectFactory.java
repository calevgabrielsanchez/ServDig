
package mx.gob.imss.ws.vo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.ws.vo package. 
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

    private final static QName _GetConsultaMod33Response_QNAME = new QName("http://vo.ws.imss.gob.mx/", "getConsultaMod33Response");
    private final static QName _GetConsultaMod33_QNAME = new QName("http://vo.ws.imss.gob.mx/", "getConsultaMod33");
    private final static QName _ReturnClaveError_QNAME = new QName("", "claveError");
    private final static QName _ReturnResultado_QNAME = new QName("", "resultado");
    private final static QName _ReturnMensajeError_QNAME = new QName("", "mensajeError");
    private final static QName _ResultadoFecUltimaBajaMod33_QNAME = new QName("", "fecUltimaBajaMod33");
    private final static QName _ResultadoFecUltimaBajaObligatorio_QNAME = new QName("", "fecUltimaBajaObligatorio");
    private final static QName _ModalidadModalidad_QNAME = new QName("", "modalidad");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.ws.vo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetConsultaMod33Response }
     * 
     */
    public GetConsultaMod33Response createGetConsultaMod33Response() {
        return new GetConsultaMod33Response();
    }

    /**
     * Create an instance of {@link GetConsultaMod33 }
     * 
     */
    public GetConsultaMod33 createGetConsultaMod33() {
        return new GetConsultaMod33();
    }

    /**
     * Create an instance of {@link Resultado }
     * 
     */
    public Resultado createResultado() {
        return new Resultado();
    }

    /**
     * Create an instance of {@link Modalidad }
     * 
     */
    public Modalidad createModalidad() {
        return new Modalidad();
    }

    /**
     * Create an instance of {@link Return }
     * 
     */
    public Return createReturn() {
        return new Return();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConsultaMod33Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://vo.ws.imss.gob.mx/", name = "getConsultaMod33Response")
    public JAXBElement<GetConsultaMod33Response> createGetConsultaMod33Response(GetConsultaMod33Response value) {
        return new JAXBElement<GetConsultaMod33Response>(_GetConsultaMod33Response_QNAME, GetConsultaMod33Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConsultaMod33 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://vo.ws.imss.gob.mx/", name = "getConsultaMod33")
    public JAXBElement<GetConsultaMod33> createGetConsultaMod33(GetConsultaMod33 value) {
        return new JAXBElement<GetConsultaMod33>(_GetConsultaMod33_QNAME, GetConsultaMod33 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "claveError", scope = Return.class)
    public JAXBElement<Integer> createReturnClaveError(Integer value) {
        return new JAXBElement<Integer>(_ReturnClaveError_QNAME, Integer.class, Return.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Resultado }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "resultado", scope = Return.class)
    public JAXBElement<Resultado> createReturnResultado(Resultado value) {
        return new JAXBElement<Resultado>(_ReturnResultado_QNAME, Resultado.class, Return.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "mensajeError", scope = Return.class)
    public JAXBElement<String> createReturnMensajeError(String value) {
        return new JAXBElement<String>(_ReturnMensajeError_QNAME, String.class, Return.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecUltimaBajaMod33", scope = Resultado.class)
    public JAXBElement<String> createResultadoFecUltimaBajaMod33(String value) {
        return new JAXBElement<String>(_ResultadoFecUltimaBajaMod33_QNAME, String.class, Resultado.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecUltimaBajaObligatorio", scope = Resultado.class)
    public JAXBElement<String> createResultadoFecUltimaBajaObligatorio(String value) {
        return new JAXBElement<String>(_ResultadoFecUltimaBajaObligatorio_QNAME, String.class, Resultado.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "modalidad", scope = Modalidad.class)
    public JAXBElement<String> createModalidadModalidad(String value) {
        return new JAXBElement<String>(_ModalidadModalidad_QNAME, String.class, Modalidad.class, value);
    }

}
