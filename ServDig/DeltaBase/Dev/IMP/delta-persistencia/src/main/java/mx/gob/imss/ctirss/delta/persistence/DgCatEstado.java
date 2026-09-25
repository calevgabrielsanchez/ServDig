package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;


/**
 * The persistent class for the DG_CAT_ESTADO database table.
 * 
 */
@Entity
@Table(name="DG_CAT_ESTADO")
@OnSearchLlavePrimaria(atributos={"cveEnt"})
@ComponentComboCampoDescripcion(atributo="nomEnt")
public class DgCatEstado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ENT", unique=true, nullable=false, length=2)
	private String cveEnt;

	@Column(name="NOM_ENT", nullable=false, length=50)
	private String nomEnt;

	//bi-directional many-to-one association to DicPai
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PAIS")
	private DicPai dicPai;

	//bi-directional one-to-one association to DgCatEstadoCp
	@OneToOne(mappedBy="dgCatEstado", fetch=FetchType.LAZY)
	private DgCatEstadoCp dgCatEstadoCp;

	//bi-directional many-to-one association to DgCatMunicipio
	@OneToMany(mappedBy="dgCatEstado")
	private List<DgCatMunicipio> dgCatMunicipios;

	//bi-directional many-to-one association to DicMunicipioImss
	@OneToMany(mappedBy="dgCatEstado")
	private List<DicMunicipioImss> dicMunicipioImsses;

	//bi-directional many-to-one association to DitPersona
	@OneToMany(mappedBy="dgCatEstado")
	private List<DitPersona> ditPersonas;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_EDO_GEOGRAFICO")
	private Boolean indEdoGeografico;

    public DgCatEstado() {
    }

	public String getCveEnt() {
		return this.cveEnt;
	}

	public void setCveEnt(String cveEnt) {
		this.cveEnt = cveEnt;
	}

	public String getNomEnt() {
		return this.nomEnt;
	}

	public void setNomEnt(String nomEnt) {
		this.nomEnt = nomEnt;
	}

	public DicPai getDicPai() {
		return this.dicPai;
	}

	public void setDicPai(DicPai dicPai) {
		this.dicPai = dicPai;
	}
	
	public DgCatEstadoCp getDgCatEstadoCp() {
		return this.dgCatEstadoCp;
	}

	public void setDgCatEstadoCp(DgCatEstadoCp dgCatEstadoCp) {
		this.dgCatEstadoCp = dgCatEstadoCp;
	}
	
	public List<DgCatMunicipio> getDgCatMunicipios() {
		return this.dgCatMunicipios;
	}

	public void setDgCatMunicipios(List<DgCatMunicipio> dgCatMunicipios) {
		this.dgCatMunicipios = dgCatMunicipios;
	}
	
	public List<DicMunicipioImss> getDicMunicipioImsses() {
		return this.dicMunicipioImsses;
	}

	public void setDicMunicipioImsses(List<DicMunicipioImss> dicMunicipioImsses) {
		this.dicMunicipioImsses = dicMunicipioImsses;
	}
	
	public List<DitPersona> getDitPersonas() {
		return this.ditPersonas;
	}

	public void setDitPersonas(List<DitPersona> ditPersonas) {
		this.ditPersonas = ditPersonas;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Boolean getIndEdoGeografico() {
		return indEdoGeografico;
	}

	public void setIndEdoGeografico(Boolean indEdoGeografico) {
		this.indEdoGeografico = indEdoGeografico;
	}
	
	
}