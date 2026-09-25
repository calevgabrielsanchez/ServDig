package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptBenefPensDetEstud;

import java.util.Set;


/**
 * The persistent class for the SPC_ORFANDAD database table.
 * 
 */
@Entity
@Table(name="SPC_ORFANDAD")
public class SpcOrfandad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ORFANDAD")
	private String idOrfandad;

	@Column(name="DES_ORFANDAD")
	private String desOrfandad;

	//bi-directional many-to-one association to SptBenefPensDetEstud
	@OneToMany(mappedBy="spcOrfandad")
	private Set<SptBenefPensDetEstud> sptBenefPensDetEstuds;

	//bi-directional many-to-one association to SptComponenteMov
	@OneToMany(mappedBy="spcOrfandad")
	private Set<SptComponenteMov> sptComponenteMovs;

    public SpcOrfandad() {
    }

	public String getIdOrfandad() {
		return this.idOrfandad;
	}

	public void setIdOrfandad(String idOrfandad) {
		this.idOrfandad = idOrfandad;
	}

	public String getDesOrfandad() {
		return this.desOrfandad;
	}

	public void setDesOrfandad(String desOrfandad) {
		this.desOrfandad = desOrfandad;
	}

	public Set<SptBenefPensDetEstud> getSptBenefPensDetEstuds() {
		return this.sptBenefPensDetEstuds;
	}

	public void setSptBenefPensDetEstuds(Set<SptBenefPensDetEstud> sptBenefPensDetEstuds) {
		this.sptBenefPensDetEstuds = sptBenefPensDetEstuds;
	}
	
	public Set<SptComponenteMov> getSptComponenteMovs() {
		return this.sptComponenteMovs;
	}

	public void setSptComponenteMovs(Set<SptComponenteMov> sptComponenteMovs) {
		this.sptComponenteMovs = sptComponenteMovs;
	}
	
}