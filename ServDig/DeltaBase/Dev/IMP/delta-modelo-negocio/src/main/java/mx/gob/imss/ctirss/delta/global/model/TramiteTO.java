package mx.gob.imss.ctirss.delta.global.model;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;

public class TramiteTO extends AbstractModel {
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 393220088120451366L;
	private Long tramiteId;
	private TipoTramiteTO tipoTramite;
    private EstadoTramite estadoTramite;
    private RazonResultado razonResultado;
    private Date fechaTramite;
    private String cveMunicipioImss;//Presente en Tramite de Alta
    private String cveModalidad; //Presente en Tramite de Alta
    private String detalleTramiteXml;
    
    public Long getTramiteId() {
		return tramiteId;
	}
	public void setTramiteId(Long tramiteId) {
		this.tramiteId = tramiteId;
	}
	public TipoTramiteTO getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(TipoTramiteTO tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public EstadoTramite getEstadoTramite() {
		return estadoTramite;
	}
	public void setEstadoTramite(EstadoTramite estadoTramite) {
		this.estadoTramite = estadoTramite;
	}
	public RazonResultado getRazonResultado() {
		return razonResultado;
	}
	public void setRazonResultado(RazonResultado razonResultado) {
		this.razonResultado = razonResultado;
	}
	public Date getFechaTramite() {
		return fechaTramite;
	}
	public void setFechaTramite(Date fechaTramite) {
		this.fechaTramite = fechaTramite;
	}
	public String getCveMunicipioImss() {
		return cveMunicipioImss;
	}
	public void setCveMunicipioImss(String cveMunicipioImss) {
		this.cveMunicipioImss = cveMunicipioImss;
	}
	public String getCveModalidad() {
		return cveModalidad;
	}
	public void setCveModalidad(String cveModalidad) {
		this.cveModalidad = cveModalidad;
	}
	public String getDetalleTramiteXml() {
		return detalleTramiteXml;
	}
	public void setDetalleTramiteXml(String detalleTramiteXml) {
		this.detalleTramiteXml = detalleTramiteXml;
	}
	
}
