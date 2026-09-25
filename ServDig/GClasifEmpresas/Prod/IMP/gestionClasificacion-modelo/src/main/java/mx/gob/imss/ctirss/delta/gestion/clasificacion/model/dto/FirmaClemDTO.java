package mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;


public class FirmaClemDTO  extends AbstractModel {

	private static final long serialVersionUID = 1L;
	
	private Long idSolicitud;
	private Long idStatus;
	private String status;
	private Long cveIdAnalisis;
	private Long cveIdGrupoAnalisisCe;
	private Long idDelegacion;
	private Long idSubDelegacion;
	private String subdelegacion;
	private String delegacion;
	private String fecPresentacion;
	private String registroPatronal;
	private String nombreRS;
	private Long idTipoTramite;
	private String tipoTramite;
	private Long idEstadoFirma;
	private String estadoFirma;
	private String acuse;

	private Date strPeriodoInicio;
	private Date strPeriodoFin;
	private String cveSolClems;
	private String tipoClem;
	
	//filtros de periodo para buscar clem firmadas por fecha de firma
	private Date strPerIniF;
	private Date strPerFinF;
	
	//datos para guardar la respuesta de la firma
	private Long cveAnalisis;
	private String rfc;
	private String folio;
	private String firma;
	private String cadori;
	private Long idClem;
	
	//guarda solicitudes seleccionados para firma
	private String solicitudesFirma;
	
	@Override
	public String toString() {
		return "FirmaClemDTO [idSolicitud=" + idSolicitud + ", idStatus=" + idStatus + ", status=" + status
				+ ", cveIdAnalisis=" + cveIdAnalisis + ", cveIdGrupoAnalisisCe=" + cveIdGrupoAnalisisCe
				+ ", idDelegacion=" + idDelegacion + ", idSubDelegacion=" + idSubDelegacion + ", subdelegacion="
				+ subdelegacion + ", delegacion=" + delegacion + ", fecPresentacion=" + fecPresentacion
				+ ", registroPatronal=" + registroPatronal + ", nombreRS=" + nombreRS + ", idTipoTramite="
				+ idTipoTramite + ", tipoTramite=" + tipoTramite + ", idEstadoFirma=" + idEstadoFirma + ", estadoFirma="
				+ estadoFirma + ", acuse=" + acuse + ", strPeriodoInicio=" + strPeriodoInicio + ", strPeriodoFin="
				+ strPeriodoFin + ", cveSolClems=" + cveSolClems + ", tipoClem=" + tipoClem + ", strPerIniF="
				+ strPerIniF + ", strPerFinF=" + strPerFinF + ", cveAnalisis=" + cveAnalisis + ", rfc=" + rfc
				+ ", folio=" + folio + ", firma=" + firma + ", cadori=" + cadori + ", idClem=" + idClem
				+ ", solicitudesFirma=" + solicitudesFirma + "]";
	}

	public Long getCveAnalisis() {
		return cveAnalisis;
	}
	
	public void setCveAnalisis(Long cveAnalisis) {
		this.cveAnalisis = cveAnalisis;
	}
	
	public String getRfc() {
		return rfc;
	}
	
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	public String getFolio() {
		return folio;
	}
	
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getCadori() {
		return cadori;
	}

	public void setCadori(String cadori) {
		this.cadori = cadori;
	}

	public FirmaClemDTO() {
		super();
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Long getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	public void setCveIdAnalisis(Long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public Long getCveIdGrupoAnalisisCe() {
		return cveIdGrupoAnalisisCe;
	}

	public void setCveIdGrupoAnalisisCe(Long cveIdGrupoAnalisisCe) {
		this.cveIdGrupoAnalisisCe = cveIdGrupoAnalisisCe;
	}

	public Long getIdDelegacion() {
		return idDelegacion;
	}

	public void setIdDelegacion(Long idDelegacion) {
		this.idDelegacion = idDelegacion;
	}

	public Long getIdSubDelegacion() {
		return idSubDelegacion;
	}

	public void setIdSubDelegacion(Long idSubDelegacion) {
		this.idSubDelegacion = idSubDelegacion;
	}

	public String getSubdelegacion() {
		return subdelegacion;
	}

	public void setSubdelegacion(String subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	public String getDelegacion() {
		return delegacion;
	}

	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}

	public String getFecPresentacion() {
		return fecPresentacion;
	}

	public void setFecPresentacion(String fecPresentacion) {
		this.fecPresentacion = fecPresentacion;
	}

	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public String getNombreRS() {
		return nombreRS;
	}

	public void setNombreRS(String nombreRS) {
		this.nombreRS = nombreRS;
	}

	public Date getStrPeriodoInicio() {
		return strPeriodoInicio;
	}

	public void setStrPeriodoInicio(Date strPeriodoInicio) {
		this.strPeriodoInicio = strPeriodoInicio;
	}

	public Date getStrPeriodoFin() {
		return strPeriodoFin;
	}

	public void setStrPeriodoFin(Date strPeriodoFin) {
		this.strPeriodoFin = strPeriodoFin;
	}

	public Long getIdTipoTramite() {
		return idTipoTramite;
	}

	public void setIdTipoTramite(Long idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public Long getIdEstadoFirma() {
		return idEstadoFirma;
	}

	public void setIdEstadoFirma(Long idEstadoFirma) {
		this.idEstadoFirma = idEstadoFirma;
	}

	public String getEstadoFirma() {
		return estadoFirma;
	}

	public void setEstadoFirma(String estadoFirma) {
		this.estadoFirma = estadoFirma;
	}

	public String getTipoClem() {
		return tipoClem;
	}

	public void setTipoClem(String tipoClem) {
		this.tipoClem = tipoClem;
	}

	public String getAcuse() {
		return acuse;
	}

	public void setAcuse(String acuse) {
		this.acuse = acuse;
	}
	
		public String getCveSolClems() {
		return cveSolClems;
	}

	public void setCveSolClems(String cveSolClems) {
		this.cveSolClems = cveSolClems;
	}

	public Date getStrPerIniF() {
		return strPerIniF;
	}

	public void setStrPerIniF(Date strPerIniF) {
		this.strPerIniF = strPerIniF;
	}

	public Date getStrPerFinF() {
		return strPerFinF;
	}

	public void setStrPerFinF(Date strPerFinF) {
		this.strPerFinF = strPerFinF;
	}

	public Long getIdClem() {
		return idClem;
	}

	public void setIdClem(Long idClem) {
		this.idClem = idClem;
	}

	public String getSolicitudesFirma() {
		return solicitudesFirma;
	}

	public void setSolicitudesFirma(String solicitudesFirma) {
		this.solicitudesFirma = solicitudesFirma;
	}
		
}