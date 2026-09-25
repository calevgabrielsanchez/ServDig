package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptBenefPensDetEstud;

import java.util.Set;


/**
 * The persistent class for the SPC_CALENDARIO_ESCOLAR database table.
 * 
 */
@Entity
@Table(name="SPC_CALENDARIO_ESCOLAR")
public class SpcCalendarioEscolar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_CALENDARIO_ESCOLAR")
	private String idCalendarioEscolar;

	@Column(name="DES_CORTA_CALENDARIO_ESCOLAR")
	private String desCortaCalendarioEscolar;

	@Column(name="DES_LARGA_CALENDARIO_ESCOLAR")
	private String desLargaCalendarioEscolar;

	//bi-directional many-to-one association to SptBenefPensDetEstud
	//@OneToMany(mappedBy="spcCalendarioEscolar") FIXME no despliega esta relacion
	//private Set<SptBenefPensDetEstud> sptBenefPensDetEstuds;

	//bi-directional many-to-one association to SptComponenteMov
	@OneToMany(mappedBy="spcCalendarioEscolar")
	private Set<SptComponenteMov> sptComponenteMovs;

    public SpcCalendarioEscolar() {
    }

	public String getIdCalendarioEscolar() {
		return this.idCalendarioEscolar;
	}

	public void setIdCalendarioEscolar(String idCalendarioEscolar) {
		this.idCalendarioEscolar = idCalendarioEscolar;
	}

	public String getDesCortaCalendarioEscolar() {
		return this.desCortaCalendarioEscolar;
	}

	public void setDesCortaCalendarioEscolar(String desCortaCalendarioEscolar) {
		this.desCortaCalendarioEscolar = desCortaCalendarioEscolar;
	}

	public String getDesLargaCalendarioEscolar() {
		return this.desLargaCalendarioEscolar;
	}

	public void setDesLargaCalendarioEscolar(String desLargaCalendarioEscolar) {
		this.desLargaCalendarioEscolar = desLargaCalendarioEscolar;
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