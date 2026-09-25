package mx.gob.imss.ctirss.delta.utilities.planificador.estadistica.utils;

import java.util.Date;

public class CorteEstadisticoProperties {
	private Date fechaCorteGeneral;
	private Double minutosTolerancia;
	private String strHoraCorte;
	private String urlMailServer;
	private String userMailServer;
	private String passwordMailServer;
	private String remitenteCorreo;
	private String nombreRemitenteCorreo;
	private String replyTo;
	private String replyToName;
	private String[] destCorteDiaActualAsignacion;
	private String[] destCorteDiaAnteriorAsignacion;
	private String[] destCorteRangoAsignacion;
	private String[] destCorteDiaActualAltaPat;
	private String[] destCorteDiaAnteriorAltaPat;
	private String[] destCorteRangoAltaPat;
	private String[] destCorteDiaActualModifClasifVent;
	private String[] destCorteDiaAnteriorModifClasifVent;
	private String[] destCorteRangoModifClasifVent;
	private String[] destCorteDiaActualModifClasifExt;
	private String[] destCorteDiaAnteriorModifClasifExt;
	private String[] destCorteRangoModifClasifExt;
	private String[] destCorteDiaActualAltaRiss;
	private String[] destCorteDiaAnteriorAltaRiss;
	private String[] destCorteRangoModifAltaRiss;

	public Date getFechaCorteGeneral() {
		return fechaCorteGeneral;
	}

	public void setFechaCorteGeneral(Date fechaCorteGeneral) {
		this.fechaCorteGeneral = fechaCorteGeneral;
	}

	public Double getMinutosTolerancia() {
		return minutosTolerancia;
	}

	public void setMinutosTolerancia(Double minutosTolerancia) {
		this.minutosTolerancia = minutosTolerancia;
	}

	public String getStrHoraCorte() {
		return strHoraCorte;
	}

	public void setStrHoraCorte(String strHoraCorte) {
		this.strHoraCorte = strHoraCorte;
	}

	public String getUrlMailServer() {
		return urlMailServer;
	}

	public void setUrlMailServer(String urlMailServer) {
		this.urlMailServer = urlMailServer;
	}

	public String getUserMailServer() {
		return userMailServer;
	}

	public void setUserMailServer(String userMailServer) {
		this.userMailServer = userMailServer;
	}

	public String getPasswordMailServer() {
		return passwordMailServer;
	}

	public void setPasswordMailServer(String passwordMailServer) {
		this.passwordMailServer = passwordMailServer;
	}

	public String getRemitenteCorreo() {
		return remitenteCorreo;
	}

	public void setRemitenteCorreo(String remitenteCorreo) {
		this.remitenteCorreo = remitenteCorreo;
	}

	public String getNombreRemitenteCorreo() {
		return nombreRemitenteCorreo;
	}

