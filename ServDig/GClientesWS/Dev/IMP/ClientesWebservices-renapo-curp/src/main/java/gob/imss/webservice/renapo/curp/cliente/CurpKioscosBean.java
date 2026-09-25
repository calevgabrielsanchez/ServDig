
package gob.imss.webservice.renapo.curp.cliente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurpKioscosBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurpKioscosBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AnioReg" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Apellido1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Apellido2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodigoError" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CRIP" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Curp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CveEntidadEmisora" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CveEntidadNac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CveMunicipioReg" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DocProbatorio" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FechNac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Foja" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FolioCarta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Libro" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nacionalidad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumEntidadReg" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumRegExtranjeros" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Sexo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatusOper" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoError" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Tomo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EstatusCURP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumActa" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DesMunicipio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DesEntidadNac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DesEntidadRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DesEstatusCURP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CurpsHistoricas" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurpKioscosBean", propOrder = {
    "anioReg",
    "apellido1",
    "apellido2",
    "codigoError",
    "crip",
    "curp",
    "cveEntidadEmisora",
    "cveEntidadNac",
    "cveMunicipioReg",
    "docProbatorio",
    "fechNac",
    "foja",
    "folioCarta",
    "libro",
    "message",
    "nacionalidad",
    "nombre",
    "numEntidadReg",
    "numRegExtranjeros",
    "sexo",
    "statusOper",
    "tipoError",
    "tomo",
    "estatusCURP",
    "numActa",
    "desMunicipio",
    "desEntidadNac",
    "desEntidadRegistro",
    "desEstatusCURP",
    "curpsHistoricas"
    
})
public class CurpKioscosBean {

    @XmlElement(name = "AnioReg")
    protected int anioReg;
    @XmlElement(name = "Apellido1")
    protected String apellido1;
    @XmlElement(name = "Apellido2")
    protected String apellido2;
    @XmlElement(name = "CodigoError")
    protected int codigoError;
    @XmlElement(name = "CRIP")
    protected String crip;
    @XmlElement(name = "Curp")
    protected String curp;
    @XmlElement(name = "CveEntidadEmisora")
    protected String cveEntidadEmisora;
    @XmlElement(name = "CveEntidadNac")
    protected String cveEntidadNac;
    @XmlElement(name = "CveMunicipioReg")
    protected int cveMunicipioReg;
    @XmlElement(name = "DocProbatorio")
    protected int docProbatorio;
    @XmlElement(name = "FechNac")
    protected String fechNac;
    @XmlElement(name = "Foja")
    protected int foja;
    @XmlElement(name = "FolioCarta")
    protected int folioCarta;
    @XmlElement(name = "Libro")
    protected int libro;
    @XmlElement(name = "Message")
    protected String message;
    @XmlElement(name = "Nacionalidad")
    protected String nacionalidad;
    @XmlElement(name = "Nombres")
    protected String nombre;
    @XmlElement(name = "NumEntidadReg")
    protected int numEntidadReg;
    @XmlElement(name = "NumRegExtranjeros")
    protected int numRegExtranjeros;
    @XmlElement(name = "Sexo")
    protected String sexo;
    @XmlElement(name = "StatusOper")
    protected String statusOper;
    @XmlElement(name = "TipoError")
    protected int tipoError;
    @XmlElement(name = "Tomo")
    protected int tomo;
    @XmlElement(name = "EstatusCURP")
    protected String estatusCURP;
    @XmlElement(name = "NumActa")
    protected int numActa;
    @XmlElement(name = "DesMunicipio")
    protected String desMunicipio;
    @XmlElement(name = "DesEntidadNac")
    protected String desEntidadNac;
    
    @XmlElement(name = "DesEntidadRegistro")
    protected String desEntidadRegistro;
    
    @XmlElement(name = "DesEstatusCURP")
    protected String desEstatusCURP;
    
    @XmlElement(name = "CurpsHistoricas")
    protected String curpsHistoricas;

    
    
    

	public String getCurpsHistoricas() {
		return curpsHistoricas;
	}

	public void setCurpsHistoricas(String curpsHistoricas) {
		this.curpsHistoricas = curpsHistoricas;
	}

	/**
     * Gets the value of the anioReg property.
     * 
     */
    public int getAnioReg() {
        return anioReg;
    }

    /**
     * Sets the value of the anioReg property.
     * 
     */
    public void setAnioReg(int value) {
        this.anioReg = value;
    }

    /**
     * Gets the value of the apellido1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Sets the value of the apellido1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido1(String value) {
        this.apellido1 = value;
    }

    /**
     * Gets the value of the apellido2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Sets the value of the apellido2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido2(String value) {
        this.apellido2 = value;
    }

    /**
     * Gets the value of the codigoError property.
     * 
     */
    public int getCodigoError() {
        return codigoError;
    }

    /**
     * Sets the value of the codigoError property.
     * 
     */
    public void setCodigoError(int value) {
        this.codigoError = value;
    }

