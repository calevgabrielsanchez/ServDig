package mx.gob.imss.ctirss.correccion.base.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.DicGrupo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;



/**
 * The persistent class for the DIC_DIVISION database table.
 * 
 */
@JsonIgnoreProperties({ "dicGrupos"})
@MappedSuperclass
public abstract class AbstractDicDivision extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_DIVISION_CVEIDDIVISION_GENERATOR", sequenceName="SEQ_DIC_DIVISION")
	@GeneratedValue(generator="DIC_DIVISION_CVEIDDIVISION_GENERATOR")
	@Column(name="CVE_ID_DIVISION")
	public long cveIdDivision;

	@Column(name="DES_DIVISION")
	private String desDivision;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_DIVISION")
	private String numDivision;

	//bi-directional many-to-one association to AbstractDicGrupo
	@OneToMany(mappedBy="dicDivision")
	private Set<DicGrupo> dicGrupos;

    public AbstractDicDivision() {
    }

	public long getCveIdDivision() {
		return this.cveIdDivision;
	}

	public void setCveIdDivision(long cveIdDivision) {
		this.cveIdDivision = cveIdDivision;
	}

	public String getDesDivision() {
		return this.desDivision;
	}

	public void setDesDivision(String desDivision) {
		this.desDivision = desDivision;
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

	public String getNumDivision() {
		return this.numDivision;
	}

	public void setNumDivision(String numDivision) {
		this.numDivision = numDivision;
	}

	public Set<DicGrupo> getDicGrupos() {
		return this.dicGrupos;
	}

	public void setDicGrupos(Set<DicGrupo> dicGrupos) {
		this.dicGrupos = dicGrupos;
	}
	
}