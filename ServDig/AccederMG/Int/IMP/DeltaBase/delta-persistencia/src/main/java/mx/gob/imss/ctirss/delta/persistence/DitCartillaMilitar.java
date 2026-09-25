package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import javax.persistence.Table;


/**
 * The persistent class for the DIT_CARTILLA_MILITAR database table.
 * 
 */
@Entity
@Table(name="DIT_CARTILLA_MILITAR")
public class DitCartillaMilitar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

	@Column(name="NUM_MATRICULA", nullable=false, length=16)
	private String numMatricula;
	
	@Column(name="REF_CLASE")
	private String refClase;

	//bi-directional one-to-one association to ditDocumentoProbatorio
	@OneToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
    public DitCartillaMilitar() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public String getNumMatricula() {
		return this.numMatricula;
	}

	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}

	/**
	 * @return the ditDocumentoProbatorio
	 */
	public DitDocumentoProbatorio getDitDocumentoProbatorio() {
		return ditDocumentoProbatorio;
	}

	/**
	 * @param ditDocumentoProbatorio the ditDocumentoProbatorio to set
	 */
	public void setDitDocumentoProbatorio(
			DitDocumentoProbatorio ditDocumentoProbatorio) {
		this.ditDocumentoProbatorio = ditDocumentoProbatorio;
	}

	public String getRefClase() {
		return refClase;
	}

	public void setRefClase(String refClase) {
		this.refClase = refClase;
	}

}