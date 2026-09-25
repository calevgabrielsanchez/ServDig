package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptPension;
import mx.gob.imss.ctirss.delta.persistence.SptTramitePension;

import java.util.Set;


/**
 * The persistent class for the SPC_INCIDENCIA database table.
 * 
 */
@Entity
@Table(name="SPC_INCIDENCIA")
public class SpcIncidencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_INCIDENCIA")
	private String idIncidencia;

	@Column(name="DES_INCIDENCIA")
	private String desIncidencia;

	//bi-directional many-to-one association to SptComponenteMov
	@OneToMany(mappedBy="spcIncidencia")
	private Set<SptComponenteMov> sptComponenteMovs;

	//bi-directional many-to-one association to SptGrupoFamiliarMov
	@OneToMany(mappedBy="spcIncidencia")
	private Set<SptGrupoFamiliarMov> sptGrupoFamiliarMovs;

	//bi-directional many-to-one association to SptPension
	@OneToMany(mappedBy="spcIncidencia")
	private Set<SptPension> sptPensions;

	//bi-directional many-to-one association to SptPensionMov
	@OneToMany(mappedBy="spcIncidencia")
	private Set<SptPensionMov> sptPensionMovs;

	//bi-directional many-to-one association to SptTramitePension
	@OneToMany(mappedBy="spcIncidencia")
	private Set<SptTramitePension> sptTramitePensions;

    public SpcIncidencia() {
    }

	public String getIdIncidencia() {
		return this.idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public String getDesIncidencia() {
		return this.desIncidencia;
	}

	public void setDesIncidencia(String desIncidencia) {
		this.desIncidencia = desIncidencia;
	}

	public Set<SptComponenteMov> getSptComponenteMovs() {
		return this.sptComponenteMovs;
	}

	public void setSptComponenteMovs(Set<SptComponenteMov> sptComponenteMovs) {
		this.sptComponenteMovs = sptComponenteMovs;
	}
	
	public Set<SptGrupoFamiliarMov> getSptGrupoFamiliarMovs() {
		return this.sptGrupoFamiliarMovs;
	}

	public void setSptGrupoFamiliarMovs(Set<SptGrupoFamiliarMov> sptGrupoFamiliarMovs) {
		this.sptGrupoFamiliarMovs = sptGrupoFamiliarMovs;
	}
	
	public Set<SptPension> getSptPensions() {
		return this.sptPensions;
	}

	public void setSptPensions(Set<SptPension> sptPensions) {
		this.sptPensions = sptPensions;
	}
	
	public Set<SptPensionMov> getSptPensionMovs() {
		return this.sptPensionMovs;
	}

	public void setSptPensionMovs(Set<SptPensionMov> sptPensionMovs) {
		this.sptPensionMovs = sptPensionMovs;
	}
	
	public Set<SptTramitePension> getSptTramitePensions() {
		return this.sptTramitePensions;
	}

	public void setSptTramitePensions(Set<SptTramitePension> sptTramitePensions) {
		this.sptTramitePensions = sptTramitePensions;
	}
	
}