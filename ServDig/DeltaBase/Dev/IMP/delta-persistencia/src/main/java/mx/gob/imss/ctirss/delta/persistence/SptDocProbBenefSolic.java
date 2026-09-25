package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_DOC_PROB_BENEF_SOLIC database table.
 * 
 */
@Entity
@Table(name="SPT_DOC_PROB_BENEF_SOLIC")
@NamedQuery(name="SptDocProbBenefSolic.findAll", query="SELECT s FROM SptDocProbBenefSolic s")
public class SptDocProbBenefSolic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDOCPROBBENEFSOLIC", sequenceName = "SEQ_SPTDOCPROBBENEFSOLIC")
	@GeneratedValue(generator = "SEQ_SPTDOCPROBBENEFSOLIC")
	@Column(name="CVE_ID_DOC_PROB_BENEF_SOLIC")
	private long cveIdDocProbBenefSolic;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_DOCTO_SOLICITUD")
	private BigDecimal numDoctoSolicitud;

	//bi-directional many-to-one association to SptDoctoReqTramPen
	@ManyToOne
	@JoinColumn(name="CVE_ID_DOCTO_REQ_TRAM_PENS")
	private SptDoctoReqTramPen sptDoctoReqTramPen;

	//bi-directional many-to-one association to SptTramitePension
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

	//bi-directional many-to-one association to SptDocProbBenefSolicBenef
	@OneToMany(mappedBy="sptDocProbBenefSolic")
	private List<SptDocProbBenefSolicBenef> sptDocProbBenefSolicBenefs;

	public SptDocProbBenefSolic() {
	}

	public long getCveIdDocProbBenefSolic() {
		return this.cveIdDocProbBenefSolic;
	}

	public void setCveIdDocProbBenefSolic(long cveIdDocProbBenefSolic) {
		this.cveIdDocProbBenefSolic = cveIdDocProbBenefSolic;
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

	public BigDecimal getNumDoctoSolicitud() {
		return this.numDoctoSolicitud;
	}

	public void setNumDoctoSolicitud(BigDecimal numDoctoSolicitud) {
		this.numDoctoSolicitud = numDoctoSolicitud;
	}

	public SptDoctoReqTramPen getSptDoctoReqTramPen() {
		return this.sptDoctoReqTramPen;
	}

	public void setSptDoctoReqTramPen(SptDoctoReqTramPen sptDoctoReqTramPen) {
		this.sptDoctoReqTramPen = sptDoctoReqTramPen;
	}

	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}

	public List<SptDocProbBenefSolicBenef> getSptDocProbBenefSolicBenefs() {
		return this.sptDocProbBenefSolicBenefs;
	}

	public void setSptDocProbBenefSolicBenefs(List<SptDocProbBenefSolicBenef> sptDocProbBenefSolicBenefs) {
		this.sptDocProbBenefSolicBenefs = sptDocProbBenefSolicBenefs;
	}

	public SptDocProbBenefSolicBenef addSptDocProbBenefSolicBenef(SptDocProbBenefSolicBenef sptDocProbBenefSolicBenef) {
		getSptDocProbBenefSolicBenefs().add(sptDocProbBenefSolicBenef);
		sptDocProbBenefSolicBenef.setSptDocProbBenefSolic(this);

		return sptDocProbBenefSolicBenef;
	}

	public SptDocProbBenefSolicBenef removeSptDocProbBenefSolicBenef(SptDocProbBenefSolicBenef sptDocProbBenefSolicBenef) {
		getSptDocProbBenefSolicBenefs().remove(sptDocProbBenefSolicBenef);
		sptDocProbBenefSolicBenef.setSptDocProbBenefSolic(null);

		return sptDocProbBenefSolicBenef;
	}

}