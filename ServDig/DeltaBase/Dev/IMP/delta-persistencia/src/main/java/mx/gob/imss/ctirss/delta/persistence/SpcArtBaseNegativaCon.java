package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SPC_ART_BASE_NEGATIVA_CONS database table.
 * 
 */
@Entity
@Table(name="SPC_ART_BASE_NEGATIVA_CONS")
@NamedQuery(name="SpcArtBaseNegativaCon.findAll", query="SELECT s FROM SpcArtBaseNegativaCon s")
public class SpcArtBaseNegativaCon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_ART_BASE_NEGATIVA_CONS_IDARTBASENEGATIVACONS_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_ART_BASE_NEGATIVA_CONS_IDARTBASENEGATIVACONS_GENERATOR")
	@Column(name="ID_ART_BASE_NEGATIVA_CONS")
	private String idArtBaseNegativaCons;

	@Column(name="CVE_ARTICULO")
	private String cveArticulo;

	@Column(name="CVE_FRACCION")
	private String cveFraccion;

	@Column(name="IND_CONSIDERANDO")
	private String indConsiderando;

	@Column(name="IND_DERECHO_RETIRO")
	private String indDerechoRetiro;

	//bi-directional many-to-one association to SpcRama
	@ManyToOne
	@JoinColumn(name="ID_RAMA")
	private SpcRama spcRama;

	//bi-directional many-to-one association to SpcRegimen
	@ManyToOne
	@JoinColumn(name="ID_REGIMEN")
	private SpcRegimen spcRegimen;

	//bi-directional many-to-one association to SpcTipoPension
	@ManyToOne
	@JoinColumn(name="ID_TIPO_PENSION")
	private SpcTipoPension spcTipoPension;

	public SpcArtBaseNegativaCon() {
	}

	public String getIdArtBaseNegativaCons() {
		return this.idArtBaseNegativaCons;
	}

	public void setIdArtBaseNegativaCons(String idArtBaseNegativaCons) {
		this.idArtBaseNegativaCons = idArtBaseNegativaCons;
	}

	public String getCveArticulo() {
		return this.cveArticulo;
	}

	public void setCveArticulo(String cveArticulo) {
		this.cveArticulo = cveArticulo;
	}

	public String getCveFraccion() {
		return this.cveFraccion;
	}

	public void setCveFraccion(String cveFraccion) {
		this.cveFraccion = cveFraccion;
	}

	public String getIndConsiderando() {
		return this.indConsiderando;
	}

	public void setIndConsiderando(String indConsiderando) {
		this.indConsiderando = indConsiderando;
	}

	public String getIndDerechoRetiro() {
		return this.indDerechoRetiro;
	}

	public void setIndDerechoRetiro(String indDerechoRetiro) {
		this.indDerechoRetiro = indDerechoRetiro;
	}

	public SpcRama getSpcRama() {
		return this.spcRama;
	}

	public void setSpcRama(SpcRama spcRama) {
		this.spcRama = spcRama;
	}

	public SpcRegimen getSpcRegimen() {
		return this.spcRegimen;
	}

	public void setSpcRegimen(SpcRegimen spcRegimen) {
		this.spcRegimen = spcRegimen;
	}

	public SpcTipoPension getSpcTipoPension() {
		return this.spcTipoPension;
	}

	public void setSpcTipoPension(SpcTipoPension spcTipoPension) {
		this.spcTipoPension = spcTipoPension;
	}

}