
package mx.gob.imss.cit.clienteswebservices.vigencia.grupoFamilarNss;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoAseguradoVO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoAseguradoVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AgregadoAfiliacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AgregadoMedico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoAseguradoVO", propOrder = {
    "agregadoAfiliacion",
    "agregadoMedico",
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
    "idPersona"
})
public class InfoAseguradoVO {

    @XmlElementRef(name = "AgregadoAfiliacion", type = JAXBElement.class)
    protected JAXBElement<String> agregadoAfiliacion;
    @XmlElementRef(name = "AgregadoMedico", type = JAXBElement.class)
    protected JAXBElement<String> agregadoMedico;
    @XmlElementRef(name = "ClavePresupuestal", type = JAXBElement.class)
    protected JAXBElement<String> clavePresupuestal;
    @XmlElementRef(name = "Colonia", type = JAXBElement.class)
    protected JAXBElement<String> colonia;
    @XmlElementRef(name = "ConDerechoInc", type = JAXBElement.class)
    protected JAXBElement<String> conDerechoInc;
    @XmlElementRef(name = "ConDerechoSm", type = JAXBElement.class)
    protected JAXBElement<String> conDerechoSm;
    @XmlElementRef(name = "Consultorio", type = JAXBElement.class)
    protected JAXBElement<String> consultorio;
    @XmlElementRef(name = "Cpid", type = JAXBElement.class)
    protected JAXBElement<String> cpid;
    @XmlElementRef(name = "Curp", type = JAXBElement.class)
    protected JAXBElement<String> curp;
    @XmlElementRef(name = "DhDeleg", type = JAXBElement.class)
    protected JAXBElement<String> dhDeleg;
    @XmlElementRef(name = "DhIpServer", type = JAXBElement.class)
    protected JAXBElement<String> dhIpServer;
    @XmlElementRef(name = "DhUMF", type = JAXBElement.class)
    protected JAXBElement<String> dhUMF;
    @XmlElementRef(name = "Direccion", type = JAXBElement.class)
    protected JAXBElement<String> direccion;
    @XmlElementRef(name = "FechaNacimiento", type = JAXBElement.class)
    protected JAXBElement<String> fechaNacimiento;
    @XmlElementRef(name = "Idee", type = JAXBElement.class)
    protected JAXBElement<String> idee;
    @XmlElementRef(name = "Materno", type = JAXBElement.class)
    protected JAXBElement<String> materno;
    @XmlElementRef(name = "Nombre", type = JAXBElement.class)
    protected JAXBElement<String> nombre;
    @XmlElementRef(name = "Nss", type = JAXBElement.class)
    protected JAXBElement<String> nss;
    @XmlElementRef(name = "Paterno", type = JAXBElement.class)
    protected JAXBElement<String> paterno;
    @XmlElementRef(name = "RegistroPatronal", type = JAXBElement.class)
    protected JAXBElement<String> registroPatronal;
    @XmlElementRef(name = "Sexo", type = JAXBElement.class)
    protected JAXBElement<String> sexo;
    @XmlElementRef(name = "Telefono", type = JAXBElement.class)
    protected JAXBElement<String> telefono;
    @XmlElementRef(name = "TipoPension", type = JAXBElement.class)
    protected JAXBElement<String> tipoPension;
    @XmlElementRef(name = "Turno", type = JAXBElement.class)
    protected JAXBElement<String> turno;
    @XmlElementRef(name = "VigenteHasta", type = JAXBElement.class)
    protected JAXBElement<String> vigenteHasta;
    @XmlElementRef(name = "IdPersona", type = JAXBElement.class)
    protected JAXBElement<Integer> idPersona;

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
        this.agregadoAfiliacion = ((JAXBElement<String> ) value);
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
        this.agregadoMedico = ((JAXBElement<String> ) value);
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
        this.clavePresupuestal = ((JAXBElement<String> ) value);
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
        this.colonia = ((JAXBElement<String> ) value);
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
        this.conDerechoInc = ((JAXBElement<String> ) value);
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
        this.conDerechoSm = ((JAXBElement<String> ) value);
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
        this.consultorio = ((JAXBElement<String> ) value);
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
        this.cpid = ((JAXBElement<String> ) value);
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
        this.curp = ((JAXBElement<String> ) value);
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
        this.dhDeleg = ((JAXBElement<String> ) value);
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
        this.dhIpServer = ((JAXBElement<String> ) value);
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
        this.dhUMF = ((JAXBElement<String> ) value);
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
        this.direccion = ((JAXBElement<String> ) value);
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
        this.fechaNacimiento = ((JAXBElement<String> ) value);
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
        this.idee = ((JAXBElement<String> ) value);
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
        this.materno = ((JAXBElement<String> ) value);
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
        this.nombre = ((JAXBElement<String> ) value);
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
        this.nss = ((JAXBElement<String> ) value);
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
        this.paterno = ((JAXBElement<String> ) value);
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
        this.registroPatronal = ((JAXBElement<String> ) value);
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
        this.sexo = ((JAXBElement<String> ) value);
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
        this.telefono = ((JAXBElement<String> ) value);
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
        this.tipoPension = ((JAXBElement<String> ) value);
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
        this.turno = ((JAXBElement<String> ) value);
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
        this.vigenteHasta = ((JAXBElement<String> ) value);
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
        this.idPersona = ((JAXBElement<Integer> ) value);
    }

}
