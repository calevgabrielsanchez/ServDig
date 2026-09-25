package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptCabeceraSolicPension;

import java.util.Set;


/**
 * The persistent class for the SPC_ASEGURADORA database table.
 * 
 */
@Entity
@Table(name="SPC_ASEGURADORA")
public class SpcAseguradora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ASEGURADORA")
	private String idAseguradora;

	@Column(name="DES_ASEGURADORA")
	private String desAseguradora;

	@Column(name="IND_VIGENCIA")
	private String indVigencia;

	//bi-directional many-to-one association to AptEnvioPreResolucionEnv
	@OneToMany(mappedBy="spcAseguradora")
	private Set<AptEnvioPreResolucionEnv> aptEnvioPreResolucionEnvs;

	//bi-directional many-to-one association to AptOfertasCnsf
	@OneToMany(mappedBy="spcAseguradora")
	private Set<AptOfertasCnsf> aptOfertasCnsfs;

	//bi-directional many-to-one association to AptResolucionCnsf
	@OneToMany(mappedBy="spcAseguradora")
	private Set<AptResolucionCnsf> aptResolucionCnsfs;

	//bi-directional many-to-one association to SptCabeceraSolicPension
//	@OneToMany(mappedBy="spcAseguradora") FIXME falta mapear relacion
//	private Set<SptCabeceraSolicPension> sptCabeceraSolicPensions;

    public SpcAseguradora() {
    }

	public String getIdAseguradora() {
		return this.idAseguradora;
	}

	public void setIdAseguradora(String idAseguradora) {
		this.idAseguradora = idAseguradora;
	}

	public String getDesAseguradora() {
		return this.desAseguradora;
	}

	public void setDesAseguradora(String desAseguradora) {
		this.desAseguradora = desAseguradora;
	}

	public String getIndVigencia() {
		return this.indVigencia;
	}

	public void setIndVigencia(String indVigencia) {
		this.indVigencia = indVigencia;
	}

	public Set<AptEnvioPreResolucionEnv> getAptEnvioPreResolucionEnvs() {
		return this.aptEnvioPreResolucionEnvs;
	}

	public void setAptEnvioPreResolucionEnvs(Set<AptEnvioPreResolucionEnv> aptEnvioPreResolucionEnvs) {
		this.aptEnvioPreResolucionEnvs = aptEnvioPreResolucionEnvs;
	}
	
	public Set<AptOfertasCnsf> getAptOfertasCnsfs() {
		return this.aptOfertasCnsfs;
	}

	public void setAptOfertasCnsfs(Set<AptOfertasCnsf> aptOfertasCnsfs) {
		this.aptOfertasCnsfs = aptOfertasCnsfs;
	}
	
	public Set<AptResolucionCnsf> getAptResolucionCnsfs() {
		return this.aptResolucionCnsfs;
	}

	public void setAptResolucionCnsfs(Set<AptResolucionCnsf> aptResolucionCnsfs) {
		this.aptResolucionCnsfs = aptResolucionCnsfs;
	}
	
//	public Set<SptCabeceraSolicPension> getSptCabeceraSolicPensions() {
//		return this.sptCabeceraSolicPensions;
//	}
//
//	public void setSptCabeceraSolicPensions(Set<SptCabeceraSolicPension> sptCabeceraSolicPensions) {
//		this.sptCabeceraSolicPensions = sptCabeceraSolicPensions;
//	}
	
}