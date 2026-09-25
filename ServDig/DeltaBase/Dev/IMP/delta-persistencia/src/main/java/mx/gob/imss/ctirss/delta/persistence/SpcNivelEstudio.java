package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptBenefPensDetEstud;

import java.util.Set;


/**
 * The persistent class for the SPC_NIVEL_ESTUDIOS database table.
 * 
 */
@Entity
@Table(name="SPC_NIVEL_ESTUDIOS")
public class SpcNivelEstudio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_NIVEL_ESTUDIOS")
	private String idNivelEstudios;

	@Column(name="DES_NIVEL_ESTUDIOS")
	private String desNivelEstudios;

	//bi-directional many-to-one association to SptBenefPensDetEstud
//	@OneToMany(mappedBy="spcNivelEstudio") FIXME no despliega esta relacion
//	private Set<SptBenefPensDetEstud> sptBenefPensDetEstuds;

	//bi-directional many-to-one association to SptComponenteMov
	@OneToMany(mappedBy="spcNivelEstudio")
	private Set<SptComponenteMov> sptComponenteMovs;

    public SpcNivelEstudio() {
    }

	public String getIdNivelEstudios() {
		return this.idNivelEstudios;
	}

	public void setIdNivelEstudios(String idNivelEstudios) {
		this.idNivelEstudios = idNivelEstudios;
	}

	public String getDesNivelEstudios() {
		return this.desNivelEstudios;
	}

	public void setDesNivelEstudios(String desNivelEstudios) {
		this.desNivelEstudios = desNivelEstudios;
	}

//	public Set<SptBenefPensDetEstud> getSptBenefPensDetEstuds() {
//		return this.sptBenefPensDetEstuds;
//	}
//
//	public void setSptBenefPensDetEstuds(Set<SptBenefPensDetEstud> sptBenefPensDetEstuds) {
//		this.sptBenefPensDetEstuds = sptBenefPensDetEstuds;
//	}
	
	public Set<SptComponenteMov> getSptComponenteMovs() {
		return this.sptComponenteMovs;
	}

	public void setSptComponenteMovs(Set<SptComponenteMov> sptComponenteMovs) {
		this.sptComponenteMovs = sptComponenteMovs;
	}
	
}