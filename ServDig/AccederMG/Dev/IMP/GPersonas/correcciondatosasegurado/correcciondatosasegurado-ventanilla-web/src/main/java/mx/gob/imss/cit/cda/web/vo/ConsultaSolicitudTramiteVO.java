package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

public class ConsultaSolicitudTramiteVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String folio;
	private String fechaSolicitud;
	private String nss;
	private String subdelegacion;
	private String status;
	private String nombre;
	private String curp;
	private Boolean estatusDescarga;
	private String idTramite;
	private String motivoCancelacion;
	private Boolean estatusMot;
	private String infAdicional;
	private Boolean mismaSubdelegacion;
	
	/**
	 * identificador de la tarea activa del tr&aacute;mite no necesariamente es la del responsable.
	 */
	private Long idTarea;
	
	/**
	 * bandera para indentificar si el responsable/autorizador tienen asignada la tarea relacionada al folio
	 */
	private Boolean propietarioTarea;
	
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(String subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public Boolean getEstatusDescarga() {
		return estatusDescarga;
	}
	public void setEstatusDescarga(Boolean estatusDescarga) {
		this.estatusDescarga = estatusDescarga;
	}
	
	public Boolean getEstatusMot() {
		return estatusMot;
	}
	public void setEstatusMot(Boolean estatusMot) {
		this.estatusMot = estatusMot;
	}
	
	public String getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}
	public String getMotivoCancelacion() {
		return motivoCancelacion;
	}
	public void setMotivoCancelacion(String motivoCancelacion) {
		this.motivoCancelacion = motivoCancelacion;
	}
	public String getInfAdicional() {
		return infAdicional;
	}
	public void setInfAdicional(String infAdicional) {
		this.infAdicional = infAdicional;
	}
	
	public Boolean getPropietarioTarea() {
		return propietarioTarea;
	}
	public void setPropietarioTarea(Boolean propietarioTarea) {
		this.propietarioTarea = propietarioTarea;
	}
	public Long getIdTarea() {
		return idTarea;
	}
	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}
	public Boolean getMismaSubdelegacion() {
		return mismaSubdelegacion;
	}
	public void setMismaSubdelegacion(Boolean mismaSubdelegacion) {
		this.mismaSubdelegacion = mismaSubdelegacion;
	}
	
}