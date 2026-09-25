package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_TIPO_PENSION database table.
 * 
 */
@Entity
@Table(name="SPC_TIPO_PENSION")
@NamedQuery(name="SpcTipoPension.findAll", query="SELECT s FROM SpcTipoPension s")
public class SpcTipoPension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_TIPO_PENSION_IDTIPOPENSION_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_TIPO_PENSION_IDTIPOPENSION_GENERATOR")
	@Column(name="ID_TIPO_PENSION")
	private String idTipoPension;

	@Column(name="DES_TIPO_PENSION")
	private String desTipoPension;

	@Column(name="ID_TIPO_TRAMITE")
	private String idTipoTramite;

	//bi-directional many-to-one association to AptDatosImpresion
	@OneToMany(mappedBy="spcTipoPension")
	private List<AptDatosImpresion> aptDatosImpresions;

	//bi-directional many-to-one association to SpcArtBaseNegativaCon
	@OneToMany(mappedBy="spcTipoPension")
	private List<SpcArtBaseNegativaCon> spcArtBaseNegativaCons;

	//bi-directional many-to-one association to SptAccSindoPen
	@OneToMany(mappedBy="spcTipoPension")
	private List<SptAccSindoPen> sptAccSindoPens;

	//bi-directional many-to-one association to SptPension
	@OneToMany(mappedBy="spcTipoPension")
	private List<SptPension> sptPensions;

	public SpcTipoPension() {
	}

	public String getIdTipoPension() {
		return this.idTipoPension;
	}

	public void setIdTipoPension(String idTipoPension) {
		this.idTipoPension = idTipoPension;
	}

	public String getDesTipoPension() {
		return this.desTipoPension;
	}

	public void setDesTipoPension(String desTipoPension) {
		this.desTipoPension = desTipoPension;
	}

	public String getIdTipoTramite() {
		return this.idTipoTramite;
	}

	public void setIdTipoTramite(String idTipoTramite) {
		this.idTipoTramite = idTipoTramite;
	}

	public List<AptDatosImpresion> getAptDatosImpresions() {
		return this.aptDatosImpresions;
	}

	public void setAptDatosImpresions(List<AptDatosImpresion> aptDatosImpresions) {
		this.aptDatosImpresions = aptDatosImpresions;
	}

	public AptDatosImpresion addAptDatosImpresion(AptDatosImpresion aptDatosImpresion) {
		getAptDatosImpresions().add(aptDatosImpresion);
		aptDatosImpresion.setSpcTipoPension(this);

		return aptDatosImpresion;
	}

	public AptDatosImpresion removeAptDatosImpresion(AptDatosImpresion aptDatosImpresion) {
		getAptDatosImpresions().remove(aptDatosImpresion);
		aptDatosImpresion.setSpcTipoPension(null);

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
		spcArtBaseNegativaCon.setSpcTipoPension(this);

		return spcArtBaseNegativaCon;
	}

	public SpcArtBaseNegativaCon removeSpcArtBaseNegativaCon(SpcArtBaseNegativaCon spcArtBaseNegativaCon) {
		getSpcArtBaseNegativaCons().remove(spcArtBaseNegativaCon);
		spcArtBaseNegativaCon.setSpcTipoPension(null);

		return spcArtBaseNegativaCon;
	}

	public List<SptAccSindoPen> getSptAccSindoPens() {
		return this.sptAccSindoPens;
	}

	public void setSptAccSindoPens(List<SptAccSindoPen> sptAccSindoPens) {
		this.sptAccSindoPens = sptAccSindoPens;
	}

	public SptAccSindoPen addSptAccSindoPen(SptAccSindoPen sptAccSindoPen) {
		getSptAccSindoPens().add(sptAccSindoPen);
		sptAccSindoPen.setSpcTipoPension(this);

		return sptAccSindoPen;
	}

	public SptAccSindoPen removeSptAccSindoPen(SptAccSindoPen sptAccSindoPen) {
		getSptAccSindoPens().remove(sptAccSindoPen);
		sptAccSindoPen.setSpcTipoPension(null);

		return sptAccSindoPen;
	}

	public List<SptPension> getSptPensions() {
		return this.sptPensions;
	}

	public void setSptPensions(List<SptPension> sptPensions) {
		this.sptPensions = sptPensions;
	}

	public SptPension addSptPension(SptPension sptPension) {
		getSptPensions().add(sptPension);
		sptPension.setSpcTipoPension(this);

		return sptPension;
	}

	public SptPension removeSptPension(SptPension sptPension) {
		getSptPensions().remove(sptPension);
		sptPension.setSpcTipoPension(null);

		return sptPension;
	}

}