	public void setNombreRemitenteCorreo(String nombreRemitenteCorreo) {
		this.nombreRemitenteCorreo = nombreRemitenteCorreo;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public String getReplyToName() {
		return replyToName;
	}

	public void setReplyToName(String replyToName) {
		this.replyToName = replyToName;
	}

	public String[] getDestCorteDiaActualAsignacion() {
		return destCorteDiaActualAsignacion;
	}

	public void setDestCorteDiaActualAsignacion(
			String[] destCorteDiaActualAsignacion) {
		this.destCorteDiaActualAsignacion = destCorteDiaActualAsignacion != null ? destCorteDiaActualAsignacion
				.clone() : null;
	}

	public String[] getDestCorteDiaAnteriorAsignacion() {
		return destCorteDiaAnteriorAsignacion;
	}

	public void setDestCorteDiaAnteriorAsignacion(
			String[] destCorteDiaAnteriorAsignacion) {
		this.destCorteDiaAnteriorAsignacion = destCorteDiaAnteriorAsignacion != null ? destCorteDiaAnteriorAsignacion
				.clone() : null;
	}

	public String[] getDestCorteRangoAsignacion() {
		return destCorteRangoAsignacion;
	}

	public void setDestCorteRangoAsignacion(String[] destCorteRangoAsignacion) {
		this.destCorteRangoAsignacion = destCorteRangoAsignacion != null ? destCorteRangoAsignacion
				.clone() : null;
	}

	public String[] getDestCorteDiaActualAltaPat() {
		return destCorteDiaActualAltaPat;
	}

	public void setDestCorteDiaActualAltaPat(String[] destCorteDiaActualAltaPat) {
		this.destCorteDiaActualAltaPat = destCorteDiaActualAltaPat != null ? destCorteDiaActualAltaPat
				.clone() : null;
	}

	public String[] getDestCorteDiaAnteriorAltaPat() {
		return destCorteDiaAnteriorAltaPat;
	}

	public void setDestCorteDiaAnteriorAltaPat(
			String[] destCorteDiaAnteriorAltaPat) {
		this.destCorteDiaAnteriorAltaPat = destCorteDiaAnteriorAltaPat != null ? destCorteDiaAnteriorAltaPat
				.clone() : null;
	}

	public String[] getDestCorteRangoAltaPat() {
		return destCorteRangoAltaPat;
	}

	public void setDestCorteRangoAltaPat(String[] destCorteRangoAltaPat) {
		this.destCorteRangoAltaPat = destCorteRangoAltaPat != null ? destCorteRangoAltaPat
				.clone() : null;
	}

	public String[] getDestCorteDiaActualModifClasifVent() {
		return destCorteDiaActualModifClasifVent;
	}

	public void setDestCorteDiaActualModifClasifVent(
			String[] destCorteDiaActualModifClasifVent) {
		this.destCorteDiaActualModifClasifVent = destCorteDiaActualModifClasifVent != null ? destCorteDiaActualModifClasifVent
				.clone() : null;
	}

	public String[] getDestCorteDiaAnteriorModifClasifVent() {
		return destCorteDiaAnteriorModifClasifVent;
	}

	public void setDestCorteDiaAnteriorModifClasifVent(
			String[] destCorteDiaAnteriorModifClasifVent) {
		this.destCorteDiaAnteriorModifClasifVent = destCorteDiaAnteriorModifClasifVent != null ? destCorteDiaAnteriorModifClasifVent
				.clone() : null;
	}

	public String[] getDestCorteRangoModifClasifVent() {
		return destCorteRangoModifClasifVent;
	}

	public void setDestCorteRangoModifClasifVent(
			String[] destCorteRangoModifClasifVent) {
		this.destCorteRangoModifClasifVent = destCorteRangoModifClasifVent != null ? destCorteRangoModifClasifVent
				.clone() : null;
	}

	public String[] getDestCorteDiaActualModifClasifExt() {
		return destCorteDiaActualModifClasifExt;
	}

	public void setDestCorteDiaActualModifClasifExt(
			String[] destCorteDiaActualModifClasifExt) {
		this.destCorteDiaActualModifClasifExt = destCorteDiaActualModifClasifExt != null ? destCorteDiaActualModifClasifExt
				.clone() : null;
	}

	public String[] getDestCorteDiaAnteriorModifClasifExt() {
		return destCorteDiaAnteriorModifClasifExt;
	}

	public void setDestCorteDiaAnteriorModifClasifExt(
			String[] destCorteDiaAnteriorModifClasifExt) {
		this.destCorteDiaAnteriorModifClasifExt = destCorteDiaAnteriorModifClasifExt != null ? destCorteDiaAnteriorModifClasifExt
				.clone() : null;
	}

	public String[] getDestCorteRangoModifClasifExt() {
		return destCorteRangoModifClasifExt;
	}

	public void setDestCorteRangoModifClasifExt(
			String[] destCorteRangoModifClasifExt) {
		this.destCorteRangoModifClasifExt = destCorteRangoModifClasifExt != null ? destCorteRangoModifClasifExt
				.clone() : null;
	}

	public String[] getDestCorteDiaActualAltaRiss() {
		return destCorteDiaActualAltaRiss;
	}

	public void setDestCorteDiaActualAltaRiss(
			String[] destCorteDiaActualAltaRiss) {
		this.destCorteDiaActualAltaRiss = destCorteDiaActualAltaRiss != null ? destCorteDiaActualAltaRiss
				.clone() : null;
	}

	public String[] getDestCorteDiaAnteriorAltaRiss() {
		return destCorteDiaAnteriorAltaRiss;
	}

	public void setDestCorteDiaAnteriorAltaRiss(
			String[] destCorteDiaAnteriorAltaRiss) {
		this.destCorteDiaAnteriorAltaRiss = destCorteDiaAnteriorAltaRiss != null ? destCorteDiaAnteriorAltaRiss
				.clone() : null;
	}

	public String[] getDestCorteRangoModifAltaRiss() {
		return destCorteRangoModifAltaRiss;
	}

	public void setDestCorteRangoModifAltaRiss(
			String[] destCorteRangoModifAltaRiss) {
		this.destCorteRangoModifAltaRiss = destCorteRangoModifAltaRiss != null ? destCorteRangoModifAltaRiss
				.clone() : null;
	}
}
