package mx.gob.imss.ctirss.delta.model.gestion.solicitud;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;

public class CitaSolicitud extends AbstractModel implements Serializable {

    private static final long serialVersionUID = -8920648651907167811L;

    private UnidadMedicaFamiliar umf;
    private Date fechaHora; // Se guarda la fecha y hora en un solo campo Date. 
    private Turno turno;
    private Ventanilla ventanilla;
    private Solicitud solicitud;
    private Long idCitaSolicitud;

    public UnidadMedicaFamiliar getUmf() {
        return umf;
    }

    public void setUmf(UnidadMedicaFamiliar umf) {
        this.umf = umf;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Ventanilla getVentanilla() {
        return ventanilla;
    }

    public void setVentanilla(Ventanilla ventanilla) {
        this.ventanilla = ventanilla;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Long getIdCitaSolicitud() {
        return idCitaSolicitud;
    }

    public void setIdCitaSolicitud(Long idCitaSolicitud) {
        this.idCitaSolicitud = idCitaSolicitud;
    }

}
