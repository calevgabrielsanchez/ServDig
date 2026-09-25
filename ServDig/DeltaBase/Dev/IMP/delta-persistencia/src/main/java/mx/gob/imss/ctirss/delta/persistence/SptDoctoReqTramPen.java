package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_DOCTO_REQ_TRAM_PENS database table.
 * 
 */
@Entity
@Table(name="SPT_DOCTO_REQ_TRAM_PENS")
@NamedQuery(name="SptDoctoReqTramPen.findAll", query="SELECT s FROM SptDoctoReqTramPen s")
public class SptDoctoReqTramPen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTDOCTOREQTRAMPENS", sequenceName = "SEQ_SPTDOCTOREQTRAMPENS")
	@GeneratedValue(generator = "SEQ_SPTDOCTOREQTRAMPENS")
	@Column(name="CVE_ID_DOCTO_REQ_TRAM_PENS")
	private long cveIdDoctoReqTramPens;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTROS_BAJA")
	private Date fecRegistrosBaja;

	@Column(name="IND_DOCTO_MODULO")
	private BigDecimal indDoctoModulo;

	@Column(name="IND_DOCTO_OPCIONAL")
	private BigDecimal indDoctoOpcional;

	@Column(name="IND_ENTREGA")
	private BigDecimal indEntrega;

	//bi-directional many-to-one association to DitDoctoReqTramite
	@ManyToOne
	@JoinColumn(name="CVE_ID_DOCTO_REQ_TRAMITE")
	private DitDoctoReqTramite ditDoctoReqTramite;

	//bi-directional many-to-one association to SpcIntegranteDocumentoPensi
	@ManyToOne
	@JoinColumn(name="CVE_ID_INTEGRANTE_DOCUMENTO_PE")
	private SpcIntegranteDocumentoPensi spcIntegranteDocumentoPensi;

	//bi-directional many-to-one association to SptDocProbBenefSolic
	@OneToMany(mappedBy="sptDoctoReqTramPen")
	private List<SptDocProbBenefSolic> sptDocProbBenefSolics;

	public SptDoctoReqTramPen() {
	}

	public long getCveIdDoctoReqTramPens() {
		return this.cveIdDoctoReqTramPens;
	}

	public void setCveIdDoctoReqTramPens(long cveIdDoctoReqTramPens) {
		this.cveIdDoctoReqTramPens = cveIdDoctoReqTramPens;
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

	public Date getFecRegistrosBaja() {
		return this.fecRegistrosBaja;
	}

	public void setFecRegistrosBaja(Date fecRegistrosBaja) {
		this.fecRegistrosBaja = fecRegistrosBaja;
	}

	public BigDecimal getIndDoctoModulo() {
		return this.indDoctoModulo;
	}

	public void setIndDoctoModulo(BigDecimal indDoctoModulo) {
		this.indDoctoModulo = indDoctoModulo;
	}

	public BigDecimal getIndDoctoOpcional() {
		return this.indDoctoOpcional;
	}

	public void setIndDoctoOpcional(BigDecimal indDoctoOpcional) {
		this.indDoctoOpcional = indDoctoOpcional;
	}

	public BigDecimal getIndEntrega() {
		return this.indEntrega;
	}

	public void setIndEntrega(BigDecimal indEntrega) {
		this.indEntrega = indEntrega;
	}

	public DitDoctoReqTramite getDitDoctoReqTramite() {
		return this.ditDoctoReqTramite;
	}

	public void setDitDoctoReqTramite(DitDoctoReqTramite ditDoctoReqTramite) {
		this.ditDoctoReqTramite = ditDoctoReqTramite;
	}

	public SpcIntegranteDocumentoPensi getSpcIntegranteDocumentoPensi() {
		return this.spcIntegranteDocumentoPensi;
	}

	public void setSpcIntegranteDocumentoPensi(SpcIntegranteDocumentoPensi spcIntegranteDocumentoPensi) {
		this.spcIntegranteDocumentoPensi = spcIntegranteDocumentoPensi;
	}

	public List<SptDocProbBenefSolic> getSptDocProbBenefSolics() {
		return this.sptDocProbBenefSolics;
	}

	public void setSptDocProbBenefSolics(List<SptDocProbBenefSolic> sptDocProbBenefSolics) {
		this.sptDocProbBenefSolics = sptDocProbBenefSolics;
	}

	public SptDocProbBenefSolic addSptDocProbBenefSolic(SptDocProbBenefSolic sptDocProbBenefSolic) {
		getSptDocProbBenefSolics().add(sptDocProbBenefSolic);
		sptDocProbBenefSolic.setSptDoctoReqTramPen(this);

		return sptDocProbBenefSolic;
	}

	public SptDocProbBenefSolic removeSptDocProbBenefSolic(SptDocProbBenefSolic sptDocProbBenefSolic) {
		getSptDocProbBenefSolics().remove(sptDocProbBenefSolic);
		sptDocProbBenefSolic.setSptDoctoReqTramPen(null);

		return sptDocProbBenefSolic;
	}

}