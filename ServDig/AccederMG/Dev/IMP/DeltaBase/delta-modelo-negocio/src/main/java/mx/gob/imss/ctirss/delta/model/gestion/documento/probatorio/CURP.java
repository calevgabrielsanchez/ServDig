package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;

@XmlRootElement( name = "CURP")
public class CURP extends DocumentoProbatorio implements Serializable{
	private static final long serialVersionUID = 1L;
    protected String curp;
    protected Long numTipoDocumento;
    protected String descripcionTipoDocumento;
    protected Date fechaInscripcion;
    protected Long anioRegistro;
    protected String noLibro;
    protected String noActa;
    protected String noFoja;    
    protected String noTomo;
    protected String crip;
    protected String numFolioExtranjero;
    protected String refFolio;
    protected Municipio municipio;

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
	
	public String getNumFolioExtranjero() {
		return numFolioExtranjero;
	}

	public void setNumFolioExtranjero(String numFolioExtranjero) {
		this.numFolioExtranjero = numFolioExtranjero;
	}
	
	public String getDescripcionTipoDocumento() {
		return descripcionTipoDocumento;
	}

	public void setDescripcionTipoDocumento(String descripcionTipoDocumento) {
		this.descripcionTipoDocumento = descripcionTipoDocumento;
	}
	
	public Long getNumTipoDocumento() {
		return numTipoDocumento;
	}

	public void setNumTipoDocumento(Long numTipoDocumento) {
		this.numTipoDocumento = numTipoDocumento;
	}
    
    /**
     * Gets the value of the noFoja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoFoja() {
		return noFoja;
	}

    /**
     * Sets the value of the noFoja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setNoFoja(String noFoja) {
		this.noFoja = noFoja;
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
   
    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    
    public void setFechaInscripcion(Date value) {
        this.fechaInscripcion = value;
    }

    /**
     * Gets the value of the anioRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public Long getAnioRegistro() {
        return anioRegistro;
    }

    /**
     * Sets the value of the anioRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAnioRegistro(Long value) {
        this.anioRegistro = value;
    }

    /**
     * Gets the value of the noLibro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoLibro() {
        return noLibro;
    }

    /**
     * Sets the value of the noLibro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoLibro(String value) {
        this.noLibro = value;
    }

    /**
     * Gets the value of the noActa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoActa() {
        return noActa;
    }

    /**
     * Sets the value of the noActa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoActa(String value) {
        this.noActa = value;
    }

    /**
     * Gets the value of the noTomo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoTomo() {
        return noTomo;
    }

    /**
     * Sets the value of the noTomo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoTomo(String value) {
        this.noTomo = value;
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

	public String getRefFolio() {
		return refFolio;
	}

	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}

}