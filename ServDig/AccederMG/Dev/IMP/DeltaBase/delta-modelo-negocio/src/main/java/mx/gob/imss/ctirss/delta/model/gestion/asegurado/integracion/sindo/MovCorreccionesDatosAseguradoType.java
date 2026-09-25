
package mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MovCorreccionesDatosAseguradoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MovCorreccionesDatosAseguradoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="delOrig" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="subOrig" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cveAplic" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tpMovto" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="origenMov" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numFolio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="argumento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="regPatron" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="digVrPat" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fMovto" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="fRecepMovi" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="cveUnica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idSubrServ" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idEventual" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numSegSoc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="digVrNss" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nomAseg" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idExtemp" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reducPago" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="extODel" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="salBase" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="salInfonavit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tpSalario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sexo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="mesNac" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="lugarNac" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="umf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="autPerm" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="delDest" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="subDest" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tpDerech" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="aaNac" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="situacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tsalODel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nombreDh" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mesNacAp" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nssCorr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="digVrNssCorr" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nomAsegC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tpPens" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="alfGuar" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numGuar" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="condicion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="locMpio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tpProrroga" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fecTerProrr" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="idPd" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="MovimientoCorreccionDatosAsegurado", namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MovCorreccionesDatosAseguradoType", namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", propOrder = {
    "delOrig",
    "subOrig",
    "cveAplic",
    "tpMovto",
    "origenMov",
    "numFolio",
    "argumento",
    "regPatron",
    "digVrPat",
    "fMovto",
    "fRecepMovi",
    "cveUnica",
    "idSubrServ",
    "idEventual",
    "numSegSoc",
    "digVrNss",
    "nomAseg",
    "idExtemp",
    "reducPago",
    "extODel",
    "salBase",
    "salInfonavit",
    "tpSalario",
    "sexo",
    "mesNac",
    "lugarNac",
    "umf",
    "autPerm",
    "delDest",
    "subDest",
    "tpDerech",
    "aaNac",
    "situacion",
    "tsalODel",
    "nombreDh",
    "mesNacAp",
    "nssCorr",
    "digVrNssCorr",
    "nomAsegC",
    "tpPens",
    "alfGuar",
    "numGuar",
    "condicion",
    "locMpio",
    "tpProrroga",
    "fecTerProrr",
    "idPd"
})
public class MovCorreccionesDatosAseguradoType implements Serializable{

    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int delOrig;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int subOrig;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int cveAplic;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int tpMovto;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int origenMov;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected String numFolio;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int argumento;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    protected String regPatron;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int digVrPat;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fMovto;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fRecepMovi;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    protected String cveUnica;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int idSubrServ;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int idEventual;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected String numSegSoc;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int digVrNss;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    protected String nomAseg;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int idExtemp;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int reducPago;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int extODel;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int salBase;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int salInfonavit;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int tpSalario;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int sexo;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int mesNac;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int lugarNac;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int umf;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int autPerm;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int delDest;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int subDest;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int tpDerech;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int aaNac;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int situacion;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    protected String tsalODel;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    protected String nombreDh;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int mesNacAp;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int nssCorr;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int digVrNssCorr;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    protected String nomAsegC;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int tpPens;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    protected String alfGuar;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int numGuar;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int condicion;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    protected String locMpio;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int tpProrroga;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fecTerProrr;
    @XmlElement(namespace = "http://www.mx.gob.imss.ctirss.delta/movCorreccionesDatosAsegurado")
    protected int idPd;

    /**
     * Gets the value of the delOrig property.
     * 
     */
    public int getDelOrig() {
        return delOrig;
    }

    /**
     * Sets the value of the delOrig property.
     * 
     */
    public void setDelOrig(int value) {
        this.delOrig = value;
    }

    /**
     * Gets the value of the subOrig property.
     * 
     */
    public int getSubOrig() {
        return subOrig;
    }

    /**
     * Sets the value of the subOrig property.
     * 
     */
    public void setSubOrig(int value) {
        this.subOrig = value;
    }

    /**
     * Gets the value of the cveAplic property.
     * 
     */
    public int getCveAplic() {
        return cveAplic;
    }

    /**
     * Sets the value of the cveAplic property.
     * 
     */
    public void setCveAplic(int value) {
        this.cveAplic = value;
    }

    /**
     * Gets the value of the tpMovto property.
     * 
     */
    public int getTpMovto() {
        return tpMovto;
    }

    /**
     * Sets the value of the tpMovto property.
     * 
     */
    public void setTpMovto(int value) {
        this.tpMovto = value;
    }

    /**
     * Gets the value of the origenMov property.
     * 
     */
    public int getOrigenMov() {
        return origenMov;
    }

    /**
     * Sets the value of the origenMov property.
     * 
     */
    public void setOrigenMov(int value) {
        this.origenMov = value;
    }

    /**
     * Gets the value of the numFolio property.
     * 
     */
    public String getNumFolio() {
        return numFolio;
    }

    /**
     * Sets the value of the numFolio property.
     * 
     */
    public void setNumFolio(String value) {
        this.numFolio = value;
    }

