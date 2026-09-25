
package mx.gob.imss.ctirss.ws.asignacion.implementacion;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for asignacionNSSBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="asignacionNSSBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="anio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="anio2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="anioIngreso" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="anioRegistro" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="antecedentes" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="apellidoMaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoMaternoMadre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoMaternoPadre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoPaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoPaternoMadre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoPaternoPadre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CURP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="captcha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chkSinCurp" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="claveOcupacion" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="codigoPostal" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="correoElectronico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="crip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="curp_acta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveColonia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveLocalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveLocalidadDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveMunicipDeleg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveMunicipDelegDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcionOcupacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dia" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dia2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="diaIngreso" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="diaRegistro" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="direccionCasa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direccionOtra" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direccionTrabajo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direcciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domCalle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domCorreoElec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domEntreCalle1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="domEntreCalle2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entidadFederativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="entidadFederativaDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="folio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hdnCveColonia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hdnCveLocalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IUMF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idPatron" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="idTransaccion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="jornadaSemana" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="lugarDesRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lugarDesRegistroMun" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lugarNacimiento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lugarNacimientoDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lugarRegistrMun" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lugarRegistro" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="matriz" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="mes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mes2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mesDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mesDesRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mesIngreso" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mesRegistro" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreMadre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombrePadre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numActa" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numExtTel1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numExtTel2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numLibro" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numTelFijo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numTelFijo2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numTomo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numfoja" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="preafiliacionReingreso" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="refCodigoPostal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refNoExt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="refNoInt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SFolio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="salarioBase" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="seguridadSocial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serie" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sexo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sexoDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sinCurp" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="strDelegacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strSubDelegacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="terminoOk" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tipoDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoDomicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoOperacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoSalario" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="tipoTrabajador" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="tipoTramite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UMF" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="UMFDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="verificadorNSS" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "asignacionNSSBean", propOrder = {
    "anio",
    "anio2",
    "anioIngreso",
    "anioRegistro",
    "antecedentes",
    "apellidoMaterno",
    "apellidoMaternoMadre",
    "apellidoMaternoPadre",
    "apellidoPaterno",
    "apellidoPaternoMadre",
    "apellidoPaternoPadre",
    "curp",
    "captcha",
    "chkSinCurp",
    "claveOcupacion",
    "codigoPostal",
    "correoElectronico",
    "crip",
    "curpActa",
    "cveColonia",
    "cveLocalidad",
    "cveLocalidadDes",
    "cveMunicipDeleg",
    "cveMunicipDelegDes",
    "descripcionOcupacion",
    "dia",
    "dia2",
    "diaIngreso",
    "diaRegistro",
    "direccionCasa",
    "direccionOtra",
    "direccionTrabajo",
    "direcciones",
    "domCalle",
    "domCorreoElec",
    "domEntreCalle1",
    "domEntreCalle2",
    "entidadFederativa",
    "entidadFederativaDes",
    "fecha",
    "folio",
    "hdnCveColonia",
    "hdnCveLocalidad",
    "iumf",
    "idPatron",
    "idTransaccion",
    "jornadaSemana",
    "lugarDesRegistro",
    "lugarDesRegistroMun",
    "lugarNacimiento",
    "lugarNacimientoDes",
    "lugarRegistrMun",
    "lugarRegistro",
    "matriz",
    "mes",
    "mes2",
    "mesDes",
    "mesDesRegistro",
    "mesIngreso",
    "mesRegistro",
    "nombre",
    "nombreMadre",
    "nombrePadre",
    "numActa",
    "numExtTel1",
    "numExtTel2",
    "numLibro",
    "numTelFijo1",
    "numTelFijo2",
    "numTomo",
    "numfoja",
    "preafiliacionReingreso",
    "refCodigoPostal",
    "refNoExt",
    "refNoInt",
    "sFolio",
    "salarioBase",
    "seguridadSocial",
    "serie",
    "sexo",
    "sexoDes",
    "sinCurp",
    "strDelegacion",
    "strSubDelegacion",
    "terminoOk",
    "tipoDomicilio",
    "tipoDomicio",
    "tipoOperacion",
    "tipoSalario",
    "tipoTrabajador",
    "tipoTramite",
    "umf",
    "umfDes",
    "verificadorNSS"
})
public class AsignacionNSSBean {

