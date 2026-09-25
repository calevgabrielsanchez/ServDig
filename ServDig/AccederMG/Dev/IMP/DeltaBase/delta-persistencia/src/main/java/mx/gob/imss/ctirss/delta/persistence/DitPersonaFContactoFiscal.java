package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PERSONAF_CONTACTO_FISCAL database table.
 * 
 */
@Entity
@Table(name="DIT_PERSONAF_CONTACTO_FISCAL")
public class DitPersonaFContactoFiscal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIT_PERSONAF_CONTACTO_FISCAL_CVEIDPFCONTFISCAL_GENERATOR", sequenceName="SEQ_DITPERSONAFCONTACTOFISCAL")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIT_PERSONAF_CONTACTO_FISCAL_CVEIDPFCONTFISCAL_GENERATOR")
	@Column(name="CVE_ID_PFCONT_FISCAL")
	private long cveIdPfcontFiscal;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitFormaContacto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_FORMA_CONTACTO")
	private DitFormaContacto ditFormaContacto;

	//bi-directional many-to-one association to DitPersonaFisica
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA_FISICA")
	private DitPersonaFisica ditPersonaFisica;

    public DitPersonaFContactoFiscal() {
    }

	public long getCveIdPfcontFiscal() {
		return this.cveIdPfcontFiscal;
	}

	public void setCveIdPfcontFiscal(long cveIdPfcontFiscal) {
		this.cveIdPfcontFiscal = cveIdPfcontFiscal;
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
	
	public DitPersonaFisica getDitPersonaFisica() {
		return this.ditPersonaFisica;
	}

	public void setDitPersonaFisica(DitPersonaFisica ditPersonaFisica) {
		this.ditPersonaFisica = ditPersonaFisica;
	}
	
}