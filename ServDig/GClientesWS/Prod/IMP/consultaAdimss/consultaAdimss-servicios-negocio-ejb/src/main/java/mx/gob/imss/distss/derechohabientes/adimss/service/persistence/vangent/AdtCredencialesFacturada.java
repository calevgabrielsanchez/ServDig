package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ADT_CREDENCIALES_FACTURADAS database table.
 * 
 */
@Embeddable
@Table(name="ADT_CREDENCIALES_FACTURADAS")
@NamedQuery(name="AdtCredencialesFacturada.findAll", query="SELECT a FROM AdtCredencialesFacturada a")
public class AdtCredencialesFacturada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_DELEGACION")
	private BigDecimal cveDelegacion;

	@Column(name="CVE_PERSONA")
	private BigDecimal cvePersona;

	private Object duracion;

	private String factura;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_EXPEDICION")
	private Date fecExpedicion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_FACTURA")
	private Date fechaFactura;

	@Column(name="NUM_ECONOMICO")
	private BigDecimal numEconomico;

	@Column(name="NUM_FOLIO_TRAMITE")
	private BigDecimal numFolioTramite;

	@Column(name="NUM_NIVEL_ATENCION")
	private BigDecimal numNivelAtencion;

	@Column(name="REF_ACUSE_RECIBO")
	private String refAcuseRecibo;

	@Column(name="TPO_EXPEDICION")
	private BigDecimal tpoExpedicion;

	@Column(name="TPO_FACTURA")
	private BigDecimal tpoFactura;

	public AdtCredencialesFacturada() {
	}

	public BigDecimal getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(BigDecimal cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public BigDecimal getCvePersona() {
		return this.cvePersona;
	}

	public void setCvePersona(BigDecimal cvePersona) {
		this.cvePersona = cvePersona;
	}

	public Object getDuracion() {
		return this.duracion;
	}

	public void setDuracion(Object duracion) {
		this.duracion = duracion;
	}

	public String getFactura() {
		return this.factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public Date getFecExpedicion() {
		return this.fecExpedicion;
	}

	public void setFecExpedicion(Date fecExpedicion) {
		this.fecExpedicion = fecExpedicion;
	}

	public Date getFechaFactura() {
		return this.fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public BigDecimal getNumEconomico() {
		return this.numEconomico;
	}

	public void setNumEconomico(BigDecimal numEconomico) {
		this.numEconomico = numEconomico;
	}

	public BigDecimal getNumFolioTramite() {
		return this.numFolioTramite;
	}

	public void setNumFolioTramite(BigDecimal numFolioTramite) {
		this.numFolioTramite = numFolioTramite;
	}

	public BigDecimal getNumNivelAtencion() {
		return this.numNivelAtencion;
	}

	public void setNumNivelAtencion(BigDecimal numNivelAtencion) {
		this.numNivelAtencion = numNivelAtencion;
	}

	public String getRefAcuseRecibo() {
		return this.refAcuseRecibo;
	}

	public void setRefAcuseRecibo(String refAcuseRecibo) {
		this.refAcuseRecibo = refAcuseRecibo;
	}

	public BigDecimal getTpoExpedicion() {
		return this.tpoExpedicion;
	}

	public void setTpoExpedicion(BigDecimal tpoExpedicion) {
		this.tpoExpedicion = tpoExpedicion;
	}

	public BigDecimal getTpoFactura() {
		return this.tpoFactura;
	}

	public void setTpoFactura(BigDecimal tpoFactura) {
		this.tpoFactura = tpoFactura;
	}

}