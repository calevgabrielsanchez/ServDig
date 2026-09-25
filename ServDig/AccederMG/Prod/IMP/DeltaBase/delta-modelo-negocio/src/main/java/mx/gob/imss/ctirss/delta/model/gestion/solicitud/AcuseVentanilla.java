package mx.gob.imss.ctirss.delta.model.gestion.solicitud;

import java.io.Serializable;


public class AcuseVentanilla implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean esFisica;
	private String fechaReporte;
	private String folioSolicitud;
	private String descripcionTramite;	
	private String nombreRS;
	private String rfc;
	private String curp;
	private String nss;
	//Lugar en que se realizo el tramite.
	private Long   idSubdelegacion;
	private String usuarioVentanilla;
	private String subdelegacion;
	//Persona que firma.
	private String rfcFirmante;
	private String nombreFirmante;
	private String curpFirmante;
	//Datos Firma
	private String cadenaOriginal;
	private String selloDigital;
	private String secuenciaNotaria;
	private String numeroSerie;
	
	

	public boolean getEsFisica() {
		return esFisica;
	}
	public void setEsFisica(boolean esFisica) {
		this.esFisica = esFisica;
	}
	public String getFechaReporte() {
		return fechaReporte;
	}
	public void setFechaReporte(String fechaReporte) {
		this.fechaReporte = fechaReporte;
	}
	public String getFolioSolicitud() {
		return folioSolicitud;
	}
	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
	}
	public String getDescripcionTramite() {
		return descripcionTramite;
	}
	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}
	public String getNombreRS() {
		return nombreRS;
	}
	public void setNombreRS(String nombreRS) {
		this.nombreRS = nombreRS;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public Long getIdSubdelegacion() {
		return idSubdelegacion;
	}
	public void setIdSubdelegacion(Long idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}
	public String getUsuarioVentanilla() {
		return usuarioVentanilla;
	}
	public void setUsuarioVentanilla(String usuarioVentanilla) {
		this.usuarioVentanilla = usuarioVentanilla;
	}
	public String getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(String subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	public String getRfcFirmante() {
		return rfcFirmante;
	}
	public void setRfcFirmante(String rfcFirmante) {
		this.rfcFirmante = rfcFirmante;
	}
	public String getNombreFirmante() {
		return nombreFirmante;
	}
	public void setNombreFirmante(String nombreFirmante) {
		this.nombreFirmante = nombreFirmante;
	}
	public String getCurpFirmante() {
		return curpFirmante;
	}
	public void setCurpFirmante(String curpFirmante) {
		this.curpFirmante = curpFirmante;
	}
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}
	public String getSelloDigital() {
		return selloDigital;
	}
	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}
	public String getSecuenciaNotaria() {
		return secuenciaNotaria;
	}
	public void setSecuenciaNotaria(String secuenciaNotaria) {
		this.secuenciaNotaria = secuenciaNotaria;
	}
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	
}
