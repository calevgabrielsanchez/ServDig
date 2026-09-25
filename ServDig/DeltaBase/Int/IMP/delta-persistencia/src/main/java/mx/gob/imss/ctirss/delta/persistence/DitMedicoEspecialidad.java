package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DIT_MEDICO_ESPECIALIDAD database table.
 * 
 */
@Entity
@Table(name="DIT_MEDICO_ESPECIALIDAD")
public class DitMedicoEspecialidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MEDICO_ESPECIALIDAD")
	private long cveIdMedicoEspecialidad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitCertificadoSitCritica
	@OneToMany(mappedBy="ditMedicoEspecialidad")
	private Set<DitCertificadoSitCritica> ditCertificadoSitCriticas;

	//bi-directional many-to-one association to DitDictBeneficiarioInca
	@OneToMany(mappedBy="ditMedicoEspecialidad")
	private Set<DitDictBeneficiarioInca> ditDictBeneficiarioIncas;

	//bi-directional many-to-one association to DicEspecialidadMedico
    @ManyToOne
	@JoinColumn(name="CVE_ESPECIALIDAD")
	private DicEspecialidadMedico dicEspecialidadMedico;

	//bi-directional many-to-one association to DicMedico
    @ManyToOne
	@JoinColumn(name="CVE_ID_MEDICO")
	private DicMedico dicMedico;

	//bi-directional many-to-one association to DitObstetrico
	@OneToMany(mappedBy="ditMedicoEspecialidad")
	private Set<DitObstetrico> ditObstetricos;

	//bi-directional many-to-one association to DitUmfConsTurnoMedico
	@OneToMany(mappedBy="ditMedicoEspecialidad")
	private Set<DitUmfConsTurnoMedico> ditUmfConsTurnoMedicos;

    public DitMedicoEspecialidad() {
    }

	public long getCveIdMedicoEspecialidad() {
		return this.cveIdMedicoEspecialidad;
	}

	public void setCveIdMedicoEspecialidad(long cveIdMedicoEspecialidad) {
		this.cveIdMedicoEspecialidad = cveIdMedicoEspecialidad;
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

	public Set<DitCertificadoSitCritica> getDitCertificadoSitCriticas() {
		return this.ditCertificadoSitCriticas;
	}

	public void setDitCertificadoSitCriticas(Set<DitCertificadoSitCritica> ditCertificadoSitCriticas) {
		this.ditCertificadoSitCriticas = ditCertificadoSitCriticas;
	}
	
	public Set<DitDictBeneficiarioInca> getDitDictBeneficiarioIncas() {
		return this.ditDictBeneficiarioIncas;
	}

	public void setDitDictBeneficiarioIncas(Set<DitDictBeneficiarioInca> ditDictBeneficiarioIncas) {
		this.ditDictBeneficiarioIncas = ditDictBeneficiarioIncas;
	}
	
	public DicEspecialidadMedico getDicEspecialidadMedico() {
		return this.dicEspecialidadMedico;
	}

	public void setDicEspecialidadMedico(DicEspecialidadMedico dicEspecialidadMedico) {
		this.dicEspecialidadMedico = dicEspecialidadMedico;
	}
	
	public DicMedico getDicMedico() {
		return this.dicMedico;
	}

	public void setDicMedico(DicMedico dicMedico) {
		this.dicMedico = dicMedico;
	}
	
	public Set<DitObstetrico> getDitObstetricos() {
		return this.ditObstetricos;
	}

	public void setDitObstetricos(Set<DitObstetrico> ditObstetricos) {
		this.ditObstetricos = ditObstetricos;
	}
	
	public Set<DitUmfConsTurnoMedico> getDitUmfConsTurnoMedicos() {
		return this.ditUmfConsTurnoMedicos;
	}

	public void setDitUmfConsTurnoMedicos(Set<DitUmfConsTurnoMedico> ditUmfConsTurnoMedicos) {
		this.ditUmfConsTurnoMedicos = ditUmfConsTurnoMedicos;
	}
	
}