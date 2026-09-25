package mx.gob.imss.digital.modelo.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "razon-resultado", namespace = "http://mx.gob.imss.digital.modelo.tramite")
@XmlRootElement(name = "razon-resultado", namespace = "http://mx.gob.imss.digital.modelo.tramite")
public class RazonResultado implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -2958857898232204689L;

    /**
     * identificador de la razon
     */
    private Long idRazonResultado;
    /**
     * Descripcion del resultado
     */
    private String descripcion;
    /**
     * @return the idRazonResultado
     */
    public Long getIdRazonResultado() {
        return idRazonResultado;
    }
    /**
     * @param idRazonResultado the idRazonResultado to set
     */
    public void setIdRazonResultado(Long idRazonResultado) {
        this.idRazonResultado = idRazonResultado;
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
