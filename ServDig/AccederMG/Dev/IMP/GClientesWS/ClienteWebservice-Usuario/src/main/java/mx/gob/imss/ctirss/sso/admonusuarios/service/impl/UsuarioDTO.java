
package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for usuarioDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="usuarioDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="activo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="apellidoMaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoPaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claveAreaNormativa" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="claveDelegacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="claveDepartamento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="claveModulo" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="clavePuesto" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="claveSubDelegacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="claveUMF" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="confirmarPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="correoElectronico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="curp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveDelegacionNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveEstatusNom" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="cveSubdelegacionNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveUmfNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departamentoDescNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionCargo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionDelegacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionDepartamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionEstatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionPuesto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionSubDelegacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idBdtu" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idSolicitud" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="matricula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modulos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modulosData" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}moduloDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="newPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nss" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nssNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numModulos" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numPerfiles" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="perfiles" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puestoDescNom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rolesData" type="{http://impl.service.admonusuarios.sso.ctirss.imss.gob.mx/}puestoDTO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="serial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "usuarioDTO", propOrder = {
    "activo",
    "apellidoMaterno",
    "apellidoPaterno",
    "claveAreaNormativa",
    "claveDelegacion",
    "claveDepartamento",
    "claveModulo",
    "clavePuesto",
    "claveSubDelegacion",
    "claveUMF",
    "confirmarPassword",
    "correoElectronico",
    "curp",
    "cveDelegacionNom",
    "cveEstatusNom",
    "cveSubdelegacionNom",
    "cveUmfNom",
    "departamentoDescNom",
    "descripcionArea",
    "descripcionCargo",
    "descripcionDelegacion",
    "descripcionDepartamento",
    "descripcionEstatus",
    "descripcionPuesto",
    "descripcionSubDelegacion",
    "idBdtu",
    "idSolicitud",
    "matricula",
    "modulos",
    "modulosData",
    "newPassword",
    "nombres",
    "nss",
    "nssNom",
    "numModulos",
    "numPerfiles",
    "password",
    "perfiles",
    "puestoDescNom",
    "rolesData",
    "serial",
    "telefono",
    "uid"
})
public class UsuarioDTO {

    protected boolean activo;
    protected String apellidoMaterno;
    protected String apellidoPaterno;
    protected Integer claveAreaNormativa;
    protected Integer claveDelegacion;
    protected Integer claveDepartamento;
    protected Long claveModulo;
    protected Integer clavePuesto;
    protected Integer claveSubDelegacion;
    protected Integer claveUMF;
    protected String confirmarPassword;
    protected String correoElectronico;
    protected String curp;
    protected String cveDelegacionNom;
    protected long cveEstatusNom;
    protected String cveSubdelegacionNom;
    protected String cveUmfNom;
    protected String departamentoDescNom;
    protected String descripcionArea;
    protected String descripcionCargo;
    protected String descripcionDelegacion;
    protected String descripcionDepartamento;
    protected String descripcionEstatus;
    protected String descripcionPuesto;
    protected String descripcionSubDelegacion;
    protected String idBdtu;
    protected Long idSolicitud;
    protected String matricula;
    protected String modulos;
    @XmlElement(nillable = true)
    protected List<ModuloDTO> modulosData;
    protected String newPassword;
    protected String nombres;
    protected String nss;
    protected String nssNom;
    protected int numModulos;
    protected int numPerfiles;
    protected String password;
    protected String perfiles;
    protected String puestoDescNom;
    @XmlElement(nillable = true)
    protected List<PuestoDTO> rolesData;
    protected String serial;
    protected String telefono;
    protected String uid;

    /**
     * Gets the value of the activo property.
     * 
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Sets the value of the activo property.
     * 
     */
    public void setActivo(boolean value) {
        this.activo = value;
    }

