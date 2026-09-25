package mx.gob.imss.digital.modelo.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
/**
 * Estado de un tramite 
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estadoTramite", namespace = "http://mx.gob.imss.digital.modelo.tramite")
@XmlRootElement(name = "estadoTramite", namespace = "http://mx.gob.imss.digital.modelo.tramite")
public class EstadoTramite implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -916804291818334717L;
    /**
     * Identificador del estado de tramite
     */
    private Long idEstadoTramitePersona;
    /**
     * descripcion
     */
    private String descripcion;
    /**
     * @return the idEstadoTramitePersona
     */
    public Long getIdEstadoTramitePersona() {
        return idEstadoTramitePersona;
    }
    /**
     * @param idEstadoTramitePersona the idEstadoTramitePersona to set
     */
    public void setIdEstadoTramitePersona(Long idEstadoTramitePersona) {
        this.idEstadoTramitePersona = idEstadoTramitePersona;
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
