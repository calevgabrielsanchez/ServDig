package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the DIT_PERSONAF_DOM_FISCAL database table.
 * 
 */
@Entity
@Table(name = "DIT_PERSONAF_DOM_FISCAL")
public class DitPersonaFDomFiscal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_PERSONAF_DOM_FISCAL_CVEIDPFDOMFISCAL_GENERATOR", sequenceName="SEQ_DITPERSONAFDOMFISCAL")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_PERSONAF_DOM_FISCAL_CVEIDPFDOMFISCAL_GENERATOR")
	@Column(name = "CVE_ID_PFDOM_FISCAL")
	private long cveIdPfdomFiscal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	// bi-directional many-to-one association to DitDomicilioSat
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_DOMICILIO")
	private DitDomicilioSat ditDomicilioSat;

	// bi-directional many-to-one association to DitPersonaFisica
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONA_FISICA")
	private DitPersonaFisica ditPersonaFisica;

	public DitPersonaFDomFiscal() {
	}

	public long getCveIdPfdomFiscal() {
		return this.cveIdPfdomFiscal;
	}

	public void setCveIdPfdomFiscal(long cveIdPfdomFiscal) {
		this.cveIdPfdomFiscal = cveIdPfdomFiscal;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public DitDomicilioSat getDitDomicilioSat() {
		return this.ditDomicilioSat;
	}

	public void setDitDomicilioSat(DitDomicilioSat ditDomicilioSat) {
		this.ditDomicilioSat = ditDomicilioSat;
	}

	public DitPersonaFisica getDitPersonaFisica() {
		return this.ditPersonaFisica;
	}

	public void setDitPersonaFisica(DitPersonaFisica ditPersonaFisica) {
		this.ditPersonaFisica = ditPersonaFisica;
	}

}