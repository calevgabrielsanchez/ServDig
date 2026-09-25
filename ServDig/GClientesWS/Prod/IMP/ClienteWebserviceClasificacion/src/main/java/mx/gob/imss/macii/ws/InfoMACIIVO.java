
package mx.gob.imss.macii.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for infoMACIIVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="infoMACIIVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NRP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nombre_Rs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cve_Delegacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Cve_Subdelegacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Cve_Municipio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fec_Registro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fec_Revision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fec_Movimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cve_Tipo_Persona" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Tipo_Persona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipo_Registro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cve_Estatus" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Estatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Clase_Declarada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fraccion_Declarada" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Prima_Declarada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Clase_Rectificada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fraccion_Rectificada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Prima_Rectificada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Folio_Resolucion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cve_Ciz" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Sistema_Origen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fec_Extraccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipo_Movimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Tipo_Tramite" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Desc_Tipo_Tramite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoMACIIVO", propOrder = {
    "nrp",
    "nombreRs",
    "cveDelegacion",
    "cveSubdelegacion",
    "cveMunicipio",
    "fecRegistro",
    "fecRevision",
    "fecMovimiento",
    "cveTipoPersona",
    "tipoPersona",
    "tipoRegistro",
    "cveEstatus",
    "estatus",
    "claseDeclarada",
    "fraccionDeclarada",
    "primaDeclarada",
    "claseRectificada",
    "fraccionRectificada",
    "primaRectificada",
    "folioResolucion",
    "cveCiz",
    "sistemaOrigen",
    "fecExtraccion",
    "tipoMovimiento",
    "tipoTramite",
    "descTipoTramite"
})
public class InfoMACIIVO {

    @XmlElement(name = "NRP")
    protected String nrp;
    @XmlElement(name = "Nombre_Rs")
    protected String nombreRs;
    @XmlElement(name = "Cve_Delegacion")
    protected Integer cveDelegacion;
    @XmlElement(name = "Cve_Subdelegacion")
    protected Integer cveSubdelegacion;
    @XmlElement(name = "Cve_Municipio")
    protected String cveMunicipio;
    @XmlElement(name = "Fec_Registro")
    protected String fecRegistro;
    @XmlElement(name = "Fec_Revision")
    protected String fecRevision;
    @XmlElement(name = "Fec_Movimiento")
    protected String fecMovimiento;
    @XmlElement(name = "Cve_Tipo_Persona")
    protected Integer cveTipoPersona;
    @XmlElement(name = "Tipo_Persona")
    protected String tipoPersona;
    @XmlElement(name = "Tipo_Registro")
    protected String tipoRegistro;
    @XmlElement(name = "Cve_Estatus")
    protected Integer cveEstatus;
    @XmlElement(name = "Estatus")
    protected String estatus;
    @XmlElement(name = "Clase_Declarada")
    protected String claseDeclarada;
    @XmlElement(name = "Fraccion_Declarada")
    protected Integer fraccionDeclarada;
    @XmlElement(name = "Prima_Declarada")
    protected String primaDeclarada;
    @XmlElement(name = "Clase_Rectificada")
    protected String claseRectificada;
    @XmlElement(name = "Fraccion_Rectificada")
    protected String fraccionRectificada;
    @XmlElement(name = "Prima_Rectificada")
    protected String primaRectificada;
    @XmlElement(name = "Folio_Resolucion")
    protected String folioResolucion;
    @XmlElement(name = "Cve_Ciz")
    protected Integer cveCiz;
    @XmlElement(name = "Sistema_Origen")
    protected String sistemaOrigen;
    @XmlElement(name = "Fec_Extraccion")
    protected String fecExtraccion;
    @XmlElement(name = "Tipo_Movimiento")
    protected String tipoMovimiento;
    @XmlElement(name = "Tipo_Tramite")
    protected Integer tipoTramite;
    @XmlElement(name = "Desc_Tipo_Tramite")
    protected String descTipoTramite;