    /**
     * Gets the value of the argumento property.
     * 
     */
    public int getArgumento() {
        return argumento;
    }

    /**
     * Sets the value of the argumento property.
     * 
     */
    public void setArgumento(int value) {
        this.argumento = value;
    }

    /**
     * Gets the value of the regPatron property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegPatron() {
        return regPatron;
    }

    /**
     * Sets the value of the regPatron property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegPatron(String value) {
        this.regPatron = value;
    }

    /**
     * Gets the value of the digVrPat property.
     * 
     */
    public int getDigVrPat() {
        return digVrPat;
    }

    /**
     * Sets the value of the digVrPat property.
     * 
     */
    public void setDigVrPat(int value) {
        this.digVrPat = value;
    }

    /**
     * Gets the value of the fMovto property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFMovto() {
        return fMovto;
    }

    /**
     * Sets the value of the fMovto property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFMovto(XMLGregorianCalendar value) {
        this.fMovto = value;
    }

    /**
     * Gets the value of the fRecepMovi property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFRecepMovi() {
        return fRecepMovi;
    }

    /**
     * Sets the value of the fRecepMovi property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFRecepMovi(XMLGregorianCalendar value) {
        this.fRecepMovi = value;
    }

    /**
     * Gets the value of the cveUnica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveUnica() {
        return cveUnica;
    }

    /**
     * Sets the value of the cveUnica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveUnica(String value) {
        this.cveUnica = value;
    }

    /**
     * Gets the value of the idSubrServ property.
     * 
     */
    public int getIdSubrServ() {
        return idSubrServ;
    }

    /**
     * Sets the value of the idSubrServ property.
     * 
     */
    public void setIdSubrServ(int value) {
        this.idSubrServ = value;
    }

    /**
     * Gets the value of the idEventual property.
     * 
     */
    public int getIdEventual() {
        return idEventual;
    }

    /**
     * Sets the value of the idEventual property.
     * 
     */
    public void setIdEventual(int value) {
        this.idEventual = value;
    }

    /**
     * Gets the value of the numSegSoc property.
     * 
     */
    public String getNumSegSoc() {
        return numSegSoc;
    }

    /**
     * Sets the value of the numSegSoc property.
     * 
     */
    public void setNumSegSoc(String value) {
        this.numSegSoc = value;
    }

    /**
     * Gets the value of the digVrNss property.
     * 
     */
    public int getDigVrNss() {
        return digVrNss;
    }

    /**
     * Sets the value of the digVrNss property.
     * 
     */
    public void setDigVrNss(int value) {
        this.digVrNss = value;
    }

    /**
     * Gets the value of the nomAseg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomAseg() {
        return nomAseg;
    }

    /**
     * Sets the value of the nomAseg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomAseg(String value) {
        this.nomAseg = value;
    }

    /**
     * Gets the value of the idExtemp property.
     * 
     */
    public int getIdExtemp() {
        return idExtemp;
    }

    /**
     * Sets the value of the idExtemp property.
     * 
     */
    public void setIdExtemp(int value) {
        this.idExtemp = value;
    }

    /**
     * Gets the value of the reducPago property.
     * 
     */
    public int getReducPago() {
        return reducPago;
    }

    /**
     * Sets the value of the reducPago property.
     * 
     */
    public void setReducPago(int value) {
        this.reducPago = value;
    }

    /**
     * Gets the value of the extODel property.
     * 
     */
    public int getExtODel() {
        return extODel;
    }

    /**
     * Sets the value of the extODel property.
     * 
     */
    public void setExtODel(int value) {
        this.extODel = value;
    }

    /**
     * Gets the value of the salBase property.
     * 
     */
    public int getSalBase() {
        return salBase;
    }

    /**
     * Sets the value of the salBase property.
     * 
     */
    public void setSalBase(int value) {
        this.salBase = value;
    }

    /**
     * Gets the value of the salInfonavit property.
     * 
     */
    public int getSalInfonavit() {
        return salInfonavit;
    }

    /**
     * Sets the value of the salInfonavit property.
     * 
     */
    public void setSalInfonavit(int value) {
        this.salInfonavit = value;
    }

    /**
     * Gets the value of the tpSalario property.
     * 
     */
    public int getTpSalario() {
        return tpSalario;
    }

