package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ADT_CREDENCIAL database table.
 * 
 */
@Entity
@Table(name="ADT_CREDENCIAL")
@NamedQuery(name="AdtCredencial.findAll", query="SELECT a FROM AdtCredencial a")
public class AdtCredencial implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdtCredencialPK id;

	@Column(name="CVE_PERSONA")
	private BigDecimal cvePersona;

	@Column(name="NUM_OID_FIRMA")
	private String numOidFirma;

	@Column(name="NUM_OID_FOTO")
	private String numOidFoto;

	@Column(name="NUM_OID_RECIBO")
	private String numOidRecibo;

	@Column(name="REF_CTO_PROVEEDOR")
	private String refCtoProveedor;

	@Column(name="REF_STATUS_CREDENCIAL")
	private String refStatusCredencial;

	@Temporal(TemporalType.DATE)
	@Column(name="STP_EXPEDICION")
	private Date stpExpedicion;

	//bi-directional many-to-one association to AdcCalidadDerechohabient
	@ManyToOne
	@JoinColumn(name="CVE_CALIDAD_ASEG", insertable=false, updatable=false)
	private AdcCalidadDerechohabient adcCalidadDerechohabient;

	//bi-directional many-to-one association to AdcCtrosEnrol
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CVE_DELEGACION", referencedColumnName="CVE_DELEGACION"),
		@JoinColumn(name="NUM_ECONOMICO", referencedColumnName="NUM_ECONOMICO"),
		@JoinColumn(name="NUM_NIVEL_ATENCION", referencedColumnName="NUM_NIVEL_ATENCION")
		})
	private AdcCtrosEnrol adcCtrosEnrol;

	//bi-directional many-to-one association to AdcStatusFirma
	@ManyToOne
	@JoinColumn(name="CVE_STATUS_FIRMA")
	private AdcStatusFirma adcStatusFirma;

	//bi-directional many-to-one association to AdcTipoMov
	@ManyToOne
	@JoinColumn(name="CVE_TIPO_REGISTRO")
	private AdcTipoMov adcTipoMov;

	//bi-directional many-to-one association to AdtPersonaCredencializada
	@ManyToOne
	@JoinColumn(name="NUM_FOLIO_PERSONA", insertable=false, updatable=false)
	private AdtPersonaCredencializada adtPersonaCredencializada;

	public AdtCredencial() {
	}

	public AdtCredencialPK getId() {
		return this.id;
	}

	public void setId(AdtCredencialPK id) {
		this.id = id;
	}

	public BigDecimal getCvePersona() {
		return this.cvePersona;
	}

	public void setCvePersona(BigDecimal cvePersona) {
		this.cvePersona = cvePersona;
	}

	public String getNumOidFirma() {
		return this.numOidFirma;
	}

	public void setNumOidFirma(String numOidFirma) {
		this.numOidFirma = numOidFirma;
	}

	public String getNumOidFoto() {
		return this.numOidFoto;
	}

	public void setNumOidFoto(String numOidFoto) {
		this.numOidFoto = numOidFoto;
	}

	public String getNumOidRecibo() {
		return this.numOidRecibo;
	}

	public void setNumOidRecibo(String numOidRecibo) {
		this.numOidRecibo = numOidRecibo;
	}

	public String getRefCtoProveedor() {
		return this.refCtoProveedor;
	}

	public void setRefCtoProveedor(String refCtoProveedor) {
		this.refCtoProveedor = refCtoProveedor;
	}

	public String getRefStatusCredencial() {
		return this.refStatusCredencial;
	}

	public void setRefStatusCredencial(String refStatusCredencial) {
		this.refStatusCredencial = refStatusCredencial;
	}

	public Date getStpExpedicion() {
		return this.stpExpedicion;
	}

	public void setStpExpedicion(Date stpExpedicion) {
		this.stpExpedicion = stpExpedicion;
	}

	public AdcCalidadDerechohabient getAdcCalidadDerechohabient() {
		return this.adcCalidadDerechohabient;
	}

	public void setAdcCalidadDerechohabient(AdcCalidadDerechohabient adcCalidadDerechohabient) {
		this.adcCalidadDerechohabient = adcCalidadDerechohabient;
	}

	public AdcCtrosEnrol getAdcCtrosEnrol() {
		return this.adcCtrosEnrol;
	}

	public void setAdcCtrosEnrol(AdcCtrosEnrol adcCtrosEnrol) {
		this.adcCtrosEnrol = adcCtrosEnrol;
	}

	public AdcStatusFirma getAdcStatusFirma() {
		return this.adcStatusFirma;
	}

	public void setAdcStatusFirma(AdcStatusFirma adcStatusFirma) {
		this.adcStatusFirma = adcStatusFirma;
	}

	public AdcTipoMov getAdcTipoMov() {
		return this.adcTipoMov;
	}

	public void setAdcTipoMov(AdcTipoMov adcTipoMov) {
		this.adcTipoMov = adcTipoMov;
	}

	public AdtPersonaCredencializada getAdtPersonaCredencializada() {
		return this.adtPersonaCredencializada;
	}

	public void setAdtPersonaCredencializada(AdtPersonaCredencializada adtPersonaCredencializada) {
		this.adtPersonaCredencializada = adtPersonaCredencializada;
	}

}