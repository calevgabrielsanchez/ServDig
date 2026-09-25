package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptBenefPensDetEstud;

import java.util.Set;


/**
 * The persistent class for the SPC_PERIODO_ESCOLAR database table.
 * 
 */
@Entity
@Table(name="SPC_PERIODO_ESCOLAR")
public class SpcPeriodoEscolar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PERIODO_ESCOLAR")
	private String idPeriodoEscolar;

	@Column(name="DES_CORTA_PERIODO_ESCOLAR")
	private String desCortaPeriodoEscolar;

	@Column(name="DES_LARGA_PERIODO_ESCOLAR")
	private String desLargaPeriodoEscolar;

	//bi-directional many-to-one association to SptBenefPensDetEstud
//	@OneToMany(mappedBy="spcPeriodoEscolar") FIXME no despliega esta relacion
//	private Set<SptBenefPensDetEstud> sptBenefPensDetEstuds;

	//bi-directional many-to-one association to SptComponenteMov
	@OneToMany(mappedBy="spcPeriodoEscolar")
	private Set<SptComponenteMov> sptComponenteMovs;

    public SpcPeriodoEscolar() {
    }

	public String getIdPeriodoEscolar() {
		return this.idPeriodoEscolar;
	}

	public void setIdPeriodoEscolar(String idPeriodoEscolar) {
		this.idPeriodoEscolar = idPeriodoEscolar;
	}

	public String getDesCortaPeriodoEscolar() {
		return this.desCortaPeriodoEscolar;
	}

	public void setDesCortaPeriodoEscolar(String desCortaPeriodoEscolar) {
		this.desCortaPeriodoEscolar = desCortaPeriodoEscolar;
	}

	public String getDesLargaPeriodoEscolar() {
		return this.desLargaPeriodoEscolar;
	}

	public void setDesLargaPeriodoEscolar(String desLargaPeriodoEscolar) {
		this.desLargaPeriodoEscolar = desLargaPeriodoEscolar;
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