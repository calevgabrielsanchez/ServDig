
package gob.imss.webservice.sat.rfc.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Identificacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Identificacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Ap_Materno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Ap_Paterno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CURP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NIT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Nacionalidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Nom_Comercial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Nombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Pais_Origen" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Razon_Soc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="c_Sit_Cont" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="c_Sit_Cont_Dom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="c_Sit_Dom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="c_det_Sit_Cont" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="d_Sit_Cont" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="d_Sit_Cont_Dom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="d_Sit_Dom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Constitucion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Ini_Opers" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Nacimiento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="f_Sit_Cont" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="t_Sociedad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Identificacion", propOrder = {
    "apMaterno",
    "apPaterno",
    "curp",
    "nit",
    "nacionalidad",
    "nomComercial",
    "nombre",
    "paisOrigen",
    "razonSoc",
    "cSitCont",
    "cSitContDom",
    "cSitDom",
    "cDetSitCont",
    "dSitCont",
    "dSitContDom",
    "dSitDom",
    "fConstitucion",
    "fIniOpers",
    "fNacimiento",
    "fSitCont",
    "tSociedad"
})
public class Identificacion {

    @XmlElement(name = "Ap_Materno", required = true, nillable = true)
    protected String apMaterno;
    @XmlElement(name = "Ap_Paterno", required = true, nillable = true)
    protected String apPaterno;
    @XmlElement(name = "CURP", required = true, nillable = true)
    protected String curp;
    @XmlElement(name = "NIT", required = true, nillable = true)
    protected String nit;
    @XmlElement(name = "Nacionalidad", required = true, nillable = true)
    protected String nacionalidad;
    @XmlElement(name = "Nom_Comercial", required = true, nillable = true)
    protected String nomComercial;
    @XmlElement(name = "Nombre", required = true, nillable = true)
    protected String nombre;
    @XmlElement(name = "Pais_Origen", required = true, nillable = true)
    protected String paisOrigen;
    @XmlElement(name = "Razon_Soc", required = true, nillable = true)
    protected String razonSoc;
    @XmlElement(name = "c_Sit_Cont", required = true, nillable = true)
    protected String cSitCont;
    @XmlElement(name = "c_Sit_Cont_Dom", required = true, nillable = true)
    protected String cSitContDom;
    @XmlElement(name = "c_Sit_Dom", required = true, nillable = true)
    protected String cSitDom;
    @XmlElement(name = "c_det_Sit_Cont", required = true, nillable = true)
    protected String cDetSitCont;
    @XmlElement(name = "d_Sit_Cont", required = true, nillable = true)
    protected String dSitCont;
    @XmlElement(name = "d_Sit_Cont_Dom", required = true, nillable = true)
    protected String dSitContDom;
    @XmlElement(name = "d_Sit_Dom", required = true, nillable = true)
    protected String dSitDom;
    @XmlElement(name = "f_Constitucion", required = true, nillable = true)
    protected String fConstitucion;
    @XmlElement(name = "f_Ini_Opers", required = true, nillable = true)
    protected String fIniOpers;
    @XmlElement(name = "f_Nacimiento", required = true, nillable = true)
    protected String fNacimiento;
    @XmlElement(name = "f_Sit_Cont", required = true, nillable = true)
    protected String fSitCont;
    @XmlElement(name = "t_Sociedad", required = true, nillable = true)
    protected String tSociedad;

    /**
     * Gets the value of the apMaterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApMaterno() {
        return apMaterno;
    }

    /**
     * Sets the value of the apMaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApMaterno(String value) {
        this.apMaterno = value;
    }

    /**
     * Gets the value of the apPaterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApPaterno() {
        return apPaterno;
    }

    /**
     * Sets the value of the apPaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApPaterno(String value) {
        this.apPaterno = value;
    }

    /**
     * Gets the value of the curp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCURP() {
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
    public void setCURP(String value) {
        this.curp = value;
    }

    /**
     * Gets the value of the nit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNIT() {
        return nit;
    }

    /**
     * Sets the value of the nit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNIT(String value) {
        this.nit = value;
    }

    /**
     * Gets the value of the nacionalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Sets the value of the nacionalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNacionalidad(String value) {
        this.nacionalidad = value;
    }

    /**
     * Gets the value of the nomComercial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomComercial() {
        return nomComercial;
    }

    /**
     * Sets the value of the nomComercial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomComercial(String value) {
        this.nomComercial = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Gets the value of the paisOrigen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaisOrigen() {
        return paisOrigen;
    }

    /**
     * Sets the value of the paisOrigen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaisOrigen(String value) {
        this.paisOrigen = value;
    }

    /**
     * Gets the value of the razonSoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazonSoc() {
        return razonSoc;
    }

    /**
     * Sets the value of the razonSoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazonSoc(String value) {
        this.razonSoc = value;
    }

    /**
     * Gets the value of the cSitCont property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCSitCont() {
        return cSitCont;
    }

    /**
     * Sets the value of the cSitCont property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCSitCont(String value) {
        this.cSitCont = value;
    }

    /**
     * Gets the value of the cSitContDom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCSitContDom() {
        return cSitContDom;
    }

    /**
     * Sets the value of the cSitContDom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCSitContDom(String value) {
        this.cSitContDom = value;
    }

    /**
     * Gets the value of the cSitDom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCSitDom() {
        return cSitDom;
    }

    /**
     * Sets the value of the cSitDom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCSitDom(String value) {
        this.cSitDom = value;
    }

    /**
     * Gets the value of the cDetSitCont property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCDetSitCont() {
        return cDetSitCont;
    }

    /**
     * Sets the value of the cDetSitCont property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCDetSitCont(String value) {
        this.cDetSitCont = value;
    }

    /**
     * Gets the value of the dSitCont property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSitCont() {
        return dSitCont;
    }

    /**
     * Sets the value of the dSitCont property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSitCont(String value) {
        this.dSitCont = value;
    }

    /**
     * Gets the value of the dSitContDom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSitContDom() {
        return dSitContDom;
    }

    /**
     * Sets the value of the dSitContDom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSitContDom(String value) {
        this.dSitContDom = value;
    }

    /**
     * Gets the value of the dSitDom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDSitDom() {
        return dSitDom;
    }

    /**
     * Sets the value of the dSitDom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDSitDom(String value) {
        this.dSitDom = value;
    }

    /**
     * Gets the value of the fConstitucion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFConstitucion() {
        return fConstitucion;
    }

    /**
     * Sets the value of the fConstitucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFConstitucion(String value) {
        this.fConstitucion = value;
    }

    /**
     * Gets the value of the fIniOpers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIniOpers() {
        return fIniOpers;
    }

    /**
     * Sets the value of the fIniOpers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIniOpers(String value) {
        this.fIniOpers = value;
    }

    /**
     * Gets the value of the fNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFNacimiento() {
        return fNacimiento;
    }

    /**
     * Sets the value of the fNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFNacimiento(String value) {
        this.fNacimiento = value;
    }

    /**
     * Gets the value of the fSitCont property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFSitCont() {
        return fSitCont;
    }

    /**
     * Sets the value of the fSitCont property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFSitCont(String value) {
        this.fSitCont = value;
    }

    /**
     * Gets the value of the tSociedad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTSociedad() {
        return tSociedad;
    }

    /**
     * Sets the value of the tSociedad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTSociedad(String value) {
        this.tSociedad = value;
    }

}
