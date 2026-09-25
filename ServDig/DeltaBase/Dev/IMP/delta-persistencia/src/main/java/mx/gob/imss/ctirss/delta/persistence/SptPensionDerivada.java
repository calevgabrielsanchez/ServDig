package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SPT_PENSION_DERIVADA database table.
 * 
 */
@Entity
@Table(name="SPT_PENSION_DERIVADA")
@NamedQuery(name="SptPensionDerivada.findAll", query="SELECT s FROM SptPensionDerivada s")
public class SptPensionDerivada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTPENSIONDERIVADA", sequenceName = "SEQ_SPTPENSIONDERIVADA")
	@GeneratedValue(generator = "SEQ_SPTPENSIONDERIVADA")
	@Column(name="CVE_ID_PENSION_DERIVADA")
	private long cveIdPensionDerivada;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_PENSION_DERIVA")
	private SptPension sptPension1;

	//bi-directional many-to-one association to SptPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_PENSION")
	private SptPension sptPension2;

	public SptPensionDerivada() {
	}

	public long getCveIdPensionDerivada() {
		return this.cveIdPensionDerivada;
	}

	public void setCveIdPensionDerivada(long cveIdPensionDerivada) {
		this.cveIdPensionDerivada = cveIdPensionDerivada;
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

	public SptPension getSptPension1() {
		return this.sptPension1;
	}

	public void setSptPension1(SptPension sptPension1) {
		this.sptPension1 = sptPension1;
	}

	public SptPension getSptPension2() {
		return this.sptPension2;
	}

	public void setSptPension2(SptPension sptPension2) {
		this.sptPension2 = sptPension2;
	}

}