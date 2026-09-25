package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the DIT_NACIMIENTO database table.
 * 
 */
@Entity
@Table(name="DIT_NACIMIENTO")
public class DitNacimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_DOCUMENTO_PROBATORIO", unique=true, nullable=false, precision=22)
	private long cveIdDocumentoProbatorio;

	@Column(name="CVE_CRIP", length=18)
	private String cveCrip;

	
	
	@Column(name="NUM_ANIO", nullable=false, length=22)
	private Integer numAnio;
	
	
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

	public Integer getNumAnio() {
		return numAnio;
	}

	public void setNumAnio(Integer numAnio) {
		this.numAnio = numAnio;
	}
	
	//bi-directional one-to-one association to DitActa
	@OneToOne
	@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
	private DitActa ditActa;

    public DitNacimiento() {
    }

	public long getCveIdDocumentoProbatorio() {
		return this.cveIdDocumentoProbatorio;
	}

	public void setCveIdDocumentoProbatorio(long cveIdDocumentoProbatorio) {
		this.cveIdDocumentoProbatorio = cveIdDocumentoProbatorio;
	}

	public String getCveCrip() {
		return this.cveCrip;
	}

	public void setCveCrip(String cveCrip) {
		this.cveCrip = cveCrip;
	}



	public DitActa getDitActa() {
		return this.ditActa;
	}

	public void setDitActa(DitActa ditActa) {
		this.ditActa = ditActa;
	}
	
}