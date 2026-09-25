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
 * Modelo para el origen de una solicitud
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "origenSolicitud", namespace = "http://mx.gob.imss.digital.modelo.solicitud")
@XmlRootElement(name = "origenSolicitud", namespace = "http://mx.gob.imss.digital.modelo.solicitud")
public class OrigenSolicitud implements Serializable {
    /**
     * Serial version uid
     */
    private static final long serialVersionUID = -4516658781588680627L;

    /**
     * Ide del origen
     */
    private Long idOrigenSolicitud;
    /**
     * Descripcion
     */
    private String descripcion;
    /**
     * @return the idOrigenSolicitud
     */
    public Long getIdOrigenSolicitud() {
        return idOrigenSolicitud;
    }
    /**
     * @param idOrigenSolicitud the idOrigenSolicitud to set
     */
    public void setIdOrigenSolicitud(Long idOrigenSolicitud) {
        this.idOrigenSolicitud = idOrigenSolicitud;
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
