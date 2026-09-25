/**
 * 
 */
package mx.gob.imss.digital.modelo.seguros;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * MOdelo de estados para un seguro ivro
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estadoSeguroIvro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
@XmlRootElement(name = "estadoSeguroIvro", namespace = "http://mx.gob.imss.digital.modelo.seguros")
public class EstadoSeguroIvro implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador del estado
     */
    private Long idEstadoSeguro;
    /**
     * DEscripcion del estado
     */
    private String descripcion;
    /**
     * @return the idEstadoSeguro
     */
    public Long getIdEstadoSeguro() {
        return idEstadoSeguro;
    }
    /**
     * @param idEstadoSeguro the idEstadoSeguro to set
     */
    public void setIdEstadoSeguro(Long idEstadoSeguro) {
        this.idEstadoSeguro = idEstadoSeguro;
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
