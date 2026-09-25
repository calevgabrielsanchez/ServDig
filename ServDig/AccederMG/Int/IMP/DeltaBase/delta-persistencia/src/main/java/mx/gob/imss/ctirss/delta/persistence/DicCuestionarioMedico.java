package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



/**
 * The persistent class for the DIC_CUESTIONARIO_MEDICO database table.
 * 
 */
@Entity
@Table(name="DIC_CUESTIONARIO_MEDICO")
public class DicCuestionarioMedico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CUESTIONARIO_MEDICO", nullable=false, precision=22)
	private long cveIdCuestionarioMedico;

	@Column(name="DES_CUESTIONARIO", length=50)
	private String desCuestionario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="TIP_CUESTIONARIO", precision=22)
	private BigDecimal tipCuestionario;

	//bi-directional many-to-one association to DicCuestionarioPregunta
	@OneToMany(mappedBy="dicCuestionarioMedico")
	private List<DicCuestionarioPregunta> dicCuestionarioPreguntas;

	//bi-directional many-to-one association to DitCuestRAsegurado
	@OneToMany(mappedBy="dicCuestionarioMedico")
	private List<DitCuestRAsegurado> ditCuestRAsegurados;

    public DicCuestionarioMedico() {
    }

	public long getCveIdCuestionarioMedico() {
		return this.cveIdCuestionarioMedico;
	}

	public void setCveIdCuestionarioMedico(long cveIdCuestionarioMedico) {
		this.cveIdCuestionarioMedico = cveIdCuestionarioMedico;
	}

	public String getDesCuestionario() {
		return this.desCuestionario;
	}

	public void setDesCuestionario(String desCuestionario) {
		this.desCuestionario = desCuestionario;
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

	public BigDecimal getTipCuestionario() {
		return this.tipCuestionario;
	}

	public void setTipCuestionario(BigDecimal tipCuestionario) {
		this.tipCuestionario = tipCuestionario;
	}

	public List<DicCuestionarioPregunta> getDicCuestionarioPreguntas() {
		return this.dicCuestionarioPreguntas;
	}

	public void setDicCuestionarioPreguntas(List<DicCuestionarioPregunta> dicCuestionarioPreguntas) {
		this.dicCuestionarioPreguntas = dicCuestionarioPreguntas;
	}
	
	public List<DitCuestRAsegurado> getDitCuestRAsegurados() {
		return this.ditCuestRAsegurados;
	}

	public void setDitCuestRAsegurados(List<DitCuestRAsegurado> ditCuestRAsegurados) {
		this.ditCuestRAsegurados = ditCuestRAsegurados;
	}
	
}