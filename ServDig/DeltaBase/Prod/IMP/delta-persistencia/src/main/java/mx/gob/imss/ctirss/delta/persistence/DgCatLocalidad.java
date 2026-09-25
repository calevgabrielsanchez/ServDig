package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the DG_CAT_LOCALIDAD database table.
 * 
 */
@Entity
@Table(name="DG_CAT_LOCALIDAD")
public class DgCatLocalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DgCatLocalidadPK id;

	@Column(length=1)
	private String agregada;

	@Column(name="NOM_LOC", nullable=false, length=100)
	private String nomLoc;

	//bi-directional many-to-one association to DgAsentamiento
	/*
	@OneToMany(mappedBy="dgCatLocalidad")
	private List<DgAsentamiento> dgAsentamientos;
	 */
	
	//bi-directional many-to-one association to DgCatPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_PERIODO", nullable=false, insertable=false, updatable=false)
	private DgCatPeriodo dgCatPeriodo;

	//bi-directional many-to-one association to DgCatMunicipio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ENT", referencedColumnName="CVE_ENT", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_MUN", referencedColumnName="CVE_MUN", nullable=false, insertable=false, updatable=false)
		})
	private DgCatMunicipio dgCatMunicipio;

	//bi-directional many-to-one association to DgCatAmbito
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="AMBITO", nullable=false)
	private DgCatAmbito dgCatAmbito;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@OneToMany(mappedBy="dgCatLocalidad")
	private List<DgDomicilioGeografico> dgDomicilioGeograficos;

	//bi-directional many-to-one association to DgVialidad
	@OneToMany(mappedBy="dgCatLocalidad")
	private List<DgVialidad> dgVialidads;

	//bi-directional many-to-one association to DitCredElector
	@OneToMany(mappedBy="dgCatLocalidad")
	private List<DitCredElector> ditCredElectors;

    public DgCatLocalidad() {
    }

	public DgCatLocalidadPK getId() {
		return this.id;
	}

	public void setId(DgCatLocalidadPK id) {
		this.id = id;
	}
	
	public String getAgregada() {
		return this.agregada;
	}

	public void setAgregada(String agregada) {
		this.agregada = agregada;
	}

	public String getNomLoc() {
		return this.nomLoc;
	}

	public void setNomLoc(String nomLoc) {
		this.nomLoc = nomLoc;
	}

	/*
	public List<DgAsentamiento> getDgAsentamientos() {
		return this.dgAsentamientos;
	}

	public void setDgAsentamientos(List<DgAsentamiento> dgAsentamientos) {
		this.dgAsentamientos = dgAsentamientos;
	}
	*/
	
	public DgCatPeriodo getDgCatPeriodo() {
		return this.dgCatPeriodo;
	}

	public void setDgCatPeriodo(DgCatPeriodo dgCatPeriodo) {
		this.dgCatPeriodo = dgCatPeriodo;
	}
	
	public DgCatMunicipio getDgCatMunicipio() {
		return this.dgCatMunicipio;
	}

	public void setDgCatMunicipio(DgCatMunicipio dgCatMunicipio) {
		this.dgCatMunicipio = dgCatMunicipio;
	}
	
	public DgCatAmbito getDgCatAmbito() {
		return this.dgCatAmbito;
	}

	public void setDgCatAmbito(DgCatAmbito dgCatAmbito) {
		this.dgCatAmbito = dgCatAmbito;
	}
	
	public List<DgDomicilioGeografico> getDgDomicilioGeograficos() {
		return this.dgDomicilioGeograficos;
	}

	public void setDgDomicilioGeograficos(List<DgDomicilioGeografico> dgDomicilioGeograficos) {
		this.dgDomicilioGeograficos = dgDomicilioGeograficos;
	}
	
	public List<DgVialidad> getDgVialidads() {
		return this.dgVialidads;
	}

	public void setDgVialidads(List<DgVialidad> dgVialidads) {
		this.dgVialidads = dgVialidads;
	}
	
	public List<DitCredElector> getDitCredElectors() {
		return this.ditCredElectors;
	}

	public void setDitCredElectors(List<DitCredElector> ditCredElectors) {
		this.ditCredElectors = ditCredElectors;
	}
	
}