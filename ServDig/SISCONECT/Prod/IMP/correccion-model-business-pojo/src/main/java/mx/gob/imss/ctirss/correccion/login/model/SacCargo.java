package mx.gob.imss.ctirss.correccion.login.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the SAC_CARGO database table.
 * 
 */
@Entity
@Table(name="SAC_CARGO")
public class SacCargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_PK")
	private long cvePk;

	@Column(name="CVE_CODIGO")
	private String cveCodigo;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	@Column(name="NUM_HIBERNATE_VERSION")
	private BigDecimal numHibernateVersion;

	//bi-directional many-to-one association to SegUsuarioFuncionario
	@OneToMany(mappedBy="sacCargo")
	private Set<SegUsuarioFuncionario> segUsuarioFuncionarios;

    public SacCargo() {
    }

	public long getCvePk() {
		return this.cvePk;
	}

	public void setCvePk(long cvePk) {
		this.cvePk = cvePk;
	}

	public String getCveCodigo() {
		return this.cveCodigo;
	}

	public void setCveCodigo(String cveCodigo) {
		this.cveCodigo = cveCodigo;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public BigDecimal getNumHibernateVersion() {
		return this.numHibernateVersion;
	}

	public void setNumHibernateVersion(BigDecimal numHibernateVersion) {
		this.numHibernateVersion = numHibernateVersion;
	}

	public Set<SegUsuarioFuncionario> getSegUsuarioFuncionarios() {
		return this.segUsuarioFuncionarios;
	}

	public void setSegUsuarioFuncionarios(Set<SegUsuarioFuncionario> segUsuarioFuncionarios) {
		this.segUsuarioFuncionarios = segUsuarioFuncionarios;
	}
	
}