    /**
     * Gets the value of the apellidoMaterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Sets the value of the apellidoMaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoMaterno(String value) {
        this.apellidoMaterno = value;
    }

    /**
     * Gets the value of the apellidoPaterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Sets the value of the apellidoPaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoPaterno(String value) {
        this.apellidoPaterno = value;
    }

    /**
     * Gets the value of the claveAreaNormativa property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClaveAreaNormativa() {
        return claveAreaNormativa;
    }

    /**
     * Sets the value of the claveAreaNormativa property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClaveAreaNormativa(Integer value) {
        this.claveAreaNormativa = value;
    }

    /**
     * Gets the value of the claveDelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClaveDelegacion() {
        return claveDelegacion;
    }

    /**
     * Sets the value of the claveDelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClaveDelegacion(Integer value) {
        this.claveDelegacion = value;
    }

    /**
     * Gets the value of the claveDepartamento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClaveDepartamento() {
        return claveDepartamento;
    }

    /**
     * Sets the value of the claveDepartamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClaveDepartamento(Integer value) {
        this.claveDepartamento = value;
    }

    /**
     * Gets the value of the claveModulo property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getClaveModulo() {
        return claveModulo;
    }

    /**
     * Sets the value of the claveModulo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setClaveModulo(Long value) {
        this.claveModulo = value;
    }

    /**
     * Gets the value of the clavePuesto property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClavePuesto() {
        return clavePuesto;
    }

    /**
     * Sets the value of the clavePuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClavePuesto(Integer value) {
        this.clavePuesto = value;
    }

    /**
     * Gets the value of the claveSubDelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClaveSubDelegacion() {
        return claveSubDelegacion;
    }

    /**
     * Sets the value of the claveSubDelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClaveSubDelegacion(Integer value) {
        this.claveSubDelegacion = value;
    }

    /**
     * Gets the value of the claveUMF property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClaveUMF() {
        return claveUMF;
    }

    /**
     * Sets the value of the claveUMF property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClaveUMF(Integer value) {
        this.claveUMF = value;
    }

    /**
     * Gets the value of the confirmarPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmarPassword() {
        return confirmarPassword;
    }

    /**
     * Sets the value of the confirmarPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmarPassword(String value) {
        this.confirmarPassword = value;
    }

    /**
     * Gets the value of the correoElectronico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Sets the value of the correoElectronico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorreoElectronico(String value) {
        this.correoElectronico = value;
    }

    /**
     * Gets the value of the curp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurp() {
        return curp;
    }

    /**
     * Sets the value of the curp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurp(String value) {
        this.curp = value;
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
     * Gets the value of the descripcionArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionArea() {
        return descripcionArea;
    }

    /**
     * Sets the value of the descripcionArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionArea(String value) {
        this.descripcionArea = value;
    }

    /**
     * Gets the value of the descripcionCargo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    /**
     * Sets the value of the descripcionCargo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionCargo(String value) {
        this.descripcionCargo = value;
    }

    /**
     * Gets the value of the descripcionDelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionDelegacion() {
        return descripcionDelegacion;
    }

    /**
     * Sets the value of the descripcionDelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionDelegacion(String value) {
        this.descripcionDelegacion = value;
    }

    /**
     * Gets the value of the descripcionDepartamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionDepartamento() {
        return descripcionDepartamento;
    }

    /**
     * Sets the value of the descripcionDepartamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionDepartamento(String value) {
        this.descripcionDepartamento = value;
    }

    /**
     * Gets the value of the descripcionEstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionEstatus() {
        return descripcionEstatus;
    }

    /**
     * Sets the value of the descripcionEstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionEstatus(String value) {
        this.descripcionEstatus = value;
    }

    /**
     * Gets the value of the descripcionPuesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionPuesto() {
        return descripcionPuesto;
    }

    /**
     * Sets the value of the descripcionPuesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionPuesto(String value) {
        this.descripcionPuesto = value;
    }

    /**
     * Gets the value of the descripcionSubDelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionSubDelegacion() {
        return descripcionSubDelegacion;
    }

    /**
     * Sets the value of the descripcionSubDelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionSubDelegacion(String value) {
        this.descripcionSubDelegacion = value;
    }

    /**
     * Gets the value of the idBdtu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdBdtu() {
        return idBdtu;
    }

    /**
     * Sets the value of the idBdtu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdBdtu(String value) {
        this.idBdtu = value;
    }

    /**
     * Gets the value of the idSolicitud property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Sets the value of the idSolicitud property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdSolicitud(Long value) {
        this.idSolicitud = value;
    }

    /**
     * Gets the value of the matricula property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Sets the value of the matricula property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatricula(String value) {
        this.matricula = value;
    }

    /**
     * Gets the value of the modulos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModulos() {
        return modulos;
    }

    /**
     * Sets the value of the modulos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModulos(String value) {
        this.modulos = value;
    }

    /**
     * Gets the value of the modulosData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modulosData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModulosData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModuloDTO }
     * 
     * 
     */
    public List<ModuloDTO> getModulosData() {
        if (modulosData == null) {
            modulosData = new ArrayList<ModuloDTO>();
        }
        return this.modulosData;
    }

    /**
     * Gets the value of the newPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the value of the newPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewPassword(String value) {
        this.newPassword = value;
    }

    /**
     * Gets the value of the nombres property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Sets the value of the nombres property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombres(String value) {
        this.nombres = value;
    }

    /**
     * Gets the value of the nss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNss() {
        return nss;
    }

    /**
     * Sets the value of the nss property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNss(String value) {
        this.nss = value;
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
     * Gets the value of the numModulos property.
     * 
     */
    public int getNumModulos() {
        return numModulos;
    }

    /**
     * Sets the value of the numModulos property.
     * 
     */
    public void setNumModulos(int value) {
        this.numModulos = value;
    }

    /**
     * Gets the value of the numPerfiles property.
     * 
     */
    public int getNumPerfiles() {
        return numPerfiles;
    }

    /**
     * Sets the value of the numPerfiles property.
     * 
     */
    public void setNumPerfiles(int value) {
        this.numPerfiles = value;
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
     * Gets the value of the perfiles property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerfiles() {
        return perfiles;
    }

    /**
     * Sets the value of the perfiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerfiles(String value) {
        this.perfiles = value;
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
     * Gets the value of the rolesData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rolesData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRolesData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PuestoDTO }
     * 
     * 
     */
    public List<PuestoDTO> getRolesData() {
        if (rolesData == null) {
            rolesData = new ArrayList<PuestoDTO>();
        }
        return this.rolesData;
    }

    /**
     * Gets the value of the serial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Sets the value of the serial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerial(String value) {
        this.serial = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the uid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets the value of the uid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUid(String value) {
        this.uid = value;
    }

	public void setRolesData(List<PuestoDTO> rolesData) {
		this.rolesData = rolesData;
	}

    
}
