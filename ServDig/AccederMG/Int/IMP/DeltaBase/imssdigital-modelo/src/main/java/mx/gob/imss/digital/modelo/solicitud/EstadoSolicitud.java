/**
 * 
 */
package mx.gob.imss.digital.modelo.solicitud;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Modelo del estado de la solicitud
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estadoSolicitud", namespace = "http://mx.gob.imss.digital.modelo.solicitud")
@XmlRootElement(name = "estadoSolicitud", namespace = "http://mx.gob.imss.digital.modelo.solicitud")
public class EstadoSolicitud implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 3664155376831085561L;
    /**
     * Id del estado de la solicitud
     */
    private Integer idEstadoSolicitud;
    /**
     * Descripcion
     */
    private String descripcion;
    /**
     * @return the idEstadoSolicitud
     */
    public Integer getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }
    /**
     * @param idEstadoSolicitud the idEstadoSolicitud to set
     */
    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
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
