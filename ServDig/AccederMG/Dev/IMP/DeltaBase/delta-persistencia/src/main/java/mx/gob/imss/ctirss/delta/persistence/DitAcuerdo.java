package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the DIT_ACUERDO database table.
 * 
 */
@Entity
@Table(name="DIT_ACUERDO")
public class DitAcuerdo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

	@Column(name="NUM_ACUERDO", nullable=false, length=8)
	private String numAcuerdo;
	
	
	@Column(name="REF_INSTANCIA_EMITE_RES",nullable=true,length=50 )
	private String refInstanciaEmiteRes;

	//bi-directional one-to-one association to ditDocumentoProbatorio
	@OneToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private DitDocumentoProbatorio ditDocumentoProbatorio;
	
    public DitAcuerdo() {
    }
    
    

	public String getRefInstanciaEmiteRes() {
		return refInstanciaEmiteRes;
	}



	public void setRefInstanciaEmiteRes(String refInstanciaEmiteRes) {
		this.refInstanciaEmiteRes = refInstanciaEmiteRes;
	}



	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public String getNumAcuerdo() {
		return this.numAcuerdo;
	}

	public void setNumAcuerdo(String numAcuerdo) {
		this.numAcuerdo = numAcuerdo;
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

}