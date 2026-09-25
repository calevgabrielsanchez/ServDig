package mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DictamenDTO extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8709705419310074809L;
	private Long cveIdPatronDictamen;
	private Long cveIdPatronSujetoObligado;
	private String registroPatronal;
	private String nombreRS;
	private String rfc;
	private Long idEjercicio;
	private String ejercicio;
	private Long idDelegacion;
	private Long idSubDelegacion;
	private String subdelegacion;
	private String delegacion;
	private String usuario;
	private Long idSolicitud;
	private Long idStatus;
	private String status;
	
	public DictamenDTO() {
		super();
	}
	
	public DictamenDTO(Long cveIdPatronDictamen, String registroPatronal,
			String nombreRS, Long idEjercicio, String ejercicio) {
		this();
		this.cveIdPatronDictamen = cveIdPatronDictamen;
		this.registroPatronal = registroPatronal;
		this.nombreRS = nombreRS;
		this.idEjercicio = idEjercicio;
		this.ejercicio = ejercicio;
	}
	
	public DictamenDTO(Long cveIdPatronDictamen, String registroPatronal,
			String nombreRS, Long idEjercicio, String ejercicio, Long idDelegacion,
			Long idSubDelegacion, String subdelegacion, String delegacion) {
		super();
		this.cveIdPatronDictamen = cveIdPatronDictamen;
		this.registroPatronal = registroPatronal;
		this.nombreRS = nombreRS;
		this.idEjercicio = idEjercicio;
		this.ejercicio = ejercicio;
		this.idDelegacion = idDelegacion;
		this.idSubDelegacion = idSubDelegacion;
		this.subdelegacion = subdelegacion;
		this.delegacion = delegacion;
	}
	
	public DictamenDTO(Long cveIdPatronDictamen, String registroPatronal,
			String nombreRS, Long idEjercicio, String ejercicio, Long idDelegacion,
			Long idSubDelegacion, String subdelegacion, String delegacion, Long cveIdPatronSujetoObligado) {
		this(cveIdPatronDictamen, registroPatronal,nombreRS,idEjercicio,ejercicio, idDelegacion,
				idSubDelegacion, subdelegacion, delegacion);
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}
	
	public DictamenDTO(Long cveIdPatronDictamen, String registroPatronal,
			String nombreRS, Long idEjercicio, String ejercicio, Long idDelegacion,
			Long idSubDelegacion, String subdelegacion, String delegacion, Long cveIdPatronSujetoObligado, String rfc) {
		this(cveIdPatronDictamen,registroPatronal,nombreRS,idEjercicio,ejercicio,idDelegacion,
				idSubDelegacion,subdelegacion, delegacion,cveIdPatronSujetoObligado);
		this.rfc = rfc;
	}
	
	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(Long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
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

	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Long getCveIdPatronDictamen() {
		return cveIdPatronDictamen;
	}
	public void setCveIdPatronDictamen(Long cveIdPatronDictamen) {
		this.cveIdPatronDictamen = cveIdPatronDictamen;
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
	public Long getIdEjercicio() {
		return idEjercicio;
	}
	public void setIdEjercicio(Long idEjercicio) {
		this.idEjercicio = idEjercicio;
	}
	public String getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
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
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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

	@Override
	public String toString() {
		return "DictamenDTO [cveIdPatronDictamen=" + cveIdPatronDictamen + ", cveIdPatronSujetoObligado="
				+ cveIdPatronSujetoObligado + ", registroPatronal=" + registroPatronal + ", nombreRS=" + nombreRS
				+ ", rfc=" + rfc + ", idEjercicio=" + idEjercicio + ", ejercicio=" + ejercicio + ", idDelegacion="
				+ idDelegacion + ", idSubDelegacion=" + idSubDelegacion + ", subdelegacion=" + subdelegacion
				+ ", delegacion=" + delegacion + ", usuario=" + usuario + ", idSolicitud=" + idSolicitud + ", idStatus="
				+ idStatus + ", status=" + status + "]";
	}
	
	
}
