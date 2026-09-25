package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the DIT_PERSONAM_CONTACTO_FISCAL database table.
 * 
 */
@Entity
@Table(name = "DIT_PERSONAM_CONTACTO_FISCAL")
public class DitPersonaMContactoFiscal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_PERSONAM_CONTACTO_FISCAL_CVEIDPMCONTFISCAL_GENERATOR", sequenceName="SEQ_DITPERSONAMCONTACTOFISCAL")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_PERSONAM_CONTACTO_FISCAL_CVEIDPMCONTFISCAL_GENERATOR")
	@Column(name = "CVE_ID_PMCONT_FISCAL")
	private long cveIdPmcontFiscal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	// bi-directional many-to-one association to DitFormaContacto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_FORMA_CONTACTO")
	private DitFormaContacto ditFormaContacto;

	// bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

	public DitPersonaMContactoFiscal() {
	}

	public long getCveIdPmcontFiscal() {
		return this.cveIdPmcontFiscal;
	}

	public void setCveIdPmcontFiscal(long cveIdPmcontFiscal) {
		this.cveIdPmcontFiscal = cveIdPmcontFiscal;
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

	public DitFormaContacto getDitFormaContacto() {
		return this.ditFormaContacto;
	}

	public void setDitFormaContacto(DitFormaContacto ditFormaContacto) {
		this.ditFormaContacto = ditFormaContacto;
	}

	public DitPersonaMoral getDitPersonaMoral() {
		return this.ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}

}