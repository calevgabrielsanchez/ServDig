package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the DIT_PERSONA_BENEFICIO database table.
 * 
 */
@Entity
@Table(name = "DIT_PERSONA_BENEFICIO")
public class DitPersonaBeneficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_PERSONA_BENEFICIO_CVEIDPERSONABENEFICIO_GENERATOR", sequenceName = "SEQ_DITPERSONABENEFICIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PERSONA_BENEFICIO_CVEIDPERSONABENEFICIO_GENERATOR")
	@Column(name = "CVE_ID_PERSONA_BENEFICIO")
	private Long cveIdPersonaBeneficio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	// bi-directional many-to-one association to DitBeneficio
	@ManyToOne
	@JoinColumn(name = "CVE_ID_BENEFICIO")
	private DitBeneficio ditBeneficio;

	// bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONA")
	private DitPersona ditPersona;
	
	// bi-directional many-to-one association to DicEstadoBeneficio
	@ManyToOne
	@JoinColumn(name = "CVE_ID_ESTADO_BENEFICIO")
	private DicEstadoBeneficio dicEstadoBeneficio;

	public DitPersonaBeneficio() {
	}

	public Long getCveIdPersonaBeneficio() {
		return this.cveIdPersonaBeneficio;
	}

	public void setCveIdPersonaBeneficio(Long cveIdPersonaBeneficio) {
		this.cveIdPersonaBeneficio = cveIdPersonaBeneficio;
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

	public DitBeneficio getDitBeneficio() {
		return this.ditBeneficio;
	}

	public void setDitBeneficio(DitBeneficio ditBeneficio) {
		this.ditBeneficio = ditBeneficio;
	}

	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
	public DicEstadoBeneficio getDicEstadoBeneficio() {
		return this.dicEstadoBeneficio;
	}

	public void setDicEstadoBeneficio(DicEstadoBeneficio dicEstadoBeneficio) {
		this.dicEstadoBeneficio = dicEstadoBeneficio;
	}

}