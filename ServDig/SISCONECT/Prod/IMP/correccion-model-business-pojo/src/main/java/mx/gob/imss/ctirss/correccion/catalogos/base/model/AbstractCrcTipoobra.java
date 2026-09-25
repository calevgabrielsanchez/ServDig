package mx.gob.imss.ctirss.correccion.catalogos.base.model;

import javax.persistence.*;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import java.math.BigDecimal;


/**
 * The persistent class for the CRC_TIPOOBRA database table.
 * 
 */

@MappedSuperclass
public class AbstractCrcTipoobra extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_PK")
	private BigDecimal cvePkTipobra;

	@Column(name="CVE_CODIGO")
	private String cveCodigo;

	@Column(name="DES_TIPOOBRA")
	private String desTipoobra;

	@Column(name="NUM_COEFICIENTECONVENIO")
	private Float numCoeficienteconvenio;

	@Column(name="NUM_HIBERNATE_VERSION")
	private BigDecimal numHibernateVersion;

	@Column(name="TIP_CLASEOBRA")
	private String tipClaseobra;

	@Column(name="TIP_CONVENIO15B")
	private BigDecimal tipConvenio15b;

	//bi-directional many-to-one association to CrtDeteccion
//	@OneToMany(mappedBy="crcTipoobra")
//	private Set<CrtDeteccion> crtDeteccions;

    public AbstractCrcTipoobra() {
    }

	public BigDecimal getCvePkTipobra() {
		return this.cvePkTipobra;
	}

	public void setCvePkTipobra(BigDecimal cvePkTipobra) {
		this.cvePkTipobra = cvePkTipobra;
	}

	public String getCveCodigo() {
		return this.cveCodigo;
	}

	public void setCveCodigo(String cveCodigo) {
		this.cveCodigo = cveCodigo;
	}

	public String getDesTipoobra() {
		return this.desTipoobra;
	}

	public void setDesTipoobra(String desTipoobra) {
		this.desTipoobra = desTipoobra;
	}

	public Float getNumCoeficienteconvenio() {
		return this.numCoeficienteconvenio;
	}

	public void setNumCoeficienteconvenio(Float numCoeficienteconvenio) {
		this.numCoeficienteconvenio = numCoeficienteconvenio;
	}

	public BigDecimal getNumHibernateVersion() {
		return this.numHibernateVersion;
	}

	public void setNumHibernateVersion(BigDecimal numHibernateVersion) {
		this.numHibernateVersion = numHibernateVersion;
	}

	public String getTipClaseobra() {
		return this.tipClaseobra;
	}

	public void setTipClaseobra(String tipClaseobra) {
		this.tipClaseobra = tipClaseobra;
	}

	public BigDecimal getTipConvenio15b() {
		return this.tipConvenio15b;
	}

	public void setTipConvenio15b(BigDecimal tipConvenio15b) {
		this.tipConvenio15b = tipConvenio15b;
	}

//	public Set<CrtDeteccion> getCrtDeteccions() {
//		return this.crtDeteccions;
//	}
//
//	public void setCrtDeteccions(Set<CrtDeteccion> crtDeteccions) {
//		this.crtDeteccions = crtDeteccions;
//	}
	
}