    /**
     * Gets the value of the nrp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNRP() {
        return nrp;
    }

    /**
     * Sets the value of the nrp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNRP(String value) {
        this.nrp = value;
    }

    /**
     * Gets the value of the nombreRs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreRs() {
        return nombreRs;
    }

    /**
     * Sets the value of the nombreRs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreRs(String value) {
        this.nombreRs = value;
    }

    /**
     * Gets the value of the cveDelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveDelegacion() {
        return cveDelegacion;
    }

    /**
     * Sets the value of the cveDelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveDelegacion(Integer value) {
        this.cveDelegacion = value;
    }

    /**
     * Gets the value of the cveSubdelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveSubdelegacion() {
        return cveSubdelegacion;
    }

    /**
     * Sets the value of the cveSubdelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveSubdelegacion(Integer value) {
        this.cveSubdelegacion = value;
    }

    /**
     * Gets the value of the cveMunicipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveMunicipio() {
        return cveMunicipio;
    }

    /**
     * Sets the value of the cveMunicipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveMunicipio(String value) {
        this.cveMunicipio = value;
    }

    /**
     * Gets the value of the fecRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecRegistro() {
        return fecRegistro;
    }

    /**
     * Sets the value of the fecRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecRegistro(String value) {
        this.fecRegistro = value;
    }

    /**
     * Gets the value of the fecRevision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecRevision() {
        return fecRevision;
    }

    /**
     * Sets the value of the fecRevision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecRevision(String value) {
        this.fecRevision = value;
    }

    /**
     * Gets the value of the fecMovimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecMovimiento() {
        return fecMovimiento;
    }

    /**
     * Sets the value of the fecMovimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecMovimiento(String value) {
        this.fecMovimiento = value;
    }

    /**
     * Gets the value of the cveTipoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveTipoPersona() {
        return cveTipoPersona;
    }

    /**
     * Sets the value of the cveTipoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveTipoPersona(Integer value) {
        this.cveTipoPersona = value;
    }

    /**
     * Gets the value of the tipoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Sets the value of the tipoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPersona(String value) {
        this.tipoPersona = value;
    }

    /**
     * Gets the value of the tipoRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * Sets the value of the tipoRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRegistro(String value) {
        this.tipoRegistro = value;
    }

    /**
     * Gets the value of the cveEstatus property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveEstatus() {
        return cveEstatus;
    }

    /**
     * Sets the value of the cveEstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveEstatus(Integer value) {
        this.cveEstatus = value;
    }

    /**
     * Gets the value of the estatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * Sets the value of the estatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstatus(String value) {
        this.estatus = value;
    }

    /**
     * Gets the value of the claseDeclarada property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaseDeclarada() {
        return claseDeclarada;
    }

    /**
     * Sets the value of the claseDeclarada property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaseDeclarada(String value) {
        this.claseDeclarada = value;
    }

    /**
     * Gets the value of the fraccionDeclarada property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFraccionDeclarada() {
        return fraccionDeclarada;
    }

    /**
     * Sets the value of the fraccionDeclarada property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFraccionDeclarada(Integer value) {
        this.fraccionDeclarada = value;
    }

    /**
     * Gets the value of the primaDeclarada property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaDeclarada() {
        return primaDeclarada;
    }

    /**
     * Sets the value of the primaDeclarada property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaDeclarada(String value) {
        this.primaDeclarada = value;
    }

    /**
     * Gets the value of the claseRectificada property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaseRectificada() {
        return claseRectificada;
    }

    /**
     * Sets the value of the claseRectificada property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaseRectificada(String value) {
        this.claseRectificada = value;
    }

    /**
     * Gets the value of the fraccionRectificada property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFraccionRectificada() {
        return fraccionRectificada;
    }

    /**
     * Sets the value of the fraccionRectificada property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFraccionRectificada(String value) {
        this.fraccionRectificada = value;
    }

    /**
     * Gets the value of the primaRectificada property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaRectificada() {
        return primaRectificada;
    }

    /**
     * Sets the value of the primaRectificada property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaRectificada(String value) {
        this.primaRectificada = value;
    }

    /**
     * Gets the value of the folioResolucion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioResolucion() {
        return folioResolucion;
    }

    /**
     * Sets the value of the folioResolucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioResolucion(String value) {
        this.folioResolucion = value;
    }

    /**
     * Gets the value of the cveCiz property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCveCiz() {
        return cveCiz;
    }

    /**
     * Sets the value of the cveCiz property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCveCiz(Integer value) {
        this.cveCiz = value;
    }

    /**
     * Gets the value of the sistemaOrigen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSistemaOrigen() {
        return sistemaOrigen;
    }

    /**
     * Sets the value of the sistemaOrigen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSistemaOrigen(String value) {
        this.sistemaOrigen = value;
    }

    /**
     * Gets the value of the fecExtraccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecExtraccion() {
        return fecExtraccion;
    }

    /**
     * Sets the value of the fecExtraccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecExtraccion(String value) {
        this.fecExtraccion = value;
    }

    /**
     * Gets the value of the tipoMovimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * Sets the value of the tipoMovimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoMovimiento(String value) {
        this.tipoMovimiento = value;
    }

    /**
     * Gets the value of the tipoTramite property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoTramite() {
        return tipoTramite;
    }

    /**
     * Sets the value of the tipoTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoTramite(Integer value) {
        this.tipoTramite = value;
    }

    /**
     * Gets the value of the descTipoTramite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescTipoTramite() {
        return descTipoTramite;
    }

    /**
     * Sets the value of the descTipoTramite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescTipoTramite(String value) {
        this.descTipoTramite = value;
    }

}
