package mx.gob.imss.ctirss.delta.utilities.planificadorDelta.model.negocio;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;

public class FiltroEstadisticaAsignacion {
	private TipoSolicitudEnum tipoSolicitud;
	private EstadoSolicitudEnum estadoSolicitud;
	private List<OrigenSolicitudEnum> listOrigenSolicitud;
	private List<TipoTramiteEnum> listTipoTramite;
	private Boolean indicadorBusquedaRango = false;
	private Date fechaSolicitudInicial;
	private Date fechaSolicitudFinal;

	public List<TipoTramiteEnum> getListTipoTramite() {
		return listTipoTramite;
	}

	public void setListTipoTramite(List<TipoTramiteEnum> listTipoTramite) {
		this.listTipoTramite = listTipoTramite;
	}

	public EstadoSolicitudEnum getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(EstadoSolicitudEnum estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public TipoSolicitudEnum getTipoSolicitud() {
		return tipoSolicitud;
	}

	public void setTipoSolicitud(TipoSolicitudEnum tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public List<OrigenSolicitudEnum> getListOrigenSolicitud() {
		return listOrigenSolicitud;
	}

	public void setListOrigenSolicitud(
			List<OrigenSolicitudEnum> listOrigenSolicitud) {
		this.listOrigenSolicitud = listOrigenSolicitud;
	}

	public Boolean getIndicadorBusquedaRango() {
		return indicadorBusquedaRango;
	}

	public void setIndicadorBusquedaRango(Boolean indicadorBusquedaRango) {
		this.indicadorBusquedaRango = indicadorBusquedaRango;
	}

	public Date getFechaSolicitudInicial() {
		return fechaSolicitudInicial;
	}

	public void setFechaSolicitudInicial(Date fechaSolicitudInicial) {
		this.fechaSolicitudInicial = fechaSolicitudInicial;
	}

	public Date getFechaSolicitudFinal() {
		return fechaSolicitudFinal;
	}

	public void setFechaSolicitudFinal(Date fechaSolicitudFinal) {
		this.fechaSolicitudFinal = fechaSolicitudFinal;
	}
}
