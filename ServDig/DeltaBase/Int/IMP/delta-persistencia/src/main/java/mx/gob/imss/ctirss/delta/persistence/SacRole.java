package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the SAC_ROLES database table.
 * 
 */
@Entity
@Table(name="SAC_ROLES")
public class SacRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_PK", nullable=false, precision=22)
	private long cvePk;

	@Column(name="DES_DESCRIPCION", nullable=false, length=30)
	private String desDescripcion;

	@Column(name="NOM_NOMBRE", nullable=false, length=50)
	private String nomNombre;

	@Column(name="NUM_HIBERNATE_VERSION", nullable=false, precision=22)
	private BigDecimal numHibernateVersion;

	//bi-directional many-to-one association to SacMenuitem
	@OneToMany(mappedBy="sacRole")
	private List<SacMenuitem> sacMenuitems;

	//bi-directional many-to-one association to SatUserrol
	@OneToMany(mappedBy="sacRole")
	private List<SatUserrol> satUserrols;

    public SacRole() {
    }

	public long getCvePk() {
		return this.cvePk;
	}

	public void setCvePk(long cvePk) {
		this.cvePk = cvePk;
	}

	public String getDesDescripcion() {
		return this.desDescripcion;
	}

	public void setDesDescripcion(String desDescripcion) {
		this.desDescripcion = desDescripcion;
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

	public List<SacMenuitem> getSacMenuitems() {
		return this.sacMenuitems;
	}

	public void setSacMenuitems(List<SacMenuitem> sacMenuitems) {
		this.sacMenuitems = sacMenuitems;
	}
	
	public List<SatUserrol> getSatUserrols() {
		return this.satUserrols;
	}

	public void setSatUserrols(List<SatUserrol> satUserrols) {
		this.satUserrols = satUserrols;
	}
	
}