package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DIC_ESPECIALIDAD_MEDICO database table.
 * 
 */
@Entity
@Table(name="DIC_ESPECIALIDAD_MEDICO")
public class DicEspecialidadMedico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ESPECIALIDAD")
	private long cveEspecialidad;

	@Column(name="DES_ESPECIALIDAD")
	private String desEspecialidad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitMedicoEspecialidad
	@OneToMany(mappedBy="dicEspecialidadMedico")
	private Set<DitMedicoEspecialidad> ditMedicoEspecialidads;

    public DicEspecialidadMedico() {
    }

	public long getCveEspecialidad() {
		return this.cveEspecialidad;
	}

	public void setCveEspecialidad(long cveEspecialidad) {
		this.cveEspecialidad = cveEspecialidad;
	}

	public String getDesEspecialidad() {
		return this.desEspecialidad;
	}

	public void setDesEspecialidad(String desEspecialidad) {
		this.desEspecialidad = desEspecialidad;
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

	public Set<DitMedicoEspecialidad> getDitMedicoEspecialidads() {
		return this.ditMedicoEspecialidads;
	}

	public void setDitMedicoEspecialidads(Set<DitMedicoEspecialidad> ditMedicoEspecialidads) {
		this.ditMedicoEspecialidads = ditMedicoEspecialidads;
	}
	
}