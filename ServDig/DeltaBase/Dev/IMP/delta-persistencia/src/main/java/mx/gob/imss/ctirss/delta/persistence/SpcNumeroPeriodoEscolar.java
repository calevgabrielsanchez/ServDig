package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptBenefPensDetEstud;

import java.util.Set;


/**
 * The persistent class for the SPC_NUMERO_PERIODO_ESCOLAR database table.
 * 
 */
@Entity
@Table(name="SPC_NUMERO_PERIODO_ESCOLAR")
public class SpcNumeroPeriodoEscolar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_NUM_PERIODO_ESCOLAR")
	private String idNumPeriodoEscolar;

	@Column(name="DES_NUM_PERIODO_ESCOLAR")
	private String desNumPeriodoEscolar;

	//bi-directional many-to-one association to SptBenefPensDetEstud
	@OneToMany(mappedBy="spcNumeroPeriodoEscolar")
	private Set<SptBenefPensDetEstud> sptBenefPensDetEstuds;

	//bi-directional many-to-one association to SptComponenteMov
	@OneToMany(mappedBy="spcNumeroPeriodoEscolar")
	private Set<SptComponenteMov> sptComponenteMovs;

    public SpcNumeroPeriodoEscolar() {
    }

	public String getIdNumPeriodoEscolar() {
		return this.idNumPeriodoEscolar;
	}

	public void setIdNumPeriodoEscolar(String idNumPeriodoEscolar) {
		this.idNumPeriodoEscolar = idNumPeriodoEscolar;
	}

	public String getDesNumPeriodoEscolar() {
		return this.desNumPeriodoEscolar;
	}

	public void setDesNumPeriodoEscolar(String desNumPeriodoEscolar) {
		this.desNumPeriodoEscolar = desNumPeriodoEscolar;
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