    /**
     * Gets the value of the crip property.
     * 
     */
    public String getCRIP() {
        return crip;
    }

    /**
     * Sets the value of the crip property.
     * 
     */
    public void setCRIP(String value) {
        this.crip = value;
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
     * Gets the value of the cveEntidadEmisora property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveEntidadEmisora() {
        return cveEntidadEmisora;
    }

    /**
     * Sets the value of the cveEntidadEmisora property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveEntidadEmisora(String value) {
        this.cveEntidadEmisora = value;
    }

    /**
     * Gets the value of the cveEntidadNac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveEntidadNac() {
        return cveEntidadNac;
    }

    /**
     * Sets the value of the cveEntidadNac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveEntidadNac(String value) {
        this.cveEntidadNac = value;
    }

    /**
     * Gets the value of the cveMunicipioReg property.
     * 
     */
    public int getCveMunicipioReg() {
        return cveMunicipioReg;
    }

    /**
     * Sets the value of the cveMunicipioReg property.
     * 
     */
    public void setCveMunicipioReg(int value) {
        this.cveMunicipioReg = value;
    }

    /**
     * Gets the value of the docProbatorio property.
     * 
     */
    public int getDocProbatorio() {
        return docProbatorio;
    }

    /**
     * Sets the value of the docProbatorio property.
     * 
     */
    public void setDocProbatorio(int value) {
        this.docProbatorio = value;
    }

    /**
     * Gets the value of the fechNac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFechNac() {
        return fechNac;
    }

    /**
     * Sets the value of the fechNac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFechNac(String value) {
        this.fechNac = value;
    }

    /**
     * Gets the value of the foja property.
     * 
     */
    public int getFoja() {
        return foja;
    }

    /**
     * Sets the value of the foja property.
     * 
     */
    public void setFoja(int value) {
        this.foja = value;
    }

    /**
     * Gets the value of the folioCarta property.
     * 
     */
    public int getFolioCarta() {
        return folioCarta;
    }

    /**
     * Sets the value of the folioCarta property.
     * 
     */
    public void setFolioCarta(int value) {
        this.folioCarta = value;
    }

    /**
     * Gets the value of the libro property.
     * 
     */
    public int getLibro() {
        return libro;
    }

    /**
     * Sets the value of the libro property.
     * 
     */
    public void setLibro(int value) {
        this.libro = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
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
     * Gets the value of the numEntidadReg property.
     * 
     */
    public int getNumEntidadReg() {
        return numEntidadReg;
    }

    /**
     * Sets the value of the numEntidadReg property.
     * 
     */
    public void setNumEntidadReg(int value) {
        this.numEntidadReg = value;
    }

    /**
     * Gets the value of the numRegExtranjeros property.
     * 
     */
    public int getNumRegExtranjeros() {
        return numRegExtranjeros;
    }

    /**
     * Sets the value of the numRegExtranjeros property.
     * 
     */
    public void setNumRegExtranjeros(int value) {
        this.numRegExtranjeros = value;
    }

    /**
     * Gets the value of the sexo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Sets the value of the sexo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSexo(String value) {
        this.sexo = value;
    }

    /**
     * Gets the value of the statusOper property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusOper() {
        return statusOper;
    }

    /**
     * Sets the value of the statusOper property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusOper(String value) {
        this.statusOper = value;
    }

    /**
     * Gets the value of the tipoError property.
     * 
     */
    public int getTipoError() {
        return tipoError;
    }

    /**
     * Sets the value of the tipoError property.
     * 
     */
    public void setTipoError(int value) {
        this.tipoError = value;
    }

    /**
     * Gets the value of the tomo property.
     * 
     */
    public int getTomo() {
        return tomo;
    }

    /**
     * Sets the value of the tomo property.
     * 
     */
    public void setTomo(int value) {
        this.tomo = value;
    }

    /**
     * Gets the value of the estatusCURP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstatusCURP() {
        return estatusCURP;
    }

    /**
     * Sets the value of the estatusCURP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstatusCURP(String value) {
        this.estatusCURP = value;
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
     * Gets the value of the desMunicipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesMunicipio() {
        return desMunicipio;
    }

    /**
     * Sets the value of the desMunicipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesMunicipio(String value) {
        this.desMunicipio = value;
    }

    /**
     * Gets the value of the desEntidadNac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesEntidadNac() {
        return desEntidadNac;
    }

    /**
     * Sets the value of the desEntidadNac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesEntidadNac(String value) {
        this.desEntidadNac = value;
    }

    /**
     * Gets the value of the desEntidadRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesEntidadRegistro() {
        return desEntidadRegistro;
    }

    /**
     * Sets the value of the desEntidadRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesEntidadRegistro(String value) {
        this.desEntidadRegistro = value;
    }

    /**
     * Gets the value of the desEstatusCURP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesEstatusCURP() {
        return desEstatusCURP;
    }

    /**
     * Sets the value of the desEstatusCURP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesEstatusCURP(String value) {
        this.desEstatusCURP = value;
    }

}
