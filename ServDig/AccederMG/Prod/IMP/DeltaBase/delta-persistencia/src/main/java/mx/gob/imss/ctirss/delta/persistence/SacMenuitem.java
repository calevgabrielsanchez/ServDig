package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the SAC_MENUITEM database table.
 * 
 */
@Entity
@Table(name="SAC_MENUITEM")
public class SacMenuitem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_PK", nullable=false, precision=22)
	private long cvePk;

	@Column(name="DES_ETIQUETA", nullable=false, length=100)
	private String desEtiqueta;

	@Column(name="DES_URL", length=255)
	private String desUrl;

	@Column(name="NUM_HIBERNATE_VERSION", nullable=false, precision=22)
	private BigDecimal numHibernateVersion;

	@Column(name="NUM_ORDEN", nullable=false, precision=22)
	private BigDecimal numOrden;

	//bi-directional many-to-one association to SacRole
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_FK_ROL")
	private SacRole sacRole;

	//bi-directional many-to-one association to SacMenuitem
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_FK_MENUITEM")
	private SacMenuitem sacMenuitem;

	//bi-directional many-to-one association to SacMenuitem
	@OneToMany(mappedBy="sacMenuitem")
	private List<SacMenuitem> sacMenuitems;

    public SacMenuitem() {
    }

	public long getCvePk() {
		return this.cvePk;
	}

	public void setCvePk(long cvePk) {
		this.cvePk = cvePk;
	}

	public String getDesEtiqueta() {
		return this.desEtiqueta;
	}

	public void setDesEtiqueta(String desEtiqueta) {
		this.desEtiqueta = desEtiqueta;
	}

	public String getDesUrl() {
		return this.desUrl;
	}

	public void setDesUrl(String desUrl) {
		this.desUrl = desUrl;
	}

	public BigDecimal getNumHibernateVersion() {
		return this.numHibernateVersion;
	}

	public void setNumHibernateVersion(BigDecimal numHibernateVersion) {
		this.numHibernateVersion = numHibernateVersion;
	}

	public BigDecimal getNumOrden() {
		return this.numOrden;
	}

	public void setNumOrden(BigDecimal numOrden) {
		this.numOrden = numOrden;
	}

	public SacRole getSacRole() {
		return this.sacRole;
	}

	public void setSacRole(SacRole sacRole) {
		this.sacRole = sacRole;
	}
	
	public SacMenuitem getSacMenuitem() {
		return this.sacMenuitem;
	}

	public void setSacMenuitem(SacMenuitem sacMenuitem) {
		this.sacMenuitem = sacMenuitem;
	}
	
	public List<SacMenuitem> getSacMenuitems() {
		return this.sacMenuitems;
	}

	public void setSacMenuitems(List<SacMenuitem> sacMenuitems) {
		this.sacMenuitems = sacMenuitems;
	}
	
}