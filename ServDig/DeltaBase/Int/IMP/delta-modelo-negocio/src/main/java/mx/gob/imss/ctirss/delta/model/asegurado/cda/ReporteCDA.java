package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ReporteCDA extends AbstractModel{

	private static final long serialVersionUID = 2545572909909173977L;
	
	private String idTramite;
	private String idSolicitud;
	private String delegacion;
	private String subdelegacion;
	private String autorizo;
	private String responsable;
	private String origen;
	private String folio;
	private String curp;
	private String nssInvolucrado;
	private String tipoTramite;
	private String estado;
	private String curpBeneficiario;
	private String vencido;
	private String fechaSolicitud;
	private String fechaFinalizacion;
	private String fechaActualizacion;
	private String reasignado;
	
	
	public String getReasignado() {
		return reasignado;
	}
	public void setReasignado(String reasignado) {
		this.reasignado = reasignado;
	}
	public String getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}
	public String getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public String getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(String subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	public String getAutorizo() {
		return autorizo;
	}
	public void setAutorizo(String autorizo) {
		this.autorizo = autorizo;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getNssInvolucrado() {
		return nssInvolucrado;
	}
	public void setNssInvolucrado(String nssInvolucrado) {
		this.nssInvolucrado = nssInvolucrado;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCurpBeneficiario() {
		return curpBeneficiario;
	}
	public void setCurpBeneficiario(String curpBeneficiario) {
		this.curpBeneficiario = curpBeneficiario;
	}
	public String getVencido() {
		return vencido;
	}
	public void setVencido(String vencido) {
		this.vencido = vencido;
	}
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getFechaFinalizacion() {
		return fechaFinalizacion;
	}
	public void setFechaFinalizacion(String fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}
	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
}
