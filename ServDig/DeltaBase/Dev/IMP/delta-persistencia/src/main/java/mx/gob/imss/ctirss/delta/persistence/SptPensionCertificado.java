package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_PENSION_CERTIFICADO database table.
 * 
 */
@Entity
@Table(name="SPT_PENSION_CERTIFICADO")
@NamedQuery(name="SptPensionCertificado.findAll", query="SELECT s FROM SptPensionCertificado s")
public class SptPensionCertificado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPT_PENSION_CERTIFICADO_CVEIDPENSIONCERTIFICADO_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPT_PENSION_CERTIFICADO_CVEIDPENSIONCERTIFICADO_GENERATOR")
	@Column(name="CVE_ID_PENSION_CERTIFICADO")
	private long cveIdPensionCertificado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptCertificadoDerecho
	@ManyToOne
	@JoinColumn(name="CVE_ID_CERTIFICADO_DERECHOS")
	private SptCertificadoDerecho sptCertificadoDerecho;

	//bi-directional many-to-one association to SptPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_PENSION")
	private SptPension sptPension;

	public SptPensionCertificado() {
	}

	public long getCveIdPensionCertificado() {
		return this.cveIdPensionCertificado;
	}

	public void setCveIdPensionCertificado(long cveIdPensionCertificado) {
		this.cveIdPensionCertificado = cveIdPensionCertificado;
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

	public SptCertificadoDerecho getSptCertificadoDerecho() {
		return this.sptCertificadoDerecho;
	}

	public void setSptCertificadoDerecho(SptCertificadoDerecho sptCertificadoDerecho) {
		this.sptCertificadoDerecho = sptCertificadoDerecho;
	}

	public SptPension getSptPension() {
		return this.sptPension;
	}

	public void setSptPension(SptPension sptPension) {
		this.sptPension = sptPension;
	}

}