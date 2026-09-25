package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

public class TramiteSolicitud extends Tramite {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5763666640460497549L;
	private Solicitud solicitud;
	private Long cveIdPatronSujetoObligado;
	private SujetoObligado sujetoObligado;
	
	public Long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(Long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}

	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}

	
	
}
