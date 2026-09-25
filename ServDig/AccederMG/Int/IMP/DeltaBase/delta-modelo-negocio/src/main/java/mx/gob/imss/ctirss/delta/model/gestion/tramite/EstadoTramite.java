package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class EstadoTramite extends AbstractModel implements Serializable {

    private static final long serialVersionUID = -916804291818334717L;
    private Integer idEstadoTramitePersona;
    private String descripcion;

    public Integer getIdEstadoTramitePersona() {
        return idEstadoTramitePersona;
    }

    public void setIdEstadoTramitePersona(final Integer idEstadoTramitePersona) {
        this.idEstadoTramitePersona = idEstadoTramitePersona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

}
