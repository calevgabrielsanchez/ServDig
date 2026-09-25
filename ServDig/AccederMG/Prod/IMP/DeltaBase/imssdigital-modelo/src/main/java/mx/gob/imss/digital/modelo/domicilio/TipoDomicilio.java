/**
 * 
 */
package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Catalogo de los tipos de domicilios
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoDomicilio", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "tipoDomicilio", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class TipoDomicilio implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Clave del ipode domicilio
     */
    private long idTipoDomicilio;
    /**
     * Descripcion del tipo de domicilio
     */
    private String descripcion;
    /**
     * @return the idTipoDomicilio
     */
    public long getIdTipoDomicilio() {
        return idTipoDomicilio;
    }
    /**
     * @param idTipoDomicilio the idTipoDomicilio to set
     */
    public void setIdTipoDomicilio(long idTipoDomicilio) {
        this.idTipoDomicilio = idTipoDomicilio;
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