    protected int anio;
    protected int anio2;
    protected int anioIngreso;
    protected int anioRegistro;
    protected short antecedentes;
    protected String apellidoMaterno;
    protected String apellidoMaternoMadre;
    protected String apellidoMaternoPadre;
    protected String apellidoPaterno;
    protected String apellidoPaternoMadre;
    protected String apellidoPaternoPadre;
    @XmlElement(name = "CURP")
    protected String curp;
    protected String captcha;
    protected boolean chkSinCurp;
    @XmlSchemaType(name = "unsignedShort")
    protected int claveOcupacion;
    protected int codigoPostal;
    protected String correoElectronico;
    protected String crip;
    @XmlElement(name = "curp_acta")
    protected String curpActa;
    protected String cveColonia;
    protected String cveLocalidad;
    protected String cveLocalidadDes;
    protected String cveMunicipDeleg;
    protected String cveMunicipDelegDes;
    protected String descripcionOcupacion;
    protected int dia;
    protected int dia2;
    protected int diaIngreso;
    protected int diaRegistro;
    protected String direccionCasa;
    protected String direccionOtra;
    protected String direccionTrabajo;
    protected String direcciones;
    protected String domCalle;
    protected String domCorreoElec;
    protected String domEntreCalle1;
    protected String domEntreCalle2;
    protected String entidadFederativa;
    protected String entidadFederativaDes;
    protected String fecha;
    protected int folio;
    protected String hdnCveColonia;
    protected String hdnCveLocalidad;
    @XmlElement(name = "IUMF")
    protected String iumf;
    protected long idPatron;
    protected int idTransaccion;
    @XmlSchemaType(name = "unsignedShort")
    protected int jornadaSemana;
    protected String lugarDesRegistro;
    protected String lugarDesRegistroMun;
    protected int lugarNacimiento;
    protected String lugarNacimientoDes;
    protected int lugarRegistrMun;
    protected int lugarRegistro;
    @XmlElement(nillable = true)
    protected List<Object> matriz;
    protected int mes;
    protected int mes2;
    protected String mesDes;
    protected String mesDesRegistro;
    protected int mesIngreso;
    protected int mesRegistro;
    protected String nombre;
    protected String nombreMadre;
    protected String nombrePadre;
    protected int numActa;
    protected String numExtTel1;
    protected String numExtTel2;
    protected int numLibro;
    protected String numTelFijo1;
    protected String numTelFijo2;
    protected int numTomo;
    protected int numfoja;
    @XmlSchemaType(name = "unsignedShort")
    protected int preafiliacionReingreso;
    protected String refCodigoPostal;
    protected String refNoExt;
    protected String refNoInt;
    @XmlElement(name = "SFolio")
    protected String sFolio;
    protected double salarioBase;
    protected String seguridadSocial;
    protected String serie;
    protected int sexo;
    protected String sexoDes;
    protected Boolean sinCurp;
    protected String strDelegacion;
    protected String strSubDelegacion;
    protected int terminoOk;
    protected String tipoDomicilio;
    protected String tipoDomicio;
    protected String tipoOperacion;
    @XmlSchemaType(name = "unsignedShort")
    protected int tipoSalario;
    @XmlSchemaType(name = "unsignedShort")
    protected int tipoTrabajador;
    protected int tipoTramite;
    @XmlElement(name = "UMF")
    protected int umf;
    @XmlElement(name = "UMFDes")
    protected String umfDes;
    @XmlSchemaType(name = "unsignedShort")
    protected int verificadorNSS;

    /**
     * Gets the value of the anio property.
     * 
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Sets the value of the anio property.
     * 
     */
    public void setAnio(int value) {
        this.anio = value;
    }

    /**
     * Gets the value of the anio2 property.
     * 
     */
    public int getAnio2() {
        return anio2;
    }

    /**
     * Sets the value of the anio2 property.
     * 
     */
    public void setAnio2(int value) {
        this.anio2 = value;
    }

    /**
     * Gets the value of the anioIngreso property.
     * 
     */
    public int getAnioIngreso() {
        return anioIngreso;
    }

    /**
     * Sets the value of the anioIngreso property.
     * 
     */
    public void setAnioIngreso(int value) {
        this.anioIngreso = value;
    }

