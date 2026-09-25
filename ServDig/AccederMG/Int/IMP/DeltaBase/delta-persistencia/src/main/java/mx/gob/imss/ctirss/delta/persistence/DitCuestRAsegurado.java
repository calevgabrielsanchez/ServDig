package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_CUEST_R_ASEGURADO database table.
 * 
 */
@Entity
@Table(name="DIT_CUEST_R_ASEGURADO")
public class DitCuestRAsegurado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CUEST_R_ASEGURADO", nullable=false, precision=22)
	private long cveIdCuestRAsegurado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_TERMINO")
	private Date fecTermino;

	@Column(name="NUM_CALIFICACION", precision=22)
	private BigDecimal numCalificacion;

	@Column(name="TIP_ESTATUS", precision=22)
	private BigDecimal tipEstatus;

	@Column(name="TIP_PROPIETARIO", precision=22)
	private BigDecimal tipPropietario;

	//bi-directional many-to-one association to DicCuestionarioMedico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CUESTIONARIO_MEDICO")
	private DicCuestionarioMedico dicCuestionarioMedico;

	//bi-directional many-to-one association to DitAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASEGURADO")
	private DitAsegurado ditAsegurado;

	//bi-directional many-to-one association to DitCuestRPregAsegurado
	@OneToMany(mappedBy="ditCuestRAsegurado")
	private List<DitCuestRPregAsegurado> ditCuestRPregAsegurados;

    public DitCuestRAsegurado() {
    }

	public long getCveIdCuestRAsegurado() {
		return this.cveIdCuestRAsegurado;
	}

	public void setCveIdCuestRAsegurado(long cveIdCuestRAsegurado) {
		this.cveIdCuestRAsegurado = cveIdCuestRAsegurado;
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

	public Date getFecTermino() {
		return this.fecTermino;
	}

	public void setFecTermino(Date fecTermino) {
		this.fecTermino = fecTermino;
	}

	public BigDecimal getNumCalificacion() {
		return this.numCalificacion;
	}

	public void setNumCalificacion(BigDecimal numCalificacion) {
		this.numCalificacion = numCalificacion;
	}

	public BigDecimal getTipEstatus() {
		return this.tipEstatus;
	}

	public void setTipEstatus(BigDecimal tipEstatus) {
		this.tipEstatus = tipEstatus;
	}

	public BigDecimal getTipPropietario() {
		return this.tipPropietario;
	}

	public void setTipPropietario(BigDecimal tipPropietario) {
		this.tipPropietario = tipPropietario;
	}

	public DicCuestionarioMedico getDicCuestionarioMedico() {
		return this.dicCuestionarioMedico;
	}

	public void setDicCuestionarioMedico(DicCuestionarioMedico dicCuestionarioMedico) {
		this.dicCuestionarioMedico = dicCuestionarioMedico;
	}
	
	public DitAsegurado getDitAsegurado() {
		return this.ditAsegurado;
	}

	public void setDitAsegurado(DitAsegurado ditAsegurado) {
		this.ditAsegurado = ditAsegurado;
	}
	
	public List<DitCuestRPregAsegurado> getDitCuestRPregAsegurados() {
		return this.ditCuestRPregAsegurados;
	}

	public void setDitCuestRPregAsegurados(List<DitCuestRPregAsegurado> ditCuestRPregAsegurados) {
		this.ditCuestRPregAsegurados = ditCuestRPregAsegurados;
	}
	
}