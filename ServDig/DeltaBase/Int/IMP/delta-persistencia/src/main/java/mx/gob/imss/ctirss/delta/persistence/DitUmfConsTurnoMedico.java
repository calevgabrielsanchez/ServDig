package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_UMF_CONS_TURNO_MEDICO database table.
 * 
 */
@Entity
@Table(name="DIT_UMF_CONS_TURNO_MEDICO")
public class DitUmfConsTurnoMedico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_UMF_CONS_TURNO_MED")
	private long cveIdUmfConsTurnoMed;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitCambioMasivoClinica
	@OneToMany(mappedBy="ditUmfConsTurnoMedicoO")
	private List<DitCambioMasivoClinica> ditCambioMasivoClinicasO;

	//bi-directional many-to-one association to DitCambioMasivoClinica
	@OneToMany(mappedBy="ditUmfConsTurnoMedicoD", fetch = FetchType.LAZY)
	private List<DitCambioMasivoClinica> ditCambioMasivoClinicasD;

	
	//bi-directional many-to-one association to DitCircunscripcionForanea
	@OneToMany(mappedBy="ditUmfConsTurnoMedico1")
	private List<DitCircunscripcionForanea> ditCircunscripcionForaneas1;

	//bi-directional many-to-one association to DitCircunscripcionForanea
	@OneToMany(mappedBy="ditUmfConsTurnoMedico2")
	private List<DitCircunscripcionForanea> ditCircunscripcionForaneas2;

	//bi-directional many-to-one association to DitGrupoFamiliar
	@OneToMany(mappedBy="ditUmfConsTurnoMedico")
	private List<DitGrupoFamiliar> ditGrupoFamiliars;

	//bi-directional many-to-one association to DitMedicoEspecialidad
    @ManyToOne
	@JoinColumn(name="CVE_ID_MEDICO_ESPECIALIDAD")
	private DitMedicoEspecialidad ditMedicoEspecialidad;

	//bi-directional many-to-one association to DitUmfConsultorioTurno
    @ManyToOne
	@JoinColumn(name="CVE_ID_UMF_CONS_TURNO")
	private DitUmfConsultorioTurno ditUmfConsultorioTurno;

    public DitUmfConsTurnoMedico() {
    }

	public long getCveIdUmfConsTurnoMed() {
		return this.cveIdUmfConsTurnoMed;
	}

	public void setCveIdUmfConsTurnoMed(long cveIdUmfConsTurnoMed) {
		this.cveIdUmfConsTurnoMed = cveIdUmfConsTurnoMed;
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

	
	public List<DitCircunscripcionForanea> getDitCircunscripcionForaneas1() {
		return this.ditCircunscripcionForaneas1;
	}

	public void setDitCircunscripcionForaneas1(List<DitCircunscripcionForanea> ditCircunscripcionForaneas1) {
		this.ditCircunscripcionForaneas1 = ditCircunscripcionForaneas1;
	}
	
	public List<DitCircunscripcionForanea> getDitCircunscripcionForaneas2() {
		return this.ditCircunscripcionForaneas2;
	}

	public void setDitCircunscripcionForaneas2(List<DitCircunscripcionForanea> ditCircunscripcionForaneas2) {
		this.ditCircunscripcionForaneas2 = ditCircunscripcionForaneas2;
	}
	
	public List<DitGrupoFamiliar> getDitGrupoFamiliars() {
		return this.ditGrupoFamiliars;
	}

	public void setDitGrupoFamiliars(List<DitGrupoFamiliar> ditGrupoFamiliars) {
		this.ditGrupoFamiliars = ditGrupoFamiliars;
	}
	
	public DitMedicoEspecialidad getDitMedicoEspecialidad() {
		return this.ditMedicoEspecialidad;
	}

	public void setDitMedicoEspecialidad(DitMedicoEspecialidad ditMedicoEspecialidad) {
		this.ditMedicoEspecialidad = ditMedicoEspecialidad;
	}
	
	public DitUmfConsultorioTurno getDitUmfConsultorioTurno() {
		return this.ditUmfConsultorioTurno;
	}

	public void setDitUmfConsultorioTurno(DitUmfConsultorioTurno ditUmfConsultorioTurno) {
		this.ditUmfConsultorioTurno = ditUmfConsultorioTurno;
	}

	public List<DitCambioMasivoClinica> getDitCambioMasivoClinicasO() {
		return ditCambioMasivoClinicasO;
	}

	public void setDitCambioMasivoClinicasO(
			List<DitCambioMasivoClinica> ditCambioMasivoClinicasO) {
		this.ditCambioMasivoClinicasO = ditCambioMasivoClinicasO;
	}

	public List<DitCambioMasivoClinica> getDitCambioMasivoClinicasD() {
		return ditCambioMasivoClinicasD;
	}

	public void setDitCambioMasivoClinicasD(
			List<DitCambioMasivoClinica> ditCambioMasivoClinicasD) {
		this.ditCambioMasivoClinicasD = ditCambioMasivoClinicasD;
	}

	
	
	
}