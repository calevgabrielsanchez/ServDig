package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIT_MATRICULA_CONSULAR")
public class DitMatriculaConsular implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;
	
	@Column(name = "NUM_DOCTO")
	private String numeroDoc;
	
	@Column(name = "AUTORIDAD")
	private String autoridadEmiteMat;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_EXPEDICION", nullable=false)
	private Date fecExpedicion;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_VENCIMIENTO", nullable=false)
	private Date fecVencimiento;
	
	@Column(name = "CALIDAD")
	private String calidadMigratoria;
	
	//bi-directional one-to-one association to ditDocumentoProbatorio
	@OneToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private DitDocumentoProbatorio ditDocumentoProbatorio;

	public long getCveIdDocumentoProbatorio() {
		return cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public String getNumeroDoc() {
		return numeroDoc;
	}

	public void setNumeroDoc(String numeroDoc) {
		this.numeroDoc = numeroDoc;
	}

	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(
			DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}
	
	public String getAutoridadEmiteMat() {
		return autoridadEmiteMat;
	}

	public void setAutoridadEmiteMat(String autoridadEmiteMat) {
		this.autoridadEmiteMat = autoridadEmiteMat;
	}

	public Date getFecExpedicion() {
		return fecExpedicion;
	}

	public void setFecExpedicion(Date fecExpedicion) {
		this.fecExpedicion = fecExpedicion;
	}
	
	public Date getFecVencimiento() {
		return fecVencimiento;
	}

	public void setFecVencimiento(Date fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}
	
	public String getCalidadMigratoria() {
		return calidadMigratoria;
	}

	public void setCalidadMigratoria(String calidadMigratoria) {
		this.calidadMigratoria = calidadMigratoria;
	}
	
}
