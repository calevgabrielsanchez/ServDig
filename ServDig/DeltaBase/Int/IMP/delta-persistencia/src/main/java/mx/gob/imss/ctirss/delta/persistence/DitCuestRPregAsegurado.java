package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_CUEST_R_PREG_ASEGURADO database table.
 * 
 */
@Entity
@Table(name="DIT_CUEST_R_PREG_ASEGURADO")
public class DitCuestRPregAsegurado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CUEST_R_PREG_ASEGURADO", nullable=false, precision=22)
	private long cveIdCuestRPregAsegurado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitCuestRAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CUEST_R_ASEGURADO")
	private DitCuestRAsegurado ditCuestRAsegurado;

	//bi-directional many-to-one association to DicCuestionarioPregunta
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CUESTIONARIO_PREGUNTA")
	private DicCuestionarioPregunta dicCuestionarioPregunta;

	//bi-directional many-to-one association to DitCuestRRespAsegurado
	@OneToMany(mappedBy="ditCuestRPregAsegurado")
	private List<DitCuestRRespAsegurado> ditCuestRRespAsegurados;

    public DitCuestRPregAsegurado() {
    }

	public long getCveIdCuestRPregAsegurado() {
		return this.cveIdCuestRPregAsegurado;
	}

	public void setCveIdCuestRPregAsegurado(long cveIdCuestRPregAsegurado) {
		this.cveIdCuestRPregAsegurado = cveIdCuestRPregAsegurado;
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

	public DitCuestRAsegurado getDitCuestRAsegurado() {
		return this.ditCuestRAsegurado;
	}

	public void setDitCuestRAsegurado(DitCuestRAsegurado ditCuestRAsegurado) {
		this.ditCuestRAsegurado = ditCuestRAsegurado;
	}
	
	public DicCuestionarioPregunta getDicCuestionarioPregunta() {
		return this.dicCuestionarioPregunta;
	}

	public void setDicCuestionarioPregunta(DicCuestionarioPregunta dicCuestionarioPregunta) {
		this.dicCuestionarioPregunta = dicCuestionarioPregunta;
	}
	
	public List<DitCuestRRespAsegurado> getDitCuestRRespAsegurados() {
		return this.ditCuestRRespAsegurados;
	}

	public void setDitCuestRRespAsegurados(List<DitCuestRRespAsegurado> ditCuestRRespAsegurados) {
		this.ditCuestRRespAsegurados = ditCuestRRespAsegurados;
	}
	
}