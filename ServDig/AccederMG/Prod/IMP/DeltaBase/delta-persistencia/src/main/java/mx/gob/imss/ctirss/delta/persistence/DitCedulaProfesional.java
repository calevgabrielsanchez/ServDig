package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIT_CEDULA_PROFESIONAL database table.
 * 
 */
@Entity
@Table(name="DIT_CEDULA_PROFESIONAL")
public class DitCedulaProfesional implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

	@Column(name="NOM_PROFESION", nullable=false, length=255)
	private String nomProfesion;

	@Column(name="NUM_CEDULA", length=10)
	private String numCedula;
	
	//bi-directional one-to-one association to DitDocumentoProbatorio
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO", nullable=false)
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
	
	
	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}

	public void setDitDocumentoProbatorio(
			DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}

	public DitCedulaProfesional() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public String getNomProfesion() {
		return this.nomProfesion;
	}

	public void setNomProfesion(String nomProfesion) {
		this.nomProfesion = nomProfesion;
	}

	public String getNumCedula() {
		return this.numCedula;
	}

	public void setNumCedula(String numCedula) {
		this.numCedula = numCedula;
	}

	
}