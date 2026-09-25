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
 * Estodo de los pagos
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estadoPago", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
@XmlRootElement(name = "estadoPago", namespace = "http://mx.gob.imss.digital.modelo.cobranza")
public class EstadoPago implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador de la modalidad
     */
    private long idEstadoPago;
    /**
     * DEscripcion de la modalidad
     */
    private String descripcion;
    /**
     * @return the idEstadoPago
     */
    public long getIdEstadoPago() {
        return idEstadoPago;
    }
    /**
     * @param idEstadoPago the idEstadoPago to set
     */
    public void setIdEstadoPago(long idEstadoPago) {
        this.idEstadoPago = idEstadoPago;
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
