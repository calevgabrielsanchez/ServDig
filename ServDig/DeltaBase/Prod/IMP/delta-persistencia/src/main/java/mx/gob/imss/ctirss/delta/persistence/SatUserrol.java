package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the SAT_USERROL database table.
 * 
 */
@Entity
@Table(name="SAT_USERROL")
public class SatUserrol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_PK", nullable=false, precision=22)
	private long cvePk;

	@Column(name="NUM_HIBERNATE_VERSION", nullable=false, precision=22)
	private BigDecimal numHibernateVersion;

	//bi-directional many-to-one association to SacRole
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_FK_ROL", nullable=false)
	private SacRole sacRole;

	//bi-directional many-to-one association to SatUser
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_FK_USER", nullable=false)
	private SatUser satUser;

    public SatUserrol() {
    }

	public long getCvePk() {
		return this.cvePk;
	}

	public void setCvePk(long cvePk) {
		this.cvePk = cvePk;
	}

	public BigDecimal getNumHibernateVersion() {
		return this.numHibernateVersion;
	}

	public void setNumHibernateVersion(BigDecimal numHibernateVersion) {
		this.numHibernateVersion = numHibernateVersion;
	}

	public SacRole getSacRole() {
		return this.sacRole;
	}

	public void setSacRole(SacRole sacRole) {
		this.sacRole = sacRole;
	}
	
	public SatUser getSatUser() {
		return this.satUser;
	}

	public void setSatUser(SatUser satUser) {
		this.satUser = satUser;
	}
	
}