    /**
     * Gets the value of the anioRegistro property.
     * 
     */
    public int getAnioRegistro() {
        return anioRegistro;
    }

    /**
     * Sets the value of the anioRegistro property.
     * 
     */
    public void setAnioRegistro(int value) {
        this.anioRegistro = value;
    }

    /**
     * Gets the value of the antecedentes property.
     * 
     */
    public short getAntecedentes() {
        return antecedentes;
    }

    /**
     * Sets the value of the antecedentes property.
     * 
     */
    public void setAntecedentes(short value) {
        this.antecedentes = value;
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
     * Gets the value of the apellidoMaternoMadre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoMaternoMadre() {
        return apellidoMaternoMadre;
    }

    /**
     * Sets the value of the apellidoMaternoMadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoMaternoMadre(String value) {
        this.apellidoMaternoMadre = value;
    }

    /**
     * Gets the value of the apellidoMaternoPadre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoMaternoPadre() {
        return apellidoMaternoPadre;
    }

    /**
     * Sets the value of the apellidoMaternoPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoMaternoPadre(String value) {
        this.apellidoMaternoPadre = value;
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
     * Gets the value of the apellidoPaternoMadre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoPaternoMadre() {
        return apellidoPaternoMadre;
    }

    /**
     * Sets the value of the apellidoPaternoMadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoPaternoMadre(String value) {
        this.apellidoPaternoMadre = value;
    }

    /**
     * Gets the value of the apellidoPaternoPadre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoPaternoPadre() {
        return apellidoPaternoPadre;
    }

    /**
     * Sets the value of the apellidoPaternoPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoPaternoPadre(String value) {
        this.apellidoPaternoPadre = value;
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
     * Gets the value of the captcha property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * Sets the value of the captcha property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaptcha(String value) {
        this.captcha = value;
    }

    /**
     * Gets the value of the chkSinCurp property.
     * 
     */
    public boolean isChkSinCurp() {
        return chkSinCurp;
    }

    /**
     * Sets the value of the chkSinCurp property.
     * 
     */
    public void setChkSinCurp(boolean value) {
        this.chkSinCurp = value;
    }

    /**
     * Gets the value of the claveOcupacion property.
     * 
     */
    public int getClaveOcupacion() {
        return claveOcupacion;
    }

    /**
     * Sets the value of the claveOcupacion property.
     * 
     */
    public void setClaveOcupacion(int value) {
        this.claveOcupacion = value;
    }

    /**
     * Gets the value of the codigoPostal property.
     * 
     */
    public int getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Sets the value of the codigoPostal property.
     * 
     */
    public void setCodigoPostal(int value) {
        this.codigoPostal = value;
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
     * Gets the value of the crip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrip() {
        return crip;
    }

    /**
     * Sets the value of the crip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrip(String value) {
        this.crip = value;
    }

    /**
     * Gets the value of the curpActa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurpActa() {
        return curpActa;
    }

    /**
     * Sets the value of the curpActa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurpActa(String value) {
        this.curpActa = value;
    }

    /**
     * Gets the value of the cveColonia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveColonia() {
        return cveColonia;
    }

    /**
     * Sets the value of the cveColonia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveColonia(String value) {
        this.cveColonia = value;
    }

    /**
     * Gets the value of the cveLocalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveLocalidad() {
        return cveLocalidad;
    }

    /**
     * Sets the value of the cveLocalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveLocalidad(String value) {
        this.cveLocalidad = value;
    }

    /**
     * Gets the value of the cveLocalidadDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveLocalidadDes() {
        return cveLocalidadDes;
    }

    /**
     * Sets the value of the cveLocalidadDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveLocalidadDes(String value) {
        this.cveLocalidadDes = value;
    }

    /**
     * Gets the value of the cveMunicipDeleg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveMunicipDeleg() {
        return cveMunicipDeleg;
    }

    /**
     * Sets the value of the cveMunicipDeleg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveMunicipDeleg(String value) {
        this.cveMunicipDeleg = value;
    }

    /**
     * Gets the value of the cveMunicipDelegDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveMunicipDelegDes() {
        return cveMunicipDelegDes;
    }

    /**
     * Sets the value of the cveMunicipDelegDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveMunicipDelegDes(String value) {
        this.cveMunicipDelegDes = value;
    }

    /**
     * Gets the value of the descripcionOcupacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionOcupacion() {
        return descripcionOcupacion;
    }

    /**
     * Sets the value of the descripcionOcupacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionOcupacion(String value) {
        this.descripcionOcupacion = value;
    }

    /**
     * Gets the value of the dia property.
     * 
     */
    public int getDia() {
        return dia;
    }

    /**
     * Sets the value of the dia property.
     * 
     */
    public void setDia(int value) {
        this.dia = value;
    }

    /**
     * Gets the value of the dia2 property.
     * 
     */
    public int getDia2() {
        return dia2;
    }

    /**
     * Sets the value of the dia2 property.
     * 
     */
    public void setDia2(int value) {
        this.dia2 = value;
    }

    /**
     * Gets the value of the diaIngreso property.
     * 
     */
    public int getDiaIngreso() {
        return diaIngreso;
    }

    /**
     * Sets the value of the diaIngreso property.
     * 
     */
    public void setDiaIngreso(int value) {
        this.diaIngreso = value;
    }

    /**
     * Gets the value of the diaRegistro property.
     * 
     */
    public int getDiaRegistro() {
        return diaRegistro;
    }

    /**
     * Sets the value of the diaRegistro property.
     * 
     */
    public void setDiaRegistro(int value) {
        this.diaRegistro = value;
    }

    /**
     * Gets the value of the direccionCasa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccionCasa() {
        return direccionCasa;
    }

    /**
     * Sets the value of the direccionCasa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccionCasa(String value) {
        this.direccionCasa = value;
    }

    /**
     * Gets the value of the direccionOtra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccionOtra() {
        return direccionOtra;
    }

    /**
     * Sets the value of the direccionOtra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccionOtra(String value) {
        this.direccionOtra = value;
    }

    /**
     * Gets the value of the direccionTrabajo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccionTrabajo() {
        return direccionTrabajo;
    }

    /**
     * Sets the value of the direccionTrabajo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccionTrabajo(String value) {
        this.direccionTrabajo = value;
    }

    /**
     * Gets the value of the direcciones property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirecciones() {
        return direcciones;
    }

    /**
     * Sets the value of the direcciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirecciones(String value) {
        this.direcciones = value;
    }

    /**
     * Gets the value of the domCalle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomCalle() {
        return domCalle;
    }

    /**
     * Sets the value of the domCalle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomCalle(String value) {
        this.domCalle = value;
    }

    /**
     * Gets the value of the domCorreoElec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomCorreoElec() {
        return domCorreoElec;
    }

    /**
     * Sets the value of the domCorreoElec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomCorreoElec(String value) {
        this.domCorreoElec = value;
    }

    /**
     * Gets the value of the domEntreCalle1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomEntreCalle1() {
        return domEntreCalle1;
    }

    /**
     * Sets the value of the domEntreCalle1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomEntreCalle1(String value) {
        this.domEntreCalle1 = value;
    }

    /**
     * Gets the value of the domEntreCalle2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomEntreCalle2() {
        return domEntreCalle2;
    }

    /**
     * Sets the value of the domEntreCalle2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomEntreCalle2(String value) {
        this.domEntreCalle2 = value;
    }

    /**
     * Gets the value of the entidadFederativa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntidadFederativa() {
        return entidadFederativa;
    }

    /**
     * Sets the value of the entidadFederativa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntidadFederativa(String value) {
        this.entidadFederativa = value;
    }

    /**
     * Gets the value of the entidadFederativaDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEntidadFederativaDes() {
        return entidadFederativaDes;
    }

    /**
     * Sets the value of the entidadFederativaDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEntidadFederativaDes(String value) {
        this.entidadFederativaDes = value;
    }

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFecha(String value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the folio property.
     * 
     */
    public int getFolio() {
        return folio;
    }

    /**
     * Sets the value of the folio property.
     * 
     */
    public void setFolio(int value) {
        this.folio = value;
    }

    /**
     * Gets the value of the hdnCveColonia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHdnCveColonia() {
        return hdnCveColonia;
    }

    /**
     * Sets the value of the hdnCveColonia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHdnCveColonia(String value) {
        this.hdnCveColonia = value;
    }

    /**
     * Gets the value of the hdnCveLocalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHdnCveLocalidad() {
        return hdnCveLocalidad;
    }

    /**
     * Sets the value of the hdnCveLocalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHdnCveLocalidad(String value) {
        this.hdnCveLocalidad = value;
    }

    /**
     * Gets the value of the iumf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIUMF() {
        return iumf;
    }

    /**
     * Sets the value of the iumf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIUMF(String value) {
        this.iumf = value;
    }

    /**
     * Gets the value of the idPatron property.
     * 
     */
    public long getIdPatron() {
        return idPatron;
    }

    /**
     * Sets the value of the idPatron property.
     * 
     */
    public void setIdPatron(long value) {
        this.idPatron = value;
    }

    /**
     * Gets the value of the idTransaccion property.
     * 
     */
    public int getIdTransaccion() {
        return idTransaccion;
    }

    /**
     * Sets the value of the idTransaccion property.
     * 
     */
    public void setIdTransaccion(int value) {
        this.idTransaccion = value;
    }

    /**
     * Gets the value of the jornadaSemana property.
     * 
     */
    public int getJornadaSemana() {
        return jornadaSemana;
    }

    /**
     * Sets the value of the jornadaSemana property.
     * 
     */
    public void setJornadaSemana(int value) {
        this.jornadaSemana = value;
    }

    /**
     * Gets the value of the lugarDesRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLugarDesRegistro() {
        return lugarDesRegistro;
    }

    /**
     * Sets the value of the lugarDesRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLugarDesRegistro(String value) {
        this.lugarDesRegistro = value;
    }

    /**
     * Gets the value of the lugarDesRegistroMun property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLugarDesRegistroMun() {
        return lugarDesRegistroMun;
    }

    /**
     * Sets the value of the lugarDesRegistroMun property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLugarDesRegistroMun(String value) {
        this.lugarDesRegistroMun = value;
    }

    /**
     * Gets the value of the lugarNacimiento property.
     * 
     */
    public int getLugarNacimiento() {
        return lugarNacimiento;
    }

    /**
     * Sets the value of the lugarNacimiento property.
     * 
     */
    public void setLugarNacimiento(int value) {
        this.lugarNacimiento = value;
    }

    /**
     * Gets the value of the lugarNacimientoDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLugarNacimientoDes() {
        return lugarNacimientoDes;
    }

    /**
     * Sets the value of the lugarNacimientoDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLugarNacimientoDes(String value) {
        this.lugarNacimientoDes = value;
    }

    /**
     * Gets the value of the lugarRegistrMun property.
     * 
     */
    public int getLugarRegistrMun() {
        return lugarRegistrMun;
    }

    /**
     * Sets the value of the lugarRegistrMun property.
     * 
     */
    public void setLugarRegistrMun(int value) {
        this.lugarRegistrMun = value;
    }

    /**
     * Gets the value of the lugarRegistro property.
     * 
     */
    public int getLugarRegistro() {
        return lugarRegistro;
    }

    /**
     * Sets the value of the lugarRegistro property.
     * 
     */
    public void setLugarRegistro(int value) {
        this.lugarRegistro = value;
    }

    /**
     * Gets the value of the matriz property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the matriz property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMatriz().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getMatriz() {
        if (matriz == null) {
            matriz = new ArrayList<Object>();
        }
        return this.matriz;
    }

    /**
     * Gets the value of the mes property.
     * 
     */
    public int getMes() {
        return mes;
    }

    /**
     * Sets the value of the mes property.
     * 
     */
    public void setMes(int value) {
        this.mes = value;
    }

    /**
     * Gets the value of the mes2 property.
     * 
     */
    public int getMes2() {
        return mes2;
    }

    /**
     * Sets the value of the mes2 property.
     * 
     */
    public void setMes2(int value) {
        this.mes2 = value;
    }

    /**
     * Gets the value of the mesDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMesDes() {
        return mesDes;
    }

    /**
     * Sets the value of the mesDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMesDes(String value) {
        this.mesDes = value;
    }

    /**
     * Gets the value of the mesDesRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMesDesRegistro() {
        return mesDesRegistro;
    }

    /**
     * Sets the value of the mesDesRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMesDesRegistro(String value) {
        this.mesDesRegistro = value;
    }

    /**
     * Gets the value of the mesIngreso property.
     * 
     */
    public int getMesIngreso() {
        return mesIngreso;
    }

    /**
     * Sets the value of the mesIngreso property.
     * 
     */
    public void setMesIngreso(int value) {
        this.mesIngreso = value;
    }

    /**
     * Gets the value of the mesRegistro property.
     * 
     */
    public int getMesRegistro() {
        return mesRegistro;
    }

    /**
     * Sets the value of the mesRegistro property.
     * 
     */
    public void setMesRegistro(int value) {
        this.mesRegistro = value;
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
     * Gets the value of the nombreMadre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreMadre() {
        return nombreMadre;
    }

    /**
     * Sets the value of the nombreMadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreMadre(String value) {
        this.nombreMadre = value;
    }

    /**
     * Gets the value of the nombrePadre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombrePadre() {
        return nombrePadre;
    }

    /**
     * Sets the value of the nombrePadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombrePadre(String value) {
        this.nombrePadre = value;
    }

    /**
     * Gets the value of the numActa property.
     * 
     */
    public int getNumActa() {
        return numActa;
    }

    /**
     * Sets the value of the numActa property.
     * 
     */
    public void setNumActa(int value) {
        this.numActa = value;
    }

    /**
     * Gets the value of the numExtTel1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumExtTel1() {
        return numExtTel1;
    }

    /**
     * Sets the value of the numExtTel1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumExtTel1(String value) {
        this.numExtTel1 = value;
    }

    /**
     * Gets the value of the numExtTel2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumExtTel2() {
        return numExtTel2;
    }

    /**
     * Sets the value of the numExtTel2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumExtTel2(String value) {
        this.numExtTel2 = value;
    }

    /**
     * Gets the value of the numLibro property.
     * 
     */
    public int getNumLibro() {
        return numLibro;
    }

    /**
     * Sets the value of the numLibro property.
     * 
     */
    public void setNumLibro(int value) {
        this.numLibro = value;
    }

    /**
     * Gets the value of the numTelFijo1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTelFijo1() {
        return numTelFijo1;
    }

    /**
     * Sets the value of the numTelFijo1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTelFijo1(String value) {
        this.numTelFijo1 = value;
    }

    /**
     * Gets the value of the numTelFijo2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTelFijo2() {
        return numTelFijo2;
    }

    /**
     * Sets the value of the numTelFijo2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTelFijo2(String value) {
        this.numTelFijo2 = value;
    }

    /**
     * Gets the value of the numTomo property.
     * 
     */
    public int getNumTomo() {
        return numTomo;
    }

    /**
     * Sets the value of the numTomo property.
     * 
     */
    public void setNumTomo(int value) {
        this.numTomo = value;
    }

    /**
     * Gets the value of the numfoja property.
     * 
     */
    public int getNumfoja() {
        return numfoja;
    }

    /**
     * Sets the value of the numfoja property.
     * 
     */
    public void setNumfoja(int value) {
        this.numfoja = value;
    }

    /**
     * Gets the value of the preafiliacionReingreso property.
     * 
     */
    public int getPreafiliacionReingreso() {
        return preafiliacionReingreso;
    }

    /**
     * Sets the value of the preafiliacionReingreso property.
     * 
     */
    public void setPreafiliacionReingreso(int value) {
        this.preafiliacionReingreso = value;
    }

    /**
     * Gets the value of the refCodigoPostal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefCodigoPostal() {
        return refCodigoPostal;
    }

    /**
     * Sets the value of the refCodigoPostal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefCodigoPostal(String value) {
        this.refCodigoPostal = value;
    }

    /**
     * Gets the value of the refNoExt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefNoExt() {
        return refNoExt;
    }

    /**
     * Sets the value of the refNoExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefNoExt(String value) {
        this.refNoExt = value;
    }

    /**
     * Gets the value of the refNoInt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefNoInt() {
        return refNoInt;
    }

    /**
     * Sets the value of the refNoInt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefNoInt(String value) {
        this.refNoInt = value;
    }

    /**
     * Gets the value of the sFolio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSFolio() {
        return sFolio;
    }

    /**
     * Sets the value of the sFolio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSFolio(String value) {
        this.sFolio = value;
    }

    /**
     * Gets the value of the salarioBase property.
     * 
     */
    public double getSalarioBase() {
        return salarioBase;
    }

    /**
     * Sets the value of the salarioBase property.
     * 
     */
    public void setSalarioBase(double value) {
        this.salarioBase = value;
    }

    /**
     * Gets the value of the seguridadSocial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeguridadSocial() {
        return seguridadSocial;
    }

    /**
     * Sets the value of the seguridadSocial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeguridadSocial(String value) {
        this.seguridadSocial = value;
    }

    /**
     * Gets the value of the serie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerie() {
        return serie;
    }

    /**
     * Sets the value of the serie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerie(String value) {
        this.serie = value;
    }

    /**
     * Gets the value of the sexo property.
     * 
     */
    public int getSexo() {
        return sexo;
    }

    /**
     * Sets the value of the sexo property.
     * 
     */
    public void setSexo(int value) {
        this.sexo = value;
    }

    /**
     * Gets the value of the sexoDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSexoDes() {
        return sexoDes;
    }

    /**
     * Sets the value of the sexoDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSexoDes(String value) {
        this.sexoDes = value;
    }

    /**
     * Gets the value of the sinCurp property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSinCurp() {
        return sinCurp;
    }

    /**
     * Sets the value of the sinCurp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSinCurp(Boolean value) {
        this.sinCurp = value;
    }

    /**
     * Gets the value of the strDelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrDelegacion() {
        return strDelegacion;
    }

    /**
     * Sets the value of the strDelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrDelegacion(String value) {
        this.strDelegacion = value;
    }

    /**
     * Gets the value of the strSubDelegacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrSubDelegacion() {
        return strSubDelegacion;
    }

    /**
     * Sets the value of the strSubDelegacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrSubDelegacion(String value) {
        this.strSubDelegacion = value;
    }

    /**
     * Gets the value of the terminoOk property.
     * 
     */
    public int getTerminoOk() {
        return terminoOk;
    }

    /**
     * Sets the value of the terminoOk property.
     * 
     */
    public void setTerminoOk(int value) {
        this.terminoOk = value;
    }

    /**
     * Gets the value of the tipoDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDomicilio() {
        return tipoDomicilio;
    }

    /**
     * Sets the value of the tipoDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDomicilio(String value) {
        this.tipoDomicilio = value;
    }

    /**
     * Gets the value of the tipoDomicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDomicio() {
        return tipoDomicio;
    }

    /**
     * Sets the value of the tipoDomicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDomicio(String value) {
        this.tipoDomicio = value;
    }

    /**
     * Gets the value of the tipoOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * Sets the value of the tipoOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoOperacion(String value) {
        this.tipoOperacion = value;
    }

    /**
     * Gets the value of the tipoSalario property.
     * 
     */
    public int getTipoSalario() {
        return tipoSalario;
    }

    /**
     * Sets the value of the tipoSalario property.
     * 
     */
    public void setTipoSalario(int value) {
        this.tipoSalario = value;
    }

    /**
     * Gets the value of the tipoTrabajador property.
     * 
     */
    public int getTipoTrabajador() {
        return tipoTrabajador;
    }

    /**
     * Sets the value of the tipoTrabajador property.
     * 
     */
    public void setTipoTrabajador(int value) {
        this.tipoTrabajador = value;
    }

    /**
     * Gets the value of the tipoTramite property.
     * 
     */
    public int getTipoTramite() {
        return tipoTramite;
    }

    /**
     * Sets the value of the tipoTramite property.
     * 
     */
    public void setTipoTramite(int value) {
        this.tipoTramite = value;
    }

    /**
     * Gets the value of the umf property.
     * 
     */
    public int getUMF() {
        return umf;
    }

    /**
     * Sets the value of the umf property.
     * 
     */
    public void setUMF(int value) {
        this.umf = value;
    }

    /**
     * Gets the value of the umfDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUMFDes() {
        return umfDes;
    }

    /**
     * Sets the value of the umfDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUMFDes(String value) {
        this.umfDes = value;
    }

    /**
     * Gets the value of the verificadorNSS property.
     * 
     */
    public int getVerificadorNSS() {
        return verificadorNSS;
    }

    /**
     * Sets the value of the verificadorNSS property.
     * 
     */
    public void setVerificadorNSS(int value) {
        this.verificadorNSS = value;
    }

}
