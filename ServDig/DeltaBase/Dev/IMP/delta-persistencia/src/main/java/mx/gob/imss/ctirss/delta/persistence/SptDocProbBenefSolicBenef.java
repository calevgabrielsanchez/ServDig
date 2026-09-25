package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_DOC_PROB_BENEF_SOLIC_BENEF database table.
 * 
 */
@Entity
@Table(name="SPT_DOC_PROB_BENEF_SOLIC_BENEF")
@NamedQuery(name="SptDocProbBenefSolicBenef.findAll", query="SELECT s FROM SptDocProbBenefSolicBenef s")
public class SptDocProbBenefSolicBenef implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDOCPROBBENEFSOLICBENEF", sequenceName = "SEQ_SPTDOCPROBBENEFSOLICBENEF")
	@GeneratedValue(generator = "SEQ_SPTDOCPROBBENEFSOLICBENEF")
	@Column(name="CVE_ID_DOC_PROB_BENEF_SOLICI_B")
	private long cveIdDocProbBenefSoliciB;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptBeneficiarioPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_PENSION")
	private SptBeneficiarioPension sptBeneficiarioPension;

	//bi-directional many-to-one association to SptDocProbBenefSolic
	@ManyToOne
	@JoinColumn(name="CVE_ID_DOC_PROB_BENEF_SOLIC")
	private SptDocProbBenefSolic sptDocProbBenefSolic;

	public SptDocProbBenefSolicBenef() {
	}

	public long getCveIdDocProbBenefSoliciB() {
		return this.cveIdDocProbBenefSoliciB;
	}

	public void setCveIdDocProbBenefSoliciB(long cveIdDocProbBenefSoliciB) {
		this.cveIdDocProbBenefSoliciB = cveIdDocProbBenefSoliciB;
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

	public SptBeneficiarioPension getSptBeneficiarioPension() {
		return this.sptBeneficiarioPension;
	}

	public void setSptBeneficiarioPension(SptBeneficiarioPension sptBeneficiarioPension) {
		this.sptBeneficiarioPension = sptBeneficiarioPension;
	}

	public SptDocProbBenefSolic getSptDocProbBenefSolic() {
		return this.sptDocProbBenefSolic;
	}

	public void setSptDocProbBenefSolic(SptDocProbBenefSolic sptDocProbBenefSolic) {
		this.sptDocProbBenefSolic = sptDocProbBenefSolic;
	}

}