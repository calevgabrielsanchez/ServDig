package mx.gob.imss.ctirss.delta.model.gestion.solicitud;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

public class CitaSolicitud extends AbstractModel implements Serializable {

    private static final long serialVersionUID = -8920648651907167811L;

    private UnidadMedicaFamiliar umf;
    private Date fechaHora; // Se guarda la fecha y hora en un solo campo Date. 
    private Turno turno;
    private Ventanilla ventanilla;
    private Solicitud solicitud;
    private Long idCitaSolicitud;
    
    /**adicion de atribuots para citas **/
    private Subdelegacion subdelegacion;
    private String refFolioCita;
    private Integer numContadorCambioCita;
    private Long cveIdSolicitud;
    private Boolean indCitaActiva;
    
    /**atributos asociados a limitar las fechas para la consulta de citas**/
    private Integer numMaximoCitas;
    private Integer numDiasMaximaCita;
    private Date fechaMaximaCita;
    
    

    public Integer getNumDiasMaximaCita() {
		return numDiasMaximaCita;
	}

	public void setNumDiasMaximaCita(Integer numDiasMaximaCita) {
		this.numDiasMaximaCita = numDiasMaximaCita;
	}

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

	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}

	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	public String getRefFolioCita() {
		return refFolioCita;
	}

	public void setRefFolioCita(String refFolioCita) {
		this.refFolioCita = refFolioCita;
	}

	public Integer getNumMaximoCitas() {
		return numMaximoCitas;
	}

	public void setNumMaximoCitas(Integer numMaximoCitas) {
		this.numMaximoCitas = numMaximoCitas;
	}

	public Date getFechaMaximaCita() {
		return fechaMaximaCita;
	}

	public void setFechaMaximaCita(Date fechaMaximaCita) {
		this.fechaMaximaCita = fechaMaximaCita;
	}

	public Integer getNumContadorCambioCita() {
		return numContadorCambioCita;
	}

	public void setNumContadorCambioCita(Integer numContadorCambioCita) {
		this.numContadorCambioCita = numContadorCambioCita;
	}

	public Long getCveIdSolicitud() {
		return cveIdSolicitud;
	}

	public void setCveIdSolicitud(Long cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}

	public Boolean getIndCitaActiva() {
		return indCitaActiva;
	}

	public void setIndCitaActiva(Boolean indCitaActiva) {
		this.indCitaActiva = indCitaActiva;
	}
    
    

}
