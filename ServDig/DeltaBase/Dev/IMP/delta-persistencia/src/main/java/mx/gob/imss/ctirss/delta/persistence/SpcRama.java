package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_RAMA database table.
 * 
 */
@Entity
@Table(name="SPC_RAMA")
@NamedQuery(name="SpcRama.findAll", query="SELECT s FROM SpcRama s")
public class SpcRama implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_RAMA_IDRAMA_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_RAMA_IDRAMA_GENERATOR")
	@Column(name="ID_RAMA")
	private String idRama;

	@Column(name="CVE_RAMA_PREI")
	private String cveRamaPrei;

	@Column(name="DES_RAMA")
	private String desRama;

	//bi-directional many-to-one association to SpcArtBaseNegativaCon
	@OneToMany(mappedBy="spcRama")
	private List<SpcArtBaseNegativaCon> spcArtBaseNegativaCons;

	//bi-directional many-to-one association to SptPension
	@OneToMany(mappedBy="spcRama")
	private List<SptPension> sptPensions;

	public SpcRama() {
	}

	public String getIdRama() {
		return this.idRama;
	}

	public void setIdRama(String idRama) {
		this.idRama = idRama;
	}

	public String getCveRamaPrei() {
		return this.cveRamaPrei;
	}

	public void setCveRamaPrei(String cveRamaPrei) {
		this.cveRamaPrei = cveRamaPrei;
	}

	public String getDesRama() {
		return this.desRama;
	}

	public void setDesRama(String desRama) {
		this.desRama = desRama;
	}

	public List<SpcArtBaseNegativaCon> getSpcArtBaseNegativaCons() {
		return this.spcArtBaseNegativaCons;
	}

	public void setSpcArtBaseNegativaCons(List<SpcArtBaseNegativaCon> spcArtBaseNegativaCons) {
		this.spcArtBaseNegativaCons = spcArtBaseNegativaCons;
	}

	public SpcArtBaseNegativaCon addSpcArtBaseNegativaCon(SpcArtBaseNegativaCon spcArtBaseNegativaCon) {
		getSpcArtBaseNegativaCons().add(spcArtBaseNegativaCon);
		spcArtBaseNegativaCon.setSpcRama(this);

		return spcArtBaseNegativaCon;
	}

	public SpcArtBaseNegativaCon removeSpcArtBaseNegativaCon(SpcArtBaseNegativaCon spcArtBaseNegativaCon) {
		getSpcArtBaseNegativaCons().remove(spcArtBaseNegativaCon);
		spcArtBaseNegativaCon.setSpcRama(null);

		return spcArtBaseNegativaCon;
	}

	public List<SptPension> getSptPensions() {
		return this.sptPensions;
	}

	public void setSptPensions(List<SptPension> sptPensions) {
		this.sptPensions = sptPensions;
	}

	public SptPension addSptPension(SptPension sptPension) {
		getSptPensions().add(sptPension);
		sptPension.setSpcRama(this);

		return sptPension;
	}

	public SptPension removeSptPension(SptPension sptPension) {
		getSptPensions().remove(sptPension);
		sptPension.setSpcRama(null);

		return sptPension;
	}

}