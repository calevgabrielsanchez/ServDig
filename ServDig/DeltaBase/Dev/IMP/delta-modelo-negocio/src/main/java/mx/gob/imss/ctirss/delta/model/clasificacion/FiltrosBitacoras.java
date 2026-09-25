package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class FiltrosBitacoras  extends AbstractModel{

	private static final long serialVersionUID = 6985112116102865841L;

	private Date periodoInicio;
	
	private Date periodoFin;
	
	private String usuario;
	
	private String strPeriodoInicio;
	
	private String strPeriodoFin;
	
	private Long delegacion;
	
	private Long subDelegacion;
	
	private Long cveIdAnalisis;

	private Boolean esInscripcionInicial;
	
	private Long cveIdGrupoAnalisisCe;
	
	private Boolean esConsulta;
	
	private Boolean consultaDictamen;
	
	private Long periodoDictaminado;
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getStrPeriodoInicio() {
		return strPeriodoInicio;
	}

	public void setStrPeriodoInicio(String strPeriodoInicio) {
		this.strPeriodoInicio = strPeriodoInicio;
	}

	public String getStrPeriodoFin() {
		return strPeriodoFin;
	}

	public void setStrPeriodoFin(String strPeriodoFin) {
		this.strPeriodoFin = strPeriodoFin;
	}

	public Long getDelegacion() {
		return delegacion;
	}

	public void setDelegacion(Long delegacion) {
		this.delegacion = delegacion;
	}

	public Long getSubDelegacion() {
		return subDelegacion;
	}

	public void setSubDelegacion(Long subDelegacion) {
		this.subDelegacion = subDelegacion;
	}
	
	public Long getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	public void setCveIdAnalisis(Long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public Boolean getEsInscripcionInicial() {
		return esInscripcionInicial;
	}

	public void setEsInscripcionInicial(Boolean esInscripcionInicial) {
		this.esInscripcionInicial = esInscripcionInicial;
	}
	
	public Date getPeriodoInicio() {
		return periodoInicio;
	}

	public void setPeriodoInicio(Date periodoInicio) {
		this.periodoInicio = periodoInicio;
	}

	public Date getPeriodoFin() {
		return periodoFin;
	}

	public void setPeriodoFin(Date periodoFin) {
		this.periodoFin = periodoFin;
	}

	public Long getCveIdGrupoAnalisisCe() {
		return cveIdGrupoAnalisisCe;
	}

	public void setCveIdGrupoAnalisisCe(Long cveIdGrupoAnalisisCe) {
		this.cveIdGrupoAnalisisCe = cveIdGrupoAnalisisCe;
	}

	public Boolean getEsConsulta() {
		return esConsulta;
	}

	public void setEsConsulta(Boolean esConsulta) {
		this.esConsulta = esConsulta;
	}

	public Boolean getConsultaDictamen() {
		return consultaDictamen;
	}

	public void setConsultaDictamen(Boolean consultaDictamen) {
		this.consultaDictamen = consultaDictamen;
	}

	public Long getPeriodoDictaminado() {
		return periodoDictaminado;
	}

	public void setPeriodoDictaminado(Long periodoDictaminado) {
		this.periodoDictaminado = periodoDictaminado;
	}

	@Override
	public String toString() {
		return "FiltrosBitacoras [cveIdAnalisis=" + cveIdAnalisis
				+ ", cveIdGrupoAnalisisCe=" + cveIdGrupoAnalisisCe
				+ ", delegacion=" + delegacion
				+ ", esConsulta=" + esConsulta + ", esInscripcionInicial="
				+ esInscripcionInicial + ", periodoFin=" + periodoFin
				+ ", periodoInicio=" + periodoInicio + ", strPeriodoFin="
				+ strPeriodoFin + ", strPeriodoInicio=" + strPeriodoInicio
				+ ", subDelegacion=" + subDelegacion + ", usuario=" + usuario + ", consultaDictamen="+consultaDictamen + ", periodoDictaminado="+periodoDictaminado
				+ "]";
	}

}
