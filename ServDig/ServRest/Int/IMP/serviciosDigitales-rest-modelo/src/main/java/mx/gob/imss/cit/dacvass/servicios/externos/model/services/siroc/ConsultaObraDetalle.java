package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsultaObraDetalle implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal idInformacionObra;
	private String idNumRegistroObra;
	private String delegacion;
	private String subDelegacion;
	private String rfc;
	private String nombreRazonSocial;
	private String rp;
	private String calle;	
	private BigDecimal numExterior;
	private String numExteriorALF;
	private BigDecimal numExteriorDos;
	private BigDecimal numInterior;
	private String numInteriorALF;
	private String codigoPostal;
	private String refEntidad;
	private String refMunicipio;
	private String refColonia;
	private Date fecIniObra;
	private Date fecFinObra;
	private Date fecIniContrato;
	private Date fecFinContrato;
	private BigDecimal impObra;
	private BigDecimal impEjercido;
	private BigDecimal refSupConstruccion;
	private String desTipoObra;
	private String desClasifiObra;
	private String tipoPatron;
	private String numProcedimiento;
	private String refObservacion;	
	private String refOtroObjetoContrato;
	private String estatusObra;
	private String fechaRegistro;
	private BigDecimal numActualiza;
	private Date fechaBloqueo;
	private BigDecimal numAproxTrabajadores;
	private String numRegStps;
	private String refObjcontSubesp;
	private BigDecimal cveIdDelegacion;
	private BigDecimal cveIdSubdelegacion;
	private BigDecimal cveIdTipoIncidencia;
	private String id;
	private BigDecimal ROWNUM_;
	
	public BigDecimal getROWNUM_() {
		return ROWNUM_;
	}
	public void setROWNUM_(BigDecimal rOWNUM_) {
		ROWNUM_ = rOWNUM_;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getIdInformacionObra() {
		return idInformacionObra;
	}
	public void setIdInformacionObra(BigDecimal idInformacionObra) {
		this.idInformacionObra = idInformacionObra;
	}
	public String getIdNumRegistroObra() {
		return idNumRegistroObra;
	}
	public void setIdNumRegistroObra(String idNumRegistroObra) {
		this.idNumRegistroObra = idNumRegistroObra;
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
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getRp() {
		return rp;
	}
	public void setRp(String rp) {
		this.rp = rp;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public BigDecimal getNumExterior() {
		return numExterior;
	}
	public void setNumExterior(BigDecimal numExterior) {
		this.numExterior = numExterior;
	}
	public String getNumExteriorALF() {
		return numExteriorALF;
	}
	public void setNumExteriorALF(String numExteriorALF) {
		this.numExteriorALF = numExteriorALF;
	}
	public BigDecimal getNumExteriorDos() {
		return numExteriorDos;
	}
	public void setNumExteriorDos(BigDecimal numExteriorDos) {
		this.numExteriorDos = numExteriorDos;
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
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getRefEntidad() {
		return refEntidad;
	}
	public void setRefEntidad(String refEntidad) {
		this.refEntidad = refEntidad;
	}
	public String getRefMunicipio() {
		return refMunicipio;
	}
	public void setRefMunicipio(String refMunicipio) {
		this.refMunicipio = refMunicipio;
	}
	public String getRefColonia() {
		return refColonia;
	}
	public void setRefColonia(String refColonia) {
		this.refColonia = refColonia;
	}
	public Date getFecIniObra() {
		return fecIniObra;
	}
	public void setFecIniObra(Date fecIniObra) {
		this.fecIniObra = fecIniObra;
	}
	public Date getFecFinObra() {
		return fecFinObra;
	}
	public void setFecFinObra(Date fecFinObra) {
		this.fecFinObra = fecFinObra;
	}
	public Date getFecIniContrato() {
		return fecIniContrato;
	}
	public void setFecIniContrato(Date fecIniContrato) {
		this.fecIniContrato = fecIniContrato;
	}
	public Date getFecFinContrato() {
		return fecFinContrato;
	}
	public void setFecFinContrato(Date fecFinContrato) {
		this.fecFinContrato = fecFinContrato;
	}
	public BigDecimal getImpObra() {
		return impObra;
	}
	public void setImpObra(BigDecimal impObra) {
		this.impObra = impObra;
	}
	public BigDecimal getImpEjercido() {
		return impEjercido;
	}
	public void setImpEjercido(BigDecimal impEjercido) {
		this.impEjercido = impEjercido;
	}
	public BigDecimal getRefSupConstruccion() {
		return refSupConstruccion;
	}
	public void setRefSupConstruccion(BigDecimal refSupConstruccion) {
		this.refSupConstruccion = refSupConstruccion;
	}
	public String getDesTipoObra() {
		return desTipoObra;
	}
	public void setDesTipoObra(String desTipoObra) {
		this.desTipoObra = desTipoObra;
	}
	public String getDesClasifiObra() {
		return desClasifiObra;
	}
	public void setDesClasifiObra(String desClasifiObra) {
		this.desClasifiObra = desClasifiObra;
	}
	public String getTipoPatron() {
		return tipoPatron;
	}
	public void setTipoPatron(String tipoPatron) {
		this.tipoPatron = tipoPatron;
	}
	public String getNumProcedimiento() {
		return numProcedimiento;
	}
	public void setNumProcedimiento(String numProcedimiento) {
		this.numProcedimiento = numProcedimiento;
	}
	public String getRefObservacion() {
		return refObservacion;
	}
	public void setRefObservacion(String refObservacion) {
		this.refObservacion = refObservacion;
	}
	public String getRefOtroObjetoContrato() {
		return refOtroObjetoContrato;
	}
	public void setRefOtroObjetoContrato(String refOtroObjetoContrato) {
		this.refOtroObjetoContrato = refOtroObjetoContrato;
	}
	public String getEstatusObra() {
		return estatusObra;
	}
	public void setEstatusObra(String estatusObra) {
		this.estatusObra = estatusObra;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public BigDecimal getNumActualiza() {
		return numActualiza;
	}
	public void setNumActualiza(BigDecimal numActualiza) {
		this.numActualiza = numActualiza;
	}
	public Date getFechaBloqueo() {
		return fechaBloqueo;
	}
	public void setFechaBloqueo(Date fechaBloqueo) {
		this.fechaBloqueo = fechaBloqueo;
	}
	public BigDecimal getNumAproxTrabajadores() {
		return numAproxTrabajadores;
	}
	public void setNumAproxTrabajadores(BigDecimal numAproxTrabajadores) {
		this.numAproxTrabajadores = numAproxTrabajadores;
	}
	public String getNumRegStps() {
		return numRegStps;
	}
	public void setNumRegStps(String numRegStps) {
		this.numRegStps = numRegStps;
	}
	public String getRefObjcontSubesp() {
		return refObjcontSubesp;
	}
	public void setRefObjcontSubesp(String refObjcontSubesp) {
		this.refObjcontSubesp = refObjcontSubesp;
	}
	public BigDecimal getCveIdDelegacion() {
		return cveIdDelegacion;
	}
	public void setCveIdDelegacion(BigDecimal cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}
	public BigDecimal getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}
	public void setCveIdSubdelegacion(BigDecimal cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}
	public BigDecimal getCveIdTipoIncidencia() {
		return cveIdTipoIncidencia;
	}
	public void setCveIdTipoIncidencia(BigDecimal cveIdTipoIncidencia) {
		this.cveIdTipoIncidencia = cveIdTipoIncidencia;
	}

}
