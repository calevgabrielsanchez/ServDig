/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;


import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Jonathan Sanchez Montiel
 *
 */
public class FiltrosReportes extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private Date periodoInicio;
	private Date periodoFin;
    private String registroPatronal;
    private String strPeriodoInicio;
    private String strPeriodoFin;
    private String tipoPersona;
    private String tipoRegistro;
    private String estatus;
    private String delegacion;
    private String subDelegacion;
    private String claseAnterior;
    private String clasePropuesta;
    private String tipoMovimiento;
    private String esInscripcionInicial;
    private String cveIdGrupoAnalisisCe;
    private Boolean esConsulta;
    
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
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
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public String getSubDelegacion() {
		return subDelegacion;
	}
	public void setSubDelegacion(String subDelegacion) {
		this.subDelegacion = subDelegacion;
	}
	
	public String getClaseAnterior() {
		return claseAnterior;
	}

	public void setClaseAnterior(String claseAnterior) {
		this.claseAnterior = claseAnterior;
	}

	public String getClasePropuesta() {
		return clasePropuesta;
	}

	public void setClasePropuesta(String clasePropuesta) {
		this.clasePropuesta = clasePropuesta;
	}
	
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getEsInscripcionInicial() {
		return esInscripcionInicial;
	}

	public void setEsInscripcionInicial(String esInscripcionInicial) {
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
	
	public String getCveIdGrupoAnalisisCe() {
		return cveIdGrupoAnalisisCe;
	}
	public void setCveIdGrupoAnalisisCe(String cveIdGrupoAnalisisCe) {
		this.cveIdGrupoAnalisisCe = cveIdGrupoAnalisisCe;
	}
	public Boolean getEsConsulta() {
		return esConsulta;
	}
	public void setEsConsulta(Boolean esConsulta) {
		this.esConsulta = esConsulta;
	}
	@Override
	public String toString() {
		return "FiltrosReportes [claseAnterior=" + claseAnterior
				+ ", clasePropuesta=" + clasePropuesta
				+ ", cveIdGrupoAnalisisCe=" + cveIdGrupoAnalisisCe
				+ ", delegacion=" + delegacion + ", esConsulta=" + esConsulta
				+ ", esInscripcionInicial=" + esInscripcionInicial
				+ ", estatus=" + estatus + ", periodoFin=" + periodoFin
				+ ", periodoInicio=" + periodoInicio + ", registroPatronal="
				+ registroPatronal + ", strPeriodoFin=" + strPeriodoFin
				+ ", strPeriodoInicio=" + strPeriodoInicio + ", subDelegacion="
				+ subDelegacion + ", tipoMovimiento=" + tipoMovimiento
				+ ", tipoPersona=" + tipoPersona + ", tipoRegistro="
				+ tipoRegistro + "]";
	}

}
