package mx.gob.imss.ctirss.delta.model.gestion.integracion.common;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ReporteRissWrapper extends AbstractModel {

	private static final long serialVersionUID = -5143203463902938733L;

	private BigDecimal idSolicitud;
	private String folio;
	private String observaciones;
	private Date fecha;
	private String origen;
	private String estado;
	private String apartado;
	private Boolean esApartadoC;
	private BigDecimal idPersona;
	private String rfcPersona;
	private String nss;
	private BigDecimal idSujOblig;
	private String rfcPersonaFisica;
	private String nrp;
	private String rfcXML;
	private String nssXML;

	private String descMedioContacto;
	private String tipoMedioContacto;

	public BigDecimal getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(BigDecimal idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getApartado() {
		return apartado;
	}

	public void setApartado(String apartado) {
		this.apartado = apartado;
	}

	public Boolean getEsApartadoC() {
		return esApartadoC;
	}

	public void setEsApartadoC(String esApartadoC) {
		if (StringUtils.isBlank(esApartadoC)) {
			this.esApartadoC = false;
		}
		
		this.esApartadoC = Boolean.parseBoolean(esApartadoC);
	}
	
	public BigDecimal getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(BigDecimal idPersona) {
		this.idPersona = idPersona;
	}

	public String getRfcPersona() {
		return rfcPersona;
	}

	public void setRfcPersona(String rfcPersona) {
		this.rfcPersona = rfcPersona;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public BigDecimal getIdSujOblig() {
		return idSujOblig;
	}

	public void setIdSujOblig(BigDecimal idSujOblig) {
		this.idSujOblig = idSujOblig;
	}

	public String getRfcPersonaFisica() {
		return rfcPersonaFisica;
	}

	public void setRfcPersonaFisica(String rfcPersonaFisica) {
		this.rfcPersonaFisica = rfcPersonaFisica;
	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getRfcXML() {
		return rfcXML;
	}

	public void setRfcXML(String rfcXML) {
		this.rfcXML = rfcXML;
	}

	public String getNssXML() {
		return nssXML;
	}

	public void setNssXML(String nssXML) {
		this.nssXML = nssXML;
	}

	public String getDescMedioContacto() {
		return descMedioContacto;
	}

	public void setDescMedioContacto(String descMedioContacto) {
		this.descMedioContacto = descMedioContacto;
	}

	public String getTipoMedioContacto() {
		return tipoMedioContacto;
	}

	public void setTipoMedioContacto(String tipoMedioContacto) {
		this.tipoMedioContacto = tipoMedioContacto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReporteRissWrapper [idSolicitud=");
		builder.append(idSolicitud);
		builder.append(", folio=");
		builder.append(folio);
		builder.append(", observaciones=");
		builder.append(observaciones);
		builder.append(", fecha=");
		builder.append(fecha);
		builder.append(", origen=");
		builder.append(origen);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", apartado=");
		builder.append(apartado);
		builder.append(", idPersona=");
		builder.append(idPersona);
		builder.append(", rfcPersona=");
		builder.append(rfcPersona);
		builder.append(", nss=");
		builder.append(nss);
		builder.append(", idSujOblig=");
		builder.append(idSujOblig);
		builder.append(", rfcPersonaFisica=");
		builder.append(rfcPersonaFisica);
		builder.append(", nrp=");
		builder.append(nrp);
		builder.append(", rfcXML=");
		builder.append(rfcXML);
		builder.append(", nssXML=");
		builder.append(nssXML);
		builder.append("]");

		return builder.toString();
	}
}