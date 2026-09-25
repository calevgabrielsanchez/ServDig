
package mx.gob.imss.vigenciagrupofamte;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.vigenciaderechosV2 package. 
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

    private final static QName _ObtieneInfoCabGpoFamResponse_QNAME = new QName("http://service.imss.gob.mx/", "obtieneInfoCabGpoFamResponse");
    private final static QName _ObtieneInfoCabGpoFam_QNAME = new QName("http://service.imss.gob.mx/", "obtieneInfoCabGpoFam");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.vigenciaderechosV2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObtieneInfoCabGpoFam }
     * 
     */
    public ObtieneInfoCabGpoFam createObtieneInfoCabGpoFam() {
        return new ObtieneInfoCabGpoFam();
    }

    /**
     * Create an instance of {@link ObtieneInfoCabGpoFamResponse }
     * 
     */
    public ObtieneInfoCabGpoFamResponse createObtieneInfoCabGpoFamResponse() {
        return new ObtieneInfoCabGpoFamResponse();
    }

    /**
     * Create an instance of {@link RespuestaWS }
     * 
     */
    public RespuestaWS createRespuestaWS() {
        return new RespuestaWS();
    }

    /**
     * Create an instance of {@link RespuestaWSConsInfoCabGpoFam }
     * 
     */
    public RespuestaWSConsInfoCabGpoFam createRespuestaWSConsInfoCabGpoFam() {
        return new RespuestaWSConsInfoCabGpoFam();
    }

    /**
     * Create an instance of {@link InfoCabezaGrupoFamiliarVO }
     * 
     */
    public InfoCabezaGrupoFamiliarVO createInfoCabezaGrupoFamiliarVO() {
        return new InfoCabezaGrupoFamiliarVO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneInfoCabGpoFamResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.imss.gob.mx/", name = "obtieneInfoCabGpoFamResponse")
    public JAXBElement<ObtieneInfoCabGpoFamResponse> createObtieneInfoCabGpoFamResponse(ObtieneInfoCabGpoFamResponse value) {
        return new JAXBElement<ObtieneInfoCabGpoFamResponse>(_ObtieneInfoCabGpoFamResponse_QNAME, ObtieneInfoCabGpoFamResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtieneInfoCabGpoFam }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.imss.gob.mx/", name = "obtieneInfoCabGpoFam")
    public JAXBElement<ObtieneInfoCabGpoFam> createObtieneInfoCabGpoFam(ObtieneInfoCabGpoFam value) {
        return new JAXBElement<ObtieneInfoCabGpoFam>(_ObtieneInfoCabGpoFam_QNAME, ObtieneInfoCabGpoFam.class, null, value);
    }

}
