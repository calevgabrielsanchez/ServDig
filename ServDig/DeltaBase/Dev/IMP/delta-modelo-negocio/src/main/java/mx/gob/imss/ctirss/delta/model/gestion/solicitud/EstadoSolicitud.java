package mx.gob.imss.ctirss.delta.model.gestion.solicitud;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class EstadoSolicitud extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 3664155376831085561L;
    private Integer idEstadoSolicitud;
    private String descripcion;
	//Derechohabiente
	protected Boolean activo;

	public EstadoSolicitud() {
		super();
	}
	
	public EstadoSolicitud(final Integer estadoSolicitudId) {
		super();
		idEstadoSolicitud = estadoSolicitudId;
	}
	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(final Boolean activo) {
		this.activo = activo;
	}

	public Integer getIdEstadoSolicitud() {
        return idEstadoSolicitud;
    }

    public void setIdEstadoSolicitud(final Integer idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

}
