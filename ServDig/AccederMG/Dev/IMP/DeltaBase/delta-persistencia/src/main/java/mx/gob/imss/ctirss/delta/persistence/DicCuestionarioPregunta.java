package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_CUESTIONARIO_PREGUNTA database table.
 * 
 */
@Entity
@Table(name="DIC_CUESTIONARIO_PREGUNTA")
public class DicCuestionarioPregunta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CUESTIONARIO_PREGUNTA", nullable=false, precision=22)
	private long cveIdCuestionarioPregunta;

	@Column(name="DES_PREGUNTA", length=20)
	private String desPregunta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_SECUENCIA", precision=22)
	private BigDecimal numSecuencia;

	@Column(name="TIP_PREGUNTA", precision=22)
	private BigDecimal tipPregunta;

	//bi-directional many-to-one association to DicCuestionarioMedico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CUESTIONARIO_MEDICO")
	private DicCuestionarioMedico dicCuestionarioMedico;

	//bi-directional many-to-one association to DicCuestionarioRespuesta
	@OneToMany(mappedBy="dicCuestionarioPregunta")
	private List<DicCuestionarioRespuesta> dicCuestionarioRespuestas;

	//bi-directional many-to-one association to DitCuestRPregAsegurado
	@OneToMany(mappedBy="dicCuestionarioPregunta")
	private List<DitCuestRPregAsegurado> ditCuestRPregAsegurados;

    public DicCuestionarioPregunta() {
    }

	public long getCveIdCuestionarioPregunta() {
		return this.cveIdCuestionarioPregunta;
	}

	public void setCveIdCuestionarioPregunta(long cveIdCuestionarioPregunta) {
		this.cveIdCuestionarioPregunta = cveIdCuestionarioPregunta;
	}

	public String getDesPregunta() {
		return this.desPregunta;
	}

	public void setDesPregunta(String desPregunta) {
		this.desPregunta = desPregunta;
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

	public BigDecimal getNumSecuencia() {
		return this.numSecuencia;
	}

	public void setNumSecuencia(BigDecimal numSecuencia) {
		this.numSecuencia = numSecuencia;
	}

	public BigDecimal getTipPregunta() {
		return this.tipPregunta;
	}

	public void setTipPregunta(BigDecimal tipPregunta) {
		this.tipPregunta = tipPregunta;
	}

	public DicCuestionarioMedico getDicCuestionarioMedico() {
		return this.dicCuestionarioMedico;
	}

	public void setDicCuestionarioMedico(DicCuestionarioMedico dicCuestionarioMedico) {
		this.dicCuestionarioMedico = dicCuestionarioMedico;
	}
	
	public List<DicCuestionarioRespuesta> getDicCuestionarioRespuestas() {
		return this.dicCuestionarioRespuestas;
	}

	public void setDicCuestionarioRespuestas(List<DicCuestionarioRespuesta> dicCuestionarioRespuestas) {
		this.dicCuestionarioRespuestas = dicCuestionarioRespuestas;
	}
	
	public List<DitCuestRPregAsegurado> getDitCuestRPregAsegurados() {
		return this.ditCuestRPregAsegurados;
	}

	public void setDitCuestRPregAsegurados(List<DitCuestRPregAsegurado> ditCuestRPregAsegurados) {
		this.ditCuestRPregAsegurados = ditCuestRPregAsegurados;
	}
	
}