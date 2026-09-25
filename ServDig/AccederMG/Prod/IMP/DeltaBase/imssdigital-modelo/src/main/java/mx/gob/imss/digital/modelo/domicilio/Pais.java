
package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pais", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "pais", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class Pais implements Serializable {

    /**
     * Serial
     */
    private final static long serialVersionUID = 1234321L;
    /**
     * Identificador del pais
     */
    protected Integer idPais;
    /**
     * descripcion
     */
    protected String descripcion;
    /**
     * nacionalidad
     */
    protected String nacionalidad;

    /**
     * Gets the value of the idPais property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getIdPais() {
        return idPais;
    }

    /**
     * Sets the value of the idPais property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setIdPais(Integer value) {
        this.idPais = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the nacionalidad property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Sets the value of the nacionalidad property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNacionalidad(String value) {
        this.nacionalidad = value;
    }

}
