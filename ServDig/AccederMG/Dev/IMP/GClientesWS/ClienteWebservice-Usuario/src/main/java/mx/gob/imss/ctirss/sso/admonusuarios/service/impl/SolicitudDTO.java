
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for solicitudDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="solicitudDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="areaNorm" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}areaNormativaDTO" minOccurs="0"/>
 *         &lt;element name="cveAprobador" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="cveDelegacionNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveEstatusNom" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="cveIdEntidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveMatricula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveSsosolicitud" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="cveSubdelegacionNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveUmfNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="delDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}delegacionDTO" minOccurs="0"/>
 *         &lt;element name="departamentoDescNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="desTelefonoOfi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="desUsrCurp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dptoDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}departamentoDTO" minOccurs="0"/>
 *         &lt;element name="estatusDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}estatusDTO" minOccurs="0"/>
 *         &lt;element name="fecRegistroActualizado" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fecRegistroAlta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fecRegistroBaja" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fecUsrNacimiento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="modulosDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}moduloDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="nomMaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomNombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomPaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreAprobador" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nssNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="perfilesDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}perfilDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="puestoDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}puestoDTO" minOccurs="0"/>
 *         &lt;element name="puestoDescNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puestosDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}puestoDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="refCorreoElectronico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="subdelDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}subdelegacionDTO" minOccurs="0"/>
 *         &lt;element name="umfDTO" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}umfDTO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitudDTO", propOrder = {
    "areaNorm",
    "cveAprobador",
    "cveDelegacionNom",
    "cveEstatusNom",
    "cveIdEntidad",
    "cveMatricula",
    "cveSsosolicitud",
    "cveSubdelegacionNom",
    "cveUmfNom",
    "delDTO",
    "departamentoDescNom",
    "desTelefonoOfi",
    "desUsrCurp",
    "dptoDTO",
    "estatusDTO",
    "fecRegistroActualizado",
    "fecRegistroAlta",
    "fecRegistroBaja",
    "fecUsrNacimiento",
    "modulosDTO",
    "nomMaterno",
    "nomNombre",
    "nomPaterno",
    "nombreAprobador",
    "nssNom",
    "password",
    "perfilesDTO",
    "puestoDTO",
    "puestoDescNom",
    "puestosDTO",
    "refCorreoElectronico",
    "subdelDTO",
    "umfDTO"
})
public class SolicitudDTO {

    protected AreaNormativaDTO areaNorm;
    protected Long cveAprobador;
    protected String cveDelegacionNom;
    protected long cveEstatusNom;
    protected String cveIdEntidad;
    protected String cveMatricula;
    protected long cveSsosolicitud;
    protected String cveSubdelegacionNom;
    protected String cveUmfNom;
    protected DelegacionDTO delDTO;
    protected String departamentoDescNom;
    protected String desTelefonoOfi;
    protected String desUsrCurp;
    protected DepartamentoDTO dptoDTO;
    protected EstatusDTO estatusDTO;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroActualizado;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroAlta;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecRegistroBaja;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fecUsrNacimiento;
    @XmlElement(nillable = true)
    protected List<ModuloDTO> modulosDTO;
    protected String nomMaterno;
    protected String nomNombre;
    protected String nomPaterno;
    protected String nombreAprobador;
    protected String nssNom;
    protected String password;
    @XmlElement(nillable = true)
    protected List<PerfilDTO> perfilesDTO;
    protected PuestoDTO puestoDTO;
    protected String puestoDescNom;
    @XmlElement(nillable = true)
    protected List<PuestoDTO> puestosDTO;
    protected String refCorreoElectronico;
    protected SubdelegacionDTO subdelDTO;
    protected UmfDTO umfDTO;

    /**
     * Gets the value of the areaNorm property.
     * 
     * @return
     *     possible object is
     *     {@link AreaNormativaDTO }
     *     
     */
    public AreaNormativaDTO getAreaNorm() {
        return areaNorm;
    }

    /**
     * Sets the value of the areaNorm property.
     * 
     * @param value
     *     allowed object is
     *     {@link AreaNormativaDTO }
     *     
     */
    public void setAreaNorm(AreaNormativaDTO value) {
        this.areaNorm = value;
    }

