/**
 * 
 */
package mx.gob.imss.digital.modelo.cobranza;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estadoCompra", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
@XmlRootElement(name = "estadoCompra", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class EstadoCompra implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador de la modalidad
     */
    private long idEstadoCompra;
    /**
     * DEscripcion de la modalidad
     */
    private String descripcion;
    
    
    /**
     * @return the idEstadoCompra
     */
    public long getIdEstadoCompra() {
        return idEstadoCompra;
    }
    /**
     * @param idEstadoCompra the idEstadoCompra to set
     */
    public void setIdEstadoCompra(long idEstadoCompra) {
        this.idEstadoCompra = idEstadoCompra;
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
