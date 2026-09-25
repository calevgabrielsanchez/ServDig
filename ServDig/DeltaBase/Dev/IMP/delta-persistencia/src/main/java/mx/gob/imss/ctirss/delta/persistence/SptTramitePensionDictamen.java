package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SPT_TRAMITE_PENSION_DICTAMEN database table.
 * 
 */
@Entity
@Table(name="SPT_TRAMITE_PENSION_DICTAMEN")
@NamedQuery(name="SptTramitePensionDictamen.findAll", query="SELECT s FROM SptTramitePensionDictamen s")
public class SptTramitePensionDictamen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTTRAMITEPENSIONDICTAMEN", sequenceName = "SEQ_SPTTRAMITEPENSIONDICTAMEN")
	@GeneratedValue(generator = "SEQ_SPTTRAMITEPENSIONDICTAMEN")
	@Column(name="CVE_ID_TRAMITE_PENSION_DICTAME")
	private long cveIdTramitePensionDictame;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptDictamen
	@ManyToOne
	@JoinColumn(name="CVE_ID_DICTAMEN")
	private SptDictamen sptDictamen;

	//bi-directional many-to-one association to SptTramitePension
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

	public SptTramitePensionDictamen() {
	}

	public long getCveIdTramitePensionDictame() {
		return this.cveIdTramitePensionDictame;
	}

	public void setCveIdTramitePensionDictame(long cveIdTramitePensionDictame) {
		this.cveIdTramitePensionDictame = cveIdTramitePensionDictame;
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

	public SptDictamen getSptDictamen() {
		return this.sptDictamen;
	}

	public void setSptDictamen(SptDictamen sptDictamen) {
		this.sptDictamen = sptDictamen;
	}

	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}

}