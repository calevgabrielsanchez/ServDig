package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_REGIMEN database table.
 * 
 */
@Entity
@Table(name="SPC_REGIMEN")
@NamedQuery(name="SpcRegimen.findAll", query="SELECT s FROM SpcRegimen s")
public class SpcRegimen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_REGIMEN_IDREGIMEN_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_REGIMEN_IDREGIMEN_GENERATOR")
	@Column(name="ID_REGIMEN",insertable=false, updatable=false)
	private String idRegimen;

	@Column(name="DES_REGIMEN")
	private String desRegimen;

	//bi-directional many-to-one association to AptDatosImpresion
	@OneToMany(mappedBy="spcRegimen")
	private List<AptDatosImpresion> aptDatosImpresions;

	//bi-directional many-to-one association to SpcArtBaseNegativaCon
	@OneToMany(mappedBy="spcRegimen")
	private List<SpcArtBaseNegativaCon> spcArtBaseNegativaCons;

	//bi-directional many-to-one association to SpcModalidad
	@OneToMany(mappedBy="spcRegimen")
	private List<SpcModalidad> spcModalidads;

	//bi-directional many-to-one association to SpcResolutivo
	@OneToMany(mappedBy="spcRegimen")
	private List<SpcResolutivo> spcResolutivos;

	//bi-directional many-to-one association to SptPension
	@OneToMany(mappedBy="spcRegimen")
	private List<SptPension> sptPensions;

	public SpcRegimen() {
	}

	public String getIdRegimen() {
		return this.idRegimen;
	}

	public void setIdRegimen(String idRegimen) {
		this.idRegimen = idRegimen;
	}

	public String getDesRegimen() {
		return this.desRegimen;
	}

	public void setDesRegimen(String desRegimen) {
		this.desRegimen = desRegimen;
	}

	public List<AptDatosImpresion> getAptDatosImpresions() {
		return this.aptDatosImpresions;
	}

	public void setAptDatosImpresions(List<AptDatosImpresion> aptDatosImpresions) {
		this.aptDatosImpresions = aptDatosImpresions;
	}

	public AptDatosImpresion addAptDatosImpresion(AptDatosImpresion aptDatosImpresion) {
		getAptDatosImpresions().add(aptDatosImpresion);
		aptDatosImpresion.setSpcRegimen(this);

		return aptDatosImpresion;
	}

	public AptDatosImpresion removeAptDatosImpresion(AptDatosImpresion aptDatosImpresion) {
		getAptDatosImpresions().remove(aptDatosImpresion);
		aptDatosImpresion.setSpcRegimen(null);

		return aptDatosImpresion;
	}

	public List<SpcArtBaseNegativaCon> getSpcArtBaseNegativaCons() {
		return this.spcArtBaseNegativaCons;
	}

	public void setSpcArtBaseNegativaCons(List<SpcArtBaseNegativaCon> spcArtBaseNegativaCons) {
		this.spcArtBaseNegativaCons = spcArtBaseNegativaCons;
	}

	public SpcArtBaseNegativaCon addSpcArtBaseNegativaCon(SpcArtBaseNegativaCon spcArtBaseNegativaCon) {
		getSpcArtBaseNegativaCons().add(spcArtBaseNegativaCon);
		spcArtBaseNegativaCon.setSpcRegimen(this);

		return spcArtBaseNegativaCon;
	}

	public SpcArtBaseNegativaCon removeSpcArtBaseNegativaCon(SpcArtBaseNegativaCon spcArtBaseNegativaCon) {
		getSpcArtBaseNegativaCons().remove(spcArtBaseNegativaCon);
		spcArtBaseNegativaCon.setSpcRegimen(null);

		return spcArtBaseNegativaCon;
	}

	public List<SpcModalidad> getSpcModalidads() {
		return this.spcModalidads;
	}

	public void setSpcModalidads(List<SpcModalidad> spcModalidads) {
		this.spcModalidads = spcModalidads;
	}

	public SpcModalidad addSpcModalidad(SpcModalidad spcModalidad) {
		getSpcModalidads().add(spcModalidad);
		spcModalidad.setSpcRegimen(this);

		return spcModalidad;
	}

	public SpcModalidad removeSpcModalidad(SpcModalidad spcModalidad) {
		getSpcModalidads().remove(spcModalidad);
		spcModalidad.setSpcRegimen(null);

		return spcModalidad;
	}

	public List<SpcResolutivo> getSpcResolutivos() {
		return this.spcResolutivos;
	}

	public void setSpcResolutivos(List<SpcResolutivo> spcResolutivos) {
		this.spcResolutivos = spcResolutivos;
	}

	public SpcResolutivo addSpcResolutivo(SpcResolutivo spcResolutivo) {
		getSpcResolutivos().add(spcResolutivo);
		spcResolutivo.setSpcRegimen(this);

		return spcResolutivo;
	}

	public SpcResolutivo removeSpcResolutivo(SpcResolutivo spcResolutivo) {
		getSpcResolutivos().remove(spcResolutivo);
		spcResolutivo.setSpcRegimen(null);

		return spcResolutivo;
	}

	public List<SptPension> getSptPensions() {
		return this.sptPensions;
	}

	public void setSptPensions(List<SptPension> sptPensions) {
		this.sptPensions = sptPensions;
	}

	public SptPension addSptPension(SptPension sptPension) {
		getSptPensions().add(sptPension);
		sptPension.setSpcRegimen(this);

		return sptPension;
	}

	public SptPension removeSptPension(SptPension sptPension) {
		getSptPensions().remove(sptPension);
		sptPension.setSpcRegimen(null);

		return sptPension;
	}

}