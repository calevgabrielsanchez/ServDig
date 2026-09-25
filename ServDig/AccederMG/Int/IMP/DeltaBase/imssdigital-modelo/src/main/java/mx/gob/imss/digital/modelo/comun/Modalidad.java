/**
 * 
 */
package mx.gob.imss.digital.modelo.comun;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * MOdelo del elemento modalidad
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modalidad", namespace = "http://mx.gob.imss.digital.modelo.comun")
@XmlRootElement(name = "modalidad", namespace = "http://mx.gob.imss.digital.modelo.comun")
public class Modalidad implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador de la modalidad
     */
    private long idModalidad;
    /**
     * DEscripcion de la modalidad
     */
    private String descripcion;
    
    protected String numModalidad;

    /**
     * @return the numModalidad
     */
    public String getNumModalidad() {
        return numModalidad;
    }

    /**
     * @param numModalidad the numModalidad to set
     */
    public void setNumModalidad(String numModalidad) {
        this.numModalidad = numModalidad;
    }
    
    /**
     * @return the idModalidad
     */
    public long getIdModalidad() {
        return idModalidad;
    }
    /**
     * @param idModalidad the idModalidad to set
     */
    public void setIdModalidad(long idModalidad) {
        this.idModalidad = idModalidad;
    }
    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }   
    
}
