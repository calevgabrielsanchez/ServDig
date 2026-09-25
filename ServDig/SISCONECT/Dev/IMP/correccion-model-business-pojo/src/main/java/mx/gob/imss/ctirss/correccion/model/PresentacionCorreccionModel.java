package mx.gob.imss.ctirss.correccion.model;

import java.math.BigDecimal;
import java.util.List;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTramiteMensajes;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 
 * @author Luis Enrique Gonzalez Hernandez
 * 
 * Pojo que nos representa los datos de una CORP 02 (presentacion de la correccion)
 *
 */
/**
 * @author User
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PresentacionCorreccionModel extends AbstractModel {
	
	private static final long serialVersionUID = 1L;
	/**Filtros de busqueda*/
	private String registroPatronalFiltro;
	private String folioSoicitudCorreccionFiltro;
	
	/**Hiddens*/
	private Integer cveSolicitudCorreccion;
	
	 
	
	/**Nombre, denominmacion o razon social del CORP 02*/
	private String razonSocial;
	private String folioCorreccion;
	private short digitoVerificador;
	private String numeroRegistroPatronal;
	private String curp;
	private String rfc;
	private String delegacion;
	private String subDelegacion;
	
	/**Domicilio fiscal*/
	private long idDomicilioFiscal;
	private String registroPatronalFiscal;
	private String calle;
	private String numExterior;
	private String numInterior;
	private String colonia;
	private String municipio;
	private String localidad;
	private String entidadFederativa;
	private String codigoPostal;
	private String telefono;
	private String email;
	
	/**Domicilio Obra*/
	private long idDomicilioObra;
	private String calleObra;
	private String numExteriorObra;
	private String numInteriorObra;
	private String coloniaObra;
	private String municipioObra;
	private String localidadObra;
	private String entidadFederativaObra;
	private String codigoPostalObra;
	
	private String cveNroRegObra;
	
	/**Domicilio Centro de Trabajo*/
	private long idDomicilioCentroTrabajo;
	private String registroPatronalCentroTrabajo;
	private String calleCentroTrabajo;
	private String numExteriorCentroTrabajo;
	private String numInteriorCentroTrabajo;
	private String coloniaCentroTrabajo;
	private String municipioCentroTrabajo;
	private String localidadCentroTrabajo;
	private String entidadFederativaCentroTrabajo;
	private String codigoPostalCentroTrabajo;
	private String actividadCentroTrabajo;
	private String fraccionCentroTrabajo;
	private String claseCentroTrabajo;
	private String primaCentroTrabajo;
	
	/**Fechas de solicitud de correccion y tipo de correccion*/
	private String labelTipoCorreccion;
	private String labelEspontanea;
	private Integer tipoDeCorreccion;
	private Integer tipoDeCorreccionHidden;
	private String fechaAutorizacionCorreccionEspontanea;
	private String fechaAceptacionInvitacionCorreccion;
	private String fechaProrroga;
	private String fechaEjercicioInicial;
	private String fechaEjercicioFinal;
	private Integer numeroTrabajadores;
	
	/**
	 * 0 ordinaria 1 construccian;
	 */
	private Integer idTipoDeSolicitud;
	
	/**Cuotas IMSS*/
	private BigDecimal cuotasImss;
	private BigDecimal cuotasImssActualizacion;
	private BigDecimal cuotasImssRecargos;
	private BigDecimal cuotasImssTotal;
	
	/**RCV*/
	private BigDecimal rcv;
	private BigDecimal rcvActualizacion;
	private BigDecimal rcvRecargos;
	private BigDecimal rcvTotal;
	
	/**Total*/
	private BigDecimal totalCuotasRcv;
	private BigDecimal totalActualizacion;
	private BigDecimal totalRecargos;
	private BigDecimal granTotal;
	
	
	/**Documentacion que presenta*/
	private Boolean chkCombtPago;
	private Boolean chkCombtAvisosAfil;
	private Boolean chkDocumentacionCorreccion;
	private String _chkCombtPago;
	private String _chkCombtAvisosAfil;
	private String _chkDocumentacionCorreccion;
	private String observaciones;
	
	/**Firmas*/
	private String labelManifiesto;
	private String nombreYFirmaPatron;
	private String lugar;
	private String fechaFirma;
	
	private List<CrtAnexosolcorrpat> anexoSolCorrPat;
	/**Para el manejo de excepciones*/
	private String mensajeError;
	
	private String htmlCopPagadas;
	
	/**
	 * FK de subdelegacion
	 */
	private Long idSubDelegacion;
	
	private String cveDelegacion;
	private String cveSubDelegacion;
	
	/**
	 * INTERNET O SUBDELEGACION
	 */
	private String procedencia;
	/**
	 * Representa la cadena original para generar la firma electronica
	 */
	private String cadenaOriginal;
	
	/**
	 * Representa la firma electronica (<code>pkcs7</code>);
	 */
	private String firmaElectronica;
	
	private String selloIMSS;
	
	/**
	 * Representa la fecha y hora actual de la firma electronica;
	 */
	private String fechaCadenaOriginal;
	
	
	private CrcTramiteMensajes mensaje;
	
	
	private String urlAcuseFirma;
	
	public Integer getTipoDeCorreccionHidden() {
		return tipoDeCorreccionHidden;
	}
	public void setTipoDeCorreccionHidden(Integer tipoDeCorreccionHidden) {
		this.tipoDeCorreccionHidden = tipoDeCorreccionHidden;
	}
	public String getHtmlCopPagadas() {
		return htmlCopPagadas;
	}
	public void setHtmlCopPagadas(String htmlCopPagadas) {
		this.htmlCopPagadas = htmlCopPagadas;
	}
	public List<CrtAnexosolcorrpat> getAnexoSolCorrPat() {
		return anexoSolCorrPat;
	}
	public void setAnexoSolCorrPat(List<CrtAnexosolcorrpat> anexoSolCorrPat) {
		this.anexoSolCorrPat = anexoSolCorrPat;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	public String get_chkCombtPago() {
		return _chkCombtPago;
	}
	public void set_chkCombtPago(String _chkCombtPago) {
		this._chkCombtPago = _chkCombtPago;
	}
	public String get_chkCombtAvisosAfil() {
		return _chkCombtAvisosAfil;
	}
	public void set_chkCombtAvisosAfil(String _chkCombtAvisosAfil) {
		this._chkCombtAvisosAfil = _chkCombtAvisosAfil;
	}
	public String get_chkDocumentacionCorreccion() {
		return _chkDocumentacionCorreccion;
	}
	public void set_chkDocumentacionCorreccion(String _chkDocumentacionCorreccion) {
		this._chkDocumentacionCorreccion = _chkDocumentacionCorreccion;
	}
	public String getRegistroPatronalFiltro() {
		return registroPatronalFiltro;
	}
	public void setRegistroPatronalFiltro(String registroPatronalFiltro) {
		this.registroPatronalFiltro = registroPatronalFiltro;
	}
	public String getFolioSoicitudCorreccionFiltro() {
		return folioSoicitudCorreccionFiltro;
	}
	public void setFolioSoicitudCorreccionFiltro(
			String folioSoicitudCorreccionFiltro) {
		this.folioSoicitudCorreccionFiltro = folioSoicitudCorreccionFiltro;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getFolioCorreccion() {
		return folioCorreccion;
	}
	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}
	public short getDigitoVerificador() {
		return digitoVerificador;
	}
	public void setDigitoVerificador(short digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}
	public String getNumeroRegistroPatronal() {
		return numeroRegistroPatronal;
	}
	public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
		this.numeroRegistroPatronal = numeroRegistroPatronal;
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
	public String getNumInterior() {
		return numInterior;
	}
	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getEntidadFederativa() {
		return entidadFederativa;
	}
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLabelTipoCorreccion() {
		return labelTipoCorreccion;
	}
	public void setLabelTipoCorreccion(String labelTipoCorreccion) {
		this.labelTipoCorreccion = labelTipoCorreccion;
	}
	public String getLabelEspontanea() {
		return labelEspontanea;
	}
	public void setLabelEspontanea(String labelEspontanea) {
		this.labelEspontanea = labelEspontanea;
	}
	public Integer getTipoDeCorreccion() {
		return tipoDeCorreccion;
	}
	public void setTipoDeCorreccion(Integer tipoDeCorreccion) {
		this.tipoDeCorreccion = tipoDeCorreccion;
	}
	public String getFechaAutorizacionCorreccionEspontanea() {
		return fechaAutorizacionCorreccionEspontanea;
	}
	public void setFechaAutorizacionCorreccionEspontanea(
			String fechaAutorizacionCorreccionEspontanea) {
		this.fechaAutorizacionCorreccionEspontanea = fechaAutorizacionCorreccionEspontanea;
	}
	public String getFechaAceptacionInvitacionCorreccion() {
		return fechaAceptacionInvitacionCorreccion;
	}
	public void setFechaAceptacionInvitacionCorreccion(
			String fechaAceptacionInvitacionCorreccion) {
		this.fechaAceptacionInvitacionCorreccion = fechaAceptacionInvitacionCorreccion;
	}
	public String getFechaProrroga() {
		return fechaProrroga;
	}
	public void setFechaProrroga(String fechaProrroga) {
		this.fechaProrroga = fechaProrroga;
	}
	public String getFechaEjercicioInicial() {
		return fechaEjercicioInicial;
	}
	public void setFechaEjercicioInicial(String fechaEjercicioInicial) {
		this.fechaEjercicioInicial = fechaEjercicioInicial;
	}
	public String getFechaEjercicioFinal() {
		return fechaEjercicioFinal;
	}
	public void setFechaEjercicioFinal(String fechaEjercicioFinal) {
		this.fechaEjercicioFinal = fechaEjercicioFinal;
	}
	public Integer getNumeroTrabajadores() {
		return numeroTrabajadores;
	}
	public void setNumeroTrabajadores(Integer numeroTrabajadores) {
		this.numeroTrabajadores = numeroTrabajadores;
	}
	public BigDecimal getCuotasImss() {
		return cuotasImss;
	}
	public void setCuotasImss(BigDecimal cuotasImss) {
		this.cuotasImss = cuotasImss;
	}
	public BigDecimal getCuotasImssActualizacion() {
		return cuotasImssActualizacion;
	}
	public void setCuotasImssActualizacion(BigDecimal cuotasImssActualizacion) {
		this.cuotasImssActualizacion = cuotasImssActualizacion;
	}
	public BigDecimal getCuotasImssRecargos() {
		return cuotasImssRecargos;
	}
	public void setCuotasImssRecargos(BigDecimal cuotasImssRecargos) {
		this.cuotasImssRecargos = cuotasImssRecargos;
	}
	public BigDecimal getCuotasImssTotal() {
		return cuotasImssTotal;
	}
	public void setCuotasImssTotal(BigDecimal cuotasImssTotal) {
		this.cuotasImssTotal = cuotasImssTotal;
	}
	public BigDecimal getRcv() {
		return rcv;
	}
	public void setRcv(BigDecimal rcv) {
		this.rcv = rcv;
	}
	public BigDecimal getRcvActualizacion() {
		return rcvActualizacion;
	}
	public void setRcvActualizacion(BigDecimal rcvActualizacion) {
		this.rcvActualizacion = rcvActualizacion;
	}
	public BigDecimal getRcvRecargos() {
		return rcvRecargos;
	}
	public void setRcvRecargos(BigDecimal rcvRecargos) {
		this.rcvRecargos = rcvRecargos;
	}
	public BigDecimal getRcvTotal() {
		return rcvTotal;
	}
	public void setRcvTotal(BigDecimal rcvTotal) {
		this.rcvTotal = rcvTotal;
	}
	public BigDecimal getTotalCuotasRcv() {
		return totalCuotasRcv;
	}
	public void setTotalCuotasRcv(BigDecimal totalCuotasRcv) {
		this.totalCuotasRcv = totalCuotasRcv;
	}
	public BigDecimal getTotalActualizacion() {
		return totalActualizacion;
	}
	public void setTotalActualizacion(BigDecimal totalActualizacion) {
		this.totalActualizacion = totalActualizacion;
	}
	public BigDecimal getTotalRecargos() {
		return totalRecargos;
	}
	public void setTotalRecargos(BigDecimal totalRecargos) {
		this.totalRecargos = totalRecargos;
	}
	public BigDecimal getGranTotal() {
		return granTotal;
	}
	public void setGranTotal(BigDecimal granTotal) {
		this.granTotal = granTotal;
	}
	public Boolean getChkCombtPago() {
		return chkCombtPago;
	}
	public void setChkCombtPago(Boolean chkCombtPago) {
		this.chkCombtPago = chkCombtPago;
	}
	public Boolean getChkCombtAvisosAfil() {
		return chkCombtAvisosAfil;
	}
	public void setChkCombtAvisosAfil(Boolean chkCombtAvisosAfil) {
		this.chkCombtAvisosAfil = chkCombtAvisosAfil;
	}
	public Boolean getChkDocumentacionCorreccion() {
		return chkDocumentacionCorreccion;
	}
	public void setChkDocumentacionCorreccion(Boolean chkDocumentacionCorreccion) {
		this.chkDocumentacionCorreccion = chkDocumentacionCorreccion;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getLabelManifiesto() {
		return labelManifiesto;
	}
	public void setLabelManifiesto(String labelManifiesto) {
		this.labelManifiesto = labelManifiesto;
	}
	public String getNombreYFirmaPatron() {
		return nombreYFirmaPatron;
	}
	public void setNombreYFirmaPatron(String nombreYFirmaPatron) {
		this.nombreYFirmaPatron = nombreYFirmaPatron;
	}
	public String getLugar() {
		return lugar;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String getFechaFirma() {
		return fechaFirma;
	}
	public void setFechaFirma(String fechaFirma) {
		this.fechaFirma = fechaFirma;
	}
	public String getCalleObra() {
		return calleObra;
	}
	public void setCalleObra(String calleObra) {
		this.calleObra = calleObra;
	}
	public String getNumExteriorObra() {
		return numExteriorObra;
	}
	public void setNumExteriorObra(String numExteriorObra) {
		this.numExteriorObra = numExteriorObra;
	}
	public String getNumInteriorObra() {
		return numInteriorObra;
	}
	public void setNumInteriorObra(String numInteriorObra) {
		this.numInteriorObra = numInteriorObra;
	}
	public String getColoniaObra() {
		return coloniaObra;
	}
	public void setColoniaObra(String coloniaObra) {
		this.coloniaObra = coloniaObra;
	}
	public String getMunicipioObra() {
		return municipioObra;
	}
	public void setMunicipioObra(String municipioObra) {
		this.municipioObra = municipioObra;
	}
	public String getLocalidadObra() {
		return localidadObra;
	}
	public void setLocalidadObra(String localidadObra) {
		this.localidadObra = localidadObra;
	}
	public String getEntidadFederativaObra() {
		return entidadFederativaObra;
	}
	public void setEntidadFederativaObra(String entidadFederativaObra) {
		this.entidadFederativaObra = entidadFederativaObra;
	}
	public String getCodigoPostalObra() {
		return codigoPostalObra;
	}
	public void setCodigoPostalObra(String codigoPostalObra) {
		this.codigoPostalObra = codigoPostalObra;
	}
	public String getCalleCentroTrabajo() {
		return calleCentroTrabajo;
	}
	public void setCalleCentroTrabajo(String calleCentroTrabajo) {
		this.calleCentroTrabajo = calleCentroTrabajo;
	}
	public String getNumExteriorCentroTrabajo() {
		return numExteriorCentroTrabajo;
	}
	public void setNumExteriorCentroTrabajo(String numExteriorCentroTrabajo) {
		this.numExteriorCentroTrabajo = numExteriorCentroTrabajo;
	}
	public String getNumInteriorCentroTrabajo() {
		return numInteriorCentroTrabajo;
	}
	public void setNumInteriorCentroTrabajo(String numInteriorCentroTrabajo) {
		this.numInteriorCentroTrabajo = numInteriorCentroTrabajo;
	}
	public String getColoniaCentroTrabajo() {
		return coloniaCentroTrabajo;
	}
	public void setColoniaCentroTrabajo(String coloniaCentroTrabajo) {
		this.coloniaCentroTrabajo = coloniaCentroTrabajo;
	}
	public String getMunicipioCentroTrabajo() {
		return municipioCentroTrabajo;
	}
	public void setMunicipioCentroTrabajo(String municipioCentroTrabajo) {
		this.municipioCentroTrabajo = municipioCentroTrabajo;
	}
	public String getLocalidadCentroTrabajo() {
		return localidadCentroTrabajo;
	}
	public void setLocalidadCentroTrabajo(String localidadCentroTrabajo) {
		this.localidadCentroTrabajo = localidadCentroTrabajo;
	}
	public String getEntidadFederativaCentroTrabajo() {
		return entidadFederativaCentroTrabajo;
	}
	public void setEntidadFederativaCentroTrabajo(
			String entidadFederativaCentroTrabajo) {
		this.entidadFederativaCentroTrabajo = entidadFederativaCentroTrabajo;
	}
	public String getCodigoPostalCentroTrabajo() {
		return codigoPostalCentroTrabajo;
	}
	public void setCodigoPostalCentroTrabajo(String codigoPostalCentroTrabajo) {
		this.codigoPostalCentroTrabajo = codigoPostalCentroTrabajo;
	}
	public String getRegistroPatronalCentroTrabajo() {
		return registroPatronalCentroTrabajo;
	}
	public void setRegistroPatronalCentroTrabajo(
			String registroPatronalCentroTrabajo) {
		this.registroPatronalCentroTrabajo = registroPatronalCentroTrabajo;
	}
	public String getRegistroPatronalFiscal() {
		return registroPatronalFiscal;
	}
	public void setRegistroPatronalFiscal(String registroPatronalFiscal) {
		this.registroPatronalFiscal = registroPatronalFiscal;
	}
	public String getActividadCentroTrabajo() {
		return actividadCentroTrabajo;
	}
	public void setActividadCentroTrabajo(String actividadCentroTrabajo) {
		this.actividadCentroTrabajo = actividadCentroTrabajo;
	}
	public String getFraccionCentroTrabajo() {
		return fraccionCentroTrabajo;
	}
	public void setFraccionCentroTrabajo(String fraccionCentroTrabajo) {
		this.fraccionCentroTrabajo = fraccionCentroTrabajo;
	}
	public String getClaseCentroTrabajo() {
		return claseCentroTrabajo;
	}
	public void setClaseCentroTrabajo(String claseCentroTrabajo) {
		this.claseCentroTrabajo = claseCentroTrabajo;
	}
	public String getPrimaCentroTrabajo() {
		return primaCentroTrabajo;
	}
	public void setPrimaCentroTrabajo(String primaCentroTrabajo) {
		this.primaCentroTrabajo = primaCentroTrabajo;
	}
	public Integer getCveSolicitudCorreccion() {
		return cveSolicitudCorreccion;
	}
	public void setCveSolicitudCorreccion(Integer cveSolicitudCorreccion) {
		this.cveSolicitudCorreccion = cveSolicitudCorreccion;
	}
	public String getFechaCadenaOriginal() {
		return fechaCadenaOriginal;
	}
	public void setFechaCadenaOriginal(String fechaCadenaOriginal) {
		this.fechaCadenaOriginal = fechaCadenaOriginal;
	}
	public String getFirmaElectronica() {
		return firmaElectronica;
	}
	public void setFirmaElectronica(String firmaElectronica) {
		this.firmaElectronica = firmaElectronica;
	}
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}
	public String getCveSubDelegacion() {
		return cveSubDelegacion;
	}
	public void setCveSubDelegacion(String cveSubDelegacion) {
		this.cveSubDelegacion = cveSubDelegacion;
	}
	public String getCveDelegacion() {
		return cveDelegacion;
	}
	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public String getProcedencia() {
		return procedencia;
	}
	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}
	public Long getIdSubDelegacion() {
		return idSubDelegacion;
	}
	public void setIdSubDelegacion(Long idSubDelegacion) {
		this.idSubDelegacion = idSubDelegacion;
	}
	public Integer getIdTipoDeSolicitud() {
		return idTipoDeSolicitud;
	}
	public void setIdTipoDeSolicitud(Integer idTipoDeSolicitud) {
		this.idTipoDeSolicitud = idTipoDeSolicitud;
	}
	public String getCveNroRegObra() {
		return cveNroRegObra;
	}
	public void setCveNroRegObra(String cveNroRegObra) {
		this.cveNroRegObra = cveNroRegObra;
	}
	public long getIdDomicilioFiscal() {
		return idDomicilioFiscal;
	}
	public void setIdDomicilioFiscal(long idDomicilioFiscal) {
		this.idDomicilioFiscal = idDomicilioFiscal;
	}
	public long getIdDomicilioObra() {
		return idDomicilioObra;
	}
	public void setIdDomicilioObra(long idDomicilioObra) {
		this.idDomicilioObra = idDomicilioObra;
	}
	public long getIdDomicilioCentroTrabajo() {
		return idDomicilioCentroTrabajo;
	}
	public void setIdDomicilioCentroTrabajo(long idDomicilioCentroTrabajo) {
		this.idDomicilioCentroTrabajo = idDomicilioCentroTrabajo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public CrcTramiteMensajes getMensaje() {
		return mensaje;
	}
	public void setMensaje(CrcTramiteMensajes mensaje) {
		this.mensaje = mensaje;
	}
	public String getSelloIMSS() {
		return selloIMSS;
	}
	public void setSelloIMSS(String selloIMSS) {
		this.selloIMSS = selloIMSS;
	}
	public String getUrlAcuseFirma() {
		return urlAcuseFirma;
	}
	public void setUrlAcuseFirma(String urlAcuseFirma) {
		this.urlAcuseFirma = urlAcuseFirma;
	}
	

	
}
