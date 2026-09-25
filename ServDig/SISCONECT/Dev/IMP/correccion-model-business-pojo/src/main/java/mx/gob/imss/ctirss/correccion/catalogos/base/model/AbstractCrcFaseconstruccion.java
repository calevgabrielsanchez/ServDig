package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the CRC_FASECONSTRUCCION database table.
 * 
 */

@MappedSuperclass
public class AbstractCrcFaseconstruccion extends AbstractModel{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_PK")
	private long cvePkFaseconst;

	@Column(name="CVE_CODIGO")
	private String cveCodigo;

	@Column(name="DES_FASECONSTRUCCION")
	private String desFaseconstruccion;

	@Column(name="NUM_HIBERNATE_VERSION")
	private BigDecimal numHibernateVersion;

	@Column(name="POR_MANOOBRA")
	private BigDecimal porManoobra;

	//bi-directional many-to-one association to CrtDeteccion
//	@OneToMany(mappedBy="crcFaseconstruccion")
//	private Set<CrtDeteccion> crtDeteccions;

    public AbstractCrcFaseconstruccion() {
    }

	public long getCvePkFaseconst() {
		return this.cvePkFaseconst;
	}

	public void setCvePkFaseconst(long cvePkFaseconst) {
		this.cvePkFaseconst = cvePkFaseconst;
	}

	public String getCveCodigo() {
		return this.cveCodigo;
	}

	public void setCveCodigo(String cveCodigo) {
		this.cveCodigo = cveCodigo;
	}

	public String getDesFaseconstruccion() {
		return this.desFaseconstruccion;
	}

	public void setDesFaseconstruccion(String desFaseconstruccion) {
		this.desFaseconstruccion = desFaseconstruccion;
	}

	public BigDecimal getNumHibernateVersion() {
		return this.numHibernateVersion;
	}

	public void setNumHibernateVersion(BigDecimal numHibernateVersion) {
		this.numHibernateVersion = numHibernateVersion;
	}

	public BigDecimal getPorManoobra() {
		return this.porManoobra;
	}

	public void setPorManoobra(BigDecimal porManoobra) {
		this.porManoobra = porManoobra;
	}

//	public Set<CrtDeteccion> getCrtDeteccions() {
//		return this.crtDeteccions;
//	}
//
//	public void setCrtDeteccions(Set<CrtDeteccion> crtDeteccions) {
//		this.crtDeteccions = crtDeteccions;
//	}
	
}