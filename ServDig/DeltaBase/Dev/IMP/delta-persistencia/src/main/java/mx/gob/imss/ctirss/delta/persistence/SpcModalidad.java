package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_MODALIDAD database table.
 * 
 */
@Entity
@Table(name="SPC_MODALIDAD")
@NamedQuery(name="SpcModalidad.findAll", query="SELECT s FROM SpcModalidad s")
public class SpcModalidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SpcModalidadPK id;

	@Column(name="DES_MODALIDAD")
	private String desModalidad;

	//bi-directional many-to-one association to SpcRegimen
	@ManyToOne
	@JoinColumn(name="ID_REGIMEN",insertable=false, updatable=false)
	private SpcRegimen spcRegimen;

	//bi-directional many-to-one association to SptPension
	@OneToMany(mappedBy="spcModalidad")
	private List<SptPension> sptPensions;

	public SpcModalidad() {
	}

	public SpcModalidadPK getId() {
		return this.id;
	}

	public void setId(SpcModalidadPK id) {
		this.id = id;
	}

	public String getDesModalidad() {
		return this.desModalidad;
	}

	public void setDesModalidad(String desModalidad) {
		this.desModalidad = desModalidad;
	}

	public SpcRegimen getSpcRegimen() {
		return this.spcRegimen;
	}

	public void setSpcRegimen(SpcRegimen spcRegimen) {
		this.spcRegimen = spcRegimen;
	}

	public List<SptPension> getSptPensions() {
		return this.sptPensions;
	}

	public void setSptPensions(List<SptPension> sptPensions) {
		this.sptPensions = sptPensions;
	}

	public SptPension addSptPension(SptPension sptPension) {
		getSptPensions().add(sptPension);
		sptPension.setSpcModalidad(this);

		return sptPension;
	}

	public SptPension removeSptPension(SptPension sptPension) {
		getSptPensions().remove(sptPension);
		sptPension.setSpcModalidad(null);

		return sptPension;
	}

}