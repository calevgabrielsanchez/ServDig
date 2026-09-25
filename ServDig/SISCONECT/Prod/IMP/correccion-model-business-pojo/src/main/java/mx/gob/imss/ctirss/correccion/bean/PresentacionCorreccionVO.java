package mx.gob.imss.ctirss.correccion.bean;

import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

public class PresentacionCorreccionVO extends AbstractModel {
	private String delegacion;
	private String subDelegacion;
	private String fechaAutorizacionEspontanea;
	private String fechaAceptacionInvitacion;
	private String fechaInicialPeriodo;
	private String fechaProrroga;
	private String fechaFinalPeriodo;
	private String fechaElaboracionPresentacionCorr;
	private String calle;
	private String colonia;
	private String telefonoDomicilio;
	private String codigoPostal;
	private String municipio;
	private String entidadFederativa;
	private String correoElectronico;
	private String numeroExt;
	private String numeroInt;
	private String localidad;
	private String nombrePatron;
	private String folioSolicitud;
	private String registroPatron;
	private String curp;
	private String rfc;
	private String digitoVerificador;
	private List<CopPagadas> copPagadas;
	private String numTrabajadoresRegularizadosTotal;
	private String tipoCorreccion;
	private String lugarElaboracion;
	private String representanteLegal;
	private String imagenRadioBtnTCEspontanea;
	private String imagenRadioBtnTCInvitacion;
	private String imagenRadioBtnDocComprobantePago;
	private String imagenRadioBtnDocComprobantePresentacionAvisos;
	private String imagenRadioBtnDocSustentaCorreccion;
	private String observaciones;
	// dOMICILIO DE LA OBRA
	private String calleObra;
	private String coloniaObra;
	private String codigoPostalObra;
	private String municipioObra;
	private String entidadFederativaObra;
	private String numeroExtObra;
	private String numeroIntObra;
	private String localidadObra;
	
