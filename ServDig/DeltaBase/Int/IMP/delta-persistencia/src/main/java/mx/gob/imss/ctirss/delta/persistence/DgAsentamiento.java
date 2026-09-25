package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the DG_ASENTAMIENTO database table.
 * 
 */
@Entity
@Table(name="DG_ASENTAMIENTO")
public class DgAsentamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DgAsentamientoPK id;

	@Column(length=1)
	private String agregado;

	@Column(name="NOM_ASEN", nullable=false, length=255)
	private String nomAsen;

	//bi-directional many-to-one association to DgCatTipoAsen
	@ManyToOne( fetch=FetchType.EAGER )
	@JoinColumn(name="CVE_TIPO_ASEN", nullable=false)
	private DgCatTipoAsen dgCatTipoAsen;

	
	//bi-directional many-to-one association to DgCodigosPostale
	@OneToMany(mappedBy="dgAsentamiento")
	private List<DgCodigosPostale> dgCodigosPostales;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@OneToMany(mappedBy="dgAsentamiento")
	private List<DgDomicilioGeografico> dgDomicilioGeograficos;
	
	//bi-directional many-to-one association to DgCatMunicipio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ENT", referencedColumnName="CVE_ENT", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_MUN", referencedColumnName="CVE_MUN", nullable=false, insertable=false, updatable=false)
		})
	private DgCatMunicipio dgCatMunicipio;


    public DgAsentamiento() {
    }

	public DgAsentamientoPK getId() {
		return this.id;
	}

	public void setId(DgAsentamientoPK id) {
		this.id = id;
	}
	
	public String getAgregado() {
		return this.agregado;
	}

	public void setAgregado(String agregado) {
		this.agregado = agregado;
	}

	public String getNomAsen() {
		return this.nomAsen;
	}

	public void setNomAsen(String nomAsen) {
		this.nomAsen = nomAsen;
	}

	public DgCatTipoAsen getDgCatTipoAsen() {
		return this.dgCatTipoAsen;
	}

	public void setDgCatTipoAsen(DgCatTipoAsen dgCatTipoAsen) {
		this.dgCatTipoAsen = dgCatTipoAsen;
	}
	

	
	public DgCatMunicipio getDgCatMunicipio() {
		return this.dgCatMunicipio;
	}

	public void setDgCatMunicipio(DgCatMunicipio dgCatMunicipio) {
		this.dgCatMunicipio = dgCatMunicipio;
	}	
		
	public List<DgCodigosPostale> getDgCodigosPostales() {
		return this.dgCodigosPostales;
	}

	public void setDgCodigosPostales(List<DgCodigosPostale> dgCodigosPostales) {
		this.dgCodigosPostales = dgCodigosPostales;
	}
	
	public List<DgDomicilioGeografico> getDgDomicilioGeograficos() {
		return this.dgDomicilioGeograficos;
	}

	public void setDgDomicilioGeograficos(List<DgDomicilioGeografico> dgDomicilioGeograficos) {
		this.dgDomicilioGeograficos = dgDomicilioGeograficos;
	}
	
}