package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio;

import java.io.Serializable;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;

public class EstadisticasAsignacion implements Serializable {
	private static final long serialVersionUID = 4276417987412689848L;
	private OrigenSolicitud origenSolicitud;
	private TipoTramite tipoTramite;
	private Long total;

	public EstadisticasAsignacion(Long cveIdOrigenSolicitud,
			Long cveTipoTramite, Long total) {
		OrigenSolicitud origenSol = new OrigenSolicitud();
		origenSol.setIdTipoSolicitud(cveIdOrigenSolicitud);
		this.origenSolicitud = origenSol;

		TipoTramite tipoTram = new TipoTramite();
		tipoTram.setIdTipoTramite(cveTipoTramite);
		this.tipoTramite = tipoTram;

		this.total = total;
	}

	public OrigenSolicitud getOrigenSolicitud() {
		return origenSolicitud;
	}

	public void setOrigenSolicitud(OrigenSolicitud origenSolicitud) {
		this.origenSolicitud = origenSolicitud;
	}

	public TipoTramite getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(TipoTramite tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
