package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DIC_MEDICO database table.
 * 
 */
@Entity
@Table(name="DIC_MEDICO")
public class DicMedico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MEDICO")
	private long cveIdMedico;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NOM_PRIMER_APELLIDO")
	private String nomPrimerApellido;

	@Column(name="NOM_SEGUNDO_APELLIDO")
	private String nomSegundoApellido;

	@Column(name="NUM_MEDFAM_MATRICULA")
	private String numMedfamMatricula;


	//bi-directional many-to-one association to DitMedicoEspecialidad
	@OneToMany(mappedBy="dicMedico")
	private Set<DitMedicoEspecialidad> ditMedicoEspecialidads;


    public DicMedico() {
    }

	public long getCveIdMedico() {
		return this.cveIdMedico;
	}

	public void setCveIdMedico(long cveIdMedico) {
		this.cveIdMedico = cveIdMedico;
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

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPrimerApellido() {
		return this.nomPrimerApellido;
	}

	public void setNomPrimerApellido(String nomPrimerApellido) {
		this.nomPrimerApellido = nomPrimerApellido;
	}

	public String getNomSegundoApellido() {
		return this.nomSegundoApellido;
	}

	public void setNomSegundoApellido(String nomSegundoApellido) {
		this.nomSegundoApellido = nomSegundoApellido;
	}

	public String getNumMedfamMatricula() {
		return this.numMedfamMatricula;
	}

	public void setNumMedfamMatricula(String numMedfamMatricula) {
		this.numMedfamMatricula = numMedfamMatricula;
	}

	
	
	public Set<DitMedicoEspecialidad> getDitMedicoEspecialidads() {
		return this.ditMedicoEspecialidads;
	}

	public void setDitMedicoEspecialidads(Set<DitMedicoEspecialidad> ditMedicoEspecialidads) {
		this.ditMedicoEspecialidads = ditMedicoEspecialidads;
	}
	
	
}