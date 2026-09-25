package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Obraoutput implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private BigDecimal idObra;
	private String numeroRegistroObra;
	private String delegacion;
	private String subDelegacion;
	private String claseObra;
	private String tipoPatron;
	private String tipoObra;
	private String fechaRegistroObra;
	private BigDecimal montoObra;
	private BigDecimal superficie;
	private String fechaInicioObra;
	private String fechaFinObra;
	private String fechaInicioContrato;
	private String fechaFinContrato;
	private String numProcedimiento;
	private BigDecimal actualizaciones;
	private String numeroRepse;
	private String objetoContrato;
	private BigDecimal numAproxTrabajadores;
	private String avisoBloqueado;
	private String fechaAvisoBloqueado;
	private BigDecimal idBloqueaAviso;
	private String numeroObraContratante;
	private String observacionObra;
	private BigDecimal montoEjercido;
	private String seqNotaria;
	public BigDecimal getIdObra() {
		return idObra;
	}
	public void setIdObra(BigDecimal idObra) {
		this.idObra = idObra;
	}
	public String getNumeroRegistroObra() {
		return numeroRegistroObra;
	}
	public void setNumeroRegistroObra(String numeroRegistroObra) {
		this.numeroRegistroObra = numeroRegistroObra;
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
	public String getClaseObra() {
		return claseObra;
	}
	public void setClaseObra(String claseObra) {
		this.claseObra = claseObra;
	}
	public String getTipoPatron() {
		return tipoPatron;
	}
	public void setTipoPatron(String tipoPatron) {
		this.tipoPatron = tipoPatron;
	}
	public String getTipoObra() {
		return tipoObra;
	}
	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}
	public String getFechaRegistroObra() {
		return fechaRegistroObra;
	}
	public void setFechaRegistroObra(String fechaRegistroObra) {
		this.fechaRegistroObra = fechaRegistroObra;
	}
	public BigDecimal getMontoObra() {
		return montoObra;
	}
	public void setMontoObra(BigDecimal montoObra) {
		this.montoObra = montoObra;
	}
	public BigDecimal getSuperficie() {
		return superficie;
	}
	public void setSuperficie(BigDecimal superficie) {
		this.superficie = superficie;
	}
	public String getFechaInicioObra() {
		return fechaInicioObra;
	}
	public void setFechaInicioObra(String fechaInicioObra) {
		this.fechaInicioObra = fechaInicioObra;
	}
	public String getFechaFinObra() {
		return fechaFinObra;
	}
	public void setFechaFinObra(String fechaFinObra) {
		this.fechaFinObra = fechaFinObra;
	}
	public String getFechaInicioContrato() {
		return fechaInicioContrato;
	}
	public void setFechaInicioContrato(String fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}
	public String getFechaFinContrato() {
		return fechaFinContrato;
	}
	public void setFechaFinContrato(String fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}
	public String getNumProcedimiento() {
		return numProcedimiento;
	}
	public void setNumProcedimiento(String numProcedimiento) {
		this.numProcedimiento = numProcedimiento;
	}
	public BigDecimal getActualizaciones() {
		return actualizaciones;
	}
	public void setActualizaciones(BigDecimal actualizaciones) {
		this.actualizaciones = actualizaciones;
	}
	public String getNumeroRepse() {
		return numeroRepse;
	}
	public void setNumeroRepse(String numeroRepse) {
		this.numeroRepse = numeroRepse;
	}
	public String getObjetoContrato() {
		return objetoContrato;
	}
	public void setObjetoContrato(String objetoContrato) {
		this.objetoContrato = objetoContrato;
	}
	public BigDecimal getNumAproxTrabajadores() {
		return numAproxTrabajadores;
	}
	public void setNumAproxTrabajadores(BigDecimal numAproxTrabajadores) {
		this.numAproxTrabajadores = numAproxTrabajadores;
	}
	public String getAvisoBloqueado() {
		return avisoBloqueado;
	}
	public void setAvisoBloqueado(String avisoBloqueado) {
		this.avisoBloqueado = avisoBloqueado;
	}
	public String getFechaAvisoBloqueado() {
		return fechaAvisoBloqueado;
	}
	public void setFechaAvisoBloqueado(String fechaAvisoBloqueado) {
		this.fechaAvisoBloqueado = fechaAvisoBloqueado;
	}
	public BigDecimal getIdBloqueaAviso() {
		return idBloqueaAviso;
	}
	public void setIdBloqueaAviso(BigDecimal idBloqueaAviso) {
		this.idBloqueaAviso = idBloqueaAviso;
	}
	public String getNumeroObraContratante() {
		return numeroObraContratante;
	}
	public void setNumeroObraContratante(String numeroObraContratante) {
		this.numeroObraContratante = numeroObraContratante;
	}
	public String getObservacionObra() {
		return observacionObra;
	}
	public void setObservacionObra(String observacionObra) {
		this.observacionObra = observacionObra;
	}
	public BigDecimal getMontoEjercido() {
		return montoEjercido;
	}
	public void setMontoEjercido(BigDecimal montoEjercido) {
		this.montoEjercido = montoEjercido;
	}
	public String getSeqNotaria() {
		return seqNotaria;
	}
	public void setSeqNotaria(String seqNotaria) {
		this.seqNotaria = seqNotaria;
	}
	@Override
	public String toString() {
		return "Obraoutput [idObra=" + idObra + ", numeroRegistroObra=" + numeroRegistroObra + ", delegacion="
				+ delegacion + ", subDelegacion=" + subDelegacion + ", claseObra=" + claseObra + ", tipoPatron="
				+ tipoPatron + ", tipoObra=" + tipoObra + ", fechaRegistroObra=" + fechaRegistroObra + ", montoObra="
				+ montoObra + ", superficie=" + superficie + ", fechaInicioObra=" + fechaInicioObra + ", fechaFinObra="
				+ fechaFinObra + ", fechaInicioContrato=" + fechaInicioContrato + ", fechaFinContrato="
				+ fechaFinContrato + ", numProcedimiento=" + numProcedimiento + ", actualizaciones=" + actualizaciones
				+ ", numeroRepse=" + numeroRepse + ", objetoContrato=" + objetoContrato + ", numAproxTrabajadores="
				+ numAproxTrabajadores + ", avisoBloqueado=" + avisoBloqueado + ", fechaAvisoBloqueado="
				+ fechaAvisoBloqueado + ", idBloqueaAviso=" + idBloqueaAviso + ", numeroObraContratante="
				+ numeroObraContratante + ", observacionObra=" + observacionObra + ", montoEjercido=" + montoEjercido
				+ ", seqNotaria=" + seqNotaria + "]";
	}
	
	
	
	
}
