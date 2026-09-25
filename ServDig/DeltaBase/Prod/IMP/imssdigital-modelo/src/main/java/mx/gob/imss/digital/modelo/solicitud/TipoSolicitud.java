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
 * Modelo para las solicitudes
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoSolicitud", namespace = "http://mx.gob.imss.digital.modelo.solicitud")
@XmlRootElement(name = "tipoSolicitud", namespace = "http://mx.gob.imss.digital.modelo.solicitud")
public class TipoSolicitud implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 8626273193359270811L;
    /**
     * Identificador del tipo de solicitud
     */
    private Long idTipoSolicitud;
    /**
     * Decripcion del tipo de solicitud
     */
    private String descripcion;
    /**
     * Siglas del tipo de solicitud
     */
    private String siglas;
    /**
     * @return the idTipoSolicitud
     */
    public Long getIdTipoSolicitud() {
        return idTipoSolicitud;
    }
    /**
     * @param idTipoSolicitud the idTipoSolicitud to set
     */
    public void setIdTipoSolicitud(Long idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
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
    /**
     * @return the siglas
     */
    public String getSiglas() {
        return siglas;
    }
    /**
     * @param siglas the siglas to set
     */
    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }
    
    
}
