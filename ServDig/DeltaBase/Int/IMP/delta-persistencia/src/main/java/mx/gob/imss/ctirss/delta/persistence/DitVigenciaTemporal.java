package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIT_VIGENCIA_TEMPORAL database table.
 * 
 */
@Entity
@Table(name="DIT_VIGENCIA_TEMPORAL")
public class DitVigenciaTemporal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

	@Column(name="REF_FOLIO", nullable=false, length=8)
	private String refFolio;

	//bi-directional one-to-one association to ditDocumentoProbatorio
	@OneToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
    public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(
			DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}

	public DitVigenciaTemporal() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public String getRefFolio() {
		return this.refFolio;
	}

	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}

}