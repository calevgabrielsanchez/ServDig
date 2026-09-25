package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ObraSirocOutput implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String numObra;
	//Nuevos parametros entrada 
	private String anioFiscal;
	private String idDelegacion;
	private String idSubdelegacion;
	private String idClaseObra;
	private String idTipoPatron;
	private String idEstatusObra;
	private String idTipoIncidencia;
	//Parametros de salida
	private String estatusObra;
	private String numeroAviso;
	private String rpRegistra;
	
	private String idTramite;
	private String idAvisObra;
	private String registroAvisoObra;
	private String rp;
	private String rfcPatron;
	private String rfcPatronDos;
	private String claseObra;
	private String tipoPatron;
	private String estatus;
	private String fechaRegistro;
	private String delegacion;
	private String subDelegacion;
	private String rfc;
	private String nombreRazonSocial;
	private String fecIniObra;
	private String fecFinObra;
	private String impObra;
	private String calle;
	private String numExterior;
	private String numExteriorALF;
	private String numExteriorDos;
	private String numInterior;
	private String numInteriorALF;
	private String codigoPostal;
	private String entidad;
	private String municipioAlcaldia;
	private String colonia;
	private String numeroRegistroObra;
	private String fechaBloqueo;
	private String rfcBloquea;
	
	private String idInformacionObra;
	private String idNumRegistroObra;
	private String refEntidad;
	private String refMunicipio;
	private String refColonia;
	private String fecIniContrato;
	private String fecFinContrato;
	private String impEjercido;
	private String refSupConstruccion;
	private String desTipoObra;
	private String desClasifiObra;
	private String numProcedimiento;
	private String refObservacion;
	private String refOtroObjetoContrato;
	private String numActualiza;
	private String numAproxTrabajadores;
	private String numRegStps;
	private String refObjcontSubesp;
	private String cveIdDelegacion;
	private String cveIdSubdelegacion;
	private String cveIdTipoIncidencia;
	private String numeroObraContratante;
	private String idregistrObra;
	private String fecRegistroAlta;
	private String idTipObra;
	
	private Date fecRegBaja;
	
	public Date getFecRegBaja() {
		return fecRegBaja;
	}
	public void setFecRegBaja(Date fecRegBaja) {
		this.fecRegBaja = fecRegBaja;
	}
	public String getNumObra() {
		return numObra;
	}
	public void setNumObra(String numObra) {
		this.numObra = numObra;
	}
	public String getAnioFiscal() {
		return anioFiscal;
	}
	public void setAnioFiscal(String anioFiscal) {
		this.anioFiscal = anioFiscal;
	}
	public String getIdDelegacion() {
		return idDelegacion;
	}
	public void setIdDelegacion(String idDelegacion) {
		this.idDelegacion = idDelegacion;
	}
	public String getIdSubdelegacion() {
		return idSubdelegacion;
	}
	public void setIdSubdelegacion(String idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}
	public String getIdClaseObra() {
		return idClaseObra;
	}
	public void setIdClaseObra(String idClaseObra) {
		this.idClaseObra = idClaseObra;
	}
	public String getIdTipoPatron() {
		return idTipoPatron;
	}
	public void setIdTipoPatron(String idTipoPatron) {
		this.idTipoPatron = idTipoPatron;
	}
	public String getIdEstatusObra() {
		return idEstatusObra;
	}
	public void setIdEstatusObra(String idEstatusObra) {
		this.idEstatusObra = idEstatusObra;
	}
	public String getIdTipoIncidencia() {
		return idTipoIncidencia;
	}
	public void setIdTipoIncidencia(String idTipoIncidencia) {
		this.idTipoIncidencia = idTipoIncidencia;
	}
	public String getEstatusObra() {
		return estatusObra;
	}
	public void setEstatusObra(String estatusObra) {
		this.estatusObra = estatusObra;
	}
	public String getNumeroAviso() {
		return numeroAviso;
	}
	public void setNumeroAviso(String numeroAviso) {
		this.numeroAviso = numeroAviso;
	}
	public String getRpRegistra() {
		return rpRegistra;
	}
	public void setRpRegistra(String rpRegistra) {
		this.rpRegistra = rpRegistra;
	}
	public String getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}
	public String getIdAvisObra() {
		return idAvisObra;
	}
	public void setIdAvisObra(String idAvisObra) {
		this.idAvisObra = idAvisObra;
	}
	public String getRegistroAvisoObra() {
		return registroAvisoObra;
	}
	public void setRegistroAvisoObra(String registroAvisoObra) {
		this.registroAvisoObra = registroAvisoObra;
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
	public String getRfcPatronDos() {
		return rfcPatronDos;
	}
	public void setRfcPatronDos(String rfcPatronDos) {
		this.rfcPatronDos = rfcPatronDos;
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
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	public String getImpObra() {
		return impObra;
	}
	public void setImpObra(String impObra) {
		this.impObra = impObra;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumExterior() {
		return numExterior;
	}
	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}
	public String getNumExteriorALF() {
		return numExteriorALF;
	}
	public void setNumExteriorALF(String numExteriorALF) {
		this.numExteriorALF = numExteriorALF;
	}
	public String getNumExteriorDos() {
		return numExteriorDos;
	}
	public void setNumExteriorDos(String numExteriorDos) {
		this.numExteriorDos = numExteriorDos;
	}
	public String getNumInterior() {
		return numInterior;
	}
	public void setNumInterior(String numInterior) {
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
	public String getNumeroRegistroObra() {
		return numeroRegistroObra;
	}
	public void setNumeroRegistroObra(String numeroRegistroObra) {
		this.numeroRegistroObra = numeroRegistroObra;
	}
	public String getFechaBloqueo() {
		return fechaBloqueo;
	}
	public void setFechaBloqueo(String fechaBloqueo) {
		this.fechaBloqueo = fechaBloqueo;
	}
	public String getRfcBloquea() {
		return rfcBloquea;
	}
	public void setRfcBloquea(String rfcBloquea) {
		this.rfcBloquea = rfcBloquea;
	}
	public String getIdInformacionObra() {
		return idInformacionObra;
	}
	public void setIdInformacionObra(String idInformacionObra) {
		this.idInformacionObra = idInformacionObra;
	}
	public String getIdNumRegistroObra() {
		return idNumRegistroObra;
	}
	public void setIdNumRegistroObra(String idNumRegistroObra) {
		this.idNumRegistroObra = idNumRegistroObra;
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
	public String getFecIniContrato() {
		return fecIniContrato;
	}
	public void setFecIniContrato(String fecIniContrato) {
		this.fecIniContrato = fecIniContrato;
	}
	public String getFecFinContrato() {
		return fecFinContrato;
	}
	public void setFecFinContrato(String fecFinContrato) {
		this.fecFinContrato = fecFinContrato;
	}
	public String getImpEjercido() {
		return impEjercido;
	}
	public void setImpEjercido(String impEjercido) {
		this.impEjercido = impEjercido;
	}
	public String getRefSupConstruccion() {
		return refSupConstruccion;
	}
	public void setRefSupConstruccion(String refSupConstruccion) {
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
	public String getNumActualiza() {
		return numActualiza;
	}
	public void setNumActualiza(String numActualiza) {
		this.numActualiza = numActualiza;
	}
	public String getNumAproxTrabajadores() {
		return numAproxTrabajadores;
	}
	public void setNumAproxTrabajadores(String numAproxTrabajadores) {
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
	public String getCveIdDelegacion() {
		return cveIdDelegacion;
	}
	public void setCveIdDelegacion(String cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}
	public String getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}
	public void setCveIdSubdelegacion(String cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}
	public String getCveIdTipoIncidencia() {
		return cveIdTipoIncidencia;
	}
	public void setCveIdTipoIncidencia(String cveIdTipoIncidencia) {
		this.cveIdTipoIncidencia = cveIdTipoIncidencia;
	}
	public String getNumeroObraContratante() {
		return numeroObraContratante;
	}
	public void setNumeroObraContratante(String numeroObraContratante) {
		this.numeroObraContratante = numeroObraContratante;
	}
	public String getIdregistrObra() {
		return idregistrObra;
	}
	public void setIdregistrObra(String idregistrObra) {
		this.idregistrObra = idregistrObra;
	}
	public String getFecRegistroAlta() {
		return fecRegistroAlta;
	}
	public void setFecRegistroAlta(String fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	public String getIdTipObra() {
		return idTipObra;
	}
	public void setIdTipObra(String idTipObra) {
		this.idTipObra = idTipObra;
	}
	
	
}
