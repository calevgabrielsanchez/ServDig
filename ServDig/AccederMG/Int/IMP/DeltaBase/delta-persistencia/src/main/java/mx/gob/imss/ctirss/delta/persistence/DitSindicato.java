package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_SINDICATO database table.
 * 
 */
@Entity
@Table(name="DIT_SINDICATO")
public class DitSindicato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_SINDICATO_GENERATOR", sequenceName = "SEQ_DITSINDICATO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_SINDICATO_GENERATOR")
	@Column(name="CVE_ID_SINDICATO", nullable=false, precision=22)
	private long cveIdSindicato;

	@Column(name="DES_AUT_LAB", length=20)
	private String desAutLab;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_DOC_REGISTRO")
	private Date fecDocRegistro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_REF_REGISTRO", length=100)
	private String numRefRegistro;

	//bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

    public DitSindicato() {
    }

	public long getCveIdSindicato() {
		return this.cveIdSindicato;
	}

	public void setCveIdSindicato(long cveIdSindicato) {
		this.cveIdSindicato = cveIdSindicato;
	}

	public String getDesAutLab() {
		return this.desAutLab;
	}

	public void setDesAutLab(String desAutLab) {
		this.desAutLab = desAutLab;
	}

	public Date getFecDocRegistro() {
		return this.fecDocRegistro;
	}

	public void setFecDocRegistro(Date fecDocRegistro) {
		this.fecDocRegistro = fecDocRegistro;
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

	public String getNumRefRegistro() {
		return this.numRefRegistro;
	}

	public void setNumRefRegistro(String numRefRegistro) {
		this.numRefRegistro = numRefRegistro;
	}

	public DitPersonaMoral getDitPersonaMoral() {
		return this.ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}
	
}