    /**
     * Sets the value of the tpSalario property.
     * 
     */
    public void setTpSalario(int value) {
        this.tpSalario = value;
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
     * Gets the value of the mesNac property.
     * 
     */
    public int getMesNac() {
        return mesNac;
    }

    /**
     * Sets the value of the mesNac property.
     * 
     */
    public void setMesNac(int value) {
        this.mesNac = value;
    }

    /**
     * Gets the value of the lugarNac property.
     * 
     */
    public int getLugarNac() {
        return lugarNac;
    }

    /**
     * Sets the value of the lugarNac property.
     * 
     */
    public void setLugarNac(int value) {
        this.lugarNac = value;
    }

    /**
     * Gets the value of the umf property.
     * 
     */
    public int getUmf() {
        return umf;
    }

    /**
     * Sets the value of the umf property.
     * 
     */
    public void setUmf(int value) {
        this.umf = value;
    }

    /**
     * Gets the value of the autPerm property.
     * 
     */
    public int getAutPerm() {
        return autPerm;
    }

    /**
     * Sets the value of the autPerm property.
     * 
     */
    public void setAutPerm(int value) {
        this.autPerm = value;
    }

    /**
     * Gets the value of the delDest property.
     * 
     */
    public int getDelDest() {
        return delDest;
    }

    /**
     * Sets the value of the delDest property.
     * 
     */
    public void setDelDest(int value) {
        this.delDest = value;
    }

    /**
     * Gets the value of the subDest property.
     * 
     */
    public int getSubDest() {
        return subDest;
    }

    /**
     * Sets the value of the subDest property.
     * 
     */
    public void setSubDest(int value) {
        this.subDest = value;
    }

    /**
     * Gets the value of the tpDerech property.
     * 
     */
    public int getTpDerech() {
        return tpDerech;
    }

    /**
     * Sets the value of the tpDerech property.
     * 
     */
    public void setTpDerech(int value) {
        this.tpDerech = value;
    }

    /**
     * Gets the value of the aaNac property.
     * 
     */
    public int getAaNac() {
        return aaNac;
    }

    /**
     * Sets the value of the aaNac property.
     * 
     */
    public void setAaNac(int value) {
        this.aaNac = value;
    }

    /**
     * Gets the value of the situacion property.
     * 
     */
    public int getSituacion() {
        return situacion;
    }

    /**
     * Sets the value of the situacion property.
     * 
     */
    public void setSituacion(int value) {
        this.situacion = value;
    }

    /**
     * Gets the value of the tsalODel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTsalODel() {
        return tsalODel;
    }

    /**
     * Sets the value of the tsalODel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTsalODel(String value) {
        this.tsalODel = value;
    }

    /**
     * Gets the value of the nombreDh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreDh() {
        return nombreDh;
    }

    /**
     * Sets the value of the nombreDh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreDh(String value) {
        this.nombreDh = value;
    }

    /**
     * Gets the value of the mesNacAp property.
     * 
     */
    public int getMesNacAp() {
        return mesNacAp;
    }

    /**
     * Sets the value of the mesNacAp property.
     * 
     */
    public void setMesNacAp(int value) {
        this.mesNacAp = value;
    }

    /**
     * Gets the value of the nssCorr property.
     * 
     */
    public int getNssCorr() {
        return nssCorr;
    }

    /**
     * Sets the value of the nssCorr property.
     * 
     */
    public void setNssCorr(int value) {
        this.nssCorr = value;
    }

    /**
     * Gets the value of the digVrNssCorr property.
     * 
     */
    public int getDigVrNssCorr() {
        return digVrNssCorr;
    }

    /**
     * Sets the value of the digVrNssCorr property.
     * 
     */
    public void setDigVrNssCorr(int value) {
        this.digVrNssCorr = value;
    }

    /**
     * Gets the value of the nomAsegC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomAsegC() {
        return nomAsegC;
    }

    /**
     * Sets the value of the nomAsegC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomAsegC(String value) {
        this.nomAsegC = value;
    }

    /**
     * Gets the value of the tpPens property.
     * 
     */
    public int getTpPens() {
        return tpPens;
    }

    /**
     * Sets the value of the tpPens property.
     * 
     */
    public void setTpPens(int value) {
        this.tpPens = value;
    }

    /**
     * Gets the value of the alfGuar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlfGuar() {
        return alfGuar;
    }

    /**
     * Sets the value of the alfGuar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlfGuar(String value) {
        this.alfGuar = value;
    }

    /**
     * Gets the value of the numGuar property.
     * 
     */
    public int getNumGuar() {
        return numGuar;
    }

    /**
     * Sets the value of the numGuar property.
     * 
     */
    public void setNumGuar(int value) {
        this.numGuar = value;
    }

    /**
     * Gets the value of the condicion property.
     * 
     */
    public int getCondicion() {
        return condicion;
    }

    /**
     * Sets the value of the condicion property.
     * 
     */
    public void setCondicion(int value) {
        this.condicion = value;
    }

    /**
     * Gets the value of the locMpio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocMpio() {
        return locMpio;
    }

    /**
     * Sets the value of the locMpio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocMpio(String value) {
        this.locMpio = value;
    }

    /**
     * Gets the value of the tpProrroga property.
     * 
     */
    public int getTpProrroga() {
        return tpProrroga;
    }

    /**
     * Sets the value of the tpProrroga property.
     * 
     */
    public void setTpProrroga(int value) {
        this.tpProrroga = value;
    }

    /**
     * Gets the value of the fecTerProrr property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecTerProrr() {
        return fecTerProrr;
    }

    /**
     * Sets the value of the fecTerProrr property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecTerProrr(XMLGregorianCalendar value) {
        this.fecTerProrr = value;
    }

    /**
     * Gets the value of the idPd property.
     * 
     */
    public int getIdPd() {
        return idPd;
    }

    /**
     * Sets the value of the idPd property.
     * 
     */
    public void setIdPd(int value) {
        this.idPd = value;
    }

}
