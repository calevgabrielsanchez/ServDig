package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.math.BigDecimal;


public class ObradetalleOutput implements Serializable {
		
	private static final long serialVersionUID = 1L;
	private BigDecimal idObra;
	private String numeroRegistroObra;
	private String delegacion;
	private String subDelegacion;
	private String claseObra;
	private String tipoPatron;
	private String tipoObra;
	private String   fechaRegistroObra;
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
	private BigDecimal    idBloqueaAviso;
	private String numeroObraContratante;
	private String observacionObra;
	private BigDecimal montoEjercido;
	private String seqNotaria;
	private String calle;
	private BigDecimal numExt;
	private String numExtALF;
	private BigDecimal numExtDos;
	private BigDecimal numInterior;
	private String numInteriorALF;
	private String colonia;
	private String municipioAlcaldia;
	private String codigoPostal;
	private String entidadFederativa;
	private String observacionUbicacion;
	private String rp;
	private String razonSocial;
	private String rfc;
	private String fechaRegistroPatron;
	private String estatusObra;
	private String fechaRegistroInc;
	private BigDecimal   idTipoIncidencia;
	private String desTipoIncidencia;
	private String fechaIncidencia;
	private String fechaRegistroActualizado;
	private String fecSuspension;
	private String fecReanudacion;
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
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public BigDecimal getNumExt() {
		return numExt;
	}
	public void setNumExt(BigDecimal numExt) {
		this.numExt = numExt;
	}
	public String getNumExtALF() {
		return numExtALF;
	}
	public void setNumExtALF(String numExtALF) {
		this.numExtALF = numExtALF;
	}
	public BigDecimal getNumExtDos() {
		return numExtDos;
	}
	public void setNumExtDos(BigDecimal numExtDos) {
		this.numExtDos = numExtDos;
	}
	public BigDecimal getNumInterior() {
		return numInterior;
	}
	public void setNumInterior(BigDecimal numInterior) {
		this.numInterior = numInterior;
	}
	public String getNumInteriorALF() {
		return numInteriorALF;
	}
	public void setNumInteriorALF(String numInteriorALF) {
		this.numInteriorALF = numInteriorALF;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getMunicipioAlcaldia() {
		return municipioAlcaldia;
	}
	public void setMunicipioAlcaldia(String municipioAlcaldia) {
		this.municipioAlcaldia = municipioAlcaldia;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public String getObservacionUbicacion() {
		return observacionUbicacion;
	}
	public void setObservacionUbicacion(String observacionUbicacion) {
		this.observacionUbicacion = observacionUbicacion;
	}
	public String getRp() {
		return rp;
	}
	public void setRp(String rp) {
		this.rp = rp;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getFechaRegistroPatron() {
		return fechaRegistroPatron;
	}
	public void setFechaRegistroPatron(String fechaRegistroPatron) {
		this.fechaRegistroPatron = fechaRegistroPatron;
	}
	public String getEstatusObra() {
		return estatusObra;
	}
	public void setEstatusObra(String estatusObra) {
		this.estatusObra = estatusObra;
	}
	public BigDecimal getIdTipoIncidencia() {
		return idTipoIncidencia;
	}
	public void setIdTipoIncidencia(BigDecimal idTipoIncidencia) {
		this.idTipoIncidencia = idTipoIncidencia;
	}
	public String getDesTipoIncidencia() {
		return desTipoIncidencia;
	}
	public void setDesTipoIncidencia(String desTipoIncidencia) {
		this.desTipoIncidencia = desTipoIncidencia;
	}
	public String getFechaIncidencia() {
		return fechaIncidencia;
	}
	public void setFechaIncidencia(String fechaIncidencia) {
		this.fechaIncidencia = fechaIncidencia;
	}
	public String getFechaRegistroActualizado() {
		return fechaRegistroActualizado;
	}
	public void setFechaRegistroActualizado(String fechaRegistroActualizado) {
		this.fechaRegistroActualizado = fechaRegistroActualizado;
	}
	public String getFecSuspension() {
		return fecSuspension;
	}
	public void setFecSuspension(String fecSuspension) {
		this.fecSuspension = fecSuspension;
	}
	public String getFecReanudacion() {
		return fecReanudacion;
	}
	public void setFecReanudacion(String fecReanudacion) {
		this.fecReanudacion = fecReanudacion;
	}
	@Override
	public String toString() {
		return "ObradetalleOutput [idObra=" + idObra + ", numeroRegistroObra=" + numeroRegistroObra + ", delegacion="
				+ delegacion + ", subDelegacion=" + subDelegacion + ", claseObra=" + claseObra + ", tipoPatron="
				+ tipoPatron + ", tipoObra=" + tipoObra + ", fechaRegistroObra=" + fechaRegistroObra + ", montoObra="
				+ montoObra + ", superficie=" + superficie + ", fechaInicioObra=" + fechaInicioObra + ", fechaFinObra="
				+ fechaFinObra + ", fechaInicioContrato=" + fechaInicioContrato + ", fechaFinContrato="
				+ fechaFinContrato + ", numProcedimiento=" + numProcedimiento + ", actualizaciones=" + actualizaciones
				+ ", numeroRepse=" + numeroRepse + ", objetoContrato=" + objetoContrato + ", numAproxTrabajadores="
				+ numAproxTrabajadores + ", avisoBloqueado=" + avisoBloqueado + ", fechaAvisoBloqueado="
				+ fechaAvisoBloqueado + ", idBloqueaAviso=" + idBloqueaAviso + ", numeroObraContratante="
				+ numeroObraContratante + ", observacionObra=" + observacionObra + ", montoEjercido=" + montoEjercido
				+ ", seqNotaria=" + seqNotaria + ", calle=" + calle + ", numExt=" + numExt + ", numExtALF=" + numExtALF
				+ ", numExtDos=" + numExtDos + ", numInterior=" + numInterior + ", numInteriorALF=" + numInteriorALF
				+ ", colonia=" + colonia + ", municipioAlcaldia=" + municipioAlcaldia + ", codigoPostal=" + codigoPostal
				+ ", entidadFederativa=" + entidadFederativa + ", observacionUbicacion=" + observacionUbicacion
				+ ", rp=" + rp + ", razonSocial=" + razonSocial + ", rfc=" + rfc + ", fechaRegistroPatron="
				+ fechaRegistroPatron + ", estatusObra=" + estatusObra + ", idTipoIncidencia=" + idTipoIncidencia
				+ ", desTipoIncidencia=" + desTipoIncidencia + ", fechaIncidencia=" + fechaIncidencia
				+ ", fechaRegistroActualizado=" + fechaRegistroActualizado + ", fecSuspension=" + fecSuspension
				+ ", fecReanudacion=" + fecReanudacion + "]";
	}
	public String getFechaRegistroInc() {
		return fechaRegistroInc;
	}
	public void setFechaRegistroInc(String fechaRegistroInc) {
		this.fechaRegistroInc = fechaRegistroInc;
	}
	
	
}
