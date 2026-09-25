package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class SolicitudConcluida  extends AbstractModel {

	private static final long serialVersionUID = 1L;
	private String registroPatronal;
	private String razonSocial;
	private String nombreComercial;
	private String nombre;
	private Date fechaPresentacion;
	
	private String estatusAnalisis;
	private int cveIdEstatusAnalisis;
	
	private int idDelegacion;
	private String delegacion;
	private int idSubdelegacion;
	private String subdelegacion;
	
	private int idTipoPersona;
	private String tipoPersona;
	private String tipoRegistro;
	
	private int idTipoCausa;
	private String tipoCausa;
	
	private int idEstatusMovimiento;
	private String estatusMovimiento;
	/*Clave de la solicitud*/
	private int cveIdSolicitud;
	/*Clave del analisis*/
	private int cveIdAnalisis;

	private String cveUsuarioSso;
	
//	private String cveUsuario;
	
//	private String nomUsuarioSistema;
	
	public SolicitudConcluida()
	{
	}

	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public int getIdTipoPersona() {
		return idTipoPersona;
	}

	public void setIdTipoPersona(int idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public int getIdTipoCausa() {
		return idTipoCausa;
	}

	public void setIdTipoCausa(int idTipoCausa) {
		this.idTipoCausa = idTipoCausa;
	}

	public String getTipoCausa() {
		return tipoCausa;
	}

	public void setTipoCausa(String tipoCausa) {
		this.tipoCausa = tipoCausa;
	}

	public int getIdEstatusMovimiento() {
		return idEstatusMovimiento;
	}

	public void setIdEstatusMovimiento(int idEstatusMovimiento) {
		this.idEstatusMovimiento = idEstatusMovimiento;
	}

	public String getEstatusMovimiento() {
		return estatusMovimiento;
	}

	public void setEstatusMovimiento(String estatusMovimiento) {
		this.estatusMovimiento = estatusMovimiento;
	}

	public int getIdDelegacion() {
		return idDelegacion;
	}

	public void setIdDelegacion(int idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	public String getDelegacion() {
		return delegacion;
	}

	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}

	public int getIdSubdelegacion() {
		return idSubdelegacion;
	}

	public void setIdSubdelegacion(int idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}

	public String getSubdelegacion() {
		return subdelegacion;
	}

	public void setSubdelegacion(String subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getEstatusAnalisis() {
		return estatusAnalisis;
	}

	public void setEstatusAnalisis(String estatusAnalisis) {
		this.estatusAnalisis = estatusAnalisis;
	}

	public int getCveIdEstatusAnalisis() {
		return cveIdEstatusAnalisis;
	}

	public void setCveIdEstatusAnalisis(int cveIdEstatusAnalisis) {
		this.cveIdEstatusAnalisis = cveIdEstatusAnalisis;
	}

	public int getCveIdSolicitud() {
		return cveIdSolicitud;
	}

	public void setCveIdSolicitud(int cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}

	public int getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	public void setCveIdAnalisis(int cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}
	
//	public String getCveUsuario() {
//		return cveUsuario;
//	}
//
//	public void setCveUsuario(String cveUsuario) {
//		this.cveUsuario = cveUsuario;
//	}
//
//	public String getNomUsuarioSistema() {
//		return nomUsuarioSistema;
//	}
//
//	public void setNomUsuarioSistema(String nomUsuarioSistema) {
//		this.nomUsuarioSistema = nomUsuarioSistema;
//	}
	
	public String getNombreComercial() {
		return nombreComercial;
	}

	public String getCveUsuarioSso() {
		return cveUsuarioSso;
	}

	public void setCveUsuarioSso(String cveUsuarioSso) {
		this.cveUsuarioSso = cveUsuarioSso;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "SolicitudConcluida [cveIdAnalisis=" + cveIdAnalisis
				+ ", cveIdEstatusAnalisis=" + cveIdEstatusAnalisis
				+ ", cveIdSolicitud=" + cveIdSolicitud + ", cveUsuarioSso="
				+ cveUsuarioSso + ", delegacion=" + delegacion
				+ ", estatusAnalisis=" + estatusAnalisis
				+ ", estatusMovimiento=" + estatusMovimiento
				+ ", fechaPresentacion=" + fechaPresentacion + ", idDelegacion="
				+ idDelegacion + ", idEstatusMovimiento=" + idEstatusMovimiento
				+ ", idSubdelegacion=" + idSubdelegacion + ", idTipoCausa="
				+ idTipoCausa + ", idTipoPersona=" + idTipoPersona
				+ ", nombre="
				+ nombre + ", nombreComercial=" + nombreComercial
				+ ", razonSocial=" + razonSocial + ", registroPatronal="
				+ registroPatronal + ", subdelegacion=" + subdelegacion
				+ ", tipoCausa=" + tipoCausa + ", tipoPersona=" + tipoPersona
				+ ", tipoRegistro=" + tipoRegistro + "]";
	}

}
