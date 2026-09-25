package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the FDT_A3_MUESTRA_DATO_CONCEPTO database table.
 * 
 */
@Embeddable
public class FdtA3MuestraDatoConceptoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_DICTAMEN", unique=true, nullable=false, precision=22)
	private long idDictamen;

	@Column(name="REG_PATRON", unique=true, nullable=false, length=8)
	private String regPatron;

	@Column(name="CVE_MODAL", unique=true, nullable=false, precision=2)
	private long cveModal;

	@Column(name="NU_MUESTRA", unique=true, nullable=false, precision=22)
	private long nuMuestra;

	@Column(name="CVE_CONCEPTO", unique=true, nullable=false, precision=22)
	private long cveConcepto;

	@Column(name="IN_TP_CONCEPTO", unique=true, nullable=false, length=1)
	private String inTpConcepto;

    public FdtA3MuestraDatoConceptoPK() {
    }
	public long getIdDictamen() {
		return this.idDictamen;
	}
	public void setIdDictamen(long idDictamen) {
		this.idDictamen = idDictamen;
	}
	public String getRegPatron() {
		return this.regPatron;
	}
	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}
	public long getCveModal() {
		return this.cveModal;
	}
	public void setCveModal(long cveModal) {
		this.cveModal = cveModal;
	}
	public long getNuMuestra() {
		return this.nuMuestra;
	}
	public void setNuMuestra(long nuMuestra) {
		this.nuMuestra = nuMuestra;
	}
	public long getCveConcepto() {
		return this.cveConcepto;
	}
	public void setCveConcepto(long cveConcepto) {
		this.cveConcepto = cveConcepto;
	}
	public String getInTpConcepto() {
		return this.inTpConcepto;
	}
	public void setInTpConcepto(String inTpConcepto) {
		this.inTpConcepto = inTpConcepto;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FdtA3MuestraDatoConceptoPK)) {
			return false;
		}
		FdtA3MuestraDatoConceptoPK castOther = (FdtA3MuestraDatoConceptoPK)other;
		return 
			(this.idDictamen == castOther.idDictamen)
			&& this.regPatron.equals(castOther.regPatron)
			&& (this.cveModal == castOther.cveModal)
			&& (this.nuMuestra == castOther.nuMuestra)
			&& (this.cveConcepto == castOther.cveConcepto)
			&& this.inTpConcepto.equals(castOther.inTpConcepto);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idDictamen ^ (this.idDictamen >>> 32)));
		hash = hash * prime + this.regPatron.hashCode();
		hash = hash * prime + ((int) (this.cveModal ^ (this.cveModal >>> 32)));
		hash = hash * prime + ((int) (this.nuMuestra ^ (this.nuMuestra >>> 32)));
		hash = hash * prime + ((int) (this.cveConcepto ^ (this.cveConcepto >>> 32)));
		hash = hash * prime + this.inTpConcepto.hashCode();
		
		return hash;
    }
}