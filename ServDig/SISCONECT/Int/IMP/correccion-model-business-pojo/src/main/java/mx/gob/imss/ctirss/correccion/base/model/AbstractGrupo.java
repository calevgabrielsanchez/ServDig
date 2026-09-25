package mx.gob.imss.ctirss.correccion.base.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.Division;
import mx.gob.imss.ctirss.correccion.model.Fraccion;



/**
 * The persistent class for the DIC_GRUPO database table.
 * 
 */
@MappedSuperclass
public abstract class AbstractGrupo extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_GRUPO_CVEIDGRUPO_GENERATOR", sequenceName="SEQ_DIC_GRUPO")
	@GeneratedValue(generator="DIC_GRUPO_CVEIDGRUPO_GENERATOR")
	@Column(name="CVE_ID_GRUPO")
	public long cveIdGrupo;

	@Column(name="DES_GRUPO")
	private String desGrupo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_GRUPO")
	private String numGrupo;

	//bi-directional many-to-one association to Fraccion
	@OneToMany(mappedBy="dicGrupo")
	private Set<Fraccion> dicFraccions;

	//bi-directional many-to-one association to Division
    @ManyToOne
	@JoinColumn(name="CVE_ID_DIVISION")
	private Division dicDivision;

    public AbstractGrupo() {
    }

	public long getCveIdGrupo() {
		return this.cveIdGrupo;
	}

	public void setCveIdGrupo(long cveIdGrupo) {
		this.cveIdGrupo = cveIdGrupo;
	}

	public String getDesGrupo() {
		return this.desGrupo;
	}

	public void setDesGrupo(String desGrupo) {
		this.desGrupo = desGrupo;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public String getNumGrupo() {
		return this.numGrupo;
	}

	public void setNumGrupo(String numGrupo) {
		this.numGrupo = numGrupo;
	}

	public Set<Fraccion> getDicFraccions() {
		return this.dicFraccions;
	}

	public void setDicFraccions(Set<Fraccion> dicFraccions) {
		this.dicFraccions = dicFraccions;
	}
	
	public Division getDicDivision() {
		return this.dicDivision;
	}

	public void setDicDivision(Division dicDivision) {
		this.dicDivision = dicDivision;
	}
	
}