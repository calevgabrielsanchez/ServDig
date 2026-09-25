/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * 
 * @author Edgar Ballesteros F.
 */
public class FiltrosAnalisisConsulta extends AbstractModel {

	/** Serial version */
	private static final long serialVersionUID = 3621815547564760610L;
	private String registroPatronal;
	private Date periodoInicio;
	private Date periodoFin;
	private String tipoMovimiento;
	private String tipoPersona;
	private String tipoRegistro;
	private String estatus;
	private String delegacion;
	private String subDelegacion;
	private String cveUsuario;
	private String strPeriodoInicio;
	private String strPeriodoFin;
	private String claseAnterior;
	private String clasePropuesta;
	private String grupoTramite;
	private String statusAnalisis;

	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
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

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
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

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
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

	public String getStatusAnalisis() {
		return statusAnalisis;
	}

	public void setStatusAnalisis(String statusAnalisis) {
		this.statusAnalisis = statusAnalisis;
	}

	public String getGrupoTramite() {
		return grupoTramite;
	}

	public void setGrupoTramite(String grupoTramite) {
		this.grupoTramite = grupoTramite;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FiltrosReportes [registroPatronal=");
		builder.append(registroPatronal);
		builder.append(", strPeriodoInicio=");
		builder.append(strPeriodoInicio);
		builder.append(", strPeriodoFin=");
		builder.append(strPeriodoFin);
		builder.append(", periodoInicio=");
		builder.append(periodoInicio);
		builder.append(", periodoFin=");
		builder.append(periodoFin);
		builder.append(", tipoMovimiento=");
		builder.append(tipoMovimiento);
		builder.append(", tipoPersona=");
		builder.append(tipoPersona);
		builder.append(", tipoRegistro=");
		builder.append(tipoRegistro);
		builder.append(", estatus=");
		builder.append(estatus);
		builder.append(", delegacion=");
		builder.append(delegacion);
		builder.append(", subDelegacion=");
		builder.append(subDelegacion);
		builder.append(", cveUsuario=");
		builder.append(cveUsuario);
		builder.append(", claseAnterior=");
		builder.append(claseAnterior);
		builder.append(", clasePropuesta=");
		builder.append(clasePropuesta);
		builder.append(", statusAnalisis=");
		builder.append(statusAnalisis);
		builder.append(", grupoTramite=");
		builder.append(grupoTramite);

		builder.append("]");
		return builder.toString();
	}

}
