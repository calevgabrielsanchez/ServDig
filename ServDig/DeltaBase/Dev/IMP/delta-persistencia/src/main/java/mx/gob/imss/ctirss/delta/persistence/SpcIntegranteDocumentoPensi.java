package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPC_INTEGRANTE_DOCUMENTO_PENSI database table.
 * 
 */
@Entity
@Table(name="SPC_INTEGRANTE_DOCUMENTO_PENSI")
@NamedQuery(name="SpcIntegranteDocumentoPensi.findAll", query="SELECT s FROM SpcIntegranteDocumentoPensi s")
public class SpcIntegranteDocumentoPensi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPC_INTEGRANTE_DOCUMENTO_PENSI_CVEIDINTEGRANTEDOCUMENTOPE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPC_INTEGRANTE_DOCUMENTO_PENSI_CVEIDINTEGRANTEDOCUMENTOPE_GENERATOR")
	@Column(name="CVE_ID_INTEGRANTE_DOCUMENTO_PE")
	private long cveIdIntegranteDocumentoPe;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTROS_BAJA")
	private Date fecRegistrosBaja;

	//bi-directional many-to-one association to DicCalidadParentesco
	@ManyToOne
	@JoinColumn(name="CVE_ID_CALIDAD_PARENTESCO")
	private DicCalidadParentesco dicCalidadParentesco;

	//bi-directional many-to-one association to SptDoctoReqTramPen
	@OneToMany(mappedBy="spcIntegranteDocumentoPensi")
	private List<SptDoctoReqTramPen> sptDoctoReqTramPens;

	public SpcIntegranteDocumentoPensi() {
	}

	public long getCveIdIntegranteDocumentoPe() {
		return this.cveIdIntegranteDocumentoPe;
	}

	public void setCveIdIntegranteDocumentoPe(long cveIdIntegranteDocumentoPe) {
		this.cveIdIntegranteDocumentoPe = cveIdIntegranteDocumentoPe;
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

	public DicCalidadParentesco getDicCalidadParentesco() {
		return this.dicCalidadParentesco;
	}

	public void setDicCalidadParentesco(DicCalidadParentesco dicCalidadParentesco) {
		this.dicCalidadParentesco = dicCalidadParentesco;
	}

	public List<SptDoctoReqTramPen> getSptDoctoReqTramPens() {
		return this.sptDoctoReqTramPens;
	}

	public void setSptDoctoReqTramPens(List<SptDoctoReqTramPen> sptDoctoReqTramPens) {
		this.sptDoctoReqTramPens = sptDoctoReqTramPens;
	}

	public SptDoctoReqTramPen addSptDoctoReqTramPen(SptDoctoReqTramPen sptDoctoReqTramPen) {
		getSptDoctoReqTramPens().add(sptDoctoReqTramPen);
		sptDoctoReqTramPen.setSpcIntegranteDocumentoPensi(this);

		return sptDoctoReqTramPen;
	}

	public SptDoctoReqTramPen removeSptDoctoReqTramPen(SptDoctoReqTramPen sptDoctoReqTramPen) {
		getSptDoctoReqTramPens().remove(sptDoctoReqTramPen);
		sptDoctoReqTramPen.setSpcIntegranteDocumentoPensi(null);

		return sptDoctoReqTramPen;
	}

}