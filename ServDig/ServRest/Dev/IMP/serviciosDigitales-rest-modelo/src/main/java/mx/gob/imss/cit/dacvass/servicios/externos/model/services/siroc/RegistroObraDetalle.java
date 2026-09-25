package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistroObraDetalle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal idTramite;
	private BigDecimal idAvisObra;
	private String delegacion;
	private String subDelegacion;
	private String registroAvisoObra;
	private String tipoPatron;
	private String rfc;
	private String nombreRazonSocial;
	private String rp;
	private String rfcPatron;
	private String fecIniObra;
	private String fecFinObra;	
	private BigDecimal impObra;
	private String calle;
	private BigDecimal numExterior;
	private String numExteriorALF;	
	private BigDecimal numExteriorDos;
	private BigDecimal numInterior;	
	private String numInteriorALF;	
	private String codigoPostal;
	private String entidad;
	private String municipioAlcaldia;
	private String colonia;
	private String estatus;	
	private String idregistrObra;	
	private Date fechaBloqueo;
	private String rfcBloquea;	
	private Date fecRegistroAlta;	
	private BigDecimal idTipObra;
	private BigDecimal ROWNUM_;
	public BigDecimal getROWNUM_() {
		return ROWNUM_;
	}
	public void setROWNUM_(BigDecimal rOWNUM_) {
		ROWNUM_ = rOWNUM_;
	}
	public String getClaseObra() {
		return claseObra;
	}
	public void setClaseObra(String claseObra) {
		this.claseObra = claseObra;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getNumeroRegistroObra() {
		return numeroRegistroObra;
	}
	public void setNumeroRegistroObra(String numeroRegistroObra) {
		this.numeroRegistroObra = numeroRegistroObra;
	}
	private String claseObra;	
	private String fechaRegistro;	
	private String numeroRegistroObra;	


	public BigDecimal getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(BigDecimal idTramite) {
		this.idTramite = idTramite;
	}
	public BigDecimal getIdAvisObra() {
		return idAvisObra;
	}
	public void setIdAvisObra(BigDecimal idAvisObra) {
		this.idAvisObra = idAvisObra;
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
	public String getRegistroAvisoObra() {
		return registroAvisoObra;
	}
	public void setRegistroAvisoObra(String registroAvisoObra) {
		this.registroAvisoObra = registroAvisoObra;
	}
	public String getTipoPatron() {
		return tipoPatron;
	}
	public void setTipoPatron(String tipoPatron) {
		this.tipoPatron = tipoPatron;
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
	public String getRfcPatron() {
		return rfcPatron;
	}
	public void setRfcPatron(String rfcPatron) {
		this.rfcPatron = rfcPatron;
	}
	public String getFecIniObra() {
		return fecIniObra;
	}
	public void setFecIniObra(String fecIniObra) {
		this.fecIniObra = fecIniObra;
	}
	public String getFecFinObra() {
		return fecFinObra;
	}
	public void setFecFinObra(String fecFinObra) {
		this.fecFinObra = fecFinObra;
	}
	public BigDecimal getImpObra() {
		return impObra;
	}
	public void setImpObra(BigDecimal impObra) {
		this.impObra = impObra;
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
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public String getMunicipioAlcaldia() {
		return municipioAlcaldia;
	}
	public void setMunicipioAlcaldia(String municipioAlcaldia) {
		this.municipioAlcaldia = municipioAlcaldia;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getIdregistrObra() {
		return idregistrObra;
	}
	public void setIdregistrObra(String idregistrObra) {
		this.idregistrObra = idregistrObra;
	}
	public Date getFechaBloqueo() {
		return fechaBloqueo;
	}
	public void setFechaBloqueo(Date fechaBloqueo) {
		this.fechaBloqueo = fechaBloqueo;
	}
	public String getRfcBloquea() {
		return rfcBloquea;
	}
	public void setRfcBloquea(String rfcBloquea) {
		this.rfcBloquea = rfcBloquea;
	}
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	public BigDecimal getIdTipObra() {
		return idTipObra;
	}
	public void setIdTipObra(BigDecimal idTipObra) {
		this.idTipObra = idTipObra;
	}


}