    /**
     * Gets the value of the cveAprobador property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCveAprobador() {
        return cveAprobador;
    }

    /**
     * Sets the value of the cveAprobador property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCveAprobador(Long value) {
        this.cveAprobador = value;
    }

    /**
     * Gets the value of the cveDelegacionNom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveDelegacionNom() {
        return cveDelegacionNom;
    }

    /**
     * Sets the value of the cveDelegacionNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveDelegacionNom(String value) {
        this.cveDelegacionNom = value;
    }

    /**
     * Gets the value of the cveEstatusNom property.
     * 
     */
    public long getCveEstatusNom() {
        return cveEstatusNom;
    }

    /**
     * Sets the value of the cveEstatusNom property.
     * 
     */
    public void setCveEstatusNom(long value) {
        this.cveEstatusNom = value;
    }

    /**
     * Gets the value of the cveIdEntidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveIdEntidad() {
        return cveIdEntidad;
    }

    /**
     * Sets the value of the cveIdEntidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveIdEntidad(String value) {
        this.cveIdEntidad = value;
    }

    /**
     * Gets the value of the cveMatricula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveMatricula() {
        return cveMatricula;
    }

    /**
     * Sets the value of the cveMatricula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveMatricula(String value) {
        this.cveMatricula = value;
    }

    /**
     * Gets the value of the cveSsosolicitud property.
     * 
     */
    public long getCveSsosolicitud() {
        return cveSsosolicitud;
    }

    /**
     * Sets the value of the cveSsosolicitud property.
     * 
     */
    public void setCveSsosolicitud(long value) {
        this.cveSsosolicitud = value;
    }

    /**
     * Gets the value of the cveSubdelegacionNom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveSubdelegacionNom() {
        return cveSubdelegacionNom;
    }

    /**
     * Sets the value of the cveSubdelegacionNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveSubdelegacionNom(String value) {
        this.cveSubdelegacionNom = value;
    }

    /**
     * Gets the value of the cveUmfNom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveUmfNom() {
        return cveUmfNom;
    }

    /**
     * Sets the value of the cveUmfNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveUmfNom(String value) {
        this.cveUmfNom = value;
    }

    /**
     * Gets the value of the delDTO property.
     * 
     * @return
     *     possible object is
     *     {@link DelegacionDTO }
     *     
     */
    public DelegacionDTO getDelDTO() {
        return delDTO;
    }

    /**
     * Sets the value of the delDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link DelegacionDTO }
     *     
     */
    public void setDelDTO(DelegacionDTO value) {
        this.delDTO = value;
    }

    /**
     * Gets the value of the departamentoDescNom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartamentoDescNom() {
        return departamentoDescNom;
    }

    /**
     * Sets the value of the departamentoDescNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartamentoDescNom(String value) {
        this.departamentoDescNom = value;
    }

    /**
     * Gets the value of the desTelefonoOfi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesTelefonoOfi() {
        return desTelefonoOfi;
    }

    /**
     * Sets the value of the desTelefonoOfi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesTelefonoOfi(String value) {
        this.desTelefonoOfi = value;
    }

    /**
     * Gets the value of the desUsrCurp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesUsrCurp() {
        return desUsrCurp;
    }

    /**
     * Sets the value of the desUsrCurp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesUsrCurp(String value) {
        this.desUsrCurp = value;
    }

    /**
     * Gets the value of the dptoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link DepartamentoDTO }
     *     
     */
    public DepartamentoDTO getDptoDTO() {
        return dptoDTO;
    }

    /**
     * Sets the value of the dptoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartamentoDTO }
     *     
     */
    public void setDptoDTO(DepartamentoDTO value) {
        this.dptoDTO = value;
    }

    /**
     * Gets the value of the estatusDTO property.
     * 
     * @return
     *     possible object is
     *     {@link EstatusDTO }
     *     
     */
    public EstatusDTO getEstatusDTO() {
        return estatusDTO;
    }

    /**
     * Sets the value of the estatusDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstatusDTO }
     *     
     */
    public void setEstatusDTO(EstatusDTO value) {
        this.estatusDTO = value;
    }

