package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the DIT_PERSONAM_DOM_FISCAL database table.
 * 
 */
@Entity
@Table(name = "DIT_PERSONAM_DOM_FISCAL")
public class DitPersonaMDomFiscal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_PERSONAM_DOM_FISCAL_CVEIDPMDOMFISCAL_GENERATOR", sequenceName="SEQ_DITPERSONAMDOMFISCAL")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_PERSONAM_DOM_FISCAL_CVEIDPMDOMFISCAL_GENERATOR")
	@Column(name = "CVE_ID_PMDOM_FISCAL")
	private long cveIdPmdomFiscal;

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

	// bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

	public DitPersonaMDomFiscal() {
	}

	public long getCveIdPmdomFiscal() {
		return this.cveIdPmdomFiscal;
	}

	public void setCveIdPmdomFiscal(long cveIdPmdomFiscal) {
		this.cveIdPmdomFiscal = cveIdPmdomFiscal;
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

	public DitPersonaMoral getDitPersonaMoral() {
		return this.ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}

}