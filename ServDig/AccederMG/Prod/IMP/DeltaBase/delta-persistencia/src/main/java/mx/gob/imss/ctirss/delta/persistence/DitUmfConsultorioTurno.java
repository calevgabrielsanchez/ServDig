package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DIT_UMF_CONSULTORIO_TURNO database table.
 * 
 */
@Entity
@Table(name="DIT_UMF_CONSULTORIO_TURNO")
public class DitUmfConsultorioTurno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_UMF_CONS_TURNO")
	private long cveIdUmfConsTurno;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicConsultorioUmf
    @ManyToOne
	@JoinColumn(name="CVE_ID_UMF_CONSULTORIO")
	private DicConsultorioUmf dicConsultorioUmf;

	//bi-directional many-to-one association to DicTurno
    @ManyToOne
	@JoinColumn(name="CVE_ID_TURNO")
	private DicTurno dicTurno;

	//bi-directional many-to-one association to DitUmfConsTurnoMedico
	@OneToMany(mappedBy="ditUmfConsultorioTurno")
	private Set<DitUmfConsTurnoMedico> ditUmfConsTurnoMedicos;

    public DitUmfConsultorioTurno() {
    }

	public long getCveIdUmfConsTurno() {
		return this.cveIdUmfConsTurno;
	}

	public void setCveIdUmfConsTurno(long cveIdUmfConsTurno) {
		this.cveIdUmfConsTurno = cveIdUmfConsTurno;
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

	public DicConsultorioUmf getDicConsultorioUmf() {
		return this.dicConsultorioUmf;
	}

	public void setDicConsultorioUmf(DicConsultorioUmf dicConsultorioUmf) {
		this.dicConsultorioUmf = dicConsultorioUmf;
	}
	
	public DicTurno getDicTurno() {
		return this.dicTurno;
	}

	public void setDicTurno(DicTurno dicTurno) {
		this.dicTurno = dicTurno;
	}
	
	public Set<DitUmfConsTurnoMedico> getDitUmfConsTurnoMedicos() {
		return this.ditUmfConsTurnoMedicos;
	}

	public void setDitUmfConsTurnoMedicos(Set<DitUmfConsTurnoMedico> ditUmfConsTurnoMedicos) {
		this.ditUmfConsTurnoMedicos = ditUmfConsTurnoMedicos;
	}
	
}