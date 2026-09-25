package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;

@XmlRootElement(name = "CertificadoNacimiento")
public class CertificadoNacimiento
    extends DocumentoProbatorio implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    protected Sexo sexo;
    protected String noFolio;
    protected Date fechaAlumbramiento;
    protected String desLugarAlumbramiento;

    /**
     * Gets the value of the sexo property.
     * 
     * @return
     *     possible object is
     *     {@link Sexo }
     *     
     */
    public Sexo getSexo() {
        return sexo;
    }

    /**
     * Sets the value of the sexo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sexo }
     *     
     */
    public void setSexo(Sexo value) {
        this.sexo = value;
    }

    /**
     * Gets the value of the noFolio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoFolio() {
        return noFolio;
    }

    /**
     * Sets the value of the noFolio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoFolio(String value) {
        this.noFolio = value;
    }

   
    public Date getFechaAlumbramiento() {
        return fechaAlumbramiento;
    }

    
    public void setFechaAlumbramiento(Date value) {
        this.fechaAlumbramiento = value;
    }

    /**
     * Gets the value of the desLugarAlumbramiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesLugarAlumbramiento() {
        return desLugarAlumbramiento;
    }

    /**
     * Sets the value of the desLugarAlumbramiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDesLugarAlumbramiento(String value) {
        this.desLugarAlumbramiento = value;
    }

}
