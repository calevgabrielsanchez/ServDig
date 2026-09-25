package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;

public class TramiteInfo extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private Long tramiteInfoId;
	private String urlWizard;
	private String descripcionTramite;
	private String instrucciones;
	private OrigenSolicitud origen;
	private TipoTramite tipoTramite;
	

	public Long getTramiteInfoId() {
		return tramiteInfoId;
	}

	public void setTramiteInfoId(Long tramiteInfoId) {
		this.tramiteInfoId = tramiteInfoId;
	}

	public String getUrlWizard() {
		return urlWizard;
	}

	public void setUrlWizard(String urlWizard) {
		this.urlWizard = urlWizard;
	}

	public String getDescripcionTramite() {
		return descripcionTramite;
	}

	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public OrigenSolicitud getOrigen() {
		return origen;
	}

	public void setOrigen(OrigenSolicitud origen) {
		this.origen = origen;
	}

	public TipoTramite getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(TipoTramite tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

}
