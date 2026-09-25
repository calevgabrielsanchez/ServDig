package mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class GraficaResponse extends AbstractModel {

	private static final long serialVersionUID = -7140263183801179636L;

	private String fecha;
	private int origen;
	private int tramite;
	private int estado;
	private long total;

	// Atributos extras para dashboard
	private Date fechaConclusion;
	private int numMes;
	private String descTipoTramite;
	private String descOrigen;
	private String descEstadoSolicitud;
	private String periodo;
	private float porcentaje;
	private int porcentajeCerrado;

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getOrigen() {
		return origen;
	}

	public void setOrigen(int origen) {
		this.origen = origen;
	}

	public int getTramite() {
		return tramite;
	}

	public void setTramite(int tramite) {
		this.tramite = tramite;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Date getFechaConclusion() {
		return fechaConclusion;
	}

	public void setFechaConclusion(Date fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}

	public int getNumMes() {
		return numMes;
	}

	public void setNumMes(int numMes) {
		this.numMes = numMes;
	}

	public String getDescTipoTramite() {
		return descTipoTramite;
	}

	public void setDescTipoTramite(String descTipoTramite) {
		this.descTipoTramite = descTipoTramite;
	}

	public String getDescOrigen() {
		return descOrigen;
	}

	public void setDescOrigen(String descOrigen) {
		this.descOrigen = descOrigen;
	}

	public String getDescEstadoSolicitud() {
		return descEstadoSolicitud;
	}

	public void setDescEstadoSolicitud(String descEstadoSolicitud) {
		this.descEstadoSolicitud = descEstadoSolicitud;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public float getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}

	public int getPorcentajeCerrado() {
		return porcentajeCerrado;
	}

	public void setPorcentajeCerrado(int porcentajeCerrado) {
		this.porcentajeCerrado = porcentajeCerrado;
	}
}