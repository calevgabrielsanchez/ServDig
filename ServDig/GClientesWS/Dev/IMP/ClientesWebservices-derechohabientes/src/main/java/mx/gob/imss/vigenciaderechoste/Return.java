
package mx.gob.imss.vigenciaderechoste;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for return complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="return">
 *   &lt;complexContent>
 *     &lt;extension base="{http://vigenciaderechos.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="AgregadoAfiliacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AgregadoMedico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Beneficiarios" type="{http://vigenciaderechos.imss.gob.mx/}InfoAseguradoVO" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ClavePresupuestal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Colonia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConDerechoInc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConDerechoSm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Consultorio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Cpid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Curp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DhDeleg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DhIpServer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DhUMF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Idee" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Materno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nss" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Paterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegistroPatronal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sexo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoPension" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Turno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VigenteHasta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdPersona" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Articulo82" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Articulo83" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Articulo84" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Articulo85" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TiemposEspera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "return", propOrder = {
    "agregadoAfiliacion",
    "agregadoMedico",
    "beneficiarios",
    "clavePresupuestal",
    "colonia",
    "conDerechoInc",
    "conDerechoSm",
    "consultorio",
    "cpid",
    "curp",
    "dhDeleg",
    "dhIpServer",
    "dhUMF",
    "direccion",
    "fechaNacimiento",
    "idee",
    "materno",
    "nombre",
    "nss",
    "paterno",
    "registroPatronal",
    "sexo",
    "telefono",
    "tipoPension",
    "turno",
    "vigenteHasta",
    "idPersona",
    "articulo82",
    "articulo83",
    "articulo84",
    "articulo85",
    "tiemposEspera"
})
public class Return
    extends RespuestaWS
{

    @XmlElementRef(name = "AgregadoAfiliacion", type = JAXBElement.class, required = false)
    protected JAXBElement<String> agregadoAfiliacion;
    @XmlElementRef(name = "AgregadoMedico", type = JAXBElement.class, required = false)
    protected JAXBElement<String> agregadoMedico;
    @XmlElement(name = "Beneficiarios", nillable = true)
    protected List<InfoAseguradoVO> beneficiarios;
    @XmlElementRef(name = "ClavePresupuestal", type = JAXBElement.class, required = false)
    protected JAXBElement<String> clavePresupuestal;
    @XmlElementRef(name = "Colonia", type = JAXBElement.class, required = false)
    protected JAXBElement<String> colonia;
    @XmlElementRef(name = "ConDerechoInc", type = JAXBElement.class, required = false)
    protected JAXBElement<String> conDerechoInc;
    @XmlElementRef(name = "ConDerechoSm", type = JAXBElement.class, required = false)
    protected JAXBElement<String> conDerechoSm;
    @XmlElementRef(name = "Consultorio", type = JAXBElement.class, required = false)
    protected JAXBElement<String> consultorio;
    @XmlElementRef(name = "Cpid", type = JAXBElement.class, required = false)
    protected JAXBElement<String> cpid;
    @XmlElementRef(name = "Curp", type = JAXBElement.class, required = false)
    protected JAXBElement<String> curp;
    @XmlElementRef(name = "DhDeleg", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dhDeleg;
    @XmlElementRef(name = "DhIpServer", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dhIpServer;
    @XmlElementRef(name = "DhUMF", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dhUMF;
    @XmlElementRef(name = "Direccion", type = JAXBElement.class, required = false)
    protected JAXBElement<String> direccion;
    @XmlElementRef(name = "FechaNacimiento", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fechaNacimiento;
    @XmlElementRef(name = "Idee", type = JAXBElement.class, required = false)
    protected JAXBElement<String> idee;
    @XmlElementRef(name = "Materno", type = JAXBElement.class, required = false)
    protected JAXBElement<String> materno;
    @XmlElementRef(name = "Nombre", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nombre;
    @XmlElementRef(name = "Nss", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nss;
    @XmlElementRef(name = "Paterno", type = JAXBElement.class, required = false)
    protected JAXBElement<String> paterno;
    @XmlElementRef(name = "RegistroPatronal", type = JAXBElement.class, required = false)
    protected JAXBElement<String> registroPatronal;
    @XmlElementRef(name = "Sexo", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sexo;
    @XmlElementRef(name = "Telefono", type = JAXBElement.class, required = false)
    protected JAXBElement<String> telefono;
    @XmlElementRef(name = "TipoPension", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tipoPension;
    @XmlElementRef(name = "Turno", type = JAXBElement.class, required = false)
    protected JAXBElement<String> turno;
    @XmlElementRef(name = "VigenteHasta", type = JAXBElement.class, required = false)
    protected JAXBElement<String> vigenteHasta;
    @XmlElementRef(name = "IdPersona", type = JAXBElement.class, required = false)
    protected JAXBElement<Integer> idPersona;
    @XmlElementRef(name = "Articulo82", type = JAXBElement.class, required = false)
    protected JAXBElement<String> articulo82;
    @XmlElementRef(name = "Articulo83", type = JAXBElement.class, required = false)
    protected JAXBElement<String> articulo83;
    @XmlElementRef(name = "Articulo84", type = JAXBElement.class, required = false)
    protected JAXBElement<String> articulo84;
    @XmlElementRef(name = "Articulo85", type = JAXBElement.class, required = false)
    protected JAXBElement<String> articulo85;
    @XmlElementRef(name = "TiemposEspera", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tiemposEspera;

    /**
     * Gets the value of the agregadoAfiliacion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAgregadoAfiliacion() {
        return agregadoAfiliacion;
    }

    /**
     * Sets the value of the agregadoAfiliacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAgregadoAfiliacion(JAXBElement<String> value) {
        this.agregadoAfiliacion = value;
    }

    /**
     * Gets the value of the agregadoMedico property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAgregadoMedico() {
        return agregadoMedico;
    }

    /**
     * Sets the value of the agregadoMedico property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAgregadoMedico(JAXBElement<String> value) {
        this.agregadoMedico = value;
    }

    /**
     * Gets the value of the beneficiarios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the beneficiarios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBeneficiarios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfoAseguradoVO }
     * 
     * 
     */
    public List<InfoAseguradoVO> getBeneficiarios() {
        if (beneficiarios == null) {
            beneficiarios = new ArrayList<InfoAseguradoVO>();
        }
        return this.beneficiarios;
    }

    /**
     * Gets the value of the clavePresupuestal property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getClavePresupuestal() {
        return clavePresupuestal;
    }

    /**
     * Sets the value of the clavePresupuestal property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setClavePresupuestal(JAXBElement<String> value) {
        this.clavePresupuestal = value;
    }

    /**
     * Gets the value of the colonia property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getColonia() {
        return colonia;
    }

    /**
     * Sets the value of the colonia property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setColonia(JAXBElement<String> value) {
        this.colonia = value;
    }

    /**
     * Gets the value of the conDerechoInc property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getConDerechoInc() {
        return conDerechoInc;
    }

    /**
     * Sets the value of the conDerechoInc property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setConDerechoInc(JAXBElement<String> value) {
        this.conDerechoInc = value;
    }

    /**
     * Gets the value of the conDerechoSm property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getConDerechoSm() {
        return conDerechoSm;
    }

    /**
     * Sets the value of the conDerechoSm property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setConDerechoSm(JAXBElement<String> value) {
        this.conDerechoSm = value;
    }

    /**
     * Gets the value of the consultorio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getConsultorio() {
        return consultorio;
    }

    /**
     * Sets the value of the consultorio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setConsultorio(JAXBElement<String> value) {
        this.consultorio = value;
    }

    /**
     * Gets the value of the cpid property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCpid() {
        return cpid;
    }

    /**
     * Sets the value of the cpid property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCpid(JAXBElement<String> value) {
        this.cpid = value;
    }

    /**
     * Gets the value of the curp property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCurp() {
        return curp;
    }

    /**
     * Sets the value of the curp property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCurp(JAXBElement<String> value) {
        this.curp = value;
    }

    /**
     * Gets the value of the dhDeleg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDhDeleg() {
        return dhDeleg;
    }

    /**
     * Sets the value of the dhDeleg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDhDeleg(JAXBElement<String> value) {
        this.dhDeleg = value;
    }

    /**
     * Gets the value of the dhIpServer property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDhIpServer() {
        return dhIpServer;
    }

    /**
     * Sets the value of the dhIpServer property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDhIpServer(JAXBElement<String> value) {
        this.dhIpServer = value;
    }

    /**
     * Gets the value of the dhUMF property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDhUMF() {
        return dhUMF;
    }

    /**
     * Sets the value of the dhUMF property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDhUMF(JAXBElement<String> value) {
        this.dhUMF = value;
    }

    /**
     * Gets the value of the direccion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDireccion() {
        return direccion;
    }

    /**
     * Sets the value of the direccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDireccion(JAXBElement<String> value) {
        this.direccion = value;
    }

    /**
     * Gets the value of the fechaNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the value of the fechaNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFechaNacimiento(JAXBElement<String> value) {
        this.fechaNacimiento = value;
    }

    /**
     * Gets the value of the idee property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdee() {
        return idee;
    }

    /**
     * Sets the value of the idee property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdee(JAXBElement<String> value) {
        this.idee = value;
    }

    /**
     * Gets the value of the materno property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMaterno() {
        return materno;
    }

    /**
     * Sets the value of the materno property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMaterno(JAXBElement<String> value) {
        this.materno = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNombre(JAXBElement<String> value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the nss property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNss() {
        return nss;
    }

    /**
     * Sets the value of the nss property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNss(JAXBElement<String> value) {
        this.nss = value;
    }

    /**
     * Gets the value of the paterno property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPaterno() {
        return paterno;
    }

    /**
     * Sets the value of the paterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPaterno(JAXBElement<String> value) {
        this.paterno = value;
    }

    /**
     * Gets the value of the registroPatronal property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRegistroPatronal() {
        return registroPatronal;
    }

    /**
     * Sets the value of the registroPatronal property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRegistroPatronal(JAXBElement<String> value) {
        this.registroPatronal = value;
    }

    /**
     * Gets the value of the sexo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSexo() {
        return sexo;
    }

    /**
     * Sets the value of the sexo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSexo(JAXBElement<String> value) {
        this.sexo = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTelefono(JAXBElement<String> value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the tipoPension property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoPension() {
        return tipoPension;
    }

    /**
     * Sets the value of the tipoPension property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoPension(JAXBElement<String> value) {
        this.tipoPension = value;
    }

    /**
     * Gets the value of the turno property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTurno() {
        return turno;
    }

    /**
     * Sets the value of the turno property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTurno(JAXBElement<String> value) {
        this.turno = value;
    }

    /**
     * Gets the value of the vigenteHasta property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVigenteHasta() {
        return vigenteHasta;
    }

    /**
     * Sets the value of the vigenteHasta property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVigenteHasta(JAXBElement<String> value) {
        this.vigenteHasta = value;
    }

    /**
     * Gets the value of the idPersona property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getIdPersona() {
        return idPersona;
    }

    /**
     * Sets the value of the idPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setIdPersona(JAXBElement<Integer> value) {
        this.idPersona = value;
    }

    /**
     * Gets the value of the articulo82 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getArticulo82() {
        return articulo82;
    }

    /**
     * Sets the value of the articulo82 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setArticulo82(JAXBElement<String> value) {
        this.articulo82 = value;
    }

    /**
     * Gets the value of the articulo83 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getArticulo83() {
        return articulo83;
    }

    /**
     * Sets the value of the articulo83 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setArticulo83(JAXBElement<String> value) {
        this.articulo83 = value;
    }

    /**
     * Gets the value of the articulo84 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getArticulo84() {
        return articulo84;
    }

    /**
     * Sets the value of the articulo84 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setArticulo84(JAXBElement<String> value) {
        this.articulo84 = value;
    }

    /**
     * Gets the value of the articulo85 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getArticulo85() {
        return articulo85;
    }

    /**
     * Sets the value of the articulo85 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setArticulo85(JAXBElement<String> value) {
        this.articulo85 = value;
    }

    /**
     * Gets the value of the tiemposEspera property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTiemposEspera() {
        return tiemposEspera;
    }

    /**
     * Sets the value of the tiemposEspera property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTiemposEspera(JAXBElement<String> value) {
        this.tiemposEspera = value;
    }

}
