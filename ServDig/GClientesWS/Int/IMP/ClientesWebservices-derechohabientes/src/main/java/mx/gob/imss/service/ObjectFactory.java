
package mx.gob.imss.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.gob.imss.service package. 
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
    private final static QName _InfoCabezaGrupoFamiliarVOFecValidezConstancia_QNAME = new QName("", "fecValidezConstancia");
    private final static QName _InfoCabezaGrupoFamiliarVOCveIdTipoMovimiento_QNAME = new QName("", "cveIdTipoMovimiento");
    private final static QName _InfoCabezaGrupoFamiliarVOIndPatronImss_QNAME = new QName("", "indPatronImss");
    private final static QName _InfoCabezaGrupoFamiliarVOFecUltimoMovto_QNAME = new QName("", "fecUltimoMovto");
    private final static QName _InfoCabezaGrupoFamiliarVOCveIdCalidadParentesco_QNAME = new QName("", "cveIdCalidadParentesco");
    private final static QName _InfoCabezaGrupoFamiliarVOCveIdAsignacionNss_QNAME = new QName("", "cveIdAsignacionNss");
    private final static QName _InfoCabezaGrupoFamiliarVOCveIdPatronGeneral_QNAME = new QName("", "cveIdPatronGeneral");
    private final static QName _InfoCabezaGrupoFamiliarVOFecInicioVigencia_QNAME = new QName("", "fecInicioVigencia");
    private final static QName _InfoCabezaGrupoFamiliarVOFecFinVigencia_QNAME = new QName("", "fecFinVigencia");
    private final static QName _InfoCabezaGrupoFamiliarVOCveEstadoDerechohabiente_QNAME = new QName("", "cveEstadoDerechohabiente");
    private final static QName _InfoCabezaGrupoFamiliarVOCveSubestadoDerechohabiente_QNAME = new QName("", "cveSubestadoDerechohabiente");
    private final static QName _InfoCabezaGrupoFamiliarVOConDerechoSm_QNAME = new QName("", "conDerechoSm");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.gob.imss.service
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecValidezConstancia", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<String> createInfoCabezaGrupoFamiliarVOFecValidezConstancia(String value) {
        return new JAXBElement<String>(_InfoCabezaGrupoFamiliarVOFecValidezConstancia_QNAME, String.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdTipoMovimiento", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<Integer> createInfoCabezaGrupoFamiliarVOCveIdTipoMovimiento(Integer value) {
        return new JAXBElement<Integer>(_InfoCabezaGrupoFamiliarVOCveIdTipoMovimiento_QNAME, Integer.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "indPatronImss", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<Integer> createInfoCabezaGrupoFamiliarVOIndPatronImss(Integer value) {
        return new JAXBElement<Integer>(_InfoCabezaGrupoFamiliarVOIndPatronImss_QNAME, Integer.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecUltimoMovto", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<String> createInfoCabezaGrupoFamiliarVOFecUltimoMovto(String value) {
        return new JAXBElement<String>(_InfoCabezaGrupoFamiliarVOFecUltimoMovto_QNAME, String.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdCalidadParentesco", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<Integer> createInfoCabezaGrupoFamiliarVOCveIdCalidadParentesco(Integer value) {
        return new JAXBElement<Integer>(_InfoCabezaGrupoFamiliarVOCveIdCalidadParentesco_QNAME, Integer.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdAsignacionNss", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<Integer> createInfoCabezaGrupoFamiliarVOCveIdAsignacionNss(Integer value) {
        return new JAXBElement<Integer>(_InfoCabezaGrupoFamiliarVOCveIdAsignacionNss_QNAME, Integer.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveIdPatronGeneral", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<Integer> createInfoCabezaGrupoFamiliarVOCveIdPatronGeneral(Integer value) {
        return new JAXBElement<Integer>(_InfoCabezaGrupoFamiliarVOCveIdPatronGeneral_QNAME, Integer.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecInicioVigencia", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<String> createInfoCabezaGrupoFamiliarVOFecInicioVigencia(String value) {
        return new JAXBElement<String>(_InfoCabezaGrupoFamiliarVOFecInicioVigencia_QNAME, String.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "fecFinVigencia", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<String> createInfoCabezaGrupoFamiliarVOFecFinVigencia(String value) {
        return new JAXBElement<String>(_InfoCabezaGrupoFamiliarVOFecFinVigencia_QNAME, String.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveEstadoDerechohabiente", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<Integer> createInfoCabezaGrupoFamiliarVOCveEstadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfoCabezaGrupoFamiliarVOCveEstadoDerechohabiente_QNAME, Integer.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "cveSubestadoDerechohabiente", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<Integer> createInfoCabezaGrupoFamiliarVOCveSubestadoDerechohabiente(Integer value) {
        return new JAXBElement<Integer>(_InfoCabezaGrupoFamiliarVOCveSubestadoDerechohabiente_QNAME, Integer.class, InfoCabezaGrupoFamiliarVO.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "conDerechoSm", scope = InfoCabezaGrupoFamiliarVO.class)
    public JAXBElement<String> createInfoCabezaGrupoFamiliarVOConDerechoSm(String value) {
        return new JAXBElement<String>(_InfoCabezaGrupoFamiliarVOConDerechoSm_QNAME, String.class, InfoCabezaGrupoFamiliarVO.class, value);
    }



}