    /**
     * Gets the value of the fecRegistroActualizado property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecRegistroActualizado() {
        return fecRegistroActualizado;
    }

    /**
     * Sets the value of the fecRegistroActualizado property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecRegistroActualizado(XMLGregorianCalendar value) {
        this.fecRegistroActualizado = value;
    }

    /**
     * Gets the value of the fecRegistroAlta property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecRegistroAlta() {
        return fecRegistroAlta;
    }

    /**
     * Sets the value of the fecRegistroAlta property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecRegistroAlta(XMLGregorianCalendar value) {
        this.fecRegistroAlta = value;
    }

    /**
     * Gets the value of the fecRegistroBaja property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecRegistroBaja() {
        return fecRegistroBaja;
    }

    /**
     * Sets the value of the fecRegistroBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecRegistroBaja(XMLGregorianCalendar value) {
        this.fecRegistroBaja = value;
    }

    /**
     * Gets the value of the fecUsrNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecUsrNacimiento() {
        return fecUsrNacimiento;
    }

    /**
     * Sets the value of the fecUsrNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecUsrNacimiento(XMLGregorianCalendar value) {
        this.fecUsrNacimiento = value;
    }

    /**
     * Gets the value of the modulosDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modulosDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModulosDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModuloDTO }
     * 
     * 
     */
    public List<ModuloDTO> getModulosDTO() {
        if (modulosDTO == null) {
            modulosDTO = new ArrayList<ModuloDTO>();
        }
        return this.modulosDTO;
    }

    /**
     * Gets the value of the nomMaterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomMaterno() {
        return nomMaterno;
    }

    /**
     * Sets the value of the nomMaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomMaterno(String value) {
        this.nomMaterno = value;
    }

    /**
     * Gets the value of the nomNombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomNombre() {
        return nomNombre;
    }

    /**
     * Sets the value of the nomNombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomNombre(String value) {
        this.nomNombre = value;
    }

    /**
     * Gets the value of the nomPaterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomPaterno() {
        return nomPaterno;
    }

    /**
     * Sets the value of the nomPaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomPaterno(String value) {
        this.nomPaterno = value;
    }

    /**
     * Gets the value of the nombreAprobador property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreAprobador() {
        return nombreAprobador;
    }

    /**
     * Sets the value of the nombreAprobador property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreAprobador(String value) {
        this.nombreAprobador = value;
    }

    /**
     * Gets the value of the nssNom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNssNom() {
        return nssNom;
    }

    /**
     * Sets the value of the nssNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNssNom(String value) {
        this.nssNom = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the perfilesDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the perfilesDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerfilesDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PerfilDTO }
     * 
     * 
     */
    public List<PerfilDTO> getPerfilesDTO() {
        if (perfilesDTO == null) {
            perfilesDTO = new ArrayList<PerfilDTO>();
        }
        return this.perfilesDTO;
    }

    /**
     * Gets the value of the puestoDTO property.
     * 
     * @return
     *     possible object is
     *     {@link PuestoDTO }
     *     
     */
    public PuestoDTO getPuestoDTO() {
        return puestoDTO;
    }

    /**
     * Sets the value of the puestoDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link PuestoDTO }
     *     
     */
    public void setPuestoDTO(PuestoDTO value) {
        this.puestoDTO = value;
    }

    /**
     * Gets the value of the puestoDescNom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPuestoDescNom() {
        return puestoDescNom;
    }

    /**
     * Sets the value of the puestoDescNom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPuestoDescNom(String value) {
        this.puestoDescNom = value;
    }

    /**
     * Gets the value of the puestosDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the puestosDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPuestosDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PuestoDTO }
     * 
     * 
     */
    public List<PuestoDTO> getPuestosDTO() {
        if (puestosDTO == null) {
            puestosDTO = new ArrayList<PuestoDTO>();
        }
        return this.puestosDTO;
    }

    /**
     * Gets the value of the refCorreoElectronico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefCorreoElectronico() {
        return refCorreoElectronico;
    }

    /**
     * Sets the value of the refCorreoElectronico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefCorreoElectronico(String value) {
        this.refCorreoElectronico = value;
    }

    /**
     * Gets the value of the subdelDTO property.
     * 
     * @return
     *     possible object is
     *     {@link SubdelegacionDTO }
     *     
     */
    public SubdelegacionDTO getSubdelDTO() {
        return subdelDTO;
    }

    /**
     * Sets the value of the subdelDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubdelegacionDTO }
     *     
     */
    public void setSubdelDTO(SubdelegacionDTO value) {
        this.subdelDTO = value;
    }

    /**
     * Gets the value of the umfDTO property.
     * 
     * @return
     *     possible object is
     *     {@link UmfDTO }
     *     
     */
    public UmfDTO getUmfDTO() {
        return umfDTO;
    }

    /**
     * Sets the value of the umfDTO property.
     * 
     * @param value
     *     allowed object is
     *     {@link UmfDTO }
     *     
     */
    public void setUmfDTO(UmfDTO value) {
        this.umfDTO = value;
    }

}
