package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the SAT_USER database table.
 * 
 */
@Entity
@Table(name="SAT_USER")
public class SatUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_PK", nullable=false, precision=22)
	private long cvePk;

	@Column(name="CLV_PASSWORD", nullable=false, length=10)
	private String clvPassword;

	@Column(name="CVE_FK_FUNCIONARIO", nullable=false, precision=22)
	private BigDecimal cveFkFuncionario;

	@Column(name="IND_DEFECTO", nullable=false, precision=22)
	private BigDecimal indDefecto;

	@Column(name="NOM_USUARIO", nullable=false, length=10)
	private String nomUsuario;

	@Column(name="NUM_HIBERNATE_VERSION", nullable=false, precision=22)
	private BigDecimal numHibernateVersion;

	//bi-directional many-to-one association to SatUserrol
	@OneToMany(mappedBy="satUser")
	private List<SatUserrol> satUserrols;

    public SatUser() {
    }

	public long getCvePk() {
		return this.cvePk;
	}

	public void setCvePk(long cvePk) {
		this.cvePk = cvePk;
	}

	public String getClvPassword() {
		return this.clvPassword;
	}

	public void setClvPassword(String clvPassword) {
		this.clvPassword = clvPassword;
	}

	public BigDecimal getCveFkFuncionario() {
		return this.cveFkFuncionario;
	}

	public void setCveFkFuncionario(BigDecimal cveFkFuncionario) {
		this.cveFkFuncionario = cveFkFuncionario;
	}

	public BigDecimal getIndDefecto() {
		return this.indDefecto;
	}

	public void setIndDefecto(BigDecimal indDefecto) {
		this.indDefecto = indDefecto;
	}

	public String getNomUsuario() {
		return this.nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}

	public BigDecimal getNumHibernateVersion() {
		return this.numHibernateVersion;
	}

	public void setNumHibernateVersion(BigDecimal numHibernateVersion) {
		this.numHibernateVersion = numHibernateVersion;
	}

	public List<SatUserrol> getSatUserrols() {
		return this.satUserrols;
	}

	public void setSatUserrols(List<SatUserrol> satUserrols) {
		this.satUserrols = satUserrols;
	}
	
}