	public String getFechaElaboracionPresentacionCorr() {
		return fechaElaboracionPresentacionCorr;
	}
	public void setFechaElaboracionPresentacionCorr(
			String fechaElaboracionPresentacionCorr) {
		this.fechaElaboracionPresentacionCorr = fechaElaboracionPresentacionCorr;
	}
	public String getImagenRadioBtnDocComprobantePago() {
		return imagenRadioBtnDocComprobantePago;
	}
	public void setImagenRadioBtnDocComprobantePago(
			String imagenRadioBtnDocComprobantePago) {
		this.imagenRadioBtnDocComprobantePago = imagenRadioBtnDocComprobantePago;
	}
	public String getImagenRadioBtnDocComprobantePresentacionAvisos() {
		return imagenRadioBtnDocComprobantePresentacionAvisos;
	}
	public void setImagenRadioBtnDocComprobantePresentacionAvisos(
			String imagenRadioBtnDocComprobantePresentacionAvisos) {
		this.imagenRadioBtnDocComprobantePresentacionAvisos = imagenRadioBtnDocComprobantePresentacionAvisos;
	}
	public String getImagenRadioBtnDocSustentaCorreccion() {
		return imagenRadioBtnDocSustentaCorreccion;
	}
	public void setImagenRadioBtnDocSustentaCorreccion(
			String imagenRadioBtnDocSustentaCorreccion) {
		this.imagenRadioBtnDocSustentaCorreccion = imagenRadioBtnDocSustentaCorreccion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getImagenRadioBtnTCEspontanea() {
		return imagenRadioBtnTCEspontanea;
	}
	public void setImagenRadioBtnTCEspontanea(String imagenRadioBtnTCEspontanea) {
		this.imagenRadioBtnTCEspontanea = imagenRadioBtnTCEspontanea;
	}
	public String getImagenRadioBtnTCInvitacion() {
		return imagenRadioBtnTCInvitacion;
	}
	public void setImagenRadioBtnTCInvitacion(String imagenRadioBtnTCInvitacion) {
		this.imagenRadioBtnTCInvitacion = imagenRadioBtnTCInvitacion;
	}
	public String getRepresentanteLegal() {
		return representanteLegal;
	}
	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}
	public String getLugarElaboracion() {
		return lugarElaboracion;
	}
	public void setLugarElaboracion(String lugarElaboracion) {
		this.lugarElaboracion = lugarElaboracion;
	}
	public String getFechaProrroga() {
		return fechaProrroga;
	}
	public void setFechaProrroga(String fechaProrroga) {
		this.fechaProrroga = fechaProrroga;
	}
	public String getTipoCorreccion() {
		return tipoCorreccion;
	}
	public void setTipoCorreccion(String tipoCorreccion) {
		this.tipoCorreccion = tipoCorreccion;
	}
	public List<CopPagadas> getCopPagadas() {
		return copPagadas;
	}
	public void setCopPagadas(List<CopPagadas> copPagadas) {
		this.copPagadas = copPagadas;
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
	public String getFechaAutorizacionEspontanea() {
		return fechaAutorizacionEspontanea;
	}
	public void setFechaAutorizacionEspontanea(String fechaAutorizacionEspontanea) {
		this.fechaAutorizacionEspontanea = fechaAutorizacionEspontanea;
	}
	public String getFechaAceptacionInvitacion() {
		return fechaAceptacionInvitacion;
	}
	public void setFechaAceptacionInvitacion(String fechaAceptacionInvitacion) {
		this.fechaAceptacionInvitacion = fechaAceptacionInvitacion;
	}
	public String getFechaInicialPeriodo() {
		return fechaInicialPeriodo;
	}
	public void setFechaInicialPeriodo(String fechaInicialPeriodo) {
		this.fechaInicialPeriodo = fechaInicialPeriodo;
	}
	public String getFechaFinalPeriodo() {
		return fechaFinalPeriodo;
	}
	public void setFechaFinalPeriodo(String fechaFinalPeriodo) {
		this.fechaFinalPeriodo = fechaFinalPeriodo;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getTelefonoDomicilio() {
		return telefonoDomicilio;
	}
	public void setTelefonoDomicilio(String telefonoDomicilio) {
		this.telefonoDomicilio = telefonoDomicilio;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getNumeroExt() {
		return numeroExt;
	}
	public void setNumeroExt(String numeroExt) {
		this.numeroExt = numeroExt;
	}
	public String getNumeroInt() {
		return numeroInt;
	}
	public void setNumeroInt(String numeroInt) {
		this.numeroInt = numeroInt;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getNombrePatron() {
		return nombrePatron;
	}
	public void setNombrePatron(String nombrePatron) {
		this.nombrePatron = nombrePatron;
	}
	public String getFolioSolicitud() {
		return folioSolicitud;
	}
	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}
	public String getRegistroPatron() {
		return registroPatron;
	}
	public void setRegistroPatron(String registroPatron) {
		this.registroPatron = registroPatron;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getDigitoVerificador() {
		return digitoVerificador;
	}
	public void setDigitoVerificador(String digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}
	public String getNumTrabajadoresRegularizadosTotal() {
		return numTrabajadoresRegularizadosTotal;
	}
	public void setNumTrabajadoresRegularizadosTotal(
			String numTrabajadoresRegularizadosTotal) {
		this.numTrabajadoresRegularizadosTotal = numTrabajadoresRegularizadosTotal;
	}
	/**
	 * @return the calleObra
	 */
	public String getCalleObra() {
		return calleObra;
	}
	/**
	 * @param calleObra the calleObra to set
	 */
	public void setCalleObra(String calleObra) {
		this.calleObra = calleObra;
	}
	/**
	 * @return the coloniaObra
	 */
	public String getColoniaObra() {
		return coloniaObra;
	}
	/**
	 * @param coloniaObra the coloniaObra to set
	 */
	public void setColoniaObra(String coloniaObra) {
		this.coloniaObra = coloniaObra;
	}
	/**
	 * @return the codigoPostalObra
	 */
	public String getCodigoPostalObra() {
		return codigoPostalObra;
	}
	/**
	 * @param codigoPostalObra the codigoPostalObra to set
	 */
	public void setCodigoPostalObra(String codigoPostalObra) {
		this.codigoPostalObra = codigoPostalObra;
	}
	/**
	 * @return the municipioObra
	 */
	public String getMunicipioObra() {
		return municipioObra;
	}
	/**
	 * @param municipioObra the municipioObra to set
	 */
	public void setMunicipioObra(String municipioObra) {
		this.municipioObra = municipioObra;
	}
	/**
	 * @return the entidadFederativaObra
	 */
	public String getEntidadFederativaObra() {
		return entidadFederativaObra;
	}
	/**
	 * @param entidadFederativaObra the entidadFederativaObra to set
	 */
	public void setEntidadFederativaObra(String entidadFederativaObra) {
		this.entidadFederativaObra = entidadFederativaObra;
	}
	/**
	 * @return the numeroExtObra
	 */
	public String getNumeroExtObra() {
		return numeroExtObra;
	}
	/**
	 * @param numeroExtObra the numeroExtObra to set
	 */
	public void setNumeroExtObra(String numeroExtObra) {
		this.numeroExtObra = numeroExtObra;
	}
	/**
	 * @return the numeroIntObra
	 */
	public String getNumeroIntObra() {
		return numeroIntObra;
	}
	/**
	 * @param numeroIntObra the numeroIntObra to set
	 */
	public void setNumeroIntObra(String numeroIntObra) {
		this.numeroIntObra = numeroIntObra;
	}
	/**
	 * @return the localidadObra
	 */
	public String getLocalidadObra() {
		return localidadObra;
	}
	/**
	 * @param localidadObra the localidadObra to set
	 */
	public void setLocalidadObra(String localidadObra) {
		this.localidadObra = localidadObra;
	}
	
}